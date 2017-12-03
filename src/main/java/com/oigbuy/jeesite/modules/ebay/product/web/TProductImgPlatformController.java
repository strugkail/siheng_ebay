/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.product.web;

import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.utils.StringUtils;
import com.oigbuy.jeesite.common.web.BaseController;
import com.oigbuy.jeesite.modules.ebay.product.entity.TProductImgPlatform;
import com.oigbuy.jeesite.modules.ebay.product.service.TProductImgPlatformService;
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
 * 产品图片Controller
 * @author AA
 * @version 2017-10-26
 */
@Controller
@RequestMapping(value = "${adminPath}/entity/tProductImgPlatform")
public class TProductImgPlatformController extends BaseController {

	@Autowired
	private TProductImgPlatformService tProductImgPlatformService;
	
	@ModelAttribute
	public TProductImgPlatform get(@RequestParam(required=false) String id) {
		TProductImgPlatform entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tProductImgPlatformService.get(id);
		}
		if (entity == null){
			entity = new TProductImgPlatform();
		}
		return entity;
	}
	
	@RequiresPermissions("entity:tProductImgPlatform:view")
	@RequestMapping(value = {"list", ""})
	public String list(TProductImgPlatform tProductImgPlatform, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TProductImgPlatform> page = tProductImgPlatformService.findPage(new Page<TProductImgPlatform>(request, response), tProductImgPlatform); 
		model.addAttribute("page", page);
		return "modules/entity/tProductImgPlatformList";
	}

	@RequiresPermissions("entity:tProductImgPlatform:view")
	@RequestMapping(value = "form")
	public String form(TProductImgPlatform tProductImgPlatform, Model model) {
		model.addAttribute("tProductImgPlatform", tProductImgPlatform);
		return "modules/entity/tProductImgPlatformForm";
	}

	@RequiresPermissions("entity:tProductImgPlatform:edit")
	@RequestMapping(value = "save")
	public String save(TProductImgPlatform tProductImgPlatform, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tProductImgPlatform)){
			return form(tProductImgPlatform, model);
		}
		tProductImgPlatformService.save(tProductImgPlatform);
		addMessage(redirectAttributes, "保存保存产品图片成功");
		return "redirect:"+Global.getAdminPath()+"/entity/tProductImgPlatform/?repage";
	}
	
	@RequiresPermissions("entity:tProductImgPlatform:edit")
	@RequestMapping(value = "delete")
	public String delete(TProductImgPlatform tProductImgPlatform, RedirectAttributes redirectAttributes) {
		tProductImgPlatformService.delete(tProductImgPlatform);
		addMessage(redirectAttributes, "删除保存产品图片成功");
		return "redirect:"+Global.getAdminPath()+"/entity/tProductImgPlatform/?repage";
	}

}