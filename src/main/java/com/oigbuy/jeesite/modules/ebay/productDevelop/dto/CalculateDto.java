package com.oigbuy.jeesite.modules.ebay.productDevelop.dto;

public class CalculateDto {
    private String siteName;
    private Double ebaySellPrice;
    private String categoryName;
    private String subCategoryName;
    private String ebayfee;
    private String paypalfee;
    private Double postage;
    private Double financefee;
    private Double lossfee;
    private Double exch_rate;
    private String grossProfit;
    private String grossProfitRate;
    private String note;
    private String saleType;
    private Double commodityCost;
    private Double weight;
    private Double packagefee;
    private Double dealfee;
    private String title;
    private String description;
    private Integer quantitySold;


    public CalculateDto(){}
    public CalculateDto(String categoryName,String siteName){
        this.categoryName = categoryName;
        this.siteName = siteName;
    }
    public Integer getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(Integer quantitySold) {
        this.quantitySold = quantitySold;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getCommodityCost() {
        return commodityCost;
    }

    public void setCommodityCost(Double commodityCost) {
        this.commodityCost = commodityCost;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getPackagefee() {
        return packagefee;
    }

    public void setPackagefee(Double packagefee) {
        this.packagefee = packagefee;
    }

    public Double getDealfee() {
        return dealfee;
    }

    public void setDealfee(Double dealfee) {
        this.dealfee = dealfee;
    }

    public String getSaleType() {
        return saleType;
    }

    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public Double getEbaySellPrice() {
        return ebaySellPrice;
    }

    public void setEbaySellPrice(Double ebaySellPrice) {
        this.ebaySellPrice = ebaySellPrice;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getEbayfee() {
        return ebayfee;
    }

    public void setEbayfee(String ebayfee) {
        this.ebayfee = ebayfee;
    }

    public String getPaypalfee() {
        return paypalfee;
    }

    public void setPaypalfee(String paypalfee) {
        this.paypalfee = paypalfee;
    }

    public Double getPostage() {
        return postage;
    }

    public void setPostage(Double postage) {
        this.postage = postage;
    }

    public Double getFinancefee() {
        return financefee;
    }

    public void setFinancefee(Double financefee) {
        this.financefee = financefee;
    }

    public Double getLossfee() {
        return lossfee;
    }

    public void setLossfee(Double lossfee) {
        this.lossfee = lossfee;
    }

    public Double getExch_rate() {
        return exch_rate;
    }

    public void setExch_rate(Double exch_rate) {
        this.exch_rate = exch_rate;
    }

    public String getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(String grossProfit) {
        this.grossProfit = grossProfit;
    }

    public String getGrossProfitRate() {
        return grossProfitRate;
    }

    public void setGrossProfitRate(String grossProfitRate) {
        this.grossProfitRate = grossProfitRate;
    }
}
