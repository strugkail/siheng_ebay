package com.oigbuy.jeesite.common.utils.translate;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;

import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;
import com.cybozu.labs.langdetect.Language;
import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.utils.HttpHelper;
import com.oigbuy.jeesite.common.utils.PropertyUtil;
import com.oigbuy.jeesite.common.utils.StringUtils;

public class TranslatorUtil{
	
	/**
	 * google翻译Url前缀
	 */
	private static final String googleBaseUrl = PropertyUtil.getStrValue("jeesite.properties", "googleBaseUrl", "http://translate.google.cn/translate_a/single");
	
	private static final org.slf4j.Logger LOGGER =  org.slf4j.LoggerFactory.getLogger(TranslatorUtil.class);
	
    public static void init(String profile) throws LangDetectException {
    	if(DetectorFactory.getLangList().isEmpty()){
    		DetectorFactory.loadProfile(profile);
    	}
    }
    public static String detect(String text) throws LangDetectException {
        Detector detector = DetectorFactory.create();
        detector.append(text);
        return detector.detect();
    }
    public static ArrayList<Language> detectLangs(String text) throws LangDetectException {
        Detector detector = DetectorFactory.create();
        detector.append(text);
        return detector.getProbabilities();
    }

	public static String getTranslateUrl(String sourceText, String sourceLanguage, String targetLanguage) throws Exception {
        String token = TokenGet.getToken(sourceText);
        StringBuilder builder = new StringBuilder(googleBaseUrl+"?");
        builder.append("client=t&sl=");
        builder.append(sourceLanguage);
        builder.append("&tl=");
        builder.append(targetLanguage);
        builder.append("&hl=zh-CN&dt=bd&dt=ex&dt=ld&dt=md&dt=qc&dt=rw&dt=rm&dt=ss&dt=t&dt=at&ie=UTF-8&oe=UTF-8&source=sel&tk=");
        builder.append(token);
        builder.append("&q=");
        builder.append(URLEncoder.encode(sourceText,"utf-8"));
        String urlStr = builder.toString().replaceAll("\\+","%20");
        return urlStr;
    }

/*    public static List<String> getAllContent(String sourceText,String targetLanguage) throws Exception {
        List<String> list = new ArrayList<String>();
        //获取语言源库路径
        String path = TranslatorUtil.class.getClassLoader().getResource("profiles").getPath();
        //初始化加载语言语言库
        init(path);
        //自动检测语言类型并生成请求url
        String URL = getTranslateUrl(sourceText,TranslatorUtil.detect(sourceText),targetLanguage);
        String returnContent = HttpHelper.get(URL);
        String[] strAyy = returnContent.split("\"");
        for (int i=0;i<strAyy.length;i++){
            if(i%2!=0)
                list.add(strAyy[i]);
        }
        return list;
    }*/

    public static String getSimpleContent(String sourceText,String targetLanguage) throws Exception{
    	
    	if(StringUtils.isBlank(sourceText)){
    		return null;
    	}
    	//获取语言源库路径
        String path = TranslatorUtil.class.getClassLoader().getResource("profiles").getPath();
        LOGGER.info("##########   翻译的path 路径为：{}，\t 截取之后的路径为{}",path,(path.substring(1,path.length())));
        //初始化加载语言语言库
        init(path);
//        init(path.substring(1,path.length()));
        
        sourceText = sourceText.trim();
        
        //如果包含特殊字符 换行的 \n 转化为 数组 翻译 翻译后再转为数组
        StringBuffer buffer = new StringBuffer();
        if(sourceText.contains(Global.SEPARATE_6)){
        	String[] texts= sourceText.split(Global.SEPARATE_6);
        	for (String string : texts) {
        		string=string.trim();
        		 //自动检测语言类型并生成请求url
                String URL = getTranslateUrl(string,TranslatorUtil.detect(string),targetLanguage);
                //请求并接收返回结果
                String returnContent = HttpHelper.get(URL);
                //分割请求内容并返回翻译结果
                String[] str = null;
                str = returnContent.split("\"");
                buffer.append(str[1]+Global.SEPARATE_6);
			}
        	return buffer.toString();
        }
        //自动检测语言类型并生成请求url
        String URL = getTranslateUrl(sourceText,TranslatorUtil.detect(sourceText),targetLanguage);
        //请求并接收返回结果
        String returnContent = HttpHelper.get(URL);
        //分割请求内容并返回翻译结果
        String[] str = null;
        str = returnContent.split("\"");
        return str[1];
        
    }
    
	public String getGoogleBaseUrl() {
		return googleBaseUrl;
	}
	
	
	public static void main(String[] args) throws Exception {
		
		
		System.out.println(getSimpleContent("value \n test \n time sun",LangueageSupport.German));
		//System.out.println(getAllContent("value \n test \n time sun",LangueageSupport.German));
	
		  System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));    
		  System.out.println(TranslatorUtil.class.getClassLoader().getResource("profiles"));       
		  System.out.println(ClassLoader.getSystemResource(""));        
		  System.out.println(TranslatorUtil.class.getResource(""));        
		  System.out.println(TranslatorUtil.class.getResource("/")); //Class文件所在路径  
		  System.out.println(new File("/").getAbsolutePath());        
		  System.out.println(System.getProperty("user.dir"));    
		
		
		
	}

}
