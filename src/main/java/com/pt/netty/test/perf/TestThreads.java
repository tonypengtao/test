package com.pt.netty.test.perf;

import java.util.concurrent.CountDownLatch;

public class TestThreads {

	public static void main(String[] args) {

		for (int i = 0;; i++) {
			System.out.println("i = " + i);
			new Thread(new HoldThread()).start();
		}
	}

}
class HoldThread extends Thread {
    CountDownLatch cdl = new CountDownLatch(1);

    public HoldThread() {
        this.setDaemon(true);
    }

    public void run() {
        try {
//            cdl.await();
        	Thread.sleep(5000);
        } catch (InterruptedException e) {
        	e.printStackTrace();
        }
    }
}