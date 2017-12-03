/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.productcollection.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oigbuy.api.core.call.ebay.GetEbayImageUrl;
import com.oigbuy.api.core.request.ebay.GetEbayImageUrlRequest;
import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.fastdfs.util.FastdfsHelper;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.modules.ebay.productDevelop.dao.ProductAndCollectionDao;
import com.oigbuy.jeesite.modules.ebay.productcollection.dao.ProductCollectionDao;
import com.oigbuy.jeesite.modules.ebay.productcollection.dao.ProductCollectionLogDao;
import com.oigbuy.jeesite.modules.ebay.productcollection.entity.ProductCollection;
import com.oigbuy.jeesite.modules.sys.utils.UserUtils;

/**
 * 竞品采集Service
 * @author 王佳点
 * @version 2017-09-04
 */
@Service
@Transactional(readOnly = true)
public class ProductCollectionService extends CrudService<ProductCollectionDao, ProductCollection> {
	
	@Autowired
	private ProductCollectionDao productCollectionDao;
	
	@Autowired
	private ProductCollectionLogDao productCollectionLogDao;
	
	@Autowired
	private ProductAndCollectionDao productAndCollectionDao;

	public ProductCollection get(String id) {
		return super.get(id);
	}
	
	public List<ProductCollection> findList(ProductCollection productCollection) {
		return super.findList(productCollection);
	}
	
	public Page<ProductCollection> findPage(Page<ProductCollection> page, ProductCollection productCollection) {
		return super.findPage(page, productCollection);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductCollection productCollection) {
		
		//根据原图地址生成本地图片地址
		if (StringUtils.isNotEmpty(productCollection.getImgLink())) {
			String imgLink=StringUtils.replace(productCollection.getImgLink(), "&amp;", "&");
			String imgUrl = FastdfsHelper.uploadFile(imgLink);
			if (StringUtils.isNotBlank(imgUrl)) {
				productCollection.setImgUrl(imgUrl);;
			} else {
				productCollection.setImgUrl(Global.getConfig("default_img_url"));
			}
		}
		
		
		if (StringUtils.isBlank(productCollection.getId())) {
			productCollection.setId(Global.getID().toString());
			productCollection.setOperator(UserUtils.getUser().getId());
			productCollection.setCreateTime(new Date()); 
			productCollection.setStatus(Global.PRODUCT_COLLECT_STATUS_COLLECTION);
			
			productCollectionDao.insert(productCollection);
		}else {
			productCollectionDao.update(productCollection);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductCollection productCollection) {
		super.delete(productCollection);
	}
	
	@Transactional(readOnly = false)
	  public void saveOrUpdate(ProductCollection productCollection) {
	    if(!productCollection.getIsNewRecord()){//更新
	      productCollectionDao.update(productCollection);
	    }else{//新增
	      productCollectionDao.insert(productCollection);
	    }
	  }
	
	@Transactional(readOnly = false)
	  public void update(ProductCollection productCollection) {
	      productCollectionDao.update(productCollection);
	  }
	  
	  /***
	   * 通过 关联的 推荐 id 进行查询  
	   * 
	   * @param id
	   * @return
	   */
	  public ProductCollection findByRecomId(String id) {
	    return this.productCollectionDao.findByRecomId(id);
	  }
	  
		/***
		 * 通过产品id 查询 竞品采集中的  itemId
		 * 
		 * @param productId
		 * @return
		 */
		
		public String findItemIdByProductId(Long productId) {
			
			String collectId = productAndCollectionDao.findByProductId(String.valueOf(productId));
			
			ProductCollection productCollection = this.productCollectionDao.get(collectId);
			
			return productCollection.getItemId();
		}
		
		/**
		 * 通过产品ID查询竞品ID
		 */
		
		public String findCollectIdByProductId(String productId){
			return productCollectionDao.findCollectIdByProductId(productId);
		}
		
		
		/**
		 * 通过itemId获取主图url
		 * @param itemId
		 * @return
		 */
		public String getMainImageUrl(String itemId){
			//调用ebay SDK
			GetEbayImageUrlRequest request = new GetEbayImageUrlRequest(itemId);
			GetEbayImageUrl ebayImageUrl = new GetEbayImageUrl(request);
			String mainImageUrl = ebayImageUrl.getResponse().getImageUrl();
			String message = ebayImageUrl.getResponse().getErrorMessage();
			if(StringUtils.isNotBlank(mainImageUrl)){
				String imageUrl = StringUtils.substringBeforeLast(mainImageUrl, ".JPG")+".JPG";
				return imageUrl;
			}else if (StringUtils.isBlank(mainImageUrl) &&  StringUtils.isNotBlank(message)){
				throw new RuntimeException(message);
			}else{
				throw new RuntimeException("itemId "+itemId+"有误 获取不到详细的图片地址！");
			}
			
		}
}