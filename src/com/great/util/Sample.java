package com.great.util;

import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.aip.ocr.AipOcr;

public class Sample {
	public static final String APP_ID = "15222808";
    public static final String API_KEY = "mkGQhpf2hctBnO5u0jXcucWr";
    public static final String SECRET_KEY = "9p5eT5kTjHWnFFtEpBN0Q4dgLYavPTHS";
    
    public static JSONObject getCarId(byte[] bytes) {
        // �����ѡ�������ýӿ�
    	AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("multi_detect", "false");
        
        // ����Ϊ����·��
//        String image = "C:/Users/Administrator/Desktop/test.jpg";
//        JSONObject res = client.plateLicense(image, options);
//        String name = res.getJSONObject("words_result").getString("number");
//        System.out.println("����"+name);

//        System.out.println(res.get("words_result"));

        // ����Ϊ����������
//        byte[] file = readFile("test.jpg");
        JSONObject res = client.plateLicense(bytes, options);
//        System.out.println(res.toString(2));
        return res;
    }
}
