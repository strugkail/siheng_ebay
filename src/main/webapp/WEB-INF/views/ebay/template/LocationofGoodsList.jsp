<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>商品所在地</title>
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
		<li class="active"><a href="${ctx}/template/goods/list">商品所在地</a></li>
		<li><a href="${ctx}/template/goods/form?flag=add">添加</a></li>
	</ul>

	<form:form id="searchForm" modelAttribute="goods" action="${ctx}/template/goods/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		<ul class="ul-form">
		<li><label>模板名称：</label> 
				<form:input path="templateName" htmlEscape="false" maxlength="40" class="input-medium" /></li>
       <li><label>商品地址：</label> 
				<form:input path="goodsAddr" htmlEscape="false" maxlength="40" class="input-medium" />
	   </li>
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
				<th>商品地址</th>
				<th>邮编</th>
				<th>国家</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="product">
				<tr>
					<td>${product.templateName}</td>
					<td>
						${product.goodsAddr}
					</td>
					<td>
						${product.postCode}
					</td>
					<td>
						${product.country} 
					</td> 
					<td>
						<a href="${ctx}/template/goods/form?flag=edit&&id=${product.id}">编辑</a>
						<a href="${ctx}/template/goods/delete?id=${product.id}">删除</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="pagination">${page}</div>

</body>
</html>