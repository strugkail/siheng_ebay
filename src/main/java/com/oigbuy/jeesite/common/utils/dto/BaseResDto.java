package com.oigbuy.jeesite.common.utils.dto;

import java.io.Serializable;


/**
 * 
 * @author Administrator
 *
 */
public class BaseResDto  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4563601666762095955L;


	private String code;
	
	
	private String message;
	
	
	private Object data;


	
	public static final String SUCCESS_CODE="200";
	
	public static final String FAIL_CODE="400";
	
	public  BaseResDto  fail(String message){
		this.setCode(FAIL_CODE);
		this.setMessage(message);
		return this;		
	}
	
	public  BaseResDto  success(Object data){
		this.setCode(SUCCESS_CODE);
		this.setData(data);
		return this;		
	}
	
	
	
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


	public Object getData() {
		return data;
	}


	public void setData(Object data) {
		this.data = data;
	}

	
	
	
}
