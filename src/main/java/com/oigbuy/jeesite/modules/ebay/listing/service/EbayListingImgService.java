/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.listing.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.fastdfs.util.FastdfsHelper;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.common.utils.ObjectUtils;
import com.oigbuy.jeesite.common.utils.StringUtils;
import com.oigbuy.jeesite.modules.ebay.listing.dao.EbayListingImgDao;
import com.oigbuy.jeesite.modules.ebay.listing.dto.ListingDetailDto;
import com.oigbuy.jeesite.modules.ebay.listing.entity.EbayListingImg;
import com.oigbuy.jeesite.modules.ebay.product.dto.ProductDetail;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductCodeManager;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductImg;
import com.oigbuy.jeesite.modules.ebay.product.entity.TProductImgPlatform;
import com.oigbuy.jeesite.modules.ebay.product.service.ProductImgService;
import com.oigbuy.jeesite.modules.ebay.product.service.TProductImgPlatformService;

/**
 * ebay listing 图片Service
 * @author bill.xu
 * @version 2017-09-22
 */
@Service
@Transactional(readOnly = true)
public class EbayListingImgService extends CrudService<EbayListingImgDao, EbayListingImg> {

	
	@Autowired
	private ProductImgService productImgService;
	
	@Autowired
	private TProductImgPlatformService tProductImgPlatformService;
	
	public EbayListingImg get(String id) {
		return super.get(id);
	}
	
	public List<EbayListingImg> findList(EbayListingImg ebayListingImg) {
		return super.findList(ebayListingImg);
	}
	
	public Page<EbayListingImg> findPage(Page<EbayListingImg> page, EbayListingImg ebayListingImg) {
		return super.findPage(page, ebayListingImg);
	}
	
	@Transactional(readOnly = false)
	public void save(EbayListingImg ebayListingImg) {
		super.save(ebayListingImg);
	}
	
	@Transactional(readOnly = false)
	public void delete(EbayListingImg ebayListingImg) {
		super.delete(ebayListingImg);
	}

	
	/***
	 * 查询 listing code manager 图片
	 * 
	 * @param listingId
	 * @param codemanagerId
	 * @param imgType
	 * @return
	 */
	public EbayListingImg findByListingIdAndCodeManagerId(Long listingId,Long codemanagerId, String imgType) {
		EbayListingImg image = null;
		List<EbayListingImg>  list = findList(new EbayListingImg(listingId,codemanagerId, imgType));
		if(CollectionUtils.isNotEmpty(list)){
			Iterator<EbayListingImg> iterator = list.iterator();
			image = iterator.next();
		}
		return image;
	}

	
	
	@Transactional(readOnly = false)
	public void saveListingImage(ProductDetail productDetail, Long listingId) {
		//主图（要判断主图是不是可用，不能有重复的）
		String [] mianIds=productDetail.getMainImageId1();
		String [] mianUrls=productDetail.getMainImageUrl1();
		//细节图
		String [] detailIds=productDetail.getMainImageId2();
		String [] detailUrls=productDetail.getMainImageUrl2();
		//特效图
		String [] specialIds=productDetail.getMainImageId5();
		String [] specialUrls=productDetail.getMainImageUrl5();
		
		List<EbayListingImg> imageList =  new ArrayList<EbayListingImg>();
		//主 g 图只有一个 
		TProductImgPlatform tProductImgPlatform = tProductImgPlatformService.get(mianIds[0]);
		
		if(tProductImgPlatform==null){
			throw new RuntimeException(" 主 g 图查询异常，请检查数据，稍后再试");
		}
		EbayListingImg image = new EbayListingImg(Global.getID().toString(),listingId, Long.valueOf(tProductImgPlatform.getImgId()), Global.IMG_TYPE_MAIN,mianUrls[0]);
		image.setImgPlatformId(Long.valueOf(tProductImgPlatform.getId()));
		imageList.add(image);
		//保存细节图
		imageList.addAll(setImageList(listingId,detailIds,detailUrls,Global.IMG_TYPE_DETAIL));
		// 保存特效图
		imageList.addAll(setImageList(listingId,specialIds,specialUrls, Global.IMG_TYPE_SPECIAL_EFFECTS));
		// 保存  子 sku 图片 
		List<ProductCodeManager> codeManagerLsit = productDetail.getCodeManagerList();
		if(CollectionUtils.isNotEmpty(codeManagerLsit)){
			List<EbayListingImg> codeManagerImageList =  new ArrayList<EbayListingImg>();
			for (ProductCodeManager productCodeManager : codeManagerLsit) {
				if(productCodeManager==null || StringUtils.isBlank(productCodeManager.getId())){
					continue;
				}
				String imageIds = productCodeManager.getImageId();
				TProductImgPlatform platformImage = tProductImgPlatformService.get(imageIds);
				if(platformImage==null || StringUtils.isBlank(platformImage.getImgId()) ){
					continue;
					}
				EbayListingImg codemanagerImage = new EbayListingImg(Global.getID().toString(),listingId,Long.valueOf(platformImage.getImgId()) , Global.IMG_TYPE_CHILD_MAIN,productCodeManager.getImgUrl());
				               codemanagerImage.setCodeManagerId(Long.valueOf(productCodeManager.getId()));
				codeManagerImageList.add(codemanagerImage);
			}
			imageList.addAll(codeManagerImageList);
		}
		
		if(CollectionUtils.isNotEmpty(imageList)){
			this.dao.insertList(imageList);
		}
	}

	
	/***
	 * 通过页面传递过来的图片集合 得到 细节图和特效图 集合 
	 * 
	 * @param listingId
	 * @param detailIds
	 * @param detailUrls
	 * @param imgTypeDetail
	 * @return
	 */
	private List<EbayListingImg> setImageList(Long listingId,String[] detailIds, String[] detailUrls, String imgTypeDetail) {
		List<EbayListingImg> imageList = new ArrayList<EbayListingImg>();
		if(detailIds!=null && detailUrls!=null){
			int idLength =  detailIds.length;
			int urlLength =  detailUrls.length;
			for (int i=0;i<idLength;i++) {
				if(i<urlLength){
					TProductImgPlatform tProductImgPlatform = tProductImgPlatformService.get(detailIds[i]);
					if(tProductImgPlatform==null){
						continue;
					}
					EbayListingImg image = new EbayListingImg(Global.getID().toString(),listingId, Long.valueOf(tProductImgPlatform.getImgId()), imgTypeDetail,detailUrls[i]);
					image.setImgPlatformId(Long.valueOf(tProductImgPlatform.getId()));
					imageList.add(image);
				}
			}
		}
		return imageList;
	}

	/***
	 * 通过 listingId 获取 该listing 的相关图片 （主 g 图，细节图，特效图）
	 * 
	 * @param listingId
	 * @param detailDto
	 * @return
	 */
	public ListingDetailDto getListingDetailImage(Long listingId,ListingDetailDto detailDto) {
		
		if(detailDto == null){
			detailDto = new ListingDetailDto();
		}
		EbayListingImg image=new EbayListingImg();
		image.setListingId(listingId);
		List<EbayListingImg> imageList = this.dao.findList(image);
		
		if(CollectionUtils.isEmpty(imageList)){
			return detailDto;
		}
			
		List<EbayListingImg> mainList = new ArrayList<EbayListingImg>();
		List<EbayListingImg> detailList = new ArrayList<EbayListingImg>();
		List<EbayListingImg> specialList = new ArrayList<EbayListingImg>();
			
		Iterator<EbayListingImg> iterator = imageList.iterator();
			
		while (iterator.hasNext()) {
				EbayListingImg type = iterator.next();
				switch (type.getImageType()) {
					case Global.IMG_TYPE_MAIN:
						mainList.add(type);
						break;
					case Global.IMG_TYPE_DETAIL:
						detailList.add(type);
						break;
					case Global.IMG_TYPE_SPECIAL_EFFECTS:
						specialList.add(type);
						break;
					default:
						break;
				}
				
//				if(Global.IMG_TYPE_MAIN.equals(type.getImageType())){
//					mainList.add(type);
//				}else if(Global.IMG_TYPE_DETAIL.equals(type.getImageType())){
//					detailList.add(type);
//				}else if(Global.IMG_TYPE_SPECIAL_EFFECTS.equals(type.getImageType())){
//					specialList.add(type);
//				}
				
			}
			if(CollectionUtils.isNotEmpty(mainList)){
				detailDto.setMainListingImg(mainList.get(0));
			}
			detailDto.setDetailListingImg(detailList);
			detailDto.setSpecialListingImg(specialList);
			
		return detailDto;
	}

	
	/***
	 * listing 图片上传或则修改   TODO  这要重构一下
	 * 
	 * @param type
	 * @param listingId
	 * @param newImgUrl
	 * @return
	 */
	@Transactional(readOnly=false)
	public EbayListingImg uploadImage(String type, String listingId,String newImgUrl,String codeManagerId) {
		EbayListingImg image = null ;
		if(Global.IMG_TYPE_MAIN.equals(type)){//如果是 主 g 图 则进行更新
			image = this.findByListingIdAndCodeManagerId(Long.valueOf(listingId), null, type);
			image.setImageUrl(newImgUrl);
			this.dao.update(image);
		}else if(Global.IMG_TYPE_DETAIL.equals(type) || Global.IMG_TYPE_SPECIAL_EFFECTS.equals(type)){//如果是细节图 ，或则是 特效图  则是直接进行保存
			image = new EbayListingImg(); 
			image.setId(String.valueOf(Global.getID()));
			image.setImageId(Global.getID());
			image.setImageType(type);
			image.setImageUrl(newImgUrl);
			image.setListingId(Long.valueOf(listingId));
			this.dao.insert(image);
		}else if (Global.IMG_TYPE_CHILD_MAIN.equals(type)){//如果是 子 g 图的话 ，这个时候要用到  codemanageId
			image = this.findByListingIdAndCodeManagerId(Long.valueOf(listingId), Long.valueOf(codeManagerId), type);
			if(ObjectUtils.notEqual(image, null)){
				image.setImageUrl(newImgUrl);
				this.dao.update(image);
			}else{
				image = new EbayListingImg(); 
				image.setCodeManagerId(Long.valueOf(codeManagerId));
				image.setId(String.valueOf(Global.getID()));
				image.setImageId(Global.getID());
				image.setImageType(type);
				image.setImageUrl(newImgUrl);
				image.setListingId(Long.valueOf(listingId));
				this.dao.insert(image);
			}
		}
		return image;
	}

	
	/***
	 * 删除 图片 （主 g 图 只有一张不可删除，其他类型 图片最少要有一张 ？？？）
	 * 
	 * @param id
	 * @param listingId
	 * @param imageType
	 * @return
	 */
	@Transactional(readOnly=false)
	public boolean  deleteImage(String id, String listingId, String imageType) {
		boolean result = false;
		List<EbayListingImg> imageList = this.findList(new EbayListingImg(Long.valueOf(listingId), imageType));
		if(CollectionUtils.isNotEmpty(imageList) && imageList.size()>1){
			EbayListingImg image = this.dao.get(id);
			FastdfsHelper.delFile(image.getImageUrl());
			this.dao.delete(image);
			result=true;
		}
		return result;
	}
	
	/***
	 * 保存更新 listing 图片 
	 * @param type
	 * @param productId
	 * @param ids
	 * @param codeManagerId
	 * @param listingId
	 * @return
	 */
	@Transactional(readOnly=false)
	public List<EbayListingImg> saveListingImage(String type, Long productId,String ids, Long codeManagerId, Long listingId) {
		
		List<EbayListingImg> images = new ArrayList<EbayListingImg>();
		EbayListingImg image = null;
		
		if(Global.IMG_TYPE_MAIN.equals(type)||Global.IMG_TYPE_CHILD_MAIN.equals(type)){// 主 g 图更新或则新增 。子 g 图 更新或则新增
			
//			ProductImg img = this.productImgService.get(ids);
			
			TProductImgPlatform platformImg = this.tProductImgPlatformService.get(ids);
			
			ProductImg img = this.productImgService.get(platformImg.getImgId());
			
			//子 g 图和 主 g 图都可以用这个查到
			List<EbayListingImg>  listingImages = this.dao.findList(new EbayListingImg(listingId, codeManagerId, type));
			
			if(CollectionUtils.isNotEmpty(listingImages)){
				image = listingImages.get(0);
			}
			if(ObjectUtils.notEqual(image, null)){//更新
					image.setIsNewRecord(false);
			}else{//新增
					image = new EbayListingImg();
					image.setIsNewRecord(true);
					image.setId(Global.getID().toString());
				}
				image.setCodeManagerId(codeManagerId);
				image.setImageId(Long.valueOf(img.getId()));
				image.setImageType(type);
				image.setImageUrl(img.getImgUrl());
				image.setListingId(listingId);
				image.setImgPlatformId(Long.valueOf(platformImg.getId()));
				this.save(image);//保存或则新增
				images.add(image);
				return images;
		}else if(Global.IMG_TYPE_DETAIL.equals(type) || Global.IMG_TYPE_SPECIAL_EFFECTS.equals(type)){//细节图  或则 特效图  新增
			String[] imageIds = ids.split(Global.SEPARATE_7);
			for (String id : imageIds) {
				image = new EbayListingImg(); 
				TProductImgPlatform platformImg = this.tProductImgPlatformService.get(id);
				ProductImg img = this.productImgService.get(platformImg.getImgId());
				image.setId(String.valueOf(Global.getID()));
				image.setImageId(Long.valueOf(img.getId()));
				image.setImageType(type);
				image.setImageUrl(img.getImgUrl());
				image.setIsNewRecord(true);
				image.setListingId(listingId);
				images.add(image);
				image.setImgPlatformId(Long.valueOf(platformImg.getId()));
			}
		}
		if(CollectionUtils.isNotEmpty(images)){
			this.dao.insertList(images);
		}
		return images;
	}

	/***
	 * 批量插入
	 * @param imageList
	 */
	
	@Transactional(readOnly=false)
	public void insertList(List<EbayListingImg> imageList) {
		this.dao.insertList(imageList);
	}

	
	
	/**
	 * listing 选择图片的时候  查询相关的图片
	 * 
	 * @param listingId
	 * @param productId
	 * @param codeManagerId
	 * @param type
	 * @return
	 */
	@Transactional(readOnly=false)
	public List<TProductImgPlatform> getListingCanChooseImageList(String listingId, String productId, String codeManagerId,String type) {
		TProductImgPlatform tProductImgPlatform = new TProductImgPlatform();
		if(StringUtils.isNotBlank(codeManagerId)){
			tProductImgPlatform.setCodeManagerId(codeManagerId);
		}
		tProductImgPlatform.setProductId(productId);
		tProductImgPlatform.setImgType(type);
		List<TProductImgPlatform> platformImageList = this.tProductImgPlatformService.findList(tProductImgPlatform );
		
		if(CollectionUtils.isNotEmpty(platformImageList)){
			EbayListingImg ebayListingImg =new EbayListingImg();
			if(StringUtils.isNotBlank(codeManagerId)){
				ebayListingImg.setCodeManagerId(Long.valueOf(codeManagerId));
			}
			ebayListingImg.setListingId(Long.valueOf(listingId));
			ebayListingImg.setImageType(type);
			List<EbayListingImg> imageList = this.dao.findList(ebayListingImg );
		    List<String> imageIds = new ArrayList<String>();
			if(CollectionUtils.isNotEmpty(imageList)){
				imageList.forEach(image->{
					
					if(image.getImgPlatformId()!=null){
						imageIds.add(String.valueOf(image.getImgPlatformId()));
					}else{
						imageIds.add(String.valueOf(image.getImageId()));
					}
					
				});
		    }
			
			Iterator<TProductImgPlatform> iterator = platformImageList.iterator();
			while (iterator.hasNext()) {
				TProductImgPlatform next = iterator.next();
				
				if(imageIds.contains(next.getId()) || imageIds.contains(next.getImgId())){
					iterator.remove();
				}
			}
		
		}
		return platformImageList;
		
	}
	
}