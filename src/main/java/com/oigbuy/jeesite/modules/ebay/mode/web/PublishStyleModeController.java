/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.mode.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.utils.StringUtils;
import com.oigbuy.jeesite.common.web.BaseController;
import com.oigbuy.jeesite.modules.ebay.mode.entity.PublishStyleMode;
import com.oigbuy.jeesite.modules.ebay.mode.service.PublishStyleModeService;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.service.PlatformSiteService;

/**
 * 刊登风格 模板Controller
 * @author bill.xu
 * @version 2017-09-05
 */
@Controller
@RequestMapping(value = "${adminPath}/mode/publishStyleMode")
public class PublishStyleModeController extends BaseController {

	@Autowired
	private PublishStyleModeService publishStyleModeService;
	
	@Autowired
	private PlatformSiteService platformSiteService;
	
	@ModelAttribute
	public PublishStyleMode get(@RequestParam(required=false) String id) {
		PublishStyleMode entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = publishStyleModeService.get(id);
		}
		if (entity == null){
			entity = new PublishStyleMode();
		}
		return entity;
	}
	
	//@RequiresPermissions("mode:publishStyleMode:view")
	@RequestMapping(value = {"list", ""})
	public String list(PublishStyleMode publishStyleMode, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PublishStyleMode> page = publishStyleModeService.findPage(new Page<PublishStyleMode>(request, response), publishStyleMode); 
		model.addAttribute("page", page);
		return "modules/mode/publishStyleModeList";
	}

	//@RequiresPermissions("mode:publishStyleMode:view")
	@RequestMapping(value = "form")
	public String form(PublishStyleMode publishStyleMode, Model model) {
		model.addAttribute("publishStyleMode", publishStyleMode);
		model.addAttribute("content", publishStyleMode.getContent());
		model.addAttribute("siteList", platformSiteService.getListByPlatName(Global.TERRACE_TYPE_EBAY));
		return "modules/mode/publishStyleModeForm";
	}

	//@RequiresPermissions("mode:publishStyleMode:edit")
	@RequestMapping(value = "save")
	public String save(PublishStyleMode publishStyleMode,@RequestParam("content") String content, Model model, RedirectAttributes redirectAttributes) {
	  
		try {
	
		if (!beanValidator(model, publishStyleMode)){
			return form(publishStyleMode, model);
		}
		if(StringUtils.isBlank(publishStyleMode.getId())){
			publishStyleMode.setIsNewRecord(true);
			publishStyleMode.setId(Global.getID().toString());
		}
		publishStyleModeService.save(publishStyleMode);
		addMessage(redirectAttributes, "保存模板成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:"+Global.getAdminPath()+"/mode/publishStyleMode/?repage";
	}
	
	//@RequiresPermissions("mode:publishStyleMode:edit")
	@RequestMapping(value = "delete")
	public String delete(PublishStyleMode publishStyleMode, RedirectAttributes redirectAttributes) {
		publishStyleModeService.delete(publishStyleMode);
		addMessage(redirectAttributes, "删除模板成功");
		return "redirect:"+Global.getAdminPath()+"/mode/publishStyleMode/?repage";
	}

	
	/***
	 * 编辑页面 ajax 获取数据
	 * @param id
	 * @return
	 */
	 @RequestMapping("getOne")
	 @ResponseBody
     public PublishStyleMode getOne(String id){
		PublishStyleMode mode= this.publishStyleModeService.get(id);
		return mode;
	}
	
	
}