//ajax500异常捕获
		function errorCapture(XMLHttpRequest, textStatus, errorThrown){
			if(XMLHttpRequest.status =='500'){
				var detailErrorMessage = XMLHttpRequest.responseText; 
				$("#ajaxMessageErrorModal").modal('show');
				$("#clearMessage").hide();
				$("#ErrorMessage").hide();
				$("#detailMessage").show();
				
				//解析出中文报错信息
				var detailErrorArray = detailErrorMessage.split("\n");
				var detailErrorArray2 = detailErrorArray[1].split(":");
				var zhErrorMessage = detailErrorArray2[1];
				$("#zhErrorMessage").val(zhErrorMessage);
				
				$("#detailMessage").click(function(){
					$("#detailErrorMessage").val(detailErrorMessage);
					$("#ErrorMessage").show();
					$("#detailMessage").hide();
					$("#clearMessage").show();
				});
			}	
		}
		
		function clearMessage(){
			$("#ErrorMessage").hide();
			$("#detailMessage").show();
			$("#clearMessage").hide();
		}
		function clear(){
			$("#ErrorMessage").html('');
		}