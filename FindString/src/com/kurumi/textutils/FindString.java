package com.kurumi.textutils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 修复所有domain目录下的文件中不符合命名规范的get、set方法
 * @author yanjunhao
 *
 */
public class FindString {  
    public static String path="src/com/";   //src相对路径  
    public static String reg = "[g,s]et[a-z](.*?)\\(";
    //递归方法  
    public static void findString(File [] dir)throws Exception{  
        for(int i=0;i<dir.length;i++){  
            if(dir[i].isDirectory()){           //如果是文件夹则获取文件夹下所有文件然后再次递归  
                path+=dir[i].getName()+"/";     //对文件路径做+”/“处理  
                File file1=new File(path); 
                File [] dir1=file1.listFiles();  
                findString(dir1);  
                String [] ss=path.split("/");       //当递归return之后 文件路径减少一层  
                path="";  
                for(int j=0;j<ss.length-1;j++){  
                    path+=ss[j]+"/";  
                }  
            }else{  
               
                String filePath = dir[i].getAbsolutePath();			//文件全路径
                if(filePath.indexOf("domain")!=-1){					//满足在domain目录下
                	FileInputStream fis = new FileInputStream(dir[i]);    //如果不是文件夹则读取byte[]流  
                    int n=(int)dir[i].length();  
                    byte [] buffer=new byte[n];  
                    fis.read(buffer);  
                	String content = new String(buffer,"UTF-8");       //编码格式 
                	Pattern p = Pattern.compile(reg);					//正则匹配
                	Matcher m = p.matcher(content);
                    while (m.find()) {
                    	String tagStr = m.group(0);					//获取目录字符
                    	String rightStr = null;
                    	String lowerCase = tagStr.substring(3, 4);	//获取错误的小写内容
                    	//tagStr = tagStr.replace(lowerCase, lowerCase.toUpperCase());
                    	rightStr = tagStr.substring(0,3)+lowerCase.toUpperCase()+tagStr.substring(4);//获取修复内容
                    	content = content.replace(tagStr, rightStr);
                        System.out.println("修复文件:"+dir[i].getName()+"		"+tagStr.replace("(", "")+"		"+rightStr.replace("(", ""));          
                    }
                    if(null!=fis) fis.close();//关闭流
                    dir[i].delete();//删除原文件
                    File file = new File(filePath); 
                    if(!file.exists()){
                    	file.createNewFile();//创建新文件
                    }
                    FileWriter fw = new FileWriter(filePath);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(content);//把修复后的内容写入新文件
                    if(null!=bw) bw.close();//关闭流
                    if(null!=fw) fw.close();
                }
            }  
        }  
        return;  
    }  
    public static void main(String [] args){  
    	File file=new File(path);  
        File [] dir=file.listFiles();  
        try {
			findString(dir);
		} catch (Exception e) {
			e.printStackTrace();
		}  
    }  
}  

