package yan.study.readExcel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TabRel {
	private String sontableglfield;
	private String targettableglfield;
	private String sonTab;								//子表
	private String targetTab;							//目标表
	private Map<String,String> syncMap;					//同步内容<子表字段，目标表字段>
	private String targetTabIsGLTab;						//目标表是否关联表
	private String defalutValue;					//默认值
	private String tableRelSql;						//TABLE_GL_REL_SYNC
	private List<String> fieldRelSqlList;
	public String getSonTab() {
		return sonTab;
	}
	public void setSonTab(String sonTab) {
		this.sonTab = sonTab;
	}
	public String getTargetTab() {
		return targetTab;
	}
	public void setTargetTab(String targetTab) {
		this.targetTab = targetTab;
	}
	public Map<String, String> getSyncMap() {
		return syncMap;
	}
	public void setSyncMap(Map<String, String> syncMap) {
		this.syncMap = syncMap;
	}
	
	public String getSontableglfield() {
		return sontableglfield;
	}
	public void setSontableglfield(String sontableglfield) {
		this.sontableglfield = sontableglfield;
	}
	public String getTargettableglfield() {
		return targettableglfield;
	}
	public void setTargettableglfield(String targettableglfield) {
		this.targettableglfield = targettableglfield;
	}
	public String getTargetTabIsGLTab() {
		return targetTabIsGLTab;
	}
	public void setTargetTabIsGLTab(String targetTabIsGLTab) {
		this.targetTabIsGLTab = targetTabIsGLTab;
	}
	public String getDefalutValue() {
		return defalutValue;
	}
	public void setDefalutValue(String defalutValue) {
		this.defalutValue = defalutValue;
	}
	public String getTableRelSql() {
		if(null==this.tableRelSql||"".equals(this.tableRelSql)){
			StringBuffer sql = new StringBuffer("insert into TABLE_GL_REL_SYNC (systemid, sontablename, sontableglfield, targettablename, targettableglfield, targettabledefault, targettableservice, reservation01)");
			sql.append("VALUES (getid(''),'"+this.getSonTab()+"','"+this.getSontableglfield()+"','"+this.getTargetTab()+"','"+this.getTargettableglfield()+"'");
			if(null==this.getDefalutValue()||"".equals(this.getDefalutValue())){
				sql.append(",null");
			}else{
				sql.append(",'"+this.getDefalutValue()+"'");
			}
			sql.append(",'--'");//服务类
			if(null==this.getTargetTabIsGLTab()||"".equals(this.getTargetTabIsGLTab())){
				sql.append(",null);");
			}else{
				sql.append(",'"+this.getTargetTabIsGLTab()+"');");
			}
			return sql.toString();
		}
		return tableRelSql;
	}
	public void setTableRelSql(String tableRelSql) {
		this.tableRelSql = tableRelSql;
	}
	public List<String> getFieldRelSqlList() {
		if(null==fieldRelSqlList||fieldRelSqlList.size()==0){
			fieldRelSqlList = new ArrayList<String>();
			for(Entry<String, String> set:this.getSyncMap().entrySet()){
				StringBuffer sql = new StringBuffer("insert into FIELD_GL_TABLE_SYNC (systemid,son_table_name, son_field, gl_table_name, gl_field)");
				sql.append("values (getid(''),'"+this.getSonTab()+"','"+set.getKey()+"','"+this.getTargetTab()+"','"+set.getValue()+"');");
				fieldRelSqlList.add(sql.toString());
			}
		}
		return fieldRelSqlList;
	}
	public void setFieldRelSqlList(List<String> fieldRelSqlList) {
		this.fieldRelSqlList = fieldRelSqlList;
	}
	
	
}
