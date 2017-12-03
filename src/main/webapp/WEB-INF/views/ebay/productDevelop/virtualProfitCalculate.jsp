<%--
  Created by IntelliJ IDEA.
  User: strugkail.li
  Date: 2017/9/4
  Time: 10:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>虚拟仓利润测算</title>
    <meta name="decorator" content="bootstrap3" />
    <style type="text/css" src="">


    </style>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.css"/>
    <script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.js" type="text/javascript"></script>
    <script type="text/javascript">
    function calculate() {
        var ebaySellPrice = $("#ebaySellPrice").val();
        var commodityCost = $("#commodityCost").val();
        var postage = $("#postage").val();
        var ebayfee = $("#ebayfee").val();
        var paypalfee = $("#paypalfee").val();
        var financefee = $("#financefee").val();
        var lossfee = $("#lossfee").val();
        var packagefee = $("#packagefee").val();
        var dealfee = $("#dealfee").val();
        var exch_rate = $("#exch_rate").val();
        if(ebaySellPrice!="" && commodityCost!="" && postage!=""){
            $.ajax({
                url : '${ctx}/product/develop/calculateUtil',
                type : 'post',
                data : {
                    ebaySellPrice: ebaySellPrice,
                    commodityCost: commodityCost,
                    postage: postage,
                    paypalfee: paypalfee,
                    ebayfee: ebayfee,
                    financefee: financefee,
                    lossfee: lossfee,
                    packagefee: packagefee,
                    dealfee: dealfee,
                    exch_rate: exch_rate
                },
                dataType:"json",
                success : function(data) {
                    if(data.result){
                        $("#grossProfit").val(data.data.grossProfit);
                        $("#grossProfitRate").val(data.data.grossProfitRate*100);
                    }
                    isOrFalse();
                },
                error : function(XMLHttpRequest, textStatus, errorThrown){
                    errorCapture(XMLHttpRequest, textStatus, errorThrown);
                }
            })
        }

    }
    function isOrFalse() {
        if(($("#grossProfit").val()!="" && $("#grossProfit").val()!=null)){

            $("#develop").attr("disabled",false);
            if($("#note").val()!="" && $("#note").val()!=null){
                $("#quitDevelop").attr("disabled",false);
                $("#temporaryQuit").attr("disabled",false);
            }else{
                $("#quitDevelop").attr("disabled","disabled");
                $("#temporaryQuit").attr("disabled","disabled");
            }
        }else{
            $("#develop").attr("disabled","disabled");
            if($("#note").val()=="" || $("#note").val()==null){
                $("#quitDevelop").attr("disabled","disabled");
                $("#temporaryQuit").attr("disabled","disabled");
            }
        }
    }
    // 为测算绑定oninput事件
    $(function () {
        $('#commodityCost').bind('input propertychange', resetProfit);
        $('#ebaySellPrice').bind('input propertychange', changeMany);
//        $('#defaultWeight').bind('input propertychange', calculate);
        $('#defaultWeight').bind('input propertychange', weightChange);
        load();
    });
    function resetProfit() {
        $("#grossProfit").val("");
        $("#grossProfitRate").val("");
    }
    // 售价改变时，相关数据变化
    function changeMany() {
        resetProfit();
        var ebaySellPrice = $("#ebaySellPrice").val();
        var siteName = $("#siteName").val();
        var categoryName = $("#categoryName").val();
        if(ebaySellPrice!="" &&  !isNaN(ebaySellPrice)){
            $.ajax({
                url : '${ctx}/product/develop/changeMany',
                type : 'post',
                data : {
                    ebaySellPrice: ebaySellPrice,
                    siteName : siteName,
                    categoryName : categoryName
                },
                dataType:"json",
                success : function(data) {
                    if(data.result){
                        $("#ebayfeeName").html(data.data.ebayfee);
                        $("#ebayfee").val(data.data.ebayfee);

                        $("#paypalfeeName").html(data.data.paypalfee);
                        $("#paypalfee").val(data.data.paypalfee);

                        $("#financefeeName").html(data.data.financefee);
                        $("#financefee").val(data.data.financefee);

                        $("#lossfeeName").html(data.data.lossfee);
                        $("#lossfee").val(data.data.lossfee);
                    }else{
                        alertx(data.msg);
                    }
                },
                error : function(XMLHttpRequest, textStatus, errorThrown){
                    errorCapture(XMLHttpRequest, textStatus, errorThrown);
                }
            })
        }
    }
    // 重量变化时，计算出特定运费
    function weightChange() {
        resetProfit();
        var siteName = $("#siteName").val();
        var defaultWeight = $("#defaultWeight").val();
        if(defaultWeight!="" && !isNaN(defaultWeight)){
            $.ajax({
                url : '${ctx}/product/develop/getFreight',
                type : 'post',
                data : {
                    siteName: siteName,
                    defaultWeight: defaultWeight
                },
                dataType:"json",
                success : function(data) {
                    if(data.result){
                        $("#postage").val(data.data);
                    }else{
                        alertx(data.msg);
                    }
                },
                error : function(XMLHttpRequest, textStatus, errorThrown){
                    errorCapture(XMLHttpRequest, textStatus, errorThrown);
                }
            })
        }


    }
    // 为按钮绑定指定事件
    function load(){
        $("#quitDevelop").click(function(){
            $("#profitCalForm").attr("action", "${ctx}/product/develop/quitDevelop");
            $("#profitCalForm").submit();
        });
        $("#temporaryQuit").click(function(){
            $("#profitCalForm").attr("action", "${ctx}/product/develop/temporaryQuit");
            $("#profitCalForm").submit();
        });
        $("#develop").click(function () {
            $("#profitCalForm").attr("action", "${ctx}/product/develop/develop");
            $("#profitCalForm").submit();
        });
        $("#profitCalForm").validate({
//            debug:true, //调试模式，即使验证成功也不会跳转到目标页面
            rules:{
                commodityCost:{
                    required:true,
                    number:true
                },
                defaultWeight: {
                    required: true,
                    number: true
                },
                ebaySellPrice:{
                    required: true,
                    number: true
                },
                grossProfit:{
                    required: true
                },
                grossProfitRate:{
                    required: true
                }

            },
            messages:{
                commodityCost:{
                    required: "不能为空",
                    number:"只能输入数字"
                },
                defaultWeight:{
                    required: "不能为空",
                    number:"只能输入数字"
                },
                ebaySellPrice:{
                    required: "不能为空",
                    number:"只能输入数字"
                },
                grossProfit:{
                    required: "不能为空"
                },
                grossProfitRate:{
                    required: "不能为空"
                }
            }
        });
    }


    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="#">虚拟仓利润测算</a></li>
</ul>
<div class="container">

    <form:form class="form-horizontal"
               action="" method="post"
               modelAttribute="calculateDto" id="profitCalForm">
        <input name="collectId" type="hidden" id="collectId" value="${collectId}"/>
        <input name="act.procInsId" type="hidden" value="${bean.act.procInsId}"/>
        <input name="procInsId" type="hidden" value="${bean.act.procInsId}"/>
        <input name="act.taskId" type="hidden" value="${bean.act.taskId}"/>
        <input name="title" type="hidden" value="${calculateDto.title}"/>
        <input name="description" type="hidden" value="${calculateDto.description}"/>
        <div class="form-group">
            <label for="siteName" class="col-md-2 col-xs-2 control-label">站点：</label>
            <div class="col-md-5 col-xs-5">
                <input type="hidden" id="siteName" name="siteName" value="${calculateDto.siteName}"/>
                <label >${calculateDto.siteName}</label>
            </div>
        </div>
        <div class="form-group">
            <label for="saleType" class="col-md-2 col-xs-2 control-label">销售类型：</label>
            <div class="col-md-5 col-xs-5">
                <input type="hidden" name="saleTypeId" value="1"/>
                <label id="saleType">虚拟海外仓</label>
            </div>
        </div>
        <div class="form-group">
            <label for="ebaySellPrice" class="col-md-2 col-xs-2 control-label">ebay售价（$）：</label>
            <div class="col-md-5 col-xs-5">
                <input class="form-control" id="ebaySellPrice" name="ebaySellPrice" value="${calculateDto.ebaySellPrice}" type="text" >
            </div>
        </div>
        <div class="form-group">
            <label for="categoryName" class="col-md-2 col-xs-2 control-label">ebay分类1：</label>
            <div class="col-md-5 col-xs-5">
                <input class="form-control" id="categoryName" name="categoryName" readonly value="${calculateDto.categoryName}" type="text" >
            </div>
        </div>
        <div class="form-group">
            <label for="subCategoryName" class="col-md-2 col-xs-2 control-label">ebay分类2：</label>
            <div class="col-md-5 col-xs-5">
                <input class="form-control" id="subCategoryName" name="subCategoryName" readonly value="${calculateDto.subCategoryName}" type="text" >
            </div>
        </div>
        <div class="form-group">
            <label for="commodityCost" class="col-md-2 col-xs-2 control-label">商品成本（¥）：</label>
            <div class="col-md-5 col-xs-5">
                <input class="form-control" id="commodityCost" name="commodityCost" value="" type="text" >
            </div>
        </div>
        <div class="form-group">
            <label for="ebayfee" class="col-md-2 col-xs-2 control-label">ebayfee（$）：</label>
            <div class="col-md-5 col-xs-5">
                <label id="ebayfeeName">${calculateDto.ebayfee}</label>
                <input type="hidden" class="form-control" id="ebayfee"  name="ebayfee" readonly value="${calculateDto.ebayfee}">
            </div>
        </div>
        <div class="form-group">
            <label for="paypalfee" class="col-md-2 col-xs-2 control-label">paypalfee（$）：</label>
            <div class="col-md-5 col-xs-5">
                <label id="paypalfeeName">${calculateDto.paypalfee}</label>
                <input type="hidden" class="form-control" id="paypalfee"  readonly name="paypalfee" value="${calculateDto.paypalfee}">
            </div>
        </div>
        <%--<div class="form-group">--%>
            <%--<label for="postage" class="col-md-2 col-xs-2 control-label">竞品邮费：</label>--%>
            <%--<div class="col-md-5 col-xs-5">--%>
                <%--<input class="form-control" id="competePostage" readonly name="competePostage" value="${calculateDto.postage}" type="text" >--%>
            <%--</div>--%>
        <%--</div>--%>
        <div class="form-group">
            <label for="postage" class="col-md-2 col-xs-2 control-label">运费（¥）：</label>
            <div class="col-md-5 col-xs-5">
                <input class="form-control" id="postage" readonly name="postage" value="" type="text" >
            </div>
        </div>
        <div class="form-group">
            <label for="packagefee" class="col-md-2 col-xs-2 control-label">包装费（¥）：</label>
            <div class="col-md-5 col-xs-5">
                <label id="packagefeeName">${calculateDto.packagefee}</label>
                <input type="hidden" class="form-control" readonly id="packagefee" name="packagefee" value="${calculateDto.packagefee}"/>
            </div>
        </div>
        <div class="form-group">
            <label for="dealfee" class="col-md-2 col-xs-2 control-label">处理费（¥）：</label>
            <div class="col-md-5 col-xs-5">
                <label id="dealfeeName">${calculateDto.dealfee}</label>
                <input type="hidden" class="form-control" id="dealfee" readonly name="dealfee" value="${calculateDto.dealfee}">
            </div>
        </div>
        <div class="form-group">
            <label for="financefee" class="col-md-2 col-xs-2 control-label">财务费（$）：</label>
            <div class="col-md-5 col-xs-5">
                <label id="financefeeName">${calculateDto.financefee}</label>
                <input type="hidden" class="form-control" id="financefee" readonly name="financefee" value="${calculateDto.financefee}">
            </div>
        </div>
        <div class="form-group">
            <label for="lossfee" class="col-md-2 col-xs-2 control-label">损失费（$）：</label>
            <div class="col-md-5 col-xs-5">
                <label id="lossfeeName">${calculateDto.lossfee}</label>
                <input type="hidden" class="form-control" id="lossfee" readonly name="lossfee" value="${calculateDto.lossfee}">
            </div>
        </div>
        <div class="form-group">
            <label for="defaultWeight" class="col-md-2 col-xs-2 control-label">重（g）：</label>
            <div class="col-md-5 col-xs-5">
                <input class="form-control" id="defaultWeight" name="defaultWeight" value="" type="text" >
            </div>
        </div>
        <div class="form-group">
            <label for="exch_rate" class="col-md-2 col-xs-2 control-label">汇率（$==>¥）：</label>
            <div class="col-md-5 col-xs-5">
                <label id="exch_rateName">${calculateDto.exch_rate}</label>
                <input class="form-control" type="hidden" id="exch_rate" readonly name="exch_rate" value="${calculateDto.exch_rate}">
            </div>
        </div>
        <div class="form-group">
            <label for="grossProfit" class="col-md-2 col-xs-2 control-label" >毛利C（$）:</label>
            <div class="col-md-5 col-xs-5">
                <input class="form-control" id="grossProfit" name="grossProfit" readonly value="" type="text"/>
            </div>
        </div>
        <div class="form-group">
            <label for="grossProfitRate" class="col-md-2 col-xs-2 control-label">毛利C率（%）：</label>
            <div class="col-md-5 col-xs-5" >
                <input class="form-control" id="grossProfitRate" name="grossProfitRate" readonly value="" type="text"  />
            </div>
            <%--<label style="height: 34px;line-height: 34px;font-size: 14px;">%</label>--%>
        </div>
        <div class="form-group">
            <label for="note" class="col-md-2 col-xs-2 control-label">备注：</label>
            <div class="col-md-5 col-xs-5">
                <textarea id="note" onchange="isOrFalse();" class="form-control" name="note" style="width: 600px; height: 100px; outline: none; resize: none"></textarea>
            </div>
        </div>
        <div class="form-group">
            <div class="col-md-offset-2 col-xs-offset-2 col-md-2 col-xs-2" style="width: auto;overflow: hidden">
                <button id="calcul" type="button" onclick="calculate();" class="btn btn-primary"style="margin-right: 60px">计算</button>
                <button id="develop" class="btn btn-primary"style="margin-right: 60px" disabled="disabled">开发</button>
                <button id="quitDevelop" class="btn btn-primary"style="margin-right: 60px" disabled="disabled">放弃开发</button>
                <button id="temporaryQuit" class="btn btn-primary"style="margin-right: 180px" disabled="disabled">暂时放弃开发</button>
            </div>
        </div>
    </form:form>
</div>

</body>
</html>