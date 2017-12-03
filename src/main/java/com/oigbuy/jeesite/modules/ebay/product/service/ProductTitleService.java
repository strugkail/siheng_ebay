/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.product.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.common.utils.StringUtils;
import com.oigbuy.jeesite.modules.ebay.listing.entity.EbayListing;
import com.oigbuy.jeesite.modules.ebay.listing.service.EbayListingService;
import com.oigbuy.jeesite.modules.ebay.product.dao.ProductTitleDao;
import com.oigbuy.jeesite.modules.ebay.product.dao.TitleKeyDao;
import com.oigbuy.jeesite.modules.ebay.product.dto.ProductDto;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductTitle;
import com.oigbuy.jeesite.modules.ebay.product.entity.TitleKey;

/**
 * 产品标题Service
 * @author bill.xu
 * @version 2017-09-22
 */
@Service
@Transactional(readOnly = true)
public class ProductTitleService extends CrudService<ProductTitleDao, ProductTitle> {

	
	@Autowired
	private TitleKeyDao titleKeyDao;
	
	
	
	@Autowired
	private EbayListingService ebayListingService;
	
	public ProductTitle get(String id) {
		return super.get(id);
	}
	
	
	/***
	 * 对一组 产品标题来说   他们拥有相同的 关键字，所以这里不是遍历的时候查询赋值
	 */
	public List<ProductTitle> findList(ProductTitle productTitle) {
		List<ProductTitle> titleList = super.findList(productTitle);
		return titleList;
	}
	
	public Page<ProductTitle> findPage(Page<ProductTitle> page, ProductTitle productTitle) {
		return super.findPage(page, productTitle);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductTitle productTitle) {
		super.save(productTitle);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductTitle productTitle) {
		super.delete(productTitle);
	}
	

	
	

	/***
	 * 保存 产品  的 标题
	 *  
	 * @param productDto
	 */
	@Transactional(readOnly=false)
	public ProductDto saveProductTitle(ProductDto productDto) {
		
		String productId = productDto.getId();
		this.dao.deleteByProductId(productId,Global.ONE); //删除 ebay 下的该产品下的所有标题
		List<ProductTitle> titleList =new ArrayList<ProductTitle>();
		
		List<ProductTitle> mainTitleList = genernalTitle(productId,productDto.getTitle(),productDto.getTitleCode(),Global.ZERO, Global.ONE,productDto.getDevelopmentType());
		titleList.addAll(mainTitleList);//构建主 标题
		List<ProductTitle> subTitleList = genernalTitle(productId,productDto.getSubtitle(), productDto.getSubTitleCode(), Global.ONE, Global.ONE,productDto.getDevelopmentType());
		titleList.addAll(subTitleList);//构建副 标题
		if(CollectionUtils.isNotEmpty(titleList)){
			this.dao.insertList(titleList);
		}
		
		productDto.setMainTitleList(mainTitleList);
		productDto.setSubTitleList(subTitleList);
		return productDto;
		
	}


	/***
	 * 
	 * @param productId 产品id 
	 * @param title  标题
	 * @param titleCode  标题编码
	 * @param type  标题类型 
	 * @param platform   平台标志 （0 wish 1 ebay）
	 * @param saleType  销售类型 （1、海外精品  2、虚拟精品 3、中国直发铺货 4、虚拟铺货）
	 * @return
	 */
	private List<ProductTitle> genernalTitle(String productId,String title, String titleCode, String type, String platform, String saleType) {
		List<ProductTitle> productTitleList = new ArrayList<ProductTitle>();
		
		if(Global.SALE_TYPE_OVERSEAS_STOREHOUSE.equals(saleType) || Global.SALE_TYPE_VIRTUAL_OVERSEAS_STOREHOUSE.equals(saleType)){
			// 精品的是要进行自己输入 只有一个
			productTitleList.add(new ProductTitle(String.valueOf(Global.getID()), productId, title, titleCode, type, platform));
			
		}else{//铺货的需要通过 关键字生成   会产生多个
			if (StringUtils.isNotBlank(title) && StringUtils.isNotBlank(titleCode)) {
				String[] titleList = title.split(Global.SEPARATE_6);
				String[] codeList = titleCode.split(Global.SEPARATE_6);
				for (int i = 0; i < titleList.length; i++) {
					if(i<codeList.length){
						productTitleList.add(new ProductTitle(Global.getID().toString(), productId, titleList[i],codeList[i], type, platform));
					}
				}
			}
			
		}
		return productTitleList;
	}



	/***
	 * 获取该产品的  标题 和 关键字 集合
	 * 
	 * @param id
	 * @param productDto
	 * @return
	 */
	@Transactional(readOnly=false)
	public ProductDto getProductTitle(String id, ProductDto productDto) {
		
		ProductTitle productTitle = new ProductTitle();
		productTitle.setPlatformType(Global.ONE);// ebay
		productTitle.setProductId(id);
		
		List<ProductTitle> mainTitleList = new ArrayList<ProductTitle>();
		productTitle.setType(Global.ZERO);// 0 主标题   1副标题
		mainTitleList = this.findList(productTitle);
		StringBuffer mainBufferTitle = new StringBuffer();
		StringBuffer mainBufferCode = new StringBuffer();
		
		for (ProductTitle pt : mainTitleList) {
			mainBufferTitle.append(pt.getContent()+Global.SEPARATE_6);
			mainBufferCode.append(pt.getCode()+Global.SEPARATE_6);
		}
		//设置 刊登主 标题的集合 
		productDto.setTitle(mainBufferTitle.toString());// 主标题的 title
		productDto.setTitleCode(mainBufferCode.toString());//主标题的title code
		
		// 副标题
		List<ProductTitle> subTitleList = new ArrayList<ProductTitle>();
		productTitle.setType(Global.ONE);// 0 主标题   1副标题
		subTitleList = this.findList(productTitle);
		StringBuffer subBufferTitle = new StringBuffer();
		StringBuffer subBufferCode = new StringBuffer();
		
		for (ProductTitle pt : subTitleList) {
			subBufferTitle.append(pt.getContent()+Global.SEPARATE_6);
			subBufferCode.append(pt.getCode()+Global.SEPARATE_6);
		}
		productDto.setSubtitle(subBufferTitle.toString());
		productDto.setSubTitleCode(subBufferCode.toString());
		
		//主标题的关键字
		List<TitleKey> mainkeyList = this.titleKeyDao.findList(new TitleKey(Long.valueOf(id), Global.ONE, Global.ZERO));
		StringBuffer key = new StringBuffer();
		StringBuffer keyCode = new StringBuffer();
		StringBuffer otherkey = new StringBuffer();
		StringBuffer otherkeyCode = new StringBuffer();
		for (TitleKey titleKey : mainkeyList) {
			if(Global.ZERO_INTEGER==titleKey.getTitleType()){//主标题  必选 关键字
				key.append(titleKey.getTitleKey()+Global.SEPARATE_6);
				keyCode.append(titleKey.getCode()+Global.SEPARATE_6);
			}else{
				otherkey.append(titleKey.getTitleKey()+Global.SEPARATE_6);
				otherkeyCode.append(titleKey.getCode()+Global.SEPARATE_6);
			}
		}
		productDto.setKeyWord1(key.toString());// 主标题的必须关键字
		productDto.setOtherKeyWord1(otherkey.toString());//主标题的 非必须关键字
		//副标题的关键字
		List<TitleKey> keyList = this.titleKeyDao.findList(new TitleKey(Long.valueOf(id), Global.ONE, Global.ONE));
		StringBuffer key1 = new StringBuffer();
		StringBuffer keyCode1 = new StringBuffer();
		StringBuffer otherkey1 = new StringBuffer();
		StringBuffer otherkeyCode1 = new StringBuffer();
		for (TitleKey titleKey : keyList) {
			if(Global.ZERO_INTEGER==titleKey.getTitleType()){//主标题  必选 关键字
				key1.append(titleKey.getTitleKey()+Global.SEPARATE_6);
				keyCode1.append(titleKey.getCode()+Global.SEPARATE_6);
			}else{
				otherkey1.append(titleKey.getTitleKey()+Global.SEPARATE_6);
				otherkeyCode1.append(titleKey.getCode()+Global.SEPARATE_6);
			}
		}
		productDto.setKeyWord2(key1.toString());// 副标题的必须关键字
		productDto.setOtherKeyWord2(otherkey1.toString());//副标题的 非必须关键字
		return productDto;
	}


	
	/***
	 * 校验 产品的标题是不是可用,得到可用的一个 product
	 * 
	 * @param product
	 * @return
	 */
	@Transactional(readOnly=false)
	public ProductTitle checkProductTitle(ProductDto product) {
		List<ProductTitle> mainTitleList = product.getMainTitleList();
		ProductTitle title = null;
		EbayListing ebay = null;
		for (ProductTitle productTitle : mainTitleList) {
			ebay = new EbayListing();
			//ebay.setTitleId(Long.valueOf(productTitle.getTitleId()));
			ebay.setTitle(productTitle.getContent());
			
			List<EbayListing> ebayListing = this.ebayListingService.findSimpleList(ebay);
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
				List<EbayListing> ebayListing = this.ebayListingService.findList(ebay);
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
}