<%@ page contentType="text/html;charset=UTF-8" %>
<!-- ajax错误信息Modal -->
<div class="modal fade" id="ajaxMessageErrorModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" style="width: 60%" role="document">
    	<div class="modal-content">
      		<div class="modal-header">
	   		 	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true" onclick="clear();">&times;</span></button>
	        	<font class="modal-title" face="楷书" size="5" color="blue">出错啦！<input type="text" id="zhErrorMessage" style="border: none;
    outline: none; font-size:20px;color :red"></input></font>  	
      		</div>
      		<div class="modal-body" >
      			<input type="button" id="detailMessage" value="查看详细信息"/>	
				<input type="button" id="clearMessage" onclick="clearMessage();" value="收起详细信息"/>
				<div class="container-fluid" id="ErrorMessage" hidden="hidden">
					<textarea id="detailErrorMessage" style="width: 100%; height: 600px;"></textarea>
				</div>
					
      		</div>
  		</div>
	</div>
</div>