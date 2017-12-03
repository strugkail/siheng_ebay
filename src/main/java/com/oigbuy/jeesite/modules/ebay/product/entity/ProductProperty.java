package com.oigbuy.jeesite.modules.ebay.product.entity;

import com.oigbuy.jeesite.common.persistence.DataEntity;


public class ProductProperty extends DataEntity<ProductProperty> {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4772414267101914191L;
	private String propertyCode;
    private String productId;
    private String propertyName;
    private String propertyValue;
    private String codeManagerId;
    
    
	private String translateName;
	private String translateValue;
	
	
	
    public ProductProperty(String productId, String codeManagerId) {
		super();
		this.productId = productId;
		this.codeManagerId = codeManagerId;
	}
    
    public ProductProperty(String productId) {
		super();
		this.productId = productId;
	}
	public String getTranslateValue() {
		return translateValue;
	}
	public void setTranslateValue(String translateValue) {
		this.translateValue = translateValue;
	}
	public String getTranslateName() {
		return translateName;
	}
	public ProductProperty(String id,String propertyCode,String productId,String propertyName,String propertyValue,String codeManagerId){
        this.id = id;
        this.propertyCode = propertyCode;
        this.productId = productId;
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
        this.codeManagerId = codeManagerId;
    }
    public ProductProperty(){}
    public ProductProperty(String propertyCode,String propertyName,String propertyValue){
        this.propertyCode = propertyCode;
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
    }
    public String getCodeManagerId() {
        return codeManagerId;
    }

    public void setCodeManagerId(String codeManagerId) {
        this.codeManagerId = codeManagerId;
    }


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public String getPropertyCode() {
        return propertyCode;
    }

    public void setPropertyCode(String propertyCode) {
        this.propertyCode = propertyCode;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
    
	public void setTranslateName(String translateName) {
		// TODO Auto-generated method stub
		this.translateName=translateName;
	}

}
