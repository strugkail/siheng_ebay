/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.logistics.web;

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
import com.oigbuy.jeesite.modules.ebay.logistics.entity.ShippingMode;
import com.oigbuy.jeesite.modules.ebay.logistics.service.ShippingModeService;

/**
 * 运输模板Controller
 * @author handong.wang
 * @version 2017-09-07
 */
@Controller
@RequestMapping(value = "${adminPath}/logistics/shippingMode")
public class ShippingModeController extends BaseController {

	@Autowired
	private ShippingModeService shippingModeService;
	
	@ModelAttribute
	public ShippingMode get(@RequestParam(required=false) String id) {
		ShippingMode entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shippingModeService.get(id);
		}
		if (entity == null){
			entity = new ShippingMode();
		}
		return entity;
	}
	
	@RequiresPermissions("logistics:shippingMode:view")
	@RequestMapping(value = {"list", ""})
	public String list(ShippingMode shippingMode, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ShippingMode> page = shippingModeService.findPage(new Page<ShippingMode>(request, response), shippingMode); 
		model.addAttribute("page", page);
		return "modules/logistics/shippingModeList";
	}

	@RequiresPermissions("logistics:shippingMode:view")
	@RequestMapping(value = "form")
	public String form(ShippingMode shippingMode, Model model) {
		model.addAttribute("shippingMode", shippingMode);
		return "modules/logistics/shippingModeForm";
	}

	@RequiresPermissions("logistics:shippingMode:edit")
	@RequestMapping(value = "save")
	public String save(ShippingMode shippingMode, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, shippingMode)){
			return form(shippingMode, model);
		}
		shippingModeService.save(shippingMode);
		addMessage(redirectAttributes, "保存运输模板成功");
		return "redirect:"+Global.getAdminPath()+"/logistics/shippingMode/?repage";
	}
	
	@RequiresPermissions("logistics:shippingMode:edit")
	@RequestMapping(value = "delete")
	public String delete(ShippingMode shippingMode, RedirectAttributes redirectAttributes) {
		shippingModeService.delete(shippingMode);
		addMessage(redirectAttributes, "删除运输模板成功");
		return "redirect:"+Global.getAdminPath()+"/logistics/shippingMode/?repage";
	}

}