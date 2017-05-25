package com.taotao.search.dao.impl;

import com.taotao.search.dao.SearchDao;
import com.taotao.pojo.Item;
import com.taotao.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SearchDaoImpl implements SearchDao {

	@Autowired
	private SolrServer solrServer;

	@Value("${HL_PARAM}")
	private String HL_PARAM;

	@Override
	public SearchResult search(SolrQuery query) throws Exception {

		// 创建查询返回对象
		SearchResult result = new SearchResult();
		List<Item> itemList = new ArrayList<>();
		// 使用查询对象获取查询响应对象
		QueryResponse queryResponse = solrServer.query(query);
		// 获取结果集对象
		SolrDocumentList results = queryResponse.getResults();

		/* 获取高亮 */
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		// 设置总记录数
		result.setRecordCount(results.getNumFound());
		// 打印查询结果条数
//		System.out.println("======count数: " + results.getNumFound());
		// 遍历查询结果集
		for (SolrDocument doc : results) {
			// 创建商品对象
			Item item = new Item();

//			System.out.println("id------"+doc.get("id"));
			item.setId((String) doc.get("id"));
			// 获取高亮
			if (highlighting != null && highlighting.size() > 0) {
				List<String> list = highlighting.get(doc.get("id")).get(HL_PARAM);
				if (list != null && list.size() > 0) {
					String itemTitle = list.get(0);
//					System.out.println("高亮------"+itemTitle);
					item.setTitle(itemTitle);
				} else {
//					System.out.println("普通------"+doc.get("item_title"));
					item.setTitle((String)doc.get("item_title"));
				}
			}
			item.setSell_point((String )doc.get("item_sell_point"));
			item.setPrice((long )doc.get("item_price"));
			item.setImage((String  )doc.get("item_image"));
			item.setCategory_name((String  )doc.get("item_category_name"));

			itemList.add(item);

//			System.out.println("title------"+doc.get("item_title"));
//			System.out.println("point------"+doc.get("item_sell_point"));
//			System.out.println("price------"+doc.get("item_price"));
//			System.out.println("image------"+doc.get("item_image"));
//			System.out.println("_name------"+doc.get("item_category_name"));
//			System.out.println("===========================");
		}

		result.setItemList(itemList);

		return result;
	}

	public void test() {
		System.out.println("测试");
	}
}
