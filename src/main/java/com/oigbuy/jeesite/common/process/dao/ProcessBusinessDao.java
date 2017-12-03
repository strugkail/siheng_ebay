package com.oigbuy.jeesite.common.process.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.common.process.entity.EbayProcessBusiness;
import com.oigbuy.jeesite.common.process.entity.UserTodoTask;


@MyBatisDao
public interface ProcessBusinessDao extends CrudDao<EbayProcessBusiness> {

	void updateByProcInsId(EbayProcessBusiness bussinesss);
	
	 EbayProcessBusiness getByProcInsId(EbayProcessBusiness bussiness);
	 
	 int deleteByproInsId(String proInsId);
	 
	 List<UserTodoTask> gettaskList(@Param("userId") String userId,@Param("sign") String sign);

	 List<EbayProcessBusiness> getlistByProcInsId(EbayProcessBusiness business);
	 
	 void updateSysParentCodeByProcInsId(EbayProcessBusiness bussinesss);
}
