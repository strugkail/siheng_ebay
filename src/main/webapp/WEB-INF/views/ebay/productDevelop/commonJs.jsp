<%--
  Created by IntelliJ IDEA.
  User: strugkail.li
  Date: 2017/9/6
  Time: 20:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%--     <script src="${ctxStatic}/layer-v3.1.0/layer/layer.js" type="text/javascript"></script> --%>
    <script type="text/javascript">
        var	purchaseSourceList;
        var participle;
        var fenge = "\n";
        var purchaseConfigInfoList;
        var combData = new Array();
        var propertyList;
        $(document).ready(function() {
            load();

        });
        function load() {
            updateCodemanager();
            //将复选框中选中的产品标签添加到材质文本框中
            $("input[name='selectedTags']").click(function () {
                setTexture();
            });

            //将规格标签设置到规格框中
            $("input[name='selectedSpeTags']").click(function(){
                setSpecification();
            });
        }
        //将产品标签设置到材质框中
        function setTexture(){
            var texture = "";
            $('input[name="selectedTags"]:checked').each(function(){
                texture += "、" + $(this).next("label").text()
            });
            $("#texture").val(texture.substring(1));
        }

        //将规格标签设置到规格框中
        function setSpecification(){
            var specification = $('input[name="selectedSpeTags"]:checked').next("label").text();
            $("#specification").val(specification);
        }


        // 分词管理
        function word_Manager(data) {
            var colorNameArray = myTrim(data).split(fenge);
            if (hasNullContent(colorNameArray)){
                alert("中间有空行！");
            }else{
                $("#"+participle).val(data);
                $('#seperateWordModal').modal('hide');
            }
        }
        // 删除属性
        function del_property(propertyCode) {
            var propertyCode = propertyCode;
            var productId = $("#productId").val();
            $.ajax({
                url : '${ctx}/product/develop/deleteProperty',
                type : 'post',
                data : {
                    productId : productId,
                    propertyCode : propertyCode,
                },
                success : function(data) {
                    processPropertySource(data);
                    propertyList = data;
                    addCodemanager();
                },
                error : function(XMLHttpRequest, textStatus, errorThrown){
                    errorCapture(XMLHttpRequest, textStatus, errorThrown);
                }
            })
        }

        // 更新codemanager列表
        function updateCodemanager(){
            var productId = $("#productId").val();
            var quantity = $("#productNumber").val();
            $.ajax({
                url : '${ctx}/product/develop/updateCodeManager',
                type : 'post',
                data : {
                    productId : productId
                },
                success : function(data) {
                    processCodeManagerSource(data);
                    purchaseConfigInfoList = data;
                    findSupplierList();
//                    for(var i=0;i<data.length;i++){
//                    getSourceAndQuantity(data[i].sourceId);
//                    }

                },
                error : function(XMLHttpRequest, textStatus, errorThrown){
                    errorCapture(XMLHttpRequest, textStatus, errorThrown);
                }
            })
        }
        // 更新属性操作
        function update_property() {
            var productId = $("#productId").val();
            var property = $("#property0").val();
            var propertyValue = $("#propertyWords0").val();
            $.ajax({
                url : '${ctx}/product/develop/updateProperty',
                type : 'post',
                data : {
                    productId : productId,
                    propertyName : property,
                    propertyValues : propertyValue
                },
                success : function(data) {
                    processPropertySource(data);
                    propertyList = data;
                    $("#property0").val("");
                    $("#p_name").val("");
                    $("#propertyWords0").val("");
                    updateCodemanager();
                },
                error : function(XMLHttpRequest, textStatus, errorThrown){
                    errorCapture(XMLHttpRequest, textStatus, errorThrown);
                }
            })
        }
        // 添加属性操作
        function add_property() {
            var productId = $("#productId").val();
            var property = $("#property0").val();
            var propertyValue = $("#propertyWords0").val();
            if((property==null || property=="") ||
                (propertyValue==null || propertyValue=="")){
                alert("属性或属性值不能为空！")
            }else{
                $.ajax({
                    url : '${ctx}/product/develop/addProperty',
                    type : 'post',
                    data : {
                        productId : productId,
                        propertyName : property,
                        propertyValues : propertyValue
                    },
                    success : function(data) {
                        processPropertySource(data);
                        propertyList = data;
                        $("#property0").val("");
                        $("#p_name").val("");
                        $("#propertyWords0").val("");
                        addCodemanager();
                    },
                    error : function(XMLHttpRequest, textStatus, errorThrown){
                        errorCapture(XMLHttpRequest, textStatus, errorThrown);
                    }
                })
            }
        }
        function addCodemanager() {
            var productId = $("#productId").val();
            var quantity = $("#productNumber").val();
            $.ajax({
                url : '${ctx}/product/develop/addCodeManager',
                type : 'post',
                data : {
                    productId : productId,
                    quantity:quantity
                },
                success : function(data) {
                    processCodeManagerSource(data);
                    purchaseConfigInfoList = data;
                    findSupplierList();
                },
                error : function(XMLHttpRequest, textStatus, errorThrown){
                    errorCapture(XMLHttpRequest, textStatus, errorThrown);
                }
            })
        }
        // 清空单词
        function clearPropertyWords(myId) {
            if($("#"+myId).val()==null ||$("#"+myId).val()==""){
                return;
            }
            $("#"+myId).val("");
        }
        // 单词拆分
        function splitChi(data) {
            var newChi = "";// 新生成的内容
            var chaichiTet = myTrim($("#chaichi").val());// 取待分词内容
            var splitChiInput = $("#splitChiInput").val();// 取分隔符

            if (chaichiTet != "") {
                var strs = new Array(); //定义一数组
                if (splitChiInput != "") {
                    strs = chaichiTet.split(splitChiInput);// 字符分割
                    for (i = 0; i < strs.length; i++) {
                        if (i == strs.length - 1) {
                            newChi = newChi + strs[i];
                            break;
                        }
                        newChi = newChi + strs[i] + fenge;// 分割后的字符输出 ,\r\n回车换行
                    }
                } else {
                    newChi = chaichiTet;
                }
            }
            word_Manager(newChi);
        }
        // 去除字符串前后空格
        function myTrim(x) {return x.replace(/^\s+|\s+$/g,'');}
        // 判断字符串数组中是否有空
        function hasNullContent(arr){
            var n = 0
            for (var i = 0 ; i < arr.length ; i++ ){if (myTrim(arr[i]) == ""){n++;}
            }
            if (n > 0){return true;} else {return false;}
        }
        // 拆分单词按钮项
        function sepWord(data) {
            participle = data;
            $("#seperateWordModal").modal('show');
            $("#chaichi").val("");
            $("#splitChiInput").val("");
        };
        // 删除子代码信息(删除单条，其他不重置)
        function dele_purchaseConfigInfo(index){
            $("#purchaseConfigInfo_"+index).remove();
            for(var i=0,j=0; i<combData.length; i++,j++){
                if($("#purchaseConfigInfo_"+i).find("th:first").text() == ""){
                    j--;
                    continue;
                }
                $("#purchaseConfigInfo_"+i).find("th:first").text(j+1);
            }
        }
        // 删除或者新增采购源后需要重新配置采购源和采购数目
        function resetSourceAndQuantity(data){
            $("#codeManagerTb tr:not(:first)").each(function(){
                var tdArr = $(this).children();
                var selObj = tdArr.eq(2).find('select');// purchase select
                var purchaseCount = tdArr.eq(3).find('input');// purchase count
                var sourceId = tdArr.eq(3).find('input')[0].defaultValue;
                selObj.empty();
                if(sourceId != null && data != null && data.length > 0){
                    selObj.removeAttr("disabled");
                    purchaseCount.removeAttr("", "");
                    //purchaseCount.val(0);
                    var index=1;
                    for(var i=0; i<data.length; i++){
                        if(typeof (sourceId)!="undefined"){
                            if(data[i].sourceId == sourceId){
                                selObj.append("<option value='"+data[i].sourceId+"' selected='selected'>"+index+"-"+data[i].supplierName+"</option>");
                                index+=1;
                            }else{
                                selObj.append("<option value='"+data[i].sourceId+"'>"+index+"-"+data[i].supplierName+"</option>");
                                index+=1;
                            }
                        }else{
                            if(i==0){
                                selObj.append("<option value='"+data[i].sourceId+"' selected='selected'>"+index+"-"+data[i].supplierName+"</option>");
                                index+=1;
                            }else{
                                selObj.append("<option value='"+data[i].sourceId+"'>"+index+"-"+data[i].supplierName+"</option>");
                                index+=1;
                            }
                        }

                    }
                }else{
//                    purchaseCount.val("");
                    purchaseCount.attr("", "");
                    selObj.html("");
                    selObj.attr("disabled", "disabled");
                }
            });
            // alert("请重新配置采购源与采购数量！");
        }
        // 为子代码计算利润信息添加绑定事件
        function calProfitBindingEvent(count){
            for(var i=0; i<count; i++){
                (function(i) {
                    $('#publishPrice_'+i).bind('input propertychange', function() {
                        calculateProfitRate(i,-1);
                    });
                    $('#costPrice_'+i).bind('input propertychange', function() {
                        calculateProfitRate(i,-1);
                    });
                    $('#weight_'+i).bind('input propertychange', function() {
                        if($('#weight_'+i).val()!="" && $('#weight_'+i).val()!=null){
                            calculateProfitRate(i,$('#weight_'+i).val());
                        }
                    });
                })(i);
            }
        }
        // 重量变化时，计算出特定运费
        function weightVal(weight,i) {
            var siteName = $("#siteName").val();
            var exch_rate = $("#exch_rate").val();
            var weight    = weight;
            $.ajax({
                url : '${ctx}/product/develop/getFreight',
                type : 'post',
                data : {
                    siteName: siteName,
                    defaultWeight: weight,
                    exchRate : exch_rate
                },
                dataType:"json",
                success : function(data) {
                    if(data.result){
                        $("#publishTransPrice_"+i).val(data.data);
                    }
                },
                error : function(XMLHttpRequest, textStatus, errorThrown){
                    errorCapture(XMLHttpRequest, textStatus, errorThrown);
                }
            })

        }
        // 测算利润率
        function calculateProfitRate(i,flag){
            if(flag!=-1){
                weightVal(flag,i);
                var postage = $("#publishTransPrice_"+i).val();
            }
            var ebaySellPrice = $("#publishPrice_"+i).val();
            var commodityCost = $("#costPrice_"+i).val();

            var postage = $("#publishTransPrice_"+i).val();
            var ebayfee = $("#ebayfee").val();

            var paypalfee = $("#paypalfee").val();
            var financefee = $("#financefee").val();

            var lossfee = $("#lossfee").val();
            var exch_rate = $("#exch_rate").val();

            var packagefee = $("#packagefee").val();
            var dealfee = $("#dealfee").val();


            if(ebaySellPrice != '' && ebaySellPrice != null
                && commodityCost != '' && commodityCost != null
                && postage != '' && postage != null
                && ebayfee != '' && ebayfee != null
                && paypalfee != '' && paypalfee != null
                && financefee != '' && financefee != null
                && lossfee != '' && lossfee != null
                && exch_rate != '' && exch_rate != null
                && packagefee != '' && packagefee != null
                && dealfee != '' && dealfee != null){
                $.ajax({
                    type : "post",
                    url : '${ctx}/product/develop/calculateUtil',
                    data : {
                        ebaySellPrice : ebaySellPrice,
                        commodityCost : commodityCost,
                        postage : postage,
                        paypalfee : paypalfee,
                        financefee : financefee,
                        lossfee : lossfee,
                        packagefee : packagefee,
                        exch_rate : exch_rate,
                        ebayfee : ebayfee,
                        dealfee : dealfee
                    },
                    dataType:"json",
                    success : function(data) {
                        if(data.data.grossProfitRate != null && data.data.grossProfitRate != "" ){
                            var profitRate = (data.data.grossProfitRate)*100;
                            $("#profitRate_"+i).val(profitRate);
                        }else{
                            $("#profitRate_"+i).val("0");
                        }
                    },
                    error : function(XMLHttpRequest, textStatus, errorThrown){
                        errorCapture(XMLHttpRequest, textStatus, errorThrown);
                    }
                });
            }else {
                $("#profitRate_"+i).val("0");
            }
        }

        // 添加采购源信息
        function add_purchaseSource(){
            var productId = $("#productId").val();
            var p_link = $("#p_link").val();
            var p_name = $("#p_name").val();
            var p_remark = $("#p_remark").val();
            if(p_link == null || p_link == ''){
                return;
            }
            if(p_name == null || p_name == ''){
                return;
            }
            $.ajax({
                url : '${ctx}/supplier/supplier/addPurchaseSource',
                type : 'post',
                data : {
                    productId : productId,
                    pLink : p_link,
                    pName : p_name,
                    pRemark : p_remark
                },
                success : function(data) {
                    processSource(data);
                    resetSourceAndQuantity(data);
                    purchaseSourceList = data;
                    $("#p_link").val("");
                    $("#p_name").val("");
                    $("#p_remark").val("");
                },
                error : function(XMLHttpRequest, textStatus, errorThrown){
                    errorCapture(XMLHttpRequest, textStatus, errorThrown);
                }
            })
        }
        // 查询采购源信息
        function findSupplierList(){
            var productId = $("#productId").val();
            $.ajax({
                url : '${ctx}/product/develop/findSupplierList',
                type : 'post',
                data : {
                    productId : productId
                },
                success : function(data) {
                    resetSourceAndQuantity(data);
                    purchaseSourceList = data;
                },
                error : function(XMLHttpRequest, textStatus, errorThrown){
                    errorCapture(XMLHttpRequest, textStatus, errorThrown);
                }
            })
        }
        // 生成数据源列表
        function processSource(data){
            $("#supplier tbody tr:not(:first)").empty("");
            var content;
            for(var i=0; i<data.length; i++){
                var row =
                    '<tr>'
                    + '<td>'+(i+1)+'</td>'
                    + '<td>'+data[i].supplierName+'</td>'
                    + '<td><a href="'+data[i].sourceUrl+'" target="_blank">'+data[i].sourceUrl+'</a></td>'
                    + '<td>'+data[i].remark+'</td>'
                    + '<td><button class="btn btn-danger" type="button" onclick="del_purchaseSource('+data[i].sourceId+')">删除</button></td>'
                    + '</tr>'
                content = content + row;
            }

            $("#addSource").after(content);
        }
        // 生成属性列表
        function processPropertySource(data){
            $("#propertyTable tbody tr:not(:first)").empty("");
            var content;
            for(var i=0; i<data.length; i++){
                var row ='<tr>\n' +
                    '    <td align="center">\n' +
                    '        <div class="col-md-5">\n' +
                    '            <input class="form-control" name="property0" type="text" value="'+data[i].propertyName+'" readonly   placeholder="">\n' +
                    '        </div>\n' +
                    '    </td>\n' +
                    '    <td align="center">\n' +
                    '        <textarea name="colorName" rows="5" wrap="off" style="overflow:auto;" readonly>'+data[i].propertyValue+'</textarea>\n' +
                    '        <button class="btn btn-default" name="seperator" type="button" disabled onclick="sepWord(\'propertyWords0\')">拆词工具</button>\n' +
                    '        <button class="btn btn-danger" name="clearColor" type="button" disabled onclick="clearPropertyWords(\'propertyWords0\')">清空</button>\n' +
                    '    </td>\n' +
                    '    <td align="center">\n' +
                    '        <div class="row" style="margin-left: 10px;">\n' +
                    '            <button class="btn btn-danger" type="button" onclick="del_property('+data[i].propertyCode+')">删除</button>\n' +
                    '        </div>\n' +
                    '    </td>\n' +
                    '</tr>';
                content = content + row;
            }
            $("#propertyTr0").after(content);
        }
        // 删除采购源信息
        function del_purchaseSource(sourceId){
            var productId = $("#productId").val();
            $.ajax({
                url : '${ctx}/supplier/supplier/deletePurchaseSource',
                type : 'post',
                data : {
                    productId : productId,
                    sourceId : sourceId
                },
                success : function(data) {
                    processSource(data);
                    resetSourceAndQuantity(data);
                    purchaseSourceList = data;
                },
                error : function(XMLHttpRequest, textStatus, errorThrown){
                    errorCapture(XMLHttpRequest, textStatus, errorThrown);
                }
            })
        }
        // 保存与开发
        function save() {
            $("#type").val("0");
            $("#inputForm").submit();
            var index = layer.load(2, {time: 15*10000});
        }
        function develop() {
            formValidate();
            $("#type").val("1");
            $("#inputForm").submit();
            var r = $("#inputForm").valid()
            if(r){
                if (!confirm("是否提交至下一流程？")) {
                    return;
                }
                var index = layer.load(3, {time: 15*10000});
            }
        }
    </script>
</head>

</html>