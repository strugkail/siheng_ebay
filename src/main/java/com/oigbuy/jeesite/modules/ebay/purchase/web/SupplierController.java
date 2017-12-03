/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.purchase.web;


import com.google.common.collect.Maps;
import com.oigbuy.jeesite.common.utils.StringUtils;
import com.oigbuy.jeesite.common.web.BaseController;
import com.oigbuy.jeesite.modules.ebay.purchase.dao.SupplierDao;
import com.oigbuy.jeesite.modules.ebay.purchase.dto.PurchaseSource;
import com.oigbuy.jeesite.modules.ebay.purchase.entity.Supplier;
import com.oigbuy.jeesite.modules.ebay.purchase.service.SupplierService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 供应商信息Controller
 * @author 马志明
 * @version 2017-06-18
 */
@Controller
@RequestMapping(value = "${adminPath}/supplier/supplier")
public class SupplierController extends BaseController {

	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private SupplierDao supplierDao;
	
	@ModelAttribute
	public Supplier get(@RequestParam(required=false) String id) {
		Supplier entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = supplierService.get(id);
		}
		if (entity == null){
			entity = new Supplier();
		}
		return entity;
	}
	
	/**
	 * 新增采购商，如果采购商不存在，则新增
	 * @return
	 */
	@RequestMapping("addPurchaseSource")
	public @ResponseBody List<PurchaseSource> addPurchaseSource(String productId, String pName, String pLink, String pRemark, Model model){
		System.out.println("============================================================================================" +
				"========================================================================================================");
		System.out.println(productId+"--"+pName+"--"+pLink);
		// 添加新的采购源
		supplierService.addPurchaseSource(productId, pName, pLink, pRemark);
		// 获取所有的采购源
		List<PurchaseSource> purchaseSourceList = supplierService.getSupplierListForProduct(productId);
		return purchaseSourceList;
	}
	
	/**
	 * 删除采购源
	 * @param productId
	 * @param sourceId
	 * @return
	 */
	@RequestMapping("deletePurchaseSource")
	public @ResponseBody List<PurchaseSource> deleteSupplierById(String productId, String sourceId){
		supplierService.delPurchaseSource(sourceId);
		// 获取所有的采购源
		List<PurchaseSource> purchaseSourceList = supplierService.getSupplierListForProduct(productId);
		return purchaseSourceList;
	}
	
	/**
	 * 获取产品所有的采购源
	 * @param productId
	 * @return
	 */
	@RequestMapping("getPurchaseSourceList")
	public @ResponseBody List<PurchaseSource> getPurchaseSourceList(String productId){
		List<PurchaseSource> purchaseSourceList = supplierService.getSupplierListForProduct(productId);
		return purchaseSourceList;
	}
	
	@RequestMapping("shedeletePurchaseSource")
	public @ResponseBody  Map<String,Object> shelvedeleteSupplierById(String productId, String sourceId){
		Map<String,Object> map = Maps.newHashMap();
		// 判断采购源是否被使用
		int count = supplierDao.getCountForPurchaseSourceCodeBySourceId(sourceId);
		if(count > 0){
			map.put("status", -1);
			map.put("message", "该采购源正被使用不能删除");
			return map;
		}
		
		supplierService.delPurchaseSource(sourceId);
		// 获取所有的采购源
		List<PurchaseSource> purchaseSourceList = supplierService.getSupplierListForProduct(productId);
		map.put("status", 0);
		map.put("message", "删除成功");
		map.put("sourceId", sourceId);
		map.put("purchaseSourceList", purchaseSourceList);
		return map;
	}

}