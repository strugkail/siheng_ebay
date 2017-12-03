<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>竞品采集管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		  /**
		 * 签收任务
		 */
		function claim(taskId) {
			$.get('${ctx}/act/ebaytask/claim' ,{taskId: taskId}, function(data) {
				if (data == 'true'){
		        	top.$.jBox.tip('签收完成');
		            location = '${ctx}/designdrawing/designDrawing?type='+$("#type").val();
				}else{
		        	top.$.jBox.tip('签收失败');
				}
		    });
		}
		/**
		 * 回退任务 
		 */
		function roolback(taskId){
			$.get('${ctx}/act/ebaytask/robackgroup' ,{taskId: taskId}, function(data) {
				if (data == 'true'){
		        	top.$.jBox.tip('回退完成'); 
		            location = '${ctx}/designdrawing/designDrawing?type='+$("#type").val();
				}else{
		        	top.$.jBox.tip('回退失败'); 
				}
		    });
		}
	</script>
	<style type="text/css">
        #contentTable tr th{
            text-align: center;
        }
        #contentTable tr td{
            text-align: center;
        }
        #contentTable tr td img{
            width: 150px;
            height: 150px;
        }
        #contentTable{
            table-layout:fixed;
        }
    </style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/productcollection/productCollection/">设计作图列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="designDrawing" action="${ctx}/designdrawing/designDrawing/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="type" name="type" type="hidden" value="${type}"/>
		<div class="row"> 
			<label>商品名称：</label>
				<form:input path="productName" htmlEscape="false" maxlength="40" class="input-medium" />
			<label>产品代码：</label> 
				<form:input path="sysParentCode" htmlEscape="false" maxlength="40" class="input-medium" />
			<label>是否签收：</label>
        	<form:select class="required" path="sign" style="width: 200px;">
		 	   		<form:option value="" selected="selected" label="--请选择--" />
		 	   		<form:option value="1" label="已签收" />
		 	   		<form:option value="2" label="未签收" />
		   	</form:select>
			<label>开发时间：</label> 
			<input id="dateStart" name="dateStart" value="${dateStart}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" style="width:200px"/>
			至
			<input id="dateEnd" name="dateEnd" value="${dateEnd}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" style="width:200px"/>		
			
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		</div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="180">图片</th> 
				<th>商品名称</th>
				<th>商品链接</th>
				<th>母代码</th>
				<th>开发人</th>
				<th>开发时间</th>
<%-- 				<shiro:hasPermission name="productcollection:productCollection:edit"> --%>
				<th>操作</th>
<%-- 				</shiro:hasPermission> --%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="act">
			<c:set var="task" value="${act.todotask}" />
			<c:set var="vars" value="${act.vars}" />
			<c:set var="procDef" value="${act.procDef}" /><%--
			<c:set var="procExecUrl" value="${act.procExecUrl}" /> --%>
			<c:set var="status" value="${act.status}" />
		    <c:set var="processBusiness" value="${act.probusiness}" />
			<tr>
				<td>
					<img src="${fns:getImageUrl()}${processBusiness.imgUrl}" width="150" height="150" class="img-polaroid"/>
				</td>
				<td>
					${processBusiness.productName}
				</td>
				<td style="word-wrap:break-word">
					<a href="${processBusiness.productUrl}" target="_blank">${processBusiness.productUrl}</a>
				</td>
				<td>
					${processBusiness.sysParentCode}
				</td>
				<td>
					${processBusiness.createName}
				</td>
				<td>
					<fmt:formatDate value="${processBusiness.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td> 
<%--     				<a href="${ctx}/designdrawing/designDrawing/form?id=${processBusiness.businessId}">编辑</a> --%>
					<c:if test="${empty task.assignee}">
						<a href="javascript:claim('${task.id}');">签收任务</a>
					</c:if>
					<c:if test="${not empty task.assignee}"><%--
						<a href="${ctx}${procExecUrl}/exec/${task.taskDefinitionKey}?procInsId=${task.processInstanceId}&act.taskId=${task.id}">办理</a> --%>
						<a href="${ctx}/act/ebaytask/form?taskId=${task.id}&taskName=${fns:urlEncode(task.name)}&taskDefKey=${task.taskDefinitionKey}&procInsId=${task.processInstanceId}&procDefId=${task.processDefinitionId}&saleType=${processBusiness.saleType}&businessId=${processBusiness.businessId}">任务办理</a>
					</c:if>
					<c:if test="${not empty task.assignee}"><%--
						<a href="${ctx}${procExecUrl}/exec/${task.taskDefinitionKey}?procInsId=${task.processInstanceId}&act.taskId=${task.id}">办理</a> --%>
					    <a href="javascript:roolback('${task.id}');">回退任务</a>
					</c:if>
					<a target="_blank" href="${pageContext.request.contextPath}/act/diagram-viewer?processDefinitionId=${task.processDefinitionId}&processInstanceId=${task.processInstanceId}">跟踪</a><%-- 
					<a target="_blank" href="${ctx}/act/task/trace/photo/${task.processDefinitionId}/${task.executionId}">跟踪2</a> 
					<a target="_blank" href="${ctx}/act/task/trace/info/${task.processInstanceId}">跟踪信息</a> --%>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>