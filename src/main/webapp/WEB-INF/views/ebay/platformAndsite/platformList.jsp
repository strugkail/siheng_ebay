<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>平台管理</title>
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
		<li class="active"><a href="${ctx}/shops/platform/">平台列表</a></li>
		<shiro:hasPermission name="shops:platform:edit"><li><a href="${ctx}/shops/platform/form">平台添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="platform" action="${ctx}/shops/platform/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>平台名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="128" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>平台名称</th>
				<th style="display: none;" >平台码</th>
				<th>站点数量</th>
				<th>店铺数量</th>
				<th>备注</th>
				<shiro:hasPermission name="shops:platform:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="platform">
			<tr>
				<td><a href="${ctx}/shops/platform/form?id=${platform.id}">
					${platform.name}
				</a></td>
				<td style="display: none;" >
					
				</td>
				<td>
					${platform.countSite}
				</td>
				<td>
					${platform.countShop}
				</td>
				<td>
					${platform.remark}
				</td>
				<shiro:hasPermission name="shops:platform:edit"><td>
    				<a href="${ctx}/shops/platform/form?id=${platform.id}">修改</a>
					<a href="${ctx}/shops/platform/delete?id=${platform.id}" onclick="return confirmx('确认要删除该平台吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>