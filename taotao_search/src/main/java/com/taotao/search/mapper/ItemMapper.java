package com.taotao.search.mapper;

import com.taotao.pojo.Item;

import java.util.List;

public interface ItemMapper {
	/**
	 * 查询商品列表
	 * @return
	 */
	List<Item> searchItemList();

	/**
	 * 查询根据id商品
	 */
	Item searchItemById(long id);
}
