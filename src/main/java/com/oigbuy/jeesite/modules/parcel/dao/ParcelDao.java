/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.parcel.dao;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.parcel.entity.Parcel;

/**
 * 包裹表DAO接口
 * @author handong.wang
 * @version 2017-09-07
 */
@MyBatisDao
public interface ParcelDao extends CrudDao<Parcel> {
	
}