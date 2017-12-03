package com.oigbuy.jeesite.modules.ebay.platformAndsite.entity;

/**
 * Created by tanwei.tan on 2017/12/1.
 */

import com.sho.tool.build.title.KeyWordBean;

/**
 * 关键字实体
 */
public class KeyWord {
    private String productId;
    private String keyWord;
    private String keyWordUrl;
    private String siteId;

    public String getKeyWord() {
        return keyWord;
    }

    public String getKeyWordUrl() {
        return keyWordUrl;
    }

    public String getProductId() {
        return productId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public void setKeyWordUrl(String keyWordUrl) {
        this.keyWordUrl = keyWordUrl;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }
}
