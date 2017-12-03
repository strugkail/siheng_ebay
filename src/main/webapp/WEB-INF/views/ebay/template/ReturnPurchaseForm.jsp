<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>退货</title>
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
					url : '${ctx}/template/retpurchase/checkTempName',
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
		<li><a href="${ctx}/template/retpurchase/">退货</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="purchase" action="${ctx}/template/retpurchase/save" method="post" class="form-horizontal">
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
			<label class="control-label">ebay退货策略：</label>
			<div class="controls">
			<form:input path="refundPolice" htmlEscape="false" maxlength="128" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
		<label style="margin-left: 100px;">接受退货：</label>
		<form:select class="" path="reciveReturn" style="width: 280px; margin-left: 15px;"> 
			<form:option value="" label="--请选择--" />
			<form:options items="${fns:getDictList('isRceivedReturn')}" itemLabel="label" itemValue="value" htmlEscape="false" />
		</form:select>
		</div>
			<div class="control-group">
		<label style="margin-left: 100px;">退货方式：</label>
		<form:select class="" path="returnMode" style="width: 280px; margin-left: 15px;">
			<form:option value="" label="--请选择--" /> 
			<form:options items="${fns:getDictList('returnMode')}" itemLabel="label" itemValue="value" htmlEscape="false" />
		</form:select>
		</div>
			<div class="control-group">
		<label style="margin-left: 70px;">接收退货期限：</label>
		<form:select class="" path="reciveReturnPeriod" style="width: 280px; margin-left: 15px;">
			<form:option value="" label="--请选择--" /> 
			<form:options items="${fns:getDictList('reciveReturnPeriod')}" itemLabel="label" itemValue="value" htmlEscape="false" />
		</form:select>
		</div>
			<div class="control-group">
			<label class="control-label">holiday return：</label>
			<div class="controls">
				<form:input path="holidayReturn" htmlEscape="false" maxlength="128" class="input-xlarge "/>
			</div>
		</div>
			<div class="control-group">
		<label style="margin-left: 70px;">退货邮费承担：</label>
		<form:select class="" path="returnPostage" style="width: 280px; margin-left: 15px;">
			<form:option value="" label="--请选择--" /> 
			<form:options items="${fns:getDictList('returnPostage')}" itemLabel="label" itemValue="value" htmlEscape="false" />
		</form:select>
		</div>
			<div class="control-group">
			<label class="control-label">RestockingFeeValue：</label>
			<form:select class="" path="restockingFeeValue" style="width: 280px; margin-left: 15px;">
			<form:option value="" label="--请选择--" /> 
			<form:options items="${fns:getDictList('RestockingFeeValue')}" itemLabel="label" itemValue="value" htmlEscape="false" />
		</form:select>
		</div>
			<div class="control-group">
			<label class="control-label">退货说明：</label>
			<div class="controls">
					<textarea name="returnPolicy"
					style="width: 600px; height: 100px; outline: none; resize: none">${purchase.returnPolicy}</textarea>
			</div>
		</div>
	
		<div class="form-actions">
		<input id="btnSubmit" class="btn btn-primary" type="" onclick="save()" value="保 存"/>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>