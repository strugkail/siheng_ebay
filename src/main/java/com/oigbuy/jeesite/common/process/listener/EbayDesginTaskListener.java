package com.oigbuy.jeesite.common.process.listener;

import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class EbayDesginTaskListener implements TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub
		Map<String, Object> map = delegateTask.getVariables();
		List<String> listgroupId = (List<String>) map.get("desgin");
		for(String userId : listgroupId){
			delegateTask.addCandidateUser(userId);
		}
		delegateTask.addCandidateUser("1");
	}
}
