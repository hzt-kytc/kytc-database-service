package com.kytc.database.service;

import java.util.List;

import com.kytc.database.dto.ColumnDTO;
import com.kytc.dto.PageDTO;
import com.kytc.dto.ResultDTO;

public interface ColumnService {
	/**
	 * @author fisher 
	 * @date 2018年5月20日上午9:16:50
	 * @description 获取表的字段信息
	 * @param database
	 * @param tableName
	 * @return PageDTO<ColumnDTO>
	 */
	PageDTO<ColumnDTO> list(String database, String tableName);
	/**
	 * @author fisher 
	 * @date 2018年5月20日上午9:18:00
	 * @description 获取表的字段名称
	 * @param database
	 * @param tableName
	 * @return ResultDTO<List<String>>
	 */
	ResultDTO<List<String>> getColumns(String database, String tableName);
}
