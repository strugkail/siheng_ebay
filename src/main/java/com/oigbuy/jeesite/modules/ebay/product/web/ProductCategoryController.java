/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.product.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.utils.StringUtils;
import com.oigbuy.jeesite.common.web.BaseController;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductCategory;
import com.oigbuy.jeesite.modules.ebay.product.service.ProductCategoryService;

/**
 * 产品分类Controller
 * @author bill.xu
 * @version 2017-09-22
 */
@Controller
@RequestMapping(value = "${adminPath}/listing/productCategory")
public class ProductCategoryController extends BaseController {

	@Autowired
	private ProductCategoryService productCategoryService;
	
	@ModelAttribute
	public ProductCategory get(@RequestParam(required=false) String id) {
		ProductCategory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productCategoryService.get(id);
		}
		if (entity == null){
			entity = new ProductCategory();
		}
		return entity;
	}
	
	@RequiresPermissions("listing:productCategory:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProductCategory productCategory, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProductCategory> page = productCategoryService.findPage(new Page<ProductCategory>(request, response), productCategory); 
		model.addAttribute("page", page);
		return "ebay/listing/productCategoryList";
	}

	@RequiresPermissions("listing:productCategory:view")
	@RequestMapping(value = "form")
	public String form(ProductCategory productCategory, Model model) {
		model.addAttribute("productCategory", productCategory);
		return "ebay/listing/productCategoryForm";
	}

	@RequiresPermissions("listing:productCategory:edit")
	@RequestMapping(value = "save")
	public String save(ProductCategory productCategory, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, productCategory)){
			return form(productCategory, model);
		}
		productCategoryService.save(productCategory);
		addMessage(redirectAttributes, "保存产品分类成功");
		return "redirect:"+Global.getAdminPath()+"/listing/productCategory/?repage";
	}
	
	@RequiresPermissions("listing:productCategory:edit")
	@RequestMapping(value = "delete")
	public String delete(ProductCategory productCategory, RedirectAttributes redirectAttributes) {
		productCategoryService.delete(productCategory);
		addMessage(redirectAttributes, "删除产品分类成功");
		return "redirect:"+Global.getAdminPath()+"/listing/productCategory/?repage";
	}

}