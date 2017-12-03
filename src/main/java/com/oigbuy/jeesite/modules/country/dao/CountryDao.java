/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.country.dao;

import java.util.List;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.country.entity.Country;

/**
 * 国家管理DAO接口
 * @author 王佳点
 * @version 2017-06-26
 */
@MyBatisDao
public interface CountryDao extends CrudDao<Country> {

	
	
	List<Country> findAllNameList();
}