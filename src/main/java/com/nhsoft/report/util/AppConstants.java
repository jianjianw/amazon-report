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
}
