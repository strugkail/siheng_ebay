/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.tags.web;

import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.utils.StringUtils;
import com.oigbuy.jeesite.common.web.BaseController;
import com.oigbuy.jeesite.modules.ebay.tags.entity.TagsType;
import com.oigbuy.jeesite.modules.ebay.tags.service.TagsTypeService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 标签类型Controller
 * @author mashuai
 * @version 2017-05-22
 */
@Controller
@RequestMapping(value = "${adminPath}/tags/tagsType")
public class TagsTypeController extends BaseController {

	@Autowired
	private TagsTypeService tagsTypeService;
	
//	@Autowired
//	private MigrateDataService migrateDataService;
//
	@ModelAttribute
	public TagsType get(@RequestParam(required=false) String id) {
		TagsType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tagsTypeService.get(id);
		}
		if (entity == null){
			entity = new TagsType();
		}
		return entity;
	}
	
	@RequiresPermissions("tags:tagsType:view")
	@RequestMapping(value = {"list", ""})
	public String list(TagsType tagsType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TagsType> page = tagsTypeService.findPage(new Page<TagsType>(request, response), tagsType);
		model.addAttribute("page", page);
		return "modules/tags/tagsTypeList";
	}
//ss
	@RequiresPermissions("tags:tagsType:view")
	@RequestMapping(value = "form")
	public String form(TagsType tagsType, Model model) {
		model.addAttribute("tagsType", tagsType);
		return "modules/tags/tagsTypeForm";
	}

	@RequiresPermissions("tags:tagsType:edit")
	@RequestMapping(value = "save")
	public String save(TagsType tagsType, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tagsType)){
			return form(tagsType, model);
		}
		tagsTypeService.save(tagsType);
		addMessage(redirectAttributes, "保存标签类型成功");
		return "redirect:"+ Global.getAdminPath()+"/tags/tagsType/?repage";
	}
	
	@RequiresPermissions("tags:tagsType:edit")
	@RequestMapping(value = "delete")
	public String delete(TagsType tagsType, RedirectAttributes redirectAttributes) {
		tagsTypeService.delete(tagsType);
		addMessage(redirectAttributes, "删除标签类型成功");
		return "redirect:"+ Global.getAdminPath()+"/tags/tagsType/?repage";
	}
	
	@RequestMapping("selectList")
	@ResponseBody
	public List<TagsType> getTagsTypeList(){
		return tagsTypeService.findList(new TagsType());
	}
	
//	@RequestMapping(value="analyzeDocumentController",method=RequestMethod.POST)
//	public String ananlyzeDomentController(@RequestParam("file") MultipartFile multipart) throws Exception{
//		File file = new File( multipart.getOriginalFilename());
//        try {
//			multipart.transferTo(file);
//		} catch (IllegalStateException | IOException e) {
//			throw e;
//		}
//		migrateDataService.analyzeCommodityDocument(file);
//		return "";
//	}

}