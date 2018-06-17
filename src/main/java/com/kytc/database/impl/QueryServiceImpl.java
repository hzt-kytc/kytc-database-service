package com.kytc.database.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kytc.database.dao.QueryDao;
import com.kytc.database.dto.ReportDTO;
import com.kytc.database.service.QueryService;
import com.kytc.dto.PageDTO;

@Service("queryServiceImpl")
public class QueryServiceImpl implements QueryService {
	@Autowired
	private QueryDao queryDao;

	@Override
	public PageDTO<Map<String, Object>> list(String sql, Integer page, Integer rows) {
		// TODO Auto-generated method stub
		sql = sql.trim().toLowerCase();
		if(sql.endsWith(";")){
			sql = sql.substring(0,sql.length()-1);
		}
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
		System.out.println(listSql);
		if(listSql.length()-listSql.trim().toLowerCase().lastIndexOf(" limit ")>15){
			listSql += " limit #{start},#{pageSize}";
			if(countSql.trim().toLowerCase().contains(" group by ")){
				countSql = "select count(1) from ("+sql+") t";
			}else{
				countSql = "select count(1) "+countSql.substring(countSql.lastIndexOf(" from "));
			}
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
		sql = sql.trim().toLowerCase();
		if(sql.length()-sql.lastIndexOf(" limit ")>15){
			sql += " limit 1";
		}else{
			sql = sql.substring(0, sql.lastIndexOf(" limit "))+" limit 1";
		}
		
		
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
				if(!titleList.contains(dto.getxTitle())){
					titleList.add(dto.getxTitle());
				}
			}
			for(int i=0;i<categoryList.size();i++){
				valueList.add(new ArrayList<Double>());
			}
			List<List<ReportDTO>> reportList = new ArrayList<List<ReportDTO>>();
			for(int i=0;i<categoryList.size();i++){
				reportList.add(new ArrayList<ReportDTO>());
			}
			for(ReportDTO dto:list){
				reportList.get(categoryList.indexOf(dto.getCategory())).add(dto);
			}
			int j=0;
			for(List<ReportDTO> list1:reportList){
				int i=0;
				for(ReportDTO dto:list1){
					for(;i<titleList.size();){
						if(dto.getxTitle().equals(titleList.get(i))){
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
				for(int i=list1.size();i<titleList.size();i++){
					list1.add(0d);
				}
			}
		}
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("category", titleList);
		List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
		for(int i=0;i<categoryList.size();i++){
			Map<String,Object> map1 = new HashMap<String,Object>();
			map1.put("name", categoryList.get(i));
			map1.put("data", valueList.get(i));
			mapList.add(map1);
		}
		if(list!=null&&list.size()>0){
			result.put("title", list.get(0).getTitle());
			result.put("yTitle", list.get(0).getyTitle());
			result.put("xTitle", list.get(0).getxList());
			result.put("subTitle", list.get(0).getSubTitle());
		}
		result.put("series", mapList);
		return result;
	}

}
