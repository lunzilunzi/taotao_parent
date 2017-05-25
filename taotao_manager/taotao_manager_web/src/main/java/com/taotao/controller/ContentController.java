package com.taotao.controller;

import com.taotao.pojo.EasyUIDataGridResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;
import com.taotao.pojo.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 内容管理Controller
 */

@Controller
@RequestMapping("/content")
public class ContentController {

	@Autowired
	private ContentService contentService;

	/**
	 * 内容分页展示
	 * @param page 第几页
	 * @param rows 每页显示多少
	 * @param categoryId 内容类型id
	 */
	@RequestMapping("/query/list")
	@ResponseBody
	public EasyUIDataGridResult getContentList(Integer page, Integer rows, Long categoryId) {
		EasyUIDataGridResult result = contentService.getContentList(page, rows, categoryId);
		return result;
	}

	/**
	 * 内容添加
	 * @param content 内容对象
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult insertContent(TbContent content){
		TaotaoResult result = contentService.insertContent(content);
		return result;
	}

	/**
	 * 删除内容
	 * @param ids 内容id数组
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public TaotaoResult removeContent(long[] ids) {
		TaotaoResult result = null;
		if (ids != null) {
			result =contentService.removeContent(ids);
		}
		return result;
	}

	@RequestMapping("/edit")
	@ResponseBody
	public TaotaoResult modifyContent(TbContent tbContent) {
		TaotaoResult result = contentService.changeContent(tbContent);
		return result;
	}


}
