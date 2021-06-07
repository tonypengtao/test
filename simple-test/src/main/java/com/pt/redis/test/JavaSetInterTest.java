package com.pt.redis.test;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class JavaSetInterTest {
	
	private static String GROUP_USERID_KEY = "tim:group:userset:a";

	public static Set<String> initServerUser() {
		Set<String> re = new HashSet<String>(20000);
		for (int i = 0; i < 20000; i++) {
			String uid = "testid"+i;
			re.add(uid);
			
		}
		
		return re;
	}
	
	public static Set<String> initGroupUser() {
		Set<String> re = new HashSet<String>(10000);
		for (int i = 0; i < 20000; i++) {
			if(i%2 == 0) {
				continue;
			}
			String uid = "testid"+i;
			re.add(uid);
			re.add(uid+"n");
		}
		
		return re;
	}
	
	public static void setInterMemo(Set<String> a, Set<String> b) {
		for (int i = 0; i < 100; i++) {
			b.retainAll(a);
			System.out.println("result.size"+b.size());
		}
	}
	
	public static void setInterMix(Set<String> a, Jedis jedis) {
		for (int i = 0; i < 100; i++) {
			Set<String> groupUsers = jedis.smembers(GROUP_USERID_KEY);
			
			groupUsers.retainAll(a);
		}
	}
	
	
	
	
	public static void main(String[] args) {

		Jedis jedis = new Jedis("192.168.0.143");
		jedis.auth("123456");
		
		Set<String> serverUsers = initServerUser();
		
		Set<String> groupUsers = initGroupUser();
				
		long start = System.currentTimeMillis();
		
		setInterMemo(serverUsers, groupUsers);
		
//		setInterMix(serverUsers, jedis);
		
		long end = System.currentTimeMillis();
		
		System.out.println("total time: "+(end - start));
		
		jedis.close();
	}
}
