package cn.sinobest.message.domain;

import java.util.List;

/**
 * WebSocket消息
 * @author yjh
 * @date 2017.09.11
 */
public class SocketMessage {
    private String header;//头信息
    private String status;//消息状态
    private String success;//成功标记
    private String msg;
    private List<Message> items;//消息列表

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Message> getItems() {
        return items;
    }

    public void setItems(List<Message> items) {
        this.items = items;
    }
}
