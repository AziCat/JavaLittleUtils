package cn.sinobest.jzpt.message;

import cn.sinobest.jzpt.framework.security.SecurityUtil;
import cn.sinobest.jzpt.framework.util.ApplicationContextUtil;
import cn.sinobest.jzpt.framework.util.JsonUtils;
import cn.sinobest.jzpt.message.config.InvocationHandlerFactory;
import cn.sinobest.jzpt.message.config.MethodMetadata;
import cn.sinobest.jzpt.message.domain.EventPublish;
import cn.sinobest.jzpt.message.service.EventPublishService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * 保存到消息表
 *
 * @author yanjunhao
 * @date 2018年9月3日
 */
public class EventPublishMethodHandler implements InvocationHandlerFactory.MethodHandler {
    private final Logger logger = LoggerFactory.getLogger(EventPublishMethodHandler.class);

    private MethodMetadata methodMetadata;
    private Class<?> type;

    EventPublishMethodHandler(MethodMetadata methodMetadata, Class<?> type) {
        this.methodMetadata = methodMetadata;
        this.type = type;
    }

    public MethodMetadata getMethodMetadata() {
        return methodMetadata;
    }

    public void setMethodMetadata(MethodMetadata methodMetadata) {
        this.methodMetadata = methodMetadata;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public static class Factory {
        public InvocationHandlerFactory.MethodHandler create(Class<?> type, MethodMetadata methodMetadata) {
            return new EventPublishMethodHandler(methodMetadata, type);
        }
    }

    @Override
    public Object invoke(Object[] argv) throws Throwable {
        checkMetadata(this.methodMetadata);
        EventPublishService eventPublishService = (EventPublishService) ApplicationContextUtil.getBean(EventPublishService.class);
        Environment env = (Environment) ApplicationContextUtil.getBean(Environment.class);
        if (null == eventPublishService) {
            throw new NullPointerException("Bean[EventPublishService] have not found");
        }
        //保存到消息表
        EventPublish eventPublish = new EventPublish();
        eventPublish.setConsumes(methodMetadata.getConsumes());
        eventPublish.setDescription(methodMetadata.getKey());
        //等待执行
        eventPublish.setEventStatus(EventPublishService.WAIT);
        eventPublish.setMaxRetry(methodMetadata.getMaxRetry());
        eventPublish.setMessageType(methodMetadata.getMessageType());
        eventPublish.setRequestHeadType(methodMetadata.getRequestType());

        //消息作用范围为http请求
        eventPublish.setScope(EventPublishService.SCOPE);

        //处理入参，只取第一个
        Object param = argv[0];
        String paramStr;
        if (param instanceof String) {
            paramStr = (String) param;
        } else {
            paramStr = JsonUtils.object2Json(param);
        }
        eventPublish.setRequestParam(paramStr);
        eventPublish.setRequestParamType(param.getClass().toString());

        eventPublish.setUrl(methodMetadata.getUrl());
        String salt = env.getProperty("token.salt");
        eventPublish.setTokenSalt(TokenUtil.encode(SecurityUtil.getAccessToken(),
                StringUtils.isEmpty(salt) ? "default" : salt));
        eventPublishService.save(eventPublish);

        logger.info(this.type + ":" + methodMetadata.getKey() + " send message success");
        return null;
    }

    /**
     * 检查方法元数据
     *
     * @param methodMetadata
     */
    private void checkMetadata(MethodMetadata methodMetadata) throws Exception {
        Assert.notNull(methodMetadata, "MethodMetadata must be not null");
        if (StringUtils.isEmpty(methodMetadata.getMessageType())) {
            throw new NullPointerException("@MessageClient:messageType must be not null");
        }
        if (StringUtils.isEmpty(methodMetadata.getUrl())) {
            throw new NullPointerException("@MessageRequestParam:url must be not null");
        }
    }
}
