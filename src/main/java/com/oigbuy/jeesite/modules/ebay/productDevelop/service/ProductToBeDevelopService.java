package com.oigbuy.jeesite.modules.ebay.productDevelop.service;

import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.modules.ebay.productDevelop.dao.ProductToBeDevelopDao;
import com.oigbuy.jeesite.modules.ebay.productDevelop.entity.ProductToBeDevelop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 产品开发Service
 * @author strugkail.li
 * @version 2017-09-04
 */
@Service
@Transactional(readOnly = true)
public class ProductToBeDevelopService extends CrudService<ProductToBeDevelopDao,ProductToBeDevelop> {
    @Autowired
    private ProductToBeDevelopDao productToBeDao;
    public ProductToBeDevelop get(String collectId) {
        return productToBeDao.get(collectId);
    }

    public List<ProductToBeDevelop> findList(ProductToBeDevelop productToBeDevelop) {
        return super.findList(productToBeDevelop);
    }

    public Page<ProductToBeDevelop> findPage(Page<ProductToBeDevelop> page, ProductToBeDevelop productToBeDevelop) {
        return super.findPage(page, productToBeDevelop);
    }
    @Transactional(readOnly = false)
    public int update(ProductToBeDevelop collect){
        return productToBeDao.update(collect);
    }
}

