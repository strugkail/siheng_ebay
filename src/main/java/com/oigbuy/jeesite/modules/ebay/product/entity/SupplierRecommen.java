package com.oigbuy.jeesite.modules.ebay.product.entity;

import java.util.Date;

import com.oigbuy.jeesite.common.persistence.DataEntity;

/**
 * 商品推荐
 * @author yuxiang.xiong
 * 2017年9月4日 上午10:07:10
 */
public class SupplierRecommen extends DataEntity<SupplierRecommen> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
     
	private String imgUrl;     // 上传之后的图片地址
	private String supplierName;   // 供应商名称
	private String productAddr;   // 产品链接
	private String recomName;   //推荐人
	private String recomTime; //推荐时间
	private String productName;   // 产品名称
	private String dateStart;  //采集开始时间
	private String dateEnd;  // 采集结束时间
	private String length;
	private String wide;
	private String high;
	private String weight;
	private String price;   //单价
	private String packageMethod;  // 包装方式
	private Date createTime;
	private String createName;
	private Date updateTime;
	private String updateName;
	private String remark;
	private String itemId;     //竞争商品ID
	
	private String flag;   //新增或者编辑的标识
	private String type;   //0.保存  1.开发  2.放弃
	
	private String productImgId;   //产品图片id
	private String newimg; // 不带前缀图片地址
	
	/***
	 * 站点  非持久化字段 页面查询
	 */
	private String siteId;
	private String siteName;
	/***
	 * 销售类型   虚拟海外仓，海外仓  非持久化字段 页面查询
	 */
	private String saleTypeId;
	
	/***
	 * 销售组ID   非持久化字段 页面查询
	 */
	private String saleGroupId;
	
	/***
	 * 步骤状态   非持久化字段 页面查询
	 */
	private String status;
	
	/***
	 * ebay 链接   非持久化字段 页面查询,对应的其实就是 竞品采集的 product_url 参考链接
	 */
	private String ebayUrl;
	
	
	
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
	public String getSaleGroupId() {
		return saleGroupId;
	}
	public void setSaleGroupId(String saleGroupId) {
		this.saleGroupId = saleGroupId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEbayUrl() {
		return ebayUrl;
	}
	public void setEbayUrl(String ebayUrl) {
		this.ebayUrl = ebayUrl;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getRecomName() {
		return recomName;
	}
	public void setRecomName(String recomName) {
		this.recomName = recomName;
	}
	public String getRecomTime() {
		return recomTime;
	}
	public void setRecomTime(String recomTime) {
		this.recomTime = recomTime;
	}
	public String getProductAddr() {
		return productAddr;
	}
	public void setProductAddr(String productAddr) {
		this.productAddr = productAddr;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
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
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getWide() {
		return wide;
	}
	public void setWide(String wide) {
		this.wide = wide;
	}
	public String getHigh() {
		return high;
	}
	public void setHigh(String high) {
		this.high = high;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPackageMethod() {
		return packageMethod;
	}
	public void setPackageMethod(String packageMethod) {
		this.packageMethod = packageMethod;
	}
	/**
	 * 新增或者编辑的标识
	 * @return
	 */
	public String getFlag() {
		return flag;
	}
	/**
	 * 新增或者编辑的标识
	 * @return
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateName() {
		return updateName;
	}
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getProductImgId() {
		return productImgId;
	}
	public void setProductImgId(String productImgId) {
		this.productImgId = productImgId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getNewimg() {
		return newimg;
	}
	public void setNewimg(String newimg) {
		this.newimg = newimg;
	}
	
	
	
}
