package com.oigbuy.jeesite.modules.ebay.product.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.process.dao.ProcessBusinessDao;
import com.oigbuy.jeesite.common.process.entity.EbayProcessBusiness;
import com.oigbuy.jeesite.common.process.entity.ProcessBean;
import com.oigbuy.jeesite.common.process.service.GeneralProcessService;
import com.oigbuy.jeesite.common.utils.HttpHelper;
import com.oigbuy.jeesite.common.utils.StringUtils;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.Platform;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.service.PlatformService;
import com.oigbuy.jeesite.modules.ebay.product.dao.ProductCodeManagerDao;
import com.oigbuy.jeesite.modules.ebay.product.dao.ProductDao;
import com.oigbuy.jeesite.modules.ebay.product.entity.ChildSku;
import com.oigbuy.jeesite.modules.ebay.product.entity.Data;
import com.oigbuy.jeesite.modules.ebay.product.entity.MessureMmsParam;
import com.oigbuy.jeesite.modules.ebay.product.entity.MmsParam;
import com.oigbuy.jeesite.modules.ebay.product.entity.ParentSku;
import com.oigbuy.jeesite.modules.ebay.product.entity.Product;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductCodeManager;
import com.oigbuy.jeesite.modules.ebay.productDevelop.dao.ProductAndCollectionDao;
import com.oigbuy.jeesite.modules.ebay.productDevelop.entity.ProductToBeDevelop;
import com.oigbuy.jeesite.modules.ebay.productDevelop.service.ProductToBeDevelopService;
import com.oigbuy.jeesite.modules.ebay.productcollection.entity.ProductCollection;
import com.oigbuy.jeesite.modules.ebay.productcollection.entity.ProductCollectionLog;
import com.oigbuy.jeesite.modules.ebay.productcollection.service.ProductCollectionLogService;
import com.oigbuy.jeesite.modules.ebay.productcollection.service.ProductCollectionService;
import com.oigbuy.jeesite.modules.sys.utils.UserUtils;

@Service
public class ProductMmsService  {

	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductCodeManagerDao proCodeManagerDao;
	@Autowired
	private PlatformService platformService;
	@Autowired
	private ProcessBusinessDao processBusinessDao;
	@Autowired
	private ProductAndCollectionDao productAndCollectionDao;
	@Autowired
	private GeneralProcessService generalProcessService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCollectionService productCollectionService;
	@Autowired
	private ProductToBeDevelopService toBeDevelopService;
	@Autowired
	private ProductCollectionLogService productCollectionLogService;
	
	/**
	 *
	 * @param messureMmsParam
	 * @param collectId
	 * @return
	 */
	@Transactional(readOnly=false)
	public String startMeasure(MessureMmsParam messureMmsParam,String collectId){
//		messureMmsParam=new MessureMmsParam("001", "美国","111>222>333", "444>555",4.21, 5.21, "http:www.baidu.com", "乒乓球", "123456","销售组","中文名称","");
		MmsParam mmsParam=new MmsParam();
		mmsParam.setCode(Global.SUCCESS);
		mmsParam.setMessage(Global.REQUEST_MESSAGE);
		mmsParam.setData(new Data());
		mmsParam.getData().setMessureMmsParam(messureMmsParam);
		String url = Global.MMSURL;
		//将对象转为json字符串
		String data = JSONObject.toJSONString(mmsParam);
		String message=HttpHelper.post(url, data,null);
		if(StringUtils.isBlank(message)){
			mmsParam.setCode(Global.GATEWAY_TIMEOUT_CODE);
			mmsParam.setMessage(Global.GATEWAY_TIMEOUT_MESSAGE);
			mmsParam.setData(null);
			message=JSONObject.toJSONString(mmsParam);
			return message;
		}
		
		//将json字符串转为对象
		JSONObject object = JSONObject.parseObject(message);
		if(Global.SUCCESS.equals(object.getString(Global.CODE))){
			Product product=new Product();
			product.setId(Global.getID().toString());
			product.setCreateId(UserUtils.getUser().getId());
			product.setDevelopmentType(Global.SALE_TYPE_OVERSEAS_STOREHOUSE);
			
			//插入产品表和竞品表关系
			ProductToBeDevelop productToBeDevelop = new ProductToBeDevelop();
			productToBeDevelop.setCollectId(collectId);
			productToBeDevelop.setProductId(product.getId());
			productAndCollectionDao.insert(productToBeDevelop);
			
			//获取竞品
			ProductCollection productCollection = productCollectionService.get(collectId);
			product.setImgUrl(productCollection.getImgUrl());
			product.setImgLink(productCollection.getImgLink());
			productDao.insert(product);
		}
		return message;
	}
	
	
	

	@Transactional(readOnly=false)
	public MmsParam mmsUpdateProduct(Product productParam) {
		MmsParam  data = new MmsParam(Global.FAIL,Global.FAIL_MESSAGE,null);
		if(productParam==null){
			return data;
		}
		//为产品设置平台
		List<Platform> list=platformService.getinfoByplatformName(productParam.getPlatformFlag());
		if(CollectionUtils.isEmpty(list)){
			return data.fail(Global.FAIL,Global.FAIL_GET_PLATFROM_MESSAGE,null);
		}
		Platform platform=list.get(0);
		
		//获取流程表中此竞品的id
		String procInsId = generalProcessService.getProcInsId(productParam.getProcessNum());
		EbayProcessBusiness business = processBusinessDao.get(procInsId);
		if(business==null){
			return data;
		}
		//获取产品对象
		String productId = productAndCollectionDao.findByCollectId(business.getBusinessId());
		Product product=productDao.get(productId);
		if(null==product){
			return data.fail(Global.FAIL,Global.FAIL_GET_PRODUCT_MESSAGE,null);
		}
			product.setPlatformFlag(platform.getId());
			product.setName(productParam.getName());
			product.setCostPrice(productParam.getCostPrice());
			product.setSellingPrice(productParam.getSellingPrice());
			product.setCurrencyType(productParam.getCurrencyType());
			product.setDefaultWeight(productParam.getDefaultWeight());
			product.setWeightType(productParam.getWeightType());
			product.setTitle(productParam.getTitle());
			product.setFreight(productParam.getFreight());
			product.setCreateTime(productParam.getCreateTime());
			product.setTitleNum(productParam.getTitleNum());
			product.setIdentifying(productParam.getIdentifying());
			product.setProfit(productParam.getProfit());
			product.setProfitRate(productParam.getProfitRate());
			product.setProfitMargin(productParam.getProfitRate()*Global.CONSTANT_100);
			product.setUpdateTime(new Date());
			product.setDeliveryWareHouse(productParam.getDeliveryWareHouse());
			productDao.updateById(product);
		return data.success(null);
	}
	
	/**
	 * 放弃开发
	 */
	@Transactional(readOnly=false)
	public MmsParam giveUpMMsDevelopment(MmsParam mmsParam){
		MmsParam  data=new MmsParam();
		//获取流程号
		String processNum=mmsParam.getData().getProduct().getProcessNum();
		//获取流程表中流程id
		String procInsId = generalProcessService.getProcInsId(processNum);
		//获取流程对象
		EbayProcessBusiness business = processBusinessDao.get(procInsId);
		//获取产品对象id
		String productId = productAndCollectionDao.findByCollectId(business.getBusinessId());
		Product product = productService.get(productId);
		
		//mms放弃产品开发
		String note=Global.MMS_GIVE_UP_MESSAGE;
		//更新竞品状态
		ProductCollection collection = new ProductCollection();
		collection.setId(business.getBusinessId());
		collection.setStatus(Global.PRODUCT_COLLECT_STATUS_GIVEUP_DEVELOPMENT);
		productCollectionService.saveOrUpdate(collection);
	    //将备注信息回写到竞品采集日志中
        if (StringUtils.isNotEmpty(note)) {
			ProductCollectionLog productCollectionLog = new ProductCollectionLog();
			productCollectionLog.setId(Global.getID().toString());
			productCollectionLog.setCreateBy(UserUtils.get(product.getCreateId()));
			productCollectionLog.setCreateDate(new Date());
			productCollectionLog.setRemarks(note);
			productCollectionLog.setCollectId(business.getBusinessId());
			productCollectionLog.setIsNewRecord(true);
			productCollectionLogService.save(productCollectionLog);
		}
        //根据流程id删除流程表中代办
		generalProcessService.deleteByproInsId(procInsId);
		List<ProductCodeManager> list = proCodeManagerDao.findListByProductId(productId);
		if(null!=list && list.size()>0){
			//删除子代码
			proCodeManagerDao.deleteProductCodeManagersByProductId(productId);
		}
		//删除竞品和产品关联表
		productAndCollectionDao.deleteByProductId(productId);
		//删除产品
		productDao.delete(product);
		return data.success(null);
	}
	
	
	/**
	 * 3.产品开发失败，调用我的这个接口， 修改产品状态为待开发状态
	 */
	
	/**
	 * 1.保存到字代码表中
	 * 2.修改product表
	 * @param parentSku
	 */
	@Transactional(readOnly=false)
	public void mmsCreateSku(ParentSku parentSku) {
		List<ChildSku> childSkuList=parentSku.getChildSku();
		Product product=productDao.get(parentSku.getProductId());
		//保存到字代码表中
		for(ChildSku childSku:childSkuList){
			ProductCodeManager productCodeManager=new ProductCodeManager();
			String codeManagerId=Global.getID().toString();
			productCodeManager.setId(codeManagerId);
			productCodeManager.setProductId(parentSku.getProductId());
			productCodeManager.setCnName(childSku.getCnName());
			productCodeManager.setEnName(childSku.getEnName());
			productCodeManager.setSysSku(childSku.getSysSku());
			productCodeManager.setLength(childSku.getLength().toString());
			productCodeManager.setWide(childSku.getWide().toString());
			productCodeManager.setHigh(childSku.getHigh().toString());
			productCodeManager.setWeight(childSku.getWeight());
			productCodeManager.setCostPrice(childSku.getCostPrice());
			productCodeManager.setSysParentSku(parentSku.getSysParentSku());
			productCodeManager.setProperty(childSku.getCnName());
			productCodeManager.setProfitRate(product.getProfitMargin());
			proCodeManagerDao.insert(productCodeManager);
		}
		//修改product表
		
		if(null!=product){
			product.setSysParentCode(parentSku.getSysParentSku());
			product.setType(Global.PRODUCT_TYPE_OFFICIAL);
			product.setUpdateTime(new Date());
//			product.setUpdateId(UserUtils.getUser().getId());
			productDao.updateById(product);
		}
	}


	public MmsParam getMmsProduct(MmsParam mmsParam){
		MmsParam  data = new MmsParam();
		if(Global.SUCCESS.equals(mmsParam.getCode())){
			try {
				Product product=mmsParam.getData().getProduct();
				return this.mmsUpdateProduct(product);
			} catch (Exception e) {
				StringWriter sw = new StringWriter();
	            PrintWriter pw = new PrintWriter(sw);
	            e.printStackTrace(pw);
	            e.printStackTrace();
				return data.fail(Global.FAIL,sw.toString(), null);
			}
		}else if(Global.FAIL.equals(mmsParam.getCode())){
			//放弃开发
			try {
				MmsParam giveUpMMsDevelopment = this.giveUpMMsDevelopment(mmsParam);
				return giveUpMMsDevelopment;
			} catch (Exception e) {
				StringWriter sw = new StringWriter();
	            PrintWriter pw = new PrintWriter(sw);
	            e.printStackTrace(pw);
	            e.printStackTrace();
				return  data.fail(Global.FAIL,sw.toString(), null);
			}
		}else{
			return data.fail(Global.FAIL, Global.FAIL_MESSAGE, null);
		}
	}

	
	/**
	 * 
	 * @param mmsParam
	 * @return
	 */
	@Transactional(readOnly=false)
	public MmsParam getMmsSku(MmsParam mmsParam) {
		MmsParam  data = new MmsParam(Global.FAIL,Global.FAIL_MESSAGE,null);
		if (Global.SUCCESS .equals(mmsParam.getCode())) {
			try {
				ParentSku parentSku = mmsParam.getData().getParentSku();
				//获取流程表中此竞品的id
				String procInsId = generalProcessService.getProcInsId(parentSku.getProcessNum());
				EbayProcessBusiness business = processBusinessDao.get(procInsId);
				//获取产品对象
				String productId = productAndCollectionDao.findByCollectId(business.getBusinessId());
				parentSku.setProductId(productId);
				mmsCreateSku(parentSku);
				
				//更新竞品状态
				ProductCollection collection = new ProductCollection();
				collection.setId(business.getBusinessId());
				collection.setStatus(Global.PRODUCT_COLLECT_STATUS_DRAWING);
				productCollectionService.saveOrUpdate(collection);
				
				/**============================流程流转=========================*/
				Product product = productService.get(productId);
				//获取竞品对象
				ProductCollection productCollection = productCollectionService.get(business.getBusinessId());
				//完成上一任务，重新创建任务
				EbayProcessBusiness ebayProcessBusiness = new EbayProcessBusiness();
				ebayProcessBusiness.setImgUrl(productCollection.getImgUrl());
				ebayProcessBusiness.setProductName(product.getName());
				ebayProcessBusiness.setProductUrl(productCollection.getProductUrl());
				ebayProcessBusiness.setSysParentCode(parentSku.getSysParentSku());
				ebayProcessBusiness.setSaleType(Global.SALE_TYPE_OVERSEAS_STOREHOUSE);
				ebayProcessBusiness.setCreateName(product.getCreateName());
				ebayProcessBusiness.setCreateTime(new Date());
				ebayProcessBusiness.setBusinessId(productId);

				ProcessBean<EbayProcessBusiness> bean = new ProcessBean<EbayProcessBusiness>();
				Map<String, Object> vars = new HashMap<String, Object>();
				vars.put(Global.SALE_TYPE, Global.SALE_TYPE_OVERSEAS_STOREHOUSE);
				bean.getAct().setTaskId(parentSku.getProcessNum());
				bean.setData(ebayProcessBusiness);
				bean.setVars(vars);
				generalProcessService.complete(bean);
				/**=======================流程代码结束=============================*/
				
				return data.success(null);
			} catch (Exception e) {
				StringWriter sw = new StringWriter();
	            PrintWriter pw = new PrintWriter(sw);
	            e.printStackTrace(pw);
	            e.printStackTrace();
				return data.fail(Global.FAIL, sw.toString(), null);
			}
		} else{
			return data;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
