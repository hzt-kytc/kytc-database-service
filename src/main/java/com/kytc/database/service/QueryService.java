package com.kytc.database.service;

import java.util.List;
import java.util.Map;

import com.kytc.dto.PageDTO;

public interface QueryService {
	/**
	 * @author fisher
	 * @description 查询数据
	 * @date 2018年6月3日下午3:54:41
	 * @param sql
	 * @return
	 */
	PageDTO<Map<String,Object>> list(String sql, Integer page, Integer rows);
	/**
	 * @author fisher
	 * @description 查询数据 做报表
	 * @date 2018年6月3日下午8:47:44
	 * @param sql
	 * @return
	 */
	Map<String,Object> listReport(String sql);
	/**
	 * @author fisher
	 * @description 获取一条数据
	 * @date 2018年6月3日下午4:02:02
	 * @param sql
	 * @return
	 */
	List<Map<String, Object>> listOne(String sql);
}
