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
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.PlatformCount;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.service.PlatformCountService;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.service.PlatformService;

/**
 * 平台Controller
 * 
 * @author mashuai
 * @version 2017-05-22
 */
@Controller
@RequestMapping(value = "${adminPath}/shops/platform")
public class PlatformController extends BaseController {

	@Autowired
	private PlatformService platformService;

	@Autowired
	private PlatformCountService platformcountService;

	@ModelAttribute
	public Platform get(@RequestParam(required = false) String id) {
		Platform entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = platformService.get(id);
		}
		if (entity == null) {
			entity = new Platform();
		}
		return entity;
	}

	/**
	 * 平台主页
	 * 
	 * @author yuxiang.xiong
	 * @param platform
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("shops:platform:view")
	@RequestMapping(value = { "list", "" })
	public String list(PlatformCount platform, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<PlatformCount> page = platformcountService.findPage(
				new Page<PlatformCount>(request, response), platform);
		model.addAttribute("page", page);
		return "ebay/platformAndsite/platformList";
	}

	/**
	 * 新增、修改平台
	 * 
	 * @author yuxiang.xiong
	 * @param platform
	 * @param model
	 * @return
	 */
	@RequiresPermissions("shops:platform:view")
	@RequestMapping(value = "form")
	public String form(Platform platform, Model model) {
		model.addAttribute("platform", platform);
		return "ebay/platformAndsite/platformForm";
	}

	/**
	 * 保存平台
	 * 
	 * @author yuxiang.xiong
	 * @param platform
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("shops:platform:edit")
	@RequestMapping(value = "save")
	public String save(Platform platform, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, platform)) {
			return form(platform, model);
		}
		String msg = "redirect:" + Global.getAdminPath()
				+ "/shops/platform/form/?repage";
		List<Platform> platInfos = platformService
				.getinfoByplatformName(platform.getName().trim());
		if (StringUtils.isBlank(platform.getId())) {

			if (!CollectionUtils.isEmpty(platInfos)) {
				addMessage(redirectAttributes, "该平台已经存在");
				return msg;
			}
		} else if (platInfos.size() > 1) {
			addMessage(redirectAttributes, "该平台已经存在");
			return msg;
		}
		platformService.save(platform);
		addMessage(redirectAttributes, "保存平台成功");
		return "redirect:" + Global.getAdminPath() + "/shops/platform/?repage";
	}

	@RequiresPermissions("shops:platform:edit")
	@RequestMapping(value = "delete")
	public String delete(Platform platform,
			RedirectAttributes redirectAttributes) {
		platformService.delete(platform);
		addMessage(redirectAttributes, "删除平台成功");
		return "redirect:" + Global.getAdminPath() + "/shops/platform/?repage";
	}

	@RequestMapping("selectList")
	@ResponseBody
	public List<Platform> getPlatformList() {
		List<Platform> form = platformService.findList(new Platform());
		return form;
	}

	/**
	 * 获取平台下拉
	 * 
	 * @author yuxiang.xiong
	 * @param platform
	 * @return
	 */
	@RequestMapping("selectPlat")
	@ResponseBody
	public List<Platform> getPlatformName(Platform platform) {
		List<Platform> form = platformService.findPlatByplatformId(platform);
		return form;
	}
}