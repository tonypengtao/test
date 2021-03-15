package com.pt.okhttp.test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpTest {

	
	public static void main(String[] args) {
		
		OkHttpClient client = new okhttp3.OkHttpClient.Builder()
				.connectTimeout(10, TimeUnit.SECONDS)
				.readTimeout(10, TimeUnit.SECONDS)
				.writeTimeout(10, TimeUnit.SECONDS)
				.retryOnConnectionFailure(true)
				.connectionPool(new ConnectionPool(10, 5L, TimeUnit.MINUTES))
				.build();
		long start = System.currentTimeMillis();
		
		for(int i=0;i<10;i++) {
			
		
			Request r = new Request.Builder().url("https://iplatform-sit.genzon.com.cn/message/service/pangu/health/check").addHeader("content-type", "application/json;charset=UTF-8").build();
			
			try {
				Response resp = client.newCall(r).execute();
	//			System.out.println(resp.code());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
		long end = System.currentTimeMillis();
		System.out.println("total: " + (end - start));
		
		System.exit(0);
	}
	
	
}
