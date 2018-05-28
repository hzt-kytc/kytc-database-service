package com.kytc.database.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kytc.database.dao.DatabaseDao;
import com.kytc.database.service.DatabaseService;
import com.kytc.dto.ResultDTO;
@Component
@Resource(name="databaseServiceImpl")
public class DatabaseServiceImpl implements DatabaseService {
	@Resource
	private DatabaseDao databaseDao;
	public ResultDTO<List<String>> list() {
		// TODO Auto-generated method stub
		return new ResultDTO<List<String>>( databaseDao.list() );
	}

}
