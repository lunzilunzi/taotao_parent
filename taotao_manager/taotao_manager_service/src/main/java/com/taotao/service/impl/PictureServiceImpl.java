package com.taotao.service.impl;

import com.taotao.pojo.PictureResult;
import com.taotao.service.PictureService;
import com.taotao.utils.FtpUtil;
import com.taotao.utils.IDUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 图片上传服务
 */

@Service
public class PictureServiceImpl implements PictureService {

	@Value("${FTP_ADDRESS}")
	private String FTP_ADDRESS;

	@Value("${FTP_PORT}")
	private Integer FTP_PORT;

	@Value("${FTP_USERNAME}")
	private String FTP_USERNAME;

	@Value("${FTP_PASSWORD}")
	private String FTP_PASSWORD;

	@Value("${FTP_BASE_PATH}")
	private String FTP_BASE_PATH;

	@Value("${IMAGE_BASE_URL}")
	private String IMAGE_BASE_URL;

	@Override
	public PictureResult uploadPicture(MultipartFile uploadFile) {
		PictureResult pictureResult = new PictureResult();

		try {
			// 生成一个新的文件名
			// 去原文件原始名字
			String oldName = uploadFile.getOriginalFilename();
			// 生成新文件名
			// UUID.randomUUID();
			String newName = IDUtils.genImageName();
			newName = newName + oldName.substring(oldName.lastIndexOf("."));

			//图片信息
			String filePath = new DateTime().toString("/yyyy/MM/dd");
			InputStream input = uploadFile.getInputStream();

			// 图片上传
		/* ip和端口号等不能写死,要写进配置文件 */
			boolean result = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FTP_BASE_PATH,
					filePath, newName, input);

			// 返回结果
			if (!result) {
				pictureResult.setError(1);
				pictureResult.setMessage("文件上传失败");
				return pictureResult;
			}

			System.out.println("图片URL: " + IMAGE_BASE_URL+filePath+"/"+newName);

			pictureResult.setError(0);
			pictureResult.setUrl(IMAGE_BASE_URL+filePath+"/"+newName);

			return pictureResult;
		} catch (IOException e) {
			pictureResult.setError(1);
			pictureResult.setMessage("文件上传发生异常");
			return pictureResult;
		}
	}
}
