
	/***
	 *  删除主图（imgType=1），细节图（imgType=2），拼图（imgType=4）  5 特效图 
	 * @param id
	 * @param imgType
	 */
    function deleteimg(id, imgType){
    	 $("#img"+id).remove();
    	 /*		 var productId = $("#productId").val();
   $.ajax({
			url : _ctx+'/ebay/product/deleteimg',
			type : 'post',
			data : {
				id : id,
				imgType : imgType,
				productId :productId
			},
			success : function(data) {
				 $("#img"+id).remove();
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){	
				alert("服务异常，请求失败");
				errorCapture(XMLHttpRequest, textStatus, errorThrown);	
			}
		})*/
		
    }
    
     /** 风格模板的切换 */
    function selectPublishStyleMode(id){
      	 
     if(id!=null && id !=''){
     	$.ajax({
			url : _ctx+'/mode/publishStyleMode/getOne',
			type : 'post',
			data : { 
				id : id
			},
			success : function(data) {
				   $("#publishStyleMode_id").val(data.id);
				   $("#publishStyleMode_id_name").html(data.modeName);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){		
				alertx("服务异常，请求失败");
				errorCapture(XMLHttpRequest, textStatus, errorThrown);	
			}
		})
      }
     }
     
     
     
     /**提交数据  如果 flag =0 表示保存 生成 listing ，flag=1 表示提交流程  */
     function saveProduct(flag){
          $("#save_product_id"+flag).attr("disabled","disabled");
    	  if(flag==0 || flag=='0'){
    		 	 var siteId=$("#platformSite_platformId").val();//站点
    	    	 var mainTitle = $("#product_title1").val();//主标题
    	    	 var publishStyleId = $("#publishStyleMode_id").val();//刊登风格id
    	    	 var logisticsModeId = $("#logisticsMode_id").val();//物流模板id
    	    	 var locationGoodsId = $("#locationofGoods_id").val();//商品所在地模板id
    	    	 var returnPurchaseId = $("#returnPurchase_id").val();//退货模板id
    		  
	    	 var sellerAccount=$("#sellerAccount").val();
	    	 if(sellerAccount == null || sellerAccount ==''){
	    		 $("#save_product_id"+flag).removeAttr("disabled");
	    		 alertx("您还没有选择卖家账号！");
	    		 return ;
	    	 }
	       	 if(siteId==null || siteId ==''){
	       		 alertx("您还没有选择站点！");
	       		$("#save_product_id"+flag).removeAttr("disabled");
	       		 return ;
	    	 }
	    	 if(mainTitle==null || mainTitle ==''){
	    		 alertx("您还没有输入标题！");
	    		 $("#save_product_id"+flag).removeAttr("disabled");
	    		 return ;
	    	 }
	    	 
	    	var siteType = $("#siteType").val();
	    	if(siteType==1 || siteType=='1'){//该是 特效图进行校验

		     	var specialImage = true;
		     	$(".mainImageId5").each(function(a,b){
		    		    if($(b).val()==null || $(b).val()==''){
		    		    	specialImage = false;
		    		    }
		    		  });
		    	 if(!specialImage || $(".mainImageId5").length<1){
		    		 $("#save_product_id"+flag).removeAttr("disabled");
		    		 alertx("您还没有输入特效 图！");
		    		 return ;
		    	 }
	    	}
	    	if(siteType==2 || siteType=='2'){//该是 细节图 进行校验
	    		
	    		var detailImage = true;
	    		$(".mainImageId2").each(function(a,b){
	    			if($(b).val()==null || $(b).val()==''){
	    				detailImage = false;
	    			}
	    		});
	    		if(!detailImage || $(".mainImageId2").length<1){
	    			$("#save_product_id"+flag).removeAttr("disabled");
	    			alertx("您还没有输入细节 图！");
	    			return ;
	    		}
	    		
	    	}
	    	
	     	var mainImage = true;
	     	$(".mainImageId1").each(function(a,b){
	    		    if($(b).val()==null || $(b).val()==''){
	    		    	mainImage = false;
	    		    }
	    		  });
	    	 if(!mainImage || $(".mainImageId1").length<1){
	    		 $("#save_product_id"+flag).removeAttr("disabled");
	    		 alertx("您还没有输入主 g 图！");
	    		 return ;
	    	 }
	    	 var sku =$("#product_sys_parent_code").val();
	    	 if(sku==null || sku =='' || typeof (sku) == undefined ){
	    		 $("#save_product_id"+flag).removeAttr("disabled");
	    		 alertx("SKU(Custom lable) 不能为空，必填！ ");
	    		 return ;
	    	 }
	    	 
	    	 /***
	    	  * 1有多属性  0 没有多属性 
	    	  */
	    	 var isHasCodeManager = $("#is_has_mutil_code_manager").val();
	    	 
	    	 if(isHasCodeManager!=1 && isHasCodeManager!='1'){//单属性
	    		 var upcEan = $("#product_productCode").val();
	    		 if(upcEan == null || upcEan ==''){
	    			 $("#save_product_id"+flag).removeAttr("disabled");
	    			 alertx("请输入 产品 productID ！");
	    			 return ;
	    		 }
	    		 
	    		 var  productNumber = $("#product_number").val();
	    		 if(productNumber==null || productNumber==''){
	    			 $("#save_product_id"+flag).removeAttr("disabled");
	    			 alertx("您还没输入数量！");
	    			 return ;
	    		 }
	    	 }
	    /*	 var  lotSize = $("#product_lotSize").val();
	    	 if(lotSize==null || lotSize==''){
	    		 $("#save_product_id"+flag).removeAttr("disabled");
	    		 alertx("您还没输入 lotSize！");
	    		 return ;
	    	 }*/
	    	 
	    	 if(isHasCodeManager==1 || isHasCodeManager=='1'){
	    		 
	    		 var fl2 = true;
	         	 $(".code_manager_publishPrice").each(function(a,b){
	        		 var value = $(b).val();
	        		 if(value ==null || value =='' || typeof(value)==undefined){
	        			 fl2 = false;
	        		 }
	         	 });
	         	 if(!fl2){
	         		 $("#save_product_id"+flag).removeAttr("disabled");
	        		 alertx("您还有子代码 的 价格没有输入！");
	        		 return ;
	         	 }
	         	 var fl = true;
	         	 $(".code_manager_productCode").each(function(a,b){
	        		 var value = $(b).val();
	        		 if(value ==null || value =='' || typeof(value)==undefined){
	        			 fl = false;  
	        		 }
	         		});
	         	 if(!fl){
	         		 $("#save_product_id"+flag).removeAttr("disabled");
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
	         		 $("#save_product_id"+flag).removeAttr("disabled");
	        		 alertx("您还有子代码 的 图片没有选择！");
	        		 return ;
	         	 }
	         
	         	 var fl3 = true;
	         	 $(".code_manager_sku").each(function(a,b){
	        		 var value = $(b).val();
	        		 if(value ==null || value =='' || typeof(value)==undefined){
	        			 fl3 = false;
	        		 }
	         		});
	         	 if(!fl3){
	         		 $("#save_product_id"+flag).removeAttr("disabled");
	        		 alertx("您还有子代码的 sku 没有输入！");
	        		 return ;
	         	 }
	    	 }
	 
	    	 var saleType = $("#product_saleType").val();
	    	 
	    	 if(saleType==1 || saleType=='1'){//一口价
	    	 
	    		var price = $("#product_sellingPrice").val();
	    		 if(price==null || price=='' || typeof (price)==undefined){
	         		 $("#save_product_id"+flag).removeAttr("disabled");
	    			 alertx("一口价为必填");
	    			 return ;
	    		 }
	    	 }else{//拍卖
		    		var price = $("#product_upsetPrice").val();
	    		 if(price==null || price=='' || typeof (price)==undefined){
	    			 $("#save_product_id"+flag).removeAttr("disabled");
	    			 alertx("拍卖价格必填");
	    			 return ;
	    		 }
	    	 }
	    	 
	    	var payMethod = $("input:checkbox[name='product.paymentMethod']:checked").length;
	    	 if(payMethod < 1){
	    		 $("#save_product_id"+flag).removeAttr("disabled");
	    		 alertx("收款方式必填！");
	    		 return ;
	    	 }
	    	 if(publishStyleId==null || publishStyleId ==''){
	    		 if(!confirm("您还没有选择刊登风格模板！")){ $("#save_product_id"+flag).removeAttr("disabled"); return ;}
	    	 }
	    	 if(logisticsModeId==null || logisticsModeId ==''){
	    		 if(!confirm("您还没有选择物流模板！")){$("#save_product_id"+flag).removeAttr("disabled"); return ;}
	    	 }
	    	 if(locationGoodsId==null || locationGoodsId ==''){
	    		 if(!confirm("您还没有选择商品所在地模板！")){$("#save_product_id"+flag).removeAttr("disabled"); return ;}
	    	 }
	    	 if(returnPurchaseId == null || returnPurchaseId ==''){
	    		 if(!confirm("您还没有选择退货模板！")){ $("#save_product_id"+flag).removeAttr("disabled"); return ;}
	    	 }
	    	 
	    	  $("#save_or_kandeng").val(flag);
	    	  $.ajax({
	                cache: true,
	                type: "POST",
	                url:_ctx+'/ebay/productDetail/saveProduct',
	                data:$('#searchForm').serialize(),
	                async: false,
	                error: function(request) {
	                    alertx("服务异常！请稍后再试");
	                    $("#save_product_id"+flag).removeAttr("disabled");
	                    return ;
	                },
	                success: function(data) {
	                	//console.log(data);
	                	if(data.result){
	                		$("#save_product_id"+flag).removeAttr("disabled");
	                	    window.location.reload();	
	                	}else if(!data.result){
	                		alertx(data.msg);
	                		$("#save_product_id"+flag).removeAttr("disabled");
	                	}else{
	                		alertx("后台服务异常，请稍后再试！");
	                		$("#save_product_id"+flag).removeAttr("disabled");
	                	}
	                }
	            });
	    	 
    	  } else {
    		  if (!confirm("是否提交至下一流程？")) {
    			  $("#save_product_id"+flag).removeAttr("disabled");
    				return;
    			}
    			var index = layer.load(3,{shade: [0.1, '#000']});  //风格3的加载
                  			
    			 $.ajax({
 	                cache: true,
 	                type: "POST",
 	                url:_ctx+'/ebay/productDetail/submitFlow',
 	                data:$('#flowForm').serialize(),
 	                async: false,
 	                error: function(request) {
 	                    alertx("服务异常！请稍后再试");
 	                    $("#save_product_id"+flag).removeAttr("disabled");
 	                    return ;
 	                },
 	                success: function(data) {
 	                	//console.log(data);
 	                	if(data.result){
 	                		$("#save_product_id"+flag).removeAttr("disabled");
 	                	    window.location.href= _ctx+"/listing/ebayListing/";	
 	                	}else if(!data.result){
 	                		alertx(data.msg);
 	                		$("#save_product_id"+flag).removeAttr("disabled");
 	                	}else{
 	                		alertx("后台服务异常，请稍后再试！");
 	                		$("#save_product_id"+flag).removeAttr("disabled");
 	                	}
 	                }
 	            });
      	  }
     }
     
     
    function changeSite(obj){
    	
    	var siteId = $(obj).val();
    	
    	if(siteId != null && siteId !=''){
    		var productId= $("#productId").val();
    		//获取相关的分类数据
    		getCategoryList(productId,siteId);
    		// 获取可以刊登的店铺集合
    		getSellerList(productId,siteId);
    		//翻译 相关的操作 
    		commonTranslate(siteId);
    	}else{
    		
    		 $("#item_specifics_tbody").html("");
	    	 $("#product_category1").val("");
	    	 $("#product_category_code1").val("");
	    	 
	    	 $("#product_category2").val("");
	    	 $("#product_category_code2").val("");
	    	 $("#seller_select_div").html("<label>卖家账户 ：</label> <select name ='sellerAccount' id='sellerAccount'><option value=''>请选择</option></select>");
    	}
    }
     
     
	 
     /* 更换站点的时候 卖家账号也要跟着变化 */
    /* function changeSite(obj){
    	 
    	var siteId = $(obj).val();
		if(siteId != null && siteId !=''){
			
    	var url=_ctx+"/ebay/productDetail/getCategoryAndItemSpecifics";
    	var productId= $("#productId").val();
				$.ajax({ 
				    cache: true,
				    type: "POST",
				    url: url,
				    data:{"siteId":siteId,"productId":productId},
				    async: false,
				    error: function(data) {
				    	$("#platformSite_platformId").val("");
				    	alertx("服务异常，请稍后再试！");
				    },
				    success: function(data) {
				    	var begin = new Date().getTime();
				    	if(data.result){
				    		data = data.data;
				    	}else{
				    		$("#platformSite_platformId").val("");
				    		alertx(data.msg);
				    		return ;
				    	}
				    	//对产品的 分类进行 设置
				    	var categoryList=data.categoryList;
				    	var sellerList=data.sellerList;
				    	var conditions = null;
				    	// 设置页面的货币单位
				    	var CurrencyName =data.CurrencyName;
				    	$(".CurrencyName").html(CurrencyName);
				    	//设置站点标志  1 表示是 使用特效图的  2 是表示细节图
				    	$("#siteType").val(data.siteType);
				    	// 设置分类
				    	if(categoryList!=null && typeof(categoryList)!=undefined){
					    	var categoryLenth = categoryList.length;
					    	var item_html="";
					    	var item = null;
					    	for(var i=0;i<categoryLenth;i++){
					    		$("#product_category"+(i+1)).val(categoryList[i].categoryName);
					    		$("#product_category_code"+(i+1)).val(categoryList[i].categoryCode);
					    		if(i==0){
					    			$("#listing_mutil_attribute").val(categoryList[i].mutilAttribute);
					    			if(categoryList[i].conditions!=null){
						    			conditions = categoryList[i].conditions;
						    		    }
					    			item = categoryList[i].itemSpecificsList;
					    	 		if(item!=null && typeof (item) != undefined){
							    		for(var j in item){
							    			   item_html +="<tr>";
							    			   item_html +="<td><input name='itemSpecificsNames"+(i+1)+"' type='text'  readonly='readonly' value='"+item[j].name+"'/></td>";
							    			   if(item[j].values!=null && item[j].values!=undefined ){
							    			         item_html +="<td><select name='itemSpecificsValues"+(i+1)+"' onChange='editable(this);' >";
							    			         for(var k in item[j].values){
							    			        	 //设置 选中一些默认值
							    			        	 var itemValue = item[j].values[k];
							    			        	 if(item[j].name=='MPN' && (itemValue == 'Does Not Apply' || itemValue == 'No Applicable' || itemValue == 'Non applicabile' || itemValue == 'Nicht Zutreffend'|| 'Non Applicable')){
							    			        		item_html +="<option value='"+item[j].values[k]+"' selected='selected'>"+item[j].values[k]+"</option>";
							    			        	 }else if(item[j].name=='Brand' && itemValue == 'Unbranded'){
							    			        		item_html +="<option value='"+item[j].values[k]+"' selected='selected'>"+item[j].values[k]+"</option>";
							    			        	 }else {
							    			        		item_html +="<option value='"+item[j].values[k]+"'>"+item[j].values[k]+"</option>";
							    			        	 }
							    			         }
							    			         if(item[j].selectMode !='SELECTION_ONLY'){
							    			        	 item_html +="<option value=''>空</option>";
							    			        	 item_html +="<option value='请输入'>请输入自定义内容</option>";
							    			         }
							    			         item_html +="</select>";
							    			         item_html +="</td>";
							    			   }else{
							    				    item_html +="<td><input name='itemSpecificsValues"+(i+1)+"' type='text'   value='' /></td>";
							    			    }
							    			   
							    			   item_html +="<td></td></tr>";
							    		}
						    		}
						    	}
							   	 $("#item_specifics_tbody").html(item_html);
					    		}
				    		} 
				    	
				       	//设值 coditionID 和 物品状况 
				    	
				    	if(conditions!=null && conditions!= "" && typeof(conditions)!=undefined){
				    		var conditions_html = "&nbsp;&nbsp;<label>物品状况<font color='red'></font>:</label>";
				    		    conditions_html += "<select name='product.categoryStatus' onchange='changeCondition(this.options[this.options.selectedIndex].id)'>";
				    		   var conditionId = "";
				    		   for(var i in conditions){
				    			   console.log(conditions[i].name);
				    			   var conditionId1 = conditions[0].id;
				    			   // 设置选择默认值
					    		   if(conditions[i].name=='New without tags'){
					    			   conditionId = conditions[i].id;//如果存在  New without tags 则默认选中
					    			   conditions_html += "<option value='"+conditions[i].name+"' id='"+conditions[i].id+"' selected='selected'>"+conditions[i].name+"</option>";
					    			}else{
					    				conditions_html += "<option value='"+conditions[i].name+"' id='"+conditions[i].id+"'>"+conditions[i].name+"</option>";
					    			}
				    		   }
				    		    conditions_html += "</select>";
				    		    if(conditionId=='' || typeof (conditionId)==undefined){
				    		    	conditionId =   conditionId1;
				    		    }
				    		    $("#product_conditionID").val(conditionId);//默认设置加载第一个 conditionID
				    		    $("#product_categoryStatus_div").html(conditions_html);
				    	}
				    	
				    	//设置卖家账号（刊登店铺）
				    	if(sellerList!=null && typeof(sellerList)!= undefined){
				    	// 对卖家账户进行设置
				    	var html="";
				    	    html= " <label> 卖家账户 ：</label> <select name ='sellerAccount' id='sellerAccount' onchange='changeSeller(this.value)' autocomplete='off'>";
				    	    html+="<option value= ''>请选择</option>";
				    	for(var i in  sellerList){
				    	    html += "<option value= '"+sellerList[i].id+"'>"+sellerList[i].sellerName+"</option>";
				    	   }
				    	     html +="</select>";
				    	//$("#seller_select_div").html(html);
				    	$("#sellerAccount").html(html);
				    	}
				    	//设置 翻译后的 子 sku  多属性 翻译内容 
				    	var codeManagerList = data.codeManagerList;
				    	
				    	if(codeManagerList!=null && typeof(codeManagerList) != undefined){
				    		for(var i in codeManagerList){
				    			var productPropertyList = codeManagerList[i].productPropertyList;
				    			if(productPropertyList!=null && typeof(productPropertyList)!=undefined){
				    				for(var k in productPropertyList){
				    					var transValues = productPropertyList[k].translateValue;
				    					var transName = productPropertyList[k].translateName;
				    					if(transValues !=null && typeof(transValues)!=undefined){
				    						$("#codeManagerList_"+i+"_productPropertyList_"+k+"_propertyId").val(transValues);
				    					}
				    					if(transName !=null && typeof(transName)!=undefined){
				    						$("#codeManagerList0_productPropertyList"+k+"_propertyName").val(transName);
				    					}
				    				}
				    			}
				    		}
				    	}
				    	
				    	
				    	
				    	// 翻译描述 和 标题
				    	translateTitle(1,1);
				    	//
				    	transAllDescription();
				    	console.log(new Date().getTime()-begin);
				    }
				  });	
		    }else{
		    	 $("#item_specifics_tbody").html("");
		    	 $("#product_category1").val("");
		    	 $("#product_category_code1").val("");
		    	 
		    	 $("#product_category2").val("");
		    	 $("#product_category_code2").val("");
		    	 $("#seller_select_div").html("<label>卖家账户 ：</label> <select name ='sellerAccount' id='sellerAccount'><option value=''>请选择</option></select>");
	   			//$("#sellerAccount_bak").val("");
		    	 
		    }
     }*/
      
     
     
     
     /**切换 卖家账户的时候进行 listing的查询 */
    
    /***
     * 卖家账户的切换  如果没有一级分类，没有站点 也是不能选择
     */
     function changeSeller(id){
   		 if(id =='' || id == null || typeof(id)==undefined){
   			// selectFirstDefault("sellerAccount");
   			 return ;
     	 }else{ 
     		 
     		 var categoryName = $("#product_category1").val();
     		 if(categoryName==null || categoryName =='' || typeof(categoryName)==undefined){
     			 selectFirstDefault("sellerAccount");
     			 alertx("没有一级分类，请稍后再试！");
     			 return ;
     		 }
     		 
	    	var rate  =  $("#listing_rate").val();
	    	if(rate ==null || rate =='' || typeof(rate)==undefined){
	    		selectFirstDefault("sellerAccount");
	    		alertx("选择店铺前请先填写 利润率 ！");
	    		return;
	    	}
     		 
     		var url=_ctx+"/ebay/productDetail/getListingMode";
     		var productId = $("#productId").val();
     		var siteId = $("#platformSite_platformId").val();
     	
  			$.ajax({ 
  				    cache: true,
  				    type: "POST",
  				    url: url,
  				    data:{"siteId":siteId,"productId":productId,"sellerId":id,"rate":rate,"categoryName":categoryName},
  				    async: false,
  				    error: function(data) {
  				    	//$("#sellerAccount").val("");
  				    	selectFirstDefault("sellerAccount");
  				    	alertx("服务异常，请稍后再试！"+data.responseText);
  				    },
  				    success: function(data) {
  				    	if(data.result){
  				    		data=data.data;
  				    	}else{
  				    		selectFirstDefault("sellerAccount");
  				    		alertx(data.msg);
  				    		return ;
  				    	}
  				    	var paypalAccount = data.paypalAccount;//是不是 大 paypal 
  				    	$("#product_paypalType").val(paypalAccount);
  				    	
  				    	var codeManagerList = data.codeManagerList;//价格反算 
  				        // 绑定的模板信息 加载 赋值 
  				        for(var i in codeManagerList){
  				        	$("#code_manager_"+codeManagerList[i].id+"_publishPrice").val(codeManagerList[i].publishPrice);
  				        	$("#code_manager_"+codeManagerList[i].id+"_productCode").val(codeManagerList[i].productCode);
  				        	/* $("#code_manager_sku_"+codeManagerList[i].id).val(codeManagerList[i].listingSkuCode); */
				        	$("#code_manager_sku_2_"+codeManagerList[i].id).val(codeManagerList[i].listingSkuCode);
  				            if(i==0){
  				            	$("#product_productCode").val(codeManagerList[i].productCode);
  				            }
  				        }
  				    	//设置  sku custom label
  		 				$("#product_sys_parent_code").val(data.listingSkuCode);
  				   		 //刊登风格模板
  				         var StyleMode = data.PublishStyleMode;
  				         if(StyleMode!=null && typeof (StyleMode) !=undefined ){
  				             selectPublishStyleMode(StyleMode.id);
  				         }
  				         //商品所在地
  				         var LocationofGoods = data.LocationofGoods;
  				         if(LocationofGoods!=null && LocationofGoods !=undefined ){
				             changSkuLocation(LocationofGoods.id);
				         }
  				         //退货
  				         var ReturnPurchase = data.ReturnPurchase;
  				         if(ReturnPurchase!=null && ReturnPurchase !=undefined ){
  				        	changSkuTuihuo(ReturnPurchase.id);
				         }
  				         //买家限制
  				         var BuyerRestriction = data.BuyerRestriction;
  				         if(BuyerRestriction!=null && BuyerRestriction !=undefined ){
  				        	changBuyerRestriction(BuyerRestriction.id);
				         }
				         var LogisticsMode = data.LogisticsMode;
  				         if(LogisticsMode!=null && LogisticsMode !=undefined ){
  				        	changeLogisticsMode(LogisticsMode.id);
				         }
  				         
  				       //$("#product_paypalType").val(data.paypalAccount);
  				     	//$("#sellerAccount_bak").val(data.seller.id);
  				    }	 
  		 });	
         }
      }
     
     
     
     
     /**计算价格，通过输入的利润率进行计算价格 */
     function calcListingPrice(value){
    	var  rate = parseFloat(value);
    	if(rate == null || rate =='' || typeof(rate)==undefined || rate <= 0 ){
    		return ;
    	}
	  	var sellerId = $("#sellerAccount").val();
	  	if(sellerId == null || sellerId=='' || typeof(sellerId)==undefined){
	  		//alert("请先选择 刊登店铺 ！");
	  		return ;
	  	}
    	var categoryName = $("#product_category1").val();
        if(categoryName==null || categoryName =='' || typeof(categoryName)==undefined){
    			// alertx("没有一级分类，请稍后再试！");
    		   return ;
    	   }
        var productId = $("#productId").val();
	  	var siteId = $("#platformSite_platformId").val();
	  	
	    $.ajax({
	 			url : _ctx+'/ebay/productDetail/calcListingPrice',
	 			type : 'post',
	 			data : {"siteId":siteId,"productId":productId,"sellerId":sellerId,"rate":rate,"categoryName":categoryName},
	 			success : function(data) {//返回的结果  codemanager  和 物流模板 
	 				
	 				if(data.result){
	 					data = data.data;
	 				}else{
	 					alertx(data.msg);
	 					return ;
	 				}
	 				var codeManagerList = data.codeManagerList; //价格反算 
				        // 绑定的模板信息 加载 赋值 
				        for(var i in codeManagerList){
				        	$("#code_manager_"+codeManagerList[i].id+"_publishPrice").val(codeManagerList[i].publishPrice);
				        	/* $("#code_manager_sku_"+codeManagerList[i].id).val(codeManagerList[i].listingSkuCode); */
				        	$("#code_manager_sku_2_"+codeManagerList[i].id).val(codeManagerList[i].listingSkuCode);
				        }
	 				//设置  sku custom label
	 				$("#product_sys_parent_code").val(data.listingSkuCode);
	 				
	 				// 设置物流模板
	 				var LogisticsMode = data.LogisticsMode;
				    if(LogisticsMode!=null && typeof(LogisticsMode) !=undefined ){
				        	changeLogisticsMode(LogisticsMode.id);
			         }   
				         
				     $("#product_paypalType").val(data.paypalAccount);
	 			},
	 			error : function(XMLHttpRequest, textStatus, errorThrown){			
	 				alertx("服务异常，请求失败");
	 				errorCapture(XMLHttpRequest, textStatus, errorThrown);	
	 			}
	 		});
     }
     
     /***
      * 切换站点获取  相关的分类信息（item specifics ， 物品状态 ，以及站点对应的 币种 单位，是应该用 特效图或则细节图的 标志 siteType 1 特效图，其他就是 细节图）
      * 
      * @param productId
      * @param siteId
      */
     function getCategoryList(productId,siteId){
    	 
    	 //console.log("getCategoryList  productId is "+productId+" siteId is   "+siteId);
    	 if(productId!=null && siteId!=null){
    		 $.ajax({
    				url : _ctx+'/ebay/productDetail/getCategoryList',
    				type : 'post',
    				data : { 
    					productId:productId,
    					siteId:siteId
    				},
    				success : function(data) {
    					if(data.result){
    						data = data.data;
    						// 设置页面的货币单位
    				    	var CurrencyName = data.CurrencyName;
    				    	$(".CurrencyName").html(CurrencyName);
    				    	//设置站点标志  1 表示是 使用特效图的  2 是表示细节图
    				    	$("#siteType").val(data.siteType);
    				    	//分类信息 
    				    	var categoryList= data.categoryList;
    				    	var conditions = null;
    				     	// 设置分类
    				    	if(categoryList!=null && typeof(categoryList)!=undefined){
    					    	var categoryLenth = categoryList.length;
    					    	var item_html="";
    					    	var item = null;
    					    	for(var i=0;i<categoryLenth;i++){
    					    		$("#product_category"+(i+1)).val(categoryList[i].categoryName);
    					    		$("#product_category_code"+(i+1)).val(categoryList[i].categoryCode);
    					    		if(i==0){
    					    			$("#listing_mutil_attribute").val(categoryList[i].mutilAttribute);
    					    			if(categoryList[i].conditions!=null){
    						    			conditions = categoryList[i].conditions;
    						    		    }
    					    			item = categoryList[i].itemSpecificsList;
    					    	 		if(item!=null && typeof (item) != undefined){	
    							    		for(var j in item){
    							    			   item_html +="<tr>";
    							    			   item_html +="<td><input name='itemSpecificsNames"+(i+1)+"' type='text'  readonly='readonly' value='"+item[j].name+"'/></td>";
    							    			   if(item[j].values!=null && item[j].values!=undefined ){
    							    			         item_html +="<td><select name='itemSpecificsValues"+(i+1)+"' onChange='editable(this);' >";
    							    			         for(var k in item[j].values){
    							    			        	 //设置 选中一些默认值
    							    			        	 var itemValue = item[j].values[k];
    							    			        	 if(item[j].name=='MPN' && (itemValue == 'Does Not Apply' || itemValue == 'No applicable' || itemValue == 'Non applicabile' || itemValue == 'nicht zutreffend'|| 'Non applicable')){
    							    			        		item_html +="<option value='"+item[j].values[k]+"' selected='selected'>"+item[j].values[k]+"</option>";
    							    			        	 }else if(item[j].name=='Brand' && itemValue == 'Unbranded'){
    							    			        		item_html +="<option value='"+item[j].values[k]+"' selected='selected'>"+item[j].values[k]+"</option>";
    							    			        	 }else {
    							    			        		item_html +="<option value='"+item[j].values[k]+"'>"+item[j].values[k]+"</option>";
    							    			        	 }
    							    			         }
    							    			         if(item[j].selectMode !='SELECTION_ONLY'){
    							    			        	 item_html +="<option value=''>空</option>";
    							    			        	 item_html +="<option value='请输入'>请输入自定义内容</option>";
    							    			         }
    							    			         item_html +="</select>";
    							    			         item_html +="</td>";
    							    			   }else{
    							    				    item_html +="<td><input name='itemSpecificsValues"+(i+1)+"' type='text'  class='translateContent' value='' /></td>";
    							    			    }
    							    			   
    							    			   item_html +="<td></td></tr>";
    							    		}
    						    		}
    						    	}
    							   	 $("#item_specifics_tbody").html(item_html);
    					    		}
    				    		} 
    				    	
    				       	//设值 coditionID 和 物品状况 
    				    	
    				    	if(conditions!=null && conditions!= "" && typeof(conditions)!=undefined){
    				    		var conditions_html = "&nbsp;&nbsp;<label>物品状况<font color='red'></font>:</label>";
    				    		    conditions_html += "<select name='product.categoryStatus' onchange='changeCondition(this.options[this.options.selectedIndex].id)'>";
    				    		   var conditionId = "";
    				    		   for(var i in conditions){
    				    			  // console.log(conditions[i].name);
    				    			   var conditionId1 = conditions[0].id;
    				    			   // 设置选择默认值
    					    		   if(conditions[i].name=='New without tags'){
    					    			   conditionId = conditions[i].id;//如果存在  New without tags 则默认选中
    					    			   conditions_html += "<option value='"+conditions[i].name+"' id='"+conditions[i].id+"' selected='selected'>"+conditions[i].name+"</option>";
    					    			}else{
    					    				conditions_html += "<option value='"+conditions[i].name+"' id='"+conditions[i].id+"'>"+conditions[i].name+"</option>";
    					    			}
    				    		   }
    				    		    conditions_html += "</select>";
    				    		    if(conditionId=='' || typeof (conditionId)==undefined){
    				    		    	conditionId =   conditionId1;
    				    		    }
    				    		    $("#product_conditionID").val(conditionId);//默认设置加载第一个 conditionID
    				    		    $("#product_categoryStatus_div").html(conditions_html);
    				    	}
    				    	
    						
    					}else{
//    						$("#platformSite_platformId").val("");
    						selectFirstDefault("platformSite_platformId");
    						alertx(data.msg);
    						return ;
    					}
    					
    				},
    				error : function(XMLHttpRequest, textStatus, errorThrown){		
    					alertx("获取 分类数据 ，请求失败！请稍后再试");
    					$("#platformSite_platformId").val("");
    					errorCapture(XMLHttpRequest, textStatus, errorThrown);	
    				}
    			})
    		 
    	 }
    	 
     }
     
     
     /***
      * 切换站点 获取 可 刊登的店铺集合,先拿到 一级分类，然后有了 一级分类才能够选择 店铺。
      * 
      * @param productId
      * @param siteId
      */
     function getSellerList(productId,siteId){
    	// console.log("getSellerList  productId is "+productId+" siteId is   "+siteId);
    	 
    	 if(productId!=null && siteId!=null){
    		 $.ajax({
    			 url : _ctx+'/ebay/productDetail/getSellerList',
    			 type : 'post',
    			 data : { 
    				 productId:productId,
    				 siteId:siteId
    			 },
    			 success : function(data) {
    				 if(data.result){
    					 data=data.data;
    				 }else{
    					 alertx(data.msg);
    					 return ;
    				 }
    					var sellerList = data;
				    	//设置卖家账号（刊登店铺）
				    	if(sellerList!=null && typeof(sellerList)!= undefined){
				    	// 对卖家账户进行设置
				    	var html="";
				    	    html+="<option value=''>请选择</option>";
					    	for(var i in  sellerList){
					    	    html += "<option value= '"+sellerList[i].id+"'>"+sellerList[i].sellerName+"</option>";
					    	   }
					    	$("#sellerAccount").html(html);
				    	}
    				 
    			 },
    			 error : function(XMLHttpRequest, textStatus, errorThrown){		
    				 alertx("服务异常，请求失败");
    				 errorCapture(XMLHttpRequest, textStatus, errorThrown);	
    			 }
    		 }) 
    		 
    	 }
     }
     
     
     