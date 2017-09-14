package cn.sinobest.message.handler.impl;

import cn.sinobest.message.domain.ClientMessage;
import cn.sinobest.message.domain.MessageType;
import cn.sinobest.message.domain.SocketMessage;
import cn.sinobest.message.handler.IMessageHandler;
import cn.sinobest.message.service.IMessageService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 刷新操作处理器
 * @author yjh
 * @date 2017.09.13
 */
public class RefreshMessageHandlerImpl implements IMessageHandler {
    private Log logger = LogFactory.getLog(RefreshMessageHandlerImpl.class);

    private IMessageService messageService;
    public RefreshMessageHandlerImpl(IMessageService messageService) {
        this.messageService = messageService;
    }


    @Override
    public SocketMessage handleMessage(ClientMessage clientMessage){
        SocketMessage socketMessage = new SocketMessage();
        //判断消息状态
        String status = clientMessage.getStatus();
        switch (status){
            case MessageType.FULL://全量返回
                socketMessage = messageService.getFullSocketMessage(clientMessage.getUserid());
                break;
            default:
                setErrorMessage(socketMessage,"status类型错误！");
        }
        return socketMessage;
    }
    private void setErrorMessage(SocketMessage socketMessage, String errorMsg) {
        logger.error(errorMsg);
        socketMessage.setHeader(MessageType.RETURN);
        socketMessage.setSuccess(MessageType.ERROR);
        socketMessage.setMsg(errorMsg);
    }
}
