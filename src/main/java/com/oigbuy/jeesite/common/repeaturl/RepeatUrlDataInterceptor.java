package com.oigbuy.jeesite.common.repeaturl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.oigbuy.jeesite.common.mapper.JsonMapper;

/**
 * 一个用户 相同url 同时提交 相同数据 验证 主要通过 session中保存到的url 和 请求参数。如果和上次相同，则是重复提交表单
 * 
 * @author tony.liu
 * 
 */
public class RepeatUrlDataInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			RepeatUrlData annotation = method
					.getAnnotation(RepeatUrlData.class);
			if (annotation != null) {
				if (repeatDataValidator(request)){// 如果重复相同数据
					response.sendRedirect(request.getContextPath() + "/static/repeat/repeat.jsp");
					return false;
				}else{
					return true;
				}
			}	
		}
		return true;
	}

	/**
	 * 验证同一个url数据是否相同提交 ,相同返回true
	 * 
	 * @param httpServletRequest
	 * @return
	 */
	public boolean repeatDataValidator(HttpServletRequest httpServletRequest) {
		String params = JsonMapper.toJsonString(httpServletRequest
				.getParameterMap());
		String url = httpServletRequest.getRequestURI();
		Map<String, String> map = new HashMap<String, String>();
		map.put(url, params);
		String nowUrlParams = map.toString();//

		Object preUrlParams = httpServletRequest.getSession().getAttribute("repeatData");
		//httpServletRequest.getSession().setMaxInactiveInterval(5);
		if (preUrlParams == null){// 如果上一个数据为null,表示还没有访问页面
			httpServletRequest.getSession().setAttribute("repeatData",nowUrlParams);
			return false;
		} else{  // 否则，已经访问过页面
			System.out.println("preUrlParams======================="+preUrlParams);
			System.out.println("nowUrlParams======================="+nowUrlParams);
			if (preUrlParams.toString().equals(nowUrlParams)){// 如果上次url+数据和本次url+数据相同，则表示城府添加数据
				return true;
			} else{// 如果上次 url+数据 和本次url加数据不同，则不是重复提交
				httpServletRequest.getSession().setAttribute("repeatData",nowUrlParams);
				return false;
			}
		}
	}

}
