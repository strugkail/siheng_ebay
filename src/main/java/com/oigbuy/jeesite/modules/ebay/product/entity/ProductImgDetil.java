package com.oigbuy.jeesite.modules.ebay.product.entity;

public class ProductImgDetil {

	
	private String id;
	private String url;
	
	public ProductImgDetil(String id, String url) {
		super();
		this.id = id;
		this.url = url;
	}
	public ProductImgDetil() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "ProductImgDetil [id=" + id + ", url=" + url + "]";
	}
	
	
}
