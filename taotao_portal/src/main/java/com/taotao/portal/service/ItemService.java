package com.taotao.portal.service;

import com.taotao.portal.pojo.ItemInfo;

/**
 * 商品Service
 */
public interface ItemService {
	// 根据id获取商品信息
	ItemInfo getItemById(Long itemId);
	// 根据id获取商品描述
	String getItemDescById(Long itemId);
	// 根据id获取商品规格参数
	String getItemParam(Long itemId);
}
