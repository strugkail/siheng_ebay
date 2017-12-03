/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.pysynctask.web;

import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.utils.StringUtils;
import com.oigbuy.jeesite.common.web.BaseController;
import com.oigbuy.jeesite.modules.ebay.pysynctask.entity.PySyncTask;
import com.oigbuy.jeesite.modules.ebay.pysynctask.service.PySyncTaskService;

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
 * 普源同步任务Controller
 * @author mzm
 * @version 2017-07-10
 */
@Controller
@RequestMapping(value = "${adminPath}/pysynctask/pySyncTask")
public class PySyncTaskController extends BaseController {

	@Autowired
	private PySyncTaskService pySyncTaskService;
	
	@ModelAttribute
	public PySyncTask get(@RequestParam(required=false) String id) {
		PySyncTask entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = pySyncTaskService.get(id);
		}
		if (entity == null){
			entity = new PySyncTask();
		}
		return entity;
	}
	
	@RequiresPermissions("pysynctask:pySyncTask:view")
	@RequestMapping(value = {"list", ""})
	public String list(PySyncTask pySyncTask, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PySyncTask> page = pySyncTaskService.findPage(new Page<PySyncTask>(request, response), pySyncTask);
		model.addAttribute("page", page);
		return "modules/pysynctask/pySyncTaskList";
	}

	@RequiresPermissions("pysynctask:pySyncTask:view")
	@RequestMapping(value = "form")
	public String form(PySyncTask pySyncTask, Model model) {
		model.addAttribute("pySyncTask", pySyncTask);
		return "modules/pysynctask/pySyncTaskForm";
	}

	@RequiresPermissions("pysynctask:pySyncTask:edit")
	@RequestMapping(value = "save")
	public String save(PySyncTask pySyncTask, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, pySyncTask)){
			return form(pySyncTask, model);
		}
		pySyncTaskService.save(pySyncTask);
		addMessage(redirectAttributes, "保存普源同步任务成功");
		return "redirect:"+ Global.getAdminPath()+"/pysynctask/pySyncTask/?repage";
	}
	
	@RequiresPermissions("pysynctask:pySyncTask:edit")
	@RequestMapping(value = "delete")
	public String delete(PySyncTask pySyncTask, RedirectAttributes redirectAttributes) {
		pySyncTaskService.delete(pySyncTask);
		addMessage(redirectAttributes, "删除普源同步任务成功");
		return "redirect:"+ Global.getAdminPath()+"/pysynctask/pySyncTask/?repage";
	}

}