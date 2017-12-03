package com.oigbuy.jeesite.modules.ebay.product.web;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oigbuy.jeesite.common.process.dao.ProcessBusinessDao;
import com.oigbuy.jeesite.common.process.service.GeneralProcessService;
import com.oigbuy.jeesite.modules.ebay.product.entity.MessureMmsParam;
import com.oigbuy.jeesite.modules.ebay.product.entity.MmsParam;
import com.oigbuy.jeesite.modules.ebay.product.service.ProductMmsService;
import com.oigbuy.jeesite.modules.ebay.product.service.ProductService;
import com.oigbuy.jeesite.modules.ebay.productDevelop.dao.ProductAndCollectionDao;
import com.oigbuy.jeesite.modules.ebay.productcollection.service.ProductCollectionService;

@Controller
@RequestMapping(value = "${adminPath}/product")
public class ProductMmsController {
	
	@Autowired
	private ProductMmsService productMessureService;
	
	@Autowired
	private GeneralProcessService generalProcessService;
	
	@Autowired
	private ProductCollectionService productCollectionService;
	
	@Autowired
	private ProcessBusinessDao processBusinessDao;
	
	@Autowired
	private ProductAndCollectionDao productAndCollectionDao;
	
	@Autowired
	private ProductService productService;
	
	private static final Logger logger=LoggerFactory.getLogger(ProductMmsService.class);
	/**
	 * 调用mms接口开始测算，并开发 
	 * @param messureParam
	 * @throws UnsupportedEncodingException 
	 */
	@ResponseBody
	@RequestMapping(value = "messure")
	public String startMeasure(MessureMmsParam messureParam,String collectId){
			return productMessureService.startMeasure(messureParam,collectId);
	}
	
	
	/**
	 * mms产品开发成功或失败调用的接口
	 * @param mmsParam
	 */
	@ResponseBody
	@RequestMapping(value="getMmsProduct")
	public MmsParam getMmsProduct(@RequestBody MmsParam mmsParam){
		logger.info("mms产品开发成功或失败调用的接口参数："+mmsParam);
		return productMessureService.getMmsProduct(mmsParam);
	}
	
	/**
	 * mms子代码开发成功调用的接口 
	 * @param mmsParam
	 */
	@ResponseBody
	@RequestMapping(value="getMmsSku")
	public MmsParam getMmsSku(@RequestBody MmsParam mmsParam) {
		logger.info("mms子代码开发成功调用的接口参数："+mmsParam);
		return productMessureService.getMmsSku(mmsParam);
	}
	
}
