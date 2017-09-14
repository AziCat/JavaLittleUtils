package cn.sinobest.message.service;

import cn.sinobest.message.domain.Message;
import cn.sinobest.message.domain.Result;
import cn.sinobest.message.domain.SocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * 消息服务接口
 *
 * @author yjh
 * @date 2017.09.08
 */
public interface IMessageService {
    /**
     * 发送消息
     *
     * @param message Message实例
     * @return 结果实例
     */
    Result sendMessage(Message message) throws Exception;

    /**
     * 保存日志
     *
     * @param type      类型
     * @param param     参数
     * @param clientIP  ip
     * @param result    返回结果
     * @param messageId 消息id
     */
    void saveLog(String type, String param, String clientIP, String result, String messageId);

    /**
     * 发送全部消息
     *
     * @param userid           警号
     * @param webSocketSession 会话实例
     */
    void sendFullMessage(String userid, WebSocketSession webSocketSession);

    /**
     * 处理客户端发送的请求
     *
     * @param param  参数
     * @return 结果
     */
    String handleMessage(String param);

    /**
     * 根据警号获取全量消息
     *
     * @param userid 警号
     * @return SocketMessage
     */
    SocketMessage getFullSocketMessage(String userid);
}
