<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>供应商推荐</title>
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
		<li class="active"><a href="${ctx}/product/productDev/supplier">供应商推荐</a></li>
		<li><a href="${ctx}/product/productDev/form?flag=add">添加</a></li>
	</ul>

	<form:form id="searchForm" modelAttribute="supplier" action="${ctx}/product/productDev/supplier" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		<ul class="ul-form">
		<li><label>商品名称：</label> 
				<form:input path="productName" htmlEscape="false" maxlength="40" class="input-medium" /></li>
		<li><label>采集时间：</label> 
			<input id="dateStart" name="dateStart" value="${dateStart}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" style="width:200px"/>
			至
			<input id="dateEnd" name="dateEnd" value="${dateEnd}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" style="width:200px"/>
       </li>
       <li><label>供应商名称：</label> 
				<form:input path="supplierName" htmlEscape="false" maxlength="40" class="input-medium" />
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
				<th>图片</th>
				<th>商品名称</th>
				<th>产品链接</th>
				<th>供应商名称</th>
				<th>推荐时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="product">
				<tr>
					<td><img src="${fns:getImageUrl()}${product.imgUrl}"  width="150" height="150" class="img-polaroid"/></td>
					<td>${product.productName}</td>
					<td>
						<a href="${product.productAddr}" target="_blank">${product.productAddr}</a>
					</td>
					<td>
						${product.supplierName}
					</td>
					<td>
						<fmt:formatDate value="${product.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td> 
					<td>
					<a href="${ctx}/product/productDev/form?flag=view&&id=${product.id}">查看</a>
					<%-- 	<a href="${ctx}/product/productDev/form?flag=edit&&id=${product.id}">编辑</a> --%>
							<a href="${ctx}/product/productDev/delete?id=${product.id}">删除</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="pagination">${page}</div>

</body>
</html>