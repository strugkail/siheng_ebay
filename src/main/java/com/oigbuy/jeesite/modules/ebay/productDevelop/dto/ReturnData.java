package com.oigbuy.jeesite.modules.ebay.productDevelop.dto;

public class ReturnData {

    private ReturnMsgDto result;
    private boolean flag;
    private String code;
    private String message;

    public ReturnMsgDto getResult() {
        return result;
    }

    public void setResult(ReturnMsgDto result) {
        this.result = result;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
