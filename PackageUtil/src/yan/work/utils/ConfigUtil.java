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
	public static String filtStr;
	public static String filtSrc;
	public static String sourcePath;
	public static String packagePath;
	public ConfigUtil() {
		Properties prop = new Properties();
		InputStream in = this.getClass().getResourceAsStream("config.properties");
		try {
			prop.load(in);
			filtStr = prop.getProperty("filtStr").trim();
			filtSrc = prop.getProperty("filtSrc").trim();
			packagePath = prop.getProperty("packagePath").trim();
			sourcePath = prop.getProperty("sourcePath").trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
