package cn.sinobest.message.handler.impl;

import cn.sinobest.message.domain.ClientMessage;
import cn.sinobest.message.domain.MessageType;
import cn.sinobest.message.domain.SocketMessage;
import cn.sinobest.message.handler.IMessageHandler;
import cn.sinobest.query.core.engine.jdbc.JdbcService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Types;
import java.util.List;

/**
 * 删除操作处理器
 * @author yjh
 * @date 2017.09.13
 */
public class DeleteMessageHandlerImpl implements IMessageHandler {
    private Log logger = LogFactory.getLog(DeleteMessageHandlerImpl.class);
    @Override
    public SocketMessage handleMessage(ClientMessage clientMessage){
        SocketMessage socketMessage = new SocketMessage();
        //判断消息状态
        String status = clientMessage.getStatus();
        switch (status){
            case MessageType.LOGIC://逻辑删除
                logicDelete(clientMessage, socketMessage);
                break;
            default:
                setErrorMessage(socketMessage,"status类型错误！");
        }
        return socketMessage;
    }

    private void logicDelete(ClientMessage clientMessage, SocketMessage socketMessage) {
        List idList = (List) clientMessage.getParams().get("ids");
        if (null == idList || idList.size() == 0){
            String errorMsg = "params传入错误！";
            setErrorMessage(socketMessage,errorMsg);
        }else {
            logger.info("逻辑删除-->"+idList);
            JdbcService jdbcService = JdbcService.getInstance();
            StringBuilder sql = new StringBuilder();
            int[] types = new int[idList.size()];
            sql.append("UPDATE B_MESSAGE_CENTER T SET T.DELETEFLAG = '1',T.LASTUPDATETIME = SYSDATE WHERE T.SYSTEMID IN (");
            for(int i = 0;i < idList.size(); i++){
                if(i == idList.size() - 1){
                    sql.append("?");
                }else {
                    sql.append("?,");
                }
                types[i] = Types.VARCHAR;
            }
            sql.append(")");
            logger.info("sql-->"+sql.toString());

            jdbcService.update(sql.toString(),idList.toArray(),types);

            setSuccessMessage(socketMessage,"删除操作成功！");

        }
    }

    private void setErrorMessage(SocketMessage socketMessage, String errorMsg) {
        logger.error(errorMsg);
        socketMessage.setHeader(MessageType.RETURN);
        socketMessage.setSuccess(MessageType.ERROR);
        socketMessage.setMsg(errorMsg);
    }
    private void setSuccessMessage(SocketMessage socketMessage,String successMsg) {
        logger.info(successMsg);
        socketMessage.setHeader(MessageType.RETURN);
        socketMessage.setSuccess(MessageType.SUCCESS);
        socketMessage.setMsg(successMsg);
    }
}
