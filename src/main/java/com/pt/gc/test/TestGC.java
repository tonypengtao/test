package com.pt.gc.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class TestGC {

	
	public static void main(String[] args) throws Exception {
		List<Object> list = new ArrayList<Object>();
		
		while (true) {
			int sleep = new Random().nextInt(100);
			if (sleep % 2 == 0) {
				list.clear();
			}else {
				for(int i=0; i<10000; i++) {
					Properties p = new Properties();
					p.put("test_key", "test_value");
					list.add(p);
				}
			}
			
			
			Thread.sleep(sleep);
		}
	}
}
