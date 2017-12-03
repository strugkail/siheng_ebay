package com.oigbuy.jeesite.common.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Fernando Scasserra @fersca
 */
public class HttpHelper {


	private static final Logger logger = LoggerFactory.getLogger(HttpHelper.class);

	public static String get(String url) {

		HttpURLConnection connection = null;
		try {
			Long start = System.currentTimeMillis();
			URL server = new URL(url);
			connection = (HttpURLConnection)server.openConnection();
			connection.setRequestMethod("GET");
			connection.addRequestProperty("Accept","application/json");
			connection.setAllowUserInteraction(false);
			//设置超时间为3秒
			connection.setConnectTimeout(3*1000);
			//防止屏蔽程序抓取而返回403错误(浏览器访问直接下载文档)
			connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			int code = connection.getResponseCode();
			StringBuilder content = new StringBuilder();

			InputStream input;
			if (code>=400){
				input = connection.getErrorStream();
			} else {
				input = connection.getInputStream();
			}
			BufferedReader docHtml = new BufferedReader(new InputStreamReader(input));
			String line;
			while ((line = docHtml.readLine()) != null){
				content.append(line);
			}
			docHtml.close();
			connection.disconnect();
			logger.debug("url:"+url+".response:"+content);
			logger.debug("url:"+url+".用时:"+(System.currentTimeMillis() - start));
			return content.toString();
		} catch (ConnectionPoolTimeoutException e){
			logger.error(e.getMessage(),e);
			return null;
		} catch (Exception e){
			logger.error(e.getMessage(),e);
		}finally{
			if(connection != null ){
				connection.disconnect();
			}
		}
		return null;
	}

	public static String post(String url, String data, String contentType) {
		HttpURLConnection connection=null;
		try {
			Long start = System.currentTimeMillis();
			URL server = new URL(url);
			connection = (HttpURLConnection)server.openConnection();
			connection.setRequestMethod("POST");
			connection.addRequestProperty("Content-Type","application/json");
			connection.setUseCaches (false);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setConnectTimeout(10*1000);
			connection.setReadTimeout(10*1000);
			DataOutputStream wr = new DataOutputStream (connection.getOutputStream ());
			wr.write(data.getBytes());
			wr.flush ();
			wr.close ();

			int code = connection.getResponseCode();
			StringBuilder content = new StringBuilder();

			InputStream input;
			if (code>=400){
				input = connection.getErrorStream();
			} else {
				input = connection.getInputStream();
			}
			if(input != null){
				BufferedReader docHtml = new BufferedReader(new InputStreamReader(input));
				String line;
				while ((line = docHtml.readLine()) != null){
					content.append(line);
				}
				docHtml.close();
				connection.disconnect();
			}
			logger.debug("url:"+url+".request.data:"+data);
			logger.debug("url:"+url+".response.content:"+content);
			logger.debug("url:"+url+".用时:"+(System.currentTimeMillis() - start));
			return content.toString();
		} catch (Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(),e);
		}
		finally{
			if(connection != null ){
				connection.disconnect();
			}
		}
		return null;
	}


}
