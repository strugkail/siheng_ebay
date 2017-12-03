package com.oigbuy.jeesite.common.utils;

import java.math.BigDecimal;

/**
 * 其他邮政利润的计算规则
 * <ul>
 * <li>利润 = 售价 * 汇率 - 运费 - 处理费 - 挂号费 - wish fee - 财务费用 - 损失费 - 成本</li>
 * <li>利润率 = 利润 / （售价 * 汇率 ）</li>
 * <li>售价 = 刊登价格 + 刊登运费</li>
 * <li>运费 = 重量 * 费用 * 折扣</li>
 * <li>wish fee = 售价 * 汇率 * 费用率</li>
 * <li>财务费用 = 售价 * 汇率 * 费用率</li>
 * <li>损失费 = 售价 * 汇率 * 费用率</li>
 * </ul>
 * 
 * @author ming.ma
 *
 */
public class ProfitCalculateForMaYouUtils {

	private static double exchangeRate = 6.59; // 汇率(美元/人民币)
	private static double processingFee = 1; // 处理费
	private static double registeredFee = 0; // 挂号费

	private static double transFeePerGram = 0.1; // 运输的每克费用
	private static double transFeeDiscountRate = 1; // 运输费用的折扣率

	private static double wishFeeRate = 0.15; // wish费用率
	private static double financeFeeRate = 0.025; // 财务费用率
	private static double lossFeeRate = 0.03; // 损失费用率
	
	/**
	 * 以四舍五入的方式保留指定位数的小数
	 * @param source
	 * @param scale
	 * @return
	 */
	private static double formatScale(double source, int scale){
		BigDecimal bg = new BigDecimal(source);
		return bg.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 利润
	 * 
	 * @param sellPrice 售价
	 * @param costPricae 成本价
	 * @return
	 */
	public static double calProfit(double sellPrice, double costPricae, double weight) {
		double profit = sellPrice * exchangeRate - calTransFee(weight)
				- processingFee - registeredFee - calWishFee(sellPrice)
				- calFinanceFee(sellPrice) - calLossFee(sellPrice) - costPricae;
		return formatScale(profit, 3);
	}
	
	/**
	 * 利润率
	 * 
	 * @param sellPrice
	 * @param costPrice
	 * @param weight
	 * @return
	 */
	public static double calProfitRate(double sellPrice, double costPrice, double weight){
		double profit = calProfit(sellPrice, costPrice, weight);
		double profitRate = profit / (sellPrice * exchangeRate) * 100;
		return formatScale(profitRate, 2);
	}

	/**
	 * wish fee (售价 * 汇率 * 费用率)
	 * 
	 * @param sellPrice
	 * @return
	 */
	public static double calWishFee(double sellPrice) {
		return formatScale(sellPrice * exchangeRate * wishFeeRate, 3);
	}

	/**
	 * 财务费用 (售价 * 汇率 * 费用率)
	 * 
	 * @param sellPrice
	 * @return
	 */
	public static double calFinanceFee(double sellPrice) {
		return formatScale(sellPrice * exchangeRate * financeFeeRate, 3);
	}

	/**
	 * 损失费 (售价 * 汇率 * 费用率)
	 * 
	 * @param sellPrice
	 * @return
	 */
	public static double calLossFee(double sellPrice) {
		return formatScale(sellPrice * exchangeRate * lossFeeRate, 3);
	}

	/**
	 * 计算运费 (重量 * 每克费用 * 折扣率)
	 * 
	 * @param weight
	 * @return
	 */
	public static double calTransFee(double weight) {
		return formatScale(weight * transFeePerGram * transFeeDiscountRate, 3);
	}
	
	public static void main(String[] args) {
		double sellPrice = 2;
		double costPricae = 5;
		double weight = 25;
		
		System.out.println(ProfitCalculateForMaYouUtils.calProfit(sellPrice, costPricae, weight));
		System.out.println(ProfitCalculateForMaYouUtils.calProfitRate(sellPrice, costPricae, weight));
	}

}
