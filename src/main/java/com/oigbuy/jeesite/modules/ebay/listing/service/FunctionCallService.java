/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.listing.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.modules.ebay.listing.dao.FunctionCallDao;
import com.oigbuy.jeesite.modules.ebay.listing.entity.FunctionCall;

/**
 * 接口调用Service
 * @author mashuai
 * @version 2017-05-25
 */
@Service
@Transactional(readOnly = true)
public class FunctionCallService extends CrudService<FunctionCallDao, FunctionCall> {
	
	@Autowired
	private FunctionCallDao functionCalldao;

	public FunctionCall get(String id) {
		return super.get(id);
	}
	
	public List<FunctionCall> findList(FunctionCall functionCall) {
		return super.findList(functionCall);
	}
	
	public Page<FunctionCall> findPage(Page<FunctionCall> page, FunctionCall functionCall) {
		return super.findPage(page, functionCall);
	}
	
	@Transactional(readOnly = false)
	public void save(FunctionCall functionCall) {
		super.save(functionCall);
	} 
	
	@Transactional(readOnly = false)
	public void delete(FunctionCall functionCall) {
		super.delete(functionCall);
	}

	
	/***
	 * 通过 listing id 进行查询
	 * 
	 * @param id
	 * @return
	 */
	public FunctionCall findByListingId(String id) {
		FunctionCall call = new  FunctionCall(id, Global.PLATFORM_FLAG_EBAY);
		List<FunctionCall>  callList= this.findList(call);
		if(CollectionUtils.isNotEmpty(callList)){
			return callList.iterator().next();
		}
		return null;
	}
	
}