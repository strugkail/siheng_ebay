package com.oigbuy.jeesite.modules.ebay.logistics.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.repeaturl.RepeatUrlData;
import com.oigbuy.jeesite.common.utils.StringUtils;
import com.oigbuy.jeesite.common.web.BaseController;
import com.oigbuy.jeesite.modules.country.service.CountryService;
import com.oigbuy.jeesite.modules.ebay.logistics.dao.LogisticsModeDao;
import com.oigbuy.jeesite.modules.ebay.logistics.entity.LogisticsMode;
import com.oigbuy.jeesite.modules.ebay.logistics.entity.SendToCountryParam;
import com.oigbuy.jeesite.modules.ebay.logistics.entity.ShippingMode;
import com.oigbuy.jeesite.modules.ebay.logistics.entity.ShippingTypeParam;
import com.oigbuy.jeesite.modules.ebay.logistics.service.LogisticsModeService;
import com.oigbuy.jeesite.modules.ebay.logistics.service.ShippingModeService;
import com.oigbuy.jeesite.modules.ebay.mode.service.ShieldCountryModeService;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.service.PlatformSiteService;
import com.oigbuy.jeesite.modules.parcel.service.ParcelService;

/**
 * 物流设置
 * @author handong.wang
 */
@Controller
@RequestMapping(value = "${adminPath}/logistics")
public class LogisticsController extends BaseController {

	@Autowired
	private LogisticsModeService logisticsModeService;
	@Autowired
	private PlatformSiteService platformSiteService;
	@Autowired
	private ParcelService parcelService;
	@Autowired
	private ShippingModeService shippingModeService;
	@Autowired
	private ShieldCountryModeService shieldCountryModeService;
	@Autowired
	private CountryService countryService;
	@Autowired
	private LogisticsModeDao logisticsModeDao;

	
	@ModelAttribute
	public LogisticsMode get(@RequestParam(required = false) String id) {
		LogisticsMode entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = logisticsModeService.get(id);
		}
		if (entity == null) {
			entity = new LogisticsMode();
		}
		return entity;
	}

	/**
	 * 物流设置列表
	 * 
	 * @author handong.wang
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("logistics:view")
	@RequestMapping(value = { "list", "" })
	public String list(LogisticsMode logisticsMode, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<LogisticsMode> page = logisticsModeService.findPage(
				new Page<LogisticsMode>(request, response), logisticsMode);
		model.addAttribute("page", page);
		return "modules/logistics/logisticsList";
	}

	/**
	 * 物流设置添加
	 * @author handong.wang
	 * @param logisticsMode
	 * @param model
	 * @return
	 */
	@RequiresPermissions("logistics:view")
	@RequestMapping(value = "form")
	public String form(LogisticsMode logisticsMode, Model model) {
		logisticsModeService.form(logisticsMode, model);
		return "modules/logistics/logisticsForm";
	}

	/**
	 * 物流设置编辑
	 * @author handong.wang
	 * @param logisticsMode
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RepeatUrlData
	@RequiresPermissions("logistics:edit")
	@RequestMapping(value = "save")
	public String save(LogisticsMode logisticsMode, Model model,
			RedirectAttributes redirectAttributes) {
		logger.info("save test");
		if (!beanValidator(model, logisticsMode)) {
			return form(logisticsMode, model);
		}
		logisticsModeService.saveAll(logisticsMode);
		addMessage(redirectAttributes, "保存物流设置成功");
		return "redirect:" + Global.getAdminPath() + "/logistics/?repage";
	}

	/**
	 * 物流设置删除
	 * @author handong.wang
	 * @param logisticsMode
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("logistics:edit")
	@RequestMapping(value = "delete")
	public String delete(LogisticsMode logisticsMode,RedirectAttributes redirectAttributes) {
		logisticsModeService.deleteAll(logisticsMode);
		addMessage(redirectAttributes, "删除物流设置成功");
		return "redirect:" + Global.getAdminPath() + "/logistics/?repage";
	}

	/**
	 * 物流设置，
	 * 1.更具站点简称获取发送至国家
	 * 2.更具站点简称获取境内,境外运输方式
	 * @author handong.wang
	 * @param siteName 站点简称
	 * @return
	 */
	@RequestMapping(value = "sendToCountry")
	@ResponseBody
	public ShippingMode sendToCountry(String siteName) {
		Long start=System.currentTimeMillis();
		 List<SendToCountryParam> sendToCountryList=logisticsModeService.getSendToCountry(siteName);
		 logger.info("sendToCountryList:"+(System.currentTimeMillis()-start));
		 List<ShippingTypeParam> shippingTypeInsideList=logisticsModeService.getShippingTypeInside(siteName);
		 List<ShippingTypeParam> shippingTypeOutsideList=logisticsModeService.getShippingTypeOutside(siteName);
		 logger.info("shippingTypeList:"+(System.currentTimeMillis()-start));
		 ShippingMode shippingMode=new ShippingMode();
		 
		 //物流设置，动态获取发送至国家
		 shippingMode.setSendToCountryList(sendToCountryList);
		 shippingMode.setShippingTypeInsideList(shippingTypeInsideList);
		 shippingMode.setShippingTypeOutsideList(shippingTypeOutsideList);
		 return shippingMode;
	}

	/**
	 * 校验模板的名称不能重复
	 * @author handong.wang
	 * @param modeName
	 * @return
	 */
	@RequestMapping(value = "modelNameJudgment")
	@ResponseBody
	public Boolean modelNameJudgment(String modeName) {
		List<LogisticsMode> list = logisticsModeDao.findListByModeName(modeName);
		if(null!=list&&list.size()>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 编辑页面 ajax请求获取数据
	 * @param id 物流模板id
	 * @return
	 */
	@RequestMapping("getOne")
	@ResponseBody
	public LogisticsMode geOne(String id) {
		LogisticsMode mode = logisticsModeService.findLogisticsMode(id);
		return mode;
	}

}
