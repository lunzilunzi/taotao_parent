package com.taotao.rest.controller;

import com.taotao.pojo.TaotaoResult;
import com.taotao.rest.service.RedisSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 缓存同步controller
 */

@Controller
@RequestMapping("/cache/sync")
public class RedisSyncController {

	@Autowired
	private RedisSyncService redisService;

	@RequestMapping("/content/{contentCid}")
	@ResponseBody
	private TaotaoResult contentCacheSync(@PathVariable long contentCid) {
		TaotaoResult result = redisService.syncContent(contentCid);
		return result;
	}
}
