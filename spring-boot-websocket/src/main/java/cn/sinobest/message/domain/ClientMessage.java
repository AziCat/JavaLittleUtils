package cn.sinobest.message.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 客户端传输参数
 * @author yjh
 * @date 2017.09.12
 */
public class ClientMessage {
    private String header;
    private String status;
    private String userid;
    private Map params;


    public static List<String> getNotNullList(){
        String[] list = new String[]{"header","status","userid"};
        return Arrays.asList(list);
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    public Map getParams() {
        return params;
    }

    public void setParams(Map params) {
        this.params = params;
    }
}
