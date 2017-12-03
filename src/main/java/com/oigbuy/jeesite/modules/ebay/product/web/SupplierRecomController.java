package com.oigbuy.jeesite.modules.ebay.product.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.utils.StringUtils;
import com.oigbuy.jeesite.common.web.BaseController;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductImg;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductImgDetil;
import com.oigbuy.jeesite.modules.ebay.product.entity.SupplierRecommen;
import com.oigbuy.jeesite.modules.ebay.product.service.SupplierRecomService;
import com.oigbuy.jeesite.modules.sys.utils.UserUtils;
import com.oigbuy.jeesite.common.fastdfs.notify.Notify;
import com.oigbuy.jeesite.common.fastdfs.util.FastdfsHelper;

/**
 * 供应商推荐（采购）
 * @author yuxiang.xiong
 * 2017年9月4日 上午9:51:07
 */
@Controller
@RequestMapping(value = "${adminPath}/product/productDev")
public class SupplierRecomController extends  BaseController {

	@Autowired
	private SupplierRecomService supplierRecomService;
	/**
	 * 供应商推荐列表
	 * @author yuxiang.xiong
	 * 2017年9月4日 上午9:51:21
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "supplier"})
	public String supplierRecom(SupplierRecommen supplier,HttpServletRequest request,HttpServletResponse response,Model model){
		
		Page<SupplierRecommen> page = supplierRecomService.findPage(
				new Page<SupplierRecommen>(request, response), supplier);
		String dateStart = request.getParameter("dateStart");
		String dateEnd = request.getParameter("dateEnd");
		model.addAttribute("page", page);
		model.addAttribute("supplier", supplier);
		model.addAttribute("dateStart", dateStart);
		model.addAttribute("dateEnd", dateEnd);
		return "ebay/product/supplierRecomList";
	}
			 
	/**
	 * 供应商添加、编辑\查看
	 * @author yuxiang.xiong
	 * 2017年9月4日 下午1:39:17
	 * @param supplier
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "form")
	public String form(SupplierRecommen supplier, Model model,HttpServletRequest request,HttpServletResponse response){
		SupplierRecommen supplierInfo = new SupplierRecommen();
		if("edit".equals(supplier.getFlag())||"view".equals(supplier.getFlag())){
		    supplierInfo = supplierRecomService.get(supplier.getId());
		}
		if(StringUtils.isBlank(supplier.getId())){
			supplierInfo.setId(Global.getID().toString());  
		}
		Map<String,Object> maplist = supplierRecomService.getProductDetails(supplier);    //修改获取图片
		supplierInfo.setFlag(supplier.getFlag());
		model.addAttribute("maplist", maplist);   //图片
		model.addAttribute("supplier", supplierInfo);
		return "ebay/product/supplierRecomForm";
	}
	

	/**
	 * 单图片上传
	 * */
	@RequestMapping("upload")
	@ResponseBody
	public ProductImgDetil uploadImg(MultipartFile file, String type,
			ModelAndView view, String productId) {
		Notify uploadFile = FastdfsHelper.uploadFile(file);
		String url = uploadFile.getAbsoluteUrl();
		String id = supplierRecomService.insertProductImg(url, type, productId);
		return new ProductImgDetil(id, url);
	}
	
	/**
	 * 批量图片上传
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
		String id = supplierRecomService.insertProductImg(url, type, productId);
		
		return new ProductImgDetil(id, turl);
	}
	
	/**
	 * 新增、编辑供应商推荐商品
	 * @author yuxiang.xiong
	 * 2017年9月4日 下午5:04:24
	 * @param supplier
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "save")
	public String save(SupplierRecommen supplier, Model model, RedirectAttributes redirectAttributes){
		
		if("edit".equals(supplier.getFlag())){
			supplier.setUpdateTime(new Date());
			supplier.setUpdateName(UserUtils.getUser().getName());
			addMessage(redirectAttributes, "修改成功");
			supplierRecomService.update(supplier);
		}else{
			supplier.setCreateTime(new Date());
			supplier.setCreateName(UserUtils.getUser().getName());
			addMessage(redirectAttributes, "新增成功");
			supplierRecomService.save(supplier);
		}
		return "redirect:"+Global.getAdminPath()+"/product/productDev/supplier/?repage";
	}
	
	/**
	 * 供应商推荐删除
	 * @author yuxiang.xiong
	 * 2017年9月4日 下午6:40:00
	 * @param supplier
	 * @return
	 */
   	@RequestMapping(value = "delete")
	 public String delete(SupplierRecommen supplier,RedirectAttributes redirectAttributes){
   		supplierRecomService.delete(supplier);
   		addMessage(redirectAttributes, "删除成功");
   		return "redirect:"+Global.getAdminPath()+"/product/productDev/supplier/?repage";
	 }
   	
   	/**
   	 * 链接上传图片
   	 * @author yuxiang.xiong
   	 * 2017年9月5日 上午11:25:37
   	 * @param type
   	 * @param view
   	 * @param productId
   	 * @param url
   	 * @return
   	 */
   	@ResponseBody
	@RequestMapping(value = "insertimg")
	public Map<String,String> insertimg(String type,
			ModelAndView view, String productId,String url) {
		Map<String,String> map = new HashMap<String, String>();
		// 1.根据原图地址生成本地图片地址
		String newImgUrl = FastdfsHelper.uploadFile(url);
		if(StringUtils.isBlank(newImgUrl)){
			return null;
		}
		String id = supplierRecomService.insertProductImg(newImgUrl, type, productId);
		map.put("url", FastdfsHelper.getImageUrl()+newImgUrl);
		map.put("id", id);
		return map;
	}
   	/**
   	 * 删除图片
   	 * @author yuxiang.xiong
   	 * 2017年9月5日 上午11:25:24
   	 * @param productImg
   	 * @param redirectAttributes
   	 * @return
   	 */
	@ResponseBody
	@RequestMapping(value = "deleteimg")
	public String deleteimg(ProductImg productImg, RedirectAttributes redirectAttributes) {
		supplierRecomService.deleteImg(productImg);
		return productImg.getId();
	}
}
