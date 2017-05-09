package com.chanzor.util;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.log4j.Logger;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * redis  集群操作类
 * @author Administrator
 *	
 */


public class RedisClusterClient {

	
	private static Logger log = Logger.getLogger(RedisClusterClient.class);
	
	private static RedisClusterClient instance ;
	
	private JedisCluster jedisCluster;
	
	public static RedisClusterClient getInstance () {
		if(instance == null ){
			instance = new RedisClusterClient();
		}
		return instance;
	}
	
	public RedisClusterClient (){
		init();
	}
	
	private Set<HostAndPort> parseHostAndPorts () {
		Set<HostAndPort> sets = new HashSet<HostAndPort>();
//		HostAndPort hp1 = new HostAndPort("123.57.45.220", 6380);
//		HostAndPort hp2 = new HostAndPort("123.57.45.220", 6381);
//		HostAndPort hp3 = new HostAndPort("123.57.45.220", 6382);
//		HostAndPort hp4 = new HostAndPort("123.57.45.220", 6390);
//		HostAndPort hp5 = new HostAndPort("123.57.45.220", 6391);
//		HostAndPort hp6 = new HostAndPort("123.57.45.220", 6392);
//		sets.add(hp1);
//		sets.add(hp2);
//		sets.add(hp3);
//		sets.add(hp4);
//		sets.add(hp5);
//		sets.add(hp6);
		return sets;
	}
	
	public void init () {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxIdle(100);
		config.setMaxTotal(1000);
		config.setMaxWaitMillis(1000l);
		jedisCluster = new JedisCluster(parseHostAndPorts(), config);
	}
	public String getKey (String key){
		String value = jedisCluster.get(key);
		closeJedis();
		return value;
	}
	public void setKey (String key ,String value){
		jedisCluster.set(key, value);
		closeJedis();
	}
	public String getAllKeys (String key , String field) {
		String str = jedisCluster.hget(key, field);
		Map<String, String> s =  jedisCluster.hgetAll(key);
		System.out.println(s);
		closeJedis();
		return str;
	}
	public void putMapkey (String key , String field , String value){
		long i = jedisCluster.hset(key, field, value);
		System.out.println(i);
		closeJedis();
	}
	public void setMap (String key , Map<String, String> hash) {
		jedisCluster.hmset(key, hash);
		closeJedis();
	}
	
	private void closeJedis (){
		if(jedisCluster != null ) jedisCluster.close();
	}
	
	
//	public static void main(String[] args) {
//		RedisClusterClient r = RedisClusterClient.getInstance();
//		String key = "ms_test";
//		String value = "ms6385";
//		r.setKey(key, value);
//		System.out.println("test11111111");
//	}
}
