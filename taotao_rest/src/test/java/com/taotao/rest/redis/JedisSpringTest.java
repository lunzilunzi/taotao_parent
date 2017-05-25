package com.taotao.rest.redis;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;

public class JedisSpringTest {

	private ApplicationContext context;

	@Before
	public void SetUp() {
		String config = "classpath:spring/applicationContext-redis.xml";
		context = new ClassPathXmlApplicationContext(config);
	}

	@Test
	public void TestSpring() throws Exception {
		// 获取连接池
		JedisPool jedisPool = context.getBean("jedisClient", JedisPool.class);
		// 获取链接
		Jedis jedis = jedisPool.getResource();

		// 存入
		jedis.set("c", "4");
		// 取出
		System.out.println(jedis.get("c"));
	}

	/**
	 * 集群版测试
	 */
	@Test
	public void testJedisCluster() {
//		HashSet<HostAndPort> nodes = new HashSet<>();
//		nodes.add(new HostAndPort("192.168.53.50", 7001));
//		nodes.add(new HostAndPort("192.168.53.50", 7002));
//		nodes.add(new HostAndPort("192.168.53.50", 7003));
//		nodes.add(new HostAndPort("192.168.53.50", 7004));
//		nodes.add(new HostAndPort("192.168.53.50", 7005));
//		nodes.add(new HostAndPort("192.168.53.50", 7006));
//
//		JedisCluster cluster = new JedisCluster(nodes);
//
//		cluster.set("key", "1000");
//		String key = cluster.get("key");
//		System.out.println(key);
//
//		cluster.close();
	}

	@Test
	public void testSpringJedisSingle() {
		JedisPool jedisPool = context.getBean("jedisClient", JedisPool.class);
		Jedis jedis = jedisPool.getResource();
		String key = jedis.get("key");
		System.out.println(key);
	}

	@Test
	public void testSpringJedisClient() {
//		JedisCluster JedisCluster = context.getBean("jedisClient", JedisCluster.class);
//		String key = JedisCluster.get("key");
//		System.out.println(key);
	}
}
