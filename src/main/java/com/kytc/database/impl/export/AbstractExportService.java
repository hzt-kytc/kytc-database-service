package com.kytc.database.impl.export;

import java.io.File;

import com.kytc.database.dto.ColumnDTO;
import com.kytc.database.service.export.ExportService;
import com.kytc.dto.PageDTO;
import com.kytc.utils.common.TxtUtils;

public abstract class AbstractExportService implements ExportService{
	protected String javaTableName = "";
	protected String javaTableName1 = "";
	protected PageDTO<ColumnDTO> page = null;
	protected String tableName = null;
	protected String packageName = null;
	protected Boolean deletedFlag = true;
	protected Boolean containsOperator = false;
	protected Boolean containsModified = false;
	protected String htmlName = "";
	private String path = "D://database//";
	protected String fileName = "";
	protected static final String PRIVATE = "private";
	protected static final String PUBLIC = "public";
	protected static final String IS_DELETED = "is_deleted";
	protected static final String GMT_CREATE = "gmt_create";
	protected static final String GMT_MODIFIED = "gmt_modified";
	protected static final String OPERATOR = "operator";
	private void tableNameToJavaName(String tableName){
		String[] names = tableName.toLowerCase().split("_");
		for(String name:names){
			if(!name.equals("tb")){
				javaTableName += (""+name.charAt(0)).toUpperCase()+name.substring(1);
			}
		}
	}
	private String columnNameToJavaName(String columnName){
		String[] names = columnName.toLowerCase().split("_");
		String javaName = "";
		for(String name:names){
			if(!name.equals("is")){
				javaName += (""+name.charAt(0)).toUpperCase()+name.substring(1);
			}
		}
		javaName = (""+javaName.charAt(0)).toLowerCase()+javaName.substring(1);
		return javaName;
	}
	protected String id = "";
	private String getShowName(ColumnDTO dto){
		String comment = dto.getColumnComment();
		comment = comment.replace(",", " ");
		comment = comment.replace("，", " ");
		if(comment==null||comment.trim().equals("")){
			return dto.getColumnName();
		}
		comment = comment.trim();
		if(comment.contains(" ")){
			return comment.split(" ")[0];
		}
		return comment;
	}
	private void init(String tableName) {
		tableNameToJavaName(tableName);
		if(tableName.trim().toLowerCase().startsWith("tb_")){
			htmlName = tableName.trim().toLowerCase().replace("tb_", "");
		} else {
			htmlName = tableName;
		}
		if(page!=null&&page.getRows()!=null&&page.getRows().size()>0){
			for(ColumnDTO dto:page.getRows()){
				dto.setJavaName(columnNameToJavaName(dto.getColumnName()));
				if(dto.getColumnName().toLowerCase().trim().equals(IS_DELETED)){
					deletedFlag = false;
				}
				if(dto.getColumnName().toLowerCase().trim().equals(OPERATOR)){
					containsOperator = true;
				}
				if(dto.getColumnName().toLowerCase().trim().equals(GMT_MODIFIED)){
					containsModified = true;
				}
				if(dto.getColumnKey().toLowerCase().trim().equals("pri")){
					id = dto.getColumnName().trim().toLowerCase();
				}
				dto.setShowComment(getShowName(dto));
			}
		}
		javaTableName1 = (javaTableName.charAt(0)+"").toLowerCase()+javaTableName.substring(1);
	}
	protected String blanks(int length){
		StringBuffer sb = new StringBuffer("");
		for(int i=0;i<length;i++){
			sb.append(" ");
		}
		return sb.toString();
	}
	protected String tab(int size){
		StringBuffer sb = new StringBuffer("\n");
		for(int i=0;i<size;i++){
			sb.append("\t");
		}
		return sb.toString();
	}
	protected String sqlTypeToJavaType(String name,String sqlType){
		sqlType = sqlType.toLowerCase();
		if(name.trim().toLowerCase().startsWith("is_")){
			return "Boolean";
		}
		String javaType = "";
		switch(sqlType){
			case "int":
			case "tinyint":
			case "smallint":
			case "mediumint":
			case "integer":
				javaType="Integer";
				break;
			case "bigint":
			case "timestamp":
				javaType="Long";
				break;
			case "datetime":
			case "date":
				javaType="Date";
				break;
			case "varchar":
			case "char":
			case "text":
			case "tinytext":
			case "mediumtext":
			case "longtext":
				javaType="String";
				break;
			case "bit":
			case "boolean":
				javaType="Boolean";
				break;
			case "double":
				javaType="Double";
				break;
			case "float":
				javaType="Float";
				break;
			case "decimal":
				javaType="Decimal";
				break;
			case "blob":
				javaType="Byte[]";
				break;
			case "time":
				javaType="Time";
				break;
			default:
				javaType="String";
				break;
		}
		return javaType;
	}
	public abstract StringBuffer toStr();
	@Override
	public void export( PageDTO<ColumnDTO> page, String tableName, String packageName ) {
		this.page = page;
		this.tableName = tableName;
		this.packageName = packageName;
		init(tableName);
		String path1 = path + javaTableName + "//";
		File file = new File(path1);
		if(!file.isDirectory()) {
			file.mkdir();
		}
		write(toStr(), path1 + fileName);
	}
	private void write(StringBuffer sb,String file) {
		TxtUtils.write(sb, file);
	}
}
