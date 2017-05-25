package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.EasyUIDataGridResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.service.ItemParamService;
import com.taotao.pojo.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 商品规格参数模板管理
 */

@Service
public class ItemParamServiceImpl implements ItemParamService {

	@Autowired
	private TbItemParamMapper itemParamMapper;

	@Override
	public TaotaoResult getItemParamByCid(long cid) {
		System.out.println("cid" + cid);
		TbItemParamExample example = new TbItemParamExample();
		TbItemParamExample.Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);

		// 判断是否查询到结果
		if (list != null && list.size() > 0) {
			System.out.println("list不为空");
			return TaotaoResult.ok(list.get(0));
		}
		System.out.println("list为空");
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult insertItemParm(TbItemParam itemParam) {
		// 补全pojo
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());

		// 插入到规格参数模板表
		itemParamMapper.insert(itemParam);

		return TaotaoResult.ok();
	}

	@Override
	public EasyUIDataGridResult getItemParamList(Integer page, Integer rows) {
		System.out.println("page :"+page+",rows: "+rows);
		// 查询商品规格列表
		TbItemParamExample example = new TbItemParamExample();
		// 分页处理
		PageHelper.startPage(page, rows);

		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		System.out.println("list.size : "+list.size());
		// 创建一个返回值对象
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		// 记录总条数
		PageInfo<TbItemParam> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}
}
