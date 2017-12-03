package com.oigbuy.jeesite.modules.ebay.productDevelop.service;

import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.modules.ebay.product.dao.ProductDao;
import com.oigbuy.jeesite.modules.ebay.product.entity.Product;
import com.oigbuy.jeesite.modules.ebay.productDevelop.dao.ProductAndCollectionDao;
import com.oigbuy.jeesite.modules.ebay.productDevelop.dao.ProductDevelopedDao;
import com.oigbuy.jeesite.modules.ebay.productDevelop.dao.ProductTagsDao;
import com.oigbuy.jeesite.modules.ebay.purchase.dao.SupplierDao;
import com.oigbuy.jeesite.modules.ebay.purchase.dto.PurchaseSource;
import com.oigbuy.jeesite.modules.ebay.tags.entity.Tags;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 产品开发Service
 * @author strugkail.li
 * @version 2017-09-07
 */
@Service
@Transactional(readOnly = true)
public class ProductDevelopedService extends CrudService<ProductDevelopedDao,Product> {
    @Autowired
    private ProductTagsDao productTagsDao;
    @Autowired
    private SupplierDao supplierDao;
    @Autowired
    private ProductAndCollectionDao productAndCollectionDao;
    @Autowired
    private ProductDao productDao;
    
    
    public Product get(String id) {
        return super.get(id);
    }

    public List<Product> findList(Product product) {

        return super.findList(product);
    }

    public Page<Product> findPage(Page<Product> page, Product product) {
        return super.findPage(page, product);
    }
    public Product getProductDetail(Product product){
        String productId = product.getId();
        List<Tags> selectedTags = productTagsDao
                .findTagsListByProductIdAndTypeFlag(productId,
                        Global.getConfig("product_tag_ids"));
        List<Tags> selectedSpeTags = productTagsDao
                .findTagsListByProductIdAndTypeFlag(productId,
                        Global.getConfig("product_spe_tag_ids"));
        // 采购源列表(供应商)
        List<PurchaseSource> purchaseSourceList = supplierDao.getAllPurchaseSourceByProductId(productId);
        product.setPurchaseSourceList(purchaseSourceList);
        product.setSelectedTags(selectedTags);
        product.setSelectedSpeTags(selectedSpeTags);
        return product;
    };
    
    @Transactional(readOnly = false)
    public void toCalculate(String collectId){
    	String productId = productAndCollectionDao.findByCollectId(collectId);
    	if (StringUtils.isNotEmpty(productId)) {
    		//删除产品和竞品关系表数据
    		productAndCollectionDao.deleteByProductId(productId);
    		//删除产品表中的空数据
			productDao.delete(productDao.get(productId));
		}
    }
}
