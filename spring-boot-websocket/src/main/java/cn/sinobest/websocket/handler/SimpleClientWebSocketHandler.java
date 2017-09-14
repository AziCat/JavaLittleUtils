/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.sinobest.websocket.handler;

import cn.sinobest.message.service.IMessageService;
import cn.sinobest.websocket.base.UserSecurity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.socket.*;

import java.net.URI;


/**
 * 通用处理器
 */
public class SimpleClientWebSocketHandler implements WebSocketHandler {
    @Autowired
    @Qualifier("SimpleMessageService")
    private IMessageService messageService;

    private Log logger = LogFactory.getLog(SimpleClientWebSocketHandler.class);

    /**
     * 链接创建后
     *
     * @param webSocketSession WebSocketSession
     * @throws Exception 异常
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        String userid = getAttributes(webSocketSession, "userid");
        if (!UserSecurity.addUser(userid, webSocketSession)) {
            //添加用户失败，关闭会话
            webSocketSession.close();
        } else {
            //添加成功后，发送全量消息
            logger.info("发送全量消息-->"+userid);
            messageService.sendFullMessage(userid, webSocketSession);
        }
    }

    /**
     * 接收到客户端的信息时触发
     *
     * @param webSocketSession WebSocketSession
     * @param webSocketMessage WebSocketMessage
     * @throws Exception 异常
     */
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        String param = webSocketMessage.getPayload().toString();
        logger.info("handleMessage："+param);
        String result = messageService.handleMessage(param);
        logger.info("handleMessageResult："+result);
        //发送到客户端
        webSocketSession.sendMessage(new TextMessage(result));
        //保存日志
        //messageService.saveLog("handleMessage",param,webSocketSession.getRemoteAddress().getAddress().getHostAddress(),result,"");
    }

    /**
     * 传输错误时触发
     *
     * @param webSocketSession WebSocketSession
     * @param throwable        Throwable
     * @throws Exception 异常
     */
    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {

    }

    /**
     * 断开链接时触发
     *
     * @param webSocketSession WebSocketSession
     * @param closeStatus      CloseStatus
     * @throws Exception 异常
     */
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        String userid = getAttributes(webSocketSession, "userid");
        UserSecurity.removeUser(userid, webSocketSession);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 从会话中获取参数
     *
     * @param webSocketSession 会话实例
     * @param key              参数名
     * @return 参数内容
     */
    private String getAttributes(WebSocketSession webSocketSession, String key) {
        URI uri = webSocketSession.getUri();
        //userid=123&dept=4403
        String query = uri.getQuery();
        if (null != query && !"".equals(query)) {
            //分析参数
            String[] queryArr = query.split("&");
            for (String queryItem : queryArr) {
                //userid=123
                String[] queryItemArr = queryItem.split("=");
                if (2 == queryItemArr.length) {
                    if (key.equals(queryItemArr[0])) return queryItemArr[1];
                }
            }
        }
        return null;
    }
}
