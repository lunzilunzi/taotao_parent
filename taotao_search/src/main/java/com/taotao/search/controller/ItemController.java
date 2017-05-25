package com.taotao.search.controller;

import com.taotao.pojo.TaotaoResult;
import com.taotao.search.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 索引库维护
 */
// /search/manager/importall
@Controller
@RequestMapping("/manager")
public class ItemController {

	@Autowired
	private ItemService itemService;

	/**
	 * 导入商品数据到索引库
	 */
	@RequestMapping("/importall")
	@ResponseBody
	public TaotaoResult importAllItems() {
		TaotaoResult result = itemService.importAllItems();
		return result;
	}

	/**
	 * 新增或更新field到索引库
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TaotaoResult addOrUpdateItem(long id) {
		TaotaoResult result = itemService.addOrModifyItemField(id);
		return result;
	}

	/**
	 * 删除索引库的field
	 */
	@RequestMapping("/remove")
	@ResponseBody
	public TaotaoResult removeItem(long id) {
		TaotaoResult result = itemService.removeItemField(id);
		return result;
	}
}
