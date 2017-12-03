package com.oigbuy.jeesite.modules.ebay.template.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.web.BaseController;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.PlatformSite;
import com.oigbuy.jeesite.modules.ebay.template.entity.ReturnPurchase;
import com.oigbuy.jeesite.modules.ebay.template.service.LocationofGoodsService;
import com.oigbuy.jeesite.modules.ebay.template.service.ReturnPurchaseService;
import com.oigbuy.jeesite.modules.sys.utils.UserUtils;

/**
 * 退货
 * @author yuxiang.xiong
 * 2017年9月5日 下午4:37:50
 */
@Controller
@RequestMapping(value = "${adminPath}/template/retpurchase")
public class ReturnPurchaseController extends  BaseController{
     
	@Autowired
	private ReturnPurchaseService returnPurchaseService;
	
	@Autowired
	private LocationofGoodsService locationofGoodsService;
	
	/**
	 * 退货列表
	 * @author yuxiang.xiong
	 * 2017年9月5日 下午6:00:58
	 * @param purchase
	 * @param request
	 * @param model
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "list",""})
	public String locationofGoods(ReturnPurchase purchase,HttpServletRequest request,Model model,HttpServletResponse response){
		
		Page<ReturnPurchase> page = returnPurchaseService.findPage(new Page<ReturnPurchase>(request, response), purchase);
		model.addAttribute("purchase", purchase);
		model.addAttribute("page", page);
		return "ebay/template/ReturnPurchaseList";
		
	}
	
	/**
	 * 退货新增、编辑
	 * @author yuxiang.xiong
	 * 2017年9月5日 下午6:01:07
	 * @param purchase
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "form")
	public String form(ReturnPurchase purchase, Model model,HttpServletRequest request,HttpServletResponse response){
		ReturnPurchase purchaseInfo = new ReturnPurchase();
		List<PlatformSite> list = locationofGoodsService.findsiteList();      //获取站点列表
		if("edit".equals(purchase.getFlag())){
			purchaseInfo = returnPurchaseService.get(purchase.getId());
		}
		purchaseInfo.setFlag(purchase.getFlag());
		model.addAttribute("purchase", purchaseInfo);
		model.addAttribute("sitelist", list);
		return "ebay/template/ReturnPurchaseForm";
	}
	
	/**
	 * 退货保存
	 * @author yuxiang.xiong
	 * 2017年9月5日 下午6:02:01
	 * @param purchase
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "save")
	public String save(ReturnPurchase purchase, Model model, RedirectAttributes redirectAttributes){
		
		if("edit".equals(purchase.getFlag())){
			purchase.setUpdateTime(new Date());
			purchase.setUpdateName(UserUtils.getUser().getLoginName());
			addMessage(redirectAttributes, "修改成功");
		}else{
			purchase.setCreateTime(new Date());
			purchase.setCreateName(UserUtils.getUser().getLoginName());
			addMessage(redirectAttributes, "新增成功");
		}
		returnPurchaseService.save(purchase);
		return "redirect:"+Global.getAdminPath()+"/template/retpurchase/list/?repage";
	}
	
	/**
	 * 删除
	 * @author yuxiang.xiong
	 * 2017年9月5日 下午6:02:41
	 * @param goods
	 * @param redirectAttributes
	 * @return
	 */
 	@RequestMapping(value = "delete")
	 public String delete(ReturnPurchase purchase,RedirectAttributes redirectAttributes){
 		returnPurchaseService.delete(purchase);
   		addMessage(redirectAttributes, "删除成功");
   		return "redirect:"+Global.getAdminPath()+"/template/retpurchase/list/?repage";
	 }
 	
 	/**
 	 * 校验模板名称是否重复
 	 * @author yuxiang.xiong
 	 * 2017年9月6日 下午3:46:48
 	 * @param templateName
 	 * @param flag
 	 * @return
 	 */
   	@RequestMapping(value="checkTempName")
   	@ResponseBody
   	public Boolean checkTempName(String templateName,String flag){
		
   		int num = returnPurchaseService.findCountBytemplateName(templateName);
   		Boolean msg = true;
		if("edit".equals(flag)){
			if(num>1){
				msg =  false;
			}
		}else{
			if(num>0){
				msg =  false;
			}
		}
		return msg;
   	}
   	
	/***
   	 * 编辑页面  ajax请求获取数据
   	 * 
   	 * @param id
   	 * @return
   	 */
   	@RequestMapping("getOne")
   	@ResponseBody
   	public ReturnPurchase geOne(String id){
   		return this.returnPurchaseService.get(id);
   	}
   	
   	
}
