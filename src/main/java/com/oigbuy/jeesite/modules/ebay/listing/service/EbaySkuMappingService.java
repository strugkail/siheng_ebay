/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.listing.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.common.utils.StringUtils;
import com.oigbuy.jeesite.modules.ebay.designdrawing.service.DesignDrawingService;
import com.oigbuy.jeesite.modules.ebay.listing.dao.EbaySkuMappingDao;
import com.oigbuy.jeesite.modules.ebay.listing.dto.ListingCodeManagerDto;
import com.oigbuy.jeesite.modules.ebay.listing.entity.EbayListing;
import com.oigbuy.jeesite.modules.ebay.listing.entity.EbayListingImg;
import com.oigbuy.jeesite.modules.ebay.listing.entity.EbayListingPrice;
import com.oigbuy.jeesite.modules.ebay.listing.entity.EbaySkuMapping;
import com.oigbuy.jeesite.modules.ebay.listing.entity.ListingProperty;
import com.oigbuy.jeesite.modules.ebay.product.dto.ProductDetail;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductCodeManager;
import com.oigbuy.jeesite.modules.ebay.product.entity.TProductImgPlatform;
import com.oigbuy.jeesite.modules.ebay.product.service.ProductCodeManagerService;

/**
 * ebay sku 映射Service
 * @author bill.xu
 * @version 2017-09-22
 */
@Service
@Transactional(readOnly = true)
public class EbaySkuMappingService extends CrudService<EbaySkuMappingDao, EbaySkuMapping> {

	@Autowired
	private EbayListingImgService ebayListingImgService;
	
	
	@Autowired
	private EbayListingPriceService ebayListingPriceService;
	
	@Autowired
	private ProductCodeManagerService codeManagerService;
	
	@Autowired
	private DesignDrawingService designDrawingService;
	
	/***
	 * listing 属性
	 */
	@Autowired
	private ListingPropertyService listingPropertyService;
	
	
	@Autowired
	private EbayListingService ebayListingService;
	
	
	public EbaySkuMapping get(String id) {
		return super.get(id);
	}
	
	public List<EbaySkuMapping> findList(EbaySkuMapping ebaySkuMapping) {
		return super.findList(ebaySkuMapping);
	}
	
	public Page<EbaySkuMapping> findPage(Page<EbaySkuMapping> page, EbaySkuMapping ebaySkuMapping) {
		return super.findPage(page, ebaySkuMapping);
	}
	
	@Transactional(readOnly = false)
	public void save(EbaySkuMapping ebaySkuMapping) {
		super.save(ebaySkuMapping);
	}
	
	@Transactional(readOnly = false)
	public void delete(EbaySkuMapping ebaySkuMapping) {
		super.delete(ebaySkuMapping);
	}

	
	/***
	 * 产品生成 listing ，保存相应的   sku mapping （库存 和 sku 信息）
	 * 
	 * @param productDetail
	 * @param listingId
	 * @param productId
	 */
	@Transactional(readOnly=false)
	public void saveListingMapping(ProductDetail productDetail, Long listingId, String productId) {
		
		List<ProductCodeManager> codeManagerList = productDetail.getCodeManagerList();
		if(CollectionUtils.isEmpty(codeManagerList)){
			return ;
		}
		List<EbaySkuMapping> mappingList = new ArrayList<EbaySkuMapping>();
		for (ProductCodeManager productCodeManager : codeManagerList) {
			if(productCodeManager==null || StringUtils.isBlank(productCodeManager.getId())){
				continue;
			}
			EbaySkuMapping mapping = new EbaySkuMapping(Global.getID().toString(),productCodeManager.getListingSkuCode(), Long.valueOf(productId), listingId, Long.valueOf(productCodeManager.getId()),productCodeManager.getProductCode());
			mapping.setQuantity(productCodeManager.getQuantity());
			mappingList.add(mapping);
		}
		if(CollectionUtils.isNotEmpty(mappingList)){
			this.dao.insertList(mappingList);
		}
		
	}

	
	/***
	 * 获得 listing 下的 codemanager 的详细信息（价格 图片等）
	 * 
	 * @param productId
	 * @param listingId
	 * @return
	 */
	public List<ListingCodeManagerDto> findListingCodeManager(Long productId,Long listingId) {
		
		List<ListingCodeManagerDto> codeManagerDtoList = new ArrayList<ListingCodeManagerDto>();
		//sku mapping 集合
		List<EbaySkuMapping>  skuMappingList = this.findList(new EbaySkuMapping(productId,listingId) );
		if(CollectionUtils.isEmpty(skuMappingList)){
			return codeManagerDtoList;
		}
		//该listing 所有的子g 图
		EbayListingImg imageQuery =new EbayListingImg(listingId,Global.IMG_TYPE_CHILD_MAIN);
		List<EbayListingImg> allImage = this.ebayListingImgService.findList(imageQuery);
		
		//该 listing 所有的 价格集合
		EbayListingPrice ebayListingPrice= new EbayListingPrice ();
		ebayListingPrice.setListingId(listingId);
		List<EbayListingPrice> allPrice =this.ebayListingPriceService.findList(ebayListingPrice);
		// listing 属性 集合
		ListingProperty listingProperty =new ListingProperty();
		listingProperty.setListingId(listingId);
		List<ListingProperty> allProperty = this.listingPropertyService.findList(listingProperty );
		Map<Long, List<ListingProperty>>  propertyMap = new HashMap<Long, List<ListingProperty>>();
 		if(CollectionUtils.isNotEmpty(allProperty)){
			for (ListingProperty pp : allProperty) {
				List<ListingProperty> childProperty = propertyMap.get(pp.getCodeManagerId());
				if(childProperty==null){
					childProperty= new ArrayList<ListingProperty>();
				}
				childProperty.add(pp);
				propertyMap.put(pp.getCodeManagerId(), childProperty);
			}
		}
		Iterator<EbaySkuMapping> iterator = skuMappingList.iterator();
		ListingCodeManagerDto dto = null;
		while (iterator.hasNext()) {
			dto = new ListingCodeManagerDto();
			EbaySkuMapping mapping = (EbaySkuMapping) iterator.next();
			dto.setId(mapping.getId());
			
			Long codeManagerId = mapping.getCodeManagerId();
			String sku = mapping.getSku();
			//EbayListingImg image =  ebayListingImgService.findByListingIdAndCodeManagerId(listingId, codeManagerId, Global.IMG_TYPE_CHILD_MAIN);
			dto.setCodeManagerId(String.valueOf(mapping.getCodeManagerId()));
			//图片
			if(CollectionUtils.isNotEmpty(allImage)){
				for (EbayListingImg img : allImage) {
					if(img!=null && codeManagerId.equals(img.getCodeManagerId())){
						dto.setCodeManagerImage(img);
					}
				}
			}
			
			//价格
			//EbayListingPrice price = this.ebayListingPriceService.findByListingIdAndSku(listingId,sku);
			//dto.setPublishPrice(price==null?null:new BigDecimal(price.getPrice()));
			
			if(CollectionUtils.isNotEmpty(allPrice)){
				for (EbayListingPrice price : allPrice) {
					if(price!=null && StringUtils.equals(sku, price.getSku())){
						dto.setPublishPrice(new BigDecimal(price.getPrice()));
					}
				}
			}
			
			
			//设置 listing 属性
			//dto.setProductPropertyList(this.listingPropertyService.findList(new ListingProperty(listingId, codeManagerId)));
			dto.setProductPropertyList(propertyMap.get(codeManagerId));
			//设置 UPC/EAN 
			dto.setProductCode(mapping.getProductCode());
			
			//拿到 product code manager 
//			ProductCodeManager codeManager = this.codeManagerService.get(String.valueOf(codeManagerId));
//			if(codeManager!=null){
//				dto.setRecommendNumber(codeManager.getRecommendNumber()==null?0:codeManager.getRecommendNumber());
//			}
			dto.setRecommendNumber(mapping.getQuantity()==null?0:mapping.getQuantity());
			
			dto.setSku(sku);
			codeManagerDtoList.add(dto);
		}
		return codeManagerDtoList;
	}

	
	/***
	 * 修改 listing 的code manager 对应的值 （价格，自定义属性等 信息 ）
	 * @param listingId 
	 * @param listingCodeManagerList
	 * @param propertyNames  自定义属性的名称集合
	 * @param siteId   站点 
	 */
	
	@Transactional(readOnly=false) 
	public void updateListingCodeManager(String listingId, List<ListingCodeManagerDto> listingCodeManagerList, List<String> propertyNames, Long siteId) {
		
		
		if(CollectionUtils.isNotEmpty(listingCodeManagerList)){
			
			
			List<EbayListingPrice> priceList = new ArrayList<EbayListingPrice>();
			List<ListingProperty> propertyList = new ArrayList<ListingProperty>();
			
			List<EbaySkuMapping> skuMappingList = new ArrayList<EbaySkuMapping>();
			
			for (ListingCodeManagerDto dto : listingCodeManagerList) {
				
				if(dto==null){
					continue;
				}
				//修改对应的价格 数量
				EbayListingPrice price = this.ebayListingPriceService.findByListingIdAndSku(Long.valueOf(listingId), dto.getSku());
				if(price!=null){
					price.setPrice(String.valueOf(dto.getPublishPrice()));
					priceList.add(price);
				}
				//更新多属性 
				List<ListingProperty> productPropertyList = dto.getProductPropertyList();
				
				if(CollectionUtils.isNotEmpty(productPropertyList) && CollectionUtils.isNotEmpty(propertyNames)){
				    int propertyNum = productPropertyList.size();
				    for (int i = 0; i < propertyNum; i++) {
				    	if(productPropertyList.get(i)==null){
				    		continue;
				    	}
				    	ListingProperty property = productPropertyList.get(i);
				    	property.setName(propertyNames.get(i));
				    	property.setSiteId(siteId);
				    	property.setId(StringUtils.isBlank(property.getId())?Global.getID().toString():property.getId());
					    
				    }
				    propertyList.addAll(productPropertyList);
				}
				
				
				EbaySkuMapping mapping = this.get(dto.getId());
				if(mapping!=null){
					mapping.setProductCode(dto.getProductCode());
					mapping.setQuantity(dto.getRecommendNumber());
					skuMappingList.add(mapping);
				}
			}
			
			this.listingPropertyService.deleteByListingId(listingId);
			if(CollectionUtils.isNotEmpty(propertyList)){
				//这里不是批量更新了的，应该是 先删除 在新增
//				this.listingPropertyService.updateBatch(propertyList);
				this.listingPropertyService.insertList(propertyList);
			}
			if(CollectionUtils.isNotEmpty(priceList)){
				this.ebayListingPriceService.updateBatch(priceList);
			}
			
			if(CollectionUtils.isNotEmpty(skuMappingList)){
				this.dao.updateBatch(skuMappingList);
			}
			
		}
	}

	
	/***
	 * 获得该  产品 下的所有的 子代码，筛选出  未被 生成 listing 的子代码集合   
	 * 
	 * @param listingId
	 * @param productId
	 * @return
	 */
	public List<ListingCodeManagerDto> selectListingCodeManager(String listingId, String productId) {
		
		List<ListingCodeManagerDto> codeManagerDtoList = new ArrayList<ListingCodeManagerDto>();
		 //获取所有图片
		List<TProductImgPlatform> images = designDrawingService.findAllByProductId(productId);
		// 该产品的所有的 子 代码的集合
		List<ProductCodeManager>  productCodeMangaerList = this.codeManagerService.findListByProductId(productId, null,images);
		// 已经添加到 该 listing 的子代码集合
		List<String>  codeManagerIds =  this.getCodeManagerIdsByListingId(Long.valueOf(productId),Long.valueOf(listingId)); 
		
		if(CollectionUtils.isNotEmpty(productCodeMangaerList)){
			ListingCodeManagerDto dto = null;
			Iterator<ProductCodeManager> iterator = productCodeMangaerList.iterator();
			while (iterator.hasNext()) {
				ProductCodeManager productCodeManager = iterator.next();
				if(productCodeManager==null || codeManagerIds.contains(productCodeManager.getId())){
					continue;
				}
				dto = new ListingCodeManagerDto();
				dto.setCodeManagerId(productCodeManager.getId());
				EbayListingImg image = new EbayListingImg();
				image.setImageUrl(productCodeManager.getImgUrl());
				dto.setCodeManagerImage(image);
				dto.setPublishPrice(new BigDecimal(productCodeManager.getPublishPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
				dto.setRecommendNumber(productCodeManager.getRecommendNumber());
				dto.setSku(productCodeManager.getSysSku());
				codeManagerDtoList.add(dto);
			}
		}
		return codeManagerDtoList;
	}

	
	/***
	 * 通过 产品id 和 listingId 获取 相关的 codeManager  的   id 集合 
	 * 
	 * @param productId
	 * @param listingId
	 * @return
	 */
	private List<String> getCodeManagerIdsByListingId(Long productId,	Long listingId) {
		List<String> ids = new ArrayList<String>();
		List<EbaySkuMapping> mappingList = this.dao.findList(new EbaySkuMapping(Long.valueOf(productId), Long.valueOf(listingId)));
		if(CollectionUtils.isNotEmpty(mappingList)){
			mappingList.forEach(mapping->{
				ids.add(String.valueOf(mapping.getCodeManagerId()));
			});
		}
		return ids;
	}

	/***
	 * 保存 listing 的新增 子代码 
	 * 
	 * @param listingId
	 * @param productId
	 * @param ids
	 * @return
	 */
	@Transactional(readOnly=false)
	public List<ListingCodeManagerDto> addCodeManager(String listingId,String productId, String ids,String preCode) {
		
			List<ListingCodeManagerDto> dtos = new ArrayList<ListingCodeManagerDto>();
		
			if(StringUtils.isBlank(ids)){
			    return dtos ;
			}
			EbayListing listing = this.ebayListingService.get(listingId);
			if(listing==null){
				return dtos;
			}
			List<String> listingPropertyNames = this.listingPropertyService.findListingPropertyNames(listingId);
			
			List<EbaySkuMapping> mappingList = new ArrayList<EbaySkuMapping>();
			List<EbayListingImg> imageList = new ArrayList<EbayListingImg>();
			List<EbayListingPrice> priceList = new ArrayList<EbayListingPrice>();
			List<ListingProperty> propertyList=new ArrayList<ListingProperty>();
			
			
			EbaySkuMapping mapping = null;
			EbayListingImg image = null;
			EbayListingPrice price = null;
			ListingCodeManagerDto dto = null;
			
			Long listingIdl = Long.valueOf(listingId);
			Long productIdl = Long.valueOf(productId);
			
			String [] codeManagerIds = ids.split(Global.SEPARATE_7);
			for (String codeManagerId : codeManagerIds) {
				if(StringUtils.isBlank(codeManagerId)){
					continue;
				}
				ProductCodeManager productCodeManager = this.codeManagerService.get(codeManagerId);
				if(productCodeManager == null){
					continue;
				}
				Long codeManagerIdl=Long.valueOf(codeManagerId);
				
				String sku = preCode+productCodeManager.getSysSku()+Global.SKU_CODE_MM;
				// mapping 信息
				mapping = new EbaySkuMapping(); 
				mapping.setCodeManagerId(codeManagerIdl);
				mapping.setId(Global.getID().toString());
				mapping.setListingId(listingIdl);
				mapping.setProductId(productIdl);
				mapping.setQuantity(productCodeManager.getQuantity());
				mapping.setSku(sku);
				mappingList.add(mapping);
				//图片
				image = new EbayListingImg();
				image.setCodeManagerId(codeManagerIdl);
				image.setId(Global.getID().toString());
				if(StringUtils.isNotBlank(productCodeManager.getImageId())){
					image.setImageId(Long.valueOf(productCodeManager.getImageId()));
				}
				image.setImageType(Global.IMG_TYPE_CHILD_MAIN);
				image.setListingId(listingIdl);
				image.setImageUrl(productCodeManager.getImgUrl());
				imageList.add(image);
				//价格
				price =new EbayListingPrice();
				price.setId(Global.getID().toString());
				price.setListingId(listingIdl);
				price.setPrice(productCodeManager.getPublishPrice().toString());
				price.setSiteId(listing.getSiteId());
				price.setSku(sku);
				priceList.add(price);
				
				//属性 
				if(CollectionUtils.isNotEmpty(listingPropertyNames)){
					listingPropertyNames.forEach(name->{
						ListingProperty property = new ListingProperty(listingIdl, codeManagerIdl);
						property.setId(Global.getID().toString());
						property.setName(name);
						property.setSiteId(listing.getSiteId());
						property.setValue("");//这时候不知道具体的值
						propertyList.add(property);
					});
				}
				dto = new ListingCodeManagerDto();
				dto.setCodeManagerId(codeManagerId);
				dto.setCodeManagerImage(image);
				dto.setPublishPrice(new BigDecimal(productCodeManager.getPublishPrice()).setScale(2, RoundingMode.HALF_UP));
				dto.setRecommendNumber(productCodeManager.getQuantity()==null?0:productCodeManager.getQuantity());
				dto.setSku(sku);
				dtos.add(dto);
				
				
			}
			
			if(CollectionUtils.isNotEmpty(priceList)){
				this.ebayListingPriceService.insertList(priceList);
			}
			if(CollectionUtils.isNotEmpty(imageList)){
				this.ebayListingImgService.insertList(imageList);
			}
			if(CollectionUtils.isNotEmpty(mappingList)){
				this.dao.insertList(mappingList);
			}
			if(CollectionUtils.isNotEmpty(propertyList)){
			   this.listingPropertyService.insertList(propertyList);	
			}
			
		return dtos;
	}
	
}