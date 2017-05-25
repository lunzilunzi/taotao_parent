package com.taotao.rest.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisTest {

	@Test
	public void testJedis1() throws Exception {
		// 创建redis链接
		Jedis jedis = new Jedis("192.168.53.50", 6379);

		/* 操作 */
		// 存入
		jedis.set("a", "2");
		// 取出
		System.out.println(jedis.get("a"));
		// 关闭
		jedis.close();
	}

	@Test
	public void testJedisPoll() throws Exception {
		// 创建连接池
		JedisPool pool = new JedisPool("192.168.53.50", 6379);
		// 获取链接
		Jedis jedis = pool.getResource();

		jedis.set("b", "3");
		System.out.println(jedis.get("b"));
		// 使用连接时,连接使用完后一定要关闭,关闭连接会自动回到连接池供别人使用,
		//  如果一直不关闭, 连接池数量耗尽后会死机
		jedis.close();
	}
}
