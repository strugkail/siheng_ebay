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
import com.oigbuy.jeesite.modules.ebay.listing.entity.EbayListingImg;
import com.oigbuy.jeesite.modules.ebay.listing.service.EbayListingImgService;

/**
 * ebay listing 图片Controller
 * @author bill.xu
 * @version 2017-09-22
 */
@Controller
@RequestMapping(value = "${adminPath}/listing/ebayListingImg")
public class EbayListingImgController extends BaseController {

	@Autowired
	private EbayListingImgService ebayListingImgService;
	
	@ModelAttribute
	public EbayListingImg get(@RequestParam(required=false) String id) {
		EbayListingImg entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = ebayListingImgService.get(id);
		}
		if (entity == null){
			entity = new EbayListingImg();
		}
		return entity;
	}
	
	@RequiresPermissions("listing:ebayListingImg:view")
	@RequestMapping(value = {"list", ""})
	public String list(EbayListingImg ebayListingImg, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<EbayListingImg> page = ebayListingImgService.findPage(new Page<EbayListingImg>(request, response), ebayListingImg); 
		model.addAttribute("page", page);
		return "ebay/listing/ebayListingImgList";
	}

	@RequiresPermissions("listing:ebayListingImg:view")
	@RequestMapping(value = "form")
	public String form(EbayListingImg ebayListingImg, Model model) {
		model.addAttribute("ebayListingImg", ebayListingImg);
		return "ebay/listing/ebayListingImgForm";
	}

	@RequiresPermissions("listing:ebayListingImg:edit")
	@RequestMapping(value = "save")
	public String save(EbayListingImg ebayListingImg, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, ebayListingImg)){
			return form(ebayListingImg, model);
		}
		ebayListingImgService.save(ebayListingImg);
		addMessage(redirectAttributes, "保存ebay listing 图片成功");
		return "redirect:"+Global.getAdminPath()+"/listing/ebayListingImg/?repage";
	}
	
	@RequiresPermissions("listing:ebayListingImg:edit")
	@RequestMapping(value = "delete")
	public String delete(EbayListingImg ebayListingImg, RedirectAttributes redirectAttributes) {
		ebayListingImgService.delete(ebayListingImg);
		addMessage(redirectAttributes, "删除ebay listing 图片成功");
		return "redirect:"+Global.getAdminPath()+"/listing/ebayListingImg/?repage";
	}

}