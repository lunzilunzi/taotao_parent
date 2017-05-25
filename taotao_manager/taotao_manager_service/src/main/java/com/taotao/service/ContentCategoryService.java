package com.taotao.service;

import com.taotao.pojo.EasyUITreeNode;
import com.taotao.pojo.TaotaoResult;

import java.util.List;

public interface ContentCategoryService {

	List<EasyUITreeNode> getCategoryList(long parentId);
	TaotaoResult insertContentCategory(long parentId, String name);
	TaotaoResult removeContentCategory(long id);
	TaotaoResult modifyContentCategoryName(long id, String name);
}
