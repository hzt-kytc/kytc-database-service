package com.kytc.database.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.kytc.database.dto.ColumnDTO;
import com.kytc.dto.PageDTO;
import com.kytc.utils.date.DateStyle;
import com.kytc.utils.date.DateUtil;

public class DatabaseUtils {
	private DatabaseUtils(){};
	public static void export(PageDTO<ColumnDTO> page,String tableName){
		init(page,tableName);
//		StringBuffer controllerSb = toController(page,tableName);
//		TxtUtils.write(controllerSb, "D://"+javaTableName+"Controller.java");
//		
//		controllerSb = toService(page,tableName);
//		TxtUtils.write(controllerSb, "D://"+javaTableName+"Service.java");
//		
//		controllerSb = toImpl(page,tableName);
//		TxtUtils.write(controllerSb, "D://"+javaTableName+"ServiceImpl.java");
//		
//		controllerSb = toDAO(page,tableName);
//		TxtUtils.write(controllerSb, "D://"+javaTableName+"Dao.java");
//		
//		controllerSb = toMapper(page,tableName);
//		TxtUtils.write(controllerSb, "D://"+javaTableName+"Mapper.xml");
//		
//		controllerSb = toPO(page,tableName);
//		TxtUtils.write(controllerSb, "D://"+javaTableName+"PO.java");
//		
//		controllerSb = toVO(page,tableName);
//		TxtUtils.write(controllerSb, "D://"+javaTableName+"VO.java");
		toIndexJs(page,tableName);
	}
	private static String blanks(int length){
		StringBuffer sb = new StringBuffer("");
		for(int i=0;i<length;i++){
			sb.append(" ");
		}
		return sb.toString();
	}
	private static Boolean deletedFlag = true;
	private static Boolean containsOperator = false;
	private static Boolean containsModified = false;
	private static String javaTableName = "";
	private static String htmlName = "";
	private static String javaTableName1 = "";
	private static String id = "";
	private static void init(PageDTO<ColumnDTO> page,String tableName){
		javaTableName = tableNameToJavaName(tableName);
		if(tableName.trim().toLowerCase().startsWith("tb_")){
			htmlName = tableName.trim().toLowerCase().replace("tb_", "");
		}
		javaTableName1 = (javaTableName.charAt(0)+"").toLowerCase()+javaTableName.substring(1);
		if(page!=null&&page.getRows()!=null&&page.getRows().size()>0){
			for(ColumnDTO dto:page.getRows()){
				dto.setJavaName(columnNameToJavaName(dto.getColumnName()));
				if(dto.getColumnName().toLowerCase().trim().equals("is_deleted")){
					deletedFlag = false;
				}
				if(dto.getColumnName().toLowerCase().trim().equals("operator")){
					containsOperator = true;
				}
				if(dto.getColumnName().toLowerCase().trim().equals("gmt_modified")){
					containsModified = true;
				}
				if(dto.getColumnKey().toLowerCase().trim().equals("pri")){
					id = dto.getColumnName().trim().toLowerCase();
				}
			}
		}
	}
	public static StringBuffer toIndexJs(PageDTO<ColumnDTO> page,String tableName){
		StringBuffer indexJs = new StringBuffer("");
		indexJs.append("$(function(){\n")
			.append("\tvar mainDiv = $(\"#cms_"+htmlName+"_main_div\");\n"
					+ "\tvar mainDivList =  $(\"div.list div.data\",mainDiv);\n")
			.append("\tvar rootPath = \"/"+javaTableName1+"/\";\n")
			.append("\tmainDiv.height($(window).height()-49);\n")
			.append("\tmainDiv.off().on(\"click\",\"a[name='add']\",function(){\n")
			.append("\t\t$.EasyUI.Window({\n"+
					"\t\t\turl:$.cms.url+rootPath+\"add\",\n"+
					"\t\t\ttype:\"get\",\n"+
					"\t\t\twidth:1000,\n"+
					"\t\t\theight:800,\n"+
					"\t\t\ttitle:\"添加\"\n\t\t});\n\t})")
			.append(".on(\"click\",\"a[name='update']\",function(){\n")
			.append("\t\t$.datagrid.getSelectRow({\n"+
					"\t\t\tgridId:mainDivList,\n"+
					"\t\t\tfield:\""+id+"\",\n"+
					"\t\t\tsuccess:function(value,row){\n"+
					"\t\t\t\t$.ajax({\n"+
					"\t\t\t\t\turl:$.cms.url+rootPath+\"update\",\n"+
					"\t\t\t\t\tdata:{\""+id+"\":value},\n"+
					"\t\t\t\t\ttype:\"post\",\n"+
					"\t\t\t\t\tdataType:\"json\",\n"+
					"\t\t\t\t\tsuccess:function(data){\n"+
					"\t\t\t\t\t\tif(data.status){\n"+
					"\t\t\t\t\t\t\tmainDivList.datagrid('reload',$(\"form[name='search_form']\",main_div).toJSON());\n"+
					"\t\t\t\t\t\t}else{\n"+
					"\t\t\t\t\t\t\t$.EasyUI.message(data.reason,\"e\");\n"+
					"\t\t\t\t\t\t}\n\t\t\t\t\t}\n\t\t\t\t})\n\t\t\t}\n\t\t});\n\t})")
		.append(".on(\"click\",\"a[name='delete']\",function(){\n")
		.append("\t\t$.datagrid.getSelectRow({\n"+
				"\t\t\tgridId:mainDivList,\n"+
				"\t\t\tfield:\""+id+"\",\n"+
				"\t\t\tsuccess:function(value,row){\n"+
				"\t\t\t\t$.EasyUI.message(\"确定要删除该条数据吗?\",'cf',function(){\n"+
				"\t\t\t\t\t$.ajax({\n"+
				"\t\t\t\t\t\turl:$.cms.url+rootPath+\"delete\",\n"+
				"\t\t\t\t\t\tdata:{\""+id+"\":value},\n"+
				"\t\t\t\t\t\ttype:\"post\",\n"+
				"\t\t\t\t\t\tdataType:\"json\",\n"+
				"\t\t\t\t\t\tsuccess:function(data){\n"+
				"\t\t\t\t\t\t\tif(data.status){\n"+
				"\t\t\t\t\t\t\t\tmainDivList.datagrid('reload',$(\"form[name='search_form']\",main_div).toJSON());\n"+
				"\t\t\t\t\t\t\t}else{\n"+
				"\t\t\t\t\t\t\t\t$.EasyUI.message(data.reason,\"e\");\n"+
				"\t\t\t\t\t\t\t}\n\t\t\t\t\t\t}\n\t\t\t\t\t})\n\t\t\t\t})\n\t\t\t})\n\t\t})\n\t})")
		.append(".on(\"click\",\"a[name='search']\",function(){\n")
		.append("\t\tmainDivList.datagrid('reload',$(\"form[name='search_form']\",mainDiv).toJSON());\n"+
				"\t})")
		.append(".on(\"click\",\"a[name='reset']\",function(){\n")
		.append("\t\t$(\"form[name='search_form']\",mainDiv).form('clear');\n")
		.append("\t\tmainDivList.datagrid('reload',$(\"form[name='search_form']\",mainDiv).toJSON());\n"+
				"\t});\n")
		.append("\tinitGrid();\n")
		.append("\tfunction initGrid(){\n"+
		        "\t\t$(\"div[name='list']\",mainDiv).height(mainDiv.height()-\n"+
				"\t\t\t$(\"form[name='search_form']\",mainDiv).height()-$(\"div[name='btn_div']\",mainDiv).height()-10);\n"+
		        "\t\tvar jsonData=$(\"form[name='search_form']\",mainDiv).toJSON();\n"+
		        "\t\t$.EasyUI.DataGrid({\n"+
			    "\t\t\tgridId:mainDivList,\n"+
			    "\t\t\turl: $.cms.url+rootPath+\"list\",\n"+
			    "\t\t\tqueryParams: jsonData,//关键之处\n"+
			    "\t\t\tfitColumns: true,\n"+
			    "\t\t\tcollapsible:false,\n"+
			    "\t\t\tnowrap: true,\n"+
			    "\t\t\tsingleSelect: true,\n"+
			    "\t\t\tpagination: true,\n"+
			    "\t\t\tremoteSort: true,\n"+
			    "\t\t\tborder: true,\n"+
			    "\t\t\trownumbers: false,\n"+
			    "\t\t\trowStyler:function(){\n"+
				"\t\t\t\treturn \"height:35px\";\n"+
			    "\t\t\t},onDblClickRow :function(rowIndex,rowData){\n"+
				"\t\t\t\t$.EasyUI.Window({\n"+
				"\t\t\t\t\turl:$.cms.url+rootPath+\"detail?"+id+"=\"+rowData."+id+",\n"+
				"\t\t\t\t\ttype:\"get\",\n"+
				"\t\t\t\t\twidth:950,\n"+
				"\t\t\t\t\theight:800,\n"+
				"\t\t\t\t\ttitle:\"修改\"\n"+
				"\t\t\t\t});\n"+
			    "\t\t\t},\n")
			.append("\t\t\tcolumns: [");
		StringBuffer sb = new StringBuffer("[{\n\t\t\t\tfield: 'CK',\n\t\t\t\ttitle: '',\n\t\t\t\tcheckbox: true,\n\t\t\t\twidth: 30\n\t\t\t}");
		for(ColumnDTO dto:page.getRows()){
			sb.append(",{\n\t\t\t\tfield: \""+dto.getColumnName()+"\",\n\t\t\t\ttitle: \""+
					(dto.getColumnComment()==null||dto.getColumnComment().trim().equals("")?dto.getJavaName():dto.getColumnComment())+
					"\",\n\t\t\t\twidth: 100,\n\t\t\t\talign:  \"center\",\n\t\t\t\tsortable: true");
			String dataType = sqlTypeToJavaType(dto.getDataType()).trim().toLowerCase();
			if(!dto.getColumnName().toLowerCase().equals(dto.getJavaName().toLowerCase())){
				sb.append(",\n\t\t\t\tformatter:function(value,row){\n\t\t\t\t\t");
				if(dataType.equals("date")){
					sb.append("return $.cms.toDate(row."+dto.getJavaName()+");");
				}else if(dataType.equals("datetime")){
					sb.append("return $.cms.toDateTime(row."+dto.getJavaName()+");");
				}else{
					sb.append("return row."+dto.getJavaName()+";");
				}
			}else{
				if(dataType.equals("date")){
					sb.append(",\n\t\t\t\tformatter:function(value,row){\n\t\t\t\t\t");
					sb.append("return $.cms.toDate(row."+dto.getJavaName()+");");
				}else if(dataType.equals("datetime")){
					sb.append(",\n\t\t\t\tformatter:function(value,row){\n\t\t\t\t\t");
					sb.append("return $.cms.toDateTime(row."+dto.getJavaName()+");");
				}
			}
			sb.append("\n\t\t\t\t}");
			sb.append("\n\t\t\t}");
		}
		sb.append("]]\n");
		indexJs.append(sb);
		indexJs.append("\t\t});\n\t}\n})");
		System.out.println(indexJs.toString());
		return indexJs;
	}
	public static StringBuffer toIndexHtml(PageDTO<ColumnDTO> page,String tableName){
		StringBuffer indexHtml = new StringBuffer("");
		indexHtml.append("<div id=\"cms_"+htmlName+"_main_div\" style=\"width: 100%; height: 468px;\">\n")
			.append("\t<form name=\"search_form\" class=\"search_form\">\n")
			.append("\t\t<table>\n\t\t\t<tbody>\n\t\t\t\t<tr>\n");
		StringBuffer searchSb = new StringBuffer("");
		searchSb.append("\t\t\t\t\t<a name=\"search\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-search'\" style=\"width:100px\">搜 索</a>\n")
			.append("\t\t\t\t\t<a name=\"reset\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-reload'\" style=\"width:100px\">重置</a>");
		List<StringBuffer> list = new ArrayList<StringBuffer>();
		for(ColumnDTO dto:page.getRows()){
			if(!dto.getColumnName().toLowerCase().trim().equals("gmt_create")&&
					!dto.getColumnName().toLowerCase().trim().equals("gmt_modified")){
				StringBuffer sb = new StringBuffer("");
				sb.append("\t\t\t\t\t<td>"+dto.getJavaName()+":</td>\n")
					.append("\t\t\t\t\t<td><input class=\"easyui-textbox\" name=\""+dto.getJavaName()+"\" /></td>\n");
				list.add(sb);
			}
		}
		int size = list.size();
		if(size<3){
			StringBuffer sb = new StringBuffer("");
			sb.append("\t\t\t\t\t<td colspan=\""+(6-list.size()*2)+"\">\n")
				.append(searchSb);
			list.add(sb);
		}else if(size<6){
			StringBuffer sb = new StringBuffer("");
			sb.append("\t\t\t\t\t<td colspan=\""+(12-list.size()*2)+"\">\n")
				.append(searchSb);
			list.add(sb);
			
			StringBuffer sb1 = new StringBuffer("");
			sb1.append("\t\t\t\t</tr>\n")
				.append("\t\t\t\t<tr>\n");
			list.add(3,sb);
		}else{
			for(int i=0;i<size/4;i++){
				StringBuffer sb1 = new StringBuffer("");
				sb1.append("\t\t\t\t</tr>\n")
					.append("\t\t\t\t<tr>\n");
				list.add(4*(i+1),sb1);
			}
			StringBuffer sb = new StringBuffer("");
			sb.append("\t\t\t\t\t<td colspan=\""+((size/4+1)*8-size*2)+"\">\n")
				.append(searchSb);
			list.add(sb);
		}
		StringBuffer sb1 = new StringBuffer("");
		sb1.append("\n\t\t\t\t</tr>\n\t\t\t</tbody>\n\t\t</table>\n\t</form>\n");
		list.add(sb1);
		
		StringBuffer operateSb = new StringBuffer("");
		operateSb.append("\t<div class=\"btn_div\" style=\"width:100%;height:40px;margin-top:10px;margin-bottom:10px;\">\n")
			.append("\t\t<a name=\"add\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-add'\" style=\"width:100px\">添加</a>\n")
			.append("\t\t&nbsp;&nbsp;&nbsp;&nbsp;\n")
			.append("\t\t<a name=\"update\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-edit'\" style=\"width:100px\">修改</a>\n")
			.append("\t\t&nbsp;&nbsp;&nbsp;&nbsp;\n")
			.append("\t\t<a name=\"delete\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-delete'\" style=\"width:100px\">删除</a>\n")
			.append("\t</div>\n");
		
		StringBuffer listSb = new StringBuffer("");
		listSb.append("\t<div class=\"list\" style=\"overflow:auto;\">\n")
			.append("\t\t<div class=\"data\" style=\"width:100%;\"></div>\n")
			.append("\t</div>\n").append("</div>");
		list.add(operateSb);
		list.add(listSb);
		for(StringBuffer sb:list){
			indexHtml.append(sb);
		}
		System.out.println(indexHtml.toString());
		return indexHtml;
	}
	public static StringBuffer toController(PageDTO<ColumnDTO> page,String tableName){
		init(page,tableName);
		if(page!=null&&page.getRows()!=null&&page.getRows().size()>0){
			StringBuffer controllerSb = new StringBuffer();
			controllerSb.append("import javax.annotation.Resource;\n\n");
			controllerSb.append("import org.springframework.stereotype.Controller;\n");
			controllerSb.append("import org.springframework.ui.Model;\n");
			controllerSb.append("import org.springframework.web.bind.annotation.RequestMapping;\n\n");
			controllerSb.append("@Controller(value=\""+javaTableName1+"Controller\")\n");
			controllerSb.append("@RequestMapping(value=\""+javaTableName1+"\")\n");
			controllerSb.append("public class "+javaTableName+"Controller {\n\n");
			controllerSb.append("\t@Resource(name=\""+javaTableName1+"ServiceImpl\")\n");
			controllerSb.append("\tprivate "+javaTableName+"Service "+javaTableName1+"ServiceImpl;\n");
			controllerSb.append("\tprivate String ROOT_PATH = \"/"+javaTableName1+"/\";\n\n");
			controllerSb.append("\t@RequestMapping(value={\"\",\"index\"},method=RequestMethod.GET)\n");
			controllerSb.append("\tpublic String index() {\n");
			controllerSb.append("\t\treturn ROOT_PATH + \"index\";\n");
			controllerSb.append("\t}\n\n");
			controllerSb.append("\t@RequestMapping(value=\"list\")\n");
			controllerSb.append("\tpublic PageDTO<"+javaTableName+"PO> list("+javaTableName+"VO vo){\n");
			controllerSb.append("\t\treturn "+javaTableName1+"ServiceImpl.list(vo);\n");
			controllerSb.append("\t}\n\n");
			
			controllerSb.append("\t@RequestMapping(value=\"add\",method=RequestMethod.GET)\n");
			controllerSb.append("\tpublic String addGet() {\n");
			controllerSb.append("\t\treturn ROOT_PATH + \"add\";\n");
			controllerSb.append("\t}\n\n");
			
			controllerSb.append("\t@RequestMapping(value=\"add\",method=RequestMethod.POST)\n");
			controllerSb.append("\t@ResponseBody\n");
			controllerSb.append("\tpublic ResultDTO<String> addPost("+javaTableName+"PO po) {\n");
			if(containsOperator){
				controllerSb.append("\t\tpo.setOperator(SessionUtils.getUserId());\n");
			}
			controllerSb.append("\t\treturn "+javaTableName1+"ServiceImpl.add(po);\n");
			controllerSb.append("\t}\n\n");
			
			controllerSb.append("\t@RequestMapping(value=\"update\",method=RequestMethod.GET)\n");
			controllerSb.append("\tpublic String updateGet(Integer "+id+",Model model) {\n");
			controllerSb.append("\t\tmodel.addAttribute(\"result\", "+javaTableName1+"ServiceImpl.detail("+id+"));\n");
			controllerSb.append("\t\treturn ROOT_PATH + \"update\";\n");
			controllerSb.append("\t}\n\n");
			
			controllerSb.append("\t@RequestMapping(value=\"update\",method=RequestMethod.POST)\n");
			controllerSb.append("\t@ResponseBody\n");
			controllerSb.append("\tpublic ResultDTO<String> updatePost("+javaTableName+"PO po) {\n");
			if(containsOperator){
				controllerSb.append("\t\tpo.setOperator(SessionUtils.getUserId());\n");
			}
			controllerSb.append("\t\treturn "+javaTableName1+"ServiceImpl.update(po);\n");
			controllerSb.append("\t}\n\n");
			
			controllerSb.append("\t@RequestMapping(value=\"detail\",method=RequestMethod.GET)\n");
			controllerSb.append("\tpublic String detailGet(Integer "+id+",Model model) {\n");
			controllerSb.append("\t\tmodel.addAttribute(\"result\", "+javaTableName1+"ServiceImpl.detail("+id+"));\n");
			controllerSb.append("\t\treturn ROOT_PATH + \"detail\";\n");
			controllerSb.append("\t}\n\n");
			controllerSb.append("\t@RequestMapping(value=\"delete\",method=RequestMethod.POST)\n");
			controllerSb.append("\t@ResponseBody\n");
			if(deletedFlag){
				controllerSb.append("\tpublic ResultDTO<String> delete(Integer "+id+"){\n");
				controllerSb.append("\t\treturn "+javaTableName1+"ServiceImpl.delete("+id+");\n");
			}else{
				controllerSb.append("\tpublic ResultDTO<String> delete("+javaTableName+"PO po){\n");
				if(containsOperator){
					controllerSb.append("\t\tpo.setOperator(SessionUtils.getUserId());\n");
				}
				controllerSb.append("\t\treturn "+javaTableName1+"ServiceImpl.delete(po);\n");
			}
			controllerSb.append("\t}\n");
			controllerSb.append("}");
			return controllerSb;
		}
		return null;
	}
	public static StringBuffer toImpl(PageDTO<ColumnDTO> page,String tableName){
		init(page,tableName);
		if(page!=null&&page.getRows()!=null&&page.getRows().size()>0){
			StringBuffer implSb = new StringBuffer();
			implSb.append("import javax.annotation.Resource;\n\n");
			implSb.append("import org.springframework.stereotype.Component;\n\n");
			implSb.append("@Component\n");
			implSb.append("@Resource(name=\""+javaTableName1+"ServiceImpl\")\n");
			implSb.append("public class "+javaTableName+"ServiceImpl implements "+javaTableName+"Service {\n");
			implSb.append("\t@Resource(name=\""+javaTableName1+"Dao\")\n");
			implSb.append("\tprivate "+javaTableName+"Dao "+javaTableName1+"Dao;\n");
			implSb.append("\t@Override\n");
			implSb.append("\tpublic ResultDTO<String> add("+javaTableName+"PO po) {\n");
			implSb.append("\t\tBoolean flag = "+javaTableName1+"Dao.add(po);\n");
			implSb.append("\t\tif(flag) {\n");
			implSb.append("\t\t\treturn CommonUtils.returnDTO();\n\t\t}");
			implSb.append("else {\n");
			implSb.append("\t\t\treturn CommonUtils.returnDTO(CodeEnum."+tableName.toUpperCase().replace("TB_", "")+"_ADD_FAIL);\n");
			implSb.append("\t\t}\n");
			implSb.append("\t}\n");
			implSb.append("\t@Override\n");
			implSb.append("\tpublic ResultDTO<String> update("+javaTableName+"PO po){\n");
			implSb.append("\t\tBoolean flag = "+javaTableName1+"Dao.update(po);\n");
			implSb.append("\t\tif(flag) {\n");
			implSb.append("\t\t\treturn CommonUtils.returnDTO();\n\t\t}");
			implSb.append("else {\n");
			implSb.append("\t\t\treturn CommonUtils.returnDTO(CodeEnum."+tableName.toUpperCase().replace("TB_", "")+"_UPDATE_FAIL);\n");
			implSb.append("\t\t}\n");
			implSb.append("\t}\n");
			implSb.append("\t@Override\n");
			if(deletedFlag){
				implSb.append("\tpublic ResultDTO<String> delete(Integer "+id+"){\n");
				implSb.append("\t\tBoolean flag = "+javaTableName1+"Dao.delete("+id+");\n");
			}else{
				implSb.append("\tpublic ResultDTO<String> delete("+javaTableName+"PO po){\n");
				implSb.append("\t\tBoolean flag = "+javaTableName1+"Dao.delete(po);\n");
			}
			implSb.append("\t\tif(flag) {\n");
			implSb.append("\t\t\treturn CommonUtils.returnDTO();\n\t\t}");
			implSb.append("else {\n");
			implSb.append("\t\t\treturn CommonUtils.returnDTO(CodeEnum."+tableName.toUpperCase().replace("TB_", "")+"_DELETE_FAIL);\n");
			implSb.append("\t\t}\n");
			implSb.append("\t}\n");
			implSb.append("\t@Override\n");
			implSb.append("\tResultDTO<"+javaTableName+"PO> detail(Integer id){\n");
			implSb.append("\t\treturn new ResultDTO<"+javaTableName+"PO>("+javaTableName1+"Dao.detail(id));\n");
			implSb.append("\t}\n");
			implSb.append("\t@Override\n");
			implSb.append("\tPageDTO<"+javaTableName+"PO> list("+javaTableName+"VO vo){\n");
			
			implSb.append("\t\tvo.initStart();\n");
			implSb.append("\t\tPageDTO<"+javaTableName+"PO> page = new PageDTO<"+javaTableName+"PO>();\n");
			implSb.append("\t\tpage.setRows("+javaTableName1+"Dao.list(vo));\n");
			implSb.append("\t\tpage.setTotal("+javaTableName1+"Dao.count(vo));\n");
			implSb.append("\t\treturn page;\n");
			implSb.append("\t}\n");
			implSb.append("}");
			return implSb;
		}
		return null;
	}
	public static StringBuffer toService(PageDTO<ColumnDTO> page,String tableName){
		init(page,tableName);
		if(page!=null&&page.getRows()!=null&&page.getRows().size()>0){
			StringBuffer interfaceSb = new StringBuffer("public interface "+javaTableName+"Service {\n");
			interfaceSb.append("\t/**\n");
			interfaceSb.append("\t * @author fisher \n");
			interfaceSb.append("\t * @date "+DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS_CN)+"\n");
			interfaceSb.append("\t * @description 添加数据 \n");
			interfaceSb.append("\t * @params po \n");
			interfaceSb.append("\t * @return ResultDTO<String> \n\t**/\n");
			interfaceSb.append("\tResultDTO<String> add("+javaTableName+"PO po);\n");
			interfaceSb.append("\t/**\n");
			interfaceSb.append("\t * @author fisher \n");
			interfaceSb.append("\t * @date "+DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS_CN)+"\n");
			interfaceSb.append("\t * @description 修改数据 \n");
			interfaceSb.append("\t * @params po \n");
			interfaceSb.append("\t * @return ResultDTO<String> \n\t**/\n");
			interfaceSb.append("\tResultDTO<String> update("+javaTableName+"PO po);\n");
			interfaceSb.append("\t/**\n");
			interfaceSb.append("\t * @author fisher \n");
			interfaceSb.append("\t * @date "+DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS_CN)+"\n");
			interfaceSb.append("\t * @description 删除数据 \n");
			
			if(deletedFlag){//true为真删除   false为假删除
				interfaceSb.append("\t * @params id 真删除 \n");
				interfaceSb.append("\t * @return ResultDTO<String> \n\t**/\n");
				interfaceSb.append("\tResultDTO<String> delete(Integer id);\n");
			}else{
				interfaceSb.append("\t * @params po 假删除 删除标志置为1\n");
				interfaceSb.append("\t * @return ResultDTO<String> \n\t**/\n");
				interfaceSb.append("\tResultDTO<String> delete("+javaTableName+"PO po);\n");
			}
			interfaceSb.append("\t/**\n");
			interfaceSb.append("\t * @author fisher \n");
			interfaceSb.append("\t * @date "+DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS_CN)+"\n");
			interfaceSb.append("\t * @description 根据id获取数据详情 \n");
			interfaceSb.append("\t * @params id \n");
			interfaceSb.append("\t * @return ResultDTO<"+javaTableName+"PO> \n\t**/\n");
			interfaceSb.append("\tResultDTO<"+javaTableName+"PO> detail(Integer id);\n");
			interfaceSb.append("\t/**\n");
			interfaceSb.append("\t * @author fisher \n");
			interfaceSb.append("\t * @date "+DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS_CN)+"\n");
			interfaceSb.append("\t * @description 获取分页数据源 \n");
			interfaceSb.append("\t * @params vo \n");
			interfaceSb.append("\t * @return PageDTO<"+javaTableName+"PO> \n\t**/\n");
			interfaceSb.append("\tPageDTO<"+javaTableName+"PO> list("+javaTableName+"VO vo);\n}");
			return interfaceSb;
		}
		return null;
	}
	public static StringBuffer toMapper(PageDTO<ColumnDTO> page,String tableName){
		init(page,tableName);
		if(page!=null&&page.getRows()!=null&&page.getRows().size()>0){
			StringBuffer mapperSb = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
					"<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
			mapperSb.append("<mapper namespace=\""+javaTableName+"Dao\">\n");
			mapperSb.append("\t<resultMap id=\""+javaTableName1+"PO\" type=\""+javaTableName+"PO\">\n");
			for(ColumnDTO dto:page.getRows()){
				if(dto.getExtra().toLowerCase().trim().equals("auto_increment")){
					mapperSb.append("\t\t<id column=\""+dto.getColumnName()+"\" property=\""+dto.getJavaName()+"\" />\n");
				}else{
					mapperSb.append("\t\t<result column=\""+dto.getColumnName()+"\" property=\""+dto.getJavaName()+"\" />\n");
				}
			}
			mapperSb.append("\t<insert id=\"add\" parameterType=\""+javaTableName+"PO\" useGeneratedKeys=\"true\" keyProperty=\"id\">\n");
			mapperSb.append("\t\tinsert\n\t\t\tinto\n\t\t\t\t"+tableName+"(\n");
			for(ColumnDTO dto:page.getRows()){
				if(!dto.getExtra().toLowerCase().trim().equals("auto_increment")){
					mapperSb.append("\t\t\t"+dto.getColumnName()+",\n");
				}
			}
			
			mapperSb.deleteCharAt(mapperSb.length()-2);
			mapperSb.append("\t\t)values(\n");
			for(ColumnDTO dto:page.getRows()){
				if(!dto.getExtra().toLowerCase().trim().equals("auto_increment")){
					if(dto.getColumnName().trim().toLowerCase().equals("is_deleted")){
						mapperSb.append("\t\t\t1,\n");
					}else if(dto.getDataType().trim().toLowerCase().equals("datetime")){
						mapperSb.append("\t\t\tCURRENT_TIMESTAMP(),\n");
					}else if(dto.getDataType().trim().toLowerCase().equals("date")){
						mapperSb.append("\t\t\tCURRENT_DATE(),\n");
					}else{
						mapperSb.append("\t\t\t#{"+dto.getJavaName()+"},\n");
					}
				}
			}
			mapperSb.deleteCharAt(mapperSb.length()-2);
			mapperSb.append("\t\t);\n\t</insert>\n");
			
			
			mapperSb.append("\t<update id=\"update\" parameterType=\""+javaTableName+"PO\">\n");
			mapperSb.append("\t\tupdate \n\t\t\t"+tableName+"\n\t\tset\n");
			for(ColumnDTO dto:page.getRows()){
				if(!dto.getColumnKey().trim().toLowerCase().equals("pri")){
					if(dto.getColumnName().toLowerCase().trim().equals("gmt_modified")){
						mapperSb.append("\t\t\t"+dto.getColumnName()+" = CURRENT_TIMESTAMP(),\n");
					}else if(dto.getColumnName().toLowerCase().trim().equals("gmt_create")){
					}else{
						mapperSb.append("\t\t\t"+dto.getColumnName()+" = #{"+dto.getJavaName()+"},\n");
					}
				}
			}
			mapperSb.deleteCharAt(mapperSb.length()-2);
			mapperSb.append("\t\twhere \n\t\t\t"+id+" = #{"+id+"}\n\t</update>\n");
			//查询详情
			mapperSb.append("\t<select id=\"detail\" parameterType=\"Integer\" resultMap=\""+javaTableName1+"PO\">\n");
			mapperSb.append("\t\tselect \n\t\t\t*\n\t\tfrom\n\t\t\t"+tableName+"\n\t\twhere\n\t\t\t"+id+" = #{"+id+"} \n\t\t\tlimit 1;\n");
			mapperSb.append("\t</select>\n");
			//删除
			if(deletedFlag){
				mapperSb.append("\t<delete id=\"delete\" parameterType=\"Integer\" >\n");
				mapperSb.append("\t\tdelete \n\t\t\tfrom \n\t\t\t\t"+tableName+"\n\t\twhere \n\t\t\t"+id+" = #{id};");
			}else{
				mapperSb.append("\t<update id=\"delete\" parameterType=\""+javaTableName+"PO\">\n");
				mapperSb.append("\t\tupdate \n\t\t\t"+tableName+"\n\t\tset\n");
				mapperSb.append("\t\t\tis_deleted = 1 ");
				if(containsOperator){
					mapperSb.append(",\n\t\t\toperator = #{operator} ");
				}
				if(containsModified){
					mapperSb.append(",\n\t\t\tgmt_modified = CURRENT_TIMESTAMP() ");
				}
				mapperSb.append("\n\t\twhere \n\t\t\t"+id+" = #{id}\n\t</update>\n");
			}
			mapperSb.append("\t<sql id=\"listByCondition\">\n\t\twhere\n\t\t\t1 = 1\n");
			for(ColumnDTO dto:page.getRows()){
				if(!dto.getColumnName().trim().equals("gmt_create")&&!dto.getColumnName().trim().equals("gmt_modified")){
					mapperSb.append("\t\t\t<if test=\""+dto.getJavaName()+"!=null and "+dto.getJavaName()+"!=''\">\n");
					mapperSb.append("\t\t\t\tand "+dto.getColumnName()+" = #{"+dto.getJavaName()+"}\n");
					mapperSb.append("\t\t\t</if>\n");
				}
			}
			mapperSb.append("\t</sql>\n");
			mapperSb.append("\t<select id=\"list\" parameterType=\""+javaTableName+"VO\" resultMap=\""+javaTableName1+"PO\">\n");
			mapperSb.append("\t\tselect \n\t\t\t*\n\t\tfrom\n\t\t\t"+tableName+"\n");
			mapperSb.append("\t\t\t<include refid=\"listByCondition\"/> \n\t\t\tlimit #{start},#{pageSize};\n");
			
			mapperSb.append("\t\t\t<if test=\"sort!=null and sort!=''\">\n");
			mapperSb.append("\t\t\t\torder by ${sort} ${order} \n");
			mapperSb.append("\t\t\t</if>\n");
			mapperSb.append("\t\t\t<if test=\"sort==null or sort==''\">\n");
			if(containsModified){
				mapperSb.append("\t\t\t\torder by gmt_modified desc \n");
			}else{
				mapperSb.append("\t\t\t\torder by "+id+" desc \n");
			}
			mapperSb.append("\t\t\t</if>\n");
			mapperSb.append("\t\t\tlimit #{start},#{pageSize};\n");
			mapperSb.append("\t</select>\n");
			mapperSb.append("\t<select id=\"count\" parameterType=\""+javaTableName+"VO\" resultType=\"Long\">\n");
			mapperSb.append("\t\tselect \n\t\t\tcount(1)\n\t\tfrom\n\t\t\t"+tableName+"\n");
			mapperSb.append("\t\t\t<include refid=\"listByCondition\"/>\n");
			mapperSb.append("\t</select>\n");
			mapperSb.append("</resultMap>\n");
			return mapperSb;
		}
		return null;
	}
	public static StringBuffer toDAO(PageDTO<ColumnDTO> page,String tableName){
		if(page!=null&&page.getRows()!=null&&page.getRows().size()>0){
			StringBuffer interfaceSb = new StringBuffer("import java.util.List;\n\npublic interface "+javaTableName+"Dao {\n");
			interfaceSb.append("\t/**\n");
			interfaceSb.append("\t * @author fisher \n");
			interfaceSb.append("\t * @date "+DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS_CN)+"\n");
			interfaceSb.append("\t * @description 添加数据 \n");
			interfaceSb.append("\t * @params po \n");
			interfaceSb.append("\t * @return Boolean true 添加成功,false 添加失败 \n\t**/\n");
			interfaceSb.append("\tBoolean add("+javaTableName+"PO po);\n");
			interfaceSb.append("\t/**\n");
			interfaceSb.append("\t * @author fisher \n");
			interfaceSb.append("\t * @date "+DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS_CN)+"\n");
			interfaceSb.append("\t * @description 修改数据 \n");
			interfaceSb.append("\t * @params po \n");
			interfaceSb.append("\t * @return Boolean true 修改成功,false 修改失败 \n\t**/\n");
			interfaceSb.append("\tBoolean update("+javaTableName+"PO po);\n");
			interfaceSb.append("\t/**\n");
			interfaceSb.append("\t * @author fisher \n");
			interfaceSb.append("\t * @date "+DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS_CN)+"\n");
			interfaceSb.append("\t * @description 删除数据 \n");
			
			if(deletedFlag){//true为真删除   false为假删除
				interfaceSb.append("\t * @params id 真删除 \n");
				interfaceSb.append("\t * @return Boolean true 修改成功,false 修改失败 \n\t**/\n");
				interfaceSb.append("\tBoolean delete(Integer id);\n");
			}else{
				interfaceSb.append("\t * @params po 假删除 删除标志置为1\n");
				interfaceSb.append("\t * @return Boolean true 修改成功,false 修改失败 \n\t**/\n");
				interfaceSb.append("\tBoolean delete("+javaTableName+"PO po);\n");
			}
			interfaceSb.append("\t/**\n");
			interfaceSb.append("\t * @author fisher \n");
			interfaceSb.append("\t * @date "+DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS_CN)+"\n");
			interfaceSb.append("\t * @description 根据id获取数据详情 \n");
			interfaceSb.append("\t * @params id \n");
			interfaceSb.append("\t * @return "+javaTableName+"PO \n\t**/\n");
			interfaceSb.append("\t"+javaTableName+"PO detail(Integer id);\n");
			interfaceSb.append("\t/**\n");
			interfaceSb.append("\t * @author fisher \n");
			interfaceSb.append("\t * @date "+DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS_CN)+"\n");
			interfaceSb.append("\t * @description 获取分页数据源 \n");
			interfaceSb.append("\t * @params vo \n");
			interfaceSb.append("\t * @return List<"+javaTableName+"PO> \n\t**/\n");
			interfaceSb.append("\tList<"+javaTableName+"PO> list("+javaTableName+"VO vo);\n");
			interfaceSb.append("\t/**\n");
			interfaceSb.append("\t * @author fisher \n");
			interfaceSb.append("\t * @date "+DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS_CN)+"\n");
			interfaceSb.append("\t * @description 获取数据源总条数 \n");
			interfaceSb.append("\t * @params vo \n");
			interfaceSb.append("\t * @return Long \n\t**/\n");
			interfaceSb.append("\tLong count("+javaTableName+"VO vo);\n}");
			return interfaceSb;
		}
		return null;
	}
	public static StringBuffer toVO(PageDTO<ColumnDTO> page,String tableName){
		Boolean flag = false;
		if(page!=null&&page.getRows()!=null&&page.getRows().size()>0){
			StringBuffer importSb = new StringBuffer("import java.util.Date;\n\n");
			StringBuffer classSb = new StringBuffer("public class "+javaTableName+"VO {\n");
			StringBuffer privateSb = new StringBuffer("");
			StringBuffer functionSb = new StringBuffer("");
			StringBuffer toStringSb = new StringBuffer("\t@Override\n"+
					"\tpublic String toString() {\n"+
					"\t\tStringBuilder builder = new StringBuilder();\n"+
					"\t\tbuilder.append(\""+
					javaTableName+"VO [");
			int index = 0;
			for(ColumnDTO dto:page.getRows()){
				String columnName = dto.getJavaName();
				String colFunName = (""+columnName.charAt(0)).toUpperCase()+columnName.substring(1);
				String javaType = sqlTypeToJavaType(dto.getDataType());
				if(javaType.equals("Date")){
					flag = true;
				}
				String str = "private "+javaType+blanks(10-javaType.length())+columnName+";";
				privateSb.append("\t"+str+blanks(40-str.length()));
				privateSb.append("//"+dto.getColumnComment()+"\n");
				functionSb.append("\tpublic void set"+colFunName+"("+javaType+" "+columnName+"){\n"
						+"\t\tthis."+columnName+" = "+columnName+";\n"
						+ "\t}\n").append("\tpublic "+javaType+" get"+colFunName+"(){\n"
						+ "\t\treturn this."+columnName+";\n"
						+ "\t}\n");
				if(index==0){
					toStringSb.append(""+columnName+"=\");\n");
				}else{
					toStringSb.append("\t\tbuilder.append(\", "+columnName+"=\");\n");
				}
				toStringSb.append("\t\tbuilder.append("+columnName+");\n");
				index++;
			}
			toStringSb.append("\t\tbuilder.append(\"]\");\n\t\treturn builder.toString();\n\t}\n");
			classSb.append(privateSb).append(functionSb).append(toStringSb).append("}");
			if(!flag){
				importSb = new StringBuffer("");
			}
			importSb.append(classSb);
			return importSb;
		}
		return null;
	}
	public static StringBuffer toPO(PageDTO<ColumnDTO> page,String tableName){
		Boolean flag = false;
		if(page!=null&&page.getRows()!=null&&page.getRows().size()>0){
			StringBuffer importSb = new StringBuffer("import java.util.Date;\n\n");
			StringBuffer classSb = new StringBuffer("public class "+javaTableName+"PO {\n");
			StringBuffer privateSb = new StringBuffer("");
			StringBuffer functionSb = new StringBuffer("");
			StringBuffer toStringSb = new StringBuffer("\t@Override\n"+
					"\tpublic String toString() {\n"+
					"\t\tStringBuilder builder = new StringBuilder();\n"+
					"\t\tbuilder.append(\""+
					javaTableName+"PO [");
			int index = 0;
			for(ColumnDTO dto:page.getRows()){
				String columnName = dto.getJavaName();
				String colFunName = (""+columnName.charAt(0)).toUpperCase()+columnName.substring(1);
				String javaType = sqlTypeToJavaType(dto.getDataType());
				if(javaType.equals("Date")){
					flag = true;
				}
				String str = "private "+javaType+blanks(10-javaType.length())+columnName+";";
				privateSb.append("\t"+str+blanks(40-str.length()));
				privateSb.append("//"+dto.getColumnComment()+"\n");
				functionSb.append("\tpublic void set"+colFunName+"("+javaType+" "+columnName+"){\n"
						+"\t\tthis."+columnName+" = "+columnName+";\n"
						+ "\t}\n").append("\tpublic "+javaType+" get"+colFunName+"(){\n"
						+ "\t\treturn this."+columnName+";\n"
						+ "\t}\n");
				if(index==0){
					toStringSb.append(""+columnName+"=\");\n");
				}else{
					toStringSb.append("\t\tbuilder.append(\", "+columnName+"=\");\n");
				}
				toStringSb.append("\t\tbuilder.append("+columnName+");\n");
				index++;
			}
			toStringSb.append("\t\tbuilder.append(\"]\");\n\t\treturn builder.toString();\n\t}\n");
			classSb.append(privateSb).append(functionSb).append(toStringSb).append("}");
			if(!flag){
				importSb = new StringBuffer("");
			}
			importSb.append(classSb);
			return importSb;
		}
		return null;
	}
	public static String tableNameToJavaName(String tableName){
		String[] names = tableName.toLowerCase().split("_");
		String javaName = "";
		for(String name:names){
			if(!name.equals("tb")){
				javaName += (""+name.charAt(0)).toUpperCase()+name.substring(1);
			}
		}
		return javaName;
	}
	public static String columnNameToJavaName(String columnName){
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
	public static String sqlTypeToJavaType(String sqlType){
		sqlType = sqlType.toLowerCase();
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
	public static void main(String[] args) {
		PageDTO<ColumnDTO> page = new PageDTO<ColumnDTO>();
		List<ColumnDTO> list = new ArrayList<>();
		List<String> columnNameList = Arrays.asList("id","name","title","description","sort_num","is_deleted","gmt_create","gmt_modified","operator");
		List<String> dataTypeList = Arrays.asList("integer","varchar","varchar","text","tinyint","tinyint","datetime","date","integer");
		List<String> keyList = Arrays.asList("PRI","UNIQUE","","","","","","","");
		List<String> commentList = Arrays.asList("逐渐","名称","标题","描述","排序号","是否删除","创建时间","最后修改时间","最后操作人");
		List<String> extraList = Arrays.asList("auto_increment","","","","","","","","");
		for(int i=0; i<columnNameList.size(); i++){
			ColumnDTO dto = new ColumnDTO();
			dto.setColumnName(columnNameList.get(i));
			dto.setDataType(dataTypeList.get(i));
			dto.setColumnComment(commentList.get(i));
			dto.setColumnKey(keyList.get(i));
			dto.setColumnType("");
			dto.setExtra(extraList.get(i));
			list.add(dto);
		}
		page.setRows(list);
		String tableName = "tb_image_info";
//		System.out.println(toController(page,tableName));;
		export(page,tableName);
	}
}
