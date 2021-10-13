package com.pt.hive.func.gmall;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.json.JSONArray;

public class EventJsonUDTF extends GenericUDTF {

	
	@Override
	public StructObjectInspector initialize(StructObjectInspector argOIs) throws UDFArgumentException {
		// 定义UDTF返回值类型和名称
		List<String> fieldName = new ArrayList<String>();
		List<ObjectInspector> fieldType = new ArrayList<ObjectInspector>();
		
		fieldName.add("event_name");
		fieldName.add("event_json");
		fieldType.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
		fieldType.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
		
		return ObjectInspectorFactory.getStandardStructObjectInspector(fieldName, fieldType);
	}
	
	@Override
	public void process(Object[] args) throws HiveException {
		
		// 1 获取传入的数据，传入的是json array => udf 传入 et
		String input = args[0].toString();
		// 2 校验
		if(StringUtils.isBlank(input)) {
			return;
		}else {
			JSONArray ja = new JSONArray(input);
			if(ja.length() == 0) {
				return;
			}
			for(int i=0; i< ja.length(); i++) {
				String[] result = new String[2];
				result[0] = ja.getJSONObject(i).getString("en");
				result[1] = ja.getString(i);
				
				forward(result);
			}
			
		}
		
	}

	@Override
	public void close() throws HiveException {
		// TODO Auto-generated method stub
		
	}

}
