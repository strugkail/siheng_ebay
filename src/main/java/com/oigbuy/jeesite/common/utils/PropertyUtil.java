package com.oigbuy.jeesite.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;


/**
 * 属性工具类
 * @Class Name PropertyUtil
 * @author 张永生
 * @Create In 2016年9月26日
 */
@SuppressWarnings("resource")
public class PropertyUtil {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Hashtable<String, Object> register = new Hashtable();

	/**
	 * 根据属性文件名称获得属性文件对象 
	 * 2015年11月20日
	 * By 张永生
	 * @param fileName
	 * @return
	 */
	public static Properties getProperties(String fileName) {
		InputStream is = null;
		Properties prop = null;
		try {
			prop = (Properties) register.get(fileName);
			if (prop == null) {
				try {
					is = new FileInputStream(fileName);
				} catch (Exception e) {
					if (fileName.startsWith("/")) {
						is = PropertyUtil.class.getResourceAsStream(fileName);
					} else {
						is = PropertyUtil.class.getResourceAsStream("/"
								+ fileName);
					}
				}
				prop = new Properties();
				prop.load(is);
				register.put(fileName, prop);
				is.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop;
	}

	/**
	 * 获得String类型的属性值 
	 * 2015年11月20日
	 * By 张永生
	 * @param fileName
	 * @param strKey
	 * @param defaultValue
	 * @return
	 */
	public static String getStrValue(String fileName, String strKey, String defaultValue) {
		Properties prop = getProperties(fileName);
		try {
			if(prop.getProperty(strKey) == null){
				return defaultValue;
			}
			return prop.getProperty(strKey);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}

	/**
	 * 获得Integer类型的属性值 
	 * 2015年11月20日
	 * By 张永生
	 * @param fileName
	 * @param strKey
	 * @param defaultValue
	 * @return
	 */
	public static Integer getIntValue(String fileName,String strKey, Integer defaultValue) {
		Properties prop = getProperties(fileName);
		try {
			String strValue = prop.getProperty(strKey);
			if(StringUtils.isNotBlank(strValue)){
				return Integer.valueOf(strValue);
			}else{
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultValue;
	}

	/**
	 * 获得Long型的属性值
	 * 2015年11月20日
	 * By 张永生
	 * @param fileName
	 * @param strKey
	 * @param defaultValue
	 * @return
	 */
	public static Long getLongValue(String fileName, String strKey, Long defaultValue) {
		Properties prop = getProperties(fileName);
		try {
			return Long.valueOf(prop.getProperty(strKey));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultValue;
	}

	/**
	 * 获得Double型的属性值 
	 * 2015年11月20日
	 * By 张永生
	 * @param fileName
	 * @param strKey
	 * @param defaultValue
	 * @return
	 */
	public static Double getDoubleValue(String fileName, String strKey, Double defaultValue) {
		Properties prop = getProperties(fileName);
		try {
			return Double.valueOf(prop.getProperty(strKey));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultValue;
	}

	/**
	 * 获得boolean型的属性值 
	 * 2015年11月20日
	 * By 张永生
	 * @param fileName
	 * @param strKey
	 * @param defaultValue
	 * @return
	 */
	public static boolean getBooleanValue(String fileName, String strKey, boolean defaultValue) {
		Properties prop = getProperties(fileName);
		try {
			return Boolean.valueOf(prop.getProperty(strKey));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultValue;
	}
	
	/**
	 * 设置属性
	 * 2016年4月1日
	 * By 朱杰
	 * @param fileName
	 * @param key
	 * @param value
	 * @return
	 */
	public static Properties updateProperties(String fileName,String key,String value) {
		Properties properties = PropertyUtil.getProperties(fileName);
		URL filepath;
		if (fileName.startsWith("/")) {
			filepath = PropertyUtil.class.getResource(fileName);
		} else {
			filepath = PropertyUtil.class.getResource("/"+ fileName);
		}
		File file=new File(filepath.getPath());
		if(file.exists()){
			FileOutputStream output;
			try {
				output = new FileOutputStream(file);
				properties.setProperty(key, value);
				properties.store(output, "update "+key+"[" + value + "]");
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return properties;
	}
}
