/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.listing.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oigbuy.api.core.request.ebay.AddEbayItemRequest;
import com.oigbuy.api.core.response.ebay.AddEbayItemResponse;
import com.oigbuy.api.domain.ebay.ItemDto;
import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.fastdfs.notify.Notify;
import com.oigbuy.jeesite.common.fastdfs.util.FastdfsHelper;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.process.entity.EbayProcessBusiness;
import com.oigbuy.jeesite.common.process.entity.ProcessBean;
import com.oigbuy.jeesite.common.process.service.GeneralProcessService;
import com.oigbuy.jeesite.common.utils.DateUtils;
import com.oigbuy.jeesite.common.utils.JsonResponseModel;
import com.oigbuy.jeesite.common.utils.StringUtils;
import com.oigbuy.jeesite.common.utils.enums.SiteCurrencyEnum;
import com.oigbuy.jeesite.common.web.BaseController;
import com.oigbuy.jeesite.modules.ebay.listing.dto.ListingCodeManagerDto;
import com.oigbuy.jeesite.modules.ebay.listing.dto.ListingDetailDto;
import com.oigbuy.jeesite.modules.ebay.listing.entity.EbayListing;
import com.oigbuy.jeesite.modules.ebay.listing.entity.EbayListingImg;
import com.oigbuy.jeesite.modules.ebay.listing.service.EbayListingImgService;
import com.oigbuy.jeesite.modules.ebay.listing.service.EbayListingService;
import com.oigbuy.jeesite.modules.ebay.listing.service.EbaySkuMappingService;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.Platform;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.PlatformSite;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.service.PlatformService;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.service.PlatformSiteService;
import com.oigbuy.jeesite.modules.ebay.product.entity.Product;
import com.oigbuy.jeesite.modules.ebay.product.entity.TProductImgPlatform;
import com.oigbuy.jeesite.modules.ebay.product.service.ProductImgService;
import com.oigbuy.jeesite.modules.ebay.product.service.ProductService;
import com.oigbuy.jeesite.modules.ebay.product.service.TProductImgPlatformService;

/**
 * ebay listingController
 * @author bill.xu
 * @version 2017-09-22
 */
@Controller
@RequestMapping(value = "${adminPath}/listing/ebayListing")
public class EbayListingController extends BaseController {

	@Autowired
	private EbayListingService ebayListingService;
	
	
	/***
	 * listing  图片
	 */
	@Autowired
	private EbayListingImgService ebayListingImgService;
	
	/***
	 * 流程
	 */
	@Autowired
	private GeneralProcessService generalProcessService;
	
	
	/***
	 * 产品图片
	 */
	@Autowired
	private ProductImgService productImgService;
	
	@Autowired
	private ProductService productService;
	
	/***
	 * 平台信息
	 */
	@Autowired
	private PlatformService platformService;
	
	/***
	 * 站点信息
	 */
	@Autowired
	private PlatformSiteService platformSiteService;
	
	
	@Autowired
	private EbaySkuMappingService ebaySkuMappingService;
	
	
	@Autowired
	private TProductImgPlatformService tProductImgPlatformService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// 设置List的最大长度
		binder.setAutoGrowCollectionLimit(1000);
	}

	
	
	@ModelAttribute
	public EbayListing get(@RequestParam(required=false) String id) {
		EbayListing entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = ebayListingService.get(id);
		}
		if (entity == null){
			entity = new EbayListing();
		}
		return entity;
	}
	
	//@RequiresPermissions("listing:ebayListing:view")
	@RequestMapping(value = {"list", ""})
	public String list(EbayListing ebayListing, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<EbayListing> page = ebayListingService.findPage(new Page<EbayListing>(request, response), ebayListing); 
		
		model.addAttribute("page", page);
		return "modules/ebay/listing/ebayListingList";
	}

	//@RequiresPermissions("listing:ebayListing:view")
	@RequestMapping(value = "form")
	public String form(EbayListing ebayListing, Model model) throws Exception {
		Long begin=System.currentTimeMillis();
		logger.info("##########   加载 listing 详情数据  开始 {}",DateUtils.formatDateTime(begin));
		//刊登的分类还要时时去拿的，比对数据库中的数据 如果一样用数据库中的数据，如果不一样用 最新拿到的数据，如果有自定义数据 就用数据库中的自定义数据
		ListingDetailDto detailDto = this.ebayListingService.findDetailByListing(ebayListing.getId());
		model.addAttribute("listingDetail", detailDto);
		model.addAttribute("payMethodList", Global.EBAY_PAYMETHOD_LIST);
		
		model.addAttribute("currencyName", SiteCurrencyEnum.getCurrencyName(detailDto.getPlatformSite().getSiteShortName()));// 站点和相应的货币符号
		model.addAttribute("siteType", Global.ITALY_AND_FRANCE_SITE_NAME.contains(detailDto.getPlatformSite().getSiteName())?Global.ONE:Global.TWO);
		
		logger.info("##########   加载 listing 详情数据 结束时间为： {}，\t \t 耗时为：{}ms",DateUtils.formatDateTime(System.currentTimeMillis()),(System.currentTimeMillis()-begin));
		return "modules/ebay/listing/listingDetail";
		
	}

//	@RequiresPermissions("listing:ebayListing:edit")
	@RequestMapping(value = "save")
	public String save(EbayListing ebayListing, Model model, RedirectAttributes redirectAttributes) throws Exception {
		if (!beanValidator(model, ebayListing)){
			return form(ebayListing, model);
		}
		ebayListingService.save(ebayListing);
		addMessage(redirectAttributes, "保存ebay listing成功");
		return "redirect:"+Global.getAdminPath()+"/listing/ebayListing/?repage";
	}
	
//	@RequiresPermissions("listing:ebayListing:edit")
	@RequestMapping(value = "delete")
	public String delete(EbayListing ebayListing, RedirectAttributes redirectAttributes) {
		ebayListingService.delete(ebayListing);
		addMessage(redirectAttributes, "删除ebay listing成功");
		return "redirect:"+Global.getAdminPath()+"/listing/ebayListing/?repage";
	}

	
	

	
	
	/***
	 * 保存或则是进行刊登
	 * 
	 * @param detailDto
	 * @param flag
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("saveAndPublish")
	@ResponseBody
	public JsonResponseModel saveAndPublish(ListingDetailDto detailDto,String flag){
		
		JsonResponseModel result = new JsonResponseModel();
		
		try {
			// 校验敏感词 标题 描述等
			this.ebayListingService.updateListing(detailDto);//更新 listing 
			
			if(Global.ONE.equals(flag)){
				ebayListingService.savePublishListingData(detailDto);
			}
			return result.success(null);
		} catch (Exception e) {
			return result.fail("服务异常，"+e.getMessage());
		}
		
//		return "redirect:"+Global.getAdminPath()+"/listing/ebayListing/?repage";
	}
	
	
	

	/***
	 * 上传  listing 图片 
	 *  
	 * @param type
	 * @param listingId
	 * @param url
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertimg")
	public JsonResponseModel insertimg(String type,String listingId,String url) {
		JsonResponseModel resultModel = new JsonResponseModel();
		String newImgUrl = FastdfsHelper.uploadFile(url);
		if(StringUtils.isBlank(newImgUrl)){
			resultModel.Fail("图片上传失败！请售后重试");
			return resultModel;
		}
		EbayListingImg image =  ebayListingImgService.uploadImage(type,listingId,newImgUrl,null);
		resultModel.Success(JsonResponseModel.SUCCESS_MSG, image);
		return resultModel;
	}
	
	
	/**
	 * 删除主图，细节图, 拼图
	 * @param productImg
	 * @param redirectAttributes
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteimg")
	public JsonResponseModel deleteimg(String imageType,String listingId,String id, RedirectAttributes redirectAttributes) {
		JsonResponseModel jsonModel = new JsonResponseModel();
		
		boolean result = ebayListingImgService.deleteImage(id,listingId,imageType);
		if(result){
			jsonModel.Success(JsonResponseModel.SUCCESS_MSG, id);
		}else{
			jsonModel.Fail("已经是最后一张了，不能再删除了！");
		}
		return jsonModel;
	}
	

	
	/***
	 * 
	 * @param file
	 * @param type
	 * @param view
	 * @param productId
	 * @param parentId   主 g 图 id， 有则修改，没有新建
	 * @return
	 */
	@RequestMapping("uploadImgs")
	@ResponseBody
	public JsonResponseModel uploadImgs(MultipartFile file,String type, String listingId,String codeManagerId) {
		JsonResponseModel jsonResult = new JsonResponseModel();
		if(file == null){
			jsonResult.Fail("请选择图片上传！");
			return jsonResult;
		}
		Notify uploadFile = FastdfsHelper.uploadFile(file);
		EbayListingImg image = this.ebayListingImgService.uploadImage(type, listingId, uploadFile.getRelativeUrl(),codeManagerId);
		jsonResult.Success(JsonResponseModel.SUCCESS_MSG, image);
		return jsonResult;
	}
	
	/**
	 * review列表
	 * @param ebayListing
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "reviewListingList")
	public String reviewListingList(EbayListing ebayListing, HttpServletRequest request, HttpServletResponse response, 
									Model model,ProcessBean<EbayProcessBusiness> bean) {
		//从业务实体中获取所需字段的值
		EbayProcessBusiness buss = new EbayProcessBusiness();
		buss.setProcInsId(bean.getAct().getProcInsId());
		EbayProcessBusiness bussiness = generalProcessService.getByProcInsId(buss);
		ebayListing.setProductId(Long.valueOf(bussiness.getBusinessId()));
		//获取此产品下所有listing
		List<EbayListing> list = ebayListingService.findList(ebayListing);
		
		model.addAttribute("list", list);
		model.addAttribute("bean", bean);
		return "modules/ebay/listing/reviewListingList";
	}
	

	/***
	 * 
	 * 
	 * @param productId
	 * @param codeManagerId
	 * @param type
	 * @param model
	 * @return
	 */
	@RequestMapping("selectImage")
	public String selectImage(String listingId,String productId,String codeManagerId,String type,Model model){
		
		
		
		
		List<TProductImgPlatform> platformImageList = this.ebayListingImgService.getListingCanChooseImageList(listingId, productId, codeManagerId, type);
		
		
		model.addAttribute("imageList", platformImageList);
		
//		List<ProductImg> imageList = this.productImgService.findAllImageByProductId(productId,type,false,listingId);
//		model.addAttribute("imageList",imageList);
		model.addAttribute("listingId", listingId);
		model.addAttribute("productId", productId);
		model.addAttribute("codeManagerId", codeManagerId);
		model.addAttribute("type", type);
		return "modules/ebay/listing/selectImage";
	}


	/***
	 * 保存 listing 图片 
	 * 
	 * @param productId  产品 id 
	 * @param listingId   listing id 
	 * @param type   类型（主图 和 子图 只能一个，细节图和特效图可以多个）
	 * @param codeManagerId 
	 * @param ids  产品图片的id集合 （多个逗号分隔）
	 * @return
	 */
	@RequestMapping("saveListingImage")
	@ResponseBody
	public  JsonResponseModel  saveListingImage(Long productId,Long listingId,String type,Long codeManagerId,String ids){
		JsonResponseModel result=new JsonResponseModel();
		Long begin = System.currentTimeMillis();
		logger.info("#########   当前时间为 {} ，保存  listing 图片 ，listing ID 为{}, 图片类型为 {}，产品的id为{}，图片的 id集合为 {}",DateUtils.formatDateTime(new Date(begin)),listingId,type,productId,ids);
		List<EbayListingImg> imageList = this.ebayListingImgService.saveListingImage(type,productId,ids,codeManagerId,listingId);
		logger.info("#########   当前时间为 {} ，保存  listing 图片 ，执行完毕，耗时{} ms",DateUtils.formatDateTime(new Date(System.currentTimeMillis())),(System.currentTimeMillis()-begin));
		return result.success(imageList);
	}
	
	
	
	
	/***
	 * 查看 某一个产品的刊登详情 （在哪些店铺进行过刊登）总的刊登数量等信息
	 * 
	 * @param productId
	 * @return
	 */
	@RequestMapping("listingDetail")
	public String listingDetail(EbayListing ebayListing, HttpServletRequest request, HttpServletResponse response, Model model){
		Product product = this.productService.get(String.valueOf(ebayListing.getProductId()));
		if(product!=null){
			model.addAttribute("product",product);
			Page<EbayListing> page = ebayListingService.findPage(new Page<EbayListing>(request, response), ebayListing); 
			model.addAttribute("page", page);
			
			List<PlatformSite> siteList = this.platformSiteService.getListByPlatName(Global.TERRACE_TYPE_EBAY);
			List<Platform> platformList = platformService.getinfoByplatformName(Global.TERRACE_TYPE_EBAY);
			model.addAttribute("siteList", siteList);
			model.addAttribute("platformList", platformList);
		}
		return "modules/ebay/listing/ebayListingList2";
	}
	
	
	
	/***
	 * 产品下有 若干子代码，生成 listing 的时候可以不选择所有的子代码，这里进行 listing 修改操作的时候 可以选择 其他的 先前没有选择的 子代码进行添加 刊登 
	 * 
	 * @param listingId
	 * @param productId
	 * @return
	 */
	@RequestMapping("selectCodeManager")
	public String selectCodeManager(String listingId,String productId,String preCode,Model model){
		List<ListingCodeManagerDto> codeManagerList = this.ebaySkuMappingService.selectListingCodeManager(listingId,productId);
		model.addAttribute("codeManagerList", codeManagerList);
		model.addAttribute("preCode", preCode);
		model.addAttribute("afterCode", Global.SKU_CODE_MM);
		model.addAttribute("listingId",listingId);
		model.addAttribute("productId",productId);
		return "modules/ebay/listing/selectCodeManager";
		
	}
	
	
	/***
	 * listing  添加 子代码 
	 *  
	 * @param listingId
	 * @param productId
	 * @param ids  子代码的 id 多个用 逗号分隔开 
	 * @return
	 */
	@RequestMapping("addCodeManager")
	@ResponseBody
	public JsonResponseModel addCodeManager(String listingId,String productId,String ids,String preCode){
		JsonResponseModel resMode = new JsonResponseModel();
		try {
			List<ListingCodeManagerDto>  codeManagerList = this.ebaySkuMappingService.addCodeManager(listingId,productId,ids,preCode);
			resMode.Success(JsonResponseModel.SUCCESS_MSG, codeManagerList);
		} catch (Exception e) {
			logger.error("listing 添加 子代码 服务异常 ，错误信息为 ：{}", e.getMessage());
			resMode.Fail("listing 添加 子代码 服务异常 ，错误信息为 ："+ e.getMessage());
		}
		return resMode;
	}
	
	
	
	
	@RequestMapping("test")
	@ResponseBody
	public Object Test(String id) throws Exception{
		Map<Object, Object> result = new HashMap<Object, Object>();
		ItemDto listingByFunctionCall = this.ebayListingService.getListingByFunctionCall(id);
		AddEbayItemRequest addItemRequest = new AddEbayItemRequest();
		addItemRequest.setDto(listingByFunctionCall);
		AddEbayItemResponse execute = addItemRequest.execute();
		result.put("ItemDto", listingByFunctionCall);
		result.put("AddItemResponse", execute);
		return result;
	}
	
	
	
	
}
