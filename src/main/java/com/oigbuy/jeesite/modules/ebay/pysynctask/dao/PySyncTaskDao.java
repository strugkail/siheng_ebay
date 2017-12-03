/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.pysynctask.dao;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.pysynctask.entity.PySyncTask;

/**
 * 普源同步任务DAO接口
 * @author mzm
 * @version 2017-07-10
 */
@MyBatisDao
public interface PySyncTaskDao extends CrudDao<PySyncTask> {
	
}