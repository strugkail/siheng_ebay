<%--
  Created by IntelliJ IDEA.
  User: strugkail.li
  Date: 2017/9/4
  Time: 10:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>产品开发管理</title>
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
            $("#searchForm").submit();
            return false;
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
    <li class="active"><a href="${ctx}/product/develop/developed">已开发列表</a></li>
</ul>

<form:form id="searchForm" modelAttribute="product" action="${ctx}/product/develop/developed" method="post" class="breadcrumb form-search">
    <div class="row">
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <input id="flag" name="flag" type="hidden" value="${allflag}"/>
        <label>产品名称：</label><form:input path="name" htmlEscape="false" id="productName" maxlength="40" class="input-medium"/>
        <label>产品代码：</label><form:input path="sysParentCode" id="sysParentCode" htmlEscape="false" maxlength="40" class="input-medium"/>
        &nbsp;<label>开发人：</label><form:input path="createName" htmlEscape="false" id="createName" maxlength="40" class="input-medium"/>
    </div>
    <br/>

    <div class="row">
        <label>开发时间：</label>
        <input id="dateStart" name="dateStart" value="${dateStart}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" style="width:200px"/>
        至
        <input id="dateEnd" name="dateEnd" value="${dateEnd}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" style="width:200px"/>
        <%--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label>来源：</label>--%>
        <%--<form:select class="required" path="developmentType" style="width: 200px;">--%>
            <%--<form:option value="" label="--请选择--" />--%>
            <%--<form:options items="${fns:getDictList('developmentType')}" itemLabel="label" itemValue="value" htmlEscape="false" />--%>
        <%--</form:select>--%>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
    </div>
    <br>
</form:form>

<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>图片</th>
        <th>产品名称</th>
        <th>产品代码</th>
        <th>开发日期</th>
        <th>开发人</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="product" varStatus="s">
        <tr>
            <td>
                <img src="${fns:getImageUrl()}${product.imgUrl}" id="imgPreview" class="img-polaroid"/>
            </td>
            <td>
                    ${product.name}
            </td>
            <td>
                    ${product.sysParentCode}
            </td>
            <td>
                <fmt:formatDate value="${product.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
                    ${product.createName}
            </td>
            <td>
                <a href="${ctx}/product/develop/developDetails?productId=${product.id}&flag=view">查看</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>