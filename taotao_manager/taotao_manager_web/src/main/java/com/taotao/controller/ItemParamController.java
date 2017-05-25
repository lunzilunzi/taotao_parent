package com.taotao.controller;

import com.taotao.pojo.EasyUIDataGridResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;
import com.taotao.pojo.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品规格参数模板管理Controller
 */

@Controller
@RequestMapping("/item/param")
public class ItemParamController {

	@Autowired
	private ItemParamService itemParamService;

	@RequestMapping("/query/itemcatid/{itemCatId}")
	@ResponseBody
	public TaotaoResult getItemParamByCid(@PathVariable Long itemCatId) {
		TaotaoResult result = itemParamService.getItemParamByCid(itemCatId);
		return result;
	}

	@RequestMapping("/save/{cid}")
	@ResponseBody
	public TaotaoResult insertItemParm(@PathVariable Long cid, String paramData) {
		// 创建pojo对象
		TbItemParam itemParam = new TbItemParam();
		itemParam.setItemCatId(cid);
		itemParam.setParamData(paramData);
		TaotaoResult result = itemParamService.insertItemParm(itemParam);

		return result;
	}

	@RequestMapping("/list")
	@ResponseBody
	public EasyUIDataGridResult getItemParamList(Integer page, Integer rows) {
		EasyUIDataGridResult result = itemParamService.getItemParamList(page, rows);
		return result;
	}
}
