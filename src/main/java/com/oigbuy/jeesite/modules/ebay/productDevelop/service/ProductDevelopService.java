package com.oigbuy.jeesite.modules.ebay.productDevelop.service;

import com.alibaba.fastjson.JSONObject;
import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.common.utils.DescartesUtil;
import com.oigbuy.jeesite.common.utils.HttpHelper;
import com.oigbuy.jeesite.modules.ebay.ebayPaypal.dao.EbayPaypalDao;
import com.oigbuy.jeesite.modules.ebay.ebayPaypal.entity.EbayPaypal;
import com.oigbuy.jeesite.modules.ebay.ebayfee.dao.EbayFeeDao;
import com.oigbuy.jeesite.modules.ebay.ebayfee.entity.EbayFee;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.dao.PlatformSiteDao;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.KeyWord;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.PlatformSite;
import com.oigbuy.jeesite.modules.ebay.product.dao.ProductCodeManagerDao;
import com.oigbuy.jeesite.modules.ebay.product.entity.Product;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductCodeManager;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductProperty;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductPropertyValue;
import com.oigbuy.jeesite.modules.ebay.productDevelop.dao.ProductAndCollectionDao;
import com.oigbuy.jeesite.modules.ebay.productDevelop.dao.ProductDevelopDao;
import com.oigbuy.jeesite.modules.ebay.productDevelop.dao.ProductDevelopedDao;
import com.oigbuy.jeesite.modules.ebay.productDevelop.dao.ProductToBeDevelopDao;
import com.oigbuy.jeesite.modules.ebay.productDevelop.dto.CalculateDto;
import com.oigbuy.jeesite.modules.ebay.productDevelop.dto.ReturnData;
import com.oigbuy.jeesite.modules.ebay.productDevelop.dto.ReturnMsgDto;
import com.oigbuy.jeesite.modules.ebay.productDevelop.entity.ProductToBeDevelop;
import com.oigbuy.jeesite.modules.ebay.purchase.dao.ProductPurchaseDao;
import com.oigbuy.jeesite.modules.ebay.purchase.dao.SupplierDao;
import com.oigbuy.jeesite.modules.ebay.purchase.dto.PurchaseConfigInfo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
/**
 * 产品开发Service
 * @author strugkail.li
 * @version 2017-09-07
 */
@Service
@Transactional(readOnly = true)
public class ProductDevelopService extends CrudService<ProductDevelopedDao,Product> {
    @Autowired
    private ProductDevelopDao developDao;
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

//    /**
//     * 获取子代码信息列表
//     * @param productId
//     * @return
//     */
//    public List<PurchaseConfigInfo> getPurchaseConfigInfoList(String productId){
//    return productDevelopDao.getPurchaseConfigInfoListByProductId(productId);
//    }
//
//    public List<ProductProperty> findPropertyListById(Product product){
//        List<ProductProperty> list = productDevelopDao.findPropertyListById(product);
//        for (ProductProperty property:list) {
//            String str1 = "";
//            List<ProductProperty> valueList = productDevelopDao.findPropertyValueListById(property);
//            for (ProductProperty value:valueList) {
//                str1 +=value.getPropertyValues()+"\r\n";
//            }
//            property.setPropertyValues(str1);
//        }
//        return list;
//    }
//    @Transactional(readOnly = false)
//    public int addProperty(ProductProperty property){
//       int m = productDevelopDao.addProperty(property);
//        String[] strs = property.getPropertyValues().split("\r\n");
//        for(int i=0;i<strs.length;i++){
//            ProductPropertyValue value = new ProductPropertyValue();
//            value.setPropertyId(property.getPropertyId());
//            value.setPropertyValue(strs[i]);
//            productDevelopDao.addPropertyValues(value);
//        }
//        return m;
//    }
//    @Transactional(readOnly = false)
//    public int deletePropertyById(ProductProperty property){
//        int m = productDevelopDao.deletePropertyById(property);
//        ProductPropertyValue value = new ProductPropertyValue();
//        value.setPropertyId(property.getPropertyId());
//        productDevelopDao.deletePropertyValueById(value);
//        return m;
//    }
//    // 查询到产品属性，添加到codemanager列表
//    public List<ProductCodeManager> addPropertyToCodeManager(List<ProductCodeManager> codeManagerList,Product product){
//        List<ProductProperty> valueList = findPropertyListById(product);
//        if(valueList.size()!=0){
//            List<List<String>> list0 = new ArrayList<List<String>>();
//            for (ProductProperty property:valueList) {
//                String[] values = property.getPropertyValues().split("\n");
//                List<String> list1 = new ArrayList<>();
//                for(int i=0;i<values.length;i++){
//                    list1.add(values[i]);
//                }
//                list0.add(list1);
//            }
//            List<List<String>> resultList = new ArrayList<List<String>>();
//            DescartesUtil.circulate(list0,resultList);
//            for (List<String> list : resultList) {
//                String str = "";
//                ProductCodeManager codeManager = new ProductCodeManager();
//                for (String string : list) {
//                    str += string + "*";
//                }
//                str = str.substring(0,str.length()-1);
//                codeManager.setProperty(str);
//                codeManager.setId(Global.getID()+"");
//                codeManager.setProductId(product.getId());
//                codeManagerList.add(codeManager);
//            }
//     }
//        return codeManagerList;
//    }
//    @Transactional(readOnly = false)
//    public void updateProductManagerCode(Product product) {
//        String productId = product.getId();
//        List<ProductProperty> propertyList = findPropertyListById(product);
//
//        // 1.删除该产品子代码表
//        productCodeManagerDao.deleteProductCodeManagersByProductId(productId);
//        // 2.删除产品的采购源与代码对应关系表
//        productPurchaseDao.deletePurchaseSourceCodesByProductId(productId);
//
//        // 3.保存产品子代码信息
//        // 获取采购源配置信息
//        List<PurchaseConfigInfo> purchaseConfigInfoList = product
//                .getPurchaseConfigInfoList();
//            List<ProductCodeManager> productCodeManagerList = new ArrayList<ProductCodeManager>();
//                productCodeManagerDao.insertList(addPropertyToCodeManager(productCodeManagerList,product));
//            }
////            List<PurchaseSourceCode> purchaseSourceCodeList = new ArrayList<PurchaseSourceCode>();
////			for (PurchaseConfigInfo p : purchaseConfigInfoList) {
//
//
////				if (p.getProfitRate() != null) {
////					String wishSize = p.getWishSize();
////					if (wishSize != null){
////						wishSize = com.sho.tool.lang.StringUtils.wishSizeTrim(wishSize);
////					}
////					ProductCodeManager pcm = new ProductCodeManager(Global
////							.getID().toString(), productId,
////							product.getCnName(), product.getEnName(), null,
////							null, new Date(), p.getSize(), p.getColor(),
////							wishSize, p.getWishColor(),
////							p.getPublishPrice(), p.getPublishTransPrice(),
////							p.getCostPrice(), p.getWeight(), p.getProfitRate(),
////							p.getQuantity());
////					PurchaseSourceCode psc = new PurchaseSourceCode(Global
////							.getID().toString(), pcm.getId(), p.getSourceId(),
////							productId);
////					productCodeManagerList.add(pcm);
////					purchaseSourceCodeList.add(psc);
////				}
////			}
////            supplierDao.insertPurchaseSourceCodeList(purchaseSourceCodeList);
//
//        public int saveProductAndColllect(ProductToBeDevelop toBeDevelop){
//           return productAndCollectionDao.insert(toBeDevelop);
//        }
//
//    /**
//     * 根据售价计算申报价格，单位美元$，如果售价单位不统一，要先根据汇率计算出美元价格，再使用此方法计算
//     * @param sellPrice 售价
//     * @return 申报价格
//     */
//    public double calDeclaredValue(double sellPrice){
//        double declaredValue = 0;
//
//        if(sellPrice >= 1.0 && sellPrice < 4.0){
//            declaredValue = 1;
//        }else if(sellPrice >= 4.0 && sellPrice < 7.0){
//            declaredValue = 2;
//        }else if(sellPrice >= 7.0 && sellPrice < 10.0){
//            declaredValue = 4;
//        }else if(sellPrice >= 10.0 && sellPrice < 20.0){
//            declaredValue = 6;
//        }else if(sellPrice >= 20.0){
//            declaredValue = 8;
//        }
//
//        return declaredValue;
//    }
//
//    /**
//     * 虚拟仓利润测算，所需参数整理
//     * @param siteId
//     * @param itemId
//     * @param calculateDto
//     * @return
//     */
//    public CalculateDto virtualCal(String siteId,String itemId,CalculateDto calculateDto){
//
//        // ebay接口url
//        String url = "http://192.168.0.24:8081/api/item/itemView?itemId="+itemId;
//        String returnData = HttpHelper.get(url);
//        ReturnData returnDto = null;
//        if(StringUtils.isNotBlank(returnData)){
//
//            // 请求ebay查询接口，并把返回结果转化为对象
//            returnDto = JSONObject.toJavaObject(JSONObject.parseObject(returnData),ReturnData.class);
//
//            // 请求正常返回时，把返回值存放在calculateDto
//            if(returnDto.isFlag()){
//                ReturnMsgDto msgDto = returnDto.getResult();
//
//                // 由站点ID查询到站点实体，获取站点名称
//                PlatformSite site = platformSiteDao.get(siteId);
//                String siteShort = site.getSiteShortName();
//
//                // 由站点名称查询到EbayPaypal、EbayFee实体
//                EbayPaypal paypal = ebayPaypalDao.getBySite(new EbayPaypal(siteShort));
//                EbayFee fee=ebayFeeDao.getBySite(new EbayFee(siteShort));
//                Double maxValue = Double.valueOf(fee.getMaxValue());
//                Double ebayRate = Double.valueOf(fee.getFeeRate());
//                calculateDto.setEbaySellPrice(msgDto.getCurrentPrice());
//                calculateDto.setCategoryName(msgDto.getCategoryName());
//                calculateDto.setSubCategoryName(msgDto.getSubCategoryName());
//                calculateDto.setSiteName(siteShort);
//
//                // 取 ebaySellPrice*ebayRate 与 maxValue 的最小值
//                calculateDto.setEbayfee(Math.min(msgDto.getCurrentPrice()*ebayRate,maxValue));
//
//                // 临界值：根据站点获取
//                // ebay售价≤PayPal临界值：ebay售价*Smallpaypal_discount+Smallpaypal_each_cost
//                // ebay售价≥PayPal临界值：ebay售价*paypal_discount+paypal_each_cost"
//                Double paypalfee = msgDto.getCurrentPrice()>paypal.getBoundaries()?(msgDto.getCurrentPrice()*paypal.getPaypalDiscount()+paypal.getPaypalEachCost()):(paypal.getSmallpaypalDiscount()*msgDto.getCurrentPrice()+paypal.getSmallpaypalEachCost());
//                calculateDto.setPaypalfee(paypalfee);
//
//                // 邮费需要换算成人民币显示在页面
//                calculateDto.setPostage(msgDto.getPostage());
//
//                //固定不变的形式
//                calculateDto.setFinancefee(msgDto.getCurrentPrice()*0.025);
//                calculateDto.setLossfee(msgDto.getCurrentPrice()*0.03);
//
//                // 汇率怎么获取不确定
//                calculateDto.setExch_rate(8.8441);
//            }else{
//             // 请求异常时，打印日志
//
//            }
//        }
//    return calculateDto;
//    }
//@Transactional(readOnly = false)
//public void insertKeyWord(List<KeyWord> insertKeyWord){
//    developDao.insertKeyWord(insertKeyWord);
//}
}
