package com.kytc.database.service.export;

import com.kytc.database.dto.ColumnDTO;
import com.kytc.dto.PageDTO;

public interface ExportService {
	/**
	 * @author fisher
	 * @description 
	 * @date 2018年11月24日上午11:17:28
	 * @param page 数据
	 * @param tableName 表名称
	 * @param packageName 包名
	 */
	void export( PageDTO<ColumnDTO> page, String tableName, String packageName );
}
