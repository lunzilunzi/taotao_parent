package com.taotao.service.impl;

import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.service.ItemParamItemService;
import com.taotao.utils.JsonUtils;
import com.taotao.utils.myUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ItemParamItemServiceImpl implements ItemParamItemService {

	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	@Override
	public String getItemParamByItemId(Long itemId) {
		// 根据商品id查询规格参数
		TbItemParamItemExample example = new TbItemParamItemExample();
		TbItemParamItemExample.Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);

		// 执行查询
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if (list != null && list.size() == 0) {
			return "";
		}
		// 取规格参数信息
		TbItemParamItem itemParamItem = list.get(0);
		String paramData = itemParamItem.getParamData();
		System.out.println("paramData : " + paramData);
		// 生成HTML

		// 把规格参数json数据转换成html数据
		String result = myUtils.jsonToHtml(paramData);

		return result;

	}
}
