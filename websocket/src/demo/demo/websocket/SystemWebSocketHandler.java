package demo.websocket;

import java.net.URLDecoder;
import java.util.Enumeration;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;
/**
 * 
 * @author yanjunhao
 *
 */
public class SystemWebSocketHandler implements WebSocketHandler {
	private static final String LOGOUTTYPE = "LOGOUTTYPE";
	private static final String ISLOGIN = "ISLOGIN";
	private static final String USERNAME = "USERNAME";
	private static Hashtable users;
	static{
		users=new Hashtable();
	}
	/**
	 * 关闭连接后
	 * (non-Javadoc)
	 * @see org.springframework.web.socket.WebSocketHandler#afterConnectionClosed(org.springframework.web.socket.WebSocketSession, org.springframework.web.socket.CloseStatus)
	 */
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		//获取登出类型
		String logoutType = (String) session.getHandshakeAttributes().get(LOGOUTTYPE);
		if(ISLOGIN.equals(logoutType)){
			
		}else{
			String userName = getUserName(session);
			System.out.println(userName+"关闭连接");
			users.remove(userName);
		}
	}
	/**
	 * 连接已建立
	 * @see org.springframework.web.socket.WebSocketHandler#afterConnectionEstablished(org.springframework.web.socket.WebSocketSession)
	 */
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String userName = getUserName(session);
		if(null!=userName){
			//判断用户是否已经存在
			WebSocketSession sessionLogin = (WebSocketSession) users.get(userName);
			if(null!=sessionLogin){
				System.out.println(userName+"已登录");
				session.sendMessage(new TextMessage("LOGIN:"+userName+"已登录"));
				session.getHandshakeAttributes().put(LOGOUTTYPE, ISLOGIN);
				//关闭连接
				session.close();
			}else{
				session.getHandshakeAttributes().put(USERNAME, userName);
				users.put(userName, session);
				System.out.println(userName+"连接成功");
				session.sendMessage(new TextMessage("LOGIN:"+userName+"连接成功"));
			}
		}else{
			System.out.println("用户名为空，断开连接");
			session.sendMessage(new TextMessage("LOGIN:"+"用户名为空，断开连接"));
			//关闭连接
			session.close();
		}

	}
	/**
	 * 获取用户名
	 * @param session
	 * @return
	 */
	private String getUserName(WebSocketSession session) throws Exception{
		String queryUrl =
	    URLDecoder.decode(URLDecoder.decode(session.getUri().getQuery(),"utf-8"),"utf-8");
		//分隔参数
		String[] arr = queryUrl.split("&");
		if(arr.length>0){
			String[] userNameArr = arr[0].split("=");
			if(userNameArr.length==2&&"userName".equals(userNameArr[0])){
				return userNameArr[1];
			}
		}
		return null;
	}
	/**
	 * 发送消息
	 * (non-Javadoc)
	 * @see org.springframework.web.socket.WebSocketHandler#handleMessage(org.springframework.web.socket.WebSocketSession, org.springframework.web.socket.WebSocketMessage)
	 */
	@SuppressWarnings("rawtypes")
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> msg) throws Exception {
		String username = (String) session.getHandshakeAttributes().get(USERNAME);
		Enumeration  e = users.elements();
		while (e.hasMoreElements()) {
			WebSocketSession s = (WebSocketSession) e.nextElement();
			s.sendMessage(new TextMessage("MSG:"+username+":"+msg.getPayload()));
			
		}

	}

	public void handleTransportError(WebSocketSession arg0, Throwable arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}

}
