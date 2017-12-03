package com.oigbuy.jeesite.modules.ebay.product.entity;

import com.oigbuy.jeesite.common.persistence.DataEntity;

public class ProductPropertyValue extends DataEntity {
private String propertyValue;
private String propertyId;

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }
}
