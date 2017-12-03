/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.platformAndsite.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.Platform;

/**
 * 平台DAO接口
 * @author mashuai
 * @version 2017-05-22
 */
@MyBatisDao
public interface PlatformDao extends CrudDao<Platform> {
	List<Platform> findPlatByplatformId(Platform platform);
	List<Platform> getinfoByplatformName(@Param("platformName") String platformName);
}