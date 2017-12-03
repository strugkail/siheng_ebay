package com.oigbuy.jeesite.modules.ebay.product.dto;

/***
 * 翻译对应的  dto
 * 
 * @author bill.xu
 *
 */
public class TranslateDto {

	private String title;//标题
	private String keyWord1;		// 刊登标题的必须 关键字
	private String otherKeyWord1;		// 刊登标题的  其他关键字
	private String subtitle;		// 刊登副标题
	private String keyWord2;		// 刊登副标题的必须 关键字
	private String otherKeyWord2;		// 刊登副标题的  其他关键字
	
//	private String names;  //自定义 分类下的 item specifics name 
//	
//	private String values; //自定义 分类下的 item specifics value 
//	
//	private String remarks; //自定义 分类下的 item specifics remark 
	
	//翻译后的语言 
	
	private String titleT;//标题
	private String keyWord1T;		// 刊登标题的必须 关键字
	private String otherKeyWord1T;		// 刊登标题的  其他关键字
	private String subtitleT;		// 刊登副标题
	private String keyWord2T;		// 刊登副标题的必须 关键字
	private String otherKeyWord2T;		// 刊登副标题的  其他关键字
//	private String[] namesT;  //自定义 分类下的 item specifics name 
//	private String[] valuesT; //自定义 分类下的 item specifics value 
//	private String[] remarksT; //自定义 分类下的 item specifics remark 
	
	
	
	public String getTitleT() {
		return titleT;
	}

	public void setTitleT(String titleT) {
		this.titleT = titleT;
	}

	public String getKeyWord1T() {
		return keyWord1T;
	}

	public void setKeyWord1T(String keyWord1T) {
		this.keyWord1T = keyWord1T;
	}

	public String getOtherKeyWord1T() {
		return otherKeyWord1T;
	}

	public void setOtherKeyWord1T(String otherKeyWord1T) {
		this.otherKeyWord1T = otherKeyWord1T;
	}

	public String getSubtitleT() {
		return subtitleT;
	}

	public void setSubtitleT(String subtitleT) {
		this.subtitleT = subtitleT;
	}

	public String getKeyWord2T() {
		return keyWord2T;
	}

	public void setKeyWord2T(String keyWord2T) {
		this.keyWord2T = keyWord2T;
	}

	public String getOtherKeyWord2T() {
		return otherKeyWord2T;
	}

	public void setOtherKeyWord2T(String otherKeyWord2T) {
		this.otherKeyWord2T = otherKeyWord2T;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKeyWord1() {
		return keyWord1;
	}

	public void setKeyWord1(String keyWord1) {
		this.keyWord1 = keyWord1;
	}

	public String getOtherKeyWord1() {
		return otherKeyWord1;
	}

	public void setOtherKeyWord1(String otherKeyWord1) {
		this.otherKeyWord1 = otherKeyWord1;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getKeyWord2() {
		return keyWord2;
	}

	public void setKeyWord2(String keyWord2) {
		this.keyWord2 = keyWord2;
	}

	public String getOtherKeyWord2() {
		return otherKeyWord2;
	}

	public void setOtherKeyWord2(String otherKeyWord2) {
		this.otherKeyWord2 = otherKeyWord2;
	}

}
