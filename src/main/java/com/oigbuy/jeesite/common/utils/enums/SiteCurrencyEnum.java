package com.oigbuy.jeesite.common.utils.enums;

import java.math.BigDecimal;

import com.oigbuy.jeesite.common.utils.translate.LangueageSupport;


/**
 * 站点和货币相关枚举
 * 
 * @author bill.xu
 *
 */
public enum SiteCurrencyEnum {

	/**美国 对应美元*/
	US("US","USD",LangueageSupport.English,BigDecimal.valueOf(0.99d)),
	
	/**英国 对应英镑*/
	UK("UK","GBP",LangueageSupport.English,BigDecimal.valueOf(0.99d)),
	
	/**德国  欧元 */
	DE("DE","EUR",LangueageSupport.German,BigDecimal.valueOf(1d)),
	
	/**法国   法郎*/
	FR("FR","EUR",LangueageSupport.French,BigDecimal.valueOf(1d)),
	
	/**西班牙  对应英镑*/
	ES("ES","EUR",LangueageSupport.English,BigDecimal.valueOf(1d)),
	
	/**意大利  对应英镑*/
	IT("IT","EUR",LangueageSupport.English,BigDecimal.valueOf(1d)),
	
	/**澳大利亚*/
	AU("AU","AUD",LangueageSupport.English,BigDecimal.valueOf(1d)),
	
	/**加拿大*/
	CAN("CAN","CAD",LangueageSupport.English,BigDecimal.valueOf(1d)),
	
	/**中国*/
	CHN("CHN","CNY",LangueageSupport.ChineseOrSimplified,BigDecimal.valueOf(1d));
	
	
	private String siteName;
	
	private String currencyName;
	
	private String language;
	
	/**
	 * 站点 对应的最低刊登价格 
	 * 美国站点 0.99 美金
	 * 英国 0.99 英镑
	 * 德国 1 欧元
	 * 法国 1 欧元
	 * 西班牙 1 欧元
	 * 意大利 1 欧元
	 * 澳洲 1 澳元
	 * 
	 */
	private BigDecimal lowestPrice;
	
	

	public BigDecimal getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(BigDecimal lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	private SiteCurrencyEnum(String siteName, String currencyName,String language,BigDecimal lowestPrice) {
		this.siteName = siteName;
		this.currencyName = currencyName;
		this.language = language;
		this.lowestPrice=lowestPrice;
	}
	
	public static String getSiteName(String currencyName) {
		for (SiteCurrencyEnum c : SiteCurrencyEnum.values()) {
			if (c.currencyName.equalsIgnoreCase(currencyName)) {
				return c.siteName;
			}
		}
		return null;
	}


	public static String getCurrencyName(String siteName) {
		for (SiteCurrencyEnum c : SiteCurrencyEnum.values()) {
			if (c.siteName.equalsIgnoreCase(siteName)) {
				return c.currencyName;
			}
		}
		return null;
	}
	
	public static String getLanguageBySite(String siteName) {
		for (SiteCurrencyEnum c : SiteCurrencyEnum.values()) {
			if (c.siteName.equalsIgnoreCase(siteName)) {
				return c.language;
			}
		}
		return null;
	}
	
	/***
	 * 通过站点获取 站点对应的刊登最低售价 
	 * 
	 * @param siteName
	 * @return
	 */
	public static BigDecimal getLowestPriceBySite(String siteName) {
		for (SiteCurrencyEnum c : SiteCurrencyEnum.values()) {
			if (c.siteName.equalsIgnoreCase(siteName)) {
				return c.lowestPrice;
			}
		}
		return BigDecimal.ZERO;
	}
	
}
