 <%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
  <script type="text/javascript" src="${ctxStatic}/modules/ebay/product/PropertyMap.js"></script>

  <script>
  
  	var baseUrl = '${fns:getImageUrl()}';
	
	var imageIdp="";//主 g 图 的 id 
	var imageUrlp="";//主 g 图的 url
	var productImageId="";//t_product_img 对应的id 
	var closeType = false;
	/**选择图片*/
	function choosePicture(){
		closeType = false;
		var productId = $("#productId").val();
		var url = "${ctx}/ebay/product/chooseImage?productId="+productId;
		$.jBox.open("iframe:" + url, "风格图片套图 选择", 1000, 600,{persistent: true,showScrolling: false,buttons: { '关闭': 'ok' },closed: function () { 
		        //这里处理选择的 主 g 图和 子 g 图 
		     if(closeType){//弹出页面用户有没有选择 模板文件 进行绑定操作
		        //主 g 图 赋值 展示  
			      
			     $("#main_g_tu_div1").html(generalHtml(1,imageIdp,imageUrlp,productImageId)); 
			     
			     //子 g 图 ajax 获取 然后局部更新   得到子数据库中的 子g 图  
			     $.ajax({
					url : '${ctx}/ebay/product/getChildImage',
					type : 'post',
					data : {
						parentId : imageIdp
					},
					success : function(data) {
						  for(var i in data){
						     $("#sku_image_url_class_"+data[i].codeManagerId).attr("src",baseUrl+data[i].imgUrl);
						     $("#image_id_"+data[i].codeManagerId).val(data[i].imgId);
						     $("#image_url_"+data[i].codeManagerId).val(data[i].imgUrl);
						  }
					},
					error : function(XMLHttpRequest, textStatus, errorThrown){			 
						errorCapture(XMLHttpRequest, textStatus, errorThrown);	
					}
				});
			   	
		     } 
		}
		});
		
	}
	
	
	
	var selectPictureFlag = false;
	var selectPictureData = new Array(); //选择图片绑定之后的数据回写 
	
	/**展示出来 供选择的 图片集合页面 ，用户可以在里面进行选择或则 自主上传 type 图片类型 （1、主  3、子  2、细节  5、 特效 ）*/
	function selectPicture(type,codeManagerId){
		selectPictureFlag = false;
		selectPictureData = new Array(); 
		var productId = $("#productId").val();
		var url = "${ctx}/ebay/product/selectImage?productId="+productId+"&type="+type+"&codeManagerId="+codeManagerId;
		$.jBox.open("iframe:" + url, "产品图片选择 ", 1200, 800,{persistent: true,showScrolling: false,buttons: { '关闭': 'ok' },closed: function () { 
		        //这里处理选择的 主 g 图和 子 g 图 
		        if(selectPictureFlag){
		        	for(var i in selectPictureData){
			        	var image = selectPictureData[i];
			        	var id = image.id;
			        	var url = image.imgUrl;
			        	var imageId = image.imgId;
			        	var codeManagerId = image.codeManagerId;
			        	// 操作成功返回的图片集合  
			        	if(type==3 || type=="3"){//子 g 图 
			        		$("#sku_image_url_class_"+codeManagerId).attr("src",baseUrl+url);
			    			$("#image_id_"+codeManagerId).val(imageId);
			    			$("#image_url_"+codeManagerId).val(url);
			        	}else{
		        			$("#main_g_tu_div"+type).append(generalHtml(type,id,url,imageId));
			        	}
		        	}
		        	
		        }
			}
		});
	}
	
	
	/**构建要 追加的图片内容  */
	function generalHtml(type,id,url,imageId){
		var html='';
		html+='<div class="col-md-2 enlarge" style="height:120px;width:120px;float:left;" id="img'+id+'" ondrop="drop(event,this)" ondragover="allowDrop(event)" draggable="true" ondragstart="drag(event, this)">';
		html+=' <input name="mainImageId'+type+'" class="mainImageId'+type+'" value="'+imageId+'" type="hidden"/>';
		html+='  <input name="mainImageUrl'+type+'" value="'+url+'" type="hidden"/>';
		html+='<img id="imgPreview" height="110px" width="110px"  src="'+(baseUrl+url)+'"></img>';
		html+='<p><button type="button" onclick="deleteimg('+id+','+type+');" class="btn btn-primary">删除</button></p></div>';
		return html;
	}
	</script>
  
  <div class="page-header">
          	<label class="col-md-1 control-label text-right" for="description"><strong>多属性设置:</strong></label>
            
            <c:if test="${ebaySaleType== 1 || ebaySaleType==2 }"><!-- 精品都需要选择套图 -->
	            <input type="button" class="input-medium btn btn-primary" value="选择模板图片" onclick="javascript:choosePicture();"><br><br>
            </c:if> 
          
          <div class="row"  style="width:100%;overflow: auto" >
            
            <c:set  var="propertyNum" value= "${fn:length(productDetail.codeManagerList[0].productPropertyList)}"></c:set>
            
            <table class="table table-bordered"  id="itemSpecifics" >
                <thead >
                    <tr id="duoshuxing_add_tr" >
                        <th>sku</th>
                        <th>数量</th>
                        <th>价格</th>
                        <th style="width: 100px">图片地址</th> 
                        	<c:forEach items="${productDetail.productPropertyNames}" var="names" varStatus="status">
                        		<th>
                        			<form:input path="productPropertyNames[${status.count-1 }]" id="codeManagerList0_productPropertyList${status.count-1}_propertyName"   onblur="translateValue(this)"/>
                        		</th>
                        	</c:forEach>
                       
                        <th>UPC/EAN</th>
                    </tr>
                </thead>
                <tbody  id="codemanager_tbody">
                
                
                <c:set var="codeManagerSize" value="0"></c:set>
                
                <c:forEach items="${productDetail.codeManagerList}" varStatus="status" var="codeManager">
                
                <!--  产品下的 sku 集合列表  -->
                    <tr id="sku_${codeManagerSize}_tr">
                         <td id="sku_${codeManagerSize}_td1">
                         	<%-- <input  class="input-medium" type="text" id="code_manager_sku_${codeManager.id}" value="${codeManager.listingSkuCode}"  readonly="${(saleType==1 || saleType=='1')?'readonly':'' }"/> --%>
	                        <form:input path="codeManagerList[${codeManagerSize}].listingSkuCode" class="code_manager_sku" id="code_manager_sku_2_${codeManager.id}"  readonly="${(saleType==1 || saleType=='1')}"/>
                         </td>
                         
                         <form:input path="codeManagerList[${codeManagerSize}].id" type="hidden"/>
                         <form:input path="codeManagerList[${codeManagerSize}].sysSku" type="hidden"/>
                         <%-- <form:input path="codeManagerList[${codeManagerSize}].listingSkuCode" id="code_manager_sku_2_${codeManager.id}" type="hidden"/> --%>
                         <form:input path="codeManagerList[${codeManagerSize}].sysParentSku" type="hidden"/>
                         <form:input path="codeManagerList[${codeManagerSize}].productId" type="hidden"/>
                         
                        <td>
                        	<form:input path="codeManagerList[${codeManagerSize}].recommendNumber" htmlEscape="false" class="input-medium" type="hidden"/>
                        	<form:input path="codeManagerList[${codeManagerSize}].quantity" htmlEscape="false" class="input-medium" type="number" maxlength="8" />
                        </td>
                        <td><form:input path="codeManagerList[${codeManagerSize}].publishPrice"  id="code_manager_${codeManager.id}_publishPrice"  htmlEscape="false" class="input-medium code_manager_publishPrice"/></td>
                       
                        <td style="width: 100px" id="code_manager_image_td_${codeManager.id}">
                           <!-- 这个id是图片关联表的id   不是  product_img 的 id -->
                           <form:input path="codeManagerList[${codeManagerSize}].imageId" id="image_id_${codeManager.id}" type="hidden" htmlEscape="false" class="input-medium code_manager_imageId" />
                           <form:input path="codeManagerList[${codeManagerSize}].imgUrl" id="image_url_${codeManager.id}" type="hidden" htmlEscape="false" class="input-medium" />
                           
	                       <img id = "sku_image_url_class_${codeManager.id}" src="${fns:getImageUrl()}${codeManager.imgUrl}" width="50px" height="50px" class="img-polaroid"/> 
				          
				          <c:if test="${ebaySaleType==3 || ebaySaleType==4}"><!--铺货的都是可以自主上传图片  -->
					          <%--  <button class="btn btn-primary" type="button" onclick="operatorImage('1','${codeManager.id}');"style="width: 100px;" >操作图片</button> --%>
					           <button class="btn btn-primary" type="button" onclick="selectPicture('3','${codeManager.id}');"style="width: 100px;" >选择图片</button>
				          </c:if>
					         <%--   <button class="btn btn-primary" type="button" onclick="selectPicture('3','${codeManager.id}');"style="width: 100px;" >选择图片</button> --%>
				      <%--     <c:if test="${ebaySaleType==3}"><!-- 中国直发铺货可以在wish 选择 -->
				           <button class="btn btn-primary" type="button" onclick="operatorImage('3','${codeManager.id}');"style="width: 100px;" >wish选择</button>
					           <button class="btn btn-primary" type="button" onclick="selectPicture('3','${codeManager.id}');"style="width: 100px;" >选择图片</button>
				          </c:if> --%>
                        
                        </td>
                         <!-- 自定义多属性的值 -->
	                       <c:set value="0" var="propertyNumber"></c:set>
	                       
	                        <c:if test="${propertyNum>0}">
			                     <c:forEach items="${codeManager.productPropertyList}" var="productProperty">
			                       	<td>
				                       	<form:input type="hidden" path="codeManagerList[${codeManagerSize}].productPropertyList[${propertyNumber}].id"  value="${productProperty.id}"/>
				                       	<form:input type="hidden" path="codeManagerList[${codeManagerSize}].productPropertyList[${propertyNumber}].codeManagerId"  value="${productProperty.codeManagerId}"/>
				                       	<form:input type="hidden" path="codeManagerList[${codeManagerSize}].productPropertyList[${propertyNumber}].productId"  value="${productProperty.productId}"/>
				                       	<form:input type="hidden" path="codeManagerList[${codeManagerSize}].productPropertyList[${propertyNumber}].propertyName"/>
			                        	
			                        	<form:input path="codeManagerList[${codeManagerSize}].productPropertyList[${propertyNumber}].propertyValue"  id="codeManagerList_${codeManagerSize}_productPropertyList_${propertyNumber}_propertyId"  value="${productProperty.propertyValue}" onblur="translateValue(this)"/>
			                       
			                       	</td>
			                        <c:set var="propertyNumber" value="${propertyNumber+1}" ></c:set>
			                      </c:forEach>
	                        </c:if>
                       
                        <td><form:input path="codeManagerList[${codeManagerSize}].productCode" htmlEscape="false"  class="input-medium code_manager_productCode"  /></td>
                    </tr>
                      <c:set var="codeManagerSize" value="${codeManagerSize+1}"></c:set> 
                    </c:forEach>  
                </tbody>
                <tfoot></tfoot>
            </table>
            </div>
        </div><br/>
        
