/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.platformAndsite.service;

import java.util.List;

import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.PlatFormSiteByEbay;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.dao.PlatformDao;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.dao.PlatformSiteDao;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.Platform;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.PlatformSite;

/**
 * 站点Service
 * @author mashuai
 * @version 2017-05-22
 */
@Service
@Transactional(readOnly = true)
public class PlatformSiteService extends CrudService<PlatformSiteDao, PlatformSite> {

	@Autowired
	private PlatformSiteDao platformSiteDao;
	
	@Autowired
	private PlatformDao platformDao;
	
	public PlatformSite get(String id) {
		return super.get(id);
	}
	
	public List<PlatformSite> findList(PlatformSite platformSite) {
		return super.findList(platformSite);
	}
	
	public Page<PlatformSite> findPage(Page<PlatformSite> page, PlatformSite platformSite) {
		return super.findPage(page, platformSite);
	}
	
	@Transactional(readOnly = false)
	public void save(PlatformSite platformSite) {
		super.save(platformSite);
	}
	
	@Transactional(readOnly = false)
	public void delete(PlatformSite platformSite) {
		super.delete(platformSite);
	}

	public List<PlatformSite> findListByPlatformId(String platformId) {
		return platformSiteDao.findListByPlatformId(platformId);
	}
	public List<PlatformSite> findListByPlatformIdandSiteName(String platformId,String SiteName) {
		return platformSiteDao.findListByPlatformIdandSiteName(platformId,SiteName);
	}
	
	public PlatformSite selectBySiteId(PlatformSite platformSite){
		return platformSiteDao.findSiteShortNameById(platformSite);
	}
	/***
	 * 通过 平台name 获取 该平台下的站点集合 
	 * 
	 *
	 * @param name
	 * @return
	 */
	public List<PlatformSite> getListByPlatName(String name){
		PlatformSite  querySite = new PlatformSite();
		List<Platform> platformList = platformDao.getinfoByplatformName(name);
		if(CollectionUtils.isNotEmpty(platformList)){
			querySite.setPlatformId(Long.valueOf(platformList.get(0).getId()));
		}
		List<PlatformSite> siteList= this.platformSiteDao.findList(querySite);
		return siteList;
	}
	public List<PlatFormSiteByEbay> findSiteByEbay(){
	    return platformSiteDao.findSiteListByEbay();
    }
	
}