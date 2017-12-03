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
            height: 110px;
        }
        #contentTable{
            table-layout:fixed;
        }
    </style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/productcollection/productCollection/">竞品采集列表</a></li>
		<shiro:hasPermission name="productcollection:productCollection:edit"><li><a href="${ctx}/productcollection/productCollection/form">竞品采集添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="productCollection" action="${ctx}/productcollection/productCollection/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div class="row">
			<label>站点：</label>
			<form:select class="required" path="siteId" style="width: 200px;">
		 	   <form:option value="" label="--请选择--" />
		 	   <form:options items="${siteList}" itemLabel="siteShortName" itemValue="id" htmlEscape="false" />
		    </form:select> 
		    <label>销售类型：</label>
			<form:select class="required" path="saleTypeId" style="width: 200px;">
		 	   <form:option value="" label="--请选择--" />
		 	   <form:option value="1" label="海外仓精品" />
		 	   <form:option value="2" label="虚拟海外仓精品" />
		    </form:select> 
		    <label>销售组：</label> 
				<form:select class="required" path="saleGroupId" style="width: 200px;">
		 	   		<form:option value="" label="--请选择--" />
		 	   		<form:options items="${groupList}" itemLabel="sellerGroupName" itemValue="groupId" htmlEscape="false" />
		   		</form:select>
		</div>
		</br>
		<div class="row">
			<label>商品名称：</label> 
				<form:input path="productName" htmlEscape="false" maxlength="40" class="input-medium" />
			<label>采集人：</label> 
				<form:input path="createName" htmlEscape="false" maxlength="40" class="input-medium" />
			<label>采集时间：</label>
				<input id="dateStart" name="dateStart" value="${dateStart}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" style="width:200px"/>
				至
				<input id="dateEnd" name="dateEnd" value="${dateEnd}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" style="width:200px"/>
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		</div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="180">图片</th>
				<th>站点</th>
				<th>销售类型</th>
				<th>销售组</th>
				<th>商品名称</th>
				<th>商品链接</th>
				<th>采集人</th>
				<th>采集时间</th>
				<th>步骤</th>
				<shiro:hasPermission name="productcollection:productCollection:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody id="contentTable">
		<c:forEach items="${page.list}" var="productCollection">
			<tr>
				<td>
					<img src="${fns:getImageUrl()}${productCollection.imgUrl}" width="150" height="150" class="img-polaroid"/>
				</td>
				<td>
					${productCollection.siteName}
				</td>
				<td>
					<c:if test="${productCollection.saleTypeId == 1}">海外仓精品</c:if>
					<c:if test="${productCollection.saleTypeId == 2}">虚拟海外仓精品</c:if>
				</td>
				<td>
					${productCollection.saleGroupName}
				</td>
				<td>
					${productCollection.productName}
				</td>
				<td style="word-wrap:break-word;">
					<a href="${productCollection.productUrl}" target="_blank">${productCollection.productUrl}</a>
				</td>
				<td>
					${fns:getUserById(productCollection.operator).name}
				</td>
				<td>
					<fmt:formatDate value="${productCollection.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<c:if test="${productCollection.status == 0}">产品采集</c:if>
					<c:if test="${productCollection.status == 1}">产品测算</c:if>
					<c:if test="${productCollection.status == 2}">产品开发</c:if>
					<c:if test="${productCollection.status == 3}">review产品数量</c:if>
					<c:if test="${productCollection.status == 4}">设计制图</c:if>
					<c:if test="${productCollection.status == 5}">资料完善</c:if>
					<c:if test="${productCollection.status == 6}">Review</c:if>
					<c:if test="${productCollection.status == 7}">放弃开发</c:if>
					<c:if test="${productCollection.status == 8}">暂时放弃开发</c:if>
				</td>
				<shiro:hasPermission name="productcollection:productCollection:edit"><td>
				<c:if test="${productCollection.status == 0 or productCollection.status == null}">
					<a href="${ctx}/productcollection/productCollection/form?id=${productCollection.id}">编辑</a>
				</c:if>
    				<a href="${ctx}/productcollection/productCollection/detail?id=${productCollection.id}">查看</a>
<%-- 					<a href="${ctx}/productcollection/productCollection/delete?id=${productCollection.id}" onclick="return confirmx('确认要删除该竞品采集吗？', this.href)">删除</a> --%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>