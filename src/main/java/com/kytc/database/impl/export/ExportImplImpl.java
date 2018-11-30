package com.kytc.database.impl.export;

import org.springframework.stereotype.Service;

@Service("exportImplImpl")
public class ExportImplImpl extends AbstractExportService {

	@Override
	public StringBuffer toStr() {
		// TODO Auto-generated method stub
		this.fileName = javaTableName+"ServiceImpl.java";
		if(page!=null&&page.getRows()!=null&&page.getRows().size()>0){
			StringBuffer implSb = new StringBuffer();
			if(this.packageName!=null&&!"".equals(this.packageName)) {
				implSb.append("package "+this.packageName+".impl;\n\n");
				implSb.append("import "+this.packageName+".po."+javaTableName+"PO;\n");
				implSb.append("import "+this.packageName+".vo."+javaTableName+"VO;\n");
				implSb.append("import "+this.packageName+".service."+javaTableName+"Service;\n\n");
			}
			implSb.append("import javax.annotation.Resource;\n");
			implSb.append("import org.springframework.stereotype.Component;\n\n");
			implSb.append("@Component\n");
			implSb.append("@Resource(name=\""+javaTableName1+"ServiceImpl\")\n");
			implSb.append("public class "+javaTableName+"ServiceImpl implements "+javaTableName+"Service {");
			implSb.append(tab(1)+"@Resource(name=\""+javaTableName1+"Dao\")");
			implSb.append(tab(1)+"private "+javaTableName+"Dao "+javaTableName1+"Dao;");
			implSb.append(tab(1)+"@Override");
			implSb.append(tab(1)+"public ResultDTO<String> add("+javaTableName+"PO po) {");
			implSb.append(tab(2)+"Boolean flag = "+javaTableName1+"Dao.add(po);");
			implSb.append(tab(2)+"if(flag) {");
			implSb.append(tab(3)+"return CommonUtils.returnDTO();"+tab(2)+"} ");
			implSb.append("else {");
			implSb.append(tab(3)+"return CommonUtils.returnDTO(CodeEnum.ADD_FAIL);");
			implSb.append(tab(2)+"}");
			implSb.append(tab(1)+"}");
			implSb.append(tab(1)+"@Override");
			implSb.append(tab(1)+"public ResultDTO<String> update("+javaTableName+"PO po){");
			implSb.append(tab(2)+"Boolean flag = "+javaTableName1+"Dao.update(po);");
			implSb.append(tab(2)+"if(flag) {");
			implSb.append(tab(3)+"return CommonUtils.returnDTO();"+tab(2)+"} ");
			implSb.append("else {");
			implSb.append(tab(3)+"return CommonUtils.returnDTO(CodeEnum.UPDATE_FAIL);");
			implSb.append(tab(2)+"}");
			implSb.append(tab(1)+"}");
			implSb.append(tab(1)+"@Override");
			if(deletedFlag){
				implSb.append(tab(1)+"public ResultDTO<String> delete(Integer "+id+"){");
				implSb.append(tab(2)+"Boolean flag = "+javaTableName1+"Dao.delete("+id+");");
			}else{
				implSb.append(tab(1)+"public ResultDTO<String> delete("+javaTableName+"PO po){");
				implSb.append(tab(2)+"Boolean flag = "+javaTableName1+"Dao.delete(po);");
			}
			implSb.append(tab(2)+"if(flag) {");
			implSb.append(tab(3)+"return CommonUtils.returnDTO();");
			implSb.append(tab(2)+"} else {");
			implSb.append(tab(3)+"return CommonUtils.returnDTO(CodeEnum.DELETE_FAIL);");
			implSb.append(tab(2)+"}");
			implSb.append(tab(1)+"}");
			implSb.append(tab(1)+"@Override");
			implSb.append(tab(1)+"public ResultDTO<"+javaTableName+"PO> detail(Integer id){");
			implSb.append(tab(2)+"return CommonUtils.returnDTO("+javaTableName1+"Dao.detail(id));");
			implSb.append(tab(1)+"}");
			implSb.append(tab(1)+"@Override");
			implSb.append(tab(1)+"public PageDTO<"+javaTableName+"PO> list("+javaTableName+"VO vo){");
			
			implSb.append(tab(2)+"vo.init();");
			implSb.append(tab(2)+"PageDTO<"+javaTableName+"PO> page = new PageDTO<"+javaTableName+"PO>();");
			implSb.append(tab(2)+"page.setRows("+javaTableName1+"Dao.list(vo));");
			implSb.append(tab(2)+"page.setTotal("+javaTableName1+"Dao.count(vo));");
			implSb.append(tab(2)+"return page;");
			implSb.append(tab(1)+"}");
			implSb.append(tab(0)+"}");
			return implSb;
		}
		return null;
	}

}
