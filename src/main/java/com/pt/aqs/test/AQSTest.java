package com.pt.aqs.test;

public class AQSTest {

	private static AQSLock lock = new AQSLock();
	
	private static int stock = 4;
	
	public static void main(String[] args) {
		
		
		for (int i = 0; i < 100; i++) {
			new Thread(()->{
				
				lock.lock();
				
				if(stock >0) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					int a = stock -1;
					stock = a;
					System.err.println("success 1.");
				}else {
					System.out.println("fial.");
				}
				
				lock.unlock();
				
			}).start();
		} 
		
	}
}
