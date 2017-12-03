package com.oigbuy.jeesite.common.process.listener;

import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Service;

/**
 * 刊登服务监听
 * @author yuxiang.xiong
 * 2017年9月8日 下午4:56:27
 */
@Service("publish")
public class EbayPublishServiceListener implements ExecutionListener{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		Map map = execution.getVariables();
		System.out.println("================publish================");
	}
}
