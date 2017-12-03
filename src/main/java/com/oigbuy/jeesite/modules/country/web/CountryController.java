/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.country.web;

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
import com.oigbuy.jeesite.modules.country.entity.Country;
import com.oigbuy.jeesite.modules.country.service.CountryService;

/**
 * 国家管理Controller
 * @author 王佳点
 * @version 2017-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/country/country")
public class CountryController extends BaseController {

	@Autowired
	private CountryService countryService;
	
	@ModelAttribute
	public Country get(@RequestParam(required=false) String id) {
		Country entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = countryService.get(id);
		}
		if (entity == null){
			entity = new Country();
		}
		return entity;
	}
	
	@RequiresPermissions("country:country:view")
	@RequestMapping(value = {"list", ""})
	public String list(Country country, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Country> page = countryService.findPage(new Page<Country>(request, response), country); 
		model.addAttribute("page", page);
		return "modules/country/countryList";
	}

	@RequiresPermissions("country:country:view")
	@RequestMapping(value = "form")
	public String form(Country country, Model model) {
		model.addAttribute("country", country);
		return "modules/country/countryForm";
	}

	@RequiresPermissions("country:country:edit")
	@RequestMapping(value = "save")
	public String save(Country country, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, country)){
			return form(country, model);
		}
		countryService.save(country);
		addMessage(redirectAttributes, "保存国家成功");
		return "redirect:"+Global.getAdminPath()+"/country/country/?repage";
	}
	
	@RequiresPermissions("country:country:edit")
	@RequestMapping(value = "delete")
	public String delete(Country country, RedirectAttributes redirectAttributes) {
		countryService.delete(country);
		addMessage(redirectAttributes, "删除国家成功");
		return "redirect:"+Global.getAdminPath()+"/country/country/?repage";
	}

}