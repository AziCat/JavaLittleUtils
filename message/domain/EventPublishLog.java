/**
 * Created on Tue Sep 04 09:55:42 CST 2018
 * Copyright SinoBest, 2010-2011, All rights reserved.
 */
package cn.sinobest.jzpt.message.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.sinobest.jzpt.framework.domain.AuditEntity;
import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * @Author yanjunhao
 * @Date Tue Sep 04 09:55:42 CST 2018
 * @TODO
 */
@Entity
@Table(name = "event_publish_log")
public class EventPublishLog extends AuditEntity {
    private static final long serialVersionUID = 1L;

    /**
     * event_publish主键
     */
    @Column(name = "event_publish_id", nullable = false, unique = false, length = 50)
    private java.lang.String eventPublishId;
    /**
     * 响应内容
     */
    @Column(name = "response_context", nullable = true, unique = false, length = 65535)
    private java.lang.String responseContext;
    /**
     * response_code
     */
    @Column(name = "response_code", nullable = true, unique = false, length = 3)
    private java.lang.String responseCode;
    /**
     * exception_content
     */
    @Column(name = "exception_content", nullable = true, unique = false, length = 65535)
    private java.lang.String exceptionContent;

    public EventPublishLog() {
    }

    public String getEventPublishId() {
        return eventPublishId;
    }

    public void setEventPublishId(String eventPublishId) {
        this.eventPublishId = eventPublishId;
    }

    public String getResponseContext() {
        return responseContext;
    }

    public void setResponseContext(String responseContext) {
        this.responseContext = responseContext;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getExceptionContent() {
        return exceptionContent;
    }

    public void setExceptionContent(String exceptionContent) {
        this.exceptionContent = exceptionContent;
    }
}

