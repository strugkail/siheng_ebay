package com.oigbuy.jeesite.modules.ebay.product.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.oigbuy.jeesite.common.utils.ObjectUtils;
import com.oigbuy.jeesite.common.utils.StringUtils;
import com.oigbuy.jeesite.common.web.BaseController;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.PlatformSite;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.service.PlatformSiteService;
import com.oigbuy.jeesite.modules.ebay.product.entity.SupplierRecommen;
import com.oigbuy.jeesite.modules.ebay.product.service.SupplierRecomService;
import com.oigbuy.jeesite.modules.ebay.productcollection.entity.ProductCollection;
import com.oigbuy.jeesite.modules.ebay.productcollection.entity.ProductCollectionLog;
import com.oigbuy.jeesite.modules.ebay.productcollection.service.ProductCollectionLogService;
import com.oigbuy.jeesite.modules.ebay.productcollection.service.ProductCollectionService;
import com.oigbuy.jeesite.modules.ebay.sellgroup.service.GroupService;
import com.oigbuy.jeesite.modules.sys.utils.UserUtils;

 /***
 * 供应商推荐产品
 * 
 * @author bill.xu
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/product/supplierRecommendProduct")
public class SupplierRecommendProductController extends BaseController{
	
	/**
	 * 供应商推荐
	 */
	@Autowired
	private SupplierRecomService supplierRecomService;
	/**
	 * 竞品采集
	 */
	@Autowired
	private ProductCollectionService productCollectionService;
	
	/***
	 * 平台站点
	 */
	@Autowired
	private PlatformSiteService platformSiteService;
	
	/***
	 * 竞品采集日志
	 */
	@Autowired
	private ProductCollectionLogService productCollectionLogService;
	
	@Autowired
	private GeneralProcessService generalProcessService;
	
	@Autowired
	private GroupService groupService;
	
	/***
	 * 供应商提供列表 
	 * 
	 * @param supplier
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	
	//@RequiresPermissions("product:supplierRecommendProduct:view")
	@RequestMapping(value = { "list",""})
	public String supplierRecommendList(SupplierRecommen supplier,HttpServletRequest request,HttpServletResponse response,Model model){
		
		Page<SupplierRecommen> page = new Page<SupplierRecommen>(request, response);
		model.addAttribute("page", supplierRecomService.findPage2(page, supplier));
		model.addAttribute("supplier", supplier);

		List<PlatformSite> siteList = this.platformSiteService.getListByPlatName(Global.TERRACE_TYPE_EBAY);
		model.addAttribute("siteList", siteList);
		
		model.addAttribute("groupList", groupService.findList(null));
		return "modules/product/supplierRecommedProductList";
	}

		
	
	//@RequiresPermissions("product:supplierRecommendProduct:edit")
	@RequestMapping(value = "form")
	public String form(SupplierRecommen supplier, Model model){
		SupplierRecommen supplierRecommend=this.supplierRecomService.get(supplier.getId());
		if(supplierRecommend==null){
			supplierRecommend =new SupplierRecommen();
		}
		ProductCollection collect = this.productCollectionService.findByRecomId(supplierRecommend.getId());
		if(null != collect){
			
			supplierRecommend.setEbayUrl(collect.getProductUrl());
			supplierRecommend.setSaleGroupId(collect.getSaleGroupId());
			supplierRecommend.setSaleTypeId(collect.getSaleTypeId());
			supplierRecommend.setStatus(collect.getStatus()); 
			supplierRecommend.setSiteId(collect.getSiteId());
			supplierRecommend.setItemId(collect.getItemId());
			
			model.addAttribute("collectId", collect.getId());//竞品采集的id
			//model.addAttribute("flag", "1");//代表该供应商推荐产品已经和竞品采集关联过  
			
			//竞品采集的操作日志记录
			ProductCollectionLog productCollectionLog =new ProductCollectionLog();
			productCollectionLog.setCollectId(String.valueOf(collect.getId()));
			List<ProductCollectionLog> logList=this.productCollectionLogService.findList(productCollectionLog);  
			model.addAttribute("logList", logList);
			}
		
		
		model.addAttribute("siteList", platformSiteService.getListByPlatName(Global.TERRACE_TYPE_EBAY));
		model.addAttribute("groupList", groupService.findList(null));
		model.addAttribute("supplierRecommendProduct", supplierRecommend);
		return "modules/product/supplierRecommedProductForm";
	}
	
	@RequestMapping(value = "detail")
	public String detail(SupplierRecommen supplier, Model model){
		SupplierRecommen	supplierRecommend=this.supplierRecomService.get(supplier.getId());
		if(supplierRecommend==null){
			supplierRecommend =new SupplierRecommen();
		}
		ProductCollection collect = this.productCollectionService.findByRecomId(supplierRecommend.getId());
		if(null != collect){
			
			supplierRecommend.setEbayUrl(collect.getProductUrl());
			supplierRecommend.setSaleGroupId(collect.getSaleGroupId());
			supplierRecommend.setSaleTypeId(collect.getSaleTypeId());
			supplierRecommend.setStatus(collect.getStatus());
			supplierRecommend.setSiteId(collect.getSiteId());
			supplierRecommend.setItemId(collect.getItemId());
			
			model.addAttribute("collectId", collect.getId());//竞品采集的id
			//model.addAttribute("flag", "1");//代表该供应商推荐产品已经和竞品采集关联过  
			
			//竞品采集的操作日志记录
			ProductCollectionLog productCollectionLog =new ProductCollectionLog();
			productCollectionLog.setCollectId(String.valueOf(collect.getId()));
			List<ProductCollectionLog> logList=this.productCollectionLogService.findList(productCollectionLog);  
			model.addAttribute("logList", logList);
			}
		
		
		model.addAttribute("siteList", platformSiteService.getListByPlatName(Global.TERRACE_TYPE_EBAY));//  站点的集合
		model.addAttribute("groupList", groupService.findList(null));
		model.addAttribute("supplierRecommendProduct", supplierRecommend);
		return "modules/product/supplierRecommedProductDetail";
	}
	
	
	
	//@RequiresPermissions("product:supplierRecommendProduct:edit")
	@RequestMapping(value = "save")
	@RepeatUrlData
	public String save(SupplierRecommen supplier, @RequestParam(value="collectId",defaultValue="") String collectId,Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, supplier)){
			return form(supplier, model);
		}
		String saleType = supplier.getSaleTypeId();
		ProductCollection productCollection = null;
		
	try {
		String remcomId = supplier.getId();//供应商推荐的id
		if(StringUtils.isNotBlank(remcomId)){
			
			if(StringUtils.isBlank(collectId)){//供应商推荐和 竞品采集 不存在绑定关系，建立关系，否则不予操作
				//保存 竞品采集
				productCollection = new ProductCollection();
				String id = Global.getID().toString();
				productCollection.setId(id);
				productCollection.setImgUrl(supplier.getImgUrl());
				productCollection.setImgLink(supplier.getNewimg());
				productCollection.setRecomId(remcomId);
//				productCollection.setStatus(Global.ZERO);//步骤状态  
				productCollection.setProductName(supplier.getProductName());//产品名称
				productCollection.setOperator(UserUtils.getUser().getId());
				productCollection.setCreateTime(new Date());
				productCollection.setIsNewRecord(true);
				//保存竞品采集操作日志记录
//				log.setCollectId(id.toString());
//				log.setRemarks("供应商推荐产品 id为："+remcomId+"，竞品采集操作日志 新增");
				 
			}else {// 更新  供应商推荐和竞品采集关系
				
				productCollection = this.productCollectionService.get(collectId);
				productCollection.setIsNewRecord(false);
				//保存竞品采集操作日志记录
//				log.setCollectId(collectId);
//				log.setRemarks("供应商推荐产品 id为："+remcomId+"，竞品采集操作日志 修改");
			}
			
			productCollection.setSaleGroupId(supplier.getSaleGroupId());//分组名
			productCollection.setSaleTypeId(supplier.getSaleTypeId());//销售类型
			productCollection.setSiteId(supplier.getSiteId());//站点
			productCollection.setProductUrl(supplier.getEbayUrl());//产品链接
			productCollection.setItemId(supplier.getItemId());//竞争商品ID
			
			this.productCollectionService.saveOrUpdate(productCollection);
			
//			log.setId(Global.getID().toString());
//			log.setIsNewRecord(true);
//			productCollectionLogService.save(log);
			addMessage(redirectAttributes, "保存竞品采集产品成功");
		}
		} catch (Exception e) {
			addMessage(redirectAttributes, "服务异常了");
			e.printStackTrace();
			logger.error("##########  供应商开发产品 保存异常  SupplierRecommendProductController.java  save \t" +e.getLocalizedMessage());
		}
	
		//铺货产品启动流程
		if (Global.ONE.equals(supplier.getType())) {
			if (Global.SALE_TYPE_DIRECT_SHIPMENT_DISTRIBUTION.equals(saleType) || Global.SALE_TYPE_VIRTUAL_OVERSEAS_DISTRIBUTION.equals(saleType)) {
				//保存操作日志
				ProductCollectionLog productCollectionLog = new ProductCollectionLog();
				productCollectionLog.setId(Global.getID().toString());
				productCollectionLog.setCreateBy(UserUtils.getUser());
				productCollectionLog.setCreateDate(new Date());
				productCollectionLog.setRemarks(supplier.getRemark());
				productCollectionLog.setCollectId(productCollection.getId());
				productCollectionLog.setIsNewRecord(true);
				productCollectionLogService.save(productCollectionLog);
				
				//更新竞品状态
				productCollection.setStatus(Global.TWO);
				productCollectionService.update(productCollection);
				
				//为业务表实体设置数据
				EbayProcessBusiness ebayProcessBusiness = new EbayProcessBusiness();
				ebayProcessBusiness.setImgUrl(supplier.getImgUrl());
				ebayProcessBusiness.setSiteId(supplier.getSiteId());
				ebayProcessBusiness.setSaleType(supplier.getSaleTypeId());
				ebayProcessBusiness.setSaleGroupId(supplier.getSaleGroupId());
				ebayProcessBusiness.setProductName(supplier.getProductName());
				ebayProcessBusiness.setProductUrl(supplier.getProductAddr());
				ebayProcessBusiness.setCreateName(UserUtils.getUser().getName());
				ebayProcessBusiness.setCreateTime(new Date());
				ebayProcessBusiness.setItemId(supplier.getItemId());
				ebayProcessBusiness.setBusinessId(productCollection.getId());
				//将业务表实体设置到流程实体中
				ProcessBean<EbayProcessBusiness> bean = new ProcessBean<EbayProcessBusiness>();
				Map<String, Object> vars = new HashMap<String, Object>();
				vars.put(Global.SALE_TYPE, supplier.getSaleTypeId());
				bean.setData(ebayProcessBusiness);
				bean.setVars(vars);
				//启动流程
				generalProcessService.startProcess(bean);	
			}else {
				//更新竞品状态
				productCollection.setStatus(Global.ZERO);
				productCollectionService.update(productCollection);
			}
		} 
			
		return "redirect:"+Global.getAdminPath()+"/product/supplierRecommendProduct";
	}
	
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(SupplierRecommen supplierRecommen, RedirectAttributes redirectAttributes) {
		//删除供应商推荐表中此数据
		supplierRecomService.delete(supplierRecommen);
		//判断竞品采集表中是否有此数据，若有则删除
		String id = supplierRecommen.getId();
		ProductCollection productCollection = productCollectionService.findByRecomId(id);
		if (ObjectUtils.notEqual(productCollection, null)) {
			productCollectionService.delete(productCollection);
		}
		addMessage(redirectAttributes, "放弃此产品成功！");
		return "success";
	}

}
