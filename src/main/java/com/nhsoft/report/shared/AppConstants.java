package com.nhsoft.report.shared;





public class AppConstants {
	/**NLGP730   */
    public static final String SESSION_CURRENT_CLOSED_CASH = "NLGP730_CURRENT_CLOSEDCASH";
    public static final String NLGP730_RESULT_DATA = "NLGP730_RESULT_DATA";
    public static final String NLGP730_CURRENT_REQUEST_ID = "NLGP730_REQUEST_ID";
    public static final String NLGP730_LAST_REQUEST_ID = "NLGP730_REQUEST_ID";
    
	//卡介质类型
	public static final String CARD_TYPE_IC = "IC卡";	
	public static final String CARD_TYPE_MAGCARD = "磁卡";

	public static final int CACHE_LIVE_SECOND = 1200; //缓存有效时间20分钟
	public static final int CACHE_LIVE_DAY = 60 * 60 * 24; //缓存有效时间一天

	
	public static final String GROUP_SHARE_CARD = "储值卡共享分组";
    public static final String GROUP_SHARE_CUSTOMER = "客户共享";
    
    public static final String GROUP_SHARE_QUERY = "查询分组";
    
    public static final String GROUP_BRANCH_SHARE_QUERY = "门店查询模板";
  
    public static final String GROUP_SHARE_WEB = "分支机构";
    
    public static final String ROLE_TYPE_SHOP = "分店权限";

    public static final String ROLE_TYPE_GROUP = "群组权限";
    
    public static final String INIT_TIME = "1899-12-30 00:00:00";
    
    public static final Short RECEPIPT_NORMAL = 1;  //有效
    
    public static final Short RECEPIPT_CANCEL = 2;  //撤销
    
    // 服务器端权限控制策略
    public static final String SECURITY_POLICY_STRICT = "SECURITY_POLICY_STRICT";// 默认全部需要验证，只有罗列部分不验证
    public static final String SECURITY_POLICY_LOOSE = "SECURITY_POLICY_LOOSE";// 默认全部不需要验证，只有罗列部分需要验证  
    
    public static final String CURRENT_USER = "current_user";   
    public static final String CURRENT_SUPPLIER_USER = "current_supplier_user"; 
    public static final String CURRENT_IP = "current_ip";
    public static final String CURRENT_URL = "CURRENT_URL";
    public static final String CURRENT_BASE_URL = "CURRENT_BASE_URL"; //基础资料服务

    public static final String ONLINE_USERS = "online_users";   
    public static final String CURRENT_POS_IP = "current_pos_ip";//为防止收银终端的请求受到IP限制 单独一个名称存放IP地址

       
    //bookResource
    public static final String POS_ITEM_TYPE = "商品类别";
    public static final String CHAIN_DELIVERY_PARAM = "连锁配送参数";
    public static final String POS_ITEM_BRAND = "商品项目品牌";
    public static final String CENTER_MESSAGE_RECEIVER = "中心留言接收员";
    public static final String POSCLIENT_MESSAGE_RECEIVER = "批发商中心留言接收员";
    public static final String SUPPLIER_MESSAGE_RECEIVER = "供应商中心留言接收员";
    public static final String MATRIX_TITLE = "矩阵抬头";
    public static final String SUPPLIER_CATEGORY = "供应商类别";
    public static final String TICKET_CATEGORY = "消费券参数";
    public static final String CARD_CATEGORY = "消费卡类型";
    public static final String FEE_ITEM = "费用项目";
    public static final String POS_PAYMENT_TYPE = "零售支付方式参数";
    public static final String ITEM_DEPARTMENT = "商品部门";
    public static final String ITEM_UNIT_GROUP = "计量单位组";
    public static final String POS_CLIENT_TYPE = "客户类型";
    public static final String POS_ITEM_DEPARTMENT = "商品部门";
    public static final String ADJUSTMENT_REASON = "库存调整原因参数";
    public static final String POS_CLIENT_PARAM = "零售客户参数";
    public static final String POS_ITEM_PARAM = "商品项目参数";
    public static final String POS_MACHINE_CONFIRM = "POS终端登录验证";
    public static final String POS_SALE_PARAM = "零售前台销售参数";
    public static final String PICTURE_PARAM = "轮播图片参数";
    public static final String EMPLOYEE_KIND = "员工类别";
    public static final String ITEM_DETAIL_MODEL = "商品描述模板";
    public static final String CENTER_NOTICE_TYPE = "公告栏类别";
    public static final String CARD_PARAM= "消费卡参数";
    public static final String CARD_TYPE_PARAM= "消费卡类型";
    public static final String DEPOSIT_SEND_MONEY= "存款金额赠送";
    public static final String POINT_CONVERT= "积分兑换物";
    public static final String POINT_PARAM= "积分规则";
    public static final String SET_GUIDE= "下次登入设置向导不启动标记"; 
	public static final String MAIL_INFO_PARAM= "邮件订阅参数";
	public static final String WEIXIN_PARAM = "微信参数";
	public static final String PASSWORD_PARAM = "密码设置参数";
	public static final String LAST_CHECK_CARD_DATE = "上次异常卡检测时间";
	public static final String CUSTOMER_GROUP_PARAM = "顾客群体参数";
	public static final String DP_SHOP_MAPPING = "大众点评商户关联";
	public static final String API_KEY_PARAM = "第三方APP参数";
	public static final String BOOK_RESOURCE_TOUCH_FORM_STYLE = "触摸屏界面布局";
	public static final String BOOK_RESOURCE_BOOK_PARAM = "帐套参数";
	public static final String BOOK_RESOURCE_RETAIL_ONLINE_UNDISOUNT_ITEM = "支付不参与折扣商品";
	public static final String CARD_USER_UPGRADE_PARAM = "卡类型升级参数";
	public static final String ORDER_FID_GENERATE_BY_DATE = "业务单据主键根据日期生成";
    public static final String WEIXIN_ITEM_CATEGORY = "微商城商品类别";
    public static final String WEIXIN_POSTAGE_TEMPLATE = "微商城运费模板";
    public static final String SYSTEM_IMAGE_STORGE_CATEGORY = "图片库类别";
    public static final String ITEM_PROPERTY_TYPE_PARAM = "商品标签类别";
    public static final String TRANSFER_LINE_REGION_PARAM = "配送波次参数";
    public static final String ACCOUNT_SUBJECT_PARAM = "会计科目参数";
    public static final String ITEM_EXTEND_ATTRIBUTE_PARAM = "商品扩展属性参数";
    public static final String WHOLESALE_DEFAULT_CLIENT = "批发APP默认客户";
    public static final String APP_MACHINE_CONFIRM = "APP终端登录验证";


    //branchResource
    public static final String WEB_BRANCH_PAID_RATE = "WEB_门店预算值"; 
    public static final String SMS_CONFIG_PARAM = "短信设置参数"; 
    public static final String C_SMS_USEFUL_EXPRESSIONS = "C_短信常用语"; 
    public static final String POS_ITEM_PRICE_BAND = "商品价格带参数"; 
    public static final String BRANCH_EXPRESEE_COMPANY = "货运公司参数"; 
    public static final String POS_ITEM_MODEL = "商品项目模板"; 
    public static final String DELETE_DATA_PARAM = "历史数据删除参数"; 
    public static final String BRANCH_REQUEST_SYSTEM_PARAM = "门店要货系统配置";
    public static final String BRANCH_SMS_CUSTOMER_GROUP = "短信组管理";
	public static final String BRANCH_POS_MACHINE_PARAM= "手持设备设置参数";
	public static final String BRANCH_WEIXIN_PUBLIC_ITEMS= "微店已发布过的商品";
	public static final String BRANCH_WEIXIN_PARAM = "微店设置参数";
	public static final String POS_ITEM_SEQUENCE_PARAM = "商品排序参数";
	public static final String MT_I15_SHORTCUT_KEY = "MTI15预置键";
	public static final String MT_I15_RECEIPT = "MTI15标头脚注";
	public static final String BRANCH_CUSTOM_QUERY_TYPE = "用户自定义查询条件参数";
	public static final String BRANCH_RESOURCE_TOUCH_FORM_STYLE = "触摸屏界面布局";
	public static final String BRANCH_RESOURCE_CARD_PARAM = "分店消费卡参数";
	public static final String BRANCH_OPEN_PARAM = "网店设置参数";
	public static final String LAST_INVENTORY_SNOPSHOT_TIME = "上次库存快照时间";
    public static final String PRICETAG_PARAM = "价签参数";
    public static final String BRANCH_LICENSE = "CPU_CLIENT_2017_06";



    //clientDict
    public static final String CLIENT_DICT_SHIP_ADDRESS = "物流配送地址";
    
    //商品类型
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
    
    public static final int ITEM_GRADA_LEVEL1 = 1; 
    public static final int ITEM_GRADA_LEVEL2 = 2; 
    public static final int ITEM_GRADA_LEVEL3 = 3; 
    public static final int ITEM_GRADA_LEVEL4 = 4; 
    public static final int ITEM_GRADA_LEVEL5 = 5; 
    public static final String ITEM_GRADA_LEVEL1_NAME = "精品"; 
    public static final String ITEM_GRADA_LEVEL2_NAME = "一级"; 
    public static final String ITEM_GRADA_LEVEL3_NAME = "二级"; 
    public static final String ITEM_GRADA_LEVEL4_NAME = "三级"; 
    public static final String ITEM_GRADA_LEVEL5_NAME = "残次"; 
    
    //订单状态
    public static final String ORDER_STATUS_DELIVERY ="全部收货";
    public static final String ORDER_STATUS_RECEIVE ="全部发货";
    public static final String ORDER_STATUS_LITTLERECE ="待处理";
    public static final String ORDER_STATUS_UNTREATED="部分发货";
    
    //留言箱
    public static final String RECEIVE_MESSAGE_CATEGORY_NORMAL = "留言箱";
    public static final String RECEIVE_MESSAGE_CATEGORY_FAVORITE = "收藏夹";
    public static final String RECEIVE_MESSAGE_CATEGORY_SYSTEM = "系统留言";
    public static final String RECEIVE_MESSAGE_CATEGORY_DELETE = "已删除的留言";
    public static final String RECEIVE_MESSAGE_CATEGORY_DELETE_OVER = "彻底删除的留言";
    
    public static final State STATE_INIT = new State(1 << 0, "制单");
	public static final State STATE_AUDIT = new State(1 << 1, "审核");
	public static final State STATE_INSPECT = new State(1 << 2, "作废");
	public static final State STATE_END = new State(1 << 2, "中止");
	public static final State STATE_INVALID = new State(1 << 2, "失效");
	public static final State STATE_PRINT = new State(1 << 2, "打印");
	public static final State STATE_REPLY = new State(1 << 2, "批复");
	public static final State STATE_EXECUTE = new State(1 << 3, "处理");
	public static final State STATE_AUDITING = new State(1 << 3, "审核中");

	
	public static final String MARKETACTION_IMMEDIATE = "即时奖励";
	public static final String MARKETACTION_NEW = "新会员奖励";
	public static final String MARKETACTION_POS = "POS消费赠券";
	public static final String MARKETACTION_DEPOSIT = "卡存款赠券";
	public static final String MARKETACTION_DELEVIER_CARD = "开卡赠券";
	public static final String MARKETACTION_BIND_WEIXIN = "微信绑定赠券";
	public static final String MARKETACTION_BIRTHDAY = "会员生日奖励";
	public static final String MARKETACTION_WSHOP_CONSUME = "微商城消费赠券";
	public static final String MARKETACTION_WSHOP_ACTION = "微商城领券活动";
	public static final String MARKETACTION_ONLINE_DEPOSIT = "微会员存款赠券";
	public static final String MARKETACTION_MOMENTS_ACTION = "朋友圈营销";
	
    public static final State TICKET_STATE_INIT = new State(1 << 0, "启用");
	public static final State TICKET_STATE_AUDIT = new State(1 << 1, "已消费");
	public static final State TICKET_STATE_INSPECT = new State(1 << 2, "作废");	
	public static final Integer S_COUPON_DETAIL_SALE_CODE = 1;
    public static final String S_COUPON_DETAIL_SALE_NAME = "启用";
    public static final Integer S_COUPON_DETAIL_CONSUME_CODE = 3;
    public static final String S_COUPON_DETAIL_CONSUME_NAME = "启用|已消费";
    public static final Integer S_COUPON_DETAIL_CANCEL_CODE = 5;
    public static final String S_COUPON_DETAIL_CANCEL_NAME = "启用|作废";
	
    public static final int STATE_INIT_CODE = 1;
    public static final int STATE_AUDIT_CODE = 2;
    public static final int STATE_CANCEL_CODE = 4;
    public static final int STATE_END_CODE = 4;
    public static final int STATE_REPLY_CODE = 4;
    public static final int STATE_EXECUTE_CODE = 8;
    
    public static final int STATE_INIT_AUDIT_CODE = STATE_INIT_CODE | STATE_AUDIT_CODE;
    public static final int STATE_INIT_AUDIT_CANCEL_CODE = STATE_INIT_AUDIT_CODE | STATE_CANCEL_CODE;
    public static final int STATE_INIT_AUDIT_STOP_CODE = STATE_INIT_AUDIT_CODE | STATE_END_CODE;
    public static final int STATE_INIT_AUDIT_REPLY_CODE = STATE_INIT_AUDIT_CODE | STATE_REPLY_CODE;
    public static final int STATE_INIT_AUDIT_REPLY_EXECUTE_CODE = STATE_INIT_AUDIT_REPLY_CODE | STATE_EXECUTE_CODE;
    
    	
	//付款状态
	public static final String ORDER_STATUS_PAYMENT = "已付款";
	public static final String ORDER_STATUS_UNPAYMENT = "未付款";
	
	//s是否过期
	public static final int EXPIRE = 1;
	public static final int UNEXPIRE = 2;
	
	//发货分店编号
	public static final Integer REQUEST_ORDER_OUT_BRANCH_NUM = 99;
	
	//商品（商品类别）排序Top10的排序方式
	public static final String POS_ITEM_RANK_MONTH = "本月";
	public static final String POS_ITEM_RANK_QUARTER = "本季";
	public static final String POS_ITEM_RANK_YEAR = "本年";
	public static final String POS_ITEM_RANK_QTY = "数量";
	public static final String POS_ITEM_RANK_MONEY = "金额";
	
	//成本核算方式
	public static final String C_ITEM_COST_MODE_AVERAGE = "加权平均法";
	public static final String C_ITEM_COST_MODE_FIFO = "先进先出法";
	public static final String C_ITEM_COST_MODE_MANUAL = "手工指定";
	public static final String C_ITEM_COST_MODE_CENTER_MANUAL = "中心手工指定";//中心手工指定 门店加权平均

	
	//商品列表排序类别
	public static final String ORDER_BY_PRICE = "按价格排序";
	public static final String ORDER_BY_SALE = "按中心销量排序";
	
	//商品列表排序方式
	public static final String ORDER_BY_PRICE_UP = "按价格升序排序";
	public static final String ORDER_BY_SALE_UP = "按中心销量升序排序";
	public static final String ORDER_BY_SALE_DOWN = "按中心销量降序排序";
	public static final String ORDER_BY_STORE_UP = "按中心库存量升序排序";
	public static final String ORDER_BY_STORE_DOWN = "按中心库存量降序排序";
	public static final String ORDER_BY_COOL_UP = "按点赞量升序排序";
	public static final String ORDER_BY_COOL_DOWN = "按点赞量降序排序";
	public static final String ORDER_BY_FAV_UP = "按收藏量升序排序";
	public static final String ORDER_BY_FAV_DOWN = "按收藏量降序排序";
	
	public static final String NEW_ITEM_RECOMMEND = "新品上架";
	public static final String RECOMMEND_ITEM_RECOMMEND = "推荐商品";
	public static final String GOODSALE_ITEM_RECOMMEND = "畅销商品";
	public static final String NOSTOCK_ITEM_RECOMMEND = "缺货商品";
	public static final String HIGH_PROFIT_ITEM_RECOMMEND = "高毛利商品";
	public static final String RECENT_OUT_ITEM_RECOMMEND = "近期订货商品";
	public static final String FAV_ITEM_RECOMMEND = "我的收藏夹";
	
	//权限拥有者
	public static final String PRIVILEGE_OWNER = "WEB";
	
	public static final String ABC_MARKET_SIGN_PRIX = "RSU";
	public static final String ABC_MARKET_SIGN_PAY_REQUEST = "REQ";
	public static final String ABC_MARKET_SIGN_PAY_ORDER = "RQP";
	public static final String ABC_MARKET_SIGN_MOFIF_CUST = "RMC";
		
	public static final Integer ABC_MARKET_STATE_SIGN_PAY_ERROR = 0;
	public static final Integer ABC_MARKET_STATE_SIGN_PAY_REQUEST = 1;
    public static final Integer ABC_MARKET_STATE_SIGN_PAY_ORDER = 2;
    
    public static final String S_Prefix_PO = "PO";//采购订单
    public static final String S_Prefix_PI = "PI";//采购收货单
    public static final String S_Prefix_MI = "MI";//调入单
    public static final String S_Prefix_MO = "MO";//调出单
    public static final String S_Prefix_OO = "OO";//库存调整单
    public static final String S_Prefix_CR = "CR";//盘点单
    public static final String S_Prefix_YH = "YH";//要货单
    public static final String S_Prefix_LO = "LO";//进销存日志
    public static final String S_Prefix_RO = "RO";//采购退货单
    public static final String S_Prefix_ZH = "ZH";//部门调拨单
    public static final String C_Prefix_CS = "CS";//组合拆分单
    public static final String C_Prefix_RP = "RP";//客户结算单
    public static final String C_Prefix_OP = "OP";//其它收支单
    public static final String S_Prefix_CP = "CP";//供应商结算单
    public static final String S_Prefix_ZP = "ZP";//内部结算单
    public static final String S_Prefix_FQ = "FS";//消费券发售
    public static final String S_Prefix_YF = "YF";//供应商预付单
    public static final String S_Prefix_YS = "YS";//内部预收单
    public static final String S_Prefix_YC = "YC";//客户预收单
    public static final String S_Prefix_JQ = "JQ";//价签申请单
    public static final String S_Prefix_WB = "WB";//批发订单
    public static final String S_Prefix_WO = "WO";//批发销售单
    public static final String S_Prefix_WR = "WR";//批发退货单
    public static final String S_Prefix_MP = "MP";//会员卡结算单
    public static final String S_Prefix_MA = "MA";//营销活动
    public static final String S_Prefix_GC = "GC";//客户群组
    public static final String S_Prefix_SH = "SH";//发货单
    public static final String S_Prefix_WD = "WD";//在线订单
    public static final String S_Prefix_PA = "PA";//筐操作记录
    public static final String S_Prefix_WT = "WT";//在线团购订单
    public static final String S_Prefix_NS = "NS";//内部调拨单
    public static final String S_Prefix_PH = "PH";//配货单
    public static final String S_Prefix_BC = "BC";//批量制卡
    

    //树类别
    public static final String TREE_TYPE_CATEGORY = "按类别";
    public static final String TREE_TYPE_BRAND = "按品牌";
    
    //经营指标分析分类
    public static final String BUSINESS_NEW_CUSTOMER = "新增会员";
    public static final String BUSINESS_NEW_CARD = "新增会员卡";
    public static final String BUSINESS_DEPOSIT_CONSUME = "储值消费";
    public static final String BUSINESS_POINT_CONSUME = "积分消费";
    public static final String BUSINESS_CARD_DEPOSIT = "储值存款";
    public static final String BUSINESS_CARD_DEPOSIT_CASH = "储值收款";
    public static final String BUSINESS_COUPON_CONSUME = "消费券使用数";
    
    //早/午/晚市指标分析分类
    public static final String MARKET_CUSTOMER_COUNT = "消费次数";
    public static final String MARKET_NEW_CARD = "新增会员卡";
    public static final String MARKET_DEPOSIT_CONSUME = "储值消费";
    public static final String MARKET_POINT_CONSUME = "积分消费";
    public static final String MARKET_CARD_DEPOSIT = "储值存款";
    public static final String MARKET_CARD_DEPOSIT_CASH = "储值收款";
    public static final String MARKET_COUPON_CONSUME = "消费券使用数";
    
    //最有价值会员
    public static final String VALUBLE_CONSUME_MONEY_RANK = "储值消费排行";
    public static final String VALUBLE_CONSUME_COUNT_RANK = "储值消费次数排行";
    public static final String VALUBLE_DEPOSIT_MONEY_RANK = "储值存款排行";
    public static final String VALUBLE_DEPOSIT_CASH_RANK = "储值收款排行";
    public static final String VALUBLE_POINT_CONSUME_RANK = "积分消费排行";
    public static final String VALUBLE_POINT_OVERAGE_RANK = "剩余积分排行";
    public static final String VALUVLE_TICKET_SALE_RANK = "消费券使用数排行";
    
    //报表参数
    public static final String PARAM_VALUE_CFM_PARAM = "消费频次模型参数";
    public static final String PARAM_VALUE_SPM_PARAM = "消费能力模型参数";
    public static final String PARAM_VALUE_LOC_PARAM = "客户流失模型参数";
    public static final String PARAM_VALUE_DLY_PARAM = "早午晚市模型参数";
    public static final String PARAM_ANALYTICS_MODEL = "C_会员模型分析参数";
   
    //报表分组
    public static final String NEW_CUSTOMER = "稀客";
    public static final String PASS_CUSTOMER = "过客";
    public static final String NORMAL_CUSTOMER = "散客";
    public static final String REGULAR_CUSTOMER = "常客";
    public static final String NO_CONSUME = "无消费";
    public static final String LOW_CONSUME = "低消费";
    public static final String NORMAL_CONSUME = "中消费";
    public static final String HIGH_CONSUME = "高消费";
    public static final String HIGH_LOSS = "严重流失";
    public static final String NORMAL_LOSS = "中度流失";
    public static final String LIGHT_LOSS = "轻度流失";
    public static final String NO_LOSS = "未流失";
    
    //查询时间类型
    public static final String BUSINESS_DATE_SOME_DATE = "日";
    public static final String BUSINESS_DATE_SOME_MONTH = "月";
    public static final String BUSINESS_DATE_SOME_YEAR = "年";
    public static final String BUSINESS_DATE_SOME_WEEK = "周";

    
	public static final String C_DEPOSIT_SORT_MONEY = "储值存款";
	public static final String C_DEPOSIT_SORT_CASH = "储值收款";
	
	public static final String C_POINT_CONSUME = "积分消费";
	public static final String C_POINT_SURPLUS = "剩余积分";

	public static final String SMS_SUPPLIER_JIXIN = "吉信通";
	public static final String SMS_SUPPLIER_JIXIN_PARAM = "http://service.winic.org:8009/sys_port/gateway/?";
	public static final String SMS_SUPPLIER_THREE = "三三得玖";
	public static final String SMS_SUPPLIER_THREE_PARAM = "http://gateway.iems.net.cn/GsmsHttp?";
	public static final String SMS_SUPPLIER_OPENMAS = "OpenMAS";
	public static final String SMS_SUPPLIER_OPENMAS_PARAM = "http://111.1.2.106:9080/OpenMasService";
	public static final String SMS_SUPPLIER_QIXIN = "企信通";
	public static final String SMS_SUPPLIER_QIXIN_PARAM = "http://dxhttp.c123.cn/tx/?";
	public static final String SMS_SUPPLIER_YMRT = "亿美软通";
	public static final String SMS_SUPPLIER_YMRT_PARAM = "http://sdkhttp.eucp.b2m.cn/sdkproxy/";
	public static final String SMS_SUPPLIER_YUNPIAN = "云片网";
	public static final String SMS_SUPPLIER_YUNPIAN_PARAM = "https://www.yunpian.com/";
	public static final String SMS_SUPPLIER_PUXUN = "普讯";
	public static final String SMS_SUPPLIER_PUXUN_PARAM = "http://202.91.244.252/puxun/";
	public static final String SMS_SUPPLIER_YST = "云树通";
	public static final String SMS_SUPPLIER_YST_PARAM = "http://115.159.219.238";

	public static final String SMS_SEND_SUCCESS = "已发送";
    public static final String SMS_SEND_FAIL = "发送中";
    public static final String SMS_SEND_NULL = "未发送";

	public static final String GROUP_CUSTOMER_PROPERTY_DEFAULT = "默认";
	public static final String GROUP_CUSTOMER_PROPERTY_CUSTOM = "自定义";

	//消费次数比较项
	public static final String CONSUME_COUNT_COMPARE_LAST_MONTH = "上个月";
	public static final String CONSUME_COUNT_COMPARE_TWO_MONTH = "上上个月";
	public static final String CONSUME_COUNT_COMPARE_LAST_QUARTER = "上个季度";
	public static final String CONSUME_COUNT_COMPARE_TWO_QUARTER = "上上个季度";

    public static final String CUSTOMER_MODEL_DEPOSIT_MONEY = "储值存款";
    public static final String CUSTOMER_MODEL_DEPOSIT_CASH = "储值收款";
    public static final String CUSTOMER_MODEL_CARD_CONSUME = "储值消费";
    public static final String CUSTOMER_MODEL_POINT_CONSUME = "积分消费";
    public static final String CUSTOMER_MODEL_COUPON_CONSUME = "消费券使用数";
    public static final String CUSTOMER_MODEL_CONSUME_COUNT = "消费次数";
    public static final String CUSTOMER_MODEL_CUSTOMER_COUNT = "群体人数";
    public static final String CUSTOMER_MODEL_CUSTOMER_NUM = "客户ID"; //仅计算多个群组总人数时用
    public static final String CUSTOMER_MODEL_GROUP_CUSTOMER_CUSTOMER_NUM = "GROUP_CUSTOMER_CUSTOMER_NUM"; //仅计算多个群组总人数时用
    public static final String CUSTOMER_MODEL_ACTION_CARD_USER = "营销活动会员";

	public static final String DATE_TYPE_COUPON_CREATE_DATE = "发券日期";
	public static final String DATE_TYPE_COUPON_CONSUME_DATE = "消费日期";
	public static final String DATE_TYPE_COUPON_VALID_DATE = "有效日期";
	public static final String DATE_TYPE_COUPON_EFFECTIVE_DATE = "生效日期";
	public static final String DATE_TYPE_COUPON_USE_DATE = "使用日期";
	
	public static final String C_CLIENT_POINT_POS = "POS消费";
	public static final String C_CLIENT_POINT_CHANGE = "积分兑换";
	public static final String C_CLIENT_POINT_CONSUME = "卡消费";
	public static final String C_CLIENT_POINT_DEPOSIT = "卡存款";
	public static final String C_CLIENT_POINT_TO_DEPOSIT = "积分转储值";
	public static final String C_CLIENT_POINT_SUMMARY = "积分归档";
	public static final String C_CLIENT_POINT_DELETE = "积分清除";
	public static final String C_CLIENT_POINT_ORI = "老会员转卡";
	public static final String C_CLIENT_POINT_ADMIN_ADJUST = "管理员调整";

	//换算方式
	public static final String ITEM_UNIT_TYPE_FIXED = "固定换算";
	public static final String ITEM_UNIT_TYPE_FLOATING = "浮动换算";

	//报表格式
	public static final String REPORT_PURCHASE_ORDER_JASPER = "PurchaseOrder.jasper";
	public static final String REPORT_PURCHASE_ORDER_JASPER_SUPPLIER = "PurchaseOrderBySupplier.jasper";
	public static final String REPORT_RECEIVE_ORDER_JASPER = "ReceiveOrder.jasper";
	public static final String REPORT_RETURN_ORDER_JASPER = "ReturnOrder.jasper";
	public static final String REPORT_RETURN_ORDER_JASPER_SUPPLIER = "ReturnOrderBySupplier.jasper";
	public static final String REPORT_REQUEST_ORDER_JASPER = "RequestOrder.jasper";
	public static final String REPORT_TRANSFER_IN_JASPER = "TransferIn.jasper";
	public static final String REPORT_TRANSFER_OUT_JASPER = "TransferOut.jasper";
	public static final String REPORT_CHECK_ORDER_JASPER = "CheckOrder.jasper";
	public static final String REPORT_CHECK_DIFF_JASPER = "CheckDiff.jasper";
	public static final String REPORT_ASSEMBLE_SPLIT_JASPER = "AssembleSplit.jasper";
	public static final String REPORT_ADJUSTMENT_ORDER_JASPER = "AdjustmentOrder.jasper";
	public static final String REPORT_SETTLEMENT_JASPER = "SupplierSettlement.jasper";
	public static final String REPORT_PRE_SETTLEMENT_JASPER = "SupplierPreSettlement.jasper";
	public static final String REPORT_INNER_SETTLEMENT_JASPER = "InnerSettlement.jasper";
    public static final String REPORT_INNER_PRE_SETTLEMENT_JASPER = "InnerPreSettlement.jasper";
    public static final String REPORT_WHOLESALE_BOOK_JASPER = "WholesaleBook.jasper";
    public static final String REPORT_WHOLESALE_ORDER_JASPER = "WholesaleOrder.jasper";
    public static final String REPORT_WHOLESALE_RETURN_JASPER = "WholesaleReturn.jasper";
    public static final String REPORT_ALLOCATION_ORDER_JASPER = "AllocationOrder.jasper";
    public static final String REPORT_CLIENT_PRE_SETTLEMENT_JASPER = "ClientPreSettlement.jasper";
    public static final String REPORT_CLIENT_SETTLEMENT_JASPER = "ClientSettlement.jasper";
    public static final String REPORT_SHIP_ORDER_JASPER = "shipOrder.jasper";
    public static final String REPORT_PRICE_PRINT = "reportPrice.jasper";
    public static final String REPORT_POLICY_PRICE_PRINT = "policyPricePrint.jasper";
    public static final String REPORT_OTHER_IN_OUT = "otherInOut.jasper";
    public static final String REPORT_OTHER_ONLINE_ORDER= "onlineOrder.jasper";
    public static final String REPORT_TRANSFER_SEND= "TransferSend.jasper";
    public static final String REPORT_REQUEST_TOTAL= "RequestTotal.jasper";
    public static final String REPORT_REQUEST_CENTER= "RequestSum.jasper";
    public static final String REPORT_BRANCH_TRANSFER_SEND= "BranchTransferSend.jasper";
    public static final String REPORT_COST_ADJUSTMENT_ORDER_JASPER = "CostAdjustmentOrder.jasper";
    public static final String REPORT_CARD_SETTLEMENT_JASPER = "CardSettlement.jasper";

	//网页
	public static final String WEB_REPORT_REQUEST_ORDER= "要货单报表";
	public static final String WEB_REPORT_OUT_ORDER= "调出单报表";
	public static final String WEB_REPORT_IN_ORDER= "调入单报表";
	public static final String WEB_REPORT_PURCHASE_ORDER= "采购单报表";
	public static final String WEB_REPORT_RECEIVE_ORDER = "收货单报表";
	public static final String WEB_REPORT_RETUREN_ORDER= "退货单报表";
	public static final String WEB_REPORT_WHOLESALE_BOOK= "批发订单报表";
	public static final String WEB_REPORT_WHOLESALE_SALE= "批发销售单报表";
	public static final String WEB_REPORT_WHOLESALE_RETURN= "批发退货单报表";
	public static final String WEB_REPORT_STORE_ORDER= "盘点单报表";
	public static final String WEB_REPORT_STORE_DIFFRENT= "库存差异处理";
	public static final String WEB_REPORT_SHIP_ORDER= "发货单报表";
	public static final String WEB_REPORT_AJUST_ORDER= "调整单报表";
	public static final String WEB_REPORT_ALLOCATION_ORDER= "转仓单报表";
	public static final String WEB_REPORT_ASSEMBLE_SPLIT= "组合拆分单报表";
	public static final String WEB_PRICE_PRINT_POS_ITEM= "价签打印";
	public static final String WEB_REPORT_ONLINE_ORDER= "网店订单报表";
	public static final String WEB_REPORT_INNER_SETTLEMENT= "加盟店结算报表";
	public static final String WEB_REPORT_CLIENT_SETTLEMENT= "客户结算报表";
	public static final String WEB_REPORT_SUPPLIER_SETTLEMENT= "供应商结算报表";
    public static final String WEB_REPORT_TRANSFER_SEND = "配送装车单报表";
    public static final String WEB_REPORT_REQUEST_CENTER = "要货中心报表";
    public static final String WEB_REPORT_CLIENT_PRESETTLEMENT = "客户预收报表";
    public static final String WEB_REPORT_BRANCH_TRANSFER_SEND = "门店配送单报表";
    public static final String WEB_REPORT_COST_AJUST_ORDER= "成本调整单报表";
    public static final String WEB_REPORT_OTHER_INOUT= "其他收支报表";
    public static final String WEB_REPORT_PRESETTLEMENT = "供应商预付报表";
    public static final String WEB_REPORT_INNER_PRESETTLEMENT = "加盟店预收报表";
    public static final String WEB_REPORT_CARD_SETTLEMENT = "会员卡结算报表";

    
	//结算类型
	public static final String SETTLEMENT_ALL = "全部结算";
	public static final String SETTLEMENT_CONSUME = "消费单结算";
	public static final String SETTLEMENT_DEPOSIT = "存款单结算";
	
	//单据名称
	public static final String ORDER_RECEIVE_ORDER = "收货确认单";
	public static final String ORDER_PURCHASE_ORDER = "采购订单";
	public static final String ORDER_PRESETTLEMENT_ORDER = "付款通知单";
	public static final String ORDER_RETURN_ORDER = "退货单";
	public static final String ORDER_OTHER_INOUT_ORDER = "其他费用单";
	public static final String ORDER_SETTLEMENT_ORDER = "结算通知单";
	
	  //库存进出日志摘要
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

	//默认支付方式
	public static final String PAYMENT_PRE_IN_SETTLEMENT = "预收冲应收";
	public static final String PAYMENT_PRE_OUT_SETTLEMENT = "预付冲应付";
	public static final int PAYMENT_CASH_CODE = 1;
	public static final int PAYMENT_YINLIAN_CODE = 2;
	//payment 中默认支付方式
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
	
	
	//pos_order_detail中支付类型
	public static final String PAYMENT_COUPON = "消费券";

	public static final String EMPLOYEE_WEB = "业务员";
	public static final String EMPLOYEE_SALE = "销售员";

	public static final String WEB_SYSTEM_BOOK = "WEB系统参数"; 
	
	//异常卡
	public static final String CARD_EXCEPTION_NE_POINT = "负积分";
	public static final String CARD_EXCEPTION_NE_BALANCE = "负余额";
	public static final String CARD_EXCEPTION_DEPOSIT_NO_SEQUENCE = "存款序号不连续";
	public static final String CARD_EXCEPTION_CONUSME_NO_SEQUENCE = "消费序号不连续";
	public static final String CARD_EXCEPTION_DEPOSIT_REPEAT = "存款序号重复";
	public static final String CARD_EXCEPTION_CONUSME_REPEAT = "消费序号重复";
	public static final String CARD_EXCEPTION_BALANCE_ERROR = "余额不一致";
	public static final String CARD_EXCEPTION_DEPOSIT_COUNT_EXCEPTION = "存款次数异常";
	public static final String CARD_EXCEPTION_CONUSME_COUNT_EXCEPTION = "消费次数异常";

	public static final String NEGATIVE_BALANCE = "负余额";
	public static final String NEGATIVE_POINT = "负积分";
	public static final String BALANCE_DISACCORD = "余额不一致";
	public static final String CONSUME_NUM_REPEAT = "消费序号重复";
	public static final String CONSUME_NUM_CONTIUATION = "消费序号不一致";
	public static final String DEPOSIT_NUM_REPEAT = "存款序号重复";
	public static final String DEPOSIT_NUM_CONTIUATION = "存款序号不一致";
	
	//销售统计项常量
	public static final String CHECKBOX_OUT = "本店调出";
	public static final String CHECKBOX_IN = "本店调入";
	public static final String CHECKBOX_WHO = "本店批发";
	public static final String CHECKBOX_WHO_RETURN = "本店批发退货";
	public static final String CHECKBOX_SALE = "分店销售";
	public static final String CHECKBOX_CHECK_ORDER = "库存盘点";
	public static final String CHECKBOX_ADJUSTMENT = "库存调整";
	public static final String CHECKBOX_RECEIVE = "本店收货";
	public static final String CHECKBOX_RETURN = "本店退货";
	
	//统计单位
	public static final String UNIT_SOTRE = "库存单位";
	public static final String UNIT_TRANFER = "配送单位";
	public static final String UNIT_PURCHASE = "采购单位";
	public static final String UNIT_BASIC = "基本单位";
	public static final String UNIT_PIFA= "批发单位";
	public static final String UNIT_USE= "常用单位";
	
	//单据状态
	public static final int POS_ORDER_DETAIL_CREATE_PURCHASE = 3; //制单订购
	
	public static final String POS_ORDER_CREATE_FINISH_NAME = "制单|完成";
	public static final int POS_ORDER_CREATE_FINISH = 5; //制单完成
	public static final State POS_ORDER_REPAY = new State(1 << 3, "反结账");
	public static final State POS_ORDER_CANCEL = new State(1 << 4, "取消");

	public static final int POS_ORDER_DETAIL_CREATE_REPAY = 9; //制单反结账
	
	public static final int POS_ORDER_DETAIL_CREATE_CANCEL = 17; //制单取消
	
	//单据明细状态
	public static final int POS_ORDER_DETAIL_STATE_SALE = 1; 
	public static final String POS_ORDER_DETAIL_STATE_SALE_NAME = "销售";
	public static final int POS_ORDER_DETAIL_STATE_PRESENT = 2; 
	public static final String POS_ORDER_DETAIL_STATE_PRESENT_NAME = "赠送";
	public static final int POS_ORDER_DETAIL_STATE_CANCEL = 4; 
	public static final String POS_ORDER_DETAIL_STATE_CANCEL_NAME = "退货";
	public static final int POS_ORDER_DETAIL_STATE_REMOVE = 8;
	public static final String POS_ORDER_DETAIL_STATE_REMOVE_NAME = "取消";
	
	//单据状态
	public static final String ORDER_STATE_CREATE = "制单";
	public static final String ORDER_STATE_ADUTIOR = "制单|审核";
	public static final String ORDER_UNAUDIT= "待审核";
	public static final String ORDER_AUDITED = "已审核";
	public static final String ORDER_STATE_PREPARED_GOODS= "已配货";
	public static final String ORDER_STATE_START_GOODS = "已发货";
	public static final String ORDER_RECEIVED = "已收货";
	
	//结算状态
	public static final String SETTLEMENT_STATE_NO = "未结算";
	public static final String SETTLEMENT_STATE_PART = "部分结算";
	public static final String SETTLEMENT_STATE_OK = "已结算";

	public static final String BRANCH_TYPE_JOIN = "加盟";
	public static final String BRANCH_TYPE_DIRECT = "直营";
	
	//结算周期
	public static final String SETTLE_TYPE_TEMP = "临时指定";
	public static final String SETTLE_TYPE_FIXED_DATE = "指定帐期";
	public static final String SETTLE_TYPE_FIXED_DAY = "指定日期";
	public static final String SETTLE_TYPE_FIXED_NOW = "货到付款";
	
	public static final String BUSINESS_TYPE_TOTAL = "购销";
	public static final String BUSINESS_TYPE_SHOP = "联营";
	public static final String BUSINESS_TYPE_DAY = "代销";

	public static final String STATE_INIT_TIME = "制单时间";
	public static final String STATE_APPLY_TIME = "申请时间";
	public static final String STATE_CREATE_TIME = "创建时间";
	public static final String STATE_AUDIT_TIME = "审核时间";
	public static final String STATE_CANCEL_TIME = "作废时间";
	public static final String STATE_EXPIRED_TIME = "到期时间";
	public static final String ALLOCATION_ORDER_TIME = "调拨时间";
	public static final String POLICY_ORDER_TIME = "促销时间";
	public static final String WHOLE_SALE_BOOK_TIME = "订单日期";
	public static final String WHOLE_SALE_ORDER_TIME = "销售日期";
	public static final String WHOLE_SALE_RETURN_TIME = "退货日期";
	public static final String WHOLE_DEADLINE_TIME = "交货期限";
	public static final String WHOLE_PAYMENT_TIME = "付款日期";
	public static final String SETTLE_DEAD_TIME = "结算截止日";
	public static final String STATE_REPLY_TIME = "批复时间";
	public static final String STATE_EXECUTE_TIME = "处理时间";
	public static final String STATE_SEND_TIME = "发送时间";
	public static final String STATE_READ_TIME = "读取时间";
    public static final String STATE_DELIVER_TIME = "发车时间";
	//首页柱状图查询依据
	public static final String BUSINESS_TREND_PAYMENT = "按营业额";
	public static final String BUSINESS_TREND_DEPOSIT = "按储值额";
	public static final String BUSINESS_TREND_CARD = "按发卡量";
	
	//首页柱状图和销售额分析查询时间格式
	public static final String BUSINESS_DATE_YEAR = "按年";
	public static final String BUSINESS_DATE_MONTH = "按月";
	public static final String BUSINESS_DATE_DAY = "按日";
	
	public static final String CHECK_ORDER_ALL = "全场盘点";
	public static final String CHECK_ORDER_CATEGORY = "类别盘点";
	public static final String CHECK_ORDER_SINGAL = "单品盘点";

	public static final String ACTION_COOL = "赞";
	public static final String ACTION_FAV = "收藏";
    public static final String ACTION_NOTICE = "到货通知";
    
	//调整方向
	public static final String ADJUSTMENT_DIRECTION_IN = "入库";
	public static final String ADJUSTMENT_DIRECTION_OUT = "出库";
	public static final String ADJUSTMENT_DIRECTION_MONEY = "成本调整";

	
	//图片用途
	public static final String SYSTEM_IMAGE_SHOW_INDEX = "index_show_image";//首页轮播
	public static final String SYSTEM_IMAGE_ITEM_DETAIL = "item_detail_image";//商品详细介绍
	public static final String SYSTEM_IMAGE_CENTER_NOTICE = "center_notice_image"; //配送中心公告栏
	public static final String SYSTEM_IMAGE_SETTLEMENT = "settlement_image"; //付款凭证
	public static final String SYSTEM_IMAGE_SCREEN = "assist_screan_image";//辅屏图片
	public static final String SYSTEM_IMAGE_SCREEN_ENNAME = "SCREEN_IMAGE";	
	public static final String APPLY_POSITEM_SYSTEM_IMAGE = "apply_positem_image"; //新品申请图片
	public static final String CARD_USER_TYPE_SYSTEM_IMAGE = "card_type_image"; //卡类型图片
	public static final String SYSTEM_IMAGE_WEIXIN_CATEGORY = "weixin_category_image";
	public static final String SYSTEM_IMAGE_WEIXIN_LOOP = "weixin_image_loop"; //微商城轮播
	public static final String SYSTEM_IMAGE_WEIXIN_SHOW = "weixin_image_show";//微商城平铺
	public static final String SYSTEM_IMAGE_WEIXIN_LOGO = "weixin_image_logo"; //微商城页头LOGO
	public static final String SYSTEM_IMAGE_WEIXIN_ITEM = "weixin_image_item";//微商城商品
	public static final String SYSTEM_IMAGE_STORE_IMAGE = "image_store"; //图片库
	public static final String SYSTEM_IMAGE_QR_CODE_BMP = "QR_Code_Bmp"; //二维码图片


	//前缀类型
	public static final String PRIX_TYPE_CATEGORY = "当前类别";
	public static final String PRIX_TYPE_TOP_CATEGORY = "顶级大类";
	public static final String PRIX_TYPE_FIXED = "固定编码";
	public static final String POS_ITEM_AUTO_CODE_TYPE_ITEMCODE="固定编码+商品代码";
    public static final String SYSTEM_ITEM_GROUP = "系统默认组";
	public static final int IMAGE_SIZE_LIMIT_KB = 100;
	
	//采购范围
	public static final String ITEM_PURCHASE_SCOPE_NOTLIMIT = "不限";
	public static final String ITEM_PURCHASE_SCOPE_CENTER = "总部购配";
	public static final String ITEM_PURCHASE_SCOPE_SELFPRODUCT = "自产";
	public static final String ITEM_PURCHASE_SCOPE_BRANCH = "门店采购";
	public static final String ITEM_PURCHASE_SCOPE_CENTERBRANCH = "总订店收";
	
	public static final String POLICY_PROMOTION="促销特价";
	public static final String POLICY_PRENSENT = "赠送促销" ;
	public static final String POLICY_PROMOTION_MONEY ="超额奖励";
	public static final String POLICY_PROMOTION_QUANTITY = "超量特价";
	public static final String POLICY_DISCOUNT = "超额折扣";
	public static final String POLICY_DISCOUNT_AVOID = "超额减免";
	
	//组合拆分类型
	public static final String ASSEMBLE_SPLIT_TYPE_ASSEMBLE ="组合";
	public static final String ASSEMBLE_SPLIT_TYPE_SPLIT = "拆分";
	
	public static final String STORE_ALL_IN = "全部入库";
	public static final String STORE_PART_IN = "部分入库";
	public static final String STORE_UN_IN = "未入库";
	
	public static final String LESS_THAN="小于";
	public static final String LESS_THAN_OR_EQUAL="小于等于";
	public static final String EQUAL="等于";
	public static final String MORE_THAN="大于";
	public static final String MORE_THAN_OR_EQUAL="大于等于";

	public static final String PRESENT_RECORD="赠送记录";
	public static final String RETURN_RECORD="退货记录";
	public static final String ANTI_SETTLEMENT="反结账记录";
	
	//采购向导
	public static final String GUIDE_ITEM_PURCHARSE_QTY_UP = "根据库存上限";
	public static final String GUIDE_ITEM_PURCHARSE_QTY_REORDER = "根据补货订货量";
	public static final String GUIDE_ITEM_PURCHARSE_QTY_ZERO = "订购数量为零";
	
	//CHANGE_ITEM
	public static final String ITEM_INVENTORY_RATE = "商品资料库存转换率";
	public static final String ITEM_TRANSFER_RATE = "商品资料配送转换率";
	public static final String ITEM_PURCHASE_RATE = "商品资料采购转换率";
	public static final String ITEM_WHOLESALE_RATE = "商品资料批发转换率";
	public static final String ITEM_SALE_CEASE_FLAG = "商品资料停售标记";
	public static final String ITEM_STOCK_CEASE_FLAG = "商品资料停购标记";
	
	public static final String ITEM_TRANSFER_PRICE = "商品资料配送价";
	public static final String ITEM_COST_PRICE = "商品资料进货价";
	public static final String ITEM_REGULAR_PRICE = "商品资料标准售价";
	public static final String ITEM_REGULAR_PRICE2 = "商品资料售价2";
	public static final String ITEM_REGULAR_PRICE3 = "商品资料售价3";
	public static final String ITEM_REGULAR_PRICE4 = "商品资料售价4";
	public static final String ITEM_WHOLESALE_PRICE = "商品资料批发价";
	public static final String ITEM_MIN_PRICE = "商品资料最低售价";
	public static final String ITEM_WHOLESALE_PRICE2 = "商品资料批发价2";
	public static final String ITEM_WHOLESALE_PRICE3 = "商品资料批发价3";
	public static final String ITEM_WHOLESALE_PRICE4 = "商品资料批发价4";
	
	public static final String BRANCH_TRANSFER_PRICE = "门店配送价";
	public static final String BRANCH_COST_PRICE = "门店进货价";
	public static final String BRANCH_REGULAR_PRICE = "门店标准售价";
	public static final String BRANCH_REGULAR_PRICE2 = "门店售价2";
	public static final String BRANCH_REGULAR_PRICE3 = "门店售价3";
	public static final String BRANCH_REGULAR_PRICE4 = "门店售价4";
	public static final String CLIENT_AGREEMENT_PRICE = "客户批发价";
	public static final String BRANCH_MIN_PRICE = "门店最低售价";
	
	public static final String CENTER_COST_PRICE = "进货价";
	public static final String CENTER_REGULAR_PRICE = "中心零售价";
	public static final String CENTER_REGULAR_PRICE2 = "中心零售价2";
	public static final String CENTER_REGULAR_PRICE3 = "中心零售价3";
	public static final String CENTER_REGULAR_PRICE4 = "中心零售价4";
	public static final String CENTER_MIN_PRICE = "中心最低售价";
	
	//CHANGE_ITEM_TYPE
	public static final String C_CHANGE_ITEM_BILL_BASIC = "商品档案";
	public static final String C_CHANGE_ITEM_BILL_BRANCH = "分店价格修改";
	public static final String C_CHANGE_ITEM_BILL_ADJUSTMENT = "调价单";
	public static final String C_CHANGE_ITEM_BILL_WHOLESALE_TRANSFER_ADJUSTMENT = "批发配送调价单";
	public static final String C_CHANGE_ITEM_BILL_RECEIVE = "收货单";
	public static final String C_CHANGE_ITEM_BILL_CLIENT = "约定价格";
	
    public static final String DATA_DELETE_POS = "POS单据";
    public static final String DATA_DELETE_ITEM_LOG = "进出库日志";
    public static final String DATA_DELETE_SETTLEMENT = "结算单据";
    public static final String DATA_DELETE_PURCHASE = "采购管理单据";
    public static final String DATA_DELETE_CHAIN = "连锁管理单据";
    public static final String DATA_DELETE_ADJUSTMENT = "库存调整单据";
    public static final String DATA_DELETE_ALLOCATION = "库存转仓单据";
    public static final String DATA_DELETE_CHECK = "库存盘点单据";
    public static final String DATA_DELETE_MANUFACTURE = "组合拆分单据";
    public static final String DATA_DELETE_WHOLE = "批发管理单据";
    public static final String DATA_DELETE_UNSET_PURCHASE = "保留未结算的采购管理单据";
    public static final String DATA_DELETE_UNSET_CHAIN = "保留未结算的连锁管理单据";
    public static final String DATA_DELETE_UNSET_WHOLE = "保留未结算的批发管理单据";
    
    public static final String RETAIL_POS_LOG_DEL = "删除";
    public static final String RETAIL_POS_LOG_SEND = "赠送";
    public static final String RETAIL_POS_LOG_CHE = "退货";
    public static final String RETAIL_POS_LOG_CHANGE_AMOUNT = "更改数量";
    public static final String RETAIL_POS_LOG_UPDATE_PRICE = "修改价格";
    public static final String RETAIL_POS_LOG_ALL_CANCLE = "整单取消";
	public static final String RETAIL_POS_LOG_POINT_MERGE = "积分合并";
	public static final String RETAIL_POS_LOG_HOLD_ORDER = "挂单";
	public static final String RETAIL_POS_LOG_CASHDRAWER = "打开钱箱";

    
	public static final String UNOUT_DAYS = "未进货天数";
	public static final String UNOUT_COMPARE_ITEM_LIMIT_DAYS = "未进货天数超商品有效期天数";
	
	/**
	 * 查询商品类型
	 */
	public static final String ITEM_TYPE_PURCHASE = "采购";
	public static final String ITEM_TYPE_INVENTORY = "库存";
	public static final String ITEM_TYPE_WHOLESALE = "批发";
	public static final String ITEM_TYPE_CHAIN = "连锁";
	public static final String ITEM_TYPE_ONLINE = "网店";
	public static final String ITEM_TYPE_SALE = "销售";
	
	
	//商品状态
	public static final int ITEM_STATUS_NORMAL = 0; //正常
	public static final int ITEM_STATUS_SLEEP = 1; //休眠
	
	//促销特价类型 
	public static final String POLICY_PROMOTION_SUPPLIER = "供应商返利"; //1
	public static final String POLICY_PROMOTION_CENTER = "总部返利";		 //2
	public static final int POLICY_PROMOTION_SUPPLIER_CODE = 1;
	public static final int POLICY_PROMOTION_CENTER_CODE = 2;		
	
	public static final String OTHER_INOUT_TYPE_SUPPLIER = "供应商";
	public static final String OTHER_INOUT_TYPE_BRANCH = "加盟店";
	public static final String OTHER_INOUT_TYPE_CLIENT = "客户";
	
	//卡状态
	public static final State CARD_INIT_STATE = new State(1 << 0, "初始化");
	public static final State CARD_ENABLE_STATE = new State(1 << 1, "启用");
	public static final State CARD_LOSS_STATE = new State(1 << 2, "挂失");
	public static final State CARD_REVOKE_STATE = new State(1 << 3, "回收");
	public static final int CARD_INIT_ENABLE_CODE= 3;//初始化|启用
	public static final int CARD_INIT_ENABLE_LOSS_CODE= 7;//初始化|启用|挂失
	public static final int CARD_INIT_REVOKE_CODE= 9;//初始化|回收
	public static final int CARD_INIT_ENABLE_REVOKE_CODE= 11;//初始化|启用|回收
	
	public static final String CARD_LOSS_TYPE_LOSS = "挂失";
	public static final String CARD_LOSS_TYPE_CANCEL_LOSS = "解挂";
	//积分政策类型
	public static final String POINT_POLICT_ITEM = "商品积分促销";
	public static final String POINT_POLICT_CARD = "储值消费积分促销";
	
	//系统用户操作动作
	public static final String WEB_LOG_ACTION_QUERY = "查看";
	public static final String WEB_LOG_ACTION_LOGIN = "登录";
	public static final String WEB_LOG_ACTION_LOGOUT = "退出";
	public static final String WEB_LOG_ACTION_ADD = "新增";
	public static final String WEB_LOG_ACTION_DEL = "删除";
	public static final String WEB_LOG_ACTION_AUDIT = "审核";
	public static final String WEB_LOG_ACTION_INSPECT = "作废";
	public static final String WEB_LOG_ACTION_CANCEL = "中止";
	public static final String WEB_LOG_ACTION_UPDATE = "修改";
	public static final String WEB_LOG_ACTION_IMPORT = "导入";
	public static final String WEB_LOG_ACTION_EXPORT = "导出";
	public static final String WEB_LOG_ACTION_PRINT = "打印";

	
	//WEBLOG操作项目
	public static final String WEB_LOG_APPLY_POSITEM = "新品申请";
	public static final String WEB_LOG_ASSEMBLE_SPLIT = "组合拆分单";
	public static final String WEB_LOG_CLIENT_PRE_SETTLEMENT = "客户预结算单";
	public static final String WEB_LOG_CLIENT_SETTLEMENT = "客户结算单";
	public static final String WEB_LOG_INNER_PRE_SETTLEMENT = "内部预结算单";
	public static final String WEB_LOG_INNER_SETTLEMENT = "内部结算单";
	public static final String WEB_LOG_OTHER_IN_OUT = "其他收支";
	public static final String WEB_LOG_POINT_POLICY = "积分政策";
	public static final String WEB_LOG_POLICY_PRESENT = "赠送促销";
	public static final String WEB_LOG_POLICY_PROMOTION_MONEY = "超额奖励";
	public static final String WEB_LOG_POLICY_PROMOTION = "促销特价";
	public static final String WEB_LOG_POLICY_PROMOTION_QUANTITY = "超量特价";
	public static final String WEB_LOG_POLICY_DISCOUNT = "超额折扣";
	public static final String WEB_LOG_CARD_USER = "会员卡";
	public static final String WEB_LOG_POINT = "积分";
	public static final String WEB_LOG_ATTACHMENT = "附件";
	public static final String WEB_LOG_POS_ITEM = "商品档案";
	public static final String WEB_LOG_PRICE_ADJUSTMENT = "调价单";
	
	public static final String WEB_LOG_ITEM_BRANCH_REQUEST = "网页要货";
	public static final String WEB_LOG_ITEM_SERVER = "网页后台";
	public static final String WEB_LOG_ITEM_PHONE = "手机查询";
	public static final String WEB_LOG_ITEM_POS = "POS终端";
	public static final String WEB_LOG_ITEM_PHONE_WHOLESALE = "手机批发订货";
	public static final String WEB_LOG_ITEM_PHONE_REQUEST = "手机门店要货";
	
	//WEBLOG系统类型
	public static final String WEB_LOG_SYSTEM_WEB = "网页系统";
	public static final String WEB_LOG_SYSTEM_POS = "桌面系统";
	public static final String WEB_LOG_SYSTEM_POS_V2 = "POS系统";
	
	//报表设计 
	//POS
	public static final String WEB_REPORT_ElECTRONIC_WALLET_STORE = "电子钱包存款报表";
	public static final String WEB_REPORT_ElECTRONIC_WALLET_CONSUME = "电子钱包消费报表";
	public static final String WEB_REPORT_POINT_EXCHANGE = "积分兑换报表";
	public static final String WEB_REPORT_POS_ORDER = "POS单据报表";
	public static final String WEB_REPORT_POS_CLIENT_ORDER_FORM = "POS客户订单报表";
	public static final String WEB_REPORT_SHIFT_TABLE = "零售交班报表";
	public static final String WEB_REPORT_BUSINESS_DAY = "营业日统计报表";
	public static final String WEB_REPORT_CHANGE_CARD = "换卡报表";
	public static final String WEB_REPORT_ITEM_TAG = "POS商品标签格式";
	public static final String WEB_REPORT_RELAT_CARD = "续卡报表";
	public static final String WEB_REPORT_POS_WHOLESALE_ORDER = "POS_批发销售单报表";
	public static final String WEB_REPORT_POS_OUT_ORDER = "POS_调出单报表";
	public static final String WEB_REPORT_POS_OTHER_ORDER = "POS_其他收支报表";

	
	public static final String WHOLESALE_CENTER_TYPE= "批发商网上公告";
	public static final String BRANCH_CENTER_TYPE= "加盟店网上公告";
	public static final String CARDUSER_CENTER_TYPE= "会员网上公告";
	
	/**
	 * 定时器发送短信状态
	 */
	public static final int SMS_SEND_STATE_SENDING = 3;
	
	public static final String COST_ADJUSTNEBT_MONEY = "成本调整";
	/*
	 * AMA模块
	 */
	public static final String C_AMA_MODULE_BASIC = "基本档案";
	public static final String C_AMA_MODULE_POS = "前台销售";
	public static final String C_AMA_MODULE_PURCHASE = "采购管理";
	public static final String C_AMA_MODULE_WHOLESALE = "批发管理";
	public static final String C_AMA_MODULE_RETAIL = "零售管理";
	public static final String C_AMA_MODULE_INVNETORY = "仓库管理";
	public static final String C_AMA_MODULE_PROMOTION = "促销政策";
	public static final String C_AMA_MODULE_SETTLEMENT = "结算管理";
	public static final String C_AMA_MODULE_CHAIN = "连锁管理";
	public static final String C_AMA_MODULE_DECISION = "决策支持";
	public static final String C_AMA_MODULE_CRM = "会员管理";
	public static final String C_AMA_MODULE_SMS = "短信管理";
	public static final String C_AMA_MODULE_SYSTEM = "系统管理";
	public static final String C_AMA_MODULE_WEB = "网页后台";
	public static final String C_AMA_MODULE_MTSCALE = "MTAgentSupport";
	public static final String C_AMA_MODULE_MARKETACTION = "营销推广";

	
	/**
	 * 现金类型
	 */
	public static final String CASH_TYPE_TOTAL = "现金累计总额";
	public static final String CASH_TYPE_POS = "前台收入";
	public static final String CASH_TYPE_CARD_DEPOSIT = "卡存款";
	public static final String CASH_TYPE_REPLACE_CARD = "换卡";
	public static final String CASH_TYPE_RELAT_CARD = "续卡";
	public static final String CASH_TYPE_OTHER_INOUT = "其他收支";	
	
	/**
	 * 基础资料导入导出
	 */
	public static final String WEB_SYS_ROLE="SYSTEM_ROLE";//角色管理
	public static final String WEB_SYS_USER="APP_USER";//用户管理
	public static final String WEB_SYS_EMPLOYEE="EMPLOYEE";//员工管理
	public static final String WEB_SYS_EMPLOYEE_KIND="EMPLOYEE_KIND";//员工类型
	public static final String WEB_SYS_REPORT_FORMAT="REPORT_FORMAT";//报表格式
	public static final String WEB_SYS_PARAM="SYSTEM_PARAM";//系统参数
	public static final String WEB_SYS_BASIC_PARAM="POS_ITEM_PARAM";//基本档案参数
	public static final String WEB_SYS_RETAIL_PARAM="POS_SALE_PARAM";//前台营业参数
	public static final String WEB_SYS_CHAIN_DELIVERY_PARAM="CHAIN_DELIVERY_PARAM";//采购连锁参数
	public static final String WEB_SYS_FEE_ITEM="FEE_ITEM";//费用项目
	public static final String WEB_SYS_ADJUST_REASON="ADJUST_REASON";//库存调整原因
	public static final String WEB_SYS_OTHER_PAY="PAYMENT_TYPE";//其他支付方式
	public static final String WEB_SYS_ITEM_CATAGORY="ITEM_CATEGORY";//商品类别
	public static final String WEB_SYS_ITEM_DEPARTMENT="ITEM_DEPARTMENT";//商品部门
	public static final String WEB_SYS_ITEM_BRAND="ITEM_BRAND";//商品品牌
	public static final String WEB_SYS_UNIT="ITEM_UNIT_GROUP";//计量单位
	public static final String WEB_SYS_USUAL_MES="USUAL_MESSAGE";//短信常用语
	public static final String WEB_CARD_CATEGORY="CARD_CATEGORY";//消费卡类型
	public static final String WEB_POINT_PARAM="POINT_PARAM";//积分规则
	public static final String WEB_CARD_PARAM="CARD_PARAM";//消费卡参数
	public static final String WEB_POS_ITEM="POS_ITEM";//商品资料
	
	/**
	 * 单据明细类型
	 */
	public static final String POS_ORDER_DETAIL_TYPE_ITEM = "商品项目";
	public static final String POS_ORDER_DETAIL_TYPE_COUPON = "消费券";
	
	public static final String PURHCASE_LAST_SUPPLIER = "最近进货供应商";
	public static final String PURHCASE_HIGH_SUPPLIER = "优先级最高供应商";
	
	//提成类型
	public static final String POS_ITEM_COMMISSION_TPYE_NONE = "没有提成";
	public static final String POS_ITEM_COMMISSION_TPYE_FIX = "固定提成金额";
	public static final String POS_ITEM_COMMISSION_TPYE_PROFIT = "按销售利润百分比提成";
	public static final String POS_ITEM_COMMISSION_TPYE_SALE_MONEY = "按销售额百分比提成";

	
    public static final int UN_AFFIRM_STATE_CODE = 0;//待确认
    public static final String UN_AFFIRM_STATE_NAME = "待确认";//待确认
    public static final int ON_AFFIRM_STATE_CODE = 1;//已确认
    public static final String ON_AFFIRM_STATE_NAME = "已确认";//已确认
    public static final int ON_SEND_ITEM_STATE_CODE = 2;
    public static final String ON_SEND_ITEM_STATE_NAME = "已发货"; //已发货
    
    //card_user_log_type
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
     * 附件属性
     */
    public static final String OSS_OBJECT_TYPE_UPLOADER = "上传人";
    public static final String OSS_OBJECT_TYPE_UPLOAD_TIME = "上传时间";
    
	public static final String BANNER_TYPE_SHELVED = "for_shelved"; //已下架的
	public static final String BANNER_TYPE_SOLDOUT = "sold_out"; //已售罄的
	public static final String BANNER_TYPE_ONSALE = "on_sale"; //出售中的
	
	/**
	 * 在线订单时间轴类型
	 */
	public static final String BANNER_DATE_TYPE_ORDER = "下单时间"; 
	public static final String BANNER_DATE_TYPE_PAY = "付款时间";
	public static final String BANNER_DATE_TYPE_PICK = "配货时间";
	public static final String BANNER_DATE_TYPE_DELEVER = "发货时间";
	public static final String BANNER_DATE_TYPE_RECEIVE = "收货时间";
	public static final String BANNER_DATE_TYPE_RETURN = "退款时间";
	public static final String BANNER_DATE_TYPE_CANCEL = "关闭时间";
	
	//在线订单状态
	public static final State ONLINE_ORDER_INIT = new State(1 << 0, "已下单");
	public static final State ONLINE_ORDER_SETTLEMENT = new State(1 << 1, "已付款");
	public static final State ONLINE_ORDER_CODPAY = new State(1 << 1, "货到付款");
	public static final State ONLINE_ORDER_PICKED = new State(1 << 2, "已配货");
	public static final State ONLINE_ORDER_SENDED = new State(1 << 3, "已发货");
	public static final State ONLINE_ORDER_FINISHED = new State(1 << 4, "已签收");
	public static final State ONLINE_ORDER_RETURN = new State(1 << 5, "已退货");
	public static final State ONLINE_ORDER_CANCEL = new State(1 << 6, "已关闭");
	public static final State ONLINE_ORDER_TEAM_SETTLEMENT = new State(1 << 7, "团购中");//团购付款 128
	public static final State ONLINE_ORDER_TEAM_FAIL = new State(1 << 8, "团购失败");//256
	public static final State ONLINE_ORDER_TEAM_SUCCESS = new State(1 << 9, "团购成功");//512
	public static final State ONLINE_ORDER_USER_CANCEL = new State(1 << 10, "用户取消"); //

	
	public static final int ONLINE_ORDER_INIT_CODE = 1; // 下单
	public static final int ONLINE_ORDER_INIT_SETTLEMENT_CODE = 3; // 下单|已付款
	public static final int ONLINE_ORDER_INIT_SETTLEMENT_PICK_CODE = 7;// 下单|已付款|已配货
	public static final int ONLINE_ORDER_INIT_SETTLEMENT_SENDED_CODE = 11;// 下单|已付款|已发货
	public static final int ONLINE_ORDER_INIT_SETTLEMENT_PICK_SENDED_CODE = 15;//下单|已付款|已配货|已发货
	public static final int ONLINE_ORDER_INIT_SETTLEMENT_PICK_SENDED_FINISHED_CODE = 31;//下单|已付款|已配货|已发货|已签收
	public static final int ONLINE_ORDER_INIT_SETTLEMENT_RETURN_CODE = 35; // 下单|已付款|已退款
	public static final int ONLINE_ORDER_INIT_SETTLEMENT_PICK_SENDED_RETURN_CODE = 47;//下单|已付款|已配货|已发货|已退款
	public static final int ONLINE_ORDER_INIT_SETTLEMENT_PICK_SENDED_FINISHED_RETURN_CODE = 63;//下单|已付款|已配货|已发货|已签收|已退款
	public static final int ONLINE_ORDER_INIT_SETTLEMENT_CANCEL_CODE = 67; // 下单|已付款|已关闭
	public static final int ONLINE_ORDER_INIT_SETTLEMENT_PICK_SENDED_RECEIVE_FINISHED_CODE = 95;//下单|已付款|已配货|已发货|已签收|已关闭
	public static final int ONLINE_ORDER_INIT_TEAM_CODE = 129; // 下单|团购中
	public static final int ONLINE_ORDER_INIT_TEAM_FAIL_CODE = 385; // 下单|团购中|团购失败
	public static final int ONLINE_ORDER_TEAM_FAIL_CODE = 384; // 团购中|团购失败
	public static final int ONLINE_ORDER_TEAM_SUCCESS_CODE = 640; // 团购中|团购成功
	
	/**
	 * 在线订单来源
	 */
	public static final String ONLINE_ORDER_SOURCE_WEIXIN = "微商店";
	public static final String ONLINE_ORDER_SOURCE_API = "第三方商城";
	public static final String ONLINE_ORDER_SOURCE_NH_WSHOP = "新希望微商城";
	public static final String ONLINE_ORDER_SOURCE_JD = "京东到家";
	public static final String ONLINE_ORDER_SOURCE_YOUZAN = "有赞";
	public static final String ONLINE_ORDER_SOURCE_MEITUAN = "美团外卖";
	public static final String ONLINE_ORDER_SOURCE_ELE = "饿了么外卖";
	public static final String ONLINE_ORDER_SOURCE_MERCURY= "水星微商城";

	
	public static final String KDT_SHIPPINT_TYPE_FETCH = "到店自提";
	public static final String DELIVER_TYPE_TRANSFER = "送货上门";

	
	/**
	 * 储值类型
	 */
	public static final String DEPOSIT_TYPE_DEPOSIT = "存款";
	public static final String DEPOSIT_TYPE_REDEPOSIT = "反存款";
	 
	/**
	 * 消费类型
	 */
	public static final String CONSUME_TYPE_CONSUME = "消费";
	public static final String CONSUME_TYPE_RECONSUME = "反消费";
	
	public static final String LOCATION_STATE = "省";
	public static final String LOCATION_CITI= "市";
	public static final String LOCATION_DISTRICT= "区";
	
	/**
	 * 促销特价循环类型
	 */
	public static final String POLICY_PROMOTION_REPEAT_MONTH = "每月";
	public static final String POLICY_PROMOTION_REPEAT_YEAR = "每年";
	
	/**
	 * 邮件收件人类型
	 */
	public static final String SYSTEM_MAIL_RECEIVE_TYPE_BRANCH = "门店用户";
	public static final String SYSTEM_MAIL_RECEIVE_TYPE_WHOLESALE = "批发客户";
	public static final String SYSTEM_MAIL_RECEIVE_TYPE_SUPPLIER = "供应商";
	
	/** 邮件发送状态 */
	public static final Integer SYSTEM_MAIL_STATE_SEND = 0;//发送成功
	public static final Integer SYSTEM_MAIL_STATE_FAILD = 1;//发送失败
	
	/** 邮件发送方式 */
	public static final String MAIL_INFO_TYPE_DEFAULT = "默认";
	public static final String MAIL_INFO_TYPE_SENDCLOUD = "SendCloud";
	
	/** 模板字段    */
	public static final String TEMPLATE_NAME = "[联系人名称]";
	public static final String TEMPLATE_ORDER_NO = "[单据号]";
	public static final String TEMPLATE_ORDER_MONEY = "[单据金额]";
	public static final String TEMPLATE_AUDIT_TIME = "[审核时间]";
	public static final String TEMPLATE_CREATE_TIME = "[制单时间]";
	public static final String TEMPLATE_ORDER_TYPE = "[单据类型]";
	public static final String TEMPLATE_SEND_NAME = "[发件人名称]";
	
	/** 缓存名称 */
	public static final String CACHE_NAME_ITEM_SALE_RANK = "CACHE_ITEM_SALE_RANK:";
	public static final String CACHE_NAME_POS_ITEM = "CACHE_POS_ITEM:";
	public static final String CACHE_NAME_SUPPLIER = "CACHE_SUPPLIER:";
	public static final String CACHE_NAME_POS_CLIENT = "CACHE_POS_CLIENT:";
	public static final String CACHE_NAME_BRANCH = "CACHE_BRANCH:";
	public static final String CACHE_DISTRIBUTION_CENTRE_MATRIX = "CACHE_DISTRIBUTION_CENTRE_MATRIX:";
	public static final String CACHE_SALE_CEASE_ITEM = "CACHE_SALE_CEASE_ITEM:";
	public static final String CACHE_SYSTEM_BOOK = "CACHE_SYSTEM_BOOK";
	public static final String CACHE_ALIPAY_ACCOUNT = "CACHE_ALIPAY_ACCOUNT";
	public static final String CACHE_ONLINE_ORDER_NO = "CACHE_ONLINE_ORDER_NO";
	public static final String CACHE_MT_SCALE = "CACHE_MT_SCALE";
	public static final String CACHE_SYSTEM_BOOK_APP_KEY_PARAM = "CACHE_SYSTEM_BOOK_APP_KEY_PARAM";
	public static final String CACHE_NAME_BRANCH_MATRIX = "CACHE_BRANCH_MATRIX";
	public static final String CACHE_NAME_EXCEPTION_CARD = "CACHE_NAME_EXCEPTION_CARD:";
	public static final String CACHE_WEIXIN_PARAM = "CACHE_WEIXIN_PARAM:";
	public static final String CACHE_PROVINCE = "CACHE_PROVINCE";
	public static final String CACHE_WEIXIN_POS_ITEM = "CACHE_WEIXIN_POS_ITEM";
	public static final String CACHE_PROMOTION_QUANTITY_CATEGORY_TRANSFER = "CACHE_PROMOTION_QUANTITY_CATEGORY_TRANSFER";
	public static final String CACHE_UNREAD_MESSAGE_COUNT = "CACHE_UNREAD_MESSAGE_COUNT:";
	public static final String CACHE_POLICY_PROMOTION_CATEGORY = "CACHE_POLICY_PROMOTION_CATEGORY:";
	public static final String CACHE_NAME_WEIXIN_CARD_TYPE = "CACHE_WEIXIN_CARD_TYPE:";
	public static final String CACHE_APP_LOGIN_INFO = "CACHE_APP_LOGIN_INFO";

	
	
	/** 健康报表KEY值 */
	public static final String BRANCH_HEALTH_AREA_RATE = "平米效率(万元/平方米/年)";
	public static final String BRANCH_HEALTH_DIRECT_AREA_RATE = "直营店平米效率(万元/平方米/年)";
	public static final String BRANCH_HEALTH_JOIN_AREA_RATE = "加盟店平米效率(万元/平方米/年)";
	public static final String BRANCH_HEALTH_EMPLOYEE_RATE = "人均劳效(万元/年)";
	public static final String BRANCH_HEALTH_DIRECT_EMPLOYEE_RATE = "直营店人均劳效(万元/年)";
	public static final String BRANCH_HEALTH_JOIN_EMPLOYEE_RATE = "加盟店人均劳效(万元/年)";
	public static final String BRANCH_HEALTH_AVG_TICKET_PRICE = "平均客单价(元)";
	public static final String BRANCH_HEALTH_AREA_SKU = "单位面积SKU";
	public static final String BRANCH_HEALTH_AVG_AREA = "单店平均面积(平方米)";
	
	public static final String BRANCH_HEALTH_BRANCH_COUNT = "门店数量";
	public static final String BRANCH_HEALTH_AREA = "门店面积(平方米)";
	public static final String BRANCH_HEALTH_DIRECT_AREA = "直营店门店面积";
	public static final String BRANCH_HEALTH_JOIN_AREA = "加盟店门店面积";
	public static final String BRANCH_HEALTH_EMPLOYEE = "员工数量";
	public static final String BRANCH_HEALTH_DIRECT_EMPLOYEE = "直营店员工数量";
	public static final String BRANCH_HEALTH_JOIN_EMPLOYEE = "加盟店员工数量";
	public static final String BRANCH_HEALTH_TICKET_AMOUNT = "客单量";
	public static final String BRANCH_HEALTH_SALE_MONEY = "销售总额(元)";
	public static final String BRANCH_HEALTH_DIRECT_SALE_MONEY = "直营店销售总额";
	public static final String BRANCH_HEALTH_JOIN_SALE_MONEY = "加盟店销售总额";
	public static final String BRANCH_HEALTH_SKU_AMOUNT = "SKU总数";
	public static final String BRANCH_HEALTH_PROFIT_RATE = "毛利率(%)";
	public static final String BRANCH_HEALTH_PROFIT = "毛利";
	public static final String BRANCH_HEALTH_BUSINESS_DAY = "门店营业天数";
	public static final String BRANCH_HEALTH_NEW_CUSTOMER = "新增会员数";
	public static final String BRANCH_HEALTH_CUSTOMER_CONSUME_RATE = "会员消费占比(%)";
	public static final String BRANCH_HEALTH_DIFF_RENT = "售租差额";
	public static final String BRANCH_HEALTH_BRANCH_RENT = "门店租金(年)";
	public static final String BRANCH_HEALTH_SLEEP_CUSTOMER = "休眠会员数(超过90天)";
	public static final String BRANCH_HEALTH_REQUEST_ITEM_AMOUNT = "门店要货商品数量";
	public static final String BRANCH_HEALTH_REQUEST_ITEM_MONEY = "门店要货商品金额";
	public static final String BRANCH_HEALTH_TRANSFER_OUT_ITEM_AMOUNT = "总部调出商品数量";
	public static final String BRANCH_HEALTH_TRANSFER_OUT_ITEM_MONEY = "总部调出商品金额";
	public static final String BRANCH_HEALTH_TRANSFER_IN_ITEM_AMOUNT = "门店退货商品数量";
	public static final String BRANCH_HEALTH_TRANSFER_IN_ITEM_MONEY = "门店退货商品金额";
	public static final String BRANCH_HEALTH_NEW_ITEM = "新增商品数量";
	public static final String BRANCH_HEALTH_INVENTORY_AMOUNT = "总仓库存数量";
	public static final String BRANCH_HEALTH_INVENTORY_MONEY = "总仓库存金额(元)";
	public static final String BRANCH_HEALTH_INVENTORY_UNSALE_AMOUNT = "总仓滞销商品库存数量";
	public static final String BRANCH_HEALTH_INVENTORY_UNSALE_MONEY = "总仓滞销商品库存金额(元)";
	public static final String BRANCH_HEALTH_REQUEST_AMOUNT = "门店要货商品数量";
	public static final String BRANCH_HEALTH_REQUEST_MONEY = "门店要货商品金额(元)";
	public static final String BRANCH_HEALTH_TRANSFER_OUT_AMOUNT = "总部调出商品数量";
	public static final String BRANCH_HEALTH_TRANSFER_OUT_MONEY = "总部调出商品金额(元)";
	public static final String BRANCH_HEALTH_TRANSFER_OUT_PROFIT = "总部调出配送毛利(元)";
	public static final String BRANCH_HEALTH_TRANSFER_IN_AMOUNT = "门店退货商品数量";
	public static final String BRANCH_HEALTH_TRANSFER_IN_MONEY = "门店退货商品金额(元)";
	public static final String BRANCH_HEALTH_NEW_POS_ITEM = "新增商品数量";
	
	public static final String BRANCH_HEALTH_CURRENT = "本期";
	public static final String BRANCH_HEALTH_LAST_MONTH = "上月";
	public static final String BRANCH_HEALTH_LAST_YEAR= "去年同期";
	
	
	
	/** 积分规则名称  */
	public static final String POINT_PARAM_NAME_CARD_TYPE = "按照会员卡储值消费计算";
	
	/**  城市类型  */
	public static final String BRANCH_REGION_TYPE_ONE = "一线城市";
	public static final String BRANCH_REGION_TYPE_TWO = "二线城市";
	public static final String BRANCH_REGION_TYPE_THREE = "三线城市";
	
	/**
	 * 打印纸张格式
	 */
	public static final String REPORT_PRICE_PRINT_ADOUBLE = "A4双排";
	public static final String REPORT_PRICE_PRINT_ADOUBLE_SALE = "A4双排促销标签";
	public static final String REPORT_PRICE_PRINT_ASINGLE = "A4单排常规售价";
	public static final String REPORT_PRICE_PRINT_ASINGLE_SALE = "A4单排促销标签";
	
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
	public static final String UNIONPAY_BY_QR = "银联二维码支付";
	public static final String UNIONPAY_CANCEL = "银联撤销";
	
	/**
	 * 微信网店类型
	 */
	public static final String WEIXIN_BRANCH_TYPE_KDT="口袋通";
	public static final String WEIXIN_BRANCH_TYPE_MERCHANT="微信小店";
	public static final String WEIXIN_BRANCH_TYPE_WSHOP="新希望微商城";
	public static final String WEIXIN_BRANCH_TYPE_YOUZAN="有赞";



	
	/**微信安全验证token
	 * 
	 */
	public static final String WEIXIN_TOKEN="WEIXIN_TOKEN";
	public static final String WEB_SHOP_URL = "http://amazon.nhsoft.cn/pos3Shop/";
	public static final String WEB_CLIENT_URL = "http://amazon.nhsoft.cn/pos3Client/";
	
	/**
	 * 积分清除日志状态
	 */
	public static final int POINT_CLEAR_LOG_CREATE = 1;//未执行
	public static final int POINT_CLEAR_LOG_EXCUTE = 2;//已执行
	
	/**
	 * 删除业务单据截止日期
	 */
	public static final String BATCH_DATE_LIMIT = "20131231";
	
	/**
	 * 营销活动类型
	 */
	public static final String MARKET_ACTION_TYPE_PAYMENT = "支付方式";
	public static final String MARKET_ACTION_TYPE_EXCEPTION_ITEM = "例外商品";
	public static final String MARKET_ACTION_TYPE_SPECIFY_ITEM = "指定商品";
	
	/**
	 * 限制日期类型
	 */
	public static final String DATE_TYPE_NO_LIMIT = "无限制";
	public static final String DATE_TYPE_CURRENT_MONTH = "本月";
	public static final String DATE_TYPE_CURRENT_SEASON = "本季";
	public static final String DATE_TYPE_CURRENT_YEAR = "本年";
	public static final String DATE_TYPE_DAY_COUNT = "天数";
	public static final String DATE_TYPE_MONTH_COUNT = "月数";
	public static final String DATE_TYPE_YEAR_COUNT = "年数";
	
	/**
	 * 数据清除记录状态
	 */
	public static final int DATA_DELETE_CREATE = 1;//未执行
	public static final int DATA_DELETE_EXCUTE = 2;//执行中
	public static final int DATA_DELETE_FINISH = 3;//已执行
	
	/**
	 * 批发订单状态
	 */
	public static final String BOOK_ORDER_STATE_TYPE_AUDIT = "订单提交";
	public static final String BOOK_ORDER_STATE_TYPE_CANCEL = "订单取消";
	public static final String BOOK_ORDER_STATE_TYPE_OUT_AUDIT = "总部确认";
	public static final String BOOK_ORDER_STATE_TYPE_OUT_PICK = "总部配货";
	public static final String BOOK_ORDER_STATE_TYPE_OUT_SEND = "发货确认";
	public static final String BOOK_ORDER_STATE_TYPE_IN_AUDIT = "收货确认";
	
	/**
	 * 默认客户类别
	 */
	public static final String POS_CLIENT_TYPE_PHONE = "手机端客户";
	
	/**
	 * 团购类型  若增加类型  请搜索常量 相关报表需要修改
	 */
	public static final String TEAM_TYPE_DIANPING = "大众点评";
	public static final String TEAM_TYPE_MEITUAN = "美团";
	public static final String TEAM_TYPE_NUOMI = "糯米";
	public static final String TEAM_TYPE_WECHAT = "微信代金券";

	/**
	 * 第三方支付方式
	 */
	public static final String PAY_TYPE_ALIPAY = "支付宝";
	public static final String PAY_TYPE_WEIXINPAY = "微信支付";
	public static final String PAY_TYPE_DPHUI = "大众闪惠";
	public static final String PAY_TYPE_UNIONPAY = "银联支付";
	
	public static final String OSS_IMAGE_BUCKET = "nhsoft-image";
	
	/**
	 * 报损原因类型
	 */
	public static final String ADJUSTMENT_REASON_TYPE_LOSS = "报损";
	public static final String ADJUSTMENT_REASON_TYPE_ADJUST = "调整";
	
	
	/**
	 * card_user介质类型
	 */
	public static final String C_CARD_SUPPORT_IC = "IC卡";
	public static final String  C_CARD_SUPPORT_ID = "磁条卡";
	public static final String  C_CARD_SUPPORT_ONLINE = "电子卡";
	public static final String  C_CARD_SUPPORT_NONE = "NONE";

	/**
	 * 仓管流程常量
	 */
	public static final String STOCK_ROLE_PURCHASE = "采购员";
	public static final String STOCK_ROLE_INVENTORY = "仓管员";
	public static final String STOCK_ROLE_RECEIVE = "收货员";
	public static final String STOCK_ROLE_DRIVER = "驾驶员";
	public static final String STOCK_ROLE_PURCHASE_MANAGER = "采购经理";
	public static final String STOCK_ROLE_PICKER = "配货员";
	public static final String STOCK_ROLE_RECEIVE_PICKER = "收货员+配货员";
	
	public static final String ORDER_TASK_TYPE_PURCHASE = "采购任务";
	public static final String ORDER_TASK_TYPE_INVENTORY_TRANSFER = "出仓任务";
	public static final String ORDER_TASK_TYPE_PROCESS = "加工任务";

	
	public static final int ORDER_TASK_DETAIL_STATUS_PURCHASE = 1; //采购
	public static final int ORDER_TASK_DETAIL_STATUS_RECEIVE = 2; //收货
	public static final int ORDER_TASK_DETAIL_STATUS_TRANSFER = 3; //配送
	
	public static final State STATE_PURCHASE_FINISHE = new State(1 << 2, "采购完成");
	public static final State STATE_TRANSFER_SEND = new State(1 << 2, "发车");
	public static final int STATE_PURCHASE_FINISHE_CODE = 4;	
	public static final int STATE_TRANSFER_SEND_CODE = 4;
	public static final int STATE_INIT_AUDIT_PURCHASE_FINISHE_CODE = STATE_INIT_AUDIT_CODE | STATE_PURCHASE_FINISHE_CODE;
	public static final int STATE_INIT_AUDIT_SEND_CODE = STATE_INIT_AUDIT_CODE | STATE_TRANSFER_SEND_CODE;
	
	public static final String RECEIVE_ORDER_TYPE_DAY_PURCHASE = "日采收货";
	
	/**
	 *  大众点评业务类型
	 */
	public static final String DP_TUANGOU = "团购";
	public static final String DP_HUI = "闪惠";

	
	/**
	 * 第三方API卡消费验证类型
	 */
	public static final String CHECK_TYPE_PASSWORD = "CHECK_TYPE_PASSWORD";
	public static final String CHECK_TYPE_PHONE = "CHECK_TYPE_PHONE";
	
	/**
	 * 线上存款或消费类型
	 */
	public static final int ONLINE_CATEGORY = 2;
	public static final int IC_CATEGORY = 3;

	/**
	 * SYNCH_LOG_SUMMARY
	 */
	public static final String SYNCH_LOG_SUMMARY_BASIC = "分店基础资料";
	public static final String SYNCH_LOG_SUMMARY_DEPOSIT = "IC卡存款";
	public static final String SYNCH_LOG_SUMMARY_CONSUME = "IC卡消费";
	public static final String SYNCH_LOG_SUMMARY_POS = "销售订单";
	public static final String SYNCH_LOG_SUMMARY_PAYMENT = "前台收款明细";
	public static final String SYNCH_LOG_SUMMARY_POINT = "积分明细";
	public static final String SYNCH_LOG_SUMMARY_SHIFTTABLE = "营业日数据";
	public static final String SYNCH_LOG_SUMMARY_BROADCAST_BASIC = "分店广播基础资料";
	/**
	 * 消费券来源
	 */
	public static final String TICKET_RESOURSE_API = "API";
	public static final String TICKET_RESOURSE_WSHOP = "新希望微商城";
	
	public 	static final String ROUND_TYPE_HALF = "四舍五入";
	public 	static final String ROUND_TYPE_OFF = "舍零";
	public 	static final String ROUND_TYPE_TO = "舍入";	
	
	public 	static final String MONEY_SCALE_TYPE_YUAN = "元";
	public 	static final String MONEY_SCALE_TYPE_JIAO = "角";
	public 	static final String MONEY_SCALE_TYPE_FEN = "分";
	
	/**
	 * 卡类型升级状态
	 */
	public static final int CARD_UPGRADE_CREATE = 1;//未升级
	public static final int CARD_UPGRADE_FINISH = 2;//已升级
	
	//自定义项目类型
	public static final String CUSTOM_TYPE_DEPARTMENT = "按部门统计";
	public static final String CUSTOM_TYPE_FOOD_TYPE = "按商品类别统计";
	public static final String CUSTOM_TYPE_FOOD = "按商品统计";
	
	/**
	 * 筐操作类型
	 */
	public static final String PACKAGE_LOG_TYPE_CENTER_CHECKORDER = "中心盘点单";
	
	/**
	 * 筐来源
	 */
	public static final String PACKAGE_LOG_SOURCE_SUPPLIER = "供应商";
	public static final String PACKAGE_LOG_SOURCE_CENTER = "中心";
	
	/**
	 * 每日提醒
	 */
	public static final String REMINDER_ITEM_OFD = "REMINDER_ITEM_OFD";//商品过期催销
   public static final String REMINDER_PURCHASE_OFD = "REMINDER_PURCHASE_OFD";//过期订单提示
   public static final String REMINDER_INCOME_OFD = "REMINDER_INCOME_OFD";//今天应收帐款
   public static final String REMINDER_PAY_OFD = "REMINDER_PAY_OFD";//今天应付帐款
   public static final String REMINDER_CLIENT_BIRTHDAY = "REMINDER_CLIENT_BIRTHDAY";//客户生日提示
   public static final String REMINDER_CARDUSER_BIRTHDAY = "REMINDER_CARDUSER_BIRTHDAY";//会员生日提示
   public static final String REMINDER_INVENTORY_WEARNING = "REMINDER_INVENTORY_WEARNING";//库存警告提示
   public static final String REMINDER_CLIENT_CREDIT_WARNING = "REMINDER_CLIENT_CREDIT_WARNING";//客户超额预警
   public static final String REMINDER_CARD_DEPOSIT_WARNING = "REMINDER_CARD_DEPOSIT_WARNING";//门店存款额度预警

	/** 周期单位 */
	public static final String PERIOD_UNIT_WEEk = "周";
	public static final String PERIOD_UNIT_DAY = "天";
	public static final String PERIOD_UNIT_MONTH = "月";
		
	/**
	 * 定时提醒类型
	 */
	public static final String REMIND_SEND_TYPE_WEIXIN = "微信";
	public static final String REMIND_SEND_TYPE_POS_CONSUME = "POS消费";
	public static final String REMIND_SEND_TYPE_POS_DEPOSIT = "POS存款";
	public static final String REMIND_SEND_TYPE_ONLINE_ORDER_PAY = "在线订单付款";
	public static final String REMIND_SEND_TYPE_TICKET_SEND = "赠送消费券";

	
	/**
	 * 促销特价类型
	 */
	public static final String POLICY_PROMOTION_CATEGORY_WHOLESALE = "批发促销特价";
	public static final String POLICY_PROMOTION_CATEGORY_PURCHASE = "采购促销特价";

	
	/**
	 * WEB_LOG表原表保留月份
	 */
	public static final int WEB_LOG_KEEP_MONTH = 3;
	
	public static final String ORDER_OPERATOR_TYPE_CREATOR = "制单人";
	public static final String ORDER_OPERATOR_TYPE_AUDITOR = "审核人";
	public static final String ORDER_OPERATOR_TYPE_PICKER = "配货人";
	public static final String ORDER_OPERATOR_TYPE_SENDER = "发车人";

	/**
	 * 默认采购截止时间点
	 */
    public static final String PURCHASE_DAY_LIMIT = "2000";//20:00
   
   /**
    * 微商城商品状态
    */
   public static final int WEIXIN_POS_ITEM_STATUS_PUBLISH = 0;//上架
   public static final int WEIXIN_POS_ITEM_STATUS_DOWN = 1;//下架
   public static final int WEIXIN_POS_ITEM_STATUS_OUT = 2;//售罄
   
   /**
    * 积分规则类型
    */
   public static final String POINT_PARAM_TYPE_ITEM = "按照单品积分计算";
   public static final String POINT_PARAM_TYPE_CONSUME_MONEY = "按照消费金额计算";
   public static final String POINT_PARAM_TYPE_CONSUME_SINGLE = "按照消费次数计算";
   public static final String POINT_PARAM_TYPE_CARD_TYPE = "按照会员卡储值消费计算";

   /**
    * REGISTER_TYPE
    */
   public static final String REGISTER_TYPE_DELIVER = "发卡";
   public static final String REGISTER_TYPE_DELIVER_ONLINE_CARD = "激活电子卡";
   public static final String REGISTER_TYPE_ALTER = "修改卡信息";
   public static final String REGISTER_TYPE_ORI = "老会员卡转卡";

   /**
    * purchase_order_receive_state
    */
   public static final String PURCHASE_ORDER_RECEIVE_STATE_FINISH = "收货完成";
   public static final String PURCHASE_ORDER_RECEIVE_STATE_PART = "部分收货";
   public static final String PURCHASE_ORDER_RECEIVE_STATE_UN = "未收货";
   
   /**
    * wholesale_book_sale_state
    */
   public static final String WHOLESALE_BOOK_SALE_STATE_FINISH = "发货完成";
   public static final String WHOLESALE_BOOK_SALE_STATE_PART = "部分发货";
   public static final String WHOLESALE_BOOK_SALE_STATE_UN = "未发货";
   
   /**
    * 在线订单类型
    */
   public static final int ONLINE_ORDER_TYPE_NORMAL = 0; //普通订单
   public static final int ONLINE_ORDER_TYPE_TEAM = 1; //团购订单
   
   /**
    * 证书类型
    */
   public static final String BRANCH_PRODUCT_FRUIT = "喜临门蔬果（熟食）食品专卖店管理系统(V2015)";
   public static final String BRANCH_PRODUCT_FRUIT_V2 = "乐盟蔬果（熟食）食品专卖店管理系统(V2016)";
   public static final String BRANCH_PRODUCT_RETAIL = "喜临门零售商业管理系统(V3.0)";
   /**
    * 微商城模块
    */
   public static final String WSHOP_MODULE_TEAM = "拼团";
   
   /**
    * SYSTEM_IMAGE_REF_TYPE
    */
   public static final String SYSTEM_IMAGE_REF_TYPE_ITEM = "POS_ITEM";
   public static final String SYSTEM_IMAGE_REF_TYPE_CATEGORY = "CATEGORY";  
   public static final String SYSTEM_IMAGE_REF_TYPE_CUSTOM = "CUSTOM";
   
   /**
    * 消费券应用分类
    */
   public static final String TICKET_CATEGORY_DEPOSIT = "会员卡储值券";
   public static final String TICKET_CATEGORY_PURCHASE = "购物抵用券";
   
   /**
    * 超量特价类型
    * 
    */
   public static final String PROMOTION_QUANTITY_CATEGORY_TRANSFER = "配送超量特价";
   public static final String PROMOTION_QUANTITY_CATEGORY_WHOLESALE = "批发超量特价";

   /**
    * 订单来源条件
    */
   public static final String POS_ORDER_SALE_TYPE_WCHAT = "微商城";
   public static final String POS_ORDER_SALE_TYPE_BRANCH = "实体店";
   
   /**
    * 微信模板通知类型
    */
   public static final String WEIXIN_SEND_KIND_CANCEL_ONLINE_ORDER = "在线订单退款";
   public static final String WEIXIN_SEND_KIND_DELIVER_ONLINE_ORDER = "在线订单发货";
   public static final String WEIXIN_SEND_KIND_ONLINE_ORDER_TRANSFER_NOTICE = "在线订单配送通知";
   public static final String WEIXIN_SEND_KIND_CARD_TYPE_UPGRADE = "卡类型升级";
   public static final String WEIXIN_SEND_KIND_CONSUME = "消费通知";
   public static final String WEIXIN_SEND_KIND_DEPOSIT = "存款通知";
   public static final String WEIXIN_SEND_KIND_SEND_COUPON = "赠券通知";
   /**
    * 模板类型
    */
   public static final String ITEM_FLAG_TYPE_QUERY = "查询模板";
   public static final String ITEM_FLAG_TYPE_NEW = "新品模板";

   /**
    * memcached缓存前缀
    */
   public static final String MEMCACHED_PRE_PAY_CODE = "AMA_MEMCACHED_PRE_PAY_CODE";
   public static final String MEMCACHED_PRE_COMFIRM_REQUEST = "AMA_MEMCACHED_PRE_COMFIRM_REQUEST";
   public static final String MEMCACHED_PRE_TRANSFER_LINE = "AMA_MEMCACHED_PRE_TRANSFER_LINE";
   public static final String MEMCACHED_PRE_SEND_BRANCH = "AMA_MEMCACHED_PRE_SEND_BRANCH";
   public static final String MEMCACHED_PRE_SAVEING_ONLINE_ORDER = "AMA_MEMCACHED_PRE_SAVEING_ONLINE_ORDER";
   public static final String MEMCACHED_PRE_ORDER_AUDITING_FID = "AMA_MEMCACHED_PRE_ORDER_AUDITING_FID";//审核中的单据号
   public static final String MEMCACHED_PRE_ORDER_AUDIT_FID = "AMA_MEMCACHED_PRE_ORDER_AUDIT_FID";//审核完成的单据号
   public static final String MEMCACHED_PRE_ALIPAY_ACCOUNT = "AMA_MEMCACHED_PRE_ALIPAY_ACCOUNT";//支付宝
   public static final String MEMCACHED_PRE_POS_MESSAGEBOARD = "AMA-POS-MESSAGEBOARD";//未读留言数
   public static final String MEMCACHED_PRE_POS_BOARDCAST = "AMA-POS-BOARDCAST";//终端广播
   public static final String MEMCACHED_PRE_CARD_BOOK_OPENID_NUM = "AMA_CARD_BOOK_OPENID_NUM";//帐套openid与 cardUserNum 关联
   public static final String MEMCACHED_PRE_MODIFY_BRANCH_NAME_DATE = "AMA_MODIFY_BRANCH_NAME_DATE";//上次修改分店名称时间
   public static final String MEMCACHED_PRE_MODIFY_BOOK_NAME_DATE = "AMA_MODIFY_BOOK_NAME_DATE";//上次修改帐套名称时间
   public static final String MEMCACHED_PRE_ORDER_OPERATOR_USERNUM = "AMA_ORDER_OPERATOR_USERNUM";//单据锁定操作人
   public static final String MEMCACHED_PRE_UNDEDUCTION_BRANCH = "AMA_UNDEDUCTION_BRANCH"; //锁定未扣库存明细的分店
   public static final String MEMCACHED_PRE_BRANCH_UPDATE_TIME = "AMA_BRANCH_UPDATE_TIME"; //分店缓存清空时间
   public static final String MEMCACHED_PRE_POSITEM_UPDATE_TIME = "AMA_POSITEM_UPDATE_TIME";//商品资料缓存清空时间
   public static final String MEMCACHED_PRE_ALIPAYACCOUNT_UPDATE_TIME = "AMA_ALIPAYACCOUNT_UPDATE_TIME";//在线支付设置缓存清空时间
   public static final String MEMCACHED_PRE_MARKET_ACTION_SCENE = "AMA_MARKET_ACTION_SCENE"; // 朋友圈分享营销活动缓存
   public static final String MEMCACHED_PRE_MEMBER_CARDCONSUME_OUTTRADENO = "AMA_MEMBER_CARDCONSUME_OUTTRADENO";//微会员卡消费第三方单据号
   public static final String MEMCACHED_PRE_POS_SYSTEM_TRACE_NUM = "AMA_POS_SYSTEM_TRACE_NUM"; //银联系统追踪号 用于冲正
   public static final String MEMCACHED_PRE_POS_JN_ORDER_NO = "AMA_POS_JN_ORDER_NO"; //江南银行执行单据号
   public static final String MEMCACHED_PRE_NEW_ONLINEORDER_FLAG = "AMA_NEW_ONLINEORDER_FLAG"; //新订单标记
	
	
	public static final int MEMCACHE_CARDUSER_VALID_TIME = 60 * 60 * 24; //一天
   public static final int MEMCACHE_MAX_VALID_TIME = 30 * 60 * 60 * 24; //30天

   /**
    * 单据重复审核控制失效时间
    */
   public static final int MEMCACHED_ORDER_AUDIT_EXPIRE_TIME = 300; //秒

   /**
    * CARD_BILL_TYPE
    */
   public static final String CARD_BILL_TYPE_TRANSFER = "调拨";
   public static final String CARD_BILL_TYPE_CHECK_INVENTORY = "盘点";
   
   /**
    * MNS队列名称
    */
   public static final String MNS_QUEUE_NAME_ONLINE_ORDER_PAY_MESSAGE = "AMA-ONLINE-ORDER-PAY-MESSAGE"; //在线订单付款通知
   public static final String MNS_QUEUE_NAME_POS_SYNCH_POS_ORDER = "AMAZON-POS-SYNCH-POS-ORDER"; //POSORDER

   
   
    public static final int REDIS_CACHE_LIVE_SECOND = 86400; //缓存有效时间一天
    /**
     * redis缓存前缀
     */
    public static final String REDIS_PRE_BOOK_RESOURCE = "AMA_BOOK_RESOURCE";
    public static final String REDIS_PRE_BRANCH_RESOURCE = "AMA_BRANCH_RESOURCE";
    public static final String REDIS_PRE_BRANCH = "AMA_BRANCH";
    public static final String REDIS_PRE_SUPPLIER = "AMA_SUPPLIER";
    public static final String REDIS_PRE_POS_CLIENT = "AMA_POS_CLIENT";
    public static final String REDIS_PRE_BRANCH_MATRIX = "AMA_BRANCH_MATRIX";
    public static final String REDIS_PRE_SYSTEM_BOOK = "AMA_SYSTEM_BOOK";
    public static final String REDIS_PRE_ALIPAY_ACCOUNT = "AMA_ALIPAY_ACCOUNT";
    public static final String REDIS_PRE_WSHOP_BRANCH_USER_OPENIDS = "AMA_WSHOP_BRANCH_USER_OPENIDS";
    public static final String REDIS_PRE_FRAME_COLUMN = "AMA_FRAME_COLUMN";
    public static final String REDIS_PRE_YOUZANSHOPID_BRANCHNUM = "AMA_YOUZANSHOPID_BRANCHNUM";//有赞shopid和branchNum映射
    public static final String REDIS_PRE_USER_COLLECT_BRANCH = "AMA_USER_COLLECT_BRANCH";//用户收藏门店
    public static final String REDIS_PRE_ONLINE_TEAM_BATCH_OPENID = "AMA_ONLINE_TEAM_BATCH_OPENID";//用户已参加团购的openid
    public static final String REDIS_PRE_POS_ORDER_INVOICE_COMPANY = "AMA_POS_ORDER_INVOICE_COMPANY";
    public static final String REDIS_PRE_MARKET_DATA_DELIVER_COUNT = "AMA_MARKET_DATA_DELIVER_COUNT"; //朋友圈营销活动已发券数量
    public static final String REDIS_PRE_TMALL_UPLOAD_TIME = "AMA_TMALL_UPLOAD_TIME"; //天猫单据上传时间
    public static final String REDIS_PRE_CARD_DEPOSIT_LOG_TIME = "AMA_CARD_DEPOSIT_LOG_TIME"; //会员卡存款日志时间
    public static final String REDIS_PRE_CARD_CONSUME_LOG_TIME = "AMA_CARD_CONSUME_LOG_TIME"; //会员卡消费日志时间
    public static final String REDIS_PRE_CARD_LOSS_LOG_TIME = "AMA_CARD_LOSS_LOG_TIME"; //会员卡挂失日志时间
    public static final String REDIS_PRE_CARD_REPLACE_LOG_TIME = "AMA_CARD_REPLACE_LOG_TIME"; //会员卡换卡日志时间
    public static final String REDIS_PRE_CARD_RELAT_LOG_TIME = "AMA_CARD_RELAT_LOG_TIME"; //会员卡续卡日志时间
    public static final String REDIS_PRE_TRANSFERED_ITEM = "AMA_TRANSFERED_ITEM"; // 已配送商品
    public static final String REDIS_PRE_OLD_TRANSFER_OUT_DATA_SYNCH = "AMA_OLD_TRANSFER_OUT_DATA_SYNCH"; //调出单历史数据redis同步标记
    public static final String REDIS_PRE_EXCEPT_CPU_ID = "AMA_EXCEPT_CPU_ID"; //例外CPUID
    public static final String REDIS_PRE_EXCEPT_CPU_ID_DATA_SYNCH = "AMA_OLD_EXCEPT_CPU_ID_DATA_SYNCH"; //例外cpuIDreids同步标记
    public static final String REDIS_PRE_APP_MIN_VERSION = "AMA_APP_MIN_VERSION"; //客户端最低版本号
    public static final String REDIS_PRE_TMALL_ITEM_MATCH = "AMA_TMALL_ITEM_MATCH"; //天猫商品自动匹配并发控制
    public static final String REDIS_PRE_BRANCH_UNSETTLE_DEPOSIT = "AMA_BRANCH_UNSETTLE_DEPOSIT"; //门店未结存款限额
    public static final String REDIS_PRE_STOCK_GROUP_STATE = "AMA_STOCK_GROUP_STATE"; //加工中心分组状态
	public static final String REDIS_PRE_CENTER_STORE = "AMA_PRE_CENTER_STORE";

   /**
    * SMS_SEND_TYPE
    */
	public static final int SMS_TYPE_SALE = 0;//营销类
	public static final int SMS_TYPE_NOTICE = 1;//通知类
	
	
	/**
	 * 循环类型
	 */
	public static final String DATE_REPEAT_TYPE_DAY = "每日";
	public static final String DATE_REPEAT_TYPE_WEEK = "每周";
	public static final String DATE_REPEAT_TYPE_MONTH = "每月";
	public static final String DATE_REPEAT_TYPE_YEAR = "每年";
	
	/**
	 * 配置文件加密key
	 */
	public static final String PROPERTY_ENCRYPT_KEY = "5304ea24ece5b82bc027c30889a7891b";
	
	/**
	 * 新希望微商城提醒帐套号
	 */
	public static final String NHSOFT_WSHOP_REMIN_BOOK_CODE = "77777777";
	
	/**
	 * SCHEDULER_TASK_NAME 定时器名称
	 */
	public static final String SCHEDULER_TASK_NAME_PRICE_ADJUSTMENT_EFFECTIVE = "PRICE_ADJUSTMENT_EFFECTIVE";//调价单应用
	public static final String SCHEDULER_TASK_NAME_SYNCH_DPC = "SYNCH_DPC";//数据同步大中心
	public static final String SCHEDULER_TASK_NAME_PRICE_ADJUSTMENT_INVALID = "PRICE_ADJUSTMENT_INVALID";//调价单失效
	public static final String SCHEDULER_TASK_NAME_WHOLESALETRASFER_PRICE_ADJUSTMENT_EFFECTIVE = "WHOLESALETRASFER_PRICE_ADJUSTMENT_EFFECTIVE";//批发配送调价单应用
	public static final String SCHEDULER_TASK_NAME_BACKUP_BOOKRESOURCE = "BACKUP_BOOKRESOURCE";//备份帐套字典表
	public static final String SCHEDULER_TASK_NAME_CARD_LOG_UPLOAD = "CARD_LOG_UPLOAD"; //卡操作日志上传

	/**
	 * 调价单失效类型
	 */
	public static final int PRICE_ADJUSTMENT_RETURN_TYPE_BY_ORDER = 0;//按单据
	public static final int PRICE_ADJUSTMENT_RETURN_TYPE_BY_ITEM = 1;//按商品资料
	public static final int PRICE_ADJUSTMENT_RETURN_TYPE_ZERO = 2;//设为0
	
	public static final String CHECK_ORDER_ITEM_SCOPE_ALL = "全部商品";
	public static final String CHECK_ORDER_ITEM_SCOPE_HAVESTORE = "有库存记录的商品";
	public static final String CHECK_ORDER_ITEM_SCOPE_NEGATESTORE = "负库存商品";
	
	/**
	 * 自定义群体类型
	 */
	public static final String GROUP_CUSTOMER_TYPE_CARD = "会员群体";
	public static final String GROUP_CUSTOMER_TYPE_POS_CLIENT = "批发客户群体";


	/**
	 * 销售单结算方式
	 */
	public static final String WHOLESALE_ORDER_SETTLEMET_TYPE_PAYNOW = "付现";
	public static final String WHOLESALE_ORDER_SETTLEMET_TYPE_PAYAFTER = "欠账";

	//广播状态
	public static final int POS_BROADCAST_STATUS_READ = 1;
	public static final int POS_BROADCAST_STATUS_COMPLETE = 2;
	public static final int POS_BROADCAST_STATUS_CANCEL = 3;
	
	/**
	 * 默认供应商类别
	 */
	public static final String DEFAULT_SUPPLIER_KIND_NAME = "默认供应商类别";

	/**
	 * 零钱包类型
	 */
	public static final String CARD_CHANGE_TYPE_LESS_THAN_1 = "小于1元";
	public static final String CARD_CHANGE_TYPE_LESS_THAN_5 = "小于5元";
	public static final String CARD_CHANGE_TYPE_LESS_THAN_10 = "小于10元";

	/**
	 * 采购汇总类型
	 */
	public static final int TASK_REQUEST_GENE = 1; //已生成任务单
	public static final int TASK_REQUEST_NOT_GENE = 2; //未生成任务单
	public static final int TASK_REQUEST_IGNONE = 3; //忽略
	public static final int TASK_REQUEST_GENE_STOCK = 4; //已生成出仓任务
	public static final int TASK_REQUEST_NOT_GENE_STOCK = 5; //未生成出仓任务
	
	/**
	 * card_change_type
	 */
	public static final String CARD_CHANGE_TYPE_IN = "存零钱包";
	public static final String CARD_CHANGE_TYPE_OUT = "取零钱包";

	/**
	 * 微信支付渠道
	 */
	public static final String WECHAT_CHANNEL_SELF = "通过商户公众号申请";
	public static final String WECHAT_CHANNEL_NHOSFT = "通过新希望服务商申请";
	public static final String WECHAT_CHANNEL_UNIONPAY = "银联";
    public static final String WECHAT_CHANNEL_JNBANK = "江南银行";
	public static final String PAY_CHANNEL_ZXBANK = "中信银行";
	public static final String PAY_CHANNEL_COMMON = "官方通道";
	
	public static final String NHSOFT_WEIXIN_CERT_NAME = "apiclient_cert.p12";
	
	/**
	 * 财务系统对接凭证类型
	 */
	public static final String FINANCE_SALE_CERTIFICATE = "收款单";
	public static final String FINANCE_CARDDEPOSIT_CERTIFICATE = "储值卡销售";
	public static final String FINANCE_DUERECEIVE_CERTIFICATE = "应收单";
	public static final String FINANCE_DUERECEIVE_CERTIFICATE_WHOLESALE = "批发应收单";
	public static final String FINANCE_DUEPAY_CERTIFICATE = "应付单";
	public static final String FINANCE_RECEIVE_IN_CERTIFICATE = "采购入库";
	public static final String FINANCE_RECEIVE_ESTIMATED_CERTIFICATE = "采购暂估";
	public static final String FINANCE_DUE_ESTIMATED_CERTIFICATE = "冲暂估";
	public static final String FINANCE_SALE_COST_CERTIFICATE = "销售成本结转";

	/**
	 * 财务系统借贷方向
	 */
	public static final String FINANCE_DIRECTION_DEBT = "借方";
	public static final String FINANCE_DIRECTION_LEND = "贷方";
	
	public static final String ADJUSTMENT_NAME_SYSTEM_IN = "系统自动调整入库";

	public static final String SYSTEM_GENERATE = "系统自动生成";
	
	public static final String LABEL_INORDER_TARE_ADJUSTMENT = "INORDER_TARE_ADJUSTMENT";
	
	/**
	 * 商品类型
	 */
    public static final State ITEM_NEW = new State(1 << 0, "新品");
    public static final State ITEM_RECOMMEND = new State(1 << 1, "推荐");
    
    public static final String SERVER_QUEUE_TYPE_ONLINETEAMSUCCESS = "onlineTeamSuccess";
    public static final String SERVER_QUEUE_TYPE_MARKETACTIONIMMEDIATE = "marketActionImmediate";
    public static final String SERVER_QUEUE_TYPE_CHECKORDERSNAPSHOT = "checkOrderSnapshot";
    public static final String SERVER_QUEUE_TYPE_MARKETACTIONIMMEDIATE_2 = "marketActionImmediate2";


    public static final String ONLINE_ORDER_LABEL_NEWGROUP = "NEW_GROUP";
    public static final String ONLINE_ORDER_LABEL_OLDGROUP = "OLD_GROUP";
    public static final String ONLINE_ORDER_LABEL_HASTICKET = "HAS_TICKET";
    public static final String ONLINE_ORDER_LABEL_HASPOSORDER = "HAS_POSORDER";

    /**
     * INNER_ORDER_TYPE
     */
    public static final String INNER_ORDER_TYPE_TRANSFER = "调拨申请";
    public static final String INNER_ORDER_TYPE_RETURN = "退货申请";
    public static final String INNER_ORDER_TYPE_DIRECT = "直营调拨申请";
    
    /**
     * 快递公司名称
     */
    public static final String EXPRESS_NAME_YUNDA = "韵达快递";
    public static final String EXPRESS_NAME_SHENGTONG = "申通快递";
    public static final String EXPRESS_NAME_TIANTIAN = "天天快递";
    public static final String EXPRESS_NAME_SHUNFENG = "顺丰快递";
    public static final String EXPRESS_NAME_YUANTONG = "圆通速递";
    public static final String EXPRESS_NAME_DEBANG = "德邦";
    public static final String EXPRESS_NAME_EMS = "EMS";
    
    /**
     * MNS_COMMAND
     */
	public static String MESSAGE_COMMAND_WEIXIN_CARD_REVOKE = "AMA_WEIXIN_CARD_REVOKE";
	public static String MESSAGE_COMMAND_WEIXIN_CARD_UPDATE = "AMA_WEIXIN_CARD_UPDATE";
	
	/**
	 * WEXIN_CARD_COMMAND
	 */
	public static String WEXIN_CARD_COMMAND_REVOKE = "REVOKE";
	public static String WEXIN_CARD_COMMAND_BALANCE = "BALANCE";
	public static String WEXIN_CARD_COMMAND_POINT = "POINT";
	public static String WEXIN_CARD_COMMAND_TICKET = "TICKET";
	
	/**
	 * APP 名称
	 */
	public static String APP_SOFTWARE_NAME_CHAIN_ASSISTANT = "门店助手";
	public static String APP_SOFTWARE_NAME_CHAIN_MANAGER = "门店管家";
	public static String APP_SOFTWARE_NAME_WHOLESALE_ASSISTANT = "批发助手"; //已不用
	public static String APP_SOFTWARE_NAME_CENTERSTORE_NORMAIL = "采购配送标准版";
	public static String APP_SOFTWARE_NAME_CENTERSTORE_EXPERT = "采购配送高级版";
	public static String APP_SOFTWARE_NAME_FRUIT_SHOP = "生鲜门店";
	
	
	/**
	 * 朋友圈营销 状态
	 */
	public static int MARKET_ACTION_MOMENTS_INIT = 1; //未开始
	public static int MARKET_ACTION_MOMENTS_PROCESSING = 2; //进行中
	public static int MARKET_ACTION_MOMENTS_FINISH = 3; //已结束
	
	/**
	 * APP消息推送命令
	 */
	public static String APP_PUSH_COMMAND_REFRESH_OUTORDER = "REFRESH_OUTORDER";//刷新调出单
	
	
	/**
	 * 调出单标签
	 */
	public static String OUT_ORDER_LABEL_BRANCH_CONFIRM = "BRANCH_CONFIRM";//门店助手用 门店确认
	public static String OUT_ORDER_LABEL_CENTER_CONFIRM = "确认";//零食工坊用总部确认
	
	/**
	 * 促销折扣类型
	 */
	public static String POLICY_DISCOUNT_ASSIGNED_TYPE_ALL = "全场";
	public static String POLICY_DISCOUNT_ASSIGNED_TYPE_ITEM = "指定商品";
	public static String POLICY_DISCOUNT_ASSIGNED_TYPE_CATEGORY = "指定类别";
	
	/**
	 * 仓管中心分组类型
	 */
	public static String STOCK_GROUP_TYPE_NON_PROCESS_TRANSFER = "非加工配送组";
	public static String STOCK_GROUP_TYPE_TRANSFER = "配送组";
	public static String STOCK_GROUP_TYPE_PROCESS = "加工组";
	public static String STOCK_GROUP_TYPE_RECEIVE = "领料组";
	
	
	/**
	 * 茂雄专用
	 */
	public static final String MEMCACHED_PRE_MAOXIONG_DEALBIZDAY = "AMA_MEMCACHED_MAOXIONG_DEALBIZDAY";
	public static final String REDIS_PRE_MAOXIONG_DEALBIZDAY = "AMA_REDIS_MAOXIONG_DEALBIZDAY";

    /**
     * BranchParam
     */
	public static final String BRANCH_PARAM_ONLINE_PUSH_ENABLE = "新订单推送至APP";
	
	public static final String TASK_REQUEST_IGNORE_ITEMNUMS_TWO = "第二次要货汇总忽略商品";
	public static final String TASK_REQUEST_IGNORE_ITEMNUMS_THREE = "第三次要货汇总忽略商品";
	
	/**
	 * 初始化卡回收
	 */
	public static final String CARD_USER_LOG_REVOKE_INIT_CARD = "初始化卡回收";

}
