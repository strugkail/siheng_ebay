/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.tags.web;

import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.utils.StringUtils;
import com.oigbuy.jeesite.common.web.BaseController;
import com.oigbuy.jeesite.modules.ebay.tags.entity.Tags;
import com.oigbuy.jeesite.modules.ebay.tags.entity.TagsType;
import com.oigbuy.jeesite.modules.ebay.tags.service.TagsService;
import com.oigbuy.jeesite.modules.ebay.tags.service.TagsTypeService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * 标签Controller
 * @author mashuai
 * @version 2017-05-22
 */
@Controller
@RequestMapping(value = "${adminPath}/tags/tags")
public class TagsController extends BaseController {

	@Autowired
	private TagsService tagsService;
	@Autowired
	private TagsTypeService tagsTypeService;
	
	@ModelAttribute
	public Tags get(@RequestParam(required=false) String id) {
		Tags entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tagsService.get(id);
		}
		if (entity == null){
			entity = new Tags();
		}
		return entity;
	}
	
	@RequiresPermissions("tags:tags:view")
	@RequestMapping(value = {"list", ""})
	public String list(Tags tags, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Tags> page = tagsService.findPage(new Page<Tags>(request, response), tags);
		List<TagsType> tagsTypeList = tagsTypeService.findList(null);
		model.addAttribute("page", page);
		model.addAttribute("tagsTypeList", tagsTypeList);
		return "modules/tags/tagsList";
	}

	@RequiresPermissions("tags:tags:view")
	@RequestMapping(value = "form")
	public String form(Tags tags, Model model) {
		List<TagsType> tagsTypeList = tagsTypeService.findList(null);
		model.addAttribute("tagsTypeList", tagsTypeList);
		return "modules/tags/tagsForm";
	}

	@RequiresPermissions("tags:tags:edit")
	@RequestMapping(value = "save")
	public String save(Tags tags, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tags)){
			return form(tags, model);
		}
		tagsService.save(tags);
		addMessage(redirectAttributes, "保存标签成功");
		return "redirect:"+ Global.getAdminPath()+"/tags/tags/?repage";
	}
	
	@RequiresPermissions("tags:tags:edit")
	@RequestMapping(value = "delete")
	public String delete(Tags tags, RedirectAttributes redirectAttributes) {
		tagsService.delete(tags);
		addMessage(redirectAttributes, "删除标签成功");
		return "redirect:"+ Global.getAdminPath()+"/tags/tags/?repage";
	}

	@RequestMapping("checkboxList")
	@ResponseBody
	public List<Tags> getIdAndName(){
		List<Tags> findIdAndNameList = tagsService.findTagsListByTypeFlag(null);
		return findIdAndNameList;
	}
}