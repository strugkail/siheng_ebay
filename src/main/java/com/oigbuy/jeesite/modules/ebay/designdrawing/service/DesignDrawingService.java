/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.designdrawing.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

import org.apache.batik.css.engine.value.StringValue;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.daihuade.tool.image.mosaic.bean.ImageInfo;

import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.fastdfs.notify.Notify;
import com.oigbuy.jeesite.common.fastdfs.util.FastdfsHelper;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.common.utils.ObjectUtils;
import com.oigbuy.jeesite.common.utils.SMBUtil;
import com.oigbuy.jeesite.common.utils.StreamUtils;
import com.oigbuy.jeesite.common.utils.StringUtils;
import com.oigbuy.jeesite.modules.ebay.designdrawing.dao.DesignDrawingDao;
import com.oigbuy.jeesite.modules.ebay.designdrawing.dto.ImgFlag;
import com.oigbuy.jeesite.modules.ebay.designdrawing.dto.MosaicImageSrc;
import com.oigbuy.jeesite.modules.ebay.designdrawing.entity.DesignDrawing;
import com.oigbuy.jeesite.modules.ebay.product.dao.ProductCodeManagerDao;
import com.oigbuy.jeesite.modules.ebay.product.dao.ProductDao;
import com.oigbuy.jeesite.modules.ebay.product.dao.ProductImgDao;
import com.oigbuy.jeesite.modules.ebay.product.dao.TProductImgPlatformDao;
import com.oigbuy.jeesite.modules.ebay.product.entity.Product;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductCodeManager;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductImg;
import com.oigbuy.jeesite.modules.ebay.product.entity.TProductImgPlatform;
import com.oigbuy.jeesite.modules.ebay.product.service.ProductImgService;
import com.oigbuy.jeesite.modules.ebay.product.service.TProductImgPlatformService;
import com.oigbuy.jeesite.modules.sys.utils.UserUtils;
import com.sho.tool.build.image.ImageBean;
import com.sho.tool.build.image.ImageOutputCallBack;
import com.sho.tool.build.image.MosaicTool;

/**
 * 设计作图Service
 * @author 王佳点
 * @version 2017-09-04
 */
@Service
@Transactional(readOnly = true)
public class DesignDrawingService extends CrudService<DesignDrawingDao, DesignDrawing> {

	@Autowired
	private ProductImgDao productImgDao;
	
	@Autowired
	private ProductCodeManagerDao productCodeManagerDao;

	@Autowired
	private TProductImgPlatformService tProductImgPlatformService;
	
	@Autowired
	private TProductImgPlatformDao tProductImgPlatformDao;
	
	@Autowired
	private ProductDao productDao;
	
	public DesignDrawing get(String id) {
		return super.get(id);
	}
	
	public List<DesignDrawing> findList(DesignDrawing designDrawing) {
		return super.findList(designDrawing);
	}
	
	public Page<DesignDrawing> findPage(Page<DesignDrawing> page, DesignDrawing designDrawing) {
		return super.findPage(page, designDrawing);
	}
	
	@Transactional(readOnly = false)
	public void save(DesignDrawing designDrawing) {
		super.save(designDrawing);
	}
	
	@Transactional(readOnly = false)
	public void delete(DesignDrawing designDrawing) {
		super.delete(designDrawing);
	}
	
	@Transactional(readOnly = false)
	public List<Map<String, String>> getDetailImgsList(DesignDrawing designDrawing){
		String productId = designDrawing.getId();
		List<Map<String, String>> detailImgsList = new ArrayList<Map<String,String>>();
		 
		//根据产品ID和图片类型获取所有详情图
		List<TProductImgPlatform> detailList = tProductImgPlatformDao.findListByProductIdAndImgType(productId, Global.IMG_TYPE_DETAIL, Global.TEMPLATE_IDEN);
		
		return detailImgsList;
	}
	
	@Transactional(readOnly = false)
	public List<Map<String, String>> getMainImgsList(DesignDrawing designDrawing){
		String productId = designDrawing.getId();
		List<Map<String, String>> mainImgsList = new ArrayList<Map<String,String>>();
		 
		//根据产品ID和图片类型获取所有详情图
		List<TProductImgPlatform> mainList = tProductImgPlatformDao.findListByProductIdAndImgType(productId, Global.IMG_TYPE_MAIN, Global.TEMPLATE_IDEN);
		for (TProductImgPlatform img : mainList) {
			ProductImg productImg = productImgDao.get(img.getImgId());
			Map<String, String> map = new HashMap<String, String>();
			map.put("url", FastdfsHelper.getImageUrl()
					+ (productImg.getImgUrl() == null ? "" : productImg.getImgUrl()));
			map.put("id", img.getId());
			mainImgsList.add(map);
		}
		return mainImgsList;
	}
	
//	@Transactional(readOnly = false)
//	public List<Map<String, Object>> getGroupImgs(DesignDrawing designDrawing,List<ProductCodeManager> variantList){
//		String productId = designDrawing.getId();
//		//获取套图数量
//		//List<TProductImgPlatform> GroupNum = tProductImgPlatformDao.findGroupByProductIdAndImgType(productId,Global.IMG_TYPE_CHILD_MAIN,Global.TEMPLATE_IDEN);
//		
//		
//		//for (int i=0; i<GroupNum.size(); i++) {
//			Map<String, Object> groupMap = new HashMap<String, Object>();
//			//根据主G图ID获取同一套子G图
//			List<TProductImgPlatform> childImgsList = tProductImgPlatformDao.findListByParentId(String.valueOf(i),Global.TEMPLATE_IDEN);
//			
//			Map<String,TProductImgPlatform> childMap = new HashMap<String, TProductImgPlatform>();
//			for (TProductImgPlatform childImg : childImgsList) {
//				childMap.put(childImg.getCodeManagerId(), childImg);
//			}
//			
//			for(ProductCodeManager codeManager : variantList){
//				TProductImgPlatform childImg = childMap.get(codeManager.getId());
//				ProductImg productChildImg = null;
//				if (ObjectUtils.notEqual(childImg, null)) {
//					productChildImg = productImgDao.get(childImg.getImgId());
//				}
//				Map<String, String> cMap = new HashMap<String, String>();
//				if(childImg != null){
//					cMap.put("url", FastdfsHelper.getImageUrl()
//							+ (productChildImg.getImgUrl() == null ? "" : productChildImg.getImgUrl()));
//					cMap.put("id", childImg.getId());
//				}
//				childList.add(cMap);
//			}
//			
//			groupMap.put("childImg", childList);
//			
//			groupImgsList.add(groupMap);
//		//}
//		return groupImgsList;
//	}
	
	@Transactional(readOnly = false)
	public synchronized String updateProductImg(String url, String type, String productId) {
		//产品图片表对象
		ProductImg productImg = new ProductImg();
		productImg.setId(Global.getID().toString());
		productImg.setProductId(productId);
		productImg.setImgUrl(url);
		
		if(Global.IMG_TYPE_JIGSAW.equals(type)){
			// 每上传一张图片，product表 maxCode + 1
			productImgDao.updateMaxCode(productId);
			Integer maxCode = productImgDao.findMaxCodeByProductId(productId);
			productImg.setCode(maxCode + "");
			// 清空redis合成图源的缓存
			clearMoaicViewImgCache();
		}
		productImgDao.insert(productImg);
		//产品图片表附表对象
		TProductImgPlatform tProductImgPlatform = new TProductImgPlatform();
		tProductImgPlatform.setId(Global.getID().toString());
		tProductImgPlatform.setImgId(productImg.getId());
		tProductImgPlatform.setImgType(type);
		tProductImgPlatform.setProductId(productId);
		tProductImgPlatform.setTemplateIden(Global.TEMPLATE_IDEN);
		tProductImgPlatform.setPlatformFlag(Global.PLATFORM_FLAG_EBAY);
		
		//对同类型图片进行排序
		int num = tProductImgPlatformDao.findcount(productId, type);
		tProductImgPlatform.setIndex(num);
		
		tProductImgPlatformDao.insert(tProductImgPlatform);
		return tProductImgPlatform.getId();
	}
	
	private void clearMoaicViewImgCache(){
		UserUtils.removeCache(Global.SESSION_CACHE_KEY_MOSAIC_VIEWIMG);
	}
	
	@Transactional(readOnly = false)
	public synchronized String updateSkuImg(String url, String type, String productId ,String parentId, String codeManagerId, Integer index) {
		ProductImg productImg = new ProductImg();
		productImg.setImgUrl(url);
		productImg.setProductId(productId);
		
		TProductImgPlatform tProductImgPlatform = new TProductImgPlatform();
		tProductImgPlatform.setImgType(type);
		tProductImgPlatform.setProductId(productId);
		tProductImgPlatform.setParentId(parentId);
		tProductImgPlatform.setCodeManagerId(codeManagerId);
		tProductImgPlatform.setTemplateIden(Global.TEMPLATE_IDEN);
		tProductImgPlatform.setPlatformFlag(Global.PLATFORM_FLAG_EBAY);
		
		//获取指定的子G图
		List<TProductImgPlatform> pImg = tProductImgPlatformDao.findImgObjByParentIdAndcodeManagerId(parentId, codeManagerId);
		if (CollectionUtils.isNotEmpty(pImg)) {
			tProductImgPlatform.setId(pImg.get(0).getId());
			
			productImg.setId(pImg.get(0).getImgId());
			productImgDao.update(productImg);
			
		}else{
			productImg.setId(Global.getID().toString());
			productImgDao.insert(productImg);
			
			tProductImgPlatform.setId(Global.getID().toString());
			tProductImgPlatform.setImgId(productImg.getId());
			
			//对同类型图片进行排序
			int num = tProductImgPlatformDao.findcount(productId, type);
			tProductImgPlatform.setIndex(num);
			// 每上传一张图片，product表 maxCode + 1
			productImgDao.updateMaxCode(productId);
			
			tProductImgPlatformDao.insert(tProductImgPlatform);
				
		}
		return tProductImgPlatform.getId();
	}
	
/**	@Transactional(readOnly = false)
	public synchronized String insertMainImg(String type,String productId) {
		ProductImg productImg = new ProductImg();
		productImg.setId(Global.getID().toString());
		productImg.setProductId(productId);
		
		productImgDao.insert(productImg);
		
		//产品图片表附表对象
		TProductImgPlatform tProductImgPlatform = new TProductImgPlatform();
		tProductImgPlatform.setId(Global.getID().toString());
		tProductImgPlatform.setImgType(type);
		tProductImgPlatform.setProductId(productId);
		tProductImgPlatform.setImgId(productImg.getId());
		tProductImgPlatform.setTemplateIden(Global.TEMPLATE_IDEN);
		tProductImgPlatform.setPlatformFlag(Global.PLATFORM_FLAG_EBAY);
		
		//对同类型图片进行排序
		int num = tProductImgPlatformDao.findcount(productId, type);
		tProductImgPlatform.setIndex(num);
		// 每上传一张图片，product表 maxCode + 1
		productImgDao.updateMaxCode(productId);
		
		tProductImgPlatformDao.insert(tProductImgPlatform);
//			
		return tProductImgPlatform.getId();
	}*/
	
	@Transactional(readOnly = false)
	public ImgFlag deleteImg(TProductImgPlatform tProductImgPlatform) {
		ImgFlag imgFlag = new ImgFlag();
		tProductImgPlatform = tProductImgPlatformDao.get(tProductImgPlatform);
		//删除图片关系表中此条 数据
		tProductImgPlatformDao.delete(tProductImgPlatform);
		//查询此图片有几条关联数据并判断是否可以删除主表数据
		int imgNum = tProductImgPlatformDao.findCountByImgId(tProductImgPlatform.getImgId());
		if (imgNum == Integer.valueOf(Global.ZERO)) {
			ProductImg productImg = productImgDao.get(tProductImgPlatform.getImgId());
			FastdfsHelper.delFile(productImg.getImgUrl());
			productImgDao.delete(productImg);
		}
		
		if (StringUtils.equals(tProductImgPlatform.getImgType(), Global.IMG_TYPE_CHILD_MAIN)) {
			List<TProductImgPlatform> groupList = tProductImgPlatformDao
					.findListByParentIdAndProductId(tProductImgPlatform.getParentId(), tProductImgPlatform.getProductId(), Global.TEMPLATE_IDEN);
			if (CollectionUtils.isEmpty(groupList)) {
				//删除组图最后一张，对parentId进行更新
				List<TProductImgPlatform> childImgList = tProductImgPlatformDao.findListByProductIdAndImgType(tProductImgPlatform.getProductId(), Global.IMG_TYPE_CHILD_MAIN,Global.TEMPLATE_IDEN);
				for (TProductImgPlatform img : childImgList) {
					if (Integer.valueOf(img.getParentId())> tProductImgPlatform.getIndex()) {
						tProductImgPlatformDao.updateParentId(img.getId(), String.valueOf(Integer.valueOf(img.getParentId())-1));
					}
				}
				imgFlag.setFlag("last");
			}
		}
		
		List<TProductImgPlatform> list = tProductImgPlatformDao.findListByProductIdAndImgType(tProductImgPlatform.getProductId(), tProductImgPlatform.getImgType(),Global.TEMPLATE_IDEN);
		 for(int i=0;i<list.size();i++){
			 String id = list.get(i).getId();
			 tProductImgPlatformDao.updateIndex(id, String.valueOf(i));
		 }
		 imgFlag.setImgId(tProductImgPlatform.getId());
		return imgFlag;
	}
	 
	@Transactional(readOnly = false)
	public List<String> deleteGroupImgs(TProductImgPlatform tProductImgPlatform) throws Exception {
		List<String> imgIdlist = new ArrayList<String>();
		List<TProductImgPlatform> childImgsList = tProductImgPlatformDao
				.findListByParentIdAndProductId(String.valueOf(tProductImgPlatform.getIndex()), tProductImgPlatform.getProductId(), Global.TEMPLATE_IDEN);
		for (TProductImgPlatform childImg : childImgsList) {
			tProductImgPlatformDao.delete(childImg);
			//查询此图片有几条关联数据并判断是否可以删除主表数据
			int childImgNum = tProductImgPlatformDao.findCountByImgId(childImg.getImgId());
			ProductImg productChildImg = null;
			if (childImgNum == Integer.valueOf(Global.ZERO)) {
				productChildImg = productImgDao.get(childImg.getImgId());
				FastdfsHelper.delFile(productChildImg.getImgUrl());
				productImgDao.delete(productChildImg);
			}
			//删除一张图片后，对同类型图片进行重新排序
			List<TProductImgPlatform> list2 = tProductImgPlatformDao.findListByProductIdAndImgType(childImg.getProductId(), childImg.getImgType(),Global.TEMPLATE_IDEN);
			for(int j=0;j<list2.size();j++){
				String pid = list2.get(j).getId();
				tProductImgPlatformDao.updateIndex(pid, String.valueOf(j));
			}
			
			imgIdlist.add(childImg.getId());
		}
		
		//删除一套图后，对parentId进行更新
		List<TProductImgPlatform> childImgList = tProductImgPlatformDao.findListByProductIdAndImgType(tProductImgPlatform.getProductId(), Global.IMG_TYPE_CHILD_MAIN,Global.TEMPLATE_IDEN);
		for (TProductImgPlatform img : childImgList) {
			if (Integer.valueOf(img.getParentId())> tProductImgPlatform.getIndex()) {
				tProductImgPlatformDao.updateParentId(img.getId(), String.valueOf(Integer.valueOf(img.getParentId())-1));
			}
		}
		
		return imgIdlist;
	}


	@Transactional(readOnly = false)
	public String parseFolder(String folderPath, String productId) throws Exception{
		String firstPath = Global.getConfig("smb_img_url_ebay");
		String secondPath = null;
		if (StringUtils.isNotEmpty(folderPath)) {
			secondPath = firstPath+"/"+folderPath+"/";
			SmbFile firstFile = SMBUtil.getSmbFile(secondPath);
				if (!firstFile.exists()) {
					throw new Exception("您输入的"+folderPath+"文件夹未找到，请检查！");
				}
		} else {
			throw new Exception("请输入分组文件夹名称！");
		}
		//获取产品母代码
		Product product = productDao.get(productId);
		String sysParentCode = product.getSysParentCode();
		//后台拼接共享盘地址
		String thirdPath = secondPath+sysParentCode+"/G图";
		//获取此产品的所有codeManagerId
		List<ProductCodeManager> codeManagerList = productCodeManagerDao.findListByProductId(productId);
		Map<String, String> childMap = new HashMap<String, String>();
		for (ProductCodeManager productCodeManager : codeManagerList) {
			childMap.put(productCodeManager.getSysSku(), productCodeManager.getId());
		}
		SmbFile file = SMBUtil.getSmbFile(thirdPath);
		if (file.exists()) {
			//获取一级文件夹下所有对象，遍历并判断是否是文件夹
			SmbFile[] files = file.listFiles();
			for (int j = 0; j < files.length ; j++) {
				if (files[j].isDirectory()) {
					//获取详情图文件夹
					String fileName = files[j].getName();
					if (StringUtils.equals(fileName, "页面图/")) {
						SmbFile[] listFiles = files[j].listFiles();
						for (int i = 0; i<listFiles.length; i++) {
							if (listFiles[i].isFile()) {
								//获取图片名称
								String name = listFiles[i].getName();
								if (StringUtils.endsWithIgnoreCase(name, ".jpg") || StringUtils.endsWithIgnoreCase(name, ".png")) {
									//上传详情图到文件服务器
									Notify uploadFile = FastdfsHelper.uploadFile(StreamUtils.InputStreamTOByte(listFiles[i].getInputStream()), 
											StringUtils.substringAfterLast(name, "."));
									String imgUrl = uploadFile.getRelativeUrl();
									//设置详情图对象到图片主表
									ProductImg productImg = new ProductImg();
									productImg.setId(Global.getID().toString());
									productImg.setProductId(productId);
									productImg.setImgUrl(imgUrl);
									
									productImgDao.insert(productImg);
									
									//设置详情图对象到图片附表
									TProductImgPlatform tProductImgPlatform = new TProductImgPlatform();
									tProductImgPlatform.setId(Global.getID().toString());
									tProductImgPlatform.setProductId(productId);
									tProductImgPlatform.setImgId(productImg.getId());
									tProductImgPlatform.setImgType(Global.IMG_TYPE_DETAIL);
									tProductImgPlatform.setTemplateIden(Global.TEMPLATE_IDEN);
									tProductImgPlatform.setPlatformFlag(Global.PLATFORM_FLAG_EBAY);
									
									//对同类型图片进行排序
									int num = tProductImgPlatformDao.findcount(productId, Global.IMG_TYPE_DETAIL);
									tProductImgPlatform.setIndex(num);
									// 每上传一张图片，product表 maxCode + 1
									productImgDao.updateMaxCode(productId);
									
									tProductImgPlatformDao.insert(tProductImgPlatform);
								}
							}
						}
							
					}else{
						//获取二级文件夹下所有对象，遍历并判断是否是文件
						SmbFile[] listFiles = files[j].listFiles();
						for (int i = 0; i<listFiles.length; i++) {
							if (listFiles[i].isFile()) {
								//获取图片名称
								String name = listFiles[i].getName();
								if (StringUtils.endsWithIgnoreCase(name, ".jpg") || StringUtils.endsWithIgnoreCase(name, ".png")) {
									if (StringUtils.startsWith(listFiles[i].getName(), "M-")) {
										//上传主G图到文件服务器
										Notify uploadFile = FastdfsHelper.uploadFile(StreamUtils.InputStreamTOByte(listFiles[i].getInputStream()), 
												StringUtils.substringAfterLast(name, "."));
										String imgUrl = uploadFile.getRelativeUrl();
										//设置主G图对象到图片主表
										ProductImg productImg = new ProductImg();
										productImg.setId(Global.getID().toString());
										productImg.setProductId(productId);
										productImg.setImgUrl(imgUrl);
										
										productImgDao.insert(productImg);
										
										//设置主G图对象到图片附表
										TProductImgPlatform tProductImgPlatform = new TProductImgPlatform();
										tProductImgPlatform.setId(Global.getID().toString());
										tProductImgPlatform.setProductId(productId);
										tProductImgPlatform.setImgId(productImg.getId());
										tProductImgPlatform.setImgType(Global.IMG_TYPE_MAIN);
										tProductImgPlatform.setTemplateIden(Global.TEMPLATE_IDEN);
										tProductImgPlatform.setPlatformFlag(Global.PLATFORM_FLAG_EBAY);
										
										//对同类型图片进行排序
										int num = tProductImgPlatformDao.findcount(productId, Global.IMG_TYPE_MAIN);
										tProductImgPlatform.setIndex(num);
										// 每上传一张图片，product表 maxCode + 1
										productImgDao.updateMaxCode(productId);
										
										tProductImgPlatformDao.insert(tProductImgPlatform);
									} else {
										//上传子G图到文件服务器
										String name2 = StringUtils.substringBefore(name, ".");
										if (StringUtils.isNotEmpty(childMap.get(name2))){
											//上传子G图到文件服务器
											Notify uploadFile2 = FastdfsHelper.uploadFile(StreamUtils.InputStreamTOByte(listFiles[i].getInputStream()),
													StringUtils.substringAfterLast(name, "."));
											String cimgUrl = uploadFile2.getRelativeUrl();
											//设置子G图对象
											ProductImg productChildImg = new ProductImg();
											productChildImg.setId(Global.getID().toString());
											productChildImg.setImgUrl(cimgUrl);
											productChildImg.setProductId(productId);
											
											//保存子G图
											productImgDao.insert(productChildImg);
											
											TProductImgPlatform productChildImgPlatform = new TProductImgPlatform();
											productChildImgPlatform.setId(Global.getID().toString());
											productChildImgPlatform.setProductId(productId);
											productChildImgPlatform.setImgId(productChildImg.getId());
											productChildImgPlatform.setImgType(Global.IMG_TYPE_CHILD_MAIN);
											productChildImgPlatform.setParentId(String.valueOf(j));
											productChildImgPlatform.setCodeManagerId(childMap.get(name2));
											productChildImgPlatform.setTemplateIden(Global.TEMPLATE_IDEN);
											productChildImgPlatform.setPlatformFlag(Global.PLATFORM_FLAG_EBAY);
											
											tProductImgPlatformDao.insert(productChildImgPlatform);
										}
									}
								}
							}
						}
					}
				}
			}
	}else{
		throw new Exception("产品母代码所对应的文件夹没找到，请检查！");
	}
		//插入图片后，对同类型图片进行重新排序
		List<TProductImgPlatform> list2 = tProductImgPlatformDao.findListByProductIdAndImgType(productId, Global.IMG_TYPE_CHILD_MAIN,Global.TEMPLATE_IDEN);
		for(int k=0;k<list2.size();k++){
		String pid = list2.get(k).getId();
		tProductImgPlatformDao.updateIndex(pid, String.valueOf(k));
		}
		return "success";
	}
	
	
	/**
	 * 获取当前页所有的合成图代码
	 * 
	 * @param productId
	 * @param currentPageNo
	 * @return
	 */
	@Transactional(readOnly = false)
	public List<String> getAllCodeForCurrentPage(String productId) {
		// 获取已生成的图片合成码
		Product product = productDao.get(productId);
		
		List<String> allCodeForCurrentPageList = new ArrayList<String>();
		HashMap<String, String> filterMap = new HashMap<String, String>();

		// 已生成过的code列表
		if (StringUtils.isNotBlank(product.getCodeHistory())) {
			List<String> allCodeList = Arrays.asList(product.getCodeHistory().split(Global.SEPARATE_3));
			Map<String, Object> params = new HashMap<String, Object>(2);
			params.put("productId", productId);
			params.put("list", allCodeList);
			List<String> compositeCode = productImgDao.getCompositeCodeListInCurrentPageCodeById(params);
			for (String code : compositeCode) {
				filterMap.put(code, "");
			}
		}
		
		// 每次生成的数量
		int imgNum = Integer.parseInt(Global.getConfig("number_img"));
		// 根据产品ID获取参与合成的图片
		ImageBean[] ImageBeanList = getImageBeanByProductId(productId);

		allCodeForCurrentPageList = MosaicTool.buildMosaicCodeEx(ImageBeanList, filterMap, imgNum);
		updateCodeHistoryById(productId, product.getCodeHistory(), allCodeForCurrentPageList);

		return allCodeForCurrentPageList;
	}
	
	private void updateCodeHistoryById(String productId, String codeHistory,
			List<String> newCodeList) {
		StringBuffer sb = new StringBuffer();

		if (CollectionUtils.isNotEmpty(newCodeList)) {
			if (StringUtils.isNotBlank(codeHistory)) {
				sb.append(Global.SEPARATE_3 + codeHistory);
			}
			for (String code : newCodeList) {
				sb.append(Global.SEPARATE_3 + code);
			}

			productDao.updateCodeHistoryById(productId, sb.toString()
					.substring(1));
		}
	}
	
	
	/**
	 * 根据产品ID获取参与合成的图片
	 * */
	private ImageBean[] getImageBeanByProductId(String productId) {
		TProductImgPlatform param = new TProductImgPlatform();
		param.setProductId(productId);
		param.setImgType(Global.IMG_TYPE_JIGSAW);
		param.setPlatformFlag(Global.PLATFORM_FLAG_EBAY);
		param.setTemplateIden(Global.TEMPLATE_IDEN);
		List<TProductImgPlatform> ProductImgList = tProductImgPlatformDao.findList(param);
		// 输出流和code
		ImageBean[] ImageBeanList = new ImageBean[ProductImgList.size()];
		int i = 0;
		for (TProductImgPlatform img : ProductImgList) {
			try {
				// ImageBean readImage = MosaicTool.readImage(new
				// URL(img.getImgUrl()).openStream());
				// ImageBean readImage = MosaicTool.readImage(new
				// ByteArrayInputStream(FastdfsHelper.download(img.getImgUrl())));
				ImageBean readImage = MosaicTool.readImage(StreamUtils
						.getFastdFsStream(img.getImgUrl()));
				readImage.setCode(Integer.parseInt(img.getImgCode()));
				ImageBeanList[i] = readImage;
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			i++;
		}
		return ImageBeanList;
	}

	
	/**
	 * 获取当前页已选择的合成图代码
	 * 
	 * @param productId
	 * @param currentPageNo
	 * @return
	 */
	public List<String> getSelectedCodeForCurrentPage(String productId,
			List<String> allCodeForCurrentPageList) {
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("productId", productId);
		params.put("list", allCodeForCurrentPageList);
		if (CollectionUtils.isNotEmpty(allCodeForCurrentPageList)) {
			return productImgDao.getCompositeCodeListInCurrentPageCodeById(params);
		}
		return null;
	}
	
	@Transactional(readOnly = false)
	public Map<String,String> saveCompositeImg(ProductImg productImg) {
		String productId = productImg.getProductId() + "";
		
		TProductImgPlatform param = new TProductImgPlatform();
		param.setProductId(productId);
		param.setImgType(Global.IMG_TYPE_JIGSAW);
		param.setPlatformFlag(Global.PLATFORM_FLAG_EBAY);
		List<TProductImgPlatform> imgList = tProductImgPlatformDao.findList(param);
		HashMap<Integer, ImageInfo> imageMap = new HashMap<Integer, ImageInfo> ();
		Map<String,String> map = new HashMap<String, String>();
		for(TProductImgPlatform img : imgList){
			int code2 = Integer.parseInt(img.getImgCode());
			ImageBean bean;
			try {
				bean = MosaicTool.readImage(StreamUtils.getFastdFsStream(img.getImgUrl()));
				bean.setCode(code2);
				imageMap.put(code2, bean);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage(),e);
			} 
		}
		
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		MosaicTool.mosaicOneByPolicyCode(productImg.getCompositeCode(), imageMap, new ImageOutputCallBack() {
			
			@Override
			public OutputStream getOutputStream(String paramString) {
				return ba;
			}
		});
//		Notify uploadFile = FastdfsHelper.uploadFile(ba.toByteArray(), productId + "_" + compositeImage.getCompositeCode());
		Notify uploadFile = FastdfsHelper.uploadFile(ba.toByteArray(), "jpg");
		String url = uploadFile.getRelativeUrl();
		String Turl = uploadFile.getAbsoluteUrl();
		//compositeImageDao.deleteCompositeImgsByProductIdandcode(productId, compositeImage.getCompositeCode());
		
		productImg.setId(Global.getID().toString());
		productImg.setImgUrl(url);
		productImgDao.insert(productImg);
		
		TProductImgPlatform productImgPlatform = new TProductImgPlatform();
		productImgPlatform.setId(Global.getID().toString());
		productImgPlatform.setImgId(productImg.getId());
		productImgPlatform.setProductId(productId);
		productImgPlatform.setImgType(Global.IMG_TYPE_COMPOSITE);
		productImgPlatform.setIndex(Global.ZERO_INTEGER);
		productImgPlatform.setPlatformFlag(Global.PLATFORM_FLAG_EBAY);
		productImgPlatform.setTemplateIden(Global.TEMPLATE_IDEN);
		tProductImgPlatformDao.insert(productImgPlatform);
		
		// 更新产品合成图的数量
//		productDao.updateCompositeImgNumByProductId(productId, compositeImageDao.findListByProductIdAndImgType(productId, null).size());
		
		map.put("url", Turl);
		map.put("id", productImgPlatform.getId());
		map.put("productId", productId);
		map.put("code", productImg.getCompositeCode());
		return map;
	}
	
	public void viewImg(String code, String productId,
			ImageOutputCallBack imageOutputCallBack) throws Exception {
		MosaicTool.mosaicOneByPolicyCode(code, getImageMap(productId),
				imageOutputCallBack);
	}
	
	private synchronized HashMap<Integer, ImageInfo> getImageMap(
			String productId) {
		MosaicImageSrc imgSrc = (MosaicImageSrc) UserUtils
				.getCache(Global.SESSION_CACHE_KEY_MOSAIC_VIEWIMG);
		if (imgSrc == null || !productId.equals(imgSrc.getProductId())) {
			TProductImgPlatform param = new TProductImgPlatform();
			param.setProductId(productId);
			param.setImgType(Global.IMG_TYPE_JIGSAW);
			param.setPlatformFlag(Global.PLATFORM_FLAG_WISH);
			List<TProductImgPlatform> imgList = tProductImgPlatformDao.findList(param);
			HashMap<Integer, ImageInfo> imageMap = new HashMap<Integer, ImageInfo>();
			for (TProductImgPlatform img : imgList) {
				int code2 = Integer.parseInt(img.getImgCode());
				ImageBean bean = MosaicTool.readImage(StreamUtils
						.getFastdFsStream(img.getImgUrl())); // 合成图片
				bean.setCode(code2);
				imageMap.put(code2, bean);
			}
			if (imgSrc == null) {
				imgSrc = new MosaicImageSrc();
			}

			imgSrc.setProductId(productId);
			imgSrc.setImageMap(imageMap);

			UserUtils.putCache(Global.SESSION_CACHE_KEY_MOSAIC_VIEWIMG, imgSrc);
		}
		return imgSrc.getImageMap();
	}


	
	/**
	 * 导入图片
	 * @param folderPath
	 * @param productId
	 */
	@Transactional(readOnly = false)
	public void ImportImage(String folderPath, String productId){
		List<ProductCodeManager> codeManagerList = productCodeManagerDao.findListByProductId(productId);
		Map<String, String> childMap = new HashMap<String, String>();
		for (ProductCodeManager productCodeManager : codeManagerList) {
			childMap.put(productCodeManager.getSysSku(), productCodeManager.getId());
		}
		File file = new File(folderPath);
		if(file.isDirectory()){
			File[] files = file.listFiles();
			for(int i = 0;i<files.length;i++){
				ProductImg productImg = new ProductImg();
				String imgs = files[i].toString();
				System.out.print(imgs.length());
				String image = files[i].toString().substring(files[i].toString().length()-5,files[i].toString().length());
				String imageTwo = files[i].toString().substring(files[i].toString().length()-4,files[i].toString().length());
				if((".jpeg").equals(image) || (".jpg").equals(imageTwo) || (".png").equals(imageTwo) || (".gif").equals(imageTwo)){
					if(files[i].toString().contains("ZT")){
						//主图的处理
						Notify uploadFile = FastdfsHelper.uploadFile(files[i]);
						String imgUrl = uploadFile.getRelativeUrl();
						productImg.setId(Global.getID().toString());
						productImg.setImgUrl(imgUrl);
						productImg.setIsNewRecord(true);
						productImgDao.insert(productImg);

						TProductImgPlatform tProductImgPlatform = new TProductImgPlatform();
						tProductImgPlatform.setId(Global.getID().toString());
						tProductImgPlatform.setImgId(productImg.getId());
						tProductImgPlatform.setProductId(productId);
						tProductImgPlatform.setImgType(Global.IMG_TYPE_MAIN);
						int number = tProductImgPlatformService.findcount(productId,Global.IMG_TYPE_MAIN);
						tProductImgPlatform.setIndex(number);
						tProductImgPlatform.setPlatformFlag(Global.PLATFORM_FLAG_WISH);
						tProductImgPlatform.setIsNewRecord(true);
						tProductImgPlatformService.save(tProductImgPlatform);
					}else{
						//细节图的处理
						Notify uploadFile = FastdfsHelper.uploadFile(files[i]);
						String imgUrl = uploadFile.getRelativeUrl();
						//设置细节图对象
						productImg.setId(Global.getID().toString());
						productImg.setImgUrl(imgUrl);
						productImg.setProductId(productId);
						productImg.setIsNewRecord(true);
						productImgDao.insert(productImg);

						TProductImgPlatform tProductImgPlatform = new TProductImgPlatform();
						tProductImgPlatform.setId(Global.getID().toString());
						tProductImgPlatform.setImgId(productImg.getId());
						tProductImgPlatform.setProductId(productId);
						tProductImgPlatform.setImgType(Global.IMG_TYPE_DETAIL);
						int number = tProductImgPlatformService.findcount(productId,Global.IMG_TYPE_DETAIL);
						tProductImgPlatform.setIndex(number);
						tProductImgPlatform.setPlatformFlag(Global.PLATFORM_FLAG_WISH);
						tProductImgPlatform.setIsNewRecord(true);
						tProductImgPlatform.setTemplateIden(Global.TEMPLATE_IDEN);
						tProductImgPlatformService.save(tProductImgPlatform);
					}
				}else{
					System.out.println("这是不图片"+files[i]);
				}
			}
		}

	}
	
	
	
	public List<TProductImgPlatform> findAllByProductId(String productId){
		return tProductImgPlatformDao.findAllByProductId(productId);
	}
	
	public List<TProductImgPlatform> findAllByTemplate(String productId){
		return tProductImgPlatformDao.findAllByTemplate(productId);
	}


}

