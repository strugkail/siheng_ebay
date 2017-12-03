package com.oigbuy.jeesite.common.process.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ProcessEngine;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.process.entity.EbayProcessBusiness;
import com.oigbuy.jeesite.common.process.service.GeneralProcessService;
import com.oigbuy.jeesite.common.web.BaseController;
import com.oigbuy.jeesite.modules.act.dao.ActDao;
import com.oigbuy.jeesite.modules.act.entity.Act;
import com.oigbuy.jeesite.modules.act.utils.ActUtils;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.PlatformSite;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.service.PlatformSiteService;
import com.oigbuy.jeesite.modules.sys.utils.UserUtils;


@Controller
@RequestMapping(value = "${adminPath}/act/ebaytask")
public class GeneralProcess extends BaseController{
	
	
	@Autowired
	private GeneralProcessService generalProcessservice;
	
	@Autowired
	private PlatformSiteService platformSiteService;
	
	@Autowired
	private ProcessEngine processEngine;
	
	@Autowired
	private ActDao actDao;
	/**
	 * 待开发待办列表
	 * @author yuxiang.xiong
	 * 2017年9月12日 上午11:17:03
	 * @param act
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"todo", ""})
	public String todoList(Act act,EbayProcessBusiness business, HttpServletResponse response,HttpServletRequest request, Model model,String flag) throws Exception {
		
		String msg = "";
		List<Act> list = generalProcessservice.todoList(business);
		// 获取eBay平台的所有站点
		List<PlatformSite> siteList = platformSiteService
				.findListByPlatformId(Global.EBAY_PLATFORM_CODE);
		model.addAttribute("list", list);
		model.addAttribute("business", business);
		model.addAttribute("siteList", siteList);
		flag = StringEscapeUtils.unescapeXml(flag);
		if (UserUtils.getPrincipal().isMobileLogin()) {
			return renderString(response, list);
		}
		if (Global.ACT_IDENTI_DEV.equals(flag)) {
			msg = "modules/ebay/act/actTaskTodoList";
		} else if (Global.ACT_IDENTI_DESGINE.equals(flag)) {
			msg = "modules/ebay/act/actDesginTodoList";
		} else if (Global.ACT_INDETI_DAT_PERFECT.equals(flag)) {
			msg = "modules/ebay/act/actProductTodoList";
		}
		return msg;
	}
	/**
	 * 任务办理（根据流程节点重定向到办理页面）
	 * @author yuxiang.xiong
	 * 2017年9月12日 下午2:21:13
	 * @param act
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "form")
	public String form(Act act,EbayProcessBusiness buss, HttpServletRequest request, Model model){
		
		// 获取流程XML上的表单KEY
		String formKey = generalProcessservice.getFormKey(act.getProcDefId(),
				act.getTaskDefKey());

		// 获取流程实例对象
		if (act.getProcInsId() != null) {
			act.setProcIns(generalProcessservice.getProcIns(act.getProcInsId()));
		}
		return "redirect:" + ActUtils.getFormUrl(formKey, act);
	}
	/**
	 * 签收任务
	 * @author yuxiang.xiong
	 * 2017年9月12日 下午2:08:35
	 * @param act
	 * @return
	 */
	@RequestMapping(value = "claim")
	@ResponseBody
	public String claim(Act act) {
		String userId = UserUtils.getUser().getId();
		generalProcessservice.claim(act.getTaskId(), userId);
		return "true";
	}
	
	@RequestMapping(value = "complete")
	@ResponseBody
	public String complete(Act act) {
//		generalProcessservice.complete(act.getTaskId(), act.getProcInsId(), act.getComment(), act.getVars().getVariableMap());
		return "true";
	}
	
	/**
	 * 回退组任务（仅限原来是组任务）
	 * @author yuxiang.xiong
	 * 2017年9月14日 下午5:33:32
	 */
	@RequestMapping(value="robackgroup")
	@ResponseBody
	public String setAssignee(Act act){
		generalProcessservice.setAssignee(act.getTaskId());
		return "true";
	}
	
	/**
	 * 添加组成员
	 * @author yuxiang.xiong
	 * 2017年9月14日 下午5:52:42
	 * @param act
	 * @param userId  需要添加的人员id
	 */
	public void addGroupUser(Act act,String userId) {
		generalProcessservice.addGroupUser(act.getTaskId(), userId);
	}
}
