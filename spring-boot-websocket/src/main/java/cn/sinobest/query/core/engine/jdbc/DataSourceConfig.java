package cn.sinobest.query.core.engine.jdbc;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;

import javax.sql.DataSource;

/**
 * 数据源配置类
 * @author yjh
 * @date 2017.09.08
 */
@Configuration
public class DataSourceConfig {
    private final Environment env;

    @Autowired
    public DataSourceConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public LobHandler lobHandler(){
        return new DefaultLobHandler();
    }
    @Bean(name = "dataSource")
    @Primary
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        dataSource.setMaxActive(Integer.parseInt(env.getProperty("spring.datasource.maxActive")));
        return dataSource;
    }
}
