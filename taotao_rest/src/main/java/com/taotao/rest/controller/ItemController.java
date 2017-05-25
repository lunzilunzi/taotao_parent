package com.taotao.rest.controller;

import com.taotao.pojo.TaotaoResult;
import com.taotao.rest.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品Controller
 */

@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;

	/**
	 * 根据id获取商品信息
	 * @param itemId 商品信息Id
	 * @return
	 */
	@RequestMapping("/info/{itemId}")
	@ResponseBody
	public TaotaoResult getItemBaseInfo(@PathVariable Long itemId) {
		TaotaoResult result = itemService.getItemBaseInfo(itemId);
		return result;
	}

	/**
	 * 根据id获取商品描述信息
	 * @param itemId 商品信息Id
	 * @return
	 */
	@RequestMapping("/desc/{itemId}")
	@ResponseBody
	public TaotaoResult getItemDescInfo(@PathVariable Long itemId) {
		TaotaoResult result = itemService.getItemDescInfo(itemId);
		return result;
	}

	/**
	 * 根据id获取商品规格信息
	 * @param itemId 商品信息Id
	 * @return
	 */
	@RequestMapping("/param/{itemId}")
	@ResponseBody
	public TaotaoResult getItemParam(@PathVariable Long itemId) {
		TaotaoResult result = itemService.getItemParamInfo(itemId);
		return result;
	}

}

