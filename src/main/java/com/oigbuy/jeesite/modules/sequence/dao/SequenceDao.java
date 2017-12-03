package com.oigbuy.jeesite.modules.sequence.dao;

import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;


@MyBatisDao
public interface SequenceDao {

	public void updateGlobalId(@Param("step") Integer step);

	public Long getGlobalId();

	public void updateProductCode(@Param("step") Integer step);

	public Long getProductCode();
	
}
