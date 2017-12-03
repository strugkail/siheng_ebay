package com.oigbuy.jeesite.modules.ebay.template.entity;

import java.util.Date;

import com.oigbuy.jeesite.common.persistence.DataEntity;


/**
 * 买家限制
 * @author yuxiang.xiong
 * 2017年9月6日 上午10:29:51
 */
public class BuyerRestriction extends DataEntity<BuyerRestriction>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String templateName;  // 模板名称
	private String paypalAccount;  // Paypal账户  是否要求有paypal 账户（1 表示 要求，0 表示不要求 ）
	private String violationFreq;   //   违反次数
	private String breachTime;        //     违反时段 
	private String noPaymentTimes;    //       订单未付款次数
	private String orderBreachTime;   //      订单违反时段
	private String limitAuctionsTimes;  //    限制拍卖次数
	private String scoreRestriction;   //     差评评分限制
	private String createName;
	private Date createTime;
	private String updateName;
	private Date updateTime;
	private String flag;   //新增、编辑标识
	private String siteId;
	
	
	
	public BuyerRestriction() {
		super();
	}
	public BuyerRestriction(String siteId) {
		super();
		this.siteId = siteId;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getPaypalAccount() {
		return paypalAccount;
	}
	public void setPaypalAccount(String paypalAccount) {
		this.paypalAccount = paypalAccount;
	}
	public String getViolationFreq() {
		return violationFreq;
	}
	public void setViolationFreq(String violationFreq) {
		this.violationFreq = violationFreq;
	}
	public String getBreachTime() {
		return breachTime;
	}
	public void setBreachTime(String breachTime) {
		this.breachTime = breachTime;
	}
	public String getNoPaymentTimes() {
		return noPaymentTimes;
	}
	public void setNoPaymentTimes(String noPaymentTimes) {
		this.noPaymentTimes = noPaymentTimes;
	}
	public String getOrderBreachTime() {
		return orderBreachTime;
	}
	public void setOrderBreachTime(String orderBreachTime) {
		this.orderBreachTime = orderBreachTime;
	}
	public String getLimitAuctionsTimes() {
		return limitAuctionsTimes;
	}
	public void setLimitAuctionsTimes(String limitAuctionsTimes) {
		this.limitAuctionsTimes = limitAuctionsTimes;
	}
	public String getScoreRestriction() {
		return scoreRestriction;
	}
	public void setScoreRestriction(String scoreRestriction) {
		this.scoreRestriction = scoreRestriction;
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
	public String getUpdateName() {
		return updateName;
	}
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	
}
