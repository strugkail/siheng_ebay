package com.oigbuy.jeesite.common.process.entity;

import java.util.Date;
import java.util.List;

import com.oigbuy.jeesite.common.persistence.DataEntity;


/**
 * 存业务表数据的实体
 * @author yuxiang.xiong
 * 2017年9月20日 上午10:19:07
 */
public class EbayProcessBusiness extends DataEntity<EbayProcessBusiness> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String procInsId;    //流程实例id
	private String imgUrl;  // 图片链接
	private String siteId;   //站点
	private String siteName; //站点名称
	private String saleType; //销售类型
	private String productUrl;  //商品链接
	private String productName;  // 产品(商品)名称
	private String source;   // 来源
	private String createName;  // 开发人
	private Date createTime;  // 创建时间
	private Integer status;  //状态
	private String businessId;     //业务表id
	private String sysParentCode;
	private String itemId;   //竞争商品ID
	private Date dateStart;  //开始时间
	private Date dateEnd;  // 结束时间
	private String shopName;  //店铺名称
	private String title;   // 刊登标题
	private String parentCode; //母代码
	private String upcEan;  
	private String flag;   //流程判断标识
	private List<String> procInsIdlist; 
	private Integer productFlag;   //产品标识  默认 0 暂时放弃开发为1
	private String saleGroupId;	//销售组ID
	private String sign;         // 1 已签收 2 未签收
	private String saleGroupName; //销售组名称
	
	public String getProcInsId() {
		return procInsId;
	}
	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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
	public String getSaleType() {
		return saleType;
	}
	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}
	public String getProductUrl() {
		return productUrl;
	}
	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getSysParentCode() {
		return sysParentCode;
	}
	public void setSysParentCode(String sysParentCode) {
		this.sysParentCode = sysParentCode;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public Date getDateStart() {
		return dateStart;
	}
	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}
	public Date getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getUpcEan() {
		return upcEan;
	}
	public void setUpcEan(String upcEan) {
		this.upcEan = upcEan;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public List<String> getProcInsIdlist() {
		return procInsIdlist;
	}
	public void setProcInsIdlist(List<String> procInsIdlist) {
		this.procInsIdlist = procInsIdlist;
	}
	public Integer getProductFlag() {
		return productFlag;
	}
	public void setProductFlag(Integer productFlag) {
		this.productFlag = productFlag;
	}
	public String getSaleGroupId() {
		return saleGroupId;
	}
	public void setSaleGroupId(String saleGroupId) {
		this.saleGroupId = saleGroupId;
	}
	public String getSaleGroupName() {
		return saleGroupName;
	}
	public void setSaleGroupName(String saleGroupName) {
		this.saleGroupName = saleGroupName;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
}
