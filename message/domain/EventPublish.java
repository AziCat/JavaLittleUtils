/**
 * Created on Tue Sep 04 09:55:41 CST 2018
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
 * @Date Tue Sep 04 09:55:41 CST 2018
 * @TODO
 */
@Entity
@Table(name = "event_publish")
public class EventPublish extends AuditEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 状态（0：成功；1：失败；2：等待执行；3：失败重试）
     */
    @Column(name = "event_status", nullable = false, unique = false, length = 2)
    private java.lang.String eventStatus;
    /**
     * 说明
     */
    @Column(name = "description", nullable = true, unique = false, length = 65535)
    private java.lang.String description;
    /**
     * 最大重试次数
     */
    @Column(name = "max_retry", nullable = true, unique = false, length = 10)
    private java.lang.Integer maxRetry;
    /**
     * 已重试次数
     */
    @Column(name = "retried_count", nullable = true, unique = false, length = 10)
    private java.lang.Integer retriedCount;
    /**
     * 请求参数
     */
    @Column(name = "request_param", unique = false, length = 65535)
    private java.lang.String requestParam;
    /**
     * 请求参数类型
     */
    @Column(name = "request_param_type", unique = false, length = 500)
    private java.lang.String requestParamType;
    /**
     * 响应参数
     */
    @Column(name = "response_param", nullable = true, unique = false, length = 65535)
    private java.lang.String responseParam;
    /**
     * 响应参数类型
     */
    @Column(name = "response_param_type", nullable = true, unique = false, length = 500)
    private java.lang.String responseParamType;
    /**
     * 结束原因
     */
    @Column(name = "end_reason", nullable = true, unique = false, length = 65535)
    private java.lang.String endReason;
    /**
     * 异常内容
     */
    @Column(name = "exception_content", nullable = true, unique = false, length = 65535)
    private java.lang.String exceptionContent;
    /**
     * 访问用token，已加密
     */
    @Column(name = "token_salt", nullable = true, unique = false, length = 65535)
    private java.lang.String tokenSalt;
    /**
     * 消息类型
     */
    @Column(name = "message_type", nullable = true, unique = false, length = 500)
    private java.lang.String messageType;
    /**
     * 调用地址
     */
    @Column(name = "url", nullable = true, unique = false, length = 65535)
    private java.lang.String url;
    /**
     * 请求头类型
     */
    @Column(name = "request_head_type", nullable = true, unique = false, length = 500)
    private java.lang.String requestHeadType;
    /**
     * consumes
     */
    @Column(name = "consumes", nullable = true, unique = false, length = 500)
    private java.lang.String consumes;

    @Column(name = "scope", nullable = true, unique = false, length = 500)
    private java.lang.String scope;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public EventPublish() {
    }


    public void setUrl(java.lang.String value) {
        this.url = value;
    }

    public java.lang.String getUrl() {
        return this.url;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMaxRetry() {
        return maxRetry;
    }

    public void setMaxRetry(Integer maxRetry) {
        this.maxRetry = maxRetry;
    }

    public Integer getRetriedCount() {
        return retriedCount;
    }

    public void setRetriedCount(Integer retriedCount) {
        this.retriedCount = retriedCount;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

    public String getRequestParamType() {
        return requestParamType;
    }

    public void setRequestParamType(String requestParamType) {
        this.requestParamType = requestParamType;
    }

    public String getResponseParam() {
        return responseParam;
    }

    public void setResponseParam(String responseParam) {
        this.responseParam = responseParam;
    }

    public String getResponseParamType() {
        return responseParamType;
    }

    public void setResponseParamType(String responseParamType) {
        this.responseParamType = responseParamType;
    }

    public String getEndReason() {
        return endReason;
    }

    public void setEndReason(String endReason) {
        this.endReason = endReason;
    }

    public String getExceptionContent() {
        return exceptionContent;
    }

    public void setExceptionContent(String exceptionContent) {
        this.exceptionContent = exceptionContent;
    }

    public String getTokenSalt() {
        return tokenSalt;
    }

    public void setTokenSalt(String tokenSalt) {
        this.tokenSalt = tokenSalt;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getRequestHeadType() {
        return requestHeadType;
    }

    public void setRequestHeadType(String requestHeadType) {
        this.requestHeadType = requestHeadType;
    }

    public void setConsumes(java.lang.String value) {
        this.consumes = value;
    }

    public java.lang.String getConsumes() {
        return this.consumes;
    }
}

