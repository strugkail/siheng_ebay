package com.oigbuy.jeesite.modules.ebay.logistics.entity;

import java.io.Serializable;
import java.util.List;

public class EbayInterfaceParam2 implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private List<ShippingTypeParam> result;
	private String code;
	private String flag;
	private String message;
	
	public List<ShippingTypeParam> getResult() {
		return result;
	}
	public void setResult(List<ShippingTypeParam> result) {
		this.result = result;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
