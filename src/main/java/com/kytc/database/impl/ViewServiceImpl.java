package com.kytc.database.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kytc.database.dao.ViewsDao;
import com.kytc.database.dto.ViewsDTO;
import com.kytc.database.service.ViewService;
import com.kytc.dto.ResultDTO;
@Component
@Resource(name="viewServiceImpl")
public class ViewServiceImpl implements ViewService {
	@Resource(name="viewsDao")
	private ViewsDao viewsDao;
	@Override
	public ResultDTO<List<String>> list(String database) {
		// TODO Auto-generated method stub
		return new ResultDTO<List<String>>( viewsDao.list(database) );
	}

	@Override
	public ResultDTO<ViewsDTO> detail(String database, String viewName) {
		// TODO Auto-generated method stub
		return new ResultDTO<ViewsDTO>( viewsDao.detail(database, viewName) );
	}

}
