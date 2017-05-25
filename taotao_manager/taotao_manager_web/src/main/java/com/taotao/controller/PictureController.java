package com.taotao.controller;

import com.taotao.pojo.PictureResult;
import com.taotao.service.PictureService;
import com.taotao.utils.JsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 上传图片处理
 */

@Controller
@RequestMapping("/pic")
public class PictureController {

	@Resource(name="pictureServiceFastDFS")
	private PictureService pictureService;

	@RequestMapping("/upload")
	@ResponseBody
	public String pictureUpload(MultipartFile uploadFile){
		PictureResult result = pictureService.uploadPicture(uploadFile);
		// 为了保证功能的兼容性,需要把Result转换成json格式的字符串
		String json = JsonUtils.objectToJson(result);
		return json;
	}

}
