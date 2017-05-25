package com.taotao.rest.service;

import com.taotao.pojo.TaotaoResult;

/**
 * 缓存同步Service
 */
public interface RedisSyncService {
	// 同步(删除)内容缓存
	TaotaoResult syncContent(long contentCid);
}
