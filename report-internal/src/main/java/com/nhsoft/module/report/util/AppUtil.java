package com.nhsoft.module.report.util;

import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.google.gson.*;

import com.nhsoft.module.report.dto.BranchDTO;
import com.nhsoft.module.report.dto.GsonIgnore;
import com.nhsoft.module.report.dto.ItemMatrixDTO;
import com.nhsoft.module.report.dto.PosItemDTO;
import com.nhsoft.module.report.model.*;
import com.nhsoft.module.report.param.CardUserType;
import com.nhsoft.module.report.param.PosItemTypeParam;
import com.nhsoft.report.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

@SuppressWarnings("rawtypes")
public class AppUtil {

	private static final Logger logger = LoggerFactory.getLogger(AppUtil.class);
	private static String filePath = null;
	private static String reportPath = null;
	private static String serverIp;
	private static String fileSeparator = System.getProperty("file.separator");
	private static Gson gson;

	private static final String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; ++i) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
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

	public static List<Integer> getUnStoreTypes() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(AppConstants.C_ITEM_TYPE_KIT);
		list.add(AppConstants.C_ITEM_TYPE_NON_STOCK);
		list.add(AppConstants.C_ITEM_TYPE_CUSTOMER_KIT);
		return list;
	}

	public static Branch getBranch(List<Branch> branchs, Integer branchNum) {
		for (int i = 0, len = branchs.size(); i < len; i++) {
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
		for (int i = 0, len = posItems.size(); i < len; i++) {
			if (posItems.get(i).getItemNum().equals(itemNum)) {
				return posItems.get(i);
			}
		}
		return null;
	}

	public static PosItemDTO getPosItemDTO(Integer itemNum, List<PosItemDTO> posItemDTOS) {
		if (posItemDTOS == null) {
			return null;
		}
		for (int i = 0, len = posItemDTOS.size(); i < len; i++) {
			if (posItemDTOS.get(i).getItemNum().equals(itemNum)) {
				return posItemDTOS.get(i);
			}
		}
		return null;
	}

	public static Supplier getSupplier(Integer supplierNum, List<Supplier> suppliers) {
		for (int i = 0, len = suppliers.size(); i < len; i++) {
			if (suppliers.get(i).getSupplierNum().equals(supplierNum)) {
				return suppliers.get(i);
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


	public static PosClient getPosClient(String clientFid, List<PosClient> posClients) {
		for (int i = 0, len = posClients.size(); i < len; i++) {
			if (posClients.get(i).getClientFid().equals(clientFid)) {
				return posClients.get(i);
			}
		}
		return null;
	}

	public static StoreMatrix getStoreMatrix(String systemBookCode, Integer branchNum, Integer itemNum,
			List<StoreMatrix> storeMatrixs) {
		for (int i = 0, len = storeMatrixs.size(); i < len; i++) {
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


	public static String getPosItemTypeName(List<PosItemTypeParam> posItemTypeParams, String categoryCode){
		if(posItemTypeParams== null){
			return null;
		}
		for (int i = 0,len = posItemTypeParams.size(); i < len; i++) {
			PosItemTypeParam posItemTypeParam = posItemTypeParams.get(i);
			if(categoryCode.equals(posItemTypeParam.getPosItemTypeCode())){
				return posItemTypeParam.getPosItemTypeName();
			}
		}
		return null;
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

	public static String getReportPath() {
		return reportPath;
	}

	public static String getFilePath() {
		return filePath;
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

	public static Object[] getInventoryAmount(List<Inventory> inventories, Integer itemNum, Integer itemMatrixNum) {
		BigDecimal amount = BigDecimal.ZERO;
		BigDecimal money = BigDecimal.ZERO;
		BigDecimal assistAmount = BigDecimal.ZERO;
		BigDecimal cost = BigDecimal.ZERO;
		boolean found = false;
		for (int i = 0; i < inventories.size(); i++) {
			Inventory inventory = inventories.get(i);
			if (inventory.getItemNum().equals(itemNum)) {
				found = true;
				if (!inventory.getInventoryMatrixs().isEmpty()) {
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
		if (amount.compareTo(BigDecimal.ZERO) > 0) {
			cost = money.divide(amount, 4, BigDecimal.ROUND_HALF_UP);
		}
		Object[] obj = new Object[5];
		obj[0] = amount;
		obj[1] = assistAmount;
		obj[2] = money;
		obj[3] = cost;
		obj[4] = found;
		return obj;
	}

	public static Object[] getInventoryAmount(List<Inventory> inventories, PosItemDTO posItem, Integer itemMatrixNum,
											  String lotNumber, BranchDTO branch) {
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
						if (org.apache.commons.lang.StringUtils.isNotEmpty(lotNumber)) {
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
				|| org.apache.commons.lang.StringUtils.isNotEmpty(lotNumber)) {
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
	public static boolean checkMaoXiongBookCode(String systemBookCode){
		if(systemBookCode.equals("6465")){
			return true;
		}
		return false;
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

	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
	}


	public static String toGetter(String fieldname) {

		if (fieldname == null || fieldname.length() == 0) {
			return null;
		}
		if (fieldname.length() > 2) {
			String second = fieldname.substring(1, 2);
			if (second.equals(second.toUpperCase())) {
				return new StringBuffer("get").append(fieldname).toString();
			}
		}

		fieldname = new StringBuffer("get").append(fieldname.substring(0, 1).toUpperCase())
				.append(fieldname.substring(1)).toString();

		return fieldname;
	}

	public static String cn2py(String sourceStr) {
		try {
			return PinyinHelper.getShortPinyin(sourceStr).toUpperCase();
		} catch (Exception e) {
			return null;
		}
	}


	public static CardUserType readCardType(Integer relatCardCardType, List<CardUserType> cardUserTypes) {
		for (int i = 0; i < cardUserTypes.size(); i++) {
			if (cardUserTypes.get(i).getTypeCode().equals(relatCardCardType.toString())) {
				return cardUserTypes.get(i);
			}
		}
		return null;
	}

}
