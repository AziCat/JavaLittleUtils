package test;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

public class HttpPostXmlUtil {
	@SuppressWarnings("deprecation")
	public static String HttpPostXml(String url, String xmlBody){
	    String result = "";
	    HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
	    client.getHttpConnectionManager().getParams().setConnectionTimeout(15000); //ͨ��������������������ӵĳ�ʱʱ��
	    client.getHttpConnectionManager().getParams().setSoTimeout(60000); //Socket�����ݵĳ�ʱʱ�䣬���ӷ�������ȡ��Ӧ������Ҫ�ȴ���ʱ��
	    PostMethod method = new PostMethod(url);
	    method.setRequestHeader("Content-Type", "application/xml;charset=UTF-8");
	    if(null != xmlBody){
	        method.setRequestBody(xmlBody);
	    }
	    try {
	        client.executeMethod(method);
	        result = method.getResponseBodyAsString();
	    } catch (Exception e) {
	        result = "error";
	       e.printStackTrace();
	    } finally {
	        if (null != method)
	            method.releaseConnection();
	    }
	    return result;
	}
	
	public static void main(String[] args) {
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?><data ajbh=\"b8b60cb88fc5f942b2ddbf6832faf4b5\" wsid=\"9\" returntime=\"2014-06-16 09:26:26\"><records name=\"sacw\" description=\"�永����Ʒ��Ϣ\" count=\"1\"><record ord=\"1\"><parameter name=\"kwid\" description=\"�永����Ψһ����\">1009988990988</parameter><parameter name=\"sakwmc\" description=\"�永��������\">�ֻ�</parameter><parameter name=\"sakwlb\" description=\"�永�������\">��Ʒ</parameter><parameter name=\"tz\" description=\"����\">��ɫ��Ϊ�ֻ�</parameter><parameter name=\"dw\" description=\"��λ\">��</parameter><parameter name=\"sl\" description=\"����\">1</parameter><parameter name=\"jz\" description=\"��ֵ\">2000</parameter><parameter name=\"sl_kc\" description=\"�������\">1</parameter><parameter name=\"jz_kc\" description=\"����ֵ\">2000</parameter><parameter name=\"bz\" description=\"����\">�����</parameter><parameter name=\"wz\" description=\"λ��\">������������Ժ��֤����1����</parameter><parameter name=\"zt\" description=\"״̬\">1504000000003</parameter><parameter name=\"zp\" description=\"��Ƭ\"></parameter><parameter name=\"zb\" description=\"��ע\"></parameter><parameter name=\"jz_dw\" description=\"��ֵ��λ\">��Ԫ</parameter><parameter name=\"sakwly\" description=\"�永������Դ\">1502000000003</parameter><parameter name=\"cslb\" description=\"��ʩ���,��ѡ����Ѻ����⣬���ᣬ���գ�\">1503000000004</parameter><parameter name=\"cqcsrq\" description=\"��ȡ��ʩ����\">2016-11-10</parameter><parameter name=\"cfwz\" description=\"���λ��\">xx��xx��</parameter><parameter name=\"gaclyj\" description=\"�����������\">�˻��ܺ���</parameter><parameter name=\"cbrclyj\" description=\"�а��˴������\">�˻��ܺ���</parameter></record></records><attachments><attachment name=\"sacwclyjb\" filename=\"�永�����������.doc\">R0lGODlheAB4APcAABESEhESEhESEhESEhISEhUUFBgVFhkXGBsZGh0bHR4eHyAgISEiIyQkJScnKCoqKysrLCsrLCsrLCsrLCsrLCssLCssLSwtLSwtLi0vMC0xMS4yNDA1NzE2OTI3OjQ3OzY3Ozg4Ojk4Ojo4Ojw4OUI3OEU2N0Y2N0Y2N0c3OEg5Okk6PEo8Pko9P0tAQUxCQ01ERU1GR01ISU1JSk1LS09NTU9OTU9OTU9PTk5QTk5RT01ST01UUE1WUVBWU1RXVldYWFlaWVxcW2BeXGJgXWNgXmRhYGViYGdkY2lmZmxpaW1ram9ta3FvbXJybnJ1bnB6bm6AbGuHa2eOaWaOaWSPaGGPZl+PZVyMY1uLY1mKYliGZFaCZlN8alJ5bU50cklueUVqfkFlhD1iiTpfjThdkTZblDValzVamTVamTVbmjVbmzZcnjZdnzdeoTdfojlhpzpjqjpkqjtkqj1mqkBoqUFpqUNqqERqp0VrpkdspUlupUxvpFBypFN0pVt6pmN/pmmDpmuEpm2FpW6GpHKFn3aFl3mEkn2DjoCCioOBiISCiIaDiYiEiomFi4qHjYqJjoyLkI2Oko6QlI6SlY6UmI2WmYicm4OinH+lnn2nnnypnXurm3utmXqvl3qwl3qvl3uul3utl32tmX+tmoGtm4Gsm4GsnIGtnIGsnIGtnIKtnIKtnYOun4WvoYWvooawo4ewpIewpIiwpYmwp4qvqYqvq4yur46ttZCtuJOvupWzu5e3upm6uJu9uJ2/uKDCuKDDuaHDuaLEuqPFu6XGvafIv6nJwavKw6zLxK7Mxq/Nx7DNx7HNyLTOy7fOzbvP0MDQ1MTQ1sbR2MfR2cjQ2sfP2cXN2MHJ1bvD0re/z7O6zbC3yq61ya61ya61ya61yK60xK+zwq+yvrCxurCwtbGvsbOuqbasn7qrkr6pgsWobcmnXs2mUdGmR9KmQ9SmP9SnP9SoQNWuQta2Rti9SdnDS9rJTNrMTdrNUNvPVNvPV9vQW9zRX97TZeDWcCH/C05FVFNDQVBFMi4wAwEAAAAh+QQAAAAAACwAAAAAeAB4AAAI/gAFCBxIUGCBBRIkOEhQsKHDhw8NOEg40UGDhBgVMmjgoONEjA4YMPiYsaTJkyhPQhxoYEHIBStjyhRQAIEDCxYuhuwoAacFj0AV9rS40afRo0iTKkU604CBmVAhbryZM6RIj0tD9vxZdKnXr0odOoAZdSBQjwWgSjTKcSQDjVnf4gzZAKzduw7JFixQwEDah0YxjHDwd6UBBXWNilzsNnFSiw0YJ7xLWakCBDINcLTAIEFhmgUU4JTAYIHTmAgSWMBw1IGCwggUdPVq9Wrl20YtPjVMFWeD1079pk6AGSoDCzBiuFg+ImfDmo4fd2TcG3flBocVdFTwsEB1hdM//</attachment></attachments></data>";
		String url = "http://localhost:8080/WebService/TestServlet.do";
		System.out.println(HttpPostXml(url, xmlStr));
	}
}
