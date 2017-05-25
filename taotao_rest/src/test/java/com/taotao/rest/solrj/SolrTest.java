package com.taotao.rest.solrj;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SolrTest {

	private SolrServer solrServer;

	@Test
	public void testIndexCreate() throws Exception {
		// 创建solr文档对象
		SolrInputDocument doc = new SolrInputDocument();
		// 域要先定义后使用, 还有注意必须要有id主键域
		/**
		 * solr中,域必须先定义后使用
		 * solr中,没有专门的域修改方法,会自动根据id进行查找,找到了则修改,没找到则新增
		 */
		doc.addField("id", "a005");
		doc.addField("item_title", "测试商品");
		doc.addField("item_price", "999");
		// 将文档加入solrServer对象中
		solrServer.add(doc);
	}

	@Test
	public void testIndexDel() throws Exception {
		// 根据主键id进行删除
		/*solrServer.deleteById("a001");*/

		// 根据查询删除 (删除所有)
		solrServer.deleteByQuery("*:*");
	}

	@Before
	public void before() {
		// 创建和Solr服务端连接(单机版)
		solrServer = new HttpSolrServer("http://192.168.53.60:8080/solr");
	}

	@After
	public void after() throws Exception {
		// 提交
		solrServer.commit();
	}
}
