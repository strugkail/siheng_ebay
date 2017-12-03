<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>Listing 选择 子代码</title>
	<meta name="decorator" content="default"/>

	<script type="text/javascript">
		
	
	  var  listingId = "${listingId}"; 
	  var  productId = "${productId}"; 
	  var  preCode = "${preCode}";
	  var  afterCode = "${afterCode}";
	  /** 确认选择 子代码 */
	   function submitChooseData(){

		  var $codeIds =$('input[type="checkbox"][name="codeManagerIds"]:checked');
		  
		  if($codeIds == null || $codeIds.length<1){
			  alert("请选择之后再提交！");
			  return ;
		  }
		  var ids = "";//codeManagerId  集合 
		  $codeIds.each(function() {
			   console.log($(this).attr("id"));
			   ids += $(this).attr("id")+",";
	          });
		      console.log(ids);
		      
		   $.ajax({
					url : '${ctx}/listing/ebayListing/addCodeManager',
					type : 'post',
					data : {
						ids : ids,
						listingId : listingId,
			        	productId : productId,
			        	preCode : preCode
					},
					success : function(data) {
						if(data.result){
							data = data.data;
						}else{
							alert(data.msg);
							return ;
						}
						  console.log(data);
						  window.parent.window.selectCodeManagerData = data;
				          window.parent.window.selectCodeManagerFlag = true;
			              window.parent.window.jBox.close() ;//关闭该 弹框
					},
					error : function(XMLHttpRequest, textStatus, errorThrown){			 
						errorCapture(XMLHttpRequest, textStatus, errorThrown);	
					}
				});
		      
		       
		      
			}
	</script>
	
</head>
<body>
		
	<div style="margin-left: 50px">
		
     <br>
 	<div class="rows" style="left: inherit; margin: 0 auto;text-align:center" >
		<input id="submit_input"  class="btn btn-primary"  onclick="javascript:submitChooseData();" type="button" value="确定选择"/>
	</div>
	<br>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>选择</th>
				<th>sku</th>
				<th>价格</th>
				<th>图片</th>
				<th>UPC/EAN</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${codeManagerList}" var="codeManager" varStatus="status">
			<tr>
			    <td><input type="checkbox" style="height: 20px;width: 20px" id="${codeManager.codeManagerId}" name="codeManagerIds"></td>
				<td>${preCode}${codeManager.sku}${afterCode}</td>
				<td>${codeManager.publishPrice}</td>
				<td><img alt="子代码图片" style="height: 80px;width: 100px" src="${fns:getImageUrl()}${codeManager.codeManagerImage.imageUrl}">  </td>
				<td></td>
			</tr> 
		</c:forEach>
		</tbody>
	</table>
	</div>
</body>
</html>