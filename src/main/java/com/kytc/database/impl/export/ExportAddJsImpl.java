package com.kytc.database.impl.export;

import org.springframework.stereotype.Service;
@Service("exportAddJsImpl")
public class ExportAddJsImpl extends AbstractExportService {

	@Override
	public StringBuffer toStr() {
		// TODO Auto-generated method stub
		this.fileName = "add.js";
		StringBuffer addJs = new StringBuffer("");
		addJs.append("$(function(){")
			.append(tab(1)+"var mainDiv = $(\"#cms_"+htmlName+"_add_main_div\");"+
					tab(1)+ "var parentMainDiv = $(\"#cms_"+htmlName+"_main_div\");")
			.append(tab(1)+"var rootPath = \"/"+javaTableName1+"/\";")
			.append(tab(1)+"mainDiv.off().on(\"click\",\"a[name='save']\",function(){"+
				tab(2)+"if(mainDiv.form('validate')){"+
				tab(3)+"var jsonData = mainDiv.toJSON();"+
				tab(3)+"jsonData.deleted = 0;"+
				tab(3)+"$.ajax({"+
				tab(4)+"url:$.cms.url+rootPath+\"add\","+
				tab(4)+"type:\"post\","+
				tab(4)+"data:jsonData,"+
				tab(4)+"dataType:\"json\","+
				tab(4)+"success:function(data){"+
				tab(5)+"if(data.status){"+
				tab(6)+"$(\"a[name='search']\",parentMainDiv).trigger(\"click\");"+
				tab(6)+"$.EasyUI.Window.close(mainDiv);"+
				tab(5)+"}else{"+
				tab(6)+"$.EasyUI.message(data.error_reason,\"s\",null);"+
				tab(6)+"return;"+
				tab(5)+"}"+
				tab(4)+"}"+
				tab(3)+"})"+
				tab(2)+"}"+
				tab(1)+"}).on(\"click\",\"a[name='close']\",function(){"+
				tab(2)+"$.EasyUI.Window.close(mainDiv);"+
				tab(1)+"});"+
				tab(0)+"});");
		return addJs;
	}

}
