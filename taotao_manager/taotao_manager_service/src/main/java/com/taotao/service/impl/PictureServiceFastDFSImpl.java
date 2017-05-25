package com.taotao.service.impl;

import com.taotao.pojo.PictureResult;
import com.taotao.service.PictureService;
import com.taotao.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * (FastDFS)图片上传Service
 */

@Service("pictureServiceFastDFS")
public class PictureServiceFastDFSImpl implements PictureService {

	@Value("${FASTDFS_IMAGE_BASE_URL}")
	private String FASTDFS_IMAGE_BASE_URL;

	@Override
	public PictureResult uploadPicture(MultipartFile uploadFile) {
		PictureResult result = new PictureResult();
		// 判断图片是否为空
		if (uploadFile.isEmpty()) {
			result.setError(1);
			result.setMessage("图片为空");
			return result;
		}
		// 上传到图片服务器
		try {
			// 取图片扩展名
			String originalFilename = uploadFile.getOriginalFilename();
			// 去扩展名不要"."
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
			FastDFSClient client = new FastDFSClient("classpath:properties/client.conf");
			String url = client.uploadFile(uploadFile.getBytes(), extName);
			// 拼接服务器地址完整url
			url = FASTDFS_IMAGE_BASE_URL + url;
			// 把url响应给客户端
			result.setError(0);
			result.setUrl(url);
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(1);
			result.setMessage("图片上传失败");
		}
		return result;
	}
}
