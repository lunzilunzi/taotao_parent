package com.taotao.rest.service.impl;

import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.*;
import com.taotao.pojo.TbItemParamItemExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ItemService;
import com.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Date 2017/5/24 18:01.
 * 实现商品Service服务
 */

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	@Autowired
	private JedisClient jedisClient;

	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	@Value("${REDIS_ITEM_EXPIRE}")
	private Integer REDIS_ITEM_EXPIRE;

	/**
	 * 根据id获取商品信息
	 *
	 * @param itemId 商品id
	 * @return 置入商品对象的TaotaoResult对象
	 */
	@Override
	public TaotaoResult getItemBaseInfo(long itemId) {
		// 设置商品key
		String key = REDIS_ITEM_KEY + ":" + itemId + ":base";

		/*从redis缓存中查询商品*/
		try {
			String json = jedisClient.get(key);
			// 判断是否有值
			if (!StringUtils.isBlank(json)) {
				TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
				// 设置key的有效期
				jedisClient.expire(key, REDIS_ITEM_EXPIRE);
				return TaotaoResult.ok(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//根据商品id查询商品信息
		TbItem item = itemMapper.selectByPrimaryKey(itemId);

		/*把商品写入缓存*/
		try {
			jedisSet(key, item, REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok(item);
	}

	/**
	 * 根据id获取商品描述
	 *
	 * @param itemId 商品id
	 * @return 置入商品描述对象的TaotaoResult对象
	 */
	@Override
	public TaotaoResult getItemDescInfo(long itemId) {
		// 设置商品key
		String key = REDIS_ITEM_KEY + ":" + itemId + ":desc";

		/*从redis缓存中查询商品描述*/
		try {
			String json = jedisClient.get(key);
			// 判断是否有值
			if (!StringUtils.isBlank(json)) {
				TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				// 设置key的有效期
				jedisClient.expire(key, REDIS_ITEM_EXPIRE);
				return TaotaoResult.ok(itemDesc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//根据商品id查询商品信息
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);

		/*把商品描述写入缓存*/
		try {
			jedisSet(key, itemDesc, REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok(itemDesc);
	}

	/**
	 * 根据id获取商品规格参数
	 *
	 * @param itemId 商品id
	 * @return 置入商品规格参数对象的TaotaoResult对象
	 */
	@Override
	public TaotaoResult getItemParamInfo(long itemId) {
		// 设置商品key
		String key = REDIS_ITEM_KEY + ":" + itemId + ":Param";

		/*从redis缓存中查询商品描述*/
		try {
			String json = jedisClient.get(key);
			// 判断是否有值
			if (!StringUtils.isBlank(json)) {
				TbItemParamItem paramItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
				// 设置key的有效期
				jedisClient.expire(key, REDIS_ITEM_EXPIRE);
				return TaotaoResult.ok(paramItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 设置查询条件
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		//执行查询
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if (list != null && list.size() > 0){
			TbItemParamItem paramItem = list.get(0);
			/*把商品描述写入缓存*/
			try {
				jedisSet(key, paramItem, REDIS_ITEM_EXPIRE);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return TaotaoResult.ok(paramItem);
		}


		return TaotaoResult.build(400, "无此商品规格");
	}

	/**
	 * 设置redis缓存
	 * @param key 缓存key
	 * @param obj 需要被缓存的对象
	 * @param time 有效时间
	 */
	private void jedisSet(String key, Object obj, int time) {
		String value = JsonUtils.objectToJson(obj);
		jedisClient.set(key, value);
		// 设置key的有效期
		jedisClient.expire(key, REDIS_ITEM_EXPIRE);
	}

}
