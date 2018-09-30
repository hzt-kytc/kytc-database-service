package com.kytc.database.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kytc.database.dao.ColumnDao;
import com.kytc.database.dto.ColumnDTO;
import com.kytc.database.service.ColumnService;
import com.kytc.dto.PageDTO;
import com.kytc.dto.ResultDTO;
@Component
@Resource(name="columnServiceImpl")
public class ColumnServiceImpl implements ColumnService {
	@Resource(name="columnDao")
	private ColumnDao columnDao;
	@Override
	public PageDTO<ColumnDTO> list(String database, String tableName) {
		// TODO Auto-generated method stub
		List<ColumnDTO> list = columnDao.list(database, tableName);
		PageDTO<ColumnDTO> page = new PageDTO<ColumnDTO>();
		page.setRows(list);
		page.setTotal( Long.valueOf(""+list.size()) );
		return page;
	}

	@Override
	public ResultDTO<List<String>> getColumns(String database, String tableName) {
		// TODO Auto-generated method stub
		return new ResultDTO<List<String>>( columnDao.getColumn(database, tableName) );
	}

}
