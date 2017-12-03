package com.oigbuy.jeesite.common.utils;

/**
 * 参数接口定义
 * 
 * */
public interface Function<T,E> {

	public T callback(E e);
}
