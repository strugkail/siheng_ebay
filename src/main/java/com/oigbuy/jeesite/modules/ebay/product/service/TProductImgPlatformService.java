/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.product.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.common.utils.StringUtils;
import com.oigbuy.jeesite.modules.ebay.listing.entity.EbayListingImg;
import com.oigbuy.jeesite.modules.ebay.listing.service.EbayListingImgService;
import com.oigbuy.jeesite.modules.ebay.product.dao.TProductImgPlatformDao;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductImg;
import com.oigbuy.jeesite.modules.ebay.product.entity.TProductImgPlatform;

/**
 * 产品图片Service
 * @author AA
 * @version 2017-10-26
 */
@Service
@Transactional(readOnly = true)
public class TProductImgPlatformService extends CrudService<TProductImgPlatformDao, TProductImgPlatform> {
	@Autowired
	private TProductImgPlatformDao tProductImgPlatformDao;
	
	@Autowired
	private ProductImgService productImgService;
	
	@Autowired
	private EbayListingImgService ebayListingImgService;

	public TProductImgPlatform get(String id) {
		return super.get(id);
	}
	
	public List<TProductImgPlatform> findList(TProductImgPlatform tProductImgPlatform) {
		return super.findList(tProductImgPlatform);
	}
	
	public Page<TProductImgPlatform> findPage(Page<TProductImgPlatform> page, TProductImgPlatform tProductImgPlatform) {
		return super.findPage(page, tProductImgPlatform);
	}
	
	@Transactional(readOnly = false)
	public void save(TProductImgPlatform tProductImgPlatform) {
		super.save(tProductImgPlatform);
	}
	
	@Transactional(readOnly = false)
	public void delete(TProductImgPlatform tProductImgPlatform) {
		super.delete(tProductImgPlatform);
	}

	public int findcount(@Param("productId") String productId, @Param("imgType") String imgType){
		return tProductImgPlatformDao.findcount(productId,imgType);
	}
	public List<TProductImgPlatform> findByTProductImgPlatform(@Param("productId") String productId, @Param("imgType") String imgType){
		return tProductImgPlatformDao.findByTProductImgPlatform(productId,imgType);
	}

	
	/***
	 * 产品图片保存（子 g 图 单个，主 g 图 ,细节图和特效图 多个）
	 * 
	 * @param type  图片类型
	 * @param productId  产品id
	 * @param ids 图片id集合
	 * @param codeManagerId 
	 * @return
	 */
	@Transactional(readOnly=false)
	public List<TProductImgPlatform> saveProductImage(String type,String productId, String ids, String codeManagerId) {
		//直接通过该 id的集合获取 对应的 图片集合
		List<TProductImgPlatform> images = new ArrayList<TProductImgPlatform>();
		if(StringUtils.isNotBlank(ids)){
			String[] imageIds = ids.split(Global.SEPARATE_7);
			for (String id : imageIds) {
				images.add(this.dao.get(id));
			}
		}
		return images;
		
//		
//		List<TProductImgPlatform> images = new ArrayList<TProductImgPlatform>();
//		
//		TProductImgPlatform image = null;
//		
//		if(Global.IMG_TYPE_CHILD_MAIN.equals(type)){// 子 g 图 更新或则新增
//			TProductImgPlatform tProductImgPlatform = this.tProductImgPlatformDao.get(ids);
//			ProductImg img = this.productImgService.get(tProductImgPlatform.getImgId());
//			
//			image = this.tProductImgPlatformDao.findByCodeManagerId(codeManagerId);
//			if(ObjectUtils.notEqual(image, null)){//更新
//					image.setIsNewRecord(false);
//			}else{//新增
//					image = new TProductImgPlatform();
//					image.setIsNewRecord(true);
//					image.setId(Global.getID().toString());
//				}
//				image.setCodeManagerId(codeManagerId);
//				image.setImgId(img.getId());
//				image.setImgType(type);
//				image.setImgUrl(img.getImgUrl());
//				image.setPlatformFlag(Global.PLATFORM_FLAG_EBAY);
//				image.setProductId(productId);
//				image.setTemplateIden(Global.TEMPLATE_IDEN_NON);
//				this.save(image);//保存或则新增
//				images.add(image);
//				return images;
//		}else if(Global.IMG_TYPE_MAIN.equals(type)|| Global.IMG_TYPE_DETAIL.equals(type) || Global.IMG_TYPE_SPECIAL_EFFECTS.equals(type)){//细节图  或则 特效图  新增
//			String[] imageIds = ids.split(Global.SEPARATE_7);
//			for (String id : imageIds) {
//				image = new TProductImgPlatform(); 
//				TProductImgPlatform tProductImgPlatform = this.tProductImgPlatformDao.get(id);
//				ProductImg img = this.productImgService.get(tProductImgPlatform.getImgId());
//				image.setId(String.valueOf(Global.getID()));
//				image.setImgId(img.getId());
//				image.setImgType(type);
//				image.setImgUrl(img.getImgUrl());
//				image.setIsNewRecord(true);
//				image.setPlatformFlag(Global.PLATFORM_FLAG_EBAY);
//				image.setProductId(productId);
//				image.setTemplateIden(Global.TEMPLATE_IDEN_NON);
//				images.add(image);
//			}
//		}
//		if(CollectionUtils.isNotEmpty(images)){
//			this.dao.insertList(images);
//		}
//		return images;
	}
	
	
	
	/***
	 *  先保存一个 产品图片 （t_product_img）
	 *  
	 *  然后再保存一个关联表 （t_product_img_platform）  并返回 该对象
	 * 
	 * @param productId  产品id
	 * @param URl 图片url
	 * @param type 类型
	 * @param codeManageID 
	 * @param 平台的标志
	 * @return
	 */
	
	@Transactional(readOnly=false)
	public TProductImgPlatform saveUploadImage(String productId,String url, String type, String codeManagerId,String platformFlag) {
		
		ProductImg  productImage = new ProductImg(productId, url, String.valueOf(Global.getID()), Global.PLATFORM_FLAG_EBAY);
		productImage.setIsNewRecord(true);
		productImgService.save(productImage);
		
		TProductImgPlatform platformImage = new TProductImgPlatform();
		if(StringUtils.isNotBlank(codeManagerId)){
			platformImage.setCodeManagerId(codeManagerId);
		}
		platformImage.setId(String.valueOf(Global.getID()));
		platformImage.setImgId(productImage.getId());
		platformImage.setImgType(type);
		platformImage.setImgUrl(url);//不入库 页面展示
		platformImage.setPlatformFlag(platformFlag);
		platformImage.setProductId(productId);
		platformImage.setTemplateIden(Global.TEMPLATE_IDEN_NON);
		this.dao.insert(platformImage);
		
		return platformImage;
	}

	
	/***
	 * 通过id删除 ，同时还要判断 该 图片有没有被引用，如果被引用则不能被删除
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=false)
	public boolean deleteById(String id) {
		TProductImgPlatform tProductImgPlatform = this.dao.get(id);
		EbayListingImg ebayListingImg = new EbayListingImg();
					   ebayListingImg.setImgPlatformId(Long.valueOf(tProductImgPlatform.getId()));
		List<EbayListingImg> imageList = this.ebayListingImgService.findList(ebayListingImg);
		if(CollectionUtils.isNotEmpty(imageList)){
			return false;
		}
		this.delete(tProductImgPlatform);
		return true;
	}

	
	/**
	 * 查询 用户选择的 套图数据map  key 为 主 g 图 value 为 子g 图的集合
	 * 
	 * @param idp   主图的id
	 * @param parentId 子 g 图的 parentId
	 * @param productId  产品id
	 * @return
	 */
	public Map<String, Object> findDrawingList(String idp,String parentId, String productId) {
		
		Map<String, Object> result = new HashMap<String, Object>(); 
		//查询所有
		List<TProductImgPlatform> imageList = this.dao.findAllByTemplate(productId);
		if(CollectionUtils.isEmpty(imageList)){
			return result;
		}
		List<TProductImgPlatform>  value = new ArrayList<TProductImgPlatform>();
		imageList.forEach(image->{
			if(idp.equals(image.getId())){
				result.put("mainImage",image);
			}
			if(parentId.equals(image.getParentId())){
				value.add(image);
			}
		});
		result.put("childImage", value);
		return result;
	}
}