/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.country.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.modules.country.dao.CountryDao;
import com.oigbuy.jeesite.modules.country.entity.Country;

/**
 * 国家管理Service
 * @author 王佳点
 * @version 2017-06-26
 */
@Service
@Transactional(readOnly = true)
public class CountryService extends CrudService<CountryDao, Country> {

	@Autowired
	private CountryDao dao;
	
	public Country get(String id) {
		return super.get(id);
	}
	
	public List<Country> findList(Country country) {
		return super.findList(country);
	}
	
	public Page<Country> findPage(Page<Country> page, Country country) {
		return super.findPage(page, country);
	}
	
	@Transactional(readOnly = false)
	public void save(Country country) {
		super.save(country);
	}
	
	@Transactional(readOnly = false)
	public void delete(Country country) {
		super.delete(country);
	}
	
	public List<Country> findAllNameList(){
		return dao.findAllNameList();
	}
}