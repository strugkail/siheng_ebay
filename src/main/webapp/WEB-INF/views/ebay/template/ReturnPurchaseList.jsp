<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>退货列表</title>
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
		<li class="active"><a href="${ctx}/template/retpurchase/list">退货</a></li>
		<li><a href="${ctx}/template/retpurchase/form?flag=add">添加</a></li>
	</ul>

	<form:form id="searchForm" modelAttribute="purchase" action="${ctx}/template/retpurchase/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		<ul class="ul-form">
		<li><label>模板名称：</label> 
				<form:input path="templateName" htmlEscape="false" maxlength="40" class="input-medium" /></li>
       <li><label>退货策略：</label> 
				<form:input path="refundPolice" htmlEscape="false" maxlength="40" class="input-medium" />
	   </li>
	    <li><label>退货方式：</label> 
				<form:select class="required" path="returnMode" style="width: 280px; margin-left: 15px;">
			<form:option value="" label="--请选择--" /> 
			<form:options items="${fns:getDictList('returnMode')}" itemLabel="label" itemValue="value" htmlEscape="false" />
		</form:select>
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
				<th>ebay退货策略</th>
				<th>退货方式</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="product">
				<tr>
					<td>${product.templateName}</td>
					<td>
						${product.refundPolice}
					</td>
					<td>
					<c:if test="${product.returnMode =='1'}">Money Back</c:if>
					<c:if test="${product.returnMode =='2'}">Money back or replacement(buyer's choice)</c:if>
					<c:if test="${product.returnMode =='3'}">Money back or exchange(buyer's choice)</c:if>
					</td> 
					<td>
						<a href="${ctx}/template/retpurchase/form?flag=edit&&id=${product.id}">编辑</a>
						<a href="${ctx}/template/retpurchase/delete?id=${product.id}">删除</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="pagination">${page}</div>

</body>
</html>