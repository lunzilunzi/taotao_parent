package com.taotao.rest.service.impl;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.CatNode;
import com.taotao.pojo.CatResult;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品分类管理
 */

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Override
	public CatResult getItemCatList() {
		CatResult catResult = new CatResult();
		catResult.setData(getCatList(0));
		return catResult;
	}

	/**
	 * 查询分类列表
	 * @param parentId
	 * @return
	 */
	private List<?> getCatList(long parentId) {
		// 创建查询模板对象
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 返回结果对象集
		List result = new ArrayList<>();
		// 得到所有根节点
		List<TbItemCat> itemCatList = itemCatMapper.selectByExample(example);
		// 网站首页标签超过了,限制取值的数量
		int count = 0;

		for (TbItemCat itemCat : itemCatList) {
			if (itemCat.getIsParent()) {
				CatNode node = new CatNode();
				if (itemCat.getParentId() == 0) {
					node.setName("<a href='/products/"+itemCat.getId()+".html'>"+itemCat.getName()+"</a>");
				} else {
					node.setName(itemCat.getName());
				}
				node.setUrl("/products/"+itemCat.getName()+".html");
				node.setItem(getCatList(itemCat.getId()));

				result.add(node);
				count++;
				// 第一级第一层只去14条记录
				if (count>=14 && parentId==0) break;
			} else {
				result.add("/products/"+itemCat.getId()+".html|"+itemCat.getName());
			}
		}
		return result;
	}
}
