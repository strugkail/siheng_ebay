package com.oigbuy.jeesite.common.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.oigbuy.jeesite.common.utils.enums.SiteCurrencyEnum;
import com.oigbuy.jeesite.modules.ebay.ebayPaypal.entity.EbayPaypal;
import com.oigbuy.jeesite.modules.ebay.product.entity.Product;
import com.oigbuy.jeesite.modules.ebay.product.entity.ProductCodeManager;

/***
 * listing  刊登价格计算工具类 
 * @author bill.xu
 *
 */
public class ListingPublishPriceUtils {


	/**处理费 1.00 人民币 */
	private static final BigDecimal DEAL_PRICE = new BigDecimal(1.00);
	
	/**财务费率 */
	private static final BigDecimal FINANCE_RATES =  new BigDecimal(2.50/100);//

	/**仓储费(元/kg/月) */
	private static final BigDecimal STORAGE_CHARGES = new BigDecimal(2.50);//
	
	/**库存周转天数 */
	private static final BigDecimal TURNOVER_DAYS = new BigDecimal(30.00);// 
	
	/**库存利息率 */
	private static final BigDecimal STOCK_INTEREST_RATE = new BigDecimal(0.05/100);// 
	
	/**损失率*/
	private static final BigDecimal LOSS_RATE = new BigDecimal(5.00/100);// 
	
	/**美元对人民币汇率*/
	private static final BigDecimal DOLLAR_EXCHANGE_RATE = new BigDecimal(6.62);// 
	
	/**英镑对美元汇率*/
	private static final BigDecimal POUND_TO_DOLLAR_EXCHANGE_RATE =new BigDecimal(0.74);// 

	/**加元对美元汇率 */
	private static final BigDecimal CANADIAN_TO_DOLLAR_EXCHANGE_RATE = new BigDecimal(1.25);// 
	
	/**澳元兑美元汇率*/
	private static final BigDecimal AUSTRALIAN_DOLLAR_EXCHANGE_RATE = new BigDecimal(1.26);// 
	
	/**计算的常量 1.035 */
	private static final BigDecimal ONE_POINT_ZERO_THREE_FIVE = new BigDecimal(1.035);// 
	
	/**eub 的每公斤重量价格*/
	private static final BigDecimal EUB_TRANS_PRICE_BASE = new BigDecimal(74.00);// 
	
	/**eub 的 附加价格*/
	private static final BigDecimal EUB_TRANS_PRICE_EXTRA = new BigDecimal(10.00);// 
	
	/**平邮每公斤的价格 */
	private static final BigDecimal COMMON_TRANS_PRICE = new BigDecimal(85.00);
	
	/**1000 */
	private static final BigDecimal ONE_THOUSAND = new BigDecimal(1000);
	
	/***
	 * X=(（商品成本+运费+处理费+仓储费+库存利息）/汇率+papyal小额额外费)/(1-ebay率-palpayl率-利润率-损失费率-财务费率)
	 * 
	 * 计算的价格 赋值在 productCodeManager 中
	 * 
	 * 
	 * @param productCodeManager
	 * @param paypal 
	 * @param siteName 
	 * @param feeRate 
	 * @param flag 
	 * @return
	 */
	public static ProductCodeManager getPrice(ProductCodeManager productCodeManager, String siteName, EbayPaypal paypal, String feeRate,BigDecimal profitRate,boolean flag) {
		//商品成本=采购成本*1.035
		BigDecimal costPrice = ONE_POINT_ZERO_THREE_FIVE.multiply(new BigDecimal(productCodeManager.getCostPrice()));
		//重量 kg
		BigDecimal weight = new BigDecimal(productCodeManager.getWeight()).divide(ONE_THOUSAND);
		//运费 平邮 平邮运费=每公斤*重量/1000/汇率
		BigDecimal transPrice1 = COMMON_TRANS_PRICE.multiply(weight);
		//运费EUB  EUB运费=（每公斤+重量/1000+附加费）/汇率
		BigDecimal transPrice2 = EUB_TRANS_PRICE_BASE.multiply(weight).add(EUB_TRANS_PRICE_EXTRA);
		//仓储费=重量/ 1000 * 仓储费(元/kg/月) / 30 * 库存周转天数 / 汇率  
		BigDecimal storePrice=weight.multiply(STORAGE_CHARGES);
		//库存利息= 采购成本*1.035 * 库存利息率 *库存周转天数 /汇率
		BigDecimal inventoryInterest = costPrice.multiply(STOCK_INTEREST_RATE).multiply(TURNOVER_DAYS);
		//先进行小额的计算，如果金额  和 临界点的金额进行比较 ，如果 大于临界点 则用大额进行计算 
		//临界值
		BigDecimal  boundaries = new BigDecimal(paypal.getBoundaries());
		//ebay fee
		BigDecimal fee = new BigDecimal(feeRate);
		
		BigDecimal transPrice = transPrice1;
		
		BigDecimal paypal1=BigDecimal.ZERO;
		BigDecimal paypal2=BigDecimal.ZERO;
		BigDecimal result =BigDecimal.ZERO;
				
		if(flag){//大额计算
			productCodeManager.setBigPaypal(true);//大额 paypal
			paypal1 = new BigDecimal(paypal.getPaypalEachCost());
			paypal2 = new BigDecimal(paypal.getPaypalDiscount());
			result = getPublishPrice(costPrice,transPrice,storePrice,inventoryInterest,paypal1,fee,paypal2,profitRate,siteName);
		
		}else{//小额计算，和默认的第一次计算
			
			productCodeManager.setBigPaypal(false);
			paypal1= new BigDecimal(paypal.getSmallpaypalEachCost());
			paypal2= new BigDecimal(paypal.getSmallpaypalDiscount());
			//默认 小额 平邮计算
			result = getPublishPrice(costPrice,transPrice,storePrice,inventoryInterest,paypal1,fee,paypal2,profitRate,siteName);
			//X<=临界值：X=X	否则 X=大额售价公式计算     (如果有一个是大额的paypal 则其他的都是 大额的paypal)
			if(result.compareTo(boundaries)>0){
				productCodeManager.setBigPaypal(true);//大额 paypal
				paypal1 = new BigDecimal(paypal.getPaypalEachCost());
				paypal2 = new BigDecimal(paypal.getPaypalDiscount());
				result = getPublishPrice(costPrice,transPrice,storePrice,inventoryInterest,paypal1,fee,paypal2,profitRate,siteName);
			}
		}
		//如果是 us 则先进行平邮计算，如果计算结果大于  5美金 则使用 eub 计算，其他都是 平邮计算 
		if(SiteCurrencyEnum.US.getSiteName().equalsIgnoreCase(siteName) && result.compareTo(new BigDecimal(5))>=0){
			transPrice = transPrice2;
			result = getPublishPrice(costPrice,transPrice,storePrice,inventoryInterest,paypal1,fee,paypal2,profitRate,siteName);
		}
		//result = result.add(new BigDecimal((Math.random()+0.01)/3));
		BigDecimal lowestPrice = SiteCurrencyEnum.getLowestPriceBySite(siteName);
		if(result.compareTo(lowestPrice)<0){
			result = lowestPrice;
		}
		productCodeManager.setPublishPrice(result.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());//这里对价格做一个微小的变动
		return productCodeManager;
	}

	
	
	/***
	 * 做 除法运算得到 结果
	 *  
	 * @param costPrice  成本价
	 * @param transPrice 运输价
	 * @param storePrice 储藏价
	 * @param inventoryInterest  库存利息
	 * @param smallpay  paypal小额
	 * @param fee    ebay fee 
	 * @param smallPaypal2
	 * @param profitRate  利润率
	 * @param siteName 站点
	 * @return
	 */
	private static BigDecimal getPublishPrice(BigDecimal costPrice,BigDecimal transPrice, BigDecimal storePrice,BigDecimal inventoryInterest, BigDecimal smallpay,BigDecimal fee, BigDecimal smallPaypal2, BigDecimal profitRate,String siteName) {
		BigDecimal fenzi = costPrice.add(transPrice).add(DEAL_PRICE).add(storePrice).add(inventoryInterest);
       
		BigDecimal huilv = DOLLAR_EXCHANGE_RATE;
		
		if(SiteCurrencyEnum.US.getSiteName().equalsIgnoreCase(siteName))//通过站点获取不同的汇率 TODO 后期查库，先用常量中的
		{
		}else if(SiteCurrencyEnum.UK.getSiteName().equalsIgnoreCase(siteName)){//英国  UK
			huilv = huilv.divide(POUND_TO_DOLLAR_EXCHANGE_RATE,2,BigDecimal.ROUND_HALF_UP); 
		}else if(SiteCurrencyEnum.CAN.getSiteName().equalsIgnoreCase(siteName)){//加拿大
			huilv = huilv.divide(CANADIAN_TO_DOLLAR_EXCHANGE_RATE,2,BigDecimal.ROUND_HALF_UP); 
		}else if(SiteCurrencyEnum.AU.getSiteName().equalsIgnoreCase(siteName)){//澳大利亚
			huilv = huilv.divide(AUSTRALIAN_DOLLAR_EXCHANGE_RATE,2,BigDecimal.ROUND_HALF_UP); 
		}
		
		fenzi = fenzi.divide(huilv,2,BigDecimal.ROUND_HALF_UP);
        fenzi = fenzi.add(smallpay); 
    	//(1-ebay率-palpayl率-利润率-损失费率-财务费率)
        
        //1-0.032-0.09-
		BigDecimal fenmu = BigDecimal.ONE.subtract(fee).subtract(smallPaypal2).subtract(LOSS_RATE).subtract(FINANCE_RATES).subtract(profitRate.divide(new BigDecimal(100)));
		return fenzi.divide(fenmu,2,BigDecimal.ROUND_HALF_UP);
	}



	/***
	 * 得到code manager 的反算价格
	 * @param product 
	 * 
	 * @param codeManagerList
	 * @param string 
	 * @param paypal 
	 * @param siteName 
	 * @param rate  要进行计算用的 利润率
	 * @return
	 */
	public static List<ProductCodeManager> getCodeManagerPrice(List<ProductCodeManager> codeManagerList, String siteName, EbayPaypal paypal, String feeRate, String rate) {

		boolean flag = false; //是不是存在大额的判断，如果存在就设置为 true，则整个 都为 大额计算
		
		if(CollectionUtils.isNotEmpty(codeManagerList)){
			for (ProductCodeManager productCodeManager : codeManagerList) {
				
				if(!flag){//如果不存在 大额的就计算下去，有出现大额的就不再计算了，后面所有的都按照大额的进行计算
					productCodeManager = getPrice(productCodeManager, siteName, paypal,feeRate,new BigDecimal(rate),false);
					flag = productCodeManager.isBigPaypal();
				}
			}
			if(flag){//存在大额的  所有的都按照大额的进行计算
				for (ProductCodeManager productCodeManager : codeManagerList) {
						productCodeManager = getPrice(productCodeManager, siteName, paypal,feeRate,new BigDecimal(rate),true);
				}
			}
		}
		return codeManagerList;
	}

	
	public static void main(String[] args) {
		
		List<ProductCodeManager> codeManagerList= new ArrayList<ProductCodeManager>();
		for(int i=1;i<10;i++){
			ProductCodeManager code=new ProductCodeManager();
			code.setCnName("test"+i);
			code.setCostPrice(100d%(i+3)+i);
			code.setId("id00000000"+i);
			codeManagerList.add(code);
		}
		codeManagerList.sort((ProductCodeManager manager1,ProductCodeManager manager2)-> manager1.getCostPrice().compareTo(manager2.getCostPrice()));
		for (ProductCodeManager productCodeManager : codeManagerList) {
			System.out.println(productCodeManager.getId()+"------"+productCodeManager.getCostPrice());
		}
		System.out.println(Math.random()+0.01/3);
		System.out.println(Math.random()/3);
		System.out.println(Math.random()/3);
		System.out.println("#########");
		
		BigDecimal result=new BigDecimal(10);
		BigDecimal result1=new BigDecimal((Math.random()+0.01)/3);
		result.add(result1);
		System.err.println(result  + "\t" + result.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		result=result.add(result1);
		System.err.println(result + "\t" + result.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		System.out.println("#########");
	}



	/***
	 * 反算获得产品的刊登价格
	 * 
	 * @param product
	 * @param siteName
	 * @param paypal
	 * @param feeRate
	 * @param profitRate 
	 * @return
	 */
	public static Product getProductPrice(Product product, String siteName,EbayPaypal paypal, String feeRate, Double profitRate) {

		       //商品成本=采购成本*1.035
				BigDecimal costPrice = ONE_POINT_ZERO_THREE_FIVE.multiply(new BigDecimal(product.getCostPrice()));
				//重量 kg
				BigDecimal weight = new BigDecimal(product.getDefaultWeight()).divide(ONE_THOUSAND);
				//运费 平邮 平邮运费=每公斤*重量/1000/汇率
				BigDecimal transPrice1 = COMMON_TRANS_PRICE.multiply(weight);
				//运费EUB  EUB运费=（每公斤+重量/1000+附加费）/汇率
				BigDecimal transPrice2 = EUB_TRANS_PRICE_BASE.multiply(weight).add(EUB_TRANS_PRICE_EXTRA);
				//仓储费=重量/ 1000 * 仓储费(元/kg/月) / 30 * 库存周转天数 / 汇率  
				BigDecimal storePrice=weight.multiply(STORAGE_CHARGES);
				//库存利息= 采购成本*1.035 * 库存利息率 *库存周转天数 /汇率
				BigDecimal inventoryInterest = costPrice.multiply(STOCK_INTEREST_RATE).multiply(TURNOVER_DAYS);
				//先进行小额的计算，如果金额  和 临界点的金额进行比较 ，如果 大于临界点 则用大额进行计算 
				//临界值
				BigDecimal  boundaries = new BigDecimal(paypal.getBoundaries());
				//ebay fee
				BigDecimal fee = new BigDecimal(feeRate);
				
				BigDecimal transPrice = transPrice1;
				
				BigDecimal paypal1=BigDecimal.ZERO;
				BigDecimal paypal2=BigDecimal.ZERO;
				BigDecimal result =BigDecimal.ZERO;
				product.setBigPaypal(false);
				
					paypal1= new BigDecimal(paypal.getSmallpaypalEachCost());
					paypal2= new BigDecimal(paypal.getSmallpaypalDiscount());
					//默认 小额 平邮计算
					result = getPublishPrice(costPrice,transPrice,storePrice,inventoryInterest,paypal1,fee,paypal2,new BigDecimal(profitRate),siteName);
					//X<=临界值：X=X	否则 X=大额售价公式计算     (如果有一个是大额的paypal 则其他的都是 大额的paypal)
					if(result.compareTo(boundaries)>0){
						product.setBigPaypal(true);
						paypal1 = new BigDecimal(paypal.getPaypalEachCost());
						paypal2 = new BigDecimal(paypal.getPaypalDiscount());
						result = getPublishPrice(costPrice,transPrice,storePrice,inventoryInterest,paypal1,fee,paypal2,new BigDecimal(profitRate),siteName);
					}
				//如果是 us 则先进行平邮计算，如果计算结果大于  5美金 则使用 eub 计算，其他都是 平邮计算 
				if(SiteCurrencyEnum.US.getSiteName().equalsIgnoreCase(siteName) && result.compareTo(new BigDecimal(5))>=0){
					transPrice = transPrice2;
					result = getPublishPrice(costPrice,transPrice,storePrice,inventoryInterest,paypal1,fee,paypal2,new BigDecimal(profitRate),siteName);
				}
				
				
				BigDecimal lowestPrice = SiteCurrencyEnum.getLowestPriceBySite(siteName);
				if(result.compareTo(lowestPrice)<0){
					result = lowestPrice;
				}
				product.setPublishPrice(result.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());//这里对价格做一个微小的变动
				return product;
	}
	
	
	
}
