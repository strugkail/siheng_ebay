/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.logistics.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.modules.ebay.logistics.dao.ShippingModeDao;
import com.oigbuy.jeesite.modules.ebay.logistics.entity.ShippingMode;

/**
 * 运输模板Service
 * @author handong.wang
 * @version 2017-09-07
 */
@Service
@Transactional(readOnly = true)
public class ShippingModeService extends CrudService<ShippingModeDao, ShippingMode> {

	@Autowired
	private ShippingModeDao dao;
	
	public ShippingMode get(String id) {
		return super.get(id);
	}
	
	public List<ShippingMode> findList(ShippingMode shippingMode) {
		return super.findList(shippingMode);
	}
	
	public Page<ShippingMode> findPage(Page<ShippingMode> page, ShippingMode shippingMode) {
		return super.findPage(page, shippingMode);
	}
	
	@Transactional(readOnly = false)
	public void save(ShippingMode shippingMode) {
		super.save(shippingMode);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShippingMode shippingMode) {
		super.delete(shippingMode);
	}
	
	@Transactional(readOnly = false)
	public void deleteByModeId(String modeId) {
		dao.deleteByModeId(modeId);
	}
	
}