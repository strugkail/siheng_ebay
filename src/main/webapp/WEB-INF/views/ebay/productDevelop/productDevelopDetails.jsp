<%--
  Created by IntelliJ IDEA.
  User: strugkail.li
  Date: 2017/9/6
  Time: 20:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="commonJs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>产品详情</title>
    <meta name="decorator" content="bootstrap3"/>
    <script type="text/javascript">
        // 生成子代码
        function processCodeManagerSource(data){
            // 从第二个tr开始匹配，移除子代码信息
            $("#codeManagerTb tr:not(:first)").remove();
            var currency = $("#currency").val();
            var content;
            for(var i=0; i<data.length; i++){
                var row =
                    '<tr id="purchaseConfigInfo_'+i+'">'
                    + '<th>'+(parseInt(i)+1)+'<input type="hidden" readonly name="purchaseConfigInfoList['+i+'].codeManagerId" value="'+data[i].codeManagerId+'"/></th>'
                    + '<th>'+data[i].property+'<input type="hidden" readonly name="purchaseConfigInfoList['+i+'].property" value="'+data[i].property+'"/></th>'
                    +'<th>'+data[i].sysSku+'<input type="hidden" readonly name="purchaseConfigInfoList['+i+'].sysSku" value="'+data[i].sysSku+'"/></th>'
                    + '<th><select class="form-control select" readonly id="sourceId_'+i+'" name="purchaseConfigInfoList['+i+'].sourceId"</select></th>'
                    + '<th><input type="hidden" name="" value="'+data[i].sourceId+'"/>'
                    + '<div class="input-group"><input class="form-control" readonly type="number" min="0" id="quantity_'+i+'" name="purchaseConfigInfoList['+i+'].quantity" value="'+data[i].quantity+'"/>'
                    + '<span onclick="refresh('+i+',\'quantity_\')" class="input-group-addon">Λ</span></div></th>'
                    + '<th><div class="input-group"><span class="input-group-addon">'+currency+'</span><input class="form-control" readonly type="number" min="0" step="0.01" id="publishPrice_'+i+'" name="purchaseConfigInfoList['+i+'].publishPrice" value="'+data[i].publishPrice+'"/>'
                    + '<span onclick="refresh('+i+',\'publishPrice_\')" class="input-group-addon">Λ</span></div></th>'
                    + '<th><div class="input-group"><span class="input-group-addon">'+currency+'</span><input class="form-control" readonly type="number" min="0" step="0.01" id="publishTransPrice_'+i+'" name="purchaseConfigInfoList['+i+'].publishTransPrice" value="'+data[i].publishTransPrice+'"/>'
                    + '<span onclick="refresh('+i+',\'publishTransPrice_\')" class="input-group-addon">Λ</span></div></th>'
                    + '<th><div class="input-group"><input class="form-control" readonly type="number" min="0" step="0.01" id="costPrice_'+i+'" name="purchaseConfigInfoList['+i+'].costPrice" value="'+data[i].costPrice+'"/>'
                    + '<span onclick="refresh('+i+',\'costPrice_\')" class="input-group-addon">Λ</span></div></th>'
                    + '<th><div class="input-group"><input class="form-control" type="number" readonly min="0" step="0.1" id="weight_'+i+'" name="purchaseConfigInfoList['+i+'].weight" value="'+data[i].weight+'"/>'
                    + '<span class="input-group-addon">g</span>'
                    + '<span onclick="refresh('+i+',\'weight_\')" class="input-group-addon">Λ</span></div></th>'
                    + '<th><div class="input-group"><input class="form-control" readonly type="text" id="profitRate_'+i+'" name="purchaseConfigInfoList['+i+'].profitRate" value="'+data[i].profitRate+'" /><span class="input-group-addon">%</span></div></th>'
                    + '<th><button class="btn btn-danger" type="button" disabled onclick="">删除</button></th>'
                    + '</tr>';
                content = content + row;
            }

            $("#codeManagerTr").after(content);
            // 添加绑定事件
            calProfitBindingEvent(data.length);
        }

        function resetSourceAndQuantity(data){
            $("#codeManagerTb tr:not(:first)").each(function(){
                var tdArr = $(this).children();
//                var selObj = tdArr.eq(2).find('select');// purchase select
                var selObj = tdArr.eq(3).find('select');// purchase select
                var purchaseCount = tdArr.eq(3).find('input');// purchase count
//                var sourceId = tdArr.eq(3).find('input')[0].defaultValue;
                var sourceId = tdArr.eq(4).find('input')[0].defaultValue;
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
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li role="presentation" class="active"><a href="#">产品详情页</a></li>
</ul>
<div class="container-fluid">
    <form:form class="" id="inputForm" modelAttribute="product" action="${ctx}/product/develop/save">
        <input type="hidden" id="type" name = "type" value=""/>
        <input type="hidden" id="productId" name="id" value="${product.id}"/>
        <input type="hidden" id="collectId" name="collectId" value="${collectId}"/>

        <input type="hidden" id="ebayfee" name="ebayfee" value="${calculateDto.ebayfee}"/>
        <input type="hidden" id="paypalfee" name="paypalfee" value="${calculateDto.paypalfee}"/>
        <input type="hidden" id="financefee" name="financefee" value="${calculateDto.financefee}"/>
        <input type="hidden" id="lossfee" name="lossfee" value="${calculateDto.lossfee}"/>
        <input type="hidden" id="exch_rate" name="exch_rate" value="${calculateDto.exch_rate}"/>
        <input type="hidden" id="packagefee" name="exch_rate" value="${calculateDto.packagefee}"/>
        <input type="hidden" id="dealfee" name="dealfee" value="${calculateDto.dealfee}"/>
        <input type="hidden" id="currency" name="currency" value="${site.currency}"/>
        <div class="row">
            <div class="form-group" id="tagsRadiobuttons">
                <label class="col-md-1 control-label text-right" for="selectedSpeTags">规格标签：</label>
                <form:radiobutton path="selectedSpeTags"  value=""  />无
                <form:radiobuttons items="${speTagsList}" disabled="true" path="selectedSpeTags" itemLabel="tagsName" itemValue="id"/>
            </div>
        </div>
        <div class="row">
            <label class="col-md-1 control-label text-right" for="selectedTags">产品标签：</label>
            <div class="col-md-11" id="tagsCheckboxes">
                <form:checkboxes items="${tagsList}" disabled="true" path="selectedTags" itemLabel="tagsName" itemValue="id"/>
            </div>

        </div>
        <hr/>

        <div class="row">
            <label class="col-md-1 control-label text-right" for="imgPreview">图片预览：</label>
            <div class="col-md-3" style="width: 300px;">
                <a href="#" class="thumbnail">
                    <img id="imgPreview" src="${fns:getImageUrl()}${product.imgUrl}" width="300px">
                </a>
            </div>
        </div>
        <hr/>
        <div class="row">
            <label class="col-md-1 control-label text-right" >站点：</label>
            <label>${product.siteShortName}</label>
            <input  name="siteShortName" type="hidden" value="${product.siteShortName}" />
        </div>
        <hr/>
        <div class="row">
            <label class="col-md-1 control-label text-right" for="imgLink">示意图片地址：</label>
            <div class="col-md-3">
                <input class="form-control" id="imgLink" name="imgLink" readonly type="text" value="${product.imgLink}" placeholder="" >
            </div>
            <label class="col-md-1 control-label text-right">开发类型<span class="help-inline">&nbsp;<font color="red">*</font> </span>：</label>
            <div class="col-md-3">
                <c:if test="${product.developmentType==1}" var="type">
                    <input class="form-control" id="developmentType" type="text" value="	海外仓精品" readonly placeholder="" >
                </c:if>
                <c:if test="${product.developmentType==2}" var="type">
                    <input class="form-control" id="developmentType" type="text" value="虚拟海外仓精品" readonly placeholder="" >
                </c:if>
                <c:if test="${product.developmentType==3}" var="type">
                    <input class="form-control" id="developmentType"  type="text" value="	中国直发仓铺货" readonly placeholder="" >
                </c:if>
                <c:if test="${product.developmentType==4}" var="type">
                    <input class="form-control" id="developmentType" type="text" value="	虚拟海外仓铺货" readonly placeholder="" >
                </c:if>
                <input value="${product.developmentType}" type="hidden" name="developmentType"/>
            </div>
            <label class="col-md-1 control-label text-right" for="developmentChannel">开发渠道<span class="help-inline">&nbsp;<font color="red">*</font> </span>：</label>
            <div class="col-md-3">
                <form:select disabled="true" path="developmentChannel"  style="width: 200px;" class="form-control">
                    <form:options items="${fns:getDictList('developmentChannel')}"
                                  itemLabel="label"  itemValue="value" htmlEscape="false" />
                </form:select>
            </div>
        </div>
        <hr/>

        <div class="row">
            <label class="col-md-1 control-label text-right" for="cnName">中文报关名称：</label>
            <div class="col-md-3">
                <input class="form-control" id="cnName" name="cnName" readonly type="text" value="${product.cnName}" placeholder="" >
            </div>
            <label class="col-md-1 control-label text-right" for="enName">英文报关名称：</label>
            <div class="col-md-3">
                <input class="form-control" id="enName" name="enName" readonly type="text" value="${product.enName}" placeholder="">
            </div>
            <label class="col-md-1 control-label text-right" for="name">中文名<span class="help-inline">&nbsp;<font color="red">*</font> </span>：</label>
            <div class="col-md-3">
                <input class="form-control" id="name" name="name" readonly type="text" value="${product.name}" placeholder="">
            </div>
        </div>
        <hr/>

        <div class="row">
            <label class="col-md-1 control-label text-right" for="sellingPrice">售价<span class="help-inline">&nbsp;<font color="red">*</font> </span>：</label>
            <div class="col-md-3">
                <input class="form-control" id="sellingPrice" readonly name="sellingPrice" type="text" value="${product.sellingPrice}"placeholder="" >
            </div>
            <label class="col-md-1 control-label text-right" for="freight">运费<span class="help-inline">&nbsp;<font color="red">*</font> </span>：</label>
            <div class="col-md-3">
                <input class="form-control" id="freight" name="freight" readonly type="text" value="${product.freight}"placeholder="">
            </div>
            <label class="col-md-1 control-label text-right" for="costPrice">成本价<span class="help-inline">&nbsp;<font color="red">*</font> </span>：</label>
            <div class="col-md-3">
                <input type="text" class="form-control" id="costPrice" readonly name="costPrice" value="${product.costPrice}"  placeholder="">
            </div>
        </div>
        <hr/>

        <div class="row">
            <label class="col-md-1 control-label text-right" for="declaredValue">申报价格<span class="help-inline">&nbsp;<font color="red">*</font> </span>：</label>
            <div class="col-md-3">
                <input class="form-control" id="declaredValue" readonly name="declaredValue" type="text" value="${product.declaredValue}"  placeholder="" >
            </div>
            <label class="col-md-1 control-label text-right" for="defaultWeight">重量<span class="help-inline">&nbsp;<font color="red">*</font> </span>：</label>
            <div class="col-md-3">
                <input class="form-control" id="defaultWeight" readonly name="defaultWeight" type="text" value="${product.defaultWeight}"  placeholder="">
            </div>
            <label class="col-md-1 control-label text-right" for="profitMargin">利润率<span class="help-inline">&nbsp;<font color="red">*</font> </span>：</label>
            <div class="col-md-3">
                <input class="form-control" id="profitMargin" readonly name="profitMargin" type="text"  value="${product.profitMargin}" >
            </div>
        </div>
        <hr/>

        <div class="row">
            <label class="col-md-1 control-label text-right" for="procurementChannel">采购渠道：</label>
            <div class="col-md-3">
                <input class="form-control" id="procurementChannel" readonly name="procurementChannel" type="text" value="${product.procurementChannel}"  placeholder="">
            </div>
            <label class="col-md-1 control-label text-right" >材质：</label>
            <div class="col-md-3">
                <input class="form-control"  readonly name="texture" type="text" value="${product.texture}" />
            </div>
            <label class="col-md-1 control-label text-right">规格： </label>
            <div class="col-md-3">
                <input class="form-control" readonly name="specification" type="text" value="${product.specification}"   placeholder="">
            </div>
        </div>
        <hr/>
        <div class="row">
            <label class="col-md-1 control-label text-right" for="productType">型号： </label>
            <div class="col-md-3">
                <input class="form-control" id="productType" readonly name="productType" type="text" value="${product.productType}"   placeholder="">
            </div>
        </div>
        <hr/>
        <div class="row">
            <label class="col-md-1 control-label text-right" for="productUrl">ebay地址：</label>
            <div class="col-md-11">
                <input class="form-control" id="productUrl" readonly name="productUrl" type="text" value="${product.productUrl}" readonly  placeholder="">
            </div>
        </div>
        <hr/>

        <div class="row">
            <label class="col-md-1 control-label text-right" for="categoryName">刊登分类1：</label>
            <div class="col-md-3">
                <input class="form-control" id="categoryName" readonly name="categoryName" type="text" value="${calculateDto.categoryName}"   placeholder="">
            </div>
            <label class="col-md-1 control-label text-right" for="subCategoryName">刊登分类2：</label>
            <div class="col-md-3">
                <input class="form-control" id="subCategoryName" readonly name="subCategoryName" type="text" value="${calculateDto.subCategoryName}"   placeholder="">
            </div>
        </div>
        <hr/>
        <div class="row">
            <label class="col-md-1 control-label text-right" for="productTitle">title<font color="red">*</font></span>：</label>
            <div class="col-md-11">
        		<textarea class="form-control" name="title"
                          style="width: 600px; height: 100px; outline: none; resize: none" readonly>${product.productTitle}</textarea>
            </div>
        </div>
        <hr/>
        <%--<div class="row">--%>
            <%--<label class="col-md-1 control-label text-right" for="description">description<font color="red">*</font></span>：</label>--%>
            <%--<div class="col-md-11">--%>
        		<%--<textarea class="form-control" name="description"--%>
                          <%--style="width: 600px; height: 100px; outline: none; resize: none" readonly>${product.description}</textarea>--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--<hr/>--%>
        <div class="row">
            <label class="col-md-1 control-label text-right" for="supplier">采购源信息
                <span class="help-inline"><font color="red">*</font> </span>：
            </label>
            <div class="col-md-8">
                <table class="table table-bordered"  id="supplier">
                    <thead>
                    <tr>
                        <th width="50">编号</th>
                        <th>供应商名称</th>
                        <th>采购链接</th>
                        <th>备注</th>
                        <th width="100">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr id="addSource">
                        <td></td>
                        <td><input class="form-control" id="p_name" readonly name="p_name" type="text" value="" placeholder=""></td>
                        <td><input class="form-control" id="p_link" readonly name="p_link" type="text" value="" placeholder=""></td>
                        <td><input class="form-control" id="p_remark" readonly name="p_remark" type="text" value="" placeholder=""></td>
                        <td><button class="btn btn-default" disabled  type="button" onclick="" >添加</button></td>
                    </tr>

                    <c:forEach items="${product.purchaseSourceList}" var="source" varStatus="s">
                        <tr>
                            <td>${s.index+1}</td>
                            <td>${source.supplierName}</td>
                            <td><a href="${source.sourceUrl}" target="_blank">${source.sourceUrl}</a></td>
                            <td>${source.remark}</td>
                            <td><button class="btn btn-danger" disabled type="button" onclick="del_purchaseSource(${source.sourceId})">删除</button></td>
                        </tr>
                    </c:forEach>

                    </tbody>
                    <tfoot></tfoot>
                </table>
            </div>
        </div>
        <hr/>

        <div class="row">
            <label class="col-md-1 control-label text-right" for="propertyTable">属性：</label>
            <div class="col-md-8">
                <table class="table table-bordered"  id="propertyTable">
                    <thead>
                    <tr>
                        <th>属性</th>
                        <th>内容</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr id="propertyTr0">
                        <td align="center">
                            <div class="col-md-5">
                                <input class="form-control" id="property0" readonly name="specification" type="text" value=""   placeholder="">
                            </div>
                        </td>
                        <td align="center">
                            <textarea id="propertyWords0" name="colorName" readonly rows="5" wrap="off" style="overflow:auto;"></textarea>
                            <button class="btn btn-default" disabled name="seperator" type="button" onclick="">拆词工具</button>
                            <button class="btn btn-danger" disabled name="clearColor" type="button" onclick="">清空</button>
                        </td>
                        <td align="center">
                            <div class="row" id="add" style="margin-left: 10px;">
                                <button class="btn btn-default" disabled type="button" onclick="">添加</button>
                            </div>
                        </td>
                    </tr>
                    <c:forEach items="${propertyList}" var="property" varStatus="s">
                        <tr id="propertyTr${s.index}">
                            <td align="center">
                                <div class="col-md-5">
                                    <input class="form-control" id="property${s.index}" name="specification" readonly type="text" value="${property.propertyName}" placeholder=""/>
                                </div>
                            </td>
                            <td align="center">
                                <textarea class="propertyWords" name="colorName" rows="5" wrap="off" readonly style="overflow:auto;">${property.propertyValue}</textarea>
                                <button class="btn btn-default" name="seperator" disabled type="button" onclick="">拆词工具</button>
                                <button class="btn btn-danger" name="clearColor" disabled type="button" onclick="">清空</button>
                            </td>
                            <td align="center">
                                <div class="row" style="margin-left: 10px;">
                                    <button class="btn btn-danger" disabled type="button" onclick="">删除</button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>

                    </tbody>
                </table>
            </div>
        </div>
        <hr/>

        <div class="row">
            <label class="col-md-1 control-label text-right" for="codeManagerTb">子代码信息：</label>
            <div class="col-md-11">
                <table class="table table-bordered"  id="codeManagerTb">
                    <thead>
                    <tr id="codeManagerTr">
                        <th>编号</th>
                        <th>规格</th>
                        <th>子代码</th>
                        <th>采购源</th>
                        <th>采购数量</th>
                        <th>刊登价格</th>
                        <th>刊登运费</th>
                        <th>成本价</th>
                        <th>重量 </th>
                        <th>利润率</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tfoot></tfoot>
                </table>
            </div>
        </div>
        <hr/>
        <div class="row" style="margin-left: 50px;">
            <button class="btn btn-primary" type="button"  onclick="history.go(-1);">返回</button>
        </div>
    </form:form>
</div>
<!-- 拆词工具Modal -->
<div class="modal fade" id="seperateWordModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">拆词工具</h4>
            </div>
            <div class="modal-body">
                <div id="light" class="white_content">
                    待分词内容:<br />
                    <textarea name="chaichi" id="chaichi" style="width: 400px; height: 100px;"></textarea>
                    <br/>
                    分隔符:<br/>
                    <input type="text" name="splitChiInput" id="splitChiInput" />
                    <br/><br/>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="splitChi()">分词</button>
            </div>
        </div>
    </div>
</div>
</body>

</html>