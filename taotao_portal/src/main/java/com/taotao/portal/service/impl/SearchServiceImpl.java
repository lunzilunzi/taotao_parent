package com.taotao.portal.service.impl;

import com.taotao.pojo.SearchResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.portal.service.SearchService;
import com.taotao.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 商品搜索Service
 */

@Service
public class SearchServiceImpl implements SearchService {

	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;

	@Override
	public SearchResult search(String queryString, int page) {
		// 调用taotao-search的服务
		// 查询参数
		Map<String, String> param = new LinkedHashMap();
		param.put("q", queryString);
		param.put("page", page+"");
		try {
			// 调用服务
			String json = HttpClientUtil.doGet(SEARCH_BASE_URL, param);
			TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, SearchResult.class);

			if (taotaoResult.getStatus() == 200) {
				SearchResult result = (SearchResult) taotaoResult.getData();
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
