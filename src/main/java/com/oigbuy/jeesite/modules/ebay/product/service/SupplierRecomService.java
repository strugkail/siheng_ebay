package com.oigbuy.jeesite.modules.ebay.product.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.modules.ebay.product.dao.ProductImgDao;
import com.oigbuy.jeesite.modules.ebay.product.dao.SupplierRecomDao;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductImg;
import com.oigbuy.jeesite.modules.ebay.product.entity.SupplierRecommen;
import com.oigbuy.jeesite.common.fastdfs.util.FastdfsHelper;

@Service
@Transactional(readOnly = true)
public class SupplierRecomService extends CrudService<SupplierRecomDao, SupplierRecommen> {
	

	@Autowired
	private ProductImgDao productImgDao;
	
	@Autowired
	private SupplierRecomDao supplierRecomDao;
	
	public SupplierRecommen get(String id) {
		return super.get(id);
	}

	public List<SupplierRecommen> findList(SupplierRecommen supplier) {
		return super.findList(supplier);
	}

	public Page<SupplierRecommen> findPage(Page<SupplierRecommen> page, SupplierRecommen supplier) {
		return super.findPage(page, supplier);
	}
	
	
	@Transactional(readOnly = false)
	public void update(SupplierRecommen supplier){
		supplierRecomDao.update(supplier);
	}
	
	@Transactional(readOnly = false)
	public void save(SupplierRecommen supplier){
		supplierRecomDao.insert(supplier);
	}
	
	@Transactional(readOnly = false)
	public synchronized String insertProductImg1(String url, String type, String productId) {
		ProductImg productImg = new ProductImg();
		productImg.setId(Global.getID().toString());
		productImg.setImgType(type);
		productImg.setImgUrl(url);
		productImg.setProductId(productId);
		
		int num = productImgDao.findcount(productId, type);
		productImg.setIndexSeq(String.valueOf(num));
		productImgDao.insert(productImg);
		return productImg.getId();
	}
	
	@Transactional(readOnly = false)
	public synchronized String insertProductImg(String url, String type, String productId) {
		ProductImg productImg = new ProductImg();
		productImg.setId(Global.getID().toString());
		productImg.setImgType(type);
		productImg.setImgUrl(url);
		productImg.setProductId(productId);
		int num = productImgDao.findcount(productId, type);
		productImg.setIndexSeq(String.valueOf(num));
		// 每上传一张图片，product表 maxCode + 1
		productImgDao.updateMaxCode(productId);
		productImgDao.insert(productImg);
		return productImg.getId();
	}

	@Transactional(readOnly = false)
	public void deleteImg(ProductImg productImg) {
		productImg = productImgDao.get(productImg);
		FastdfsHelper.delFile(productImg.getImgUrl());
		productImgDao.delete(productImg);

		List<ProductImg> list = productImgDao
				.findListByProductIdAndImgType(productImg.getProductId(),
						productImg.getImgType(), Global.ZERO);
		for (int i = 0; i < list.size(); i++) {
			String id = list.get(i).getId();
			productImgDao.updateindexSeq(id, String.valueOf(i));
		}
	}

	public Map<String, Object> getProductDetails(SupplierRecommen supplier) {
		String productId = supplier.getId();
		List<ProductImg> listForMain = productImgDao
				.findListByProductIdAndImgType(productId, "1", Global.ZERO); // 主图
		List<Map<String, String>> mainlist = new ArrayList<Map<String, String>>();
		Map<String, Object> mainMap = new HashMap<String, Object>();
		for (ProductImg imgs : listForMain) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("url", FastdfsHelper.getImageUrl()
					+ (imgs.getImgUrl() == null ? "" : imgs.getImgUrl()));
			map.put("id", imgs.getId());
			mainlist.add(map);
		}
		mainMap.put("mainlist", mainlist);
		return mainMap;
	}
	
	
	/***
	 *
	 * 销售部门下 供应商推荐产品  分页列表
	 * 
	 * @param page
	 * @param supplier
	 * @return
	 */
	public Page<SupplierRecommen> findPage2(Page<SupplierRecommen> page,
			SupplierRecommen supplier) {
		supplier.setPage(page);
		page.setList(supplierRecomDao.findList2(supplier));
		return page;
	}
	
}
