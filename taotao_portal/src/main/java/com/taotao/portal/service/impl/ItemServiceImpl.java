package com.taotao.portal.service.impl;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.service.ItemService;
import com.taotao.utils.HttpClientUtil;
import com.taotao.utils.JsonUtils;
import com.taotao.utils.myUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 商品信息管理Service
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${ITEM_INFO_URL}")
	private String ITEM_INFO_URL;
	@Value("${ITEM_DESC_URL}")
	private String ITEM_DESC_URL;
	@Value("${ITEM_PARAM_URL}")
	private String ITEM_PARAM_URL;

	/**
	 * 取商品基本信息
	 * @param itemId
	 * @return
	 */
	@Override
	public ItemInfo getItemById(Long itemId) {
		try {
			// 调用redis服务
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_INFO_URL + itemId);
			if (!StringUtils.isBlank(json)) {
				TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, ItemInfo.class);
				if (taotaoResult.getStatus() == 200) {
					ItemInfo item = (ItemInfo)taotaoResult.getData();
					return item;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 取商品描述
	 * @param itemId
	 * @return
	 */
	@Override
	public String getItemDescById(Long itemId) {
		try {
			// 查询商品描述
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_DESC_URL + itemId);
			TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItemDesc.class);
			if (taotaoResult.getStatus() == 200) {
				TbItemDesc itemDesc = (TbItemDesc)taotaoResult.getData();
				// 取商品描述
				String result = itemDesc.getItemDesc();
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据商品id查询规格参数
	 * @param itemId
	 * @return
	 */
	@Override
	public String getItemParam(Long itemId) {
		try {
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_PARAM_URL + itemId);
			// 把json转换成java对象
			TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItemParamItem.class);
			if (taotaoResult.getStatus() == 200) {
				TbItemParamItem paramItem = (TbItemParamItem)taotaoResult.getData();
				String paramData = paramItem.getParamData();

				// 把规格参数json数据转换成java对象
				String result = myUtils.jsonToHtml(paramData);

				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}














