package cn.sinobest.jzpt.message.annotation;

import cn.sinobest.jzpt.message.config.MessageClientsRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用自动扫描消息客户端
 *
 * @author yanjunhao
 * @date 2018年9月2日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({MessageClientsRegistrar.class})
public @interface EnableMessageClients {
    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

    Class<?>[] defaultConfiguration() default {};

    Class<?>[] clients() default {};
}
