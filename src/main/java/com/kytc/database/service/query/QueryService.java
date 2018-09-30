package com.kytc.database.service.query;

import java.util.List;

import com.kytc.database.po.QueryPO;
import com.kytc.database.vo.QueryVO;
import com.kytc.dto.DropDTO;
import com.kytc.dto.PageDTO;
import com.kytc.dto.ResultDTO;

public interface QueryService {
	/**
	 * @author fisher 
	 * @date 2018年06月17日 16:35:37
	 * @description 添加数据 
	 * @param po 
	 * @return ResultDTO<String> 
	**/
	ResultDTO<String> add(QueryPO po);
	/**
	 * @author fisher 
	 * @date 2018年06月17日 16:35:37
	 * @description 修改数据 
	 * @param po 
	 * @return ResultDTO<String> 
	**/
	ResultDTO<String> update(QueryPO po);
	/**
	 * @author fisher 
	 * @date 2018年06月17日 16:35:37
	 * @description 删除数据 
	 * @param id 真删除 
	 * @return ResultDTO<String> 
	**/
	ResultDTO<String> delete(Integer id);
	/**
	 * @author fisher 
	 * @date 2018年06月17日 16:35:37
	 * @description 根据id获取数据详情 
	 * @param id 
	 * @return ResultDTO<QueryPO> 
	**/
	ResultDTO<QueryPO> detail(Integer id);
	/**
	 * @author fisher 
	 * @date 2018年06月17日 16:35:37
	 * @description 获取分页数据源 
	 * @param vo 
	 * @return PageDTO<QueryPO> 
	**/
	PageDTO<QueryPO> list(QueryVO vo);
	/**
	 * @author fisher
	 * @description 获取下拉数据源
	 * @date 2018年6月17日下午5:06:03
	 * @return
	 */
	List<DropDTO> drop();
}