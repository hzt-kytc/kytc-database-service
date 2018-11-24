package com.kytc.database.impl.export;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.kytc.utils.date.DateStyle;
import com.kytc.utils.date.DateUtil;
@Service("exportServiceImpl")
public class ExportServiceImpl extends AbstractExportService {

	@Override
	public StringBuffer toStr() {
		// TODO Auto-generated method stub
		this.fileName = javaTableName+"Service.java";
		if(page!=null&&page.getRows()!=null&&page.getRows().size()>0){
			StringBuffer interfaceSb = new StringBuffer("");
			if(this.packageName!=null&&!"".equals(this.packageName)) {
				interfaceSb.append("package "+this.packageName+".service;\n\n");
				interfaceSb.append("import "+this.packageName+".po."+javaTableName+"PO;\n");
				interfaceSb.append("import "+this.packageName+".vo."+javaTableName+"VO;\n\n");
			}
			interfaceSb.append("public interface "+javaTableName+"Service {");
			interfaceSb.append(tab(1)+"/**");
			interfaceSb.append(tab(1)+" * @author fisher ");
			interfaceSb.append(tab(1)+" * @date "+DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS_CN));
			interfaceSb.append(tab(1)+" * @description 添加数据 ");
			interfaceSb.append(tab(1)+" * @param po ");
			interfaceSb.append(tab(1)+" * @return ResultDTO<String> "+tab(1)+"**/");
			interfaceSb.append(tab(1)+"ResultDTO<String> add("+javaTableName+"PO po);");
			interfaceSb.append(tab(1)+"/**");
			interfaceSb.append(tab(1)+" * @author fisher ");
			interfaceSb.append(tab(1)+" * @date "+DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS_CN));
			interfaceSb.append(tab(1)+" * @description 修改数据 ");
			interfaceSb.append(tab(1)+" * @param po ");
			interfaceSb.append(tab(1)+" * @return ResultDTO<String> "+tab(1)+"**/");
			interfaceSb.append(tab(1)+"ResultDTO<String> update("+javaTableName+"PO po);");
			interfaceSb.append(tab(1)+"/**");
			interfaceSb.append(tab(1)+" * @author fisher ");
			interfaceSb.append(tab(1)+" * @date "+DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS_CN));
			interfaceSb.append(tab(1)+" * @description 删除数据 ");
			
			if(deletedFlag){//true为真删除   false为假删除
				interfaceSb.append(tab(1)+" * @param id 真删除 ");
				interfaceSb.append(tab(1)+" * @return ResultDTO<String> "+tab(1)+"**/");
				interfaceSb.append(tab(1)+"ResultDTO<String> delete(Integer id);");
			}else{
				interfaceSb.append(tab(1)+" * @param po 假删除 删除标志置为1");
				interfaceSb.append(tab(1)+" * @return ResultDTO<String> "+tab(1)+"**/");
				interfaceSb.append(tab(1)+"ResultDTO<String> delete("+javaTableName+"PO po);");
			}
			interfaceSb.append(tab(1)+"/**");
			interfaceSb.append(tab(1)+" * @author fisher ");
			interfaceSb.append(tab(1)+" * @date "+DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS_CN));
			interfaceSb.append(tab(1)+" * @description 根据id获取数据详情 ");
			interfaceSb.append(tab(1)+" * @param id ");
			interfaceSb.append(tab(1)+" * @return ResultDTO<"+javaTableName+"PO> "+tab(1)+"**/");
			interfaceSb.append(tab(1)+"ResultDTO<"+javaTableName+"PO> detail(Integer id);");
			interfaceSb.append(tab(1)+"/**");
			interfaceSb.append(tab(1)+" * @author fisher ");
			interfaceSb.append(tab(1)+" * @date "+DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS_CN));
			interfaceSb.append(tab(1)+" * @description 获取分页数据源 ");
			interfaceSb.append(tab(1)+" * @param vo ");
			interfaceSb.append(tab(1)+" * @return PageDTO<"+javaTableName+"PO> "+tab(1)+"**/");
			interfaceSb.append(tab(1)+"PageDTO<"+javaTableName+"PO> list("+javaTableName+"VO vo);\n}");
			return interfaceSb;
		}
		return null;
	}

}
