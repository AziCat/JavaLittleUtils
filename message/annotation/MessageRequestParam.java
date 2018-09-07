package cn.sinobest.jzpt.message.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface MessageRequestParam {
    enum TYPE {
        /**
         * post请求
         */
        POST("POST"),
        /**
         * put请求
         */
        PUT("PUT");
        private final String value;

        TYPE(String value) {
            this.value = value;
        }
    }

    //调用地址
    String url() default "";
    //请求类型
    TYPE type() default TYPE.POST;
    //content-type
    String consumes() default "application/json";
    //最大重试次数
    int maxRetry() default 10;
}
