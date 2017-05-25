package com.taotao.portal.controller;

import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品详情页面展示Controller
 */

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;

	/**
	 * 查询商品信息
	 * @param itemId 商品id
	 * @param model
	 */
	@RequestMapping("/item/{itemId}")
	public String showItem(@PathVariable long itemId, Model model) {
		ItemInfo item = itemService.getItemById(itemId);
		model.addAttribute("item", item);

		return "item";
	}

	/**
	 * 查询商品描述信息
	 * @param itemId 商品id
	 * produces 的参数是解决乱码
	 */
	@RequestMapping(value = "/item/desc/{itemId}", produces = MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String getItemDesc(@PathVariable long itemId) {
		String result = itemService.getItemDescById(itemId);

		return result;
	}

	/**
	 * 查询商品规格信息
	 * @param itemId 商品id
	 * produces 的参数是解决乱码
	 */
	@RequestMapping(value = "/item/param/{itemId}", produces = MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String getItemParam(@PathVariable long itemId) {
		String result = itemService.getItemParam(itemId);

		return result;
	}
}













