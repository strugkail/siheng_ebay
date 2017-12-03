package com.oigbuy.jeesite.common.process.listener;

import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;


/**
 * 节点任务监听
 * @author yuxiang.xiong
 * 2017年9月8日 下午6:30:55
 */
public class EbayTaskListener implements TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub
		Map<String, Object> map = delegateTask.getVariables();
		List<String> listgroupId = (List<String>) map.get("purchase");
		for(String userId : listgroupId){
			delegateTask.addCandidateUser(userId);
		}
		delegateTask.addCandidateUser("1");
	}

}
