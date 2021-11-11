package com.pt.cglib.test;

import java.lang.reflect.Method;

import org.objectweb.asm.Type;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.ImmutableBean;
import net.sf.cglib.core.Converter;
import net.sf.cglib.core.Signature;
import net.sf.cglib.proxy.InterfaceMaker;

public class CglibTest {

	public static void testBeanGenerator() throws Exception {
		BeanGenerator gen = new BeanGenerator();
		gen.addProperty("valu", String.class);
		gen.setSuperclass(SampleBean.class);
		Object userBean = gen.create();
		Method setValue = userBean.getClass().getMethod("setValue", String.class);
		setValue.invoke(userBean, "tom");
		
		Method getValue = userBean.getClass().getMethod("getValue");
		System.out.println(getValue.invoke(userBean));
		SampleBean sample = (SampleBean)userBean;
		System.out.println(sample.getValue());
		
	}
	
	public static void testImmutableBean() throws Exception {
		SampleBean bean = new SampleBean();
		bean.setValue("tt");
		SampleBean bean2 = (SampleBean)ImmutableBean.create(bean);
		System.out.println(bean2.getValue());
		bean2.setValue("haha");
	}
	
	public static void testBeanCopier() throws Exception {
		BeanCopier copier = BeanCopier.create(SampleBean.class, OtherSampleBean.class, true);
		SampleBean sampleBean = new SampleBean();
		sampleBean.setValue("hello world!");
		OtherSampleBean otherBean = new OtherSampleBean();
		copier.copy(sampleBean, otherBean, new Converter() {
			
			@Override
			public Object convert(Object value, Class target, Object context) {
				
				return value.toString()+"123";
			}
		});
		System.out.println(otherBean.getValue());
	}
	
	public static void testInterfaceMarker() throws Exception {
		Signature sign = new Signature("hello", Type.BOOLEAN_TYPE, new Type[]{Type.INT_TYPE});
		
		InterfaceMaker interfaceMaker = new InterfaceMaker();
		interfaceMaker.add(sign, new Type[0]);
		Class iface = interfaceMaker.create();
		System.out.println(iface.getMethods().length);
		System.out.println(iface.getMethods()[0].getName());
		System.out.println(iface.getMethods()[0].getReturnType());
	}
	
	public static void main(String[] args) throws Exception {
//		testBeanGenerator();
//		testImmutableBean();
//		testBeanCopier();
		testInterfaceMarker();
	}
}
