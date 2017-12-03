<%--
  Created by IntelliJ IDEA.
  User: strugkail.li
  Date: 2017/9/4
  Time: 9:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>ebay产品开发管理</title>
    <meta name="decorator" content="default"/>
    <style type="text/css">
        /*#imgPreview:hover {*/
            /*transform: scale(4.5);*/
            /*-webkit-transform: scale(4.5); !*Safari 和 Chrome*!*/
            /*-moz-transform: scale(4.5); !*Firefox*!*/
            /*-ms-transform: scale(4.5); !*IE9*!*/
            /*-o-transform: scale(4.5); !*Opera*!*/
            /*z-index: 1;*/
            /*border: 1px solid dimgrey;*/
            /*position: absolute;*/
            /*left:300px;*/
        /*}*/
    </style>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#btnSubmit").click(function () {
                $("#createName").val($.trim($("#createName").val()));
                $("#sysParentCode").val($.trim($("#sysParentCode").val()));
                $("#productName").val($.trim($("#productName").val()));
            })
        });
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").attr("action","${ctx}/product/develop/toBeDevelop");
            $("#searchForm").submit();
            return false;
        }

        /**
		 * 签收任务
		 */
		function claim(taskId) {
			$.get('${ctx}/act/ebaytask/claim' ,{taskId: taskId}, function(data) {
				if (data == 'true'){
		        	top.$.jBox.tip('签收完成');
		            location = '${ctx}/product/develop/toBeDevelop';
				}else{
		        	top.$.jBox.tip('签收失败');
				}
		    });
		}
        // 对Date的扩展，将 Date 转化为指定格式的String
        // 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
        // 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
        Date.prototype.Format = function (fmt) { //author: meizz
            var o = {
                "M+": this.getMonth() + 1, //月份
                "d+": this.getDate(), //日
                "H+": this.getHours(), //小时
                "m+": this.getMinutes(), //分
                "s+": this.getSeconds(), //秒
                "q+": Math.floor((this.getMonth() + 3) / 3), //季度
                "S": this.getMilliseconds() //毫秒
            };
            if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
            for (var k in o)
                if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            return fmt;
        }

        /**
		 * 回退任务
		 */
		function roolback(taskId,collectId,saleType){
			$.get('${ctx}/act/ebaytask/robackgroup' ,{taskId: taskId}, function(data) {
				if (data == 'true'){
		        	top.$.jBox.tip('回退完成');
		            location = '${ctx}/product/develop/toCalculate?collectId='+collectId+'&saleType='+saleType;
				}else{
		        	top.$.jBox.tip('回退失败');
				}
		    });
			
			
			
		}
        
        function onceSubmit(){
        	$("#productName").val($.trim($("#productName").val()));
        	$("#productUrl").val($.trim($("#productUrl").val()))
        	$("#searchForm").sumbit();
        }
		
    </script>
    <style type="text/css">
        #contentTable tr th{
            text-align: center;
        }
        #contentTable tr td{
            text-align: center;
        }
        #contentTable tr td img{
            width: 150px;
            height: 110px;
        }
        #contentTable{
            table-layout:fixed;
        }
    </style>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/product/productDev">待开发列表</a></li>
</ul>
<form:form id="searchForm" modelAttribute="toBeDevelop" action="${ctx}/product/develop/toBeDevelop" method="post" class="breadcrumb form-search" >
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <div class="row">
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <label>站点：</label>
        <form:select class="required" path="siteId" style="width: 200px;">
            <form:option value="" label="--请选择--" />
            <c:forEach items="${siteList}" var="site" >
                <form:option value="${site.id}" >${site.siteName}</form:option>
            </c:forEach>
        </form:select>
        &nbsp;&nbsp;<label>销售类型：</label>
        <form:select class="required" path="saleTypeId" style="width: 200px;">
            <form:option value="" label="--请选择--" />
            <form:options items="${fns:getDictList('saleType')}"  itemLabel="label" itemValue="value" htmlEscape="false" />
        </form:select>
     	<label>产品名称：</label>
     		<form:input path="productName" htmlEscape="false" maxlength="40" class="input-medium"/>
        <label>商品地址：</label>
        	<form:input path="productUrl" htmlEscape="false" maxlength="40" class="input-medium"/>
    </div>
    <br/>

    <div class="row">
        &nbsp;&nbsp;&nbsp;<label>销售组：</label>
        	<form:select class="required" path="saleGroupId" style="width: 200px;">
		 	   		<form:option value="" label="--请选择--" />
		 	   		<form:options items="${groupList}" itemLabel="sellerGroupName" itemValue="groupId" htmlEscape="false" />
		   	</form:select>
        &nbsp;&nbsp;&nbsp;<label>是否签收：</label>
        	<form:select class="required" path="sign" style="width: 200px;">
		 	   		<form:option value="" selected="selected" label="--请选择--" />
		 	   		<form:option value="1" label="已签收" />
		 	   		<form:option value="2" label="未签收" />
		   	</form:select>
        &nbsp;&nbsp;&nbsp;<label>采集时间：</label>
        <input id="dateStart" name="dateStart" value="${dateStart}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" style="width:200px"/>
        至
        <input id="dateEnd" name="dateEnd" value="${dateEnd}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" style="width:200px"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="btnSubmit"  class="btn btn-primary" type="submit" value="查询"/>
    </div>
        <br>
</form:form>

<sys:message content="${message}"/>
<table id="contentTable" style="table-layout:fixed;" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <%--<th style="width: 30px">#</th>--%>
        <th>图片</th>
        <th>站点</th>
        <th>销售类型</th>
        <th>销售组</th>
        <th>商品名称</th>
        <th>商品地址</th>
        <th>采集人</th>
        <th>采集时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="act">
    	<c:set var="task" value="${act.todotask}" />
		<c:set var="vars" value="${act.vars}" />
		<c:set var="procDef" value="${act.procDef}" /><%--
		<c:set var="procExecUrl" value="${act.procExecUrl}" /> --%>
		<c:set var="status" value="${act.status}" />
	    <c:set var="processBusiness" value="${act.probusiness}" />

        <tr>
            <td>
                <img src="${fns:getImageUrl()}${processBusiness.imgUrl}" id="imgPreview" class="img-polaroid"/>
            </td>
            <td>
                    ${processBusiness.siteName}
            </td>
            <td>
                <c:if test="${processBusiness.saleType ==1}">海外仓精品</c:if>
                <c:if test="${processBusiness.saleType ==2}">虚拟海外仓精品</c:if>
                <c:if test="${processBusiness.saleType ==3}">中国直发仓铺货</c:if>
                <c:if test="${processBusiness.saleType ==4}">虚拟海外仓铺货</c:if>
            </td>
             <td>
                    ${processBusiness.saleGroupName}
            </td>
            <td>
                    ${processBusiness.productName}
            </td>
            <td style="word-wrap:break-word;">
                <a href="${processBusiness.productUrl}" target="_blank">${processBusiness.productUrl}</a>
            </td>
            <td>
                    ${processBusiness.createName}
            </td>
            <td>
                <fmt:formatDate value="${processBusiness.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
				<c:if test="${empty task.assignee}">
					<a href="javascript:claim('${task.id}');">签收任务</a>
				</c:if>
				<c:if test="${not empty task.assignee}"><%--
					<a href="${ctx}${procExecUrl}/exec/${task.taskDefinitionKey}?procInsId=${task.processInstanceId}&act.taskId=${task.id}">办理</a> --%>
					<a href="${ctx}/act/ebaytask/form?taskId=${task.id}&taskName=${fns:urlEncode(task.name)}&taskDefKey=${task.taskDefinitionKey}&procInsId=${task.processInstanceId}&procDefId=${task.processDefinitionId}&saleType=${processBusiness.saleType}">任务办理</a>
				</c:if>
				<c:if test="${not empty task.assignee}"><%--
					<a href="${ctx}${procExecUrl}/exec/${task.taskDefinitionKey}?procInsId=${task.processInstanceId}&act.taskId=${task.id}">办理</a> --%>
				    <a href="javascript:roolback('${task.id}','${processBusiness.businessId}','${processBusiness.saleType}');">回退任务</a>
				</c:if>
				<a target="_blank" href="${pageContext.request.contextPath}/act/diagram-viewer?processDefinitionId=${task.processDefinitionId}&processInstanceId=${task.processInstanceId}">跟踪</a><%--
				<a target="_blank" href="${ctx}/act/task/trace/photo/${task.processDefinitionId}/${task.executionId}">跟踪2</a>
				<a target="_blank" href="${ctx}/act/task/trace/info/${task.processInstanceId}">跟踪信息</a> --%>
			</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>