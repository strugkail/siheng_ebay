<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>站点管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
		
				
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
		<li><a href="${ctx}/shops/platformSite/">站点列表</a></li>
		<li class="active"><a href="${ctx}/shops/platformSite/form?id=${platformSite.id}">站点<shiro:hasPermission name="shops:platformSite:edit">${not empty platformSite.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="shops:platformSite:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="platformSite" action="${ctx}/shops/platformSite/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		
		<div style='margin-left: 141px; margin-top: 10px; margin-bottom: 10px'>
				
		</div>
		<div class="control-group" >
			<label class="control-label">平台：</label>
			<div class="controls">
				<form:select path="platformId" cssClass="input-xlarge" style="width: 285px;">
					<form:option value="">请选择</form:option>
					<form:options items="${tableList}" itemLabel="name"
						itemValue="id" htmlEscape="false" />
				</form:select>
			</div>
		</div>
		<!-- <div class="control-group">
			<label class="control-label">平台：</label>
			
			<div class="controls" id="ptidID_1">
				<select style="width: 285px;" onchange="func(this)" id="sele"></select>
			</div>
			
		</div> -->
		<div class="control-group">
			<label class="control-label">站点名称：</label>
			<div class="controls">
				<form:input path="siteName" htmlEscape="false" maxlength="128" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remark" htmlEscape="false" maxlength="128" class="input-xlarge "/>
			</div>
		</div>
		<%--< div class="control-group"> 
			<label class="control-label">货币：</label>
			<div class="controls">
				<form:input path="currency" htmlEscape="false" maxlength="16" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">站点名称缩写：</label>
			<div class="controls">
				<form:input path="siteShortName" htmlEscape="false" maxlength="16" class="input-xlarge "/>
			</div>
		</div>										
		<div class="control-group">
			<label class="control-label">paypal卖家账号：</label>
			<div class="controls">
				<form:input path="paypalAccount" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div> --%>
		<div class="form-actions">
			<shiro:hasPermission name="shops:platformSite:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>