/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.platformAndsite.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.util.CollectionUtils;
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
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.Platform;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.PlatformSite;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.PlatformSiteCount;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.service.PlatformService;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.service.PlatformSiteCountService;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.service.PlatformSiteService;

/**
 * 站点Controller
 * @author mashuai
 * @version 2017-05-22
 */
@Controller
@RequestMapping(value = "${adminPath}/shops/platformSite")
public class PlatformSiteController extends BaseController {

	@Autowired
	private PlatformSiteService platformSiteService;
	
	@Autowired
	private PlatformSiteCountService platformSiteCountService;
	
	@Autowired
	private PlatformService platformService;
	
	@ModelAttribute
	public PlatformSite get(@RequestParam(required=false) String id) {
		PlatformSite entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = platformSiteService.get(id);
		}
		if (entity == null){
			entity = new PlatformSite();
		}
		return entity;
	}
	
	/**
	 * 站点列表主页
	 * @author yuxiang.xiong
	 * @param platformSite
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("shops:platformSite:view")
	@RequestMapping(value = {"list", ""})
	public String list(PlatformSiteCount platformSite, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Platform> form = platformService.findList(new Platform());
		model.addAttribute("tableList", form);
		Page<PlatformSiteCount> page = platformSiteCountService.findPage(new Page<PlatformSiteCount>(request, response), platformSite); 
		model.addAttribute("page", page);
		model.addAttribute("platformSite", platformSite);
		return "ebay/platformAndsite/platformSiteList";
	}
     
	/**
	 * 新增、修改站点页面
	 * @author yuxiang.xiong
	 * @param platformSite
	 * @param model
	 * @return
	 */
	@RequiresPermissions("shops:platformSite:view")
	@RequestMapping(value = "form")
	public String form(PlatformSite platformSite, Model model) {
		List<Platform> form = platformService.findList(new Platform());
		model.addAttribute("tableList", form);
		model.addAttribute("platformSite", platformSite);
		return "ebay/platformAndsite/platformSiteForm";
	}
    
	/**
	 * 保存站点
	 * @author yuxiang.xiong
	 *
	 * @param platformSite
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("shops:platformSite:edit")
	@RequestMapping(value = "save")
	public String save(PlatformSite platformSite, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, platformSite)){
			return form(platformSite, model);
		}
		String msg = "redirect:"+Global.getAdminPath()+"/shops/platformSite/form/?repage";
		List<PlatformSite> siteList = platformSiteService.findListByPlatformIdandSiteName(platformSite.getPlatformId().toString(), platformSite.getSiteName().trim());
		if(StringUtils.isBlank(platformSite.getId())){
			
			if(!CollectionUtils.isEmpty(siteList)){
				addMessage(redirectAttributes, "该平台下的站点已经存在");
				return msg;
			}
		}else if(siteList.size()>1){
			addMessage(redirectAttributes, "该平台下的站点已经存在");
			return msg;
		}
		platformSiteService.save(platformSite);
		addMessage(redirectAttributes, "保存站点成功");
		return "redirect:"+Global.getAdminPath()+"/shops/platformSite/?repage";
	}
	/**
	 * 删除 站点
	 * @author yuxiang.xiong
	 * @param platformSite
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("shops:platformSite:edit")
	@RequestMapping(value = "delete")
	public String delete(PlatformSite platformSite, RedirectAttributes redirectAttributes) {
		platformSiteService.delete(platformSite);
		addMessage(redirectAttributes, "删除站点成功");
		return "redirect:"+Global.getAdminPath()+"/shops/platformSite/?repage";
	}
	/**
	 * 获取站点
	 * @author yuxiang.xiong
	 * @param platformId
	 * @return
	 */
	@RequestMapping("selectList")
	@ResponseBody
	public List<PlatformSite> getPlatformList(String platformId){
		return platformSiteService.findListByPlatformId(platformId); //通过平台id获取站点
	}
}