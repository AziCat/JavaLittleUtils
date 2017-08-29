package com.tydic.service.demo.util;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClintUtil {


	/**
	 * @param url
	 * @param param
	 * @return
	 * @throws IOException
	 * 返回格式
	 		{
    			"code": "999999",
    			"time": 1496725107747,
    			"data": {
        		"total": 4449848,
        		"data": [
            		{
                		"hh": "556106165",
                		"hxzmc2": "非农业人口"
            		}
        			]
    		},
    			"success": true
			}
	 */
	public static String sendServiceCallRequest(String url,ServiceCallParam param) throws IOException{
		HttpURLConnection connection = HttpClintUtil.getConnection(url);
//		DataOutputStream dops = new DataOutputStream(connection.getOutputStream());
		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(),"utf-8");
		try {
			String content = JSONObject.toJSONString(param);
			System.out.println("参数："+content);
			out.write(content);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prinResponseData(connection);
	}

	private static HttpURLConnection getConnection(String urlStr){
		URL url;
		HttpURLConnection urlConn = null;
		try {
			url = new URL(urlStr);
			urlConn = (HttpURLConnection)url.openConnection();
			urlConn.setRequestProperty("Accept-Charset", "UTF-8");
            urlConn.setRequestProperty("contentType", "UTF-8");
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			urlConn.setRequestMethod("POST");
			urlConn.setUseCaches(false);
			urlConn.setInstanceFollowRedirects(true);
			urlConn.setRequestProperty("Content-Type", "application/json");
			urlConn.connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return urlConn;
	}
	private static String prinResponseData(HttpURLConnection urlConn) throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(),"utf-8"));
		String inputLine = null;
		String resultData = "";
		while((inputLine = reader.readLine())!=null){
			resultData += inputLine +"\n";
		}
		reader.close();
		urlConn.disconnect();
		String formatJson = JsonFormatTool.formatJson(resultData);
		return formatJson;
	}
}
