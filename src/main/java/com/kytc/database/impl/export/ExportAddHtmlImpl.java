package com.kytc.database.impl.export;

import org.springframework.stereotype.Service;

import com.kytc.database.dto.ColumnDTO;
@Service("exportAddHtmlImpl")
public class ExportAddHtmlImpl extends AbstractExportService {

	@Override
	public StringBuffer toStr() {
		// TODO Auto-generated method stub
		this.fileName = "add.jsp";
		StringBuffer detailHtml = new StringBuffer("");
		detailHtml.append("<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\""+
              tab(1)+"pageEncoding=\"UTF-8\"%>"+
              tab(0)+"<%@taglib uri=\"http://java.sun.com/jsp/jstl/core\" prefix=\"c\"%>"+
              tab(0)+"<%@taglib uri=\"http://java.sun.com/jsp/jstl/fmt\" prefix=\"fmt\"%>"+ 
              tab(0)+"<script type=\"text/javascript\" src=\"${pageContext.request.contextPath}/js/"+javaTableName1+"/add.js\"></script>"+
              tab(0)+"<div id=\"cms_"+htmlName+"_add_main_div\" style=\"width:100%;height:100%;\">");
		int size = 4;
		String width1 = " style=\"width:15%;\"";
		String width2 = " style=\"width:35%;\"";
		if(page.getRows().size()>7){
			size = 4;
			detailHtml.append(tab(1)+"<table class=\"d_table\">");
		}else{
			size = 2;
			width1 = " style=\"width:30%;\"";
			width2 = " style=\"width:70%;\"";
			detailHtml.append(tab(1)+"<table class=\"s_table\">");
		}
		int i = 0;
		int j = 0;
		for(ColumnDTO dto:page.getRows()){
			if("auto_increment".equals(dto.getExtra())){
				j++;
				continue;
			}
			if(dto.getColumnName().equals("operator")||dto.getColumnName().equals("is_deleted")||dto.getColumnName().equals("gmt_create")||dto.getColumnName().equals("gmt_modified")){
				j++;
				continue;
			}
			if(i%size==0){
				detailHtml.append(tab(2)+"<tr>");
			}
			detailHtml.append(tab(3)+"<td"+((i<size)?width1:"")+">"+dto.getShowComment()+":</td>");
			if(size==4&&(page.getRows().size()-j)%2==1&&(i+(j*2))==page.getRows().size()*2-2){
				detailHtml.append(tab(3)+"<td"+((i<size)?width2+" colspan=\"3\"":" colspan=\"3\"")+">");
			}else{
				detailHtml.append(tab(3)+"<td"+((i<size)?width2:"")+">");
			}
			if(dto.getDataType().equals("date")){
				detailHtml.append(tab(4)+"<input class=\"easyui-datebox\" name=\""+dto.getJavaName()+"\"/>"+tab(3)+"</td>");
			}else if(dto.getDataType().equals("datetime")||dto.getDataType().equals("timestamp")){
				detailHtml.append(tab(4)+"<input class=\"easyui-datetimebox\" name=\""+dto.getJavaName()+"\"/>"+tab(3)+"</td>");
			}else if(dto.getColumnName().equals("sort_num")){
				detailHtml.append(tab(4)+"<input class=\"easyui-numberbox\" name=\"sortNum\" value=\"1\" data-options=\"min:1,required:true\">"+tab(3)+"</td>");
			}else{
				detailHtml.append(tab(4)+"<input name=\""+dto.getJavaName()+"\" class=\"easyui-textbox\"/>"+tab(3)+"</td>");
			}
			i+=2;
			if(i%size==0||i+(j*2)==page.getRows().size()*2){
				detailHtml.append(tab(2)+"</tr>");
			}
		}
		detailHtml.append(tab(2)+"<tr>"+
				tab(3)+"<td colspan=\""+size+"\">"+
				tab(4)+"<div class=\"btn_div\">"+
				tab(5)+"<a name=\"save\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-save'\">保存</a>"+
				tab(5)+"<a name=\"close\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-close'\">关闭</a>"+
				tab(4)+"</div>"+
				tab(3)+"</td>"+
				tab(2)+"</tr>"+tab(1)+"</table>");
		detailHtml.append(tab(0)+"</div>");
		return detailHtml;
	}

}
