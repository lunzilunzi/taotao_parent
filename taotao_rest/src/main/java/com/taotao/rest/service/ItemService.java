package com.taotao.rest.service;

import com.taotao.pojo.TaotaoResult;

/**
 * 商品管理Service
 */
public interface ItemService {
	// 根据id获取商品信息
	TaotaoResult getItemBaseInfo(long itemId);
	// 根据id获取商品描述信息
	TaotaoResult getItemDescInfo(long itemId);
	// 根据id获取商品规格参数信息
	TaotaoResult getItemParamInfo(long itemId);
}
