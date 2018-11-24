package com.kytc.database.impl.export;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kytc.database.dto.ColumnDTO;
@Service("exportIndexHtmlImpl")
public class ExportIndexHtmlImpl extends AbstractExportService {

	@Override
	public StringBuffer toStr() {
		// TODO Auto-generated method stub
		this.fileName = "index.jsp";
		StringBuffer indexHtml = new StringBuffer("");
		indexHtml.append("<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\""+
				tab(1)+"pageEncoding=\"UTF-8\"%>"+
				tab(0)+"<script type=\"text/javascript\" src=\"${pageContext.request.contextPath}/js/"+javaTableName1+"/index.js\"></script>");
		indexHtml.append(tab(0)+"<div id=\"cms_"+htmlName+"_main_div\" style=\"width: 100%; height: 468px;\">")
			.append(tab(1)+"<form name=\"search_form\" class=\"search_form\">")
			.append(tab(2)+"<table>"+
					tab(3)+"<tbody>"+
					tab(4)+"<tr>");
		StringBuffer searchSb = new StringBuffer("");
		searchSb.append(tab(6)+"<a name=\"search\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-search'\" style=\"width:100px;height:32px;\">搜 索</a>")
			.append(tab(6)+"<a name=\"reset\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-reload'\" style=\"width:100px;height:32px;\">重置</a>");
		List<StringBuffer> list = new ArrayList<StringBuffer>();
		for(ColumnDTO dto:page.getRows()){
			if(!dto.getColumnName().toLowerCase().trim().equals("gmt_create")&&
					!dto.getColumnName().toLowerCase().trim().equals("gmt_modified")&&
					!dto.getColumnName().toLowerCase().trim().equals("is_deleted")&&
					!dto.getColumnName().toLowerCase().trim().equals("sort_num")){
				StringBuffer sb = new StringBuffer("");
				sb.append(tab(5)+"<td>"+dto.getShowComment()+":</td>")
					.append(tab(5)+"<td><input class=\"easyui-textbox\" name=\""+dto.getJavaName()+"\" /></td>");
				list.add(sb);
			}
		}
		int size = list.size();
		if(size<3){
			StringBuffer sb = new StringBuffer("");
			sb.append(tab(5)+"<td colspan=\""+(6-list.size()*2)+"\" align=\"center\">")
				.append(searchSb);
			list.add(sb);
		}else if(size<6){
			StringBuffer sb = new StringBuffer("");
			sb.append(tab(5)+"<td colspan=\""+(12-list.size()*2)+"\" align=\"center\">")
				.append(searchSb);
			list.add(sb);
			
			StringBuffer sb1 = new StringBuffer("");
			sb1.append(tab(4)+"</tr>")
				.append(tab(4)+"<tr>");
			list.add(3,sb);
		}else{
			for(int i=0;i<size/4;i++){
				StringBuffer sb1 = new StringBuffer("");
				sb1.append(tab(4)+"</tr>")
					.append(tab(4)+"<tr>");
				list.add(5*i+4,sb1);
			}
			StringBuffer sb = new StringBuffer("");
			sb.append(tab(5)+"<td colspan=\""+((size/4+1)*8-size*2)+"\" align=\"center\">")
				.append(searchSb);
			list.add(sb);
		}
		StringBuffer sb1 = new StringBuffer("");
		sb1.append(tab(5)+"</td>"+tab(4)+"</tr>"+tab(3)+"</tbody>"+tab(2)+"</table>"+tab(1)+"</form>");
		list.add(sb1);
		
		StringBuffer operateSb = new StringBuffer("");
		operateSb.append(tab(1)+"<div class=\"btn_div\" style=\"width:100%;height:40px;margin-top:10px;margin-bottom:10px;\">")
			.append(tab(2)+"<a name=\"add\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-add'\" style=\"width:100px\">添加</a>")
			.append(tab(2)+"&nbsp;&nbsp;&nbsp;&nbsp;")
			.append(tab(2)+"<a name=\"update\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-edit'\" style=\"width:100px\">修改</a>")
			.append(tab(2)+"&nbsp;&nbsp;&nbsp;&nbsp;")
			.append(tab(2)+"<a name=\"delete\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-delete'\" style=\"width:100px\">删除</a>")
			.append(tab(1)+"</div>");
		
		StringBuffer listSb = new StringBuffer("");
		listSb.append(tab(1)+"<div class=\"list\" style=\"overflow:auto;\">")
			.append(tab(2)+"<div class=\"data\" style=\"width:100%;\"></div>")
			.append(tab(1)+"</div>").append(tab(0)+"</div>");
		list.add(operateSb);
		list.add(listSb);
		for(StringBuffer sb:list){
			indexHtml.append(sb);
		}
		return indexHtml;
	}

}
