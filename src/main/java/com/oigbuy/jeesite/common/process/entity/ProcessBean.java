package com.oigbuy.jeesite.common.process.entity;

import java.util.Map;

import com.oigbuy.jeesite.common.persistence.ActEntity;

public class ProcessBean <T> extends ActEntity<T>{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5346173892569391878L;
	
	private Map<String, Object> vars;
	
	private T data;

	public Map<String, Object> getVars() {
		return vars;
	}

	public void setVars(Map<String, Object> vars) {
		this.vars = vars;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	
}
