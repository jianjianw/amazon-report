package com.nhsoft.report.util;





public class AppConstants {
	/**
	 * payment 中默认支付方式
	 */
	public static final String PAYMENT_CASH = "现金";
	public static final String PAYMENT_YINLIAN = "银联卡";
	public static final String PAYMENT_SIGN = "签单";
	public static final String PAYMENT_WEIXIN = "微店结算";
	public static final String PAYMENT_CHEQUE = "支票";
	public static final String PAYMENT_GIFTCARD = "储值卡";
	public static final String PAYMENT_ALIPAY = "支付宝";
	public static final String PAYMENT_ORI = "老会员转卡";
	public static final String PAYMENT_POINT_CONSUME = "积分消费";
	public static final String PAYMENT_UNIONPAY = "银联支付";
	public static final String PAYMENT_DEPOSIT_POINT = "积分"; //积分转储值付款方式


	public static final int CACHE_LIVE_DAY = 60 * 60 * 24; //缓存有效时间一天
	public static final String INIT_TIME = "1899-12-30 00:00:00";
	
	/**
	 * 单据明细类型
	 */
	public static final String POS_ORDER_DETAIL_TYPE_ITEM = "商品项目";
	public static final String POS_ORDER_DETAIL_TYPE_COUPON = "消费券";
	
	/**
	 * card_change_type
	 */
	public static final String CARD_CHANGE_TYPE_IN = "存零钱包";
	public static final String CARD_CHANGE_TYPE_OUT = "取零钱包";
	
	/**
	 * state_code
	 */
	public static final int STATE_INIT_CODE = 1;
	public static final int STATE_AUDIT_CODE = 2;
	public static final int STATE_INIT_AUDIT_CODE = STATE_INIT_CODE | STATE_AUDIT_CODE;

	public static final String S_Prefix_WD = "WD";//在线订单
	
	/**
	 * 库存进出日志摘要
	 */
	public static final String POS_ITEM_LOG_IN_ORDER = "调入单";
	public static final String POS_ITEM_LOG_OUT_ORDER = "调出单";
	public static final String POS_ITEM_LOG_RECEIVE_ORDER = "收货单";
	public static final String POS_ITEM_LOG_RETURN_ORDER = "退货单";
	public static final String POS_ITEM_LOG_CHECKORDER = "盘点单";
	public static final String POS_ITEM_LOG_ADJUSTMENTORDER = "调整单";
	public static final String POS_ITEM_LOG_ALLOCATIONORDER = "转仓单";
	public static final String POS_ITEM_LOG_POS = "前台销售";
	public static final String POS_ITEM_LOG_WHOLESALE_ORDER_ORDER = "批发销售";
	public static final String POS_ITEM_LOG_WHOLESALE_RETURN_ORDER = "批发退货";
	public static final String POS_ITEM_LOG_SPLIT = "制单拆分";
	public static final String POS_ITEM_LOG_ASSEMBLE = "制单组合";
	public static final String POS_ITEM_LOG_COST_ADJUST = "成本调整单";
	public static final String POS_ITEM_LOG_CONSUME_POINT = "积分兑换";
	public static final String POS_ITEM_LOG_ANTI_POS = "前台销售反结账";
	/**单据类型补充     */
	public static final String POS_ITEM_LOG_PURCHASE_ORDER = "采购订单";
	public static final String POS_ITEM_LOG_REQUEST_ORDER = "要货单";
	public static final String POS_ITEM_LOG_WHOLESALE_BOOK = "批发订单";
	
	/**
	 * 管理中心分店编号
	 */
	public static final Integer REQUEST_ORDER_OUT_BRANCH_NUM = 99;
	
	/**
	 * 促销类型
	 */
	public static final String POLICY_PROMOTION="促销特价";
	public static final String POLICY_PRENSENT = "赠送促销" ;
	public static final String POLICY_PROMOTION_MONEY ="超额奖励";
	public static final String POLICY_PROMOTION_QUANTITY = "超量特价";
	public static final String POLICY_DISCOUNT = "超额折扣";
	public static final String POLICY_DISCOUNT_AVOID = "超额减免";
	
	/**
	 * 统计单位
	 */
	public static final String UNIT_SOTRE = "库存单位";
	public static final String UNIT_TRANFER = "配送单位";
	public static final String UNIT_PURCHASE = "采购单位";
	public static final String UNIT_BASIC = "基本单位";
	public static final String UNIT_PIFA= "批发单位";
	public static final String UNIT_USE= "常用单位";
	
	/**
	 * 销售统计项常量
	 */
	public static final String CHECKBOX_OUT = "本店调出";
	public static final String CHECKBOX_IN = "本店调入";
	public static final String CHECKBOX_WHO = "本店批发";
	public static final String CHECKBOX_WHO_RETURN = "本店批发退货";
	public static final String CHECKBOX_SALE = "分店销售";
	public static final String CHECKBOX_CHECK_ORDER = "库存盘点";
	public static final String CHECKBOX_ADJUSTMENT = "库存调整";
	public static final String CHECKBOX_RECEIVE = "本店收货";
	public static final String CHECKBOX_RETURN = "本店退货";
	
	/**
	 * 操作人类型
	 */
	public static final String ORDER_OPERATOR_TYPE_CREATOR = "制单人";
	public static final String ORDER_OPERATOR_TYPE_AUDITOR = "审核人";
	public static final String ORDER_OPERATOR_TYPE_PICKER = "配货人";
	public static final String ORDER_OPERATOR_TYPE_SENDER = "发车人";
	
	/**
	 * 成本核算方式
	 */
	public static final String C_ITEM_COST_MODE_AVERAGE = "加权平均法";
	public static final String C_ITEM_COST_MODE_FIFO = "先进先出法";
	public static final String C_ITEM_COST_MODE_MANUAL = "手工指定";
	public static final String C_ITEM_COST_MODE_CENTER_MANUAL = "中心手工指定";//中心手工指定 门店加权平均
	
	/**
	 * 商品状态
	 */
	public static final int ITEM_STATUS_NORMAL = 0; //正常
	public static final int ITEM_STATUS_SLEEP = 1; //休眠
	
	
	/**
	 * 查询时间类型
	 */
	public static final String BUSINESS_DATE_SOME_DATE = "日";
	public static final String BUSINESS_DATE_SOME_MONTH = "月";
	public static final String BUSINESS_DATE_SOME_YEAR = "年";
	public static final String BUSINESS_DATE_SOME_WEEK = "周";

	/** 缓存名称 */
	public static final String CACHE_NAME_BRANCH = "CACHE_BRANCH:";



	/**
	 * memcached缓存前缀
	 */

	public static final String MEMCACHED_PRE_BRANCH_UPDATE_TIME = "AMA_BRANCH_UPDATE_TIME"; //分店缓存清空时间

	/**
	 * 报损原因类型
	 */
	public static final String ADJUSTMENT_REASON_TYPE_LOSS = "报损";
	
	
	/**
	 * 首页柱状图查询依据
	 */
	public static final String BUSINESS_TREND_PAYMENT = "按营业额";
	public static final String BUSINESS_TREND_DEPOSIT = "按储值额";
	
	/**
	 * 首页柱状图和销售额分析查询时间格式
	 */
	public static final String BUSINESS_DATE_YEAR = "按年";
	public static final String BUSINESS_DATE_DAY = "按日";
	
	/**
	 * 单据明细状态
	 */
	public static final int POS_ORDER_DETAIL_STATE_SALE = 1;
	public static final int POS_ORDER_DETAIL_STATE_REMOVE = 8;
	public static final int POS_ORDER_DETAIL_STATE_CANCEL = 4;
	public static final int POS_ORDER_DETAIL_STATE_PRESENT = 2;
	
	/**
	 * 查询商品类型
	 */
	public static final String ITEM_TYPE_PURCHASE = "采购";
	public static final String ITEM_TYPE_INVENTORY = "库存";
	public static final String ITEM_TYPE_WHOLESALE = "批发";
	public static final String ITEM_TYPE_CHAIN = "连锁";
	public static final String ITEM_TYPE_ONLINE = "网店";
	public static final String ITEM_TYPE_SALE = "销售";
	
	
	/**
	 * 商品类型
	 */
	public static final int C_ITEM_TYPE_STANDARD = 1; //标准
	public static final String C_ITEM_TYPE_STANDARD_NAME = "标准"; //标准
	public static final int C_ITEM_TYPE_MATRIX = 2;//含多特性
	public static final String C_ITEM_TYPE_MATRIX_NAME = "含多特性";//含多特性
	public static final int C_ITEM_TYPE_SERIALIZED = 3;//标识码
	public static final String C_ITEM_TYPE_SERIALIZED_NAME = "标识码";//标识码
	public static final int C_ITEM_TYPE_KIT = 4;//组合商品
	public static final String C_ITEM_TYPE_KIT_NAME = "组合商品";//组合商品
	public static final int C_ITEM_TYPE_NON_STOCK = 5;//非库存商品
	public static final String C_ITEM_TYPE_NON_STOCK_NAME = "非库存商品";//非库存商品
	public static final int C_ITEM_TYPE_ASSEMBLE = 6;//制单组合
	public static final String C_ITEM_TYPE_ASSEMBLE_NAME = "制单组合";//制单组合
	public static final int C_ITEM_TYPE_SPLIT = 7;//制单拆分
	public static final String C_ITEM_TYPE_SPLIT_NAME = "制单拆分";//制单拆分
	public static final int C_ITEM_TYPE_CUSTOMER_KIT = 8;//自定义组合
	public static final String C_ITEM_TYPE_CUSTOMER_KIT_NAME = "自定义组合";//自定义组合
	public static final int C_ITEM_TYPE_ELEMENT = 9;
	public static final String C_ITEM_TYPE_ELEMENT_NAME = "成分商品"; //'ELEMENT';
	public static final int C_ITEM_TYPE_GRADE = 10;
	public static final String C_ITEM_TYPE_GRADE_NAME = "分级商品";
	public static final int C_ITEM_TYPE_MATERIAL = 11;
	public static final String C_ITEM_TYPE_MATERIAL_NAME = "原料商品";
	
	/**
	 * 超量特价类型
	 *
	 */
	public static final String PROMOTION_QUANTITY_CATEGORY_TRANSFER = "配送超量特价";
	public static final String PROMOTION_QUANTITY_CATEGORY_WHOLESALE = "批发超量特价";
	
	/**
	 * 促销特价类型
	 */
	public static final String POLICY_PROMOTION_CATEGORY_WHOLESALE = "批发促销特价";
	public static final String POLICY_PROMOTION_CATEGORY_PURCHASE = "采购促销特价";
}
