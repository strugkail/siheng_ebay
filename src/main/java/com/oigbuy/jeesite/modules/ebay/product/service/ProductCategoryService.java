/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.product.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oigbuy.api.core.call.ebay.GetEbayCategorySpecifics;
import com.oigbuy.api.core.call.ebay.GetEbayItem;
import com.oigbuy.api.core.request.ebay.GetEbayCategorySpecificsRequest;
import com.oigbuy.api.core.request.ebay.GetEbayItemRequest;
import com.oigbuy.api.core.response.ebay.GetEbayCategorySpecificsResponse;
import com.oigbuy.api.core.response.ebay.GetEbayItemResponse;
import com.oigbuy.api.domain.ebay.CategorySpecificsDto;
import com.oigbuy.api.domain.ebay.ItemViewDto;
import com.oigbuy.api.domain.ebay.ParamDto;
import com.oigbuy.api.domain.ebay.SpecificsDto;
import com.oigbuy.api.domain.ebay.UsualDto;
import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.modules.ebay.listing.dto.ListingDetailDto;
import com.oigbuy.jeesite.modules.ebay.listing.service.ListingPropertyService;
import com.oigbuy.jeesite.modules.ebay.product.dao.ItemSpecificsDao;
import com.oigbuy.jeesite.modules.ebay.product.dao.ProductCategoryDao;
import com.oigbuy.jeesite.modules.ebay.product.dto.ProductDetail;
import com.oigbuy.jeesite.modules.ebay.product.dto.ProductDto;
import com.oigbuy.jeesite.modules.ebay.product.entity.ItemSpecifics;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductCategory;
import com.oigbuy.jeesite.modules.ebay.productcollection.service.ProductCollectionService;

/**
 * 产品分类Service
 * @author bill.xu
 * @version 2017-09-22
 */
@Service
@Transactional(readOnly = true)
public class ProductCategoryService extends CrudService<ProductCategoryDao, ProductCategory> {

	
	
	@Autowired
	private ItemSpecificsDao itemSpecificsDao;
	
	
	/***
	 * 竞品采集service    获取 产品 itemId 
	 */
	@Autowired
	private ProductCollectionService productCollectService;
	
	
	@Autowired
	private ItemSpecificsService itemSpecificsService;
	
	@Autowired
	private ListingPropertyService listingPropertyService;
	
	public ProductCategory get(String id) {
		return super.get(id);
	}
	
	public List<ProductCategory> findList(ProductCategory productCategory) {
		
		List<ProductCategory> categoryList = super.findList(productCategory);
		if(CollectionUtils.isNotEmpty(categoryList)){
			for (ProductCategory ca : categoryList) {
				ItemSpecifics entity = new ItemSpecifics(ca.getProductId(), Long.valueOf(ca.getId()));
				List<ItemSpecifics> items = itemSpecificsDao.findList(entity);
				ca.setItemSpecificsList(items);
			}
		}
		return categoryList;
	}
	
	public Page<ProductCategory> findPage(Page<ProductCategory> page, ProductCategory productCategory) {
		return super.findPage(page, productCategory);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductCategory productCategory) {
		super.save(productCategory);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductCategory productCategory) {
		super.delete(productCategory);
	}


	
	/***
	 * 保存 分类 和分类细节 
	 * 
	 * @param id
	 * @param productDetail
	 * @param listingId 
	 */
	@Transactional(readOnly = false)
	public void saveProductCategory(ProductDetail productDetail, Long listingId) {
		
		ProductDto productDto= productDetail.getProduct();
		Long productId = Long.valueOf(productDto.getId());
		String siteId = productDetail.getSiteId();
		//保存前先删除已有的 item specifics 
		this.itemSpecificsService.deleteByProductIdAndCategoryId(productDto.getId(),siteId,listingId);
		
		this.dao.deleteByProductIdAndSiteId(productDto.getId(),siteId,listingId);
		
		 List<ProductCategory>  categoryList = new ArrayList<ProductCategory>();
		 List<ItemSpecifics> itemList = new ArrayList<ItemSpecifics>();
		//保存 分类1 
		 ProductCategory category1  = saveCategory(listingId,productDetail.getItemSpecificsNames1(), productDetail.getItemSpecificsValues1(), productDetail.getItemSpecificsRemarks1(),productId,siteId,productDto.getCategory1(),Global.ONE,Global.ONE);
		 category1.setCategoryCode(productDto.getCategoryCode1());
		 
		 categoryList.add(category1);
		 itemList.addAll(category1.getItemSpecificsList());
		//保存 分类2
		 ProductCategory category2 = saveCategory(listingId,productDetail.getItemSpecificsNames2(), productDetail.getItemSpecificsValues2(), productDetail.getItemSpecificsRemarks2(),productId,siteId,productDto.getCategory2(),Global.ONE,Global.TWO);
		 category2.setCategoryCode(productDto.getCategoryCode2());
		 categoryList.add(category2);
		 itemList.addAll(category2.getItemSpecificsList());
		
		//保存 自定义分类细节
		ProductCategory category0 = saveCategory(listingId,productDetail.getItemSpecificsNames(), productDetail.getItemSpecificsValues(), productDetail.getItemSpecificsRemarks(),productId,siteId,"自定义",Global.ZERO,Global.ZERO);
		category0.setCategoryCode("");
		categoryList.add(category0);
		itemList.addAll(category0.getItemSpecificsList());
		
		if(CollectionUtils.isNotEmpty(categoryList)){
			this.dao.insertList(categoryList);
		}
		if(CollectionUtils.isNotEmpty(itemList)){
			this.itemSpecificsDao.insertList(itemList);
		}
	}



	private ProductCategory saveCategory(Long listingId, String[] itemSpecificsNames,String[] itemSpecificsValues, String[] itemSpecificsRemarks,Long productId, String siteId, String name, String type,String order) {
		
		Long categoryId = Global.getID();
		ProductCategory category= new ProductCategory(String.valueOf(categoryId),productId, Long.valueOf(siteId),StringEscapeUtils.unescapeHtml4(name),order);
		category.setListingId(listingId);
		category.setPlatformId(Long.valueOf(siteId));
		List<ItemSpecifics> items = new ArrayList<ItemSpecifics>();
		String nameT="",valueT="",remarkT="";
		if(itemSpecificsNames!=null && itemSpecificsNames.length>0){
			for (int i = 0; i < itemSpecificsNames.length; i++) {
				
				nameT = itemSpecificsNames[i];
				
				if(i<itemSpecificsValues.length){
                    valueT=itemSpecificsValues[i];
				}
			/*	if(i<itemSpecificsRemarks.length){
					remarkT=itemSpecificsRemarks[i];
				}*/
				ItemSpecifics item = new ItemSpecifics(String.valueOf(Global.getID()),productId, categoryId, nameT, valueT,remarkT, type);
				items.add(item);
			}
		}
		category.setItemSpecificsList(items);
		return  category;
	}

	
	/**
	 * 通过 站点简称 和 产品id 获取 实时相关的分类
	 *  
	 * @param siteName   站点简称  类似 UK
	 * @param productId   产品id
	 * @param flag 标志是 查询 产品的分类(false)还是 listing 的分类（true） 
	 * @param listingId 
	 * @return
	 * @throws Exception 
	 */
	public List<ProductCategory> findBySiteAndProductId(String siteName,Long productId, boolean flag,Long listingId) throws Exception {
		
		List<ProductCategory> categoryList = new ArrayList<ProductCategory>(); 
    	String  itemId = this.productCollectService.findItemIdByProductId(productId);
    	ProductCategory category1 =null;
    try {
		 GetEbayItem getItem = new GetEbayItem(new GetEbayItemRequest(itemId));
		 GetEbayItemResponse response = getItem.getResponse();
		 ItemViewDto item = response.getItem();
		 boolean isMutilAttribute = item.getLsd();// 从接口 item 中获取
		 if(item!=null){
			 category1 = getCategory(item.getCategoryName(),item.getCategoryId(),siteName,productId,flag,isMutilAttribute);
			 //分类 一
			 categoryList.add(category1);
			 //分类 二 
			 categoryList.add(getCategory(item.getSubCategoryName(),item.getSubCategoryId(),siteName,productId,flag,isMutilAttribute));
		 }
    	} catch (Exception e) {
    		logger.error("通过接口获取产品分类异常，itemId 为：{},异常信息为：{}",itemId,e.getLocalizedMessage());
			throw new Exception("通过接口获取产品分类异常，itemId 为："+itemId+"\t,"+e.getLocalizedMessage());
		}
		if(flag){//查询自定义的 item specifics   //是 listing 
			ProductCategory selfCategory = this.findSelfCategoryByListing(listingId);			
			categoryList.add(selfCategory);
			//查询出 这个产品的所有的 item specifics，并设置 分类一下的某些属性值 选中状态 
			//查到这个listing 保存的  item specifics 
			ProductCategory entity =new ProductCategory(Global.ONE, listingId);
			List<ProductCategory> listingCategory = this.dao.findList(entity);
			if(CollectionUtils.isNotEmpty(listingCategory)){
				ProductCategory  listingCa = listingCategory.get(0);
				if(listingCa!=null){
					ItemSpecifics itemSpecificsT = new ItemSpecifics();
					itemSpecificsT.setProductId(productId);
					itemSpecificsT.setCategoryId(Long.valueOf(listingCa.getId()));
					List<ItemSpecifics> itemSepcificsList = this.itemSpecificsDao.findList(itemSpecificsT); 
					if(category1!=null && CollectionUtils.isNotEmpty(itemSepcificsList)){
						Map<String, String> itemMap = new HashMap<String, String>();
						itemSepcificsList.forEach(it->{
							itemMap.putIfAbsent(it.getName(), it.getValue());
						});
						List<ItemSpecifics> itemSpecifics = category1.getItemSpecificsList();
						for (ItemSpecifics item : itemSpecifics) {
							item.setSelectValue(itemMap.get(item.getName()));
						}
					}
				}
			}
		}
		return categoryList;
	}
	
	

	
	/***
	 * 得到该 listing 的自定义 分类 和  item specifics
	 * 
	 * @param listingId
	 * @return
	 */
	private ProductCategory findSelfCategoryByListing(Long listingId) {
		ProductCategory category = new ProductCategory();
		
		List<ProductCategory>  list = this.findList(new ProductCategory(Global.ZERO, listingId));
		if(CollectionUtils.isNotEmpty(list)){
			Iterator<ProductCategory> iterator = list.iterator();
			category = iterator.next();
		}
		return category;
	}

	/****
	 *  获取 分类 ，以及设置 分类下的 item specifics 和 conditions
	 *  
	 * @param categoryName  分类名称
	 * @param categoryId   分类id 
	 * @param siteName  站点简称
	 * @param productId  产品id
	 * @param flag   产品 false ， listing true
	 * @param isMutilAttribute   是不是多属性
	 * @return
	 */
	private ProductCategory getCategory(String categoryName, String categoryId,String siteName, Long productId, boolean flag, Boolean isMutilAttribute) {
		
		ProductCategory category = new  ProductCategory(categoryName,categoryId);
		
		List<ItemSpecifics> itemSpecificsList = new  ArrayList<ItemSpecifics>();
		
		GetEbayCategorySpecificsRequest request =new GetEbayCategorySpecificsRequest();
		ParamDto dto =new ParamDto();
				dto.setCategoryId(categoryId);
				dto.setSite(siteName); 
				request.setDto(dto);
		GetEbayCategorySpecifics itemspecifics = new GetEbayCategorySpecifics(request);
		
		GetEbayCategorySpecificsResponse response = itemspecifics.getResponse();
		CategorySpecificsDto categorySpecificsDto = response.getCategorySpecificsDto();
		if(categorySpecificsDto!=null){
		List<UsualDto> conditions = new ArrayList<UsualDto>();
		conditions.addAll(categorySpecificsDto.getConditions());
		category.setConditions(conditions);
		//设置每个分类下的 item specifics
		List<SpecificsDto> specfics = categorySpecificsDto.getSpecfics();
	    	ItemSpecifics item = null;
	    	for (SpecificsDto specificsDto : specfics) {
	    		item = new ItemSpecifics();
	    		item.setName(specificsDto.getName());
	    		item.setValues(specificsDto.getValues());
	    		item.setRemark("");
	    		item.setSelectMode(specificsDto.getSelectionMode());
	    		itemSpecificsList.add(item);
		}
		}
		category.setItemSpecificsList(itemSpecificsList );
		category.setMutilAttribute(isMutilAttribute?Global.ONE:Global.ZERO);
		return category;
	}

	
	/***
	 * 保存 listing 的分类和 item specifics
	 * 
	 * @param detailDto
	 */
	@Transactional(readOnly=false)
	public void saveListingCategoryAndItemSpecifics(ListingDetailDto detailDto) {
			//要先删除 原来的分类 和 item specifics 
		//保存前先删除已有的 item specifics 
		this.itemSpecificsService.deleteByProductIdAndCategoryId(String.valueOf(detailDto.getProductId()),String.valueOf(detailDto.getPlatformSite().getId()),Long.valueOf(detailDto.getId()));
				
		this.dao.deleteByProductIdAndSiteId(String.valueOf(detailDto.getProductId()),String.valueOf(detailDto.getPlatformSite().getId()),Long.valueOf(detailDto.getId()));
			
		List<ItemSpecifics> itemSpecificsList = new ArrayList<ItemSpecifics>();
			// 关于自定义的有两部分 ，一个是原来 已经有过自定义的 在 分类 三中 ，还有就是页面直接传递过来的   itemSpecificsNames 集合 
		List<ProductCategory> productCategoryList = detailDto.getProductCategoryList();
		
			if(CollectionUtils.isNotEmpty(productCategoryList)){
				
				String listingId = detailDto.getId();//listing id 
				Long siteId = detailDto.getSiteId();// site id
				Long platformId = detailDto.getPlatformId();// platformId
				Long productId = detailDto.getProductId();// platformId
				for (ProductCategory productCategory : productCategoryList) {
					Long categoryId = Global.getID();
					productCategory.setId(String.valueOf(categoryId));
					productCategory.setSiteId(siteId);
					productCategory.setListingId(Long.valueOf(listingId));
					productCategory.setPlatformId(platformId);
					productCategory.setProductId(productId);
					List<ItemSpecifics> items = productCategory.getItemSpecificsList();
					if(CollectionUtils.isNotEmpty(items)){
						for (ItemSpecifics itemSpecifics : items) {
							itemSpecifics.setId(String.valueOf(Global.getID()));
							itemSpecifics.setCategoryId(categoryId);
							itemSpecifics.setName(itemSpecifics.getName());
							itemSpecifics.setProductId(productId);
							itemSpecifics.setRemark(itemSpecifics.getRemarks());
							itemSpecifics.setType(itemSpecifics.getType());//1 表示是分类下的  0  是自定义的
							itemSpecifics.setValue(itemSpecifics.getValue());
						}
						itemSpecificsList.addAll(items);
					}
					if(Global.ZERO.equals(productCategory.getOrderNum())){
						List<String> names = detailDto.getItemSpecificsNames();
						List<String> values = detailDto.getItemSpecificsValues();
						if(CollectionUtils.isNotEmpty(names)){
							int  size = names.size();
							for (int i=0;i<size;i++) {
								ItemSpecifics item = new ItemSpecifics();
								item.setCategoryId(categoryId);
								item.setId(String.valueOf(Global.getID()));
								item.setName(names.get(i));
								if(i<values.size()){
									item.setValue(values.get(i));
								}
								item.setProductId(productId);
								item.setType(Global.ZERO);
								itemSpecificsList.add(item);
							}
						}
					}
				}
			}
			
			//保存所有的 分类和 item specifics 
			if(CollectionUtils.isNotEmpty(productCategoryList)){
				this.dao.insertList(productCategoryList);
			}
			if(CollectionUtils.isNotEmpty(itemSpecificsList)){
				this.itemSpecificsDao.insertList(itemSpecificsList);
			}
	}

	/***
	 * 通过 listingId 查询，刊登的时候要进行数据查询 封装  
	 * 
	 * @param listingId
	 * @param productId 
	 * @return
	 */
	public List<ProductCategory> findByListingId(Long listingId, Long productId) {
		ProductCategory query = new ProductCategory()  ;
						query.setProductId(productId);
						query.setListingId(listingId);
		//List<ItemSpecifics> items = new ArrayList<ItemSpecifics>();
		List<ProductCategory> productCategorys = this.findList(query);
		
//		if(CollectionUtils.isNotEmpty(productCategorys)){//遍历 分类 获取分类下的 item specifics 
//		       Iterator<ProductCategory> iterator = productCategorys.iterator();	
//		       while (iterator.hasNext()) {
//		    	   ProductCategory category = iterator.next();
//		    	   ItemSpecifics itemSpecifics = new ItemSpecifics(productId, Long.valueOf(category.getId()));
//		    	   List<ItemSpecifics> itemSpecificsList = this.itemSpecificsDao.findList(itemSpecifics);
//		    	   category.setItemSpecificsList(itemSpecificsList);
//		    	   //items.addAll(itemSpecificsList);
//			}
//		}
		return productCategorys;
	}

	
	
	/***
	 * 通过 分类id 获取 对应的 item specifics
	 * 
	 * @param categoryId
	 * @param site
	 * @param productId
	 * @param flag 标志 如果是 listing 过来的 为 true ，需要和 已经保存的 自定义分类进行比对
	 * @param conditions
	 * @return
	 */
//	@Deprecated
//	private List<ItemSpecifics>  getItemSpecifics(String categoryId,String site,Long productId,boolean flag, List<UsualDto> conditions) {
//		
//		List<ItemSpecifics> itemSpecificsList =new ArrayList<ItemSpecifics>();
//		
//		GetCategorySpecificsRequest request =new GetCategorySpecificsRequest();
//		ParamDto dto =new ParamDto();
//				dto.setCategoryId(categoryId);
//				dto.setSite(site);
//		request.setDto(dto);
//		GetCategorySpecifics itemspecifics = new GetCategorySpecifics(request);
//		
//		GetCategorySpecificsResponse response = itemspecifics.getResponse();
//		
//		CategorySpecificsDto categorySpecificsDto = response.getCategorySpecificsDto();
//	
//		conditions.addAll(categorySpecificsDto.getConditions());//TODO  不确定分类一 和分类二 下的是不是会重复
//		
//		List<SpecificsDto> specfics = categorySpecificsDto.getSpecfics();
//	    	ItemSpecifics item = null;
//	    	for (SpecificsDto specificsDto : specfics) {
//	    		item = new ItemSpecifics();
//	    		item.setName(specificsDto.getName());
//	    		item.setValues(specificsDto.getValues());
//	    		item.setRemark("");
//	    		itemSpecificsList.add(item);
//		}
//		 return itemSpecificsList;
//	}
//	
}