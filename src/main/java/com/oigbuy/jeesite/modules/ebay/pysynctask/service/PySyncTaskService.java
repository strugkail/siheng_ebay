/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.pysynctask.service;


import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.modules.ebay.pysynctask.dao.PySyncTaskDao;
import com.oigbuy.jeesite.modules.ebay.pysynctask.entity.PySyncTask;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 普源同步任务Service
 * @author mzm
 * @version 2017-07-10
 */
@Service
@Transactional(readOnly = true)
public class PySyncTaskService extends CrudService<PySyncTaskDao, PySyncTask> {

	public PySyncTask get(String id) {
		return super.get(id);
	}
	
	public List<PySyncTask> findList(PySyncTask pySyncTask) {
		return super.findList(pySyncTask);
	}
	
	public Page<PySyncTask> findPage(Page<PySyncTask> page, PySyncTask pySyncTask) {
		return super.findPage(page, pySyncTask);
	}
	
	@Transactional(readOnly = false)
	public void save(PySyncTask pySyncTask) {
		super.save(pySyncTask);
	}
	
	@Transactional(readOnly = false)
	public void delete(PySyncTask pySyncTask) {
		super.delete(pySyncTask);
	}
	
}