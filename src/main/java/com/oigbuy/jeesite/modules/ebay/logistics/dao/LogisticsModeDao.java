/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.logistics.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.logistics.entity.LogisticsMode;

/**
 * 物流设置DAO接口
 * @author 王汉东
 * @version 2017-09-05
 */
@MyBatisDao
public interface LogisticsModeDao extends CrudDao<LogisticsMode> {
	
	LogisticsMode findListById(String id);

	List<LogisticsMode> findByCondition(@Param("paramMap") Map<String, Object> param);
	
	List<LogisticsMode> findListByModeName(String modeName);
}