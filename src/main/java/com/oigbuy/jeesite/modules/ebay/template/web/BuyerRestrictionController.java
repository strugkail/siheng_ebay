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
import com.oigbuy.jeesite.modules.ebay.template.entity.BuyerRestriction;
import com.oigbuy.jeesite.modules.ebay.template.service.BuyerRestrictionService;
import com.oigbuy.jeesite.modules.ebay.template.service.LocationofGoodsService;
import com.oigbuy.jeesite.modules.sys.utils.UserUtils;

/**
 * 买家限制
 * 
 * @author yuxiang.xiong 2017年9月6日 上午9:54:24
 */
@Controller
@RequestMapping(value = "${adminPath}/template/buyer")
public class BuyerRestrictionController extends BaseController {

	@Autowired
	private BuyerRestrictionService buyerRestrictionService;
	
	@Autowired
	private LocationofGoodsService locationofGoodsService;

	/**
	 * 买家限制列表
	 * 
	 * @author yuxiang.xiong 2017年9月6日 上午10:32:08
	 * @param restriction
	 * @param request
	 * @param model
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "list", "" })
	public String locationofGoods(BuyerRestriction restriction,
			HttpServletRequest request, Model model,
			HttpServletResponse response) {

		Page<BuyerRestriction> page = buyerRestrictionService.findPage(
				new Page<BuyerRestriction>(request, response), restriction);
		model.addAttribute("restriction", restriction);
		model.addAttribute("page", page);
		return "ebay/template/BuyerRestrictionList";

	}

	/**
	 * 买家限制新增、编辑
	 * 
	 * @author yuxiang.xiong 2017年9月6日 上午10:33:41
	 * @param restriction
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "form")
	public String form(BuyerRestriction restriction, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		BuyerRestriction restrictionInfo = new BuyerRestriction();
		List<PlatformSite> list = locationofGoodsService.findsiteList();      //获取站点列表
		if ("edit".equals(restriction.getFlag())) {
			restrictionInfo = buyerRestrictionService.get(restriction.getId());
		}
		restrictionInfo.setFlag(restriction.getFlag());
		model.addAttribute("restriction", restrictionInfo);
		model.addAttribute("sitelist", list);
		return "ebay/template/BuyerRestrictionForm";
	}

	/**
	 * 买家限制保存
	 * 
	 * @author yuxiang.xiong 2017年9月6日 上午10:37:27
	 * @param restriction
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "save")
	public String save(BuyerRestriction restriction, Model model,
			RedirectAttributes redirectAttributes) {

		if ("edit".equals(restriction.getFlag())) {
			restriction.setUpdateTime(new Date());
			restriction.setUpdateName(UserUtils.getUser().getLoginName());
			addMessage(redirectAttributes, "修改成功");
		} else {
			restriction.setCreateTime(new Date());
			restriction.setCreateName(UserUtils.getUser().getLoginName());
			addMessage(redirectAttributes, "新增成功");
		}
		buyerRestrictionService.save(restriction);
		return "redirect:" + Global.getAdminPath()
				+ "/template/buyer/list/?repage";
	}
    /**
     * 删除
     * @author yuxiang.xiong
     * 2017年9月6日 下午3:08:31
     * @param restriction
     * @param redirectAttributes
     * @return
     */
	@RequestMapping(value = "delete")
	public String delete(BuyerRestriction restriction,
			RedirectAttributes redirectAttributes) {
		buyerRestrictionService.delete(restriction);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:" + Global.getAdminPath()
				+ "/template/buyer/list/?repage";
	}
	
	/**
	 * 校验模板名称是否重复
	 * @author yuxiang.xiong
	 * 2017年9月6日 下午3:46:21
	 * @param templateName
	 * @param flag
	 * @return
	 */
   	@RequestMapping(value="checkTempName")
   	@ResponseBody
   	public Boolean checkTempName(String templateName,String flag){
		
   		int num = buyerRestrictionService.findCountBytemplateName(templateName);
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
   	public BuyerRestriction geOne(String id){
   		BuyerRestriction buyer = this.buyerRestrictionService.get(id);
   		buyer.setPaypalAccount(Global.YES.equals(buyer.getPaypalAccount())?Global.CHINESE_YES:Global.CHINESE_NO);
   		return  buyer;
   	}
   	
}
