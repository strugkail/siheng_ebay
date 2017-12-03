<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>竞品采集管理</title>
	<meta name="decorator" content="default"/>






	<script type="text/javascript">
		$(document).ready(function() {
		    var siteId=$("#siteId").val();
		    var itemId=$("#itemId").val();
		    //当你的站点不为空的事件
		    if(siteId!=""){
                    $.ajax({
                        url : '${ctx}/productcollection/productCollection/selectBySiteId',
                        type : 'post',
                        data : {
                            siteId : siteId
                            },
                        success : function(data){
                            $("#siteShortName").attr("value",data);
                        }
                });
            }
            //下拉触发事件
            $("#siteId").change(function(){
                $.ajax({
                    url : '${ctx}/productcollection/productCollection/selectBySiteId',
                    type : 'post',
                    data : {
                        siteId : siteId
                    },
                    success : function(data){
                        $("#siteShortName").attr("value",data);
                    }
                });
            });

			
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
			
			// 绑定图片链接地址改变事件
// 			$("#imgLink").bind('input propertychange',function(){
// 				$("#imgPreview").attr("src", $("#imgLink").val());
// 			}); 
			
			//通过itemId调用接口展示示意图
			/* $("#itemId").bind('blur',function(){
				var itemId = $("#itemId").val();
				if(itemId == null || itemId == '' ){
					alertx("请输入竞品采集ID!");
					return;
				}
				
				$.ajax({
		 			url : '${ctx}/productcollection/productCollection/getMainImageUrl',
		 			type : 'post',
		 			data : {
		 				itemId : itemId
		 			},
		 			success : function(data){
		 				
		 				console.log(data);
		 				
		 				debugger;
		 				if(data.result){
		 					data=data.data;
		 				}else{
		 					$("#itemId").attr("value", '');
		 					$("#imgLink").attr("value", '');
		 					$("#imgPreview").attr("src", '');
		 					if(typeof (data.msg)==undefined){
			 					alert(data.msg);
		 					}
		 					return;
		 				}
		 				$("#imgLink").attr("value", data);
		 				$("#imgPreview").attr("src", $("#imgLink").val());
		 			},
		 			error : function(XMLHttpRequest, textStatus, errorThrown){			 
		 				errorCapture(XMLHttpRequest, textStatus, errorThrown);	
		 			}
		 		});
				
			});  */
			
		});
		

		//保存
		function save(){
			$("#type").val(0);
			//保存时，再次异步验证ItemId
			var itemId = $("#itemId").val();
			$.ajax({
	 			url : '${ctx}/productcollection/productCollection/getMainImageUrl',
	 			type : 'post',
	 			data : {
	 				itemId : itemId
	 			},
	 			success : function(data){
		 				if(data.result){
		 					var index = layer.load(3,{shade: [0.1, '#000']});  //风格3的加载
		 					if(!$("#inputForm").valid()){
		 						layer.close(index);
		 					}
		 					$("#inputForm").submit(); 
		 				}else{
		 					$("#itemId").attr("value", '');
		 					alertx("竞争商品ID有误，请检查！");
		 				}
	 			},
	 			error : function(XMLHttpRequest, textStatus, errorThrown){			 
	 				errorCapture(XMLHttpRequest, textStatus, errorThrown);	
	 			}
	 		});
	    	
		}
		//开发
		function savePub(){
			$("#type").val(1);
			var itemId = $("#itemId").val();
			if (!confirm("是否提交至下一流程？")) {
				return;
			}
			
			//提交时，再次异步验证ItemId
			$.ajax({
	 			url : '${ctx}/productcollection/productCollection/getMainImageUrl',
	 			type : 'post',
	 			data : {
	 				itemId : itemId
	 			},
	 			success : function(data){
		 				if(data.result){
		 					var index = layer.load(3,{shade: [0.1, '#000']});  //风格3的加载
		 					if(!$("#inputForm").valid()){
		 						layer.close(index);
		 					}
		 					$("#inputForm").submit(); 
		 				}else{
		 					$("#itemId").attr("value", '');
		 					alertx("竞争商品ID有误，请重新输入！");
		 				}
	 			},
	 			error : function(XMLHttpRequest, textStatus, errorThrown){			 
	 				errorCapture(XMLHttpRequest, textStatus, errorThrown);	
	 			}
	 		});
				
			
			
		}
		
		
		
		function checkItemId(obj){
			var itemId =  $(obj).val();
			if(itemId == null || itemId == '' ){
				alertx("请输入竞品采集ID!");
					return;
			}
            /**
             * 生成参考链接
             * @type {*}
             */
			var siteId=$("#siteId").val();
            var SiteShortName=$("#siteShortName").val();
            if(siteId==19&&itemId!=""){
                $("#productUrl"). attr("value","https://www.ebay.com/itm/"+itemId);
            }else if(siteId==18&&itemId!=""){
                $("#productUrl"). attr("value","https://www.ebay.co."+SiteShortName+"/itm/"+itemId);
            }else if(siteId!=""&&siteId!=18&&siteId!=19&&itemId!=null) {
                $("#productUrl"). attr("value","https://www.ebay."+SiteShortName+"/itm/"+itemId);
            }
			$.ajax({
				 	url : '${ctx}/productcollection/productCollection/getMainImageUrl?time='+new Date().getTime(),
				 	type : 'post',
				 	data : {
				 				itemId : itemId
				 	},
				 	success : function(data){
		 				if(data.result){
		 					data=data.data;
		 				}else{
		 					$("#itemId").attr("value", '');
		 					$("#imgLink").attr("value", '');
		 					$("#imgPreview").attr("src", '');
		 					if(typeof(data.msg) != undefined){
			 					alert(data.msg);
		 					}
		 					return;
		 				}
		 				$("#imgLink").attr("value", data);
		 				$("#imgPreview").attr("src", $("#imgLink").val());
		 			},
		 			error : function(XMLHttpRequest, textStatus, errorThrown){			 
		 				errorCapture(XMLHttpRequest, textStatus, errorThrown);	
		 			}
		 		});
		}
		
		
		
	</script>
	<!-- <style type="text/css">
			#imgPreview:hover {
					transform: scale(3.0);
					-webkit-transform: scale(3.0); /*Safari 和 Chrome*/
					-moz-transform: scale(3.0); /*Firefox*/
					-ms-transform: scale(3.0); /*IE9*/
					-o-transform: scale(3.0); /*Opera*/
					z-index: 5555;
					border: 2px solid black;
					position: absolute;
					left:200px;
			}
	</style> -->
</head>
<body>
    <input type="hidden" id="siteShortName" value="">
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/productcollection/productCollection/">竞品采集列表</a></li>
		<li class="active"><a href="${ctx}/productcollection/productCollection/form?id=${productCollection.id}">竞品采集<shiro:hasPermission name="productcollection:productCollection:edit">${not empty productCollection.id?'编辑':'添加'}</shiro:hasPermission><shiro:lacksPermission name="productcollection:productCollection:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="productCollection" action=" ${ctx}/productcollection/productCollection/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" id="type" name="type" path="type" />
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">站点：</label>
			<div class="controls">
				<form:select class="required" path="siteId" style="width: 250px;">
 		 	   		<%--<form:option value="" label="--请选择--" /> --%>
		 	   		<form:options items="${siteList}" itemLabel="siteName" itemValue="id" htmlEscape="false" />
		   		</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">销售类型：</label>
			<div class="controls">
				<form:select class="required" path="saleTypeId" style="width: 250px;">
<%-- 		 	  	 	<form:option value="" label="--请选择--" /> --%>
		 	  	<form:option value="1" label="海外仓精品" />
		 	  	<form:option value="2" label="虚拟海外仓精品" />
		    </form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">销售组：</label>
			<div class="controls">
				<form:select class="required" path="saleGroupId" style="width: 200px;">
		 	   		<form:option value="" label="--请选择--" />
		 	   		<form:options items="${groupList}" itemLabel="sellerGroupName" itemValue="groupId" htmlEscape="false" />
		   		</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品中文名称：</label>
			<div class="controls">
				<form:input path="productName" htmlEscape="false" maxlength="300" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="imgPreview">图片预览：</label>
		  	<div class="controls" style="width: 300px;" >
	    		<a href="#" class="thumbnail">
	      			<img id="imgPreview" src="${fns:getImageUrl()}${productCollection.imgUrl}" width="300px" class="required">
	    		</a>
  			</div>
		</div>
		<div class="control-group" style="display:none;">
		 	<label class="control-label" for="imgLink">示意图片地址<font color="red">*</font></span>：</label>
	        <div class="controls">
	            <form:input path="imgLink" htmlEscape="false" maxlength="300" class="input-xlarge" />
	        </div>
	    </div>
		<div class="control-group">
			<label class="control-label">参考链接：</label>
			<div class="controls">
				<form:input path="productUrl" htmlEscape="false" maxlength="300" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">竞争商品ID：</label>
			<div class="controls">
				<form:input path="itemId" onblur="checkItemId(this);" htmlEscape="false" maxlength="300" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">采购总量：</label>
			<div class="controls">
				<form:input path="productNumber" htmlEscape="false" maxlength="11" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">制图数量：</label>
			<div class="controls">
				<form:input path="pictureNumber" htmlEscape="false" maxlength="11" class="input-xlarge required"/>&nbsp;<span>套图</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">销售备注：</label>
			<div class="controls">
				<textarea name="remark" style="width: 600px; height: 100px; outline: none; resize: both">${productCollection.remark}</textarea>
			</div>
		</div>
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<label>处理历史：</label>
			<thead>
				<tr>
					<th>操作人</th>
					<th>操作时间</th>
					<th>备注</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${logList}" var="log">
				<tr>
					<td>${fns:getUserById(log.createBy).name}</td>
					<td>
						<fmt:formatDate value="${log.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>${log.remarks}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<div class="form-actions">
			<shiro:hasPermission name="productcollection:productCollection:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="save()"/>&nbsp;</shiro:hasPermission>
			<shiro:hasPermission name="productcollection:productCollection:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="提交" onclick="savePub();"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>