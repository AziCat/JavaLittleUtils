package cn.sinobest.jzpt.message;

import cn.sinobest.jzpt.message.config.InvocationHandlerFactory;

/**
 * 方法默认实现类
 * @author yanjunhao
 * @date 2018年9月3日
 */
public class DefaultMethodHandler implements InvocationHandlerFactory.MethodHandler {
    @Override
    public Object invoke(Object[] argv) throws Throwable {
        System.out.println("DefaultMethodHandler invoke.....");
        return null;
    }
}
