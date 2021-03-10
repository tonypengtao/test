package com.pt.json.test;

import java.io.StringWriter;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonTest {

	public static void main(String[] args) throws JSONException {
		
		String str = "{\r\n" + 
				"\"name\":\"tom\",\r\n" + 
				"\"age\":13,\r\n" + 
				"\"addr\": {\r\n" + 
				"  \"cn\":\"zh\",\r\n" + 
				"  \"st\":\"guangdong\",\r\n" + 
				"  \"ci\":\"shenzhon\"\r\n" + 
				"},\r\n" + 
				"\"course\":[\"english\",\"math\",\"chinese\"]\r\n" + 
				"}";
		
		com.alibaba.fastjson.JSONObject jo = com.alibaba.fastjson.JSONObject.parseObject(str);
//		JSONObject jo = new JSONObject(str);
		
		System.out.println(jo);
		
		String content = " {\"value\": $jo.course }";
		
		VelocityEngine ve = new VelocityEngine();
        ve.init();
        VelocityContext ctx = new VelocityContext();
        ctx.put("jo", jo);
        
        StringWriter writer = new StringWriter();
        ve.evaluate(ctx, writer, "", content);
        
        System.out.println(writer.toString());

		
	}
}
