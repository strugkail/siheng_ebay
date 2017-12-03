package com.oigbuy.jeesite.modules.ebay.productDevelop.entity;

import com.oigbuy.jeesite.common.persistence.DataEntity;

import java.util.Date;

public class ProductToBeDevelop extends DataEntity<ProductToBeDevelop>{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String collectId;		// 采集产品Id
    private String siteId;		// 站点ID
    private String siteName;		// 站点名称
    private String saleTypeId;		// 销售类型
    private String saleGroupId;		// 销售组
    private String productName;		// 商品名称
    private String imgUrl;			// 图片Url
    private String imgLink;			// 图片链接
    private String productUrl;		// 参考链接
    private String productNumber;		// 采购量
    private String pictureNumber;		// 制图数量(默认为4张)
    private String remark;		// 销售备注
    private String operator;		// 操作人
    private String createTime;		// 操作时间
    private String status;		// 采集状态
    private String recomId;		// 供应商推荐ID
    private String dateStart;
    private String dateEnd;
    private String itemId;//竞争商品id
    private String productId; // 产品id
    
    private String sign;         // 1 已签收 2 未签收

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public ProductToBeDevelop(String collectId){this.collectId = collectId;}
    public ProductToBeDevelop(){}
    public ProductToBeDevelop(String productId,String collectId){this.collectId=collectId;this.productId=productId;}
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }



    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSaleTypeId() {
        return saleTypeId;
    }

    public void setSaleTypeId(String saleTypeId) {
        this.saleTypeId = saleTypeId;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getSaleGroupId() {
        return saleGroupId;
    }

    public void setSaleGroupId(String saleGroupId) {
        this.saleGroupId = saleGroupId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getPictureNumber() {
        return pictureNumber;
    }

    public void setPictureNumber(String pictureNumber) {
        this.pictureNumber = pictureNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRecomId() {
        return recomId;
    }

    public void setRecomId(String recomId) {
        this.recomId = recomId;
    }

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
    
}
