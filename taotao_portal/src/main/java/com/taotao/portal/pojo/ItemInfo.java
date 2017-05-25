package com.taotao.portal.pojo;

import com.taotao.pojo.TbItem;

/**
 * 商品基本信息类
 */
public class ItemInfo extends TbItem {

	/**
	 * 添加一个切割图片的方法
	 * @return 将多个图片地址,切割成的数组
	 */
	public String[] getImages() {
		String image = getImage();
		if (image != null) {
			String[] images = image.split(",");
			return images;
		}
		return null;
	}
}
