package com.oigbuy.jeesite.modules.ebay.template.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.oigbuy.jeesite.common.persistence.DataEntity;

/**
 * 退货实体
 * @author yuxiang.xiong
 * 2017年9月5日 下午5:04:45
 */
public class ReturnPurchase extends DataEntity<ReturnPurchase> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String templateName;    // 模板名称
	private String refundPolice;  // 退款策略
	private String reciveReturn;   //接收退货
	private String returnMode;    //退货方式
	private String reciveReturnPeriod; //接收退货期限
	private String holidayReturn;  // holiday return  不为空表示延长 true  
	private String returnPostage;   // 退货邮费承担
	private String restockingFeeValue;  //restockingFeeValue
	private String returnPolicy;  //退货说明
	private Date createTime;  //创建时间
	private String createName;
	private Date updateTime;
	private String updateName;
	private String flag;   //新增、编辑标识
	private String saleType;  //销售类型
	private String siteId; // 站点
	
	
	
	
	public ReturnPurchase() {
		super();
	}
	public ReturnPurchase(String saleType, String siteId) {
		super();
		this.saleType = saleType;
		this.siteId = siteId;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	@Length(min=0, max=255, message="ebay退款策略必须介于 0 和 255 之间")
	public String getRefundPolice() {
		return refundPolice;
	}
	public void setRefundPolice(String refundPolice) {
		this.refundPolice = refundPolice;
	}
	public String getReciveReturn() {
		return reciveReturn;
	}
	public void setReciveReturn(String reciveReturn) {
		this.reciveReturn = reciveReturn;
	}
	public String getReturnMode() {
		return returnMode;
	}
	public void setReturnMode(String returnMode) {
		this.returnMode = returnMode;
	}
	public String getReciveReturnPeriod() {
		return reciveReturnPeriod;
	}
	public void setReciveReturnPeriod(String reciveReturnPeriod) {
		this.reciveReturnPeriod = reciveReturnPeriod;
	}
	public String getHolidayReturn() {
		return holidayReturn;
	}
	public void setHolidayReturn(String holidayReturn) {
		this.holidayReturn = holidayReturn;
	}
	public String getReturnPostage() {
		return returnPostage;
	}
	public void setReturnPostage(String returnPostage) {
		this.returnPostage = returnPostage;
	}
	public String getRestockingFeeValue() {
		return restockingFeeValue;
	}
	public void setRestockingFeeValue(String restockingFeeValue) {
		this.restockingFeeValue = restockingFeeValue;
	}
	public String getReturnPolicy() {
		return returnPolicy;
	}
	public void setReturnPolicy(String returnPolicy) {
		this.returnPolicy = returnPolicy;
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
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getSaleType() {
		return saleType;
	}
	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

}
