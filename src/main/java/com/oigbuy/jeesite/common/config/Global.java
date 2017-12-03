/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.common.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.DefaultResourceLoader;

import com.ckfinder.connector.ServletContextFactory;
import com.google.common.collect.Maps;
import com.oigbuy.jeesite.common.utils.PropertiesLoader;
import com.oigbuy.jeesite.common.utils.SpringContextHolder;
import com.oigbuy.jeesite.common.utils.StringUtils;
import com.oigbuy.jeesite.modules.sequence.service.SequenceService;
import com.sho.tool.build.title.KeyWordBean;

/**
 * 全局配置类
 * @author ThinkGem
 * @version 2014-06-25
 */
public class Global {

	/**
	 * 当前对象实例
	 */
	private static Global global = new Global();
	
	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = Maps.newHashMap();
	
	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader loader = new PropertiesLoader("jeesite.properties");
	
	
	/**
	 * wish产品维护状态
	 */
	public static final int RECORD_MAIN_STATE_ERROR = -1;
	public static final int RECORD_MAIN_STATE_IN_PROCESS = 0;
	public static final int RECORD_MAIN_STATE_FINISH = 1;

	/**
	 * ebay暂时放弃开发判断条件,利润率大于16%，且月销量大于2
	 */
	public static final Double QUIT_CRUCIAL_RATE = 16D;
	public static final Integer  QUANTITY_SOLD = 2;
	/**
	 * 显示/隐藏
	 */
	public static final String SHOW = "1";
	public static final String HIDE = "0";

	/**
	 * 暂时放弃开发是否隐藏数据 1 是，0 否
	 */
	public static final Integer EBAY_SHOW = 0;
	public static final Integer EBAY_HIDE = 1;
	/**
	 * 是/否
	 */
	public static final String YES = "1";
	public static final String NO = "0";
	
	public static final String CHINESE_YES = "是";
	public static final String CHINESE_NO = "否";
	
	/**
	 * 状态值-->0/1/2/3/4/5/6/7/8/9
	 */
	public static final String ZERO="0";
	public static final String ONE="1";
	public static final String TWO="2";
	public static final String THREE="3";
	public static final String FOUR="4";
	public static final String FIVE="5";
	public static final String SIX="6";
	public static final String SEVEN="7";
	public static final String EIGHT="8";
	public static final String NINE="9";
	
	/**
	 * 请求平台接口的类型
	 */
	public static final String FUNCTION_OPER_TYPE_ADD = "add";
	public static final String DEL = "del";
	public static final String UPDATE = "update";
	public static final String SELECT = "select";
	
	public static final String FUNCTION_OPER_TYPE_MODIFY_WISH = "modifyWish_"; // 维护wish产品的操作类型前缀
	public static final String FUNCTION_OPER_TYPE_DISABLE_PRODUCT = "modifyWish_disableProduct"; // 下架产品
	public static final String FUNCTION_OPER_TYPE_ENABLE_PRODUCT = "modifyWish_enableProduct";   // 上架产品
	public static final String FUNCTION_OPER_TYPE_DISABLE_MULTI_PRODUCT = "modifyWish_disableMultiProduct"; // 下架多产品
	public static final String FUNCTION_OPER_TYPE_ENABLE_MULTI_PRODUCT = "modifyWish_enableMultiProduct";   // 上架多产品
	public static final String FUNCTION_OPER_TYPE_DISABLE_PRODUCT_VARIATION = "modifyWish_disablePV"; // 下架产品变化
	public static final String FUNCTION_OPER_TYPE_ENABLE_PRODUCT_VARIATION = "modifyWish_enablePV";   // 上架产品变化
	public static final String FUNCTION_OPER_TYPE_WISH_TAG = "modifyWish_updateWishTag"; // 维护tag
	public static final String FUNCTION_OPER_TYPE_WISH_DESCRIPTION = "modifyWish_updateDescription"; // 维护description
	public static final String FUNCTION_OPER_TYPE_WISH_NAME = "modifyWish_updateName"; // 维护标题
	public static final String FUNCTION_OPER_TYPE_UPDATE_PV = "modifyWish_updatePV"; // 更新产品变化
	public static final String FUNCTION_OPER_TYPE_ADD_PV = "modifyWish_addPV"; // 添加产品变化
	
	public static final String FUNCTION_OPER_TYPE_WISH_ATTR = "modifyWish_updateAttr";
	
	
	/**
	 * 普源任务操作类型
	 */
	public static final String PY_TASK_OPERTYPE_ADD_GOODS = "addGoods"; // 添加商品
	public static final String PY_TASK_OPERTYPE_ADD_SKU_LISTING = "addSku_listing"; // 选择店铺刊登
	public static final String PY_TASK_OPERTYPE_ADD_SKU_CODE_MANAGER = "addSku_codeManager"; // 添加店铺SKU和产品SKU映射
	public static final String PY_TASK_OPERTYPE_UPDATE_PY_TAG = "updatePyTag"; // 更新普源的标签
	
	/**
	 * 分隔符
	 */
	public static final String SEPARATE_0="\\|";
	public static final String SEPARATE_1="|";
	public static final String SEPARATE_2="\r\n";
	public static final String SEPARATE_3="_";
	public static final String SEPARATE_4="-";
	public static final String SEPARATE_5="#";
	public static final String SEPARATE_6="\n";
	public static final String SEPARATE_7=",";
	public static final String SEPARATE_8=":";
	public static final String SEPARATE_9="、";
	public static final String SEPARATE_10="*";
	public static final String SEPARATE_11="\t";
	/**分割符 空 " " */
	public static final String SEPARATE_12=" ";
	
	/** "" */
	public static final String SEPARATE_13="";
	public static final String SEPARATE_14=">";

	/**
	 * ebayfee paypalfee对象常量
	 */
	public static final String EBAY_PAYPAL = "paypal";
	public static final String EBAY_FEE = "fee";
	/**
	 * 代码前缀
	 */
	public static final String EBAY_PRODUCT_CODE_PREFIX = loader.getProperty("ebay_product_code_prefix","E");

	/**public static final String EBAY_INTERFACE_URL = loader.getProperty("ebay_base_url","http://192.168.0.24:8081/api/item/itemView");
	 * ebay接口地址
	 */


	/**
	 * 虚拟仓利润测算接口地址
	 */
	public static final String VIRTUAL_BASE_CALCULATE_URL = loader.getProperty("virtual_base_calculate_url","http://192.168.0.16:8081/knowledge/a/ebay/calculate/calC");
	
	/**
	 * ebay调用mms接口
	 */
	public static final String MMSURL = loader.getProperty("mmsUrl");
	
	/**
	 * ebay接口地址-物流设置，动态获取发送至国家
	 */
	public static final String SEND_TO_COUNTRY_URL = loader.getProperty("sendToCountryUrl");
	
	/**
	 * ebay接口地址-运输方式
	 */
	public static final String SHIPPING_TYPE_URL = loader.getProperty("shippingTypeUrl");

	
	/**
	 * mms 消息中心 exchange
	 */
	public static final String RABBIT_MMS_EXCHANGE = loader.getProperty("rabbitmq.mms.exchange");
	
	/**
	 * rabbit mq 发送到消息中心
	 */
	public static final String RABBIT_BASE_PRODUCT_ROUTINGKEY = loader.getProperty("rabbitmq.base.product.routingkey");
	

	/**
	 * mms 消费失败的消息中心routingkey
	 */
	public static final String RABBIT_MMS_ERROR_ROUTINGKEY = loader.getProperty("rabbitmq.mms.error.routingkey");
	
	
	/**
	 * mms 消费失败的消息中心queue
	 */
	//public static final String RABBIT_MMS_ERROR_QUEUE = loader.getProperty("rabbitmq.mms.error.queue");
	
	/***
	 * 敏感词 校验的 服务 url
	 */
	public static final String SENSITIVE_WORD_CHECK_URL = loader.getProperty("sensitive.word.check.server.url");
	
	/**
	 * 对/错
	 */
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	
	/**
	 * 上传文件基础虚拟路径
	 */
	public static final String USERFILES_BASE_URL = "/userfiles/";
	
	/**
	 * session缓存的图片合成Key
	 */
	public static final String SESSION_CACHE_KEY_MOSAIC_VIEWIMG = "MOSAIC_VIEWIMG";
	
	// 店铺状态
	/**
	 * 新店铺
	 */
	public static final String SHOP_STATUS_NEW = "0";
	/**
	 * 次店铺
	 */
	public static final String SHOP_STATUS_HALF_NEW = "1";
	/**
	 * 老店铺
	 */
	public static final String SHOP_STATUS_OLD = "2";
	
	// 图片类型：主图，细节图，合成图类型
	/**
	 * 主图类型
	 */
	public static final String IMG_TYPE_MAIN = "1";
	/**
	 * 细节图类型
	 */
	public static final String IMG_TYPE_DETAIL = "2";
	/**
	 * 子 G 图类型 
	 */
	public static final String IMG_TYPE_CHILD_MAIN = "3";
	/**
	 * 拼图类型
	 */
	public static final String IMG_TYPE_JIGSAW = "4";
	/**
	 * 特效图 
	 */
	public static final String IMG_TYPE_SPECIAL_EFFECTS= "5";
	/**
	 * 合成图类型
	 */
	public static final String IMG_TYPE_COMPOSITE = "6";
	
	// 财务费费率
	public static final double FINANCE_RATE = 0.025;
	// 损失费费率
	public static final double LOSS_RATE = 0.03;

	// 对应人民币￥
	private static final double PACKAGE_FEE = 0.35;
	private static final double DEAL_FEE = 1.00;
	
	//listing审核状态
	
	
	// 步骤类型
	/**
	 * （步骤0）
	 */
	public static final String STEP_TYPE_ZERO = "0";
	/**
	 * （步骤一）
	 */
	public static final String STEP_TYPE_ONE = "1";
	/**
	 * （步骤二）
	 */
	public static final String STEP_TYPE_TWO = "2";
	/**
	 * （步骤三）
	 */
	public static final String STEP_TYPE_THREE = "3";
	/**
	 * （步骤四）
	 */
	public static final String STEP_TYPE_FOUR = "4";
	
	// 接口调用的状态
	/**
	 * 待处理
	 */
	public static final String FUNCTION_STUTAS_WAITING = "0";
	/**
	 * 正在进行中
	 */
	public static final String FUNCTION_STUTAS_IN_PROGRESS = "1";
	/**
	 * 处理异常
	 */
	public static final String FUNCTION_STUTAS_ERROR = "2";
	/**
	 * 处理完成
	 */
	public static final String FUNCTION_STUTAS_FINISH = "3";
	
	
	// 任务补录
	/**
	 * wish采购补录
	 */
	public static final String TASK_CATEGORY_WISH = "0";
	/**
	 * wish产品多属性图片补录
	 */
	public static final String TASK_CATEGORY_IMG = "1";
	/**
	 * wish产品单品图片
	 */
	public static final String TASK_CATEGORY_SINGLE = "2";
	/**
	 * 店铺补录
	 */
	public static final String TASK_CATEGORY_SHOP = "3";
	
	
	// 任务来源
	/**
	 * wish采购
	 */
	public static final String TASK_DATA_TYPE_WISH = "0";
	/**
	 * wish产品多属性
	 */
	public static final String TASK_DATA_TYPE_IMG = "1";
	/**
	 * 单品图片
	 */
	public static final String TASK_DATA_TYPE_SINGLE = "2";
	/**
	 * 店铺
	 */
	public static final String TASK_DATA_TYPE_SHOP = "3";
	
	// listing的刊登审核状态
	/**
	 * 审核中
	 */
	public static final String LISTING_AUDIT_STATUS_PENDING = "0"; 
	/**
	 * 审核通过
	 */
	public static final String LISTING_AUDIT_STATUS_APPROVED = "1"; 
	/**
	 * 拒绝
	 */
	public static final String LISTING_AUDIT_STATUS_REJECTED = "2";
	
	// 上架或下架状态
	/**
	 * 上架
	 */
	public static final String STATE_ENABLE = "0";
	/**
	 * 下架
	 */
	public static final String STATE_DISABLE = "1";

	/**
	 * 导入数据初始值
	 */
	public static final Integer INIT_ID = 1;

	/**
	 *  product 中identitying类型
	 *  0.原始数据 1.迁移数据
	 */
	public static final Integer OLD_DATA = 0;
	public static final Integer TRANSFER_DATA = 1;
	/**
	 * 模板类型
	 * puyuan  普元线下数据模板
	 * zhanghu  账户组线下模板
	 */
	public static final String PUYUAN_TEMPLET = "puyuan";
	public static final String ZHANGHU_TEMPLET = "zhanghu";

	/**
	 * 平台类型
	 * ebay  ebay平台
	 * wish wish平台
	 * new  新平台
	 */
	public static final String TERRACE_TYPE_EBAY = "ebay";
	public static final String TERRACE_TYPE_WISH = "wish";
	public static final String TERRACE_TYPE_NEW = "new";



	/**
	 * 消息状态
	 *		0: 未刊登,
	 * 		1:已刊登 ,
	 * 		2: 忽略
	 */
	public static final String PRODUCT_QUEUE_NO = "0";
	public static final String PRODUCT_QUEUE_YES = "1";
	public static final String PRODUCT_QUEUE_IGNORE = "2";
	
	
	/**
	 * 草稿箱产品状态
	 */
	public static final String PRODUCT_TYPE_DRAFT="0";
	/**
	 * 已开发产品状态
	 */
	public static final String PRODUCT_TYPE_OFFICIAL="1";
	
	/**
	 * 待完成产品数量补充标识
	 */
	public static final String FINISHING_PRODUCT_QUANTITY="0";
	/**
	 * 已完成产品数量补充标识
	 */
	public static final String FINISHED_PRODUCT_QUANTITY="1";
	
	public static final String THREADPOOL_NAME_ANALYZE_PRODUCT_OF_WISH="analyze_product_of_wish";

	
	public static final Integer ZERO_INTEGER = 0;
	public static final Integer ONE_INTEGER = 1;
	public static final Integer TWO_INTEGER = 2;
	public static final Integer THREE_INTEGER = 3;
	
	
	/**listing 状态 0 新建状态	 */
	public static final String LISTING_STATUS_ZERO = "0";
	/**listing 状态 1待刊登状态	 */
	public static final String LISTING_STATUS_ONE = "1";
	/**listing 状态 2已刊登状态	 */
	public static final String LISTING_STATUS_TWO = "2";
	/**listing 状态 3 刊登待审核 状态	 */
	public static final String LISTING_STATUS_THREE = "3";
	/**listing 状态 4刊登审核通过状态	 */
	public static final String LISTING_STATUS_FOUR = "4";
	/**listing 状态 5 刊登审核不通过状态	 */
	public static final String LISTING_STATUS_FIVE = "5";
	
	/**
	 * 获取当前对象实例
	 */
	public static Global getInstance() {
		return global;
	}

	/**
	 * 图片合成码最大值 默认为0
	 */
	public static final Integer MAX_CODE = 0;
	
	/**
	 * 获取配置
	 * @see ${fns:getConfig('adminPath')}
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = loader.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}
	
	/**
	 * 获取管理端根路径
	 */
	public static String getAdminPath() {
		return getConfig("adminPath");
	}
	
	/**
	 * 获取前端根路径
	 */
	public static String getFrontPath() {
		return getConfig("frontPath");
	}
	
	/**
	 * 获取URL后缀
	 */
	public static String getUrlSuffix() {
		return getConfig("urlSuffix");
	}
	
	/**
	 * 是否是演示模式，演示模式下不能修改用户、角色、密码、菜单、授权
	 */
	public static Boolean isDemoMode() {
		String dm = getConfig("demoMode");
		return "true".equals(dm) || "1".equals(dm);
	}
	
	/**
	 * 在修改系统用户和角色时是否同步到Activiti
	 */
	public static Boolean isSynActivitiIndetity() {
		String dm = getConfig("activiti.isSynActivitiIndetity");
		return "true".equals(dm) || "1".equals(dm);
	}
    
	/**
	 * 页面获取常量
	 * @see ${fns:getConst('YES')}
	 */
	public static Object getConst(String field) {
		try {
			return Global.class.getField(field).get(null);
		} catch (Exception e) {
			// 异常代表无配置，这里什么也不做
		}
		return null;
	}
	
//	/**
//	 * 获取递增ID
//	 * */
//	public static  Long getID(){
////		String key = loader.getProperty("global_id");
////		return redisService.incr(key);
//
//		SequenceService sequenceService = SpringContextHolder.getBean("sequenceService");
//		return sequenceService.createGlobalId();
//	}
	/**
	 * 获取递增ID
	 * */
	public static Long getID(){
//		String key = loader.getProperty("global_id");
//		return redisService.incr(key);

		SequenceService sequenceService = SpringContextHolder.getBean("sequenceService");
		try {
			return sequenceService.createGlobalId();
		} catch (Exception e) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
			}
			return sequenceService.createGlobalId();
		}
	}

	/**
	 * 全局母代码生成
	 * 母代码前缀+分秒毫秒+随机数
	 * 加上锁
	 * */
	public static String getProductCode(){
		SequenceService sequenceService = SpringContextHolder.getBean("sequenceService");
		if(StringUtils.isBlank(EBAY_PRODUCT_CODE_PREFIX)){
			throw new RuntimeException("母代码前缀不能为空");
		}
		
		return  EBAY_PRODUCT_CODE_PREFIX + sequenceService.createProductCode();
//		return  PRODUCT_CODE_PREFIX + new SimpleDateFormat("MMddHHmmssSSS").format(new Date())+(int)((Math.random()*9)+0);
	}
	
	/**
	 * 获取上传文件的根目录
	 * @return
	 */
	public static String getUserfilesBaseDir() {
		String dir = getConfig("userfiles.basedir");
		if (StringUtils.isBlank(dir)){
			try {
				dir = ServletContextFactory.getServletContext().getRealPath("/");
			} catch (Exception e) {
				return "";
			}
		}
		if(!dir.endsWith("/")) {
			dir += "/";
		}
//		System.out.println("userfiles.basedir: " + dir);
		return dir;
	}
	
    /**
     * 获取工程路径
     * @return
     */
    public static String getProjectPath(){
    	// 如果配置了工程路径，则直接返回，否则自动获取。
		String projectPath = Global.getConfig("projectPath");
		if (StringUtils.isNotBlank(projectPath)){
			return projectPath;
		}
		try {
			File file = new DefaultResourceLoader().getResource("").getFile();
			if (file != null){
				while(true){
					File f = new File(file.getPath() + File.separator + "src" + File.separator + "main");
					if (f == null || f.exists()){
						break;
					}
					if (file.getParentFile() != null){
						file = file.getParentFile();
					}else{
						break;
					}
				}
				projectPath = file.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return projectPath;
    }
    
    /**
     * 集合转换成数组
     * @param list<T>
     * @param Class T
     * */
    public static String [] toStringArray(List<String> t){
    	if(t.size()<1){
    		return null;
    	}
    	String [] str = new String [t.size()];
    	for(int i=0;i<t.size();i++){
    		str[i]=t.get(i);
    	}
    	return str;
    }
    
    /**
     * 集合转换成数组
     * @param list<T>
     * @param Class T
     * */
    public static KeyWordBean [] toKeyWordArray(List<KeyWordBean> t){
    	if(t.size()<1){
    		return null;
    	}
    	KeyWordBean [] str = new KeyWordBean [t.size()];
    	for(int i=0;i<t.size();i++){
    		str[i]=t.get(i);
    	}
    	return str;
    }
    
	public static String getRandomLetter() {
		String[] letterArray = { "a", "b", "c", "d", "e", "f", "g", "h", "i",
				"j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u",
				"v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G",
				"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
				"T", "U", "V", "W", "X", "Y", "Z" };
		return letterArray[(int) (Math.random() * 47)];
	}
	
	/**
	 * 传入map的销售类型键值
	 */
	public static final String SALE_TYPE="saleType";
	public static final String ROLLBACK = "rollback";
	/**
	 * 流程节点标识
	 */
	public static String ACT_IDENTI_DEV="development";
	public static String ACT_IDENTI_DESGINE="designdrawing";
	public static String ACT_INDETI_DAT_PERFECT="dataperfect";
	public static String ACT_INDETI_DAT_REVIEW="datareview";
	
	/**
	 * 流程所需部门code
	 */
	public static String ACT_WISH_SALE_CODE="10000003001";  //ebay销售
	public static String ACT_WISH_PURCHASE_CODE="10000003002"; //ebay采购
	public static String ACT_WISH_DESGIN_CODE="10000003003";  //设计
	
	/**
	 * ebay销售类型
	 */
	public static String SALE_TYPE_OVERSEAS_STOREHOUSE="1"; 				//海外仓精品
	public static String SALE_TYPE_VIRTUAL_OVERSEAS_STOREHOUSE="2";  		//虚拟海外仓精品
	public static String SALE_TYPE_DIRECT_SHIPMENT_DISTRIBUTION ="3";  		//中国直发仓铺货
	public static String SALE_TYPE_VIRTUAL_OVERSEAS_DISTRIBUTION = "4"; 	//虚拟海外仓铺货

	/**
	 *平台标识
	 * 0.Wish平台标识
	 * 2.新平台标识
	 * 1.Ebay平台标识
	 */
	public static String PLATFORM_FLAG_WISH = "0";
	public static String PLATFORM_FLAG_NEW = "2";
	public static String PLATFORM_FLAG_EBAY = "1";
	/**
	 * 设置list默认接收的最大长度为1000
	 */
	public static Integer LIST_MAX_SIZE = 1000;
	/**
	 *	模板图片标识    0.非模板图片
	 */
	public static String TEMPLATE_IDEN_NON = "0";
	/**
	 *	模板图片标识   1.模板图片
	 * 
	 */
	public static String TEMPLATE_IDEN = "1";
	
	/**
	 * 文件夹上传图片主G图名称
	 */
	public static String MAIN_IMG_NAME = ".jpg";
	
	/**
	 * 管理员常量
	 */
	public static String ADMIN = "admin";
	/**
	 * ebay平台编号维护 18
	 */
	public static String EBAY_PLATFORM_CODE = "18";

	/**
	 * 竞品采集状态
	 * 
	 * 0.产品采集  1.产品测算  2.产品开发  3.review产品数量  4.设计制图  5.资料完善 6.Review.7.放弃开发 8.暂时放弃开发
	 * 
	 */
	public static String PRODUCT_COLLECT_STATUS_COLLECTION = "0";  				//产品采集
	public static String PRODUCT_COLLECT_STATUS_CALCULATION = "1";				//产品测算
	public static String PRODUCT_COLLECT_STATUS_DEVELOP = "2";					//产品开发
	public static String PRODUCT_COLLECT_STATUS_REVIEW_CODE_NUMBER = "3";		//review产品数量
	public static String PRODUCT_COLLECT_STATUS_DRAWING = "4";					//设计作图
	public static String PRODUCT_COLLECT_STATUS_DATA_PERFECT = "5"; 			//资料完善
	public static String PRODUCT_COLLECT_STATUS_REVIEW = "6"; 					//Review
	public static String PRODUCT_COLLECT_STATUS_GIVEUP_DEVELOPMENT = "7";		 //放弃开发
	public static String PRODUCT_COLLECT_STATUS_GIVEUP_DEVELOPMENT_TEMPORARILY = "8"; //暂时放弃开发
	
	
	/***
	 * ebay 付款方式 集合 
	 */
	public static  List<String> EBAY_PAYMETHOD_LIST = new ArrayList<String>(); 	
	
	static {
		
		EBAY_PAYMETHOD_LIST.add("AmEx");
		EBAY_PAYMETHOD_LIST.add("CashOnPickup");
		EBAY_PAYMETHOD_LIST.add("Discover");
		EBAY_PAYMETHOD_LIST.add("IntegratedMerchantCreditCard");
		EBAY_PAYMETHOD_LIST.add("MOCC");
		EBAY_PAYMETHOD_LIST.add("PayPal");
		EBAY_PAYMETHOD_LIST.add("Other");
		EBAY_PAYMETHOD_LIST.add("PersonalCheck");
		EBAY_PAYMETHOD_LIST.add("VisaMC");
	}
	
	/**
	 * 单点登录的开关设置，不影响生产环境
	 */
	public static String SSO_LOGIN_FLAG = loader.getProperty("sso_login_flag","false");
	
	/**
	 * 物流模板境内  1
	 */
	public static final String LOGISTIC_MODE_TERRITORY="1";
	/**
	 * 物流模板境外 2
	 */
	public static final String LOGISTIC_MODE_OVERSEAS="2";

	/**
	 * http响应code key
	 */
	public static final String CODE="code";

	/**
	 * http响应e message key
	 */
	public static final String MESSAGE="message";
	/**
	 * 成功 200
	 */
	public static final String SUCCESS="200";
	
	/**
	 * 接口请求成功
	 */
	public static final String SUCCESS_MESSAGE="接口请求成功!";
	
	/**
	 * 接口请求信息
	 */
	public static final String REQUEST_MESSAGE="ebay-mms request param";
	/**
	 * 失败 400
	 */
	public static final String FAIL="400";
	
	/**
	 * 接口请求失败
	 */
	public static final String FAIL_MESSAGE="接口请求失败!";
	
	/**
	 * 接口请求失败
	 */
	public static final String FAIL_GET_PLATFROM_MESSAGE="接口调用失败，你传入的平台类型有误!";
	
	/**
	 * 接口请求失败
	 */
	public static final String FAIL_GET_PRODUCT_MESSAGE="接口调用失败，没有获取到产品!";
	
	
	
	/**
	 * 网关超时 504
	 */
	public static final String GATEWAY_TIMEOUT_CODE="504";
	
	/**
	 * 网关超时 
	 */
	public static final String GATEWAY_TIMEOUT_MESSAGE="网关超时";

	/**
	 * 参数格式异常
	 */
	public static final String PARAMETER_FORMAT_ABNORMAL="403";
	
	/***
	 * ebay 子 代码 sku 后缀
	 */
	public static final String SKU_CODE_MM = "MM";
	
	/**
	 * 调用mms接口，设置用户id=1000000
	 */
	public static final String MMS_USER_ID="1000000";
	
	/**
	 * 常数100,用来让mms的利率*100
	 */
	public static final Integer CONSTANT_100=100;
	
	/**
	 * mms放弃产品开发
	 */
	public static final String MMS_GIVE_UP_MESSAGE="mms放弃产品开发";
	
	/**
	 * 获取套图 主 g 图  map 中 主 g 图的标志 key
	 */
	public static final String IMAGE_MAIN_KEY = "10000";
	
	/**
	 * 法国 和 意大利 使用  特效图片，其他站点 使用 细节图片
	 */
	public static final String ITALY_AND_FRANCE_SITE_NAME = "意大利,法国";
	
	/**
	 * mms mq sku rountingKey
	 */
	public static final String RABBIT_MMS_SKU_ROUTINGKEY = loader.getProperty("rabbitmq.mms.sku.routingkey");
	
	/**
	 * 资料完善页面流程回退到设计制图标识
	 */
	public static final String ROLLBACK_FLAG = "1";

	/**
	 * 德国站点默认关税 0.05
	 */
	public static final String TARIFF_GERMANY_SITE="0.05";
	/**
	 * 非德国站点默认关税 0.04
	 */
	public static final String TARIFF_NOT_GERMANY_SITE="0.04";
}
