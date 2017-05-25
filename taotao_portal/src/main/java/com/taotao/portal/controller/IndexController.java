package com.taotao.portal.controller;

import com.taotao.pojo.TaotaoResult;
import com.taotao.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 访问首页
 */

@Controller
public class IndexController {

	@Autowired
	private ContentService contentService;

	@RequestMapping("/index")
	public String showIndex(Model model) {
		String adJson = contentService.getContentList();
		model.addAttribute("ad1", adJson);
		return "index";
	}

	/**
	 * HttpClient的post方法测试
	 */
	@RequestMapping(value = "/httpClient/post", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult testPost(String username, String password) {
		String result = "username:" + username + "\tpassword" + password;
		System.out.println("result"+result);
		return TaotaoResult.ok(result);
	}

	@RequestMapping(value = "/httpClient/dopost", method = RequestMethod.POST)
	@ResponseBody
	public String doPost() {
		return "ok";
	}

//	@RequestMapping(value = "/httpClient/post", method = RequestMethod.POST)
//	@ResponseBody
//	public String testPost(String username, String password) {
//		return "username:" + username + "\tpassword" + password;
//	}

	@RequestMapping("/item")
	public String showItem() {
		return "item";
	}
}













