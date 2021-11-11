package com.pt.cglib.proxy.test;

import net.sf.cglib.proxy.Enhancer;

public class Demo {

	public static void main(String[] args) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(User.class);
		enhancer.setCallback(new MyProxy());
		User u = (User)enhancer.create();
		String word = u.say("world");
		System.out.println(word);
	}
}
