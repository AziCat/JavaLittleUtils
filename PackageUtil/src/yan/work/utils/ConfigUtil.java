package yan.work.utils;

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
	static String buildPath;
	static String sourcePath;
	static String packagePath;
	static String buildFolders;
	static String buildReplace;
	static String frontPath;
	static String contextPath;
	ConfigUtil() {
		Properties prop = new Properties();
		InputStream in = this.getClass().getResourceAsStream("config.properties");
		try {
			prop.load(in);
			buildPath = prop.getProperty("buildPath").trim();
			packagePath = prop.getProperty("packagePath").trim();
			sourcePath = prop.getProperty("sourcePath").trim();
			buildFolders = prop.getProperty("buildFolders").trim();
			buildReplace = prop.getProperty("buildReplace").trim();
			frontPath = prop.getProperty("frontPath").trim();
			contextPath = prop.getProperty("contextPath").trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
