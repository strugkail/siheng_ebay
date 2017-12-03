package com.oigbuy.jeesite.modules.ebay.logistics.entity;

import java.io.Serializable;
import java.util.List;

public class EbayInterfaceParam implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private List<SendToCountryParam> result;
	private String code;
	private String flag;
	private String message;
	
	public List<SendToCountryParam> getResult() {
		return result;
	}
	public void setResult(List<SendToCountryParam> result) {
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
