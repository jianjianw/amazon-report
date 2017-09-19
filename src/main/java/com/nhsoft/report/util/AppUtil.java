package com.nhsoft.report.util;

import com.google.gson.*;

import com.nhsoft.report.model.Branch;
import com.nhsoft.report.model.ItemMatrix;
import com.nhsoft.report.model.PosItem;
import com.nhsoft.report.model.Supplier;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.*;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("rawtypes")
public class AppUtil {

	private static final Logger logger = LoggerFactory.getLogger(AppUtil.class);
	private static ChangeItemService changeItemService;
	private static WebLogService webLogService;
	private static String filePath = null;
	private static String reportPath = null;
	private static String excelPath = null;
	private static String pdfPath = null;
	private static String utilPath = null;
	private static String qrCodeImagePath = null;
	private static String serverIp;
	private static ThreadPoolTaskExecutor poolTaskExecutor;
	private static MyThreadPoolTaskScheduler poolTaskScheduler;
	private static String fileSeparator = System.getProperty("file.separator");
	private static String weixinMNSQueueName;
	private static String tmallPosOrderMNSQueueName;
	private static String tmallRequestOrderMNSQueueName;
	private static String tmallOutOrderMNSQueueName;
	private static String tmallShipOrderMNSQueueName;
	private static Gson gson;

	public static String getWeixinMNSQueueName() {
		return weixinMNSQueueName;
	}

	public static ThreadPoolTaskExecutor getPoolTaskExecutor() {
		return poolTaskExecutor;
	}

	public void setPoolTaskExecutor(ThreadPoolTaskExecutor poolTaskExecutor) {
		AppUtil.poolTaskExecutor = poolTaskExecutor;
	}

	public static final List<String> ENGLISH_STOP_WORDS = Arrays.asList("a", "an", "and", "are", "as", "at", "be",
			"but", "by", "for", "if", "in", "into", "is", "it", "no", "not", "of", "on", "or", "such", "that", "the",
			"their", "then", "there", "these", "they", "this", "to", "was", "will", "with");

	public void setChangeItemService(ChangeItemService changeItemService) {
		AppUtil.changeItemService = changeItemService;
	}

	public void setWebLogService(WebLogService webLogService) {
		AppUtil.webLogService = webLogService;
	}
	
	public void setWeixinMNSQueueName(String weixinMNSQueueName) {
		AppUtil.weixinMNSQueueName = weixinMNSQueueName;
	}

	public static String getQrCodeImagePath() {
		return qrCodeImagePath;
	}

	public static void setQrCodeImagePath(String qrCodeImagePath) {
		AppUtil.qrCodeImagePath = qrCodeImagePath;
	}

	private static final String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; ++i) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	public static String readImageUrl() {
		return "posImage" + fileSeparator;
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
		} catch (Exception localException) {
		}
		return resultString;
	}

	public static Double BigDecimalToDouble(BigDecimal decimal) {
		if (decimal != null) {
			return decimal.doubleValue();
		} else {
			return 0.00;
		}
	}

	public static String NumToBig(BigDecimal decimalNum) {
		// String[][] s = new String[][] {{"1","1"},{"1","1"}};
		String result = "";
		String cNum = new String("零壹贰叁肆伍陆柒捌玖-万仟佰拾亿仟佰拾万仟佰拾元角分");
		String[][] cChar = new String[][] {
				{ "零仟", "零佰", "零拾", "零零零", "零零", "零亿", "零万", "零元", "亿万", "零角", "零分", "零整" },
				{ "零", "零", "零", "零", "零", "亿", "万", "元", "亿", "零", "整", "整" } };
		String sNum = String.valueOf(decimalNum.multiply(new BigDecimal(100)).abs().intValue());
		for (int i = 0; i <= sNum.length() - 1; i++) {
			result = result + cNum.charAt(sNum.charAt(i) - 47 - 1) + cNum.charAt(26 - (sNum.length() - 1) + i - 1);
		}
		for (int i = 0; i <= 11; i++) {// 去掉多余的零
			result = result.replaceAll(cChar[0][i], cChar[1][i]);
		}
		if (decimalNum.signum() < 0) {
			result = "负" + result;
		}
		return result;
	}

	public static String getMoneyFormat(Double value) {
		if (value == null) {
			return "0.00";
		}
		DecimalFormat df = new DecimalFormat("#,##0.00");
		return df.format(value);
	}

	public static String getSimpleMoneyFormat(Double value) {
		if (value == null) {
			return "0";
		}
		return value.toString();
	}

	public static String getStringFormat(String value) {
		if (value == null) {
			return "";
		}
		return value.toString();
	}

	public static String getNumberFormat(Double value) {
		if (value == null) {
			return "0.00";
		}
		DecimalFormat df = new DecimalFormat("#,##0.#");
		return df.format(value);
	}

	public static String getRateFormat(Double value) {
		if (value == null) {
			return "0.00";
		}
		DecimalFormat df = new DecimalFormat("0.00#############");
		return df.format(value);
	}

	public static String getBooleanFormat(Boolean value) {
		if (value == null) {
			return "否";
		}
		if (value) {
			return "是";
		}
		return "否";
	}

	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
	}

	public static boolean isNotNull(String var) {
		if ((var != null) && (!var.equals(""))) {
			return true;
		}

		return false;
	}

	public static String calcGrowingRate(Double lastPeriod, Double currentPeriod) {
		if ((currentPeriod.compareTo(0.00) > 0) && (lastPeriod.compareTo(0.00) == 0)) {
			return "100.00%";
		} else if ((currentPeriod.compareTo(0.00) < 0) && (lastPeriod.compareTo(0.00) == 0)) {
			return "-100.00%";
		} else if ((currentPeriod.compareTo(0.00) == 0) && (lastPeriod.compareTo(0.00) == 0)) {
			return "0.00%";
		}
		Double precent = (currentPeriod - lastPeriod) * 100 / lastPeriod;
		return String.format("%.2f%%", precent);
	}

	public static String generateUUID() {
		return UUID.randomUUID().toString();
	}

	public static int getMobileType(String mobile) {
		if (StringUtils.length(mobile) == 12) {
			mobile = StringUtils.substring(mobile, 1);
		}
		if ((StringUtils.isEmpty(mobile)) || (StringUtils.length(mobile) != 11)) {
			return -1;
		}
		List<String> chinaUnicom = Arrays.asList(new String[] { "130", "131", "132", "155", "156", "186", "145" });
		List<String> chinaMobile = Arrays.asList(new String[] { "134", "135", "136", "137", "138", "139", "150", "151",
				"152", "157", "158", "159", "188", "187", "147" });

		List<String> chinaTelecom = Arrays.asList(new String[] { "133", "153", "189", "180" });
		if (chinaUnicom.contains(mobile.substring(0, 3)))
			return 1;// 联通
		if (chinaMobile.contains(mobile.substring(0, 3)))
			return 2; // 移动
		if (chinaTelecom.contains(mobile.substring(0, 3)))
			return 3; // 电信
		return -1;// 其它
	}

	public static List<Integer> getUnStoreTypes() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(AppConstants.C_ITEM_TYPE_KIT);
		list.add(AppConstants.C_ITEM_TYPE_NON_STOCK);
		list.add(AppConstants.C_ITEM_TYPE_CUSTOMER_KIT);
		return list;
	}

	public static Branch getBranch(List<Branch> branchs, Integer branchNum) {
		for (int i = 0; i < branchs.size(); i++) {
			if (branchs.get(i).getId().getBranchNum().equals(branchNum)) {
				return branchs.get(i);
			}
		}
		return null;
	}

	public static PosMachine getPosMachine(List<PosMachine> posMachines, String machineId) {
		for (PosMachine posMachine : posMachines) {
			if (posMachine.getPosMachineTerminalId().equals(machineId)) {
				return posMachine;
			}
		}
		return null;
	}

	public static PosItem getPosItem(Integer itemNum, List<PosItem> posItems) {
		if (posItems == null) {
			return null;
		}
		for (int i = 0; i < posItems.size(); i++) {
			if (posItems.get(i).getItemNum().equals(itemNum)) {
				return posItems.get(i);
			}
		}
		return null;
	}

	public static Supplier getSupplier(Integer supplierNum, List<Supplier> suppliers) {
		for (int i = 0; i < suppliers.size(); i++) {
			if (suppliers.get(i).getSupplierNum().equals(supplierNum)) {
				return suppliers.get(i);
			}
		}
		return null;
	}

	public static PosItem getPosItemByCode(String itemCode, List<PosItem> posItems) {
		for (int i = 0; i < posItems.size(); i++) {
			PosItem posItem = posItems.get(i);
			if (posItem.getItemDelTag() != null && posItem.getItemDelTag()) {
				continue;
			}
			if (posItem.getItemEliminativeFlag() != null && posItem.getItemEliminativeFlag()) {
				continue;
			}
			if (posItems.get(i).getItemCode().equals(itemCode)) {
				return posItem;
			}
		}
		return null;
	}

	public static StoreItemSupplier getStoreItemSupplier(List<StoreItemSupplier> storeItemSuppliers,
			StoreItemSupplierId id) {
		for (int i = 0; i < storeItemSuppliers.size(); i++) {
			StoreItemSupplier storeItemSupplier = storeItemSuppliers.get(i);
			if (storeItemSupplier.getId().equals(id)) {
				return storeItemSupplier;
			}
		}
		return null;
	}

	public static Boolean strToBool(String str) {
		if (str == null) {
			return false;
		}
		if (str.equals("0")) {
			return false;
		} else {
			return true;
		}
	}

	private static void saveChangeItem(String group, String fieldType, String field, String oldValue, String newValue,
			String changeItemBillType, String billNo, Integer itemGradeNum, AppUser appUser) {
		
		if(appUser == null){
			
			if (CurrentThread.getSessionHolder() == null) {
				return;
			}
			appUser = (AppUser) CurrentThread.getSessionHolder().getAttribute(AppConstants.CURRENT_USER);
			if (appUser == null) {
				logger.info("当前登录用户不存在");
				return;
			}
		}
		ChangeItem changeItem = new ChangeItem();
		changeItem.setSystemBookCode(appUser.getSystemBookCode());
		if (group == null) {
			if(appUser.getBranchNum() != null){
				changeItem.setChangeItemGroup(appUser.getBranchNum().toString());
				
			} else {
				changeItem.setChangeItemGroup(appUser.getBranchs().get(0).getId().getBranchNum().toString());
				
			}

		} else {
			changeItem.setChangeItemGroup(group);
		}
		changeItem.setChangeItemFieldType(fieldType);
		changeItem.setChangeItemField(field);
		changeItem.setChangeItemOldValue(oldValue);
		changeItem.setChangeItemNewValue(newValue);
		changeItem.setChangeItemOperateDate(Calendar.getInstance().getTime());
		changeItem.setChangeItemOperator(appUser.getAppUserName());
		changeItem.setChangeItemBillType(changeItemBillType);
		changeItem.setChangeItemBillNo(billNo);
		changeItem.setChangeItemItemGrade(itemGradeNum);
		changeItemService.save(changeItem);

	}

	public static void saveChangeItem(String group, String fieldType, String field, String oldValue, String newValue,
			String changeItemBillType, String billNo) {
		saveChangeItem(group, fieldType, field, oldValue, newValue, changeItemBillType, billNo, null, null);
	}
	
	public static void saveChangeItem(String group, String fieldType, String field, String oldValue, String newValue,
			String changeItemBillType, String billNo, AppUser appUser) {
		saveChangeItem(group, fieldType, field, oldValue, newValue, changeItemBillType, billNo, null, appUser);
	}

	public static void comparePosItem(PosItem persisent, PosItem posItem) {
		if (persisent != null) {
			if (persisent.getItemRegularPrice().compareTo(posItem.getItemRegularPrice()) != 0) {
				saveChangeItem(null, posItem.getItemNum().toString(), AppConstants.ITEM_REGULAR_PRICE, persisent
						.getItemRegularPrice().toString(), posItem.getItemRegularPrice().toString(),
						AppConstants.C_CHANGE_ITEM_BILL_BASIC, "");
			}
			if (persisent.getItemLevel2Price().compareTo(posItem.getItemLevel2Price()) != 0) {
				saveChangeItem(null, posItem.getItemNum().toString(), AppConstants.ITEM_REGULAR_PRICE2, persisent
						.getItemLevel2Price().toString(), posItem.getItemLevel2Price().toString(),
						AppConstants.C_CHANGE_ITEM_BILL_BASIC, "");
			}
			if (persisent.getItemLevel3Price().compareTo(posItem.getItemLevel3Price()) != 0) {
				saveChangeItem(null, posItem.getItemNum().toString(), AppConstants.ITEM_REGULAR_PRICE3, persisent
						.getItemLevel3Price().toString(), posItem.getItemLevel3Price().toString(),
						AppConstants.C_CHANGE_ITEM_BILL_BASIC, "");
			}
			if (persisent.getItemLevel4Price().compareTo(posItem.getItemLevel4Price()) != 0) {
				saveChangeItem(null, posItem.getItemNum().toString(), AppConstants.ITEM_REGULAR_PRICE4, persisent
						.getItemLevel4Price().toString(), posItem.getItemLevel4Price().toString(),
						AppConstants.C_CHANGE_ITEM_BILL_BASIC, "");
			}
			if (persisent.getItemCostPrice().compareTo(posItem.getItemCostPrice()) != 0) {
				saveChangeItem(null, posItem.getItemNum().toString(), AppConstants.ITEM_COST_PRICE, persisent
						.getItemCostPrice().toString(), posItem.getItemCostPrice().toString(),
						AppConstants.C_CHANGE_ITEM_BILL_BASIC, "");
			}
			if (persisent.getItemTransferPrice().compareTo(posItem.getItemTransferPrice()) != 0) {
				saveChangeItem(null, posItem.getItemNum().toString(), AppConstants.ITEM_TRANSFER_PRICE, persisent
						.getItemTransferPrice().toString(), posItem.getItemTransferPrice().toString(),
						AppConstants.C_CHANGE_ITEM_BILL_BASIC, "");
			}
			if (persisent.getItemWholesalePrice().compareTo(posItem.getItemWholesalePrice()) != 0) {
				saveChangeItem(null, posItem.getItemNum().toString(), AppConstants.ITEM_WHOLESALE_PRICE, persisent
						.getItemWholesalePrice().toString(), posItem.getItemWholesalePrice().toString(),
						AppConstants.C_CHANGE_ITEM_BILL_BASIC, "");
			}
			if (persisent.getItemMinPrice().compareTo(posItem.getItemMinPrice()) != 0) {
				saveChangeItem(null, posItem.getItemNum().toString(), AppConstants.ITEM_MIN_PRICE, persisent
						.getItemMinPrice().toString(), posItem.getItemMinPrice().toString(),
						AppConstants.C_CHANGE_ITEM_BILL_BASIC, "");
			}
			if (persisent.getItemLevel2Wholesale() != null
					&& persisent.getItemLevel2Wholesale().compareTo(posItem.getItemLevel2Wholesale()) != 0) {
				saveChangeItem(null, posItem.getItemNum().toString(), AppConstants.ITEM_WHOLESALE_PRICE2, persisent
						.getItemLevel2Wholesale().toString(), posItem.getItemLevel2Wholesale().toString(),
						AppConstants.C_CHANGE_ITEM_BILL_BASIC, "");
			}
			if (persisent.getItemLevel3Wholesale() != null
					&& persisent.getItemLevel3Wholesale().compareTo(posItem.getItemLevel3Wholesale()) != 0) {
				saveChangeItem(null, posItem.getItemNum().toString(), AppConstants.ITEM_WHOLESALE_PRICE3, persisent
						.getItemLevel3Wholesale().toString(), posItem.getItemLevel3Wholesale().toString(),
						AppConstants.C_CHANGE_ITEM_BILL_BASIC, "");
			}
			if (persisent.getItemLevel4Wholesale() != null
					&& persisent.getItemLevel4Wholesale().compareTo(posItem.getItemLevel4Wholesale()) != 0) {
				saveChangeItem(null, posItem.getItemNum().toString(), AppConstants.ITEM_WHOLESALE_PRICE4, persisent
						.getItemLevel4Wholesale().toString(), posItem.getItemLevel4Wholesale().toString(),
						AppConstants.C_CHANGE_ITEM_BILL_BASIC, "");
			}
			if (persisent.getItemInventoryRate().compareTo(posItem.getItemInventoryRate()) != 0) {
				saveChangeItem(null, posItem.getItemNum().toString(), AppConstants.ITEM_INVENTORY_RATE, persisent
						.getItemInventoryRate().toString(), posItem.getItemInventoryRate().toString(),
						AppConstants.C_CHANGE_ITEM_BILL_BASIC, "");
			}
			if (persisent.getItemTransferRate().compareTo(posItem.getItemTransferRate()) != 0) {
				saveChangeItem(null, posItem.getItemNum().toString(), AppConstants.ITEM_TRANSFER_RATE, persisent
						.getItemTransferRate().toString(), posItem.getItemTransferRate().toString(),
						AppConstants.C_CHANGE_ITEM_BILL_BASIC, "");
			}
			if (persisent.getItemPurchaseRate().compareTo(posItem.getItemPurchaseRate()) != 0) {
				saveChangeItem(null, posItem.getItemNum().toString(), AppConstants.ITEM_PURCHASE_RATE, persisent
						.getItemPurchaseRate().toString(), posItem.getItemPurchaseRate().toString(),
						AppConstants.C_CHANGE_ITEM_BILL_BASIC, "");
			}
			if (persisent.getItemWholesaleRate().compareTo(posItem.getItemWholesaleRate()) != 0) {
				saveChangeItem(null, posItem.getItemNum().toString(), AppConstants.ITEM_WHOLESALE_RATE, persisent
						.getItemWholesaleRate().toString(), posItem.getItemWholesaleRate().toString(),
						AppConstants.C_CHANGE_ITEM_BILL_BASIC, "");
			}
			// if(persisent.getItemSaleCeaseFlag() == null){
			// persisent.setItemSaleCeaseFlag(false);
			// }
			// if(posItem.getItemSaleCeaseFlag() == null){
			// posItem.setItemSaleCeaseFlag(false);
			// }
			// if(persisent.getItemSaleCeaseFlag().compareTo(posItem.getItemWholesaleRate())
			// != 0){
			// saveChangeItem(null, posItem.getItemNum().toString(),
			// AppConstants.ITEM_WHOLESALE_RATE,
			// persisent.getItemWholesaleRate().toString(),
			// posItem.getItemWholesaleRate().toString(),
			// AppConstants.C_CHANGE_ITEM_BILL_BASIC, "");
			// }

		} else {
			if (posItem.getItemRegularPrice().compareTo(BigDecimal.ZERO) > 0) {
				saveChangeItem(null, posItem.getItemNum().toString(), AppConstants.ITEM_REGULAR_PRICE, "0.00", posItem
						.getItemRegularPrice().toString(), AppConstants.C_CHANGE_ITEM_BILL_BASIC, "");

			}
			if (posItem.getItemLevel2Price().compareTo(BigDecimal.ZERO) > 0) {
				saveChangeItem(null, posItem.getItemNum().toString(), AppConstants.ITEM_REGULAR_PRICE2, "0.00", posItem
						.getItemLevel2Price().toString(), AppConstants.C_CHANGE_ITEM_BILL_BASIC, "");

			}
			if (posItem.getItemLevel3Price().compareTo(BigDecimal.ZERO) > 0) {
				saveChangeItem(null, posItem.getItemNum().toString(), AppConstants.ITEM_REGULAR_PRICE3, "0.00", posItem
						.getItemLevel3Price().toString(), AppConstants.C_CHANGE_ITEM_BILL_BASIC, "");

			}
			if (posItem.getItemLevel4Price().compareTo(BigDecimal.ZERO) > 0) {
				saveChangeItem(null, posItem.getItemNum().toString(), AppConstants.ITEM_REGULAR_PRICE4, "0.00", posItem
						.getItemLevel4Price().toString(), AppConstants.C_CHANGE_ITEM_BILL_BASIC, "");

			}
			if (posItem.getItemCostPrice().compareTo(BigDecimal.ZERO) > 0) {
				saveChangeItem(null, posItem.getItemNum().toString(), AppConstants.ITEM_COST_PRICE, "0.00", posItem
						.getItemCostPrice().toString(), AppConstants.C_CHANGE_ITEM_BILL_BASIC, "");

			}
			if (posItem.getItemTransferPrice().compareTo(BigDecimal.ZERO) > 0) {
				saveChangeItem(null, posItem.getItemNum().toString(), AppConstants.ITEM_TRANSFER_PRICE, "0.00", posItem
						.getItemTransferPrice().toString(), AppConstants.C_CHANGE_ITEM_BILL_BASIC, "");

			}
			if (posItem.getItemWholesalePrice().compareTo(BigDecimal.ZERO) > 0) {
				saveChangeItem(null, posItem.getItemNum().toString(), AppConstants.ITEM_WHOLESALE_PRICE, "0.00",
						posItem.getItemWholesalePrice().toString(), AppConstants.C_CHANGE_ITEM_BILL_BASIC, "");

			}
			if (posItem.getItemMinPrice().compareTo(BigDecimal.ZERO) > 0) {
				saveChangeItem(null, posItem.getItemNum().toString(), AppConstants.ITEM_MIN_PRICE, "0.00", posItem
						.getItemMinPrice().toString(), AppConstants.C_CHANGE_ITEM_BILL_BASIC, "");

			}

			if (posItem.getItemLevel2Wholesale() != null
					&& posItem.getItemLevel2Wholesale().compareTo(BigDecimal.ZERO) > 0) {
				saveChangeItem(null, posItem.getItemNum().toString(), AppConstants.ITEM_WHOLESALE_PRICE2, "0.00",
						posItem.getItemLevel2Wholesale().toString(), AppConstants.C_CHANGE_ITEM_BILL_BASIC, "");

			}
			if (posItem.getItemLevel3Wholesale() != null
					&& posItem.getItemLevel3Wholesale().compareTo(BigDecimal.ZERO) > 0) {
				saveChangeItem(null, posItem.getItemNum().toString(), AppConstants.ITEM_WHOLESALE_PRICE3, "0.00",
						posItem.getItemLevel3Wholesale().toString(), AppConstants.C_CHANGE_ITEM_BILL_BASIC, "");

			}
			if (posItem.getItemLevel4Wholesale() != null
					&& posItem.getItemLevel4Wholesale().compareTo(BigDecimal.ZERO) > 0) {
				saveChangeItem(null, posItem.getItemNum().toString(), AppConstants.ITEM_WHOLESALE_PRICE4, "0.00",
						posItem.getItemLevel4Wholesale().toString(), AppConstants.C_CHANGE_ITEM_BILL_BASIC, "");

			}

		}

	}

	public static void comparePosItemGrade(PosItemGrade persisent, PosItemGrade posItemGrade, String billType,
			String billNo, AppUser appUser) {
		if (billType == null) {
			billType = AppConstants.C_CHANGE_ITEM_BILL_BASIC;
		}
		if (billNo == null) {
			billNo = "";
		}
		if (persisent != null) {

			if (posItemGrade.getItemGradeRegularPrice() == null) {
				posItemGrade.setItemGradeRegularPrice(BigDecimal.ZERO);
			}
			if (persisent.getItemGradeRegularPrice() == null) {
				persisent.setItemGradeRegularPrice(BigDecimal.ZERO);
			}
			if (posItemGrade.getItemGradeLevel2Price() == null) {
				posItemGrade.setItemGradeLevel2Price(BigDecimal.ZERO);
			}
			if (persisent.getItemGradeLevel2Price() == null) {
				persisent.setItemGradeLevel2Price(BigDecimal.ZERO);
			}
			if (posItemGrade.getItemGradeLevel3Price() == null) {
				posItemGrade.setItemGradeLevel3Price(BigDecimal.ZERO);
			}
			if (persisent.getItemGradeLevel3Price() == null) {
				persisent.setItemGradeLevel3Price(BigDecimal.ZERO);
			}
			if (posItemGrade.getItemGradeLevel4Price() == null) {
				posItemGrade.setItemGradeLevel4Price(BigDecimal.ZERO);
			}
			if (persisent.getItemGradeLevel4Price() == null) {
				persisent.setItemGradeLevel4Price(BigDecimal.ZERO);
			}

			if (persisent.getItemGradeRegularPrice().compareTo(posItemGrade.getItemGradeRegularPrice()) != 0) {
				saveChangeItem(null, posItemGrade.getItemNum() + "", AppConstants.CENTER_REGULAR_PRICE, persisent
						.getItemGradeRegularPrice().toString(), posItemGrade.getItemGradeRegularPrice().toString(),
						billType, billNo, persisent.getItemGradeNum(), appUser);
			}

			if (persisent.getItemGradeLevel2Price().compareTo(posItemGrade.getItemGradeLevel2Price()) != 0) {
				saveChangeItem(null, posItemGrade.getItemNum() + "", AppConstants.CENTER_REGULAR_PRICE2, persisent
						.getItemGradeLevel2Price().toString(), posItemGrade.getItemGradeLevel2Price().toString(),
						billType, billNo, persisent.getItemGradeNum(), appUser);
			}
			if (persisent.getItemGradeLevel3Price().compareTo(posItemGrade.getItemGradeLevel3Price()) != 0) {
				saveChangeItem(null, posItemGrade.getItemNum() + "", AppConstants.CENTER_REGULAR_PRICE3, persisent
						.getItemGradeLevel3Price().toString(), posItemGrade.getItemGradeLevel3Price().toString(),
						billType, billNo, persisent.getItemGradeNum(), appUser);
			}
			if (persisent.getItemGradeLevel4Price().compareTo(posItemGrade.getItemGradeLevel4Price()) != 0) {
				saveChangeItem(null, posItemGrade.getItemNum() + "", AppConstants.CENTER_REGULAR_PRICE4, persisent
						.getItemGradeLevel4Price().toString(), posItemGrade.getItemGradeLevel4Price().toString(),
						billType, billNo, persisent.getItemGradeNum(), appUser);
			}

		} else {

			if (posItemGrade.getItemGradeRegularPrice() == null) {
				posItemGrade.setItemGradeRegularPrice(BigDecimal.ZERO);
			}

			if (posItemGrade.getItemGradeLevel2Price() == null) {
				posItemGrade.setItemGradeLevel2Price(BigDecimal.ZERO);
			}
			if (posItemGrade.getItemGradeLevel3Price() == null) {
				posItemGrade.setItemGradeLevel3Price(BigDecimal.ZERO);
			}
			if (posItemGrade.getItemGradeLevel4Price() == null) {
				posItemGrade.setItemGradeLevel4Price(BigDecimal.ZERO);
			}
			if (posItemGrade.getItemGradeRegularPrice().compareTo(BigDecimal.ZERO) > 0) {
				saveChangeItem(null, posItemGrade.getItemNum() + "", AppConstants.CENTER_REGULAR_PRICE, "0.00",
						posItemGrade.getItemGradeRegularPrice().toString(), billType, billNo,
						posItemGrade.getItemGradeNum(), appUser);

			}
			if (posItemGrade.getItemGradeLevel2Price().compareTo(BigDecimal.ZERO) > 0) {
				saveChangeItem(null, posItemGrade.getItemNum() + "", AppConstants.CENTER_REGULAR_PRICE2, "0.00",
						posItemGrade.getItemGradeLevel2Price().toString(), billType, billNo,
						posItemGrade.getItemGradeNum(), appUser);

			}
			if (posItemGrade.getItemGradeLevel3Price().compareTo(BigDecimal.ZERO) > 0) {
				saveChangeItem(null, posItemGrade.getItemNum() + "", AppConstants.CENTER_REGULAR_PRICE3, "0.00",
						posItemGrade.getItemGradeLevel3Price().toString(), billType, billNo,
						posItemGrade.getItemGradeNum(), appUser);

			}
			if (posItemGrade.getItemGradeLevel4Price().compareTo(BigDecimal.ZERO) > 0) {
				saveChangeItem(null, posItemGrade.getItemNum() + "", AppConstants.CENTER_REGULAR_PRICE4, "0.00",
						posItemGrade.getItemGradeLevel4Price().toString(), billType, billNo,
						posItemGrade.getItemGradeNum(), appUser);

			}
		}

	}

	public static void compareBranchItemGrade(BranchItemGrade persisent, BranchItemGrade branchItemGrade,
			String billType, String billNo, AppUser appUser) {
		if (billType == null) {
			billType = AppConstants.C_CHANGE_ITEM_BILL_BRANCH;
		}
		if (billNo == null) {
			billNo = "";
		}
		if (persisent != null) {

			if (branchItemGrade.getItemGradeRegularPrice() == null) {
				branchItemGrade.setItemGradeRegularPrice(BigDecimal.ZERO);
			}
			if (persisent.getItemGradeRegularPrice() == null) {
				persisent.setItemGradeRegularPrice(BigDecimal.ZERO);
			}
			if (branchItemGrade.getItemGradeLevel2Price() == null) {
				branchItemGrade.setItemGradeLevel2Price(BigDecimal.ZERO);
			}
			if (persisent.getItemGradeLevel2Price() == null) {
				persisent.setItemGradeLevel2Price(BigDecimal.ZERO);
			}
			if (branchItemGrade.getItemGradeLevel3Price() == null) {
				branchItemGrade.setItemGradeLevel3Price(BigDecimal.ZERO);
			}
			if (persisent.getItemGradeLevel3Price() == null) {
				persisent.setItemGradeLevel3Price(BigDecimal.ZERO);
			}
			if (branchItemGrade.getItemGradeLevel4Price() == null) {
				branchItemGrade.setItemGradeLevel4Price(BigDecimal.ZERO);
			}
			if (persisent.getItemGradeLevel4Price() == null) {
				persisent.setItemGradeLevel4Price(BigDecimal.ZERO);
			}

			if (persisent.getItemGradeRegularPrice().compareTo(branchItemGrade.getItemGradeRegularPrice()) != 0) {
				saveChangeItem(branchItemGrade.getId().getBranchNum() + "", branchItemGrade.getId().getItemNum() + "",
						AppConstants.BRANCH_REGULAR_PRICE, persisent.getItemGradeRegularPrice().toString(),
						branchItemGrade.getItemGradeRegularPrice().toString(), billType, billNo, branchItemGrade
								.getId().getItemGradeNum(), appUser);
			}

			if (persisent.getItemGradeLevel2Price().compareTo(branchItemGrade.getItemGradeLevel2Price()) != 0) {
				saveChangeItem(branchItemGrade.getId().getBranchNum() + "", branchItemGrade.getId().getItemNum() + "",
						AppConstants.BRANCH_REGULAR_PRICE2, persisent.getItemGradeLevel2Price().toString(),
						branchItemGrade.getItemGradeLevel2Price().toString(), billType, billNo, branchItemGrade.getId()
								.getItemGradeNum(), appUser);
			}
			if (persisent.getItemGradeLevel3Price().compareTo(branchItemGrade.getItemGradeLevel3Price()) != 0) {
				saveChangeItem(branchItemGrade.getId().getBranchNum() + "", branchItemGrade.getId().getItemNum() + "",
						AppConstants.BRANCH_REGULAR_PRICE3, persisent.getItemGradeLevel3Price().toString(),
						branchItemGrade.getItemGradeLevel3Price().toString(), billType, billNo, branchItemGrade.getId()
								.getItemGradeNum(), appUser);
			}
			if (persisent.getItemGradeLevel4Price().compareTo(branchItemGrade.getItemGradeLevel4Price()) != 0) {
				saveChangeItem(branchItemGrade.getId().getBranchNum() + "", branchItemGrade.getId().getItemNum() + "",
						AppConstants.BRANCH_REGULAR_PRICE4, persisent.getItemGradeLevel4Price().toString(),
						branchItemGrade.getItemGradeLevel4Price().toString(), billType, billNo, branchItemGrade.getId()
								.getItemGradeNum(), appUser);
			}

		} else {

			if (branchItemGrade.getItemGradeRegularPrice() == null) {
				branchItemGrade.setItemGradeRegularPrice(BigDecimal.ZERO);
			}

			if (branchItemGrade.getItemGradeLevel2Price() == null) {
				branchItemGrade.setItemGradeLevel2Price(BigDecimal.ZERO);
			}
			if (branchItemGrade.getItemGradeLevel3Price() == null) {
				branchItemGrade.setItemGradeLevel3Price(BigDecimal.ZERO);
			}
			if (branchItemGrade.getItemGradeLevel4Price() == null) {
				branchItemGrade.setItemGradeLevel4Price(BigDecimal.ZERO);
			}
			if (branchItemGrade.getItemGradeRegularPrice().compareTo(BigDecimal.ZERO) > 0) {
				saveChangeItem(branchItemGrade.getId().getBranchNum() + "", branchItemGrade.getId().getItemNum() + "",
						AppConstants.BRANCH_REGULAR_PRICE, "0.00", branchItemGrade.getItemGradeRegularPrice()
								.toString(), billType, billNo, branchItemGrade.getId().getItemGradeNum(), appUser);

			}
			if (branchItemGrade.getItemGradeLevel2Price().compareTo(BigDecimal.ZERO) > 0) {
				saveChangeItem(branchItemGrade.getId().getBranchNum() + "", branchItemGrade.getId().getItemNum() + "",
						AppConstants.BRANCH_REGULAR_PRICE2, "0.00", branchItemGrade.getItemGradeLevel2Price()
								.toString(), billType, billNo, branchItemGrade.getId().getItemGradeNum(), appUser);

			}
			if (branchItemGrade.getItemGradeLevel3Price().compareTo(BigDecimal.ZERO) > 0) {
				saveChangeItem(branchItemGrade.getId().getBranchNum() + "", branchItemGrade.getId().getItemNum() + "",
						AppConstants.BRANCH_REGULAR_PRICE3, "0.00", branchItemGrade.getItemGradeLevel3Price()
								.toString(), billType, billNo, branchItemGrade.getId().getItemGradeNum(), appUser);

			}
			if (branchItemGrade.getItemGradeLevel4Price().compareTo(BigDecimal.ZERO) > 0) {
				saveChangeItem(branchItemGrade.getId().getBranchNum() + "", branchItemGrade.getId().getItemNum() + "",
						AppConstants.BRANCH_REGULAR_PRICE4, "0.00", branchItemGrade.getItemGradeLevel4Price()
								.toString(), billType, billNo, branchItemGrade.getId().getItemGradeNum(), appUser);

			}
		}

	}

	public static void compareStoreMatrix(StoreMatrix persisent, StoreMatrix storeMatrix, String billType,
			String billNo, Integer type) {
		
		compareStoreMatrix(persisent, storeMatrix, billType, billNo, type, null);
	}
	
	public static void compareStoreMatrix(StoreMatrix persisent, StoreMatrix storeMatrix, String billType,
			String billNo, Integer type, AppUser appUser) {
		if (billType == null) {
			billType = AppConstants.C_CHANGE_ITEM_BILL_BRANCH;
		}
		if (billNo == null) {
			billNo = "";
		}
		if (persisent != null) {
			if (type == 0) {
				if (storeMatrix.getStoreMatrixTransferPrice() == null) {
					storeMatrix.setStoreMatrixTransferPrice(BigDecimal.ZERO);
				}
				if (persisent.getStoreMatrixTransferPrice() == null) {
					persisent.setStoreMatrixTransferPrice(BigDecimal.ZERO);
				}
				if (persisent.getStoreMatrixTransferPrice().compareTo(storeMatrix.getStoreMatrixTransferPrice()) != 0) {
					saveChangeItem(storeMatrix.getId().getBranchNum().toString(), storeMatrix.getId().getItemNum()
							.toString(), AppConstants.BRANCH_TRANSFER_PRICE, persisent.getStoreMatrixTransferPrice()
							.toString(), storeMatrix.getStoreMatrixTransferPrice().toString(), billType, billNo,  appUser);
				}
			} else if (type == 2) {
				if (storeMatrix.getStoreMatrixCostPrice() == null) {
					storeMatrix.setStoreMatrixCostPrice(BigDecimal.ZERO);
				}
				if (persisent.getStoreMatrixCostPrice() == null) {
					persisent.setStoreMatrixCostPrice(BigDecimal.ZERO);
				}
				if (storeMatrix.getStoreMatrixRegularPrice() == null) {
					storeMatrix.setStoreMatrixRegularPrice(BigDecimal.ZERO);
				}
				if (persisent.getStoreMatrixRegularPrice() == null) {
					persisent.setStoreMatrixRegularPrice(BigDecimal.ZERO);
				}
				if (storeMatrix.getStoreMatrixLevel2Price() == null) {
					storeMatrix.setStoreMatrixLevel2Price(BigDecimal.ZERO);
				}
				if (persisent.getStoreMatrixLevel2Price() == null) {
					persisent.setStoreMatrixLevel2Price(BigDecimal.ZERO);
				}
				if (storeMatrix.getStoreMatrixLevel3Price() == null) {
					storeMatrix.setStoreMatrixLevel3Price(BigDecimal.ZERO);
				}
				if (persisent.getStoreMatrixLevel3Price() == null) {
					persisent.setStoreMatrixLevel3Price(BigDecimal.ZERO);
				}
				if (storeMatrix.getStoreMatrixLevel4Price() == null) {
					storeMatrix.setStoreMatrixLevel4Price(BigDecimal.ZERO);
				}
				if (persisent.getStoreMatrixLevel4Price() == null) {
					persisent.setStoreMatrixLevel4Price(BigDecimal.ZERO);
				}
				if (storeMatrix.getStoreMatrixMinPrice() == null) {
					storeMatrix.setStoreMatrixMinPrice(BigDecimal.ZERO);
				}
				if (persisent.getStoreMatrixMinPrice() == null) {
					persisent.setStoreMatrixMinPrice(BigDecimal.ZERO);
				}
				if (persisent.getStoreMatrixRegularPrice().compareTo(storeMatrix.getStoreMatrixRegularPrice()) != 0) {
					saveChangeItem(storeMatrix.getId().getBranchNum().toString(), storeMatrix.getId().getItemNum()
							.toString(), AppConstants.BRANCH_REGULAR_PRICE, persisent.getStoreMatrixRegularPrice()
							.toString(), storeMatrix.getStoreMatrixRegularPrice().toString(), billType, billNo, appUser);
				}
				if (persisent.getStoreMatrixLevel2Price().compareTo(storeMatrix.getStoreMatrixLevel2Price()) != 0) {
					saveChangeItem(storeMatrix.getId().getBranchNum().toString(), storeMatrix.getId().getItemNum()
							.toString(), AppConstants.BRANCH_REGULAR_PRICE2, persisent.getStoreMatrixLevel2Price()
							.toString(), storeMatrix.getStoreMatrixLevel2Price().toString(), billType, billNo, appUser);
				}
				if (persisent.getStoreMatrixLevel3Price().compareTo(storeMatrix.getStoreMatrixLevel3Price()) != 0) {
					saveChangeItem(storeMatrix.getId().getBranchNum().toString(), storeMatrix.getId().getItemNum()
							.toString(), AppConstants.BRANCH_REGULAR_PRICE3, persisent.getStoreMatrixLevel3Price()
							.toString(), storeMatrix.getStoreMatrixLevel3Price().toString(), billType, billNo);
				}
				if (persisent.getStoreMatrixLevel4Price().compareTo(storeMatrix.getStoreMatrixLevel4Price()) != 0) {
					saveChangeItem(storeMatrix.getId().getBranchNum().toString(), storeMatrix.getId().getItemNum()
							.toString(), AppConstants.BRANCH_REGULAR_PRICE4, persisent.getStoreMatrixLevel4Price()
							.toString(), storeMatrix.getStoreMatrixLevel4Price().toString(), billType, billNo, appUser);
				}
				if (persisent.getStoreMatrixMinPrice().compareTo(storeMatrix.getStoreMatrixMinPrice()) != 0) {
					saveChangeItem(storeMatrix.getId().getBranchNum().toString(), storeMatrix.getId().getItemNum()
							.toString(), AppConstants.BRANCH_MIN_PRICE, persisent.getStoreMatrixMinPrice().toString(),
							storeMatrix.getStoreMatrixMinPrice().toString(), billType, billNo, appUser);
				}

			}
		} else {
			if (type == 0) {
				if (storeMatrix.getStoreMatrixTransferPrice() == null) {
					storeMatrix.setStoreMatrixTransferPrice(BigDecimal.ZERO);
				}
				if (storeMatrix.getStoreMatrixTransferPrice().compareTo(BigDecimal.ZERO) > 0) {
					saveChangeItem(storeMatrix.getId().getBranchNum().toString(), storeMatrix.getId().getItemNum()
							.toString(), AppConstants.BRANCH_TRANSFER_PRICE, "0.00", storeMatrix
							.getStoreMatrixTransferPrice().toString(), billType, billNo, appUser);

				}

			} else if (type == 2) {
				if (storeMatrix.getStoreMatrixCostPrice() == null) {
					storeMatrix.setStoreMatrixCostPrice(BigDecimal.ZERO);
				}

				if (storeMatrix.getStoreMatrixRegularPrice() == null) {
					storeMatrix.setStoreMatrixRegularPrice(BigDecimal.ZERO);
				}

				if (storeMatrix.getStoreMatrixLevel2Price() == null) {
					storeMatrix.setStoreMatrixLevel2Price(BigDecimal.ZERO);
				}
				if (storeMatrix.getStoreMatrixLevel3Price() == null) {
					storeMatrix.setStoreMatrixLevel3Price(BigDecimal.ZERO);
				}

				if (storeMatrix.getStoreMatrixLevel4Price() == null) {
					storeMatrix.setStoreMatrixLevel4Price(BigDecimal.ZERO);
				}

				if (storeMatrix.getStoreMatrixMinPrice() == null) {
					storeMatrix.setStoreMatrixMinPrice(BigDecimal.ZERO);
				}
				if (storeMatrix.getStoreMatrixRegularPrice().compareTo(BigDecimal.ZERO) > 0) {
					saveChangeItem(storeMatrix.getId().getBranchNum().toString(), storeMatrix.getId().getItemNum()
							.toString(), AppConstants.BRANCH_REGULAR_PRICE, "0.00", storeMatrix
							.getStoreMatrixRegularPrice().toString(), billType, billNo, appUser);

				}
				if (storeMatrix.getStoreMatrixLevel2Price().compareTo(BigDecimal.ZERO) > 0) {
					saveChangeItem(storeMatrix.getId().getBranchNum().toString(), storeMatrix.getId().getItemNum()
							.toString(), AppConstants.BRANCH_REGULAR_PRICE2, "0.00", storeMatrix
							.getStoreMatrixLevel2Price().toString(), billType, billNo, appUser);

				}
				if (storeMatrix.getStoreMatrixLevel3Price().compareTo(BigDecimal.ZERO) > 0) {
					saveChangeItem(storeMatrix.getId().getBranchNum().toString(), storeMatrix.getId().getItemNum()
							.toString(), AppConstants.BRANCH_REGULAR_PRICE3, "0.00", storeMatrix
							.getStoreMatrixLevel3Price().toString(), billType, billNo, appUser);

				}
				if (storeMatrix.getStoreMatrixLevel4Price().compareTo(BigDecimal.ZERO) > 0) {
					saveChangeItem(storeMatrix.getId().getBranchNum().toString(), storeMatrix.getId().getItemNum()
							.toString(), AppConstants.BRANCH_REGULAR_PRICE4, "0.00", storeMatrix
							.getStoreMatrixLevel4Price().toString(), billType, billNo, appUser);

				}
				if (storeMatrix.getStoreMatrixMinPrice().compareTo(BigDecimal.ZERO) > 0) {
					saveChangeItem(storeMatrix.getId().getBranchNum().toString(), storeMatrix.getId().getItemNum()
							.toString(), AppConstants.BRANCH_MIN_PRICE, "0.00", storeMatrix.getStoreMatrixMinPrice()
							.toString(), billType, billNo, appUser);

				}

			}

		}

	}

	public static void compareStoreItemClient(StoreItemClient persisent, StoreItemClient storeItemClient) {
		if (persisent != null) {
			if (persisent.getStoreItemClientAgreementPrice().compareTo(
					storeItemClient.getStoreItemClientAgreementPrice()) != 0) {
				saveChangeItem(storeItemClient.getId().getClientFid(), storeItemClient.getId().getItemNum().toString(),
						AppConstants.CLIENT_AGREEMENT_PRICE, persisent.getStoreItemClientAgreementPrice().toString(),
						storeItemClient.getStoreItemClientAgreementPrice().toString(),
						AppConstants.C_CHANGE_ITEM_BILL_CLIENT, "");
			}

		} else {
			saveChangeItem(storeItemClient.getId().getClientFid(), storeItemClient.getId().getItemNum().toString(),
					AppConstants.CLIENT_AGREEMENT_PRICE, "0.00", storeItemClient.getStoreItemClientAgreementPrice()
							.toString(), AppConstants.C_CHANGE_ITEM_BILL_CLIENT, "");

		}

	}

	public static PosClient getPosClient(String clientFid, List<PosClient> posClients) {
		for (int i = 0; i < posClients.size(); i++) {
			if (posClients.get(i).getClientFid().equals(clientFid)) {
				return posClients.get(i);
			}
		}
		return null;
	}

	public static StoreMatrix getStoreMatrix(String systemBookCode, Integer branchNum, Integer itemNum,
			List<StoreMatrix> storeMatrixs) {
		for (int i = 0; i < storeMatrixs.size(); i++) {
			StoreMatrix storeMatrix = storeMatrixs.get(i);
			if (storeMatrix.getId().getSystemBookCode().equals(systemBookCode)
					&& storeMatrix.getId().getBranchNum().equals(branchNum)
					&& storeMatrix.getId().getItemNum().equals(itemNum)) {
				return storeMatrix;
			}
		}
		return null;
	}

	public static ItemMatrix getItemMatrix(List<ItemMatrix> itemMatrixs, Integer itemNum, Integer itemMatrixNum) {
		if (itemMatrixNum == null) {
			return null;
		}
		for (int i = 0; i < itemMatrixs.size(); i++) {
			ItemMatrix itemMatrix = itemMatrixs.get(i);
			if (itemMatrix.getId().getItemNum().equals(itemNum)
					&& itemMatrix.getId().getItemMatrixNum().equals(itemMatrixNum)) {
				return itemMatrix;
			}
		}
		return null;
	}

	public static List<Integer> getNormalPosOrderState() {
		List<Integer> stateCodes = new ArrayList<Integer>();
		stateCodes.add(5);
		stateCodes.add(7);
		return stateCodes;
	}

	public static boolean isNumber(String str) {
		for (int i = 0; i < str.length(); i++) {
			int chr = str.charAt(i);
			if (chr < 48 || chr > 57) {
				return false;
			}
		}
		return true;
	}

	public static String getTopCategoryCode(List<PosItemTypeParam> posItemTypeParams, String categoryCode) {
		boolean isSubCategory = true;
		String topCode = categoryCode;
		while (isSubCategory) {
			isSubCategory = false;
			for (int i = 0; i < posItemTypeParams.size(); i++) {
				PosItemTypeParam param = posItemTypeParams.get(i);
				if (param.getPosItemTypeCode().equals(topCode)) {
					if (StringUtils.isNotEmpty(param.getPosItemTypeFatherCode())
							&& !param.getPosItemTypeFatherCode().equals(param.getPosItemTypeCode())) {
						topCode = param.getPosItemTypeFatherCode();
						isSubCategory = true;
						break;
					} else {
						return param.getPosItemTypeCode();
					}
				}
			}
		}
		return topCode;
	}

	public static PosItemTypeParam getTopCategory(List<PosItemTypeParam> posItemTypeParams, String categoryCode) {
		boolean isSubCategory = true;
		String topCode = categoryCode;
		while (isSubCategory) {
			isSubCategory = false;
			for (int i = 0; i < posItemTypeParams.size(); i++) {
				PosItemTypeParam param = posItemTypeParams.get(i);
				if (param.getPosItemTypeCode().equals(topCode)) {
					if (StringUtils.isNotEmpty(param.getPosItemTypeFatherCode())
							&& !param.getPosItemTypeFatherCode().equals(param.getPosItemTypeCode())) {
						topCode = param.getPosItemTypeFatherCode();
						isSubCategory = true;
						break;
					} else {
						return param;
					}
				}
			}
		}
		return null;
	}

	public static void saveAppUserWebLog(AppUser appUser, String item, String action, String memo) {
		WebLog webLog = new WebLog();
		if (appUser == null) {
			return;
		}
		webLog.setWebLogItem(item);
		webLog.setWebLogAction(action);
		webLog.setWebLogMemo(memo);
		webLog.setAppUserNum(appUser.getAppUserNum());
		webLog.setWebLogCode(appUser.getAppUserCode());
		webLog.setWebLogName(appUser.getAppUserName());
		webLog.setSystemBookCode(appUser.getSystemBookCode());
		webLog.setWebLogIp(appUser.getAppUserIp());
		webLog.setWebLogTime(Calendar.getInstance().getTime());
		webLog.setWebLogSystem(appUser.getAppUserSystem());
		if (appUser.getBranchs().size() > 0) {
			webLog.setBranchNum(appUser.getBranchs().get(0).getId().getBranchNum());

		} else if(appUser.getBranchNum() != null){
			webLog.setBranchNum(appUser.getBranchNum());
		}


		webLogService.save(webLog);
	}

	public static void saveSupplierUserWebLog(SupplierUser supplierUser, String item, String action, String memo) {
		WebLog webLog = new WebLog();
		webLog.setWebLogItem(item);
		webLog.setWebLogAction(action);
		webLog.setWebLogMemo(memo);
		webLog.setSupplierUserNum(supplierUser.getSupplierUserNum());
		webLog.setWebLogCode(supplierUser.getSupplierUserCode());
		webLog.setWebLogName(supplierUser.getSupplierUserName());
		webLog.setSystemBookCode(supplierUser.getSystemBookCode());
		webLog.setWebLogIp(null);
		if (supplierUser.getSupplier() != null) {
			webLog.setBranchNum(supplierUser.getSupplier().getBranchNum());
		}
		webLogService.save(webLog);
	}

	public static void saveWholesaleUserWebLog(WholesaleUser wholesaleUser, String item, String action, String memo) {
		WebLog webLog = new WebLog();
		webLog.setWebLogItem(item);
		webLog.setWebLogAction(action);
		webLog.setWebLogMemo(memo);
		webLog.setWholesaleUserId(wholesaleUser.getWholesaleUserId());
		webLog.setWebLogCode(wholesaleUser.getWholesaleUserCode());
		webLog.setWebLogName(wholesaleUser.getWholesaleUserName());
		webLog.setSystemBookCode(wholesaleUser.getSystemBookCode());
		webLog.setWebLogIp(CurrentThread.getSessionHolder().getRemoteAddr());
		if (wholesaleUser.getPosClient() != null) {
			webLog.setBranchNum(wholesaleUser.getPosClient().getBranchNum());
		}
		webLogService.save(webLog);
	}

	public static String getIpAddress(String ip) {
		try {
			URL url = new URL("http://ip.taobao.com/service/getIpInfo.php?ip=" + ip);
			InputStreamReader isr = new InputStreamReader(url.openStream());
			BufferedReader br = new BufferedReader(isr);
			StringBuffer sb = new StringBuffer();

			while (br.ready()) {
				sb.append(br.readLine());
			}
			br.close();
			isr.close();
			JSONObject jsonobj = new JSONObject(sb.toString());
			String data = jsonobj.getString("data");
			jsonobj = new JSONObject(data);
			String address = jsonobj.getString("country") + jsonobj.getString("region") + jsonobj.getString("city");
			if (address.equals("未分配或者内网IP")) {
				return "";
			}
			return address;
		} catch (Exception e) {
			return "";
		}
	}

	public static List<PreSmsSend> getOrderNoticeSms(String systemBookCode, Integer branchNum, String numbers,
			String customer, String context, String group) {
		List<PreSmsSend> smsSends = new ArrayList<PreSmsSend>();
		if (numbers == null) {
			return smsSends;
		}
		String[] phones = numbers.split(",");
		for (int i = 0; i < phones.length; i++) {
			String phone = phones[i];
			if (!isValidMobilePhoneNumber(phone)) {
				continue;
			}
			PreSmsSend smsSend = new PreSmsSend();
			smsSend.setSystemBookCode(systemBookCode);
			smsSend.setBranchNum(branchNum);
			smsSend.setSmsSendNumber(phone);
			smsSend.setSmsSendCustomer(customer);
			smsSend.setSmsSendContext(context);
			smsSend.setSmsSendGroup(group);
			smsSend.setSmsSendState(AppConstants.SMS_SEND_STATE_SENDING);
			smsSend.setSmsSendOperator("");
			smsSends.add(smsSend);
		}
		return smsSends;
	}

	public static String getMatrixName(ItemMatrix itemMatrix) {
		if (itemMatrix == null) {
			return "";
		}
		String str = "(";
		if (StringUtils.isNotEmpty(itemMatrix.getItemMatrix01())) {
			str = str + itemMatrix.getItemMatrix01();
		}
		if (StringUtils.isNotEmpty(itemMatrix.getItemMatrix02())) {
			str = str + "|" + itemMatrix.getItemMatrix02();
		}
		if (StringUtils.isNotEmpty(itemMatrix.getItemMatrix03())) {
			str = str + "|" + itemMatrix.getItemMatrix03();
		}
		if (StringUtils.isNotEmpty(itemMatrix.getItemMatrix04())) {
			str = str + "|" + itemMatrix.getItemMatrix04();
		}
		if (StringUtils.isNotEmpty(itemMatrix.getItemMatrix05())) {
			str = str + "|" + itemMatrix.getItemMatrix05();
		}
		if (StringUtils.isNotEmpty(itemMatrix.getItemMatrix06())) {
			str = str + itemMatrix.getItemMatrix06();
		}
		str = str + ")";
		return str;
	}

	public static Inventory getInventory(List<Inventory> inventories, Integer itemNum) {
		if(inventories == null){
			return null;
		}
		for (int i = 0, len = inventories.size(); i < len; i++) {
			Inventory inventory = inventories.get(i);
			if (inventory.getItemNum().equals(itemNum)) {
				return inventory;
			}
		}
		return null;
	}
	
	public static Inventory getInventory(List<Inventory> inventories, Integer itemNum, Integer storehouseNum) {
		for (int i = 0, len = inventories.size(); i < len; i++) {
			Inventory inventory = inventories.get(i);
			if (inventory.getItemNum().equals(itemNum) && storehouseNum.equals(inventory.getId().getStorehouseNum())) {
				return inventory;
			}
		}
		return null;
	}

	public static BigDecimal getItemCommission(SaleCommission saleCommission, BigDecimal qty, BigDecimal saleMoney,
			BigDecimal price, BigDecimal cost) {
		BigDecimal commission = BigDecimal.ZERO;
		if (saleCommission == null) {
			return commission;
		}
		if (saleCommission.getCommissionType().equals(AppConstants.POS_ITEM_COMMISSION_TPYE_NONE)) {
			return commission;
		} else if (saleCommission.getCommissionType().equals(AppConstants.POS_ITEM_COMMISSION_TPYE_FIX)) {
			return saleCommission.getCommissionMoney().multiply(qty);
		} else if (saleCommission.getCommissionType().equals(AppConstants.POS_ITEM_COMMISSION_TPYE_SALE_MONEY)) {
			return saleMoney.multiply(saleCommission.getCommissionMoney());
		} else if (saleCommission.getCommissionType().equals(AppConstants.POS_ITEM_COMMISSION_TPYE_PROFIT)) {
			if (saleCommission.getCommissionBase() == null) {
				saleCommission.setCommissionBase(BigDecimal.ZERO);
			}
			if (saleCommission.getCommissionBase().compareTo(BigDecimal.ZERO) != 0) {
				return (price.subtract(saleCommission.getCommissionBase())).multiply(
						saleCommission.getCommissionMoney()).multiply(qty);

			} else {
				return saleMoney.subtract(qty.multiply(cost)).multiply(saleCommission.getCommissionMoney());
			}
		}
		return commission;
	}

	public static String getCardUserLogMemo(CardUserLog cardUserLog, Object object) {
		StringBuffer sb = new StringBuffer();
		if (object instanceof CardConsume) {
			CardConsume cardConsume = (CardConsume) object;
			sb.append("【").append("时间:" + DateUtil.getLongDateTimeStr(cardConsume.getConsumeDate()))
					.append("  分店:" + cardConsume.getConsumeBranchName())
					.append("  消费前金额:" + cardConsume.getConsumeBalance().setScale(2, BigDecimal.ROUND_HALF_UP))
					.append("  消费金额:" + cardConsume.getConsumeMoney().setScale(2, BigDecimal.ROUND_HALF_UP))
					.append("  消费次数:" + cardConsume.getConsumeCount()).append("】");
			return cardUserLog.getCardUserLogMemo().concat(sb.toString());
		} else if (object instanceof CardDeposit) {
			CardDeposit cardDeposit = (CardDeposit) object;
			sb.append("【").append("时间:" + DateUtil.getLongDateTimeStr(cardDeposit.getDepositDate()))
					.append("  分店:" + cardDeposit.getDepositBranchName())
					.append("  存款前金额:" + cardDeposit.getDepositBalance().setScale(2, BigDecimal.ROUND_HALF_UP))
					.append("  付款金额:" + cardDeposit.getDepositCash().setScale(2, BigDecimal.ROUND_HALF_UP))
					.append("  存款金额:" + cardDeposit.getDepositMoney().setScale(2, BigDecimal.ROUND_HALF_UP))
					.append("  存款次数:" + cardDeposit.getDepositCount())
					.append("  支付方式:" + cardDeposit.getDepositPaymentTypeName()).append("】");
		} else {
			ClientPoint clientPoint = (ClientPoint) object;
			sb.append("【").append("时间:" + DateUtil.getLongDateTimeStr(clientPoint.getClientPointDate()))
					.append("  分店:" + clientPoint.getClientPointBranchName())
					.append("  积分值:" + clientPoint.getClientPointBalance().setScale(2, BigDecimal.ROUND_HALF_UP))
					.append("】");
		}
		return cardUserLog.getCardUserLogMemo().concat(sb.toString());
	}

	public static String getIntegerParmeList(List<Integer> list) {
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		for (int i = 0; i < list.size(); i++) {
			if (i == list.size() - 1) {
				sb.append(list.get(i));
			} else {
				sb.append(list.get(i) + ",");
			}

		}
		sb.append(") ");
		return sb.toString();
	}

	public static String getShortParmeList(List<Short> list) {
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		for (int i = 0; i < list.size(); i++) {
			if (i == list.size() - 1) {
				sb.append(list.get(i));
			} else {
				sb.append(list.get(i) + ",");
			}

		}
		sb.append(") ");
		return sb.toString();
	}

	public static String getStringParmeList(List<String> list) {
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		for (int i = 0; i < list.size(); i++) {
			if (i == list.size() - 1) {
				sb.append("'" + list.get(i) + "'");
			} else {
				sb.append("'" + list.get(i) + "',");
			}

		}
		sb.append(") ");
		return sb.toString();
	}

	public static String getStringParmeArray(String[] array) {
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		for (int i = 0; i < array.length; i++) {
			if (i == array.length - 1) {
				sb.append("'" + array[i] + "'");
			} else {
				sb.append("'" + array[i] + "',");
			}

		}
		sb.append(") ");
		return sb.toString();
	}

	public static List<Integer> findSubRegions(List<Region> regions, Integer regionNum) {
		Set<Integer> set = new HashSet<Integer>();
		Set<Integer> subSet = new HashSet<Integer>();
		Set<Integer> compareSet = new HashSet<Integer>();
		compareSet.add(regionNum);
		while (compareSet.size() != 0) {
			subSet.clear();
			for (int i = 0; i < regions.size(); i++) {
				Region region = regions.get(i);
				if (region.getRegionParentNum() != null && compareSet.contains(region.getRegionParentNum())) {
					subSet.add(region.getRegionNum());
				}
			}
			Iterator<Integer> iterator = subSet.iterator();
			while (iterator.hasNext()) {
				Integer num = iterator.next();
				if (set.contains(num)) {
					subSet.remove(num);
				}
			}
			compareSet = new HashSet<Integer>(subSet);
			set.addAll(subSet);
		}
		return new ArrayList<Integer>(set);
	}

	public static List<String> findSubCategory(List<PosItemTypeParam> posItemTypeParams, String categoryCode) {
		Set<String> set = new HashSet<String>();
		Set<String> subSet = new HashSet<String>();
		Set<String> compareSet = new HashSet<String>();
		compareSet.add(categoryCode);
		set.add(categoryCode);
		while (compareSet.size() != 0) {
			subSet.clear();
			for (int i = 0; i < posItemTypeParams.size(); i++) {
				PosItemTypeParam posItemTypeParam = posItemTypeParams.get(i);
				if (posItemTypeParam.getPosItemTypeFatherCode() != null
						&& compareSet.contains(posItemTypeParam.getPosItemTypeFatherCode())) {
					subSet.add(posItemTypeParam.getPosItemTypeCode());
				}
			}
			Iterator<String> iterator = subSet.iterator();
			while (iterator.hasNext()) {
				String code = iterator.next();
				if (set.contains(code)) {
					subSet.remove(code);
				}
			}
			compareSet = new HashSet<String>(subSet);
			set.addAll(subSet);
		}
		return new ArrayList<String>(set);
	}

	public static File createFile(String fileName, String filePath, byte[] b) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(filePath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			file = new File(filePath + fileSeparator + fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(b);
			bos.close();
			fos.close();
			return file;
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return null;
	}

	public static String getReportPath() {
		return reportPath;
	}

	public static String getFilePath() {
		return filePath;
	}

	public static String getExcelPath() {
		return excelPath;
	}

	public static String getPdfPath() {
		return pdfPath;
	}

	public static String getUtilPath() {
		return utilPath;
	}

	public static void setFilePath(String filePath) {
		AppUtil.filePath = filePath;
		AppUtil.reportPath = filePath + "report" + fileSeparator;
		AppUtil.excelPath = filePath + "excel" + fileSeparator;
		AppUtil.pdfPath = filePath + "pdf" + fileSeparator;
		AppUtil.utilPath = filePath + "util" + fileSeparator;
	}

	public static boolean isValidEmail(String email) {
		if (StringUtils.isEmpty(email)) {
			return false;
		}
		Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher m = p.matcher(email);
		return m.matches();
	}

	public static String generateVelocityContext(List datas, String vmName, Map params) {
		// 创建引擎
		VelocityEngine ve = new VelocityEngine();
		// 设置模板加载路径，这里设置的是class下
		ve.setProperty(Velocity.RESOURCE_LOADER, "class");
		ve.setProperty("class.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		try {
			// 进行初始化操作
			ve.init();
			// 加载模板，设定模板编码
			Template t = ve.getTemplate("com/nhsoft/pos3/server/report/vm/" + vmName, "utf-8");
			// 设置初始化数据
			VelocityContext context = new VelocityContext();
			context.put("datas", datas);

			if (params != null) {
				Iterator iterator = params.keySet().iterator();
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					context.put(key, params.get(key));
				}
			}

			// 设置输出
			StringWriter writer = new StringWriter();
			// 将环境数据转化输出 -
			t.merge(context, writer);
			// 简化操作
			// ve.mergeTemplate("qiailin.vm", "gbk", context, writer );
			return writer.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static byte[] getBytes(File file) {
		byte[] buffer = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}
	
	public static Object[] getInventoryAmount(Inventory inventory, PosItem posItem, Integer itemMatrixNum,
			String lotNumber, Branch branch) {
		BigDecimal amount = BigDecimal.ZERO;
		BigDecimal money = BigDecimal.ZERO;
		BigDecimal assistAmount = BigDecimal.ZERO;
		BigDecimal cost = BigDecimal.ZERO;
		if(inventory != null){
			
			if (posItem.getItemCostMode(branch).equals(AppConstants.C_ITEM_COST_MODE_MANUAL)) {
				List<InventoryLnDetail> inventoryLnDetails = inventory.getInventoryLnDetails();
				for (int j = 0; j < inventoryLnDetails.size(); j++) {
					InventoryLnDetail inventoryLnDetail = inventoryLnDetails.get(j);
					if (StringUtils.isNotEmpty(lotNumber)) {
						if (!lotNumber.equals(inventoryLnDetail.getInventoryLnDetailLotNumber())) {
							continue;
						}
					}
					if (itemMatrixNum != null && itemMatrixNum != 0) {
						if (inventoryLnDetail.getItemMatrixNum() != null
								&& !itemMatrixNum.equals(inventoryLnDetail.getItemMatrixNum())) {
							continue;
						}
					}
					amount = amount.add(inventoryLnDetail.getInventoryLnDetailAmount());
					money = money.add(inventoryLnDetail.getInventoryLnDetailAmount().multiply(
							inventoryLnDetail.getInventoryLnDetailCostPrice()));
					assistAmount = assistAmount.add(inventoryLnDetail.getInventoryLnDetailAssistAmount());
					cost = inventoryLnDetail.getInventoryLnDetailCostPrice();
				}
			} else if (posItem.getItemCostMode(branch).equals(AppConstants.C_ITEM_COST_MODE_FIFO)) {
				List<InventoryBatchDetail> inventoryBatchDetails = inventory.getInventoryBatchDetails();
				for (int j = 0; j < inventoryBatchDetails.size(); j++) {
					InventoryBatchDetail inventoryBatchDetail = inventoryBatchDetails.get(j);
					if (itemMatrixNum != null && itemMatrixNum != 0) {
						if (inventoryBatchDetail.getItemMatrixNum() != null
								&& !itemMatrixNum.equals(inventoryBatchDetail.getItemMatrixNum())) {
							continue;
						}
					}
					amount = amount.add(inventoryBatchDetail.getInventoryBatchDetailAmount());
					money = money.add(inventoryBatchDetail.getInventoryBatchDetailAmount().multiply(
							inventoryBatchDetail.getInventoryBatchDetailCostPrice()));
					assistAmount = assistAmount.add(inventoryBatchDetail.getInventoryBatchDetailAssistAmount());
				}
			} else if (posItem.getItemType() == AppConstants.C_ITEM_TYPE_MATRIX) {
				List<InventoryMatrix> inventoryMatrixs = inventory.getInventoryMatrixs();
				for (int j = 0; j < inventoryMatrixs.size(); j++) {
					InventoryMatrix inventoryMatrix = inventoryMatrixs.get(j);
					if (itemMatrixNum != null && itemMatrixNum != 0) {
						if (inventoryMatrix.getId().getItemMatrixNum() != null
								&& !itemMatrixNum.equals(inventoryMatrix.getId().getItemMatrixNum())) {
							continue;
						}
					}
					amount = amount.add(inventoryMatrix.getInventoryMatrixAmount());
					assistAmount = assistAmount.add(inventoryMatrix.getInventoryMatrixAssistAmount());
				}
			} else {
				amount = amount.add(inventory.getInventoryAmount());
				money = money.add(inventory.getInventoryMoney());
				assistAmount = assistAmount.add(inventory.getInventoryAssistAmount());
			}
		}
			
		
		if (!posItem.getItemCostMode(branch).equals(AppConstants.C_ITEM_COST_MODE_MANUAL)
				|| StringUtils.isNotEmpty(lotNumber)) {
			if (amount.compareTo(BigDecimal.ZERO) != 0) {
				cost = money.divide(amount, 4, BigDecimal.ROUND_HALF_UP);
			} else {
				if(branch.isJoinBranch()){
					cost = posItem.getItemTransferPrice();

				} else {
					cost = posItem.getItemCostPrice();
					
				}
			}
		}
		Object[] obj = new Object[5];
		obj[0] = amount;
		obj[1] = assistAmount;
		obj[2] = money;
		obj[3] = cost;
		return obj;
	}

	public static Object[] getInventoryAmount(List<Inventory> inventories, PosItem posItem, Integer itemMatrixNum,
			String lotNumber, Branch branch) {
		BigDecimal amount = BigDecimal.ZERO;
		BigDecimal money = BigDecimal.ZERO;
		BigDecimal assistAmount = BigDecimal.ZERO;
		BigDecimal cost = BigDecimal.ZERO;
		boolean found = false;
		for (int i = 0; i < inventories.size(); i++) {
			Inventory inventory = inventories.get(i);
			if (inventory.getItemNum().equals(posItem.getItemNum())) {
				found = true;
				if (posItem.getItemCostMode(branch).equals(AppConstants.C_ITEM_COST_MODE_MANUAL)) {
					List<InventoryLnDetail> inventoryLnDetails = inventory.getInventoryLnDetails();
					for (int j = 0; j < inventoryLnDetails.size(); j++) {
						InventoryLnDetail inventoryLnDetail = inventoryLnDetails.get(j);
						if (StringUtils.isNotEmpty(lotNumber)) {
							if (!lotNumber.equals(inventoryLnDetail.getInventoryLnDetailLotNumber())) {
								continue;
							}
						}
						if (itemMatrixNum != null && itemMatrixNum != 0) {
							if (inventoryLnDetail.getItemMatrixNum() != null
									&& !itemMatrixNum.equals(inventoryLnDetail.getItemMatrixNum())) {
								continue;
							}
						}
						amount = amount.add(inventoryLnDetail.getInventoryLnDetailAmount());
						money = money.add(inventoryLnDetail.getInventoryLnDetailAmount().multiply(
								inventoryLnDetail.getInventoryLnDetailCostPrice()));
						assistAmount = assistAmount.add(inventoryLnDetail.getInventoryLnDetailAssistAmount());
						cost = inventoryLnDetail.getInventoryLnDetailCostPrice();
					}
				} else if (posItem.getItemCostMode(branch).equals(AppConstants.C_ITEM_COST_MODE_FIFO)) {
					List<InventoryBatchDetail> inventoryBatchDetails = inventory.getInventoryBatchDetails();
					for (int j = 0; j < inventoryBatchDetails.size(); j++) {
						InventoryBatchDetail inventoryBatchDetail = inventoryBatchDetails.get(j);
						if (itemMatrixNum != null && itemMatrixNum != 0) {
							if (inventoryBatchDetail.getItemMatrixNum() != null
									&& !itemMatrixNum.equals(inventoryBatchDetail.getItemMatrixNum())) {
								continue;
							}
						}
						amount = amount.add(inventoryBatchDetail.getInventoryBatchDetailAmount());
						money = money.add(inventoryBatchDetail.getInventoryBatchDetailAmount().multiply(
								inventoryBatchDetail.getInventoryBatchDetailCostPrice()));
						assistAmount = assistAmount.add(inventoryBatchDetail.getInventoryBatchDetailAssistAmount());
					}
				} else if (posItem.getItemType() == AppConstants.C_ITEM_TYPE_MATRIX) {
					List<InventoryMatrix> inventoryMatrixs = inventory.getInventoryMatrixs();
					for (int j = 0; j < inventoryMatrixs.size(); j++) {
						InventoryMatrix inventoryMatrix = inventoryMatrixs.get(j);
						if (itemMatrixNum != null && itemMatrixNum != 0) {
							if (inventoryMatrix.getId().getItemMatrixNum() != null
									&& !itemMatrixNum.equals(inventoryMatrix.getId().getItemMatrixNum())) {
								continue;
							}
						}
						amount = amount.add(inventoryMatrix.getInventoryMatrixAmount());
						assistAmount = assistAmount.add(inventoryMatrix.getInventoryMatrixAssistAmount());
					}
				} else {
					amount = amount.add(inventory.getInventoryAmount());
					money = money.add(inventory.getInventoryMoney());
					assistAmount = assistAmount.add(inventory.getInventoryAssistAmount());
				}
			}
		}
		if (!posItem.getItemCostMode(branch).equals(AppConstants.C_ITEM_COST_MODE_MANUAL)
				|| StringUtils.isNotEmpty(lotNumber)) {
			if (amount.compareTo(BigDecimal.ZERO) > 0) {
				cost = money.divide(amount, 4, BigDecimal.ROUND_HALF_UP);
			}
		}
		Object[] obj = new Object[5];
		obj[0] = amount;
		obj[1] = assistAmount;
		obj[2] = money;
		obj[3] = cost;
		obj[4] = found;
		return obj;
	}

	public static Object[] getInventoryAmount(Inventory inventory, Integer itemNum, String lotNumber,
			Integer itemMatrixNum) {
		Object[] objects = new Object[3];
		BigDecimal qty = BigDecimal.ZERO;
		BigDecimal assistQty = BigDecimal.ZERO;
		BigDecimal cost = BigDecimal.ZERO;
		if (inventory != null) {
			if (inventory.getInventoryAmount().compareTo(BigDecimal.ZERO) != 0) {

				cost = inventory.getInventoryMoney()
						.divide(inventory.getInventoryAmount(), 4, BigDecimal.ROUND_HALF_UP);
			}
			List<InventoryMatrix> inventoryMatrixs = inventory.getInventoryMatrixs();
			List<InventoryBatchDetail> inventoryBatchDetails = inventory.getInventoryBatchDetails();
			List<InventoryLnDetail> inventoryLnDetails = inventory.getInventoryLnDetails();
			if (inventoryMatrixs.size() > 0) {
				for (int i = 0; i < inventoryMatrixs.size(); i++) {
					InventoryMatrix inventoryMatrix = inventoryMatrixs.get(i);
					if (itemMatrixNum != null && itemMatrixNum != 0) {
						if (!itemMatrixNum.equals(inventoryMatrix.getId().getItemMatrixNum())) {
							continue;
						}
					}
					qty = qty.add(inventoryMatrix.getInventoryMatrixAmount());
					assistQty = assistQty.add(inventoryMatrix.getInventoryMatrixAssistAmount());

				}
			} else if (inventoryBatchDetails.size() > 0) {
				for (int i = 0; i < inventoryBatchDetails.size(); i++) {
					InventoryBatchDetail inventoryBatchDetail = inventoryBatchDetails.get(i);
					if (itemMatrixNum != null && itemMatrixNum != 0) {
						if (inventoryBatchDetail.getItemMatrixNum() != null
								&& !itemMatrixNum.equals(inventoryBatchDetail.getItemMatrixNum())) {
							continue;
						}
					}
					qty = qty.add(inventoryBatchDetail.getInventoryBatchDetailAmount());
					assistQty = assistQty.add(inventoryBatchDetail.getInventoryBatchDetailAssistAmount());

				}
			} else if (inventoryLnDetails.size() > 0) {
				for (int i = 0; i < inventoryLnDetails.size(); i++) {
					InventoryLnDetail inventoryLnDetail = inventoryLnDetails.get(i);
					if (itemMatrixNum != null && itemMatrixNum != 0) {
						if (inventoryLnDetail.getItemMatrixNum() != null
								&& !itemMatrixNum.equals(inventoryLnDetail.getItemMatrixNum())) {
							continue;
						}
					}
					if (lotNumber != null) {
						if (!StringUtils.equals(lotNumber, inventoryLnDetail.getInventoryLnDetailLotNumber())) {
							continue;
						}
						cost = inventoryLnDetail.getInventoryLnDetailCostPrice();
					}
					qty = qty.add(inventoryLnDetail.getInventoryLnDetailAmount());
					assistQty = assistQty.add(inventoryLnDetail.getInventoryLnDetailAssistAmount());
				}
			} else {
				qty = inventory.getInventoryAmount();
				assistQty = inventory.getInventoryAssistAmount();
			}
		}
		objects[0] = qty.setScale(4, BigDecimal.ROUND_HALF_UP);
		;
		objects[1] = assistQty.setScale(4, BigDecimal.ROUND_HALF_UP);
		;
		objects[2] = cost.setScale(4, BigDecimal.ROUND_HALF_UP);
		return objects;
	}

	public static Storehouse getStorehouse(List<Storehouse> storehouses, Integer storehouseNum) {
		for (int i = 0; i < storehouses.size(); i++) {
			Storehouse storehouse = storehouses.get(i);
			if (storehouse.getStorehouseNum().equals(storehouseNum)) {
				return storehouse;
			}
		}
		return null;
	}

	public static List<Storehouse> findStorehouses(List<Storehouse> storehouses, Integer branchNum, boolean isCenter) {
		List<Storehouse> list = new ArrayList<Storehouse>();
		for (int i = 0; i < storehouses.size(); i++) {
			Storehouse storehouse = storehouses.get(i);
			List<Branch> branchs = storehouse.getBranchs();
			if (branchs.size() == 0) {
				continue;
			}
			Branch branch = branchs.get(0);
			if (branch.getId().getBranchNum().equals(branchNum)) {
				if (isCenter) {
					if (storehouse.getStorehouseCenterTag() != null && storehouse.getStorehouseCenterTag()) {
						list.add(storehouse);
					}
				} else {
					list.add(storehouse);
				}
			}
		}
		return list;
	}

	public static List<String> findAllModules() {
		List<String> list = new ArrayList<String>();
		list.add(AppConstants.C_AMA_MODULE_BASIC);
		list.add(AppConstants.C_AMA_MODULE_POS);
		list.add(AppConstants.C_AMA_MODULE_PURCHASE);
		list.add(AppConstants.C_AMA_MODULE_WHOLESALE);
		list.add(AppConstants.C_AMA_MODULE_RETAIL);
		list.add(AppConstants.C_AMA_MODULE_INVNETORY);
		list.add(AppConstants.C_AMA_MODULE_PROMOTION);
		list.add(AppConstants.C_AMA_MODULE_SETTLEMENT);
		list.add(AppConstants.C_AMA_MODULE_CHAIN);
		list.add(AppConstants.C_AMA_MODULE_DECISION);
		list.add(AppConstants.C_AMA_MODULE_CRM);
		list.add(AppConstants.C_AMA_MODULE_SMS);
		list.add(AppConstants.C_AMA_MODULE_SYSTEM);
		list.add(AppConstants.C_AMA_MODULE_WEB);
		list.add(AppConstants.C_AMA_MODULE_MTSCALE);
		list.add(AppConstants.C_AMA_MODULE_MARKETACTION);
		return list;
	}

	private static void addPrivilege(String privilegeResourceCategory, String PrivilegeResourceName,
			List<PrivilegeResource> privilegeResources, List<PrivilegeResource> addPrivilegeResources) {
		for (int i = 0; i < privilegeResources.size(); i++) {
			PrivilegeResource privilegeResource = privilegeResources.get(i);
			if (privilegeResource.getPrivilegeResourceName().equals(PrivilegeResourceName)) {
				addPrivilegeResources.add(privilegeResource);
			}
		}
	}

	public static List<PrivilegeResource> getLemengCashierPrivileges(List<PrivilegeResource> privilegeResources) {
		List<PrivilegeResource> list = new ArrayList<PrivilegeResource>();
		for (int i = 0; i < privilegeResources.size(); i++) {
			PrivilegeResource privilegeResource = privilegeResources.get(i);
			if (!privilegeResource.getPrivilegeResourceType()
					.equals(PrivilegeConstants.C_PRIVILEGE_REOUSRCE_WEB_LEMENG)) {
				continue;
			}
			if (!privilegeResource.getPrivilegeResourceCategory().equals(LemengPrivilegeConstants.PRIVILEGE_POSSALE)) {
				continue;
			}
			if (privilegeResource.getPrivilegeResourceName().equals(LemengPrivilegeConstants.PRIVILEGE_POS_VIEW_SHIFT)) {
				continue;
			}
			if (privilegeResource.getPrivilegeResourceName().equals(LemengPrivilegeConstants.PRIVILEGE_POS_ANTI_PAY)) {
				continue;
			}
			if (privilegeResource.getPrivilegeResourceName().equals(LemengPrivilegeConstants.PRIVILEGE_POS_RETRUEN)) {
				continue;
			}
			if (privilegeResource.getPrivilegeResourceName().equals(LemengPrivilegeConstants.PRIVILEGE_POS_PRESENT)) {
				continue;
			}
			if (privilegeResource.getPrivilegeResourceName().equals(LemengPrivilegeConstants.PRIVILEGE_CARD_REDEPOSIT)) {
				continue;
			}
			list.add(privilegeResource);
		}
		return list;
	}

	public static List<PrivilegeResource> getCashierPrivileges(List<PrivilegeResource> privilegeResources) {
		List<PrivilegeResource> list = new ArrayList<PrivilegeResource>();
		// 前台销售
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_POS_SALE, PrivilegeConstants.PRIVILEGE_PRINT_SHIFT_TABLE_REPORT,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_POS_SALE, PrivilegeConstants.PRIVILEGE_POS_SALE,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_POS_SALE, PrivilegeConstants.PRIVILEGE_POS_ANTI_PAY,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_POS_SALE, PrivilegeConstants.PRIVILEGE_POS_RE_PRINT,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_POS_SALE, PrivilegeConstants.PRIVILEGE_POS_OPEN_DRAW,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_POS_SALE, PrivilegeConstants.PRIVILEGE_POS_RETRUEN,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_POS_SALE, PrivilegeConstants.PRIVILEGE_POS_DELETE,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_POS_SALE, PrivilegeConstants.PRIVILEGE_POS_MODIFY_AMOUNT,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_POS_SALE, PrivilegeConstants.PRIVILEGE_POS_MODIFY_PRICE,
				privilegeResources, list);

		// 基本档案
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_BASIC_INFO,
				PrivilegeConstants.PRIVILEGE_TYPE_BASIC_PRICETAG_VIEW, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_BASIC_INFO,
				PrivilegeConstants.PRIVILEGE_TYPE_BASIC_PRICETAG_EDIT, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_BASIC_INFO,
				PrivilegeConstants.PRIVILEGE_TYPE_BASIC_PRICETAG_AUDIT, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_BASIC_INFO,
				PrivilegeConstants.PRIVILEGE_TYPE_BASIC_PRICETAG_PRINT, privilegeResources, list);

		// 会员管理
		addPrivilege(PrivilegeConstants.PRIVILEGE_COUSTOMER, PrivilegeConstants.PRIVILEGE_CARD_DELIEVER,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_COUSTOMER, PrivilegeConstants.PRIVILEGE_COUSTOMER_CARDDEPOSIT,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_COUSTOMER, PrivilegeConstants.PRIVILEGE_COUSTOMER_CARD_CONSUME,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_COUSTOMER, PrivilegeConstants.PRIVILEGE_COUSTOMER_CARD_MODIFY,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_COUSTOMER, PrivilegeConstants.PRIVILEGE_COUSTOMER_CARD_POINT_CHANGE,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_COUSTOMER,
				PrivilegeConstants.PRIVILEGE_COUSTOMER_CARD_POINT_TO_DEPOSIT, privilegeResources, list);

		// 库存管理
		addPrivilege(PrivilegeConstants.PRIVILEGE_INVENTORY, PrivilegeConstants.PRIVILEGE_INVENTORY_CHECK_ORDER,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_INVENTORY, PrivilegeConstants.PRIVILEGE_INVENTORY_CHECK_ORDER_DEL,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_INVENTORY, PrivilegeConstants.PRIVILEGE_INVENTORY_CHECK_DIFFERENCE,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_INVENTORY, PrivilegeConstants.PRIVILEGE_RESOURCE_STORE_QUERY,
				privilegeResources, list);

		// 连锁管理
		addPrivilege(PrivilegeConstants.PRIVILEGE_CATENATION, PrivilegeConstants.PRIVILEGE_CATENATION_IN_VIEW,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_CATENATION, PrivilegeConstants.PRIVILEGE_CATENATION_IN_NEW,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_CATENATION, PrivilegeConstants.PRIVILEGE_CATENATION_IN_AUDIT,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_CATENATION, PrivilegeConstants.PRIVILEGE_CATENATION_OUT_VIEW,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_CATENATION, PrivilegeConstants.PRIVILEGE_CATENATION_OUT_NEW,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_CATENATION, PrivilegeConstants.PRIVILEGE_CATENATION_OUT_AUDIT,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_CATENATION, PrivilegeConstants.PRIVILEGE_CATENATION_REQUEST_VIEW,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_CATENATION, PrivilegeConstants.PRIVILEGE_CATENATION_REQUEST_NEW,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_CATENATION, PrivilegeConstants.PRIVILEGE_CATENATION_REQUEST_AUDIT,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_CATENATION, PrivilegeConstants.PRIVILEGE_CATENATION_TRACK,
				privilegeResources, list);

		// 促销管理
		addPrivilege(PrivilegeConstants.PRIVILEGE_POLICY, PrivilegeConstants.PRIVILEGE_POLICY_PROMOTION_QUERY,
				privilegeResources, list);

		// 每日提醒
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_DAILY_TIPS, PrivilegeConstants.PRIVILEGE_TIPS_ITEM_OFD,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_DAILY_TIPS, PrivilegeConstants.PRIVILEGE_TIPS_CLIENT_BIRTHDAY,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_DAILY_TIPS, PrivilegeConstants.PRIVILEGE_TIPS_CARDUSER_BIRTHDAY,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_DAILY_TIPS,
				PrivilegeConstants.PRIVILEGE_TIPS_INVENTORY_WEARNING, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_DAILY_TIPS,
				PrivilegeConstants.PRIVILEGE_TIPS_CLIENT_CREDIT_WARNING, privilegeResources, list);

		return list;
	}

	public static List<PrivilegeResource> getManagePrivileges(List<PrivilegeResource> privilegeResources) {
		List<PrivilegeResource> list = new ArrayList<PrivilegeResource>();
		// 前台销售
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_POS_SALE, PrivilegeConstants.PRIVILEGE_PRINT_SHIFT_TABLE_REPORT,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_POS_SALE, PrivilegeConstants.PRIVILEGE_RESOURCE_POS_VIEW,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_POS_SALE,
				PrivilegeConstants.PRIVILEGE_RESOURCE_CLIENT_ORDER_VIEW, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_POS_SALE, PrivilegeConstants.PRIVILEGE_GROSS_MARGIN_PARAM,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_POS_SALE, PrivilegeConstants.PRIVILEGE_POS_VIEW_SHIFT,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_POS_SALE, PrivilegeConstants.PRIVILEGE_POS_PRINT_SHIFT,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_POS_SALE, PrivilegeConstants.PRIVILEGE_POS_ANTI_PAY,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_POS_SALE, PrivilegeConstants.PRIVILEGE_POS_RE_PRINT,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_POS_SALE, PrivilegeConstants.PRIVILEGE_POS_OPEN_DRAW,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_POS_SALE, PrivilegeConstants.PRIVILEGE_POS_RETRUEN,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_POS_SALE, PrivilegeConstants.PRIVILEGE_POS_MGR_DISCOUNT,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_POS_SALE, PrivilegeConstants.PRIVILEGE_POS_DELETE,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_POS_SALE, PrivilegeConstants.PRIVILEGE_POS_PRESENT,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_POS_SALE, PrivilegeConstants.PRIVILEGE_POS_MODIFY_AMOUNT,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_POS_SALE, PrivilegeConstants.PRIVILEGE_POS_MODIFY_PRICE,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_POS_SALE, PrivilegeConstants.PRIVILEGE_POS_SHIFT_COLLECT,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_POS_SALE, PrivilegeConstants.PRIVILEGE_POS_CARD_OPT,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_POS_SALE, PrivilegeConstants.PRIVILEGE_CARD_RETURN,
				privilegeResources, list);

		// 系统管理
		addPrivilege(PrivilegeConstants.PRIVILEGE_SYSTEM, PrivilegeConstants.PRIVILEGE_RESOURCE_SYSTEM_EMPLOYEE,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_SYSTEM, PrivilegeConstants.PRIVILEGE_RESOURCE_SYSTEM_SYSN_VIEW,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_SYSTEM, PrivilegeConstants.PRIVILEGE_RESOURCE_SYSTEM_USER_RECORD,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_SYSTEM,
				PrivilegeConstants.PRIVILEGE_RESOURCE_SYSTEM_USER_RECORD_MANUAL, privilegeResources, list);

		// 基本档案
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_BASIC_INFO, PrivilegeConstants.PRIVILEGE_TYPE_BASIC_CLIENT_VIEW,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_BASIC_INFO,
				PrivilegeConstants.PRIVILEGE_TYPE_BASIC_CLIENT_MANAGE, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_BASIC_INFO, PrivilegeConstants.PRIVILEGE_TYPE_BASIC_PARA_VIEW,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_BASIC_INFO, PrivilegeConstants.PRIVILEGE_TYPE_BASIC_PARA_EDIT,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_BASIC_INFO,
				PrivilegeConstants.PRIVILEGE_TYPE_BASIC_SUPPLIER_VIEW, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_BASIC_INFO,
				PrivilegeConstants.PRIVILEGE_TYPE_BASIC_SUPPLIER_MANAGE, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_BASIC_INFO,
				PrivilegeConstants.PRIVILEGE_TYPE_BASIC_POSITEM_VIEW, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_BASIC_INFO,
				PrivilegeConstants.PRIVILEGE_TYPE_BASIC_POSITEM_MANAGE, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_BASIC_INFO,
				PrivilegeConstants.PRIVILEGE_TYPE_BASIC_PRICETAG_VIEW, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_BASIC_INFO,
				PrivilegeConstants.PRIVILEGE_TYPE_BASIC_PRICETAG_EDIT, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_BASIC_INFO,
				PrivilegeConstants.PRIVILEGE_TYPE_BASIC_PRICETAG_AUDIT, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_BASIC_INFO,
				PrivilegeConstants.PRIVILEGE_TYPE_BASIC_PRICETAG_PRINT, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_BASIC_INFO, PrivilegeConstants.PRIVILEGE_TYPE_BASIC_TICKET_SEND,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_BASIC_INFO,
				PrivilegeConstants.PRIVILEGE_TYPE_BASIC_PRICE_ADJ_VIEW, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_BASIC_INFO,
				PrivilegeConstants.PRIVILEGE_TYPE_BASIC_PRICE_ADJ_NEW, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_BASIC_INFO,
				PrivilegeConstants.PRIVILEGE_TYPE_BASIC_PRICE_ADJ_AUDIT, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_BASIC_INFO,
				PrivilegeConstants.PRIVILEGE_TYPE_BASIC_APPLY_ITEM_NEW, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_BASIC_INFO,
				PrivilegeConstants.PRIVILEGE_TYPE_BASIC_APPLY_ITEM_AUDIT, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_BASIC_INFO,
				PrivilegeConstants.PRIVILEGE_TYPE_BASIC_ELIMINATIVE_ITEM, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_BASIC_INFO,
				PrivilegeConstants.PRIVILEGE_TYPE_BASIC_ITEM_EXCEPTION, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_BASIC_INFO,
				PrivilegeConstants.PRIVILEGE_TYPE_BASIC_ITEM_STORE_MATRIX, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_BASIC_INFO, PrivilegeConstants.PRIVILEGE_TYPE_BASIC_ITEM_PRICE,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_BASIC_INFO, PrivilegeConstants.PRIVILEGE_TYPE_BASIC_ITEM_STOCK,
				privilegeResources, list);

		// 会员管理
		addPrivilege(PrivilegeConstants.PRIVILEGE_COUSTOMER, PrivilegeConstants.PRIVILEGE_RESOURCE_CARD_REVOKE,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_COUSTOMER, PrivilegeConstants.PRIVILEGE_RESOURCE_CARD_LOSS,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_COUSTOMER, PrivilegeConstants.PRIVILEGE_RESOURCE_CARD_REPLACE,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_COUSTOMER, PrivilegeConstants.PRIVILEGE_RESOURCE_CARD_RELAT,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_COUSTOMER, PrivilegeConstants.PRIVILEGE_RESOURCE_CARD_LOCK,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_COUSTOMER, PrivilegeConstants.PRIVILEGE_RESOURCE_CARD_TIPS,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_COUSTOMER, PrivilegeConstants.PRIVILEGE_RESOURCE_CARD_FLAG,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_COUSTOMER, PrivilegeConstants.PRIVILEGE_RESOURCE_CARD_DELIEVER,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_COUSTOMER, PrivilegeConstants.PRIVILEGE_RESOURCE_CARD_DEPOSIT,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_COUSTOMER, PrivilegeConstants.PRIVILEGE_RESOURCE_CARD_CONSUME,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_COUSTOMER, PrivilegeConstants.PRIVILEGE_RESOURCE_CARD_MODIFY,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_COUSTOMER, PrivilegeConstants.PRIVILEGE_RESOURCE_POINT_CHANGE,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_COUSTOMER, PrivilegeConstants.PRIVILEGE_RESOURCE_POINT_TO_DEPOSIT,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_COUSTOMER, PrivilegeConstants.PRIVILEGE_RESOURCE_CARD_RECORDS,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_COUSTOMER, PrivilegeConstants.PRIVILEGE_RESOURCE_CARD_DETAIL,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_COUSTOMER, PrivilegeConstants.PRIVILEGE_RESOURCE_CARD_INFO,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_COUSTOMER, PrivilegeConstants.PRIVILEGE_RESOURCE_CARD_MANAGE,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_COUSTOMER, PrivilegeConstants.PRIVILEGE_RESOURCE_CARD_OPER,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_COUSTOMER, PrivilegeConstants.PRIVILEGE_RESOURCE_CARD_SALE_SUMMARY,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_COUSTOMER, PrivilegeConstants.PRIVILEGE_RESOURCE_CARD_COLLECT,
				privilegeResources, list);

		// 库存管理、
		addPrivilege(PrivilegeConstants.PRIVILEGE_INVENTORY, PrivilegeConstants.PRIVILEGE_INVENTORY_CHECK_ORDER,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_INVENTORY, PrivilegeConstants.PRIVILEGE_INVENTORY_CHECK_ORDER_DEL,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_INVENTORY, PrivilegeConstants.PRIVILEGE_INVENTORY_CHECK_DIFFERENCE,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_INVENTORY, PrivilegeConstants.PRIVILEGE_RESOURCE_STORE_QUERY,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_INVENTORY, PrivilegeConstants.PRIVILEGE_INVENTORY_ASSEMBLE_VIEW,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_INVENTORY, PrivilegeConstants.PRIVILEGE_INVENTORY_ASSEMBLE_NEW,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_INVENTORY, PrivilegeConstants.PRIVILEGE_INVENTORY_ASSEMBLE_AUDIT,
				privilegeResources, list);

		// 查询统计
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_QUERY_STAT,
				PrivilegeConstants.PRIVILEGE_RESOURCE_REPORT_ITEM_ON_ORDER, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_QUERY_STAT,
				PrivilegeConstants.PRIVILEGE_RESOURCE_REPORT_BIZDAY_COLLECT, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_QUERY_STAT,
				PrivilegeConstants.PRIVILEGE_RESOURCE_REPORT_SALE_COMMISSION, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_QUERY_STAT,
				PrivilegeConstants.PRIVILEGE_RESOURCE_REPORT_SALE_STOCK, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_QUERY_STAT,
				PrivilegeConstants.PRIVILEGE_RESOURCE_REPORT_PAID_ORDER, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_QUERY_STAT,
				PrivilegeConstants.PRIVILEGE_RESOURCE_ANALYSIS_POS_ORDER, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_QUERY_STAT,
				PrivilegeConstants.PRIVILEGE_RESOURCE_ANALYSIS_ITEM_SALE, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_QUERY_STAT,
				PrivilegeConstants.PRIVILEGE_RESOURCE_ANALYSIS_SUPPLEMENT, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_QUERY_STAT,
				PrivilegeConstants.PRIVILEGE_RESOURCE_BRANCH_PAYMENT_COLLECT, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_QUERY_STAT,
				PrivilegeConstants.PRIVILEGE_RESOURCE_REPORT_BRANCH_DAY_SALE, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_QUERY_STAT,
				PrivilegeConstants.PRIVILEGE_RESOURCE_REPORT_BRANCH_MONTH_SALE, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_QUERY_STAT,
				PrivilegeConstants.PRIVILEGE_RESOURCE_REPORT_ITEM_PRICE, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_QUERY_STAT,
				PrivilegeConstants.PRIVILEGE_RESOURCE_REPORT_ITEM_UNSALEABLE, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_QUERY_STAT,
				PrivilegeConstants.PRIVILEGE_RESOURCE_REPORT_ITEM_ABC, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_QUERY_STAT,
				PrivilegeConstants.PRIVILEGE_RESOURCE_REPORT_ITEM_BACKLOG, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_QUERY_STAT,
				PrivilegeConstants.PRIVILEGE_RESOURCE_REPORT_GROUP_ITEM_SALE, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_QUERY_STAT,
				PrivilegeConstants.PRIVILEGE_RESOURCE_REPORT_SALE_COMPARE, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_QUERY_STAT,
				PrivilegeConstants.PRIVILEGE_RESOURCE_REPORT_SALE_DISCOUNT, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_QUERY_STAT,
				PrivilegeConstants.PRIVILEGE_RESOURCE_REPORT_BRANCH_SALE, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_QUERY_STAT,
				PrivilegeConstants.PRIVILEGE_RESOURCE_REPORT_POS_ORDER_DETAIL, privilegeResources, list);

		// 连锁管理
		addPrivilege(PrivilegeConstants.PRIVILEGE_CATENATION, PrivilegeConstants.PRIVILEGE_CATENATION_IN_VIEW,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_CATENATION, PrivilegeConstants.PRIVILEGE_CATENATION_IN_NEW,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_CATENATION, PrivilegeConstants.PRIVILEGE_CATENATION_IN_AUDIT,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_CATENATION, PrivilegeConstants.PRIVILEGE_CATENATION_OUT_VIEW,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_CATENATION, PrivilegeConstants.PRIVILEGE_CATENATION_OUT_NEW,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_CATENATION, PrivilegeConstants.PRIVILEGE_CATENATION_OUT_AUDIT,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_CATENATION, PrivilegeConstants.PRIVILEGE_CATENATION_REQUEST_VIEW,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_CATENATION, PrivilegeConstants.PRIVILEGE_CATENATION_REQUEST_NEW,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_CATENATION, PrivilegeConstants.PRIVILEGE_CATENATION_REQUEST_AUDIT,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_CATENATION, PrivilegeConstants.PRIVILEGE_CATENATION_TRACK,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_CATENATION, PrivilegeConstants.PRIVILEGE_RESOURCE_GROUP_INVENTORY,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_CATENATION, PrivilegeConstants.PRIVILEGE_CATENATION_TRANSFER_PRICE,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_CATENATION,
				PrivilegeConstants.PRIVILEGE_CATENATION_TRANSFER_IN_PRICE, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_CATENATION,
				PrivilegeConstants.PRIVILEGE_RESOURCE_CATENATION_ON_PASSAGE, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_CATENATION, PrivilegeConstants.PRIVILEGE_CATENATION_TRSNSFER_QUERY,
				privilegeResources, list);

		// 促销管理
		addPrivilege(PrivilegeConstants.PRIVILEGE_POLICY, PrivilegeConstants.PRIVILEGE_POLICY_PROMOTION_QUERY,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_POLICY, PrivilegeConstants.PRIVILEGE_RESOURCE_POLICY_DISCOUNT,
				privilegeResources, list);

		// 短信管理
		addPrivilege(PrivilegeConstants.PRIVILEGE_SMS, PrivilegeConstants.PRIVILEGE_COUSTOMERE_SMS_MEMBER,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_SMS, PrivilegeConstants.PRIVILEGE_COUSTOMERE_SMS_SETUP,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_SMS, PrivilegeConstants.PRIVILEGE_COUSTOMERE_SMS_SEND_D,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_SMS, PrivilegeConstants.PRIVILEGE_COUSTOMERE_SMS_SEND_Q,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_SMS, PrivilegeConstants.PRIVILEGE_COUSTOMERE_SMS_QUERY,
				privilegeResources, list);

		// 每日提醒
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_DAILY_TIPS, PrivilegeConstants.PRIVILEGE_TIPS_ITEM_OFD,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_DAILY_TIPS, PrivilegeConstants.PRIVILEGE_TIPS_PURCHASE_OFD,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_DAILY_TIPS, PrivilegeConstants.PRIVILEGE_TIPS_CLIENT_BIRTHDAY,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_DAILY_TIPS, PrivilegeConstants.PRIVILEGE_TIPS_CARDUSER_BIRTHDAY,
				privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_DAILY_TIPS,
				PrivilegeConstants.PRIVILEGE_TIPS_INVENTORY_WEARNING, privilegeResources, list);
		addPrivilege(PrivilegeConstants.PRIVILEGE_TYPE_DAILY_TIPS,
				PrivilegeConstants.PRIVILEGE_TIPS_CLIENT_CREDIT_WARNING, privilegeResources, list);

		return list;
	}

	public static int getStringWidth(String str) {
		int width = 0;
		for (int i = 0; i < str.length(); i++) {
			int gb = gbValue(str.charAt(i));
			if ((gb < 45217) || (gb > 63486)) {
				width++;
			} else {
				width = width + 2;
			}
		}
		return width;
	}

	private static int gbValue(char ch) {// 将一个汉字（GB2312）转换为十进制表示。
		String str = new String();
		str += ch;
		try {
			byte[] bytes = str.getBytes("GB2312");
			if (bytes.length < 2)
				return 0;
			return (bytes[0] << 8 & 0xff00) + (bytes[1] & 0xff);
		} catch (Exception e) {
			return 0;
		}
	}

	public static String getString(int width) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < width; i++) {
			sb.append(" ");
		}
		return sb.toString();
	}

	public static boolean checkYearTable(String queryYear) {
		if (queryYear.compareTo("2016") <= 0) {
			return true;
		}
		return false;
	}

	public static String constructHTTPGetParam(String secret, Map<String, Object> map) {
		map.put("timestamp", DateUtil.getLongDateTimeStr(Calendar.getInstance().getTime()));
		Set<String> keySet = map.keySet();
		Object[] objs = keySet.toArray();
		Arrays.sort(objs);
		StringBuffer sb = new StringBuffer();
		sb.append(secret);
		for (int i = 0; i < objs.length; i++) {
			String param = objs[i].toString();
			String value = map.get(param).toString();
			if (isChinese(value)) {
				continue;
			}
			sb.append(param).append(value);
		}
		sb.append(secret);
		String sign = sb.toString();
		sign = DigestUtils.md5Hex(sign);// 对 API 输入参数进行 md5 加密
		map.put("sign", sign);
		sb = new StringBuffer();
		keySet = map.keySet();
		Iterator<String> keyIterator = keySet.iterator();
		while (keyIterator.hasNext()) {
			String key = keyIterator.next();
			Object object = map.get(key);
			String value = object.toString();
			if (key.equals("url")) {
				try {
					value = URLEncoder.encode(value, "utf-8");
				} catch (UnsupportedEncodingException e) {

				}
			}
			if (sb.toString().isEmpty()) {
				sb.append("?");

			} else {
				sb.append("&");

			}
			sb.append(key).append("=").append(value);
		}
		return sb.toString();
	}

	public static SystemBook getSystemBook(List<SystemBook> systemBooks, String systemBookCode) {
		for (int i = 0; i < systemBooks.size(); i++) {
			SystemBook systemBook = systemBooks.get(i);
			if (systemBook.getSystemBookCode().equals(systemBookCode)) {
				return systemBook;
			}
		}
		return null;
	}

	public static boolean isChinese(String str) {
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			// 汉字范围 \u4e00-\u9fa5 (中文)
			if (c >= 19968 && c <= 171941) {
				return true;
			}
		}
		return false;
	}

	public static Gson toBuilderGson() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.disableHtmlEscaping();
		gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		gsonBuilder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {

			@Override
			public Date deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
					throws JsonParseException {
				try {
					return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(json.getAsString());
				} catch (ParseException e) {
					return null;
				}
			}
		});
		gsonBuilder.registerTypeAdapter(Integer.class, new JsonDeserializer<Integer>() {

			@Override
			public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				try {
					return Integer.parseInt(json.getAsString());
				} catch (Exception e) {
					return null;
				}
			}

		});
		gsonBuilder.registerTypeAdapter(BigDecimal.class, new JsonDeserializer<BigDecimal>() {

			@Override
			public BigDecimal deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				try {
					return new BigDecimal(json.getAsString());
				} catch (Exception e) {
					return null;
				}
			}

		});
		gsonBuilder.setExclusionStrategies(new ExclusionStrategy() {

			@Override
			public boolean shouldSkipField(FieldAttributes f) {
				if(f.getAnnotation(GsonIgnore.class) != null) {
					return true;
				}
				return false;
			}

			@Override
			public boolean shouldSkipClass(Class<?> clazz) {
				if(clazz.getAnnotation(GsonIgnore.class) != null) {
					return true;
				}
				return false;
			}
			
		});
		return gsonBuilder.create();
	}

	public static String generateCheckCode(Integer codeLength) {
		return generateCheckCode(codeLength, System.currentTimeMillis());
	}

	public static String generateCheckCode(Integer codeLength, Long seed) {
		Random random = new Random();
		if (seed != null) {
			random.setSeed(seed);

		}
		long nextInt = random.nextInt(Integer.parseInt(StringUtils.rightPad("9", codeLength, "0")))
				+ Integer.parseInt(StringUtils.rightPad("1", codeLength, "0"));
		if (nextInt < 0) {
			nextInt = -nextInt;
		}
		String str = nextInt + "";
		return str;
	}

	public static boolean isValidMobilePhoneNumber(String phonenumber) {
		if (phonenumber == null) {
			return false;
		}
		String phone = "^((1))\\d{10}$";
		return phonenumber.matches(phone);
	}
	
	public static void main(String[] args) {
		System.out.print(getMACAddress());
	}

	public static String generatePrintId(Set<String> printNums, Long seed, String preStr) {
		String str = null;

		while (str == null || printNums.contains(str)) {
			Random random = new Random();
			if (seed != null) {
				random.setSeed(seed);

			}
			long nextInt = random.nextInt(9000000) + 1000000;
			if (nextInt < 0) {
				nextInt = -nextInt;
			}
			str = preStr.concat(nextInt + "");
		}
		printNums.add(str);
		return str;
	}

	public static String getSeperate() {
		return System.getProperty("line.separator");
	}

	public static PosItemTypeParam findTopCategory(String code, List<PosItemTypeParam> posItemTypeParams) {
		if (code == null) {
			return new PosItemTypeParam("", "未知类别");
		}
		PosItemTypeParam topParam = null;
		boolean isReturn = true;
		String findCode = code;
		while (isReturn) {
			for (int i = 0; i < posItemTypeParams.size(); i++) {
				PosItemTypeParam param = posItemTypeParams.get(i);
				if (findCode.equals(param.getPosItemTypeCode())) {
					topParam = param;
					break;
				}
			}
			if (topParam == null) {
				return null;
			}
			if (topParam.getPosItemTypeFatherCode() == null) {
				isReturn = false;
			} else {
				findCode = topParam.getPosItemTypeFatherCode();
			}
		}
		return topParam;
	}

	public static String downloadFileFromUrl(String address, String filePath, String fileName) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			URL url = new URL(address);
			DataInputStream dataInputStream = new DataInputStream(url.openStream());
			FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath + fileName));
			byte[] b = new byte[1024];
			int length;
			while ((length = dataInputStream.read(b)) > 0) {
				fileOutputStream.write(b, 0, length);
			}
			fileOutputStream.flush();
			dataInputStream.close();
			fileOutputStream.close();
			logger.info("文件输出路径:" + filePath + fileName);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			return address;
		}
		return getRelativePath(filePath) + fileName;
	}

	private static String getRelativePath(String absolutePath) {
		String path = absolutePath.substring(AppUtil.getFilePath().length());
		path = "/" + path.replace("\\", "/");
		return path;
	}

	public static String createCodeImage(String barCode, String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		byte[] QRCodeImage;
		String QRCodeStr = new String(barCode);
		String fileName = AppUtil.getUUID();
		String returnUlr = fileName + ".png";
		try {
			Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			hints.put(EncodeHintType.MARGIN, 1);
			BitMatrix bitMatrix = new MultiFormatWriter().encode(QRCodeStr, BarcodeFormat.QR_CODE, 200, 200, hints);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			MatrixToImageWriter.writeToStream(bitMatrix, "png", outputStream);
			QRCodeImage = outputStream.toByteArray();

			file = new File(filePath + returnUlr);
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(QRCodeImage);
			fileOutputStream.flush();
			fileOutputStream.close();

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return getRelativePath(filePath) + fileName + ".png";

	}

	public static PosItemGrade getPosItemGrade(List<PosItemGrade> posItemGrades, Integer itemGradeNum) {
		for (int i = 0; i < posItemGrades.size(); i++) {
			PosItemGrade posItemGrade = posItemGrades.get(i);
			if (posItemGrade.getItemGradeNum().equals(itemGradeNum)) {
				return posItemGrade;

			}
		}
		return null;
	}

	public static String getStringValue(BigDecimal value) {
		String str = value.toString();

		int index = str.indexOf(".");
		if (index == -1) {
			return str;
		}
		String pre = str.substring(0, index);
		String point = str.substring(index + 1);
		while (point.endsWith("0")) {
			point = point.substring(0, point.length() - 1);
		}
		if (point.isEmpty()) {
			return pre;
		}
		return pre + "." + point;
	}

	public static String getDBColumnName(String name) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append("_");
				c = Character.toLowerCase(c);
			}
			sb.append(c);

		}
		return sb.toString();
	}

	public static BigDecimal getTransferPrice(StoreMatrix storeMatrix) {
		if (storeMatrix == null) {
			return null;
		}
		if (!storeMatrix.getStoreMatrixRquestEnabled()) {
			return null;
		}
		if (storeMatrix.getStoreMatrixTransferPrice().compareTo(BigDecimal.ZERO) == 0) {
			return null;
		}
		return storeMatrix.getStoreMatrixTransferPrice();

	}

	public static BigDecimal getTransferPrice(List<StoreMatrix> storeMatrixs, Integer branchNum, Integer itemNum) {
		if(storeMatrixs == null){
			return null;
		}
		for (int i = 0; i < storeMatrixs.size(); i++) {
			StoreMatrix storeMatrix = storeMatrixs.get(i);
			if (!storeMatrix.getStoreMatrixRquestEnabled()) {
				continue;
			}
			if (storeMatrix.getStoreMatrixTransferPrice().compareTo(BigDecimal.ZERO) == 0) {
				continue;
			}
			if (storeMatrix.getId().getBranchNum().equals(branchNum)
					&& storeMatrix.getId().getItemNum().equals(itemNum)) {
				return storeMatrix.getStoreMatrixTransferPrice();
			}
		}
		return null;
	}

	public static BigDecimal getSalePrice(List<StoreMatrix> storeMatrixs, Integer branchNum, Integer itemNum) {
		for (int i = 0; i < storeMatrixs.size(); i++) {
			StoreMatrix storeMatrix = storeMatrixs.get(i);
			if (!storeMatrix.getStoreMatrixPriceEnabled()) {
				continue;
			}
			if (storeMatrix.getStoreMatrixRegularPrice().compareTo(BigDecimal.ZERO) == 0) {
				continue;
			}
			if (storeMatrix.getId().getBranchNum().equals(branchNum)
					&& storeMatrix.getId().getItemNum().equals(itemNum)) {
				return storeMatrix.getStoreMatrixRegularPrice();
			}
		}
		return null;
	}

	public static BigDecimal getSalePrice(StoreMatrix storeMatrix) {
		if (storeMatrix == null) {
			return null;
		}
		if (!storeMatrix.getStoreMatrixPriceEnabled()) {
			return null;
		}
		if (storeMatrix.getStoreMatrixRegularPrice().compareTo(BigDecimal.ZERO) == 0) {
			return null;
		}
		return storeMatrix.getStoreMatrixRegularPrice();
	}

	public static StoreItemSupplier getPreStoreItemSupplier(List<StoreItemSupplier> storeItemSuppliers,
			String systemBookCode, Integer branchNum, Integer itemNum) {
		StoreItemSupplier preStoreItemSupplier = null;
		for (int i = 0; i < storeItemSuppliers.size(); i++) {
			StoreItemSupplier storeItemSupplier = storeItemSuppliers.get(i);
			if (storeItemSupplier.getId().getSystemBookCode().equals(systemBookCode)
					&& storeItemSupplier.getId().getItemNum().equals(itemNum)) {
				if(branchNum != null && !storeItemSupplier.getId().getBranchNum().equals(branchNum)){
					continue;
				}
				if(storeItemSupplier.getStoreItemSupplierDefault() != null && storeItemSupplier.getStoreItemSupplierDefault()){
					return storeItemSupplier;
				}
				if (preStoreItemSupplier == null) {
					preStoreItemSupplier = storeItemSupplier;
				} else {
					if (storeItemSupplier.getStoreItemSupplierPri() > preStoreItemSupplier.getStoreItemSupplierPri()) {
						preStoreItemSupplier = storeItemSupplier;
					} else if (storeItemSupplier.getStoreItemSupplierPri().equals(
							preStoreItemSupplier.getStoreItemSupplierPri())) {
						if (preStoreItemSupplier.getStoreItemSupplierLastestTime() == null
								&& storeItemSupplier.getStoreItemSupplierLastestTime() != null) {
							preStoreItemSupplier = storeItemSupplier;
						} else if (preStoreItemSupplier.getStoreItemSupplierLastestTime() != null
								&& storeItemSupplier.getStoreItemSupplierLastestTime() != null) {
							if (preStoreItemSupplier.getStoreItemSupplierLastestTime().compareTo(
									storeItemSupplier.getStoreItemSupplierLastestTime()) < 0) {
								preStoreItemSupplier = storeItemSupplier;
							}
						}
					}
				}
			}
		}
		return preStoreItemSupplier;
	}

	public static List<StoreItemSupplier> findStoreItemSuppliers(List<StoreItemSupplier> storeItemSuppliers,
			String systemBookCode, Integer branchNum, Integer itemNum) {
		List<StoreItemSupplier> list = new ArrayList<StoreItemSupplier>();
		for (int i = 0; i < storeItemSuppliers.size(); i++) {
			StoreItemSupplier storeItemSupplier = storeItemSuppliers.get(i);
			if (storeItemSupplier.getId().getBranchNum().equals(branchNum)
					&& storeItemSupplier.getId().getSystemBookCode().equals(systemBookCode)
					&& storeItemSupplier.getId().getItemNum().equals(itemNum)) {
				list.add(storeItemSupplier);
			}
		}
		return list;
	}

	public static List<StoreItemSupplier> findStoreItemSuppliersBySupplier(List<StoreItemSupplier> storeItemSuppliers,
			String systemBookCode, Integer branchNum, Integer supplierNum) {
		List<StoreItemSupplier> list = new ArrayList<StoreItemSupplier>();
		for (int i = 0; i < storeItemSuppliers.size(); i++) {
			StoreItemSupplier storeItemSupplier = storeItemSuppliers.get(i);
			if (storeItemSupplier.getId().getBranchNum().equals(branchNum)
					&& storeItemSupplier.getId().getSystemBookCode().equals(systemBookCode)
					&& storeItemSupplier.getId().getSupplierNum().equals(supplierNum)) {
				list.add(storeItemSupplier);
			}
		}
		return list;
	}

	public static String getIpAddr(HttpServletRequest request) {

		String ip = request.getRemoteAddr();

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static PosItemArea getPosItemArea(List<PosItemArea> posItemAreas, Integer itemNum, String posItemAreaUnit) {
		for (int i = 0; i < posItemAreas.size(); i++) {
			PosItemArea posItemArea = posItemAreas.get(i);
			if (posItemArea.getId().getItemNum().equals(itemNum)
					&& posItemArea.getPosItemAreaUnit().equals(posItemAreaUnit)) {
				return posItemArea;
			}
		}
		return null;
	}

	public static PosOrder getPosOrder(String orderNo, List<PosOrder> posOrders) {
		for (int i = 0; i < posOrders.size(); i++) {
			PosOrder posOrder = posOrders.get(i);
			if (posOrder.getOrderNo().equals(orderNo)) {
				return posOrder;
			}
		}
		return null;
	}
	
	public static PosOrderDetail getPosOrderDetail(PosOrderDetailId id, List<PosOrderDetail> posOrderDetails) {
		for(int i = 0;i<posOrderDetails.size();i++) {
			PosOrderDetail posOrderDetail = posOrderDetails.get(i);
			if(posOrderDetail.getId().equals(id)) {
				return posOrderDetail;
			}
		}
		return null;
	}

	public static StoreMatrixDetail getStoreMatrixDetail(String systemBookCode, Integer branchNum, Integer itemNum,
			Integer itemMatrixNum, List<StoreMatrixDetail> storeMatrixDetails) {
		for (int i = 0; i < storeMatrixDetails.size(); i++) {
			StoreMatrixDetail storeMatrixDetail = storeMatrixDetails.get(i);
			if (storeMatrixDetail.getId().getSystemBookCode().equals(systemBookCode)
					&& storeMatrixDetail.getId().getBranchNum() == branchNum
					&& storeMatrixDetail.getId().getItemNum() == itemNum
					&& storeMatrixDetail.getId().getItemMatrixNum() == itemMatrixNum) {
				return storeMatrixDetail;
			}
		}
		return null;
	}

	public static WeixinPosItem getWeixinPosItem(List<WeixinPosItem> weixinPosItems, Integer weixinItemNum) {
		for (int i = 0; i < weixinPosItems.size(); i++) {
			WeixinPosItem weixinPosItem = weixinPosItems.get(i);
			if (weixinPosItem.getWeixinItemNum() == weixinItemNum) {
				return weixinPosItem;
			}
		}
		return null;
	}

	public static Ticket getTicket(List<Ticket> tickets, String ticketName) {
		for (int i = 0; i < tickets.size(); i++) {
			Ticket ticket = tickets.get(i);
			if (!ticket.isActived()) {
				continue;
			}
			if (ticket.getTicketName().equals(ticketName)) {
				return ticket;
			}
		}
		return null;
	}

	/**
	 * 检查券应用分店
	 * 
	 * @param branchs
	 * @param branchNum
	 * @return
	 */
	public static boolean checkTicketValidBranch(List<Branch> branchs, Integer branchNum) {
		if (branchs.size() == 0) {
			return true;
		}
		for (int i = 0; i < branchs.size(); i++) {
			Branch branch = branchs.get(i);
			if (branch.getId().getBranchNum() == 0) {
				return true;
			}
			if (branch.getId().getBranchNum().equals(branchNum)) {
				return true;
			}
		}
		return false;

	}

	public static boolean checkPicture(String suppfix) {
		if (suppfix.equals(".png") || suppfix.equals(".jpg") || suppfix.equals(".gif") || suppfix.equals(".jpeg")
				|| suppfix.equals(".bmp")) {
			return true;
		}
		return false;
	}
	
	public static boolean checkHtml(String suppfix) {
		if (suppfix.equals(".html") || suppfix.equals(".htm")) {
			return true;
		}
		return false;
	}

	public static List<String> getPosOrderOnlineSource() {
		List<String> list = new ArrayList<String>();
		list.add(AppConstants.ONLINE_ORDER_SOURCE_NH_WSHOP);
		list.add(AppConstants.ONLINE_ORDER_SOURCE_API);
		list.add(AppConstants.ONLINE_ORDER_SOURCE_YOUZAN);
		list.add(AppConstants.ONLINE_ORDER_SOURCE_MEITUAN);

		return list;
	}

	public static String getTicketPrintedId(String systemBookCode, Integer branchNum, Date date, String paramPre) {
		String shiftTableBizDay = DateUtil.getDateShortStr(date);
		String preFid = StringUtils.leftPad(branchNum.toString(), 2, "0") + shiftTableBizDay.substring(2, 4)
				+ StringUtils.leftPad(DateUtil.getDayOfYear(date).toString(), 3, "0");
		return paramPre.concat(preFid);
	}

	public static String filterDangerousQuery(String query) {
		if (query == null) {
			return null;
		}
		if(query.contains("%")){
			query = query.replaceAll("%", "[%]");
		}
		if(query.contains("'")){
			query = query.replaceAll("'", "");
		}
		if (query.contains("drop") || query.contains("alter") || query.contains("update")) {
			return "";
		}
		return query;
	}

	public static void setServerIp(String serverIp) {
		AppUtil.serverIp = serverIp;

	}

	public static String getServerIp() {
		return serverIp;
	}

	public static PosItemTypeParam getCategory(String code, List<PosItemTypeParam> posItemTypeParams) {
		if (code == null) {
			return new PosItemTypeParam("", "未知类别");
		}
		for (int i = 0; i < posItemTypeParams.size(); i++) {
			PosItemTypeParam posItemTypeParam = posItemTypeParams.get(i);
			if (posItemTypeParam.getPosItemTypeCode().equals(code)) {
				return posItemTypeParam;
			}
		}
		return null;
	}

	public static BigDecimal getGroupCustomerValue(GroupCustomer groupCustomer, String type) {
		if (groupCustomer.getGroupCustomerStatistics() == null) {
			groupCustomer.setGroupCustomerStatistics(new GroupCustomerStatistics());
		}
		if (type.equals(AppConstants.MARKET_CUSTOMER_COUNT)) {
			return BigDecimal.valueOf(groupCustomer.getGroupCustomerStatistics().getCustomerStatisticsConusmeCount());
		} else if (type.equals(AppConstants.CUSTOMER_MODEL_DEPOSIT_MONEY)) {
			return groupCustomer.getGroupCustomerStatistics().getCustomerStatisticsDepositMoney();
		} else if (type.equals(AppConstants.CUSTOMER_MODEL_DEPOSIT_CASH)) {
			return groupCustomer.getGroupCustomerStatistics().getCustomerStatisticsDepositCash();
		} else if (type.equals(AppConstants.CUSTOMER_MODEL_CARD_CONSUME)) {
			return groupCustomer.getGroupCustomerStatistics().getCustomerStatisticsConsumeMoney();
		} else if (type.equals(AppConstants.CUSTOMER_MODEL_POINT_CONSUME)) {
			return groupCustomer.getGroupCustomerStatistics().getCustomerStatisticsConusmePonit();
		} else if (type.equals(AppConstants.CUSTOMER_MODEL_COUPON_CONSUME)) {
			return BigDecimal.valueOf(groupCustomer.getGroupCustomerStatistics().getCustomerStatisticsConusmeTicket());
		} else if (type.equals(AppConstants.CUSTOMER_MODEL_CUSTOMER_COUNT)) {
			return BigDecimal.valueOf(groupCustomer.getGroupCustomerStatistics().getCustomerStatisticsCount());
		}
		return BigDecimal.ZERO;
	}

	public static String getUseRateFormat(BigDecimal value) {
		if (value == null) {
			return "0";
		}
		DecimalFormat df = new DecimalFormat("#,###.###");
		return df.format(value);
	}

	public static final byte[] readBytes(InputStream is, int contentLen) {
		if (contentLen > 0) {
			int readLen = 0;

			int readLengthThisTime = 0;

			byte[] message = new byte[contentLen];

			try {

				while (readLen != contentLen) {

					readLengthThisTime = is.read(message, readLen, contentLen - readLen);

					if (readLengthThisTime == -1) {// Should not happen.
						break;
					}

					readLen += readLengthThisTime;
				}

				return message;
			} catch (IOException e) {
				// Ignore
				// e.printStackTrace();
			}
		}

		return new byte[] {};
	}

	/**
	 * 过滤英文字母
	 * 
	 * @param text
	 */
	public static String filterLetter(String text) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < text.length(); i++) {
			int chr = text.charAt(i);
			if (chr >= 48 && chr <= 57) {
				sb.append(text.charAt(i));
			}
		}
		return sb.toString();
	}

	/**
	 * 博全的帐套号+ 测试帐套号
	 * 
	 * @return
	 */
	public static List<String> getBQBookCodes() {
		List<String> systemBookCodes = new ArrayList<String>();
		systemBookCodes.add("4020");
		systemBookCodes.add("4344");
		systemBookCodes.add("4444");
		systemBookCodes.add("4301");
		systemBookCodes.add("4302");
		systemBookCodes.add("4303");
		systemBookCodes.add("4077");
		return systemBookCodes;
	}

	public static Integer getDefaultSupplier(List<StoreItemSupplier> storeItemSuppliers, Integer branchNum,
			Integer itemNum) {
		if (storeItemSuppliers == null) {
			return null;
		}
		Integer centerSupplierNum = null;
		for (int i = 0; i < storeItemSuppliers.size(); i++) {
			StoreItemSupplier storeItemSupplier = storeItemSuppliers.get(i);
			if (!storeItemSupplier.getId().getItemNum().equals(itemNum)) {
				continue;
			}
			if (storeItemSupplier.getId().getBranchNum().equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)) {
				centerSupplierNum = storeItemSupplier.getId().getSupplierNum();
			}
			if (storeItemSupplier.getId().getBranchNum().equals(branchNum)) {
				return storeItemSupplier.getId().getSupplierNum();
			}
		}
		return centerSupplierNum;
	}

	public static Map<String, String> getPostMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			InputStream is = request.getInputStream();
			byte[] reqBodyBytes = AppUtil.readBytes(is, request.getContentLength());
			String requestStr = new String(reqBodyBytes);
			requestStr = URLDecoder.decode(requestStr, "UTF-8");
			String[] array = requestStr.split("&");
			for (int i = 0; i < array.length; i++) {
				String keyValue = array[i];
				String[] lines = keyValue.split("=");
				if (lines.length != 2) {
					continue;
				}
				String key = lines[0];
				String value = lines[1];
				map.put(key, value);
			}
			is.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return map;
	}

	public static boolean isPromotionDateValid(Date dateFrom, Date dateTo, Date now, String promotionRepeatType,
			Date promotionRepeatEnd) {

		Date dateFromTemp = DateUtil.getMinOfDate(dateFrom);
		Date dateToTemp = DateUtil.getMaxOfDate(dateTo);
		if (dateTo.compareTo(now) >= 0) {
			return true;
		}
		boolean flag = DateUtil.betweenDate(now, dateFromTemp, dateToTemp);
		if (flag) {
			return flag;
		}
		if (StringUtils.isEmpty(promotionRepeatType)) {
			return false;
		}
		if (promotionRepeatEnd != null && promotionRepeatEnd.compareTo(now) < 0) {
			return false;
		}
		Calendar c = Calendar.getInstance();
		int year = DateUtil.getYear(now);
		int month = DateUtil.getMonth(now);
		if (promotionRepeatType.equals(AppConstants.POLICY_PROMOTION_REPEAT_MONTH)) {

			int dayOfMonth = DateUtil.getDayOfMonth(dateFromTemp);
			Date endOfMonth = DateUtil.getLastDayOfMonth(now);
			if (dayOfMonth == 31) {
				dateFromTemp = DateUtil.getMinOfDate(endOfMonth);
			} else {
				c.setTime(dateFromTemp);
				c.set(Calendar.YEAR, year);
				c.set(Calendar.MONTH, month - 1);
				dateFromTemp = c.getTime();
			}

			dayOfMonth = DateUtil.getDayOfMonth(dateToTemp);
			if (dayOfMonth == 31) {
				dateToTemp = DateUtil.getMaxOfDate(endOfMonth);
			} else {
				c.setTime(dateToTemp);
				c.set(Calendar.YEAR, year);
				c.set(Calendar.MONTH, month - 1);
				dateToTemp = c.getTime();
			}

		} else if (promotionRepeatType.equals(AppConstants.POLICY_PROMOTION_REPEAT_YEAR)) {

			c.setTime(dateFromTemp);
			c.set(Calendar.YEAR, year);
			dateFromTemp = c.getTime();

			c.setTime(dateToTemp);
			c.set(Calendar.YEAR, year);
			dateToTemp = c.getTime();

		}
		return DateUtil.betweenDate(now, dateFromTemp, dateToTemp);

	}

	public static final String readHttpPostStr(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		InputStream is = null;
		try {
			is = request.getInputStream();
			byte[] reqBodyBytes = AppUtil.readBytes(is, request.getContentLength());
			String requestStr = new String(reqBodyBytes);
			requestStr = URLDecoder.decode(requestStr, "UTF-8");
			System.out.println(requestStr);
			return sb.toString();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return "";
		} finally {
			try {
				is.close();
			} catch (IOException e) {
			}
		}
	}

	public static boolean checkFieldExists(Class c, String fieldName) {
		Field[] fields = c.getDeclaredFields();
		boolean b = false;
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getName().equals(fieldName)) {
				b = true;
				break;
			}
		}
		return b;
	}

	public static String join(List<String> strList, String seperator) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strList.size(); i++) {
			if (i == 0) {
				sb.append(strList.get(i));
			} else {
				sb.append(seperator + strList.get(i));
			}
		}
		return sb.toString();
	}

	public static TaskRequest getTaskRequest(List<TaskRequest> list, String taskRequestFid) {
		for (TaskRequest taskRequest : list) {
			if (taskRequest.getTaskRequestFid() != null && taskRequest.getTaskRequestFid().equals(taskRequestFid)) {
				return taskRequest;
			}
		}
		return null;
	}

	public static BigDecimal getLockInventoryQty(Map<String, BigDecimal> map, Integer itemNum, Integer itemMatrixNum,
			String lot) {
		BigDecimal qty = BigDecimal.ZERO;
		Iterator<String> iterator = map.keySet().iterator();
		StringBuffer sb = new StringBuffer();
		sb.append(itemNum.toString()).append("|");
		if (itemMatrixNum != null) {
			sb.append(itemMatrixNum.toString()).append("|");
		}
		if (lot != null) {
			sb.append(lot);
		}

		String str = sb.toString();
		while (iterator.hasNext()) {
			String key = iterator.next();
			if (key.startsWith(str)) {
				qty = qty.add(map.get(key));

			}
		}
		return qty;
	}

	public static BigDecimal getValue(String catalog) {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		try {
			Object object = engine.eval(catalog);
			if(object instanceof BigDecimal){
				return (BigDecimal)object;
			} else 	if(object instanceof Double){
				return BigDecimal.valueOf((Double)object);
			} else 	if(object instanceof Integer){
				return BigDecimal.valueOf((Integer)object);
			} else 	if(object instanceof Float){
				return BigDecimal.valueOf((Float)object);
			}
			return null;
		} catch (ScriptException e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> split(String str, Class<T> type) {
		String[] strList = str.split(",");
		List<T> returnList = new ArrayList<T>();
		for(int i = 0;i<strList.length;i++) {
			if(type == Integer.class) {
				returnList.add((T)Integer.valueOf(strList[i].trim()));
			}
			else if(type == BigDecimal.class) {
				returnList.add((T)new BigDecimal(strList[i].trim()));
			}
			else if(type == String.class) {
				returnList.add((T)strList[i]);
			}
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getValue(Object object, Class<T> type) {
		if(object == null) {
			if(type == Integer.class) {
				return (T)Integer.valueOf(0);
			} 
			else if(type == BigDecimal.class){
				return (T)BigDecimal.ZERO;
			}
			else if(type == Long.class) {
				return (T)Long.valueOf(0);
			}
		} else {
			return (T)object;
		}
		return null;
	}
		
	public static String getValidDate(Date dateStart, String validDays) {
		int days = DateUtil.diffDay(dateStart, Calendar.getInstance().getTime());
		Integer valids = Integer.valueOf(validDays);
		Integer day = valids - days;
		String value = "";
		if (day < 0) {
			return "0个月";
		}
		if (day >= 365) {
			int year = day / 365;
			value = value + year + "年";
			day = day % 365;
		}
		if (day >= 30) {
			int month = day / 30;
			value = value + month + "个月";
			day = day % 30;
		}
		if (day > 0 || value.equals("")) {
			value = value + day + "天";
		}
		return value;
	}
	
	public static String getFileSeparator(){
		return fileSeparator;
	}
	
	public static String getEnter(){
		
		return "\n";
	}

	public static Gson getGson() {
		if(gson == null){
			gson = toBuilderGson();
		}
		return gson;
	}

	public static boolean isIntegerValue(BigDecimal bd) {
		return bd.signum() == 0 || bd.scale() <= 0 || bd.stripTrailingZeros().scale() <= 0;
	}
	
	public static <T> T get(List<T> list, T condition) {
		try {
			if(list == null || condition == null) {
				return null;
			}
			Field[] fields = condition.getClass().getDeclaredFields();
			Map<Field, Object> availFields = new HashMap<Field, Object>();
			for(Field f : fields) {
				f.setAccessible(true);
				Object obj = f.get(condition);
				if(obj == null) {
					continue;
				}
				if(obj instanceof List || obj instanceof Map || obj instanceof Set) {
					continue;
				}
				availFields.put(f, obj);

			}
			if(availFields.size() == 0) {
				return null;
			}
			for(T t : list) {
				boolean found = true;
				for(Map.Entry<Field, Object> entry : availFields.entrySet()) {
					Object o = entry.getKey().get(t);
					if(o instanceof BigDecimal) {
						if(((BigDecimal)o).compareTo((BigDecimal)entry.getValue()) != 0) {
							found = false;
							break;
						}
					}
					else {
						if(!o.equals(entry.getValue())) {
							found = false;
							break;
						}
					}
				}
				if(found) {
					return t;
				}
			}
			return null;
		} catch(Exception e) {
			return null;
		}
	}
	
	public static <T> List<T> getList(List<T> list, T condition) {
		try {
			List<T> retList = new ArrayList<T>();
			if(list == null || condition == null) {
				return retList;
			}
			Field[] fields = condition.getClass().getDeclaredFields();
			Map<Field, Object> availFields = new HashMap<Field, Object>();
			for(Field f : fields) {
				f.setAccessible(true);
				Object obj = f.get(condition);
				if(obj == null) {
					continue;
				}
				if(obj instanceof List || obj instanceof Map || obj instanceof Set) {
					continue;
				}
				availFields.put(f, obj);

			}
			if(availFields.size() == 0) {
				return retList;
			}
			for(T t : list) {
				boolean found = true;
				for(Map.Entry<Field, Object> entry : availFields.entrySet()) {
					Object o = entry.getKey().get(t);
					if(o instanceof BigDecimal) {
						if(((BigDecimal)o).compareTo((BigDecimal)entry.getValue()) != 0) {
							found = false;
							break;
						}
					}
					else {
						if(!o.equals(entry.getValue())) {
							found = false;
							break;
						}
					}
				}
				if(found) {
					retList.add(t);
				}
			}
			return retList;
		} catch(Exception e) {
			return new ArrayList<T>();
		}
	}
	
    /**
     * 获取一定长度的随机字符串
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    
    public static String getMACAddress(){  
    	try {
        	
        	InetAddress ia = InetAddress.getLocalHost();
            //获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。  
            byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();  
            //下面代码是把mac地址拼装成String  
            StringBuffer sb = new StringBuffer();  
            
            for(int i=0;i<mac.length;i++){  
            	if(i!=0){  
            		sb.append("-");  
            	}  
            	//mac[i] & 0xFF 是为了把byte转化为正整数  
            	String s = Integer.toHexString(mac[i] & 0xFF);  
            	sb.append(s.length()==1?0+s:s);  
            }  
            
            //把字符串所有小写字母改为大写成为正规的mac地址并返回  
            return sb.toString().toUpperCase();  
              
		} catch (Exception e) {
			
		}
    	return null;

    }

	public static String getTmallPosOrderMNSQueueName() {
		return tmallPosOrderMNSQueueName;
	}

	public static String getTmallRequestOrderMNSQueueName() {
		return tmallRequestOrderMNSQueueName;
	}

	public static String getTmallOutOrderMNSQueueName() {
		return tmallOutOrderMNSQueueName;
	}

	public static String getTmallShipOrderMNSQueueName() {
		return tmallShipOrderMNSQueueName;
	}

	public static void setTmallPosOrderMNSQueueName(String tmallPosOrderMNSQueueName) {
		AppUtil.tmallPosOrderMNSQueueName = tmallPosOrderMNSQueueName;
	}

	public static void setTmallRequestOrderMNSQueueName(String tmallRequestOrderMNSQueueName) {
		AppUtil.tmallRequestOrderMNSQueueName = tmallRequestOrderMNSQueueName;
	}

	public static void setTmallOutOrderMNSQueueName(String tmallOutOrderMNSQueueName) {
		AppUtil.tmallOutOrderMNSQueueName = tmallOutOrderMNSQueueName;
	}

	public static void setTmallShipOrderMNSQueueName(String tmallShipOrderMNSQueueName) {
		AppUtil.tmallShipOrderMNSQueueName = tmallShipOrderMNSQueueName;
	}

	public static MyThreadPoolTaskScheduler getPoolTaskScheduler() {
		return poolTaskScheduler;
	}

	public static void setPoolTaskScheduler(MyThreadPoolTaskScheduler poolTaskScheduler) {
		AppUtil.poolTaskScheduler = poolTaskScheduler;
	}  
	
	public static boolean checkMaoXiongBookCode(String systemBookCode){
		if(systemBookCode.equals("6465")){
			return true;
		}
		return false;
	}

	public static boolean checkPQYBookCode(String systemBookCode){
		if(systemBookCode.equals("6360") || systemBookCode.equals("4020") || systemBookCode.equals("4344")){
			return true;
		}
		return false;
	}

	public static boolean isImage(String suffix) {
		Pattern p = Pattern.compile("\\.(jpg|jpeg|png|bmp|gif)$");
		if(p.matcher(suffix.toLowerCase()).matches()) {
			return true;
		}
		return false;
	}
	
	public static boolean checkOutWithOrderPrice(String systemBookCode){
		if(systemBookCode.equals("6435") || systemBookCode.equals("6360") || systemBookCode.equals("4997")){
			return true;
		}
		return false;
		
	}
	

}
