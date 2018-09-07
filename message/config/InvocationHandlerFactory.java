package cn.sinobest.jzpt.message.config;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 工厂接口
 * @author yanjunhao
 * @date 2018年9月3日
 */
public interface InvocationHandlerFactory {
    /**
     * 创建代理类调用处理器
     * @param type
     * @param dispatch
     * @return
     */
    InvocationHandler create(Class<?> type, Map<Method, MethodHandler> dispatch);

    /**
     * Like {@link InvocationHandler#invoke(Object, java.lang.reflect.Method, Object[])}, except for a
     * single method.
     */
    interface MethodHandler {
        /**
         * 调用入口
         * @param argv
         * @return
         * @throws Throwable
         */
        Object invoke(Object[] argv) throws Throwable;
    }

    static final class Default implements InvocationHandlerFactory {

        @Override
        public InvocationHandler create(Class<?> type, Map<Method, MethodHandler> dispatch) {
            return new ReflectiveMessage.MessageInvocationHandler(type, dispatch);
        }
    }
}
