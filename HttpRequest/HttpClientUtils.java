package yan.study.httputils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.client.methods.HttpPut;

public class HttpClientUtils {
	public static void sendGet(String url, String param) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(url + "?" + param);
		client.executeMethod(method);
		InputStream stream = method.getResponseBodyAsStream();

		BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
		StringBuffer buf = new StringBuffer();
		String line;
		while (null != (line = br.readLine())) {
			buf.append(line).append("\n");
		}
		System.out.println(buf.toString());
		// 释放连接
		method.releaseConnection();

	}
	
	public static void sendPost(String url,Map<String,String> paramMap)  throws Exception {
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
		for(Map.Entry<String, String> m : paramMap.entrySet()){
			method.addParameter(m.getKey(), m.getValue());
		}
		HttpMethodParams param = method.getParams();  
	    param.setContentCharset("UTF-8"); 
	    client.executeMethod(method);
	    System.out.println(method.getStatusLine());
		InputStream stream = method.getResponseBodyAsStream();

		BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
		StringBuffer buf = new StringBuffer();
		String line;
		while (null != (line = br.readLine())) {
			buf.append(line).append("\n");
		}
		System.out.println(buf.toString());
		// 释放连接
		method.releaseConnection();
		
	}
	
	public static void sendPut(String url,Map<String,String> paramMap)  throws Exception {
		HttpClient client = new HttpClient();
		PutMethod method = new PutMethod(url);
		method.addRequestHeader("data","wwww");
		HttpMethodParams param = method.getParams(); 
		for(Map.Entry<String, String> m : paramMap.entrySet()){
			//param.setParameter("data", "wwww");
		}
	    param.setContentCharset("UTF-8"); 
	    client.executeMethod(method);
	    System.out.println(method.getStatusLine());
		InputStream stream = method.getResponseBodyAsStream();

		BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
		StringBuffer buf = new StringBuffer();
		String line;
		while (null != (line = br.readLine())) {
			buf.append(line).append("\n");
		}
		System.out.println(buf.toString());
		// 释放连接
		method.releaseConnection();
		
	}
	public static void main(String[] args) {
		String url = "http://192.168.75.24:7001/basic/services/sacwService/kcb/post.action";
		String jsonData = "{\"lOG\":{\"uSER_ID\":\"sylmj\",\"uSER_NAME\":\"三元里民警\",\"dEPARTMENTCODE\":\"440111540000\"},\"kCS\":[{\"aJBH\":\"A4401115400002012040019\",\"dEPARTMENTCODE\":\"440111540000\",\"cREATOR\":\"sylmj\",\"cREATEDTIME\":\"2015-12-29 16:02:00\",\"wPYWBH\":\"W440111540000201204000085\",\"wPXZ\":\"03\",\"wPCODE\":\"A0103\",\"kCZT\":\"01\",\"cWZT\":\"19\",\"wPSL\":\"1.000000\",\"wPDW\":\"把\",\"kWBH\":\"7654321\",\"cKBH\":\"PCS3707201112260000000000632648\",\"cBMJJH\":\"sylmj\",\"cBDW\":\"440111540000\",\"wXTLB\":\"SACWXT\",\"wDXID\":\"WP00000000000000002\"},{\"aJBH\":\"A4401115400002012040018\",\"dEPARTMENTCODE\":\"440111540000\",\"cREATOR\":\"sylmj\",\"cREATEDTIME\":\"2015-12-29 16:02:00\",\"wPYWBH\":\"W440111540000201204000086\",\"wPXZ\":\"03\",\"wPCODE\":\"A0104\",\"kCZT\":\"01\",\"cWZT\":\"19\",\"wPSL\":\"1.000000\",\"wPDW\":\"把\",\"kWBH\":\"7654321\",\"cKBH\":\"PCS3707201112260000000000632648\",\"cBMJJH\":\"sylmj\",\"cBDW\":\"440111540000\",\"wXTLB\":\"SACWXT\",\"wDXID\":\"WP00000000000000003\",\"sYSTEMID\":\"PCS3707201204110000000000112963\"}]}";
		Map<String,String> map = new HashMap<String,String>();
		map.put("data", jsonData);
		try {
			sendPut(url,map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
