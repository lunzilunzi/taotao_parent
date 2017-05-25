package com.taotao.rest.controller;

import com.taotao.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/itemcat")
public class ItemCatController {

	@Autowired
	ItemCatService itemCatService;
/*

	@RequestMapping(value = "/list",
			produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
	@ResponseBody
	public String getItemCatList(String callback) {
		CatResult catResult = itemCatService.getItemCatList();
		// 把pojo转换成字符串
		String json = JsonUtils.objectToJson(catResult);
		// 拼装返回值
		String result = callback + "(" + json + ");";
		return result;
	}
*/

	/**
	 * 使用工具类,spring4.1版本后才有
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Object getItemCatList(String callback) {
		CatResult catResult = itemCatService.getItemCatList();
		MappingJacksonValue jackson = new MappingJacksonValue(catResult);
		jackson.setJsonpFunction(callback);
		return jackson;
	}

}
