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
import com.kytc.database.service.export.ExportService;
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
	@Resource(name="exportPOImpl")
	private ExportService exportPOImpl;
	@Resource(name="exportDaoImpl")
	private ExportService exportDaoImpl;
	@Resource(name="exportMapperImpl")
	private ExportService exportMapperImpl;
	@Resource(name="exportServiceImpl")
	private ExportService exportServiceImpl;
	@Resource(name="exportImplImpl")
	private ExportService exportImplImpl;
	@Resource(name="exportControllerImpl")
	private ExportService exportControllerImpl;
	@Resource(name="exportVOImpl")
	private ExportService exportVOImpl;
	@Resource(name="exportIndexJsImpl")
	private ExportService exportIndexJsImpl;
	@Resource(name="exportIndexHtmlImpl")
	private ExportService exportIndexHtmlImpl;
	
	@Resource(name="exportAddJsImpl")
	private ExportService exportAddJsImpl;
	@Resource(name="exportAddHtmlImpl")
	private ExportService exportAddHtmlImpl;
	
	@Resource(name="exportUpdateJsImpl")
	private ExportService exportUpdateJsImpl;
	@Resource(name="exportUpdateHtmlImpl")
	private ExportService exportUpdateHtmlImpl;
	
	@Resource(name="exportDetailJsImpl")
	private ExportService exportDetailJsImpl;
	@Resource(name="exportDetailHtmlImpl")
	private ExportService exportDetailHtmlImpl;
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
		String packageName = "com.biaoqing.cms";
		PageDTO<ColumnDTO> page = columnServiceImpl.list(database, tableName);
//		DatabaseUtils.export(page, tableName);
		exportPOImpl.export(page, tableName, packageName);
		exportDaoImpl.export(page, tableName, packageName);
		exportMapperImpl.export(page, tableName, packageName);
		exportServiceImpl.export(page, tableName, packageName);
		exportImplImpl.export(page, tableName, packageName);
		exportControllerImpl.export(page, tableName, packageName);
		exportVOImpl.export(page, tableName, packageName);
		exportIndexJsImpl.export(page, tableName, packageName);
		
		exportIndexHtmlImpl.export(page, tableName, packageName);
		exportAddJsImpl.export(page, tableName, packageName);
		exportAddHtmlImpl.export(page, tableName, packageName);
		exportUpdateJsImpl.export(page, tableName, packageName);
		exportUpdateHtmlImpl.export(page, tableName, packageName);
		exportDetailJsImpl.export(page, tableName, packageName);
		exportDetailHtmlImpl.export(page, tableName, packageName);
		return new ResultDTO<String>();
	}
	@Override
	public ResultDTO<Map<String, Object>> dataDetail(String database,
			String tableName, String priKey, String priValue) {
		// TODO Auto-generated method stub
		return new ResultDTO<Map<String, Object>>(tableDao.dataDetail(database, tableName, priKey, priValue));
	}
	@Override
	public ResultDTO<String> addData(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Boolean flag = tableDao.addData(map);
		if( flag ){
			return new ResultDTO<String>();
		}
		return new ResultDTO<String>("12345","添加失败");
	}
	@Override
	public ResultDTO<String> updateData(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Boolean flag = tableDao.updateData(map);
		if( flag ){
			return new ResultDTO<String>();
		}
		return new ResultDTO<String>("12345","修改失败");
	}
	@Override
	public ResultDTO<String> delete(String database, String tableName,
			String priKey, String priValue) {
		// TODO Auto-generated method stub
		Boolean flag = tableDao.deleteData(database, tableName, priKey, priValue);
		if( flag ){
			return new ResultDTO<String>();
		}
		return new ResultDTO<String>("12345","删除失败");
	}
}
