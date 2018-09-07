package cn.sinobest.jzpt.message.config;

import cn.sinobest.jzpt.message.EventPublishMethodHandler;
import org.springframework.beans.factory.FactoryBean;

/**
 * 通过FactoryBean返回自定义bean
 *
 * @author yanjunhao
 * @date 2018年9月2日
 */
public class MessageClientFactoryBean implements FactoryBean<Object> {
    private Class<?> type;
    private String messageType;

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public Class<?> getType() {
        return this.type;
    }

    @Override
    public Object getObject() throws Exception {
        ReflectiveMessage reflectiveMessage = new ReflectiveMessage(
                this.type, this.messageType,new EventPublishMethodHandler.Factory());
        return reflectiveMessage.newInstance(this.type);
    }

    @Override
    public Class<?> getObjectType() {
        return this.type;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
