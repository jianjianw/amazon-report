package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.*;
import com.nhsoft.module.report.dto.*;
import com.nhsoft.module.report.model.*;
import com.nhsoft.module.report.param.PosItemTypeParam;
import com.nhsoft.module.report.query.*;
import com.nhsoft.module.report.service.*;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.module.report.util.DateUtil;
import com.nhsoft.module.report.util.ReportUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class Report2ServiceImpl implements Report2Service {


	@Autowired
	public ReportDao reportDao;
	@Autowired
	public BookResourceService bookResourceService;
	@Autowired
	public PosItemService posItemService;
	@Autowired
	public TransferOutOrderDao transferOutOrderDao;
	@Autowired
	public ReceiveOrderDao receiveOrderDao;
	@Autowired
	public BranchService branchService;
	@Autowired
	public TransferInOrderDao transferInOrderDao;
	@Autowired
	public InnerSettlementDao innerSettlementDao;
	@Autowired
	public InnerPreSettlementDao innerPreSettlementDao;
	@Autowired
	public CardUserDao cardUserDao;
	@Autowired
	public CardSettlementDao cardSettlementDao;
	@Autowired
	public OnlineOrderDao onlineOrderDao;
	@Autowired
	public CardConsumeDao cardConsumeDao;
	@Autowired
	public CardDepositDao cardDepositDao;
	@Autowired
	public OtherInoutDao otherInoutDao;
	@Autowired
	public SystemBookService systemBookService;
	@Autowired
	public PosOrderDao posOrderDao;




	@Autowired
	private BranchRegionDao branchRegionDao;
	@Autowired
	private CardBillService cardBillService;
	@Autowired
	private PosItemGradeDao posItemGradeDao;
	@Autowired
	private MobileAppV2Service mobileAppV2Service;

	
	@Override
	public List<TransferPolicyDTO> findTransferPolicyDTOs(PolicyPosItemQuery policyPosItemQuery) {
		List<TransferPolicyDTO> transferPolicyDTOs = reportDao.findTransferPolicyDTOs(policyPosItemQuery);
		if(transferPolicyDTOs.size() == 0){
			return transferPolicyDTOs;
		}
		List<Integer> itemNums = new ArrayList<Integer>();
		
		for(int i = 0;i < transferPolicyDTOs.size();i++){
			
			TransferPolicyDTO dto = transferPolicyDTOs.get(i);
			if(!itemNums.contains(dto.getItemNum())){
				itemNums.add(dto.getItemNum());
			}
		}
		List<PosItem> posItems = posItemService.findByItemNumsWithoutDetails(itemNums);
		for(int i = 0;i < transferPolicyDTOs.size();i++){
			
			TransferPolicyDTO dto = transferPolicyDTOs.get(i);
			PosItem posItem = AppUtil.getPosItem(dto.getItemNum(), posItems);
			
			dto.setItemCode(posItem.getItemCode());
			dto.setItemName(posItem.getItemName());
			dto.setItemSpec(posItem.getItemSpec());
			dto.setItemUnit(posItem.getItemUnit());
			
		}
		return transferPolicyDTOs;
	}

	@Override
	public List<PosReceiveDiffMoneySumDTO> findPosReceiveDiffMoneySumDTOsByBranch(String systemBookCode,
	                                                                              List<Integer> branchNums, Date dateFrom, Date dateTo) {
		
		List<PosReceiveDiffMoneySumDTO> list = reportDao.findPosReceiveDiffMoneySumDTOsByBranch(systemBookCode, branchNums, dateFrom, dateTo);
		List<Branch> branchs = branchService.findInCache(systemBookCode);
		List<BranchRegion> branchRegions = branchRegionDao.findAll(systemBookCode);
		for(int i = 0;i < list.size();i++){
			PosReceiveDiffMoneySumDTO dto = list.get(i);
			dto.setTotalReceiveDiff(dto.getTotalReceiveMoney().subtract(dto.getTotalSaleMoney())
					.subtract(dto.getTotalCardDeposit()).subtract(dto.getTotalOtherMoney()).subtract(dto.getTotalRelatMoney()).subtract(dto.getTotalReplaceMoney()));
			
			Branch branch = AppUtil.getBranch(branchs, dto.getBranchNum());
			if(branch != null){
				dto.setBranchCode(branch.getBranchCode());
				dto.setBranchName(branch.getBranchName());
				
				if(branch.getBranchRegionNum() != null){
					BranchRegion branchRegion = BranchRegion.get(branchRegions, branch.getBranchRegionNum());
					if(branchRegion != null){
						dto.setRegionCode(branchRegion.getBranchRegionCode());
						dto.setRegionName(branchRegion.getBranchRegionName());
					}
				}
				
			}			
		}		
		return list;
	}

	@Override
	public List<PosReceiveDiffMoneySumDTO> findPosReceiveDiffMoneySumDTOsByBranchCasher(String systemBookCode,
			List<Integer> branchNums, Date dateFrom, Date dateTo) {
		List<PosReceiveDiffMoneySumDTO> list = reportDao.findPosReceiveDiffMoneySumDTOsByBranchCasher(systemBookCode, branchNums, dateFrom, dateTo);
		return list;
	}
	////
	@Override
	public BigDecimal getBranchBalanceMoney(String systemBookCode, Integer centerBranchNum, Integer branchNum) {
		BigDecimal outMoney = transferOutOrderDao.findBalance(systemBookCode, centerBranchNum, branchNum, null, null);
		BigDecimal inMoney = transferInOrderDao.findBalance(systemBookCode, centerBranchNum, branchNum, null, null);
		BigDecimal preMoney = innerPreSettlementDao.readBranchUnPaidMoney(systemBookCode, branchNum, centerBranchNum);
		BigDecimal otherMoney = otherInoutDao.getUnPaidMoney(systemBookCode, centerBranchNum, branchNum, null, null, 0);
		BigDecimal cardMoney = cardSettlementDao.readBranchUnPaidMoney(systemBookCode, branchNum, centerBranchNum);
		return preMoney.subtract(outMoney).subtract(inMoney).subtract(cardMoney).subtract(otherMoney);
	}

	@Override
	public List<OnlineOrderSaleAnalysisDTO> findOnlineOrderSaleAnalysisByItem(OnlineOrderQuery onlineOrderQuery) {
		onlineOrderQuery.setDateType(AppConstants.BANNER_DATE_TYPE_DELEVER);

		List<OnlineOrderSaleAnalysisDTO> list = onlineOrderDao.findOnlineOrderSaleAnalysisByItem(onlineOrderQuery);
		if(list.size() == 0){
			return list;
		}
		List<Integer> itemNums = new ArrayList<Integer>();
		for(int i = 0;i < list.size();i++){
			OnlineOrderSaleAnalysisDTO dto = list.get(i);
			if(!itemNums.contains(dto.getItemNum())){
				itemNums.add(dto.getItemNum());
			}
		}
		List<PosItem> posItems = posItemService.findByItemNumsInCache(onlineOrderQuery.getSystemBookCode(), itemNums);
		for(int i = 0;i < list.size();i++){
			OnlineOrderSaleAnalysisDTO dto = list.get(i);
			PosItem posItem = AppUtil.getPosItem(dto.getItemNum(), posItems);
			if(posItem == null){
				continue;
			}
			dto.setItemCode(posItem.getItemCode());
			dto.setItemName(posItem.getItemName());
			
		}		
		return list;
	}

	@Override
	public List<OnlineOrderSaleAnalysisDTO> findOnlineOrderSaleAnalysisByBranchItem(OnlineOrderQuery onlineOrderQuery) {
		onlineOrderQuery.setDateType(AppConstants.BANNER_DATE_TYPE_DELEVER);
		List<OnlineOrderSaleAnalysisDTO> list = onlineOrderDao.findOnlineOrderSaleAnalysisByBranchItem(onlineOrderQuery);
		if(list.size() == 0){
			return list;
		}
		List<Integer> itemNums = new ArrayList<Integer>();
		for(int i = 0;i < list.size();i++){
			OnlineOrderSaleAnalysisDTO dto = list.get(i);
			if(!itemNums.contains(dto.getItemNum())){
				itemNums.add(dto.getItemNum());
			}
		}
		List<PosItem> posItems = posItemService.findByItemNumsInCache(onlineOrderQuery.getSystemBookCode(), itemNums);
		List<Branch> branchs = branchService.findInCache(onlineOrderQuery.getSystemBookCode());
		for(int i = 0;i < list.size();i++){
			OnlineOrderSaleAnalysisDTO dto = list.get(i);
			PosItem posItem = AppUtil.getPosItem(dto.getItemNum(), posItems);
			if(posItem != null){
				
				dto.setItemCode(posItem.getItemCode());
				dto.setItemName(posItem.getItemName());
			}
			
			Branch branch = AppUtil.getBranch(branchs, dto.getBranchNum());
			if(branch != null){
				
				dto.setBranchCode(branch.getBranchCode());
				dto.setBranchName(branch.getBranchName());
			}
			
		}		
		return list;
	}

	@Override
    public List<SaleAnalysisByPosItemDTO> findSaleAnalysisByBranchDayItems(
            SaleAnalysisQueryData saleAnalysisQueryData) {
		List<Object[]> objects = new ArrayList<Object[]>();

//		SystemBook systemBook = systemBookService.readInCache(saleAnalysisQueryData.getSystemBookCode());
//		Date now = Calendar.getInstance().getTime();
//		now = DateUtil.getMinOfDate(now);
//		
//		Date dateFrom = saleAnalysisQueryData.getDtFrom();
//		Date dateTo = saleAnalysisQueryData.getDtTo();
//		if(dateTo.compareTo(now) >= 0){
//			dateTo = now;
//		}
//		
//		Date deleteDate = systemBook.getDeleteDate();
//		dateFrom = DateUtil.getMinOfDate(dateFrom);
//		if(deleteDate != null && dateFrom.compareTo(deleteDate) < 0){
//			dateFrom = deleteDate;
//		}
//		if(saleAnalysisQueryData.getIsQueryCF() == null){
//			saleAnalysisQueryData.setIsQueryCF(false);
//		}
//		dateTo = DateUtil.getMaxOfDate(dateTo);
//		if(systemBook.getBookReadDpc() != null && systemBook.getBookReadDpc()
//				&& StringUtils.isEmpty(saleAnalysisQueryData.getSaleType())
//				&& !saleAnalysisQueryData.getIsQueryCF()){
//			Date dpcLimitTime = DateUtil.addDay(now, -2);
//			
//			if(dpcLimitTime.compareTo(dateFrom) <= 0){
//				objects = reportDao.findSaleAnalysisCommonItemMatrix(saleAnalysisQueryData);
//			}
//			else if(dpcLimitTime.compareTo(dateTo) > 0){
//				OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
//				orderQueryDTO.setSystemBookCode(saleAnalysisQueryData.getSystemBookCode());
//				orderQueryDTO.setBranchNum(saleAnalysisQueryData.getBranchNums());
//				orderQueryDTO.setDateFrom(dateFrom);
//				orderQueryDTO.setDateTo(dateTo);			
//				orderQueryDTO.setQueryKit(saleAnalysisQueryData.getIsQueryCF());					
//				
//				List<OrderDetailReportDTO> list = posOrderRemoteService.findItemMatrixStateSummaryDetail(orderQueryDTO);
//				Object[] object = null;
//				for(int i = 0;i<list.size();i++){
//					object = new Object[8];
//					object[0] = list.get(i).getItemNum();
//					object[1] = list.get(i).getItemMatrixNum();
//					object[2] = list.get(i).getStateCode();
//					object[3] = list.get(i).getAmount();
//					object[4] = list.get(i).getPaymentMoney();
//					object[5] = list.get(i).getAssistAmount();
//					object[6] = list.get(i).getItemCount();	
//					object[7] = list.get(i).getDiscount();	
//					objects.add(object);
//				}
//			}
//			else {
//				
//				OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
//				orderQueryDTO.setSystemBookCode(saleAnalysisQueryData.getSystemBookCode());
//				orderQueryDTO.setBranchNum(saleAnalysisQueryData.getBranchNums());
//				orderQueryDTO.setDateFrom(dateFrom);
//				orderQueryDTO.setDateTo(DateUtil.addDay(dpcLimitTime, -1));
//				if(saleAnalysisQueryData.getIsQueryCF() != null){
//					orderQueryDTO.setQueryKit(saleAnalysisQueryData.getIsQueryCF());
//					
//				}
//				List<OrderDetailReportDTO> list = posOrderRemoteService.findItemMatrixStateSummaryDetail(orderQueryDTO);
//				Object[] object = null;
//				for(int i = 0;i<list.size();i++){
//					object = new Object[8];
//					object[0] = list.get(i).getItemNum();
//					object[1] = list.get(i).getItemMatrixNum();
//					object[2] = list.get(i).getStateCode();
//					object[3] = list.get(i).getAmount();
//					object[4] = list.get(i).getPaymentMoney();
//					object[5] = list.get(i).getAssistAmount();
//					object[6] = list.get(i).getItemCount();	
//					object[7] = list.get(i).getDiscount();	
//					objects.add(object);
//				}
//				
//				saleAnalysisQueryData.setDtFrom(dpcLimitTime);
//				List<Object[]> localObjects = reportDao.findSaleAnalysisCommonItemMatrix(saleAnalysisQueryData);
//				boolean find = false;
//				for(int i = 0;i < localObjects.size();i++){
//					Object[] localObject = localObjects.get(i);
//					if(localObject[1] == null){
//						localObject[1] = 0;
//					}
//					find = false;
//					for(int j = 0;j < objects.size();j++){
//						object = objects.get(j);
//						if(object[0].equals(localObject[0]) && object[1].equals(localObject[1])
//								&& object[2].equals(localObject[2])){
//							object[3] = ((BigDecimal)object[3]).add(localObject[3] == null?BigDecimal.ZERO:(BigDecimal)localObject[3]);
//							object[4] = ((BigDecimal)object[4]).add(localObject[4] == null?BigDecimal.ZERO:(BigDecimal)localObject[4]);
//							object[5] = ((BigDecimal)object[5]).add(localObject[5] == null?BigDecimal.ZERO:(BigDecimal)localObject[5]);
//							object[6] = ((Integer)object[6]) + (localObject[6] == null?0:(Integer)localObject[6]);
//							object[7] = ((BigDecimal)object[7]).add(localObject[7] == null?BigDecimal.ZERO:(BigDecimal)localObject[7]);
//							find = true;
//							break;
//						}
//					}
//					
//					if(!find){
//						objects.add(localObject);
//					}
//				}
//			}
//		} else {
			objects = posOrderDao.findSaleAnalysisByBranchDayItem(saleAnalysisQueryData);
			
//		}
		if(objects.size() == 0){
			return new ArrayList<SaleAnalysisByPosItemDTO>();
		}
		
		Map<String, SaleAnalysisByPosItemDTO> map = new HashMap<String, SaleAnalysisByPosItemDTO>();
		Integer branchNum;
		String bizday;
		Integer itemNum;
		Integer stateCode;
		BigDecimal amount;
		BigDecimal money;
		BigDecimal assistAmount;
		BigDecimal count_;
		BigDecimal discount;
		StringBuilder key;
		List<Integer> itemNums = new ArrayList<Integer>();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			branchNum = (Integer) object[0];
			bizday = (String) object[1];
			itemNum = (Integer) object[2];
			stateCode = (Integer) object[3];
			amount = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			money = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			assistAmount = object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6];
			count_ = BigDecimal.valueOf(object[7] == null ? 0 : (Integer) object[7]);
			if(object[8] instanceof BigDecimal){
				discount = object[8] == null ? BigDecimal.ZERO : (BigDecimal) object[8];
				
			} else if(object[8] instanceof Double){
				discount = object[8] == null ? BigDecimal.ZERO : BigDecimal.valueOf((Double) object[8]);
			} else {
				discount = BigDecimal.ZERO;
			}
			
			if (stateCode == AppConstants.POS_ORDER_DETAIL_STATE_REMOVE) {
				continue;
			}
			key = new StringBuilder();
			key.append(branchNum).append("|").append(bizday).append("|").append(itemNum);
			SaleAnalysisByPosItemDTO data = map.get(key.toString());
			if (data == null) {
				data = new SaleAnalysisByPosItemDTO();
				data.setBranchNum(branchNum);
				data.setItemNum(itemNum);
				data.setBranchNum(branchNum);
				data.setBizday(bizday);
				map.put(key.toString(), data);
			}
			if (stateCode.equals(AppConstants.POS_ORDER_DETAIL_STATE_CANCEL)) {
				data.setTotalNum(data.getTotalNum().subtract(amount));
				data.setTotalMoney(data.getTotalMoney().subtract(money));
				data.setCountTotal(data.getCountTotal().subtract(count_));
				data.setReturnNum(data.getReturnNum().add(amount));
				data.setReturnMoney(data.getReturnMoney().add(money));
				data.setReturnAssist(data.getReturnAssist().add(assistAmount));
				data.setItemDiscount(data.getItemDiscount().subtract(discount));
				
			}
			if (stateCode.equals(AppConstants.POS_ORDER_DETAIL_STATE_PRESENT)) {
				data.setPresentNum(data.getPresentNum().add(amount));
				data.setPresentMoney(data.getPresentMoney().add(money));
				data.setPresentAssist(data.getPresentAssist().add(assistAmount));
				data.setCountTotal(data.getCountTotal().add(count_));
				data.setTotalNum(data.getTotalNum().add(amount));
			}
			if (stateCode.equals(AppConstants.POS_ORDER_DETAIL_STATE_SALE)) {
				data.setTotalNum(data.getTotalNum().add(amount));
				data.setTotalMoney(data.getTotalMoney().add(money));
				data.setCountTotal(data.getCountTotal().add(count_));
				data.setSaleNum(data.getSaleNum().add(amount));
				data.setSaleMoney(data.getSaleMoney().add(money));
				data.setSaleAssist(data.getSaleAssist().add(assistAmount));
				data.setItemDiscount(data.getItemDiscount().add(discount));
			}
			if(!itemNums.contains(itemNum)){
				itemNums.add(itemNum);
			}
			
		}
		List<PosItemTypeParam> posItemTypeParams = bookResourceService.findPosItemTypeParamsInCache(saleAnalysisQueryData.getSystemBookCode());
		List<PosItem> posItems = posItemService.findByItemNumsWithoutDetails(itemNums);
		List<SaleAnalysisByPosItemDTO> list = new ArrayList<SaleAnalysisByPosItemDTO>(map.values());
		PosItemTypeParam topCategory;
		
		
		Collections.sort(list, new Comparator<SaleAnalysisByPosItemDTO>() {
			@Override
			public int compare(SaleAnalysisByPosItemDTO o1, SaleAnalysisByPosItemDTO o2) {
				return o1.getItemNum().compareTo(o2.getItemNum());
			}
		});
		Integer preItemNum = null;
		PosItem posItem = null;
		for (int i = list.size() - 1; i >= 0; i--) {
			SaleAnalysisByPosItemDTO data = list.get(i);
			
			Integer posItemNum = data.getItemNum();
			
			if(preItemNum == null || !preItemNum.equals(posItemNum)){
				
				posItem = AppUtil.getPosItem(posItemNum, posItems);
				if (posItem == null) {
					list.remove(i);
					continue;
				}
				preItemNum = posItemNum;
			}
			
			if (saleAnalysisQueryData.getBrandCodes() != null && saleAnalysisQueryData.getBrandCodes().size() > 0) {
				if (!saleAnalysisQueryData.getBrandCodes().contains(posItem.getItemBrand())) {
					list.remove(i);
					continue;
				}
			}
			if (saleAnalysisQueryData.getPosItemTypeCodes() != null && saleAnalysisQueryData.getPosItemTypeCodes().size() > 0) {
				if (!saleAnalysisQueryData.getPosItemTypeCodes().contains(posItem.getItemCategoryCode())) {
					list.remove(i);
					continue;
				}
			}
			if (StringUtils.isNotEmpty(saleAnalysisQueryData.getItemDepartments())) {
				if (!saleAnalysisQueryData.getItemDepartments().contains(posItem.getItemDepartment())) {
					list.remove(i);
					continue;
				}
			}
			data.setItemName(posItem.getItemName());
			data.setItemCode(posItem.getItemCode());
			data.setSpec(posItem.getItemSpec());
			data.setUnit(posItem.getItemUnit());
			data.setCategoryName(posItem.getItemCategory());
			data.setCategoryCode(posItem.getItemCategoryCode());
			topCategory = AppUtil.getTopCategory(posItemTypeParams, posItem.getItemCategoryCode());
			if(topCategory != null){
				data.setTopCategoryCode(topCategory.getPosItemTypeCode());
				data.setTopCategoryName(topCategory.getPosItemTypeName());
			} else {
				data.setTopCategoryCode(data.getCategoryCode());
				data.setTopCategoryName(data.getCategoryName());
			}
			
		}
		return list;
    }

	@Override
    public List<Object[]> findProfitAnalysisByBranchDayItem(
            ProfitAnalysisQueryData profitAnalysisQueryData) {
		List<Object[]> objects = posOrderDao.findProfitAnalysisByBranchDayItem(profitAnalysisQueryData);
		return objects;
    }

	@Override
	public List<PosReceiveDiffMoneySumDTO> findPosReceiveDiffMoneySumDTOsByShiftTable(String systemBookCode,
			List<Integer> branchNums, Date dateFrom, Date dateTo, String casher) {
		
		List<PosReceiveDiffMoneySumDTO> list = reportDao.findPosReceiveDiffMoneySumDTOsByShiftTable(systemBookCode, branchNums, dateFrom, dateTo, casher);
		return list;
	}

	@Override
	public List<CardQtySumDTO> findCardQtySumDatasByBranch(String systemBookCode, List<Integer> branchNums,
                                                           Date dateFrom, Date dateTo) {
		List<CardQtySumDTO> cardQtySumDTOs = reportDao.findCardQtySumDatasByBranch(systemBookCode, branchNums, dateFrom, dateTo);
		List<Branch> branchs = branchService.findInCache(systemBookCode);
		
		List<Object[]> balanceObjects = cardBillService.findBranchBalance(systemBookCode, branchNums, null, null);

		for(int i = 0;i < cardQtySumDTOs.size();i++){
			CardQtySumDTO dto = cardQtySumDTOs.get(i);
			
			Branch branch = AppUtil.getBranch(branchs, dto.getBranchNum());
			if(branch != null){
				dto.setBranchName(branch.getBranchName());
			}
			
			for(int j = 0;j < balanceObjects.size();j++){
				Object[] object = balanceObjects.get(j);
				if(object[0].equals(dto.getBranchNum())){
					dto.setInventoryQty((Integer) object[1]);
					break;
				}
			}
		}		
		return cardQtySumDTOs;
	}

	@Override
	public List<CardQtySumDTO> findCardQtySumDatasByBranchAndDay(String systemBookCode, List<Integer> branchNums,
			Date dateFrom, Date dateTo) {
		List<CardQtySumDTO> cardQtySumDTOs = reportDao.findCardQtySumDatasByBranchAndDay(systemBookCode, branchNums, dateFrom, dateTo);
		List<Branch> branchs = branchService.findInCache(systemBookCode);
		for(int i = 0;i < cardQtySumDTOs.size();i++){
			CardQtySumDTO dto = cardQtySumDTOs.get(i);
			
			Branch branch = AppUtil.getBranch(branchs, dto.getBranchNum());
			if(branch != null){
				dto.setBranchName(branch.getBranchName());
			}
		}	
		
		//截止到dateTo的库存
		List<Object[]> balanceObjects = cardBillService.findBranchBalance(systemBookCode, branchNums, null, null);
		Date now = Calendar.getInstance().getTime();
		if(dateTo.compareTo(DateUtil.getMinOfDate(now)) < 0){
			List<Object[]> dateBalanceObjects = cardBillService.findBranchBalance(systemBookCode, branchNums, DateUtil.addDay(dateTo, 1), now);
			for(int i = 0;i < dateBalanceObjects.size();i++){
				
				Object[] localObject = dateBalanceObjects.get(i);
				
				for(int j = 0;j < balanceObjects.size();j++){
					Object[] object = balanceObjects.get(j);
					if(object[0].equals(localObject[0])){
						object[1] = ((Integer)object[1]) - ((Integer)localObject[1]);
						break;
					}
				}			
			
			}
		
		}
		Collections.sort(cardQtySumDTOs, new Comparator<CardQtySumDTO>() {

			@Override
			public int compare(CardQtySumDTO o1, CardQtySumDTO o2) {
				if(o1.getBranchNum() > o2.getBranchNum()){
					return 1;
				} else if(o1.getBranchNum() < o2.getBranchNum()){
					return -1;
				} else {
					if(o1.getBizDay().compareTo(o2.getBizDay()) > 0){
						return 1;
					} else if(o1.getBizDay().compareTo(o2.getBizDay()) < 0){
						return -1;
					} else {
						return 0;
					}
				}
			}
		});
		Integer lastBranchNum = null;
		for(int i = 0;i < cardQtySumDTOs.size();i++){
			CardQtySumDTO dto = cardQtySumDTOs.get(i);
			if(lastBranchNum == null){
				lastBranchNum = dto.getBranchNum();
			} 
			
			if(!lastBranchNum.equals(dto.getBranchNum())){
				CardQtySumDTO lastDTO = cardQtySumDTOs.get(i - 1);
				for(int j = 0;j < balanceObjects.size();j++){
					Object[] object = balanceObjects.get(j);
					if(object[0].equals(lastDTO.getBranchNum())){
						lastDTO.setInventoryQty((Integer) object[1]);
						break;
					}
				}
			}
			if(i == cardQtySumDTOs.size() - 1){
				for(int j = 0;j < balanceObjects.size();j++){
					Object[] object = balanceObjects.get(j);
					if(object[0].equals(dto.getBranchNum())){
						dto.setInventoryQty((Integer) object[1]);
						break;
					}
				}
			}
			lastBranchNum = dto.getBranchNum();
		}
		
		Integer preInventoryQty = 0;
		for(int i = cardQtySumDTOs.size() - 1;i >= 0;i--){
			CardQtySumDTO dto = cardQtySumDTOs.get(i);
			
			if(dto.getInventoryQty() != null){
				preInventoryQty = dto.getInventoryQty() - dto.getInQty() - dto.getCheckQty() - dto.getRevokeQty() 
						+ dto.getChangeQty() + dto.getSendQty() + dto.getOutQty() + dto.getReplaceQty();
			} else {
				dto.setInventoryQty(preInventoryQty);
				preInventoryQty = preInventoryQty - dto.getInQty() - dto.getCheckQty() - dto.getRevokeQty() 
						+ dto.getChangeQty() + dto.getSendQty() + dto.getOutQty() + dto.getReplaceQty();
			}
			
		}
		return cardQtySumDTOs;
	}

	@Override
	public List<PurchaseOrderCollect> findLatestReceiveDetail(PurchaseOrderCollectQuery purchaseOrderCollectQuery) {
		String systemBookCode = purchaseOrderCollectQuery.getSystemBookCode();
		List<Integer> branchNums = new ArrayList<Integer>();
		Integer branchNum = purchaseOrderCollectQuery.getBranchNum();
		if (branchNum != null) {
			branchNums.add(branchNum);
		} else {
			branchNums.addAll(purchaseOrderCollectQuery.getBranchNums());
		}
		List<Integer> itemNums = purchaseOrderCollectQuery.getItemNums();
		List<String> itemCategoryCodes = purchaseOrderCollectQuery.getItemCategoryCodes();
		List<Integer> supplierNums = purchaseOrderCollectQuery.getSupplierNums();
		Date dateFrom = purchaseOrderCollectQuery.getDtFrom();
		Date dateTo = purchaseOrderCollectQuery.getDtTo();
		String operator = purchaseOrderCollectQuery.getOperator();

		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
		List<PurchaseOrderCollect> list = new ArrayList<PurchaseOrderCollect>();
		
		List<Object[]> receiveObjects = receiveOrderDao.findQueryDetails(systemBookCode, branchNums, dateFrom, dateTo,
				itemNums, itemCategoryCodes, supplierNums, operator, null);
		
		Integer orderBranchNum = null;
		Integer itemNum = null;
		Integer itemMatrixNum = null;
		Date receiveDate = null;
		Supplier supplier = null;
		Boolean receiveOrderAntiFlag = null;
		Boolean receiveOrderRepealFlag = null;
		for (int i = 0; i < receiveObjects.size(); i++) {
			Object[] object = receiveObjects.get(i);
			
			receiveDate = (Date) object[2];
			itemNum = (Integer) object[3];
			itemMatrixNum = object[13] == null?0:(Integer)object[13];
			orderBranchNum = (Integer) object[20];
			
			receiveOrderRepealFlag = object[22] == null?false:(Boolean)object[22];
			receiveOrderAntiFlag = object[23] == null?false:(Boolean)object[23];

			
			//AMA-9641
			if(receiveOrderAntiFlag || receiveOrderRepealFlag){
				continue;
			}
			
			PurchaseOrderCollect persisent = PurchaseOrderCollect.get(list, orderBranchNum, itemNum, itemMatrixNum);
			if(persisent == null){
				
				PurchaseOrderCollect data = new PurchaseOrderCollect();
				data.setItemNum(itemNum);
				data.setItemMatrixNum(itemMatrixNum);
				data.setBranchNum(orderBranchNum);
				data.setPurchaseOrderNo((String) object[0]);
				supplier = (Supplier) object[1];
				data.setSupplierName(supplier.getSupplierCode() + "|" + supplier.getSupplierName());
				data.setOperateDate(receiveDate);
				data.setPurchaseItemPrice((BigDecimal) object[5]);
				data.setPurchaseItemUnit((String) object[18]);
				
				PosItem posItem = AppUtil.getPosItem(data.getItemNum(), posItems);
				if (posItem == null) {
					continue;
				}
				if (posItem.getItemDelTag() != null && posItem.getItemDelTag()) {
					continue;
				}
				if (posItem.getItemEliminativeFlag() != null && posItem.getItemEliminativeFlag()) {
					continue;
				}
				data.setItemCode(posItem.getItemCode());
				data.setItemName(posItem.getItemName());
				data.setItemCategory(posItem.getItemCategory());
				data.setItemCategoryCode(posItem.getItemCategoryCode());
				
				ItemMatrix itemMatrix = AppUtil.getItemMatrix(posItem.getItemMatrixs(), data.getItemNum(), data.getItemMatrixNum());
				if (itemMatrix != null) {
					data.setItemName(data.getItemName().concat(AppUtil.getMatrixName(itemMatrix)));
				}
				list.add(data);
			} else {
				if(receiveDate.compareTo(persisent.getOperateDate()) > 0){
					persisent.setPurchaseOrderNo((String) object[0]);
					supplier = (Supplier) object[1];
					persisent.setSupplierName(supplier.getSupplierCode() + "|" + supplier.getSupplierName());
					persisent.setOperateDate(receiveDate);
					persisent.setPurchaseItemPrice((BigDecimal) object[5]);
					persisent.setPurchaseItemUnit((String) object[18]);
										
				}
								
			}

		}		
		return list;
	}

	@Override
	public List<CardDepositCommissionDTO> findCardDepositCommissionDTOs(String systemBookCode,
                                                                        List<Integer> branchNums, Date dateFrom, Date dateTo, Integer groupType, String sellers) {
		return reportDao.findCardDepositCommissionDTOs(systemBookCode, branchNums, dateFrom, dateTo, groupType, sellers);
	}
	
	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer("1200");
		sb.insert(2, ":");
		System.out.println(sb.toString());
	}

	@Override
	public List<CustomerAnalysisTimePeriod> findCustomerAnalysisTimePeriods(String systemBookCode, Date dateFrom,
																			Date dateTo, List<Integer> branchNums, List<Integer> itemNums, String saleType, Date timeFrom, Date timeTo) {


		String timeFromStr = DateUtil.getHHmmStr2(timeFrom);
		String timeToStr = DateUtil.getHHmmStr2(timeTo);


		List<CustomerAnalysisTimePeriod> list = new ArrayList<CustomerAnalysisTimePeriod>();

		List<Object[]> objects = null;
		if (itemNums != null && itemNums.size() > 0) {
			objects = posOrderDao.findCustomerAnalysisTimePeriodsByItems(systemBookCode, dateFrom, dateTo, branchNums,
					itemNums, saleType, timeFromStr, timeToStr);
		} else {
			objects = posOrderDao.findCustomerAnalysisTimePeriods(systemBookCode, dateFrom, dateTo, branchNums,
					saleType, timeFromStr, timeToStr);
		}
		int size = objects.size();
		Object[] object = null;
		for(int i = 0;i < size;i++){

			object = objects.get(i);
			CustomerAnalysisTimePeriod data = new CustomerAnalysisTimePeriod();
			data.setBranchNum((Integer) object[0]);
			data.setTotalMoney(object[1] == null?BigDecimal.ZERO:(BigDecimal)object[1]);
			data.setCustomerNums(object[2] == null?BigDecimal.ZERO:BigDecimal.valueOf((Integer) object[2]));

			data.setCustomerAvePrice(BigDecimal.ZERO);
			if (data.getCustomerNums().compareTo(BigDecimal.ZERO) > 0) {
				data.setCustomerAvePrice(data.getTotalMoney().divide(data.getCustomerNums(), 4,
						BigDecimal.ROUND_HALF_UP));
			}

			list.add(data);
		}
		return list;
	}

	@Override
	public List<CardReportDTO> findCardReportByBranchDay(String systemBookCode, List<Integer> branchNums,
                                                         Date dateFrom, Date dateTo, Integer cardUserCardType) {
		List<CardReportDTO> cardReportDTOs = new ArrayList<CardReportDTO>();
		List<Object[]> cardSendObjects = cardUserDao.findCardCountByBranchBizday(systemBookCode, branchNums, dateFrom,
				dateTo, cardUserCardType);
		List<Object[]> cardRevokeObjects = cardUserDao.findRevokeCardCountByBranchBizday(systemBookCode, branchNums,
				dateFrom, dateTo, cardUserCardType);
		List<Object[]> cardDepositObjects = cardDepositDao.findBranchBizdayPaymentTypeSum(systemBookCode, branchNums, dateFrom, dateTo,
				cardUserCardType);
		List<Object[]> cardConsumeObjects = cardConsumeDao.findBranchBizdaySum(systemBookCode, branchNums, dateFrom, dateTo,
				cardUserCardType);
		List<Branch> branchs = branchService.findInCache(systemBookCode);
////
		for (int i = 0; i < cardSendObjects.size(); i++) {
			Object[] sendObject = cardSendObjects.get(i);
			CardReportDTO cardReportDTO = new CardReportDTO();
			cardReportDTO.setBranchNum((Integer) sendObject[0]);
			cardReportDTO.setBranchName(AppUtil.getBranch(branchs, cardReportDTO.getBranchNum()).getBranchName());
			cardReportDTO.setBizDay((String) sendObject[1]);
			cardReportDTOs.add(cardReportDTO);
			cardReportDTO.setSendCardCount(((Long) sendObject[2]).intValue());
		}
		for (int i = 0; i < cardRevokeObjects.size(); i++) {
			Object[] revokeObject = cardRevokeObjects.get(i);
			CardReportDTO cardReportDTO = CardReportDTO.readByBranchBizday((String) revokeObject[1], (Integer) revokeObject[0], cardReportDTOs);
			if (cardReportDTO == null) {
				cardReportDTO = new CardReportDTO();
				cardReportDTO.setBranchNum((Integer) revokeObject[0]);
				cardReportDTO.setBranchName(AppUtil.getBranch(branchs, cardReportDTO.getBranchNum()).getBranchName());
				cardReportDTO.setBizDay((String) revokeObject[1]);
				cardReportDTOs.add(cardReportDTO);
			}
			cardReportDTO.setReturnCardCount(((Long) revokeObject[2]).intValue());
			cardReportDTO.setReturnCardMoney(revokeObject[3] == null?BigDecimal.ZERO:(BigDecimal)revokeObject[3]);

		}
		
		String paymentType = null;
		for (int i = 0; i < cardDepositObjects.size(); i++) {
			Object[] depositObject = cardDepositObjects.get(i);
			paymentType = (String) depositObject[2];
			CardReportDTO cardReportDTO = CardReportDTO.readByBranchBizday((String) depositObject[1], (Integer) depositObject[0], cardReportDTOs);
			if (cardReportDTO == null) {
				cardReportDTO = new CardReportDTO();
				cardReportDTO.setBranchNum((Integer) depositObject[0]);
				cardReportDTO.setBranchName(AppUtil.getBranch(branchs, cardReportDTO.getBranchNum()).getBranchName());
				cardReportDTO.setBizDay((String) depositObject[1]);
				cardReportDTOs.add(cardReportDTO);
			}
			if(paymentType.equals(AppConstants.PAYMENT_ORI)){
				cardReportDTO.setOriDepositMoney(cardReportDTO.getOriDepositMoney().add((BigDecimal) depositObject[4]));
			} else {
				cardReportDTO.setPaymentMoney(cardReportDTO.getPaymentMoney().add((BigDecimal) depositObject[3]));
				cardReportDTO.setDepositMoney(cardReportDTO.getDepositMoney().add((BigDecimal) depositObject[4]));
				cardReportDTO.setSendMoney(cardReportDTO.getDepositMoney().subtract(cardReportDTO.getPaymentMoney()));
				
			}
		}
		for (int i = 0; i < cardConsumeObjects.size(); i++) {
			Object[] consumeObject = cardConsumeObjects.get(i);
			CardReportDTO cardReportDTO = CardReportDTO.readByBranchBizday((String) consumeObject[1], (Integer) consumeObject[0], cardReportDTOs);
			if (cardReportDTO == null) {
				cardReportDTO = new CardReportDTO();
				cardReportDTO.setBranchNum((Integer) consumeObject[0]);
				cardReportDTO.setBranchName(AppUtil.getBranch(branchs, cardReportDTO.getBranchNum()).getBranchName());
				cardReportDTO.setBizDay((String) consumeObject[1]);
				cardReportDTOs.add(cardReportDTO);
			}
			cardReportDTO.setConsumeMoney((BigDecimal) consumeObject[2]);
		}
		return cardReportDTOs;
	}

	@Override
	public List<SaleAnalysisBranchItemGradeDTO> findSaleAnalysisBranchItemGradeDTOs(
			SaleAnalysisQueryData saleAnalysisQueryData) {
		if(saleAnalysisQueryData.getPosItemNums() == null || saleAnalysisQueryData.getPosItemNums().size() == 0){
			return new ArrayList<SaleAnalysisBranchItemGradeDTO>();
		}
		Integer itemNum = saleAnalysisQueryData.getPosItemNums().get(0);
		List<PosItemGrade> posItemGrades = posItemGradeDao.find(saleAnalysisQueryData.getSystemBookCode(), itemNum);
		if(posItemGrades.size() == 0){
			return new ArrayList<SaleAnalysisBranchItemGradeDTO>();
		}		
		List<Object[]> objects = posOrderDao.findBranchGradeSummary(saleAnalysisQueryData);
		if(objects.size() == 0){
			return new ArrayList<SaleAnalysisBranchItemGradeDTO>();
		}
			
		List<SaleAnalysisBranchItemGradeDTO> list = new ArrayList<SaleAnalysisBranchItemGradeDTO>();
		Integer branchNum;
		Integer itemGradeNum;
		BigDecimal amount;
		BigDecimal money;
		BigDecimal profit;
		for(int i = 0;i < objects.size();i++){
			Object[] object = objects.get(i);
			branchNum = (Integer) object[0];
			itemGradeNum = object[1] == null ? 0 : (Integer) object[1];
			amount = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			money = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			profit = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];

			SaleAnalysisBranchItemGradeDTO dto = SaleAnalysisBranchItemGradeDTO.get(list, branchNum);
			if(dto == null){
				dto = new SaleAnalysisBranchItemGradeDTO();
				dto.setBranchNum(branchNum);
				
				for(int j = 0;j < posItemGrades.size();j++){
					PosItemGrade posItemGrade = posItemGrades.get(j);
					NameAndValueDTO detailDTO = new NameAndValueDTO();
					detailDTO.setIntValue(posItemGrade.getItemGradeNum());
					detailDTO.setName(posItemGrade.getItemGradeName());
					detailDTO.setValue(BigDecimal.ZERO);
					dto.getDetails().add(detailDTO);
				}	
				list.add(dto);
			}
			NameAndValueDTO detailDTO = NameAndValueDTO.get(dto.getDetails(), itemGradeNum);
			if(detailDTO == null){
				detailDTO = new NameAndValueDTO();
				detailDTO.setIntValue(itemGradeNum);
				detailDTO.setName("未知分级" + itemGradeNum);
				detailDTO.setValue(BigDecimal.ZERO);
				dto.getDetails().add(detailDTO);
			}
			detailDTO.setValue(profit);
						
			dto.setSaleAmount(dto.getSaleAmount().add(amount));
			dto.setSaleMoney(dto.getSaleMoney().add(money));
			dto.setSaleProfit(dto.getSaleProfit().add(profit));

		}
		
		List<Branch> branchs = branchService.findInCache(saleAnalysisQueryData.getSystemBookCode());
		SaleAnalysisBranchItemGradeDTO dto;
		NameAndValueDTO detailDto;
		for(int i = 0;i < list.size();i++){
			dto = list.get(i);
			
			Branch branch = AppUtil.getBranch(branchs, dto.getBranchNum());
			if(branch != null){
				dto.setBranchName(branch.getBranchName());
				dto.setBranchCode(branch.getBranchCode());
				dto.setBranchRegionNum(branch.getBranchRegionNum());
			}
			if(dto.getSaleMoney().compareTo(BigDecimal.ZERO) > 0){
				dto.setSaleProfitRate(dto.getSaleProfit().divide(dto.getSaleMoney(), 4, BigDecimal.ROUND_HALF_UP));
			
				
			}
			if(dto.getSaleProfit().compareTo(BigDecimal.ZERO) > 0){
				for(int j = 0;j < dto.getDetails().size();j++){
					detailDto =  dto.getDetails().get(j);
					detailDto.setValue(detailDto.getValue().divide(dto.getSaleProfit(), 4, BigDecimal.ROUND_HALF_UP));
				}
				
			}
			
		}
		return list;
	}

	@Override
	public List<OtherInfoSummaryDTO> findOtherInfos(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		List<Branch> branches = branchService.findInCache(systemBookCode);
		if(branchNums== null || branchNums.size() == 0) {
			branchNums = branches.stream().filter(b -> b.getBranchActived() != null && b.getBranchActived() && (b.getBranchVirtual() == null || !b.getBranchVirtual())).map(b -> b.getId().getBranchNum()).collect(Collectors.toList());
			branchNums = branches.stream().map(b -> b.getId().getBranchNum()).collect(Collectors.toList());
		}
		ReportUtil<OtherInfoSummaryDTO> reportUtil = new ReportUtil<>(OtherInfoSummaryDTO.class);
		for(Integer branchNum : branchNums) {
			List<NameAndValueDTO> dtos = mobileAppV2Service.findOtherInfos(systemBookCode, Collections.singletonList(branchNum), dateFrom, dateTo);
			for(NameAndValueDTO dto : dtos) {
				OtherInfoSummaryDTO otherInfoSummaryDTO = reportUtil.getInstance();
				otherInfoSummaryDTO.setBranchNum(branchNum);
				otherInfoSummaryDTO.setBranchName(AppUtil.getBranch(branches, branchNum).getBranchName());
				switch (dto.getName()) {
					case "删除":
						otherInfoSummaryDTO.setDeleteCount(dto.getIntValue());
						otherInfoSummaryDTO.setDeleteMoney(dto.getValue());
						break;
					case "退货":
						otherInfoSummaryDTO.setReturnCount(dto.getIntValue());
						otherInfoSummaryDTO.setReturnMoney(dto.getValue());
						break;
					case "整单取消":
						otherInfoSummaryDTO.setOrderCancelCount(dto.getIntValue());
						otherInfoSummaryDTO.setOrderCancelMoney(dto.getValue());
						break;
					case "挂单":
						otherInfoSummaryDTO.setHangOrderCount(dto.getIntValue());
						otherInfoSummaryDTO.setHangOrderMoney(dto.getValue());
						break;
					case "修改价格":
						otherInfoSummaryDTO.setChangePriceCount(dto.getIntValue());
						otherInfoSummaryDTO.setChangePriceMoney(dto.getValue());
						break;
					case "打开钱箱":
						otherInfoSummaryDTO.setCashBoxOpenCount(dto.getIntValue());
						break;
					case "反结账":
						otherInfoSummaryDTO.setAntiOrderCount(dto.getIntValue());
						otherInfoSummaryDTO.setAntiOrderMoney(dto.getValue());
						break;
					case "经理折扣":
						otherInfoSummaryDTO.setMgrDiscountCount(dto.getIntValue());
						otherInfoSummaryDTO.setMgrDiscountMoney(dto.getValue());
						break;
				}
				reportUtil.add(otherInfoSummaryDTO);
			}
		}
		return reportUtil.toList();

	}

	@Override
	public List<OtherInfoDTO> findOtherInfoDetails(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, String infoType) {
		return mobileAppV2Service.findOtherInfoDetails(systemBookCode, branchNum, dateFrom, dateTo, infoType);
	}
}
