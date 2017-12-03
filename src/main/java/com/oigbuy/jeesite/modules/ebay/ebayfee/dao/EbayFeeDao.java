/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.ebayfee.dao;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.ebayfee.entity.EbayFee;

/**
 * ebay最小费率DAO接口
 * @author strugkail
 * @version 2017-09-25
 */
@MyBatisDao
public interface EbayFeeDao extends CrudDao<EbayFee> {
    EbayFee getBySite(EbayFee fee);
    EbayFee getBySiteAndCategory(EbayFee fee);
}