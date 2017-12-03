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
import com.oigbuy.jeesite.modules.ebay.listing.entity.EbayListingPrice;
import com.oigbuy.jeesite.modules.ebay.listing.service.EbayListingPriceService;

/**
 * ebay  listing 价格Controller
 * @author bill.xu
 * @version 2017-09-22
 */
@Controller
@RequestMapping(value = "${adminPath}/listing/ebayListingPrice")
public class EbayListingPriceController extends BaseController {

	@Autowired
	private EbayListingPriceService ebayListingPriceService;
	
	@ModelAttribute
	public EbayListingPrice get(@RequestParam(required=false) String id) {
		EbayListingPrice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = ebayListingPriceService.get(id);
		}
		if (entity == null){
			entity = new EbayListingPrice();
		}
		return entity;
	}
	
	@RequiresPermissions("listing:ebayListingPrice:view")
	@RequestMapping(value = {"list", ""})
	public String list(EbayListingPrice ebayListingPrice, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<EbayListingPrice> page = ebayListingPriceService.findPage(new Page<EbayListingPrice>(request, response), ebayListingPrice); 
		model.addAttribute("page", page);
		return "ebay/listing/ebayListingPriceList";
	}

	@RequiresPermissions("listing:ebayListingPrice:view")
	@RequestMapping(value = "form")
	public String form(EbayListingPrice ebayListingPrice, Model model) {
		model.addAttribute("ebayListingPrice", ebayListingPrice);
		return "ebay/listing/ebayListingPriceForm";
	}

	@RequiresPermissions("listing:ebayListingPrice:edit")
	@RequestMapping(value = "save")
	public String save(EbayListingPrice ebayListingPrice, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, ebayListingPrice)){
			return form(ebayListingPrice, model);
		}
		ebayListingPriceService.save(ebayListingPrice);
		addMessage(redirectAttributes, "保存ebay  listing 价格成功");
		return "redirect:"+Global.getAdminPath()+"/listing/ebayListingPrice/?repage";
	}
	
	@RequiresPermissions("listing:ebayListingPrice:edit")
	@RequestMapping(value = "delete")
	public String delete(EbayListingPrice ebayListingPrice, RedirectAttributes redirectAttributes) {
		ebayListingPriceService.delete(ebayListingPrice);
		addMessage(redirectAttributes, "删除ebay  listing 价格成功");
		return "redirect:"+Global.getAdminPath()+"/listing/ebayListingPrice/?repage";
	}

}