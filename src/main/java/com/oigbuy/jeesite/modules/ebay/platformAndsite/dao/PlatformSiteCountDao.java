package com.oigbuy.jeesite.modules.ebay.platformAndsite.dao;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.PlatformSiteCount;

/**
 * 平台统计Dao接口
 * @author yuxiang.xiong
 *
 */
@MyBatisDao
public interface PlatformSiteCountDao extends CrudDao<PlatformSiteCount> {

}
