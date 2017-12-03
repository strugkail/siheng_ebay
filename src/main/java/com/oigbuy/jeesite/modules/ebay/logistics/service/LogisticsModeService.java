/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.logistics.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.oigbuy.api.core.request.ebay.GetEbayShippingLocationRequest;
import com.oigbuy.api.core.request.ebay.GetEbayShippingServiceRequest;
import com.oigbuy.api.core.response.ebay.GetEbayShippingLocationResponse;
import com.oigbuy.api.core.response.ebay.GetEbayShippingServiceResponse;
import com.oigbuy.api.domain.ebay.ShippingServerDTO;
import com.oigbuy.api.domain.ebay.UsualDto;
import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.common.utils.enums.SiteCurrencyEnum;
import com.oigbuy.jeesite.common.utils.formula.JexlInstance;
import com.oigbuy.jeesite.modules.country.entity.Country;
import com.oigbuy.jeesite.modules.country.service.CountryService;
import com.oigbuy.jeesite.modules.ebay.logistics.dao.LogisticsModeDao;
import com.oigbuy.jeesite.modules.ebay.logistics.entity.LogisticsMode;
import com.oigbuy.jeesite.modules.ebay.logistics.entity.SendToCountryParam;
import com.oigbuy.jeesite.modules.ebay.logistics.entity.ShippingMode;
import com.oigbuy.jeesite.modules.ebay.logistics.entity.ShippingTypeParam;
import com.oigbuy.jeesite.modules.ebay.mode.entity.ShieldCountryMode;
import com.oigbuy.jeesite.modules.ebay.mode.service.ShieldCountryModeService;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.PlatformSite;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.service.PlatformSiteService;
import com.oigbuy.jeesite.modules.ebay.product.entity.Product;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductCodeManager;
import com.oigbuy.jeesite.modules.ebay.product.service.ProductService;
import com.oigbuy.jeesite.modules.ebay.template.entity.Seller;
import com.oigbuy.jeesite.modules.parcel.entity.Parcel;
import com.oigbuy.jeesite.modules.parcel.service.ParcelService;

/**
 * 物流设置Service
 * @author 王汉东
 * @version 2017-09-05
 */
@Service
@Transactional(readOnly = true)
public class LogisticsModeService extends CrudService<LogisticsModeDao, LogisticsMode> {

	@Autowired
	private PlatformSiteService platformSiteService;
	@Autowired
	private LogisticsModeDao logisticsModeDao;
	@Autowired
	private ShippingModeService shippingModeService;
	@Autowired
	private ShieldCountryModeService shieldCountryModeService;
	@Autowired
	private CountryService countryService;
	@Autowired
	private ParcelService parcelService;
	
	@Autowired
	private ProductService productService;
	
	public LogisticsMode get(String id) {
		return super.get(id);
	}
	
	public List<LogisticsMode> findList(LogisticsMode logisticsMode) {
		return super.findList(logisticsMode);
	}
	
	public Page<LogisticsMode> findPage(Page<LogisticsMode> page, LogisticsMode logisticsMode) {
		return super.findPage(page, logisticsMode);
	}
	
	@Transactional(readOnly = false)
	public void save(LogisticsMode logisticsMode) {
		super.save(logisticsMode);
	}
	
	@Transactional(readOnly = false)
	public void saveAll(LogisticsMode logisticsMode) {
		save(logisticsMode);
		List<ShippingMode> interiorList=logisticsMode.getInteriorList();
		List<ShippingMode> externalList=logisticsMode.getExternalList();
		//保存或更新境内运输方式
		for(int i=0;i<interiorList.size();i++){
			ShippingMode shippingMode=interiorList.get(i);
			//在添加的时候shippingType().length=0,不保存，shippingType.length>0保存
			if(shippingMode.getShippingType().length()>0){
				shippingMode.setModeId(logisticsMode.getId());
				shippingModeService.save(shippingMode);
			}
			//在修改的时候如果shippingType.length()==0则删除该shippingMode
			if(shippingMode.getShippingType().length()==0){
				if(null!=shippingMode.getId()&&shippingMode.getId().length()>0){
					shippingModeService.delete(shippingMode);
				}
			}
		}
		//保存或更新境外运输方式
		for(int i=0;i<externalList.size();i++){
			ShippingMode shippingMode=externalList.get(i);
			if(shippingMode.getShippingType().length()>0){
				List<String> list=shippingMode.getSelectCountryList();
				if(null!=list){
					String country=StringUtils.join(list.toArray(), Global.SEPARATE_7);
					shippingMode.setCountry(country);
				}
				shippingMode.setModeId(logisticsMode.getId());
				shippingModeService.save(shippingMode);
			}
			//在修改的时候如果shippingType.length()==0则删除该shippingMode
			if(shippingMode.getShippingType().length()==0){
				if(null!=shippingMode.getId()&&shippingMode.getId().length()>0){
					shippingModeService.delete(shippingMode);;
				}
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(LogisticsMode logisticsMode) {
		super.delete(logisticsMode);
	}
	
	@Transactional(readOnly = false)
	public void deleteAll(LogisticsMode logisticsMode) {
		delete(logisticsMode);
		String modeId=logisticsMode.getId();
		shippingModeService.deleteByModeId(modeId);
	}
	
	
	
	public LogisticsMode findListById(String id) {
		return logisticsModeDao.findListById(id);
	}
	
	public List<LogisticsMode> getLogisticsModeList(String id){
		List<LogisticsMode>  modeList= this.findList(null);
		for (LogisticsMode logisticsMode : modeList) {
			ShippingMode shippingMode=new ShippingMode();
			shippingMode.setModeId(logisticsMode.getId());
			List<ShippingMode> list=shippingModeService.findList(shippingMode);
			List<ShippingMode> interiorList=new ArrayList<ShippingMode>();
			List<ShippingMode> externalList=new ArrayList<ShippingMode>();
			for (ShippingMode mode : list) {
				if(Global.LOGISTIC_MODE_TERRITORY.equals(mode.getType())){
					interiorList.add(mode);
				}else if(Global.LOGISTIC_MODE_OVERSEAS.equals(mode.getType())){
					externalList.add(mode);
				}
			}
			logisticsMode.setInteriorList(interiorList);
			logisticsMode.setExternalList(externalList);
			ShieldCountryMode shieldCountryModes= this.shieldCountryModeService.get(logisticsMode.getShieldDestination());
			//查询屏蔽目的地
			List<Country> countryParam= countryService.findAllNameList();
			Map<String,String> maps = new HashMap<String,String>();
			for(Country c : countryParam){
				maps.put(c.getId(), c.getCnName());
				
			}
				StringBuilder countryName=new StringBuilder();
				String countryId=shieldCountryModes.getCountryId();
				String [] countryArray=countryId.split(Global.SEPARATE_7);
				for(int j=0;j<countryArray.length;j++){
					if(j!=countryArray.length-1){
						countryName.append(maps.get(countryArray[j])+Global.SEPARATE_7);
					}else{
						countryName.append(maps.get(countryArray[j]));
					}
				}
				shieldCountryModes.setCountryName(countryName.toString());
				logisticsMode.setShieldCountryMode(shieldCountryModes);
		}
		return modeList;
	}
	/**
	 * 进入物流设置添加或编辑页面的逻辑
	 * @author handong.wang
	 * @param logisticsMode
	 * @param model
	 */
	public void form(LogisticsMode logisticsMode, Model model){
		model.addAttribute("logisticsMode", logisticsMode);
		//如果是进入编辑页面，则需要关联查询运输模板
		if(logisticsMode.getId()!=null){
			ShippingMode shippingMode=new ShippingMode();
			shippingMode.setModeId(logisticsMode.getId());
			
			List<ShippingMode> shippingModelist=shippingModeService.findList(shippingMode);
			
			List<ShippingMode> interiorList=new ArrayList<ShippingMode>();
			List<ShippingMode> externalList=new ArrayList<ShippingMode>();
			for (ShippingMode mode : shippingModelist) {
				if(Global.LOGISTIC_MODE_TERRITORY.equals(mode.getType())){
					interiorList.add(mode);
				}else if(Global.LOGISTIC_MODE_OVERSEAS.equals(mode.getType())){
					String country=mode.getCountry();
					if(StringUtils.isNotBlank(country)){
						List<String> selectCountryList = Arrays.asList(country.split(Global.SEPARATE_7));  
						mode.setSelectCountryList(selectCountryList);
					}
					externalList.add(mode);
				}
			}
			
			logisticsMode.setInteriorList(interiorList);
			logisticsMode.setExternalList(externalList);
		}
		//查询站点
		List<PlatformSite> platformSitlist=platformSiteService.getListByPlatName(Global.TERRACE_TYPE_EBAY);
		
		//查询包裹
		List<Parcel> parcelList=parcelService.findList(null);
		//查询屏蔽目的地
		List<ShieldCountryMode> shieldCountryModesList=shieldCountryModeService.findList(null);
		Integer size=shieldCountryModesList.size();
		
		List<Country> countryParam= countryService.findAllNameList();
		Map<String,String> maps = new HashMap<String,String>();
		for(Country c : countryParam){
			maps.put(c.getId(), c.getCnName());
		}
		for(int i=0;i<size;i++){
			StringBuilder countryName=new StringBuilder();
			ShieldCountryMode shieldCountryMode=shieldCountryModesList.get(i);
			String countryId=shieldCountryMode.getCountryId();
			String [] countryArray=countryId.split(Global.SEPARATE_7);
			for(int j=0;j<countryArray.length;j++){
				if(j!=countryArray.length-1){
					countryName.append(maps.get(countryArray[j])+Global.SEPARATE_7);
				}else{
					countryName.append(maps.get(countryArray[j]));
				}
			}
			shieldCountryMode.setCountryName(countryName.toString());
		}
		//设置可运至国家
		List<SendToCountryParam> countryList=new ArrayList<SendToCountryParam>();
		String siteName=null;
		if(StringUtils.isNotBlank(logisticsMode.getSiteId())){
			siteName=platformSiteService.get(logisticsMode.getSiteId()).getSiteShortName();
			countryList=getSendToCountry(siteName);
		}
		model.addAttribute("countryList",countryList);
		//设置运输方式：
		List<ShippingTypeParam> shippingTypeInsideList=new ArrayList<ShippingTypeParam>();
		List<ShippingTypeParam> shippingTypeOutsideList=new ArrayList<ShippingTypeParam>();
		if(StringUtils.isNotBlank(logisticsMode.getSiteId())){
			shippingTypeInsideList=getShippingTypeInside(siteName);
			shippingTypeOutsideList=getShippingTypeOutside(siteName);
		}
		model.addAttribute("shippingTypeInsideList",shippingTypeInsideList);
		model.addAttribute("shippingTypeOutsideList",shippingTypeOutsideList);
		model.addAttribute("platformSitlist",platformSitlist);
		model.addAttribute("parcelList",parcelList);
		model.addAttribute("shieldCountryModesList",shieldCountryModesList);
	}
	
	/**
	 * 物流设置，动态获取发送至国家
	 * @author handong.wang
	 * @param siteName
	 * @return
	 */
	public List<SendToCountryParam> getSendToCountry(String siteName) {
		List<SendToCountryParam> list = new ArrayList<SendToCountryParam>();
		GetEbayShippingLocationRequest request=new GetEbayShippingLocationRequest(siteName);
		GetEbayShippingLocationResponse response = request.execute();
		List<UsualDto> countrys = response.getShippingLocations();
		if(CollectionUtils.isNotEmpty(countrys)){
			SendToCountryParam param=null;
			for (UsualDto usualDto : countrys) {
				param=new SendToCountryParam();
				param.setDescription(usualDto.getDescription());
				param.setName(usualDto.getName());
				list.add(param);
			}
		}
		return list;
	}
	/**
	 * 获取境内运输方式
	 * @author handong.wang
	 * @param siteName
	 * @return
	 */
	public List<ShippingTypeParam> getShippingTypeInside(String siteName) {

		List<ShippingTypeParam> list = new  ArrayList<ShippingTypeParam>();
		GetEbayShippingServiceRequest request = new GetEbayShippingServiceRequest();
		    request.setSite(siteName);
		GetEbayShippingServiceResponse response = request.execute();
		    List<ShippingServerDTO> shippingTypes = response.getServers();
		if(CollectionUtils.isNotEmpty(shippingTypes)){
			ShippingTypeParam param=null;
			for (ShippingServerDTO shippingServerDTO : shippingTypes) {
				param=new ShippingTypeParam();
				//运输方式区分境内
				if(!shippingServerDTO.getInternationalService()){
					param.setName(shippingServerDTO.getShippingService());
					if(StringUtils.isNotBlank(shippingServerDTO.getShippingTimeMin())&&StringUtils.isNotBlank(shippingServerDTO.getShippingTimeMax())){
						param.setDescription(shippingServerDTO.getDescription()+"("+shippingServerDTO.getShippingTimeMin()+"-"+shippingServerDTO.getShippingTimeMax()+"days)");
					}else{
						param.setDescription(shippingServerDTO.getDescription());
					}
					list.add(param);
				}
			}
		}
		return list;
	}
	/**
	 * 获取境外运输方式
	 * @author handong.wang
	 * @param siteName
	 * @return
	 */
	public List<ShippingTypeParam> getShippingTypeOutside(String siteName) {
		
		List<ShippingTypeParam> list = new  ArrayList<ShippingTypeParam>();
		GetEbayShippingServiceRequest request = new GetEbayShippingServiceRequest();
		request.setSite(siteName);
		GetEbayShippingServiceResponse response = request.execute();
		List<ShippingServerDTO> shippingTypes = response.getServers();
		if(CollectionUtils.isNotEmpty(shippingTypes)){
			ShippingTypeParam param=null;
			for (ShippingServerDTO shippingServerDTO : shippingTypes) {
				param=new ShippingTypeParam();
				//运输方式区分境外
				if(shippingServerDTO.getInternationalService()){
					param.setName(shippingServerDTO.getShippingService());
					if(StringUtils.isNotBlank(shippingServerDTO.getShippingTimeMin())&&StringUtils.isNotBlank(shippingServerDTO.getShippingTimeMax())){
						param.setDescription(shippingServerDTO.getDescription()+"("+shippingServerDTO.getShippingTimeMin()+"-"+shippingServerDTO.getShippingTimeMax()+"days)");
					}else{
						param.setDescription(shippingServerDTO.getDescription());
					}
					list.add(param);
				}
			}
		}
		return list;
	}
	
	/***
	 * 通过 物流模板的id  获取 物流数据 
	 * 
	 * @param id
	 * @return
	 */
	public LogisticsMode findLogisticsMode(String id) {
		
		LogisticsMode mode = this.get(id);

		if(mode == null){
			return null;
		}
		
		ShippingMode shippingMode = new ShippingMode();
		shippingMode.setModeId(mode.getId());
		List<ShippingMode> list = shippingModeService.findList(shippingMode);

		List<ShippingMode> interiorList = new ArrayList<ShippingMode>();
		List<ShippingMode> externalList = new ArrayList<ShippingMode>();

		for (ShippingMode shipping : list) {
			if (Global.ONE.equals(shipping.getType())) {
				interiorList.add(shipping);
			} else if (Global.TWO.equals(shipping.getType())) {
				//把 country 可到达国家转化为 list 
				if(shipping.getCountry()!=null){
					List<String> selectCountryList = Arrays.asList(shipping.getCountry().split(Global.SEPARATE_7));  
					shipping.setSelectCountryList(selectCountryList);
				}
				externalList.add(shipping);
			}
		}
		mode.setExternalList(externalList);
		mode.setInteriorList(interiorList);
		// 屏蔽目的地
		ShieldCountryMode shieldCountry = this.shieldCountryModeService.get(mode.getShieldDestination());

		if (shieldCountry != null) {

			StringBuilder contryNameStr = new StringBuilder();

			List<Country> countryList = this.countryService.findAllNameList();
			for (Country country : countryList) {
				if (shieldCountry.getCountryId().contains(country.getId())) {
					contryNameStr.append(country.getGbName() + Global.SEPARATE_12);
				}
			}
			mode.setShieldDestination(contryNameStr.toString());
		}
		return mode;
	}


	/***
	 * 自动选择模板   
	 * 
	 * @param product
	 * @param codeManagerList
	 * @param site
	 * @param seller
	 * @return
	 */
	@Transactional(readOnly=false)
	public LogisticsMode autoSelectMode(Product product,List<ProductCodeManager> codeManagerList, PlatformSite site,Seller seller) {
		LogisticsMode  mode = null;
		String saleType = product.getDevelopmentType();
		String lenth="",wide="",height="";//长宽高
		double weight = 0d;//重量
		String siteId = site.getId();//站点id
		boolean flag = false;
		if(CollectionUtils.isNotEmpty(codeManagerList)){
			ProductCodeManager codeManager = codeManagerList.get(0);
			lenth = codeManager.getLength();
			weight = codeManager.getWeight();
			wide = codeManager.getWide();
			height = codeManager.getHigh();
		}
		Map<String, Object> paramMap = new  HashMap<String, Object>(); 
							paramMap.put("length",lenth);
							paramMap.put("wide",wide);
							paramMap.put("height",height);
							paramMap.put("weight",weight);
							paramMap.put("siteId",siteId);
							
		if(Global.SALE_TYPE_OVERSEAS_STOREHOUSE.equals(saleType)){//海外精品（海外仓）
			// 海外仓 通过 站点  长宽高 和重量 进行判断选择
			
		}else{//其他都是走 中国直发的判断逻辑
			// 如果是 美国站点 US 通过站点和 售价（5美金）判断，如果是其他站点则 通过 重量 长宽高进行判断
			//美国有两个 模板 一个大于等于 5 美金 一个小于 5美金
			if(SiteCurrencyEnum.US.getSiteName().equalsIgnoreCase(site.getSiteShortName())){//如果是美国站点
				for (ProductCodeManager productCodeManager : codeManagerList) {
					if(productCodeManager.getPublishPrice()>= new Double(5.00)){
						flag=true;
						break;
					}else{
						continue;
					}
				}
			}
		}
		if(flag){
			paramMap.put("money",Global.ONE);//标识是不是 大于 5美金的
		}
		List<LogisticsMode> modeList = this.dao.findByCondition(paramMap);
		if(CollectionUtils.isNotEmpty(modeList)){
			mode = modeList.get(0);
			mode = this.findLogisticsMode(mode.getId());
		}
		return mode;
	}

	/***
	 * 自动获取 物流模板
	 * 
	 * @param product   该产品
	 * @param site   站点id
	 * @param priceList   价格集合
	 * @param paramMap  长宽高重量的集合
	 * @return
	 */
	public LogisticsMode autoSelectLogisticsMode(Product product, PlatformSite site,List<Double> priceList, Map<String, Object> paramMap) {
		
		LogisticsMode  logisticsMode = null;  
		//获取基本的数据
		LogisticsMode mode = new LogisticsMode();
		String saleType = product.getDevelopmentType();
		mode.setSalesType(saleType);//销售类型   1 海外仓精品   2 虚拟海外仓精品  3中国直发仓铺货   4 虚拟海外仓铺货
		mode.setSiteId(site.getId());

		// 海外仓精品 
		boolean isOverSeas = Global.SALE_TYPE_OVERSEAS_STOREHOUSE.equals(saleType) ;
		// 站点是 英国 ，这些需要进行重量 和 尺寸的筛选
		boolean isUk =  SiteCurrencyEnum.UK.getSiteName().equalsIgnoreCase(site.getSiteShortName());
		// 美国 或则
		boolean isUs = SiteCurrencyEnum.US.getSiteName().equalsIgnoreCase(site.getSiteShortName());
		// 海外仓精品 和   德国的站点
		boolean isDe = SiteCurrencyEnum.DE.getSiteName().equalsIgnoreCase(site.getSiteShortName());
		//海外仓精品 要加上 仓库查询 
		if(isOverSeas && (isUk || isUs || isDe )){
			mode.setWarehouse(product.getDeliveryWareHouse());
		}
		List<LogisticsMode>  modeList = this.dao.findList(mode);
		if(CollectionUtils.isEmpty(modeList)){
			return logisticsMode;
		}
		if(isOverSeas &&(isUs || isUk || isDe) ){// 海外精品 (英国德国美国)
			 JexlInstance instance = JexlInstance.getInstance();
			for (LogisticsMode mode2 : modeList) {
				if(StringUtils.isNotBlank(mode2.getFormula())){
					 boolean calcFlag =  instance.calcBoolean(mode2.getFormula(), paramMap);
					 if(calcFlag){
						logisticsMode = mode2;
						break;
					}else{
						continue;
					}
				}
			}
			return  logisticsMode;
		}else{// 直接获取返回的结果 
			if(SiteCurrencyEnum.US.getSiteName().equalsIgnoreCase(saleType)){//美国站点
				Double maxPrice = Collections.max(priceList);
			   for (LogisticsMode mode2 : modeList) {
				   if(maxPrice <= mode2.getMaxPrice() && maxPrice >= mode2.getMinPrice()){
					   logisticsMode = mode2;
					    break;
				   }
			    }
			   return logisticsMode;
			}else{
			   return modeList.iterator().next();
			}
		}
	}
	
	
	public static void main(String[] args) {
		Map<String, Object>	paramMap = new HashMap<String, Object>();
		 //重量 长宽高 
   	 	paramMap.put(JexlInstance.LENGTH, "200");//长
   	 	paramMap.put(JexlInstance.WIDE, "50");//宽
   	 	paramMap.put(JexlInstance.HEIGHT, "30");//高
   	 	paramMap.put(JexlInstance.WEIGHT, "505");//重量  
   	 	paramMap.put(JexlInstance.WEAR_HOUSE, "中国直发仓");//重量  
   	 		String minW = "505";
   	 		String maxW = "3000";
		 Object calc = JexlInstance.getInstance().calc(minW+"<="+JexlInstance.WEIGHT+" && "+JexlInstance.WEIGHT+"<="+maxW +"&& "+JexlInstance.WEAR_HOUSE+"=='中国直发仓' && (1==1)", paramMap);
		 
		 Object calc2 = JexlInstance.getInstance().calc(minW+"<="+JexlInstance.WEIGHT+" && "+JexlInstance.WEIGHT+"<="+maxW +"&& "+JexlInstance.WEAR_HOUSE+"=='中国直发仓' && !(1==1)", paramMap);
		 System.out.println(calc);
		 System.out.println(calc2);
	
	}
	
	
}