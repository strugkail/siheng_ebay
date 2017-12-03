package com.oigbuy.jeesite.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Workbook;

import com.oigbuy.jeesite.modules.sys.entity.User;
import com.sho.tool.office.ExcelExportUtils;

public class Test {

	public static void main(String[] args) {
		
		List<User> users = new ArrayList<User>();
		
		User user1 = new User();
		user1.setLoginName("测试1");
		user1.setCreateDate(new Date());
		user1.setId("1");
		user1.setEmail("!@#$%^&**(");
		
		User user2 = new User();
		SimpleDateFormat myFmt2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		user2.setLoginName(myFmt2.format(new Date()));
		user2.setCreateDate(new Date());
		user2.setId("2");
		user2.setEmail("<>?:"+"'{}|\'");
		
		users.add(user1);
		users.add(user2);
		
		UserData data = new UserData();
		data.setUsers(users);
		
		
		Workbook book = ExcelExportUtils.export("/test.xls", data);
		
		try {
			OutputStream ous = new FileOutputStream(new File("F:/a.xls"));
			book.write(ous);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
