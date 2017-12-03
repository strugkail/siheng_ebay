package com.oigbuy.jeesite.modules.ebay.template.dao;

import java.util.List;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.Shops;
import com.oigbuy.jeesite.modules.ebay.template.entity.Seller;
/**
 * 卖家账户DAO接口
 * 
 * @author jalyn.zhang
 *
 */
@MyBatisDao
public interface SellerDao extends CrudDao<Seller> {

	/***
	 * 获得 店铺集合
	 * 
	 * @return
	 */
	public List<Shops> getShopList();
	
}
