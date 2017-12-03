<%--
  Created by IntelliJ IDEA.
  User: strugkail.li
  Date: 2017/9/4
  Time: 10:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<script src="${ctxStatic}/layer-v3.1.0/layer/layer.js" type="text/javascript"></script>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>海外仓利润测算</title>
    <meta name="decorator" content="bootstrap3" />
    <style type="text/css">
        .btn btn-primary{
            margin-left: 150px;
        }

    </style>

    <script type="text/javascript">
        $(document).ready(function() {
            var collectId = $("#collectId").val();
            $.ajax({
                type : "post",
                url : '${ctx}/product/develop/getCollectionStatus',
                data : {
                    collectId : collectId
                },
                success : function(data) {
                    if(data){
                        alert("产品开发中...");
                        $("#btnSubmit").attr("disabled", "disabled");
                    }else{
                        $("#btnSubmit").val("开发");
                    }
                }
            });

        });
            <%--function calProfit() {--%>
                <%--var publishPrice = $("#sellingPrice").val();--%>
                <%--var publishTransPrice = $("#freight").val();--%>
                <%--var costPrice = $("#costPrice").val();--%>
                <%--var weight = $("#defaultWeight").val();--%>
                <%--var calType = $("input[name='calType']:checked").val();--%>
                <%--if(publishPrice != '' && publishPrice != null--%>
                    <%--&& publishTransPrice != '' && publishTransPrice != null--%>
                    <%--&& costPrice != '' && costPrice != null--%>
                    <%--&& weight != '' && weight != null--%>
                    <%--&& calType != '' && calType != null){--%>

                    <%--$.ajax({--%>
                        <%--type : "post",--%>
                        <%--url : '${ctx}/product/productDev/calProfit',--%>
                        <%--data : {--%>
                            <%--publishPrice : publishPrice,--%>
                            <%--publishTransPrice : publishTransPrice,--%>
                            <%--costPrice : costPrice,--%>
                            <%--weight : weight,--%>
                            <%--calType : calType--%>
                        <%--},--%>
                        <%--success : function(data) {--%>
                            <%--if(data.profitRate != null && data.profitRate != "" ){--%>
                                <%--$("#profitMargin").val(data.profitRate);--%>
                                <%--$("#btnSubmit").attr("disabled", false);--%>
                            <%--}else{--%>
                                <%--$("#profitMargin").val("");--%>
                                <%--$("#btnSubmit").attr("disabled", "disabled");--%>
                            <%--}--%>

                            <%--$("#declaredValueId").val(data.declaredValue);--%>
                        <%--}--%>
                    <%--});--%>
                <%--}else{--%>
                    <%--$("#profitMargin").val("");--%>
                    <%--$("#btnSubmit").attr("disabled", "disabled");--%>
                <%--}--%>
            <%--}--%>

//            $('#profitCalForm').bind('input propertychange', calProfit);
//            $("input[name='calType']").change(calProfit);



        //前端验证产品中文名称不能为空且不能重复
        $(document).ready(function() {
            $("#profitCalForm").validate({
                rules : {
                    name : {
                        required:true,
                        remote: {
                            url: "${ctx}/product/productDev/isExistName",
                            type: "post",
                            data: {
                                name: function () {
                                    return $("#name").val();
                                }
                            },
                            dataFilter: function (data) {
                                if (data == "true") {
                                    return true;
                                }
                                else {
                                    return false;
                                }
                            }
                        }
                    }

                },
                messages:{
                    name :{
                        required:"产品中文名称不能为空！",
                        remote:"中文名已存在！"
                    }
                }
            });

        });
        function realDevelopForm() {
            var remark          = $("#remark").val();
            var postage         = $("#postage").val();
            var startPrice      = $("#startPrice").val();
            var subCategoryName = $("#subCategoryNa").val();
            var categoryName    = $("#categoryName").val();
            var title           = $("#title").val();
            var productUrl      = $("#productUrl").val();
            var itemId          = $("#itemId").val();
            var siteName        = $("#siteName").val();
            var processNum      = $("#processNum").val();
            var sellerGroupName = $("#sellerGroupName").val();
            var picture         = $("#picture").val();
            var name            = $("#name").val();
            var collectId       = $("#collectId").val();
            var productNumber   = $("#productNumber").val();
                $.ajax({
                    type : "post",
                    url : '${ctx}/product/develop/overseasCal',
                    data : {
                        remark : remark,
                        postage : postage,
                        startPrice : startPrice,
                        subCategoryName : subCategoryName,
                        categoryName : categoryName,
                        title : title,
                        processNum : processNum,
                        productUrl : productUrl,
                        siteName : siteName,
                        itemId : itemId,
                        sellerGroupName : sellerGroupName,
                        picture : picture,
                        name : name,
                        collectId : collectId,
                        collectionQuantity : productNumber
                    },
                    success : function(data) {
                        alertx("message:"+data.msg);
                        if(data.code=="200"){
                            window.location.href='${ctx}/product/develop/toBeDevelop';
                        }
                    }
                });
}
        function developReal() {
            var startPrice = $("#startPrice").val();
            if(startPrice!=""){
                $("#btnSubmit").attr("disabled",false);
            }else{
                $("#btnSubmit").attr("disabled","disabled");
            }
        }
        function developRealBtn() {
            var index = layer.load(3, {time: 5*1000});
            realDevelopForm();
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="#">利润测算</a></li>
</ul>
<div class="container">

    <form:form class="form-horizontal"
               action="" method="post"
               modelAttribute="messureMmsParam" id="profitCalForm">
        <input name="processNum" id="processNum" type="hidden" value="${bean.act.taskId}"/>
        <input name="siteName" id="siteName" type="hidden" value="${siteName}"/>
        <input name="itemId" id="itemId" type="hidden" value="${itemId}"/>
        <input name="picture" id="picture" type="hidden" value="${toBeDevelop.imgLink}"/>
        <input name="productNumber" id="productNumber" type="hidden" value="${toBeDevelop.productNumber}"/>
        <input name="productUrl" id="productUrl" type="hidden" value="${toBeDevelop.productUrl}"/>
        <input name="name" id="name" type="hidden" value="${toBeDevelop.productName}"/>
        <input name="collectId" id="collectId" type="hidden" value="${toBeDevelop.collectId}"/>
        <input name="sellerGroupName" id="sellerGroupName" type="hidden" value="${sellerGroupName}"/>
        <div class="form-group">
            <label  class="col-md-2 col-xs-2 control-label">站点：</label>
            <div class="col-md-5 col-xs-5">
                <label>${siteName}</label>
            </div>
        </div>
        <div class="form-group">
            <label  class="col-md-2 col-xs-2 control-label">销售类型：</label>
            <div class="col-md-5 col-xs-5">
                <label>海外仓</label>
            </div>
        </div>
        <div class="form-group">
            <label  class="col-md-2 col-xs-2 control-label">产品标题：</label>
            <div class="col-md-5 col-xs-5">
                <input class="form-control" id="title" readonly value="${calculateDto.title}" name="title" type="text" >
            </div>
        </div>
        <div class="form-group">
            <label  class="col-md-2 col-xs-2 control-label">ebay分类：</label>
            <div class="col-md-5 col-xs-5">
                <input class="form-control" id="categoryName" readonly name="categoryName" value="${calculateDto.categoryName}" type="text" >
            </div>
        </div>
        <div class="form-group">
            <label  class="col-md-2 col-xs-2 control-label">ebay分类2：</label>
            <div class="col-md-5 col-xs-5">
                <input class="form-control" id="subCategoryName" readonly name="subCategoryName" value="${calculateDto.subCategoryName}" type="text" >
            </div>
        </div>
        <div class="form-group">
            <label  class="col-md-2 col-xs-2 control-label">售价：</label>
            <div class="col-md-5 col-xs-5">
                <input class="form-control" id="startPrice" readonly name="startPrice"value="${calculateDto.ebaySellPrice}"  type="text" >
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-2 col-xs-2 control-label">运费：</label>
            <div class="col-md-5 col-xs-5">
                <input class="form-control" value="${calculateDto.postage}" id="postage" readonly name="postage" type="text" >
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-2 col-xs-2 control-label">毛利C(£)：</label>
            <div class="col-md-5 col-xs-5">
                <input class="form-control" id="grossProfit" readonly name="grossProfit" value="" type="text" >
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-2 col-xs-2 control-label">毛利C率：</label>
            <div class="col-md-5 col-xs-5">
                <input class="form-control" id="grossProfitRate" readonly value="" type="text" >
            </div>
            <label style="height: 34px;line-height: 34px;font-size: 14px;">%</label>
        </div>
        <div class="form-group">
            <label  class="col-md-2 col-xs-2 control-label">备注：</label>
            <div class="col-md-5 col-xs-5">
                <textarea id="remark" class="form-control" name="remark" style="width: 600px; height: 100px; outline: none; resize: none">${product.remark}</textarea>
            </div>
        </div>
        <div class="form-group">
            <div class="col-md-offset-2 col-xs-offset-2 col-md-2 col-xs-2" style="width: auto;overflow: hidden">
                <a  onclick="window.location.href='${ctx}/product/develop/toBeDevelop'" class="btn btn-primary" style="margin-right: 60px">返回</a>
                <button id="btnSubmit" class="btn btn-primary" type="button" onclick="developRealBtn()" style="margin-right: 60px">开发</button>
            </div>
        </div>

    </form:form>
</div>

</body>
</html>