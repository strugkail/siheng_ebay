package com.oigbuy.jeesite.common.aop;

import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogAspect {
	// 切面方法
	public Object traceMethod(final ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {
		Object returnVal = null;
		final Logger log = getLog(proceedingJoinPoint);// 获取当前执行的dao类的全称
		final String methodName = proceedingJoinPoint.getSignature().getName();// 获取当前dao类的执行方法
		long start = System.currentTimeMillis();
		try {
			if (log.isInfoEnabled()) {
				final Object[] args = proceedingJoinPoint.getArgs();
				final String arguments;
				if (args == null || args.length == 0) {
					arguments = "";
				} else {
					arguments = Arrays.deepToString(args);
				}
				log.info("============================================================================================================================");
				log.info("进入方法 [" + methodName + "];参数 [" + arguments + "]");
			}
			returnVal = proceedingJoinPoint.proceed();
			return returnVal;
		} finally {
			if (log.isInfoEnabled()) {
				log.info("离开方法 [" + methodName + "] ;返回值 ["
						+ (returnVal != null ? returnVal.toString() : "null")
						+ "].");
				long end = System.currentTimeMillis();
				log.info("执行方法耗时：" + (end - start) + "毫秒。");
				log.info("============================================================================================================================");
			}
		}
	}

	protected Logger getLog(final JoinPoint joinPoint) {
		final Object target = joinPoint.getTarget();
		if (target != null) {
			return LoggerFactory.getLogger(target.getClass());
		}
		return LoggerFactory.getLogger(getClass());
	}
}