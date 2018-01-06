package com.nhsoft.module.report.util;


import com.nhsoft.module.report.query.State;

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


	public static final int CACHE_LIVE_DAY = 60 * 60; //缓存有效时间一天
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



	/**
	 * memcached缓存前缀
	 */

	public static final String MEMCACHED_PRE_BRANCH_UPDATE_TIME = "AMA_BRANCH_UPDATE_TIME"; //分店缓存清空时间
	public static final String MEMCACHED_PRE_POSITEM_UPDATE_TIME = "AMA_POSITEM_UPDATE_TIME";//商品资料缓存清空时间
	
	
	/**
	 * 首页柱状图查询依据
	 */
	public static final String BUSINESS_TREND_PAYMENT = "按营业额";
	public static final String BUSINESS_TREND_DEPOSIT = "按储值额";
	
	/**
	 * 首页柱状图和销售额分析查询时间格式
	 */
	public static final String BUSINESS_DATE_YEAR = "按年";
	public static final String BUSINESS_DATE_MONTH = "按月";
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

	/*
	 * AMA模块
	 */
	public static final String C_AMA_MODULE_PURCHASE = "采购管理";
	public static final String C_AMA_MODULE_WHOLESALE = "批发管理";
	public static final String C_AMA_MODULE_INVNETORY = "仓库管理";
	public static final String C_AMA_MODULE_CHAIN = "连锁管理";
	public static final String C_AMA_MODULE_PROMOTION = "促销政策";
	public static final String C_AMA_MODULE_SETTLEMENT = "结算管理";
	public static final String C_AMA_MODULE_SMS = "短信管理";



	public static final String BUSINESS_TYPE_SHOP = "联营";
	public static final String STATE_AUDIT_TIME = "审核时间";
	public static final String POLICY_ORDER_TIME = "促销时间";
	public static final String STATE_INIT_TIME = "制单时间";

	//系统用户操作动作
	public static final String WEB_LOG_ACTION_LOGIN = "登录";



	//WEBLOG操作项目
	public static final String WEB_LOG_ITEM_BRANCH_REQUEST = "网页要货";
	public static final String WEB_LOG_ITEM_SERVER = "网页后台";
	public static final String WEB_LOG_ITEM_PHONE = "手机查询";
	public static final String WEB_LOG_ITEM_POS = "POS终端";


	/**
	 * 支付宝操作类型
	 */
	public static final String ALIPAY_CREATE_BY_BARCODE = "条码支付";
	public static final String ALIPAY_PRE_CREATE_BY_QR = "二维码支付";
	public static final String ALIPAY_CANCEL = "撤销";
	public static final String WEIXINPAY_CREATE_BY_BARCODE = "微信条码支付";
	public static final String WEIXINPAY_CREATE_BY_QRCODE = "微信二维码支付";
	public static final String WEIXINPAY_CANCEL = "微信撤销";
	public static final String DP_HUI_BY_BARCODE = "大众点评条码支付";
	public static final String DP_HUI_CANCEL = "大众点评撤销";
	public static final String DP_HUI_BY_QR = "大众点评二维码支付";



	/**
	 * 第三方支付方式
	 */
	public static final String PAY_TYPE_ALIPAY = "支付宝";
	public static final String PAY_TYPE_WEIXINPAY = "微信支付";
	public static final String PAY_TYPE_DPHUI = "大众闪惠";


	/** 周期单位 */
	public static final String PERIOD_UNIT_WEEk = "周";
	public static final String PERIOD_UNIT_DAY = "天";
	public static final String PERIOD_UNIT_MONTH = "月";


	/**
	 * 在线订单时间轴类型
	 */
	public static final String BANNER_DATE_TYPE_PAY = "付款时间";
	public static final String BANNER_DATE_TYPE_DELEVER = "发货时间";


	/**
	 * 报损原因类型
	 */
	public static final String ADJUSTMENT_REASON_TYPE_LOSS = "报损";
	public static final String ADJUSTMENT_REASON_TYPE_ADJUST = "调整";



	public static final int REDIS_CACHE_LIVE_SECOND = 86400; //缓存有效时间一天


	//bookResource
	public static final String ADJUSTMENT_REASON = "库存调整原因参数";
	public static final String POS_ITEM_TYPE = "商品类别";
	public static final String CARD_CATEGORY = "消费卡类型";
	public static final String CHAIN_DELIVERY_PARAM = "连锁配送参数";
	
	
	
	/** 缓存名称 */
	public static final String CACHE_NAME_POS_CLIENT = "CACHE_POS_CLIENT:";
	public static final String CACHE_SALE_CEASE_ITEM = "CACHE_SALE_CEASE_ITEM:";
	public static final String CACHE_NAME_POS_ITEM = "CACHE_POS_ITEM:";
	public static final String CACHE_NAME_BRANCH = "CACHE_BRANCH:";
	public static final String CACHE_NAME_SUPPLIER = "CACHE_SUPPLIER:";
	public static final String CACHE_SYSTEM_BOOK = "CACHE_SYSTEM_BOOK";



	public static final int CACHE_LIVE_SECOND = 1200; //缓存有效时间20分钟



	//采购范围
	public static final String ITEM_PURCHASE_SCOPE_SELFPRODUCT = "自产";
	public static final String ITEM_PURCHASE_SCOPE_BRANCH = "门店采购";



	//调整方向
	public static final String ADJUSTMENT_DIRECTION_IN = "入库";
	public static final String ADJUSTMENT_DIRECTION_OUT = "出库";

	/**
	 * CARD_BILL_TYPE
	 */
	public static final String CARD_BILL_TYPE_TRANSFER = "调拨";


	//卡状态
	public static final int CARD_INIT_REVOKE_CODE= 9;//初始化|回收


	/**
	 * 初始化卡回收
	 */
	public static final String CARD_USER_LOG_REVOKE_INIT_CARD = "初始化卡回收";


	/**
	 * REGISTER_TYPE
	 */
	public static final String REGISTER_TYPE_DELIVER = "发卡";
	public static final String REGISTER_TYPE_ORI = "老会员卡转卡";


	public static final String MARKETACTION_MOMENTS_ACTION = "朋友圈营销";

	//在线订单状态
	public static final State ONLINE_ORDER_SETTLEMENT = new State(1 << 1, "已付款");
	public static final State ONLINE_ORDER_CODPAY = new State(1 << 1, "货到付款");

	/**
	 * 在线订单类型
	 */
	public static final int ONLINE_ORDER_TYPE_NORMAL = 0; //普通订单
	public static final int ONLINE_ORDER_TYPE_TEAM = 1; //团购订单


	/**
	 * 在线订单来源
	 */

	public static final String ONLINE_ORDER_SOURCE_API = "第三方商城";
	public static final String ONLINE_ORDER_SOURCE_NH_WSHOP = "新希望微商城";
	public static final String ONLINE_ORDER_SOURCE_YOUZAN = "有赞";
	public static final String ONLINE_ORDER_SOURCE_MEITUAN = "美团外卖";
	public static final String KDT_SHIPPINT_TYPE_FETCH = "到店自提";
	public static final String DELIVER_TYPE_TRANSFER = "送货上门";


	public static final String PRESENT_RECORD="赠送记录";
	public static final String RETURN_RECORD="退货记录";
	public static final String ANTI_SETTLEMENT="反结账记录";

	public static final String LESS_THAN="小于";
	public static final String LESS_THAN_OR_EQUAL="小于等于";
	public static final String EQUAL="等于";
	public static final String MORE_THAN="大于";
	public static final String MORE_THAN_OR_EQUAL="大于等于";

	public static final int POS_ORDER_DETAIL_CREATE_REPAY = 9; //制单反结账


	/**
	 * MNS_COMMAND
	 */
	public static String MESSAGE_COMMAND_WEIXIN_CARD_REVOKE = "AMA_WEIXIN_CARD_REVOKE";
	public static String MESSAGE_COMMAND_WEIXIN_CARD_UPDATE = "AMA_WEIXIN_CARD_UPDATE";

	/**
	 * 订单来源条件
	 */
	//public static final String POS_ORDER_SALE_TYPE_WCHAT = "微商城";
	public static final String POS_ORDER_SALE_TYPE_BRANCH = "实体店";


	public static final String BRANCH_TYPE_DIRECT = "直营";


	/**
	 * 团购类型  若增加类型  请搜索常量 相关报表需要修改
	 */
	public static final String TEAM_TYPE_DIANPING = "大众点评";
	public static final String TEAM_TYPE_MEITUAN = "美团";
	public static final String TEAM_TYPE_NUOMI = "糯米";
	public static final String TEAM_TYPE_WECHAT = "微信代金券";



	/**
	 * 筐来源
	 */
	public static final String PACKAGE_LOG_SOURCE_SUPPLIER = "供应商";
	public static final String PACKAGE_LOG_SOURCE_CENTER = "中心";



	public static final String SMS_SEND_SUCCESS = "已发送";
	public static final String SMS_SEND_FAIL = "发送中";
	public static final String SMS_SEND_NULL = "未发送";


	public static final String S_Prefix_WO = "WO";//批发销售单


	/**
	 * 微信网店类型
	 */
	public static final String WEIXIN_BRANCH_TYPE_KDT="口袋通";
	public static final String WEIXIN_BRANCH_TYPE_YOUZAN="有赞";



	public 	static final String ROUND_TYPE_HALF = "四舍五入";
	public 	static final String ROUND_TYPE_OFF = "舍零";
	public 	static final String ROUND_TYPE_TO = "舍入";

	public 	static final String MONEY_SCALE_TYPE_YUAN = "元";
	public 	static final String MONEY_SCALE_TYPE_JIAO = "角";
	public 	static final String MONEY_SCALE_TYPE_FEN = "分";


	/**
	 * wholesale_book_sale_state
	 */
	public static final String WHOLESALE_BOOK_SALE_STATE_FINISH = "发货完成";
	public static final String WHOLESALE_BOOK_SALE_STATE_PART = "部分发货";


	//结算周期
	public static final String SETTLE_TYPE_TEMP = "临时指定";
	public static final String SETTLE_TYPE_FIXED_DATE = "指定帐期";
	public static final String SETTLE_TYPE_FIXED_DAY = "指定日期";
	public static final String SETTLE_TYPE_FIXED_NOW = "货到付款";


	/**
	 * purchase_order_receive_state
	 */
	public static final String PURCHASE_ORDER_RECEIVE_STATE_FINISH = "收货完成";
	public static final String PURCHASE_ORDER_RECEIVE_STATE_PART = "部分收货";


	//促销特价类型
	public static final String POLICY_PROMOTION_SUPPLIER = "供应商返利"; //1
	public static final String POLICY_PROMOTION_CENTER = "总部返利";		 //2
	public static final int POLICY_PROMOTION_SUPPLIER_CODE = 1;
	public static final int POLICY_PROMOTION_CENTER_CODE = 2;


	public static final String UNOUT_DAYS = "未进货天数";


	/**
	 * 营销活动类型
	 */
	public static final String MARKET_ACTION_TYPE_PAYMENT = "支付方式";
	
	/**
	 * card_user_log_type
	 */
	public static final String CARD_USER_LOG_TYPE_LOCK = "锁定|解锁";
	public static final String CARD_USER_LOG_TYPE_CONSUME = "消费修复";
	public static final String CARD_USER_LOG_TYPE_DEPOSIT = "存款修复";
	public static final String CARD_USER_LOG_TYPE_POINT = "积分修复";
	public static final String CARD_USER_LOG_TYPE_CARD = "卡类型修改";
	public static final String CARD_USER_LOG_TYPE_RECAL_DATE = "异常重新计算时间";
	public static final String CARD_USER_LOG_TYPE_REVOKE = "卡回收";
	public static final String CARD_USER_LOG_TYPE_CHANGE_STORGE = "修改卡介质";
	public static final String CARD_USER_LOG_TYPE_INIT = "卡初始化";
	public static final String CARD_USER_LOG_INIT_PASSWORD = "清空密码";
	
	/**
	 * client_point_type
	 */
	public static final String C_CLIENT_POINT_POS = "POS消费";
	public static final String C_CLIENT_POINT_CHANGE = "积分兑换";
	public static final String C_CLIENT_POINT_CONSUME = "卡消费";
	public static final String C_CLIENT_POINT_DEPOSIT = "卡存款";
	public static final String C_CLIENT_POINT_TO_DEPOSIT = "积分转储值";
	public static final String C_CLIENT_POINT_SUMMARY = "积分归档";
	public static final String C_CLIENT_POINT_DELETE = "积分清除";
	public static final String C_CLIENT_POINT_ORI = "老会员转卡";
	public static final String C_CLIENT_POINT_ADMIN_ADJUST = "管理员调整";
	
	/**
	 * 自定义群体类型
	 */
	public static final String GROUP_CUSTOMER_TYPE_CARD = "会员群体";
	public static final String GROUP_CUSTOMER_TYPE_POS_CLIENT = "批发客户群体";
	
	/**
	 * 群体类型
	 */
	public static final String GROUP_CUSTOMER_PROPERTY_DEFAULT = "默认";
	public static final String GROUP_CUSTOMER_PROPERTY_CUSTOM = "自定义";



	//pos_order_detail中支付类型
	public static final String PAYMENT_COUPON = "消费券";


	public static final String CUSTOMER_MODEL_CUSTOMER_COUNT = "群体人数";
	public static final String CUSTOMER_MODEL_COUPON_CONSUME = "消费券使用数";
	public static final String CUSTOMER_MODEL_CONSUME_COUNT = "消费次数";

	/**
	 * 现金类型
	 */
	public static final String CASH_TYPE_TOTAL = "现金累计总额";
	public static final String CASH_TYPE_POS = "前台收入";
	public static final String CASH_TYPE_CARD_DEPOSIT = "卡存款";
	public static final String CASH_TYPE_REPLACE_CARD = "换卡";
	public static final String CASH_TYPE_RELAT_CARD = "续卡";
	public static final String CASH_TYPE_OTHER_INOUT = "其他收支";



	public static final String RETAIL_POS_LOG_DEL = "删除";
	public static final String RETAIL_POS_LOG_ALL_CANCLE = "整单取消";


	//早/午/晚市指标分析分类
	public static final String MARKET_CUSTOMER_COUNT = "消费次数";



	public static final String CUSTOMER_MODEL_DEPOSIT_MONEY = "储值存款";
	public static final String CUSTOMER_MODEL_DEPOSIT_CASH = "储值收款";
	public static final String CUSTOMER_MODEL_CARD_CONSUME = "储值消费";
	public static final String CUSTOMER_MODEL_POINT_CONSUME = "积分消费";


	public static final String BRANCH_TYPE_JOIN = "加盟";

	public static final String ONLINE_ORDER_SOURCE_MERCURY= "水星微商城";
	
	
	
	
	
	/**
	 * redis缓存前缀
	 */
	public static final String REDIS_PRE_OLD_TRANSFER_OUT_DATA_SYNCH = "AMA_OLD_TRANSFER_OUT_DATA_SYNCH"; //调出单历史数据redis同步标记
	public static final String REDIS_PRE_TRANSFERED_ITEM = "AMA_TRANSFERED_ITEM"; // 已配送商品
	public static final String REDIS_PRE_BOOK_RESOURCE = "AMA_BOOK_RESOURCE";
	public static final String REDIS_PRE_BRANCH_RESOURCE = "AMA_BRANCH_RESOURCE";
	public static final String REDIS_PRE_USER_COLLECT_BRANCH = "AMA_USER_COLLECT_BRANCH";//用户收藏门店
	public static final String REDIS_PRE_SYSTEM_BOOK = "AMA_SYSTEM_BOOK";
	
	
	
	//branchResource
	public static final String BRANCH_EXPRESEE_COMPANY = "货运公司参数";
	public static final String PARAM_ANALYTICS_MODEL = "C_会员模型分析参数";
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
