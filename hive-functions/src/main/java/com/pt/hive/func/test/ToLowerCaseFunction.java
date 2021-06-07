package com.pt.hive.func.test;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

/**
 *  tolowerCase函数类，继承UDF类
 * @author pengtao
 *
 */
public class ToLowerCaseFunction extends UDF{

	//实现指定格式方法
	public Text evaluate(Text a) {
		if(a == null) {
			return null;
		}
		//返回结果
		return new Text(a.toString().toLowerCase());
	}
}
