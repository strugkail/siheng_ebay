<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>买家限制列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
     
	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/template/buyer/list">买家限制</a></li>
		<li><a href="${ctx}/template/buyer/form?flag=add">添加</a></li>
	</ul>

	<form:form id="searchForm" modelAttribute="restriction" action="${ctx}/template/buyer/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		<ul class="ul-form">
		<li><label>模板名称：</label> 
				<form:input path="templateName" htmlEscape="false" maxlength="40" class="input-medium" /></li>
	   <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" /></li>
			<li class="clearfix"></li>
		</ul>
		
		
	</form:form>

	<sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed table-nowrap">
		<thead>
			<tr>
				<th>模板名称</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="buyer">
				<tr>
					<td>${buyer.templateName}</td>
					<td>
						<a href="${ctx}/template/buyer/form?flag=edit&&id=${buyer.id}">编辑</a>
						<a href="${ctx}/template/buyer/delete?id=${buyer.id}">删除</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="pagination">${page}</div>

</body>
</html>