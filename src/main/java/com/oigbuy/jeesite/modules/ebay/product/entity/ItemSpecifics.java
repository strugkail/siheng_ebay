/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.product.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.oigbuy.jeesite.common.persistence.DataEntity;

/**
 * 分类细节Entity
 * @author bill.xu
 * @version 2017-09-22
 */
public class ItemSpecifics extends DataEntity<ItemSpecifics> {
	
	private static final long serialVersionUID = 1L;
	private Long productId;		// 产品id
	private Long categoryId;		// 分类id
	private String name;		// 分类细节名称
	private String value;		// 分类细节名称对应的分类值
	private String remark;		// 分类细节名称对应的备注
	
	private String type; //0 表示是自定义的   1 表示 是分类下的
	
	private List<String> values;//有多个对应的值 
	
	/***
	 * 表示该 item Specifics 的 值 是如何操作 
	 * AUTOMATIC   --->
	 * MANUAL  --->
	 * PREFILLED  --->
	 * SELECTION_ONLY ---> 只可以进行选择
	 * FREE_TEXT  --->  进行自主输入
	 * CUSTOM_CODE  --->
	 */
	private String selectMode; //  SELECTION_ONLY("SelectionOnly"),  FREE_TEXT("FreeText"),  CUSTOM_CODE("CustomCode");
	
	private List<String> selectValues;//选中的值（多个）
	
	private String selectValue;//选中的值
	
	
	public ItemSpecifics(String id,Long productId, Long categoryId, String name,
			String value, String remark, String type) {
		super();
		this.id=id;
		this.productId = productId;
		this.categoryId = categoryId;
		this.name = name;
		this.value = value;
		this.remark = remark;
		this.type = type;
	}

	public ItemSpecifics(Long productId, Long categoryId) {
		super();
		this.productId = productId;
		this.categoryId = categoryId;
	}

	public ItemSpecifics() {
		super();
	}

	public ItemSpecifics(String id){
		super(id);
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	@Length(min=0, max=200, message="分类细节名称长度必须介于 0 和 200 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=200, message="分类细节名称对应的分类值长度必须介于 0 和 200 之间")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Length(min=0, max=255, message="分类细节名称对应的备注长度必须介于 0 和 255 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

	public String getSelectMode() {
		return selectMode;
	}

	public void setSelectMode(String selectMode) {
		this.selectMode = selectMode;
	}

	public List<String> getSelectValues() {
		return selectValues;
	}

	public void setSelectValues(List<String> selectValues) {
		this.selectValues = selectValues;
	}

	public String getSelectValue() {
		return selectValue;
	}

	public void setSelectValue(String selectValue) {
		this.selectValue = selectValue;
	}

	
}