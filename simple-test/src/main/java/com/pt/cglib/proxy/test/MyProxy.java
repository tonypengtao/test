package com.pt.cglib.proxy.test;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class MyProxy implements MethodInterceptor{

	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		System.out.println("这里可以插入执行关键代码之前的逻辑");
        Object o1 = proxy.invokeSuper(obj, args);//关键代码:
        System.out.println("这里可以插入执行关键代码之后的逻辑");
		return o1;
	}

}
