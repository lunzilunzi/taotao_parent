package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.*;
import com.taotao.service.ItemService;
import com.taotao.utils.HttpClientUtil;
import com.taotao.utils.IDUtils;
import com.taotao.pojo.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品管理Service
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	@Value("${SOLR_BASE_URL}")
	private String SOLR_BASE_URL;
	@Value("${SOLR_REMOVE_URL}")
	private String SOLR_REMOVE_URL;
	@Value("${SOLR_UPDATE_URL}")
	private String SOLR_UPDATE_URL;

	@Override
	public TbItem getItemById(long itemId) {

		/*利用ID查询*/
//		itemMapper.selectByPrimaryKey(itemId);

		/*利用模板对象查询*/
		TbItemExample example = new TbItemExample();
		TbItemExample.Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);

		List<TbItem> tbItems = itemMapper.selectByExample(example);
		if (tbItems != null && tbItems.size() > 0){
			TbItem item = tbItems.get(0);
			return item;
		}

		return null;
	}

	/**
	 * 商品列表查询
	 * @param page 第几页
	 * @param rows 每页显示多少行
	 * @return
	 */

	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		// 查询商品列表
		TbItemExample example = new TbItemExample();
		//分页处理
		PageHelper.startPage(page, rows);
		List<TbItem> list = itemMapper.selectByExample(example);
		//创建一个返回值对象
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		//记录总条数
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	/**
	 * 添加商品
	 * @param item 商品对象
	 * @param desc 商品详情
	 * @param itemParam 商品规格信息
	 * @return
	 * @throws Exception
	 */

	@Override
	public TaotaoResult createItem(TbItem item, String desc, String itemParam) throws Exception {
		// item补全
		// 生成商品ID
		Long itemId = IDUtils.genItemId();
		item.setId(itemId);
		// 商品状态, 1-正常, 2-下架, 3-删除
		item.setStatus((byte)1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		// 插入到数据库
		itemMapper.insert(item);

		// 添加商品描述
		TaotaoResult result = insertItemDesc(itemId, desc);
		if (result.getStatus() != 200) {
			throw new Exception();
		}

		/*添加规格参数*/
		result = insertItemParamItem(itemId, itemParam);
		if (result.getStatus() != 200) {
			throw new Exception();
		}


		return  TaotaoResult.ok(itemId+"");
	}

	/**
	 * 添加商品描述
	 * @param itemId
	 * @param desc
	 * @return
	 */
	private TaotaoResult insertItemDesc(Long itemId, String desc) {
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		itemDescMapper.insert(itemDesc);

		return TaotaoResult.ok();
	}

	/**
	 * 添加field至索引库
	 * @param itemId 商品id
	 */
	@Override
	public TaotaoResult addOrUpdateItem(String itemId) throws Exception {

		// 添加field到solr索引库
		Map<String, String> param = new LinkedHashMap<>();
		param.put("id", itemId);
		String json = HttpClientUtil.doGet(SOLR_BASE_URL + SOLR_UPDATE_URL, param);
		TaotaoResult result = TaotaoResult.formatToPojo(json, null);

		if (result.getStatus() != 200) {
			throw new Exception();
		}
		return result;
	}

	/**
	 * 添加规格参数
	 * @param itemId
	 * @param itemParams
	 * @return
	 */
	private TaotaoResult insertItemParamItem(Long itemId, String itemParams) {
		// 创建一个pojo
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParams);
		itemParamItem.setCreated(new Date());
		itemParamItem.setUpdated(new Date());

		// 向表中插入数据
		itemParamItemMapper.insert(itemParamItem);
		return TaotaoResult.ok();
	}
}
