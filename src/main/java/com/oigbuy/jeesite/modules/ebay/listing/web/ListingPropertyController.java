///**
// * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
// */
//package com.oigbuy.jeesite.modules.ebay.listing.web;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import com.oigbuy.jeesite.common.config.Global;
//import com.oigbuy.jeesite.common.persistence.Page;
//import com.oigbuy.jeesite.common.web.BaseController;
//import com.oigbuy.jeesite.common.utils.StringUtils;
//import com.oigbuy.jeesite.modules.ebay.listing.entity.ListingProperty;
//import com.oigbuy.jeesite.modules.ebay.listing.service.ListingPropertyService;
//
///**
// * listing 属性Controller
// * @author bill.xu
// * @version 2017-10-25
// */
//@Controller
//@RequestMapping(value = "${adminPath}/listing/listingProperty")
//public class ListingPropertyController extends BaseController {
//
//	@Autowired
//	private ListingPropertyService listingPropertyService;
//	
//	@ModelAttribute
//	public ListingProperty get(@RequestParam(required=false) String id) {
//		ListingProperty entity = null;
//		if (StringUtils.isNotBlank(id)){
//			entity = listingPropertyService.get(id);
//		}
//		if (entity == null){
//			entity = new ListingProperty();
//		}
//		return entity;
//	}
//	
//	@RequiresPermissions("listing:listingProperty:view")
//	@RequestMapping(value = {"list", ""})
//	public String list(ListingProperty listingProperty, HttpServletRequest request, HttpServletResponse response, Model model) {
//		Page<ListingProperty> page = listingPropertyService.findPage(new Page<ListingProperty>(request, response), listingProperty); 
//		model.addAttribute("page", page);
//		return "ebay/listing/listingPropertyList";
//	}
//
//	@RequiresPermissions("listing:listingProperty:view")
//	@RequestMapping(value = "form")
//	public String form(ListingProperty listingProperty, Model model) {
//		model.addAttribute("listingProperty", listingProperty);
//		return "ebay/listing/listingPropertyForm";
//	}
//
//	@RequiresPermissions("listing:listingProperty:edit")
//	@RequestMapping(value = "save")
//	public String save(ListingProperty listingProperty, Model model, RedirectAttributes redirectAttributes) {
//		if (!beanValidator(model, listingProperty)){
//			return form(listingProperty, model);
//		}
//		listingPropertyService.save(listingProperty);
//		addMessage(redirectAttributes, "保存listing 属性表成功");
//		return "redirect:"+Global.getAdminPath()+"/listing/listingProperty/?repage";
//	}
//	
//	@RequiresPermissions("listing:listingProperty:edit")
//	@RequestMapping(value = "delete")
//	public String delete(ListingProperty listingProperty, RedirectAttributes redirectAttributes) {
//		listingPropertyService.delete(listingProperty);
//		addMessage(redirectAttributes, "删除listing 属性表成功");
//		return "redirect:"+Global.getAdminPath()+"/listing/listingProperty/?repage";
//	}
//
//}