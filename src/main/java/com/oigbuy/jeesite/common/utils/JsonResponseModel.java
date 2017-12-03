package com.oigbuy.jeesite.common.utils;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/***
 * 后台向客户端相应的 json model
 * 
 * @author bill.xu
 *
 */
public class JsonResponseModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -873693755384635520L;

	/***
	 * 操作成功信息
	 */
	 public  static final String SUCCESS_MSG="操作成功";

 	/***
 	 * 操作失败信息
 	 */
      public  static final String FAIL_MSG="操作失败";
	
	/**
	 * 给客户端响应成功还是失败
	 * true
	 * false
	 */
	private boolean result;
	
	/**
	 * 消息体,操作成功提示文本,或者操作失败提示文本
	 */
	private String msg;
	
	
	/**
	 * 请求成功的一些 data 数据
	 */
	private Object data;
	
	

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	
	
	@Override
	public String toString() {
		return "JsonResponseModel [result=" + result + ", msg=" + msg
				+ ", data=" + data + "]";
	}

	/***
	 *  操作成功的时候要用的方法
	 * 
	 * @param str  信息提示
	 * @param data  数据
	 * @return
	 */
	public String Success(String str,Object data){
		this.setMsg(str);
		this.setResult(true);
		this.setData(data);
		return JSON.toJSONString(this);
	}
	
	/***
	 * 操作成功的时候要用的方法
	 * 
	 * @param str  信息提示
	 * @return
	 */
	public String Success(String str){
		this.setMsg(str);
		this.setResult(true);
		return JSON.toJSONString(this);
	}
	
	/***
	 * 操作失败的时候要用的方法
	 * @param str  提示的返回信息
	 * @return
	 */
	public String Fail(String str){
		this.setMsg(str);
		this.setResult(false);
		return JSON.toJSONString(this);
	}
	
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
	public JsonResponseModel success(Object data){
		this.setData(data);
		this.setMsg(SUCCESS_MSG);
        this.setResult(true);		
		return this;
	}
	
	public JsonResponseModel fail(String msg){
		this.setMsg(msg);
		this.setResult(false);
		return this;
	}
	
	
	
}
