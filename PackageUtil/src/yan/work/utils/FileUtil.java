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
import java.util.Arrays;
import java.util.List;

/**
 * 文件工具类
 * @author yanjunhao
 *
 */
public class FileUtil {
	private static ConfigUtil config = new ConfigUtil();
	public static int count = 0;
	/**
	 * 创建文件目录
	 * @param path 目录
	 */
	private static void createDirectory(String path){
		if(StringUtil.isEmpty(path)){								//非空判断
			return;
		}
		File f = new File(path);									//获取文件
		if(!f.exists()) f.mkdirs();                                 //如果为空，创建目录
	}
	@SuppressWarnings("static-access")
	public static void createFile(String filePath) throws Exception{
		if(StringUtil.isEmpty(filePath)){								//非空判断
			return;
		}
		if(!filePath.contains(".")){
			return;
		}
		String sourcePath = config.sourcePath;//根目录
		String packagePath = config.packagePath;//打包目录
		String buildPath = config.buildPath;//编译文件输出目录
		String frontPath = config.frontPath;//前台路径
		List<String> buildFolderList = Arrays.asList(config.buildFolders.split(","));//编译目录列表
		String [] arr = filePath.split("\\\\");
		String fileName = arr[arr.length-1];//文件名
		String dir = filePath.replace(fileName,"");
		//判断文件是否属于编译目录
		boolean isBuild = false;
		for(String buildFolder : buildFolderList){
			if(filePath.contains(buildFolder)){
				isBuild = true;
				dir = dir.replace(buildFolder,"");
			}
		}
		File[] fileArr;
		String targetFolder;
		if(isBuild){
			fileArr = new File(buildPath+config.buildReplace+dir).listFiles();			//获取编译目录
			targetFolder = packagePath+config.contextPath+"\\"+config.buildReplace+dir;
		}else{
			//如果不是编译目录，判断是否前台文件
			if(filePath.contains(frontPath)){
				dir = dir.replace(frontPath,"");
				fileArr = new File(buildPath+dir).listFiles();			//获取编译目录
				targetFolder = packagePath+config.contextPath+"\\"+dir;
			}else{
				fileArr = new File(sourcePath+dir).listFiles();			//获取源文件目录
				targetFolder = packagePath+dir;
			}
		}
		createDirectory(targetFolder);								//创建目标目录
		for(File targetFile:fileArr){
			String targetName = targetFile.getName();
			if(fileName.endsWith(".java")){				//如果是java文件
				if((targetName.split("\\.")[0].startsWith(fileName.split("\\.")[0])&&!targetName.split("\\.")[0].equals(fileName.split("\\.")[0])
						&&targetName.endsWith("class")&&targetName.contains("$"))
						||targetName.split("\\.")[0].equals(fileName.split("\\.")[0])){				//复制内容


					File packFile = new File(targetFolder+targetName);
					packFile.createNewFile();
					copyFile(targetFile,packFile);
				}
			}else{
				if(targetName.equals(fileName)){
					File packFile = new File(targetFolder+targetName);
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
		count ++;
	}
	/**
	 * 获取要打包的文件列表
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static List<String> getPackageFileList(){
		List<String> list = new ArrayList<>();
		InputStream is = null;
		BufferedReader br = null;
		try {
			is = FileUtil.class.getResourceAsStream("packageList");
			br = new BufferedReader(new InputStreamReader(is));
			String str;
			while(null!=(str=br.readLine())){
				String targetPath = str.replaceAll("/", "\\\\");
				list.add(targetPath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(br);
			close(is);
		}
		return list;
	}
	private static void close(BufferedReader br){
		if(null != br){
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private static void close(InputStream is){
		if(null != is)
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	private static void close(OutputStream os){
		if(null != os)
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}
