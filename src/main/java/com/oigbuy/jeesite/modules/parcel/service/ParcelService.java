/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.parcel.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.modules.parcel.dao.ParcelDao;
import com.oigbuy.jeesite.modules.parcel.entity.Parcel;

/**
 * 包裹表Service
 * @author handong.wang
 * @version 2017-09-07
 */
@Service
@Transactional(readOnly = true)
public class ParcelService extends CrudService<ParcelDao, Parcel> {

	public Parcel get(String id) {
		return super.get(id);
	}
	
	public List<Parcel> findList(Parcel parcel) {
		return super.findList(parcel);
	}
	
	public Page<Parcel> findPage(Page<Parcel> page, Parcel parcel) {
		return super.findPage(page, parcel);
	}
	
	@Transactional(readOnly = false)
	public void save(Parcel parcel) {
		super.save(parcel);
	}
	
	@Transactional(readOnly = false)
	public void delete(Parcel parcel) {
		super.delete(parcel);
	}
	
}