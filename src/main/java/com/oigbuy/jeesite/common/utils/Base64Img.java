package com.oigbuy.jeesite.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.util.Base64Utils;

public class Base64Img {
	/**
	 * 远程读取image转换为Base64字符串
	 * @param imgUrl
	 * @return
	 */
	public static String Image2Base64(String imgUrl) {
		URL url = null;
		InputStream is = null; 
		ByteArrayOutputStream outStream = null;
		HttpURLConnection httpUrl = null;
		try{
			url = new URL(imgUrl);
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.connect();
			httpUrl.getInputStream();
			is = httpUrl.getInputStream();			
			
			outStream = new ByteArrayOutputStream();
			//创建一个Buffer字符串
			byte[] buffer = new byte[1024];
			//每次读取的字符串长度，如果为-1，代表全部读取完毕
			int len = 0;
			//使用一个输入流从buffer里把数据读取出来
			while( (len=is.read(buffer)) != -1 ){
				//用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
				outStream.write(buffer, 0, len);
			}
			// 对字节数组Base64编码
			return Base64Utils.encodeToString(outStream.toByteArray());
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(is != null)
			{
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(outStream != null)
			{
				try {
					outStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(httpUrl != null)
			{
				httpUrl.disconnect();
			}
		}
		return imgUrl;
	}
	
	/** 
     * @Title: GenerateImage 
     * @Description: TODO(base64字符串转化成图片) 
     * @param imgStr 
     * @return 
     */  
    public static boolean GenerateImage(String imgStr) {  
        if (imgStr == null) // 图像数据为空  
            return false;  
        try {  
            // Base64解码  
            byte[] b = Base64Utils.decodeFromString(imgStr);
            for (int i = 0; i < b.length; ++i) {  
                if (b[i] < 0) {// 调整异常数据  
                    b[i] += 256;  
                }  
            }  
            // 生成jpeg图片  
            String imgFilePath = "d://224.jpg";  
            OutputStream out = new FileOutputStream(imgFilePath);  
            out.write(b);  
            out.flush();  
            out.close();  
            return true;  
        } catch (Exception e) {  
            return false;  
        }  
    }  
	public static void main(String[] args) {
//		String msg=Image2Base64("http://192.168.2.67/group1/M00/01/56/wKgCQ1oFDP2ANVTuAAKy201epPE533.jpg");
		String msg=Image2Base64("https://cbu01.alicdn.com/img/ibank/2016/461/840/3313048164_465808874.jpg");
		GenerateImage(msg);
		System.out.println(msg);
		
	}
}
