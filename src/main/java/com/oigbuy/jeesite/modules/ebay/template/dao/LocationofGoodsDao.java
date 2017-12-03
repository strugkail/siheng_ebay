package com.oigbuy.jeesite.modules.ebay.template.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.PlatformSite;
import com.oigbuy.jeesite.modules.ebay.template.entity.LocationofGoods;

@MyBatisDao
public interface LocationofGoodsDao extends CrudDao<LocationofGoods> {

	 List<PlatformSite> findsiteList();
	 int findCountBytemplateName(@Param("templateName") String templateName);
}
