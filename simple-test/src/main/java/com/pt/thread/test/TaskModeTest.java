package com.pt.thread.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskModeTest {

	public static AtomicInteger ai = new AtomicInteger();
	
	public static void main(String[] args) {
		
		int threadNum = Runtime.getRuntime().availableProcessors()*2 ;
		System.out.println(threadNum);
		
//		ExecutorService pool = Executors.newScheduledThreadPool(threadNum);
		ExecutorService pool = initPool(threadNum, threadNum) ;
		
//		for(int m=0;m<10;m++) {
//			pool = initPool(threadNum,threadNum*2);
//		}
		
		ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(200000);
		new Thread(()->{
			int n = 0;
			while(true) {
				queue.offer(n++);
			}
		}).start();
		
		new Thread(()->{
			
			try {
				Thread.sleep(5000);
				System.exit(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}).start();
		
		for(int i=0 ; i<threadNum ;i++) {
			final int ii = i;
			pool.execute(()->{
				while (true) {
					//
					try {
						int x = queue.take();
						StringBuffer sb = new StringBuffer();
						for(int k=0; k<100000; k++) {
							sb.append(" hello world!! ").append("\n");
						}
						
//						int y = ai.incrementAndGet();
						System.out.println("runx: "+x);
//						System.out.println("run: "+y);
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			});
//			System.out.println("executed: "+i); 
		}
		
	}
	
	private static ThreadPoolExecutor initPool(int corePoolSize, int maxPoolSize) {
		return new ThreadPoolExecutor(corePoolSize, maxPoolSize, 6,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(Integer.MAX_VALUE), (r) -> {
                    Thread thread = new Thread(r);
                    thread.setName("test_thread");
                    return thread;
                });
	}
	
}
