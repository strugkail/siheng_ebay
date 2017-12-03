package com.oigbuy.jeesite.modules.ebay.product.service;

import com.alibaba.fastjson.JSONObject;
import com.oigbuy.api.core.call.ebay.GetEbayItem;
import com.oigbuy.api.core.request.ebay.GetEbayItemRequest;
import com.oigbuy.api.domain.ebay.ItemViewDto;
import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.fastdfs.util.FastdfsHelper;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.rabbitmq.ProductMsgProduce;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.common.utils.DescartesUtil;
import com.oigbuy.jeesite.common.utils.HttpHelper;
import com.oigbuy.jeesite.modules.ebay.designdrawing.service.DesignDrawingService;
import com.oigbuy.jeesite.modules.ebay.ebayPaypal.dao.EbayPaypalDao;
import com.oigbuy.jeesite.modules.ebay.ebayPaypal.entity.EbayPaypal;
import com.oigbuy.jeesite.modules.ebay.ebayPaypal.service.EbayPaypalService;
import com.oigbuy.jeesite.modules.ebay.ebayfee.dao.EbayFeeDao;
import com.oigbuy.jeesite.modules.ebay.ebayfee.entity.EbayFee;
import com.oigbuy.jeesite.modules.ebay.ebayfee.service.EbayFeeService;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.dao.PlatformSiteDao;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.PlatformSite;
import com.oigbuy.jeesite.modules.ebay.product.dao.ProductCodeManagerDao;
import com.oigbuy.jeesite.modules.ebay.product.dao.ProductDao;
import com.oigbuy.jeesite.modules.ebay.product.entity.*;
import com.oigbuy.jeesite.modules.ebay.product.utils.ExcelUtil;
import com.oigbuy.jeesite.modules.ebay.productDevelop.dao.ProductAndCollectionDao;
import com.oigbuy.jeesite.modules.ebay.productDevelop.dao.ProductDevelopDao;
import com.oigbuy.jeesite.modules.ebay.productDevelop.dao.ProductTagsDao;
import com.oigbuy.jeesite.modules.ebay.productDevelop.dao.ProductToBeDevelopDao;
import com.oigbuy.jeesite.modules.ebay.productDevelop.dto.CalculateDto;
import com.oigbuy.jeesite.modules.ebay.productDevelop.entity.ProductTags;
import com.oigbuy.jeesite.modules.ebay.productDevelop.entity.ProductToBeDevelop;
import com.oigbuy.jeesite.modules.ebay.purchase.dao.ProductPurchaseDao;
import com.oigbuy.jeesite.modules.ebay.purchase.dao.SupplierDao;
import com.oigbuy.jeesite.modules.ebay.purchase.dto.PurchaseConfigInfo;
import com.oigbuy.jeesite.modules.ebay.purchase.dto.PurchaseSourceCode;
import com.oigbuy.jeesite.modules.ebay.purchase.entity.ProductPurchase;
import com.oigbuy.jeesite.modules.ebay.purchase.entity.Supplier;
import com.oigbuy.jeesite.modules.ebay.purchase.service.SupplierService;
import com.oigbuy.jeesite.modules.ebay.pysynctask.dao.PySyncTaskDao;
import com.oigbuy.jeesite.modules.ebay.pysynctask.entity.PySyncTask;
import com.oigbuy.jeesite.modules.ebay.tags.entity.Tags;
import com.oigbuy.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author tony.liu
 */
@Service
@Transactional(readOnly = true)
public class ProductService extends CrudService<ProductDao, Product> {

    public static final double FINANCE_RATE = 0.025;
    public static final double LOSS_RATE = 0.03;
//    private static final double EXCH_RATE = 8.8441;

    // 对应人民币￥
    private static final double PACKAGE_FEE = 0.35;
    private static final double DEAL_FEE = 1.00;

    private static Logger logger = LoggerFactory
            .getLogger(ProductService.class);

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductTagsDao productTagsDao;
    @Autowired
    private PySyncTaskDao pySyncTaskDao;
    @Autowired
    private SupplierDao supplierDao;
    @Autowired
    private ProductDevelopDao productDevelopDao;
    @Autowired
    private ProductPurchaseDao productPurchaseDao;
    @Autowired
    private ProductCodeManagerDao productCodeManagerDao;
    @Autowired
    private ProductAndCollectionDao productAndCollectionDao;
    @Autowired
    private PlatformSiteDao platformSiteDao;
    @Autowired
    private EbayPaypalDao ebayPaypalDao;
    @Autowired
    private EbayFeeDao ebayFeeDao;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private ProductToBeDevelopDao productToBeDao;
    @Autowired
    private ProductCodeManagerService productCodeManagerService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private DesignDrawingService designDrawingService;
    @Autowired
    private TProductImgPlatformService tProductImgPlatformService;
    @Autowired
    private ProductMsgProduce messageProducer;
    @Autowired
    private EbayPaypalService ebayPaypalService;
    @Autowired
    private EbayFeeService ebayFeeService;

    public Product get(String id) {
        return super.get(id);
    }

    public List<Product> findList(Product product) {
        return super.findList(product);
    }

    public Page<Product> findPage(Page<Product> page, Product product) {
        return super.findPage(page, product);
    }

	/**
	 * 保存产品信息，并同步到普源
	 * @param product
	 */
	@Transactional(readOnly = false)
	public void saveAndPublishProduct(Product product) {

	    Long start = System.currentTimeMillis();
        logger.info("===========================产品开发开始=============================");
        // 1.保存产品基本信息
        saveProductCommonInfo(product);

        synchronized (this) {

            // 2.生成母代码
            product.setSysParentCode(Global.getProductCode());

            // 开发完成标志
            product.setType(Global.PRODUCT_TYPE_OFFICIAL);
            //待完成产品数量补充标识
            product.setFinishedProductQuantity(Global.FINISHING_PRODUCT_QUANTITY);
            productDao.updateById(product);
            logger.info("===================产品已提交，产品信息:" + product.toString()+"==============================");
            // 3.生成子代码,并生成需要开发的任务
//            if (Integer.parseInt(product.getDevelopmentType()) < 3) {
                createSysSku(product);
//            }

        }
        logger.info("=========================================保存产品总耗时："+(System.currentTimeMillis()-start)/1000+"秒=======================================");

    }

    @Transactional(readOnly = false)
    public int insert(Product product) {
        return productDao.insert(product);
    }

    /**
     * 生成子代码sku
     *
     * @param product
     */
    private void createSysSku(Product product) {
        String productId = product.getId();

        // 删除产品的codeManager数据
        productCodeManagerDao.deleteProductCodeManagersByProductId(productId);
        // 子代码信息
        List<PurchaseConfigInfo> purchaseConfigInfoList = product.getPurchaseConfigInfoList();
        List<ProductProperty> propertyList = propertyService.findByProductId(productId);
        if(propertyList.size()>0){
            for (int i=0;i<purchaseConfigInfoList.size();i++){
                PurchaseConfigInfo pc = purchaseConfigInfoList.get(i);
                pc.setCodeManagerId(propertyList.get(i).getCodeManagerId());
            }
        }
        // 获取产品的parentCode
        String sysParentCode = product.getSysParentCode();

        if (CollectionUtils.isNotEmpty(purchaseConfigInfoList)) {
            List<ProductCodeManager> productCodeManagerList = new ArrayList<ProductCodeManager>();
            List<PurchaseSourceCode> purchaseSourceCodeList = new ArrayList<PurchaseSourceCode>();
            List<ProductPurchase> productPurchaseList = new ArrayList<ProductPurchase>();

            // 创建一个任务ID
            String taskId = Global.getID().toString();
            int i = 0;
            for (PurchaseConfigInfo p : purchaseConfigInfoList) {
                ProductCodeManager pcm = null;
                if (p.getProfitRate() != null) {
                    pcm = new ProductCodeManager(
                            p.getCodeManagerId(),
                            product.getId(), product.getCnName(), product.getEnName(), sysParentCode,
                            i == 0 ? sysParentCode : Global.getProductCode(), new Date(), p.getSize(), p.getColor(),
                            p.getWishSize(), p.getWishColor(), p.getPublishPrice(), p.getPublishTransPrice(),
                            p.getCostPrice(), p.getWeight(), p.getProfitRate(), p.getQuantity(), p.getProperty(),
                            p.getSourceId(),p.getProfit());
                    pcm.setCodeIndex(i);
                    productCodeManagerList.add(pcm);
                    PurchaseSourceCode psc = new PurchaseSourceCode(
                            Global.getID().toString(),
                            pcm.getId(), p.getSourceId(), productId);
                    purchaseSourceCodeList.add(psc);
                    // 如果开发产品的时候配置了采购数量，需要保存采购信息
                    if (p.getQuantity() != null && p.getQuantity() > 0) {
                        ProductPurchase productPurchase = new ProductPurchase(
                                Global.getID().toString(),
                                psc.getSourceCodeId(), productId, taskId, p.getQuantity());
                        productPurchaseList.add(productPurchase);
                    }

                    // 生成普元的增加商品的任务
                    String content = pcm.getSysParentSku() + "|" + pcm.getSysSku();
                    PySyncTask pySyncTask = new PySyncTask(
                            Global.getID().toString(),
                            pcm.getId(), Global.PY_TASK_OPERTYPE_ADD_GOODS, content, new Date());
                    logger.info("============================开始保存普源商品任务信息=============================");
                    Long start = System.currentTimeMillis();
                    pySyncTaskDao.insert(pySyncTask);
                    logger.info("========================添加普元增加商品的任务成功:" + pySyncTask.toString()+"耗时："+(System.currentTimeMillis()-start)+"毫秒===============================");
                    i++;
                }
            }
            // 保存子代码信息
            if (productCodeManagerList.size() > 0) {
                logger.info("============================开始保存子代码信息=============================");
                Long start = System.currentTimeMillis();
                productCodeManagerDao.insertList(productCodeManagerList);
                logger.info("============================子代码信息添加成功,耗时："+(System.currentTimeMillis()-start)+"毫秒=============================");
            }
            // 保存子代码与采购源关系列表
            if (purchaseSourceCodeList.size() > 0) {
                Long start = System.currentTimeMillis();
                logger.info("============================开始保存子代码与采购源关联关系=============================");
                supplierDao.insertPurchaseSourceCodeList(purchaseSourceCodeList);
                logger.info("=========================子代码与采购源关系添加成功，耗时："+(System.currentTimeMillis()-start)+"毫秒=================================");
            }
            // 新建采购任务
            if (CollectionUtils.isNotEmpty(productPurchaseList)) {
                Long start = System.currentTimeMillis();
                logger.info("============================开始保存采购任务=============================");
                productPurchaseDao.insertList(productPurchaseList);
                logger.info("======================新建采购任务添加成功，耗时："+(System.currentTimeMillis()-start)+"毫秒=============================");
            }
        } else {
            logger.error("采购源配置信息列表为空，请重新检查！");
        }
    }

    /**
     * 添加产品时，保存产品信息到产品表
     *
     * @param product
     * @para
     */
    @Transactional(readOnly = false)
    public void saveProduct(Product product) {

        // 设置产品创建时间
        product.setCreateTime(new Date());

        // 设置创建人
        product.setCreateId(UserUtils.getPrincipal().getId().toString());

        // 基本信息保存与设置
        saveProductCommonInfo(product);

        // 添加产品
        productDao.insert(product);
        logger.info("****产品添加成功，产品信息：****"+product.toString());
    }

    /**
     * 更新产品信息
     *
     * @param product
     */
    @Transactional(readOnly = false)
    public void updateProduct(Product product) {

        // 设置产品的更新时间
        product.setUpdateTime(new Date());

        // 设置更新产品的操作人
        product.setUpdateId(UserUtils.getPrincipal().getId().toString());

        // 插入产品标签和规格标签
        insertProductTags(product);

        // 更新子代码信息
        updateCodeManagerList(product);


        // 更新入库
        productDao.updateById(product);
        logger.info("========================产品更新成功，产品信息："+product.toString()+"=================================");
    }

    public void updateCodeManagerList(Product product){
        ProductCodeManager codeManager = null;
        // 采购源配置信息
        List<PurchaseSourceCode> purchaseSourceCodeList = new ArrayList<PurchaseSourceCode>();
        for (PurchaseConfigInfo info:product.getPurchaseConfigInfoList()) {
            codeManager = new ProductCodeManager();
            codeManager.setProductId(product.getId());
            codeManager.setId(info.getCodeManagerId());
            codeManager.setPublishPrice(info.getPublishPrice());
            codeManager.setCostPrice(info.getCostPrice());
            codeManager.setProfitRate(info.getProfitRate());
            codeManager.setRecommendNumber(info.getQuantity());
            codeManager.setWeight(info.getWeight());
            codeManager.setProperty(info.getProperty());
            codeManager.setSourceId(info.getSourceId());
            codeManager.setPublishTransPrice(info.getPublishTransPrice());
            codeManager.setProfit(info.getProfit());
            ProductCodeManager myCodeManager = productCodeManagerDao.get(codeManager.getId());
            PurchaseSourceCode psc = new PurchaseSourceCode(
                    Global.getID().toString(),
                    info.getId(), info.getSourceId(), product.getId());
            purchaseSourceCodeList.add(psc);
            if(myCodeManager==null){
                productCodeManagerDao.insert(codeManager);
            }else{
                productCodeManagerDao.update(codeManager);
            }
        }
    }

    /**
     * 保存产品的公共信息(保存草稿箱与开发产品都需要保存的产品数据)
     *
     * @param product
     */
    private void saveProductCommonInfo(Product product) {

        // 设置默认重量
        product.setWeightType(Global.ZERO);
        // 设置更新时间
        product.setUpdateTime(new Date());

        // 1.根据原图地址生成本地图片地址
        if (StringUtils.isNotBlank(product.getImgLink())) {
            String newImgUrl = FastdfsHelper.uploadFile(product.getImgLink());
            if (StringUtils.isNotBlank(newImgUrl)) {
                product.setImgUrl(newImgUrl);
            } else {
                product.setImgUrl(Global.getConfig("default_img_url"));
            }
        }
        // 2. 产品和标签的关系表插入数据
        insertProductTags(product);

    }

    /**
     * 添加产品标签
     *
     * @param product
     */
    private void insertProductTags(Product product) {
        String productId = product.getId();

        // 1.删除productTag
        productTagsDao.deleteAllByProductId(productId);
        // 2.保存product和tag关系
        List<Tags> tagsList = new ArrayList<Tags>();
        List<Tags> selectedTags = product.getSelectedTags();
        List<Tags> selectedSpeTags = product.getSelectedSpeTags();
        if (CollectionUtils.isNotEmpty(selectedTags)) {
            tagsList.addAll(selectedTags);
        }
        if (CollectionUtils.isNotEmpty(selectedSpeTags)) {
            tagsList.addAll(selectedSpeTags);
        }

        if (CollectionUtils.isNotEmpty(tagsList)) {
            List<ProductTags> ProductTagsList = new ArrayList<ProductTags>();
            for (Tags tag : tagsList) {
                ProductTags productTags = new ProductTags(Long.parseLong(productId),
                        Long.parseLong(tag.getId()));
                ProductTagsList.add(productTags);
            }
            productTagsDao.insertList(ProductTagsList);
            logger.info("===============================添加对应产品id的tags==========================");
        }
    }

    /**
     * 获取子代码信息列表
     *
     * @param productId
     * @return
     */
    public List<PurchaseConfigInfo> getPurchaseConfigInfoList(String productId) {
        return productDevelopDao.getInfoListByProductId(productId);
    }

    /***
     * 更新该 产品  并且更新该产品下的 子 sku 信息（主要是价格数量等）
     *
     * @param productTemp  要更新的产品数据
     * @param codeManagerList   要操作的子 sku 数据
     */

    //@Transactional(readOnly=false)
//	public boolean updateProductAndCodeManager(Product productTemp,List<ProductCodeManager> codeManagerList) {
//	 // this.productDao.updateById(productTemp);
//	  for (ProductCodeManager productCodeManager : codeManagerList) {
//		  this.productCodeManagerDao.update(productCodeManager);
//	   }
//	  return true;
//	}

    /**
     * 由productId获取属性列表
     *
     * @param product
     * @return
     */
    public List<ProductProperty> findPropertyListById(Product product) {
        List<ProductProperty> list = propertyService.findPropertyListById(product.getId());
        if (list.size() > 0) {
            for (ProductProperty property : list) {
                String value = "";
                List<ProductProperty> newList = propertyService.findPropertyValueListById(property);
                for (ProductProperty newProperty : newList) {
                    value += newProperty.getPropertyValue() + Global.SEPARATE_2;
                }
                property.setPropertyValue(value);
            }
        }
        return list;
    }

    /**
     * 添加到数据库
     *
     * @param property
     * @return
     */
    @Transactional(readOnly = false)
    public void addProperty(ProductProperty property) {
        String[] strs = property.getPropertyValue().split(Global.SEPARATE_6);
        String propertyCode =
                Global.getID() + "";
        for (int i = 0; i < strs.length; i++) {
            propertyService.addProperty(new ProductProperty(
//                    System.currentTimeMillis() + "",
                    Global.getID()+"",
                    propertyCode,
                    property.getProductId(),
                    property.getPropertyName(),
                    strs[i],
                    null
            ));
        }

    }

    /**
     * 根据propertyId删除属性信息
     *
     * @param property
     * @return
     */
    @Transactional(readOnly = false)
    public int deletePropertyById(ProductProperty property) {
        int m = propertyService.deletePropertyById(property);
        return m;
    }

    /**
     * 为子代码添加属性信息
     *
     * @param codeManagerList
     * @param product
     * @return
     */
    public List<ProductCodeManager> addPropertyToCodeManager(List<ProductCodeManager> codeManagerList, Product product) {
        List<ProductProperty> valueList = findPropertyListById(product);
        if (valueList.size() > 0) {
            List<List<String>> list0 = new ArrayList<>();
            for (ProductProperty property : valueList) {
                String[] values = property.getPropertyValue().split(Global.SEPARATE_2);
                List<String> list1 = new ArrayList<>();
                for (int i = 0; i < values.length; i++) {
                    list1.add(values[i]);
                }
                list0.add(list1);
            }
            List<List<String>> resultList = new ArrayList<List<String>>();
            DescartesUtil.circulate(list0, resultList);
            List<ProductCodeManager> codeList = new ArrayList<>();
            // 为codemanager添加组合属性
            if (resultList.size() > 0) {
                for (List<String> list : resultList) {
                    String str = "";
                    ProductCodeManager codeManager = new ProductCodeManager();
                    for (String string : list) {
                        str += string + "*";
                    }
                    str = str.substring(0, str.length() - 1);
                    codeManager.setProperty(str);
                    codeManager.setId(
                            Global.getID() + ""
//                            System.currentTimeMillis()+""
                    );
                    codeManager.setProductId(product.getId());
                    codeManagerList.add(codeManager);
                    codeList.add(codeManager);
                }
            }
            // 组合新的要入库的property信息
            List<ProductProperty> newList = new ArrayList<>();
            for (ProductCodeManager code : codeList) {
                String[] strs = code.getProperty().split("\\*");
                int i = 0;
                for (ProductProperty property : valueList) {
                    propertyService.deletePropertyById(property);
                    ProductProperty newProperty = new ProductProperty();
                    newProperty.setCodeManagerId(code.getId() + "");
                    newProperty.setPropertyCode(property.getPropertyCode());
                    newProperty.setPropertyName(property.getPropertyName());
                    newProperty.setProductId(property.getProductId());
                    newProperty.setId(
                            Global.getID() + ""
                    );
                    newProperty.setPropertyValue(strs[i]);
                    newList.add(newProperty);
                    i += 1;
                }
            }
            propertyService.insertList(newList);
        }
        return codeManagerList;
    }

    /**
     * 页面异步更新子代码信息，供页面展示
     *
     * @param product
     */
    @Transactional(readOnly = false)
    public void updateProductManagerCode(Product product) {
        String productId = product.getId();

        // 1.删除该产品子代码表
        productCodeManagerDao.deleteProductCodeManagersByProductId(productId);

        // 2.删除产品的采购源与代码对应关系表
        productPurchaseDao.deletePurchaseSourceCodesByProductId(productId);
        List<ProductCodeManager> productCodeManagerList = new ArrayList<ProductCodeManager>();
        productCodeManagerList = addPropertyToCodeManager(productCodeManagerList, product);
        if (productCodeManagerList.size() > 0) {
            productCodeManagerDao.insertList(productCodeManagerList);
        }
    }

    /**
     * 保存产品信息时，保存产品id与竞品id的关系
     *
     * @param toBeDevelop
     * @return
     */
    @Transactional(readOnly = false)
    public int saveProductAndColllect(ProductToBeDevelop toBeDevelop) {
        return productAndCollectionDao.insert(toBeDevelop);
    }

    /**
     * 由collectId查询productId
     *
     * @param
     * @return
     */
    public String findByCollectId(String collectId) {
        return productAndCollectionDao.findByCollectId(collectId);
    }

    /**
     * 由productId查询collectId
     *
     * @param
     * @return
     */
    public String findByProductId(String productId) {
        return productAndCollectionDao.findByProductId(productId);
    }

    /**
     * 由collectId更新t_collection_product表
     */

    @Transactional(readOnly = false)
    public int updateById(ProductToBeDevelop develop){
        return productAndCollectionDao.updateById(develop);
    }
    /**
     * 保留两位小数
     */
    public String transUtil(Double d) {
        return new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_DOWN).toString();
    }

    /**
     * 虚拟仓利润测算，所需参数整理
     *
     * @param siteId
     * @param itemId
     * @param calculateDto
     * @return
     */
    public CalculateDto virtualCal(String siteId, String itemId, CalculateDto calculateDto) {


        /** 调用ebay SDK **/
        GetEbayItemRequest request = new GetEbayItemRequest();
        logger.info("==============>itemId:{}",itemId);
        if(StringUtils.isBlank(siteId)){
            throw new RuntimeException("siteId 不能为空！请检查...");
        }
        if(StringUtils.isBlank(itemId)){
            throw new RuntimeException("itemId 不能为空！请检查...");
        }
        request.setItemId(itemId);
        Long start = System.currentTimeMillis();
        logger.info("开始请求ebay查询接口：http://192.168.2.127:8081/api/item/itemView");
        GetEbayItem getItem = new GetEbayItem(request);
        ItemViewDto msgDto = getItem.getResponse().getItem();
        logger.info("ebay查询接口请求结束，耗时："+(System.currentTimeMillis()-start)+"ms");
        // 请求正常返回时，把返回值存放在calculateDto
        if (null != msgDto) {
            // 由站点ID查询到站点实体，获取站点名称
            PlatformSite site = platformSiteDao.get(siteId);
            String siteShort = site.getSiteShortName();
            // 由站点名称查询到EbayPaypal、EbayFee实体
            Map<String,Object> map = getManyChange(new CalculateDto(msgDto.getCategoryName(),siteShort));
            EbayPaypal paypal = (EbayPaypal)map.get(Global.EBAY_PAYPAL);
            EbayFee fee = (EbayFee)map.get(Global.EBAY_FEE);

            Double maxValue = Double.valueOf(fee.getMaxValue());
            Double ebayRate = Double.valueOf(fee.getFeeRate());
            calculateDto.setEbaySellPrice(Double.valueOf(transUtil(msgDto.getCurrentPrice())));
            calculateDto.setCategoryName(msgDto.getCategoryName());
            calculateDto.setSubCategoryName(msgDto.getSubCategoryName());
            calculateDto.setSiteName(siteShort);

            // 描述和标题
            calculateDto.setTitle(msgDto.getTitle());
            //	calculateDto.setDescription(msgDto.getDescription());

            // 取 ebaySellPrice*ebayRate 与 maxValue 的最小值
            calculateDto.setEbayfee(transUtil(Math.min(msgDto.getCurrentPrice() * ebayRate, maxValue)));

            // 临界值：根据站点获取
            // ebay售价≤PayPal临界值：ebay售价*Smallpaypal_discount+Smallpaypal_each_cost
            // ebay售价≥PayPal临界值：ebay售价*paypal_discount+paypal_each_cost"
            Double paypalfee = msgDto.getCurrentPrice() > paypal.getBoundaries() ? (msgDto.getCurrentPrice() * paypal.getPaypalDiscount() + paypal.getPaypalEachCost()) : (paypal.getSmallpaypalDiscount() * msgDto.getCurrentPrice() + paypal.getSmallpaypalEachCost());
            calculateDto.setPaypalfee(transUtil(paypalfee));

            // 邮费需要换算成人民币显示在页面
            calculateDto.setPostage(Double.valueOf(transUtil(msgDto.getPostage())));

            //固定不变的形式
            calculateDto.setFinancefee(Double.valueOf(transUtil(msgDto.getCurrentPrice() * FINANCE_RATE)));
            calculateDto.setLossfee(Double.valueOf(transUtil(msgDto.getCurrentPrice() * LOSS_RATE)));
            calculateDto.setPackagefee(Double.valueOf(transUtil(PACKAGE_FEE)));
            calculateDto.setDealfee(Double.valueOf(transUtil(DEAL_FEE)));

            calculateDto.setExch_rate(site.getExchangeRate());
        } else {
            logger.error("ebay接口请求返回对象为null,请处理！");
            throw new RuntimeException("ebay接口请求返回对象为null,请处理！");
        }
        return calculateDto;
    }

    /**
     *
     * @param calculateDto
     * @return
     */
    public Map<String,Object> getManyChange(CalculateDto calculateDto){
        // 由站点名称查询到EbayPaypal、EbayFee实体
        EbayPaypal paypal = ebayPaypalService.getBySite(new EbayPaypal(calculateDto.getSiteName()));
        EbayFee fee = ebayFeeService.getBySiteAndCategory(new EbayFee(calculateDto.getCategoryName(),calculateDto.getSiteName()));
        if(paypal==null){
            throw new RuntimeException("此站点："+calculateDto.getSiteName()+"暂未维护ebay Paypal Fee，请联系管理员！");
        }
        if(fee==null){
            throw new RuntimeException("此站点："+calculateDto.getSiteName()+"，此分类："+calculateDto.getCategoryName()+"下暂未维护ebay Fee，请联系管理员！");
        }
        Map<String,Object> map = new HashMap<>();
        map.put(Global.EBAY_PAYPAL,paypal);
        map.put(Global.EBAY_FEE,fee);
        return map;
    }
    public Product getProductDetail(String productId) {
        List<Tags> selectedTags = productTagsDao
                .findTagsListByProductIdAndTypeFlag(productId,
                        Global.getConfig("product_tag_ids"));
        List<Tags> selectedSpeTags = productTagsDao
                .findTagsListByProductIdAndTypeFlag(productId,
                        Global.getConfig("product_spe_tag_ids"));
        Product product = productDao.get(productId);
        product.setSelectedTags(selectedTags);
        product.setSelectedSpeTags(selectedSpeTags);
        return product;
    }

    public boolean temporaryQuit(String itemId, CalculateDto calculateDto) {
        boolean isOrNo = false;
        GetEbayItemRequest request = new GetEbayItemRequest();
        request.setItemId(itemId);
        GetEbayItem getItem = new GetEbayItem(request);
        ItemViewDto msgDto = getItem.getResponse().getItem();
        StringBuilder builder = new StringBuilder(Global.VIRTUAL_BASE_CALCULATE_URL);
        // 请求参数拼接
        builder.append("?ebaySellPrice=" + msgDto.getCurrentPrice());
        builder.append("&commodityCost=" + calculateDto.getCommodityCost());
        builder.append("&postage=" + msgDto.getPostage());
        builder.append("&paypalfee=" + calculateDto.getPaypalfee());
        builder.append("&ebayfee=" + calculateDto.getEbayfee());
        builder.append("&financefee=" + calculateDto.getFinancefee());
        builder.append("&lossfee=" + calculateDto.getLossfee());
        builder.append("&packagefee=" + calculateDto.getPackagefee());
        builder.append("&dealfee=" + calculateDto.getDealfee());
        builder.append("&exch_rate=" + calculateDto.getExch_rate());
        String returnData = HttpHelper.get(builder.toString());
        JSONObject json = (JSONObject) JSONObject.parse(returnData);
        Double grossProfitRate = json.getDouble("grossProfitRate");
        Integer quantitySold = json.getInteger("quantitySold");
        if (grossProfitRate > Global.QUIT_CRUCIAL_RATE && quantitySold>Global.QUANTITY_SOLD) {
            isOrNo = true;
        }
        return isOrNo;
    }

    public List<PurchaseConfigInfo> trans2List(String productId){
        List<PurchaseConfigInfo> purchaseConfigInfoList = getPurchaseConfigInfoList(productId);
        ProductToBeDevelop develop = productToBeDao.get(findByProductId(productId));
        if(develop!=null){
            if(purchaseConfigInfoList.size()<1){
                PurchaseConfigInfo purchaseConfigInfo = new PurchaseConfigInfo();
                purchaseConfigInfo.setCodeManagerId(
                        Global.getID()+""
                );
                purchaseConfigInfo.setProperty("单品");
                /**铺货形式没有采购数量，避免强转错误*/
                if(develop.getProductNumber()==null || develop.getProductNumber()==""){
                    purchaseConfigInfo.setQuantity(0);
                }else{
                    purchaseConfigInfo.setQuantity(Integer.parseInt(develop.getProductNumber()));
                }
                purchaseConfigInfoList.add(purchaseConfigInfo);
            }else{
                for (PurchaseConfigInfo p:purchaseConfigInfoList) {
                    if(null == p.getQuantity()||p.getQuantity()==0){
//                        if(develop.getProductNumber()==null || develop.getProductNumber()==""){
                            p.setQuantity(0);
//                        }else{
//                            p.setQuantity(Integer.parseInt(develop.getProductNumber()));
//                        }
                    }
                }
            }
        }
        return purchaseConfigInfoList;
    }


    /**
     * 添加线下平台的数据（不是所有字段都添加数据）
     */
    @Transactional(readOnly = false)
    public void addExcelData(Product product){
        productDao.addExcelData(product);
    }

    @Transactional(readOnly = false)
    public void addProduct(Product product){
        productDao.addProduct(product);
    }

    /**
     * 添加.xls类型文件的数据
     * @param file
     */
    @Transactional(readOnly = false)
    public void insertXlsData(String file) throws Exception {
        ExcelUtil eu = new ExcelUtil(file);
        //要处理的列
        int[] needSheets = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21,
                22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55,
                56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92};
        //工作表(从0开始)
        int sheet = 0;
        List<String[]> listStr = eu.readExcel(sheet, needSheets);
        if (listStr.size() > 0) {
            for (int i = 0; i < listStr.size(); i++) {
                String[] strs = listStr.get(i);
                if (i > 0) {
                    if (strs.length == needSheets.length) {
                        String name = strs[1];                                              //产品名称
                        String sysParentCode = strs[2];                                     //母SKU
                        String procurementChannel = strs[7];                                //采购渠道
                        String cnName = strs[8];                                            //中文报关名
                        String enName = strs[9];                                            //英文报关名
                        String tags = strs[11];                                             //产品标签
                        String productType = strs[12];                                      //型号
                        String specification = strs[10];                                    //规格
                        String texture = strs[13];                                          //材质
                        String costPrice = strs[14].equals("") ? "0" : strs[14];            //成本价
                        String defaultWeight =  strs[15].equals("") ? "0" : strs[15];       //重量
                        String declaredValue = strs[16].equals("") ? "0" : strs[16];        //申报价格
                        String supplier = strs[17];                                         //供应商
                        String supplierUrl = strs[19];                                      //供应商连接
                        Product product = new Product(String.valueOf(Global.getID()), sysParentCode, productType, null, cnName, enName,
                                Double.valueOf(declaredValue), name, procurementChannel, tags, specification, Double.valueOf(defaultWeight), Double.valueOf(costPrice));
                        Product product1 = new Product();
                        product1.setSysParentCode(product.getSysParentCode());
                        addProduct(product, null, supplier, supplierUrl);

                        setParams(listStr, Global.INIT_ID, null, product.getId(), Global.PUYUAN_TEMPLET, null,supplier, supplierUrl);
                    }
                }
            }
        }
    }
    /**
     * 添加.xlsx文件的数据
     * @param file
     */
    @Transactional(readOnly = false)
    public void insertXlsxData(String file) throws Exception{
        ExcelUtil excelUtil = new ExcelUtil();
        List<String[]> listStr = excelUtil.readXlsx(file);
        if (listStr.size() > 0) {
            for (int i = 0; i < listStr.size(); i++) {
                String[] strs = listStr.get(i);
                if (strs != null) {
                    if (!("完成日期").equals(strs[0])) {
                        if (i > 0) {
                            Product product = null;
                            String sysParentCode = strs[3];                                 //SKU
                            String title = strs[7];                                         //标题
                            String wishSizeName = strs[11];                                 //wish尺寸
                            String folderPath = strs[17];                                   //图片路径
                            String description = strs[20];                                  //描述
                            String name  = strs[22];                                        //商品名称
                            String createTime = listStr.get(i)[0];                          //完成时间
                            String parentSKU = listStr.get(i)[3];                           //父级SKU

                            String firstdata = listStr.get(i)[0];                           //第一个数据
                            String productId = listStr.get(i)[4];                           //保存的ProductId
                            if (StringUtils.isNoneEmpty(firstdata)) {
                                product = new Product(String.valueOf(Global.getID()), name, title, sysParentCode, wishSizeName, description);
                            }
                            if (i == 1) {
                                designDrawingService.ImportImage(folderPath, product.getId());
                                List<TProductImgPlatform> tProductImgPlatformList = tProductImgPlatformService.findByTProductImgPlatform(product.getId(), Global.IMG_TYPE_MAIN);
                                if(tProductImgPlatformList.size() > 0){
                                    product.setImgUrl(tProductImgPlatformList.get(0).getImgUrl());
                                }
                                addProduct(product, null, null,null);
                                listStr.get(i)[4] = product.getId();
                                setParams(listStr, Global.INIT_ID, product.getSysParentCode(), product.getId(), Global.ZHANGHU_TEMPLET,wishSizeName,null,null);
                                continue;
                            } else if (StringUtils.isNoneEmpty(createTime) && StringUtils.isNoneEmpty(parentSKU)) {
                                designDrawingService.ImportImage(folderPath, product.getId());
                                List<TProductImgPlatform> tProductImgPlatformList = tProductImgPlatformService.findByTProductImgPlatform(product.getId(), Global.IMG_TYPE_MAIN);
                                if (tProductImgPlatformList.size() > 0) {
                                    product.setImgUrl(tProductImgPlatformList.get(0).getImgUrl());
                                }
                                addProduct(product, null, null,null);
                                listStr.get(i)[4] = product.getId();
                                setParams(listStr, i, product.getSysParentCode(), product.getId(), Global.ZHANGHU_TEMPLET,wishSizeName,null,null);
                            } else if (("").equals(createTime) && StringUtils.isNoneEmpty(parentSKU)) {
                                listStr.get(i)[4] = listStr.get(i - 1)[4];
                                setParams(listStr, i, get(listStr.get(i - 1)[4]).getSysParentCode(), listStr.get(i)[4], Global.ZHANGHU_TEMPLET, wishSizeName,null,null);
                            } else {
                                continue;
                            }
                        }
                    }
                }
            }
        }
}


    /**
     *
     * @param product  封装的product信息
     * @param tag      产品对应的tag标签
     * @param supplier 产品对应的供应商
     *
     */
    @Transactional(readOnly = false)
    public void addProduct(Product product, String tag, String supplier, String supplierUrl) throws Exception {
        product.setIdentifying(Global.TRANSFER_DATA);
        product.setType(Global.PRODUCT_TYPE_DRAFT);
        product.setCreateTime(new Date());
        product.setCreateId(UserUtils.getPrincipal().getId().toString());
        product.setMaxCode(Global.MAX_CODE);
        Product product1 = new Product();
        product1.setSysParentCode(product.getSysParentCode());
        if (findProductByEntity(product1).size() == 0) {
            if (tag == null && supplier == null) {
                addProduct(product);
                addProductQueue(product);
            } else {
                addExcelData(product);
                addProductQueue(product);
                if (StringUtils.isNoneEmpty(supplier)) {
                    supplierService.addPurchaseSource(product.getId(), supplier, supplierUrl, null);
                }
            }
        } else {
            List<Product> productResultList = findProductByEntity(product1);
            for(int j = 0;j<productResultList.size();j++){
                product.setId(productResultList.get(j).getId());
                product.setName(null);
                updateProduct(product);
            }
            Product productSupplier = new Product();
            productSupplier.setSysParentCode(product.getSysParentCode());
            List<Product> productList = findList(productSupplier);
            if(productList.size()>0)
                for(int u=0 ;u<productList.size();u++){
                    supplierService.addPurchaseSource(productList.get(u).getId(), supplier, supplierUrl, null);
                }
        }
    }
    /**
     * 处理productCodeManager
     * @param listStr  产品list
     * @param SysParentCode 母代码
     * @param  productId 产品Id
     * @param type      模板类型
     */
    @Transactional(readOnly = false)
    public void setParams(List<String[]> listStr,Integer i,String SysParentCode,String productId,String type,String wishSize,String supplier, String supplierUrl) throws Exception{
        //项目组模板数据导入格式
        String ParentCode = listStr.get(1)[3];
        ProductCodeManager productCodeManager = new ProductCodeManager();
        if ((Global.ZHANGHU_TEMPLET).equals(type)) {
            String sysParentSku = listStr.get(i - 1)[3];
            String sysSku = listStr.get(i)[3];
            String imgUrl = listStr.get(i)[17];

            if (i == 1) {
                productCodeManager.setSysParentSku(ParentCode);
                productCodeManager.setSysSku(ParentCode);
            } else {
                if (StringUtils.isNoneEmpty(SysParentCode)) {
                    productCodeManager.setSysParentSku(SysParentCode);
                } else {
                    productCodeManager.setSysParentSku(sysParentSku);
                }
                productCodeManager.setSysSku(sysSku);
            }

            productCodeManager.setProductId(productId);
            productCodeManager.setId(String.valueOf(Global.getID()));
            productCodeManager.setCreateTime(new Date());
            /*productCodeManager.setImgUrl(imgUrl);*/
            productCodeManager.setWishSize(wishSize);
            List<ProductCodeManager> productCodeManagerResult = productCodeManagerService.getProductCodeManagerByCode(productCodeManager.getSysSku());
            addOrUpdateProductCodeManage(productCodeManagerResult,productCodeManager,supplier,supplierUrl,productId);
        } else {  //普元线下数据
            String parentCode = listStr.get(1)[2];
            String parentSku = listStr.get(i)[2];
            String SysSku = listStr.get(i)[3];
            String cnName = listStr.get(i)[8];
            String enName = listStr.get(i)[9];

            if (i == 1) {
                productCodeManager.setSysParentSku(parentCode);
                productCodeManager.setSysSku(ParentCode);
            } else {
                if (SysParentCode != null) {
                    productCodeManager.setSysParentSku(SysParentCode);
                } else {
                    productCodeManager.setSysParentSku(parentSku);
                }
                productCodeManager.setSysSku(SysSku);
            }

            productCodeManager.setProductId(productId);
            productCodeManager.setId(String.valueOf(Global.getID()));
            productCodeManager.setCnName(cnName);
            productCodeManager.setEnName(enName);
            productCodeManager.setCreateTime(new Date());
            List<ProductCodeManager> productCodeManagerList = productCodeManagerService.getProductCodeManagerByCode(productCodeManager.getSysSku());
            addOrUpdateProductCodeManage(productCodeManagerList,productCodeManager,supplier,supplierUrl,productId);
        }
    }

    public void addProductQueue(Product product) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ProductQueue productQueue = new ProductQueue();
        productQueue.setId(Global.getID().toString());
        productQueue.setProductId(Integer.valueOf(product.getId()));
        productQueue.setProductFrom(Global.TERRACE_TYPE_EBAY);
        productQueue.setProductName(product.getName());
        productQueue.setImgUrl(product.getImgUrl());
        productQueue.setCreateTime(sdf.format(new Date()));
        productQueue.setStatus(Global.PRODUCT_QUEUE_NO);
        messageProducer.sendMessage(productQueue);
    }

    @Transactional(readOnly = false)
    public void addOrUpdateProductCodeManage(List<ProductCodeManager> productCodeManagerList,ProductCodeManager productCodeManager,String supplier,String supplierUrl,String productId) throws Exception{
        if (productCodeManagerList.size()>0) {
            for(int j=0;j<productCodeManagerList.size();j++){
                productCodeManager.setId(productCodeManagerList.get(j).getId());
                productCodeManagerService.UpdateBycode(productCodeManagerList.get(j));
            }
        } else {
            productCodeManager.setIsNewRecord(true);
            productCodeManagerService.save(productCodeManager);
            if(StringUtils.isNoneEmpty(supplierUrl) && StringUtils.isNoneEmpty(supplier)){
                List<PurchaseSourceCode> list = new ArrayList<>();
                PurchaseSourceCode purchaseSourceCode = new PurchaseSourceCode();
                purchaseSourceCode.setProductId(productCodeManager.getProductId());
                purchaseSourceCode.setCodeId(productCodeManager.getId());
                Map<String,String> map = new HashMap<>();
                map.put("productId",productId);
                map.put("supplierName",supplier);
                map.put("sourceUrl",supplierUrl);
                List<Supplier> purchaseSourceCodeList = supplierDao.getSupplierByMap(map);
                purchaseSourceCode.setSourceId(purchaseSourceCodeList.get(0).getSupplierId());
                purchaseSourceCode.setSourceCodeId(String.valueOf(Global.getID()));
                list.add(purchaseSourceCode);
                supplierDao.insertPurchaseSourceCodeList(list);
            }
        }
    }

    public List<Product> findProductByEntity(Product product){
       return productDao.findAllList(product);
    }
    
    public void updateFinishedProductQuantity(String productId, String finishedProductQuantity){
        productDao.updateFinishedProductQuantity(productId, finishedProductQuantity);
     }
    
    
    @Transactional(readOnly = false)
    public void updateProductNum(Product product){
      ProductCodeManager codeManager = null;
      // 采购源配置信息
      for (PurchaseConfigInfo info:product.getPurchaseConfigInfoList()) {
          codeManager = new ProductCodeManager();
          codeManager.setProductId(product.getId());
          codeManager.setId(info.getCodeManagerId());
          codeManager.setRecommendNumber(info.getQuantity());
          productCodeManagerDao.updateNumBycodeManagerId(codeManager);
      }
  }
    
}