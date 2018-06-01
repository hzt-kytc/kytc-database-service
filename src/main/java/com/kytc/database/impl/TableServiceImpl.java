package com.kytc.database.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kytc.database.dao.TableDao;
import com.kytc.database.dto.ColumnDTO;
import com.kytc.database.dto.TableDTO;
import com.kytc.database.service.ColumnService;
import com.kytc.database.service.TableService;
import com.kytc.database.utils.DatabaseUtils;
import com.kytc.database.vo.TableVO;
import com.kytc.dto.PageDTO;
import com.kytc.dto.ResultDTO;
@Component
@Resource(name="tableServiceImpl")
public class TableServiceImpl implements TableService {
	@Resource(name="tableDao")
	private TableDao tableDao;
	@Resource(name="columnServiceImpl")
	private ColumnService columnServiceImpl;
	@Override
	public ResultDTO<List<String>> list(String database) {
		// TODO Auto-generated method stub
		return new ResultDTO<List<String>>( tableDao.list(database) );
	}
	@Override
	public ResultDTO<TableDTO> detail(String database,String tableName) {
		// TODO Auto-generated method stub
		return new ResultDTO<TableDTO>( tableDao.detail(database, tableName) );
	}
	@Override
	public PageDTO<Map<String, Object>> dataList(TableVO vo) {
		// TODO Auto-generated method stub
		vo.initStart();
		PageDTO<Map<String, Object>> page = new PageDTO<Map<String, Object>>();
		page.setTotal( tableDao.dataCount(vo) );
		page.setRows( tableDao.dataList(vo) );
		return page;
	}
	@Override
	public ResultDTO<String> export(String database, String tableName) {
		// TODO Auto-generated method stub
		PageDTO<ColumnDTO> page = columnServiceImpl.list(database, tableName);
		DatabaseUtils.export(page, tableName);
		return new ResultDTO<String>();
	}
	@Override
	public ResultDTO<Map<String, Object>> dataDetail(String database,
			String tableName, String priKey, String priValue) {
		// TODO Auto-generated method stub
		return new ResultDTO<Map<String, Object>>(tableDao.dataDetail(database, tableName, priKey, priValue));
	}
}
