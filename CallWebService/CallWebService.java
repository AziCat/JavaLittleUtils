package call;
import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.rpc.client.RPCServiceClient;

import cn.sinobest.webservice.JzptStub;
public class CallWebService {
	public static String axis2RPC() throws AxisFault {
		RPCServiceClient client = new RPCServiceClient();  
        Options options = client.getOptions();  
        String url = "http://localhost:8080/WebService/services/jzpt?wsdl";  
        EndpointReference end = new EndpointReference(url);  
        options.setTo(end);  
          
        Object[] obj = new Object[]{"tom","b"};  
         Class<?>[] classes = new Class[] { String.class ,String.class};    
        QName qname = new QName("http://webservice.sinobest.cn", "test");    
        String result = (String) client.invokeBlocking(qname, obj,classes)[0];  
        return result;
	}
	
	public static String axisFault() throws AxisFault{
		ServiceClient sc = new ServiceClient();  
        Options opts = new Options();   
        String url = "http://localhost:8080/WebService/services/jzpt?wsdl";  
        EndpointReference end = new EndpointReference(url);  
        opts.setTo(end);  
        opts.setAction("test");  
        sc.setOptions(opts);  
          
        OMFactory fac = OMAbstractFactory.getOMFactory();    
        OMNamespace omNs = fac.createOMNamespace("http://webservice.sinobest.cn", "");    
        OMElement method = fac.createOMElement("test",omNs);    
        
        OMElement a = fac.createOMElement("a",omNs);  
        a.setText("a");  
        method.addChild(a);
        
        OMElement b = fac.createOMElement("b",omNs);  
        b.setText("b");  
        method.addChild(b);
        
        
        OMElement res = sc.sendReceive(method);  
        res.getFirstElement().getText();    
        return res.getFirstElement().getText();
	}
	
	public static void main(String[] args) {
		try {
			JzptStub s = new JzptStub("http://127.0.0.1:8080/WebService/services/jzpt?wsdl");
			JzptStub.Test t = new JzptStub.Test();
			t.setB("fffb");
			t.setA("a");
			String result = s.test(t).get_return();
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
