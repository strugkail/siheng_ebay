/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.platformAndsite.dao;

import java.util.List;

import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.PlatFormSiteByEbay;
import org.apache.ibatis.annotations.Param;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.PlatformSite;

/**
 * 站点DAO接口
 * @author mashuai
 * @version 2017-05-22
 */
@MyBatisDao
public interface PlatformSiteDao extends CrudDao<PlatformSite> {

	List<PlatformSite> findListByPlatformId(@Param(value = "platformId") String platformId);
	List<PlatformSite> findListByPlatformIdandSiteName(@Param(value = "platformId")String platformId,@Param(value = "siteName")String siteName);
	PlatformSite findSiteShortNameById(PlatformSite platformSite);
	List<PlatFormSiteByEbay> findSiteListByEbay();
}