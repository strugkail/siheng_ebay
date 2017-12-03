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
import com.oigbuy.jeesite.modules.ebay.product.entity.ItemSpecifics;
import com.oigbuy.jeesite.modules.ebay.product.service.ItemSpecificsService;

/**
 * 分类细节Controller
 * @author bill.xu
 * @version 2017-09-22
 */
@Controller
@RequestMapping(value = "${adminPath}/listing/itemSpecifics")
public class ItemSpecificsController extends BaseController {

	@Autowired
	private ItemSpecificsService itemSpecificsService;
	
	@ModelAttribute
	public ItemSpecifics get(@RequestParam(required=false) String id) {
		ItemSpecifics entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = itemSpecificsService.get(id);
		}
		if (entity == null){
			entity = new ItemSpecifics();
		}
		return entity;
	}
	
	@RequiresPermissions("listing:itemSpecifics:view")
	@RequestMapping(value = {"list", ""})
	public String list(ItemSpecifics itemSpecifics, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ItemSpecifics> page = itemSpecificsService.findPage(new Page<ItemSpecifics>(request, response), itemSpecifics); 
		model.addAttribute("page", page);
		return "ebay/listing/itemSpecificsList";
	}

	@RequiresPermissions("listing:itemSpecifics:view")
	@RequestMapping(value = "form")
	public String form(ItemSpecifics itemSpecifics, Model model) {
		model.addAttribute("itemSpecifics", itemSpecifics);
		return "ebay/listing/itemSpecificsForm";
	}

	@RequiresPermissions("listing:itemSpecifics:edit")
	@RequestMapping(value = "save")
	public String save(ItemSpecifics itemSpecifics, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, itemSpecifics)){
			return form(itemSpecifics, model);
		}
		itemSpecificsService.save(itemSpecifics);
		addMessage(redirectAttributes, "保存分类细节成功");
		return "redirect:"+Global.getAdminPath()+"/listing/itemSpecifics/?repage";
	}
	
	@RequiresPermissions("listing:itemSpecifics:edit")
	@RequestMapping(value = "delete")
	public String delete(ItemSpecifics itemSpecifics, RedirectAttributes redirectAttributes) {
		itemSpecificsService.delete(itemSpecifics);
		addMessage(redirectAttributes, "删除分类细节成功");
		return "redirect:"+Global.getAdminPath()+"/listing/itemSpecifics/?repage";
	}

}