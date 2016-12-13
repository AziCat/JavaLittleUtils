package cn.sinobest.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogUtil {
	public static void log(String log, String logPath) {
		try {
			File file = new File(logPath);
			if(!file.exists()){
				file.createNewFile();
			}
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			FileWriter writer = new FileWriter(logPath, true);
			writer.write(log);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
