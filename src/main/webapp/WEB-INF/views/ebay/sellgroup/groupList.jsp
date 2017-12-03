<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>销售组管理</title>
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sellgroup/group/">销售组列表</a></li>
		<shiro:hasPermission name="sellgroup:group:edit"><li><a href="${ctx}/sellgroup/group/form">销售组添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="group" action="${ctx}/sellgroup/group/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>销售组名称：</label>
				<form:input path="sellerGroupName" htmlEscape="false" maxlength="40" class="input-medium" />
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>销售组名称</th>
				<shiro:hasPermission name="sellgroup:group:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="group">
			<tr>
				<td>${group.sellerGroupName}</td>
				<shiro:hasPermission name="sellgroup:group:edit"><td>
    				<a href="${ctx}/sellgroup/group/form?id=${group.groupId}">修改</a>
					<a href="${ctx}/sellgroup/group/delete?id=${group.groupId}" onclick="return confirmx('确认要删除该销售组吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>