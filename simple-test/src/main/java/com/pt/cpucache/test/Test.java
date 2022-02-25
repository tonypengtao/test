package com.pt.cpucache.test;

import java.util.concurrent.CountDownLatch;

public class Test {
	
	public static long COUNT = 1000000000L;
	
	private static class T {
		private long p1,p2,p3,p4,p5,p6,p7;
		private long x = 0L;
		private long p9,p10,p11,p12,p13,p14,p15;
	}
	
	public static T[] arr = new T[2];
	
	static {
		arr[0] = new T();
		arr[1] = new T();
	}
	
	public static void main(String[] args) throws Exception {
		CountDownLatch latch = new CountDownLatch(2);
		
		Thread t1 = new Thread(()->{
			for(long i=0; i<COUNT; i++) {
				arr[0].x = i;
			}
			latch.countDown();
		});
		
		Thread t2 = new Thread(()->{
			for(long i=0; i<COUNT; i++) {
				arr[1].x = i;
			}
			latch.countDown();
		});
		
		final long start = System.nanoTime();
		t1.start();
		t2.start();
		latch.await();
		
		
		System.out.println((System.nanoTime() - start) / 1000000);
	}

}
