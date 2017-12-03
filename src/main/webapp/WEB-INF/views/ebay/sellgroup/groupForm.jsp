<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>销售组管理</title>
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sellgroup/group/">销售组列表</a></li>
		<li class="active">
			<a href="${ctx}/sellgroup/group/form?id=${group.id}">销售组
		<shiro:hasPermission name="sellgroup:group:edit">
			${not empty group.id?'修改':'添加'}
		</shiro:hasPermission>
		<shiro:lacksPermission name="sellgroup:group:edit">
			查看
		</shiro:lacksPermission>
		</a>
		</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="group" action="${ctx}/sellgroup/group/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group"  style="display:none;">
			<label class="control-label" >销售组id：</label>
			<div class="controls">
				<form:input path="groupId" htmlEscape="false" maxlength="11" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">组名：</label>
			<div class="controls">
				<form:input path="sellerGroupName" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">刊登账户：</label>
			<div class="controls">
				<form:select path="sellerList" class="required input-xlarge" multiple="true" minlenght="1">
					<form:options items="${sellerList}" itemLabel="sellerName"	itemValue="id" htmlEscape="false" />
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人员列表：</label>
			<div class="controls">
				<form:select path="userList" class="required input-xlarge"   multiple="true" minlenght="1">
					<form:options items="${userList}" itemLabel="LoginName"	itemValue="id" htmlEscape="false" />
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sellgroup:group:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>