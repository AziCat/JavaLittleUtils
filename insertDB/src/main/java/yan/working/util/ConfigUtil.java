package main.java.yan.working.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * 数据库配置读取工具类
 * @author yanjunhao
 * @date 2016年6月15日, AM 10:50:24
 *
 */
public class ConfigUtil {
	public static String url = null;
	public static String driverName = null;
	public static String userName = null;
	public static String password = null;
	public static String xmlPath = null;
	public ConfigUtil() {
		Properties prop = new Properties();
		InputStream in = this.getClass().getResourceAsStream("config.properties");
		try {
			prop.load(in);
			url = prop.getProperty("database.url").trim();
			driverName = prop.getProperty("database.driverClassName").trim();
			userName = prop.getProperty("database.username").trim();
			password = prop.getProperty("database.password").trim();
			xmlPath = prop.getProperty("xmlPath").trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
