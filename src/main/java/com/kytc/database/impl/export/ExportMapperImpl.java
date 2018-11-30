package com.kytc.database.impl.export;

import org.springframework.stereotype.Service;

import com.kytc.database.dto.ColumnDTO;
@Service("exportMapperImpl")
public class ExportMapperImpl extends AbstractExportService {

	@Override
	public StringBuffer toStr() {
		// TODO Auto-generated method stub
		this.fileName = javaTableName+"Mapper.xml";
		if(page!=null&&page.getRows()!=null&&page.getRows().size()>0){
			StringBuffer mapperSb = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
					"<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \n\"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
			mapperSb.append("<mapper namespace=\""+this.packageName+".dao."+javaTableName+"Dao\">");
			mapperSb.append(tab(1)+"<resultMap id=\""+javaTableName1+"PO\" type=\""+this.packageName+".po."+javaTableName+"PO\">");
			for(ColumnDTO dto:page.getRows()){
				if(dto.getExtra().toLowerCase().trim().equals("auto_increment")){
					mapperSb.append(tab(2)+"<id column=\""+dto.getColumnName()+"\" property=\""+dto.getJavaName()+"\" />");
				}else{
					mapperSb.append(tab(2)+"<result column=\""+dto.getColumnName()+"\" property=\""+dto.getJavaName()+"\" />");
				}
			}
			mapperSb.append(tab(1)+"</resultMap>"+tab(1)+"<insert id=\"add\" parameterType=\""+this.packageName+".po."+javaTableName+"PO\" useGeneratedKeys=\"true\" keyProperty=\"id\">");
			mapperSb.append(tab(2)+"insert"+tab(3)+"into"+tab(4)+tableName+"(");
			for(ColumnDTO dto:page.getRows()){
				if(!dto.getExtra().toLowerCase().trim().equals("auto_increment")){
					mapperSb.append(tab(5)+dto.getColumnName()+",");
				}
			}
			
			mapperSb.deleteCharAt(mapperSb.length()-1);
			mapperSb.append(tab(4)+")values(");
			for(ColumnDTO dto:page.getRows()){
				if(!dto.getExtra().toLowerCase().trim().equals("auto_increment")){
					if(dto.getColumnName().trim().toLowerCase().equals("is_deleted")){
						mapperSb.append(tab(5)+"0,");
					}else if(dto.getDataType().trim().toLowerCase().equals("datetime")){
						mapperSb.append(tab(5)+"CURRENT_TIMESTAMP(),");
					}else if(dto.getDataType().trim().toLowerCase().equals("date")){
						mapperSb.append(tab(5)+"CURRENT_DATE(),");
					}else{
						mapperSb.append(tab(5)+"#{"+dto.getJavaName()+"},");
					}
				}
			}
			mapperSb.deleteCharAt(mapperSb.length()-1);
			mapperSb.append(tab(4)+");"+tab(1)+"</insert>");
			
			
			mapperSb.append(tab(1)+"<update id=\"update\" parameterType=\""+this.packageName+".po."+javaTableName+"PO\">");
			mapperSb.append(tab(2)+"update "+tab(3)+tableName+tab(2)+"set");
			for(ColumnDTO dto:page.getRows()){
				if(!dto.getColumnKey().trim().toLowerCase().equals("pri")){
					if(dto.getColumnName().toLowerCase().trim().equals("gmt_modified")){
						mapperSb.append(tab(3)+dto.getColumnName()+" = CURRENT_TIMESTAMP(),");
					}else if(dto.getColumnName().toLowerCase().trim().equals("gmt_create")){
					}else if(dto.getDataType().trim().toLowerCase().equals("datetime")){
						mapperSb.append(tab(3)+dto.getColumnName()+" = CURRENT_TIMESTAMP(),");
					}else if(dto.getDataType().trim().toLowerCase().equals("date")){
						mapperSb.append(tab(3)+dto.getColumnName()+" = CURRENT_DATE(),");
					}else{
						mapperSb.append(tab(3)+dto.getColumnName()+" = #{"+dto.getJavaName()+"},");
					}
				}
			}
			mapperSb.deleteCharAt(mapperSb.length()-1);
			mapperSb.append(tab(2)+"where "+tab(3)+id+" = #{"+id+"}"+tab(1)+"</update>");
			//查询详情
			mapperSb.append(tab(1)+"<select id=\"detail\" parameterType=\"Integer\" resultMap=\""+javaTableName1+"PO\">");
			mapperSb.append(tab(2)+"select "+tab(3)+"*"+tab(2)+"from"+tab(3)+tableName+tab(2)+"where"+tab(3)+id+" = #{"+id+"} " + tab(2) +"limit 1;");
			mapperSb.append(tab(1)+"</select>");
			//删除
			if(deletedFlag){
				mapperSb.append(tab(1)+"<delete id=\"delete\" parameterType=\"Integer\" >");
				mapperSb.append(tab(2)+"delete " + tab(3) + "from " + tab(4) + tableName+tab(2)+"where "+tab(3)+id+" = #{id};");
				mapperSb.append(tab(1)+"</delete>");
			}else{
				mapperSb.append(tab(1)+"<update id=\"delete\" parameterType=\""+this.packageName+".po."+javaTableName+"PO\">");
				mapperSb.append(tab(2)+"update " + tab(3) + tableName+tab(2)+"set");
				mapperSb.append(tab(3)+"is_deleted = 1 ");
				if(containsOperator){
					mapperSb.append(","+tab(3)+"operator = #{operator} ");
				}
				if(containsModified){
					mapperSb.append(","+tab(3)+"gmt_modified = CURRENT_TIMESTAMP() ");
				}
				mapperSb.append(tab(2)+"where "+tab(3)+id+" = #{id}"+tab(1)+"</update>");
			}
			mapperSb.append(tab(1)+"<sql id=\"listByCondition\">"+tab(2)+"where"+tab(3)+"1 = 1");
			for(ColumnDTO dto:page.getRows()){
				if(!dto.getColumnName().trim().equals("gmt_create")&&!dto.getColumnName().trim().equals("gmt_modified")){
					mapperSb.append(tab(3)+"<if test=\""+dto.getJavaName()+"!=null and "+dto.getJavaName()+"!=''\">");
					mapperSb.append(tab(4)+"and "+dto.getColumnName()+" = #{"+dto.getJavaName()+"}");
					mapperSb.append(tab(3)+"</if>");
				}
			}
			mapperSb.append(tab(1)+"</sql>");
			mapperSb.append(tab(1)+"<select id=\"list\" parameterType=\""+this.packageName+".vo."+javaTableName+"VO\" resultMap=\""+javaTableName1+"PO\">");
			mapperSb.append(tab(2)+"select "+tab(3)+"*"+tab(2)+"from"+tab(3)+tableName);
			mapperSb.append(tab(3)+"<include refid=\"listByCondition\"/> ");
			
			mapperSb.append(tab(3)+"<if test=\"sort!=null and sort!=''\">");
			mapperSb.append(tab(4)+"order by ${sort} ${order} ");
			mapperSb.append(tab(3)+"</if>");
			mapperSb.append(tab(3)+"<if test=\"sort==null or sort==''\">");
			if(containsModified){
				mapperSb.append(tab(4)+"order by gmt_modified desc ");
			}else{
				mapperSb.append(tab(4)+"order by "+id+" desc ");
			}
			mapperSb.append(tab(3)+"</if>");
			mapperSb.append(tab(3)+"limit #{start},#{pageSize};");
			mapperSb.append(tab(1)+"</select>");
			mapperSb.append(tab(1)+"<select id=\"count\" parameterType=\""+this.packageName+".vo."+javaTableName+"VO\" resultType=\"Long\">");
			mapperSb.append(tab(2)+"select "+tab(3)+"count(1)"+tab(2)+"from"+tab(3)+tableName);
			mapperSb.append(tab(3)+"<include refid=\"listByCondition\"/>");
			mapperSb.append(tab(1)+"</select>\n");
			mapperSb.append("</mapper>");
			return mapperSb;
		}
		return null;
	}

}
