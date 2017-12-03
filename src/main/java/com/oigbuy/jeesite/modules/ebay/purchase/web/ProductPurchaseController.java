/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.purchase.web;

import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.utils.StringUtils;
import com.oigbuy.jeesite.common.web.BaseController;
import com.oigbuy.jeesite.modules.ebay.purchase.entity.ProductPurchase;
import com.oigbuy.jeesite.modules.ebay.purchase.service.ProductPurchaseService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 产品采购Controller
 * @author 马志明
 * @version 2017-06-21
 */
@Controller
@RequestMapping(value = "${adminPath}/purchase/productPurchase")
public class ProductPurchaseController extends BaseController {

	@Autowired
	private ProductPurchaseService productPurchaseService;
	
	@ModelAttribute
	public ProductPurchase get(@RequestParam(required=false) String id) {
		ProductPurchase entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productPurchaseService.get(id);
		}
		if (entity == null){
			entity = new ProductPurchase();
		}
		return entity;
	}
	
	@RequiresPermissions("purchase:productPurchase:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProductPurchase productPurchase, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProductPurchase> page = productPurchaseService.findPage(new Page<ProductPurchase>(request, response), productPurchase);
		model.addAttribute("page", page);
		return "modules/purchase/productPurchaseList";
	}

	@RequiresPermissions("purchase:productPurchase:view")
	@RequestMapping(value = "form")
	public String form(ProductPurchase productPurchase, Model model) {
		model.addAttribute("productPurchase", productPurchase);
		return "modules/purchase/productPurchaseForm";
	}

	@RequiresPermissions("purchase:productPurchase:edit")
	@RequestMapping(value = "save")
	public String save(ProductPurchase productPurchase, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, productPurchase)){
			return form(productPurchase, model);
		}
		productPurchaseService.save(productPurchase);
		addMessage(redirectAttributes, "保存保存产品采购成功成功");
		return "redirect:"+ Global.getAdminPath()+"/purchase/productPurchase/?repage";
	}
	
	@RequiresPermissions("purchase:productPurchase:edit")
	@RequestMapping(value = "delete")
	public String delete(ProductPurchase productPurchase, RedirectAttributes redirectAttributes) {
		productPurchaseService.delete(productPurchase);
		addMessage(redirectAttributes, "删除保存产品采购成功成功");
		return "redirect:"+ Global.getAdminPath()+"/purchase/productPurchase/?repage";
	}

}