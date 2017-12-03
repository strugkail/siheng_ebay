<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>供应商推荐添加</title>
<meta name="decorator" content="bootstrap3" />
<script type="text/javascript">

function load(){
	
	if($("#flag").val()=="view"){
		$("#btnSubmit").hide();
	}
	
	var mainProductImgData = new Array();
$('#file-main')
			.fileinput({
				language : 'zh',
				uploadUrl : '${ctx}/product/productDev/uploadImgs',
				allowedFileExtensions : [ 'jpg', 'png', 'gif' ],
				showRemove : true, //隐藏移除按钮
				enctype : 'multipart/form-data',
				dropZoneEnabled : false,
				showClose : false,
				showPreview : false,
				initialPreviewShowDelete : true,
				initialPreviewAsData : true,
				initialPreview : mainProductImgData,
				layoutTemplates : {
					// 	actionDelete: '',
					actionUpload : ''
				},
				uploadExtraData : {
					type : 1,
					productId : ""
				}

			})
			.on("fileuploaded",
					function(event, outData, id, index) {
				//文件上传成功后返回的数据， 此处我只保存返回文件的id  
				var id = outData.response.id;
				var url = outData.response.url;
				var content = '<div class="col-md-2 enlarge" id="img'+id+'">'
						+ '<div class="thumbnail">'
						+ '<img id="imgPreview" height="110" width="110"  src="'+url+'"></img>'
						+ '<div class="caption">'
						+ '<p><button type="button" onclick="deleteimg('
						+ id
						+ ');" class="btn btn-primary">删除</button></p>'
						+ '</div>' + '</div>' + '</div>';
				$("#producttu").after(content);
			});
    }



	$(document).ready(function() {
		// 页面加载完，执行该方法
	load();
		
		initImg();
	});
	function initImg(){
		/* 修改页面，图片回显 */  
		<c:forEach items="${maplist.mainlist}" var="m">  
		//mainProductImgData.push("${m}"); 
		var url = "${m.url}";
		var id= "${m.id}";
		var content = '<div class="col-md-2 enlarge" id="img'+id+'">'
		+ '<div class="thumbnail">'
			+ '<img id="imgPreview" height="110" width="110"  src="'+url+'"></img>'
			+'<div class="caption">'
			+'<p><button type="button" onclick="deleteimg('+id+');" class="btn btn-primary">删除</button></p>'
		    +'</div>'  
		+ '</div>'
	+ '</div>';
       $("#producttu").after(content);
	   </c:forEach>
	}
	
    function uploadImg(){
    	var img = $("#newimg").val();
    	var productId = $("#id").val();
    	$.ajax({
			url : '${ctx}/product/productDev/insertimg',
			type : 'post',
			data : {
				url : img,
				type : 1,
	        	productId : productId
			},
			success : function(data) {
				if(data==null||data==''){
					alertx("请输入正确的图片地址 ");
					$("#newimg").val("");
					return;
				}else{
					//$("#newimg").val("");
				var url = data.url;
				var id = data.id;
				var content = '<div class="col-md-2 enlarge" id="img'+id+'" ondrop="drop(event,this)" ondragover="allowDrop(event)" draggable="true" ondragstart="drag(event, this)">'
				+ '<div class="thumbnail">'
					+ '<img id="imgPreview" height="110" width="110"  src="'+url+'"></img>'
					+'<div class="caption">'
					+'<p><button type="button" onclick="deleteimg('+id+');" class="btn btn-primary">删除</button></p>'
				    +'</div>'  
				+ '</div>'
			+ '</div>';
			$("#img").hide();
            $("#producttu").append(content);
            $("#imgUrl").val(id);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){			 
				errorCapture(XMLHttpRequest, textStatus, errorThrown);	
			}
		})
    }
    
    function deleteimg(id, imgType){
    	var productId = $("#productId").val();
    	$.ajax({
			url : '${ctx}/product/productDev/deleteimg',
			type : 'post',
			data : {
				id : id,
				imgType : imgType,
				productId :productId
			},
			success : function(data) {
				 $("#img"+data).remove();
				 $("#img").show();
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
			<li class="active"><a href="#">供应商推荐添加</a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="supplier"
		action="${ctx}/product/productDev/save" method="post" class="form-inline form-horizontal">
		<form:hidden path="id" />
		<form:hidden path="flag" />
		<form:hidden path="imgUrl" />
		<sys:message content="${message}" />

		<div class="control-group">
			<label class="control-label">供应商名称：</label>
				<form:input path="supplierName" required="required" htmlEscape="false" class="input-xlarge form-control" />
		</div>
		<div class="control-group">
			<label class="control-label">产品名称： </label>
				<form:input path="productName" required="required" htmlEscape="false" class="input-xlarge form-control" />
		</div>
		<div class="control-group">
			<div class="container-fluid kv-main">
			<div class="page-header">
				<label>产品图片<font color="red">*</font></span>：</label>
			</div>
			<div class="row"  id="producttu">
			<img src="${fns:getImageUrl()}${supplier.imgUrl}" id="img" width="150" height="150" class="img-polaroid"/>
			</div>
			<!-- <input id="file-main" name="file" type="file" multiple>  -->
			<br/>
			<div class="col-md-4">
			 <input class="form-control" id="newimg" name="newimg" type="text" placeholder="请输入图片地址······"></input>
		   </div>
		   <div class="col-md-3">
			<button class="btn btn-primary" type="button" onclick="uploadImg();">上传</button>
		   </div>
		</div>
		</div>
		</br>
		<div class="control-group">
			<label class="control-label">产品链接：</label>
				<form:input path="productAddr" htmlEscape="false" required="required" class="input-xlarge form-control" />
		</div>
		<br></br>
		<div class="control-group">
			<label class="control-label">包装方式：</label>
			<form:input path="packageMethod" htmlEscape="false" required="required" class="input-xlarge form-control" />
		</div>
			
<!--  		<div class="control-group"> -->
<!-- 			<label class="control-label">推荐时间：</label> -->
<%-- 			<input id="recomTime" name="recomTime" value="${supplier.recomTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" style="width:180px;height:30px"/> --%>
<!-- 		</div> -->
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">推 荐 人：</label> -->
<%-- 			<form:input path="recomName" htmlEscape="false" class="input-xlarge form-control" /> --%>
<!-- 		</div>   -->
         <div class="form-group">
				<label for="length" class="col-md-4 col-xs-4 control-label">长
					<span class="help-inline"><font color="red">*</font> </span>：
				</label>
				<div class="col-md-6 col-xs-6">
					<div class="input-group">
						<form:input
							class="form-control" path="length"
							type="number" min="0" step="0.01" required="required" />
							<span class="input-group-addon">CM</span> 
					</div>
				</div>
			</div>
			<br></br>
			<div class="form-group">
				<label for="wide" class="col-md-4 col-xs-4 control-label">宽
					<span class="help-inline"><font color="red">*</font> </span>：
				</label>
				<div class="col-md-6 col-xs-6">
					<div class="input-group">
						 <form:input
							class="form-control" path="wide" type="number"
							min="0" step="0.01" required="required" />
							<span class="input-group-addon">CM</span>
					</div>
				</div>
			</div>
				<br></br>
				<div class="form-group">
				<label for="high" class="col-md-4 col-xs-4 control-label">高
					<span class="help-inline"><font color="red">*</font> </span>：
				</label>
				<div class="col-md-6 col-xs-6">
					<div class="input-group">
						 <form:input
							class="form-control" path="high"  type="number"
							min="0" step="0.01" required="required" />
							<span class="input-group-addon">CM</span>
					</div>
				</div>
			</div>
				<br></br>
			<div class="form-group">
				<label for="price" class="col-md-4 col-xs-4 control-label">采购价格
					<span class="help-inline"><font color="red">*</font> </span>：
				</label>
				<div class="col-md-6 col-xs-6">
					<div class="input-group">
						<form:input class="form-control"  path="price" type="number" min="0"  step="0.01" required="required"/>
					<span class="input-group-addon">¥</span> 
					</div>
				</div>
			</div>
				<br></br>
			<div class="form-group">
				<label for="weight" class="col-md-4 col-xs-4 control-label">重量
					<span class="help-inline"><font color="red">*</font> </span>：
				</label>
				<div class="col-md-6 col-xs-6">
					<div class="input-group">
						<form:input class="form-control" path="weight" type="number" min="0" step="0.1" required="required"/>
						<span class="input-group-addon">G</span> 
					</div>
				</div>
			</div>
			<br></br>
				<div class="form-group">
				<label for="remark" class="col-md-2 col-xs-2 control-label">备注
					<span class="help-inline"><font color="red">*</font> </span>：
				</label>
				<div class="col-md-6 col-xs-6">
						<textarea name="remark"
					style="width: 600px; height: 100px; outline: none; resize: none">${supplier.remark}</textarea>
				</div>
			</div>
			<br></br>
		<div class="row text-center">
				<input id="btnSubmit" class="btn btn-primary" type="submit"
					value="保 存"/>
			<input id="btnCancel" class="btn" type="button" value="返 回"
				onclick="history.go(-1)" />
		</div>
	</form:form>
	
</body>
</html>