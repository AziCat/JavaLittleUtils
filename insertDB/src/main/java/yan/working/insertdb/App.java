package main.java.yan.working.insertdb;

import java.io.File;
import java.io.FileWriter;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.java.yan.working.util.ConfigUtil;
import main.java.yan.working.util.IgnoreDTDEntityResolver;
import main.java.yan.working.util.JDBCUtil;
import oracle.sql.BLOB;

import org.apache.commons.codec.binary.Base64;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * Hello world!
 *
 */
public class App {
	/*
	 * Map<表名,Map<字段名,字段类型>>
	 */
	private static Map<String,Map<String,String>> tabParamsMap = new HashMap<String,Map<String,String>>();
	
    @SuppressWarnings("static-access")
	public static void main( String[] args ){
    	ConfigUtil config = new ConfigUtil();
    	System.out.println(config.xmlPath);
        //读取目录下要入库的xml报文
    	File[] xmlFiles = new File(config.xmlPath).listFiles();
    	//遍历
    	for(File xmlFile : xmlFiles){
    		initXmlFile(xmlFile);
    	}
    	System.out.println("``````````````````````````````入库完毕`````````````````````````````");
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void initXmlFile(File xmlFile) {
		if(xmlFile.isDirectory()){
			File[] xmlFiles = xmlFile.listFiles();
			for(File file:xmlFiles){
				initXmlFile(file);
			}
		}else{
			Connection conn = null;
			System.out.println("开始解析文件-->"+xmlFile.getName());
			Document doc = getDoc(xmlFile);									//解析xml文件
			List<Element> recordElement = doc.selectNodes("//RBSPMessage/Method/Items/Item/Value/Data/PACKAGE/DATA/RECORD");
			for(Iterator it = recordElement.iterator();it.hasNext();){							//遍历节录节点
				Element record = (Element)it.next();
				String tableName = record.attributeValue("YWXXDM");			//获取表名
				System.out.println("开始插入表-->"+tableName);
				Map<String,String> tabMap = tabParamsMap.get(tableName);	//获取表对应字段参数
				if(null==tabMap){											//如果获取不到，进行初始化
					tabMap = initTabMap(tableName);
				}
				boolean isBlobTab = false;
				String base64Blob = null;
				String blobName = null;
				String text = null;
				Set<String> keySet = tabMap.keySet();						//获取字段
				StringBuffer sqlHead = new StringBuffer("INSERT INTO "+tableName+" (SYSTEMID");
				StringBuffer sqlValues = new StringBuffer("VALUES ('I'||getid(null) ");
				for(String zd : keySet){
					if("SYSTEMID".equals(zd)) continue;						//跳过systemid
					Element item = (Element) record.selectSingleNode(zd);
					if(null==item) continue;								//节点为空，跳过
					text = item.getText();
					if(null == text || "".equals(text)) continue;			//节点内容为空，跳过
					String type = tabMap.get(zd);							//字段类型
					sqlHead.append(","+zd);
					if("VARCHAR2".equals(type)){
						sqlValues.append(",'"+text+"'");
					}else if("DATE".equals(type)){
						sqlValues.append(",to_date('"+text+"','yyyymmddhh24miss')");
					}else if("NUMBER".equals(type)){
						sqlValues.append(","+text);
					}else if("CLOB".equals(type)){
						sqlValues.append(",to_clob('"+text+"')");
					}else if("BLOB".equals(type)){
						isBlobTab = true;
						base64Blob = text;
						blobName = zd;
						sqlValues.append(",empty_blob()");
					}
				}
				sqlHead.append(") ");
				sqlValues.append(") ");
				String insertSql = sqlHead.toString()+sqlValues.toString();
				//System.out.println(insertSql);
				try{
					conn = JDBCUtil.getConntion();
					if(isBlobTab){												//处理有大字段的表
						String blobId = tableName + new Date().getTime();		//创建id
						insertSql = insertSql.replace("'I'||getid(null)", "'"+blobId+"'");		//替换getid(null)
						insertBlob(insertSql,tableName,blobName,blobId,base64Blob,conn);
					}else{
						insertTab(insertSql,conn,tableName);
					}
				}catch(Exception e){
					e.printStackTrace();
					System.out.println(tableName+"插入出错！");
					System.out.println("出错脚本："+insertSql);
					try {
					File file = new File("F:\\insertUtil\\log.txt");
					if(!file.exists()){
						file.createNewFile();
					}
					// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
						FileWriter writer = new FileWriter("F:\\insertUtil\\log.txt", true);
						writer.write(e.toString()+"\r\n");
						writer.write("出错脚本："+insertSql);
						writer.close();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}finally{
					
				}
			}
			JDBCUtil.close(conn);
			System.out.println("解析文件完成-->"+xmlFile.getName());
		}
		
	}
	
	@SuppressWarnings("deprecation")
	private static void insertBlob(String insertSql, String tableName,String blobName,
			String blobId, String base64Blob, Connection conn) throws Exception{
		BLOB blob = null;		
		//先执行插入操作
		PreparedStatement pst = conn.prepareStatement(insertSql);
		pst.execute();
		
		String sql = "select "+blobName+" from "+tableName+" where systemid = ? for update";
		pst = conn.prepareStatement(sql);
		pst.setString(1, blobId);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			blob = (BLOB)rs.getBlob(blobName);
		}
		
		//更新blob
		byte[] bloeByte = Base64.decodeBase64(base64Blob.getBytes());
		
		pst = conn.prepareStatement("update "+tableName+" set "+blobName+"=? where SYSTEMID ='"+blobId+"'");
		OutputStream out = blob.getBinaryOutputStream();
		out.write(bloeByte);
		out.flush();
		out.close();
		
		pst.setBlob(1, blob);						
		pst.executeUpdate();
		
		JDBCUtil.close(rs, pst, null);
		System.out.println(tableName+"插入完毕。");
	}

	private static void insertTab(String insertSql,Connection conn,String tableName) throws Exception{
		PreparedStatement pst = conn.prepareStatement(insertSql);
		pst.execute();
		pst.close();
		System.out.println(tableName+"插入完毕。");
		//System.out.println("成功插入-->"+insertSql);
	}

	private static Map<String, String> initTabMap(String tableName) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Map<String,String> tabMap = new HashMap<String,String>();
		try{
			String sql = "SELECT column_name AS zd,data_type AS tp from user_tab_columns where table_name= ?";
			conn = JDBCUtil.getConntion();
			pst = conn.prepareStatement(sql);
			pst.setString(1, tableName);
			rs = pst.executeQuery();
			while(rs.next()){
				tabMap.put(rs.getString("zd"), rs.getString("tp"));
			}
			tabParamsMap.put(tableName, tabMap);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			JDBCUtil.close(rs,pst,conn);
		}
		
		return tabMap;
	}


	private static Document getDoc(File controlXml){
		Document doc = null;
		try {
			SAXReader reader = new SAXReader();
			reader.setEntityResolver(new IgnoreDTDEntityResolver());
			doc = reader.read(controlXml);
		}catch (DocumentException e) {
			e.printStackTrace();
		}
		return doc;
    }
}
