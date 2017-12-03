/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.productcollection.entity;

import org.hibernate.validator.constraints.Length;

import com.oigbuy.jeesite.common.persistence.DataEntity;

/**
 * 竞品采集记录日志Entity
 * @author bill.xu
 * @version 2017-09-07
 */
public class ProductCollectionLog extends DataEntity<ProductCollectionLog> {
	
	private static final long serialVersionUID = 1L;
	private String collectId;		// 竞品采集的关联id
	
	public ProductCollectionLog() {
		super();
	}

	public ProductCollectionLog(String id){
		super(id);
	}

	@Length(min=0, max=20, message="竞品采集的关联id长度必须介于 0 和 20 之间")
	public String getCollectId() {
		return collectId;
	}

	public void setCollectId(String collectId) {
		this.collectId = collectId;
	}
	
}