package com.taotao.service.impl;

import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.EasyUITreeNode;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.ContentCategoryService;
import com.taotao.pojo.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 内容分类管理Service
 */

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;

	@Override
	public List<EasyUITreeNode> getCategoryList(long parentId) {
		// 根据parentId查询节点列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 执行查询
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<EasyUITreeNode> resultList = new ArrayList<>();

		for (TbContentCategory tbCC : list) {
			// 创建一个节点
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbCC.getId());
			node.setText(tbCC.getName());
			node.setState(tbCC.getIsParent()?"closed":"open");
			// 添加到列表中
			resultList.add(node);
		}
		return resultList;
	}

	@Override
	public TaotaoResult insertContentCategory(long parentId, String name) {
		// 创建一个pojo
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setName(name);
		contentCategory.setIsParent(false);
		// '状态', 可选值:1(正常), 2(删除)
		contentCategory.setStatus(1);
		contentCategory.setParentId(parentId);
		contentCategory.setSortOrder(1);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());

		// 添加记录
		contentCategoryMapper.insert(contentCategory);

		// 查看父节点的isParent列是否为true,如果不是改为true
		TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
		// 判断是否为true
		if (!parentCat.getIsParent()) {
			parentCat.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKey(parentCat);
		}
		// 返回结果
		return TaotaoResult.ok(contentCategory);
	}

	@Override
	public TaotaoResult removeContentCategory(long id) {
		// 查询id对应的节点
		TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(id);
		// 创建查询模板
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(category.getParentId());

		// 删除当前节点
		contentCategoryMapper.deleteByPrimaryKey(id);
		// 得到父节点的所有子节点集合
		List<TbContentCategory> categoryList = contentCategoryMapper.selectByExample(example);
		// 判断如果没有子节点,则改变isParent属性
		if (categoryList.size()==0){
			TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(category.getParentId());
			contentCategory.setIsParent(false);
			contentCategoryMapper.updateByPrimaryKey(contentCategory);
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult modifyContentCategoryName(long id, String name) {
		// 查询id对应的节点
		TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(id);
		// 修改名字
		category.setName(name);
		// 更新数据库
		contentCategoryMapper.updateByPrimaryKey(category);
		return TaotaoResult.ok();
	}
}
