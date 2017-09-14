package cn.sinobest.message.domain;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 消息实体
 *
 * @author yjh
 * @date 2017.09.08
 */
public class Message {
    private String systemid;
    //消息类型，字典MC_TYPE
    private String message_type;

    //消息类型翻译

    private String message_type_cn;
    //消息正文
    private String content;
    //发送人
    private String sender;
    //接收人
    private String receiver;
    //链接
    private String url;
    //接收单位
    private String receive_dept;
    //消息有效期限 yyyy-MM-dd HH:mm:ss
    private Date deadline;
    private Date createtime;

    public String getMessage_type_cn() {
        return message_type_cn;
    }

    public void setMessage_type_cn(String message_type_cn) {
        this.message_type_cn = message_type_cn;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSystemid() {
        return systemid;
    }

    public void setSystemid(String systemid) {
        this.systemid = systemid;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceive_dept() {
        return receive_dept;
    }

    public void setReceive_dept(String receive_dept) {
        this.receive_dept = receive_dept;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public static List<String> getNotNullList(){
        String[] list = new String[]{"message_type","content","receiver"};
        return Arrays.asList(list);
    }
}
