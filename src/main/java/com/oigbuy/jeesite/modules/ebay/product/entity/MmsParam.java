package com.oigbuy.jeesite.modules.ebay.product.entity;

import java.io.Serializable;

import com.oigbuy.jeesite.common.config.Global;

public class MmsParam implements Serializable {
	private static final long serialVersionUID = 1L;

	private String code;
	private String message;
	private Data data;

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	public MmsParam() {
		super();
	}
	public MmsParam(String code, String message, Data data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public MmsParam fail(String code, String message, Data data) {
		this.setCode(code);
		this.setData(data);
		this.setMessage(message);
		return this;
	}

	public MmsParam success(Data data) {
		this.setCode(Global.SUCCESS);
		this.setData(data);
		this.setMessage(Global.SUCCESS_MESSAGE);
		return this;
	}

}
