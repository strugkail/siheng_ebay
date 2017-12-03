package com.oigbuy.jeesite.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;

import org.apache.commons.lang3.StringUtils;

import com.oigbuy.jeesite.common.config.Global;

import jcifs.UniAddress;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;
import jcifs.smb.SmbSession;

public class SMBUtil {
	private static String domain;
	private static String user;
	private static String pass;
	private static String host;
	private static String sharedFolder;

	private static NtlmPasswordAuthentication auth;
	// Global.getConfig("smb_sharedFolder");
	static {
		domain = Global.getConfig("smb_domain");
		user = Global.getConfig("smb_user");
		pass = Global.getConfig("smb_pwd");
		host = Global.getConfig("smb_host");

		jcifs.Config.setProperty("jcifs.smb.client.disablePlainTextPasswords", "false");
		try {
			auth = new NtlmPasswordAuthentication(domain, user, pass);
			InetAddress ip = InetAddress.getByName(host);
			UniAddress myDomain = new UniAddress(ip);
			System.out.println("auth:" + auth.getDomain());
			System.out.println("username:" + auth.getUsername());
			System.out.println("password:" + auth.getPassword());
			SmbSession.logon(myDomain, auth);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	public static String createDir(String rootFolder, String dir) {
		String rootPath = "smb://" + host + "/" + rootFolder + "/";
		SmbFile fp;
		try {
			fp = new SmbFile(rootPath + dir, auth);
			// 目录不存在创建文件夹
			if (!fp.exists() || !fp.isDirectory()) {
				fp.mkdir();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

		return rootFolder + "/" + dir;
	}

	public static void fileUpload(String rootFolder, String newFileName, byte[] b) {
		String rootPath = "smb://" + host + "/" + rootFolder + "/";
		String path = rootPath + newFileName;
		SmbFile smbFile;
		SmbFileOutputStream smbfos = null;
		try {
			smbFile = new SmbFile(path, auth);
			smbfos = new SmbFileOutputStream(smbFile);
			smbfos.write(b);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			if (smbfos != null) {
				try {
					smbfos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 获取文件夹下的所有文件
	 * 
	 * @param rootFolder
	 * @param group
	 * @return
	 */
	public static SmbFile[] getListFile(String rootFolder, String group, String fileFolder) {
		String rootPath = "smb://" + host + "/" + rootFolder;
		String remoteUrl = rootPath + "/" + group + "/" + fileFolder;
		if (StringUtils.isNotBlank(group)) {
			remoteUrl = rootPath + "/" + group + "/" + fileFolder + "/";
		} else {
			remoteUrl = rootPath + "/" + fileFolder + "/";
		}
		SmbFile smbFile;
		SmbFileOutputStream smbfos = null;
		try {
			smbFile = new SmbFile(remoteUrl, auth);

			if (!smbFile.exists()) {
				return null;
			} else {
				return smbFile.listFiles();
			}

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			if (smbfos != null) {
				try {
					smbfos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String getRootPath(String rootFolder) {
		String rootPath = "//" + host + "/" + rootFolder + "/";
		return rootPath.replaceAll("\\/", "\\\\");
	}
	
	/**
	 * 根据url获取文件
	 * @param url
	 * @return
	 */
	public static SmbFile getSmbFile(String url){
		String rootPath = null;
		if (url.startsWith("smb://" + host + "/")) {
			rootPath = url;
		}else {
			rootPath = "smb://" + host + "/" + url+"/";
		}
		SmbFile smbFile = null;
		try {
			smbFile = new SmbFile(rootPath, auth);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e.getMessage());
		}
		return smbFile;
		
	}


	public static void main(String[] args) {
		// String rootPath = Global.getConfig("smb_img_url_wish");
		// String group = "group1/";
		// String productFolder = "T10001/";
		// SmbFile[] files = SMBUtil.getListFile(rootPath, group, productFolder);
		// for (SmbFile f : files) {
		// System.out.println(f.getName());
		// }
		System.out.println(SMBUtil.getRootPath(Global.getConfig("smb_img_url_wish")));

	}
}
