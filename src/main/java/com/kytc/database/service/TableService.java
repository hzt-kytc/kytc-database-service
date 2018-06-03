package com.kytc.database.service;

import java.util.List;
import java.util.Map;

import com.kytc.database.dto.TableDTO;
import com.kytc.database.vo.TableVO;
import com.kytc.dto.PageDTO;
import com.kytc.dto.ResultDTO;

public interface TableService {
	/**
	 * @author fisher 
	 * @date 2018年5月13日下午9:13:40
	 * @description 获取所有的表名
	 * @param database
	 * @return ResultDTO<List<String>>
	 */
	ResultDTO<List<String>> list(String database);
	/**
	 * @author fisher 
	 * @date 2018年5月19日下午9:51:43
	 * @description 获取表的详细信息
	 * @param database
	 * @param tableName
	 * @return ResultDTO<TableDTO>
	 */
	ResultDTO<TableDTO> detail(String database,String tableName);
	/**
	 * @author fisher 
	 * @date 2018年5月20日上午9:44:14
	 * @description 获取数据集
	 * @param vo
	 * @return PageDTO<Map<String,Object>>
	 */
	PageDTO<Map<String,Object>> dataList(TableVO vo);
	/**
	 * @author fisher 
	 * @date 2018年5月20日下午8:15:53
	 * @description 导出数据操作文件
	 * @param database
	 * @param tableName
	 * @return ResultDTO<String>
	 */
	ResultDTO<String> export(String database, String tableName);
	/**
	 * @param database
	 * @param tableName
	 * @param priKey
	 * @param priValue
	 * @return
	 */
	ResultDTO<Map<String,Object>> dataDetail(String database, String tableName,String priKey,String priValue);
	/**
	 * @author fisher
	 * @description 添加数据
	 * @date 2018年6月2日下午9:42:01
	 * @param map
	 * @return
	 */
	ResultDTO<String> addData(Map<String,Object> map);
}
