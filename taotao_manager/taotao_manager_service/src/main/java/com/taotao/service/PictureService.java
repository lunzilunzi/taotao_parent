package com.taotao.service;

import com.taotao.pojo.PictureResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface PictureService {
	PictureResult uploadPicture(MultipartFile uploadFile);
}
