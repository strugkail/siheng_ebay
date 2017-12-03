package com.oigbuy.jeesite.modules.ebay.platformAndsite.entity;

/**
 * Created by tanwei.tan on 2017/12/1.
 */
public class PlatFormSiteByEbay {
    private Long platformId;		// 平台Id
    private String siteName;		// 站点名称
    private String siteShortName;		// 站点名称缩写
    private String Id;//站点Id

    public PlatFormSiteByEbay() {
        super();
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteShortName() {
        return siteShortName;
    }

    public void setSiteShortName(String siteShortName) {
        this.siteShortName = siteShortName;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Long getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Long platformId) {
        this.platformId = platformId;
    }
}
