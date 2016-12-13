package cn.sinobest.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {
	public static String[] delPath = null;		//删除目录数组
	public static String[] delTypeArr = null;	//需要删除的文件类型数组
	public static Integer saveDay = null;		//保存天数
	public static String logPath = null;		//日志
	public ConfigUtil(){
		Properties prop = new Properties();
		InputStream in = this.getClass().getResourceAsStream("config.properties");
		try {
			prop.load(in);
			delPath = prop.getProperty("deletePath").trim().split(",");
			delTypeArr = prop.getProperty("delType").trim().split(",");
			logPath = prop.getProperty("log").trim();
			saveDay = Integer.parseInt(prop.getProperty("saveDay").trim());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
