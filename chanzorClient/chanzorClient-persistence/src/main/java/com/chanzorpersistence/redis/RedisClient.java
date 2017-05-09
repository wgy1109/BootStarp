package com.chanzorpersistence.redis;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class RedisClient {
	public static void main(String[] args) {
		RedisUtil  redis =new RedisUtil();  
		 Set<String> keys = redis.keys("*"); 
	        Iterator<String> it=keys.iterator() ;   
	        while(it.hasNext()){   
	            String key = it.next();   
	            System.out.println(key);   
	        }
//	        System.out.println(redis.get("cesgu"));
	        System.out.println(String.format("keys: %s", redis.hkeys("SMS.USER-989699") ));
	        System.out.println(String.format("values: %s", redis.hvals("SMS.USER-989699") ));
//	        Map<String,String> spinfo=new HashMap<String,String>();
//		    spinfo.put("signature", "456");


	}
	//
}