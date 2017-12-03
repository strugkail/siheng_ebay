package com.oigbuy.jeesite.common.utils.sensitive;

/***
 * 敏感词校验的请求 dto
 * 
 * @author bill.xu
 *
 */
public class SensitiveReqDto {

	
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public SensitiveReqDto(String content) {
		super();
		this.content = content;
	}

	public SensitiveReqDto() {
		super();
	}
	
	
   	
}
