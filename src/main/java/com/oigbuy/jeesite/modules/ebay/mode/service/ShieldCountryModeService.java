/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.mode.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.modules.ebay.mode.dao.ShieldCountryModeDao;
import com.oigbuy.jeesite.modules.ebay.mode.entity.ShieldCountryMode;

/**
 * 屏蔽目的地模板Service
 * @author bill.xu
 * @version 2017-09-05
 */
@Service
@Transactional(readOnly = true)
public class ShieldCountryModeService extends CrudService<ShieldCountryModeDao, ShieldCountryMode> {

	public ShieldCountryMode get(String id) {
		return super.get(id);
	}
	
	public List<ShieldCountryMode> findList(ShieldCountryMode shieldCountryMode) {
		return super.findList(shieldCountryMode);
	}
	
	public Page<ShieldCountryMode> findPage(Page<ShieldCountryMode> page, ShieldCountryMode shieldCountryMode) {
		return super.findPage(page, shieldCountryMode);
	}
	
	@Transactional(readOnly = false)
	public void save(ShieldCountryMode shieldCountryMode) {
		super.save(shieldCountryMode);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShieldCountryMode shieldCountryMode) {
		super.delete(shieldCountryMode);
	}
	
}