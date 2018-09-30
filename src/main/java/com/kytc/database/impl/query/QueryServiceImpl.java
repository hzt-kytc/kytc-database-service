package com.kytc.database.impl.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kytc.database.dao.query.QueryQueryDao;
import com.kytc.database.po.QueryPO;
import com.kytc.database.service.query.QueryService;
import com.kytc.database.vo.QueryVO;
import com.kytc.dto.DropDTO;
import com.kytc.dto.PageDTO;
import com.kytc.dto.ResultDTO;

@Service("queryQueryServiceImpl")
public class QueryServiceImpl implements QueryService {
	@Autowired
	private QueryQueryDao queryQueryDao;
	@Override
	public ResultDTO<String> add(QueryPO po) {
		Boolean flag = queryQueryDao.add(po);
		if(flag) {
			return new ResultDTO<String>();
		}else {
			return new ResultDTO<String>("12345", "添加失败");
		}
	}
	@Override
	public ResultDTO<String> update(QueryPO po){
		Boolean flag = queryQueryDao.update(po);
		if(flag) {
			return new ResultDTO<String>();
		}else {
			return new ResultDTO<String>("12345", "保存失败");
		}
	}
	@Override
	public ResultDTO<String> delete(Integer id){
		Boolean flag = queryQueryDao.delete(id);
		if(flag) {
			return new ResultDTO<String>();
		}else {
			return new ResultDTO<String>("12345", "删除失败");
		}
	}
	@Override
	public ResultDTO<QueryPO> detail(Integer id){
		return new ResultDTO<QueryPO>(queryQueryDao.detail(id));
	}
	@Override
	public PageDTO<QueryPO> list(QueryVO vo){
		vo.initStart();
		PageDTO<QueryPO> page = new PageDTO<QueryPO>();
		page.setRows(queryQueryDao.list(vo));
		page.setTotal(queryQueryDao.count(vo));
		return page;
	}
	@Override
	public List<DropDTO> drop() {
		// TODO Auto-generated method stub
		return queryQueryDao.drop();
	}
}