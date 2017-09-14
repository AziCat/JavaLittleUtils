package cn.sinobest.message.domain;

/**
 * 消息类型常量
 * @author yjh
 * @date 2017.09.13
 */
public class MessageType {
    public final static String SUCCESS = "1";
    public final static String ERROR = "0";

    //头信息定义
    public final static String MESSAGE = "MESSAGE";
    public final static String RETURN = "RETURN";
    public final static String REFRESH = "REFRESH";
    public final static String DELETE = "DELETE";
    public final static String UPDATE = "UPDATE";

    //状态信息定义
    public final static String INCREMENT = "INCREMENT";
    public final static String FULL = "FULL";
    public final static String STOPSEND = "STOPSEND";
    public final static String LOGIC = "LOGIC";
}
