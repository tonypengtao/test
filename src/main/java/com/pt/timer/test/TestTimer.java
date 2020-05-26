package com.pt.timer.test;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class TestTimer {

	
	public static void main(String[] args) {
		
		Timer timer = new Timer("server registry heartbeat thread");
		System.out.println("now: "+System.currentTimeMillis());
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				String name = Thread.currentThread().getName();
				System.out.println(name+" now: "+System.currentTimeMillis());
			}
		}, 3000, 2000);
		
		java.util.List<String> a = null;
		
//		a.parallelStream()().flatMap(mapper)
		
	}
	
	
}
