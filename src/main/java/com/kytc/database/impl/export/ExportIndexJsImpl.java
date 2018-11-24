package com.kytc.database.impl.export;

import org.springframework.stereotype.Service;

import com.kytc.database.dto.ColumnDTO;
@Service("exportIndexJsImpl")
public class ExportIndexJsImpl extends AbstractExportService {

	@Override
	public StringBuffer toStr() {
		// TODO Auto-generated method stub
		this.fileName = "index.js";
		StringBuffer indexJs = new StringBuffer("");
		indexJs.append("$(function(){")
			.append(tab(1)+"var mainDiv = $(\"#cms_"+htmlName+"_main_div\");"
					+tab(1)+ "var mainDivList =  $(\"div.list div.data\",mainDiv);")
			.append(tab(1)+"var rootPath = \"/"+javaTableName1+"/\";")
			.append(tab(1)+"mainDiv.height($(window).height()-49);")
			.append(tab(1)+"mainDiv.off().on(\"click\",\"a[name='add']\",function(){")
			.append(tab(2)+"$.EasyUI.Window({"+
					tab(3)+"url:$.cms.url+rootPath+\"add\","+
					tab(3)+"type:\"get\","+
					tab(3)+"width:1000,"+
					tab(3)+"height:\"auto\","+
					tab(3)+"title:\"添加\""+
					tab(2)+"});"+
					tab(1)+"})")
			.append(".on(\"click\",\"a[name='update']\",function(){")
			.append(tab(2)+"$.datagrid.getSelectRow({"+
					tab(3)+"gridId:mainDivList,"+
					tab(3)+"field:\""+id+"\","+
					tab(3)+"success:function(value,row){"+
					tab(4)+"$.EasyUI.Window({"+
					tab(5)+"url:$.cms.url+rootPath+\"update\","+
					tab(5)+"type:\"get\","+
					tab(5)+"data:{\""+id+"\":value},"+
					tab(5)+"width:1000,"+
					tab(5)+"height:\"auto\","+
					tab(5)+"title:\"修改\""+
					tab(4)+"});"+
					tab(3)+"}"+
					tab(2)+"})"+
					tab(1)+"})")
		.append(".on(\"click\",\"a[name='delete']\",function(){")
		.append(tab(2)+"$.datagrid.getSelectRow({"+
				tab(3)+"gridId:mainDivList,"+
				tab(3)+"field:\""+id+"\","+
				tab(3)+"success:function(value,row){"+
				tab(4)+"$.EasyUI.message(\"确定要删除该条数据吗?\",'cf',function(){"+
				tab(5)+"$.ajax({"+
				tab(6)+"url:$.cms.url+rootPath+\"delete\","+
				tab(6)+"data:{\""+id+"\":value},"+
				tab(6)+"type:\"post\","+
				tab(6)+"dataType:\"json\","+
				tab(6)+"success:function(data){"+
				tab(7)+"if(data.status){"+
				tab(8)+"mainDivList.datagrid('reload',$(\"form[name='search_form']\",mainDiv).toJSON());"+
				tab(7)+"}else{"+
				tab(8)+"$.EasyUI.message(data.reason,\"e\");"+
				tab(7)+"}"+
				tab(6)+"}"+
				tab(5)+"})"+
				tab(4)+"})"+
				tab(3)+"}"+
				tab(2)+"})"+
				tab(1)+"})")
		.append(".on(\"click\",\"a[name='search']\",function(){")
		.append(tab(2)+"mainDivList.datagrid('reload',$(\"form[name='search_form']\",mainDiv).toJSON());"+
				tab(1)+"})")
		.append(".on(\"click\",\"a[name='reset']\",function(){")
		.append(tab(2)+"$(\"form[name='search_form']\",mainDiv).form('clear');")
		.append(tab(2)+"mainDivList.datagrid('reload',$(\"form[name='search_form']\",mainDiv).toJSON());"+
				tab(1)+"});")
		.append(tab(1)+"initGrid();")
		.append(tab(1)+"function initGrid(){"+
				tab(2)+"$(\"div.list\",mainDiv).height(mainDiv.height()-"+
				tab(3)+"$(\"form[name='search_form']\",mainDiv).height()-$(\"div.btn_div\",mainDiv).height()-10);"+
				tab(2)+"var jsonData=$(\"form[name='search_form']\",mainDiv).toJSON();"+
				tab(2)+"$.EasyUI.DataGrid({"+
				tab(3)+"gridId:mainDivList,"+
				tab(3)+"url: $.cms.url+rootPath+\"list\","+
				tab(3)+"queryParams: jsonData,//关键之处"+
				tab(3)+"fitColumns: true,"+
				tab(3)+"collapsible:false,"+
				tab(3)+"nowrap: true,"+
				tab(3)+"singleSelect: true,"+
				tab(3)+"pagination: true,"+
				tab(3)+"remoteSort: true,"+
				tab(3)+"border: true,"+
				tab(3)+"rownumbers: false,"+
				tab(3)+"rowStyler:function(){"+
				tab(4)+"return \"height:35px\";"+
				tab(3)+"},onDblClickRow :function(rowIndex,rowData){"+
				tab(4)+"$.EasyUI.Window({"+
				tab(5)+"url:$.cms.url+rootPath+\"detail?"+id+"=\"+rowData."+id+","+
				tab(5)+"type:\"get\","+
				tab(5)+"width:950,"+
				tab(5)+"height:\"auto\","+
				tab(5)+"title:\"查询详情\""+
				tab(4)+"});"+
				tab(3)+"},")
			.append(tab(3)+"columns: [");
		StringBuffer sb = new StringBuffer("[{"+tab(4)+"field: 'CK',"+tab(4)+"title: '',"+tab(4)+"checkbox: true,"+tab(4)+"width: 30"+tab(3)+"}");
		for(ColumnDTO dto:page.getRows()){
			sb.append(",{"+tab(4)+"field: \""+dto.getColumnName()+"\","+tab(4)+"title: \""+
					dto.getShowComment()+
					"\","+tab(4)+"width: 100,"+tab(4)+"align:  \"center\","+tab(4)+"sortable: true");
			if(!dto.getColumnName().toLowerCase().equals(dto.getJavaName().toLowerCase())){
				sb.append(","+tab(4)+"formatter:function(value,row){"+tab(5));
				if(dto.getDataType().equals("date")){
					sb.append("return $.cms.toDate(row."+dto.getJavaName()+");");
				}else if(dto.getDataType().equals("datetime")){
					sb.append("return $.cms.toDateTime(row."+dto.getJavaName()+");");
				}else if(dto.getColumnName().trim().toLowerCase().startsWith("is_")){
					sb.append("return row."+dto.getJavaName()+"==1?\"是\":\"否\";");
				}else{
					sb.append("return row."+dto.getJavaName()+";");
				}
				sb.append(tab(4)+"}");
			}else{
				if(dto.getDataType().equals("date")){
					sb.append(","+tab(4)+"formatter:function(value,row){"+tab(5));
					sb.append("return $.cms.toDate(row."+dto.getJavaName()+");");
					sb.append(tab(4)+"}");
				}else if(dto.getDataType().equals("datetime")){
					sb.append(","+tab(4)+"formatter:function(value,row){"+tab(5));
					sb.append("return $.cms.toDateTime(row."+dto.getJavaName()+");");
					sb.append(tab(4)+"}");
				}
			}
			sb.append(tab(3)+"}");
		}
		sb.append("]]");
		indexJs.append(sb);
		indexJs.append(tab(2)+"});"+tab(1)+"}"+tab(0)+"})");
		return indexJs;
	}

}
