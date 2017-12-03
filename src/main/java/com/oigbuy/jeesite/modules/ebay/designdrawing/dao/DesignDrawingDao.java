/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.designdrawing.dao;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.designdrawing.entity.DesignDrawing;

/**
 * 竞品采集DAO接口
 * @author 王佳点
 * @version 2017-09-04
 */
@MyBatisDao
public interface DesignDrawingDao extends CrudDao<DesignDrawing> {
	
}
