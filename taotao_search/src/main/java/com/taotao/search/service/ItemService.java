package com.taotao.search.service;

import com.taotao.pojo.Item;
import com.taotao.pojo.TaotaoResult;

public interface ItemService {
	TaotaoResult importAllItems();
	TaotaoResult addOrModifyItemField(long id);
	TaotaoResult removeItemField(long id);
}
