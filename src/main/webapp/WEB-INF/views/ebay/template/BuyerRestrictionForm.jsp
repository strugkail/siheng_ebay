<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>买家限制</title>
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
        	 var violationFreq = $("#violationFreq").val();
        	 var breachTime = $("#breachTime").val();
        	 var noPaymentTimes = $("#noPaymentTimes").val();
        	 var orderBreachTime = $("#orderBreachTime").val();
        	 
        	 if(violationFreq=="" && breachTime!=""){
        			 alertx('请同时选择违反次数和违反时段'); 
        			 return;
        	 }
        	 if(breachTime=="" && violationFreq!=""){
        			 alertx('请同时选择违反次数和违反时段');
        			 return;
        	 }
        	 
        	 if(noPaymentTimes=="" && orderBreachTime!=""){
	    			 alertx('请同时选择未付款次数和违反时段'); 
	    			 return;
	    	 }
	    	 if(orderBreachTime=="" && noPaymentTimes!=""){
	    			 alertx('请同时选择未付款次数和违反时段');
	    			 return;
	    	 }
    	 
	     		$.ajax({ 
					url : '${ctx}/template/buyer/checkTempName',
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
		<li><a href="${ctx}/template/buyer/">买家限制</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="restriction" action="${ctx}/template/buyer/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="flag" />
		<sys:message content="${message}"/>		
			<div class="control-group">
				<label class="control-label ">模板名称：</label>
				<div class="controls">
					<form:input path="templateName" htmlEscape="false" maxlength="128" class="input-xlarge required"/>
				</div>
			</div>
		   <div class="control-group">
				<label style="margin-left: 120px;">站点：</label>
				<form:select path="siteId" style="width: 280px; margin-left: 15px;"> 
					<form:option value="" label="--请选择--" />
					<form:options items="${sitelist}" itemLabel="siteName" itemValue="id" htmlEscape="false" />
				</form:select>
		   </div>
			<div class="control-group">
			     <label class="control-label">买家必须拥有Paypal账户：</label>
				<form:radiobutton path="paypalAccount" value="1" checked="true"/>是
				<form:radiobutton path="paypalAccount" value="0"/>否
			</div>
		    <div class="control-group">
				<label class="control-label">买家政策违反相关</label>
				<div>
					   <label class="control-label">违反次数：</label>
					<form:select  path="violationFreq" style="width: 100px; margin-left: -200px;">
						<form:option value="" label="--请选择--" /> 
						<form:options items="${fns:getDictList('violationFreq')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
					
						<label class="control-label" style="margin-left: 100px;" >违反时段：</label>
					<form:select  path="breachTime" style="width: 100px; margin-left: 120px;">
						<form:option value="" label="--请选择--" /> 
						<form:options items="${fns:getDictList('breachTime')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</div>
				
		   </div>
		
			<div class="control-group">
				<label class="control-label">买家不付款订单相关</label>
				 <div>
					   <label class="control-label">未付款次数：</label>
					<form:select  path="noPaymentTimes" style="width: 100px; margin-left: -200px;">
						<form:option value="" label="--请选择--" /> 
						<form:options items="${fns:getDictList('noPaymentTimes')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
					
					<label class="control-label" style="margin-left: 100px;" >违反时段：</label>
					<form:select  path="orderBreachTime" style="width: 100px; margin-left: 120px;">
						<form:option value="" label="--请选择--" /> 
						<form:options items="${fns:getDictList('orderBreachTime')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
			   </div>
		  </div>
		
		  <div class="control-group">
			<label class="control-label">10天期间限制拍卖次数：</label>
			<form:select  path="limitAuctionsTimes" style="width: 100px; margin-left: 15px;">
				<form:option value="" label="--请选择--" /> 
				<form:options items="${fns:getDictList('limitAuctionsTimes')}" itemLabel="label" itemValue="value" htmlEscape="false" />
			</form:select>
		  </div>
		  
		  <div class="control-group">
			<label class="control-label">买家差评评分限制：</label>
			<form:select  path="scoreRestriction" style="width: 100px; margin-left: 15px;">
				<form:option value="" label="--请选择--" /> 
				<form:options items="${fns:getDictList('scoreRestriction')}" itemLabel="label" itemValue="value" htmlEscape="false" />
			</form:select>
		  </div>
		<div class="form-actions">
		<input id="btnSubmit" class="btn btn-primary" type="" onclick="save();" value="保 存"/>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>