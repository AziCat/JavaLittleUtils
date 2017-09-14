package cn.sinobest.websocket.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户信息处理类
 * @author yjh
 * @date 2017.09.08
 */
public class UserSecurity {
    private static Log logger = LogFactory.getLog(UserSecurity.class);
    //线程安全的哈希表，一个用户可以持有多个会话
    private static ConcurrentHashMap<String,List<WebSocketSession>> users = new ConcurrentHashMap<>();

    /**
     * 添加连接用户
     * @param userid 警号
     * @param session 会话实例
     * @return 是否添加成功
     */
    public static boolean addUser(String userid, WebSocketSession session){
        if(null == userid || "".equals(userid)){
            logger.error("用户id不能为空！");
            return false;
        }else{
            if(!session.isOpen()){
                logger.error("用户："+userid+"的会话已失效，请重新连接！");
                return false;
            }else{
                List<WebSocketSession> sessionList = users.get(userid);
                if(null == sessionList){
                    sessionList = new ArrayList<>();
                }
                sessionList.add(session);
                users.put(userid,sessionList);
                logger.info("用户："+userid+"连接成功，当前在线用户人数为："+users.size());
                logger.info("用户："+userid+"持有会话数："+sessionList.size());
            }
        }
        return true;
    }

    /**
     * 移除用户会话
     * @param userid 警号
     * @param session 会话
     */
    public static void removeUser(String userid, WebSocketSession session){
         if(null == userid || "".equals(userid)){
            logger.error("用户id不能为空！");
        }else{
            List<WebSocketSession> sessionList = users.get(userid);
            if(null != sessionList && sessionList.size() > 0){
                //移除
                sessionList.remove(session);
                if(0 == sessionList.size()) {
                    //如果持有会话数为0，移除用户
                    users.remove(userid);
                }else{
                    users.put(userid,sessionList);
                }
                logger.info("用户："+userid+"移除一个会话，当前在线用户人数为："+users.size());
                logger.info("用户："+userid+"持有会话数："+sessionList.size());
                try {
                    session.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 通过警号获取用户持有的会话列表
     * @param userid 警号
     * @return 会话列表
     */
    public static List<WebSocketSession> getSessionListByUserid(String userid){
        return users.get(userid);
    }
}
