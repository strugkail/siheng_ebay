//package com.oigbuy.jeesite.modules.ebay.listing.dto.req;
//
//import java.io.Serializable;
///**
// * 买家选项
// * 
// * @author jalyn.zhang
// *
// */
//public class BuyerDetailDto implements Serializable {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	
//	private String buyerModelName;
//	
//	private boolean ishavePaypalAccount;//买家必须拥有	Paypal账户
//	
//	private Integer maxViolations;//最大违反政策次数
//	
//	private String violationPeriod;//违反周期
//	
//	private Integer maxUnPaiedCount;//未付款最大次数
//	
//	private String unPaidPeriod;//未付款周期  TODO  对应的都是天数吗？
//	
//	private Integer saleCounts;//限制拍卖次数
//	
//	private Integer buyerBadScore;//买家差评限制（-1 -2）
//	
//
//	public String getBuyerModelName() {
//		return buyerModelName;
//	}
//
//	public void setBuyerModelName(String buyerModelName) {
//		this.buyerModelName = buyerModelName;
//	}
//
//	public boolean isIshavePaypalAccount() {
//		return ishavePaypalAccount;
//	}
//
//	public void setIshavePaypalAccount(boolean ishavePaypalAccount) {
//		this.ishavePaypalAccount = ishavePaypalAccount;
//	}
//
//
//	public String getViolationPeriod() {
//		return violationPeriod;
//	}
//
//	public void setViolationPeriod(String violationPeriod) {
//		this.violationPeriod = violationPeriod;
//	}
//
//
//	public String getUnPaidPeriod() {
//		return unPaidPeriod;
//	}
//
//	public void setUnPaidPeriod(String unPaidPeriod) {
//		this.unPaidPeriod = unPaidPeriod;
//	}
//
//
//	public Integer getBuyerBadScore() {
//		return buyerBadScore;
//	}
//
//	public void setBuyerBadScore(Integer buyerBadScore) {
//		this.buyerBadScore = buyerBadScore;
//	}
//
//	public Integer getMaxViolations() {
//		return maxViolations;
//	}
//
//	public void setMaxViolations(Integer maxViolations) {
//		this.maxViolations = maxViolations;
//	}
//
//	public Integer getMaxUnPaiedCount() {
//		return maxUnPaiedCount;
//	}
//
//	public void setMaxUnPaiedCount(Integer maxUnPaiedCount) {
//		this.maxUnPaiedCount = maxUnPaiedCount;
//	}
//
//	public Integer getSaleCounts() {
//		return saleCounts;
//	}
//
//	public void setSaleCounts(Integer saleCounts) {
//		this.saleCounts = saleCounts;
//	}
//
//	
//	
//
//}
