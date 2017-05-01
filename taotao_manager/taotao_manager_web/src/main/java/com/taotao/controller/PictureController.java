package com.taotao.controller;

import com.taotao.service.PictureService;
import com.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 上传图片处理
 */

@Controller
@RequestMapping("/pic")
public class PictureController {

	@Autowired
	private PictureService pictureService;

	@RequestMapping("/upload")
	@ResponseBody
	public String pictureUpload(MultipartFile uploadFile){
		Map result = pictureService.uploadPicture(uploadFile);
		// 为了保证功能的兼容性,需要把Result转换成json格式的字符串
		String json = JsonUtils.objectToJson(result);
		return json;
	}

}
