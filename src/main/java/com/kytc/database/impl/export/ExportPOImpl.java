package com.kytc.database.impl.export;

import org.springframework.stereotype.Service;

import com.kytc.database.dto.ColumnDTO;
@Service("exportPOImpl")
public class ExportPOImpl extends AbstractExportService {

	@Override
	public StringBuffer toStr() {
		// TODO Auto-generated method stub
		Boolean flag = false;
		this.fileName = javaTableName + "PO.java";
		if(page!=null&&page.getRows()!=null&&page.getRows().size()>0){
			StringBuffer importSb = new StringBuffer("import java.util.Date;\n\n");
			StringBuffer classSb = new StringBuffer("public class "+javaTableName+"PO {");
			StringBuffer privateSb = new StringBuffer("");
			StringBuffer functionSb = new StringBuffer("");
			StringBuffer toStringSb = new StringBuffer(tab(1)+"@Override"+
					tab(1)+"public String toString() {"+
					tab(2)+"JsonUtils.toJSON( this );"+
					tab(1)+"}\n");
			for(ColumnDTO dto:page.getRows()){
				String columnName = dto.getJavaName();
				String colFunName = (""+columnName.charAt(0)).toUpperCase()+columnName.substring(1);
				String javaType = sqlTypeToJavaType(dto.getColumnName(),dto.getDataType());
				if(javaType.equals("Date")){
					flag = true;
				}
				String str = PRIVATE+" "+javaType+blanks(10-javaType.length())+columnName+";";
				privateSb.append(tab(1) + str + blanks(40-str.length()));
				privateSb.append("//"+dto.getColumnComment()+"");
				functionSb.append(tab(1) + PUBLIC + " void set"+colFunName+"("+javaType+" "+columnName+"){"
						+ tab(2) + "this."+columnName+" = "+columnName+";"
						+ tab(1) + "}").append(tab(1) + PUBLIC + " " + javaType+" get"+colFunName+"(){"
						+ tab(2) + "return this."+columnName+";"
						+ tab(1) + "}");
			}
			classSb.append(privateSb).append(functionSb).append(toStringSb).append("}");
			if( !flag ){
				importSb = new StringBuffer("");
			}
			importSb.append(classSb);
			StringBuffer packageSb = new StringBuffer("");
			if( packageName != null && !"".equals(packageName) ) {
				packageSb.append("package "+packageName+".po;\n\n");
			}
			packageSb.append(importSb);
			return packageSb;
		}
		return null;
	}

}
