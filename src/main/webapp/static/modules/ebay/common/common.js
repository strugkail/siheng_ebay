
	var participle;
	var fenge = "\n";
	
	//点击拆词 加载弹出框
	function sepWord(data) {
		participle = data;
		$("#seperateWordModal").modal('show');
		$("#chaichi").val("");
		$("#splitChiInput").val("");	
	};	

	
	function closeFenci(){
		$("#seperateWordModal").modal('hide');
	}
	// 清空拆词
	function clearChaiCi(id){
		if($("#"+id).val() == "" || $("#"+id).val("") == null){
			return;
		}
		$("#"+id).val("");
	}
	
	
	// 公用的分割词语方法
	function splitChi() {
		var newChi = "";// 新生成的内容
		var chaichiTet = myTrim($("#chaichi").val());// 取待分词内容
		var splitChiInput = $("#splitChiInput").val();// 取分隔符
		
		if (chaichiTet != "") {
			var strs = new Array(); //定义一数组 
			if(splitChiInput != ""){
				strs = chaichiTet.split(splitChiInput);// 字符分割 				
				for (i = 0; i < strs.length; i++) {
					if(i == strs.length - 1){
						newChi = newChi + strs[i];
						break;
					}
					newChi = newChi + strs[i] + fenge; // 分割后的字符输出 ,\r\n回车换行
				}
			}else{
				newChi = chaichiTet;
			}
			$("#"+participle).val(newChi);
 			$('#seperateWordModal').modal('hide');
			
		} else {
			alertx("分词内容不能为空！")
		}
	};
	
	//去除字符串前后空格
	function myTrim(x) {
	    return x.replace(/^\s+|\s+$/g,'');
	}
	
	// 生成标题  type  1  生成主标题    2 生成副标题
	
	function gentitle_button(type) {
		var keyName = $("#keywords"+type).val();
		var endName = $("#otherkeywords"+type).val();
		
		if(keyName == "" || keyName == null){
			alertx("关键字不能为空");
			return;
		}
		$.ajax({
			type : "post",
			url : _ctx+'/ebay/product/generateTitle',
			data : {
				keyName : keyName,
				endName : endName
			},
			success : function(data) {
				var codeData = "";
				var titleData = "";
				for (var i = 0; i < data.length; i++) {
					titleData = titleData + data[i].title + fenge;
					codeData = codeData + data[i].code + fenge;
				}
				//产品标题
				$("#product_title"+type).val(titleData);
				$("#product_title_code"+type).val(codeData);
				//listing 标题
				$("#listing_title"+type).val(data[0].title);
				$("#listing_title_code"+type).val(data[0].code);
				
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){			 
				errorCapture(XMLHttpRequest, textStatus, errorThrown);	
			}
		});
	}
	
	  
    /**标题关键字的翻译   type 为 1 表示是产品的翻译，2 表示是listing 页面的翻译*/
   function  translateTitle(type,flag){
   	 
	   var title=$("#product_title1").val();
	   var title2=$("#product_title2").val();
	     if(type==2 || type=='2'){
	    	 title=$("#listing_title1").val();
	    	 title2=$("#listing_title2").val();
	     }
		 var titleKey = $("#keywords1").val();
		 var titleotherKey = $("#otherkeywords1").val();
		 var titleKey2 = $("#keywords2").val();
		 var titleotherKey2 = $("#otherkeywords2").val();
		 
		 var siteId=$("#platformSite_platformId").val();
			if(siteId == null || siteId == ''){
				//alert("标题翻译时，请先选择站点！");
				return ;
			}
			
		$.ajax({
				url : _ctx + '/ebay/productDetail/translateTitle',
				type : 'post',
				data : {
					siteId : siteId,
					title :title,
					keyWord1:titleKey,
					otherKeyWord1:titleotherKey,
					subtitle :title2,
					keyWord2:titleKey2,
					otherKeyWord2:titleotherKey2
				},
				success : function(data) {
					
					
					if(data.result){
						data = data.data;
					}else{
						//如果是 手动触发 要进行错误提示
						if(flag!=1 && flag!='1'){
							alertx(data.msg);
						}
						
						$("#product_title1").css("color","red");
						$("#keywords1").css("color","red");
						$("#otherkeywords1").css("color","red");
						
						$("#product_title2").css("color","red");
						$("#keywords2").css("color","red");
						$("#otherkeywords2").css("color","red");
						
						$("#listing_title1").css("color","red");
						$("#listing_title2").css("color","red");
						return ;
					}
					if(data!=null && typeof(data)!=undefined && data!=''){
					   	 
					   	 $("#product_title1").val(data.titleT);
						 $("#keywords1").val(data.keyWord1T);
						 $("#otherkeywords1").val(data.otherKeyWord1T);
						 
						 $("#product_title2").val(data.subtitleT);
						 $("#keywords2").val(data.keyWord2T);
						 $("#otherkeywords2").val(data.otherKeyWord2T);
						 
						 //设置 listing 的翻译 内容
						 $("#listing_title1").val(data.titleT);
						 $("#listing_title2").val(data.subtitleT);
						 
						 
						 $("#product_title1").css("color","black");
						 $("#keywords1").css("color","black");
						 $("#otherkeywords1").css("color","black");
							
						 $("#product_title2").css("color","black");
						 $("#keywords2").css("color","black");
						 $("#otherkeywords2").css("color","black");
							
						 $("#listing_title1").css("color","black");
						 $("#listing_title2").css("color","black");
						 
						 
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){	
					 $("#product_title1").css("color","red");
					 $("#keywords1").css("color","red");
					 $("#otherkeywords1").css("color","red");
					 
					 $("#product_title2").css("color","red");
					 $("#keywords2").css("color","red");
					 $("#otherkeywords2").css("color","red");
					 
					 //设置 listing 的翻译 内容
					 $("#listing_title1").css("color","red");
					 $("#listing_title2").css("color","red");
					
					errorCapture(XMLHttpRequest, textStatus, errorThrown);	
				}
			})
	      		
	 
    }
    

   /**拍卖一口价的切换    1 是一口价  2是拍卖 */
	function changeSaleType(type){
		
  	    var listing_category = "";
  	 
		    var html=" <label class='control-label'>上架时间：</label>";
		        html+="<select name='product.shelfTime'>";
		        html+="<option value='GTC'>卖完为止</option>";
		        if(type=="2" || type==2){
		        	html+="<option value='Days_1'>1天</option>";
		        	//设置 listing 分类 Chinese 
		        	listing_category="CHINESE";
		        }
		        html+="<option value='Days_3'>3天</option>";
		        html+="<option value='Days_5'>5天</option>";
		        html+="<option value='Days_7'>7天</option>";
		        html+="<option value='Days_10'>10天</option>";
		        if(type=="1" || type==1){
		        html+="<option value='Days_30'>30天</option>";
		        listing_category="FIXED_PRICE_ITEM";
		        }
		        html+="</select>";
			$("#shelfTime_div").html(html);
		    $("#listing_category").val(listing_category);
	}
	

	/** 翻译 每个 item specifics 以及输入的标题  产品完善资料页面 */
	function translateValue(obj){
		var siteId = $("#platformSite_platformId").val();
		if(siteId == null || siteId == ''){
			//alert("请先选择站点！");
			return ;
		}
		/***
		 * 如果是 数字不进行翻译 
		 */
		var content = $.trim($(obj).val());
		if(!isNaN(Number(content))){
			return ;
		}
		var id = $(obj).attr("id");
		var content2= $.trim($("#"+id+"_old").val());
		if(content!=null && content!='' && content!=undefined){
			$.ajax({
				url : _ctx+'/ebay/productDetail/translate',
				type : 'post',
				data : {
					siteId : siteId,
					content :content
				},
				success : function(data) {
					if(data.result){
						data = data.data;
					}else{
						$(obj).css("color","red");
						return ;
					}
					if(data!=null &&  typeof(data)!=undefined && data!=''){
							$(obj).css("color","black");
							$("#"+id).val(data);
							$("#"+id+"_old").val(data);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){	
					$(obj).css("color","red");
					errorCapture(XMLHttpRequest, textStatus, errorThrown);	
				}
			})
		
		}
		
	} 
	
	
	/** 删除  item_specifics 自定义属性*/
	function deleteItemSpecifics(obj){
		$(obj).parent().parent().remove();
	}

	
	/**
	 * 切换 物品状况设置 conditionId
	 */
    function changeCondition (id){
   	 if(id != null && id !=''){
	    	 $("#product_conditionID").val(id);
   	 }
    }
	
	
	/**	 删除 列 属性 该列 要有相同的class*/
	function deleteSelfProperty(obj){
		var cssName =$(obj).parent().attr("class");
		$("."+cssName).remove();
	}  
	
	/** 删除 子 代码 ，多属性中的 删除一行数据 还要判断不能全部删除了的*/
	function deleteCodeManager(obj){
	  var className = $(obj).parent().parent().attr("class");
	  var num = $("."+className).length;
	  if(num < 2 ){
		  alertx("不能再删了，最少要留一个吧！")
		  return ;
	  }
		$(obj).parent().parent().remove();
	}
	
	
	  
	
	/**
	 * select 下拉框可编辑
	 * 
	 * @param select1
	 */
    function editable(selectObj){  
   	   if(selectObj.value == "请输入"){  
   	      var newvalue = prompt("请输入自定义内容","");  
   	      if(newvalue){  
   	         addSelected(selectObj,newvalue,newvalue);  
   	      }else{
   	    	$(selectObj).attr("value","");
   	      }  
   	   }  
   	}  
   	  
   	function addSelected(selectObj,valueObj,textObj){  
   	    if (document.all)    {  
   	            var Opt = selectObj.document.createElement("OPTION");  
   	            Opt.text = textObj;  
   	            Opt.value = valueObj;  
   	            selectObj.options.add(Opt); 
   	           // $(Opt).attr("selected","selected");
   	            Opt.selected = true;  
   	    }else{  
   	            var Opt = new Option(textObj,valueObj,false,false);  
   	            Opt.selected = true;  
   	            selectObj.options[selectObj.options.length] = Opt;  
   	    }  
   	}  
    
   	
   	
   	/***
   	 * 资料完善  标题部分 翻译以及 listing 标题查重
   	 * 
   	 * @param objContent  要翻译的内容
   	 * @param siteId  站点id
   	 * @param listingId  listingID
   	 * @param msg 校验不通过 要提示的信息
   	 */
    function translateAndCheckDuplicate(objContent,siteId,listingId,msg,type){
    	
    	var content = $.trim($(objContent).val());
    	
    	if(content==null ||content=='' || typeof(content)==undefined){
    		return ;
    	}
    	if(siteId ==null ||siteId==''||typeof(siteId) ==undefined){
    		siteId = $("#platformSite_platformId").val();
    	}
    	if(siteId ==null ||siteId==''||typeof(siteId) ==undefined){
    		return ;
    	}
    	var id = $(objContent).attr("id");
    	
    	$.ajax({
			url : _ctx+'/ebay/productDetail/translateAndCheckDuplicate',
			type : 'post',
			data : {
				siteId : siteId,
				content :content,
				listingId:listingId,
				type:type
			},
			success : function(data) {
				if(data.result){
					data = data.data;
				}else{
					alertx(msg+data.msg);
					$(objContent).css("color","red");
					return ;
				}
				if(data!=null && data!='' && typeof (data)!=undefined){
					$(objContent).val(data);
					$(objContent).css("color","black");

				}
				
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){			 
				alertx(msg+"请求异常！标题翻译失败。");
				errorCapture(XMLHttpRequest, textStatus, errorThrown);	
			}
		})
    }
   	
    /***
     * 翻译页面需要翻译的地方
     * 
     * 
     * @param obj   需要翻译的地方 
     * @param siteId  站点 要翻译的语言
     */
	function translateAllContent(obj,siteId){
		
		if(siteId == null || siteId == ''){
			return ;
		}
		var content = $.trim($(obj).val());
		if(content==null || content=='' || typeof(content)==undefined){
			return ;
		}
		/***
		 * 如果是 数字不进行翻译 
		 */
		if(!isNaN(Number(content))){
			return ;
		}
		if(content!=null && content!='' && content!=undefined){
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
							$(obj).css("color","red");
							return ;
						}
						if(data!=null && typeof(data)!=undefined && data!=''){
							$(obj).css("color","black");
							$(obj).attr("value",data);
						}
					},
					error : function(XMLHttpRequest, textStatus, errorThrown){	
						$(obj).css("color","red");
						errorCapture(XMLHttpRequest, textStatus, errorThrown);	
					}
				})
			
			}
	}
    
    
	/***
	 * 需要翻译的 地方都要有 class 为 translateContent 这个属性
	 * @param siteId
	 */
    function  commonTranslate(siteId){
    	$(".translateContent").each(function (){
			translateAllContent($(this),siteId);
		})
    }
    

    /***
     * 同步 价格 和  数量,要保证 class 一样
     
     *  
     * @param obj
     */
    function synchronousValue(obj){
    	var $value = $(obj).prev();
    	var clsName =  $($value).attr("class");
    	var value = $($value).attr("value");
    	if(value==null || value=='' || typeof(value) == undefined){
    		alert("请输入 值 后 再进行操作！");
    		return ;
    	}
    	$("."+clsName).each(function (){
            $(this).attr("value",value);
    	});
    }
	
    
    
    
    /***
     * 遍历 select 默认选中 第一个 值为 空 的选项
     * 
     * @param selectId
     */
    function selectFirstDefault(selectId){
    	var html="";
    	$("#"+selectId+" option").each(function (){  
    		 var value = $(this).val();  
    		 var text = $(this).text();  
    	//	 console.log("selectFirstDefault "+value +"---------"+text);
    		 if(value==''||value==null || typeof(value)==undefined ){
    			 html +="<option value='"+value+"' selected='selected'>"+text+"</option>";
//    			 $(this).attr("selected","selected");
    		 }else{
    			 html +="<option value='"+value+"'>"+text+"</option>";
    		 }
    	});
    	//console.log(html);
    	$("#"+selectId).html(html);
    	
    }
    