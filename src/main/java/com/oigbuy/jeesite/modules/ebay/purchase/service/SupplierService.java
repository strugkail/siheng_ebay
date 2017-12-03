/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.purchase.service;

import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.modules.ebay.purchase.dao.SupplierDao;
import com.oigbuy.jeesite.modules.ebay.purchase.dto.PurchaseSource;
import com.oigbuy.jeesite.modules.ebay.purchase.entity.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 供应商信息Service
 * @author 马志明
 * @version 2017-06-18
 */
@Service
@Transactional(readOnly = true)
public class SupplierService extends CrudService<SupplierDao, Supplier> {
	
	@Autowired
	private SupplierDao supplierDao;
	
	public Supplier get(String id) {
		return super.get(id);
	}
	
	public List<Supplier> findList(Supplier supplier) {
		return super.findList(supplier);
	}
	
	public Page<Supplier> findPage(Page<Supplier> page, Supplier supplier) {
		return super.findPage(page, supplier);
	}
	
	@Transactional(readOnly = false)
	public void save(Supplier supplier) {
		super.save(supplier);
	}
	
	@Transactional(readOnly = false)
	public void delete(Supplier supplier) {
		super.delete(supplier);
	}
	
	@Transactional(readOnly = false)
	public void addPurchaseSource(String productId, String supplierName, String supplierLink, String supplierRemark){
		// 根据供应商名称判断供应商是否已存在
		Supplier supplier = supplierDao.getSupplierBySupplierName(supplierName);
		if(supplier == null){
			supplier = new Supplier();
			supplier.setSupplierId(Global.getID() + "");
			supplier.setSupplierName(supplierName);
			supplierDao.insert(supplier);
		}
		
		// 判断采购源是否已存在
		PurchaseSource purchaseSource = supplierDao.getSourceByUrlAndProductId(productId, supplierLink);
		if(purchaseSource == null){
			purchaseSource = new PurchaseSource();
			purchaseSource.setSourceId(Global.getID() + "");
			purchaseSource.setSupplierId(supplier.getSupplierId());
			purchaseSource.setProductId(productId);
			purchaseSource.setSourceUrl(supplierLink);
			purchaseSource.setRemark(supplierRemark);
			supplierDao.insertPurchaseSource(purchaseSource);
		}

	}
	
	public List<PurchaseSource> getSupplierListForProduct(String productId){
		return supplierDao.getAllPurchaseSourceByProductId(productId);
	}
	
	@Transactional(readOnly = false)
	public void delPurchaseSource(String sourceId){
		supplierDao.deletePurchaseSourceById(sourceId);
	}
	
}