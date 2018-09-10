package cn.sinobest.jzpt.message.config;

import cn.sinobest.jzpt.message.annotation.PathParameter;

import java.io.Serializable;
import java.util.List;

/**
 * 方法元数据
 *
 * @author yanjunhao
 * @date 2018年9月3日
 */
public class MethodMetadata implements Serializable {
    private String requestType;
    private String messageType;
    private String url;
    private String key;
    private String consumes;
    private Integer maxRetry;
    private List<PathParameter> pathParameterList;

    public List<PathParameter> getPathParameterList() {
        return pathParameterList;
    }

    public void setPathParameterList(List<PathParameter> pathParameterList) {
        this.pathParameterList = pathParameterList;
    }

    public Integer getMaxRetry() {
        return maxRetry;
    }

    public void setMaxRetry(Integer maxRetry) {
        this.maxRetry = maxRetry;
    }

    public String getConsumes() {
        return consumes;
    }

    public void setConsumes(String consumes) {
        this.consumes = consumes;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
