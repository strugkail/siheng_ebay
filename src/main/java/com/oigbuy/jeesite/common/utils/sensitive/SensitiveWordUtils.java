package com.oigbuy.jeesite.common.utils.sensitive;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.utils.HttpHelper;
import com.oigbuy.jeesite.common.utils.StringUtils;
import com.oigbuy.jeesite.common.utils.dto.BaseResDto;

/**
 * 敏感词  校验
 * 
 * @author bill.xu
 *
 */
public class SensitiveWordUtils {

	
	/***
	 * 请求校验的服务地址 本地服务地址
	 */
	
	//public static final String SERVER_URL= "http://192.168.0.42:8080/siheng_knowledge/a/sensitiveword/sensitiveWord/getSensitiveWord";
	
//	public static final String SERVER_URL= "http://192.168.2.67:8080/siheng_knowledge/a/sensitiveword/sensitiveWord/getSensitiveWord";
	
	public static final String SERVER_URL = Global.SENSITIVE_WORD_CHECK_URL;
	
	/***
	 * 判断该字符串中是不是含有 敏感词，如果有 则返回对应的敏感词提示 信息，如果没有返回 null
	 * 
	 * @param content
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  static String checkString(String content){
		if (StringUtils.isBlank(content)) {
			return null;
		}
		SensitiveReqDto dto = new SensitiveReqDto(content);
		String json = HttpHelper.post(SERVER_URL, JSONObject.toJSONString(dto), null);
		BaseResDto resDto =  JSONObject.parseObject(json, BaseResDto.class);
		if(BaseResDto.SUCCESS_CODE.equals(resDto.getCode())){//成功
			List<String> str = (List<String>) resDto.getData();
			if(str == null || CollectionUtils.isEmpty(str)){
				return  null;
			}else{
				StringBuffer buffer = new StringBuffer("含有敏感词语：");
				for (int i = 0; i < str.size(); i++) {
					buffer.append((i+1)+Global.SEPARATE_8+str.get(i)+Global.SEPARATE_11);
				}
				return buffer.toString();
			}
		}else{
			return resDto.getMessage();
		}
	}
	
	/**
	 * 
	 * @param listStr
	 * @return
	 */
	public static String checkListString(List<String> listStr) {
		StringBuffer  buffer = new StringBuffer();
		if(CollectionUtils.isNotEmpty(listStr)){
			for (String str : listStr) {
				buffer.append(str);
			}
		}
		if(StringUtils.isNotBlank(buffer.toString())){
			return checkString(buffer.toString());
		}
		return null;
	}
	
	
	/***
	 * 校验 输入的是否有敏感词，如果有的话就抛出相应的提示异常
	 * 
	 * 默认顺序  是  标题     副标题   描述1    描述 2 等顺序
	 * 
	 * @param str
	 * @throws Exception 
	 */
	public static void checkSensitiveWord(String... str) throws Exception{
		for (String content : str) {
			String checkResultStr = SensitiveWordUtils.checkString(content);
			if(StringUtils.isNotBlank(checkResultStr)){
				throw new Exception(checkResultStr);
			}
		}
	}
	
	
	
 
}
