package com.nhsoft.module.report.rpc.impl;


import com.nhsoft.amazon.shared.AppConstants;
import com.nhsoft.module.report.dto.*;
import com.nhsoft.module.report.model.*;
import com.nhsoft.module.report.param.ChainDeliveryParam;
import com.nhsoft.module.report.param.ExpressCompany;
import com.nhsoft.module.report.param.PosItemTypeParam;
import com.nhsoft.module.report.rpc.MobileAppV2Rpc;
import com.nhsoft.module.report.service.*;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.report.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class MobileAppV2RpcImpl implements MobileAppV2Rpc {

	@Autowired
	private BranchService branchService;
	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private PosItemService posItemService;
	@Autowired
	private DistributionCentreMatrixService distributionCentreMatrixService;
	@Autowired
	private StoreMatrixService storeMatrixService;
	@Autowired
	private PosImageService posImageService;
	@Autowired
	private TransferOutOrderService transferOutOrderService;
	@Autowired
	private BranchItemService branchItemService;
	@Autowired
	private BookResourceService bookResourceService;
	@Autowired
	private InnerSettlementService innerSettlementService;
	@Autowired
	private InnerPreSettlementService innerPreSettlementService;
	@Autowired
	private TransferInOrderService transferInOrderService;
	@Autowired
	private OtherInoutService otherInoutService;
	@Autowired
	private CardSettlementService cardSettlementService;
	@Autowired
	private RequestOrderService requestOrderService;
	@Autowired
	private SystemBookService systemBookService;
	//private MailInfoJob mailInfoJob;
	@Autowired
	private BranchGroupService branchGroupService;
	@Autowired
	private BranchResourceService branchResourceService;
	private String mailUser;
	private String mailPassword;
	@Autowired
	private MobileAppV2Service mobileAppV2Service;
	
	private BigDecimal getObjectValue(List<Object[]> objects, Integer itemNum) {
		BigDecimal value = BigDecimal.ZERO;
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			if (object[0].equals(itemNum)) {
				return object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			}
		}
		return value;
	}

	@Override
	public List<PosItemShowDTO> findPosItemShowByPush(String systemBookCode, Integer centerBranchNum,
	                                                  Integer branchNum, String keyword, Integer rankFrom, Integer rankTo) {
		Branch branch = branchService.readWithNolock(systemBookCode, branchNum);
		List<Object[]> objects = inventoryService.findCenterStore(systemBookCode, centerBranchNum, null);
		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
		List<Integer> recommendItemNums = distributionCentreMatrixService
				.findRecommend(systemBookCode, centerBranchNum);
		List<PosItemShowDTO> list = new ArrayList<PosItemShowDTO>();
		int posItemSize = posItems.size();
		for (int i = 0; i < posItemSize; i++) {
			PosItem posItem = posItems.get(i);
			if (posItem.getItemDelTag() != null && posItem.getItemDelTag()) {
				continue;
			}
			if (posItem.getItemEliminativeFlag() != null && posItem.getItemEliminativeFlag()) {
				continue;
			}
			if (posItem.getItemType() == AppConstants.C_ITEM_TYPE_KIT) {
				continue;
			}
			if (posItem.getItemPurchaseScope().equals(AppConstants.ITEM_PURCHASE_SCOPE_SELFPRODUCT)
					|| posItem.getItemPurchaseScope().equals(AppConstants.ITEM_PURCHASE_SCOPE_CENTER)) {
				continue;
			}
			if (posItem.getItemStatus() != null && posItem.getItemStatus() == AppConstants.ITEM_STATUS_SLEEP) {
				continue;
			}
			if (!recommendItemNums.contains(posItem.getItemNum())) {
				continue;
			}
			BigDecimal inventoryAmount = getObjectValue(objects, posItem.getItemNum());
			if (inventoryAmount.compareTo(BigDecimal.ZERO) <= 0) {
				continue;
			}

			if (StringUtils.isNotEmpty(keyword)) {

				boolean findBar = false;
				List<ItemBar> itemBars = posItem.getItemBars();
				for (int j = 0; j < itemBars.size(); j++) {
					ItemBar itemBar = itemBars.get(j);
					if (itemBar.getItemBarCode().contains(keyword)) {
						findBar = true;
						break;
					}
				}
				if (!posItem.getItemCode().contains(keyword) && !posItem.getItemName().contains(keyword)
						&& !posItem.getStoreItemPinyin().contains(keyword.toUpperCase()) && !findBar) {
					continue;
				}
			}

			PosItemShowDTO posItemShow = new PosItemShowDTO();
			posItemShow.setCoolQty(inventoryAmount); // 用于排序
			posItemShow.setItemNum(posItem.getItemNum());
			posItemShow.setItemCode(posItem.getItemCode());
			posItemShow.setItemName(posItem.getItemName());
			posItemShow.setItemUnit(posItem.getItemUnit());
			posItemShow.setItemPinYin(posItem.getStoreItemPinyin());
			posItemShow.setItemSpec(posItem.getItemSpec());
			posItemShow.setItemPlace(posItem.getItemPlace());
			posItemShow.setItemType(posItem.getItemType());
			posItemShow.setItemTransferRate(posItem.getItemTransferRate());
			posItemShow.setItemTransferUnit(posItem.getItemTransferUnit());
			posItemShow.setItemBrand(posItem.getItemBrand());
			posItemShow.setItemBranchSalePrice(posItem.getItemRegularPrice());
			posItemShow.setItemBranchTransferPrice(posItem.getItemTransferPrice());
			posItemShow.setItemBarCode("");
			if (posItem.getItemBars().size() > 0) {
				posItemShow.setItemBarCode(posItem.getItemBars().get(0).getItemBarCode());
			}
			posItemShow.setItemTypeName(posItem.getItemCategory());
			list.add(posItemShow);
		}
		// 按库存逆序
		// System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		Comparator<PosItemShowDTO> comparator = new Comparator<PosItemShowDTO>() {

			@Override
			public int compare(PosItemShowDTO arg0, PosItemShowDTO arg1) {
				if (arg0.getCoolQty() == null && arg1.getCoolQty() != null) {
					return 1;
				}
				if (arg0.getCoolQty() != null && arg1.getCoolQty() == null) {
					return -1;
				}
				if (arg0.getCoolQty() == null && arg1.getCoolQty() == null) {
					return 1;
				}
				if (arg0.getCoolQty().compareTo(arg1.getCoolQty()) == 0) {
					return 0;
				}
				return -arg0.getCoolQty().compareTo(arg1.getCoolQty());
			}
		};
		Collections.sort(list, comparator);
		List<PosItemShowDTO> returnList = new ArrayList<PosItemShowDTO>();
		List<Integer> itemNums = new ArrayList<Integer>();
		int count = list.size();
		for (int i = rankFrom; i < rankTo; i++) {
			if (i >= count) {
				break;
			}
			PosItemShowDTO posItemShow = list.get(i);
			itemNums.add(posItemShow.getItemNum());
			returnList.add(posItemShow);
		}
		if (itemNums.size() == 0) {
			return returnList;
		}
		// 门店配送价 零售价
		List<StoreMatrix> storeMatrixs = storeMatrixService.findByBranch(systemBookCode, branchNum, itemNums);
		List<PosImage> posImages = posImageService.find(systemBookCode, itemNums);
		int returnSize = returnList.size();
		int posImageSize = posImages.size();
		for (int i = 0; i < returnSize; i++) {
			PosItemShowDTO posItemShow = returnList.get(i);
			StoreMatrix storeMatrix = AppUtil.getStoreMatrix(systemBookCode, branchNum, posItemShow.getItemNum(),
					storeMatrixs);
			if (storeMatrix != null) {
				// 门店配送价
				if ((storeMatrix.getStoreMatrixRquestEnabled())
						&& (storeMatrix.getStoreMatrixTransferPrice().compareTo(BigDecimal.ZERO) > 0)) {
					posItemShow.setItemBranchTransferPrice(storeMatrix.getStoreMatrixTransferPrice());
				}
				// 门店零售价
				if(branch.getBranchMatrixPriceActived() && storeMatrix != null
						&& storeMatrix.getStoreMatrixPriceEnabled() != null && storeMatrix.getStoreMatrixPriceEnabled()
						&& (storeMatrix.getStoreMatrixRegularPrice().compareTo(BigDecimal.ZERO) > 0)) {
					posItemShow.setItemBranchSalePrice(storeMatrix.getStoreMatrixRegularPrice());
				}
			}

			for (int j = 0; j < posImageSize; j++) {
				PosImage posImage = posImages.get(j);
				if (posImage.getItemNum().equals(posItemShow.getItemNum())) {

					if (StringUtils.isEmpty(posImage.getPosImageUrl())) {
						continue;
					}
					if (posImage.getPosImageDefault() != null && posImage.getPosImageDefault()) {
						posItemShow.setDefaultImageUrl(posImage.getPosImageUrl());

					} else {
						posItemShow.getImageUrls().add(posImage.getPosImageUrl());
					}

				}
			}
		}
		return returnList;
	}

	@Override
	public List<PosItemShowDTO> findPosItemShowByNew(String systemBookCode, Integer centerBranchNum, Integer branchNum,
			String keyword, Integer rankFrom, Integer rankTo) {
		Branch branch = branchService.readWithNolock(systemBookCode, branchNum);
		List<Integer> fileterItems = transferOutOrderService.findTransferedItems(systemBookCode,
				branchNum, centerBranchNum);
		List<Object[]> objects = inventoryService.findCenterStore(systemBookCode, centerBranchNum, null);
		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
		List<PosItemShowDTO> list = new ArrayList<PosItemShowDTO>();
		// 按建档日期逆序
		Comparator<PosItem> comparator = new Comparator<PosItem>() {

			@Override
			public int compare(PosItem arg0, PosItem arg1) {
				if (arg0.getItemCreateTime() == null && arg1.getItemCreateTime() != null) {
					return 1;
				}
				if (arg1.getItemCreateTime() == null && arg0.getItemCreateTime() != null) {
					return -1;
				}
				if (arg0.getItemCreateTime() == null && arg1.getItemCreateTime() == null) {
					return 1;
				}
				if (DateUtil.getDateTimeString(arg0.getItemCreateTime()).equals(
						DateUtil.getDateTimeString(arg1.getItemCreateTime()))) {
					return 0;
				}
				return -arg0.getItemCreateTime().compareTo(arg1.getItemCreateTime());
			}
		};
		Collections.sort(posItems, comparator);
		int posItemSize = posItems.size();
		for (int i = 0; i < posItemSize; i++) {
			PosItem posItem = posItems.get(i);
			if (posItem.getItemDelTag() != null && posItem.getItemDelTag()) {
				continue;
			}
			if (posItem.getItemEliminativeFlag() != null && posItem.getItemEliminativeFlag()) {
				continue;
			}
			if (posItem.getItemType() == AppConstants.C_ITEM_TYPE_KIT) {
				continue;
			}
			if (posItem.getItemPurchaseScope().equals(AppConstants.ITEM_PURCHASE_SCOPE_SELFPRODUCT)
					|| posItem.getItemPurchaseScope().equals(AppConstants.ITEM_PURCHASE_SCOPE_CENTER)) {
				continue;
			}
			if (posItem.getItemStatus() != null && posItem.getItemStatus() == AppConstants.ITEM_STATUS_SLEEP) {
				continue;
			}
			if (fileterItems.contains(posItem.getItemNum())) {
				continue;
			}
			BigDecimal inventoryAmount = getObjectValue(objects, posItem.getItemNum());
			if (inventoryAmount.compareTo(BigDecimal.ZERO) <= 0) {
				continue;
			}
			if (StringUtils.isNotEmpty(keyword)) {

				boolean findBar = false;
				List<ItemBar> itemBars = posItem.getItemBars();
				for (int j = 0; j < itemBars.size(); j++) {
					ItemBar itemBar = itemBars.get(j);
					if (itemBar.getItemBarCode().contains(keyword)) {
						findBar = true;
						break;
					}
				}
				if (!posItem.getItemCode().contains(keyword) && !posItem.getItemName().contains(keyword)
						&& !posItem.getStoreItemPinyin().contains(keyword.toUpperCase()) && !findBar) {
					continue;
				}
			}
			PosItemShowDTO posItemShow = new PosItemShowDTO();
			posItemShow.setItemNum(posItem.getItemNum());
			posItemShow.setItemCode(posItem.getItemCode());
			posItemShow.setItemName(posItem.getItemName());
			posItemShow.setItemUnit(posItem.getItemUnit());
			posItemShow.setItemPinYin(posItem.getStoreItemPinyin());
			posItemShow.setItemSpec(posItem.getItemSpec());
			posItemShow.setItemPlace(posItem.getItemPlace());
			posItemShow.setItemType(posItem.getItemType());
			posItemShow.setItemTransferRate(posItem.getItemTransferRate());
			posItemShow.setItemTransferUnit(posItem.getItemTransferUnit());
			posItemShow.setItemBrand(posItem.getItemBrand());
			posItemShow.setItemBranchSalePrice(posItem.getItemRegularPrice());
			posItemShow.setItemBranchTransferPrice(posItem.getItemTransferPrice());
			posItemShow.setItemBarCode("");
			if (posItem.getItemBars().size() > 0) {
				posItemShow.setItemBarCode(posItem.getItemBars().get(0).getItemBarCode());
			}
			posItemShow.setItemTypeName(posItem.getItemCategory());
			list.add(posItemShow);
		}
		List<PosItemShowDTO> returnList = new ArrayList<PosItemShowDTO>();
		List<Integer> itemNums = new ArrayList<Integer>();
		int count = list.size();
		for (int i = rankFrom; i < rankTo; i++) {
			if (i >= count) {
				break;
			}
			PosItemShowDTO posItemShow = list.get(i);
			itemNums.add(posItemShow.getItemNum());
			returnList.add(posItemShow);
		}
		if (itemNums.size() == 0) {
			return returnList;
		}
		// 门店配送价 零售价
		List<StoreMatrix> storeMatrixs = storeMatrixService.findByBranch(systemBookCode, branchNum, itemNums);
		List<PosImage> posImages = posImageService.find(systemBookCode, itemNums);
		int returnSize = returnList.size();
		int posImageSize = posImages.size();
		for (int i = 0; i < returnSize; i++) {
			PosItemShowDTO posItemShow = returnList.get(i);
			StoreMatrix storeMatrix = AppUtil.getStoreMatrix(systemBookCode, branchNum, posItemShow.getItemNum(),
					storeMatrixs);
			if (storeMatrix != null) {
				// 门店配送价
				if ((storeMatrix.getStoreMatrixRquestEnabled())
						&& (storeMatrix.getStoreMatrixTransferPrice().compareTo(BigDecimal.ZERO) > 0)) {
					posItemShow.setItemBranchTransferPrice(storeMatrix.getStoreMatrixTransferPrice());
				}
				// 门店零售价
				if(branch.getBranchMatrixPriceActived() && storeMatrix != null
						&& storeMatrix.getStoreMatrixPriceEnabled() != null && storeMatrix.getStoreMatrixPriceEnabled()
						&& (storeMatrix.getStoreMatrixRegularPrice().compareTo(BigDecimal.ZERO) > 0)) {
					posItemShow.setItemBranchSalePrice(storeMatrix.getStoreMatrixRegularPrice());
				}
			}

			for (int j = 0; j < posImageSize; j++) {
				PosImage posImage = posImages.get(j);
				if (posImage.getItemNum().equals(posItemShow.getItemNum())) {
					if (StringUtils.isEmpty(posImage.getPosImageUrl())) {
						continue;
					}

					if (posImage.getPosImageDefault() != null && posImage.getPosImageDefault()) {
						posItemShow.setDefaultImageUrl(posImage.getPosImageUrl());
					} else {
						posItemShow.getImageUrls().add(posImage.getPosImageUrl());
					}
				}
			}
		}
		return returnList;
	}

	@Override
	public List<TwoTypeAndTwoValue> findCategoryShowByNew(String systemBookCode, Integer centerBranchNum,
			Integer branchNum) {
		List<Integer> fileterItems = transferOutOrderService.findTransferedItems(systemBookCode,
				branchNum, centerBranchNum);
		List<Object[]> objects = inventoryService.findCenterStore(systemBookCode, centerBranchNum, null);
		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
		Map<String, TwoTypeAndTwoValue> map = new HashMap<String, TwoTypeAndTwoValue>();
		List<PosItemTypeParam> posItemTypeParams = bookResourceService.findPosItemTypeParamsInCache(systemBookCode);
		List<Integer> requestItemNums = branchItemService.findItemNums(systemBookCode, centerBranchNum);
		int typeParamSize = posItemTypeParams.size();
		int posItemSize = posItems.size();
		for(int i = 0;i < typeParamSize;i++){
			
			PosItemTypeParam param = posItemTypeParams.get(i);
			if(param.getPosItemTypeFatherCode() == null){
				TwoTypeAndTwoValue twoTypeAndTwoValue = new TwoTypeAndTwoValue();
				twoTypeAndTwoValue.setName(param.getPosItemTypeName());
				twoTypeAndTwoValue.setType(param.getPosItemTypeCode());
				twoTypeAndTwoValue.setCount(0);
				map.put(param.getPosItemTypeCode(), twoTypeAndTwoValue);
				
			}
		}
		for (int i = 0; i < posItemSize; i++) {
			PosItem posItem = posItems.get(i);
			if (posItem.getItemDelTag() != null && posItem.getItemDelTag()) {
				continue;
			}
			if (posItem.getItemEliminativeFlag() != null && posItem.getItemEliminativeFlag()) {
				continue;
			}
			if (posItem.getItemType() == AppConstants.C_ITEM_TYPE_KIT) {
				continue;
			}

			if (posItem.getItemPurchaseScope().equals(AppConstants.ITEM_PURCHASE_SCOPE_SELFPRODUCT)
					|| posItem.getItemPurchaseScope().equals(AppConstants.ITEM_PURCHASE_SCOPE_CENTER)) {
				continue;
			}
			if (posItem.getItemStatus() != null && posItem.getItemStatus() == AppConstants.ITEM_STATUS_SLEEP) {
				continue;
			}
			if (fileterItems.contains(posItem.getItemNum())) {
				continue;
			}
			if (requestItemNums.size() > 0) {
				if (!requestItemNums.contains(posItem.getItemNum())) {
					continue;
				}
			}		
			BigDecimal inventoryAmount = getObjectValue(objects, posItem.getItemNum());
			if (inventoryAmount.compareTo(BigDecimal.ZERO) <= 0) {
				continue;
			}			

			String topCode = null;
			String topName = null;
			PosItemTypeParam typeParam = AppUtil.getTopCategory(posItemTypeParams, posItem.getItemCategoryCode());
			if (typeParam != null) {
				topCode = typeParam.getPosItemTypeCode();
				topName = typeParam.getPosItemTypeName();
			} else {
				topCode = posItem.getItemCategoryCode();
				topName = posItem.getItemCategory();
			}
			TwoTypeAndTwoValue twoTypeAndTwoValue = map.get(topCode);
			if (twoTypeAndTwoValue == null) {
				twoTypeAndTwoValue = new TwoTypeAndTwoValue();
				twoTypeAndTwoValue.setName(topName);
				twoTypeAndTwoValue.setType(topCode);
				twoTypeAndTwoValue.setCount(0);
				map.put(topCode, twoTypeAndTwoValue);
			}
			twoTypeAndTwoValue.setCount(twoTypeAndTwoValue.getCount() + 1);
		}
		List<TwoTypeAndTwoValue> list = new ArrayList<TwoTypeAndTwoValue>(map.values());
		Collections.sort(list, new Comparator<TwoTypeAndTwoValue>() {

			@Override
			public int compare(TwoTypeAndTwoValue o1, TwoTypeAndTwoValue o2) {
				return o1.getType().compareTo(o2.getType());
			}
		});
		return list;
	}

	@Override
	public List<PosItemShowDTO> findNewPosItemShowByCategory(String systemBookCode, Integer centerBranchNum,
			Integer branchNum, String categoryCode, String keyword, Integer rankFrom, Integer rankTo) {
		List<Integer> fileterItems = transferOutOrderService.findTransferedItems(systemBookCode,
				branchNum, centerBranchNum);
		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
		List<PosItemShowDTO> list = new ArrayList<PosItemShowDTO>();
		List<PosItemTypeParam> posItemTypeParams = bookResourceService.findPosItemTypeParamsInCache(systemBookCode);
		List<Object[]> objects = inventoryService.findCenterStore(systemBookCode, centerBranchNum, null);
		Branch branch = branchService.readWithNolock(systemBookCode, branchNum);

		List<String> categoryCodes = null;
		if (StringUtils.isNotEmpty(categoryCode)) {
			categoryCodes = AppUtil.findSubCategory(posItemTypeParams, categoryCode);
		}
		Date initDate = DateUtil.getDateTimeHMS(AppConstants.INIT_TIME);
		List<Integer> requestItemNums = branchItemService.findItemNums(systemBookCode, centerBranchNum);

		for (int i = 0; i < posItems.size(); i++) {
			PosItem posItem = posItems.get(i);
	
			if (posItem.getItemDelTag() != null && posItem.getItemDelTag()) {
				continue;
			}
			if (posItem.getItemEliminativeFlag() != null && posItem.getItemEliminativeFlag()) {
				continue;
			}
			if (posItem.getItemType() == AppConstants.C_ITEM_TYPE_KIT) {
				continue;
			}

			if (posItem.getItemPurchaseScope().equals(AppConstants.ITEM_PURCHASE_SCOPE_SELFPRODUCT)
					|| posItem.getItemPurchaseScope().equals(AppConstants.ITEM_PURCHASE_SCOPE_CENTER)) {
				continue;
			}
			if (posItem.getItemStatus() != null && posItem.getItemStatus() == AppConstants.ITEM_STATUS_SLEEP) {
				continue;
			}
			if (fileterItems.contains(posItem.getItemNum())) {
				continue;
			}
			if (requestItemNums.size() > 0) {
				if (!requestItemNums.contains(posItem.getItemNum())) {
					continue;
				}
			}
		
			BigDecimal inventoryAmount = getObjectValue(objects, posItem.getItemNum());
			if (inventoryAmount.compareTo(BigDecimal.ZERO) <= 0) {
				continue;
			}
			
			if (categoryCodes != null) {
				if (!categoryCodes.contains(posItem.getItemCategoryCode())) {
					continue;
				}
			}
			if (StringUtils.isNotEmpty(keyword)) {

				boolean findBar = false;
				List<ItemBar> itemBars = posItem.getItemBars();
				for (int j = 0; j < itemBars.size(); j++) {
					ItemBar itemBar = itemBars.get(j);
					if (itemBar.getItemBarCode().contains(keyword)) {
						findBar = true;
						break;
					}
				}
				if (!posItem.getItemCode().contains(keyword) && !posItem.getItemName().contains(keyword)
						&& !posItem.getStoreItemPinyin().contains(keyword.toUpperCase()) && !findBar) {
					continue;
				}
			}

			PosItemShowDTO posItemShow = new PosItemShowDTO();
			posItemShow.setItemNum(posItem.getItemNum());
			posItemShow.setItemCode(posItem.getItemCode());
			posItemShow.setItemName(posItem.getItemName());
			posItemShow.setItemUnit(posItem.getItemUnit());
			posItemShow.setItemPinYin(posItem.getStoreItemPinyin());
			posItemShow.setItemSpec(posItem.getItemSpec());
			posItemShow.setItemPlace(posItem.getItemPlace());
			posItemShow.setItemType(posItem.getItemType());
			posItemShow.setItemTransferRate(posItem.getItemTransferRate());
			posItemShow.setItemTransferUnit(posItem.getItemTransferUnit());
			posItemShow.setItemBrand(posItem.getItemBrand());
			posItemShow.setItemBranchSalePrice(posItem.getItemRegularPrice());
			posItemShow.setItemBranchTransferPrice(posItem.getItemTransferPrice());
			posItemShow.setItemBarCode("");
			if (posItem.getItemBars().size() > 0) {
				posItemShow.setItemBarCode(posItem.getItemBars().get(0).getItemBarCode());
			}
			PosItemTypeParam typeParam = AppUtil.getTopCategory(posItemTypeParams, posItem.getItemCategoryCode());
			if (typeParam != null) {
				posItemShow.setItemCategoryCode(typeParam.getPosItemTypeCode());
				posItemShow.setItemCategoryName(typeParam.getPosItemTypeName());
			} else {
				posItemShow.setItemCategoryCode(posItem.getItemCategoryCode());
				posItemShow.setItemCategoryName(posItem.getItemCategory());
			}
			posItemShow.setItemCreateTime(posItem.getItemCreateTime() == null ? initDate : posItem.getItemCreateTime());
			posItemShow.setItemTypeName(posItem.getItemCategory());
			list.add(posItemShow);
		}
		if(list.isEmpty()){
			return list;
		}

		// 按建档日期逆序
		Comparator<PosItemShowDTO> comparator = new Comparator<PosItemShowDTO>() {

			@Override
			public int compare(PosItemShowDTO arg0, PosItemShowDTO arg1) {

				return -arg0.getItemCreateTime().compareTo(arg1.getItemCreateTime());
			}
		};
		Collections.sort(list, comparator);

		List<PosItemShowDTO> returnList = new ArrayList<PosItemShowDTO>();
		int count = list.size();
		List<Integer> itemNums = new ArrayList<Integer>();
		for (int i = rankFrom; i < rankTo; i++) {
			if (i >= count) {
				break;
			}
			PosItemShowDTO posItemShow = list.get(i);
			returnList.add(posItemShow);
			itemNums.add(posItemShow.getItemNum());
		}
		// 门店配送价 零售价
		List<StoreMatrix> storeMatrixs = storeMatrixService.findByBranch(systemBookCode, branchNum, itemNums);
		List<PosImage> posImages = posImageService.find(systemBookCode, itemNums);
		for (int i = 0; i < returnList.size(); i++) {
			PosItemShowDTO posItemShow = returnList.get(i);
			StoreMatrix storeMatrix = AppUtil.getStoreMatrix(systemBookCode, branchNum, posItemShow.getItemNum(),
					storeMatrixs);
			if (storeMatrix != null) {
				// 门店配送价
				if ((storeMatrix.getStoreMatrixRquestEnabled())
						&& (storeMatrix.getStoreMatrixTransferPrice().compareTo(BigDecimal.ZERO) > 0)) {
					posItemShow.setItemBranchTransferPrice(storeMatrix.getStoreMatrixTransferPrice());
				}
				// 门店零售价
				if(branch.getBranchMatrixPriceActived() && storeMatrix != null
						&& storeMatrix.getStoreMatrixPriceEnabled() != null && storeMatrix.getStoreMatrixPriceEnabled()
						&& (storeMatrix.getStoreMatrixRegularPrice().compareTo(BigDecimal.ZERO) > 0)) {
					posItemShow.setItemBranchSalePrice(storeMatrix.getStoreMatrixRegularPrice());
				}
			}
			for (int j = 0; j < posImages.size(); j++) {
				PosImage posImage = posImages.get(j);
				if (posImage.getItemNum().equals(posItemShow.getItemNum())) {
					if (StringUtils.isEmpty(posImage.getPosImageUrl())) {
						continue;
					}
					
					if (posImage.getPosImageDefault() != null && posImage.getPosImageDefault()) {
						posItemShow.setDefaultImageUrl(posImage.getPosImageUrl());
					} else {
						posItemShow.getImageUrls().add(posImage.getPosImageUrl());
					}
				}
			}
		}
		return returnList;
	}

	@Override
	public AccountPayDTO findAccountPays(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		dateFrom = DateUtil.getMinOfDate(dateFrom);
		dateTo = DateUtil.getMaxOfDate(dateTo);
		List<Integer> branchNums = new ArrayList<Integer>();
		branchNums.add(branchNum);

		Integer centerBranchNum = AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM;

		AccountPayDTO accountPay = new AccountPayDTO();
		List<Object[]> objects = innerSettlementService.findMoneyByBranchNums(systemBookCode, centerBranchNum, dateFrom,
				dateTo, branchNums, true);
		if (objects.size() > 0) {
			Object[] object = objects.get(0);
			BigDecimal money = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			BigDecimal discountMoney = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			accountPay.setDiscountMoney(accountPay.getDiscountMoney().add(discountMoney));
			accountPay.setActualDueMoney(accountPay.getActualDueMoney().add(money));
		}

		objects = innerPreSettlementService.findMoneyByBranchNums(systemBookCode, centerBranchNum, dateFrom, dateTo,
				branchNums);
		if (objects.size() > 0) {
			Object[] object = objects.get(0);
			BigDecimal money = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			accountPay.setActualDueMoney(accountPay.getActualDueMoney().add(money));
		}
		// 上期预结算金额
		objects = innerPreSettlementService.findDueMoney(systemBookCode, centerBranchNum, branchNums, null, dateTo);
		if (objects.size() > 0) {
			Object[] object = objects.get(0);
			BigDecimal money = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			accountPay.setTotalDueMoney(accountPay.getTotalDueMoney().subtract(money));
		}

		objects = transferOutOrderService.findMoneyByBranchNums(systemBookCode, centerBranchNum, dateFrom, dateTo,
				branchNums);
		if (objects.size() > 0) {
			Object[] object = objects.get(0);
			BigDecimal money = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			accountPay.setDueMoney(accountPay.getDueMoney().add(money));
		}
		// 上期调出金额
		objects = transferOutOrderService
				.findDueMoney(systemBookCode, centerBranchNum, branchNums, null, dateTo);
		if (objects.size() > 0) {
			Object[] object = objects.get(0);
			BigDecimal money = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			accountPay.setTotalDueMoney(accountPay.getTotalDueMoney().add(money));
		}

		objects = transferInOrderService.findMoneyByBranchNums(systemBookCode, centerBranchNum, dateFrom, dateTo,
				branchNums);
		if (objects.size() > 0) {
			Object[] object = objects.get(0);
			BigDecimal money = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			accountPay.setDueMoney(accountPay.getDueMoney().add(money));
		}
		// 上期调入金额
		objects = transferInOrderService.findDueMoney(systemBookCode, centerBranchNum, branchNums, null, dateTo);
		if (objects.size() > 0) {
			Object[] object = objects.get(0);
			BigDecimal money = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			accountPay.setTotalDueMoney(accountPay.getTotalDueMoney().add(money));
		}

		objects = cardSettlementService.findBranchsMoney(systemBookCode, centerBranchNum, branchNums, dateFrom, dateTo);
		if (objects.size() > 0) {
			Object[] object = objects.get(0);
			BigDecimal money = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			accountPay.setDueMoney(accountPay.getDueMoney().add(money));
		}
		// 上期卡结算金额
		objects = cardSettlementService.findBranchsMoney(systemBookCode, centerBranchNum, branchNums, null, dateTo);
		if (objects.size() > 0) {
			Object[] object = objects.get(0);
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			accountPay.setTotalDueMoney(accountPay.getTotalDueMoney().add(money));
		}

		objects = otherInoutService.findBranchsMoney(systemBookCode, centerBranchNum, dateFrom, dateTo, branchNums);
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			boolean flag = (Boolean) object[1];
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			if (flag) {
				accountPay.setDueMoney(accountPay.getDueMoney().add(money));

			} else {
				accountPay.setDueMoney(accountPay.getDueMoney().subtract(money));
			}
		}
		// 上期收支金额
		objects = otherInoutService.findBranchsMoney(systemBookCode, centerBranchNum, null, dateTo, branchNums);
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			BigDecimal money = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];			
			accountPay.setTotalDueMoney(accountPay.getTotalDueMoney().add(money));

		}
		accountPay.setNotDueMoney(accountPay.getDueMoney().subtract(accountPay.getActualDueMoney())
				.subtract(accountPay.getDiscountMoney()));
		// 账户余额
		BigDecimal preMoney = innerPreSettlementService.readBranchUnPaidMoney(systemBookCode, branchNum, centerBranchNum);
		BigDecimal requestMoney = requestOrderService.readBranchUnOutMoney(systemBookCode, branchNum, centerBranchNum);
		BigDecimal outMoney = transferOutOrderService.readBranchUnPaidMoney(systemBookCode, branchNum, centerBranchNum);
		BigDecimal inMoney = transferInOrderService.readBranchUnPaidMoney(systemBookCode, branchNum, centerBranchNum);
		BigDecimal cardMoney = cardSettlementService.readBranchUnPaidMoney(systemBookCode, branchNum, centerBranchNum);
		BigDecimal otherMoney = otherInoutService.getUnPaidMoney(systemBookCode, centerBranchNum, branchNum, null, null, 0);
		BigDecimal balance = preMoney.subtract(requestMoney).subtract(outMoney).subtract(inMoney).subtract(cardMoney)
				.add(otherMoney);
		accountPay.setBalanceMoney(balance);
		return accountPay;
	}

	@Override
	public List<AccountDetailDTO> findAccountDetails(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, Integer rankFrom, Integer rankTo) {
		int offset = rankFrom;
		int limit = rankTo - rankFrom;

		Integer centerBranchNum = AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM;
		List<Integer> branchNums = new ArrayList<Integer>();
		branchNums.add(branchNum);

		List<AccountDetailDTO> list = new ArrayList<AccountDetailDTO>();
		List<TransferInOrder> transferInOrders = transferInOrderService.findBySettleBranch(systemBookCode, branchNum,
				centerBranchNum, dateFrom, dateTo);
		BigDecimal inMoney = BigDecimal.ZERO;
		List<Object[]> objects = transferInOrderService.findDueMoney(systemBookCode, centerBranchNum, branchNums , null, dateTo);
		if(objects.size() > 0){
			Object[] object = objects.get(0);
			inMoney = object[1] == null?BigDecimal.ZERO:(BigDecimal)object[1];
		}
		for (int i = 0; i < transferInOrders.size(); i++) {
			TransferInOrder transferInOrder = transferInOrders.get(i);
			AccountDetailDTO data = new AccountDetailDTO();
			data.setBranchNum(transferInOrder.getInBranchNum());
			data.setAccountDate(transferInOrder.getInOrderPaymentDate());
			data.setAccountFid(transferInOrder.getInOrderFid());
			data.setAccountMemo(transferInOrder.getInOrderMemo());
			data.setAccountDelMoney(transferInOrder.getInOrderDueMoney() == null ? BigDecimal.ZERO : transferInOrder
					.getInOrderDueMoney().negate());
			data.setAccountType("调入单");
			list.add(data);
		}

		List<TransferOutOrder> transferOutOrders = transferOutOrderService.findBySettleBranch(systemBookCode, branchNum,
				centerBranchNum, dateFrom, dateTo);
		BigDecimal outMoney = BigDecimal.ZERO;
		objects = transferOutOrderService.findDueMoney(systemBookCode, centerBranchNum, branchNums, null, dateTo);
		if(objects.size() > 0){
			Object[] object = objects.get(0);
			outMoney = object[1] == null?BigDecimal.ZERO:(BigDecimal)object[1];
		}
		for (int i = 0; i < transferOutOrders.size(); i++) {
			TransferOutOrder transferOutOrder = transferOutOrders.get(i);
			AccountDetailDTO data = new AccountDetailDTO();
			data.setAccountDate(transferOutOrder.getOutOrderPaymentDate());
			data.setAccountFid(transferOutOrder.getOutOrderFid());
			data.setAccountMemo(transferOutOrder.getOutOrderMemo());
			data.setBranchNum(transferOutOrder.getBranchNum());
			data.setAccountAddMoney(transferOutOrder.getOutOrderDueMoney() == null ? BigDecimal.ZERO : transferOutOrder
					.getOutOrderDueMoney());
			data.setAccountType("调出单");
			list.add(data);
		}

		List<InnerPreSettlement> innerPreSettlements = innerPreSettlementService.findBySettleBranch(systemBookCode,
				branchNum, centerBranchNum, dateFrom, dateTo);
		objects = innerPreSettlementService.findDueMoney(systemBookCode, centerBranchNum, branchNums, null, dateTo);
		BigDecimal preMoney = BigDecimal.ZERO;
		if(objects.size() > 0){
			Object[] object = objects.get(0);
			preMoney = object[1] == null?BigDecimal.ZERO:(BigDecimal)object[1];
		}
		for (int i = 0; i < innerPreSettlements.size(); i++) {
			InnerPreSettlement innerPreSettlement = innerPreSettlements.get(i);
			AccountDetailDTO data = new AccountDetailDTO();
			data.setAccountDate(innerPreSettlement.getPreSettlementAuditTime());
			data.setAccountFid(innerPreSettlement.getPreSettlementNo());
			data.setAccountMemo(innerPreSettlement.getPreSettlementMemo());
			data.setBranchNum(innerPreSettlement.getBranchNum());
			data.setAccountDelMoney(innerPreSettlement.getPreSettlementPaid());
			data.setAccountType("加盟店预收");
			list.add(data);
		}

		List<OtherInout> otherInouts = otherInoutService.findByBranch(systemBookCode, centerBranchNum, branchNum, dateFrom,
				dateTo);
		BigDecimal otherMoney = BigDecimal.ZERO;
		for (int i = 0; i < otherInouts.size(); i++) {
			OtherInout otherInout = otherInouts.get(i);
			AccountDetailDTO data = new AccountDetailDTO();
			data.setAccountDate(otherInout.getOtherInoutAuditTime());
			data.setAccountFid(otherInout.getOtherInoutBillNo());
			data.setAccountMemo(otherInout.getOtherInoutMemo());
			data.setBranchNum(otherInout.getBranchNum());
			data.setAccountType("其他收支");
			if (otherInout.getOtherInoutFlag()) {
				data.setAccountAddMoney(otherInout.getOtherInoutMoney());

			} else {
				data.setAccountDelMoney(otherInout.getOtherInoutMoney());
			}
			list.add(data);
		}
		objects = otherInoutService.findMoneybyBranch(systemBookCode, centerBranchNum, branchNum, null, dateTo);
		for(int i = 0;i < objects.size();i++){
			Object[] object = objects.get(i);
			BigDecimal money = object[2] == null?BigDecimal.ZERO:(BigDecimal)object[2];				
			otherMoney = otherMoney.add(money);
			
		}

		List<CardSettlement> cardSettlements = cardSettlementService.findBySettleBranch(systemBookCode, centerBranchNum,
				branchNum, dateFrom, dateTo);
		BigDecimal cardMoney = BigDecimal.ZERO;
		for (int i = 0; i < cardSettlements.size(); i++) {
			CardSettlement cardSettlement = cardSettlements.get(i);
			AccountDetailDTO data = new AccountDetailDTO();
			data.setAccountDate(cardSettlement.getCardSettlementAuditTime());
			data.setAccountFid(cardSettlement.getCardSettlementNo());
			data.setAccountMemo(cardSettlement.getCardSettlementMemo());
			data.setBranchNum(cardSettlement.getBranchNum());
			data.setAccountAddMoney(cardSettlement.getCardSettlementMoney());
			data.setAccountType("卡结算单");
			list.add(data);
		}
		objects = cardSettlementService.findMoneyBySettleBranch(systemBookCode, centerBranchNum, branchNum, null, dateTo);
		if(objects.size() > 0){
			Object[] object = objects.get(0);
			BigDecimal due = object[0] == null?BigDecimal.ZERO:(BigDecimal)object[0];
			BigDecimal paid = object[1] == null?BigDecimal.ZERO:(BigDecimal)object[1];
			BigDecimal discount = object[2] == null?BigDecimal.ZERO:(BigDecimal)object[2];
			cardMoney = due.subtract(paid).subtract(discount);
		}
		List<InnerSettlement> innerSettlements = innerSettlementService.findBySettleBranchNum(systemBookCode,
				centerBranchNum, branchNum, dateFrom, dateTo);
		for (int i = 0; i < innerSettlements.size(); i++) {
			InnerSettlement innerSettlement = innerSettlements.get(i);
			AccountDetailDTO data = new AccountDetailDTO();
			data.setAccountDate(innerSettlement.getInnerSettlementAuditTime());
			data.setAccountFid(innerSettlement.getInnerSettlementNo());
			data.setAccountMemo(innerSettlement.getInnerSettlementMemo());
			data.setBranchNum(innerSettlement.getBranchNum());
			if (innerSettlement.getInnerSettlementPaymentType().equals(AppConstants.PAYMENT_PRE_IN_SETTLEMENT)) {
				data.setAccountDelMoney(innerSettlement.getInnerSettlementTotalDiscount());
			} else {
				data.setAccountDelMoney(innerSettlement.getInnerSettlementTotalMoney().add(
						innerSettlement.getInnerSettlementTotalDiscount()));
			}
			data.setAccountType("内部结算单");
			list.add(data);
		}
		Collections.sort(list, new Comparator<AccountDetailDTO>() {

			@Override
			public int compare(AccountDetailDTO arg0, AccountDetailDTO arg1) {
				return arg0.getAccountDate().compareTo(arg1.getAccountDate());
			}
		});

		AccountDetailDTO totalData = new AccountDetailDTO();
		totalData.setAccountFid("上期余额");
		totalData.setAccountAddMoney(BigDecimal.ZERO);
		totalData.setAccountDelMoney(BigDecimal.ZERO);
		totalData.setAccountDicountMoney(BigDecimal.ZERO);
		list.add(0, totalData);
		
		BigDecimal totalMoney = inMoney.add(outMoney).subtract(preMoney).add(otherMoney).add(cardMoney);
		for(int i = list.size() - 1;i >= 0;i--){
			AccountDetailDTO data = list.get(i);
			if(i == list.size() - 1){
				data.setAccountTotalMoney(totalMoney);
			} else {
				AccountDetailDTO lastData = list.get(i + 1);
				data.setAccountTotalMoney(lastData.getAccountTotalMoney().subtract(lastData.getAccountAddMoney()).add(lastData.getAccountDelMoney()));
			}
		}
		list.remove(0);
		Collections.sort(list, new Comparator<AccountDetailDTO>() {

			@Override
			public int compare(AccountDetailDTO arg0, AccountDetailDTO arg1) {
				return -arg0.getAccountDate().compareTo(arg1.getAccountDate());
			}
		});
		list.add(list.size(), totalData);
		int count = list.size();
		List<AccountDetailDTO> returnList = new ArrayList<AccountDetailDTO>();
		for (int i = offset; i < offset + limit; i++) {
			if (i >= count) {
				break;
			}
			AccountDetailDTO data = list.get(i);
			returnList.add(data);
		}
		if (count - rankTo == 1) {
			returnList.add(totalData);
		}
		return returnList;
	}

//	@Override
//	public List<BranchHealthDTO> sendHealthyEmail(String systemBookCode, Integer branchNum, String userCode, String emailAddress, BigDecimal shopArea, Integer peopleCount, Integer year, Integer month,
//	                                              BigDecimal shopRent) {
//		SystemBook systemBook = systemBookService.read(systemBookCode);
//		if (systemBook == null) {
//			throw new ServiceBizException("帐套不存在");
//		}
//		Branch selfBranch = branchService.read(systemBookCode, branchNum);
//		if (selfBranch == null) {
//			throw new ServiceBizException("分店不存在");
//		}
//		MailInfoParam mailInfoParam = bookResourceService.readMailInfoParam(systemBookCode);
//		if (mailInfoParam == null) {
//			mailInfoParam = new MailInfoParam();
//			mailInfoParam.setMailInfoType(AppConstants.MAIL_INFO_TYPE_DEFAULT);
//			mailInfoParam.setMailInfoSendMail(mailUser);
//			mailInfoParam.setMailInfoSendMailPassWord(mailPassword);
//
//		}
//
//		boolean updateBranch = false;
//		if (selfBranch.getBranchArea() == null || selfBranch.getBranchArea().compareTo(shopArea) != 0) {
//			selfBranch.setBranchArea(shopArea);
//			updateBranch = true;
//		}
//		if (selfBranch.getBranchEmployeeCount() == null || !selfBranch.getBranchEmployeeCount().equals(peopleCount)) {
//			selfBranch.setBranchEmployeeCount(peopleCount);
//			updateBranch = true;
//		}
//		if (selfBranch.getBranchRent() == null || selfBranch.getBranchRent().compareTo(shopRent) != 0) {
//			selfBranch.setBranchRent(shopRent);
//			updateBranch = true;
//		}
//		if (updateBranch) {
//			branchService.updateABCAccountOrBranchArea(selfBranch);
//		}
//
//		List<AppUser> branchUsers = new ArrayList<AppUser>();
//		// 邮箱不为空 则更新用户邮箱并发送邮件
//		if (StringUtils.isNotEmpty(emailAddress)) {
//			if (!AppUtil.isValidEmail(emailAddress)) {
//				throw new ServiceBizException("Email地址不合法");
//			}
//			List<AppUser> appUsers = appUserService.findByCode(systemBookCode, userCode);
//			if (appUsers.size() == 0) {
//				throw new ServiceBizException("用户不存在");
//			}
//			if (appUsers.size() > 0) {
//				AppUser appUser = appUsers.get(0);
//				if (StringUtils.isEmpty(appUser.getAppUserEmail())
//						|| !StringUtils.equals(appUser.getAppUserEmail(), emailAddress)) {
//					appUser.setAppUserEmail(emailAddress);
//					appUserService.update(appUser);
//				}
//				branchUsers.add(appUser);
//			}
//		}
//
//		Calendar c = Calendar.getInstance();
//		c.set(Calendar.YEAR, year);
//		c.set(Calendar.MONTH, month - 1);
//		Date date = c.getTime();
//		date = DateUtil.getMinOfMonth(date);
//
//		List<BranchHealthReportDTO> branchHealthReportDTOs = null;
//		if (branchNum.equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)) {
//			List<Branch> branchs = branchService.findAllActived(systemBookCode);
//			// 配送中心编号
//			List<Integer> rdcBranchNums = new ArrayList<Integer>();
//			// 非配送中心分店编号
//			List<Integer> notRdcBranchNums = new ArrayList<Integer>();
//			BigDecimal allEmployeeCount = BigDecimal.ZERO;
//			BigDecimal allBranchArea = BigDecimal.ZERO;
//			// 加盟店人员数量 门店面积
//			BigDecimal joinEmployeeCount = BigDecimal.ZERO;
//			BigDecimal joinBranchArea = BigDecimal.ZERO;
//			// 直营店人员数量 门店面积
//			BigDecimal directEmployeeCount = BigDecimal.ZERO;
//			BigDecimal directBranchArea = BigDecimal.ZERO;
//			for (int j = 0; j < branchs.size(); j++) {
//				Branch branch = branchs.get(j);
//				if (branch.getBranchRdc() != null && branch.getBranchRdc()) {
//					rdcBranchNums.add(branch.getId().getBranchNum());
//				} else {
//					notRdcBranchNums.add(branch.getId().getBranchNum());
//				}
//				if (branch.getBranchArea() != null) {
//					allBranchArea = allBranchArea.add(branch.getBranchArea());
//					if (StringUtils.isEmpty(branch.getBranchType())
//							|| branch.getBranchType().equals(AppConstants.BRANCH_TYPE_DIRECT)) {
//						directBranchArea = directBranchArea.add(branch.getBranchArea());
//					} else {
//						joinBranchArea = joinBranchArea.add(branch.getBranchArea());
//					}
//				}
//				if (branch.getBranchEmployeeCount() != null) {
//					allEmployeeCount = allEmployeeCount.add(BigDecimal.valueOf(branch.getBranchEmployeeCount()));
//					if (StringUtils.isEmpty(branch.getBranchType())
//							|| branch.getBranchType().equals(AppConstants.BRANCH_TYPE_DIRECT)) {
//						directEmployeeCount = directEmployeeCount.add(BigDecimal.valueOf(branch
//								.getBranchEmployeeCount()));
//					} else {
//						joinEmployeeCount = joinEmployeeCount.add(BigDecimal.valueOf(branch.getBranchEmployeeCount()));
//					}
//				}
//			}
//			branchHealthReportDTOs = mailInfoJob.sendCenterHealthReport(systemBook, selfBranch, date, allEmployeeCount,
//					allBranchArea, rdcBranchNums, notRdcBranchNums, branchUsers, mailInfoParam, branchs.size(),
//					directBranchArea, joinBranchArea, directEmployeeCount, joinEmployeeCount);
//		} else {
//			branchHealthReportDTOs = mailInfoJob.sendBranchHealthReport(systemBook, selfBranch, date,
//					branchService.findRdcBranchNums(systemBookCode), mailInfoParam, branchUsers);
//		}
//
//		List<BranchHealthDTO> branchHealths = ObjectConverter.copyLists(branchHealthReportDTOs, BranchHealthDTO.class);
//		return branchHealths;
//	}
	
	private MobileMarketActionDTO getMarketAction(List<MobileMarketActionDTO> marketActions, String actionId) {
		for (int i = 0; i < marketActions.size(); i++) {
			MobileMarketActionDTO marketAction = marketActions.get(i);
			if (marketAction.getActionId().equals(actionId)) {
				return marketAction;
			}
		}
		return null;
	}
	
	@Override
	public List<RequestOrderDTO> findRequestOrders(String systemBookCode, Integer centerBranchNum,
			Integer branchNum,  Date dateFrom, Date dateTo) {
		
		if(centerBranchNum == null){
			centerBranchNum = branchGroupService.readTransferBranch(systemBookCode, branchNum).getId().getBranchNum();
		}
		List<RequestOrderDTO> requestOrderDTOs = requestOrderService.findDTOs(systemBookCode, centerBranchNum, branchNum, dateFrom, dateTo);
		if(requestOrderDTOs.size() > 0){
			List<String> requestOrderFids = new ArrayList<String>();
			for(int i = 0;i < requestOrderDTOs.size();i++){
				requestOrderFids.add(requestOrderDTOs.get(i).getRequestOrderFid());
				
			}
			List<ExpressCompany> expressCompanies = branchResourceService.findExpressCompaniesInCache(systemBookCode, centerBranchNum);
			List<Object[]> objects = requestOrderService.findFidShipOrderDeliver(requestOrderFids);
			requestOrderFids.clear();
			Object[] object = null;
			String requestOrderFid = null;
			String expressCompanyName = null;
			for(int i = 0;i < objects.size();i++){
				object = objects.get(i);
				requestOrderFid = (String) object[0];
				expressCompanyName = (String) object[1];
				
				//一张要货单多张发货单 只取第一条
				if(requestOrderFids.contains(requestOrderFid)){
					continue;
				}				
				requestOrderFids.add(requestOrderFid);
				
				ExpressCompany expressCompany = ExpressCompany.get(expressCompanies, expressCompanyName);
				if(expressCompany == null){
					continue;
				}
				
				RequestOrderDTO requestOrderDTO = RequestOrderDTO.get(requestOrderDTOs, requestOrderFid);
				requestOrderDTO.setExpressCompanyLinkMan(expressCompany.getExpressCompanyLinkMan());
				requestOrderDTO.setExpressCompanyPhone(expressCompany.getExpressCompanyPhone());
				requestOrderDTO.setExpressCompanyName(expressCompanyName);
			}
			
		}
		return requestOrderDTOs;
	}
	
	@Override
	public List<RequestOrderDetailDTO> findRequestOrderDetails(String systemBookCode, 
			Integer centerBranchNum, String requestOrderFid) {
		
		List<RequestOrderDetail> details = requestOrderService.findDetails(requestOrderFid);
		List<RequestOrderDetailDTO> list = new ArrayList<RequestOrderDetailDTO>();
		List<Integer> itemNums = new ArrayList<Integer>();
		for(int i = 0;i < details.size();i++){
			RequestOrderDetail detail = details.get(i);
			
			RequestOrderDetailDTO dto = new RequestOrderDetailDTO();
			BeanUtils.copyProperties(detail, dto);
			itemNums.add(detail.getItemNum());
			list.add(dto);
		}
		if(itemNums.size() == 0){
			return list;
		}
		ChainDeliveryParam param = bookResourceService.readChainDeliveryParam(systemBookCode);
		if(!param.getEnableInventoryLnDetailRate()){
			for(int i = 0;i < list.size();i++){
				RequestOrderDetailDTO detailDTO = list.get(i);
				if(detailDTO.getRequestOrderDetailOutQty() == null){
					detailDTO.setRequestOrderDetailOutQty(BigDecimal.ZERO);
				}
				detailDTO.setRequestOrderDetailQty(detailDTO.getRequestOrderDetailQty().divide(detailDTO.getRequestOrderDetailUseRate(), 2, BigDecimal.ROUND_HALF_UP));
				detailDTO.setRequestOrderDetailOutQty(detailDTO.getRequestOrderDetailOutQty().divide(detailDTO.getRequestOrderDetailUseRate(), 2, BigDecimal.ROUND_HALF_UP));

			}
		} else {
			List<PosItem> posItems = posItemService.findByItemNumsInCache(systemBookCode, itemNums);
			Branch branch = branchService.readInCache(systemBookCode, centerBranchNum);
			for(int i = 0;i < list.size();i++){
				RequestOrderDetailDTO detailDTO = list.get(i);
				detailDTO.setRequestOrderDetailQty(detailDTO.getRequestOrderDetailQty().divide(detailDTO.getRequestOrderDetailUseRate(), 2, BigDecimal.ROUND_HALF_UP));
				
				PosItem posItem = AppUtil.getPosItem(detailDTO.getItemNum(), posItems);
				if(posItem.getItemCostMode(branch).equals(AppConstants.C_ITEM_COST_MODE_MANUAL)){
					if(detailDTO.getRequestOrderDetailOutUseQty() != null){
						detailDTO.setRequestOrderDetailOutQty(detailDTO.getRequestOrderDetailOutUseQty());

					} else {
						detailDTO.setRequestOrderDetailOutQty(detailDTO.getRequestOrderDetailOutQty().divide(detailDTO.getRequestOrderDetailUseRate(), 2, BigDecimal.ROUND_HALF_UP));
						
					}
				} else {
					detailDTO.setRequestOrderDetailOutQty(detailDTO.getRequestOrderDetailOutQty().divide(detailDTO.getRequestOrderDetailUseRate(), 2, BigDecimal.ROUND_HALF_UP));
				}
				
			}
			
		}
		return list;
	}

	@Override
	public MobileBusinessDTO getIndexMobileBusinessDTO(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return mobileAppV2Service.getIndexMobileBusinessDTO(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<NameAndValueDTO> findOtherInfos(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return mobileAppV2Service.findOtherInfos(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<NameAndValueDTO> findDiscountDetails(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return mobileAppV2Service.findDiscountDetails(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<NameAndValueDTO> findCashDetails(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return mobileAppV2Service.findCashDetails(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<NameAndValueDTO> findPaymentDetails(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return mobileAppV2Service.findPaymentDetails(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<NameAndValueDTO> findCardDepositDetails(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return mobileAppV2Service.findCardDepositDetails(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<NameAndTwoValueDTO> findItemRank(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer rankFrom, Integer rankTo, String sortField) {
		return mobileAppV2Service.findItemRank(systemBookCode,branchNums,dateFrom,dateTo,rankFrom,rankTo,sortField);
	}

	@Override
	public List<NameAndTwoValueDTO> findCategoryRank(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer rankFrom, Integer rankTo, String sortField) {
		return mobileAppV2Service.findCategoryRank(systemBookCode,branchNums,dateFrom,dateTo,rankFrom,rankTo,sortField);
	}

	@Override
	public MobileBusinessPeriodDTO getMobileBusinessPeriodDTO(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return mobileAppV2Service.getMobileBusinessPeriodDTO(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public MobileCardDTO getMobileCardDTO(String systemBookCode, List<Integer> branchNums) {
		return mobileAppV2Service.getMobileCardDTO(systemBookCode,branchNums);
	}

	@Override
	public List<NameAndValueDTO> findBranchCardCount(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return mobileAppV2Service.findBranchCardCount(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<NameAndValueDTO> findGroupCustomerCount(String systemBookCode, Integer branchNum) {
		return mobileAppV2Service.findGroupCustomerCount(systemBookCode,branchNum);
	}

	@Override
	public List<MobileCustomerModelDTO> findMobileCustomerModelDTOs(String systemBookCode, Integer branchNum) {
		return mobileAppV2Service.findMobileCustomerModelDTOs(systemBookCode,branchNum);
	}

	@Override
	public List<NameAndValueDTO> findBranchCardConsume(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return mobileAppV2Service.findBranchCardConsume(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<NameAndValueDTO> findBranchCardDeposit(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return mobileAppV2Service.findBranchCardDeposit(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public MobileCardDTO getMobileCardDTOByDate(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType) {
		return mobileAppV2Service.getMobileCardDTOByDate(systemBookCode,branchNums,dateFrom,dateTo,dateType);
	}

	@Override
	public void addUserBranchNum(String systemBookCode, Integer appUserNum, Integer branchNum) {
		mobileAppV2Service.addUserBranchNum(appUserNum,branchNum);
	}

	@Override
	public void deleteUserBranchNum(String systemBookCode, Integer appUserNum, Integer branchNum) {
		mobileAppV2Service.deleteUserBranchNum(appUserNum,branchNum);
	}

	@Override
	public List<Integer> findUserBranchNums(String systemBookCode, Integer appUserNum) {
		return mobileAppV2Service.findUserBranchNums(appUserNum);
	}

	@Override
	public List<NameAndTwoValueDTO> findBranchPosSummary(String systemBookCode, List<Integer> branchNums, Integer itemNum, Date dateFrom, Date dateTo) {
		return mobileAppV2Service.findBranchPosSummary(systemBookCode,branchNums,itemNum,dateFrom,dateTo);
	}

	@Override
	public List<SalesDiscountDTO> findSalesDiscount(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer rankFrom, Integer rankTo, String sortType) {
		return mobileAppV2Service.findSalesDiscount(systemBookCode,branchNums,dateFrom,dateTo,rankFrom,rankTo,sortType);
	}
	
	@Override
	public List<NameAndTwoValueDTO> findDeptRank(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer rankFrom, Integer rankTo, String sortField) {
		return mobileAppV2Service.findDeptRank(systemBookCode, branchNums, dateFrom, dateTo, rankFrom, rankTo, sortField);
	}

	@Override
	public List<NameAndTwoValueDTO> findSmallCategoryRank(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer rankFrom, Integer rankTo, String sortField) {
		return mobileAppV2Service.findSmallCategoryRank(systemBookCode, branchNums, dateFrom, dateTo, rankFrom, rankTo, sortField);
	}

	@Override
	public List<NameAndValueDTO> findBranchCardCountV2(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return mobileAppV2Service.findBranchCardCountV2(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	@Cacheable(value = "serviceCache")
	public MobileBusinessDTO findMobileBusiness(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return mobileAppV2Service.findMobileBusiness(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	@Cacheable(value = "serviceCache")
	public List<MobileBusinessDetailDTO> findPaymentSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return mobileAppV2Service.findPaymentSummary(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<MobileBusinessDetailDTO> findDepositSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return mobileAppV2Service.findDepositSummary(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<NameAndValueDTO> findDiscountSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return mobileAppV2Service.findDiscountSummary(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	@Cacheable(value = "serviceCache")
	public List<MobileBusinessDTO> findBusinessMoneyGroupByShop(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return mobileAppV2Service.findBusinessMoneyGroupByShop(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<MobileBusinessDTO> findDiscountGroupByShop(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return mobileAppV2Service.findDiscountGroupByShop(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	@Cacheable(value = "serviceCache")
	public List<MobileBusinessDTO> findBusinessReceiptGroupByShop(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return mobileAppV2Service.findBusinessReceiptGroupByShop(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<MobileBusinessDetailDTO> findCashSummaryGroupByShop(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String cashType) {
		return mobileAppV2Service.findCashSummaryGroupByShop(systemBookCode,branchNums,dateFrom,dateTo,cashType);
	}

	@Override
	public List<MobileBusinessDetailDTO> findDepositSummaryGroupByShop(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String cashType) {
		return mobileAppV2Service.findDepositSummaryGroupByShop(systemBookCode,branchNums,dateFrom,dateTo,cashType);
	}

	@Override
	public List<MobileBusinessDetailDTO> findPaymentSummaryGroupByShop(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String type) {
		return mobileAppV2Service.findPaymentSummaryGroupByShop(systemBookCode,branchNums,dateFrom,dateTo,type);
	}

	@Override
	public List<MobileBusinessDetailDTO> findCashSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return mobileAppV2Service.findCashSummary(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<OtherInfoDTO> findOtherInfoDetails(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, String infoType) {
		return mobileAppV2Service.findOtherInfoDetails(systemBookCode,branchNum,dateFrom,dateTo,infoType);
	}

	@Override
	public List<ShipOrderDTO> findShipOrders(String systemBookCode, Integer centerBranchNum, Integer branchNum, Date dateFrom, Date dateTo) {
		return mobileAppV2Service.findShipOrders(systemBookCode,centerBranchNum,branchNum,dateFrom,dateTo);
	}

	@Override
	public List<CardReportDTO> findCardReportByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return mobileAppV2Service.findCardReportByBranch(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<CardReportDTO> findCardReportByBranchBq(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String sortField, Integer branchNum) {
		return mobileAppV2Service.findCardReportByBranchBq(systemBookCode,branchNums,dateFrom,dateTo,sortField,branchNum);
	}


}
