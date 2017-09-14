package cn.sinobest.message.domain;

/**
 * 返回结果
 *
 * @author yjh
 * @date 2017.09.11
 */
public class Result {
    private String success;
    private String msg;
    private String systemid;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSystemid() {
        return systemid;
    }

    public void setSystemid(String systemid) {
        this.systemid = systemid;
    }
}
