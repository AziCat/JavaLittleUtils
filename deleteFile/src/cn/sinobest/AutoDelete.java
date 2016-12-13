package cn.sinobest;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.sinobest.util.ConfigUtil;
import cn.sinobest.util.LogUtil;

/**
 * 根据时间配置删除指定目录文件
 * 
 * @author yanjunhao
 * 
 */
public class AutoDelete {
	public static List<String> deleteList = new ArrayList<String>();		//删除列表
	public static String[] delTypeArr = null;								//删除类型
	public static Integer saveDay = null;									//保存天数 
	public static String[] delPath = null;									//执行删除任务的路径
	public static String logPath = null;									//日志文件路径
	public static Date now = new Date();
	public static long delTime = 0;
	static{
		//初始化数据
		ConfigUtil configUtil = new ConfigUtil();
		delTypeArr = configUtil.delTypeArr;
		delPath = configUtil.delPath;
		saveDay = configUtil.saveDay;
		logPath = configUtil.logPath;
		
		Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(now);
        rightNow.add(Calendar.DAY_OF_YEAR,-1*saveDay);//日期减于保存天数
        delTime = rightNow.getTimeInMillis();
	}
	public static void main(String[] args) {
		initDeleteList();
		
		//执行删除操作
		StringBuffer logBuffer = new StringBuffer();
		for(String file:deleteList){
			logBuffer.append("删除时间--->"+now.toLocaleString()+"   删除文件--->"+file+"\r\n");
			File f = new File(file);
			f.delete();
		}
		//记录日志
		logBuffer.append("*****************************************************\r\n");
		LogUtil.log(logBuffer.toString(), logPath);
	}
	/**
	 * 初始化删除列表
	 */
	public static void initDeleteList() {
		for(String path:delPath){
			File root = new File(path);
			try {
				showAllFiles(root);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * 遍历目标文件目录
	 * @param dir
	 * @throws Exception
	 */
	public static void showAllFiles(File dir) throws Exception {
		File[] fs = dir.listFiles();
		for (int i = 0; i < fs.length; i++) {
			if (fs[i].isDirectory()) {
				try {
					showAllFiles(fs[i]);
				} catch (Exception e) {
				}
			}
			long time = fs[i].lastModified();
			String filePath = fs[i].getAbsolutePath();
			String fileName = fs[i].getName();
			if(isDelTime(time)&&isDelType(fileName)){
				deleteList.add(filePath);
			}
		}
	}
	/**
	 * 获取文件扩展名
	 * @param fileName
	 * @return
	 */
	public static String getFileExtendName(String fileName){
		if(fileName.indexOf(".")==-1){
			return "document";
		}
		return fileName.substring(fileName.lastIndexOf(".")+1);
	}
	/**
	 * 是否删除类型
	 * @param fileName
	 * @return
	 */
	public static boolean isDelType(String fileName){
		for(String fileExtendName : delTypeArr){
			if(getFileExtendName(fileName).equals(fileExtendName)){
				return true;
			}
		}
		return false;
	}
	/**
	 * 是否满足删除时间
	 * @param lastModiTime
	 * @return
	 */
	public static boolean isDelTime(long lastModiTime){
		return lastModiTime<delTime;
	}
}
