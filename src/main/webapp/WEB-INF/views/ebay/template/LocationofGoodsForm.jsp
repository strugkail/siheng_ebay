<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品所在地</title>
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
		
         function save(){
        	 
        	   var templateName = $("#templateName").val();
        	   var flag = $("#flag").val();
	     		$.ajax({ 
					url : '${ctx}/template/goods/checkTempName',
					data : {
						templateName : templateName,
						flag : flag
					},
					success : function(data) {
					     if(data==false){
					    	 alertx('模板名称重复');
					     }else{
					    	 $("#inputForm").submit();
					     }
					},
					error : function(XMLHttpRequest, textStatus, errorThrown){			 
						errorCapture(XMLHttpRequest, textStatus, errorThrown);	
					}
				})
         }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/template/goods/">商品所在地</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="goods" action="${ctx}/template/goods/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="flag" />
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">模板名称：</label>
			<div class="controls">
				<form:input path="templateName" htmlEscape="false" maxlength="128" class="input-xlarge required"/>
			</div>
		</div>
	
		<div class="control-group">
		<label style="margin-left: 105px;">销售类型：</label>
		<form:select class="" path="saleType" style="width: 280px; margin-left: 4px;">
			<form:option value="" label="--请选择--" />
			<form:options items="${fns:getDictList('saleType')}" itemLabel="label" itemValue="value" htmlEscape="false" />
		</form:select>
		</div>
			<div class="control-group">
		<label style="margin-left: 120px;">站点：</label>
		<form:select class="" path="siteId" style="width: 280px; margin-left: 15px;"> 
			<form:option value="" label="--请选择--" />
			<form:options items="${sitelist}" itemLabel="siteName" itemValue="id" htmlEscape="false" />
		</form:select>
		</div>
			<div class="control-group">
			<label class="control-label">商品地址：</label>
			<div class="controls">
			<form:input path="goodsAddr" htmlEscape="false" maxlength="128" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮编：</label>
			<div class="controls">
			<form:input path="postCode" htmlEscape="false" maxlength="128" class="input-xlarge "/>
			</div>
		</div>
			<div class="control-group">
		<label style="margin-left: 120px;">国家：</label>
		<form:select class="" path="country" style="width: 280px; margin-left: 15px;">
			<form:option value="" label="--请选择--" /> 
			<form:options items="${countryList}" itemLabel="cnName" itemValue="id" htmlEscape="false" />
		</form:select>
		
		</div>
			<div class="control-group">
			<label class="control-label">店铺（用户）账号：</label>
			<div class="controls">
			<form:input path="sellerName" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		
		
		<div class="form-actions">
		<input id="btnSubmit" class="btn btn-primary" type="" onclick = "save();"value="保 存"/>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>