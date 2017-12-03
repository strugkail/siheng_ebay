/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.listing.web;

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
import com.oigbuy.jeesite.modules.ebay.listing.entity.EbaySkuMapping;
import com.oigbuy.jeesite.modules.ebay.listing.service.EbaySkuMappingService;

/**
 * ebay sku 映射Controller
 * @author bill.xu
 * @version 2017-09-22
 */
@Controller
@RequestMapping(value = "${adminPath}/listing/ebaySkuMapping")
public class EbaySkuMappingController extends BaseController {

	@Autowired
	private EbaySkuMappingService ebaySkuMappingService;
	
	@ModelAttribute
	public EbaySkuMapping get(@RequestParam(required=false) String id) {
		EbaySkuMapping entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = ebaySkuMappingService.get(id);
		}
		if (entity == null){
			entity = new EbaySkuMapping();
		}
		return entity;
	}
	
	@RequiresPermissions("listing:ebaySkuMapping:view")
	@RequestMapping(value = {"list", ""})
	public String list(EbaySkuMapping ebaySkuMapping, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<EbaySkuMapping> page = ebaySkuMappingService.findPage(new Page<EbaySkuMapping>(request, response), ebaySkuMapping); 
		model.addAttribute("page", page);
		return "ebay/listing/ebaySkuMappingList";
	}

	@RequiresPermissions("listing:ebaySkuMapping:view")
	@RequestMapping(value = "form")
	public String form(EbaySkuMapping ebaySkuMapping, Model model) {
		model.addAttribute("ebaySkuMapping", ebaySkuMapping);
		return "ebay/listing/ebaySkuMappingForm";
	}

	@RequiresPermissions("listing:ebaySkuMapping:edit")
	@RequestMapping(value = "save")
	public String save(EbaySkuMapping ebaySkuMapping, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, ebaySkuMapping)){
			return form(ebaySkuMapping, model);
		}
		ebaySkuMappingService.save(ebaySkuMapping);
		addMessage(redirectAttributes, "保存ebay sku 映射成功");
		return "redirect:"+Global.getAdminPath()+"/listing/ebaySkuMapping/?repage";
	}
	
	@RequiresPermissions("listing:ebaySkuMapping:edit")
	@RequestMapping(value = "delete")
	public String delete(EbaySkuMapping ebaySkuMapping, RedirectAttributes redirectAttributes) {
		ebaySkuMappingService.delete(ebaySkuMapping);
		addMessage(redirectAttributes, "删除ebay sku 映射成功");
		return "redirect:"+Global.getAdminPath()+"/listing/ebaySkuMapping/?repage";
	}

}