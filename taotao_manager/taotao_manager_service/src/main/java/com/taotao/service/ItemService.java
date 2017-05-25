package com.taotao.service;

import com.taotao.pojo.EasyUIDataGridResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TaotaoResult;

public interface ItemService {

	TbItem getItemById(long itemId);
	EasyUIDataGridResult getItemList(int page, int rows);
	TaotaoResult createItem(TbItem item, String desc, String itemParams) throws Exception;

	TaotaoResult addOrUpdateItem(String itemId) throws Exception;
}
