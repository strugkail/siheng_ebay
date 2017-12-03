/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.ebayPaypal.dao;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.ebayPaypal.entity.EbayPaypal;

/**
 * ebay最小费率DAO接口
 * @author strugkail
 * @version 2017-09-25
 */
@MyBatisDao
public interface EbayPaypalDao extends CrudDao<EbayPaypal> {
    EbayPaypal getBySite(EbayPaypal paypal);
}