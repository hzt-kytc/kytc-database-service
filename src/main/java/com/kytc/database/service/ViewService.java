package com.kytc.database.service;

import java.util.List;

import com.kytc.database.dto.ViewsDTO;
import com.kytc.dto.ResultDTO;

public interface ViewService {
	/**
	 * @author fisher 
	 * @date 2018年5月19日下午9:54:39
	 * @description 获取试图的列表
	 * @param database
	 * @return ResultDTO<List<String>>
	 */
	ResultDTO<List<String>> list(String database);
	/**
	 * @author fisher 
	 * @date 2018年5月19日下午9:54:54
	 * @description 获取试图的详细信息
	 * @param database
	 * @param viewName
	 * @return ResultDTO<ViewsDTO>
	 */
	ResultDTO<ViewsDTO> detail(String database,String viewName);
}
