<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>竞品采集管理</title>
	<meta name="decorator" content="bootstrap3"/>
 	<!-- <style type="text/css"> 
 		#imgPreview { 
 			height: 110px 
 		} 
 	</style> -->
	
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			
			/* 修改页面，图片回显 */  
			//回显详情图
			<c:forEach items="${detailImgsList}" var="d"> 
				var url = "${d.url}";
				var id= "${d.id}";
				var detailContent = '<div class="col-md-2 enlarge" id="img'+id+'">'
				+ '<div class="thumbnail">'
					+ '<img id="imgPreview" height="110" width="110"  src="'+url+'"></img>'
					+'<div class="caption">'
					+'<p><button type="button" onclick="deleteimg('+id+');" class="btn btn-danger btn-xs">删除</button></p>'
				    +'</div>'  
				+ '</div>'
			+ '</div>';
		       $("#detailtu").after(detailContent);
		   </c:forEach>
		   
		 //回显主G图
			<c:forEach items="${mainImgsList}" var="m"> 
				var url = "${m.url}";
				var id= "${m.id}";
				var mainContent = '<div class="col-md-2 enlarge" id="img'+id+'">'
				+ '<div class="thumbnail">'
					+ '<img id="imgPreview" height="110" width="110"  src="'+url+'"></img>'
					+'<div class="caption">'
					+'<p><button type="button" onclick="deleteimg('+id+');" class="btn btn-danger btn-xs">删除</button></p>'
				    +'</div>'  
				+ '</div>'
			+ '</div>';
		       $("#maintu").after(mainContent);
		   </c:forEach>
		   
		   //回显拼图
			<c:forEach items="${puzzleImgsList}" var="p"> 
				var url = "${p.url}";
				var id= "${p.id}";
				var puzzleContent = '<div class="col-md-2 enlarge" id="img'+id+'">'
				+ '<div class="thumbnail">'
					+ '<img id="imgPreview" height="110" width="110"  src="'+url+'"></img>'
					+'<div class="caption">'
					+'<p><button type="button" onclick="deleteimg('+id+');" class="btn btn-danger btn-xs">删除</button></p>'
				    +'</div>'  
				+ '</div>'
			+ '</div>';
		       $("#puzzletu").after(puzzleContent);
		   </c:forEach>
		   
			//回显合成图
			<c:forEach items="${compositeImgsList}" var="c"> 
				var url = "${c.url}";
				var id= "${c.id}";
				var compositeContent = '<div class="col-md-2 enlarge" id="img'+id+'">'
				+ '<div class="thumbnail">'
					+ '<img id="imgPreview" height="110" width="110"  src="'+url+'"></img>'
					+'<div class="caption">'
					+'<p><button type="button" onclick="deleteimg('+id+');" class="btn btn-danger btn-xs">删除</button></p>'
				    +'</div>'  
				+ '</div>'
			+ '</div>';
		       $("#compositetu").after(compositeContent);
		   </c:forEach>
		   
		 	//回显子G图
			<c:forEach items="${groupImgsMap}" var="groupImg" varStatus="s"> 
			var mainIndex = "${s.index+1}";
	       	 <c:forEach items="${groupImg.value}" var="childImg" varStatus="c">
		      	var childUrl = "${childImg.imgUrl}";
				var childId= "${childImg.id}";
				if(childId != '' && childId != null){
				var childContent = '<div class="col-md-2 enlarge" id="img'+childId+'">'
				+ '<div class="thumbnail">'
					+ '<img id="imgPreview" height="110" width="110"  src="'+childUrl+'"></img>'
					+'<div class="caption">'
					+'<p><button type="button" onclick="deleteimg('+childId+');" class="btn btn-danger btn-xs">删除</button></p>'
				    +'</div>'  
					+ '</div>'
					+ '</div>';
				$("#varianttu${s.index+1}${c.index}").after(childContent);
				}
	      	 </c:forEach> 
	      	 
	      	 //加载页面时，根据套图数量，显示模型数量
	      	 $("#model"+mainIndex).show();
		   </c:forEach>
		   
		   //加载
		   load();
		});
		
		//页面加载完执行的方法 
		function load(){
		   /*使用文件上传图片*/
		   //上传详情图
		   var productId = $("#id").val();
		   $('#file-detail').fileinput({
		        language: 'zh',
		        uploadUrl: '${ctx}/designdrawing/designDrawing/uploadImgs',
		        allowedFileExtensions: ['jpg', 'png', 'gif'],
		        showUpload: false, //是否显示上传按钮
		        showRemove : true, //是否显示移除按钮
		        enctype : 'multipart/form-data',
		        dropZoneEnabled: false,
		        showClose: false,
		        showPreview : false,
// 		        showCaption: false,//是否显示标题  
		        initialPreviewShowDelete: true, 
		        initialPreviewAsData: true,
// 		        initialPreview: mainProductImgData,
		        layoutTemplates: {
		       // 	actionDelete: '',
		        	actionUpload: ''
		        },
		        uploadExtraData: {
		        	type : 2,
		        	productId : productId
		        }
		        
		    }).on("filebatchselected", function(event, files) {
	            $(this).fileinput("upload");
	        })
		    .on("fileuploaded", function(event, outData, id, index) {  
		           //文件上传成功后返回的数据， 此处我只保存返回文件的id  
		           var id = outData.response.id; 
		           var url = outData.response.url;
		       		var content = '<div class="col-md-2 enlarge" id="img'+id+'">'
					+ '<div class="thumbnail">'
						+ '<img id="imgPreview" height="110" width="110"  src="'+url+'"></img>'
						+'<div class="caption">'
						+'<p><button type="button" onclick="deleteimg('+id+');" class="btn btn-danger btn-xs">删除</button></p>'
					    +'</div>'  
					+ '</div>'
					+ '</div>';
	               $("#detailtu").after(content);
		    }); 
		   
		   //上传主G图
		   var productId = $("#id").val();
		   $('#file-main').fileinput({
		        language: 'zh',
		        uploadUrl: '${ctx}/designdrawing/designDrawing/uploadImgs',
		        allowedFileExtensions: ['jpg', 'png', 'gif'],
		        showUpload: false, //是否显示上传按钮
		        showRemove : true, //是否显示移除按钮
		        enctype : 'multipart/form-data',
		        dropZoneEnabled: false,
		        showClose: false,
		        showPreview : false,
		        initialPreviewShowDelete: true, 
		        initialPreviewAsData: true,
// 		        initialPreview: mainProductImgData,
		        layoutTemplates: {
		       // 	actionDelete: '',
		        	actionUpload: ''
		        },
		        uploadExtraData: {
		        	type : 1,
		        	productId : productId
		        }
		        
		    }).on("filebatchselected", function(event, files) {
	            $(this).fileinput("upload");
	        })
		    .on("fileuploaded", function(event, outData, id, index) {  
		           //文件上传成功后返回的数据， 此处我只保存返回文件的id  
		           var id = outData.response.id; 
		           var url = outData.response.url;
		       		var content = '<div class="col-md-2 enlarge" id="img'+id+'">'
					+ '<div class="thumbnail">'
						+ '<img id="imgPreview" height="110" width="110"  src="'+url+'"></img>'
						+'<div class="caption">'
						+'<p><button type="button" onclick="deleteimg('+id+');" class="btn btn-danger btn-xs">删除</button></p>'
					    +'</div>'  
					+ '</div>'
					+ '</div>';
	               $("#maintu").after(content);
		    }); 
		   
		   
		 //上传拼图
		   var productId = $("#id").val();
		   $('#file-puzzle').fileinput({
		        language: 'zh',
		        uploadUrl: '${ctx}/designdrawing/designDrawing/uploadImgs',
		        allowedFileExtensions: ['jpg', 'png', 'gif'],
		        showUpload: false, //是否显示上传按钮
		        showRemove : true, //是否显示移除按钮
		        enctype : 'multipart/form-data',
		        dropZoneEnabled: false,
		        showClose: false,
		        showPreview : false,
		        initialPreviewShowDelete: true, 
		        initialPreviewAsData: true,
// 		        initialPreview: mainProductImgData,
		        layoutTemplates: {
		       // 	actionDelete: '',
		        	actionUpload: ''
		        },
		        uploadExtraData: {
		        	type : 4,
		        	productId : productId
		        }
		        
		    }).on("filebatchselected", function(event, files) {
	            $(this).fileinput("upload");
	        })
		    .on("fileuploaded", function(event, outData, id, index) {  
		           //文件上传成功后返回的数据， 此处我只保存返回文件的id  
		           var id = outData.response.id; 
		           var url = outData.response.url;
		       		var content = '<div class="col-md-2 enlarge" id="img'+id+'">'
					+ '<div class="thumbnail">'
						+ '<img id="imgPreview" height="110" width="110"  src="'+url+'"></img>'
						+'<div class="caption">'
						+'<p><button type="button" onclick="deleteimg('+id+');" class="btn btn-danger btn-xs">删除</button></p>'
					    +'</div>'  
					+ '</div>'
					+ '</div>';
	               $("#puzzletu").after(content);
		    }); 
		   
		   /****************************************************************************************************************/
		   
			
			//上传子G图
			<c:forEach varStatus="a" begin="0" end="9"> 
				var parentId = "${a.index}";
				<c:forEach items="${variantList}" var="variant" varStatus="s">
					var codeManagerId = "${variant.id}";
						$('#file-variant${a.index+1}${s.index}').fileinput({
					        language: 'zh',
					        uploadUrl: '${ctx}/designdrawing/designDrawing/uploadSkuImgs',
					        allowedFileExtensions: ['jpg', 'png', 'gif'],
					        showUpload: false, //是否显示上传按钮
					        showRemove : true, //是否显示移除按钮
					        enctype : 'multipart/form-data',
					        dropZoneEnabled: false,
					        showClose: false,
					        showPreview : false,
// 					        showCaption: false,//是否显示标题 
					        initialPreviewShowDelete: true, 
					        initialPreviewAsData: true,
			// 		        initialPreview: mainProductImgData,
					        layoutTemplates: {
					       // 	actionDelete: '',
					        	actionUpload: ''
					        },
					        uploadExtraData: {
					        	type : 3,
					        	productId : productId,
		// 			        	index : index,
					        	parentId : parentId,
					        	codeManagerId : codeManagerId
					        }
					        
					    }).on("filebatchselected", function(event, files) {
				            $(this).fileinput("upload");
				        })
					    .on("fileuploaded", function(event, outData, id, index) {  
					           //文件上传成功后返回的数据， 此处我只保存返回文件的id  
					           var id = outData.response.id; 
					           if(document.getElementById("img"+id) != null){
					        	   document.getElementById("img"+id).innerHTML = "";
					           }
					           var url = outData.response.url;
					       		var content = '<div class="col-md-2 enlarge" id="img'+id+'">'
								+ '<div class="thumbnail">'
									+ '<img id="imgPreview" height="110" width="110"  src="'+url+'"></img>'
									+'<div class="caption">'
									+'<p><button type="button" onclick="deleteimg('+id+');" class="btn btn-danger btn-xs">删除</button></p>'
								    +'</div>'  
								+ '</div>'
							+ '</div>';
				               $("#varianttu${a.index+1}${s.index}").after(content);
					    });
					</c:forEach>
				</c:forEach>
		}
		
		//删除图片
		function deleteimg(id, imgType){
	    	var productId = $("#id").val();
	    	$.ajax({
				url : '${ctx}/designdrawing/designDrawing/deleteimg',
				type : 'post',
				data : {
					id : id,
					imgType : imgType,
					productId :productId
				},
				success : function(data) {
					var imgId = data.imgId;
					var flag = data.flag;

					$("#img"+imgId).remove();
					if(flag != null && flag != ''){
						window.location.reload();
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){			 
					errorCapture(XMLHttpRequest, textStatus, errorThrown);	
				}
			})
	    }
		
		//新增一套图
		var groupNum = "${fn:length(groupImgsList)}";
		function addGroupImgs(){
			if(groupNum == 0){
				groupNum = 1;
			}
			groupNum++;
			
			$("#model"+groupNum).show();
			alertx("新增一套图成功！");
		}
		
		//删除一套图
		function deleteGroupImgs(index){
			if(confirm("确认删除本套图？")){
				var productId = $("#id").val();
				$.ajax({
					url : '${ctx}/designdrawing/designDrawing/deleteGroupImgs',
					type : 'post',
					data : {
						index : index,
						productId : productId
					},
					success : function(data) {
						if(data.result){
							alertx(data.msg);
							data = data.data;
						}else{
							alertx(data.msg);
							return;
						}
						if(data!=null && typeof(data)!=undefined && data!=''){
							for(var i =0;i<data.length;i++){ 
								imgId = data[i] 
								 $("#img"+imgId).remove();
							} 
						}
						//删除一套图成功后重新加载页面
						window.location.reload(); 
						
					},
					error : function(XMLHttpRequest, textStatus, errorThrown){			 
						errorCapture(XMLHttpRequest, textStatus, errorThrown);	
					}
				})
				
			}
		}
		
		//文件夹上传解析
		function uploadFolder(){
			var folderPath = $("#folderPath").val();
			var productId = $("#id").val();
			$.ajax({
				url : '${ctx}/designdrawing/designDrawing/parseFolder',
				type : 'post',
				data : {
					folderPath : folderPath,
					productId : productId
				},
				success : function(data){
					alertx(data.msg);
					//解析成功后重新加载页面
					window.location.reload(); 
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){			 
					errorCapture(XMLHttpRequest, textStatus, errorThrown);	
				}
			})
		}
		
		
		//生成合成图
		function generateMainPic() {
			//商品ID
			var productId = $("#id").val();
			$.ajax({
				url : '${ctx}/designdrawing/designDrawing/getCurrentPageData?productId=' + productId,
				type : 'get',
				data : null,
				async : false,
				cache : false,
				contentType : false,
				processData : false,
				success : function(data) {
					$("#composePicModal").modal('show');
					var content = getContent(data.viewCodeList, data.selectedCodeList);
					if(content == ""){
						content = "<h2 class='text-center'>没有更多内容了</h2>";
					}
					$("#picList").empty();
					$("#picList").append(content);
					
					$("#allboxs").removeAttr("disabled");
					$("#allboxs").removeAttr("checked");

				},
				error : function(XMLHttpRequest, textStatus, errorThrown){			 
					errorCapture(XMLHttpRequest, textStatus, errorThrown);	
				}
			})
		}
		
		
		function getContent(viewData, selectData) {
			var dataList = new Array();
			for(var key in viewData){
				var data = new Object();
				data["code"] = viewData[key];
				
				if(selectData.indexOf(viewData[key]) > -1){
					data["checked"] = true;
				}else{
					data["checked"] = false;
				}
				dataList.push(data);
			}
			
			var productId = $("#id").val();
			var content = "";
			var hang = "<div class='row'>";
			for (var n = 0; n < dataList.length; n++) {
				hang = hang
				+ '<div class="col-md-2 enlarge">'
					+ '<div class="thumbnail">'
						+ '<img id="imgPreview"  height="110" width="110"  src="${ctx}/designdrawing/designDrawing/viewImg.do?code='+ dataList[n].code + '&productId=' + productId + '"></img>'
						+ '<div  class="caption" id="'+ dataList[n].code +'">'
						+ '<input name="imgPlatformId" type="hidden"> '
						+ '<input name="imgCoded" class="checkbox" type="checkbox" onchange="changeCompositeImgState(this.value, this.checked)" '
					if(dataList[n].checked){
						hang = hang + ' checked ';
					}
					hang = hang
							+ 'value="'+dataList[n].code+'"/>'+dataList[n].code
						+ '</div>'
					+ '</div>'
				+ '</div>';

				if ((n+1) % 6 == 0) {
					content = content + hang + '</div>';
					hang = "<div class='row'>";
				}
				
				if (n == dataList.length - 1) {
					content = content + hang + '</div>';
					break;
				}
				
			}
			return content;
		}
		
		// 合成图下一页
		function next_button() {
			generateMainPic();
		}
		
		// 挑选合成图操作
		function changeCompositeImgState(code, isChecked){
			var productId = $("#id").val();
			if(isChecked){// 选中合成图，需要生成合成图并记录
				$.ajax({
					url : '${ctx}/designdrawing/designDrawing/saveCompositeImg',
					type : 'post',
					data : {
						productId : productId,
						compositeCode : code
					},
					success : function(data) {
						var imgid = data.id; 
						$("#"+code+" input[name=imgPlatformId]").val(imgid);
				           var url = data.url;
				           var productId=data.productId;
				       		var content = '<div class="col-md-2 enlarge" id="img'+imgid+'">'
							+ '<div class="thumbnail">'
								+ '<img id="imgPreview" height="110" width="110"  src="'+url+'"></img>'
								+'<div class="caption">'
								+'<p><button type="button" onclick="deleteimg('+imgid+');" class="btn btn-danger btn-xs">删除</button></p>'
							    +'</div>'  
							+ '</div>'
						+ '</div>';
			            $("#compositetu").append(content);
					},
					error : function(XMLHttpRequest, textStatus, errorThrown){			 
						errorCapture(XMLHttpRequest, textStatus, errorThrown);	
					}
				})
				
			}else{// 从数据库中删除合成图状态
				var imgPlatformId = $("#"+code+" input[name=imgPlatformId]").val();
				$.ajax({
					url : '${ctx}/designdrawing/designDrawing/deleteimg',
					type : 'post',
					data : {
						id : imgPlatformId,
						productId : productId,
						compositeCode : code
					},
					success : function(data) {
						var imgId = data.imgId;
						var flag = data.flag;
						$("#img"+imgId).remove();
					},
					error : function(XMLHttpRequest, textStatus, errorThrown){			 
						errorCapture(XMLHttpRequest, textStatus, errorThrown);	
					}
				})
			}
		}
		
		function checkForm(){
			if (!confirm("是否提交至下一流程？")) {
				return false;
			}
			
			var index = layer.load(3,{shade: [0.1, '#000']});  //风格3的加载
			if(!$("#inputForm").valid()){
				layer.close(index);
			}
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
<%-- 		<li><a href="${ctx}/productcollection/productCollection/">竞品采集列表</a></li> --%>
<%-- 		<li class="active"><a href="${ctx}/productcollection/productCollection/form?id=${productCollection.id}">竞品采集<shiro:hasPermission name="productcollection:productCollection:edit">${not empty productCollection.id?'查看':'添加'}</shiro:hasPermission><shiro:lacksPermission name="productcollection:productCollection:edit">查看</shiro:lacksPermission></a></li> --%>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="designDrawing" action="${ctx}/designdrawing/designDrawing/save" method="post" class="form-horizontal" onsubmit="return checkForm();">
		<form:hidden path="id"/>
		<form:hidden path="imgUrl"/>
		<form:hidden path="productName"/>
		<input id="type" name="type" type="hidden" value="${type}"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">商品名称：</label>
			<span>${designDrawing.productName}</span> 
<!-- 			<div class="controls"> -->
<%--  				<form:input path="productName" htmlEscape="false" maxlength="300" class="input-xlarge "/> --%> 
<!-- 			</div> -->
		</div>
		<div class="control-group">
			<label class="control-label">制图数量：</label>
			<span>${productCollection.pictureNumber}</span> 
		</div>
		<div class="control-group">
			<label class="control-label">示意图片地址：</label>
			<span>
				<a href="${designDrawing.imgLink}" target="_blank">${designDrawing.imgLink}</a>
			</span>
		</div>
		<div class="control-group">
			<label class="control-label">商品链接：</label>
			<span>
				<a href="${productCollection.productUrl}" target="_blank">${productCollection.productUrl}</a>
			</span>
		</div>
		<div class="control-group">
			<label class="control-label">采购链接：</label>
			<c:forEach items="${purchaseSourceList }" var="list" varStatus="s">
				<ul>
					<a href="${list.sourceUrl}" target="_blank">${list.sourceUrl}</a>
				</ul>
			</c:forEach>
		</div>
		
		<div class="control-group">
			<div class="container-fluid kv-main">
				<div class="page-header">
					<label>详情图<font color="red">*</font></span>：</label>
				</div>
				<div class="row" id="detailtu">
				</div>
			</div>
				<input id="file-detail" name="file" type="file" multiple> 
		</div>
		
		<div class="control-group">
			<div class="container-fluid kv-main">
				<div class="page-header">
					<label>主G图<font color="red">*</font></span>：</label>
				</div>
				<div class="row" id="maintu">
				</div>
				<input id="file-main" name="file" type="file" multiple> 
			</div>
		</div>
		
		<div class="control-group">
			<div class="container-fluid kv-main">
				<div class="page-header">
					<label>拼 图<font color="red">*</font></span>：</label>
				</div>
				<div class="row" id="puzzletu">
				</div>
				<input id="file-puzzle" name="file" type="file" multiple> 
			</div>
		</div>
		
		<div class="control-group">
			<div class="container-fluid kv-main">
				<div class="page-header">
					<label>合成图<font color="red">*</font></span>：</label>
				</div>
				<div class="row" id="compositetu">
				</div> 
			</div>
		</div>
		
		<div class="row" style="text-align: center">
			<button class="btn btn-primary" type="button" onclick="generateMainPic()">生成合成图</button>
		</div>
		<hr/>
		
		<div class="row">
			<div class="col-xs-4" style="float: left">
				<label class="control-label">文件夹上传解析：</label>
 				<input type="text" id="folderPath" name="folderPath" maxlength="500px" class="form-control"/> 
			</div>
			<div class="col-xs-4" style="float: left;margin-top:20px">
 				<input id="btnCancel" class="btn btn-primary" type="button" value="上传" onclick="uploadFolder();"/>
			</div>
		</div>
		
		<br/>
		<hr/>
		
		<div style="text-align: right;">
			<input id="btnCancel" class="btn btn-primary" type="button" value="新增一套图" onclick="addGroupImgs();"/>
		</div>
		
		<div id="model1">
		<font size="5" color="Blue">模型一</font>
		<div style="text-align: right;">
			<input id="btnCancel" class="btn" type="button" value="删除本套图" onclick="deleteGroupImgs(0);"/>
		</div>
		<div class="row">
		 	<label class="col-md-1 control-label text-right">子G图
	        	<span class="help-inline"><font color="red">*</font></span>：
        	</label>
			<div class="col-md-8">
				<table class="table table-bordered"  id="varianttu">
					<thead>
						<tr>
 	                        <th>SKU</th>
 	                        <th>属性</th>
	                        <th>图片</th>
	                    </tr>
					</thead>
					<tbody>
						<c:forEach items="${variantList}" var="variant" varStatus="s">
						<tr>
							<td>${variant.sysSku}</td>
							<td>${variant.property}</td>
							<td>
								<div class="row" id="varianttu1${s.index}">
								</div>
								<input id="file-variant1${s.index}" name="file" type="file">
							</td>
						</tr>
						</c:forEach>
					</tbody>
					<tfoot></tfoot>
				</table>
			</div>
		</div>
		<hr/>
		</div>
		
		
		<div id="model2" hidden="hidden">
		<font size="5" color="Blue">模型二</font>
		<div style="text-align: right;">
			<input id="btnCancel" class="btn" type="button" value="删除本套图" onclick="deleteGroupImgs(1);"/>
		</div>
		<div class="row">
		 	<label class="col-md-1 control-label text-right">子G图
	        	<span class="help-inline"><font color="red">*</font></span>：
        	</label>
			<div class="col-md-8">
				<table class="table table-bordered"  id="varianttu">
					<thead>
						<tr>
 	                        <th>SKU</th>
 	                        <th>属性</th>
	                        <th>图片</th>
	                    </tr>
					</thead>
					<tbody>
						<c:forEach items="${variantList}" var="variant" varStatus="s">
						<tr>
							<td>${variant.sysSku}</td>
							<td>${variant.property}</td>
							<td>
								<div class="row" id="varianttu2${s.index}">
								</div>
								<input id="file-variant2${s.index}" name="file" type="file">
							</td>
						</tr>
						</c:forEach>
						
					</tbody>
					<tfoot></tfoot>
				</table>
			</div>
		</div>
		<hr/>
		</div>
		
		<div id="model3" hidden="hidden">
		<font size="5" color="Blue">模型三</font>
		<div style="text-align: right;">
			<input id="btnCancel" class="btn" type="button" value="删除本套图" onclick="deleteGroupImgs(2);"/>
		</div>
		<div class="row">
		 	<label class="col-md-1 control-label text-right">子G图
	        	<span class="help-inline"><font color="red">*</font></span>：
        	</label>
			<div class="col-md-8">
				<table class="table table-bordered"  id="varianttu">
					<thead>
						<tr>
 	                        <th>SKU</th>
 	                        <th>属性</th>
	                        <th>图片</th>
	                    </tr>
					</thead>
					<tbody>
						<c:forEach items="${variantList}" var="variant" varStatus="s">
						<tr>
							<td>${variant.sysSku}</td>
							<td>${variant.property}</td>
							<td>
								<div class="row" id="varianttu3${s.index}">
								</div>
								<input id="file-variant3${s.index}" name="file" type="file">
							</td>
						</tr>
						</c:forEach>
						
					</tbody>
					<tfoot></tfoot>
				</table>
			</div>
		</div>
		<hr/>
		</div>
		
		<div id="model4" hidden="hidden">
		<font size="5" color="Blue">模型四</font>
		<div style="text-align: right;">
			<input id="btnCancel" class="btn" type="button" value="删除本套图" onclick="deleteGroupImgs(3);"/>
		</div>
		<div class="row">
		 	<label class="col-md-1 control-label text-right">子G图
	        	<span class="help-inline"><font color="red">*</font></span>：
        	</label>
			<div class="col-md-8">
				<table class="table table-bordered"  id="varianttu">
					<thead>
						<tr>
 	                        <th>SKU</th>
 	                        <th>属性</th>
	                        <th>图片</th>
	                    </tr>
					</thead>
					<tbody>
						<c:forEach items="${variantList}" var="variant" varStatus="s">
						<tr>
							<td>${variant.sysSku}</td>
							<td>${variant.property}</td>
							<td>
								<div class="row" id="varianttu4${s.index}">
								</div>
								<input id="file-variant4${s.index}" name="file" type="file">
							</td>
						</tr>
						</c:forEach>
						
					</tbody>
					<tfoot></tfoot>
				</table>
			</div>
		</div>
		<hr/>
		</div>
		
		<div id="model5" hidden="hidden">
		<font size="5" color="Blue">模型五</font>
		<div style="text-align: right;">
			<input id="btnCancel" class="btn" type="button" value="删除本套图" onclick="deleteGroupImgs(4);"/>
		</div>
		<div class="row">
		 	<label class="col-md-1 control-label text-right">子G图
	        	<span class="help-inline"><font color="red">*</font></span>：
        	</label>
			<div class="col-md-8">
				<table class="table table-bordered"  id="varianttu">
					<thead>
						<tr>
 	                        <th>SKU</th>
 	                        <th>属性</th>
	                        <th>图片</th>
	                    </tr>
					</thead>
					<tbody>
						<c:forEach items="${variantList}" var="variant" varStatus="s">
						<tr>
							<td>${variant.sysSku}</td>
							<td>${variant.property}</td>
							<td>
								<div class="row" id="varianttu5${s.index}">
								</div>
								<input id="file-variant5${s.index}" name="file" type="file">
							</td>
						</tr>
						</c:forEach>
						
					</tbody>
					<tfoot></tfoot>
				</table>
			</div>
		</div>
		<hr/>
		</div>
		
		<div id="model6" hidden="hidden">
		<font size="5" color="Blue">模型六</font>
		<div style="text-align: right;">
			<input id="btnCancel" class="btn" type="button" value="删除本套图" onclick="deleteGroupImgs(5);"/>
		</div>
		<div class="row">
		 	<label class="col-md-1 control-label text-right">子G图
	        	<span class="help-inline"><font color="red">*</font></span>：
        	</label>
			<div class="col-md-8">
				<table class="table table-bordered"  id="varianttu">
					<thead>
						<tr>
 	                        <th>SKU</th>
 	                        <th>属性</th>
	                        <th>图片</th>
	                    </tr>
					</thead>
					<tbody>
						<c:forEach items="${variantList}" var="variant" varStatus="s">
						<tr>
							<td>${variant.sysSku}</td>
							<td>${variant.property}</td>
							<td>
								<div class="row" id="varianttu6${s.index}">
								</div>
								<input id="file-variant6${s.index}" name="file" type="file">
							</td>
						</tr>
						</c:forEach>
						
					</tbody>
					<tfoot></tfoot>
				</table>
			</div>
		</div>
		<hr/>
		</div>
		
		<div id="model7" hidden="hidden">
		<font size="5" color="Blue">模型七</font>
		<div style="text-align: right;">
			<input id="btnCancel" class="btn" type="button" value="删除本套图" onclick="deleteGroupImgs(6);"/>
		</div>
		
		<div class="row">
		 	<label class="col-md-1 control-label text-right">子G图
	        	<span class="help-inline"><font color="red">*</font></span>：
        	</label>
			<div class="col-md-8">
				<table class="table table-bordered"  id="varianttu">
					<thead>
						<tr>
 	                        <th>SKU</th>
 	                        <th>属性</th>
	                        <th>图片</th>
	                    </tr>
					</thead>
					<tbody>
						<c:forEach items="${variantList}" var="variant" varStatus="s">
						<tr>
							<td>${variant.sysSku}</td>
							<td>${variant.property}</td>
							<td>
								<div class="row" id="varianttu7${s.index}">
								</div>
								<input id="file-variant7${s.index}" name="file" type="file">
							</td>
						</tr>
						</c:forEach>
						
					</tbody>
					<tfoot></tfoot>
				</table>
			</div>
		</div>
		<hr/>
		</div>
		
		<div id="model8" hidden="hidden">
		<font size="5" color="Blue">模型八</font>
		<div style="text-align: right;">
			<input id="btnCancel" class="btn" type="button" value="删除本套图" onclick="deleteGroupImgs(7);"/>
		</div>
		
		<div class="row">
		 	<label class="col-md-1 control-label text-right">子G图
	        	<span class="help-inline"><font color="red">*</font></span>：
        	</label>
			<div class="col-md-8">
				<table class="table table-bordered"  id="varianttu">
					<thead>
						<tr>
 	                        <th>SKU</th>
 	                        <th>属性</th>
	                        <th>图片</th>
	                    </tr>
					</thead>
					<tbody>
						<c:forEach items="${variantList}" var="variant" varStatus="s">
						<tr>
							<td>${variant.sysSku}</td>
							<td>${variant.property}</td>
							<td>
								<div class="row" id="varianttu8${s.index}">
								</div>
								<input id="file-variant8${s.index}" name="file" type="file">
							</td>
						</tr>
						</c:forEach>
						
					</tbody>
					<tfoot></tfoot>
				</table>
			</div>
		</div>
		<hr/>
		</div>
		
		<div id="model9" hidden="hidden">
		<font size="5" color="Blue">模型九</font>
		<div style="text-align: right;">
			<input id="btnCancel" class="btn" type="button" value="删除本套图" onclick="deleteGroupImgs(8);"/>
		</div>
		
		<div class="row">
		 	<label class="col-md-1 control-label text-right">子G图
	        	<span class="help-inline"><font color="red">*</font></span>：
        	</label>
			<div class="col-md-8">
				<table class="table table-bordered"  id="varianttu">
					<thead>
						<tr>
 	                        <th>SKU</th>
 	                        <th>属性</th>
	                        <th>图片</th>
	                    </tr>
					</thead>
					<tbody>
						<c:forEach items="${variantList}" var="variant" varStatus="s">
						<tr>
							<td>${variant.sysSku}</td>
							<td>${variant.property}</td>
							<td>
								<div class="row" id="varianttu9${s.index}">
								</div>
								<input id="file-variant9${s.index}" name="file" type="file">
							</td>
						</tr>
						</c:forEach>
						
					</tbody>
					<tfoot></tfoot>
				</table>
			</div>
		</div>
		<hr/>
		</div>
		
		<div id="model10" hidden="hidden">
		<font size="5" color="Blue">模型十</font>
		<div style="text-align: right;">
			<input id="btnCancel" class="btn" type="button" value="删除本套图" onclick="deleteGroupImgs(9);"/>
		</div>
		
		<div class="row">
		 	<label class="col-md-1 control-label text-right">子G图
	        	<span class="help-inline"><font color="red">*</font></span>：
        	</label>
			<div class="col-md-8">
				<table class="table table-bordered"  id="varianttu">
					<thead>
						<tr>
 	                        <th>SKU</th>
 	                        <th>属性</th>
	                        <th>图片</th>
	                    </tr>
					</thead>
					<tbody>
						<c:forEach items="${variantList}" var="variant" varStatus="s">
						<tr>
							<td>${variant.sysSku}</td>
							<td>${variant.property}</td>
							<td>
								<div class="row" id="varianttu10${s.index}">
								</div>
								<input id="file-variant10${s.index}" name="file" type="file">
							</td>
						</tr>
						</c:forEach>
					</tbody>
					<tfoot></tfoot>
				</table>
			</div>
		</div>
		<hr/>
		</div>
		
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交"/>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<act:histoicFlow procInsId="${bean.act.procInsId}"/>
	</form:form>
	
	<!-- 合成图片的Modal -->
	<div class="modal fade" id="composePicModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
		<div class="modal-dialog" style="width: 100%" role="document">
	    	<div class="modal-content">
	      		<div class="modal-header">
	   				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	      			<div class="col-md-12">
			        	<h4 class="modal-title" id="myModalLabel">合成图列表:</h4>
	      			</div>
	      			<div class="col-md-6">
		        		<input type="checkbox" class="checkbox" id="allboxs" onclick="allcheck();"/>
	   				</div>
	   				<div class="col-md-6 text-right">
	   					<a class="btn btn-info" id="next_page" onclick="next_button()" >
	   					<span class="glyphicon glyphicon-refresh"></span>&nbsp;换一批</a>
	   				</div>
	      		</div>
      		<div class="modal-body">
      			<div class="container-fluid" id="picList"></div>
			</div> 
	      	<div class="modal-footer">
		        
	      	</div>
  		</div>
	</div>
</div>	
	
</body>
</html>