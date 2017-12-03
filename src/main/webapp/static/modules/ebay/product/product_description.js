
	//新增数据
	function addProductDescription(){
		var html="";
			html+= "<tr>";
			html+= "<td align='center'>";
			html+=	"<textarea name='productDescritions' class='productDescritions' onblur='translateDescription(this)'/>";
			html+=	"<input name='productDescritionsT' class='productDescritionsT' type='hidden'></td>";
			html+= "<td align='center'>";
			html+= "<input type='button' value='新增' class='input-medium btn btn-primary' onclick='javascript:addProductDescription()'> &nbsp;&nbsp;&nbsp;&nbsp;";
			html+= "<input type='button' value='删除' class='input-medium btn btn-primary' onclick='javascript:deleteProductDescription(this)'>";
			html+= "</td>";	   
			html+= "</tr>";	  
		$("#product_description_table").append(html);
	}


	//删除新增的数据
	function deleteProductDescription(obj){
		var  len = $("#product_description_table tr").length;
		if(len<=2){
			alertx("不能再删了，最少留一个吧！");
			return ;
		}else{
			$(obj).parent().parent().remove();
		}
	}
	
	
	function translateDescription(obj){
		
		var siteId = $("#platformSite_platformId").val();
		if(siteId == null || siteId == ''){
			//alert("请先选择站点！");
			return ;
		}
		var content = $.trim($(obj).val());
		/***
		 * 如果是 数字不进行翻译 
		 */
		if(!isNaN(Number(content))){
			return ;
		}
		
		var objClass = $(obj).attr("class");
		var obj2 = $(obj).parent().find("."+objClass+"T");
		var objClass2 = $(obj2).attr("class");
		var content2= $.trim($(obj2).val());
		
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
							//alert(data.msg);
							$(obj).css("color","red");
							return ;
						}
						if(data!=null && typeof(data)!=undefined && data!=''){
							$(obj).attr("value",data);
							$(obj2).attr("value",content);
						}
					},
					error : function(XMLHttpRequest, textStatus, errorThrown){	
						$(obj).css("color","red");
						alertx("服务异常，请稍后再试！");
						errorCapture(XMLHttpRequest, textStatus, errorThrown);	
					}
				})
			
			}
		
	}
	
	
	/**翻译所有*/
	function transAllDescription(){
		$(".productDescritions").each(function (){
			translateDescription($(this));
		})
	}