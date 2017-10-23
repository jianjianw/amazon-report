package com.nhsoft.module.report.dto;

import java.io.Serializable;

public class BaseResponseDTO implements Serializable {

	/**
	 * 
	 */
	public static final String SUCCESS_CODE = "0,请求成功";	
	public static final String ERROR_MSG_TIMESTAMP_ERROR = "10001,时间戳格式不合法";
	public static final String ERROR_MSG_TIME_OUT_ERROR = "10002,请求超时";
	public static final String ERROR_MSG_PARAM_ERROR = "10003,请求参数错误";
	public static final String ERROR_MSG_SIGN_ERROR = "10004,签名验证失败";
	public static final String ERROR_MSG_SYSTEM_ERROR = "10005,系统异常";
	public static final String ERROR_MSG_IP_NOT_ALLOWED = "10006,当前IP拒绝访问";
	public static final String ERROR_MSG_METHOD_NOT_EXIST = "10007,请求方法名不存在";
	public static final String ERROR_MSG_ID_NOT_AUTHORIZED = "10008,无效的app_id";
	public static final String ERROR_MSG_METHOD_NOT_ALLOWED = "10009,接口未授权";
	public static final String ERROR_MSG_JSON_ERROR = "10010,JSON解析失败";
	public static final String ERROR_MSG_OUT_TRADE_NO_EXIST = "10011,关联流水号已存在";
	public static final String ERROR_MSG_OUT_OF_SERVICE = "10012,该服务暂时不可用";

	
	public static final String ERROR_MSG_BRANCH_NOT_EXIST = "20001,请求分店号不存在";
	public static final String ERROR_MSG_BRANCH_NAME_ERROR = "20002,请求分店名称错误";
	public static final String ERROR_MSG_ITEM_NOT_EXIST = "20003,找不到商品资料";
	public static final String ERROR_MSG_ITEM_UNIT_ERROR = "20004,商品单位不合法";
	public static final String ERROR_MSG_SERVICE_ERROR_CODE = "20005";
	
	public static final String ERROR_MSG_CARD_NOT_EXIST = "20006,会员卡不存在";
	public static final String ERROR_MSG_CARD_NOT_VALID = "20007,会员卡未启用";
	public static final String ERROR_MSG_CARD_EXPIRE = "20008,会员卡已过期";
	public static final String ERROR_MSG_CARD_LOCKED = "20009,会员卡已锁定";
	public static final String ERROR_MSG_CARD_IC_FORBIDDEN = "20010,IC卡不允许线上消费";
	public static final String ERROR_MSG_CARD_BALANCE_NOTENOUGH_CODE = "20011"; //会员卡余额不足
	public static final String ERROR_MSG_CARD_POINT_BALANCE_NOTENOUGH = "20012,会员卡积分余额不足";
	public static final String ERROR_MSG_CARD_VALID_BALANCE_NOTENOUGH_CODE = "20013";//会员卡可用余额不足

	private static final long serialVersionUID = 712669022807564812L;
	private String code;
	private String msg;
	
	public BaseResponseDTO(){
		
	}
	
	public static void main(String[] args) {
		String a=  "221,会员卡可用余额不足，当前可用余额为%s元";
		String[] array = a.split(",");
		System.out.println(array);
	}
	
	public BaseResponseDTO(String constants){
		String[] array = constants.split(",");
		this.code = array[0];		
		this.msg = array[1];
		
		
	}
	
	public BaseResponseDTO(String code, String msg){
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
