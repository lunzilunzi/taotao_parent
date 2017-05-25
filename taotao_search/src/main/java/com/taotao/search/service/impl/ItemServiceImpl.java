package com.taotao.search.service.impl;

import com.taotao.pojo.TaotaoResult;
import com.taotao.search.mapper.ItemMapper;
import com.taotao.pojo.Item;
import com.taotao.search.service.ItemService;
import com.taotao.utils.ExceptionUtil;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SolrServer solrServer;

	@Override
	public TaotaoResult importAllItems(){
		try {
			// 查询商品列表
			List<Item> items = itemMapper.searchItemList();
			// 把商品信息写入索引库
			for (Item item : items) {
				setSolrField(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return TaotaoResult.ok();
	}

	/**
	 * 添加或修改field
	 * @param id
	 * @return
	 */
	@Override
	public TaotaoResult addOrModifyItemField(long id) {
		try {
			Item item = itemMapper.searchItemById(id);
			setSolrField(item);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return TaotaoResult.ok();
	}

	/**
	 * 删除field
	 * @param id
	 * @return
	 */
	@Override
	public TaotaoResult removeItemField(long id) {
		try {
			solrServer.deleteById(id + "");
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return TaotaoResult.ok();
	}

	/**
	 * 设置solr库中商品field
	 * @param item 商品对象
	 * @return Solr文档对象
	 */
	private void setSolrField(Item item) throws Exception {
		// 创建solr文档对象
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", item.getId());
		doc.addField("item_title", item.getTitle());
		doc.addField("item_sell_point", item.getSell_point());
		doc.addField("item_price", item.getPrice());
		doc.addField("item_image", item.getImage());
		doc.addField("item_category_name", item.getCategory_name());
		doc.addField("item_desc", item.getItem_des());

		// 将文档加入solrServer对象中
		solrServer.add(doc);
		// 提交
		solrServer.commit();
	}
}
