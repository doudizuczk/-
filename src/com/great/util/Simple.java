package com.great.util;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.aip.ocr.AipOcr;

public class Simple {
	public static final String APP_ID = "15220247";
	public static final String API_KEY = "nwDGwPU94m6aoufWZlqIqnlH";
	public static final String SECRET_KEY = "O7zea5NDRp0DXbu1I8f2kysOefCG5Bnv";

	/*
	 * public Map Simple(String ){ Map<String,Object> map = new HashMap<>();
	 * return map; }
	 */
	public static JSONObject getMap(byte[] bytes){
		// 初始化一个AipOcr
				AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

				HashMap<String, String> options = new HashMap<String, String>();

				options.put("detect_direction", "true");
				/**
				 * 是否开启身份证风险类型(身份证复印件、临时身份证、身份证翻拍、修改过的身份证)功能， 默认不开启，即：false。
				 * 可选值:true-开启；false-不开启
				 */
				options.put("detect_risk", "false");
				/**
				 * front：身份证含照片的一面 back：身份证带国徽的一面 必须正确声明，否则"error_msg": "recognize id
				 * card error"
				 */
				String idCardSide = "front";
				// 本地图片
//				String path = "E:/KuGou/9.jpg";
				// idcard 表示读取图片的类型是身份证
				JSONObject res = client.idcard(bytes, idCardSide, options);
				return  res;
	}
}
