/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.ebayPaypal.entity;

import com.oigbuy.jeesite.common.persistence.DataEntity;

/**
 * ebay最小费率Entity
 * @author strugkail
 * @version 2017-09-25
 */
public class EbayPaypal extends DataEntity<EbayPaypal> {
	
	private static final long serialVersionUID = 1L;
	private String account;// 站点简称
	private Double paypalDiscount;// 大额费率
	private Double paypalEachCost;// 额外费
	private Double smallpaypalDiscount;// 小额费率
	private Double smallpaypalEachCost;// 小额费
	private Double boundaries;// 临界值
	public EbayPaypal(){};
	public EbayPaypal(String account){
		this.account = account;
	}
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Double getPaypalDiscount() {
		return paypalDiscount;
	}

	public void setPaypalDiscount(Double paypalDiscount) {
		this.paypalDiscount = paypalDiscount;
	}

	public Double getPaypalEachCost() {
		return paypalEachCost;
	}

	public void setPaypalEachCost(Double paypalEachCost) {
		this.paypalEachCost = paypalEachCost;
	}

	public Double getSmallpaypalDiscount() {
		return smallpaypalDiscount;
	}

	public void setSmallpaypalDiscount(Double smallpaypalDiscount) {
		this.smallpaypalDiscount = smallpaypalDiscount;
	}

	public Double getSmallpaypalEachCost() {
		return smallpaypalEachCost;
	}

	public void setSmallpaypalEachCost(Double smallpaypalEachCost) {
		this.smallpaypalEachCost = smallpaypalEachCost;
	}

	public Double getBoundaries() {
		return boundaries;
	}

	public void setBoundaries(Double boundaries) {
		this.boundaries = boundaries;
	}
}