<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <script type="text/javascript">
	$(document).ready(function() {
		showShoppingType();
		showShieldCountryName();
		showSiteName();
		for(var i=1;i < 10;i++){
            shippingTypeChange(i);
        }
	});
	
	/*
		页面加载的时候，
		1.如果运输方式为null,就隐藏；反之，显示
	*/
 	function showShoppingType(){
		var arr=["2","3","4","6","7","8","9"];
		for(var i in arr){
			console.log(arr[i]);
			var a = $('#shippingTypeId'+arr[i]+' option:selected').val();
			if (a.length != 0) {
				var ui = document.getElementById("display"+arr[i]);
				ui.style.display = "inline";
			}
		}
	} 
	
	/*
		运输方式改变
		1.控制首件运费和可运至国家是否显示
	*/
	function show(v,i){
		var ui = document.getElementById("display"+i);
		if (v.length == 0) {
			ui.style.display = "none";
			return;
		}
		ui.style.display = "inline";
        shippingTypeChange(i);
	}

	/*
		修改功能，显示运至**国家
	*/
	function showSiteName(){
		var value = $('#siteId option:selected').attr("value");
		if(value.length == 0){
			return ;
		}
		var b = $('#siteId option:selected').text();
		$(".siteName").html(b);
	}
	
	/*
		修改功能，显示屏蔽国家
	*/
	function showShieldCountryName(){
		var b = $('#shieldDestinationId option:selected').attr("id");
		$("#countryName").html(b);
		var shieldCountryNameId = document.getElementById("shieldCountryNameId");
		shieldCountryNameId.style.display = "inline";
	}
	/*
		屏蔽目的地改变
		1.屏蔽国家发生改变
	*/
	function shieldCountryName() {
		var b = $('#shieldDestinationId option:selected').attr("id");
		var value = $('#shieldDestinationId option:selected').attr("value");
		$("#countryName").html(b);
		var shieldCountryNameId = document.getElementById("shieldCountryNameId");
		if (value.length == 0) {
			shieldCountryNameId.style.display = "none";
			return;
		}
		shieldCountryNameId.style.display = "inline";
	}

	/* 
		站点改变：
		1.改变境内和境外运输方式
		2.改变可运至国家
	*/
	function siteIdChange() {
		var a = $('#siteId option:selected').val();
		var currency = $('#siteId option:selected').attr("id");
		var siteShortName = $('#siteId option:selected').attr("title");
		if (a.length != 0) {
			var b = $('#siteId option:selected').text();
			$(".siteName").html(b);
			$(".currency").html(currency);
			$.ajax({
				url : "${ctx}/logistics/sendToCountry",
				type : "post",
				chache : "true",
				async : false,
				data : {
					"siteName" : siteShortName
				},
				dataType : "json",
				success : function(data) {
					$(".country").html('');
					var list=data.sendToCountryList;
					for ( var i in list) {
						for(j=0;j<5;j++){
							$("#country"+[j]).append("&nbsp;&nbsp;&nbsp;<input type='checkbox' name='externalList["+j+"].country' value="+list[i].name+">"+ list[i].description);
						}
					}
					var list2=data.shippingTypeInsideList;
					var list3=data.shippingTypeOutsideList;
					for(var i=1;i<10;i++){
						$("#shippingTypeId"+[i]).empty();
						$("#shippingTypeId"+[i]).append("<option value='' selected='selected'>请选择</option>");
					}
					for (var j = 0; j < list2.length; j++) {  
						for(i=1;i<5;i++){
							$("#shippingTypeId"+[i]).append("<option value='" + list2[j].name + "' >" + list2[j].description + "</option>");
						}
                    }  
					for (var j = 0; j < list3.length; j++) {
						for(i=5;i<10;i++){
							$("#shippingTypeId"+[i]).append("<option value='" + list3[j].name + "' >" + list3[j].description + "</option>");
						}
                    }  
				},
				error : function(data) {
				}
			});
		} else {
			$(".country").html('');
			$(".siteName").html('');
			$(".currency").html('');
		}
	}
	/* 
		判断模板名称不能重复
	*/
	function modelNameJudgment(name){
		var name = $.trim(name);
		if(name!=null && name!='' && typeof(name)!=undefined){
			$.ajax({
					url : "${ctx}/logistics/modelNameJudgment",
					type : "post",
					chache : "true",
					async : false,
					data : {
						"modeName" : name
					},
					dataType : "json",
					success : function(data) {
						if(data){
							alertx("模板名称已经存在，请重新输入！");
							$("#modelName").val("");
						}
					},
					error : function(data) {
						
					}
				});
			}
    }

    /*
        页面加载和运输方式改变的时候执行
     */
    function shippingTypeChange(type){
        var description=$("#shippingTypeId"+type).find("option:selected").text();
        if($.trim(description)=='请选择'){
            $("#decriptionId"+type).val("");
        }else{
            $("#decriptionId"+type).val(description);
        }
    }

</script>
</head>

</html>