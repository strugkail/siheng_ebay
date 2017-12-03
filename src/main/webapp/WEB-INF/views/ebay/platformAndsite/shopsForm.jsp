<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>店铺管理</title>
<meta name="decorator" content="bootstrap3" />
<script type="text/javascript">
	$(document).ready(
			function() {
				$("#inputForm").validate({
					rules:{
						codePreFix:{
		                   required:true
		               },
		               shopTagsId:{
		                   required:true
		               }
		           },
		           messages:{
		        	   codePreFix:{
		                   required: "不能为空"
		               },
		               shopTagsId:{
		                   required: "不能为空"
		               }
		           },
		           submitHandler : function(form) {
						loading('正在提交，请稍等...');
						form.submit();
					},
					errorContainer : "#messageBox",
					errorPlacement : function(error, element) {
						$("#messageBox").text("输入有误，请先更正。");
						if (element.is(":checkbox")
								|| element.is(":radio")
								|| element.parent().is(
										".input-append")) {
							error.appendTo(element.parent()
									.parent());
						} else {
							error.insertAfter(element);
						}
					}    
		       });
				initCountry();
				$.ajax({ 
					url : '${ctx}/shops/shops/selcountryList',
					data : null,
					success : function(date) {
						//	console.log()
						$('#cnName').append(
								"<option selected='selected'>" + "--请选择--"
										+ "</option>");
						for (var i = 0; i < date.length; i++) {
							//console.log(date[i].id)
							var id = date[i].id;
							var names = date[i].cnName
							$('#cnName').append(
									"<option value='"+id+"'>" + names + "</option>");
							}
					},
					error : function(XMLHttpRequest, textStatus, errorThrown){			 
						errorCapture(XMLHttpRequest, textStatus, errorThrown);	
					}
				})
				//$("#name").focus();
// 				var shopsTag = $("#shopTagsId").val(); 
// 				$("input[type=radio][name=shopTagsId][value='"+shopsTag+"']").prop("checked", true);
				
				if($("#flag").val()=='view'){
				$("input[type='text']").attr("disabled", "disabled");
				$("select").attr("disabled",true);
				 $("#btnSubmit").hide();
				}
				 
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
				var namr = $("#platformName").val();
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
   function insertCountry(){
	   var id = $("#cnName").val();
   $.ajax({
			url : '${ctx}/shops/shops/getcountry',
			type : 'post',
			data : {
				id : id
			},
			success : function(data) {
				var count = $("#trcountry tr").length;
			    var id = data.id;
			    var code = data.code;
			    var gbName = data.gbName;
			    var cnName = data.cnName;
			    var content = '<tr id="coutry'+id+'"><td> '+code+'<input type="text" style="display: none;" name="countryIds['+count+']" value="'+id+'"/></td>'
				   +'<td> '+gbName+'</td>'+'<td> '+cnName+'</td>'
				   +'<td><a '
					+'onclick="deletTr('+id+');">删除</a></td></tr>'
				   $("#trcountry").append(content);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){			 
				errorCapture(XMLHttpRequest, textStatus, errorThrown);	
			}
		}) 
   }
    function initCountry(){
	   var id = $("#id").val();
	   
	   $.ajax({
			url : '${ctx}/shops/shops/getcountrys',
			type : 'post',
			data : {
				id : id
			},
			success : function(data) {
				for(var i=0;i<data.length;i++){
				var count =i;
			    var id = data[i].id;
			    var code = data[i].code;
			    var gbName = data[i].gbName;
			    var cnName = data[i].cnName;
			    var content = '<tr id="coutry'+id+'"><td> '+code+'<input type="text" style="display: none;" name="countryIds['+count+']" value="'+id+'"/></td>'
				   +'<td> '+gbName+'</td>'+'<td> '+cnName+'</td>'
				   +'<td><a '
					+'onclick="deletTr('+id+');">删除</a></td></tr>'
				   $("#trcountry").append(content);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){			 
				errorCapture(XMLHttpRequest, textStatus, errorThrown);	
			}
		}) 
   } 
   function deletTr(id){
	   $("#coutry"+id).remove();
   }
   
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<c:if test="${shops.shopStatus == '0'}">
			<li class="active"><a href="#">店铺补录</a></li>
		</c:if>
		<c:if test="${shops.shopStatus != '0'}">
		<li><a href="${ctx}/shops/shops/">店铺列表</a></li>
		<li class="active"><a
			href="${ctx}/shops/shops/form?id=${shops.id}">店铺<shiro:hasPermission
					name="shops:shops:edit">${not empty shops.id?'修改':'添加'}</shiro:hasPermission>
				<shiro:lacksPermission name="shops:shops:edit">查看</shiro:lacksPermission></a></li>
		</c:if>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="shops"
		action="${ctx}/shops/shops/save" method="post" class="form-inline form-horizontal">
		<form:hidden path="id" />
		
		<sys:message content="${message}" />

       	<input type="hidden" name="platformName" id="platformName" value="${shops.platformName}" />
		<input type="hidden" name="flag" id="flag" value="${flag}"/>

		<div class="control-group">
			<label>平台：</label>
				<select style="width: 285px;" onchange="funcp(this)" id="sel_pt" class="form-control"></select>
			<input type="text" name="platformId" id="platformId"
				style="display: none;" value="${shops.platformId}">

		</div>
		<div class="control-group">
			<label>站点：</label>
				<select style="width: 285px;" onchange="func(this)" id="sell" class="form-control"></select>
			<input type="text" name="siteId" id="siteId" style="display: none;"
				value="${shops.siteId}">
		</div>
		<div class="control-group">
			<label class="">wish使用名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="32"
					class="input-xlarge form-control" />
		</div>
		<div class="control-group">
			<label class="">公司内部命名：</label>
				<form:input path="account" htmlEscape="false" maxlength="32"
					class="input-xlarge form-control" />
		</div>
		<div class="control-group">
			<label class="">店铺登录密码：</label>
				<form:input path="password" htmlEscape="false" maxlength="32"
					class="input-xlarge form-control" />
		</div>
		
		<div class="control-group">
			<label class="">标签：<font color="red">*</font></span>：</label>
			<form:radiobuttons items="${tagsList}" path="shopTagsId"
				itemLabel="tagsName" itemValue="id"
				class="input-xlarge form-control" />
		</div>
		
		<div class="control-group">
			<label class="control-label">屏蔽国家：</label>
			<div class="form-group">
				<select id="cnName" style="width: 285px;" name="cnName"
					class="form-control">
				</select>
				<button class="btn btn-primary" type="button"
					onclick="insertCountry();">添加</button>
			</div>
			<br/>
			<table id="contentTable"
				class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>国家代码</th>
						<th>英文名称</th>
						<th>中文名称</th>
						<shiro:hasPermission name="shops:shops:edit">
							<th>操作</th>
						</shiro:hasPermission>
					</tr>
				</thead>
				<tbody id="trcountry">
				</tbody>
			</table>
		</div>
		
		<div class="control-group">
			<label class="control-label">描述：</label>
			<form:input path="description" htmlEscape="false" maxlength="300"
				class="input-xlarge form-control" />
		</div>
		
　		<div class="control-group">
			<label class="control-label">店铺SKU前缀：<font color="red">*</font></span>：</label>
			<form:input path="codePreFix" htmlEscape="false" maxlength="300"
				class="input-xlarge form-control" placeholder="字母数字随意组合"/>
		</div>
		<div class="control-group">
			<label>店铺所属组：</label>
				<select style="width: 285px;"  id="groupId" name="groupId" class="form-control"></select>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="shops:shops:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit"
					value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回"
				onclick="history.go(-1)" />
		</div>
	</form:form>
</body>
</html>