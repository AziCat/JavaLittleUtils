package cn.sinobest.message.service.impl;

import cn.sinobest.base.util.CommonFunction;
import cn.sinobest.base.util.DateUtil;
import cn.sinobest.base.util.JsonUtil;
import cn.sinobest.message.domain.*;
import cn.sinobest.message.handler.IMessageHandler;
import cn.sinobest.message.handler.impl.DeleteMessageHandlerImpl;
import cn.sinobest.message.handler.impl.RefreshMessageHandlerImpl;
import cn.sinobest.message.handler.impl.UpdateMessageHandlerImpl;
import cn.sinobest.message.service.IMessageService;
import cn.sinobest.query.core.engine.jdbc.JdbcService;
import cn.sinobest.websocket.base.UserSecurity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 消息服务实现类
 *
 * @author yjh
 * @date 2017.09.11
 */
@Service("SimpleMessageService")
public class MessageServiceImpl implements IMessageService {


    private Log logger = LogFactory.getLog(MessageServiceImpl.class);

    @Override
    public Result sendMessage(Message message) throws Exception {
        Result result = new Result();
        //发送内容长度检验
        String content = message.getContent();
        if (content.length() > 1200) {
            throw new Exception("content长度超过1200！");
        }
        //时间检验
        if (CommonFunction.checkDateIsLtDbDate(message.getDeadline())) {
            throw new Exception("deadline不能小于等于系统时间！");
        }
        //生成systemid
        String systemid = CommonFunction.getSystemid();
        message.setSystemid(systemid);
        //插入b_message_center表
        if (insertMessage(message) > 0) {
            result.setSuccess("1");
            result.setMsg("发送操作成功。");
            result.setSystemid(systemid);
            logger.info(result.getMsg() + "----" + systemid);
            //发送追加消息
            sendClientIncrement(message);
        } else {
            result.setSuccess("0");
            result.setMsg("数据保存失败！请联系管理员！");
            result.setSystemid("");
            logger.error(result.getMsg());
        }

        return result;
    }

    /**
     * 发送增量消息
     *
     * @param message 消息实体
     */
    private void sendClientIncrement(Message message) {
        //获取接收人
        String userid = message.getReceiver();
        //判断接收人是否持有会话
        List<WebSocketSession> sessions = UserSecurity.getSessionListByUserid(userid);
        if (null != sessions && sessions.size() > 0) {
            logger.info("用户：" + userid + "持有会话数：" + sessions.size());
            //是-->发送消息
            //构建消息实体
            List<Message> messageList = new ArrayList<>();
            message.setMessage_type_cn(CommonFunction.code2Detail("MC_TYPE",message.getMessage_type()));
            messageList.add(message);
            SocketMessage socketMessage = new SocketMessage();
            socketMessage.setHeader(MessageType.MESSAGE);
            socketMessage.setStatus(MessageType.INCREMENT);
            socketMessage.setSuccess(MessageType.SUCCESS);
            socketMessage.setItems(messageList);
            //遍历会话列表
            String jsonStr = JsonUtil.bean2Json(socketMessage);
            sessions.forEach(session -> {
                try {
                    session.sendMessage(new TextMessage(jsonStr));
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error(e);
                }
            });
            logger.info("发送消息："+jsonStr);
        }
    }

    @Override
    public void saveLog(String type, String param, String clientIP, String result, String messageId) {
        JdbcService jdbcService = JdbcService.getInstance();
        String sql = "INSERT INTO B_MESSAGE_CENTER_LOG(SYSTEMID,MESSAGEID,TYPE,PARAM,RESULT,CREATETIME,CLIENTIP)VALUES(GETID(NULL),?,?,?,?,SYSDATE,?)";
        jdbcService.update(sql, new Object[]{messageId, type, param, result, clientIP},
                new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR});
    }

    @Override
    public void sendFullMessage(String userid, WebSocketSession webSocketSession) {
        SocketMessage socketMessage = getFullSocketMessage(userid);
        String jsonStr = JsonUtil.bean2Json(socketMessage);
        try {
            webSocketSession.sendMessage(new TextMessage(jsonStr));
            logger.info("发送消息："+jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        }
    }
    @Override
    public SocketMessage getFullSocketMessage(String userid) {
        //获取消息表中有效消息
        List<Message> messageList = getMessageListByUserid(userid);
        //发送消息
        SocketMessage socketMessage = new SocketMessage();
        socketMessage.setHeader(MessageType.MESSAGE);
        socketMessage.setStatus(MessageType.FULL);
        socketMessage.setSuccess(MessageType.SUCCESS);
        socketMessage.setItems(messageList);
        return socketMessage;
    }

    @Override
    public String handleMessage(String param) {
        IMessageHandler messageHandler;
        String header ;
        SocketMessage socketMessage = new SocketMessage();
        try {
            ClientMessage clientMessage = JsonUtil.json2BeanWithNullCheck(param,ClientMessage.class,ClientMessage.getNotNullList());
            header = clientMessage.getHeader();
            //根据header分发到不同的处理器
            switch (header){
                case MessageType.UPDATE://更新
                    messageHandler = new UpdateMessageHandlerImpl();
                    socketMessage = messageHandler.handleMessage(clientMessage);
                    break;
                case MessageType.REFRESH://刷新
                    messageHandler = new RefreshMessageHandlerImpl(this);
                    socketMessage = messageHandler.handleMessage(clientMessage);
                    break;
                case MessageType.DELETE://删除
                    messageHandler = new DeleteMessageHandlerImpl();
                    socketMessage = messageHandler.handleMessage(clientMessage);
                    break;
                default:
                    throw new Exception("header类型错误!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            socketMessage.setHeader(MessageType.RETURN);
            socketMessage.setSuccess(MessageType.ERROR);
            socketMessage.setMsg(e.toString());
        }
        return JsonUtil.bean2Json(socketMessage);
    }

    private List<Message> getMessageListByUserid(String userid) {
        String sql = " SELECT T.*,CODEDETAIL('MC_TYPE',T.MESSAGE_TYPE) AS MESSAGE_TYPE_CN FROM B_MESSAGE_CENTER T " +
                " WHERE T.DELETEFLAG <> '1' AND T.STOPSENDFLAG <> '1' " +
                " AND T.RECEIVER = ? " +
                " AND (T.DEADLINE IS NULL OR T.DEADLINE >= SYSDATE) " +
                " ORDER BY T.CREATETIME DESC ";

        JdbcService jdbcService = JdbcService.getInstance();
        List<Message> messageList = new ArrayList<>();

        List list = jdbcService.queryForList(sql,new Object[]{userid},new int[]{Types.VARCHAR});
        if(null != list && list.size() > 0){
            for(Object item : list){
                Message message = new Message();
                Map itemMap = (Map) item;

                message.setMessage_type_cn((String) itemMap.get("MESSAGE_TYPE_CN"));
                message.setMessage_type((String) itemMap.get("MESSAGE_TYPE"));
                message.setCreatetime((Date) itemMap.get("CREATETIME"));
                message.setSystemid((String) itemMap.get("SYSTEMID"));
                message.setContent((String) itemMap.get("CONTENT"));
                message.setDeadline((Date) itemMap.get("DEADLINE"));
                message.setReceive_dept((String) itemMap.get("RECEIVE_DEPT"));
                message.setReceiver((String) itemMap.get("RECEIVER"));
                message.setSender((String) itemMap.get("SENDER"));
                message.setUrl((String) itemMap.get("URL"));

                messageList.add(message);
            }
        }

        return messageList;
    }

    /**
     * 插入插入b_message_center表
     *
     * @param message 消息实体
     */
    private int insertMessage(Message message) {
        JdbcService jdbcService = JdbcService.getInstance();
        message.setCreatetime(DateUtil.getDBCurrentTime(jdbcService));
        String sql = "INSERT INTO B_MESSAGE_CENTER" +
                "(SYSTEMID,MESSAGE_TYPE,CONTENT,SENDER,RECEIVER,RECEIVE_DEPT,CREATETIME,LASTUPDATETIME,DEADLINE,URL)" +
                "VALUES" +
                "(?,?,?,?,?,?,?,SYSDATE,?,?)";
        return jdbcService.update(sql,
                new Object[]{message.getSystemid(), message.getMessage_type(), message.getContent(), message.getSender(), message.getReceiver(), message.getReceive_dept(),message.getCreatetime(), message.getDeadline(), message.getUrl()},
                new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,Types.TIMESTAMP, Types.TIMESTAMP, Types.VARCHAR});
    }
}
