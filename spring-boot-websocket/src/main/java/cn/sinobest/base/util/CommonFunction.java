package cn.sinobest.base.util;


import cn.sinobest.query.core.engine.jdbc.JdbcService;

import java.sql.Types;
import java.util.Date;
import java.util.Map;

/**
 * 公用方法
 * @author yjh
 * @date 2017.09.11
 */
public class CommonFunction {
    /**
     * 生成一个系统id
     * @return id
     */
    public static String getSystemid(){
        JdbcService jdbcService = JdbcService.getInstance();
        String sql = "SELECT GETID(NULL) AS ID FROM DUAL";
        Map map = jdbcService.queryForSingle(sql,new Object[]{},new int[]{});
        return (String) map.get("ID");
    }

    /**
     * 检查传入时间是否小于数据库时间
     * @param date 时间
     * @return boolean
     */
    public static boolean checkDateIsLtDbDate(Date date){
        JdbcService jdbcService = JdbcService.getInstance();
        String sql = "SELECT 1 AS FLAG FROM dual WHERE ? < SYSDATE ";
        Map map = jdbcService.queryForSingle(sql,new Object[]{date},new int[]{Types.TIMESTAMP});
        return map != null;
    }

    /**
     * 字典翻译
     * @param kind 字典Kind
     * @param code 字典code
     * @return 字典翻译内容
     */
    public static String code2Detail(String kind,String code){
        JdbcService jdbcService = JdbcService.getInstance();
        String sql = "SELECT CODEDETAIL(?,?) AS DETAIL FROM DUAL";
        Map map = jdbcService.queryForSingle(sql,new Object[]{kind,code},new int[]{Types.VARCHAR,Types.VARCHAR});
        return (String) map.get("DETAIL");
    }
}
