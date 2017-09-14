package cn.sinobest.message.handler;

import cn.sinobest.message.domain.ClientMessage;
import cn.sinobest.message.domain.SocketMessage;

/**
 * 客户端消息处理接口
 * @author yjh
 * @date 2017.09.13
 */
public interface IMessageHandler {
    /**
     * 处理消息
     * @param clientMessage 客户端消息实例
     * @return 服务端消息实例
     */
    SocketMessage handleMessage(ClientMessage clientMessage);
}
