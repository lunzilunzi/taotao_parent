package com.taotao.service;

import com.taotao.pojo.EasyUIDataGridResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.utils.TaotaoResult;

public interface ItemParamService {
	TaotaoResult getItemParamByCid(long cid);
	TaotaoResult insertItemParm(TbItemParam itemParam);
	EasyUIDataGridResult getItemParamList(Integer page, Integer rows);
}
