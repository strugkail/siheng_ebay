/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.mode.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.oigbuy.jeesite.modules.ebay.mode.entity.ShieldCountryMode;
import com.oigbuy.jeesite.modules.ebay.mode.service.ShieldCountryModeService;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.PlatformSite;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.service.PlatformSiteService;

/**
 * 屏蔽目的地模板Controller
 * @author bill.xu
 * @version 2017-09-05
 */
@Controller
@RequestMapping(value = "${adminPath}/mode/shieldCountryMode")
public class ShieldCountryModeController extends BaseController {

	/***
	 * 屏蔽目的国家
	 */
	@Autowired
	private ShieldCountryModeService shieldCountryModeService;
	
	/***
	 * 国家
	 */
	@Autowired
	private CountryService countryService;
	
	/***
	 * 站点
	 */
	@Autowired
	private PlatformSiteService PlatformSiteService;
	
	@ModelAttribute
	public ShieldCountryMode get(@RequestParam(required=false) String id) {
		ShieldCountryMode entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shieldCountryModeService.get(id);
		}
		if (entity == null){
			entity = new ShieldCountryMode();
		}
		return entity;
	}
	
	//@RequiresPermissions("mode:shieldCountryMode:view")
	@RequestMapping(value = {"list", ""})
	public String list(ShieldCountryMode shieldCountryMode, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ShieldCountryMode> page = shieldCountryModeService.findPage(new Page<ShieldCountryMode>(request, response), shieldCountryMode); 
		model.addAttribute("page", page);
		return "modules/mode/shieldCountryModeList";
	}

	//@RequiresPermissions("mode:shieldCountryMode:view")
	@RequestMapping(value = "form")
	public String form(ShieldCountryMode shieldCountryMode, Model model) {
		//国家的列表 TODO 
	    List<Country>	countryList = countryService.findList(null);
		//销售类型  TODO 
		//站点  集合  TODO 
	    List<PlatformSite> siteList = PlatformSiteService.findList(null);
		
		if(StringUtils.isBlank(shieldCountryMode.getId())){//新增
			
		}else{//修改
			for (Country country : countryList) {//通过设置 disable 设置页面的选中状态
				if(StringUtils.isNotBlank(shieldCountryMode.getCountryId()) && shieldCountryMode.getCountryId().contains(country.getId())){
					country.setDisable("1");//设置选中
				}else{
					country.setDisable("0");//设置未选中
				}
			}
			
		}
		//TODO 发货仓库 集合
		
		
		
		
		model.addAttribute("shieldCountryMode", shieldCountryMode);
		model.addAttribute("countryList", countryList);
		model.addAttribute("siteList", siteList);
		return "modules/mode/shieldCountryModeForm";
	}

	
	/***
	 * 
	 * @param shieldCountryMode
	 * @param ids   选择的国家id字符串
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	//@RequiresPermissions("mode:shieldCountryMode:edit")
	@RequestMapping(value = "save")
	public String save(ShieldCountryMode shieldCountryMode,Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, shieldCountryMode)){
			return form(shieldCountryMode, model);
		}if(StringUtils.isBlank(shieldCountryMode.getId())){
			shieldCountryMode.setIsNewRecord(true);
			shieldCountryMode.setId(Global.getID().toString());
		}
		shieldCountryModeService.save(shieldCountryMode);
		addMessage(redirectAttributes, "保存屏蔽目的地模板成功");
		return "redirect:"+Global.getAdminPath()+"/mode/shieldCountryMode/?repage";
	}
	
	//@RequiresPermissions("mode:shieldCountryMode:edit")
	@RequestMapping(value = "delete")
	public String delete(ShieldCountryMode shieldCountryMode, RedirectAttributes redirectAttributes) {
		shieldCountryModeService.delete(shieldCountryMode);
		addMessage(redirectAttributes, "删除屏蔽目的地模板成功");
		return "redirect:"+Global.getAdminPath()+"/mode/shieldCountryMode/?repage";
	}

}