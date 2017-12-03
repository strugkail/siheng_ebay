/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.designdrawing.web;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jcifs.smb.SmbException;

import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.process.entity.EbayProcessBusiness;
import com.oigbuy.jeesite.common.process.entity.ProcessBean;
import com.oigbuy.jeesite.common.process.service.GeneralProcessService;
import com.oigbuy.jeesite.common.repeaturl.RepeatUrlData;
import com.oigbuy.jeesite.common.utils.JsonResponseModel;
import com.oigbuy.jeesite.common.utils.ObjectUtils;
import com.oigbuy.jeesite.common.utils.StringUtils;
import com.oigbuy.jeesite.common.web.BaseController;
import com.oigbuy.jeesite.modules.act.entity.Act;
import com.oigbuy.jeesite.modules.ebay.designdrawing.dto.ImgFlag;
import com.oigbuy.jeesite.modules.ebay.designdrawing.entity.DesignDrawing;
import com.oigbuy.jeesite.modules.ebay.designdrawing.service.DesignDrawingService;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductCodeManager;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductImg;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductImgDetil;
import com.oigbuy.jeesite.modules.ebay.product.entity.TProductImgPlatform;
import com.oigbuy.jeesite.modules.ebay.product.service.ProductCodeManagerService;
import com.oigbuy.jeesite.modules.ebay.productcollection.entity.ProductCollection;
import com.oigbuy.jeesite.modules.ebay.productcollection.service.ProductCollectionService;
import com.oigbuy.jeesite.modules.ebay.purchase.dto.PurchaseSource;
import com.oigbuy.jeesite.modules.ebay.purchase.service.SupplierService;
import com.oigbuy.jeesite.modules.sys.utils.UserUtils;
import com.oigbuy.jeesite.common.fastdfs.notify.Notify;
import com.oigbuy.jeesite.common.fastdfs.util.FastdfsHelper;
import com.sho.tool.build.image.ImageOutputCallBack;

/**
 * 设计作图Controller
 * @author 王佳点
 * @version 2017-09-04
 */
@Controller
@RequestMapping(value = "${adminPath}/designdrawing/designDrawing")
public class DesignDrawingController extends BaseController {

	@Autowired
	private DesignDrawingService designDrawingService;
	
	@Autowired
	private ProductCodeManagerService productCodeManagerService;
	
	@Autowired
	private GeneralProcessService generalProcessService;
	
	@Autowired
	private ProductCollectionService productCollectionService;
	
	@Autowired
	private SupplierService supplierService;
	
	@ModelAttribute
	public DesignDrawing get(@RequestParam(required=false) String id) {
		DesignDrawing entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = designDrawingService.get(id);
		}
		if (entity == null){
			entity = new DesignDrawing();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(DesignDrawing designDrawing, HttpServletRequest request, HttpServletResponse response, Model model,EbayProcessBusiness business) throws Exception {
		String type = designDrawing.getType();
		if (Global.ZERO.equals(type)) {
				business.setSaleType(Global.SALE_TYPE_VIRTUAL_OVERSEAS_STOREHOUSE);
			} else {
				business.setSaleType(Global.SALE_TYPE_OVERSEAS_STOREHOUSE);
			}
//		Page<DesignDrawing> page = designDrawingService.findPage(new Page<DesignDrawing>(request,response), designDrawing);
		List<Act> list = generalProcessService.todoList(business);
		
		//获取开发时间区间
		String dateStart = request.getParameter("dateStart");
		String dateEnd = request.getParameter("dateEnd");
		
//		model.addAttribute("page", page);
		model.addAttribute("dateStart", dateStart);
		model.addAttribute("dateEnd", dateEnd);
		model.addAttribute("list", list);
		model.addAttribute("type", type);
		return "ebay/designDrawing/designDrawingList";
	}

	@RequestMapping(value = "form")
	public String form(DesignDrawing designDrawing, Model model, ProcessBean<EbayProcessBusiness> bean) {
		
		String productId = designDrawing.getId();
		//根据产品ID获取采购链接
		List<PurchaseSource> purchaseSourceList = supplierService.getSupplierListForProduct(productId);
		//根据产品ID获取竞品采集ID
		String collectId = productCollectionService.findCollectIdByProductId(productId);
		//获取采集表中相关信息
		ProductCollection productCollection = productCollectionService.get(collectId);
		
		//获取模板图片
		List<TProductImgPlatform> images = designDrawingService.findAllByTemplate(productId);
		//根据productId获取SKU
		List<ProductCodeManager> variantList = productCodeManagerService.findListByProductId(productId,null,images);
		
		
		List<TProductImgPlatform> detailList = new ArrayList<TProductImgPlatform>();
		List<TProductImgPlatform> mainList = new ArrayList<TProductImgPlatform>();
		List<TProductImgPlatform> puzzleList = new ArrayList<TProductImgPlatform>();
		List<TProductImgPlatform> groupList = new ArrayList<TProductImgPlatform>();
		List<TProductImgPlatform> compositeList = new ArrayList<TProductImgPlatform>();
		for(TProductImgPlatform platform : images){
			if(StringUtils.equals(platform.getImgType(), Global.IMG_TYPE_DETAIL)){
				detailList.add(platform);
			}else if(StringUtils.equals(platform.getImgType(), Global.IMG_TYPE_MAIN)){
				mainList.add(platform);
			}else if(StringUtils.equals(platform.getImgType(), Global.IMG_TYPE_JIGSAW)){
				puzzleList.add(platform);
			}else if(StringUtils.equals(platform.getImgType(), Global.IMG_TYPE_CHILD_MAIN)){
				groupList.add(platform);
			}else if(StringUtils.equals(platform.getImgType(), Global.IMG_TYPE_COMPOSITE)){
				compositeList.add(platform);
			}
		}
		
		
		//获取组图（子G图）
		//List<Map<String, Object>> groupImgsList = designDrawingService.getGroupImgs(designDrawing,variantList);
		
		Map<String, List<TProductImgPlatform>> groupImgsMap = new LinkedHashMap<>();
		
		Map<String, Map<String,TProductImgPlatform>> groupImgMap = new LinkedHashMap<String, Map<String,TProductImgPlatform>>();
		for (TProductImgPlatform platform : groupList) {
			Map<String,TProductImgPlatform> tempMap = groupImgMap.get(platform.getParentId());
			if(tempMap == null){
				tempMap = new LinkedHashMap<String,TProductImgPlatform>();
			}
			tempMap.put(platform.getCodeManagerId(),platform);
			groupImgMap.put(platform.getParentId(), tempMap);
		}
//		List<Integer> indexList = new ArrayList<Integer>();
		for(String parentId : groupImgMap.keySet()){
			List<TProductImgPlatform> tempList = new ArrayList<TProductImgPlatform>();
			for(ProductCodeManager codeManager : variantList){
				Map<String,TProductImgPlatform> tempMap = groupImgMap.get(parentId);
				TProductImgPlatform p = tempMap.get(codeManager.getId());
				if(p!=null){
					if(StringUtils.isNoneBlank(p.getImgUrl())){
						p.setImgUrl(FastdfsHelper.getImageUrl()+p.getImgUrl());
					}
					tempList.add(p);
				}else{
					tempList.add(new TProductImgPlatform());
				}
			}
			groupImgsMap.put(parentId, tempList);
//			indexList.add(Integer.parseInt(parentId));
		}
		
//		if(indexList.size() < 9){
//			int max = Collections.max(indexList);
//			for(int i = max;i<(i+9);i++){
//				if(indexList.size() == 9){
//					break;
//				}else{
//					indexList.add(i);
//				}
//			}
//		}
		
		//获取所有详情图
		//List<Map<String, String>> detailImgsList = designDrawingService.getDetailImgsList(designDrawing);
		List<Map<String, String>> detailImgsList = new ArrayList<Map<String,String>>();
		for (TProductImgPlatform img : detailList) {
			//ProductImg productImg = productImgDao.get(img.getImgId());
			Map<String, String> map = new HashMap<String, String>();
			map.put("url", FastdfsHelper.getImageUrl()
					+ (img.getImgUrl() == null ? "" : img.getImgUrl()));
			map.put("id", img.getId());
			detailImgsList.add(map);
		}
		
		//获取所有主G图
		//List<Map<String, String>> mainImgsList = designDrawingService.getMainImgsList(designDrawing);
		List<Map<String, String>> mainImgsList = new ArrayList<Map<String,String>>();
		for (TProductImgPlatform img : mainList) {
			//ProductImg productImg = productImgDao.get(img.getImgId());
			Map<String, String> map = new HashMap<String, String>();
			map.put("url", FastdfsHelper.getImageUrl()
					+ (img.getImgUrl() == null ? "" : img.getImgUrl()));
			map.put("id", img.getId());
			mainImgsList.add(map);
		}
		
		//获取所有拼图
		List<Map<String, String>> puzzleImgsList = new ArrayList<Map<String,String>>();
		for (TProductImgPlatform img : puzzleList) {
			//ProductImg productImg = productImgDao.get(img.getImgId());
			Map<String, String> map = new HashMap<String, String>();
			map.put("url", FastdfsHelper.getImageUrl()
					+ (img.getImgUrl() == null ? "" : img.getImgUrl()));
			map.put("id", img.getId());
			puzzleImgsList.add(map);
		}
		
		//获取所有合成图
		List<Map<String, String>> compositeImgsList = new ArrayList<Map<String,String>>();
		for (TProductImgPlatform img : compositeList) {
			//ProductImg productImg = productImgDao.get(img.getImgId());
			Map<String, String> map = new HashMap<String, String>();
			map.put("url", FastdfsHelper.getImageUrl()
					+ (img.getImgUrl() == null ? "" : img.getImgUrl()));
			map.put("id", img.getId());
			compositeImgsList.add(map);
		}
				
		
		
		model.addAttribute("designDrawing", designDrawing);
		model.addAttribute("variantList", variantList);
		model.addAttribute("detailImgsList", detailImgsList);
		model.addAttribute("mainImgsList", mainImgsList);
		model.addAttribute("puzzleImgsList", puzzleImgsList);
		model.addAttribute("compositeImgsList", compositeImgsList);
		model.addAttribute("groupImgsMap", groupImgsMap);
		model.addAttribute("productCollection", productCollection);
		model.addAttribute("purchaseSourceList", purchaseSourceList);
		model.addAttribute("bean", bean);
//		model.addAttribute("indexList", indexList);
		return "ebay/designDrawing/designDrawingForm";
	}

	@RequestMapping(value = "save")
	@RepeatUrlData
	public String save(DesignDrawing designDrawing, Model model, RedirectAttributes redirectAttributes,ProcessBean<EbayProcessBusiness> bean) {
		//更新竞品状态
		String collectId = productCollectionService.findCollectIdByProductId(designDrawing.getId());
		ProductCollection productCollection = new ProductCollection();
		productCollection.setId(collectId);
		productCollection.setStatus(Global.PRODUCT_COLLECT_STATUS_DATA_PERFECT);
		productCollectionService.saveOrUpdate(productCollection);
			
		//为业务表实体设置数据
		EbayProcessBusiness ebayProcessBusiness = new EbayProcessBusiness();
		ebayProcessBusiness.setImgUrl(designDrawing.getImgUrl());
		ebayProcessBusiness.setProductName(designDrawing.getProductName());
		ebayProcessBusiness.setProductUrl(designDrawing.getProductLink());
		ebayProcessBusiness.setSysParentCode(designDrawing.getSysParentCode());
		ebayProcessBusiness.setSaleType(designDrawing.getSaleType());
		ebayProcessBusiness.setCreateName(UserUtils.getUser().getName());
		ebayProcessBusiness.setCreateTime(new Date());
		ebayProcessBusiness.setBusinessId(designDrawing.getId());
//			//将业务表实体设置到流程实体中
//			ProcessBean<EbayProcessBusiness> bean = new ProcessBean<EbayProcessBusiness>();
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put(Global.SALE_TYPE, designDrawing.getSaleType());
		bean.setData(ebayProcessBusiness);
		bean.setVars(vars);
		//完成流程
		generalProcessService.complete(bean);
		addMessage(redirectAttributes, "设计制图保存成功");
		if (Global.SALE_TYPE_VIRTUAL_OVERSEAS_STOREHOUSE.equals(designDrawing.getSaleType())) {
			return "redirect:"+Global.getAdminPath()+"/designdrawing/designDrawing/?type=0&repage";
		}else {
			return "redirect:"+Global.getAdminPath()+"/designdrawing/designDrawing/?type=1&repage";
		}
	}
	
	/**
	 * 图片上传（详情图和主G图）
	 * */
	@RequestMapping("uploadImgs")
	@ResponseBody
	public ProductImgDetil uploadImgs(MultipartFile file, String type,
			ModelAndView view, String productId) {
		if(file == null){
			return null;
		}
		Notify uploadFile = FastdfsHelper.uploadFile(file);
		String url = uploadFile.getRelativeUrl();
		String turl = uploadFile.getAbsoluteUrl();
		String id = designDrawingService.updateProductImg(url, type, productId);
		
		return new ProductImgDetil(id, turl);
	}
	

	/**
	 * 图片上传（子G图）
	 * */
	@RequestMapping("uploadSkuImgs")
	@ResponseBody
	public ProductImgDetil uploadSkuImgs(MultipartFile file, String type,
			ModelAndView view, String productId, String parentId,Integer index,String codeManagerId) {
		if(file == null){
			return null;
		}
		Notify uploadFile = FastdfsHelper.uploadFile(file);
		String url = uploadFile.getRelativeUrl();
		String turl = uploadFile.getAbsoluteUrl();
		String id = designDrawingService.updateSkuImg(url, type, productId, parentId, codeManagerId,index);
		
		return new ProductImgDetil(id, turl);
	}
	
	/**
	 * 新增一套图时，先插入一张主G图
	 * */
	/**
	@RequestMapping("insertMainImg")
	@ResponseBody
	public String insertMainImg(String type,String productId) {
		String imgId = designDrawingService.insertMainImg(type, productId);
		return imgId;
	}*/
	
	
	/**
	 * 删除图片
	 * @param productImg
	 * @param redirectAttributes
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteimg")
	public ImgFlag deleteimg(TProductImgPlatform tProductImgPlatform, RedirectAttributes redirectAttributes) {
		ImgFlag imgFlag = designDrawingService.deleteImg(tProductImgPlatform);
		return imgFlag;
	}
	
	
	/**
	 * 删除一套图片
	 * @param productImg
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteGroupImgs")
	public JsonResponseModel deleteGroupImgs(TProductImgPlatform tProductImgPlatform) {
		JsonResponseModel jsonResult = new JsonResponseModel();
		List<String> list = null;
		try {
			list = designDrawingService.deleteGroupImgs(tProductImgPlatform);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.Fail(e.getMessage());
			return jsonResult;
		}
 		jsonResult.Success("删除套图成功!", list);
		return jsonResult;
	}
	
	/**
	 * 解析文件夹，新增图片
	 * @param folderPath
	 * @param productId
	 * @return
	 * @throws IOException 
	 * @throws SmbException 
	 */
	@ResponseBody
	@RequestMapping(value = "parseFolder")
	public JsonResponseModel parseFolder(String folderPath , String productId) throws SmbException, IOException{
		JsonResponseModel jsonResult = new JsonResponseModel();
		String message = null;
		try {
			message = designDrawingService.parseFolder(folderPath, productId);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.Fail(e.getMessage());
			return jsonResult;
		}
		jsonResult.Success("文件解析成功！");
		return jsonResult;
	}
	
	/**
	 * 获取合成图当前页的数据(包含已生成的所有合成图代码与已选择的合成图代码)
	 * 
	 * @param productId
	 * @param currentPageNo 当前页数
	 * @return
	 */
	@RequestMapping("getCurrentPageData")
	public @ResponseBody Map<String, Object> getCurrentPageData(String productId) {
		// 获取当前页已生成的合成图代码集合，如果当前页代码数量不够则生成新的需要的数量
		List<String> allCodeForCurrentPageList = designDrawingService.getAllCodeForCurrentPage(productId);
		// 获取当前页已选择的合成图代码集合
		List<String> selectedCodeForCurrentPageList = designDrawingService.getSelectedCodeForCurrentPage(productId, allCodeForCurrentPageList);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("viewCodeList", allCodeForCurrentPageList);
		map.put("selectedCodeList", selectedCodeForCurrentPageList);
		return map;
	}
	
	@RequestMapping(value = "saveCompositeImg")
	public @ResponseBody Map<String,String> saveCompositeImg(String productId, String compositeCode) {
		Map<String,String> map = new HashMap<String, String>();
		
		ProductImg productImg = new ProductImg();
		productImg.setProductId(productId);
		productImg.setCompositeCode(compositeCode);
		
		map = designDrawingService.saveCompositeImg(productImg);
		return map;
	}
	
	@RequestMapping("viewImg")
	public void viewImg(String code, String productId,
			HttpServletResponse response) {
		final ServletOutputStream outputStream;
		try {
			outputStream = response.getOutputStream();
			designDrawingService.viewImg(code, productId, new ImageOutputCallBack() {
				@Override
				public OutputStream getOutputStream(String paramString) {
					return outputStream;
				}
			});
		} catch (Exception e) {
			// throw new RuntimeException(e.getMessage(),e);
			throw new RuntimeException(e);
		}
	}
	

}