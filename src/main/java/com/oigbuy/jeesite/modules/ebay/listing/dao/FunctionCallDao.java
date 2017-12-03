/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.listing.dao;

import java.util.List;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.listing.entity.FunctionCall;

/**
 * 接口调用DAO接口
 * 
 * @author mashuai
 * @version 2017-05-25
 */
@MyBatisDao
public interface FunctionCallDao extends CrudDao<FunctionCall> {

	void insertList(List<FunctionCall> functiongCallList);
}