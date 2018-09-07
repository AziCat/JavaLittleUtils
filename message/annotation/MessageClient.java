package cn.sinobest.jzpt.message.annotation;

import java.lang.annotation.*;

/**
 * 消息客户端注解
 * @author yanjunhao
 * @date 2018年9月2日
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MessageClient {
    String messageType() default "None";
}
