package com.pt.jvm.test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OOMTest {

	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		
		for(int k=0;k<5;k++) {
			new Thread(()->{
				
				for(int i=0;i<1000000;i++) {
					String str = "";
					str += "pt-start ";
					str += UUID.randomUUID().toString();
					str += " pt-end";
					list.add(str);
					
				}
				
			},"test-thread-"+k).start();
		}
		
		
		
		System.out.println("ok");
		
	}
	
	
}
