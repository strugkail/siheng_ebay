package com.oigbuy.jeesite.modules.ebay.template.service;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.service.CrudService;
import com.oigbuy.jeesite.common.utils.StringUtils;
import com.oigbuy.jeesite.common.utils.enums.SiteCurrencyEnum;
import com.oigbuy.jeesite.modules.ebay.platformAndsite.entity.PlatformSite;
import com.oigbuy.jeesite.modules.ebay.template.dao.LocationofGoodsDao;
import com.oigbuy.jeesite.modules.ebay.template.entity.LocationofGoods;


@Service
@Transactional(readOnly = true)
public class LocationofGoodsService extends CrudService<LocationofGoodsDao, LocationofGoods>{

	@Autowired
	private LocationofGoodsDao locationofGoodsDao;
	
	public LocationofGoods get(String id) {
		return super.get(id);
	}

	public List<LocationofGoods> findList(LocationofGoods goods) {
		return super.findList(goods);
	}

	public Page<LocationofGoods> findPage(Page<LocationofGoods> page, LocationofGoods goods) {
		return super.findPage(page, goods);
	}
	
	
	public List<PlatformSite> findsiteList(){
		return locationofGoodsDao.findsiteList();
	}
	/**
	 * 根据模板名称查询数量
	 * @author yuxiang.xiong
	 * 2017年9月6日 下午2:06:41
	 * @param templateName
	 * @return
	 */
	public int findCountBytemplateName(String templateName){
		return locationofGoodsDao.findCountBytemplateName(templateName);
	}

	
	/***
	 * 自动选择 商品所在地模板 
	 * 
	 * 通过站点和销售类型  查找 
	 * 如果在英国站点下，还要匹配 用户的账号（店铺的账号） 
	 * 
	 * @param site  站点id
	 * @param saleType  销售类型
	 * @param sellerName  卖家账户名称（店铺名称）
	 * @return
	 */
	public LocationofGoods autoSelectLocationofGoods(PlatformSite site,String saleType, String sellerName) {

		List<LocationofGoods> locationofGoodsList = this.dao.findList(new LocationofGoods(site.getId(),saleType));
		LocationofGoods locationofGoods =null;
		
		if(CollectionUtils.isEmpty(locationofGoodsList)){
			return locationofGoods;
		}
		//如果是 英国 海外精品仓 ，还需要通过 账户 判断，其他不需要
		if(SiteCurrencyEnum.UK.getSiteName().equalsIgnoreCase(site.getSiteShortName()) && Global.SALE_TYPE_OVERSEAS_STOREHOUSE.equals(saleType)){
				Iterator<LocationofGoods> iterator = locationofGoodsList.iterator();
				while (iterator.hasNext()) {
					LocationofGoods type = iterator.next();
					if(StringUtils.isNotBlank(type.getSellerName()) && type.getSellerName().contains(sellerName)){
						locationofGoods = type;
						break;
				 }
			 }
		}else{
			locationofGoods=locationofGoodsList.get(0);
		}
		return locationofGoods;
	}
}
