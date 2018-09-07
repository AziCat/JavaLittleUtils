package cn.sinobest.jzpt.message.config;


import cn.sinobest.jzpt.message.DefaultMethodHandler;
import cn.sinobest.jzpt.message.EventPublishMethodHandler;
import cn.sinobest.jzpt.message.annotation.MessageRequestParam;
import cn.sinobest.jzpt.message.Util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 通过此类构建目标接口的代理类
 *
 * @author yanjunhao
 * @date 2018年9月3日
 */
public class ReflectiveMessage {
    private Class<?> type;
    private String messageType;
    private EventPublishMethodHandler.Factory factory;


    ReflectiveMessage(Class<?> type, String messageType, EventPublishMethodHandler.Factory factory) {
        this.type = type;
        this.messageType = messageType;
        this.factory = factory;
    }

    public Object newInstance(Class<?> type) throws Exception {

        Map<String, InvocationHandlerFactory.MethodHandler> nameToHandler = apply(type);
        //需要处理的方法
        Map<Method, InvocationHandlerFactory.MethodHandler> methodToHandler = new LinkedHashMap<>();
        for (Method method : type.getMethods()) {
            //检查接口中定义的方法必须有且只能有1个入参
            if (!"equals".equals(method.getName()) && !"hashCode".equals(method.getName()) && !"toString".equals(method.getName())) {
                if (method.getParameterCount() != 1) {
                    throw new Exception("[" + type + "]interface,method[" + method.getName() + "]should have at least one parameter and only one");
                }
            }
            if (null == method.getAnnotation(MessageRequestParam.class)) {
                methodToHandler.put(method, new DefaultMethodHandler());
            } else if (method.getDeclaringClass() != Object.class) {
                methodToHandler.put(method, nameToHandler.get(Util.configKey(type, method)));
            }
        }
        InvocationHandler handler = new MessageInvocationHandler(type, methodToHandler);
        return Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, handler);
    }

    private Map<String, InvocationHandlerFactory.MethodHandler> apply(Class<?> type) {
        Map<String, InvocationHandlerFactory.MethodHandler> result = new HashMap<>(16);
        for (Method md : type.getMethods()) {
            //获取元数据
            MethodMetadata methodMetadata = getMetadata(type, md);
            result.put(methodMetadata.getKey(), this.factory.create(type, methodMetadata));
        }
        return result;
    }

    private MethodMetadata getMetadata(Class<?> type, Method md) {
        MethodMetadata methodMetadata = new MethodMetadata();
        MessageRequestParam messageRequestParam = md.getAnnotation(MessageRequestParam.class);
        String key = Util.configKey(type, md);
        methodMetadata.setKey(key);
        methodMetadata.setMessageType(this.messageType);
        if (null != messageRequestParam) {
            String requestType = messageRequestParam.type().name();
            methodMetadata.setRequestType(requestType);

            String url = messageRequestParam.url();
            methodMetadata.setUrl(url);

            String consumes = messageRequestParam.consumes();
            methodMetadata.setConsumes(consumes);

            Integer maxRetry = messageRequestParam.maxRetry();
            methodMetadata.setMaxRetry(maxRetry);
        }
        return methodMetadata;
    }

    /**
     * 替换原来的方法实现
     */
    public static class MessageInvocationHandler implements InvocationHandler {
        private final Class<?> type;
        private final Map<Method, InvocationHandlerFactory.MethodHandler> dispatch;

        MessageInvocationHandler(Class<?> type, Map<Method, InvocationHandlerFactory.MethodHandler> dispatch) {
            if (null == type || dispatch == null) {
                throw new NullPointerException("type or dispatch must be not null.");
            }
            this.type = type;
            this.dispatch = dispatch;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if ("equals".equals(method.getName())) {
                try {
                    Object
                            otherHandler =
                            args.length > 0 && args[0] != null ? Proxy.getInvocationHandler(args[0]) : null;
                    return equals(otherHandler);
                } catch (IllegalArgumentException e) {
                    return false;
                }
            } else if ("hashCode".equals(method.getName())) {
                return hashCode();
            } else if ("toString".equals(method.getName())) {
                return toString();
            }
            return dispatch.get(method).invoke(args);
        }

    }
}
