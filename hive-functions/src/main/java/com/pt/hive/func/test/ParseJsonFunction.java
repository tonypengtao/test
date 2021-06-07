package com.pt.hive.func.test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.hadoop.hive.ql.exec.UDF;

import com.google.gson.Gson;

/**
 * parse json string
 * @author pengtao
 *
 */
public class ParseJsonFunction extends UDF {

	Gson gson = new Gson();
	
	public String evaluate(String a) {
		if(a == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		Map m = gson.fromJson(a, Map.class);
		m.values().stream().forEach((i)->sb.append(i).append(","));
		return sb.deleteCharAt(sb.length()-1).toString();
	}
	
	
//	public static void main(String[] args) {
//		Map<String, String> m = new HashMap<String, String>();
//		m.put("a", "a");
//		m.put("b", "b");
//		
//		StringBuilder sb = new StringBuilder();
//		m.values().stream().forEach((i)->sb.append(i).append(","));
//		
//		System.out.println(sb.deleteCharAt(sb.length()-1).toString());
//		
//	}
}
