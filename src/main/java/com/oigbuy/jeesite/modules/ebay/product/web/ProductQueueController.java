/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.product.web;

import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.utils.StringUtils;
import com.oigbuy.jeesite.common.web.BaseController;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductQueue;
import com.oigbuy.jeesite.modules.ebay.product.service.ProductQueueService;
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
 * 对消息的增删改查Controller
 * @author AA
 * @version 2017-10-27
 */
@Controller
@RequestMapping(value = "${adminPath}/product/ProductQueue")
public class ProductQueueController extends BaseController {

	@Autowired
	private ProductQueueService tProductQueueService;
	
	@ModelAttribute
	public ProductQueue get(@RequestParam(required=false) String id) {
		ProductQueue entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tProductQueueService.get(id);
		}
		if (entity == null){
			entity = new ProductQueue();
		}
		return entity;
	}
	
	@RequiresPermissions("product:tProductQueue:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProductQueue tProductQueue, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProductQueue> page = tProductQueueService.findPage(new Page<ProductQueue>(request, response), tProductQueue);
		model.addAttribute("page", page);
		return "modules/ebay/product/tProductQueueList";
	}

	@RequiresPermissions("product:tProductQueue:view")
	@RequestMapping(value = "form")
	public String form(ProductQueue tProductQueue, Model model) {
		model.addAttribute("tProductQueue", tProductQueue);
		return "modules/ebay/product/tProductQueueForm";
	}

	@RequiresPermissions("product:tProductQueue:edit")
	@RequestMapping(value = "save")
	public String save(ProductQueue tProductQueue, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tProductQueue)){
			return form(tProductQueue, model);
		}
		tProductQueueService.save(tProductQueue);
		addMessage(redirectAttributes, "保存产品消息推送成功");
		return "redirect:"+Global.getAdminPath()+"/product/tProductQueue/?repage";
	}
	
	@RequiresPermissions("product:tProductQueue:edit")
	@RequestMapping(value = "delete")
	public String delete(ProductQueue tProductQueue, RedirectAttributes redirectAttributes) {
		tProductQueueService.delete(tProductQueue);
		addMessage(redirectAttributes, "删除产品消息推送成功");
		return "redirect:"+Global.getAdminPath()+"/product/tProductQueue/?repage";
	}

}