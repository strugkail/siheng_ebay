<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>站点管理</title>
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
		$(function(){
			$.ajax({
					url : '${ctx}/shops/platform/selectList',
					data : null,
					success : function(data) {
					//	console.log()
					$('#sele').append("<option selected='selected'>"+"--请选择--"+"</option>");
						 for (var i = 0; i < data.length; i++) {
							//console.log(date[i].id)
							var id =data[i].id;
							var name =data[i].name;
							
							$('#sele').append("<option value='"+id+"'>"+name+"</option>");
							
						} 
					},
					error : function(XMLHttpRequest, textStatus, errorThrown){			 
						errorCapture(XMLHttpRequest, textStatus, errorThrown);	
					}
			})
		})
		
		function func(obj) {
		//获取被选中的option标签
		var vs = $('select option:selected').val();
		$(":input[name=platformId]").val(vs);
	}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/shops/platformSite/">站点列表</a></li>
		<shiro:hasPermission name="shops:platformSite:edit"><li><a href="${ctx}/shops/platformSite/form">站点添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="platformSite" action="${ctx}/shops/platformSite/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<%-- 	<li><label>站点名称：</label>
				<form:input path="siteName" htmlEscape="false" maxlength="128" class="input-medium"/>
			</li>
			<li><label>paypal卖家账号：</label>
				<form:input path="paypalAccount" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li> --%>
			<div class="control-group">
			<div class="controls">
			<label class="control-label">平台：</label>
				<form:select path="platformId" cssClass="input-xlarge" style="width: 285px;">
					<form:option value="">请选择</form:option>
					<form:options items="${tableList}" itemLabel="name"
						itemValue="id" htmlEscape="false" />
				</form:select>
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
			</div>
		</div>
			<!-- <li class="clearfix"></li> -->
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>站点名称</th>
				<th style="display: none;">货币</th>
				<th style="display: none;">站点名称缩写</th>
				<th>店铺数量</th>
				<th>备注</th>
				<shiro:hasPermission name="shops:platformSite:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="platformSite">
			<tr>
				<td><a href="${ctx}/shops/platformSite/form?id=${platformSite.id}">
					${platformSite.siteName}
				</a></td>
				<td style="display: none;">
				
				</td>
				<td style="display: none;">
			
				</td>
				<td>
					${platformSite.countShop}
				</td>
				<td>
					${platformSite.remark}
				</td>
				<shiro:hasPermission name="shops:platformSite:edit"><td>
    				<a href="${ctx}/shops/platformSite/form?id=${platformSite.id}">修改</a>
					<a href="${ctx}/shops/platformSite/delete?id=${platformSite.id}" onclick="return confirmx('确认要删除该站点吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>