package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.EasyUIDataGridResult;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.service.ContentService;
import com.taotao.pojo.TaotaoResult;
import com.taotao.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 首页商品内容管理
 */

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_SYNC_URL}")
	private String REST_CONTENT_SYNC_URL;

	/**
	 * 首页商品列表查询
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public EasyUIDataGridResult getContentList(Integer page, Integer rows, Long categoryId) {
		// 查询商品列表
		TbContentExample example = new TbContentExample();
		if (categoryId != 0){
			TbContentExample.Criteria criteria = example.createCriteria();
			criteria.andCategoryIdEqualTo(categoryId);
		}
		// 分页处理
		PageHelper.startPage(page, rows);

		List<TbContent> list = contentMapper.selectByExample(example);
		// 创建一个返回值对象
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		// 记录总条数
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public TaotaoResult insertContent(TbContent content) {
		// 补全pojo内容
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);

		// 添加缓存同步逻辑
		try {
			HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + content.getCategoryId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult removeContent(long[] ids) {
		for (long id : ids) {
			// 添加缓存同步逻辑
			try {
				TbContent content = contentMapper.selectByPrimaryKey(id);
				HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + content.getCategoryId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			contentMapper.deleteByPrimaryKey(id);
		}


		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult changeContent(TbContent tbContent) {
		tbContent.setUpdated(new Date());
		contentMapper.updateByPrimaryKeyWithBLOBs(tbContent);

		// 添加缓存同步逻辑
		try {
			HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + tbContent.getCategoryId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok();
	}
}
