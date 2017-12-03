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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.fastdfs.util.FastdfsHelper;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.common.utils.ObjectUtils;
import com.oigbuy.jeesite.modules.ebay.listing.entity.EbayListingImg;
import com.oigbuy.jeesite.modules.ebay.listing.service.EbayListingImgService;
import com.oigbuy.jeesite.modules.ebay.product.dao.ProductCodeManagerDao;
import com.oigbuy.jeesite.modules.ebay.product.dao.ProductDao;
import com.oigbuy.jeesite.modules.ebay.product.dao.ProductImgDao;
import com.oigbuy.jeesite.modules.ebay.product.dao.TProductImgPlatformDao;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductCodeManager;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductImg;
import com.oigbuy.jeesite.modules.ebay.product.entity.TProductImgPlatform;
import com.oigbuy.jeesite.modules.sys.utils.UserUtils;

/**
 * 产品图片Service
 * @author mashuai
 * @version 2017-05-23
 */
@Service
@Transactional(readOnly = true)
public class ProductImgService extends CrudService<ProductImgDao, ProductImg> {
	@Autowired
	private ProductImgDao productImgDao;
	
	@Autowired
	private ProductDao productDao;
	
	/***
	 *子代码
	 */
	@Autowired
	private ProductCodeManagerDao productCodeManagerDao;
	
	
	/***
	 * 图片关联引用表 
	 */
	@Autowired
	private TProductImgPlatformDao tProductImgPlatformDao;
	
	/***
	 * ebay listing 图片
	 */
	@Autowired
	private EbayListingImgService ebayListingImgService;
	

	public ProductImg get(String id) {
		return super.get(id);
	}
	
	public List<ProductImg> findList(ProductImg productImg) {
		return super.findList(productImg);
	}
	
	public Page<ProductImg> findPage(Page<ProductImg> page, ProductImg productImg) {
		return super.findPage(page, productImg);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductImg productImg) {
		super.save(productImg);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductImg productImg) {
		super.delete(productImg);
	}
	
	/**
	 * 通过产品id和图片类型获取图片信息
	 * @author yuxiang.xiong
	 * 2017年7月19日 下午4:46:16
	 * @param productId
	 * @param imgType
	 * @return
	 */
	@Transactional(readOnly = false)
	public List<Map<String, String>> findListByProductIdAndImgType(String productId, String imgType, String templateIden) {
		List<ProductImg> list = productImgDao.findListByProductIdAndImgType(productId, imgType, templateIden);
		List<Map<String, String>> totallist = new ArrayList<Map<String, String>>();
		for(int i =0;i<list.size();i++){
			Map<String,String> map = new HashMap<String,String>();
			String id = list.get(i).getId();
			String url = list.get(i).getImgUrl();
			map.put("id", id);
			map.put("url", FastdfsHelper.getImageUrl()+url);
			map.put("index", list.get(i).getIndexSeq());
			totallist.add(map);
		}
		return totallist ;
	}
	
	
	/**
	 * 通过 productID 模板  查询 主 g 图 和 成套的 子 g 图   map 集合
	 * 
	 * 主  g 图 key 固定为  
	 * 子 g 图 key 为 对应的 parentId  
	 * 
	 * @author bill.xu 
	 * @param productId
	 * @return
	 */
	@Transactional(readOnly = false)
	public Map<String, List<TProductImgPlatform>> findTemByProductId(String productId) {
		
		Map<String, List<TProductImgPlatform>> resultMap = new HashMap<String, List<TProductImgPlatform>>();
		//所有的模板图片
		List<TProductImgPlatform> images = tProductImgPlatformDao.findAllByTemplate(productId);
		//所有的模板主 g 图
		List<TProductImgPlatform> mainImages = new ArrayList<TProductImgPlatform>();
		//所有的子 g 图的集合
		List<TProductImgPlatform> childImages = new ArrayList<TProductImgPlatform>();
		
		if(CollectionUtils.isNotEmpty(images)){
			Map<String,ProductCodeManager> codeManagerMap = new HashMap<String, ProductCodeManager>();
			List<ProductCodeManager> productCodeManagerList = this.productCodeManagerDao.findListByProductId(productId);
			if(CollectionUtils.isNotEmpty(productCodeManagerList)){
				productCodeManagerList.forEach(codeManager->{
					ProductCodeManager code = codeManagerMap.get(codeManager.getId());
					if(code==null){
						codeManagerMap.put(codeManager.getId(), codeManager);
					}
				});
			}
			images.forEach(allImage->{
				
				allImage.setProductCodeManager(codeManagerMap.get(allImage.getCodeManagerId()));
				//主 g 图  类型为 1 和 6
				if(Global.IMG_TYPE_MAIN.equals(allImage.getImgType()) ||Global.IMG_TYPE_COMPOSITE.equals(allImage.getImgType()) ){
					mainImages.add(allImage);
				}else if(Global.IMG_TYPE_CHILD_MAIN.equals(allImage.getImgType())){
					childImages.add(allImage);
				}
			});
		}
		if(CollectionUtils.isNotEmpty(childImages)){ 
			 childImages.forEach(childImage->{
				 List<TProductImgPlatform> list = resultMap.get(childImage.getParentId());
					if(list==null){
						list = new ArrayList<TProductImgPlatform>();
					}
					list.add(childImage);
					resultMap.put(childImage.getParentId(), list);
			});
			
		}
		resultMap.put(Global.IMAGE_MAIN_KEY, mainImages);
		return resultMap ;
	}
	
	

	
	
	
	
	
	private void clearMoaicViewImgCache(){
		UserUtils.removeCache(Global.SESSION_CACHE_KEY_MOSAIC_VIEWIMG);
	}
	
	
	@Transactional(readOnly = false)
	public void deleteImg(ProductImg productImg) {
		productImg = productImgDao.get(productImg);
		FastdfsHelper.delFile(productImg.getImgUrl());
		productImgDao.delete(productImg);
		
		List<ProductImg> list = productImgDao.findListByProductIdAndImgType(productImg.getProductId(), productImg.getImgType(), Global.ZERO);
		 for(int i=0;i<list.size();i++){
			 String id = list.get(i).getId();
			 productImgDao.updateindexSeq(id, String.valueOf(i));
		 }
		
		if("1".equals(productImg.getImgType())){// 删除主图时，要把合成图里的主图删除
			//compositeImageDao.deleteCompositeImgById(productImg.getId());
			// 更新产品合成图的数量
			//productDao.updateCompositeImgNumByProductId(productImg.getProductId(), compositeImageDao.findListByProductIdAndImgType(productImg.getProductId(), null).size());
			
		}else if("4".equals(productImg.getImgType())){// 输出拼图时，清空code history
			//productDao.updateCodeHistoryEmptyById(productImg.getProductId());
			// 清空redis合成图源的缓存
			clearMoaicViewImgCache();
		}
		
	}

	
	@Transactional(readOnly = false)
	public synchronized String insertProductImg(String url, String type, String productId) {
		ProductImg productImg = new ProductImg();
		productImg.setId(Global.getID().toString());
		productImg.setImgType(type);
		productImg.setImgUrl(url);
		productImg.setProductId(productId);
		
		int num = productImgDao.findcount(productId, type);
		productImg.setIndexSeq(String.valueOf(num));
		if(Global.IMG_TYPE_COMPOSITE.equals(type)){
			// 每上传一张图片，product表 maxCode + 1
			productImgDao.updateMaxCode(productId);
			Integer maxCode = productImgDao.findMaxCodeByProductId(productId);
			productImg.setCode(maxCode + "");
			
			// 清空redis合成图源的缓存
			clearMoaicViewImgCache();
		}
		
		
		productImgDao.insert(productImg);

		//当上传主图时，同时添加图片到合成图
		if(Global.ONE.equals(type)){
			//CompositeImage compositeImage = new CompositeImage(productImg.getId(), productId, null, productImg.getImgUrl(), "0"); // 0为主图类型
			//compositeImageDao.insert(compositeImage);
			// 更新产品合成图的数量
			//productDao.updateCompositeImgNumByProductId(productId, compositeImageDao.findListByProductIdAndImgType(productId, null).size());
		}
		
		return productImg.getId();
	}
	
	@Transactional
	public void updateindexSeq(String id,String indexSeq){
		productImgDao.updateindexSeq(id,indexSeq);
	}
	
	
	
	@Transactional
	public boolean isHasChild(String id,String type){
		boolean result= false;
		ProductImg productImg =this.productImgDao.get(id);
		if (Global.IMG_TYPE_MAIN.equals(productImg.getImgType())) {
			List<ProductImg> childImgsList = productImgDao.findListByParentId(productImg.getId(),type);
			if (CollectionUtils.isNotEmpty(childImgsList)) {
				result=true;
			}
		}
		return result;
	}

	
	/***
	 * 选择模板 图片的时候要进行保存子sku 图片信息,返回为 保存后的 主g 图 和对应的 子 g 图的list，主 g 图和 子 g 图 模板 可以不配套使用
	 * 
	 * @param idp  选择的主 g 图 id
	 * @param idc  选择的  子 g图的 主 g 图 id 
	 * @param productId  产品 
	 * @return   ProductImg 
	 */
	@Transactional(readOnly = false)
	public TProductImgPlatform bindSkuImage(String idp, String idc, String productId) {
		        //选择的模板中的 主 g 图 id
				TProductImgPlatform mainImage = this.tProductImgPlatformDao.get(idp);
		
				//选择模板下的 子 g 图的 对应的 主 g 图 id 	
				List<TProductImgPlatform> childImageList = this.tProductImgPlatformDao.findListByParentId(idc, Global.TEMPLATE_IDEN);
				
				//该产品 新加主 g 图，已经添加对应的 子 g 图
				//TODO  这里现在是进行了 新增，是不是可以进行 修改 
				TProductImgPlatform zhuImage = new TProductImgPlatform();
				String zhuId = String.valueOf(Global.getID());
				zhuImage.setId(zhuId);
				zhuImage.setImgId(mainImage.getImgId());
				zhuImage.setImgType(Global.IMG_TYPE_MAIN);
				zhuImage.setImgUrl(mainImage.getImgUrl());//注意  get 中存不存在 imgUrl,不会进库 但是可以在页面展示需要
				zhuImage.setPlatformFlag(Global.PLATFORM_FLAG_EBAY);
				zhuImage.setPlatformId(mainImage.getPlatformId());
				zhuImage.setProductId(productId);
				zhuImage.setTemplateIden(Global.TEMPLATE_IDEN_NON);
				this.tProductImgPlatformDao.insert(zhuImage);//保存该主 g 图
				//保存对应的子 g 图
				List<TProductImgPlatform> imageListc = new ArrayList<TProductImgPlatform>();// 子 g 图的集合 
				// 遍历 子 g 图 code manager id 查询
				TProductImgPlatform cImage =null;
				int index = 0;
				for (TProductImgPlatform childImage : childImageList) {
					//查询该子 图是不是存在，如果存在 则进行更新，否则新增
					cImage = this.tProductImgPlatformDao.findByCodeManagerId(childImage.getCodeManagerId());
					 if(ObjectUtils.notEqual(cImage, null)){//更新该 子 g 图
						 cImage.setImgId(childImage.getImgId());
						 cImage.setParentId(zhuId);
						 this.tProductImgPlatformDao.update(cImage);
					 }else{//新增 子 g 图
						 cImage = new TProductImgPlatform();
						 cImage.setCodeManagerId(childImage.getCodeManagerId());
						 cImage.setId(String.valueOf(Global.getID()));
						 cImage.setImgId(childImage.getImgId());
						 cImage.setImgType(Global.IMG_TYPE_CHILD_MAIN);
						 cImage.setPlatformFlag(Global.PLATFORM_FLAG_EBAY);
						 cImage.setPlatformId(childImage.getPlatformId());
						 cImage.setProductId(productId);
						 cImage.setParentId(zhuId);
						 cImage.setIndex(index);
						 cImage.setTemplateIden(Global.TEMPLATE_IDEN_NON);
						 this.tProductImgPlatformDao.insert(cImage);
					 }
					 index++;
					 imageListc.add(cImage);
				}
				zhuImage.settProductImgPlatformList(imageListc);
				
		return zhuImage;
	}

	
	/***
	 * 获取 主 g 图 下的 子g 图集合   非 模板的数据
	 * 
	 * @param parentId
	 * 
	 * @return
	 */
	public List<TProductImgPlatform> findByParentId(String parentId, String templateIden) {
		
		List<TProductImgPlatform> images = this.tProductImgPlatformDao.findListByParentId(parentId, templateIden);
		if(CollectionUtils.isNotEmpty(images)){
			Iterator<TProductImgPlatform> iterator = images.iterator();
			while (iterator.hasNext()) {
				TProductImgPlatform image =  iterator.next();
				image.setProductCodeManager(this.productCodeManagerDao.get(image.getCodeManagerId()));
			}
		}
		return images;
	}
	
	/***
	 * 
	 * @param codeManagerId
	 * @param imgType 
	 * @param productId
	 * @param  templeteIden 0 1
	 * @return
	 */
	public ProductImg findByCodeManagerId(String codeManagerId,String imgType, String productId, String templeteIden) {
		ProductImg image=new ProductImg();
		image.setCodeManagerId(codeManagerId);
		image.setImgType(imgType);
		image.setProductId(productId);
		image.setTemplateIden(templeteIden);
		return this.productImgDao.findByCodeManagerId(image);
	}

	
	
	/***
	 * 通过产品id 获取 该产品下的 主 g 图 ，特效图 ， 细节图（已经进行绑定到关系表的数据）
	 * 
	 * 
	 * 
	 * @author bill.xu 
	 * @param id
	 */
	public Map<String,List<TProductImgPlatform>> getMainAndDetailAndSpecialImage(String id) {
		 Map<String,List<TProductImgPlatform>> resultMap = new  HashMap<String, List<TProductImgPlatform>>();
       // TODO 产品完善资料进来的时候，默认不加载已经 绑定关系的图片，通过选图进行选择，如果这里显示出来的话，再通过选择的数据就是一样的
		 //		 //主 G 图
//		 List<TProductImgPlatform> mainList = this.tProductImgPlatformDao.findListByProductIdAndImgType(id,Global.IMG_TYPE_MAIN , Global.TEMPLATE_IDEN_NON);
//		 // 细节图
//		 List<TProductImgPlatform> detailList= this.tProductImgPlatformDao.findListByProductIdAndImgType(id,Global.IMG_TYPE_DETAIL , Global.TEMPLATE_IDEN_NON);
//		 //特效图
//		 List<TProductImgPlatform> specialList = this.tProductImgPlatformDao.findListByProductIdAndImgType(id,Global.IMG_TYPE_SPECIAL_EFFECTS , Global.TEMPLATE_IDEN_NON);
//		 resultMap.put(Global.IMG_TYPE_MAIN, mainList);
//		 resultMap.put(Global.IMG_TYPE_DETAIL, detailList);
//		 resultMap.put(Global.IMG_TYPE_SPECIAL_EFFECTS, specialList);
		 
		 return resultMap; 
	}
	
		/***
		 * 获得 该产品的所有图片 ，并根据 type 进行过滤（ 如果 该产品已经存在 该 主 g 图 ，细节图 2，  特效图5 则进行过滤掉 ）
		 * 
		 * @param productId
		 * @param type 
		 * @param flag  为 true 是表示是 产品那边选择图片使用，false 表示 是listing 选择产品图片
		 * @param listingId   flag 为 false时使用
		 * @return
		 */
		public List<ProductImg> findAllImageByProductId(String productId,String type,boolean flag, String listingId){
			
			List<ProductImg> imageList = this.dao.findByProductId(productId);
			
			if(CollectionUtils.isEmpty(imageList) || Global.IMG_TYPE_CHILD_MAIN.equals(type)){
				return imageList;
			}
			
			//if(CollectionUtils.isNotEmpty(imageList)&& (Global.IMG_TYPE_DETAIL.equals(type) || Global.IMG_TYPE_SPECIAL_EFFECTS.equals(type) || Global.IMG_TYPE_MAIN.equals(type))){
			List<String> imageIds = new ArrayList<String>();
				
			 if(flag){//产品查询
					// 查询
					List<TProductImgPlatform>  platformImageList = this.tProductImgPlatformDao.findListByProductIdAndImgType(productId, type, Global.TEMPLATE_IDEN_NON);
					if(CollectionUtils.isNotEmpty(platformImageList)){
						platformImageList.forEach(image->{
							imageIds.add(image.getImgId());
						});
					}
			}else{//listing 查询产品图片
					List<EbayListingImg> listingImageList = this.ebayListingImgService.findList(new EbayListingImg(Long.valueOf(listingId), type));
					if(CollectionUtils.isNotEmpty(listingImageList)){
						listingImageList.forEach(ebayListingImg->{
							imageIds.add(String.valueOf(ebayListingImg.getImageId()));
						});
					}
				}
				
				if(CollectionUtils.isNotEmpty(imageIds)){
					Iterator<ProductImg> iterator = imageList.iterator();
					while (iterator.hasNext()){
						ProductImg productImg =  iterator.next();
						//TODO 如果 已经存在该图 则去掉该 图片，如果 该 类型是 特效图 或则是 细节图 ，则 去掉 非 特效图 和 细节图（和 type 不一样的图片）
						if(imageIds.contains(productImg.getId()) || ( (Global.IMG_TYPE_DETAIL.equals(type) || Global.IMG_TYPE_SPECIAL_EFFECTS.equals(type)) && !type.equals(productImg.getImgType()) )){
							iterator.remove();
						}
					}
				}
				
			//}
			return imageList;
		}

		
		
		/***
		 *  返回 true 表示可以删除 并且数据已经删除，返回 false 表示 不可删除
		 * @param id  
		 * @return
		 */
		@Transactional(readOnly=false)
		public boolean deleteProductImg(String id) {
			
			
			ProductImg productImg = this.dao.get(id);
			
			int count = this.tProductImgPlatformDao.findCountByImgId(id);
			
			if(count>0){
				return false;
			}
			
			EbayListingImg ebayListingImg = new EbayListingImg();
			ebayListingImg.setImageId(Long.valueOf(id));
			List<EbayListingImg> findList = ebayListingImgService.findList(ebayListingImg);
			if(CollectionUtils.isNotEmpty(findList)){
				return false;
			}
			FastdfsHelper.delFile(productImg.getImgUrl());
			this.dao.delete(productImg);
			return true;
		}
	
		
}