package com.pt.q.test;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueTest {

	static ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<Integer>(10000);
	
	public static void main(String[] args) {
		
//		LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<Integer>(10000);
		
		
		for (int i = 0; i < 10; i++) {
			new Thread(()->{
				
				for(int k=0;;k++) {
					try {
						Integer n = q.take();
						StringBuffer sb = new StringBuffer();
						for(int m=0; m<10000; m++) {
							sb.append(" hello world!! ").append("\n");
						}
						System.out.println(n);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			},"consumer-"+i).start();
			
		}
		
		for (int i = 0; i < 1; i++) {
			new Thread(()->{
				
				for(int k=0;;k++) {
					boolean r = q.offer(k);
					if(!r) {
						System.err.println("full...");
						return;
					}
					
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			},"produce-"+i).start();
			
		}
		
	}
}
