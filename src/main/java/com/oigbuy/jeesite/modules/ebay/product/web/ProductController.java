package com.oigbuy.jeesite.modules.ebay.product.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.fastdfs.notify.Notify;
import com.oigbuy.jeesite.common.fastdfs.util.FastdfsHelper;
import com.oigbuy.jeesite.common.process.entity.EbayProcessBusiness;
import com.oigbuy.jeesite.common.process.service.GeneralProcessService;
import com.oigbuy.jeesite.common.utils.JsonResponseModel;
import com.oigbuy.jeesite.common.utils.StringUtils;
import com.oigbuy.jeesite.common.web.BaseController;
import com.oigbuy.jeesite.modules.act.entity.Act;
import com.oigbuy.jeesite.modules.ebay.designdrawing.service.DesignDrawingService;
import com.oigbuy.jeesite.modules.ebay.logistics.service.LogisticsModeService;
import com.oigbuy.jeesite.modules.ebay.mode.service.PublishStyleModeService;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.service.PlatformSiteService;
import com.oigbuy.jeesite.modules.ebay.product.entity.Product;
import com.oigbuy.jeesite.modules.ebay.product.entity.TProductImgPlatform;
import com.oigbuy.jeesite.modules.ebay.product.service.ProductCodeManagerService;
import com.oigbuy.jeesite.modules.ebay.product.service.ProductImgService;
import com.oigbuy.jeesite.modules.ebay.product.service.ProductService;
import com.oigbuy.jeesite.modules.ebay.product.service.TProductImgPlatformService;
import com.oigbuy.jeesite.modules.ebay.template.service.BuyerRestrictionService;
import com.oigbuy.jeesite.modules.ebay.template.service.LocationofGoodsService;
import com.oigbuy.jeesite.modules.ebay.template.service.ReturnPurchaseService;
import com.sho.tool.build.title.KeyWordBean;
import com.sho.tool.build.title.TitleBuild;
import com.sho.tool.build.title.WordBuildResult;

/**
 * 
 * @author tony.liu
 */
@Controller
@RequestMapping(value = "${adminPath}/ebay/product")
public class ProductController extends BaseController {

	@Autowired
	private ProductService productService;
	
	/***
	 * 刊登风格
	 */
	@Autowired
	private PublishStyleModeService publishStyleModeService;
	
	/***
	 * 产品图片
	 */
	@Autowired
	private ProductImgService productImgService;
	/**平台站点*/
	@Autowired
	private PlatformSiteService platformSiteService;
	/***
	 * 物流模板设值
	 */
	@Autowired
	private LogisticsModeService logisticsModeService;
	/***
	 * 商品所在地
	 */
	@Autowired
	private LocationofGoodsService locationofGoodsService;
	/***
	 * 买家限制
	 */
	@Autowired
	private BuyerRestrictionService buyerRestrictionService;
	/***
	 * 退货
	 */
	@Autowired
	private ReturnPurchaseService returnPurchaseService;
	/**
	 * 子代码
	 */
	@Autowired
	private ProductCodeManagerService productCodeManagerService;
	/***
	 * 设计作图模板
	 */
	@Autowired
	private DesignDrawingService designDrawingService;
	/**
	 * 流程
	 */
	@Autowired
	private GeneralProcessService generalProcessService;

	
	/***
	 * 产品图片关联
	 */
	@Autowired
	private TProductImgPlatformService tProductImgPlatformService;
	
	
	@ModelAttribute
	public Product get(@RequestParam(required = false) String id) {
		Product entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = productService.get(id);
		}
		if (entity == null) {
			entity = new Product();
		}
		return entity;
	}

	/**
	 * 已完成开发的产品列表
	 * 
	 * @param product
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequiresPermissions("ebay:product:view")
	@RequestMapping(value = { "list", "" })
	public String getList(Product product, HttpServletRequest request,
			HttpServletResponse response, Model model,EbayProcessBusiness business) throws Exception {
		product.setType("1");// 已完成开发的产品
//		Page<Product> page = productService.findPage(new Page<Product>(request, response), product);
		business.setProductName(product.getName());
		business.setSaleType(product.getDevelopmentType());
		List<Act> list = generalProcessService.todoList(business);
		//获取开发时间区间
		String dateStart = request.getParameter("dateStart");
		String dateEnd = request.getParameter("dateEnd");
		
//		model.addAttribute("page", page);
		model.addAttribute("list", list);
		model.addAttribute("tagsList", null);
		model.addAttribute("speTagsList", null);
		model.addAttribute("dateStart", dateStart);
		model.addAttribute("dateEnd", dateEnd);
		model.addAttribute("business", business);
		return "modules/ebay/product/productList";
	}



//	@RequestMapping("calProfit")
//	@ResponseBody
//	public ProductProfit calProfit(Model model, ProductProfit productProfit) {
//		productProfit = productService.calProfit(productProfit);
//		model.addAttribute("productProfit", productProfit);
//		return productProfit;
//	}
	
	
	
	/***
	 * 上传图片（根据输入的图片地址进行上传）
	 * @param productId  产品id
	 * @param url   图片url
	 * @param type 图片类型
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertimg")
	public JsonResponseModel insertimg(String productId,String url,String type,String codeManagerId) {
		
		JsonResponseModel result = new JsonResponseModel();
		// 1.根据原图地址生成本地图片地址
		String newImgUrl = FastdfsHelper.uploadFile(url);
		if(StringUtils.isBlank(newImgUrl)){
			return result.fail("上传图片地址不正确，请重新上传！");
		}
		TProductImgPlatform imagePlatform = this.tProductImgPlatformService.saveUploadImage(productId,newImgUrl,type,codeManagerId,Global.PLATFORM_FLAG_EBAY);
		return result.success(imagePlatform);
	}
	

	
	/***
	 * 删除图片 关系表
	 * 
	 * @param productId
	 * @param id
	 * @param imgType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteimg")
	public String deleteimg(String productId,String id,String imgType) {
		TProductImgPlatform tProductImgPlatform = this.tProductImgPlatformService.get(id);
	    this.tProductImgPlatformService.delete(tProductImgPlatform);
		return id;
	}
	
	/***
	 * 删除图片 主表图片
	 * 
	 * @param productId
	 * @param id
	 * @param imgType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteProductImg")
	public JsonResponseModel deleteProductImg(String productId,String id) {
		JsonResponseModel json = new JsonResponseModel();
		boolean result = this.tProductImgPlatformService.deleteById(id);
		if(result){
		 return json.success(id);
		}
		return	json.fail("该图片还有引用，不能删除！");
	}
	

	
	/***
	 * 
	 * @param file
	 * @param productId
	 * @return
	 */
	@RequestMapping("uploadImgs")
	@ResponseBody
	public JsonResponseModel uploadImgs(MultipartFile file,String productId,String type,String codeManagerId) {
		JsonResponseModel result = new JsonResponseModel();
		if(file == null){
			return result.fail("请选择图片再进行上传！");
		}
		Notify uploadFile = FastdfsHelper.uploadFile(file);
		TProductImgPlatform imagePlatform = this.tProductImgPlatformService.saveUploadImage(productId,uploadFile.getRelativeUrl(),type,codeManagerId,Global.PLATFORM_FLAG_EBAY);
		return result.success(imagePlatform);
	}

	
	
	/***
	 * 构建标题
	 * 
	 * @param keyName   关键字
	 * @param endName  其他关键字
	 * @return
	 */
	@RequestMapping("generateTitle")
	@ResponseBody
	public List<WordBuildResult> generateTitle(String keyName, String endName) {
		// 先获取第二关键字
		List<String> secondList = new ArrayList<String>();
//		for (Dict d : DictUtils.getDictList("secondKeyWord")) {
//			secondList.add(d.getValue());
//		}
		String[] commonKeys = Global.toStringArray(secondList);
		String[] keyArray = keyName.split(Global.SEPARATE_6);
		if(StringUtils.isNotBlank(endName)){
			String[] endNameArray = endName.split(Global.SEPARATE_6);
			Random rand = new Random();
			int i = rand.nextInt(endNameArray.length);
			if(i<0){i=0;}
			endName=endNameArray[i];
		}
		int i = 0;
		List<KeyWordBean> keysList = new ArrayList<KeyWordBean>();
		for (String k : keyArray) {
			keysList.add(new KeyWordBean(k, i));
			i++;
		}
		KeyWordBean[] keys = Global.toKeyWordArray(keysList);
		List<WordBuildResult> result = TitleBuild.buildTitle("",endName,keys,commonKeys);
//		Iterator<WordBuildResult> it=result.iterator();
//		return it.next();
		return result;
	}
	
	/***
	 * 选择 主 g 图模板  弹出页面
	 * 
	 * @param productId  产品id
	 * @param parentId  主 g 图 的 id 
	 * @return
	 */
	@RequestMapping("chooseImage")
	public String chooseImage(String productId,Model model){
		Map<String, List<TProductImgPlatform>> productImageMap = this.productImgService.findTemByProductId(productId);
		model.addAttribute("mainImageList", productImageMap.get(Global.IMAGE_MAIN_KEY));
		productImageMap.remove(Global.IMAGE_MAIN_KEY);// 移除 主 g 图的集合
		model.addAttribute("productImageMap", productImageMap);//全部都是子 g 图 套图的 map
		model.addAttribute("productId", productId);
		return "modules/ebay/product/imageChoose";
	}
	
	/***
	 * 选择模板 图片的时候要进行保存对应到 image 中
	 * 
	 * @param idp  选择的主 g 图 id
	 * @param parentId  选择的  子 g图的 parentId 
	 * @param productId  产品 
	 * @param model
	 * @return
	 */
	@RequestMapping("bindImage")
	@ResponseBody
	public JsonResponseModel bindImage(String idp,String parentId,String productId,Model model){
		JsonResponseModel result = new JsonResponseModel();
		try {
			Map<String, Object> imageMap = tProductImgPlatformService.findDrawingList(idp,parentId,productId);
			return result.success(imageMap);
		} catch (Exception e) {
		   return result.fail("选择套图处理异常："+e.getMessage());
		}
	}
	
	
	/**
	 * review产品列表
	 * 
	 * @param product
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequiresPermissions("ebay:product:view")
	@RequestMapping(value = "review")
	public String getReviewList(Product product, HttpServletRequest request,
			HttpServletResponse response, Model model,EbayProcessBusiness business) throws Exception {
		product.setType("1");// 已完成开发的产品
//		Page<Product> page = productService.findPage(new Page<Product>(request, response), product);
		business.setProductName(product.getName());
		business.setSaleType(product.getDevelopmentType());
		List<Act> list = generalProcessService.todoList(business);
		//获取开发时间区间
		String dateStart = request.getParameter("dateStart");
		String dateEnd = request.getParameter("dateEnd");
		
//		model.addAttribute("page", page);
		model.addAttribute("list", list);
		model.addAttribute("tagsList", null);
		model.addAttribute("speTagsList", null);
		model.addAttribute("dateStart", dateStart);
		model.addAttribute("dateEnd", dateEnd);
		return "modules/ebay/product/productReviewList";
	}
	
	
	
	
	
	
	/***
	 * 查询 产品图片可 供选择的集合 
	 * 
	 * @param productId
	 * @param codeManagerId
	 * @param type
	 * @param model
	 * @return
	 */
	@RequestMapping("selectImage")
	public String selectImage(String productId,String codeManagerId,String type,Model model){
		
		TProductImgPlatform tProductImgPlatform = new TProductImgPlatform();
		if(StringUtils.isNotBlank(codeManagerId)){
			tProductImgPlatform.setCodeManagerId(codeManagerId);
		}
		tProductImgPlatform.setProductId(productId);
		
		boolean isMain = Global.IMG_TYPE_MAIN.equals(type);
		//如果是 主 g 图 还包含 类型为 6 的拼图
		tProductImgPlatform.setImgType(isMain?Global.SEPARATE_13:type);
		List<TProductImgPlatform> platformImageList = this.tProductImgPlatformService.findList(tProductImgPlatform);
		if(isMain && CollectionUtils.isNotEmpty(platformImageList)){
			List<TProductImgPlatform>  mainImageList = new ArrayList<TProductImgPlatform>();
			platformImageList.forEach(img->{
				if(Global.IMG_TYPE_MAIN.equals(img.getImgType()) || Global.IMG_TYPE_COMPOSITE.equals(img.getImgType())){
					mainImageList.add(img);
				}
			});
			model.addAttribute("imageList", mainImageList);
		}else{
			model.addAttribute("imageList", platformImageList);
		}
		model.addAttribute("type", type);
		model.addAttribute("productId", productId);
		model.addAttribute("codeManagerId", codeManagerId);
		return "modules/ebay/product/selectImage";
	}
	
	
	/***
	 * 保存产品图片关系
	 * 
	 * @param type 图片类型
	 * @param productId  产品id
	 * @param ids 选择的图片id集合
	 * @return
	 */
	@RequestMapping("saveProductImage")
	@ResponseBody
	public JsonResponseModel saveProductImage(String type,String productId,String ids,String codeManagerId){
		JsonResponseModel result=new JsonResponseModel();
		List<TProductImgPlatform> imageList = this.tProductImgPlatformService.saveProductImage(type,productId,ids,codeManagerId);
		return result.success(imageList);
	}
}