package cn.sinobest.query.core.engine.jdbc;

import cn.sinobest.websocket.base.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service("JdbcService")
public class JdbcService implements IJdbcService {

    private final LobHandler lobHandler;
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcService(LobHandler lobHandler, JdbcTemplate jdbcTemplate, @Qualifier("dataSource") DataSource dataSource) {
        this.lobHandler = lobHandler;
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * 分页查询
     */
    public List pagedQuery(String sql, Object[] args, int[] argsType, int pageNum, int pageSize) {
        if (0 >= pageNum) {
            throw new RuntimeException("开始页数pageNum 应该是从1开始的。.");
        }
        int startIndex = (pageNum - 1) * pageSize;
        int lastIndex = startIndex + pageSize;
        StringBuilder paginationSQL = new StringBuilder(" SELECT * FROM ( ");
        paginationSQL.append(" SELECT temp.* ,ROWNUM num FROM ( ");
        paginationSQL.append(sql);
        paginationSQL.append(" ) temp where ROWNUM <= ").append(lastIndex);
        paginationSQL.append(" ) WHERE num >= ").append(startIndex);
        return jdbcTemplate.queryForList(paginationSQL.toString(), args, argsType);
    }

    /**
     * 支持大对象更新
     */
    public int update(String sql, Object[] args, int[] argsType) {
        return jdbcTemplate.update(sql, new LobCreatorPreparedStatementSetter(
                args, argsType, lobHandler.getLobCreator()));
    }

    /**
     * 重写上方法，对于分页传入的是当前页的最开始数据量，以及结束数据量
     *
     * @param sql
     * @param args
     * @param argsType
     * @param startIndex
     * @param lastIndex
     * @return
     */
    public List queryPage(String sql, Object[] args, int[] argsType,
                          int startIndex, int lastIndex) {
        StringBuilder paginationSQL = new StringBuilder(" SELECT * FROM ( ");
        paginationSQL.append(" SELECT temp.* ,ROWNUM num FROM ( ");
        paginationSQL.append(sql);
        paginationSQL.append(" ) temp where ROWNUM <= ").append(lastIndex);
        paginationSQL.append(" ) WHERE num >= ").append(startIndex);
        return jdbcTemplate.queryForList(paginationSQL.toString(), args,
                argsType);
    }

    /**
     * 找单个对象
     */
    public Map queryForSingle(String sql, Object[] args, int[] argsType) {
        Map map = null;
        List list = jdbcTemplate.queryForList(sql, args, argsType);
        if (list.size() > 0) {
            map = (Map) list.get(0);
        }
        return map;
    }

    /**
     * 按列表返回查找结果
     */
    public List queryForList(String sql, Object[] args, int[] argsType) {
        return jdbcTemplate.queryForList(sql, args, argsType);
    }

    public LobHandler getLobHandler() {
        return lobHandler;
    }


    public Connection getDataBaseConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static JdbcService getInstance() {
        Object object = SpringContextUtil.getBean("JdbcService");
        return (JdbcService) object;
    }


    public String ClobToString(Clob clob) throws Exception{
        String reString = "";
        Reader is = clob.getCharacterStream();// 得到流
        BufferedReader br = new BufferedReader(is);
        String s = br.readLine();
        StringBuffer sb = new StringBuffer();
        while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
            sb.append(s);
            s = br.readLine();
        }
        reString = sb.toString();
        if (br != null) {
            br.close();
        }
        if (is != null) {
            is.close();
        }
        return reString;
    }
}
