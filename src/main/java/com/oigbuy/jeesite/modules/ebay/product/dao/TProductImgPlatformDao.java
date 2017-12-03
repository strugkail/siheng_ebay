/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.oigbuy.jeesite.common.persistence.CrudDao;
import com.oigbuy.jeesite.common.persistence.annotation.MyBatisDao;
import com.oigbuy.jeesite.modules.ebay.product.entity.TProductImgPlatform;
 
/**
 * 产品图片DAO接口
 * @author AA
 * @version 2017-10-26
 */
@MyBatisDao
public interface TProductImgPlatformDao extends CrudDao<TProductImgPlatform> {

    int findcount(@Param("productId") String productId, @Param("imgType") String imgType);//根据产品id和图片类型查询数量
    
    List<TProductImgPlatform> findAllByProductId(@Param("productId")String productId);
    
    List<TProductImgPlatform> findAllByTemplate(@Param("productId")String productId);
    
    
    List<TProductImgPlatform> findByTProductImgPlatform(@Param("productId") String productId, @Param("imgType") String imgType);
    
	List<TProductImgPlatform> findImgObjByParentIdAndcodeManagerId(@Param("parentId") String parentId,@Param("codeManagerId") String codeManagerId);

	
	/***
	 * 查询  模板下的子g图 集合
	 * 
	 * @param parentId   
	 * @param templateIden
	 * @return
	 */
	List<TProductImgPlatform> findListByParentId(@Param("parentId") String parentId,@Param("templateIden") String templateIden); //根据主G图获取子G图

	int findCountByImgId(@Param("imgId") String imgId); //查询图片表中一条数据，在附表中关联数据的数量
	
	/***
	 * 通过产品ID  图片类型、模板标识  查询
	 * 
	 * @param productId
	 * @param imgType
	 * @param templateIden
	 * @return
	 */
	List<TProductImgPlatform> findListByProductIdAndImgType(@Param("productId") String productId, @Param("imgType") String imgType, @Param("templateIden") String templateIden);

	void updateIndex(@Param("id")String id,@Param("index")String index);//更新图片排序
	
	void updateParentId(@Param("id")String id,@Param("parentId")String parentId);//更新子G图parentId

	
	
	/***
	 * 通过 code manager id 查询 子 sku 非 模板的 图片,应该是只有一个
	 * 
	 * @param codeManagerId
	 * @return
	 */
	TProductImgPlatform findByCodeManagerId(@Param("codeManagerId")String codeManagerId);

	
	/***
	 * 批量插入
	 * 
	 * @param images
	 */
	void insertList(List<TProductImgPlatform> images);
	
	/**
	 * 通过产品ID和图片类型分组查询子G图的套数
	 * @param productId
	 * @param imgType
	 * @param templateIden
	 * @return
	 */
	List<TProductImgPlatform> findGroupByProductIdAndImgType(@Param("productId") String productId, @Param("imgType") String imgType, @Param("templateIden") String templateIden);
	/**
	 * 根据产品Id和parentId查找一组子G图
	 * @param parentId
	 * @param productId
	 * @param templateIden
	 * @return
	 */
	List<TProductImgPlatform> findListByParentIdAndProductId(@Param("parentId") String parentId,@Param("productId") String productId,@Param("templateIden") String templateIden);

}