package com.taotao.controller;

import com.taotao.pojo.EasyUITreeNode;
import com.taotao.service.ContentCategoryService;
import com.taotao.pojo.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品内容分类管理
 */

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService contentCategoryService;

	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCatList(@RequestParam(value = "id", defaultValue = "0") long parentId) {
		List<EasyUITreeNode> categoryList = contentCategoryService.getCategoryList(parentId);
		return categoryList;
	}

	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult createContentCategory(long parentId, String name) {
		TaotaoResult result = contentCategoryService.insertContentCategory(parentId, name);
		return result;
	}

	@RequestMapping("/delete")
	@ResponseBody
	public TaotaoResult deleteContentCategory(long id) {
		TaotaoResult result = contentCategoryService.removeContentCategory(id);
		return result;
	}

	@RequestMapping("/update")
	@ResponseBody
	public TaotaoResult updateContentCategoryName(long id, String name) {
		TaotaoResult result = contentCategoryService.modifyContentCategoryName(id, name);
		return result;
	}
}


















