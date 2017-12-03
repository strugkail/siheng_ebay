package com.oigbuy.jeesite.modules.ebay.template.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.oigbuy.jeesite.modules.country.entity.Country;
import com.oigbuy.jeesite.modules.country.service.CountryService;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.PlatformSite;
import com.oigbuy.jeesite.modules.ebay.template.entity.LocationofGoods;
import com.oigbuy.jeesite.modules.ebay.template.service.LocationofGoodsService;
import com.oigbuy.jeesite.modules.sys.utils.UserUtils;

/**
 * 商品所在地
 * @author yuxiang.xiong
 * 2017年9月5日 下午2:18:41
 */
@Controller
@RequestMapping(value = "${adminPath}/template/goods")
public class LocationofGoodsController extends BaseController {

	@Autowired
	private LocationofGoodsService locationofGoodsService;

	@Autowired
	private CountryService countryService;
	
	/**
	 * 商品所在地列表
	 * @author yuxiang.xiong
	 * 2017年9月5日 下午2:18:25
	 * @param goods
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "list",""})
	public String locationofGoods(LocationofGoods goods,HttpServletRequest request,Model model,HttpServletResponse response){
		
		Page<LocationofGoods> page = locationofGoodsService.findPage(new Page<LocationofGoods>(request, response), goods);
		model.addAttribute("goods", goods);
		model.addAttribute("page", page);
		return "ebay/template/LocationofGoodsList";
		
	}
	
	/**
	 * 新增、编辑商品所在地
	 * @author yuxiang.xiong
	 * 2017年9月5日 下午2:18:10
	 * @param goods
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "form")
	public String form(LocationofGoods goods, Model model,HttpServletRequest request,HttpServletResponse response){
		LocationofGoods goodsInfo = new LocationofGoods();
		List<PlatformSite> list = locationofGoodsService.findsiteList();   //获取站点列表
		List<Country> countryList = countryService.findList(null);   // 获取国家列表
		if("edit".equals(goods.getFlag())){
			goodsInfo = locationofGoodsService.get(goods.getId());
		}
		goodsInfo.setFlag(goods.getFlag());
		model.addAttribute("goods", goodsInfo);
		model.addAttribute("sitelist", list);
		model.addAttribute("countryList", countryList);
		return "ebay/template/LocationofGoodsForm";
	}
	
	/**
	 * 保存
	 * @author yuxiang.xiong
	 * 2017年9月5日 下午4:05:04
	 * @param goods
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "save")
	public String save(LocationofGoods goods, Model model, RedirectAttributes redirectAttributes){
		
		if("edit".equals(goods.getFlag())){
			goods.setUpdateTime(new Date());
			goods.setUpdateName(UserUtils.getUser().getLoginName());
			addMessage(redirectAttributes, "修改成功");
		}else{
			goods.setCreateTime(new Date());
			goods.setCreateName(UserUtils.getUser().getLoginName());
			addMessage(redirectAttributes, "新增成功");
		}
		locationofGoodsService.save(goods);
		return "redirect:"+Global.getAdminPath()+"/template/goods/list/?repage";
	}
	
	/**
	 * 删除
	 * @author yuxiang.xiong
	 * 2017年9月5日 下午4:07:21
	 * @param goods
	 * @param redirectAttributes
	 * @return
	 */
   	@RequestMapping(value = "delete")
	 public String delete(LocationofGoods goods,RedirectAttributes redirectAttributes){
   		locationofGoodsService.delete(goods);
   		addMessage(redirectAttributes, "删除成功");
   		return "redirect:"+Global.getAdminPath()+"/template/goods/list/?repage";
	 }
   	
   	/**
   	 * 校验模板名称
   	 * @author yuxiang.xiong
   	 * 2017年9月6日 下午2:06:14
   	 * @param templateName
   	 * @param flag
   	 * @return
   	 */
   	@RequestMapping(value="checkTempName")
   	@ResponseBody
   	public Boolean checkTempName(String templateName,String flag){
		
   		int num = locationofGoodsService.findCountBytemplateName(templateName);
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
   	public LocationofGoods geOne(String id){
   		LocationofGoods goods=this.locationofGoodsService.get(id);
   		return goods;
   	}
   	
   	
}
