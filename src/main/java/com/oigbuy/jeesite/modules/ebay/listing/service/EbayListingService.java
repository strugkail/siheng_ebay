/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.listing.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.oigbuy.api.domain.ebay.BuyerDetailDto;
import com.oigbuy.api.domain.ebay.ItemDto;
import com.oigbuy.api.domain.ebay.ItemSpecificsDto;
import com.oigbuy.api.domain.ebay.PayMentDetailDto;
import com.oigbuy.api.domain.ebay.PictureDto;
import com.oigbuy.api.domain.ebay.RefundDetailDto;
import com.oigbuy.api.domain.ebay.ShippingDetail;
import com.oigbuy.api.domain.ebay.ShippingServiceDto;
import com.oigbuy.api.domain.ebay.UsualDto;
import com.oigbuy.api.domain.ebay.VariationDto;
import com.oigbuy.api.domain.ebay.VariationSet;
import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.common.utils.DateUtils;
import com.oigbuy.jeesite.common.utils.ObjectUtils;
import com.oigbuy.jeesite.common.utils.StringUtils;
import com.oigbuy.jeesite.common.utils.enums.SiteCurrencyEnum;
import com.oigbuy.jeesite.common.utils.formula.JexlInstance;
import com.oigbuy.jeesite.common.utils.sensitive.SensitiveWordUtils;
import com.oigbuy.jeesite.modules.ebay.listing.dao.EbayListingDao;
import com.oigbuy.jeesite.modules.ebay.listing.dto.ListingCodeManagerDto;
import com.oigbuy.jeesite.modules.ebay.listing.dto.ListingDetailDto;
import com.oigbuy.jeesite.modules.ebay.listing.entity.EbayListing;
import com.oigbuy.jeesite.modules.ebay.listing.entity.EbayListingImg;
import com.oigbuy.jeesite.modules.ebay.listing.entity.FunctionCall;
import com.oigbuy.jeesite.modules.ebay.listing.entity.ListingProperty;
import com.oigbuy.jeesite.modules.ebay.logistics.entity.LogisticsMode;
import com.oigbuy.jeesite.modules.ebay.logistics.entity.ShippingMode;
import com.oigbuy.jeesite.modules.ebay.logistics.service.LogisticsModeService;
import com.oigbuy.jeesite.modules.ebay.mode.entity.PublishStyleMode;
import com.oigbuy.jeesite.modules.ebay.mode.service.PublishStyleModeService;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.PlatformSite;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.service.PlatformService;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.service.PlatformSiteService;
import com.oigbuy.jeesite.modules.ebay.product.dto.ProductDetail;
import com.oigbuy.jeesite.modules.ebay.product.dto.ProductDto;
import com.oigbuy.jeesite.modules.ebay.product.entity.ItemSpecifics;
import com.oigbuy.jeesite.modules.ebay.product.entity.Product;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductCategory;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductCodeManager;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductTitle;
import com.oigbuy.jeesite.modules.ebay.product.service.ProductCategoryService;
import com.oigbuy.jeesite.modules.ebay.product.service.ProductCodeManagerService;
import com.oigbuy.jeesite.modules.ebay.product.service.ProductDescriptionService;
import com.oigbuy.jeesite.modules.ebay.product.service.ProductService;
import com.oigbuy.jeesite.modules.ebay.product.service.ProductTitleService;
import com.oigbuy.jeesite.modules.ebay.product.service.TitleKeyService;
import com.oigbuy.jeesite.modules.ebay.sellgroup.service.GroupService;
import com.oigbuy.jeesite.modules.ebay.template.entity.BuyerRestriction;
import com.oigbuy.jeesite.modules.ebay.template.entity.LocationofGoods;
import com.oigbuy.jeesite.modules.ebay.template.entity.ReturnPurchase;
import com.oigbuy.jeesite.modules.ebay.template.entity.Seller;
import com.oigbuy.jeesite.modules.ebay.template.service.BuyerRestrictionService;
import com.oigbuy.jeesite.modules.ebay.template.service.LocationofGoodsService;
import com.oigbuy.jeesite.modules.ebay.template.service.ReturnPurchaseService;
import com.oigbuy.jeesite.modules.ebay.template.service.SellerService;
import com.oigbuy.jeesite.modules.sys.entity.User;
import com.oigbuy.jeesite.modules.sys.utils.UserUtils;
import com.sho.tool.date.RandTimeUtils;

/**
 * ebay listingService
 * @author bill.xu
 * @version 2017-09-22
 */
@Service
@Transactional(readOnly = true)
public class EbayListingService extends CrudService<EbayListingDao, EbayListing> {
	
	@Autowired
	private TitleKeyService  titleKeyService;
	
	@Autowired
	private ProductTitleService productTitleService;

	@Autowired
	private EbayListingImgService ebayListingImgService;
	
	@Autowired
	private EbaySkuMappingService ebaySkuMappingService;
	
	
	@Autowired
	private ProductCodeManagerService codeManagerService;
	
	
	@Autowired
	private ProductCategoryService productCategoryService;
	
	
	@Autowired
	private EbayListingPriceService ebayListingPriceService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private SellerService sellerService;
	
	/***
	 * 刊登任务 
	 */
	@Autowired
	private FunctionCallService functionCallService;
	/***
	 * 站点
	 */
	@Autowired
	private PlatformSiteService platformSiteService;	
	
	/***
	 * 平台
	 */
	@Autowired
	private PlatformService platformService;
	
	/***
	 * 产品描述
	 */
	@Autowired
	private ProductDescriptionService productDescriptionService;
	
	/***
	 * 物流模板 
	 */
	@Autowired
	private LogisticsModeService logisticsModeService;
	
	/**
	 * 商品所在地模板
	 */
	@Autowired
	private LocationofGoodsService locationofGoodsService;
	
	/***
	 * 退货模板
	 */
	@Autowired
	private ReturnPurchaseService returnPurchaseService;
	
	/***
	 * 买家限制
	 */
	@Autowired
	private BuyerRestrictionService buyerRestrictionService;
	
	
	/***
	 * listing 属性
	 */
	@Autowired
	private ListingPropertyService listingPropertyService;
	
	
	/***
	 * 用户组
	 */
	@Autowired
	private GroupService groupService;
	
	
	@Autowired
	private PublishStyleModeService publishStyleModeService;
	
	public EbayListing get(String id) {
		return super.get(id);
	}
	
	
	public List<EbayListing> findSimpleList(EbayListing ebayListing){
		return super.findList(ebayListing);
	}
	
	public List<EbayListing> findList(EbayListing ebayListing) {
		List<EbayListing> list = super.findList(ebayListing);
		for (EbayListing ebay : list) {
            //设置产品信息		
			if(ebay.getProductId()!=null){
				ebay.setProduct(this.productService.get(String.valueOf(ebay.getProductId())));
			}if(ebay.getShopId()!=null){
				ebay.setSeller(this.sellerService.findByShopId(ebay.getShopId()));
			}if(ebay.getSiteId()!=null){
				ebay.setPlatformSite(this.platformSiteService.get(String.valueOf(ebay.getSiteId())));
			}if(ebay.getPlatformId()!=null){
				ebay.setPlatform(platformService.get(String.valueOf(ebay.getPlatformId())));
			}
		}
		return list;
	}
	
	
	/**
	 * 查询 list 列表的时候封装相关的信息
	 */
	public Page<EbayListing> findPage(Page<EbayListing> page, EbayListing ebayListing) {
		Page<EbayListing> findPage = super.findPage(page, ebayListing);
		List<EbayListing>  list = findPage.getList();
		for (EbayListing ebay : list) {
            //设置产品信息		
			if(ebay.getProductId()!=null){
				ebay.setProduct(this.productService.get(String.valueOf(ebay.getProductId())));
			}if(ebay.getShopId()!=null){
				ebay.setSeller(this.sellerService.findByShopId(ebay.getShopId()));
			}if(ebay.getSiteId()!=null){
				ebay.setPlatformSite(this.platformSiteService.get(String.valueOf(ebay.getSiteId())));
			}if(ebay.getPlatformId()!=null){
				ebay.setPlatform(platformService.get(String.valueOf(ebay.getPlatformId())));
			}
		}
		return findPage;
	}
	
	@Transactional(readOnly = false)
	public void save(EbayListing ebayListing) {
		super.save(ebayListing);
	}
	
	@Transactional(readOnly = false)
	public void delete(EbayListing ebayListing) {
		super.delete(ebayListing);
	}
	
	@Transactional(readOnly=false)
	public void update(EbayListing listing) {
		listing.setUpdateDate(new Date());
		this.dao.update(listing);
	}

	
	/***
	 * 产品 开发成为 listing
	 * 
	 * @param productDetail
	 * @param shopId 
	 * @return
	 * @throws Exception 
	 */
	
	@Transactional(readOnly=false)
	public EbayListing saveListing(ProductDetail productDetail, Long shopId) throws Exception {
		Long begin=System.currentTimeMillis();		
		logger.info("########## 保存 listing 信息 saveListing 开始时间为：{}",begin);
		Long  listingId = Global.getID();
		ProductDto productDto = productDetail.getProduct();
		EbayListing listing = this.createListing(productDetail,listingId,shopId);
		//保存 listing 属性
		this.listingPropertyService.saveListingProperty(productDetail,listingId,productDetail.getSiteId());
		
		// 保存映射关系的  sku mapping  
		this.ebaySkuMappingService.saveListingMapping(productDetail,listingId,productDto.getId());
		//保存分类
		productCategoryService.saveProductCategory(productDetail,listingId);
		//保存 listing  图片 还有 code_m	anager 图片 
		this.ebayListingImgService.saveListingImage(productDetail,listingId);
		//保存价格   
		this.ebayListingPriceService.saveListingPrice(productDetail,listingId,productDetail.getSiteId());
		
		logger.info("########## 保存 listing 信息 saveListing 结束时间为：{},\t 耗时为 {}",System.currentTimeMillis(),(System.currentTimeMillis()-begin));
		return listing;
	}


	/***
	 * 
	 * @param productDetail
	 * @param listingId
	 * @param shopId
	 * @return
	 * @throws Exception
	 */
	private EbayListing createListing(ProductDetail productDetail, Long listingId,Long shopId) throws Exception {
		
		ProductDto product = productDetail.getProduct();
		EbayListing listing = new EbayListing();
		//这里可以复制拷贝过去 也可以一个个的赋值 
	//	PropertyUtils.copyProperties(listing, product);
		
		listing.setAutoComplementOnlineQuantity(product.getAutoComplementOnlineQuantity());
		listing.setCategoryStatus(product.getCategoryStatus());
		listing.setConditionID(product.getConditionID());
		listing.setCounter(product.getCounter());
		
		listing.setDescription(product.getDescription());//物品描述
		
		listing.setImmediatePay(product.getImmediatePay());
		listing.setListingCategory(product.getListingCategory());
		listing.setLotSize(product.getLotSize());
		listing.setModeCategory(product.getModeCategory());
		listing.setNumber(product.getNumber());
		listing.setPaymentDesc(product.getPaymentDesc());
		
		String [] paymethod = product.getPaymentMethod();
		StringBuffer bb = new StringBuffer();
		for (String method : paymethod) {
			bb.append(method+Global.SEPARATE_7);
		}
		listing.setPaymentMethod(bb.toString());
		listing.setPaypalType(product.getPaypalType());
		listing.setPersonalBelongings(product.getPersonalBelongings());
		
		//如果是单属性的话  则用自己的 否则使用 子代码的一个 
		if(Global.ONE.equals(productDetail.getMultiAttribute())){
			listing.setProductCode(product.getProductCode());
		}else{
			List<ProductCodeManager> codeManagerList = productDetail.getCodeManagerList();
			for (ProductCodeManager productCodeManager : codeManagerList) {
				if(productCodeManager!=null && StringUtils.isNotBlank(productCodeManager.getProductCode()) ){
					listing.setProductCode(productCodeManager.getProductCode());
					break;
				}
			}
		}
		listing.setSaleType(product.getSaleType());
		listing.setShelfTime(product.getShelfTime());
		listing.setStyle(product.getStyle());
		
		
		ProductTitle title = getProductTitle(product);
		if(title == null || StringUtils.isBlank(title.getMainTitle()) || StringUtils.isBlank(title.getSubTitle())){
			throw new RuntimeException("没有可用的 产品标题，请重新生成！");
		}
		
		listing.setTitle(title.getMainTitle());
		listing.setSubTitle(title.getSubTitle()); 
		
		listing.setUpsetPrice(new BigDecimal(StringUtils.isNotBlank(product.getUpsetPrice())?product.getUpsetPrice():"0"));
		listing.setWindowDisplayPicture(product.getWindowDisplayPicture());
		
		listing.setId(listingId.toString());
		listing.setTitleId(Long.valueOf(title.getMainTitleId())); 
		listing.setSubTitleId(Long.valueOf(title.getSubTitleId())); 
		listing.setSysParentSku(product.getSysParentCode());
		listing.setStatus(Global.LISTING_STATUS_ZERO);
		listing.setSiteId(Long.valueOf(productDetail.getSiteId()));
		listing.setShopId(shopId);
		listing.setProductId(Long.valueOf(product.getId()));
		listing.setParentSku(product.getListingSkuCode());
		listing.setPaymentDesc(product.getPaymentDesc());
		listing.setPlatformId(productDetail.getPlatformSite().getPlatformId());
		listing.setSellingPrice(new BigDecimal(product.getSellingPrice()==null?0:product.getSellingPrice()));
		listing.setOtherRemark(product.getRemark());
		//保存模板信息等  以及页面的多个描述 
		
		listing.setPublishStyleId(product.getPublishStyleId());
		listing.setBuyerLimitId(product.getBuyerLimitId());
		listing.setLocationId(product.getLocationId());
		listing.setLogisticsTemplateId(product.getLogisticsTemplateId());
		listing.setReturnId(product.getReturnId());
		
		// 保存刊登描述,通过 销售类型判断 应该取哪个值？？
		
		//如果是海外仓精品 或则是 虚拟海外仓精品 则取用户手动输入的数据
		if(Global.SALE_TYPE_OVERSEAS_STOREHOUSE.equals(product.getDevelopmentType()) || Global.SALE_TYPE_VIRTUAL_OVERSEAS_STOREHOUSE.equals(product.getDevelopmentType())){
			listing.setPublishDescription(productDetail.getProductDescritions2());//这是用户输入的 翻译后的
		}else if(Global.SALE_TYPE_DIRECT_SHIPMENT_DISTRIBUTION.equals(product.getDevelopmentType())||Global.SALE_TYPE_VIRTUAL_OVERSEAS_DISTRIBUTION.equals(product.getDevelopmentType())){
			// 中国直发仓或则虚拟仓 铺货
			List<String> productDescritions = productDetail.getProductDescritions();
			StringBuffer buffer = new StringBuffer();
			for (String string : productDescritions) {
				buffer.append(string+Global.SEPARATE_6);
			}
			listing.setPublishDescription(buffer.toString());
		}
		
		listing.setMultiAttribute(Global.ONE.equals(productDetail.getMultiAttribute()));//判断是不是多属性
		
		this.dao.insert(listing);
		return listing;
	}

	
	/***
	 * 获得可用的 产品 title  和  副标题 
	 * 
	 * @param product
	 * @return
	 */
	private ProductTitle getProductTitle(ProductDto product) {
		List<ProductTitle> mainTitleList = product.getMainTitleList();
		ProductTitle title = null;
		EbayListing ebay = null;
		for (ProductTitle productTitle : mainTitleList) {
			ebay = new EbayListing();
			//ebay.setTitleId(Long.valueOf(productTitle.getTitleId()));
			ebay.setTitle(productTitle.getContent());
			
			List<EbayListing> ebayListing = this.dao.findList(ebay);
			if(CollectionUtils.isNotEmpty(ebayListing)){
				continue;
			}else{
				title = new ProductTitle();
				title.setMainTitle(productTitle.getContent());
				title.setMainTitleId(productTitle.getTitleId());
				break;
			}
		}
		if(title!=null){
			List<ProductTitle> subTitleList = product.getSubTitleList();
			for (ProductTitle productTitle : subTitleList) {
				ebay = new EbayListing();
				//ebay.setSubTitleId(Long.valueOf(productTitle.getTitleId()));
				ebay.setSubTitle(productTitle.getContent());
				List<EbayListing> ebayListing = this.dao.findList(ebay);
				if(CollectionUtils.isNotEmpty(ebayListing)){
					continue;
				}else{
					title.setSubTitle(productTitle.getContent());
					title.setSubTitleId(productTitle.getTitleId());
					break;
				}
			}
		}
		
		return title;
	}

	
	/**
	 * 判断 主 g 图 是不是有重复的，选择没有使用过的主 g 图,判断 标题是不是有重复的
	 * 
	 * @param productDetail
	 * @throws Exception 
	 */
	public ProductDetail checkImageAndTtile(ProductDetail productDetail) throws Exception {
		//主 g 图 集合 
		String [] mianIds = productDetail.getMainImageId1();
		String [] mianUrls = productDetail.getMainImageUrl1();
		if(mianIds == null || mianIds.length<1 ){
			throw new Exception("请上传主g图！");
		}
		int length = mianIds.length;
		for (int i=0;i<length;i++) {
			EbayListingImg ebayListingImg = new EbayListingImg();
			ebayListingImg.setImageId(Long.valueOf(mianIds[i]));
			ebayListingImg.setImageType(Global.IMG_TYPE_MAIN);
			List<EbayListingImg> findList = this.ebayListingImgService.findList(ebayListingImg);
			if(CollectionUtils.isNotEmpty(findList)){
				mianIds=ArrayUtils.remove(mianIds, i);
				mianUrls=ArrayUtils.remove(mianUrls, i);
				length--;
				i--;
			}
		}
		//将处理后的 可用的 主 g 图 id 赋值给 product ，如果为空 则表示无 主 g 图可用
		productDetail.setMainImageId1(mianIds);
		productDetail.setMainImageUrl1(mianUrls);
		if(mianIds.length<1){
			throw new Exception("您没有可用的主 g 图！请继续上传新的");
		}
		//判断 标题和描述字段不能有敏感词
		//判断 标题 副标题  
		String title = productDetail.getProduct().getTitle();
		String subtitle = productDetail.getProduct().getSubtitle();

		//描述
		List<String> productDescritions = productDetail.getProductDescritions();
		StringBuffer  buffer = new StringBuffer();
		if(CollectionUtils.isNotEmpty(productDescritions)){
			if(CollectionUtils.isNotEmpty(productDescritions)){
				for (String str : productDescritions) {
					buffer.append(str);
				}
			}
		}
		String description = buffer.toString();
		String productDescritions2 =productDetail.getProductDescritions2();
		//敏感词校验
		SensitiveWordUtils.checkSensitiveWord(title,subtitle,description,productDescritions2);
		//标题重复校验 
//		try {
//			ElasticSearchHelper helper=new ElasticSearchHelper();
//			boolean resultStatus =  helper.getResultStatus("title", title);
//			if(resultStatus){
//				throw new Exception("标题重复了的，改动之后再提交！");
//			}	
//		} catch (Exception e) {
//			throw new Exception("标题查重异常！"+e.getLocalizedMessage());
//		}
		return productDetail;
	}

	
	/***
	 * 获得 境内境外的设置信息
	 * 
	 * @param logisticsMode
	 * @param i
	 * @param b
	 * @return
	 */
	private ShippingServiceDto getShippingService(LogisticsMode logisticsMode,int i, boolean b) {
		
		ShippingMode shippingMode = null;
		
		ShippingServiceDto service = new ShippingServiceDto();
		
		if(b){//境内的设置
			if(logisticsMode.getInteriorList()==null || logisticsMode.getInteriorList().size()<=i){
				return null;
			}
			shippingMode = logisticsMode.getInteriorList().get(i);
			
		}else{//境外的设置
			if(logisticsMode.getExternalList()==null || logisticsMode.getExternalList().size()<=i){
				return null;
			}
			shippingMode = logisticsMode.getExternalList().get(i);
			if(shippingMode.getSelectCountryList()!=null){
				// 要是数组 可到达国家
				String[] countryName = (String[]) shippingMode.getSelectCountryList().toArray();
				if(StringUtils.isNoneBlank(countryName)){
					service.setCountries(countryName);
					
				}
			}
		}
		service.setShippingServiceName(shippingMode.getShippingType());
		
		// cny 额外费用
		service.setAdditionalCnyPice(shippingMode.getAkHiPrCny());
		// 额外费用
		service.setAdditionalPice(Double.valueOf(shippingMode.getAkHiPr()));
		//首件费用 cny
		service.setCnyPrice(Double.valueOf(shippingMode.getFirstTimeFeeCny()));
		//首件费用 
		service.setMainPrice(Double.valueOf(shippingMode.getFirstTimeFee()));
		//续重 cny 价格
		service.setOverweightCnyPice(Double.valueOf(shippingMode.getRenewalFeeCny()));
		//续重 价格
		service.setOverweightPice(Double.valueOf(shippingMode.getRenewalFee()));
		
		return service;
	}

	/***
	 * 保存产品的title 和 title 关键字
	 * 
	 * @param productDetail
	 * @param shopId
	 * @return
	 */
	@Transactional(readOnly = false)
	public ProductDetail saveProductDetail(ProductDetail productDetail) {
		Long begin =System.currentTimeMillis();
		logger.info("############ EbayListingService 保存产品相关内容  saveProductDetail  开始时间为 {}ms",begin);

		ProductDto productDto = productDetail.getProduct();
		
		//保存 listing 标题
		productDto = productTitleService.saveProductTitle(productDto);
		//保存 产品的标题关键字
		titleKeyService.saveProductTitleKey(productDto);
		//设置 产品描述
		productDescriptionService.saveProductDescription(productDto.getId(),productDetail);
		
		//更新 code manager 数量和  upc 
		codeManagerService.updateProductCodeManager(productDetail.getCodeManagerList());
		
		productDetail.setProduct(productDto);
		logger.info("############ EbayListingService 保存产品相关内容  saveProductDetail  结束时间为 {}ms,\t 消耗时间为 {}ms",System.currentTimeMillis(),(System.currentTimeMillis()-begin));
		return productDetail;
	}

	
	/***
	 * 获取  listing 的详细信息
	 * 
	 * @param listingId
	 * @return
	 */
	@Transactional(readOnly = false)
	public ListingDetailDto findDetailByListing(String listingId)  throws Exception {
		EbayListing ebayListing = this.get(listingId);
		
		if(!ObjectUtils.notEqual(ebayListing, null)){
			throw new RuntimeException("查询 不到 对应的listing ，请确认数据是否正确！");
		}
		ListingDetailDto detailDto = new ListingDetailDto();
		//属性值 copy
		PropertyUtils.copyProperties(detailDto, ebayListing);
		
		//设置支付方式
		if(StringUtils.isNoneBlank(ebayListing.getPaymentMethod())){
			detailDto.setPaymentMethods(Arrays.asList(ebayListing.getPaymentMethod().split(Global.SEPARATE_7)));
		}
		//设置站点信息
		PlatformSite platformSite = this.platformSiteService.get(String.valueOf(ebayListing.getSiteId()));
		if(platformSite==null){
			throw new RuntimeException("该 listing "+ebayListing.getId()+" 站点信息查询失败，数据异常！");
		}
		detailDto.setPlatformSite(platformSite);
		Seller seller = this.sellerService.findByShopId(ebayListing.getShopId());
		if(seller==null){
			throw new RuntimeException("该 listing "+ebayListing.getId()+" 店铺信息查询失败，数据异常！");
		}
		//刊登店铺信息
		detailDto.setSeller(seller);
		//产品相关的数据
		Product product = this.productService.get(String.valueOf(ebayListing.getProductId()));
		if(product==null){
			throw new RuntimeException("该 listing "+ebayListing.getId()+" 对应 产品 信息查询失败，数据异常！");
		}
		
		detailDto.setProduct(product);
		
		// 刊登分类，item specifics 还是要实时的去拿
		List<ProductCategory> productCategory = this.productCategoryService.findBySiteAndProductId(platformSite.getSiteShortName(), ebayListing.getProductId(), true, Long.valueOf(listingId));
		detailDto.setProductCategoryList(productCategory);
		
		//多属性
		List<ListingCodeManagerDto> codeManagerList = this.ebaySkuMappingService.findListingCodeManager(ebayListing.getProductId(),Long.valueOf(listingId));
		detailDto.setListingCodeManagerList(codeManagerList);
		//设置 子 sku 中的 自定义属性的 names
		detailDto.setListingPropertyNames(getListingPropertyNames(detailDto.getListingCodeManagerList()));
		//设置 主 g 图  特效图 细节图
		detailDto = ebayListingImgService.getListingDetailImage(Long.valueOf(listingId),detailDto);
		
		
		LogisticsMode listingLogisticsMode = this.getListingLogisticsMode(ebayListing.getLogisticsTemplateId(),product,seller,platformSite,codeManagerList);
		detailDto.setLogisticsMode(listingLogisticsMode);
		if(listingLogisticsMode!=null){
			detailDto.setLogisticsTemplateId(Long.valueOf(listingLogisticsMode.getId()));
		}
		LocationofGoods listingLocationofGoods = this.getListingLocationofGoods(ebayListing.getLocationId(),platformSite,product.getDevelopmentType(),seller.getSellerName());
		detailDto.setLocationofGoods(listingLocationofGoods);
		if(listingLocationofGoods!=null){
			detailDto.setLocationId(Long.valueOf(listingLocationofGoods.getId()));
		}
		ReturnPurchase listingReturnPurchase = this.getListingReturnPurchase(ebayListing.getReturnId(),product.getDevelopmentType(),ebayListing.getSiteId());
		detailDto.setReturnPurchase(listingReturnPurchase);
		if(listingReturnPurchase!=null){
			detailDto.setReturnId(Long.valueOf(listingReturnPurchase.getId()));
		}
		BuyerRestriction listingBuyerRestriction = this.getListingBuyerRestriction(ebayListing.getBuyerLimitId());
		detailDto.setBuyerRestriction(listingBuyerRestriction);
		if(listingBuyerRestriction!=null){
			detailDto.setBuyerLimitId(Long.valueOf(listingBuyerRestriction.getId()));
		}
		PublishStyleMode listingPublishStyleMode = this.getListingPublishStyleMode(ebayListing.getPublishStyleId(),ebayListing.getSiteId());
		detailDto.setPublishStyleMode(listingPublishStyleMode);
		if(listingPublishStyleMode!=null){
			detailDto.setPublishStyleId(Long.valueOf(listingPublishStyleMode.getId()));
		}
		detailDto.setConditions(this.getConditionsByCategory(detailDto.getProductCategoryList()));
		
		return detailDto;
	} 

	
	
	
	/***
	 * 获取刊登风格模板
	 * 
	 * @param publishStyleId
	 * @return
	 */
	private PublishStyleMode getListingPublishStyleMode(Long publishStyleId,Long siteId) {
		if(publishStyleId!=null){
			return publishStyleModeService.get(String.valueOf(publishStyleId));
		 }else{
			return publishStyleModeService.autoSelectPublishStyleMode(String.valueOf(siteId));
		 }
	}


	/***
	 * 选择买家限制 模板
	 * 
	 * @param buyerLimitId
	 * @return
	 */
	private BuyerRestriction getListingBuyerRestriction(Long buyerLimitId) {
		if(buyerLimitId!=null){
			return 	buyerRestrictionService.get(String.valueOf(buyerLimitId));
		}else{
			return this.buyerRestrictionService.autoSelectBuyerRestriction();
		}
	}


	/***
	 * 如果存在该 listing 的  退货模板  则 通过id查找数据库中的，否则再去找一下 匹配规则 再去找一下  找到为止
	 * 
	 * @param returnId
	 * @return
	 */
	private ReturnPurchase getListingReturnPurchase(Long returnId,String saleType,Long siteIds) {
		
		if(returnId!=null){
			return 	returnPurchaseService.get(String.valueOf(returnId));
		}else{
			return returnPurchaseService.autoSelectReturnPurchase(saleType, String.valueOf(siteIds));
		}
		
	}


	/***
	 * 如果存在该 listing 的商品所在地模板  则 通过id查找数据库中的，否则再去找一下 匹配规则 再去找一下  找到为止
	 * 
	 * @param locationId
	 * @return
	 */
	private LocationofGoods getListingLocationofGoods(Long locationId,PlatformSite site,String saleType,String sellerName) {
		if(locationId!=null){
			return locationofGoodsService.get(String.valueOf(locationId));
		}else{
			return locationofGoodsService.autoSelectLocationofGoods(site, saleType, sellerName);
		}
	}

 
	
	/***
	 * 如果存在该 listing 的物流模板 则 通过id查找数据库中的，否则再去找一下 匹配规则 再去找一下  找到为止
	 * @param logisticsTemplateId
	 * @param product
	 * @param seller
	 * @param platformSite
	 * @param codeManagerList
	 * @return
	 */
	private LogisticsMode getListingLogisticsMode(Long logisticsTemplateId, Product product, Seller seller, PlatformSite platformSite, List<ListingCodeManagerDto> codeManagerList) {
		if(logisticsTemplateId!=null){
			return  logisticsModeService.findLogisticsMode(String.valueOf(logisticsTemplateId));
		}else{
			// 自动选择模板 
			List<Double> priceList = new ArrayList<Double>(); 
			Map<String, Object> paramMap = new HashMap<String, Object>();
			if(StringUtils.isNotBlank(product.getDeliveryWareHouse())){
				paramMap.put(JexlInstance.WEAR_HOUSE, product.getDeliveryWareHouse());//长
			}
			if(CollectionUtils.isNotEmpty(codeManagerList)){
				for (ListingCodeManagerDto codeManagerDto : codeManagerList) {
					priceList.add(codeManagerDto.getPublishPrice().doubleValue());
				}
				ListingCodeManagerDto codeManagerDtoT = codeManagerList.get(0);
				
				ProductCodeManager codeManager = this.codeManagerService.get(codeManagerDtoT.getCodeManagerId());
				if(codeManager!=null){
					//重量 长宽高 
					paramMap.put(JexlInstance.LENGTH, codeManager.getLength());//长
					paramMap.put(JexlInstance.WIDE, codeManager.getWide());//宽
					paramMap.put(JexlInstance.HEIGHT, codeManager.getHigh());//高
					paramMap.put(JexlInstance.WEIGHT, codeManager.getWeight());//重量  
				}
			}
		    return logisticsModeService.autoSelectLogisticsMode(product, platformSite, priceList, paramMap);
		}
	}

	/***
	 * 通过 code manager 中获取得到 自定义属性 得到  所有的 自定义属性的 names 
	 * 
	 * @param listingCodeManagerList
	 * @return
	 */
	private List<String> getListingPropertyNames(List<ListingCodeManagerDto> listingCodeManagerList) {
		List<String> names = new ArrayList<String>();
		if(CollectionUtils.isNotEmpty(listingCodeManagerList)){
			ListingCodeManagerDto dto = listingCodeManagerList.get(0);
			if(dto!=null){
				List<ListingProperty> listingProperty = dto.getProductPropertyList();
				if(CollectionUtils.isNotEmpty(listingProperty)){
					for (ListingProperty property : listingProperty) {
						names.add(property.getName());
					}
				}
			}
		}
		return names;
	}

	/***
	 * 通过 分类的集合 获取 分类下的 conditions 集合   只拿一级分类下的 
	 * 
	 * 
	 * @param productCategoryList
	 * @return
	 */
	private List<UsualDto> getConditionsByCategory(List<ProductCategory> productCategoryList) {
		List<UsualDto>  conditions = new ArrayList<UsualDto>();
		if(CollectionUtils.isNotEmpty(productCategoryList)){
			for (ProductCategory productCategory : productCategoryList) {
				if(productCategory!=null && productCategory.getConditions()!=null ){
					conditions.addAll(productCategory.getConditions());
				}
			}
		}
		if(CollectionUtils.isNotEmpty(conditions)){
		   List<String> names = new ArrayList<String>();
		   Iterator<UsualDto> iterator = conditions.iterator();
		   while (iterator.hasNext()) {
			   UsualDto dto = iterator.next();
			   if(names.contains(dto.getName())){
				   iterator.remove();
				   continue;
				}
			   names.add(dto.getName());
		   }
		}
		return conditions;
	}

	/***	
	 * 更新 listing  （原有的 listing 页面进行保存） 
	 * 
	 * @param detailDto
	 * @throws Exception 
	 */
	@Transactional(readOnly=false)
	public void updateListing(ListingDetailDto detailDto) throws Exception {
		
		SensitiveWordUtils.checkSensitiveWord(detailDto.getTitle(),detailDto.getSubTitle(),detailDto.getDescription(),detailDto.getPublishDescription());
		
		//把付款方式转化为 字符串 用 逗号 分隔开的
		List<String> paymentMethod = detailDto.getPaymentMethods();
		StringBuffer payMethod = new StringBuffer();
		if(CollectionUtils.isNotEmpty(paymentMethod)){
			for (String string : paymentMethod) {
				payMethod.append(string+Global.SEPARATE_7);
			}
		}
		detailDto.setPaymentMethod(payMethod.toString());
	     //更新主表
		this.update(detailDto);
		//更新相关的分类数据  productCategory 和 item specifics 
		this.productCategoryService.saveListingCategoryAndItemSpecifics(detailDto);
		// 需要进行修改的东西进行更新
		//修改相应的 价格 自定义的属性  信息等（图片已经修改过）
		this.ebaySkuMappingService.updateListingCodeManager(detailDto.getId(),detailDto.getListingCodeManagerList(),detailDto.getListingPropertyNames(),detailDto.getSiteId());
		
	}


	
	/***
	 * 查询可刊登的店铺列表,通过用户的id ，获取该用户下的 分组集合，通过分组集合 获取分组几个下的 店铺集合
	 * 
	 * @param siteId
	 * @param productId
	 * @return
	 */
	@Transactional(readOnly=false)
	public List<Seller> getCanUseSellerList(Long siteId, Long productId) {
		User user = UserUtils.getUser();
		List<Seller> userIdList = groupService.getCanUseSellerList(user.getId());
		if(CollectionUtils.isNotEmpty(userIdList)){
		    Iterator<Seller>  its = userIdList.iterator();
		    while (its.hasNext()) {
		    	Seller se = its.next();
		    	if(se==null){
		    		continue;
		    	}
		    	EbayListing ebayListing = new EbayListing();
		    	ebayListing.setShopId(Long.valueOf(se.getShopId()));
		    	ebayListing.setProductId(productId);
				List<EbayListing> ebayList = this.findSimpleList(ebayListing);
				if(CollectionUtils.isNotEmpty(ebayList)){//该 seller 有刊登该 产品 所以要 剔除
					its.remove();
				}
			}
		}
		return userIdList;
	}

	
	/***
	 * listing 保存刊登任务
	 * 
	 * @param detailDto
	 */
	@Transactional(readOnly=false)
	public void savePublishListingData(ListingDetailDto detailDto) {
		FunctionCall call = functionCallService.findByListingId(detailDto.getId());
		
		if(call!=null){
			return ;
		}
		List<ListingCodeManagerDto> listingCodeManagerList = detailDto.getListingCodeManagerList();
			
		StringBuffer content = new StringBuffer();
		if(CollectionUtils.isNotEmpty(listingCodeManagerList)){
			int size =  listingCodeManagerList.size();
				if(size>0){
					content.append(listingCodeManagerList.get(0).getSku()+Global.SEPARATE_4);
					content.append(listingCodeManagerList.get(size-1).getSku());
				}
			}
		//	SimpleDateFormat t = new SimpleDateFormat("HH:mm:ss");
			Date[] dates = null;
			try {
						/**
						 * 随机产生一组时间对象，开始的时间startTime参数所指定的时间及当天以时间对象遵守以下参数所指定的规则
						 * 
						 * @param startTime
						 *            开始时间
						 * @param duration
						 *            标准时间长度(单位：分钟)
						 * @param days
						 *            跨越天数
						 * @param count
						 *            产生的随机时间对象个数
						 * @param minInterval
						 *            最小时间间隔(单位：秒)
						 * @return
						 */
						dates = RandTimeUtils.createRandomTime(new Date(),60*10,1, 1,30);
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage(), e);
					}
					
			
				int imageNum = 0;
			
				List<EbayListingImg> imageList = ebayListingImgService.findList(new EbayListingImg(Long.valueOf(detailDto.getId()), null))  ;
				 if(CollectionUtils.isNotEmpty(imageList)){
					 imageNum= imageList.size();
				 }
				
				FunctionCall functionCall = new FunctionCall(Global.getID().toString(), detailDto.getId(), String.valueOf(detailDto.getShopId()),String.valueOf(detailDto.getPlatformId()), Global.FUNCTION_OPER_TYPE_ADD, content.toString(), Global.FUNCTION_STUTAS_WAITING.toString(), new Date(),dates[0], Global.ZERO, imageNum);
				functionCall.setIsNewRecord(true);
				functionCall.setPlatformType(Global.PLATFORM_FLAG_EBAY);
				functionCallService.save(functionCall);
					
	}
	
	
	
	
	
	/***
	 * 通过 刊登任务的id 获取 listing 的详细信息（ 可进行刊登的数据模型 ） 
	 * 
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public ItemDto getListingByFunctionCall(String id) throws Exception{
		
		ItemDto itemDto = new ItemDto();
		
		Long begin = System.currentTimeMillis();
		logger.info("####################当前时间为  {}， 通过刊登任务的id={} 查询可以进行 刊登的  listing 数据",DateUtils.formatDateTime(new Date(begin)),id);
		
		FunctionCall function = this.functionCallService.get(id);
		if(function==null){
			logger.error("#################### 通过刊登任务的id={} 查询可以进行 刊登的  listing 数据,查询不到给刊登任务。",id);
			throw  new  Exception("刊登任务查询失败！");
		}
		String listingId = function.getListingId();
		if(StringUtils.isBlank(listingId)){
			throw  new  Exception("该 刊登任务 "+id+"对应的 listingId 不存在！");
		}
		
		EbayListing listing = this.dao.get(listingId);
		if(listing == null){
			throw  new  Exception("该 刊登任务 "+id+"对应的 listing不存在！");
		}
		
		PlatformSite platformSite = this.platformSiteService.get(String.valueOf(listing.getSiteId()));
		//获得该 listing 所有图片
		ListingDetailDto dto = this.ebayListingImgService.getListingDetailImage(Long.valueOf(listingId), null);
				
		Map<String, Object> categoryAndItemSpecifics = getCategoryAndItemSpecifics(Long.valueOf(listingId), listing.getProductId());
		
		ProductCategory cate1 =	(ProductCategory)categoryAndItemSpecifics.get(Global.ONE);
		ProductCategory cate2 =	(ProductCategory)categoryAndItemSpecifics.get(Global.TWO);
		
		itemDto.setAutoComplementOnlineQuantity(Global.ONE.equals(listing.getAutoComplementOnlineQuantity()));
		itemDto.setBuyer(setItemBuyer(String.valueOf(listing.getBuyerLimitId())));
		if(cate1!=null){
			itemDto.setCategoryId(cate1.getCategoryCode());//设置 listing 分类的相关
			itemDto.setCategoryName(cate1.getCategoryName());
		}
		
		itemDto.setConditionDescription(listing.getDescription());//物品描述
		itemDto.setConditionID(listing.getConditionID());
		itemDto.setConditionStatus(listing.getCategoryStatus());//物品状况
		itemDto.setCurrency(SiteCurrencyEnum.getCurrencyName(platformSite.getSiteShortName()));
		itemDto.setDescription(listing.getPublishDescription());// 刊登描述
		//itemDto.setDispatchTimeMax(listing.);//发货期限
		itemDto.setFloorPrice(listing.getUpsetPrice().doubleValue());//拍卖底价
		itemDto.setGalleryType(listing.getWindowDisplayPicture());//橱窗展示图片类型
		itemDto.setHitCounter(listing.getCounter());//计数器
//		itemDto.setItemId("");//不需要
		itemDto.setItemPictures(getItemPictures(dto.getDetailListingImg()));//细节图
		if(categoryAndItemSpecifics.get(Global.THREE)!=null){
			itemDto.setItemSpecifics((List<ItemSpecificsDto>) categoryAndItemSpecifics.get(Global.THREE));
		}
		itemDto.setListingDuration(listing.getShelfTime());//上架时间  days_1 
		itemDto.setListingType(listing.getListingCategory());//listing分类
		itemDto.setLotSize(Integer.valueOf(listing.getLotSize()));
		itemDto.setModelType("");//模板分类
		itemDto.setNote(listing.getOtherRemark());//其他备注
		itemDto.setOtherStyle(listing.getStyle());//样式
		itemDto.setPayDetail(this.getPayMentDetail(listing));
		itemDto.setPictures(getMainItemPictures(dto.getMainListingImg()));
		itemDto.setPrivateListing(Global.ONE.equals(listing.getPersonalBelongings()));//是不是私有的   1 私有 		
		itemDto.setProductCode(listing.getSysParentSku());//产品母代码
		itemDto.setProductCountry(platformSite.getSiteShortName());//国家  UK 国家简写
		itemDto.setProductId(listing.getProductCode());//UPC EAN 码
		LocationofGoods locationofGoods = this.getProductLocation(String.valueOf(listing.getLocationId()));
		
		itemDto.setProductLocation(locationofGoods.getGoodsAddr());//商品地址
		itemDto.setProductModelName("");//商品模板名称
		itemDto.setQuantity(Integer.valueOf(listing.getNumber()));//数量
//		itemDto.setQuantityThreshold(100);//限制数量  不需要
		
		itemDto.setRefundDetail(this.getRefundDetail(String.valueOf(listing.getReturnId())));// 设置退款详情
		itemDto.setSellerName(this.getSellerName(listing.getShopId()));//卖家账号
		LogisticsMode logisticsMode = this.logisticsModeService.findLogisticsMode(String.valueOf(listing.getLogisticsTemplateId()));
		itemDto.setShippingLocalDetail(this.getShipLocalDetail(logisticsMode,true));//境内物流设置
		itemDto.setShippingOutsideDetail(this.getShipLocalDetail(logisticsMode,false));//境外物流
		itemDto.setSite(platformSite.getSiteShortName());//站点
		itemDto.setSpecialPictures(this.getItemPictures(dto.getSpecialListingImg()));//特效图片
		itemDto.setStartPrice(listing.getSellingPrice().doubleValue());//一口价 
		itemDto.setStyle("");//不需要
		if(cate2!=null){
			itemDto.setSubCategoryName(cate2.getCategoryName());
			itemDto.setSubCategoryId(cate2.getCategoryCode());
		}
		itemDto.setSubTitle(listing.getSubTitle());
		itemDto.setTitile(listing.getTitle());
		//如果没有多属性的话 就不要设置这些东西
		ListingProperty   property =  new ListingProperty();
		property.setListingId(Long.valueOf(listingId));
		List<ListingProperty> listingProperty = this.listingPropertyService.findList(property);
		if(CollectionUtils.isNotEmpty(listingProperty) && listing.isMultiAttribute()){
			itemDto.setVariations(this.getVariations(listingId,listing.getProductId()));
			itemDto.setVariationSets(this.findVariationSet(listingId));
		}
		itemDto.setZipCode(locationofGoods.getPostCode());
		logger.info("####################当前时间为  {}， 通过刊登任务的id={} 查询可以进行 刊登的  listing 数据,处理结束 ，耗时为 {}  ms，得到的 listing 详情数据为  :{}",DateUtils.formatDateTime(new Date(begin)),id,(System.currentTimeMillis()-begin),JSON.toJSONString(itemDto));
		return itemDto;
		
	}
	
	
	
	/***
	 * 获得该 listing 的所有多属性的集合 
	 * 
	 * @param listingId
	 * @return
	 */
	private List<VariationSet> findVariationSet(String listingId) {
	   List<VariationSet> variationSets =	new ArrayList<VariationSet>();
	   Map<String, List<String>> map = listingPropertyService.findListingVariation(listingId);
	   map.forEach((k,v)->{
		   VariationSet varset = new VariationSet();
		   varset.setVariationName(k);
		   varset.setVariationValues((String[])v.toArray(new String[0]));
		   variationSets.add(varset);
	   });
//	   for (Map.Entry<String, List<String>> entry : map.entrySet()) {
//		   VariationSet varset = new VariationSet();
//		   varset.setVariationName(entry.getKey());
//		   varset.setVariationValues((String[]) entry.getValue().toArray(new String[0]));
//		   variationSets.add(varset);
//	  }
		return variationSets;
	}


	
	/***
	 * 获取 listing 自定义属性 
	 * 
	 * @param listingId
	 * @param productId
	 * @return
	 */
	private List<VariationDto> getVariations(String listingId, Long productId) {
		//设置 多属性 （code_manager 属性）
		List<VariationDto> variations = new ArrayList<VariationDto>();
		
		List<ListingCodeManagerDto> codeManagers = this.ebaySkuMappingService.findListingCodeManager(productId, Long.valueOf(listingId));
		if(CollectionUtils.isNotEmpty(codeManagers)){
			Iterator<ListingCodeManagerDto> iterator = codeManagers.iterator();
			VariationDto  variationDto = null; 
			while (iterator.hasNext()) {
				ListingCodeManagerDto dto = iterator.next();
				variationDto = new VariationDto();
				EbayListingImg codeManagerImage = dto.getCodeManagerImage();
				if(codeManagerImage!=null){
					variationDto.setPictureUrl(codeManagerImage.getTransferUrl());// 图片的url
				}
				variationDto.setPrice(dto.getPublishPrice().doubleValue());//价格
				variationDto.setQuantiy(dto.getRecommendNumber());
				variationDto.setSku(dto.getSku());//子 sku
				
				List<ListingProperty> productPropertyList = dto.getProductPropertyList();
				if(CollectionUtils.isNotEmpty(productPropertyList)){
					Iterator<ListingProperty> it = productPropertyList.iterator();
					List<ItemSpecificsDto> specifics =new ArrayList<ItemSpecificsDto>();
					while (it.hasNext()) {
						ListingProperty property = it.next();
						ItemSpecificsDto itemSpecificsDto = new ItemSpecificsDto();
						itemSpecificsDto.setName(property.getName());
						itemSpecificsDto.setRemark(property.getRemarks());
						itemSpecificsDto.setValue(property.getValue());
						specifics.add(itemSpecificsDto);
					}
					variationDto.setSpecifics(specifics);
				}
				variationDto.setUpcEan(dto.getProductCode());
				variations.add(variationDto);
			}
		}
		
		return variations;
	}


	/***
	 * 设置 ebay 刊登的 buyer 
	 * 
	 * @param buyerId
	 * @return
	 */
	private BuyerDetailDto setItemBuyer(String buyerId) {
		BuyerDetailDto buyer = new BuyerDetailDto();
		BuyerRestriction buyerRestriction = this.buyerRestrictionService.get(buyerId);
		if(StringUtils.isNotBlank(buyerRestriction.getScoreRestriction())){
			buyer.setBuyerBadScore(Integer.valueOf(buyerRestriction.getScoreRestriction()));// 差评评分限制
		}
		buyer.setIshavePaypalAccount(Global.ONE.equals(buyerRestriction.getPaypalAccount()));//是不是拥有 paypal 账户
		if(StringUtils.isNotBlank(buyerRestriction.getNoPaymentTimes())){
			buyer.setMaxUnPaiedCount(Integer.valueOf(buyerRestriction.getNoPaymentTimes()));//订单未付款次数
		}
		if(StringUtils.isNotBlank(buyerRestriction.getLimitAuctionsTimes())){
			buyer.setSaleCounts(Integer.valueOf(buyerRestriction.getLimitAuctionsTimes()));//限制拍卖次数
		}
		buyer.setUnPaidPeriod(buyerRestriction.getOrderBreachTime());//未付款周期
		buyer.setViolationPeriod(buyerRestriction.getBreachTime());//违反周期
		if(StringUtils.isNotBlank(buyerRestriction.getViolationFreq())){
			buyer.setMaxViolations(Integer.valueOf(buyerRestriction.getViolationFreq()));//违反次数
		}
		return buyer;
	}

	
	
	/***
	 * 获取 该listing 的  自定义分类 和 item specifics 
	 * 
	 * 1 分类一
	 * 2 分类二
	 * 0 自定义分类
	 * 3 所有的 itemSpecifics
	 * 
	 * 现在是获取 二级和自定义的 item specifics 
	 * 
	 * @param listingId
	 * @param productId 
	 * @return
	 */
	private Map<String, Object> getCategoryAndItemSpecifics(Long listingId, Long productId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<ItemSpecificsDto> dtos = new ArrayList<ItemSpecificsDto>();
		List<ProductCategory> categorys = this.productCategoryService.findByListingId(listingId,productId);
		Map<String, ItemSpecificsDto> itemSpecificsDtoMap=new HashMap<String, ItemSpecificsDto>();
		if(CollectionUtils.isNotEmpty(categorys)){
			Iterator<ProductCategory> iterator = categorys.iterator();
			while (iterator.hasNext()) {
				ProductCategory category = iterator.next();
				resultMap.put(String.valueOf(category.getOrderNum()), category);
//				if(Global.ONE.equals(category.getOrderNum())){ //二级分类  和 自定义分类  获取 item specifics 
//					continue;
//				}
				if(CollectionUtils.isNotEmpty(category.getItemSpecificsList())){
					Iterator<ItemSpecifics> it = category.getItemSpecificsList().iterator();
					while(it.hasNext()){
						ItemSpecifics item = it.next();
						ItemSpecificsDto dto = new ItemSpecificsDto();
						dto.setName(item.getName());
						dto.setRemark(item.getRemark());
						dto.setValue(item.getValue());
						dto.setType(item.getType());
						itemSpecificsDtoMap.put(item.getName(), dto);
						//dtos.add(dto);
					}
				}
			}
		}
		//item specifics 不能有重复
		itemSpecificsDtoMap.forEach((k,v)->{
			 dtos.add(v);
		});
		resultMap.put(Global.THREE,dtos);
		return resultMap;
	}

	/***
	 * 获得该 listing 的 刊登  图片 
	 * 
	 * @param list
	 * @return
	 */
	private List<PictureDto> getItemPictures(List<EbayListingImg> list) {
		List<PictureDto> pictureDtos = new ArrayList<PictureDto>();
		if(CollectionUtils.isNotEmpty(list)){
		    Iterator<EbayListingImg> iterator = list.iterator();	
		    while(iterator.hasNext()){
		    	EbayListingImg image = iterator.next();
		    	PictureDto imageDto = new PictureDto();
		    	imageDto.setName(image.getId());
		    	imageDto.setUrl(image.getTransferUrl());//
//		    	imageDto.setUrl(FastdfsHelper.getImageUrl()+image.getImageUrl());//图片地址为  fastdfs 的服务地址
		    	pictureDtos.add(imageDto);
		    }
		}		
		return pictureDtos;
	}

	/***
	 * 获得 主 图的 
	 * @param mainListingImg
	 * @return
	 */
	private PictureDto getMainItemPictures(EbayListingImg mainListingImg) {
		PictureDto dto = new PictureDto();
		dto.setName(mainListingImg.getId());
		dto.setUrl(mainListingImg.getTransferUrl());
		return dto;
	}
	
	
	/***
	 * 设置该 listing 的付款详情
	 * 
	 * @param listing
	 * @return
	 */
	private PayMentDetailDto getPayMentDetail(EbayListing listing) {
		PayMentDetailDto dto = new PayMentDetailDto();
		dto.setPayDescription(listing.getPaymentDesc());//付款描述
		String [] arr = listing.getPaymentMethod().split(Global.SEPARATE_7);
		dto.setPaymentMethod(arr);//收款方式
		dto.setPayNow(Global.TWO.equals(listing.getImmediatePay()));//1 非立即付款  2代表是 立即付款	
		dto.setPaypalMethod(listing.getPaypalType());//paypal 支付方式  大额小额账户
		return dto;
	}

	/***
	 * 获得商品地址 邮编信息
	 * @param locationId
	 * @return
	 */
	private LocationofGoods getProductLocation(String locationId) {
		LocationofGoods goods = this.locationofGoodsService.get(locationId);
		return goods;
	}
	

	/***
	 * 获取 退款详情设置 
	 * 
	 * @param returnId
	 * @return
	 */
	private RefundDetailDto getRefundDetail(String returnId) {
		ReturnPurchase returnPurchase = this.returnPurchaseService.get(returnId);
		if(returnPurchase==null){
			return null;
		}
		RefundDetailDto dto = new RefundDetailDto();
		dto.setExtendeRefundDays(StringUtils.isNotBlank(returnPurchase.getHolidayReturn())); //延长退货期限至..   holiday return
		dto.setRefundAccept(returnPurchase.getReciveReturn());//接受退货
		dto.setRefundCostBy(returnPurchase.getReturnPostage());//退货邮费承担
		dto.setRefundDescription(returnPurchase.getReturnPolicy());//退货说明
		dto.setRefundFeeValue(returnPurchase.getRestockingFeeValue());//退货手续费
		dto.setRefundModelName("");//不需要
		dto.setRefundStrategy(returnPurchase.getRefundPolice());//退货策略
		dto.setRefundType(returnPurchase.getReturnMode());//退货方式
		dto.setRefundWithin(returnPurchase.getReciveReturnPeriod());// 退货期限   类似 30 Days??
		return dto;
	}
	
	/***
	 * 通过shopId 获取 seller name 
	 * 
	 * @param shopId
	 * @return
	 */
	private String getSellerName(Long shopId) {
		Seller seller = this.sellerService.findByShopId(shopId);
		return seller.getSellerName();
	}
	
	/***
	 * 
	 * @param logisticsMode
	 * @param flag  true 表示境内物流   false  表示境外物流 
	 * @return
	 */
	private ShippingDetail getShipLocalDetail(LogisticsMode logisticsMode,boolean flag) {
		
		if(logisticsMode==null){ 
			return null;
		}
		ShippingDetail shippingLocalDetail = new ShippingDetail();
		
			shippingLocalDetail.setBuyingTime(logisticsMode.getCommondityProcurementCycle());// 商品采购周期  天数 1 ??
			//数组
			if(StringUtils.isNoneBlank(logisticsMode.getShieldDestination())){
				String [] countryName = logisticsMode.getShieldDestination().split(Global.SEPARATE_12);
				shippingLocalDetail.setExCountries(countryName);////屏蔽目的地  英文的  
			}
			shippingLocalDetail.setLocalPickup(logisticsMode.getLocalPickup());
			shippingLocalDetail.setReadyTime(logisticsMode.getStockUpTime());//备货时间  1天 2天 数字
			
			shippingLocalDetail.setService1(getShippingService(logisticsMode,0,flag));
//			shippingLocalDetail.setService2(getShippingService(logisticsMode,1,flag));
//			shippingLocalDetail.setService3(getShippingService(logisticsMode,2,flag));
//			shippingLocalDetail.setService4(getShippingService(logisticsMode,3,flag));
//			shippingLocalDetail.setService5(getShippingService(logisticsMode,4,flag));
//			
			
//			ShippingDetail shippingOutsideDetail = new ShippingDetail();
//			shippingOutsideDetail.setService1(getShippingService(logisticsMode,0,false));
//			shippingOutsideDetail.setService2(getShippingService(logisticsMode,1,false));
//			shippingOutsideDetail.setService3(getShippingService(logisticsMode,2,false));
//			shippingOutsideDetail.setService4(getShippingService(logisticsMode,3,false));
//			shippingOutsideDetail.setService5(getShippingService(logisticsMode,4,false));
		
		return shippingLocalDetail;
	}

}