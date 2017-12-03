package com.oigbuy.jeesite.modules.ebay.product.dao;

import java.util.List;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.product.entity.SupplierRecommen;

@MyBatisDao
public interface SupplierRecomDao extends CrudDao<SupplierRecommen>{

	
	/***
	 * 销售部门下 供应商推荐产品
	 * 
	 * @param recommen
	 * @return
	 */
	public List<SupplierRecommen> findList2(SupplierRecommen recommen);
	
}
