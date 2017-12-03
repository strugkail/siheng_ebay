/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.sys.dao;

import com.oigbuy.jeesite.common.persistence.TreeDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.sys.entity.Office;
import com.oigbuy.jeesite.modules.sys.entity.User;

/**
 * 机构DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
	
}
