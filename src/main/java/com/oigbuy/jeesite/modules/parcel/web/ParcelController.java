/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.parcel.web;

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
import com.oigbuy.jeesite.modules.parcel.entity.Parcel;
import com.oigbuy.jeesite.modules.parcel.service.ParcelService;

/**
 * 包裹表Controller
 * @author handong.wang
 * @version 2017-09-07
 */
@Controller
@RequestMapping(value = "${adminPath}/parcel/parcel")
public class ParcelController extends BaseController {

	@Autowired
	private ParcelService parcelService;
	
	@ModelAttribute
	public Parcel get(@RequestParam(required=false) String id) {
		Parcel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = parcelService.get(id);
		}
		if (entity == null){
			entity = new Parcel();
		}
		return entity;
	}
	
	@RequiresPermissions("parcel:parcel:view")
	@RequestMapping(value = {"list", ""})
	public String list(Parcel parcel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Parcel> page = parcelService.findPage(new Page<Parcel>(request, response), parcel); 
		model.addAttribute("page", page);
		return "modules/parcel/parcelList";
	}

	@RequiresPermissions("parcel:parcel:view")
	@RequestMapping(value = "form")
	public String form(Parcel parcel, Model model) {
		model.addAttribute("parcel", parcel);
		return "modules/parcel/parcelForm";
	}

	@RequiresPermissions("parcel:parcel:edit")
	@RequestMapping(value = "save")
	public String save(Parcel parcel, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, parcel)){
			return form(parcel, model);
		}
		parcelService.save(parcel);
		addMessage(redirectAttributes, "保存包裹表成功");
		return "redirect:"+Global.getAdminPath()+"/parcel/parcel/?repage";
	}
	
	@RequiresPermissions("parcel:parcel:edit")
	@RequestMapping(value = "delete")
	public String delete(Parcel parcel, RedirectAttributes redirectAttributes) {
		parcelService.delete(parcel);
		addMessage(redirectAttributes, "删除包裹表成功");
		return "redirect:"+Global.getAdminPath()+"/parcel/parcel/?repage";
	}

}