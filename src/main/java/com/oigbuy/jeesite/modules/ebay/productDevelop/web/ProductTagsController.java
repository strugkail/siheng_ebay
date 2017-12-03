/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.productDevelop.web;

import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.utils.StringUtils;
import com.oigbuy.jeesite.common.web.BaseController;
import com.oigbuy.jeesite.modules.ebay.productDevelop.entity.ProductTags;
import com.oigbuy.jeesite.modules.ebay.productDevelop.service.ProductTagsService;

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
 * 产品和标签的关系表Controller
 * @author mashuai
 * @version 2017-05-23
 */
@Controller
@RequestMapping(value = "${adminPath}/product/productTags")
public class ProductTagsController extends BaseController {

	@Autowired
	private ProductTagsService productTagsService;
	
	@ModelAttribute
	public ProductTags get(@RequestParam(required=false) String id) {
		ProductTags entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productTagsService.get(id);
		}
		if (entity == null){
			entity = new ProductTags();
		}
		return entity;
	}
	
	@RequiresPermissions("product:productTags:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProductTags productTags, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProductTags> page = productTagsService.findPage(new Page<ProductTags>(request, response), productTags);
		model.addAttribute("page", page);
		return "modules/product/productTagsList";
	}

	@RequiresPermissions("product:productTags:view")
	@RequestMapping(value = "form")
	public String form(ProductTags productTags, Model model) {
		model.addAttribute("productTags", productTags);
		return "modules/product/productTagsForm";
	}

	@RequiresPermissions("product:productTags:edit")
	@RequestMapping(value = "save")
	public String save(ProductTags productTags, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, productTags)){
			return form(productTags, model);
		}
		productTagsService.save(productTags);
		addMessage(redirectAttributes, "保存产品和标签的关系表成功");
		return "redirect:"+ Global.getAdminPath()+"/product/productTags/?repage";
	}
	
	@RequiresPermissions("product:productTags:edit")
	@RequestMapping(value = "delete")
	public String delete(ProductTags productTags, RedirectAttributes redirectAttributes) {
		productTagsService.delete(productTags);
		addMessage(redirectAttributes, "删除产品和标签的关系表成功");
		return "redirect:"+ Global.getAdminPath()+"/product/productTags/?repage";
	}

}