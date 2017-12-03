 <%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
  <script type="text/javascript" src="${ctxStatic}/modules/ebay/product/PropertyMap.js"></script>
  <script>
  
  	var baseUrl = '${fns:getImageUrl()}';

	var selectPictureFlag = false;
	var selectPictureData = new Array(); //选择图片绑定之后的数据回写 
	
	/** 3、子 */
	function operatorListingImage(type,codeManagerId){
		selectPictureFlag = false;
		selectPictureData = new Array(); 
		var listingId = $("#listing_id").val();
		var productId = $("#productId").val();
		
		var url = "${ctx}/listing/ebayListing/selectImage?listingId="+listingId+"&productId="+productId+"&type="+type+"&codeManagerId="+codeManagerId;
		$.jBox.open("iframe:" + url, "Listing 图片选择 ", 1200, 800,{persistent: true,showScrolling: false,buttons: { '关闭': 'ok' },closed: function () { 
		        //这里处理选择的 主 g 图和 子 g 图 
		        if(selectPictureFlag){
		        	for(var i in selectPictureData){
			        	var image = selectPictureData[i];
			        	var id = image.id;
			        	var url = image.imageUrl;
			        	var imageId = image.imageId;
			        	var codeManagerId = image.codeManagerId;
			        	// 操作成功返回的图片集合  
			        	if(type==3 || type=="3"){//子 g 图 
			        		$("#sku_image_url_class_"+codeManagerId).attr("src",baseUrl+url);
			    			$("#image_id_"+codeManagerId).val(imageId);
			    			$("#image_url_"+codeManagerId).val(url);
			    			
			        	}else if(type==1 || type=="1"){
		        			$("#main_g_tu_div"+type).html(generalHtml(type,id,url,imageId));
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
		html+=' <input name="mainImageId'+type+'" value="'+imageId+'" type="hidden"/>';
		html+='  <input name="mainImageUrl'+type+'" value="'+url+'" type="hidden"/>';
		html+='<img id="imgPreview" height="110px" width="110px"  src="'+(baseUrl+url)+'"></img>';
		if(type!=1 && type!='1'){
			html+='<p><button type="button" onclick="deleteimg('+id+','+type+');" class="btn btn-primary">删除</button></p>';
		}
		html+='</div>';
		return html;
	}
	
	</script>
  
  <div class="page-header">
          	<label class="col-md-1 control-label text-right" for="description"><strong>多属性设置:</strong></label>
          <div class="row"  style="width:100%;overflow: auto" >
            <table class="table table-bordered"  id="itemSpecifics" >
                <thead >
                    <tr id="duoshuxing_add_tr" >
                        <th>sku</th>
                        <!-- <th>数量</th> -->
                        <th>价格</th>
                        <th style="width: 100px">图片地址</th> 
                        <c:forEach items="${listingDetail.listingPropertyNames}" var="names" varStatus="status">
                        		<th>
                        			<form:input path="listingPropertyNames[${status.count-1}]" value="${names}" id="listingPropertyNames[${status.count-1}]"  onblur="translateValue(this)"/>
                        		</th>
                        </c:forEach>
                       
                        <th>UPC/EAN</th>
                    </tr>
                </thead>
                <tbody  id="codemanager_tbody">
                
                
                <c:set var="codeManagerSize" value="0"></c:set>
                
                <c:forEach items="${listingDetail.listingCodeManagerList}" varStatus="status1" var="codeManager">
                
                <!--  产品下的 sku 集合列表  -->
                    <tr id="sku_${codeManagerSize}_tr">
                         <td id="sku_${codeManagerSize}_td1">${codeManager.sku}</td>
                         <form:input path="listingCodeManagerList[${codeManagerSize}].id" type="hidden" value="${codeManager.id}"/>
                         <form:input path="listingCodeManagerList[${codeManagerSize}].sku" type="hidden" value="${codeManager.sku}"/>
                         <form:input path="listingCodeManagerList[${codeManagerSize}].codeManagerId" type="hidden" value="${codeManager.codeManagerId}"/>
                       <%--  <td><form:input path="listingCodeManagerList[${codeManagerSize}].recommendNumber" htmlEscape="false" class="input-medium" value="${codeManager.recommendNumber}"/></td> --%>
                        <td><form:input path="listingCodeManagerList[${codeManagerSize}].publishPrice"  id="code_manager_${codeManager.codeManagerId}_publishPrice"   value="${codeManager.publishPrice}" htmlEscape="false" class="input-medium code_manager_publishPrice"/></td>
                       
                        <td style="width: 100px" id="code_manager_image_td_${codeManager.codeManagerId}">
                        
                           <form:input path="listingCodeManagerList[${codeManagerSize}].codeManagerImage.imageId" id="image_id_${codeManager.codeManagerId}" type="hidden" htmlEscape="false" class="input-medium code_manager_imageId" />
                           <form:input path="listingCodeManagerList[${codeManagerSize}].codeManagerImage.imageUrl" id="image_url_${codeManager.codeManagerId}" type="hidden" htmlEscape="false" class="input-medium" />
                           
	                       <img id = "sku_image_url_class_${codeManager.codeManagerId}" src="${fns:getImageUrl()}${codeManager.codeManagerImage.imageUrl}" width="50px" height="50px" class="img-polaroid"/> 
				          
				           <button class="btn btn-primary" type="button" onclick="operatorListingImage('3','${codeManager.codeManagerId}');"style="width: 100px;" >选择图片</button>
				           <%-- <button class="btn btn-primary" type="button" onclick="operatorListingImage('3','${codeManager.codeManagerId}');"style="width: 100px;" >wish选择</button> --%>
                        
                        </td><!-- 自定义多属性的值 -->
			                  <c:forEach items="${codeManager.productPropertyList}" var="listingProperty" varStatus="status2">
			                     <td>
			                       	<form:input path="listingCodeManagerList[${codeManagerSize}].productPropertyList[${status2.count-1}].value" id="listingCodeManagerList_${codeManagerSize}_productPropertyList_${status2.count-1}_value" value="${listingProperty.value}"  onblur="translateValue(this)"/>
			                       	<form:input type="hidden" path="listingCodeManagerList[${codeManagerSize}].productPropertyList[${status2.count-1}].id" />
			                       	<form:input type="hidden" path="listingCodeManagerList[${codeManagerSize}].productPropertyList[${status2.count-1}].listingId" />
			                       	<form:input type="hidden" path="listingCodeManagerList[${codeManagerSize}].productPropertyList[${status2.count-1}].siteId"/>
			                       	<form:input type="hidden" path="listingCodeManagerList[${codeManagerSize}].productPropertyList[${status2.count-1}].codeManagerId" />
			                     </td>
			                  </c:forEach>
                       	
                        <td><form:input path="listingCodeManagerList[${codeManagerSize}].productCode" htmlEscape="false" class="input-medium code_manager_productCode"  /></td>
                    </tr>
                      <c:set var="codeManagerSize" value="${codeManagerSize+1}"></c:set> 
                    </c:forEach>  
                </tbody>
                <tfoot></tfoot>
            </table>
            </div>
        </div><br/>
