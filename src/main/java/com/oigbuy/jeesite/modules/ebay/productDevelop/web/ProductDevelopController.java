package com.oigbuy.jeesite.modules.ebay.productDevelop.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.process.entity.EbayProcessBusiness;
import com.oigbuy.jeesite.common.process.entity.ProcessBean;
import com.oigbuy.jeesite.common.process.service.GeneralProcessService;
import com.oigbuy.jeesite.common.repeaturl.RepeatUrlData;
import com.oigbuy.jeesite.common.utils.*;
import com.oigbuy.jeesite.common.web.BaseController;
import com.oigbuy.jeesite.modules.act.entity.Act;
import com.oigbuy.jeesite.modules.ebay.ebayPaypal.entity.EbayPaypal;
import com.oigbuy.jeesite.modules.ebay.ebayPaypal.service.EbayPaypalService;
import com.oigbuy.jeesite.modules.ebay.ebayfee.entity.EbayFee;
import com.oigbuy.jeesite.modules.ebay.ebayfee.service.EbayFeeService;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.KeyWord;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.PlatFormSiteByEbay;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.PlatformSite;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.service.PlatformSiteService;
import com.oigbuy.jeesite.modules.ebay.product.entity.MessureMmsParam;
import com.oigbuy.jeesite.modules.ebay.product.entity.Product;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductProperty;
import com.oigbuy.jeesite.modules.ebay.product.service.ProductDescriptionService;
import com.oigbuy.jeesite.modules.ebay.product.service.ProductMmsService;
import com.oigbuy.jeesite.modules.ebay.product.service.ProductService;
import com.oigbuy.jeesite.modules.ebay.productDevelop.dto.CalculateDto;
import com.oigbuy.jeesite.modules.ebay.productDevelop.entity.CountryFreight;
import com.oigbuy.jeesite.modules.ebay.productDevelop.entity.ProductToBeDevelop;
import com.oigbuy.jeesite.modules.ebay.productDevelop.service.CountryFreightService;
import com.oigbuy.jeesite.modules.ebay.productDevelop.service.ProductDevelopService;
import com.oigbuy.jeesite.modules.ebay.productDevelop.service.ProductDevelopedService;
import com.oigbuy.jeesite.modules.ebay.productDevelop.service.ProductToBeDevelopService;
import com.oigbuy.jeesite.modules.ebay.productcollection.entity.ProductCollection;
import com.oigbuy.jeesite.modules.ebay.productcollection.entity.ProductCollectionLog;
import com.oigbuy.jeesite.modules.ebay.productcollection.service.ProductCollectionLogService;
import com.oigbuy.jeesite.modules.ebay.productcollection.service.ProductCollectionService;
import com.oigbuy.jeesite.modules.ebay.purchase.dto.PurchaseConfigInfo;
import com.oigbuy.jeesite.modules.ebay.purchase.dto.PurchaseSource;
import com.oigbuy.jeesite.modules.ebay.purchase.service.SupplierService;
import com.oigbuy.jeesite.modules.ebay.sellgroup.entity.Group;
import com.oigbuy.jeesite.modules.ebay.sellgroup.service.GroupService;
import com.oigbuy.jeesite.modules.ebay.tags.entity.Tags;
import com.oigbuy.jeesite.modules.ebay.tags.service.TagsService;
import com.oigbuy.jeesite.modules.sys.entity.User;
import com.oigbuy.jeesite.modules.sys.utils.UserUtils;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 产品开发Controller
 * @author strugkail.li
 * @version 2017-09-04
 */
@Controller
@RequestMapping(value = "${adminPath}/product/develop")
public class ProductDevelopController extends BaseController{

	@Autowired
	private ProductToBeDevelopService toBeDevelopService;
	@Autowired
	private ProductDevelopedService developedService;
	@Autowired
	private TagsService tagsService;
	@Autowired
	private ProductService productService;
	@Autowired
	private PlatformSiteService platformSiteService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private GeneralProcessService generalProcessService;
	@Autowired
	private ProductCollectionService productCollectionService;
	@Autowired
	private ProductMmsService productMmsService;
	@Autowired
	private ProductCollectionLogService productCollectionLogService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private CountryFreightService countryFreightService;
	
	@ModelAttribute
	public ProductToBeDevelop get(@RequestParam(required = false) String id) {
		ProductToBeDevelop entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = toBeDevelopService.get(id);
		}
		if (entity == null) {
			entity = new ProductToBeDevelop();
		}
		return entity;
	}
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// 设置List的最大长度
		binder.setAutoGrowCollectionLimit(Global.LIST_MAX_SIZE);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	/**
	 * 待开发列表
	 * @param toBeDevelop 竞品采集实体
	 * @param request
	 * @param model
	 * @return 页面
	 * @throws Exception 
	 */
    @RequestMapping(value = "toBeDevelop")
    public String getToBeDevelopProducts(ProductToBeDevelop toBeDevelop,
										 HttpServletRequest request,
										 HttpServletResponse response,
										 Model model,
										 EbayProcessBusiness business) throws Exception{
//		Page<ProductToBeDevelop> page = toBeDevelopService.findPage(new Page<ProductToBeDevelop>(request,
//				response), toBeDevelop);
    	business.setSaleType(toBeDevelop.getSaleTypeId());
		Page<Act> pageAct = new Page<Act>(request,response);
//		Page<Act> page  = generalProcessService.findTodoPage(new Page<Act>(request,response),act,business);
		pageAct.setPageSize(20);
		List<Act> listAct =generalProcessService.todoList(business);

		//分页
		int pageSize = pageAct.getPageSize();
		int totalSize = listAct.size();
		int pageIndex = pageAct.getPageNo();

		int startIndex = (pageIndex - 1) * pageSize;
		int endIndex = (pageIndex*pageSize)>totalSize?totalSize:(pageIndex*pageSize);
		listAct = listAct.subList(startIndex,endIndex);
		pageAct.setCount(totalSize);
		pageAct.setList(listAct);

		//获取eBay平台的所有站点
		List<PlatformSite> siteList = platformSiteService.findListByPlatformId(Global.EBAY_PLATFORM_CODE);

		//时间区间
		String dateStart = request.getParameter("dateStart");
		String dateEnd = request.getParameter("dateEnd");

		model.addAttribute("dateStart", dateStart);
		model.addAttribute("dateEnd", dateEnd);
		model.addAttribute("page", pageAct);
		model.addAttribute("siteList",siteList);
		model.addAttribute("groupList", groupService.findList(null));
		model.addAttribute("toBeDevelop",toBeDevelop);
    return "ebay/productDevelop/productToBeDevelopList";
    }


	/**
	 * 已开发产品列表页面
	 * @param product 产品实体
	 * @param request
	 * @param response
	 * @param model
	 * @param flag
	 * @return
	 */
	@RequestMapping("developed")
    public String getDevelopedProducts(Product product, HttpServletRequest request,
									   HttpServletResponse response, Model model,String flag){

		// 已完成开发的产品
		product.setType(Global.PRODUCT_TYPE_OFFICIAL);
		product.setFinishedProductQuantity(Global.FINISHING_PRODUCT_QUANTITY);
		Page<Product> page = productService.findPage(new Page<Product>(request, response), product);

		//获取开发时间区间
		String dateStart = request.getParameter("dateStart");
		String dateEnd = request.getParameter("dateEnd");

		model.addAttribute("page", page);
		model.addAttribute("dateStart", dateStart);
		model.addAttribute("dateEnd", dateEnd);
	return "ebay/productDevelop/productDevelopedList";
	}

	/**
	 * 利润测算页面跳转
	 * @param
	 * @param model
	 * @param
	 * @return
	 */
	@RequestMapping("ProfitCalculate")
    public String virtualCal(Model model, ProcessBean<EbayProcessBusiness> bean){

		//从业务实体中获取所需字段的值
		EbayProcessBusiness buss = new EbayProcessBusiness();
		buss.setProcInsId(bean.getAct().getProcInsId());
		EbayProcessBusiness bussiness = generalProcessService.getByProcInsId(buss);
		String siteId = bussiness.getSiteId();
		String siteName = bussiness.getSiteName();
		String itemId = bussiness.getItemId();
		String productUrl = bussiness.getProductUrl();
		String collectId = bussiness.getBusinessId();
		String taskId = bean.getAct().getTaskId();
		String procInsId = bean.getAct().getProcInsId();
		
		//通过采集id获取
		String productId = productService.findByCollectId(collectId);
		Product product = productService.get(productId);
		
//		if(Global.SALE_TYPE_VIRTUAL_OVERSEAS_STOREHOUSE.equals(bussiness.getSaleType())){
//
//			if (StringUtils.isNotEmpty(productId)) {
//				return  "redirect:" + Global.getAdminPath() + "/product/develop/updateProduct?productId="+productId+"&act.taskId="+taskId+"&act.procInsId="+procInsId;
//			}else {
//				CalculateDto calculateDto = null;
//				calculateDto = productService.virtualCal(siteId,itemId,new CalculateDto());
//				calculateDto.setEbaySellPrice(calculateDto.getEbaySellPrice()+calculateDto.getPostage());
//				model.addAttribute("calculateDto",calculateDto);
//				model.addAttribute("collectId",collectId);
//				model.addAttribute("bean", bean);
//				return "ebay/productDevelop/virtualProfitCalculate";
//			}
//
//		} else
			if(Global.SALE_TYPE_OVERSEAS_STOREHOUSE.equals(bussiness.getSaleType())){

			CalculateDto calculateDto = productService.virtualCal(siteId,itemId,new CalculateDto());
			ProductToBeDevelop toBeDevelop = toBeDevelopService.get(collectId);
			Group group = groupService.get(toBeDevelop.getSaleGroupId());
			PlatformSite site = platformSiteService.get(siteId);
			model.addAttribute("bean", bean);
			model.addAttribute("calculateDto", calculateDto);
			model.addAttribute("toBeDevelop", toBeDevelop);
			model.addAttribute("sellerGroupName", group.getSellerGroupName());
			model.addAttribute("siteName", siteName);
			model.addAttribute("siteShortName", site.getSiteShortName());
			model.addAttribute("itemId", itemId);
			model.addAttribute("productUrl", productUrl);
			return "ebay/productDevelop/realProfitCalculate";
		}else{
				if (StringUtils.isNotEmpty(productId)) {
					return  "redirect:" + Global.getAdminPath() + "/product/develop/updateProduct?productId="+productId+"&act.taskId="+taskId+"&act.procInsId="+procInsId;
				}else {
					CalculateDto calculateDto = null;
					calculateDto = productService.virtualCal(siteId,itemId,new CalculateDto());
					calculateDto.setEbaySellPrice(calculateDto.getEbaySellPrice()+calculateDto.getPostage());
					model.addAttribute("calculateDto",calculateDto);
					model.addAttribute("collectId",collectId);
					model.addAttribute("bean", bean);
					return "ebay/productDevelop/virtualProfitCalculate";
				}
		}
//		else {
//			if (StringUtils.isNotEmpty(productId) && Global.ZERO.equals(product.getType())) {
//				return  "redirect:" + Global.getAdminPath() + "/product/develop/updateProduct?productId="+productId+"&act.taskId="+taskId+"&act.procInsId="+procInsId;
//			}else {
//				return "redirect:" + Global.getAdminPath() + "/product/develop/develop?collectId="+collectId+"&act.taskId="+taskId+"&act.procInsId="+procInsId;
//			}
//		}
	}
	/**
	 * 产品开发页面
	 * @param
	 * @param model
	 * @param collectId
	 * @return
	 */
	@RequestMapping("develop")
	public String productDevelop(Product product,ProcessBean<EbayProcessBusiness> bean,Model model,String collectId,CalculateDto calculateDto){

		//从业务实体中获取所需字段的值
		EbayProcessBusiness buss = new EbayProcessBusiness();
		buss.setProcInsId(bean.getAct().getProcInsId());
		EbayProcessBusiness bussiness = generalProcessService.getByProcInsId(buss);
		String taskId = bean.getAct().getTaskId();
		String procInsId = bean.getAct().getProcInsId();
		// 获取所有产品标签集合
		List<Tags> tagsList = tagsService.findTagsListByTypeFlag(Global.getConfig("product_tag_ids"));
		// 获取所有规格标签集合
		List<Tags> speTagsList = tagsService.findTagsListByTypeFlag(Global.getConfig("product_spe_tag_ids"));
		product.setSiteList(platformSiteService.findList(null));
		//保存操作日志
		if (StringUtils.isNotEmpty(calculateDto.getNote())) {
			ProductCollectionLog productCollectionLog = new ProductCollectionLog();
			productCollectionLog.setId(Global.getID().toString());
			productCollectionLog.setCreateBy(UserUtils.getUser());
			productCollectionLog.setCreateDate(new Date());
			productCollectionLog.setRemarks(calculateDto.getNote());
			productCollectionLog.setCollectId(collectId);

			productCollectionLog.setIsNewRecord(true);
			productCollectionLogService.save(productCollectionLog);
		}
		// 更新竞品状态
		ProductCollection productCollection = new ProductCollection();
		productCollection.setId(collectId);
		productCollection.setStatus(Global.PRODUCT_COLLECT_STATUS_DEVELOP);
		productCollectionService.saveOrUpdate(productCollection);
		// 设置竞品采集相关信息
		ProductToBeDevelop develop = toBeDevelopService.get(collectId);
		product.setImgUrl(develop.getImgUrl());
		product.setProductUrl(develop.getProductUrl());
		product.setImgLink(develop.getImgLink());
		product.setDevelopmentType(develop.getSaleTypeId());
		product.setProductNumber(develop.getProductNumber());
		product.setName(develop.getProductName());

		PlatformSite site = platformSiteService.get(develop.getSiteId());
		product.setSiteShortName(site.getSiteShortName());
		// 生成productId
		product.setId(Global.getID().toString());
		User user = UserUtils.getUser();
		product.setOfficeId(user.getOffice().getId());
		// 保存产品与竞品信息关联表
		ProductToBeDevelop toBeDevelop = new ProductToBeDevelop(product.getId(),collectId);

		String myProductId = productService.findByCollectId(collectId);
		if(StringUtils.isBlank(myProductId)){
			productService.saveProductAndColllect(toBeDevelop);
		}else{
			productService.updateById(toBeDevelop);
		}
		if(Integer.parseInt(develop.getSaleTypeId())>Integer.parseInt(Global.SALE_TYPE_VIRTUAL_OVERSEAS_STOREHOUSE)){
			productService.saveProduct(product);
			model.addAttribute("collectId",collectId);
			model.addAttribute("product", product);
			model.addAttribute("bean", bean);
			model.addAttribute("tagsList", tagsList);
			model.addAttribute("speTagsList", speTagsList);
			model.addAttribute("site", site);
		}else{

			// 设置从利润测算带来的数据
			product.setProfitMargin(Double.valueOf(calculateDto.getGrossProfitRate()));
			product.setSellingPrice(calculateDto.getEbaySellPrice());
			product.setCategoryName(calculateDto.getCategoryName());
			product.setSubCategoryName(calculateDto.getSubCategoryName());
			product.setCostPrice(calculateDto.getCommodityCost());
			product.setFreight(calculateDto.getPostage());
			// 设置申报价格
			product.setDeclaredValue(ProfitCalculateForOtherUtils.calDeclaredValue(calculateDto.getEbaySellPrice()));
			productService.saveProduct(product);
			model.addAttribute("productId", product.getId());

		}
		return "redirect:" + Global.getAdminPath() + "/product/develop/updateProduct?productId="+product.getId()
				+"&act.taskId="+taskId+"&act.procInsId="+procInsId+"&profit="+calculateDto.getGrossProfit();

	}

	/**
	 * 已开发产品详情页面
	 * @param model
	 * @return
	 */
	@RequestMapping("developDetails")
	public String developedDetails(String productId, Model model){

		// 获取所有产品标签集合
		List<Tags> tagsList = tagsService.findTagsListByTypeFlag(Global.getConfig("product_tag_ids"));

		// 获取所有规格标签集合
		List<Tags> speTagsList = tagsService.findTagsListByTypeFlag(Global.getConfig("product_spe_tag_ids"));

		// 获取属性列表和子代码信息列表
		Product product = productService.getProductDetail(productId);
		List<ProductProperty> propertyList = productService.findPropertyListById(product);
		// 采购源配置列表
		List<PurchaseSource> purchaseSourceList = supplierService.getSupplierListForProduct(productId);
		product.setPurchaseSourceList(purchaseSourceList);

		// 由productId获取collectId,进而获取对应竞品采集信息
		String collectId = productService.findByProductId(productId);
		ProductToBeDevelop develop = toBeDevelopService.get(collectId);
		product.setProductUrl(develop.getProductUrl());
		PlatformSite site = platformSiteService.get(develop.getSiteId());
		product.setSiteShortName(site.getSiteShortName());

		// 调用ebay接口，获取封装的dto
		CalculateDto calculateDto = productService.virtualCal(develop.getSiteId(),develop.getItemId(),new CalculateDto());

		model.addAttribute("tagsList", tagsList);
		model.addAttribute("propertyList", propertyList);
		model.addAttribute("speTagsList", speTagsList);
		model.addAttribute("product",product);
		model.addAttribute("calculateDto",calculateDto);
		model.addAttribute("site",site);
		return "ebay/productDevelop/productDevelopDetails";
	}

	/**
	 * 产品开发草稿箱页面
	 * @param product
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("developDraft")
	public String developDraft(Product product, HttpServletRequest request,
							   HttpServletResponse response, Model model,String flag){

		// 设置为草稿箱状态
		product.setType(Global.PRODUCT_TYPE_DRAFT);
		Page<Product> page = developedService.findPage(new Page<Product>(request, response), product);

		//获取开发时间区间
		String dateStart = request.getParameter("dateStart");
		String dateEnd = request.getParameter("dateEnd");

		model.addAttribute("page", page);
		model.addAttribute("dateStart", dateStart);
		model.addAttribute("dateEnd", dateEnd);
		return "ebay/productDevelop/productDevelopDraftList";
	}
	/**
	 * 表单提交，保存产品到草稿箱或开发完成逻辑
	 * @param product
	 * @param redirectAttributes
	 * @return
	 */
//	@RequiresPermissions("product:product:edit")
	@RequestMapping(value = "save")
	public String save(Product product, Model model, ProcessBean<EbayProcessBusiness> bean, RedirectAttributes redirectAttributes,String flag,ProductToBeDevelop collect) {

		// 0:草稿箱状态  1：正式刊登状态
		String type = product.getType();
		// 获取、保存当前用户的部门id
		User user = UserUtils.getUser();
		product.setOfficeId(user.getOffice().getId());


		String developmentType = product.getDevelopmentType();


		// 保存草稿箱，对于字段不进行验证的功能
		if(Global.ZERO.equals(type)){
			productService.saveProduct(product);
			addMessage(redirectAttributes, "产品保存成功！");
			return "redirect:" + Global.getAdminPath() + "/product/develop/toBeDevelop";
		} else {
			// 保存产品信息
			productService.insert(product);
			productService.saveAndPublishProduct(product);
			addMessage(redirectAttributes, "开发产品成功");
			
			//更新竞品状态
			if (Integer.parseInt(developmentType) > Integer.parseInt(Global.SALE_TYPE_VIRTUAL_OVERSEAS_STOREHOUSE)) {
				collect.setStatus(Global.PRODUCT_COLLECT_STATUS_DATA_PERFECT);
			}else {
				collect.setStatus(Global.PRODUCT_COLLECT_STATUS_DRAWING);
			}
			toBeDevelopService.update(collect);
			
			//完成上一任务，重新创建任务
			EbayProcessBusiness ebayProcessBusiness = new EbayProcessBusiness();
			ebayProcessBusiness.setImgUrl(product.getImgUrl());
			ebayProcessBusiness.setProductName(product.getName());
			ebayProcessBusiness.setProductUrl(product.getProductUrl());
			ebayProcessBusiness.setSysParentCode(product.getSysParentCode());
			ebayProcessBusiness.setSaleType(product.getDevelopmentType());
			ebayProcessBusiness.setCreateName(UserUtils.getUser().getName());
			ebayProcessBusiness.setCreateTime(new Date());
			ebayProcessBusiness.setBusinessId(product.getId());

//			ProcessBean<EbayProcessBusiness> bean = new ProcessBean<EbayProcessBusiness>();
			Map<String, Object> vars = new HashMap<String, Object>();
			vars.put(Global.SALE_TYPE, developmentType);
			bean.setData(ebayProcessBusiness);
			bean.setVars(vars);

			generalProcessService.complete(bean);

			return "redirect:" + Global.getAdminPath() + "/product/develop/developed";
		}
	}

	/**
	 * 转到update页面
	 * @param model
	 * @param
	 * @return
	 */
	@RequestMapping(value = "updateProduct")
	public String updateProduct(String profit,String productId, Model model, ProcessBean<EbayProcessBusiness> bean){

		// 获取所有产品标签集合
		List<Tags> tagsList = tagsService.findTagsListByTypeFlag(Global.getConfig("product_tag_ids"));

		// 获取所有规格标签集合
		List<Tags> speTagsList = tagsService.findTagsListByTypeFlag(Global.getConfig("product_spe_tag_ids"));

		// 由productId 获取产品信息
		Product product = productService.getProductDetail(productId);

		// 采购源配置列表
		List<PurchaseSource> purchaseSourceList = supplierService.getSupplierListForProduct(productId);
		product.setPurchaseSourceList(purchaseSourceList);

		// 属性列表
		List<ProductProperty> propertyList = productService.findPropertyListById(product);

		// 由productId获取collectId,进而获取对应竞品采集信息
		String collectId = productService.findByProductId(productId);
		ProductToBeDevelop develop = toBeDevelopService.get(collectId);
		product.setProductNumber(develop.getProductNumber());

		PlatformSite site = platformSiteService.get(develop.getSiteId());

		// 调用ebay接口，获取封装的dto
		CalculateDto calculateDto = productService.virtualCal(develop.getSiteId(),develop.getItemId(),new CalculateDto());

		product.setProductUrl(develop.getProductUrl());
		product.setTitle(calculateDto.getTitle());
		product.setProfit(Double.valueOf(profit));

		model.addAttribute("tagsList", tagsList);
		model.addAttribute("speTagsList", speTagsList);
		model.addAttribute("product",product);
		model.addAttribute("propertyList",propertyList);
		model.addAttribute("bean",bean);
		model.addAttribute("calculateDto",calculateDto);
		model.addAttribute("site",site);
		return "ebay/productDevelop/updateProduct";
	}
	/**
	 * 执行update业务逻辑
	 * @return
	 */
	@RepeatUrlData
	@RequestMapping(value = "update")
	public String update(Product product,RedirectAttributes redirectAttributes,ProcessBean<EbayProcessBusiness> bean){
		if(Global.ZERO.equals(product.getType())){
			productService.updateProduct(product);
			return "redirect:" + Global.getAdminPath() + "/product/develop/toBeDevelop";
		}else{
			// 保存进入下一流程
			productService.saveAndPublishProduct(product);
			String developmentType = product.getDevelopmentType();
			//更新竞品状态
			String collectId = productCollectionService.findCollectIdByProductId(product.getId());
			ProductCollection productCollection = new ProductCollection();
			productCollection.setId(collectId);
			productCollection.setStatus(Global.PRODUCT_COLLECT_STATUS_REVIEW_CODE_NUMBER);
			productCollectionService.saveOrUpdate(productCollection);
			
			//获取竞品对象
			productCollection = productCollectionService.get(collectId);

			//完成上一任务，重新创建任务
			EbayProcessBusiness ebayProcessBusiness = new EbayProcessBusiness();
			ebayProcessBusiness.setImgUrl(product.getImgUrl());
			ebayProcessBusiness.setSiteId(productCollection.getSiteId());
			ebayProcessBusiness.setSaleGroupId(productCollection.getSaleGroupId());
			ebayProcessBusiness.setProductName(product.getName());
			ebayProcessBusiness.setProductUrl(product.getProductUrl());
			ebayProcessBusiness.setSysParentCode(product.getSysParentCode());
			ebayProcessBusiness.setSaleType(product.getDevelopmentType());
			ebayProcessBusiness.setCreateName(UserUtils.getUser().getName());
			ebayProcessBusiness.setCreateTime(new Date());
			ebayProcessBusiness.setBusinessId(product.getId());

//			ProcessBean<EbayProcessBusiness> bean = new ProcessBean<EbayProcessBusiness>();
			Map<String, Object> vars = new HashMap<String, Object>();
			vars.put(Global.SALE_TYPE, developmentType);
			bean.setData(ebayProcessBusiness);
			bean.setVars(vars);

			generalProcessService.complete(bean);
			return "redirect:" + Global.getAdminPath() + "/product/develop/developed";
		}
	}



	/**
	 *	虚拟仓利润测算接口，测算逻辑存放在知识库
	 * @return
	 */
	@RequestMapping(value = "calculateUtil")
	@ResponseBody
	public JsonResponseModel calculateUtil(CalculateDto calculateDto){
		JsonResponseModel jsonResult = new JsonResponseModel();
		String data = JSONObject.toJSONString(calculateDto);
		try {
			String returnData = HttpHelper.post(Global.VIRTUAL_BASE_CALCULATE_URL, data, null);
			JSONObject returnJson = JSONObject.parseObject(returnData);
			jsonResult.setData(returnJson);
			jsonResult.setResult(true);
		} catch (Exception e) {
			jsonResult.fail("测算失败!失败信息："+e);
		}
		return jsonResult;
	}

	/**
	 * 异步添加属性
	 * @param propertyName
	 * @param propertyValues
	 * @param productId
	 * @return
	 */
	@RequestMapping("addProperty")
	public @ResponseBody List<ProductProperty> addProperty(String propertyName,String propertyValues,String productId){

		// 异步添加产品属性
		ProductProperty property = new ProductProperty();
		property.setPropertyName(propertyName);
		property.setPropertyValue(propertyValues);
		property.setProductId(productId);
		productService.addProperty(property);
		List<ProductProperty> propertyList = productService.findPropertyListById(new Product(productId));
		return propertyList;
	}

	/**
	 * 异步删除属性
	 * @param
	 * @param productId
	 * @return
	 */
	@RequestMapping("deleteProperty")
	public @ResponseBody List<ProductProperty> deleteProperty(String propertyCode,String productId){
		ProductProperty property = new ProductProperty();
		property.setProductId(productId);
		property.setPropertyCode(propertyCode);
		productService.deletePropertyById(property);
		List<ProductProperty> propertyList = productService.findPropertyListById(new Product(productId));
		return propertyList;
	}
	/**
	 * 异步更新codemanager代码
	 * @param productId
	 * @return 最新List列表
	 */
	@RequestMapping("updateCodeManager")
	public @ResponseBody List<PurchaseConfigInfo> updateCodeManager(String productId){
		return productService.trans2List(productId);
	}
	@RequestMapping("addCodeManager")
	public @ResponseBody List<PurchaseConfigInfo> addCodeManager(String productId){
		productService.updateProductManagerCode(new Product(productId));
		return productService.trans2List(productId);
	}

	/**
	 *	查询采购源列表
	 * @param productId
	 * @return
	 */
	@RequestMapping("findSupplierList")
	public @ResponseBody List<PurchaseSource> findSupplierList(String productId){
		return supplierService.getSupplierListForProduct(productId);
	}
	/**
	 * 查询海外仓对应的竞品状态，确定开发按钮是否置灰
	 */
	@RequestMapping("getCollectionStatus")
	public @ResponseBody Boolean getCollectionStatus(String collectId){
		ProductToBeDevelop toBeDevelop = toBeDevelopService.get(collectId);
		Boolean isTrue = false;
		String status = toBeDevelop.getStatus();
		if(Global.PRODUCT_COLLECT_STATUS_DEVELOP.equals(status)){
			isTrue = true;
		}
		return isTrue;
	}
	/**
	 * 海外仓测算开发
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("overseasCal")
	public @ResponseBody JSONObject overseasCal(MessureMmsParam messureMmsParam,String collectId) throws UnsupportedEncodingException{
		messureMmsParam.setDescription("该产品必须选择ebay平台，和"+messureMmsParam.getSiteName()+"站点");
		messureMmsParam.setPicture(Base64Img.Image2Base64(messureMmsParam.getPicture()));
		if(StringUtils.isNotBlank(messureMmsParam.getSiteName()) && !messureMmsParam.getSiteName().equals("德国")){

			messureMmsParam.setTariff(Global.TARIFF_NOT_GERMANY_SITE);
		}
		if(StringUtils.isNotBlank(messureMmsParam.getSiteName()) && messureMmsParam.getSiteName().equals("德国")){
			messureMmsParam.setTariff(Global.TARIFF_GERMANY_SITE);
		}
		String returnData = productMmsService.startMeasure(messureMmsParam,collectId);
		JSONObject object = JSONObject.parseObject(returnData);
		if(Global.SUCCESS.equals(object.getString(Global.CODE))){
			ProductToBeDevelop toBeDevelop = new ProductToBeDevelop();
			toBeDevelop.setCollectId(collectId);
			toBeDevelop.setStatus(Global.PRODUCT_COLLECT_STATUS_DEVELOP);
			toBeDevelopService.update(toBeDevelop);
		}else if(Global.GATEWAY_TIMEOUT_CODE.equals(object.getString(Global.CODE))){
			object.put("msg","网关异常！");
		}
		return object;
	}

	/**
	 * 由重量、站点简称计算出运费
	 * @param siteName
	 * @param defaultWeight
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getFreight")
	public JsonResponseModel getFreight(String siteName,Double defaultWeight){
		List<CountryFreight> list = countryFreightService.getByShortName(siteName);
		Double finalFee = 0D;
		CountryFreight freight = null;
		JsonResponseModel jsonResult = new JsonResponseModel();
		if(list.size()>1){
			for (int i=1;i<list.size();i++) {
				if(defaultWeight>list.get(i-1) .getExtent() && defaultWeight<list.get(i).getExtent()){
					freight = list.get(i);
					continue;
				}
			}
			Double dealFee = freight.getDealFee();
			Double freightFee = freight.getFreightFee();
			finalFee = (defaultWeight/1000)*freightFee +dealFee;
			jsonResult.setData(productService.transUtil(finalFee));
			jsonResult.setResult(true);
		}else if(list.size()==1){
			freight = list.get(0);
			Double dealFee = freight.getDealFee();
			Double freightFee = freight.getFreightFee();
			finalFee = (defaultWeight/1000)*freightFee +dealFee;
			jsonResult.setData(productService.transUtil(finalFee));
			jsonResult.setResult(true);
		}else{
			jsonResult.setMsg("未查到此站点运费信息！");
			return jsonResult;
		}

		return jsonResult;
	}
	@ResponseBody
	@RequestMapping("changeMany")
	public JsonResponseModel getManyChanges(CalculateDto calculateDto){
		JsonResponseModel jsonResult = new JsonResponseModel();
		Map<String,Object> map = null;
		try {
			map = productService.getManyChange(new CalculateDto(calculateDto.getCategoryName(),calculateDto.getSiteName()));
			jsonResult.setResult(true);
		}catch (Exception e){
			jsonResult.fail("错误信息："+e);
		}
		EbayPaypal ebayPaypal = (EbayPaypal)map.get(Global.EBAY_PAYPAL);
		EbayFee ebay = (EbayFee)map.get(Global.EBAY_FEE);
		Double maxValue = Double.valueOf(ebay.getMaxValue());
		Double ebayRate = Double.valueOf(ebay.getFeeRate());
		Double ebaySellPrice = calculateDto.getEbaySellPrice();
		// 取 ebaySellPrice*ebayRate 与 maxValue 的最小值
		String ebayFee = productService.transUtil(Math.min(ebaySellPrice * ebayRate, maxValue));

		// 临界值：根据站点获取
		// ebay售价≤PayPal临界值：ebay售价*Smallpaypal_discount+Smallpaypal_each_cost
		// ebay售价≥PayPal临界值：ebay售价*paypal_discount+paypal_each_cost"
		String paypalFee = productService.transUtil(ebaySellPrice > ebayPaypal.getBoundaries() ? (ebaySellPrice * ebayPaypal.getPaypalDiscount() + ebayPaypal.getPaypalEachCost()) : (ebayPaypal.getSmallpaypalDiscount() * ebaySellPrice + ebayPaypal.getSmallpaypalEachCost()));
		// 财务费
		String financefee = productService.transUtil(productService.FINANCE_RATE*ebaySellPrice);
		// 损失费
		String lossfee = productService.transUtil(productService.LOSS_RATE*ebaySellPrice);
		map.put("ebayfee",ebayFee);
		map.put("paypalfee",paypalFee);
		map.put("financefee",financefee);
		map.put("lossfee",lossfee);
		jsonResult.setData(map);
		return jsonResult;
	}
    /**
     * 放弃开发
     * @return
     */
    @RequestMapping("quitDevelop")
    public String quitDevelop(String procInsId,String collectId,String note){
       ProductToBeDevelop develop = toBeDevelopService.get(collectId);
       develop.setStatus(Global.PRODUCT_COLLECT_STATUS_GIVEUP_DEVELOPMENT);
       toBeDevelopService.update(develop);
       
       //将备注信息回写到竞品采集日志中
       if (StringUtils.isNotEmpty(note)) {
			ProductCollectionLog productCollectionLog = new ProductCollectionLog();
			productCollectionLog.setId(Global.getID().toString());
			productCollectionLog.setCreateBy(UserUtils.getUser());
			productCollectionLog.setCreateDate(new Date());
			productCollectionLog.setRemarks(note);
			productCollectionLog.setCollectId(collectId);

			productCollectionLog.setIsNewRecord(true);
			productCollectionLogService.save(productCollectionLog);
		}
		generalProcessService.deleteByproInsId(procInsId);
       return "redirect:" + Global.getAdminPath() + "/product/develop/toBeDevelop";
    }
    /**
     * 暂时放弃开发
     * @return
     */
    @RequestMapping("temporaryQuit")
	public String temporaryQuit(String procInsId,String collectId,CalculateDto calculateDto,String note){
		ProductToBeDevelop develop = toBeDevelopService.get(collectId);
		String itemId = develop.getItemId();
//		if(!productService.temporaryQuit(itemId,calculateDto)){
			develop.setStatus(Global.PRODUCT_COLLECT_STATUS_GIVEUP_DEVELOPMENT_TEMPORARILY);
			toBeDevelopService.update(develop);
		   //将备注信息回写到竞品采集日志中
	       if (StringUtils.isNotEmpty(note)) {
				ProductCollectionLog productCollectionLog = new ProductCollectionLog();
				productCollectionLog.setId(Global.getID().toString());
				productCollectionLog.setCreateBy(UserUtils.getUser());
				productCollectionLog.setCreateDate(new Date());
				productCollectionLog.setRemarks(note);
				productCollectionLog.setCollectId(collectId);

				productCollectionLog.setIsNewRecord(true);
				productCollectionLogService.save(productCollectionLog);
			}
			
			EbayProcessBusiness business = new EbayProcessBusiness();
			business.setProcInsId(procInsId);
			business.setProductFlag(Global.EBAY_HIDE);
			generalProcessService.updateSysParentCodeByProcInsId(business);
//		}else{
//			develop.setStatus(Global.PRODUCT_COLLECT_STATUS_CALCULATION);
//			EbayProcessBusiness business = new EbayProcessBusiness();
//			business.setProcInsId(procInsId);
//			business.setProductFlag(Global.EBAY_SHOW);
//			generalProcessService.updateSysParentCodeByProcInsId(business);
//		}
        return "redirect:" + Global.getAdminPath() + "/product/develop/toBeDevelop";
    }

    /**
     * 回退任务，重新测算
     * @param collectId
     * @param saleType
     * @return
     */
    @RequestMapping("toCalculate")
   	public String toCalculate(String collectId,String saleType){
    	if (Global.SALE_TYPE_VIRTUAL_OVERSEAS_STOREHOUSE.equals(saleType)) {
    		developedService.toCalculate(collectId);
		}
        return "redirect:" + Global.getAdminPath() + "/product/develop/toBeDevelop";
       }
    
    
    /**
	 * review产品数量列表
	 * @param toBeDevelop 竞品采集实体
	 * @param request
	 * @param model
	 * @return 页面
	 * @throws Exception 
	 */
    @RequestMapping(value = "reviewProductNum")
    public String getReviewProductNumList(ProductToBeDevelop toBeDevelop,HttpServletRequest request, HttpServletResponse response,
										 Act act, Model model,EbayProcessBusiness business) throws Exception{
//		Page<ProductToBeDevelop> page = toBeDevelopService.findPage(new Page<ProductToBeDevelop>(request,
//				response), toBeDevelop);
    	business.setSaleType(toBeDevelop.getSaleTypeId());
		Page<Act> pageAct = new Page<Act>(request,response);
//		Page<Act> page  = generalProcessService.findTodoPage(new Page<Act>(request,response),act,business);
		pageAct.setPageSize(20);
		List<Act> listAct =generalProcessService.todoList(business);

		//分页
		int pageSize = pageAct.getPageSize();
		int totalSize = listAct.size();
		int pageIndex = pageAct.getPageNo();

		int startIndex = (pageIndex - 1) * pageSize;
		int endIndex = (pageIndex*pageSize)>totalSize?totalSize:(pageIndex*pageSize);
		listAct = listAct.subList(startIndex,endIndex);
		pageAct.setCount(totalSize);
		pageAct.setList(listAct);

		//获取eBay平台的所有站点
		List<PlatformSite> siteList = platformSiteService.findListByPlatformId(Global.EBAY_PLATFORM_CODE);

		//时间区间
		String dateStart = request.getParameter("dateStart");
		String dateEnd = request.getParameter("dateEnd");

		model.addAttribute("dateStart", dateStart);
		model.addAttribute("dateEnd", dateEnd);
//        model.addAttribute("page",page);
		model.addAttribute("page", pageAct);
		model.addAttribute("siteList",siteList);
		model.addAttribute("groupList", groupService.findList(null));
		model.addAttribute("toBeDevelop",toBeDevelop);
    return "ebay/productDevelop/reviewProductNumList";
    }
    
    /**
	 * review产品数量页面
	 * @param model
	 * @return
	 */
	@RequestMapping("reviewProductNumForm")
	public String reviewProductNumForm(String id, Model model, HttpServletResponse response,ProcessBean<EbayProcessBusiness> bean){

		// 获取所有产品标签集合
		List<Tags> tagsList = tagsService.findTagsListByTypeFlag(Global.getConfig("product_tag_ids"));

		// 获取所有规格标签集合
		List<Tags> speTagsList = tagsService.findTagsListByTypeFlag(Global.getConfig("product_spe_tag_ids"));

		// 获取属性列表和子代码信息列表
		Product product = productService.getProductDetail(id);
		List<ProductProperty> propertyList = productService.findPropertyListById(product);
		// 采购源配置列表
		List<PurchaseSource> purchaseSourceList = supplierService.getSupplierListForProduct(id);
		product.setPurchaseSourceList(purchaseSourceList);

		// 由productId获取collectId,进而获取对应竞品采集信息
		String collectId = productService.findByProductId(id);
		ProductToBeDevelop develop = toBeDevelopService.get(collectId);
		product.setProductUrl(develop.getProductUrl());
		PlatformSite site = platformSiteService.get(develop.getSiteId());
		product.setSiteShortName(site.getSiteShortName());

		// 调用ebay接口，获取封装的dto
		CalculateDto calculateDto = productService.virtualCal(develop.getSiteId(),develop.getItemId(),new CalculateDto());
		//获取Ebay所有平台
		List<PlatFormSiteByEbay> platformSiteList=platformSiteService.findSiteByEbay();
		response.setCharacterEncoding("utf-8");
       JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
       JSONArray  json = JSONArray.fromObject(platformSiteList,jsonConfig);
		model.addAttribute("platformSiteList", platformSiteList);
		model.addAttribute("platFormJsonList", json);
        model.addAttribute("tagsList", tagsList);
		model.addAttribute("propertyList", propertyList);
		model.addAttribute("speTagsList", speTagsList);
		model.addAttribute("product",product);
		model.addAttribute("calculateDto",calculateDto);
		model.addAttribute("site",site);
		model.addAttribute("bean", bean);
		return "ebay/productDevelop/reviewProductNumForm";
	}
	
	/**
	 * 保存产品数量
	 * @return
	 */
	@RepeatUrlData
	@RequestMapping(value = "saveProductNum")
	public String saveProductNum(Product product, List<KeyWord> siteId,List<KeyWord> keyword, RedirectAttributes redirectAttributes, ProcessBean<EbayProcessBusiness> bean){
		Product product2 = productService.get(product);
		//更新产品数量
		productService.updateProductNum(product);
		if(Global.ZERO.equals(product.getType())){
			return "redirect:" + Global.getAdminPath() + "/product/develop/reviewProductNum?flag=reviewProductNum";
		}else{
//        .insertKeyWord();
			//更新产品完成数量补充标识为已完成
			productService.updateFinishedProductQuantity(product.getId(), Global.FINISHED_PRODUCT_QUANTITY);
			
			String developmentType = product.getDevelopmentType();
			//更新竞品状态
			String collectId = productCollectionService.findCollectIdByProductId(product.getId());
			ProductCollection productCollection = new ProductCollection();
			productCollection.setId(collectId);
			if (Integer.parseInt(developmentType) > Global.TWO_INTEGER) {
				productCollection.setStatus(Global.PRODUCT_COLLECT_STATUS_DATA_PERFECT);
			}else {
				productCollection.setStatus(Global.PRODUCT_COLLECT_STATUS_DRAWING);
			}
			productCollectionService.saveOrUpdate(productCollection);
			
			//获取竞品对象
			productCollection = productCollectionService.get(collectId);

			//完成上一任务，重新创建任务
			EbayProcessBusiness ebayProcessBusiness = new EbayProcessBusiness();
			ebayProcessBusiness.setImgUrl(product2.getImgUrl());
			ebayProcessBusiness.setSiteId(productCollection.getSiteId());
			ebayProcessBusiness.setSaleGroupId(productCollection.getSaleGroupId());
			ebayProcessBusiness.setProductName(product2.getName());
			ebayProcessBusiness.setProductUrl(product.getProductUrl());
			ebayProcessBusiness.setSysParentCode(product2.getSysParentCode());
			ebayProcessBusiness.setSaleType(product2.getDevelopmentType());
			ebayProcessBusiness.setCreateName(UserUtils.getUser().getName());
			ebayProcessBusiness.setCreateTime(new Date());
			ebayProcessBusiness.setBusinessId(product.getId());

//			ProcessBean<EbayProcessBusiness> bean = new ProcessBean<EbayProcessBusiness>();
			Map<String, Object> vars = new HashMap<String, Object>();
			vars.put(Global.SALE_TYPE, developmentType);
			bean.setData(ebayProcessBusiness);
			bean.setVars(vars);

			generalProcessService.complete(bean);
			return "redirect:" + Global.getAdminPath() + "/product/develop/reviewProductNum?flag=reviewProductNum";
		}
	}

	/**
	 * 已完成产品数量补充列表
	 * @param product 产品实体
	 * @param request
	 * @param response
	 * @param model
	 * @param
	 * @return
	 */
	@RequestMapping("finishedProductQuantity")
    public String getfinishedProductQuantityList(Product product, HttpServletRequest request,
									   HttpServletResponse response, Model model){

		// 已完成开发的产品
		product.setType(Global.PRODUCT_TYPE_OFFICIAL);
		product.setFinishedProductQuantity(Global.FINISHED_PRODUCT_QUANTITY);

		Page<Product> page = productService.findPage(new Page<Product>(request, response), product);

		//获取开发时间区间
		String dateStart = request.getParameter("dateStart");
		String dateEnd = request.getParameter("dateEnd");

		model.addAttribute("page", page);
		model.addAttribute("dateStart", dateStart);
		model.addAttribute("dateEnd", dateEnd);
	return "ebay/productDevelop/productDevelopedList";
	}
}
