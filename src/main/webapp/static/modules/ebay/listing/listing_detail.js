   

$(document).ready(function() {
		var mainProductImgData = new Array();
		var listingId = $("#listing_id").val();
		
        var arr = ["1","2","5"];  
		for(var i=0;i<arr.length;i++){
			var type = arr[i];
			//1、主G图 ，2、细节图  5、特效图
			$('#file-main_g_tu'+type).fileinput({
		        language: 'zh',
		        uploadUrl: _ctx+'/listing/ebayListing/uploadImgs',
		        allowedFileExtensions: ['jpg', 'png', 'gif'],
		        showRemove : true, //隐藏移除按钮
		        enctype : 'multipart/form-data',
		        dropZoneEnabled: false,
		        showClose: false,
		        showPreview : false,
		        initialPreviewShowDelete: true, 
		        initialPreviewAsData: true,
		        initialPreview: mainProductImgData,
		        maxFileSize:2048,
		        layoutTemplates: {
		       // 	actionDelete: '',
		        	actionUpload: ''
		        },
		        uploadExtraData: {
		        	type : type,
		        	listingId : listingId
		        }
		        })
		        .on("filebatchselected", function(event, files) {
		            $(this).fileinput("upload");
		        })
		        .on("fileuploaded", function(event, outData, id, index) {  
		    	var data = outData.response;
		    	   if(data.result){
		    		   data = data.data;
		    	   }else{
		    		   alertx(data.msg);
		    		   return ;
		    	   }
		           //文件上传成功后返回的数据， 此处我只保存返回文件的id  
		           var id = data.id; 
		           var url = data.imageUrl;
		           var tt = data.imageType;
		       		var content = '<div class="col-md-2 enlarge" style="height:150px;width:150px;float:left;" id="img'+id+'">';
			       		content+='<input name="mainImageId'+tt+'" type="hidden" value="'+id+'"/>';
			       		content+='<input name="mainImageUrl'+tt+'" type="hidden" value="'+url+'"/>';
		       		    content+= '<img id="imgPreview" style="height:110px;width:110px"  src="'+(baseUrl+url)+'"></img>';
		       		   if(tt!=1 && tt!='1'){
		       			   content+= '<p><button type="button" style="margin-top:3px;height:30px;width:50px" onclick="deleteimg('+id+','+tt+');" class="btn btn-primary">删除</button></p>';
		       		   }
		       		    content+= '</div>';
		        if(tt==1 || tt=="1"){
		            $("#main_g_tu_div"+tt).html(content);
		        	$("#zhu_g_tu_id_input").val(id);
		        }else{
		        	$("#main_g_tu_div"+tt).append(content);
		        }
		    });
		}
});




/**上传图片 输入 url */
function uploadImg(idT,type){
	
	var img = $("#"+idT+type).val();
	
	var productId = $("#productId").val();
	var listingId = $("#listing_id").val();
	
	if(img==null || img==''){
		alertx("请输入图片url！");
		return ;
	}
	$.ajax({
		url : _ctx+'/listing/ebayListing/insertimg',
		type : 'post',
		data : {
			url : img,
			type : type,
			listingId : listingId
		},
		success : function(data) {
			if(data.result){
				data=data.data;
			}else{
				alertx(data.msg);
				return;
			}
			$("#"+idT+type).val("");
			var url = data.imageUrl;
			var id = data.id;
			var  content = '<div class="col-md-2 enlarge" style="height:150px;width:150px;float:left;" id="img'+id+'" ondrop="drop(event,this)" ondragover="allowDrop(event)" draggable="true" ondragstart="drag(event, this)">';
				 content+='<input name="mainImageId'+type+'" type="hidden" value="'+id+'"/>';
				 content+='<input name="mainImageUrl'+type+'" type="hidden" value="'+url+'"/>';
				 content+= '<img id="imgPreview" style="height:110px;width:110px;"  src="'+(baseUrl+url)+'"></img>';
				 if(type != 1 && type != '1'){
					 content+= '<p><button type="button" style="margin-top:3px;height:30px;width:50px;"  onclick="deleteimg('+id+','+type+');" class="btn btn-primary">删除</button></p>';
				 }
				 content+='</div>';
				 if(type == 1 || type=='1'){
					 $("#"+idT+"_div"+type).html(content);
				 }else{
					 $("#"+idT+"_div"+type).append(content);
				 }
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){	
			alertx("服务异常，请稍后再试");
			errorCapture(XMLHttpRequest, textStatus, errorThrown);	
		}
	})
}


//删除 listing 的图片  listing图片id 和类型 
function deleteimg(id, imageType){
	var listingId = $("#listing_id").val();
	$.ajax({
		url : _ctx+'/listing/ebayListing/deleteimg',
		type : 'post',
		data : {
			id : id,
			imageType : imageType,
			listingId :listingId
		},
		success : function(data) {
			if(data.result){
				$("#img"+id).remove();
			}else{
				alertx(data.msg);
				return ;
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			alertx("服务异常，请稍后再试");
			errorCapture(XMLHttpRequest, textStatus, errorThrown);	
		}
	})
}


/***
 * 0 表示就是保存该 listing
 * 1 保存该listing 并且进行刊登
 * 
 * @param flag
 */
function saveListing(flag){
	

	var siteType='${siteType}';
	
	$("#listing_save_id"+flag).attr("disabled","disabled");
	
	
	var title1 = $("#listing_title1").val();//主标题
	/*var title2 = $("#listing_title2").val();//副标题
*/	if(title1 ==null || title1==''){
		$("#listing_save_id"+flag).removeAttr("disabled");
		alertx("主标题不能为空！");
		return;
	}
/*	if(title2 ==null || title2==''){
		$("#listing_save_id"+flag).removeAttr("disabled");
		alertx("副标题不能为空！");
		return;
	}*/
     var  mainImageNum =$("input[name='mainImageId1']").length;
     	if(mainImageNum<1){
     		$("#listing_save_id"+flag).removeAttr("disabled");
     		alertx("请上传主g 图！");
     		return ;
     	}
     	
     	if(siteType==2 || siteType=='2'){
     		var detailImageNum =$("input[name='mainImageId2']").length;
     		if(detailImageNum<1){
     			$("#listing_save_id"+flag).removeAttr("disabled");
     			alertx("请上传 细节图！");
     			return ;
     		}
     	}
     	
     	if(siteType==1||siteType=='1'){
     		var specialImageNum =$("input[name='mainImageId5']").length;
     		if(specialImageNum<1){
     			$("#listing_save_id"+flag).removeAttr("disabled");
     			alertx("请上传特效图！");
     			return ;
     		}
     	}
     	
	
  	 var fl = true;
 	 $(".code_manager_productCode").each(function(a,b){
		 var value = $(b).val();
		 if(value ==null || value =='' || typeof(value)==undefined){
			 fl = false;
		 }
 		});
 	 if(!fl){
 		 $("#listing_save_id"+flag).removeAttr("disabled");
		 alertx("您还有子代码  的 upc/ean 码没有输入！");
		 return ;
 	 }
 	 var fl1 = true;
 	 $(".code_manager_imageId").each(function(a,b){
 		 var value = $(b).val();
 		 if(value ==null || value =='' || typeof(value)==undefined){
 			 fl1 = false;
 		 }
 	 });
 	 if(!fl1){
 		 $("#listing_save_id"+flag).removeAttr("disabled");
 		 alertx("您还有子代码 的 图片没有选择！");
 		 return ;
 	 }
 	 var fl2 = true;
 	 $(".code_manager_publishPrice").each(function(a,b){
 		 var value = $(b).val();
 		 if(value ==null || value =='' || typeof(value)==undefined){
 			 fl2 = false;
 		 }
 	 });
 	 if(!fl2){
 		 $("#listing_save_id"+flag).removeAttr("disabled");
 		 alertx("您还有子代码 的 价格没有输入！");
 		 return ;
 	 }
 	 
 	 var saleType = $("#saleType").val();
 	 if(saleType==1 || saleType=='1'){//一口价
    	 
 		var price = $("#sellingPrice").val();
 		 if(price==null || price=='' || typeof (price)==undefined){
 			 $("#listing_save_id"+flag).removeAttr("disabled");
 			 alertx("一口价为必填");
 			 return ;
 		 }
 	 }else{//拍卖
	    var price = $("#upsetPrice").val();
 		 if(price==null || price=='' || typeof (price)==undefined){
 			 $("#listing_save_id"+flag).removeAttr("disabled");
 			 alertx("拍卖价格必填");
 			 return ;
 		 }
 	 }
 	 
 /*	 var  lotSize = $("#listing_lotSize").val();
	 if(lotSize==null || lotSize==''){
		 $("#listing_save_id"+flag).removeAttr("disabled");
		 alertx("您还没输入 lotSize！");
		 return ;
	 }*/
/* 	 
	var payMethod = $("input:checkbox[name='product.paymentMethod']:checked").length;
   	 if(payMethod < 1){
   		 $("#listing_save_id"+flag).removeAttr("disabled");
   		 alertx("收款方式必填！");
   		 return ;
   	 }*/
	 
	 
 	 var   multiAttribute  = $("#listing_multiAttribute").val();
 	 
 	 if(multiAttribute || multiAttribute=='1'|| multiAttribute==1){//是多属性
 		 
 	 }else{//不是多属性
 		 var  productNumber = $("#listing_number").val();
		 if(productNumber==null || productNumber=='' || typeof(productNumber)==undefined){
			 $("#listing_save_id"+flag).removeAttr("disabled");
			 alertx("您还没输入数量！");
			 return ;
		 }
 	 }
 	 
	 var publishStyleId = $("#publishStyleMode_id").val();//刊登风格id
	 var logisticsModeId = $("#logisticsMode_id").val();//物流模板id
	 var locationGoodsId = $("#locationofGoods_id").val();//商品所在地模板id
	 var returnPurchaseId = $("#returnPurchase_id").val();//退货模板id
	 var buyerId = $("#buyerRestriction_id").val();//买家限制模板id
	 
	 if(publishStyleId==null || publishStyleId =='' || typeof(publishStyleId) ==undefined){
		 $("#listing_save_id"+flag).removeAttr("disabled");
		 alertx("您还没有维护 刊登风格模板！");
		 //return ;
	 }
	 if(logisticsModeId==null || logisticsModeId =='' || typeof(logisticsModeId) ==undefined){
		 $("#listing_save_id"+flag).removeAttr("disabled");
		 alertx("您还没有维护 物流模板！");
		 //return ;
	 }
	 if(locationGoodsId==null || locationGoodsId =='' || typeof(locationGoodsId) ==undefined){
		 $("#listing_save_id"+flag).removeAttr("disabled");
		 alert("您还没有维护 商品所在地模板！");
		// return ;
	 }
	 if(returnPurchaseId == null || returnPurchaseId =='' || typeof(returnPurchaseId) ==undefined){
		 $("#listing_save_id"+flag).removeAttr("disabled");
		 alertx("您还没有维护  退货模板！");
		 //return ;
	 }
	 if(buyerId == null || buyerId =='' || typeof(buyerId) ==undefined){
		 $("#listing_save_id"+flag).removeAttr("disabled");
		 alertx("您还没有维护 买家限制模板！");
		 //return ;
	 }
	  $("#save_or_kandeng").val(flag);
	  $.ajax({
            cache: true,
            type: "POST",
            url:_ctx+'/listing/ebayListing/saveAndPublish',
            data:$('#searchForm').serialize(),
            async: false,
            error: function(request) {
                alertx("服务异常！请稍后再试");
                $("#listing_save_id"+flag).removeAttr("disabled");
                return ;
            },
            success: function(data) {
            	console.log(data);
            	$("#listing_save_id"+flag).removeAttr("disabled");
            	if(data.result){
            	    window.location.href=_ctx + "/listing/ebayListing";
            	}else if(!data.result){
            		alertx(data.msg);
            		return ;
            	}else{
            		alertx("后台服务异常，请稍后再试！");
            		return ;
            	}
            }
        });
	 
	// $("#searchForm").submit();
}


//切换 物品状况 设置 conditionID
function changeCondition(id){
	 $("#product_conditionID").val(id);
}


	/***
	 * 翻译内容
	 * 
	 * @param obj
	 * @param siteId
	 */
	function transValue(obj,siteId){
		
		var content = $(obj).attr("value");
		if(!isNaN(Number(content))){
			return ;
		}
		$.ajax({
			url : _ctx+"/ebay/productDetail/translate",
			type : 'post',
			data : {
				siteId : siteId,
				content :content
			},
			success : function(data) {
				if(data.result){
					data=data.data;
				}else{
					alertx(data.msg);
					return ;
				}
				if(data!=null && typeof(data)!=undefined && data!=''){
					$(obj).attr("value",data);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){	
				alertx("服务异常，请稍后再试！");
				errorCapture(XMLHttpRequest, textStatus, errorThrown);	
			}
		})
		
	}
