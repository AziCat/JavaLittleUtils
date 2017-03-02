package yan.study.readExcel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	public static void main(String[] args) {
		readExcel("C:\\Users\\yanjunhao\\Desktop\\触发器-中间转换.xls");
	}
	
	public static void readExcel(String fileName){  
        boolean isE2007 = false;    //判断是否是excel2007格式  
        if(fileName.endsWith("xlsx"))  
            isE2007 = true;  
        try {  
            InputStream input = new FileInputStream(fileName);  //建立输入流  
            Workbook wb  = null;  
            //根据文件格式(2003或者2007)来初始化  
            if(isE2007){  
                wb = new XSSFWorkbook(input); 
            }else {
                wb = new HSSFWorkbook(input);  
            }
            int i=0;
            while(i<wb.getActiveSheetIndex()){
            	Sheet sheet = wb.getSheetAt(i);     //循环sheet
            	readSheet(sheet);
            	i++;
            }
            
        } catch (IOException ex) {  
            ex.printStackTrace();  
        }  
    }

	private static void readSheet(Sheet sheet) {
		Map<String, String> syncMap;
		StringBuffer defalutValue;
		TabRel entity = new TabRel();
		syncMap = new HashMap<String,String>();
		defalutValue = new StringBuffer();
		Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器  
		while (rows.hasNext()) {  
		    Row row = rows.next();  //获得行数据  
		    if(row.getRowNum()==0){				//获取第一行的内容
		    	entity.setSonTab(row.getCell(0).getStringCellValue().trim().toUpperCase());
		    	entity.setTargetTab(row.getCell(1).getStringCellValue().trim().toUpperCase());
		    	String rule[] = row.getCell(2).getStringCellValue().trim().split("=");
		    	for(String field : rule){
		    		if(field.contains(":new.")){
		    			entity.setSontableglfield(field.replace(":new.", "").toUpperCase());
		    		}else{
		    			entity.setTargettableglfield(field.toUpperCase());
		    		}
		    	}
		    	if(entity.getTargetTab().contains("GL")){
		    		entity.setTargetTabIsGLTab("1");
		    	}
		    }else{								//关联字段
		    	String targetFieldName = row.getCell(1).getStringCellValue().trim().toUpperCase();
		    	String sonFieldName = row.getCell(0).getStringCellValue().trim().toUpperCase();
		    	if(sonFieldName.contains("(")){	//包含(的跳过
		    		continue;
		    	}
		    	String sonFiledStr = sonFieldName.replace(":NEW.", "").replace(",","");
		    	String tarFiledStr = targetFieldName.replace(",","");
		    	if(sonFieldName.contains("'")){//包含'的为默认值
		    		String defalutStr = sonFiledStr.replaceAll("'", "");
		    		defalutValue.append(tarFiledStr+":"+defalutStr+";");
		    	}else{
		    		syncMap.put(sonFiledStr, tarFiledStr);
		    	}
		    	
		    }
		    
		} 
		entity.setSyncMap(syncMap);
		entity.setDefalutValue(defalutValue.toString());
		System.out.println("--"+entity.getSonTab()+"----->"+entity.getTargetTab()+"--TABLE_GL_REL_SYNC");
		System.out.println(entity.getTableRelSql());
		System.out.println("--"+entity.getSonTab()+"----->"+entity.getTargetTab()+"--FIELD_GL_TABLE_SYNC");
		for(String sql:entity.getFieldRelSqlList()){
			System.out.println(sql);
		}
	}  
}
