<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>店铺管理</title>
	<meta name="decorator" content="bootstrap3"/>
    <script type="text/javascript" src="<%=request.getContextPath() %>/static/My97DatePicker/WdatePicker.js"></script> 
	<script type="text/javascript">
		$(document).ready(function() {
		});

		$(function() {//页面加载完成就执行的方法
			$.ajax({
				url : '${ctx}/shops/platform/selectList',
				data : null,
				success : function(date) {
					//	console.log()
					$('#sel_pt').append(
							"<option selected='selected'>" + "--请选择--"
									+ "</option>");
					for (var i = 0; i < date.length; i++) {
						//console.log(date[i].id)
						var id = date[i].id;
						var names = date[i].name
						if($("#platformId").val()==id){
							$('#sel_pt').append(
									"<option value='"+id+"' selected='selected'>" + names + "</option>");
							funcp(obj);
							}else{
							
						$('#sel_pt').append(
								"<option value='"+id+"'>" + names + "</option>");
						}

					}
					
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){			 
					errorCapture(XMLHttpRequest, textStatus, errorThrown);	
				}
			})

		})
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function funcp(obj) {
			//获取被选中的option标签
			var vsp = $('#sel_pt').find("option:selected").val();
			$("#platformId").val(vsp);
			$('#sell').empty();//切换平台时，站点清空
			$.ajax({
				url : '${ctx}/shops/platformSite/selectList?platformId='+vsp,
				data : null,
				success : function(date) {
					$('#sell').append(
							"<option selected='selected'>" + "--请选择--"
									+ "</option>");
					for (var i = 0; i < date.length; i++) {
						if($("#siteId").val()==date[i].id){
							$('#sell').append(
									"<option value='"+date[i].id+"' selected='selected'>" + date[i].siteName
											+ "</option>");
						}else{
							$('#sell').append(
									"<option value='"+date[i].id+"'>" + date[i].siteName
											+ "</option>");
						}
						
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){			 
					errorCapture(XMLHttpRequest, textStatus, errorThrown);	
				}
			})
		}
		function func(obj) {
			var vspv = $('#sell').find("option:selected").val();
			$("#siteId").val(vspv);
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<c:if test="${shops.shopStatus == '0'}">
			<li class="active"><a href="#">店铺补录列表</a></li>
		</c:if>
		<c:if test="${shops.shopStatus != '0'}">
			<li class="active"><a href="${ctx}/shops/shops/">店铺列表</a></li>
<%-- 			<shiro:hasPermission name="shops:shops:edit"><li><a href="${ctx}/shops/shops/form">店铺添加</a></li></shiro:hasPermission> --%>
		</c:if>
	</ul>
	<form:form  id="searchForm" modelAttribute="shops" action="${ctx}/shops/shops/" method="post" class="form-inline breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
<%-- 		<ul class="list-inline">
		    <li><label>平台：</label>
			<select  onchange="funcp(this)" id="sel_pt" class="form-control"></select>
				
			</li>
				<input type="text" name="platformId" id="platformId"
					style="display: none;" value="${shops.platformId}">
			<li><label>站点：</label>
				<select style="width: 285px;" onchange="func(this)" id="sell" class="form-control"></select>
				<input type="text" name="siteId"  id="siteId"
					style="display: none;" value="${shops.siteId}">
			</li>
			<li><label>店铺名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li style="display:none"><label>登录名称：</label>
				<form:input path="account" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul> --%>

			<div class="form-group">
				<label>平台</label>
				 <select style="width: 250px;" onchange="funcp(this)" id="sel_pt" class="form-control"></select>
				 <input type="text" name="platformId" id="platformId"
					style="display: none;" value="${shops.platformId}">
			
				<label>站点</label>
				<select  style="width: 250px;"onchange="func(this)" id="sell" class="form-control"></select>
				<input type="text" name="siteId"  id="siteId"
					style="display: none;" value="${shops.siteId}">
			
			</div>
			
			<li><label>店铺标签：</label>
				<form:select class="required" path="shopTagsId" style="width: 200px;">
				<form:option value="" label="--请选择--" />
				<form:options items="${tagsList}" itemLabel="tagsName" itemValue="id" htmlEscape="false" />
			   </form:select>
			</li>
			 <li><label>公司内部命名：</label>
				<form:input path="account" style="height:30px;" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li style="padding-top: 12px;"><label>店铺创建时间：</label> 
				<%-- <form:input path="name" htmlEscape="false" maxlength="40" class="input-medium" /> 
					至 
				<form:input path="name" htmlEscape="false" maxlength="40" class="input-medium" /> --%>
				<input style="height:30px;" id="dateStart" name="dateStart" value="${dateStart}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" style="width:280px;heigth:30px"/>
				至
				<input style="height:30px;" id="dateEnd" name="dateEnd" value="${dateEnd}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" style="width:280px;heigth:30px"/>
			</li>
			<button type="submit" id="btnSubmit" class="btn btn-default">查询</button>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>平台名称</th>
				<th>站点名称</th>
				<th>wish使用名称</th>
				<th>公司内部命名</th>
				<th>描述</th>
				<th>店铺创建时间</th>
				<th>店铺标签</th>
				<shiro:hasPermission name="shops:shops:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="shops">
			<tr>
				<td>${shops.platformName}</td>
				<td>${shops.siteName}</td>
				<td>
					${shops.name}
				</td>
				<td>
					${shops.account}
				</td>
				<td>
					${shops.description}
				</td>
				<td>
					<fmt:formatDate value="${shops.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${shops.shopTagsName}
				</td>
				<shiro:hasPermission name="shops:shops:edit">
				<td>
					<c:if test="${shops.shopStatus == '0'}">
						<a href="${ctx}/shops/shops/form?id=${shops.id}&flag=record">补录</a>
					</c:if>
					<c:if test="${shops.shopStatus != '0'}">
						<a href="${ctx}/shops/shops/form?id=${shops.id}&flag=edit">修改</a>
					    <a href="${ctx}/shops/shops/form?id=${shops.id}&flag=view">查看</a>
					</c:if>
				</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>