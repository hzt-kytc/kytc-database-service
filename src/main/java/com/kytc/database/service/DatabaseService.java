package com.kytc.database.service;

import java.util.List;

import com.kytc.dto.ResultDTO;

public interface DatabaseService {
	/**
	 * @author fisher 
	 * @date 2018年5月13日下午4:02:06
	 * @description 获取数据库列表
	 * @return ResultDTO<List<String>>
	 */
	ResultDTO<List<String>> list();
}
