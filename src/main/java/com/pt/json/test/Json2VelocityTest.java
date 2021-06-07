package com.pt.json.test;

import java.io.StringWriter;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.json.JSONException;
import org.json.JSONObject;

public class Json2VelocityTest {

	public static void main(String[] args) throws JSONException {
		
		String str = "{\r\n" + 
				"\"name\":\"tom\",\r\n" + 
				"\"age\":13,\r\n" + 
				"\"addr\": {\r\n" + 
				"  \"cn\":\"zh\",\r\n" + 
				"  \"st\":\"guangdong\",\r\n" + 
				"  \"ci\":\"shenzhon\"\r\n" + 
				"},\r\n" + 
				"\"course\":[\"123\",\"234\",\"345\"]\r\n" + 
				"}";
		
//		com.alibaba.fastjson.JSONObject jo = com.alibaba.fastjson.JSONObject.parseObject(str);
		JSONObject jo = new JSONObject(str);
		
		System.out.println(jo);
		
		long start = System.currentTimeMillis();
		
		VelocityEngine ve = new VelocityEngine();
		
//		ve.setProperty("velocimacro.library.autoreload", "false");
//		ve.setProperty("file.resource.loader.modificationCheckInterval", "0");
		
		ve.init();

		for(int i=0;i<1000;i++) {
			String content = " {\"value"+i+":\": ${jo.course} }";
	        VelocityContext ctx = new VelocityContext();
	        ctx.put("jo", jo);
	        
	        StringWriter writer = new StringWriter();
	        
//	        ve.evaluate(ctx, writer, "", content);
	        Velocity.evaluate(ctx, writer, "", content);
	        
	        String result = writer.toString();
		}
		
        long end = System.currentTimeMillis();
        
//        System.out.println(result);
        System.out.println("total: " + (end-start));
        

		
	}
}
