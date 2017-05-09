package com.chanzor.util;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * redis master-slave
 * 
 * @author Administrator
 *
 */
public class RedisMasterSlave {

	private static RedisMasterSlave instance;

	private ShardedJedisPool pool;

	private ShardedJedis client;

	public static RedisMasterSlave getInstance() {
		if (instance == null) {
			instance = new RedisMasterSlave();
		}
		return instance;
	}

	public RedisMasterSlave() {
		init();
	}

	private void init() {
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
//		shards.add(new JedisShardInfo("123.57.45.220", 6385));
//		shards.add(new JedisShardInfo("123.57.45.220", 6386));
//		shards.add(new JedisShardInfo("123.57.45.220", 6387));
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(10);
		config.setMaxWaitMillis(3 * 1000);
		config.setMaxTotal(30);
		pool = new ShardedJedisPool(config, shards);
	}

	private void close() {
		if (client != null) {
			client.close();
		}
		pool.destroy();
	}

	public void setKey(String key, String value) {

	}

	public String getStringKey(String key) {
		client = pool.getResource();
		String value = client.get(key);
		return value;
	}

//	public static void main(String[] args) {
//		RedisMasterSlave r = RedisMasterSlave.getInstance();
//		for (int i = 0; i < 300; i++)
//			System.out.println(r.getStringKey("key" + i));
//	}
}
