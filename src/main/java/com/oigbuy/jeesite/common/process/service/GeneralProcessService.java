package com.oigbuy.jeesite.common.process.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.modules.ebay.product.entity.Product;
import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.persistence.ActEntity;
import com.oigbuy.jeesite.common.process.dao.ProcessBusinessDao;
import com.oigbuy.jeesite.common.process.entity.EbayProcessBusiness;
import com.oigbuy.jeesite.common.process.entity.ProcessBean;
import com.oigbuy.jeesite.common.process.entity.UserTodoTask;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.common.utils.Collections3;
import com.oigbuy.jeesite.common.utils.StringUtils;
import com.oigbuy.jeesite.modules.act.dao.ActDao;
import com.oigbuy.jeesite.modules.act.entity.Act;
import com.oigbuy.jeesite.modules.act.utils.ActUtils;
import com.oigbuy.jeesite.modules.act.utils.ProcessDefCache;
import com.oigbuy.jeesite.modules.oa.dao.TestAuditDao;
import com.oigbuy.jeesite.modules.sys.dao.OfficeDao;
import com.oigbuy.jeesite.modules.sys.dao.UserDao;
import com.oigbuy.jeesite.modules.sys.entity.Office;
import com.oigbuy.jeesite.modules.sys.entity.User;
import com.oigbuy.jeesite.modules.sys.utils.UserUtils;


/**
 * 流程引擎通用方法实现
 * @author yuxiang.xiong
 * 2017年9月8日 上午9:49:24
 */
@Service
@Transactional(readOnly = true)
public class GeneralProcessService extends CrudService<ProcessBusinessDao, EbayProcessBusiness> {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private IdentityService identityService;
	
	@Autowired
	private ActDao actDao;
	
	@Autowired
	private UserDao userdao;
	
	@Autowired
	private ProcessEngine processEngine;
	
	@Autowired
	private TestAuditDao testAuditDao;
	
	@Autowired
	private FormService formService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private ProcessBusinessDao processBusinessDao;
	
	@Autowired
	private OfficeDao officedao;
	
	/**
	 * 实体类封装流程实例sql
	 * @author yuxiang.xiong
	 * 2017年9月8日 上午11:50:46
	 * @param entity    实体类对象
	 * @param procInsId   sql映射文件指定表中对应 procInsId 如：entity a 则 a.PROC_INS_ID
	 */
	public void dataProsql(ActEntity<?> entity, String procInsId) {

		List<String> list = getProcIdList(); // 得到流程实例id
		String proid = "";
		String content = "";
		for (int i = 0; i < list.size(); i++) {

			if (list.size() - 1 == i) {
				proid = proid += "'" + list.get(i) + "'";
			} else {
				proid = proid += "'" + list.get(i) + "',";
			}
		}
		if (StringUtils.isBlank(proid)) {
			content = " AND " + procInsId + " in ('')"; // 拼接查询sql语句
		} else {

			content = " AND " + procInsId + " in (" + proid + ")"; // 拼接查询sql语句
		}
		entity.getSqlMap().put("sqlmap", content);
	}
	
	/**
	 * 获取待办流程实例id列表
	 * @author yuxiang.xiong
	 * 2017年9月8日 下午12:01:06
	 * @return
	 */
	public List<String> getProcIdList() {

		String userId = UserUtils.getUser().getId(); // 获取当前人的Id
		// =============== 查询个人待办任务 ===============
		TaskQuery todoTaskQuery = taskService.createTaskQuery()
				.taskAssignee(userId).active().includeProcessVariables()
				.orderByTaskCreateTime().desc();
		// 查询列表
		List<Task> todoList = todoTaskQuery.list();
		List<String> prilist = new ArrayList<String>();
		for (Task task : todoList) {

			String id = task.getProcessInstanceId();
			prilist.add(id);
		}
		// ==========查询待签收任务===========
		TaskQuery toClaimQuery = taskService.createTaskQuery()
				.taskCandidateUser(userId).includeProcessVariables().active()
				.orderByTaskCreateTime().desc();
		List<Task> toClaimList = toClaimQuery.list();
		for (Task task : toClaimList) {
			String id = task.getProcessInstanceId();
			prilist.add(id);
		}
		return prilist;
	}
	
	/**
	 * 获取流程表单（首先获取任务节点表单KEY，如果没有则取流程开始节点表单KEY）
	 * @author yuxiang.xiong
	 * 2017年9月8日 下午2:30:07
	 * @param procDefId
	 * @param taskDefKey
	 * @return
	 */
	public String getFormKey(String procDefId, String taskDefKey){
		String formKey = "";
		if (StringUtils.isNotBlank(procDefId)){
			if (StringUtils.isNotBlank(taskDefKey)){
				try{
					formKey = formService.getTaskFormKey(procDefId, taskDefKey);
				}catch (Exception e) {
					formKey = "";
				}
			}
			if (StringUtils.isBlank(formKey)){
				formKey = formService.getStartFormKey(procDefId);
			}
			if (StringUtils.isBlank(formKey)){
				formKey = "/404";
			}
		}
		logger.debug("getFormKey: {}", formKey);
		return formKey;
	}
	@Transactional(readOnly = false)
	public void deleteByproInsId(String proInsId){
		processBusinessDao.deleteByproInsId(proInsId);
	}
	/**
	 * 获取流程实例对象
	 * @author yuxiang.xiong
	 * 2017年9月8日 下午2:44:37
	 * @param procInsId  流程实例id
	 * @return
	 */
	@Transactional(readOnly = false)
	public ProcessInstance getProcIns(String procInsId) {
		return runtimeService.createProcessInstanceQuery().processInstanceId(procInsId).singleResult();
	}
	/**
	 * 分配任务处理人
	 * @author yuxiang.xiong
	 * 2017年9月26日 下午2:51:52
	 * @param vars
	 * @return
	 */
	public Map<String,Object> getAssignUser(Map<String,Object> vars) {
		
		User usersale = new User();
		Office office_sale =new Office();//获取wish_销售部门信息
		office_sale.setCode(Global.ACT_WISH_SALE_CODE);
		usersale.setOffice(office_sale);
		List<String> listsaleId = userdao.findUserByOfficeCode(usersale);
		
		User userdesgin = new User();
		Office office_desgin =new Office();//获取wish_设计部门信息
		office_desgin.setCode(Global.ACT_WISH_DESGIN_CODE);
		userdesgin.setOffice(office_desgin);
		List<String> listdeaginId = userdao.findUserByOfficeCode(userdesgin);
		
		User userpurchase = new User();
		Office office_purchase =new Office();//获取wish_采购部门信息
		office_purchase.setCode(Global.ACT_WISH_PURCHASE_CODE);
		userpurchase.setOffice(office_purchase);
		List<String> listpurchaseId = userdao.findUserByOfficeCode(userpurchase);
		vars.put("desgin", listdeaginId);
		vars.put("sale", listsaleId);
		vars.put("purchase", listpurchaseId);
		return vars;
	}
	/**
	 * 启动流程（如采集竞品的提交）
	 * @author yuxiang.xiong
	 * 2017年9月8日 下午2:38:03
	 * @param bean   流程实体bean，存放业务数据和流程所需参数
	 */
	@Transactional(readOnly = false)
	public String startProcess(ProcessBean<EbayProcessBusiness> bean) {
		
		String userId = UserUtils.getUser().getId();

		Map<String, Object> vars = bean.getVars();
		// 设置流程变量
		if (vars == null) {
			vars = Maps.newHashMap();
		}
		vars = getAssignUser(vars); //获取任务分配
		// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
		identityService.setAuthenticatedUserId(userId);
		// 启动流程
		ProcessInstance procIns = runtimeService.startProcessInstanceByKey(
				ActUtils.PD_TEST_EABY[0], bean.getId(), vars);
		EbayProcessBusiness business = bean.getData();
		business.setProcInsId(procIns.getId());
		processBusinessDao.insert(business);
		return business.getProcInsId();
	}
	
	/**
	 * 签收（任务认领）
	 * @author yuxiang.xiong
	 * 2017年9月8日 下午6:08:30
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void claim(String taskId, String userId) {
		taskService.claim(taskId, userId);
	}

	@Transactional(readOnly = false)
	public void updateSysParentCodeByProcInsId(EbayProcessBusiness bussinesss){
		processBusinessDao.updateSysParentCodeByProcInsId(bussinesss);
	}
	
	/**
	 * 获取任务id
	 * @author yuxiang.xiong
	 * 2017年9月11日 下午2:46:42
	 * @param proInsId   流程实例id
	 * @return
	 */
	public String getTaskId(String proInsId) {

		String taskId = "";
		List<Task> list = processEngine.getTaskService()/** 与正在执行的任务管理相关的Service */
		.createTaskQuery().processInstanceId(proInsId).list();
		/** 创建任务查询对象 */
		if (!Collections3.isEmpty(list)) {
			taskId = list.get(0).getId();
		}
		return taskId;
	}
	/**
	 * 查询待办、待签收列表 分页显示
	 */
	public Page<Act> findTodoPage(Page<Act> page, Act act,EbayProcessBusiness business){
		page.setList(todoList(business));
		act.setPage(page);
		return page;
	}
	/**
	 * 查询待办、待签收列表
	 * @author yuxiang.xiong
	 * 2017年9月12日 下午2:25:53
	 * @return
	 * @throws Exception 
	 */
	@Transactional(readOnly = false)
	public List<Act> todoList(EbayProcessBusiness business) {
		String userId = UserUtils.getUser().getId();// ObjectUtils.toString(UserUtils.getUser().getId());
		List<Act> result = new ArrayList<Act>();
		List<UserTodoTask> tasklist = processBusinessDao.gettaskList(userId,business.getSign());
		List<String> strlist = new ArrayList<String>();
		for (UserTodoTask todotask : tasklist) {
			strlist.add(todotask.getProcessInstanceId());
		}
		business.setProcInsIdlist(strlist);
		// 通过流程实例id查询流程表数据
		List<EbayProcessBusiness> listBusiness = processBusinessDao.getlistByProcInsId(business);
		Map<String, EbayProcessBusiness> mapBusiness = new HashMap<String, EbayProcessBusiness>();
		for (EbayProcessBusiness b : listBusiness) {
			mapBusiness.put(b.getProcInsId(), b);
		}
		
		List<Task> taskList = taskService.createTaskQuery().list();
		Map<String, Task> taskMap = new HashMap<String, Task>();
		for (Task task : taskList) {
			taskMap.put(task.getId(), task);
		}
		
		for (UserTodoTask todotask : tasklist) {
			EbayProcessBusiness b = mapBusiness.get(todotask.getProcessInstanceId());
			Task task = taskMap.get(todotask.getId());
			if(task != null){
				//String activityId = getTask(todotask.getId()).getTaskDefinitionKey();
				String activityId = task.getTaskDefinitionKey();
				if(!("development".equals(activityId) || "designdrawing".equals(activityId))){
					if(!activityId.equals(business.getFlag())){
						continue;
					}
				}
				if (b == null) {
					continue;
				}
				Act e = new Act();
				e.setTodotask(todotask);
				e.setProcDef(ProcessDefCache.get(todotask.getProcessDefinitionId()));
				e.setProbusiness(b);
				result.add(e);
			}
		}
		return result;
	}
	
	/**
	 * 获取实体对象数据并插入工作流实体
	 * @author yuxiang.xiong
	 * 2017年9月13日 上午11:34:03
	 * @param act
	 * @param procDefKey  环节
	 * @param procInsId   流程实例id
	 * @return
	 */
	public Act getObjectData(Act act,EbayProcessBusiness business) {
		EbayProcessBusiness busine = processBusinessDao.getByProcInsId(business);
		if(busine==null){
			return null;
		}
		act.setProbusiness(busine);
		return act;
	}
	/**
	 * 提交
	 * @author yuxiang.xiong
	 * 2017年9月12日 下午2:14:26
	 * @param process   流程实体bean，存放业务数据和流程所需参数
	 */
	@Transactional(readOnly = false)
	public void complete(ProcessBean<EbayProcessBusiness> process) {
		if (StringUtils.isEmpty(process.getProcInsId())
				|| StringUtils.isEmpty(process.getAct().getProcInsId())) {
			process.getAct().setProcInsId(
					getProcInsId(process.getAct().getTaskId()));
		}
		
		// 添加意见
		if (StringUtils.isNotBlank(process.getAct().getProcInsId())
				&& StringUtils.isNotBlank(process.getAct().getComment())) {
			taskService.addComment(process.getAct().getTaskId(), process.getAct()
					.getProcInsId(), process.getAct().getComment());
		}
		Map<String, Object> vars = process.getVars();  //获取流程需要的参数
		// 设置流程变量
		if (vars == null) {
			vars = Maps.newHashMap();
		}
		vars = getAssignUser(vars);
		// 提交任务
		taskService.complete(process.getAct().getTaskId(), vars);
		/*
		 * ebay临时业务数据处理
		 */
		EbayProcessBusiness business = process.getData();
		business.setProcInsId(process.getAct().getProcInsId());
		processBusinessDao.updateByProcInsId(business);
	}
	/**
	 * 获取实例id
	 * @param taskId
	 * @return
	 */
	public String getProcInsId(String taskId){
		Task task =taskService.createTaskQuery().taskId(taskId).singleResult();
		return task.getProcessInstanceId();
	}
	/**
	 * 回退组任务
	 * @author yuxiang.xiong
	 * 2017年9月14日 下午5:36:21
	 */
	@Transactional(readOnly = false)
	public void setAssignee(String taskId){
		processEngine.getTaskService().setAssignee(taskId, null);
	}
	
	/**
	 * 获取任务
	 * @author yuxiang.xiong
	 * 2017年9月15日 下午2:18:39
	 * @param taskId 任务ID
	 * @return
	 */
	public Task getTask(String taskId){
		return taskService.createTaskQuery().taskId(taskId).singleResult();
	}
	
	/**
	 * 添加组成员
	 * @author yuxiang.xiong
	 * 2017年9月14日 下午5:49:40
	 * @param act
	 * @param userId  需要添加的人员id
	 */
	public void addGroupUser(String taskId, String userId) {
		processEngine.getTaskService().addCandidateUser(taskId, userId);
	}
	
	/**
	 * 根据流程实例id查询
	 * @author yuxiang.xiong
	 * 2017年9月27日 上午11:55:04
	 * @param business
	 * @return
	 */
	public EbayProcessBusiness getByProcInsId(EbayProcessBusiness business){
		return processBusinessDao.getByProcInsId(business);
	}
	
	/**
	 * 结束流程
	 * @author yuxiang.xiong
	 * 2017年9月27日 下午3:58:23
	 * @param taskId   任务id
	 * @throws Exception
	 */
	public void endProcess(String taskId) throws Exception {
		ActivityImpl endActivity = findActivitiImpl(taskId, "end");
		commitProcess(taskId, null, endActivity.getId());
	} 
  
	
	/**
	 * 根据流程节点id操作流程
	 * @author yuxiang.xiong
	 * 2017年9月27日 下午4:27:38
	 * @param taskId
	 * @param variables
	 * @param activityId
	 * @throws Exception
	 */
	private void commitProcess(String taskId, Map<String, Object> variables,
			String activityId) throws Exception {
		if (variables == null) {
			variables = Maps.newHashMap();
		}
		// 跳转节点为空，默认提交操作
		if (StringUtils.isBlank(activityId)) {
			taskService.complete(taskId, variables);
		} else {// 流程转向操作
			turnTransition(taskId, activityId, variables);
		}
	}
     
	/**
	 * 流程转向
	 * @author yuxiang.xiong
	 * 2017年9月27日 下午4:28:53
	 * @param taskId
	 * @param activityId
	 * @param variables
	 * @throws Exception
	 */
	private void turnTransition(String taskId, String activityId,
			Map<String, Object> variables) throws Exception {
		// 当前节点
		ActivityImpl currActivity = findActivitiImpl(taskId, null);
		// 清空当前流向
		List<PvmTransition> oriPvmTransitionList = clearTransition(currActivity);

		// 创建新流向
		TransitionImpl newTransition = currActivity.createOutgoingTransition();
		// 目标节点
		ActivityImpl pointActivity = findActivitiImpl(taskId, activityId);
		// 设置新流向的目标节点
		newTransition.setDestination(pointActivity);

		// 执行转向任务
		taskService.complete(taskId, variables);
		// 删除目标节点新流入
		pointActivity.getIncomingTransitions().remove(newTransition);

		// 还原以前流向
		restoreTransition(currActivity, oriPvmTransitionList);
	}
     
	/**
	 *  还原流向
	 * @author yuxiang.xiong
	 * 2017年9月27日 下午4:29:34
	 * @param activityImpl
	 * @param oriPvmTransitionList
	 */
	private void restoreTransition(ActivityImpl activityImpl,
			List<PvmTransition> oriPvmTransitionList) {
		// 清空现有流向
		List<PvmTransition> pvmTransitionList = activityImpl
				.getOutgoingTransitions();
		pvmTransitionList.clear();
		// 还原以前流向
		for (PvmTransition pvmTransition : oriPvmTransitionList) {
			pvmTransitionList.add(pvmTransition);
		}
	}
 
	/**
	 * 清空流向
	 * @author yuxiang.xiong
	 * 2017年9月27日 下午4:30:08
	 * @param activityImpl
	 * @return
	 */
	private List<PvmTransition> clearTransition(ActivityImpl activityImpl) {
		// 存储当前节点所有流向临时变量
		List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
		// 获取当前节点所有流向，存储到临时变量，然后清空
		List<PvmTransition> pvmTransitionList = activityImpl
				.getOutgoingTransitions();
		for (PvmTransition pvmTransition : pvmTransitionList) {
			oriPvmTransitionList.add(pvmTransition);
		}
		pvmTransitionList.clear();

		return oriPvmTransitionList;
	}

	/**
	 * 查找活动节点
	 * @author yuxiang.xiong
	 * 2017年9月27日 下午4:30:39
	 * @param taskId
	 * @param activityId
	 * @return
	 * @throws Exception
	 */
	private ActivityImpl findActivitiImpl(String taskId, String activityId)
			throws Exception {
		// 取得流程定义
		ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTaskId(taskId);

		// 获取当前活动节点ID
		if (StringUtils.isBlank(activityId)) {
			activityId = getTask(taskId).getTaskDefinitionKey();
		}

		// 根据流程定义，获取该流程实例的结束节点
		if (activityId.toUpperCase().equals("END")) {
			for (ActivityImpl activityImpl : processDefinition.getActivities()) {
				List<PvmTransition> pvmTransitionList = activityImpl
						.getOutgoingTransitions();
				if (pvmTransitionList.isEmpty()) {
					return activityImpl;
				}
			}
		}

		// 根据节点ID，获取对应的活动节点
		ActivityImpl activityImpl = ((ProcessDefinitionImpl) processDefinition)
				.findActivity(activityId);

		return activityImpl;
	}
	  
	/**
	 * 获取流程定义
	 * @author yuxiang.xiong
	 * 2017年9月27日 下午4:31:00
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	private ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(
			String taskId) throws Exception {
		// 取得流程定义
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(getTask(taskId)
						.getProcessDefinitionId());

		if (processDefinition == null) {
			throw new Exception("流程定义未找到!");
		}

		return processDefinition;
	}
	
	
	
}
