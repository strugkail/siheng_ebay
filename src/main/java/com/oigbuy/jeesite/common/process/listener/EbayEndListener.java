package com.oigbuy.jeesite.common.process.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.oigbuy.jeesite.common.process.dao.ProcessBusinessDao;


/**
 * 流程结束执行监听
 * @author yuxiang.xiong
 * 2017年9月21日 上午9:58:13
 */
public class EbayEndListener implements ExecutionListener {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ProcessBusinessDao processBusinessDao;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		String proInsId = execution.getProcessInstanceId();
		processBusinessDao.deleteByproInsId(proInsId);
	}

}
