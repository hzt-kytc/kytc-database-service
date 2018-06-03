package com.kytc.database.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kytc.database.dao.QueryDao;
import com.kytc.database.dto.ReportDTO;
import com.kytc.database.service.QueryService;
import com.kytc.dto.PageDTO;

@Component
@Resource(name = "queryServiceImpl")
public class QueryServiceImpl implements QueryService {
	@Resource(name = "queryDao")
	private QueryDao queryDao;

	@Override
	public PageDTO<Map<String, Object>> list(String sql, Integer page, Integer rows) {
		// TODO Auto-generated method stub
		sql = sql.trim();
		String listSql = sql;
		String countSql = sql;
		Integer start = 0;
		if(rows==null){
			rows = 15;
		}
		if(page!=null){
			start = (page-1)*rows;
		}
		PageDTO<Map<String, Object>> pageDTO = new PageDTO<Map<String, Object>>();
		System.out.println(page);
		if(listSql.length()-listSql.lastIndexOf(" limit ")>15){
			listSql += " limit #{start},#{pageSize}";
			countSql = "select count(1) "+countSql.substring(countSql.lastIndexOf(" from "));
			List<Map<String, Object>> list = queryDao.list(listSql,start,rows);
			pageDTO.setRows(list);
			pageDTO.setTotal(queryDao.count(countSql));
		}else{
			List<Map<String, Object>> list = queryDao.list(sql,start,rows);
			pageDTO.setRows(list);
			pageDTO.setTotal(Long.valueOf(list.size()));
		}
		return pageDTO;
	}

	@Override
	public List<Map<String, Object>> listOne(String sql) {
		// TODO Auto-generated method stub
		sql += " limit 1";
		Map<String, Object> map = queryDao.listOne(sql);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		if (map != null) {
			for (String key : map.keySet()) {
				Object param = map.get(key);
				String type = "String";
				if (param instanceof Integer) {
					type = "Integer";
				} else if (param instanceof String) {
					type = "String";
				} else if (param instanceof Double) {
					type = "Double";
				} else if (param instanceof Float) {
					type = "Float";
				} else if (param instanceof Long) {
					type = "Long";
				} else if (param instanceof Boolean) {
					type = "Boolean";
				} else if (param instanceof Date) {
					type = "Date";
				}
				Map<String,Object> map1 = new HashMap<String,Object>();
				map1.put("field", key);
				map1.put("type", type);
				list.add(map1);
			}
		}
		return list;
	}

	@Override
	public Map<String, Object> listReport(String sql) {
		// TODO Auto-generated method stub
		List<ReportDTO> list = queryDao.listReport(sql);
		List<String> categoryList = new ArrayList<String>();
		List<String> titleList = new ArrayList<String>();
		List<List<Double>> valueList = new ArrayList<List<Double>>();
		if(list!=null&&list.size()>0){
			for(ReportDTO dto:list){
				if(!categoryList.contains(dto.getCategory())){
					categoryList.add(dto.getCategory());
				}
				if(!titleList.contains(dto.getTitle())){
					titleList.add(dto.getTitle());
				}
			}
			for(int i=0;i<titleList.size();i++){
				valueList.add(new ArrayList<Double>());
			}
			List<List<ReportDTO>> reportList = new ArrayList<List<ReportDTO>>();
			for(int i=0;i<titleList.size();i++){
				reportList.add(new ArrayList<ReportDTO>());
			}
			for(ReportDTO dto:list){
				reportList.get(titleList.indexOf(dto.getTitle())).add(dto);
			}
			int j=0;
			for(List<ReportDTO> list1:reportList){
				int i=0;
				for(ReportDTO dto:list1){
					for(;i<categoryList.size();){
						if(dto.getCategory().equals(categoryList.get(i))){
							valueList.get(j).add(dto.getValue());
							i=i+1;
							break;
						}else{
							valueList.get(j).add(0d);
						}
						i=i+1;
					}
				}
				j++;
			}
			for(List<Double> list1:valueList){
				for(int i=list1.size();i<categoryList.size();i++){
					list1.add(0d);
				}
			}
		}
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("category", categoryList);
		List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
		for(int i=0;i<titleList.size();i++){
			Map<String,Object> map1 = new HashMap<String,Object>();
			map1.put("name", titleList.get(i));
			map1.put("data", valueList.get(i));
			mapList.add(map1);
		}
		result.put("series", mapList);
		System.out.println(result);
		return result;
	}

}
