/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.listing.entity;

import org.hibernate.validator.constraints.Length;

import com.oigbuy.jeesite.common.persistence.DataEntity;

/**
 * ebay listing 图片Entity
 * @author bill.xu
 * @version 2017-09-22
 */
public class EbayListingImg extends DataEntity<EbayListingImg> {
	
	private static final long serialVersionUID = 1L;
	private Long listingId;		// listing id
	private Long codeManagerId;		// code manager id
	private Long imageId;		// 图片id，主要做listing 图片是否重复校验
	private String imageType;		// 图片类型
	private String imageUrl;		// 上传的本地图片
	private String transferUrl;		// 中转服务的url（如，wish平台把图片上传到ebay中）
	private String platformUrl;		// 上传ebay之后的图片链接
	private String indexSeq;		// 排序序号
	private String uploadInfo;		// 上传ebay的反馈信息，出错或则异常
	private Long imgPlatformId;  //产品平台图片关联表的id
	
	
	
	
	
	
	public EbayListingImg(String id,Long listingId, Long imageId, String imageType,String imageUrl) {
		super();
		this.id=id;
		this.listingId = listingId;
		this.imageId = imageId;
		this.imageType = imageType;
		this.imageUrl = imageUrl;
	}

	public EbayListingImg(Long listingId, String imageType) {
		super();
		this.listingId = listingId;
		this.imageType = imageType;
	}

	public EbayListingImg(Long listingId,Long codeManagerId ,String imageType) {
		super();
		this.listingId = listingId;
		this.codeManagerId = codeManagerId;
		this.imageType = imageType;
	}

	public EbayListingImg() {
		super();
	}

	public EbayListingImg(String id){
		super(id);
	}

	public Long getListingId() {
		return listingId;
	}

	public void setListingId(Long listingId) {
		this.listingId = listingId;
	}
	
	public Long getCodeManagerId() {
		return codeManagerId;
	}

	public void setCodeManagerId(Long codeManagerId) {
		this.codeManagerId = codeManagerId;
	}
	
	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}
	
	@Length(min=0, max=4, message="图片类型长度必须介于 0 和 4 之间")
	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	
	@Length(min=0, max=300, message="上传的本地图片长度必须介于 0 和 300 之间")
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	@Length(min=0, max=300, message="中转服务的url（如，wish平台把图片上传到ebay中）长度必须介于 0 和 300 之间")
	public String getTransferUrl() {
		return transferUrl;
	}

	public void setTransferUrl(String transferUrl) {
		this.transferUrl = transferUrl;
	}
	
	@Length(min=0, max=300, message="上传ebay之后的图片链接长度必须介于 0 和 300 之间")
	public String getPlatformUrl() {
		return platformUrl;
	}

	public void setPlatformUrl(String platformUrl) {
		this.platformUrl = platformUrl;
	}
	
	@Length(min=0, max=8, message="排序序号长度必须介于 0 和 8 之间")
	public String getIndexSeq() {
		return indexSeq;
	}

	public void setIndexSeq(String indexSeq) {
		this.indexSeq = indexSeq;
	}
	
	@Length(min=0, max=300, message="上传ebay的反馈信息，出错或则异常长度必须介于 0 和 300 之间")
	public String getUploadInfo() {
		return uploadInfo;
	}

	public void setUploadInfo(String uploadInfo) {
		this.uploadInfo = uploadInfo;
	}

	public Long getImgPlatformId() {
		return imgPlatformId;
	}

	public void setImgPlatformId(Long imgPlatformId) {
		this.imgPlatformId = imgPlatformId;
	}
	
}