package yan.work.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件工具类
 * @author yanjunhao
 *
 */
public class FileUtil {
	private static ConfigUtil config = new ConfigUtil();
	/**
	 * 创建文件目录 
	 * @param path
	 */
	public static void createDirectory(String path){
		if(StringUtil.isEmpty(path)){								//非空判断
			return;
		}
		File f = new File(path);									//获取文件
		if(!f.exists()){
			f.mkdirs();												//如果为空，创建目录
		}
	}
	@SuppressWarnings("static-access")
	public static void createFile(String filePath) throws Exception{
		if(StringUtil.isEmpty(filePath)){								//非空判断
			return;
		}
		String sourcePath = config.sourcePath;
		String packagePath = config.packagePath;
		String [] arr = filePath.split("\\\\");
		String fileName = arr[arr.length-1];
		String dir = filePath.replace(fileName,"");
//		System.out.println("sourcePath:"+sourcePath);
//		System.out.println("packagePath:"+packagePath);
//		System.out.println("fileName:"+fileName);
//		System.out.println("dir:"+dir);
		createDirectory(packagePath+dir);								//创建打包目录
		File[] fileArr = new File(sourcePath+dir).listFiles();			//获取源文件目录
		for(File targetFile:fileArr){
			String targetName = targetFile.getName();
			if("java".equals(fileName.split("\\.")[1])){				//如果是java文件
				if(targetName.startsWith(fileName.split("\\.")[0])
						&&targetName.endsWith("class")){				//复制内容
					File packFile = new File(packagePath+dir+targetName);
					packFile.createNewFile();
					copyFile(targetFile,packFile);
				}
			}else{
				if(targetName.equals(fileName)){
					File packFile = new File(packagePath+dir+targetName);
					packFile.createNewFile();
					copyFile(targetFile,packFile);
				}
			}
		}
	}
	/**
	 * 文件复制	targetFile-》packFile
	 * @param targetFile
	 * @param packFile
	 */
	private static void copyFile(File targetFile, File packFile) {
		InputStream fis = null;  
	    OutputStream fos = null;  
	    try {  
	        fis = new FileInputStream(targetFile);  
	        fos = new FileOutputStream(packFile);  
	        byte[] buf = new byte[(int) targetFile.length()];  
	        int i;  
	        while ((i = fis.read(buf)) != -1) {  
	            fos.write(buf, 0, i);  
	        }  
	    }  
	    catch (Exception e) {  
	        e.printStackTrace();  
	    } finally {  
	        close(fis);  
	        close(fos);  
	    }  
	    System.out.println("打包成功---->"+packFile.getAbsolutePath());
	}
	/**
	 * 获取要打包的文件列表
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static List<String> getPackageFileList(){
		List<String> list = new ArrayList<String>();
		InputStream is = null;
		BufferedReader br = null;
		try {
			//is = new FileInputStream(f);
			is = FileUtil.class.getResourceAsStream("packageList");
			br = new BufferedReader(new InputStreamReader(is));
			String str = null;
			while(null!=(str=br.readLine())){
				if(str.indexOf(".")<0) continue;					//过滤目录
				String targetPath = str.replace(config.filtStr, "").replace("src", config.filtSrc)
						.replaceAll("/", "\\\\");
				list.add(targetPath);
				//System.out.println(targetPath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	public static void close(InputStream is){
		if(null != is)
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	public static void close(OutputStream os){
		if(null != os)
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}
