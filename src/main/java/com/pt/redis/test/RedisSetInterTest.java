package com.pt.redis.test;

import java.util.Set;

import redis.clients.jedis.Jedis;

public class RedisSetInterTest {

	private static String SERVER_USERID_KEY = "tim:userset";
	
	private static String GROUP_USERID_KEY = "tim:group:userset:a";
	
	public static void initServerUser(Jedis jedis) {
		for (int i = 0; i < 20000; i++) {
			String uid = "testid"+i;
			jedis.sadd(SERVER_USERID_KEY, uid);
			
		}
	}
	
	public static void initGroupUser(Jedis jedis) {
		for (int i = 0; i < 20000; i++) {
			if(i%8 == 0) {
				String uid = "testid"+i;
				jedis.sadd(GROUP_USERID_KEY, uid);
				jedis.sadd(GROUP_USERID_KEY, uid+"n");
			}
			
		}
	}
	
	public static void setInter(Jedis jedis) {
		for (int i = 0; i < 100; i++) {
			Set<String> result = jedis.sinter(GROUP_USERID_KEY, SERVER_USERID_KEY);
			
//			System.out.println("result.size"+result.size());
		}
	} 
	
	
	
	public static void main(String[] args) {
		Jedis jedis = new Jedis("192.168.0.143");
		jedis.auth("123456");
		
		long start = System.currentTimeMillis();
		
//		initServerUser(jedis);
		
//		initGroupUser(jedis);
		
		setInter(jedis);
		
		
		long end = System.currentTimeMillis();
		
		System.out.println("total time: "+(end - start));
		
		jedis.close();
	}
}
