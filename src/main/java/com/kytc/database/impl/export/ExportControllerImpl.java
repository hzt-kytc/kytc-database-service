package com.kytc.database.impl.export;

import org.springframework.stereotype.Service;
@Service("exportControllerImpl")
public class ExportControllerImpl extends AbstractExportService {

	@Override
	public StringBuffer toStr() {
		// TODO Auto-generated method stub
		this.fileName = javaTableName+"Controller.java";
		if(page!=null&&page.getRows()!=null&&page.getRows().size()>0){
			StringBuffer controllerSb = new StringBuffer();
			if(this.packageName!=null&&!"".equals(this.packageName)) {
				controllerSb.append("package "+ this.packageName+".controller;\n\n");
				controllerSb.append("import "+ this.packageName+".po."+javaTableName+"PO;\n");
				controllerSb.append("import "+ this.packageName+".vo."+javaTableName+"VO;\n");
				controllerSb.append("import "+ this.packageName+".service."+javaTableName+"Service;\n\n");
			}
			controllerSb.append("import javax.annotation.Resource;\n");
			controllerSb.append("import org.springframework.stereotype.Controller;\n");
			controllerSb.append("import org.springframework.ui.Model;\n");
			controllerSb.append("import org.springframework.web.bind.annotation.RequestMapping;\n\n");
			controllerSb.append("@Controller(value=\""+javaTableName1+"Controller\")\n");
			controllerSb.append("@RequestMapping(value=\""+javaTableName1+"\")\n");
			controllerSb.append("public class "+javaTableName+"Controller {\n");
			controllerSb.append(tab(1)+"@Resource(name=\""+javaTableName1+"ServiceImpl\")");
			controllerSb.append(tab(1)+"private "+javaTableName+"Service "+javaTableName1+"ServiceImpl;");
			controllerSb.append(tab(1)+"private String ROOT_PATH = \"/"+javaTableName1+"/\";\n");
			controllerSb.append(tab(1)+"@RequestMapping(value={\"\",\"index\"},method=RequestMethod.GET)");
			controllerSb.append(tab(1)+"public String index() {");
			controllerSb.append(tab(2)+"return ROOT_PATH + \"index\";");
			controllerSb.append(tab(1)+"}\n");
			controllerSb.append(tab(1)+"@RequestMapping(value=\"list\")");
			controllerSb.append(tab(1)+"@ResponseBody");
			controllerSb.append(tab(1)+"public PageDTO<"+javaTableName+"PO> list("+javaTableName+"VO vo){");
			controllerSb.append(tab(2)+"return "+javaTableName1+"ServiceImpl.list(vo);");
			controllerSb.append(tab(1)+"}\n");
			
			controllerSb.append(tab(1)+"@RequestMapping(value=\"add\",method=RequestMethod.GET)");
			controllerSb.append(tab(1)+"public String addGet() {");
			controllerSb.append(tab(2)+"return ROOT_PATH + \"add\";");
			controllerSb.append(tab(1)+"}\n");
			
			controllerSb.append(tab(1)+"@RequestMapping(value=\"add\",method=RequestMethod.POST)");
			controllerSb.append(tab(1)+"@ResponseBody");
			controllerSb.append(tab(1)+"public ResultDTO<String> addPost("+javaTableName+"PO po) {");
			if(containsOperator){
				controllerSb.append(tab(2)+"po.setOperator(SessionUtils.getUserId());");
			}
			controllerSb.append(tab(2)+"return "+javaTableName1+"ServiceImpl.add(po);");
			controllerSb.append(tab(1)+"}\n");
			
			controllerSb.append(tab(1)+"@RequestMapping(value=\"update\",method=RequestMethod.GET)");
			controllerSb.append(tab(1)+"public String updateGet(Integer "+id+",Model model) {");
			controllerSb.append(tab(2)+"model.addAttribute(\"result\", "+javaTableName1+"ServiceImpl.detail("+id+"));");
			controllerSb.append(tab(2)+"return ROOT_PATH + \"update\";");
			controllerSb.append(tab(1)+"}\n");
			
			controllerSb.append(tab(1)+"@RequestMapping(value=\"update\",method=RequestMethod.POST)");
			controllerSb.append(tab(1)+"@ResponseBody");
			controllerSb.append(tab(1)+"public ResultDTO<String> updatePost("+javaTableName+"PO po) {");
			if(containsOperator){
				controllerSb.append(tab(2)+"po.setOperator(SessionUtils.getUserId());");
			}
			controllerSb.append(tab(2)+"return "+javaTableName1+"ServiceImpl.update(po);");
			controllerSb.append(tab(1)+"}\n");
			
			controllerSb.append(tab(1)+"@RequestMapping(value=\"detail\",method=RequestMethod.GET)");
			controllerSb.append(tab(1)+"public String detailGet(Integer "+id+",Model model) {");
			controllerSb.append(tab(2)+"model.addAttribute(\"result\", "+javaTableName1+"ServiceImpl.detail("+id+"));");
			controllerSb.append(tab(2)+"return ROOT_PATH + \"detail\";");
			controllerSb.append(tab(1)+"}\n");
			controllerSb.append(tab(1)+"@RequestMapping(value=\"delete\",method=RequestMethod.POST)");
			controllerSb.append(tab(1)+"@ResponseBody");
			if(deletedFlag){
				controllerSb.append(tab(1)+"public ResultDTO<String> delete(Integer "+id+"){");
				controllerSb.append(tab(2)+"return "+javaTableName1+"ServiceImpl.delete("+id+");");
			}else{
				controllerSb.append(tab(1)+"public ResultDTO<String> delete("+javaTableName+"PO po){");
				if(containsOperator){
					controllerSb.append(tab(2)+"po.setOperator(SessionUtils.getUserId());");
				}
				controllerSb.append(tab(2)+"return "+javaTableName1+"ServiceImpl.delete(po);");
			}
			controllerSb.append(tab(1)+"}");
			controllerSb.append(tab(0)+"}");
			return controllerSb;
		}
		return null;
	}

}
