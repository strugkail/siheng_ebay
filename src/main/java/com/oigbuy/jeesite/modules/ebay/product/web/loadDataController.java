package com.oigbuy.jeesite.modules.ebay.product.web;

import com.oigbuy.jeesite.common.rabbitmq.ProductMsgProduce;
import com.oigbuy.jeesite.modules.ebay.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by luwang.wang on 2017-10-25.
 */
@Controller
@RequestMapping(value = "${adminPath}/ebay/loadData")
public class loadDataController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMsgProduce messageProducer;

    @RequestMapping("toView")
    public String toJsp(){
        return "modules/ebay/product/loadExcel";
    }

    @RequestMapping("getFile")
    @Transactional(readOnly = false)
    public String getFile(String file) throws Exception{
        String[] fileArray = file.split(",");
        if(fileArray.length>0){
            for(int i = 0;i<fileArray.length;i++){
                String xlsx = fileArray[i].substring(fileArray[i].length()-5,fileArray[i].length());
                String xls = fileArray[i].substring(fileArray[i].length()-4,fileArray[i].length());
                if((".xlsx").equals(xlsx)){
                    productService.insertXlsxData(fileArray[i]);
                }else if((".xls").equals(xls)){
                    productService.insertXlsData(fileArray[i]);
                }else{
                    System.out.print("这个不是Excel数据");
                }
            }
        }
        return "modules/ebay/product/loadExcelSuccess";
    }

}
