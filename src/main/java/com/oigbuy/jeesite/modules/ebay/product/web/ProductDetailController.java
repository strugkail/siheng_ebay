package com.oigbuy.jeesite.modules.ebay.product.web;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oigbuy.elasticsearch.helper.ElasticSearchHelper;
import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.process.entity.EbayProcessBusiness;
import com.oigbuy.jeesite.common.process.entity.ProcessBean;
import com.oigbuy.jeesite.common.process.service.GeneralProcessService;
import com.oigbuy.jeesite.common.utils.BarCodeUtils;
import com.oigbuy.jeesite.common.utils.DateUtils;
import com.oigbuy.jeesite.common.utils.JsonResponseModel;
import com.oigbuy.jeesite.common.utils.ListingPublishPriceUtils;
import com.oigbuy.jeesite.common.utils.ObjectUtils;
import com.oigbuy.jeesite.common.utils.StringUtils;
import com.oigbuy.jeesite.common.utils.enums.SiteCurrencyEnum;
import com.oigbuy.jeesite.common.utils.formula.JexlInstance;
import com.oigbuy.jeesite.common.utils.sensitive.SensitiveWordUtils;
import com.oigbuy.jeesite.common.utils.translate.TranslatorUtil;
import com.oigbuy.jeesite.common.web.BaseController;
import com.oigbuy.jeesite.modules.ebay.designdrawing.service.DesignDrawingService;
import com.oigbuy.jeesite.modules.ebay.ebayPaypal.entity.EbayPaypal;
import com.oigbuy.jeesite.modules.ebay.ebayPaypal.service.EbayPaypalService;
import com.oigbuy.jeesite.modules.ebay.ebayfee.entity.EbayFee;
import com.oigbuy.jeesite.modules.ebay.ebayfee.service.EbayFeeService;
import com.oigbuy.jeesite.modules.ebay.listing.entity.EbayListing;
import com.oigbuy.jeesite.modules.ebay.listing.service.EbayListingService;
import com.oigbuy.jeesite.modules.ebay.logistics.entity.LogisticsMode;
import com.oigbuy.jeesite.modules.ebay.logistics.service.LogisticsModeService;
import com.oigbuy.jeesite.modules.ebay.mode.entity.PublishStyleMode;
import com.oigbuy.jeesite.modules.ebay.mode.service.PublishStyleModeService;
import com.oigbuy.jeesite.modules.ebay.mode.service.ShieldCountryModeService;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.PlatformSite;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.service.PlatformService;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.service.PlatformSiteService;
import com.oigbuy.jeesite.modules.ebay.product.dto.ProductDetail;
import com.oigbuy.jeesite.modules.ebay.product.dto.ProductDto;
import com.oigbuy.jeesite.modules.ebay.product.dto.TranslateDto;
import com.oigbuy.jeesite.modules.ebay.product.entity.Product;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductCodeManager;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductProperty;
import com.oigbuy.jeesite.modules.ebay.product.entity.TProductImgPlatform;
import com.oigbuy.jeesite.modules.ebay.product.service.ItemSpecificsService;
import com.oigbuy.jeesite.modules.ebay.product.service.ProductCategoryService;
import com.oigbuy.jeesite.modules.ebay.product.service.ProductCodeManagerService;
import com.oigbuy.jeesite.modules.ebay.product.service.ProductDescriptionService;
import com.oigbuy.jeesite.modules.ebay.product.service.ProductImgService;
import com.oigbuy.jeesite.modules.ebay.product.service.ProductService;
import com.oigbuy.jeesite.modules.ebay.product.service.ProductTitleService;
import com.oigbuy.jeesite.modules.ebay.product.service.TProductImgPlatformService;
import com.oigbuy.jeesite.modules.ebay.product.service.TitleKeyService;
import com.oigbuy.jeesite.modules.ebay.productcollection.entity.ProductCollection;
import com.oigbuy.jeesite.modules.ebay.productcollection.service.ProductCollectionService;
import com.oigbuy.jeesite.modules.ebay.template.entity.BuyerRestriction;
import com.oigbuy.jeesite.modules.ebay.template.entity.LocationofGoods;
import com.oigbuy.jeesite.modules.ebay.template.entity.ReturnPurchase;
import com.oigbuy.jeesite.modules.ebay.template.entity.Seller;
import com.oigbuy.jeesite.modules.ebay.template.service.BuyerRestrictionService;
import com.oigbuy.jeesite.modules.ebay.template.service.LocationofGoodsService;
import com.oigbuy.jeesite.modules.ebay.template.service.ReturnPurchaseService;
import com.oigbuy.jeesite.modules.ebay.template.service.SellerService;
import com.oigbuy.jeesite.modules.sys.utils.UserUtils;


/***
 * 产品编辑页面
 * 
 * 
 * @author bill.xu
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/ebay/productDetail")
public class ProductDetailController extends BaseController {

	@Autowired
	private ProductService productService;
	/***
	 * 刊登风格
	 */
	@Autowired
	private PublishStyleModeService publishStyleModeService;
	/***
	 * 产品图片
	 */
	@Autowired
	private ProductImgService productImgService;
	
	/**平台站点*/
	@Autowired
	private PlatformSiteService platformSiteService;
	
	/***
	 * 物流模板设值
	 */
	@Autowired
	private LogisticsModeService logisticsModeService;
	
	/***
	 * 商品所在地
	 */
	@Autowired
	private LocationofGoodsService locationofGoodsService;
	
	/***
	 * 买家限制
	 */
	@Autowired
	private BuyerRestrictionService buyerRestrictionService;
	
	/***
	 * 退货
	 */
	@Autowired
	private ReturnPurchaseService returnPurchaseService;
	

	
	/**
	 * 子代码
	 */
	@Autowired
	private ProductCodeManagerService productCodeManagerService;
	
	/**
	 * 卖家账号
	 */
	@Autowired
	private SellerService sellerService;
	
	
	
	
	/***
	 * 产品分类
	 */
	@Autowired
	private ProductCategoryService productCategoryService;
	
	
	/***
	 * 产品标题
	 */
	@Autowired
	private ProductTitleService productTitleService;
	
 
	
	/**
	 * ebay  listing 
	 */
	@Autowired
	private EbayListingService ebayListingService;
	
	
	@Autowired
	private ItemSpecificsService itemSpecificsService;

	
	/***
	 * ebay  paypal
	 * 
	 */
	@Autowired
	private EbayPaypalService ebayPaypalService;  
	
	
	/***
	 * ebay fee
	 */
	@Autowired
	private EbayFeeService ebayFeeService;
	
	
	/***
	 * 屏蔽目的地
	 */
	@Autowired
	private ShieldCountryModeService shieldCountryModeService;
	
	/***
	 * 标题关键字
	 */
	@Autowired
	private TitleKeyService titleKeyService;
	
	/***
	 * 产品描述
	 */
	@Autowired
	private ProductDescriptionService productDescriptionService;
	
	/***
	 * 流程
	 */
	@Autowired
	private GeneralProcessService generalProcessService;
	
	/***
	 * 竞品采集
	 */
	@Autowired
	private ProductCollectionService productCollectionService;
	
	/***
	 * 图片
	 */
	@Autowired
	private TProductImgPlatformService tProductImgPlatformService;
	
	
	
	/***
	 * 平台
	 */
	@Autowired
	private PlatformService platformService;
	
	
	@Autowired
	private DesignDrawingService designDrawingService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// 设置List的最大长度
		binder.setAutoGrowCollectionLimit(1000);
	}

	
	
	@RequiresPermissions("ebay:product:view")
	@RequestMapping(value = "productDetail")
	public String getProductDetail(String id, Model model, ProcessBean<EbayProcessBusiness> bean) throws Exception {
		Product product  =  this.productService.get(id);
		if(product == null){
			throw new RuntimeException("产品id 有误，查询不到对应的产品！");
		}
		ProductDto productDto = new ProductDto();
		try {
			PropertyUtils.copyProperties(productDto, product);
		} catch (IllegalAccessException | InvocationTargetException	| NoSuchMethodException e) {
	        throw  new RuntimeException("属性复制异常！请稍后再试");
		}
		ProductDetail productDetail = new ProductDetail();
		 //获取所有图片
		List<TProductImgPlatform> images = designDrawingService.findAllByProductId(product.getId());
		//子代码 信息   这里的图片怎么取，是子代码信息中的还是 关联的 产品图片中的 
		List<ProductCodeManager> codeManagerList = this.productCodeManagerService.findListByProductId(product.getId(),null,images);
		productDetail.setCodeManagerList(codeManagerList);
		if(CollectionUtils.isNotEmpty(codeManagerList)){// 如果存在多属性则为 1 否则为 0
			model.addAttribute("isHasCodeManager", codeManagerList.size()>1?Global.ONE:Global.ZERO);
		}
		productDetail.setProductPropertyNames(getProductPropertyNames(codeManagerList));
		//站点
		model.addAttribute("siteList", platformSiteService.getListByPlatName(Global.TERRACE_TYPE_EBAY));
		
		productDto = setProductDtoData(id,productDto);
		productDetail.setProduct(productDto);
		
		model.addAttribute("product", productDto);
		model.addAttribute("productDetail", productDetail);
		//查询此产品所对应的listing
		EbayListing ebayListing = new EbayListing();
		ebayListing.setProductId(Long.valueOf(id));
		List<EbayListing> ebayListingList = ebayListingService.findList(ebayListing);
		model.addAttribute("ebayListingList", ebayListingList);
		model.addAttribute("bean", bean);
		model.addAttribute("ebaySaleType", productDto.getDevelopmentType());//1 海外仓精品   2 虚拟海外仓精品  3中国直发仓铺货   4 虚拟海外仓铺货
		
		return "modules/ebay/product/productDetail";
	}
 
	
	
	/***
	 * 获得自定义属性的 name 集合
	 * 
	 * @param codeManagerList
	 * @return
	 */
	private List<String> getProductPropertyNames(List<ProductCodeManager> codeManagerList) {
		List<String> names = new ArrayList<String>();
		if(CollectionUtils.isEmpty(codeManagerList) || codeManagerList.get(0)==null){
			return names;
		}
		List<ProductProperty> propertyList = codeManagerList.get(0).getProductPropertyList();
			if(CollectionUtils.isNotEmpty(propertyList)){
				propertyList.forEach(productProperty->{
					names.add(productProperty.getPropertyName());
				});
			}
		return names;
	}



	/***
	 * 获得该产品的页面要 展示的数据
	 * 
	 * @param id
	 * @param productDto 
	 * @return
	 */
	private ProductDto setProductDtoData(String id, ProductDto productDto) {
		
		//图片集合
		Map<String,List<TProductImgPlatform>> imageMap = this.productImgService.getMainAndDetailAndSpecialImage(id);
	   //该产品的主 g 图
		productDto.setMainProductImage(imageMap.get(Global.IMG_TYPE_MAIN));
		//该产品的细节 图
		productDto.setDetailProductImage(imageMap.get(Global.IMG_TYPE_DETAIL));
		//该产品的特效 图
		productDto.setSpecialProductImage(imageMap.get(Global.IMG_TYPE_SPECIAL_EFFECTS));
		
		productDto = this.productTitleService.getProductTitle(id,productDto);
		//描述信息
		productDto = productDescriptionService.getProductDescription(id,productDto);
		
		return productDto;
	}


	
	@ResponseBody
	@RequestMapping("checkSensitiveWords")
	public JsonResponseModel checkSensitiveWords(String title,String description){
		JsonResponseModel result = new JsonResponseModel();
		try {
			SensitiveWordUtils.checkSensitiveWord(title,description);
			
			return result.success(null);
		} catch (Exception e) {

		   return  result.fail(e.getLocalizedMessage());
		}
		
	}
	
	

	@RequestMapping("saveProduct")
	@ResponseBody
	public JsonResponseModel saveProduct2(HttpServletRequest request,ProductDetail productDetail,String flag, ProcessBean<EbayProcessBusiness> bean) throws Exception{
		Long begin = System.currentTimeMillis();
		JsonResponseModel jsonResult = new JsonResponseModel();
		if(StringUtils.isBlank(productDetail.getSellerAccount())){
				return jsonResult.fail("刊登店铺不能为空！");
				//throw new RuntimeException("刊登店铺不能为空！");
		 }
		logger.info("############  saveProduct 保存 产品 开始时间为 {}ms",begin);
		productDetail = ebayListingService.checkImageAndTtile(productDetail);
		Seller seller = this.sellerService.get(productDetail.getSellerAccount());
		if(seller==null){
			return jsonResult.fail("刊登店铺查询异常，数据有误！");
		   //throw new RuntimeException("刊登店铺查询异常，数据有误！");
		}
		//通过站点 获取 平台的id  
		//店铺的id 
		Long shopId = Long.valueOf(seller.getShopId());
		PlatformSite platformSite = platformSiteService.get(productDetail.getSiteId());
		if(platformSite==null){
				return jsonResult.fail("刊登店铺站点  查询异常，数据有误！");
				//throw new RuntimeException("刊登店铺站点  查询异常，数据有误！");
			}
			productDetail.setPlatformSite(platformSite);
		try {
			productDetail = ebayListingService.saveProductDetail(productDetail);
			ebayListingService.saveListing(productDetail,shopId);
			logger.info("############  saveProduct 保存 产品 结束时间为 {}ms",(System.currentTimeMillis()-begin));
			return jsonResult.success(productDetail);
		} catch (Exception e) {
			return jsonResult.fail("保存listing 异常！"+e.getMessage());
		}
			
	}
	
	
	
	@ResponseBody
	@RequestMapping("submitFlow")
	public JsonResponseModel submitFlow(HttpServletRequest request,String productId, ProcessBean<EbayProcessBusiness> bean){
		JsonResponseModel result = new JsonResponseModel();
		//获取产品信息
		Product product = productService.get(productId);
		if(product==null){
			return result.fail("产品id有误，查询不到对应的产品数据！");
		}
		
		//更新竞品状态
		String collectId = productCollectionService.findCollectIdByProductId(productId);
		ProductCollection productCollection = new ProductCollection();
		productCollection.setId(collectId);
		productCollection.setStatus("5");
		productCollectionService.saveOrUpdate(productCollection);
		
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
//
//		ProcessBean<EbayProcessBusiness> bean = new ProcessBean<EbayProcessBusiness>();
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put(Global.SALE_TYPE, product.getDevelopmentType());
		bean.setData(ebayProcessBusiness);
		bean.setVars(vars);
		try {
			generalProcessService.complete(bean);
        return result.success(null);			
		} catch (Exception e) {
			return result.fail("流程处理 服务异常！");
		}
		
	}
	
	
//	@RequestMapping("saveProduct")
//	@RepeatUrlData
//	public String saveProduct(HttpServletRequest request,ProductDetail productDetail,String flag, ProcessBean<EbayProcessBusiness> bean) throws Exception{
//	
//		if(Global.ZERO.equals(flag)){//  保存 listing
//			if(StringUtils.isBlank(productDetail.getSellerAccount())){
//				throw new RuntimeException("刊登店铺不能为空！");
//			}
//			Long begin = System.currentTimeMillis();
//			logger.info("############  saveProduct 保存 产品 开始时间为 {}ms",begin);
//			productDetail = ebayListingService.checkImageAndTtile(productDetail);
//			Seller seller = this.sellerService.get(productDetail.getSellerAccount());
//			if(seller==null){
//				throw new RuntimeException("刊登店铺查询异常，数据有误！");
//			}
//			//通过站点 获取 平台的id  
//			//店铺的id 
//			Long shopId = Long.valueOf(seller.getShopId());
//			PlatformSite platformSite = platformSiteService.get(productDetail.getSiteId());
//			if(platformSite==null){
//				throw new RuntimeException("刊登店铺站点  查询异常，数据有误！");
//			}
//			productDetail.setPlatformSite(platformSite);
//			productDetail = ebayListingService.saveProductDetail(productDetail);
//			ebayListingService.saveListing(productDetail,shopId);
//			logger.info("############  saveProduct 保存 产品 结束时间为 {}ms",(System.currentTimeMillis()-begin));
//			return "redirect:"+Global.getAdminPath()+"/ebay/productDetail/productDetail?id="+productDetail.getProduct().getId();
//			
//		}else{//提交流程
//			//获取产品信息
//			String productId = productDetail.getProduct().getId();
//			Product product = productService.get(productId);
//			//更新竞品状态
//			String collectId = productCollectionService.findCollectIdByProductId(productId);
//			ProductCollection productCollection = new ProductCollection();
//			productCollection.setId(collectId);
//			productCollection.setStatus("5");
//			productCollectionService.saveOrUpdate(productCollection);
//			
//			//完成上一任务，重新创建任务
//			EbayProcessBusiness ebayProcessBusiness = new EbayProcessBusiness();
//			ebayProcessBusiness.setImgUrl(product.getImgUrl());
//			ebayProcessBusiness.setProductName(product.getName());
//			ebayProcessBusiness.setProductUrl(product.getProductUrl());
//			ebayProcessBusiness.setSysParentCode(product.getSysParentCode());
//			ebayProcessBusiness.setSaleType(product.getDevelopmentType());
//			ebayProcessBusiness.setCreateName(UserUtils.getUser().getName());
//			ebayProcessBusiness.setCreateTime(new Date());
//			ebayProcessBusiness.setBusinessId(product.getId());
//			Map<String, Object> vars = new HashMap<String, Object>();
//			vars.put(Global.SALE_TYPE, product.getDevelopmentType());
//			bean.setData(ebayProcessBusiness);
//			bean.setVars(vars);
//
//			generalProcessService.complete(bean);
//			
//			return "redirect:"+Global.getAdminPath()+"/listing/ebayListing/?repage";
//		}
//		
//	}

	
	/***
	 * 产品选择 要刊登的店铺之后要 计算价格，获得 关联的模板
	 * 
	 * @param productId
	 * @param siteId
	 * @param sellerId
	 * @param rate  页面输入的 利润率 ，反算价格要使用这个 
	 * @return
	 */
	@RequestMapping("getListingMode")
	@ResponseBody
	public JsonResponseModel getListingMode(Long productId,Long siteId,Long sellerId,String rate,String categoryName){
		JsonResponseModel result = new JsonResponseModel();
		Seller seller = sellerService.get(String.valueOf(sellerId));
		if(seller ==null || seller.getShopId()==null){
			return result.fail("店铺信息查询异常 ，请稍后再试！");
		}
	   try {
			Map<String, Object> resultMap =	getListingMode(productId, siteId, seller,rate,categoryName);
			resultMap.putIfAbsent("seller",seller );
			return result.success(resultMap);
		} catch (Exception e) {
			logger.error("选择店铺，查询服务异常 :{}",e.getMessage());
			return result.fail("选择店铺，查询服务异常"+e.getMessage());
		}
	}

	

	/***
	 * 获取 切换账号时的数据   反算价格 和 获取相关的模板 信息
 	 * 
	 * @param productId
	 * @param siteId
	 * @param seller
	 * @param rate
	 * @return
	 * @throws Exception 
	 */
	private  Map<String, Object> getListingMode(Long productId, Long siteId , Seller seller,String rate,String category1) throws Exception {
		Long begin = System.currentTimeMillis();
		logger.info("#########  进入到  通过关联卖家账号获取相关数据 接口 ，开始时间为 {}",begin);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String siteIds = String.valueOf(siteId);
		Product product = this.productService.get(String.valueOf(productId));
		//反算价格
		PlatformSite site= platformSiteService.get(siteIds);
		
		getCodeManagerAndLogisticsMode(seller,rate,resultMap,product,site,category1);
		
	    resultMap.put("product", product);
	    
		//刊登风格模板
		PublishStyleMode publishStyle = this.publishStyleModeService.autoSelectPublishStyleMode(siteIds);
		resultMap.put("PublishStyleMode", publishStyle);
				
		String saleType = product.getDevelopmentType();//销售类型   1 海外仓精品   2 虚拟海外仓精品  3中国直发仓铺货   4 虚拟海外仓铺货
		
		LocationofGoods locationofGoods = locationofGoodsService.autoSelectLocationofGoods(site,saleType,seller.getSellerName());
		resultMap.put("LocationofGoods", locationofGoods);
		
		ReturnPurchase returnPurchase = returnPurchaseService.autoSelectReturnPurchase(saleType,siteIds);
		resultMap.put("ReturnPurchase", returnPurchase);
		
		BuyerRestriction buyerRestriction = buyerRestrictionService.autoSelectBuyerRestriction();
		resultMap.put("BuyerRestriction", buyerRestriction);
		
		logger.info("#########  进入到  通过关联卖家账号获取相关数据 接口 ，结束时间为 {} ms",(System.currentTimeMillis()-begin));
	  return resultMap;
	}

	/***
	 * 获得 反算价格 和 物流模板 
	 * 
	 * @param seller
	 * @param rate
	 * @param resultMap
	 * @param product
	 * @param site
	 * @param category1  分类 一 获取 ebayfee 
	 * @throws Exception 
	 */
	private void getCodeManagerAndLogisticsMode(Seller seller,String rate, Map<String, Object> resultMap, Product product,PlatformSite site,String category1) throws Exception {
		
		//String saleType = product.getDevelopmentType();//销售类型   1 海外仓精品   2 虚拟海外仓精品  3中国直发仓铺货   4 虚拟海外仓铺货
		
		boolean isBigPaypal = false;//是不是 大 paypal账户
		
		EbayPaypal paypal = this.ebayPaypalService.getBySite(new EbayPaypal(site.getSiteShortName()));
		
		EbayFee fee = ebayFeeService.getBySiteAndCategory(new EbayFee(category1,site.getSiteShortName()));

		if(fee==null){
			throw new Exception("该站点下的  ebay fee 获取失败！请维护数据后重试！");
		}
		 //获取所有图片
		List<TProductImgPlatform> images = designDrawingService.findAllByProductId(product.getId());
		List<ProductCodeManager> codeManagerList = this.productCodeManagerService.findListByProductId(product.getId(),null,images);
		logger.info("######### 反算 code manager 价格 开始时间为 {}",System.currentTimeMillis());
	    //获得 code manager的反算刊登价格
	    codeManagerList = ListingPublishPriceUtils.getCodeManagerPrice(codeManagerList,site.getSiteShortName(),paypal,fee.getFeeRate(),rate);
	    logger.info("######### 反算 code manager 价格 结束 时间为 {}",System.currentTimeMillis());
	     // 长宽高的一个基本数据map
	    Map<String, Object> paramMap = new HashMap<String, Object>();
	    if(StringUtils.isNotBlank(product.getDeliveryWareHouse())){
	    	paramMap.put(JexlInstance.WEAR_HOUSE, product.getDeliveryWareHouse());//仓库
	    }
	    List<Double> priceList = new ArrayList<Double>();
	     if(CollectionUtils.isNotEmpty(codeManagerList)){
	    	 
	    	 Double dd = new Double(0);
	    	 Double length = dd,wide = dd,height=dd,weight=dd;
	    	 
	    	 isBigPaypal = codeManagerList.get(0).isBigPaypal();
	    	for(ProductCodeManager  k: codeManagerList){
	    		
	    		k.setListingSkuCode(seller.getSname()+site.getSiteShortName()+k.getSysSku()+Global.SKU_CODE_MM);
	    		k.setProductCode(BarCodeUtils.getCodeBySite(site.getSiteShortName()));
	    		priceList.add(k.getPublishPrice());//获得每个 codemanager的价格集合
	    		
	    		if(StringUtils.isNotBlank(k.getLength())){
	    		  length = Double.valueOf(k.getLength()).compareTo(length) <= 0 ?length:Double.valueOf(k.getLength());
	    		}
	    		if(StringUtils.isNotBlank(k.getWide())){
	    			wide = Double.valueOf(k.getWide()).compareTo(wide) <= 0 ?wide:Double.valueOf(k.getWide());
	    		}
	    		if(StringUtils.isNotBlank(k.getHigh())){
	    			height = Double.valueOf(k.getHigh()).compareTo(height) <= 0 ?height:Double.valueOf(k.getHigh());
	    		}
	    		if(k.getWeight()!=null){
	    			weight = k.getWeight().compareTo(weight)<=0?weight:k.getWeight();
	    		}
	    	}
	    	 //重量 长宽高 
	    	 paramMap.put(JexlInstance.LENGTH,length);//长
	    	 paramMap.put(JexlInstance.WIDE, wide);//宽
	    	 paramMap.put(JexlInstance.HEIGHT, height);//高
	    	 paramMap.put(JexlInstance.WEIGHT, weight);//重量  
	     }
	    //如果不是 海外仓精品，要系统生成
//	    if(!Global.SALE_TYPE_OVERSEAS_STOREHOUSE.equals(saleType)){
//	    }
	    resultMap.put("codeManagerList", codeManagerList);
	    resultMap.put("ebayPaypal", paypal);//ebay pay 拿到临界值和对应的大小paypal 的账户
	    // 大额小额 paypal 账户
	    String paypalAccount = isBigPaypal?seller.getPalAccount():seller.getSubPalAccount();
	    resultMap.put("paypalAccount", paypalAccount);
	    
	    LogisticsMode mode = logisticsModeService.autoSelectLogisticsMode(product,site,priceList,paramMap);
	    //获取一个可用的物流模板 
	  //	LogisticsMode mode = logisticsModeService.autoSelectMode(product,codeManagerList,site,seller);
	  	resultMap.put("LogisticsMode", mode);
	  	resultMap.put("listingSkuCode", seller.getSname()+site.getSiteShortName()+product.getSysParentCode()+Global.SKU_CODE_MM);
	}
	
	/***
	 * 通过站点和 产品id(站点进行切换的时候)  已经拆分为 下面的几个接口 暂时废弃（2017-11-30）
	 * 
	 * 获取 该产品的  分类和分类细节 
	 * 
	 * @param productId
	 * @param siteId
	 * @param translateDto  标题 关键字 翻译 dto
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("getCategoryAndItemSpecifics")
	@ResponseBody
	@Deprecated
	public JsonResponseModel getCategoryAndItemSpecifics(Long productId,Long siteId) {
		JsonResponseModel jsonResult = new JsonResponseModel();
		Long begin=System.currentTimeMillis();
		logger.info("##############   切换站点动态获取 分类数据，开始时间为 {}，请求参数为 产品id {}，站点 id {}",DateUtils.formatDateTime(new Date(begin)),productId,siteId);
		PlatformSite site= platformSiteService.get(String.valueOf(siteId));
		
		if(!ObjectUtils.notEqual(site, null)){
			return jsonResult.fail("站点信息查询出错！");
		}
		Product product = this.productService.get(String.valueOf(productId));
		if(!ObjectUtils.notEqual(product, null)){
			return jsonResult.fail("产品查询出错！");
		}
		List<Seller> sellerList = this.ebayListingService.getCanUseSellerList(siteId,productId); 
		if(CollectionUtils.isEmpty(sellerList)){
			return jsonResult.fail("没有可用刊登店铺！");
		}
		Map<String, Object> result = new HashMap<String, Object>();
		String siteName = site.getSiteShortName();
		//设置 分类信息
		try {
			result.put("categoryList", this.productCategoryService.findBySiteAndProductId(siteName,productId,false,null));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonResult.fail("分类信息查询异常"+e.getLocalizedMessage());
		}
		result.put("sellerList", sellerList);
		result.put("CurrencyName", SiteCurrencyEnum.getCurrencyName(siteName));// 站点和相应的货币符号
		//此处翻译 code manager 多属性的值	
		 //获取所有图片
		List<TProductImgPlatform> images = designDrawingService.findAllByProductId(product.getId());
		result.put("codeManagerList", this.productCodeManagerService.findListByProductId(product.getId(),siteName,images));
		// 站点区分 法国和意大利 设置为 1 ，图片使用 特效图。 其他为 2 图片使用 细节图
		result.put("siteType", Global.ITALY_AND_FRANCE_SITE_NAME.contains(site.getSiteName())?Global.ONE:Global.TWO);
		logger.info("##############   切换站点动态获取 分类数据，结束时间为 {}，请求参数为 产品id {}，站点 id {}，耗时为 {}ms",DateUtils.formatDateTime(new Date()),productId,siteId,(System.currentTimeMillis()-begin));
		return jsonResult.success(result);

	}
	
	
	
	
	
	/**
	 * 接口拆分   获取分类（通过 站点和 item id）
	 * 
	 * 和 
	 * 
	 * 
	 * @param productId
	 * @param siteId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getCategoryList")
	public JsonResponseModel getCategoryList(Long productId,Long siteId){
		JsonResponseModel jsonResult = new JsonResponseModel();
		PlatformSite site= platformSiteService.get(String.valueOf(siteId));
		if(!ObjectUtils.notEqual(site, null)){
			return jsonResult.fail("站点信息查询出错！");
		}
		Map<String, Object> result = new HashMap<String, Object>();
		//设置 分类信息
		try {
			result.put("categoryList", this.productCategoryService.findBySiteAndProductId(site.getSiteShortName(),productId,false,null));
			result.put("CurrencyName", SiteCurrencyEnum.getCurrencyName(site.getSiteShortName()));// 站点和相应的货币符号
			result.put("siteType", Global.ITALY_AND_FRANCE_SITE_NAME.contains(site.getSiteName())?Global.ONE:Global.TWO);
			return jsonResult.success(result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("切换站点 获取 分类 集合 异常，"+e.getMessage());
			return jsonResult.fail("分类信息查询异常"+e.getLocalizedMessage());
		}
	}
		
	/**
	 * 接口拆分   获取 子代码 多属性相关内容
	 * 
	 * 
	 * @param productId
	 * @param siteId
	 * @return
	 */
	@ResponseBody
	//@RequestMapping("getCodeManagerList")
	@Deprecated
	public JsonResponseModel getCodeManagerList(Long productId,Long siteId){
		JsonResponseModel jsonResult = new JsonResponseModel();
		Product product = this.productService.get(String.valueOf(productId));
		if(!ObjectUtils.notEqual(product, null)){
			return jsonResult.fail("产品查询出错！");
		}
		PlatformSite site= platformSiteService.get(String.valueOf(siteId));
		if(!ObjectUtils.notEqual(site, null)){
			return jsonResult.fail("站点信息查询出错！");
		}
		try {
			List<TProductImgPlatform> images = designDrawingService.findAllByProductId(product.getId());
			List<ProductCodeManager> codeManagerList = this.productCodeManagerService.findListByProductId(product.getId(),site.getSiteShortName(),images);
			return jsonResult.success(codeManagerList);
		} catch (Exception e) {
			logger.error("##########  切换站点获取 相关子代码 多属性数据 服务异常 "+e.getMessage());
			return jsonResult.fail("切换站点获取 相关子代码 多属性数据 服务异常 "+e.getMessage());
		}
	}
	
	
	/***
	 * 接口拆分   获取可刊登店铺 集合
	 * 
	 * @param productId
	 * @param siteId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getSellerList")
	public JsonResponseModel getSellerList(Long productId,Long siteId){
		JsonResponseModel jsonResult = new JsonResponseModel();
		try {
			List<Seller> sellerList = this.ebayListingService.getCanUseSellerList(siteId,productId); 
			if(CollectionUtils.isEmpty(sellerList)){
				return jsonResult.fail("没有可用刊登店铺！");
			}
			return jsonResult.success(sellerList);
		} catch (Exception e) {
			logger.error("切换站点获取 可刊登店铺 集合 异常 ！"+e.getMessage());
			return jsonResult.fail("切换站点获取 可刊登店铺 集合 异常 ！"+e.getMessage());
		}
	}
	
	
	

	/***
	 * 翻译标题（关键字等）
	 * 
	 * @param translateDto
	 * @param siteName
	 * @return
	 */
	
	@RequestMapping("translateTitle")
	@ResponseBody
	public  JsonResponseModel translateTitle(TranslateDto translateDto,String siteId) {
		JsonResponseModel result = new JsonResponseModel();
		PlatformSite site= platformSiteService.get(String.valueOf(siteId));
		if(site==null){
			return result.fail("站点信息查询异常！请稍后再试");
		}
		String language = SiteCurrencyEnum.getLanguageBySite(site.getSiteShortName());
		try {
			translateDto.setKeyWord1T(TranslatorUtil.getSimpleContent(translateDto.getKeyWord1(), language));
			translateDto.setKeyWord2T(TranslatorUtil.getSimpleContent(translateDto.getKeyWord2(), language));
			translateDto.setOtherKeyWord1T(TranslatorUtil.getSimpleContent(translateDto.getOtherKeyWord1(), language));
			translateDto.setOtherKeyWord2T(TranslatorUtil.getSimpleContent(translateDto.getOtherKeyWord2(), language));
			translateDto.setTitleT(TranslatorUtil.getSimpleContent(translateDto.getTitle(),language));
			translateDto.setSubtitleT(TranslatorUtil.getSimpleContent(translateDto.getSubtitle(),language));
			SensitiveWordUtils.checkSensitiveWord(translateDto.getTitleT(),translateDto.getSubtitleT(),translateDto.getKeyWord1T(),translateDto.getKeyWord2T(),translateDto.getOtherKeyWord1T(),translateDto.getOtherKeyWord2T());
			return 	result.success(translateDto);
		} catch (Exception e) {
			logger.error("翻译接口出错！请稍后再试，异常信息为：{}",e.getMessage());
			return  result.fail("翻译服务异常！稍后再试"+e.getLocalizedMessage());
		}

	}


	
	
	/***
	 * 通过站点翻译相关内容
	 * 
	 * @param content
	 * @param siteId
	 * @return
	 */
	@RequestMapping("translate")
	@ResponseBody
	public JsonResponseModel translate(String content,String siteId){
		JsonResponseModel json =  new JsonResponseModel();
			PlatformSite platformSite = platformSiteService.get(siteId);
			String result;
			try {
				result = TranslatorUtil.getSimpleContent(content, SiteCurrencyEnum.getLanguageBySite(platformSite.getSiteShortName()));
				SensitiveWordUtils.checkSensitiveWord(result);//检测是不是存在敏感词
				return json.success(result);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("翻译接口失败，异常信息为：{}",e.getLocalizedMessage());
				return  json.fail("翻译失败，请稍后再试！"+e.getMessage());
			}
	}
	
	
	/**
	 * 标题 翻译 并且 校验 是不是重复和 有敏感字  TODO 
	 * 
	 * @param content  要翻译的标题内容
	 * @param siteId  站点 翻译语言
	 * @param listingId  listing 标题查重
	 * @param type 1 表示主标题  2 表示 副标题
	 * @return
	 */
	@RequestMapping("translateAndCheckDuplicate")
	@ResponseBody
	public JsonResponseModel translateAndCheckDuplicate(String content,String siteId,String listingId,String type){
		JsonResponseModel result =  new JsonResponseModel();
		PlatformSite platformSite = platformSiteService.get(siteId);
		if(platformSite==null){
			return result.fail("站点查询失败，翻译出错！");
		}	
		String resultContent;
		try {
			resultContent = TranslatorUtil.getSimpleContent(content, SiteCurrencyEnum.getLanguageBySite(platformSite.getSiteShortName()));
			//敏感词校验
			SensitiveWordUtils.checkSensitiveWord(resultContent);
			//标题重复校验 
			ElasticSearchHelper helper=new ElasticSearchHelper(); 
			boolean resultStatus =  helper.getResultStatus("title", resultContent);
			 if(resultStatus){
				 return result.fail("产品标题重复了的，请改动之后再提交！");
			 }	
			EbayListing ebayListing = new EbayListing();
			if(Global.ONE.equals(type)){
			 		ebayListing.setTitle(resultContent);
				}else{
					ebayListing.setSubTitle(resultContent);
				}
			List<EbayListing> listingList = ebayListingService.findList(ebayListing);
			if(CollectionUtils.isNotEmpty(listingList) && (listingList.size()>1 || !StringUtils.equals(listingList.get(0).getId(), listingId))){//说明有重复的
				return result.fail("该标题  已存在 于 listing 中，请修改后再试！ ");
			}
			return result.success(resultContent);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("翻译接口失败，异常信息为：{}",e.getLocalizedMessage());
			return  result.fail("翻译失败，请稍后再试！"+e.getLocalizedMessage());
		}
	}
	
	
	
	
	
	/***
	 * 页面 改变利润率 进行价格反算和物流模板选择
	 * 
	 * @param productId
	 * @param siteId
	 * @param sellerId
	 * @param rate
	 * @return
	 */
	@RequestMapping("calcListingPrice") 
	@ResponseBody
	public JsonResponseModel calcListingPrice(Long productId, Long siteId ,Long sellerId,String rate,String categoryName){
		 	
		 JsonResponseModel jsonResult = new JsonResponseModel();
		 	//反算价格
			PlatformSite site = platformSiteService.get(String.valueOf(siteId));
			Product product = this.productService.get(String.valueOf(productId));
			Seller seller = this.sellerService.get(String.valueOf(sellerId));
			if(site==null || product==null ||seller==null ){
				return jsonResult.fail("请求参数异常，查询不到相应的数据！");
			}
			Map<String, Object> resultMap = new HashMap<String, Object>();
			try {
				getCodeManagerAndLogisticsMode(seller, rate, resultMap,	product, site,categoryName);
				return jsonResult.success(resultMap);
			} catch (Exception e) {
			   return jsonResult.fail("价格反算异常  ！ "+e.getMessage());
			}
	}
	
	
}