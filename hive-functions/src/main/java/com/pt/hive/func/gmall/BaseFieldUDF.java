package com.pt.hive.func.gmall;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.json.JSONObject;

public class BaseFieldUDF extends UDF{

	private static final String EMPTY = "";
	public String evaluate(String line, String key) {
		
		// 1 切分数据
		String[] log = line.split("\\|");
		// 2 校验
		if(log.length != 2 || StringUtils.isBlank(log[1]) ) {
			return EMPTY;
		}
		// 3解析json对象
		JSONObject json = new JSONObject(log[1].trim());
		// 4 根据传入的key获取对应的值
		if("st".equals(key)) {
			return log[0];
		}else if("et".equals(key)) {
			if(json.has("et")) {
				return json.getString("et");
			}
		}else {
			JSONObject cm = json.getJSONObject("cm");
			if(cm.has(key)) {
				return cm.getString(key);
			}
		}
		
		return EMPTY;
	}
	
	/**
     * 测试
     */
    public static void main(String[] args) {
        String line = "1583776132686|{\"cm\":{\"ln\":\"-42.8\",\"sv\":\"V2.3.9\",\"os\":\"8.1.7\",\"g\":\"X470IP70@gmail.com\",\"mid\":\"0\",\"nw\":\"4G\",\"l\":\"en\",\"vc\":\"13\",\"hw\":\"1080*1920\",\"ar\":\"MX\",\"uid\":\"0\",\"t\":\"1583758268106\",\"la\":\"-31.3\",\"md\":\"sumsung-18\",\"vn\":\"1.1.1\",\"ba\":\"Sumsung\",\"sr\":\"M\"},\"ap\":\"app\",\"et\":[{\"ett\":\"1583685512624\",\"en\":\"display\",\"kv\":{\"goodsid\":\"0\",\"action\":\"2\",\"extend1\":\"2\",\"place\":\"1\",\"category\":\"17\"}},{\"ett\":\"1583769686402\",\"en\":\"newsdetail\",\"kv\":{\"entry\":\"3\",\"goodsid\":\"1\",\"news_staytime\":\"16\",\"loading_time\":\"0\",\"action\":\"4\",\"showtype\":\"5\",\"category\":\"97\",\"type1\":\"\"}},{\"ett\":\"1583709065211\",\"en\":\"ad\",\"kv\":{\"activityId\":\"1\",\"displayMills\":\"58537\",\"entry\":\"1\",\"action\":\"3\",\"contentType\":\"0\"}},{\"ett\":\"1583693966746\",\"en\":\"active_background\",\"kv\":{\"active_source\":\"3\"}},{\"ett\":\"1583734521683\",\"en\":\"error\",\"kv\":{\"errorDetail\":\"java.lang.NullPointerException\\\\\\\\n    at cn.lift.appIn.web.AbstractBaseController.validInbound(AbstractBaseController.java:72)\\\\\\\\n at cn.lift.dfdf.web.AbstractBaseController.validInbound\",\"errorBrief\":\"at cn.lift.dfdf.web.AbstractBaseController.validInbound(AbstractBaseController.java:72)\"}},{\"ett\":\"1583755388633\",\"en\":\"praise\",\"kv\":{\"target_id\":0,\"id\":1,\"type\":3,\"add_time\":\"1583713812739\",\"userid\":4}}]}";
        String result = new BaseFieldUDF().evaluate(line, "ln");
        System.out.println(result);
    }
	
}
