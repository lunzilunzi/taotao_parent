package com.taotao.controller;

import com.taotao.pojo.EasyUIDataGridResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import com.taotao.pojo.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品管理Controller
 */
@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;

	@RequestMapping("/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId){
		TbItem tbItem = itemService.getItemById(itemId);
		return tbItem;
	}

	/**
	 * 展示所有商品
	 * @param page 分页第几页
	 * @param rows 分页每页多少
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		EasyUIDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}

	/**
	 * 添加单个商品
	 * @param item 商品信息
	 * @param desc 商品描述(因为商品描述与商品表分开)
	 * @param itemParams
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult createItem(TbItem item, String desc, String itemParams) throws Exception {
		TaotaoResult result = itemService.createItem(item, desc, itemParams);
		// 添加索引库
		result = itemService.addOrUpdateItem((String) result.getData());
		return result;
	}
}
