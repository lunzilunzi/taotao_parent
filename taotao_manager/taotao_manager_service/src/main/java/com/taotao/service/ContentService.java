package com.taotao.service;

import com.taotao.pojo.EasyUIDataGridResult;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TaotaoResult;

public interface ContentService {
	EasyUIDataGridResult getContentList(Integer page, Integer rows, Long categoryId);
	TaotaoResult insertContent(TbContent content);
	TaotaoResult removeContent(long[] ids);
	TaotaoResult changeContent(TbContent tbContent);
}
