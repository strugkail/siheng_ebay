/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.ebayfee.web;

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
import com.oigbuy.jeesite.modules.ebay.ebayfee.entity.EbayFee;
import com.oigbuy.jeesite.modules.ebay.ebayfee.service.EbayFeeService;

/**
 * ebay最小费率Controller
 * @author strugkail
 * @version 2017-09-25
 */
@Controller
@RequestMapping(value = "${adminPath}/ebayfee/ebayFee")
public class EbayFeeController extends BaseController {

	@Autowired
	private EbayFeeService ebayFeeService;
	
	@ModelAttribute
	public EbayFee get(@RequestParam(required=false) String id) {
		EbayFee entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = ebayFeeService.get(id);
		}
		if (entity == null){
			entity = new EbayFee();
		}
		return entity;
	}
	
	@RequiresPermissions("ebayfee:ebayFee:view")
	@RequestMapping(value = {"list", ""})
	public String list(EbayFee ebayFee, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<EbayFee> page = ebayFeeService.findPage(new Page<EbayFee>(request, response), ebayFee); 
		model.addAttribute("page", page);
		return "ebay/ebayfee/ebayFeeList";
	}

	@RequiresPermissions("ebayfee:ebayFee:view")
	@RequestMapping(value = "form")
	public String form(EbayFee ebayFee, Model model) {
		model.addAttribute("ebayFee", ebayFee);
		return "ebay/ebayfee/ebayFeeForm";
	}

	@RequiresPermissions("ebayfee:ebayFee:edit")
	@RequestMapping(value = "save")
	public String save(EbayFee ebayFee, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, ebayFee)){
			return form(ebayFee, model);
		}
		ebayFeeService.save(ebayFee);
		addMessage(redirectAttributes, "保存用于虚拟仓利润测算字段提供成功");
		return "redirect:"+Global.getAdminPath()+"/ebayfee/ebayFee/?repage";
	}
	
	@RequiresPermissions("ebayfee:ebayFee:edit")
	@RequestMapping(value = "delete")
	public String delete(EbayFee ebayFee, RedirectAttributes redirectAttributes) {
		ebayFeeService.delete(ebayFee);
		addMessage(redirectAttributes, "删除用于虚拟仓利润测算字段提供成功");
		return "redirect:"+Global.getAdminPath()+"/ebayfee/ebayFee/?repage";
	}

}