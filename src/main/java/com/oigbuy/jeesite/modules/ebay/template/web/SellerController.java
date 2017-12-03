package com.oigbuy.jeesite.modules.ebay.template.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.PlatformSite;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.Shops;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.service.PlatformSiteService;
import com.oigbuy.jeesite.modules.ebay.template.entity.Seller;
import com.oigbuy.jeesite.modules.ebay.template.service.SellerService;
import com.oigbuy.jeesite.modules.sys.entity.User;
import com.oigbuy.jeesite.modules.sys.utils.UserUtils;

/**
 * 卖家账户Controller
 * 
 * @author jalyn.zhang
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/template/seller")
public class SellerController extends BaseController {

	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private PlatformSiteService siteService;
	
	@ModelAttribute
	public Seller get(@RequestParam(required=false) String id) {
		Seller entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sellerService.get(id);
		}
		if (entity == null){
			entity = new Seller();
		}
		return entity;
	}

	@RequiresPermissions("template:seller:view")
	@RequestMapping(value = "list")
	public String list(Seller seller, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Seller> page = sellerService.findPage(new Page<Seller>(request, response), seller); 
		model.addAttribute("page", page);
		return "modules/template/sellerList";
	}
	
	@RequiresPermissions("template:seller:view")
	@RequestMapping(value = "form")
	public String form(Seller seller, Model model) {
		model.addAttribute("seller", seller);
		model.addAttribute("siteList", siteService.findList(null));
		model.addAttribute("shopList", this.sellerService.getShopList());
		
		return "modules/template/sellerForm";
	}

	@RequiresPermissions("template:seller:edit")
	@RequestMapping(value = "save")
	public String save(Seller seller, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, seller)){
			return form(seller, model);
		}
		User user = UserUtils.getUser();
		seller.setOperator(user.getName());
		seller.setOperateTime(new Date());
		sellerService.save(seller);
		addMessage(redirectAttributes, "保存卖家成功");
		return "redirect:"+Global.getAdminPath()+"/template/seller/list";
	}
	
	@RequiresPermissions("template:seller:edit")
	@RequestMapping(value = "delete")
	public String delete(Seller seller, RedirectAttributes redirectAttributes) {
		sellerService.delete(seller);
		addMessage(redirectAttributes, "删除卖家成功");
		return "redirect:"+Global.getAdminPath()+"/template/seller/list";
	}
	
	/***
	 * 
	 * @param siteId
	 * @return
	 */
	@RequestMapping("findBySiteId")
	@ResponseBody
	public List<Seller> findBySiteId(String siteId){
		Seller seller=new Seller();
		seller.setSiteId(Long.valueOf(siteId));
		return this.sellerService.findList(seller);
	}
	
	
}
