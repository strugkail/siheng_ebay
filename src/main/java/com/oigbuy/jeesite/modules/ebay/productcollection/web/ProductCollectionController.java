/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.productcollection.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.oigbuy.jeesite.common.process.entity.EbayProcessBusiness;
import com.oigbuy.jeesite.common.process.entity.ProcessBean;
import com.oigbuy.jeesite.common.process.service.GeneralProcessService;
import com.oigbuy.jeesite.common.repeaturl.RepeatUrlData;
import com.oigbuy.jeesite.common.service.BaseService;
import com.oigbuy.jeesite.common.utils.JsonResponseModel;
import com.oigbuy.jeesite.common.utils.StringUtils;
import com.oigbuy.jeesite.common.web.BaseController;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.PlatformSite;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.service.PlatformSiteService;
import com.oigbuy.jeesite.modules.ebay.productcollection.entity.ProductCollection;
import com.oigbuy.jeesite.modules.ebay.productcollection.entity.ProductCollectionLog;
import com.oigbuy.jeesite.modules.ebay.productcollection.service.ProductCollectionLogService;
import com.oigbuy.jeesite.modules.ebay.productcollection.service.ProductCollectionService;
import com.oigbuy.jeesite.modules.ebay.sellgroup.entity.Group;
import com.oigbuy.jeesite.modules.ebay.sellgroup.service.GroupService;
import com.oigbuy.jeesite.modules.sys.entity.User;
import com.oigbuy.jeesite.modules.sys.utils.UserUtils;

/**
 * 竞品采集Controller
 * @author 王佳点
 * @version 2017-09-04
 */
@Controller
@RequestMapping(value = "${adminPath}/productcollection/productCollection")
public class ProductCollectionController extends BaseController {

	@Autowired
	private ProductCollectionService productCollectionService;
	@Autowired
	private PlatformSiteService platformSiteService;
	@Autowired
	private ProductCollectionLogService productCollectionLogService;
	@Autowired
	private GeneralProcessService generalProcessService;
	@Autowired
	private GroupService groupService;
	
	@ModelAttribute
	public ProductCollection get(@RequestParam(required=false) String id) {
		ProductCollection entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productCollectionService.get(id);
		}
		if (entity == null){
			entity = new ProductCollection();
		}
		return entity;
	}
    @RequestMapping(value = "selectBySiteId")
    public void selectBySiteId(String siteId,HttpServletResponse response) throws IOException{
	    System.out.println("----------------------------------------------");
	    PlatformSite platformSite1=new PlatformSite();
	    platformSite1.setId(siteId);
        PlatformSite platformSite= platformSiteService.selectBySiteId(platformSite1);
        PrintWriter pw = response.getWriter();
        pw.print(platformSite.getSiteShortName());;
    }

	@RequiresPermissions("productcollection:productCollection:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProductCollection productCollection, HttpServletRequest request, HttpServletResponse response, Model model) {
	
		// 数据权限设置
		User user = UserUtils.getUser();
		BaseService.dataScopeFilter(productCollection, "dsf", null, "a.OPERATOR=" + user.getId());
		
		Page<ProductCollection> page = productCollectionService.findPage(new Page<ProductCollection>(request, response), productCollection); 
		//获取eBay平台的所有站点
		List<PlatformSite> siteList = platformSiteService.findListByPlatformId(Global.EBAY_PLATFORM_CODE);
		//获取所有销售组
		List<Group> groupList = groupService.findList(null);
		//获取采集时间区间
		String dateStart = request.getParameter("dateStart");
		String dateEnd = request.getParameter("dateEnd");
		
		model.addAttribute("page", page);
		model.addAttribute("siteList", siteList);
		model.addAttribute("groupList", groupList);
		model.addAttribute("dateStart", dateStart);
		model.addAttribute("dateEnd", dateEnd);
		return "ebay/productcollection/productCollectionList";
	}

	@RequiresPermissions("productcollection:productCollection:view")
	@RequestMapping(value = "form")
	public String form(ProductCollection productCollection, Model model ) {
		
		String id = productCollection.getId();
		//获取操作日志
		if (StringUtils.isNotBlank(id)) {
			ProductCollectionLog productCollectionLog = new ProductCollectionLog();
			productCollectionLog.setCollectId(id);
			List<ProductCollectionLog> logList = productCollectionLogService.findList(productCollectionLog);
			model.addAttribute("logList", logList);
		}
		
		//获取eBay平台的所有站点
		List<PlatformSite> siteList = platformSiteService.findListByPlatformId(Global.EBAY_PLATFORM_CODE);
		//获取所有销售组
		List<Group> groupList = groupService.findList(null);
		model.addAttribute("productCollection", productCollection);
		model.addAttribute("siteList", siteList);
		model.addAttribute("groupList", groupList);
		return "ebay/productcollection/productCollectionForm";
	}

	
	@RequiresPermissions("productcollection:productCollection:view")
	@RequestMapping(value = "detail")
	public String detail(ProductCollection productCollection, Model model) {
		
		String id = productCollection.getId();
		//获取操作日志
		if (StringUtils.isNotBlank(id)) {
			ProductCollectionLog productCollectionLog = new ProductCollectionLog();
			productCollectionLog.setCollectId(id);
			List<ProductCollectionLog> logList = productCollectionLogService.findList(productCollectionLog);
			model.addAttribute("logList", logList);
		}
		
		//获取eBay平台的所有站点
		List<PlatformSite> siteList = platformSiteService.findListByPlatformId(Global.EBAY_PLATFORM_CODE);
		//获取所有销售组
		List<Group> groupList = groupService.findList(null);
		model.addAttribute("productCollection", productCollection);
		model.addAttribute("siteList", siteList);
		model.addAttribute("groupList", groupList);
		return "ebay/productcollection/productCollectionDetail";
	}

	@RequiresPermissions("productcollection:productCollection:edit")
	@RequestMapping(value = "save")
	@RepeatUrlData
	public String save(ProductCollection productCollection, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, productCollection)){
			return form(productCollection, model);
		}
		//保存竞品采集
		productCollectionService.save(productCollection);
		//判断是保存还是开发
		if ((Global.ZERO).equals(productCollection.getType())) {
			addMessage(redirectAttributes, "保存竞品采集成功");
		}else {
			//保存操作日志
			ProductCollectionLog productCollectionLog = new ProductCollectionLog();
			productCollectionLog.setId(Global.getID().toString());
			productCollectionLog.setCreateBy(UserUtils.getUser());
			productCollectionLog.setCreateDate(new Date());
			productCollectionLog.setRemarks(productCollection.getRemark());
			productCollectionLog.setCollectId(productCollection.getId());
			productCollectionLog.setIsNewRecord(true);
			productCollectionLogService.save(productCollectionLog);
			
			//更新竞品状态
			productCollection.setStatus(Global.PRODUCT_COLLECT_STATUS_CALCULATION);
			productCollectionService.saveOrUpdate(productCollection);
			
			//为业务表实体设置数据
			EbayProcessBusiness ebayProcessBusiness = new EbayProcessBusiness();
			ebayProcessBusiness.setImgUrl(productCollection.getImgUrl());
			ebayProcessBusiness.setSiteId(productCollection.getSiteId());
			ebayProcessBusiness.setSaleType(productCollection.getSaleTypeId());
			ebayProcessBusiness.setProductName(productCollection.getProductName());
			ebayProcessBusiness.setProductUrl(productCollection.getProductUrl());
			ebayProcessBusiness.setSaleGroupId(productCollection.getSaleGroupId());
			ebayProcessBusiness.setCreateName(UserUtils.getUser().getName());
			ebayProcessBusiness.setCreateTime(new Date());
			ebayProcessBusiness.setItemId(productCollection.getItemId());
			ebayProcessBusiness.setBusinessId(productCollection.getId());
			//将业务表实体设置到流程实体中
			ProcessBean<EbayProcessBusiness> bean = new ProcessBean<EbayProcessBusiness>();
			Map<String, Object> vars = new HashMap<String, Object>();
			vars.put(Global.SALE_TYPE, productCollection.getSaleTypeId());
			bean.setData(ebayProcessBusiness);
			bean.setVars(vars);
			//启动流程
			generalProcessService.startProcess(bean);
			
			addMessage(redirectAttributes, "开发竞品采集成功");
			
		}
		
		return "redirect:"+Global.getAdminPath()+"/productcollection/productCollection/";
	}
	
	@RequiresPermissions("productcollection:productCollection:edit")
	@RequestMapping(value = "delete")
	public String delete(ProductCollection productCollection, RedirectAttributes redirectAttributes) {
		productCollectionService.delete(productCollection);
		addMessage(redirectAttributes, "删除竞品采集成功");
		return "redirect:"+Global.getAdminPath()+"/productcollection/productCollection/";
	}
	
	@ResponseBody
	@RequestMapping(value = "getMainImageUrl")
	public JsonResponseModel getMainImageUrl(String itemId){
		JsonResponseModel result=new JsonResponseModel();
		try {
			String mainImageUrl = productCollectionService.getMainImageUrl(itemId);
			return result.success(mainImageUrl);
		} catch (Exception e) {
			return result.fail(e.getMessage());
		}
		
	}


}