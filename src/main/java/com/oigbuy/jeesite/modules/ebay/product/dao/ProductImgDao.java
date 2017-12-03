/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.product.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductImg;

/**
 * 产品图片DAO接口
 * @author mashuai
 * @version 2017-05-23
 */
@MyBatisDao
public interface ProductImgDao extends CrudDao<ProductImg> {

	List<ProductImg> findListByIdList(@Param("imgList") List<String> imgList);

	/**
	 * 获取图片by productId and imgType
	 * @param productId
	 * @param imgType 1：主图，2:细节图，4：拼图
	 * @return 
	 */
	List<ProductImg> findListByProductIdAndImgType(@Param("productId") String productId, @Param("imgType") String imgType, @Param("templateIden") String templateIden);
	
	List<String> findUrlListByProductId(@Param("id") String productId, @Param("imgType") String imgType);
	List<ProductImg> queryProductDetailImages(@Param("productId") String productId);

	Integer findMaxCodeByProductId(@Param("productId") String productId);
	
	void updateMaxCode(@Param("productId") String productId);
	
	void insertList(List<ProductImg> productImges);
	
	void deleteProductImgsByProductId(@Param("productId") String productId);
	void updateindexSeq(@Param("id")String id,@Param("indexSeq")String indexSeq);
	int findcount(@Param("productId") String productId, @Param("imgType") String imgType);
	
	void insertTempList(List<ProductImg> productImges);  //插入数据到临时表
	void deletetempImgsByProductIdandType(@Param("productId") String productId, @Param("imgType") String imgType); //删除临时表数据
	List<ProductImg> findTempListByProductIdAndImgType(@Param("productId") String productId, @Param("imgType") String imgType);  // 查找临时表数据
	
	int findtempCount(@Param("productId") String productId, @Param("imgType") String imgType);   //查询临时表数量
	
	void insertTemp(ProductImg productImge);    //插入图片到临时表
	
	List<ProductImg> findListByParentId(@Param("parentId") String parentId,@Param("templateIden") String templateIden); //根据主G图获取子G图
	
	List<ProductImg> findImgObjByParentIdAndcodeManagerId(@Param("parentId") String parentId,@Param("codeManagerId") String codeManagerId);
	
	/***
	 * 
	 * @param codeManagerId   
	 * @param imgType
	 * @param productId
	 * @param string  0  代表是 普通的产品图片   1  代表是模板图片 
	 * @return
	 */
	ProductImg findByCodeManagerId(ProductImg img);
	
	/***************表结构变化后******************/
	//通过产品Id和图片类型查询
	List<ProductImg> findListByProductIdAndImgTypeNew(@Param("productId") String productId, @Param("imgType") String imgType, @Param("templateIden") String templateIden);
	//更新t_product_img表
	void updateMain(ProductImg productImg);
	//更新t_product_img_platform表
	void updateSubsidiary(ProductImg productImg);
	//新增t_product_img表
	void insertMain(ProductImg productImg);
	//新增t_product_img_platform表
	void insertSubsidiary(ProductImg productImg);

	
	/***
	 * 通过产品id 获得该产品的所有图片 
	 * 
	 * @param productId
	 * @return
	 */
	List<ProductImg> findByProductId(@Param("productId")String productId);
	
	/**
	 * 获取合成图code
	 * @param params
	 * @return
	 */
	List<String> getCompositeCodeListInCurrentPageCodeById(Map<String, Object> params);
	
	
	
	
	
	/*********************************/
	
}