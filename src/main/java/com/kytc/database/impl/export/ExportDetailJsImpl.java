package com.kytc.database.impl.export;

import org.springframework.stereotype.Service;
@Service("exportDetailJsImpl")
public class ExportDetailJsImpl extends AbstractExportService {

	@Override
	public StringBuffer toStr() {
		// TODO Auto-generated method stub
		this.fileName = "detail.js";
		StringBuffer addJs = new StringBuffer("");
		addJs.append("$(function(){")
			.append(tab(1)+"var mainDiv = $(\"#cms_"+htmlName+"_detail_main_div\");")
			.append(tab(1)+"mainDiv.off().on(\"click\",\"a[name='close']\",function(){"+
					tab(2)+"$.EasyUI.Window.close(mainDiv);"+
					tab(1)+"});"+
					tab(0)+"});");
		return addJs;
	}

}
