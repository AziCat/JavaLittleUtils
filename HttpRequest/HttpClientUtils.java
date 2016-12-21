package yan.study.httputils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

public class HttpClientUtils {
	public static void sendGet(String url,String param) throws HttpException, IOException{
		 HttpClient client = new HttpClient();  
		 HttpMethod method = new GetMethod(url+"?"+param); 
		 client.executeMethod(method);
		 InputStream stream = method.getResponseBodyAsStream();  
	       
	     BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));  
	     StringBuffer buf = new StringBuffer();  
	     String line;  
	     while (null != (line = br.readLine())) {  
	         buf.append(line).append("\n");  
	     }  
	     System.out.println(buf.toString());  
	       //释放连接  
	     method.releaseConnection();  
	    
   }
}
