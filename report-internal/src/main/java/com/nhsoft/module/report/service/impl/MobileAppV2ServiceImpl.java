package com.nhsoft.module.report.service.impl;


import com.nhsoft.module.report.dao.*;
import com.nhsoft.module.report.dto.*;
import com.nhsoft.module.report.model.Branch;
import com.nhsoft.module.report.model.GroupCustomer;
import com.nhsoft.module.report.model.RetailPosLog;
import com.nhsoft.module.report.param.PosItemTypeParam;
import com.nhsoft.module.report.service.*;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.module.report.util.CopyUtil;
import com.nhsoft.module.report.util.RedisUtil;
import com.nhsoft.phone.server.model.MobileBusiness;
import com.nhsoft.phone.server.model.MobileSalesRank;
import com.nhsoft.phone.server.model.SalesDiscount;
import com.nhsoft.module.report.query.CardUserQuery;
import com.nhsoft.module.report.query.LogQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class MobileAppV2ServiceImpl implements MobileAppV2Service {
	
	@Autowired
	private MobileAppDao mobileAppDao;
	@Autowired
	private PosOrderDao posOrderDao;
	@Autowired
	private CardDepositDao cardDepositDao;
	@Autowired
	private ReplaceCardDao replaceCardDao;
	@Autowired
	private RelatCardDao relatCardDao;
	@Autowired
	private OtherInoutDao otherInoutDao;
	@Autowired
	private CardUserDao cardUserDao;
	@Autowired
	private RetailPosLogDao retailPosLogDao;
	@Autowired
	private BranchService branchService;
	@Autowired
	private BookResourceService bookResourceService;
	@Autowired
	private CardUserService cardUserService;
	@Autowired
	private GroupCustomerDao groupCustomerDao;
	@Autowired
	private BranchResourceService branchResourceService;
	@Autowired
	private CardConsumeDao cardConsumeDao;
	@Autowired
	private BranchGroupService branchGroupService;
	@Autowired
	private ShipOrderDao shipOrderDao;
	@Autowired
	private CardUserLogDao cardUserLogDao;

	@Override
	public MobileBusinessDTO getIndexMobileBusinessDTO(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                                       Date dateTo) {
		
		MobileBusinessDTO dto = mobileAppDao
				.findMobileAppBusinessDTO(systemBookCode, branchNums, dateFrom, dateTo);		
			dto.setCashTotal(dto.getCashTotal().add(
				posOrderDao.getPosCash(systemBookCode, branchNums, dateFrom, dateTo)));
		dto.setCashTotal(dto.getCashTotal().add(
				cardDepositDao.getCashMoney(systemBookCode, branchNums, dateFrom, dateTo, AppConstants.PAYMENT_CASH)));
		dto.setCashTotal(dto.getCashTotal().add(
				replaceCardDao.getCashMoney(systemBookCode, branchNums, dateFrom, dateTo)));
		dto.setCashTotal(dto.getCashTotal().add(
				relatCardDao.getCashMoney(systemBookCode, branchNums, dateFrom, dateTo)));
		dto.setCashTotal(dto.getCashTotal().add(
				otherInoutDao.getCashMoney(systemBookCode, branchNums, dateFrom, dateTo)));
		dto.setCardDeposit(cardDepositDao.getCashMoney(systemBookCode, branchNums, dateFrom, dateTo, null));
		dto.setCardAddedCount(cardUserDao.findTotalCardCount(systemBookCode, branchNums, dateFrom, dateTo, null));
		return dto;
	}

	@Override
	public List<NameAndValueDTO> findOtherInfos(String systemBookCode, List<Integer> branchNums, Date dateFrom,
	                                            Date dateTo) {
		List<NameAndValueDTO> list = new ArrayList<NameAndValueDTO>();
		
		List<Object[]> objects = retailPosLogDao.findTypeCountAndMoney(systemBookCode, branchNums, dateFrom, dateTo, null);
		String typeName;
		int count;	
		BigDecimal money;
		NameAndValueDTO nameAndValueDTO;
		for(int i = 0;i < objects.size();i++){
			Object[] object = objects.get(i);
			
			typeName = (String)object[0];
			count = object[1] == null?0:(Integer)object[1];
			money = object[2] == null?BigDecimal.ZERO:(BigDecimal)object[2];
			
			nameAndValueDTO = new NameAndValueDTO();
			nameAndValueDTO.setName(typeName);
			nameAndValueDTO.setIntValue(count);
			nameAndValueDTO.setValue(money);
			list.add(nameAndValueDTO);
		}
		
		Object[] object = posOrderDao.findRepayCountAndMoney(systemBookCode, branchNums, dateFrom, dateTo);
		nameAndValueDTO = new NameAndValueDTO();
		nameAndValueDTO.setName("反结账");
		nameAndValueDTO.setIntValue(object[0] == null?0:(Integer)object[0]);
		nameAndValueDTO.setValue(object[1] == null?BigDecimal.ZERO:(BigDecimal)object[1]);
		list.add(nameAndValueDTO);
		object = posOrderDao.sumBusiDiscountAnalysisAmountAndMoney(systemBookCode, dateFrom, dateTo, branchNums);
		nameAndValueDTO = new NameAndValueDTO();
		nameAndValueDTO.setName("客户折扣");
		nameAndValueDTO.setIntValue(object[0] == null?0:(Integer)object[0]);
		nameAndValueDTO.setValue(object[1] == null?BigDecimal.ZERO:(BigDecimal)object[1]);
		list.add(nameAndValueDTO);
		nameAndValueDTO = new NameAndValueDTO();
		nameAndValueDTO.setName("经理折扣");
		nameAndValueDTO.setIntValue(object[2] == null?0:(Integer)object[2]);
		nameAndValueDTO.setValue(object[3] == null?BigDecimal.ZERO:(BigDecimal)object[3]);
		list.add(nameAndValueDTO);
		return list;
	}

	@Override
	public List<NameAndValueDTO> findDiscountDetails(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo) {
		List<NameAndValueDTO> list = new ArrayList<NameAndValueDTO>();

		List<BranchDTO> branchs = CopyUtil.toList(branchService.findInCache(systemBookCode),BranchDTO.class);
		
		NameAndValueDTO nameAndValueDTO = null;
		nameAndValueDTO = new NameAndValueDTO();
		nameAndValueDTO.setValue(BigDecimal.ZERO);
		nameAndValueDTO.setName("客户折扣");
		nameAndValueDTO.createDetails(branchNums, branchs);
		list.add(nameAndValueDTO);
		
		nameAndValueDTO = new NameAndValueDTO();
		nameAndValueDTO.setValue(BigDecimal.ZERO);
		nameAndValueDTO.setName("会员折扣");
		nameAndValueDTO.createDetails(branchNums, branchs);
		list.add(nameAndValueDTO);
		
		nameAndValueDTO = new NameAndValueDTO();
		nameAndValueDTO.setValue(BigDecimal.ZERO);
		nameAndValueDTO.setName("其他折扣");
		nameAndValueDTO.createDetails(branchNums, branchs);
		list.add(nameAndValueDTO);
		
		nameAndValueDTO = new NameAndValueDTO();
		nameAndValueDTO.setValue(BigDecimal.ZERO);
		nameAndValueDTO.setName("经理折扣");
		nameAndValueDTO.createDetails(branchNums, branchs);
		list.add(nameAndValueDTO);
		
		nameAndValueDTO = new NameAndValueDTO();
		nameAndValueDTO.setValue(BigDecimal.ZERO);
		nameAndValueDTO.setName("四舍五入");
		nameAndValueDTO.createDetails(branchNums, branchs);
		list.add(nameAndValueDTO);
		
		nameAndValueDTO = new NameAndValueDTO();
		nameAndValueDTO.setValue(BigDecimal.ZERO);
		nameAndValueDTO.setName("促销折扣");
		nameAndValueDTO.createDetails(branchNums, branchs);
		list.add(nameAndValueDTO);
		
		List<Object[]> objects = posOrderDao.findBusiDiscountAnalysisBranchs(systemBookCode, dateFrom, dateTo, branchNums);
		for(int i = 0;i < objects.size();i++){
			Object[] object = objects.get(i);
			
			Integer branchNum = (Integer)object[0];
						
			nameAndValueDTO = NameAndValueDTO.get(list, "客户折扣");
			NameAndValueDTO.NameAndValueDetailDTO nameAndValueDetailDTO = nameAndValueDTO.getDetail(branchNum);
			nameAndValueDetailDTO.setDetailValue((BigDecimal) object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1]);
			nameAndValueDTO.setValue(nameAndValueDTO.getValue().add(nameAndValueDetailDTO.getDetailValue()));
			
			nameAndValueDTO = NameAndValueDTO.get(list, "会员折扣");
			nameAndValueDetailDTO = nameAndValueDTO.getDetail(branchNum);
			nameAndValueDetailDTO.setDetailValue((BigDecimal) object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2]);
			nameAndValueDTO.setValue(nameAndValueDTO.getValue().add(nameAndValueDetailDTO.getDetailValue()));
						
			nameAndValueDTO = NameAndValueDTO.get(list, "经理折扣");
			nameAndValueDetailDTO = nameAndValueDTO.getDetail(branchNum);
			nameAndValueDetailDTO.setDetailValue((BigDecimal) object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5]);
			nameAndValueDTO.setValue(nameAndValueDTO.getValue().add(nameAndValueDetailDTO.getDetailValue()));
			
			nameAndValueDTO = NameAndValueDTO.get(list, "四舍五入");
			nameAndValueDetailDTO = nameAndValueDTO.getDetail(branchNum);
			nameAndValueDetailDTO.setDetailValue((BigDecimal) object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6]);
			nameAndValueDTO.setValue(nameAndValueDTO.getValue().add(nameAndValueDetailDTO.getDetailValue()));
			
			nameAndValueDTO = NameAndValueDTO.get(list, "促销折扣");
			nameAndValueDetailDTO = nameAndValueDTO.getDetail(branchNum);
			BigDecimal policyMoney = (BigDecimal) object[7] == null ? BigDecimal.ZERO : (BigDecimal) object[7];
			nameAndValueDetailDTO.setDetailValue(policyMoney);
			nameAndValueDTO.setValue(nameAndValueDTO.getValue().add(nameAndValueDetailDTO.getDetailValue()));
			
			nameAndValueDTO = NameAndValueDTO.get(list, "其他折扣");
			nameAndValueDetailDTO = nameAndValueDTO.getDetail(branchNum);
			nameAndValueDetailDTO.setDetailValue((BigDecimal) object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3]);
			nameAndValueDetailDTO.setDetailValue(nameAndValueDetailDTO.getDetailValue().subtract(policyMoney));
			nameAndValueDTO.setValue(nameAndValueDTO.getValue().add(nameAndValueDetailDTO.getDetailValue()));
			
		}
		return list;
	}

	@Override
	public List<NameAndValueDTO> findCashDetails(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo) {
		
		List<NameAndValueDTO> list = new ArrayList<NameAndValueDTO>();
		
		NameAndValueDTO nameAndValueDTO = null;
		nameAndValueDTO = new NameAndValueDTO();
		nameAndValueDTO.setValue(posOrderDao.getPosCash(systemBookCode, branchNums, dateFrom, dateTo));
		nameAndValueDTO.setName("POS收入");
		list.add(nameAndValueDTO);
		
		nameAndValueDTO = new NameAndValueDTO();
		nameAndValueDTO.setValue(cardDepositDao.getCashMoney(systemBookCode, branchNums, dateFrom, dateTo, AppConstants.PAYMENT_CASH));
		nameAndValueDTO.setName("卡存款");
		list.add(nameAndValueDTO);
		
		nameAndValueDTO = new NameAndValueDTO();
		nameAndValueDTO.setValue(replaceCardDao.getCashMoney(systemBookCode, branchNums, dateFrom, dateTo));
		nameAndValueDTO.setName("换卡");
		list.add(nameAndValueDTO);
		
		nameAndValueDTO = new NameAndValueDTO();
		nameAndValueDTO.setValue(relatCardDao.getCashMoney(systemBookCode, branchNums, dateFrom, dateTo));
		nameAndValueDTO.setName("续卡");
		list.add(nameAndValueDTO);
		
		nameAndValueDTO = new NameAndValueDTO();
		nameAndValueDTO.setValue(otherInoutDao.getCashMoney(systemBookCode, branchNums, dateFrom, dateTo));
		nameAndValueDTO.setName("其他收支");
		list.add(nameAndValueDTO);
		return list;
	}

	@Override
	public List<NameAndValueDTO> findPaymentDetails(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo) {
		List<Object[]> objects = mobileAppDao.findPaymentSummary(systemBookCode, branchNums, dateFrom, dateTo);
		List<NameAndValueDTO> list = new ArrayList<NameAndValueDTO>();

		Object[] object = null;
		boolean hasCouponType = false;
		for(int i = 0;i < objects.size();i++){
			object = objects.get(i);
			NameAndValueDTO dto = new NameAndValueDTO();
			
			dto.setName((String) object[0]);
			dto.setValue(object[1] == null?BigDecimal.ZERO:(BigDecimal)object[1]);
			if (dto.getName().equals(AppConstants.PAYMENT_COUPON)) {
				dto.setValue(dto.getValue().add(mobileAppDao.getCouponMoney(systemBookCode, branchNums, dateFrom, dateTo)));
				hasCouponType = true;
			}
			list.add(dto);
		}
		if (!hasCouponType) {

			BigDecimal couponMoney = mobileAppDao.getCouponMoney(systemBookCode, branchNums, dateFrom, dateTo);
			NameAndValueDTO dto = new NameAndValueDTO();
			dto.setValue(couponMoney);
			dto.setName(AppConstants.PAYMENT_COUPON);
			list.add(dto);
		}
		return list;
	}

	@Override
	public List<NameAndValueDTO> findCardDepositDetails(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo) {
		List<Object[]> objects = cardDepositDao.findMoneyByType(systemBookCode, branchNums, dateFrom, dateTo);
		List<NameAndValueDTO> list = new ArrayList<NameAndValueDTO>();

		NameAndValueDTO sumDTO = new NameAndValueDTO();
		sumDTO.setName("卡存款累计总额");
		sumDTO.setValue(BigDecimal.ZERO);
		list.add(sumDTO);
		
		Object[] object = null;
		for(int i = 0;i < objects.size();i++){
			object = objects.get(i);
			NameAndValueDTO dto = new NameAndValueDTO();
			
			dto.setName((String) object[0]);
			dto.setValue(object[1] == null?BigDecimal.ZERO:(BigDecimal)object[1]);
			list.add(dto);
			
			sumDTO.setValue(sumDTO.getValue().add(dto.getValue()));
		}
		return list;
	}

	@Override
	public List<NameAndTwoValueDTO> findItemRank(String systemBookCode, List<Integer> branchNums, Date dateFrom,
	                                             Date dateTo, Integer rankFrom, Integer rankTo, String sortField) {
		List<MobileSalesRank> mobileSalesRanks = mobileAppDao.findProductRank(systemBookCode, branchNums, dateFrom, dateTo, null, null, rankFrom, rankTo,
				sortField);
		List<NameAndTwoValueDTO> list = new ArrayList<NameAndTwoValueDTO>();
		
		NameAndTwoValueDTO totalDTO = new NameAndTwoValueDTO();
		totalDTO.setValue(BigDecimal.ZERO);
		totalDTO.setValue2(BigDecimal.ZERO);
		
		for(int i = 0;i < mobileSalesRanks.size();i++){
			MobileSalesRank mobileSalesRank = mobileSalesRanks.get(i);
			NameAndTwoValueDTO dto = new NameAndTwoValueDTO();
			dto.setKey(mobileSalesRank.getSalesId());
			dto.setName(mobileSalesRank.getSalesName());
			dto.setValue(mobileSalesRank.getSalesCount());
			dto.setValue2(mobileSalesRank.getSelesMoney());
			list.add(dto);
			totalDTO.setValue(totalDTO.getValue().add(dto.getValue()));
			totalDTO.setValue2(totalDTO.getValue2().add(dto.getValue2()));
			 
		}
		if(rankFrom == null && rankTo == null){
			list.add(totalDTO);
		}
		return list;
	}
	
	private MobileSalesRank readFromList(List<MobileSalesRank> mobileSalesRanks, String itemTypeCode) {
		for (int i = 0; i < mobileSalesRanks.size(); i++) {
			MobileSalesRank rank = mobileSalesRanks.get(i);
			if (rank.getSalesCode().equals(itemTypeCode)) {
				return rank;
			}
		}
		return null;
	}

	@Override
	public List<NameAndTwoValueDTO> findCategoryRank(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo, Integer rankFrom, Integer rankTo, String sortField) {
		
		List<NameAndTwoValueDTO> dtos = new ArrayList<NameAndTwoValueDTO>();

		List<MobileSalesRank> list = mobileAppDao.findCategoryRank(systemBookCode, branchNums, dateFrom, dateTo,
				null);
		if(list.size() == 0){
			return dtos;
		}
		
		List<PosItemTypeParam> posItemTypeParams = bookResourceService.findPosItemTypeParamsInCache(systemBookCode);
		List<MobileSalesRank> returnList = new ArrayList<MobileSalesRank>();
		for (int i = list.size() - 1; i >= 0; i--) {
			MobileSalesRank childRank = list.get(i);
			String code = childRank.getSalesCode();
			PosItemTypeParam param = AppUtil.findTopCategory(code, posItemTypeParams);

			if (param == null) {
				continue;
			}
			MobileSalesRank newRank = readFromList(returnList, param.getPosItemTypeCode());
			if (newRank == null) {
	
				newRank = new MobileSalesRank();
				BeanUtils.copyProperties(childRank, newRank);
				newRank.setSalesName(param.getPosItemTypeName());
				newRank.setSalesCode(param.getPosItemTypeCode());
				newRank.setSalesId(param.getPosItemTypeCode());				
				returnList.add(newRank);
			} else {
				newRank.setSalesCount(newRank.getSalesCount().add(childRank.getSalesCount()));
				newRank.setSelesMoney(newRank.getSelesMoney().add(childRank.getSelesMoney()));
				newRank.setSelesUnitCount(newRank.getSelesUnitCount().add(childRank.getSelesUnitCount()));
			}
		}
		if(StringUtils.isEmpty(sortField)){
			sortField = "money";
		}
		Comparator<MobileSalesRank> comparator = null;
		if(sortField.equals("amount")){
			comparator = new Comparator<MobileSalesRank>() {

				@Override
				public int compare(MobileSalesRank o1, MobileSalesRank o2) {
					
					if (o1.getSalesCount().compareTo(o2.getSalesCount()) > 0) {
						return -1;
					} else if (o1.getSalesCount().compareTo(o2.getSalesCount()) < 0) {
						return 1;
					} else {
						return -o1.getSelesMoney().compareTo(o2.getSelesMoney());
					}
				}

			};
		} else {
			comparator = new Comparator<MobileSalesRank>() {

				@Override
				public int compare(MobileSalesRank o1, MobileSalesRank o2) {
					
					if (o1.getSelesMoney().compareTo(o2.getSelesMoney()) > 0) {
						return -1;
					} else if (o1.getSelesMoney().compareTo(o2.getSelesMoney()) < 0) {
						return 1;
					} else {
						return -o1.getSalesCount().compareTo(o2.getSalesCount());
					}
				}
			};
		}
		
		Collections.sort(returnList, comparator);
		MobileSalesRank rank = null;
		if(rankFrom != null && rankTo != null){
			
			int count = returnList.size();
			for (int i = rankFrom; i < rankTo; i++) {
				if (i >= count) {
					break;
				}
				rank = returnList.get(i);
				NameAndTwoValueDTO dto = new NameAndTwoValueDTO();
				
				dto.setName(rank.getSalesName());
				dto.setValue(rank.getSalesCount());
				dto.setValue2(rank.getSelesMoney());
				dtos.add(dto);
			}
			NameAndTwoValueDTO otherDto = new NameAndTwoValueDTO();	
			otherDto.setName("其他类别");
			otherDto.setValue(BigDecimal.ZERO);
			otherDto.setValue2(BigDecimal.ZERO);
			
			for (int i = 0;i < returnList.size();i++) {
				
				if(i < rankFrom || i >= rankTo){
					
					rank = returnList.get(i);
					otherDto.setValue(otherDto.getValue().add(rank.getSalesCount()));
					otherDto.setValue2(otherDto.getValue2().add(rank.getSelesMoney()));
				}
				
			}
			dtos.add(otherDto);
		} else {
			for (int i = 0;i < returnList.size(); i++) {
				
				rank = returnList.get(i);
								
				NameAndTwoValueDTO dto = new NameAndTwoValueDTO();				
				dto.setName(rank.getSalesName());
				dto.setValue(rank.getSalesCount());
				dto.setValue2(rank.getSelesMoney());
				dtos.add(dto);
			}
			
		}
		return dtos;
		
	}
	
	@Override
	public List<NameAndTwoValueDTO> findSmallCategoryRank(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer rankFrom, Integer rankTo, String sortField) {
		List<NameAndTwoValueDTO> dtos = new ArrayList<NameAndTwoValueDTO>();
		
		List<MobileSalesRank> list = mobileAppDao.findCategoryRank(systemBookCode, branchNums, dateFrom, dateTo,
				null);
		if(list.size() == 0){
			return dtos;
		}
		
		if(StringUtils.isEmpty(sortField)){
			sortField = "money";
		}
		Comparator<MobileSalesRank> comparator = null;
		if(sortField.equals("amount")){
			comparator = new Comparator<MobileSalesRank>() {
				
				@Override
				public int compare(MobileSalesRank o1, MobileSalesRank o2) {
					
					if (o1.getSalesCount().compareTo(o2.getSalesCount()) > 0) {
						return -1;
					} else if (o1.getSalesCount().compareTo(o2.getSalesCount()) < 0) {
						return 1;
					} else {
						return -o1.getSelesMoney().compareTo(o2.getSelesMoney());
					}
				}
				
			};
		} else {
			comparator = new Comparator<MobileSalesRank>() {
				
				@Override
				public int compare(MobileSalesRank o1, MobileSalesRank o2) {
					
					if (o1.getSelesMoney().compareTo(o2.getSelesMoney()) > 0) {
						return -1;
					} else if (o1.getSelesMoney().compareTo(o2.getSelesMoney()) < 0) {
						return 1;
					} else {
						return -o1.getSalesCount().compareTo(o2.getSalesCount());
					}
				}
			};
		}
		
		Collections.sort(list, comparator);
		MobileSalesRank rank = null;
		if(rankFrom != null && rankTo != null){
			
			int count = list.size();
			for (int i = rankFrom; i < rankTo; i++) {
				if (i >= count) {
					break;
				}
				rank = list.get(i);
				NameAndTwoValueDTO dto = new NameAndTwoValueDTO();
				
				dto.setName(rank.getSalesName());
				dto.setValue(rank.getSalesCount());
				dto.setValue2(rank.getSelesMoney());
				dtos.add(dto);
			}
			NameAndTwoValueDTO otherDto = new NameAndTwoValueDTO();
			otherDto.setName("其他类别");
			otherDto.setValue(BigDecimal.ZERO);
			otherDto.setValue2(BigDecimal.ZERO);
			
			for (int i = 0;i < list.size();i++) {
				
				if(i < rankFrom || i >= rankTo){
					
					rank = list.get(i);
					otherDto.setValue(otherDto.getValue().add(rank.getSalesCount()));
					otherDto.setValue2(otherDto.getValue2().add(rank.getSelesMoney()));
				}
				
			}
			dtos.add(otherDto);
		} else {
			for (int i = 0;i < list.size(); i++) {
				
				rank = list.get(i);
				
				NameAndTwoValueDTO dto = new NameAndTwoValueDTO();
				dto.setName(rank.getSalesName());
				dto.setValue(rank.getSalesCount());
				dto.setValue2(rank.getSelesMoney());
				dtos.add(dto);
			}
			
		}
		return dtos;
	}
	
	@Override
	public MobileBusinessPeriodDTO getMobileBusinessPeriodDTO(String systemBookCode, List<Integer> branchNums,
                                                              Date dateFrom, Date dateTo) {
		List<Object[]> objects = mobileAppDao.findShopTimeAnalysis(systemBookCode, branchNums, dateFrom, dateTo);
		MobileBusinessPeriodDTO dto = new MobileBusinessPeriodDTO();
		
		Object[] object;
		Integer hour;
		BigDecimal money;
		Integer receiptCount;
		NameAndValueDTO nameAndValueDTO;
		for(int i = 0;i < objects.size();i++){
			object = objects.get(i);
			hour = (Integer) object[0];
			money = object[1] == null?BigDecimal.ZERO:(BigDecimal)object[1];
			receiptCount = object[2] == null?0:(Integer) object[2];
			
			nameAndValueDTO = dto.getBusinessMoneyList().get(hour.intValue());
			nameAndValueDTO.setValue(money);
			
			nameAndValueDTO = dto.getReceiptCountList().get(hour.intValue());
			nameAndValueDTO.setValue(BigDecimal.valueOf(receiptCount));
			
			dto.setBusinessMoney(dto.getBusinessMoney().add(money));
			dto.setReceiptCount(dto.getReceiptCount() + receiptCount);
			
			
		}
		
		if(dto.getReceiptCount() > 0){
			dto.setCustomerAvgPrice(dto.getBusinessMoney().divide(BigDecimal.valueOf(dto.getReceiptCount()), 2, BigDecimal.ROUND_HALF_UP));

		} else {
			dto.setCustomerAvgPrice(BigDecimal.ZERO);
		}
		
		return dto;
	}

	@Override
	public List<NameAndValueDTO> findBranchCardCount(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo) {
	
		List<Branch> branchs = branchService.findInCache(systemBookCode);
		List<Object[]> objects = cardUserService.findCardCount(systemBookCode, branchNums, dateFrom, dateTo, null);
		List<NameAndValueDTO> list = new ArrayList<NameAndValueDTO>();
		
		Object[] object = null;
		Integer num = null;
		Integer count = null;
		Branch branch = null;
				
		//发卡数量相同的排名一样
		Integer lastCount = null;
		Integer lastRank = 0;
		int sameCountBranch = 1;
		Integer otherCardCount = 0; //其他门店发卡总数
		for(int i = 0;i < objects.size();i++){
			object = objects.get(i);
			num = (Integer) object[0];
			count = object[1] == null?0:(Integer) object[1] ;
			
			
			if(lastCount == null || count < lastCount){
				lastCount = count;
				lastRank = lastRank + 1 + (sameCountBranch - 1);
				sameCountBranch = 1;
			} else {
				sameCountBranch++;
			}
			boolean contain = false;
			if(branchNums.contains(num)){
				branchNums.remove(num);
				contain = true;
			}
			if(i < 10 || (i >= 10 && contain)){
				
				branch = AppUtil.getBranch(branchs, num);
				NameAndValueDTO dto = new NameAndValueDTO();
				dto.setIntValue(lastRank);//排名
				dto.setValue(BigDecimal.valueOf(count));//发卡量
				dto.setName(branch.getBranchName());
				list.add(dto);
				
			} else {
				otherCardCount = otherCardCount + count;

			}
		}
		if(branchNums.size() > 0){
			for(int i = 0;i < branchNums.size();i++){
				
				branch = AppUtil.getBranch(branchs, branchNums.get(i));
				
				NameAndValueDTO dto = new NameAndValueDTO();
				if(lastCount != null && lastCount == 0){
					dto.setIntValue(lastRank); 
					
				} else {
					
					dto.setIntValue(lastRank + 1); 
				}
				dto.setValue(BigDecimal.ZERO);
				dto.setName(branch.getBranchName());
				list.add(dto);
			}
			
		}
		if(otherCardCount > 0){
			NameAndValueDTO dto = new NameAndValueDTO();		
			dto.setValue(BigDecimal.valueOf(otherCardCount));
			dto.setName("其他门店");
			list.add(dto);
		}	
		return list;
	}

	@Override
	public MobileCardDTO getMobileCardDTO(String systemBookCode, List<Integer> branchNums) {
		return mobileAppDao.getMobileCardDTO(systemBookCode, branchNums);
	}

	@Override
	public List<NameAndValueDTO> findGroupCustomerCount(String systemBookCode, Integer branchNum) {
		List<NameAndValueDTO> list = new ArrayList<NameAndValueDTO>();
		List<GroupCustomer> groupCustomers = groupCustomerDao.findBybranch(systemBookCode, branchNum, null);
		for (int i = 0; i < groupCustomers.size(); i++) {
			GroupCustomer groupCustomer = groupCustomers.get(i);
			NameAndValueDTO dto = new NameAndValueDTO();
			dto.setName(groupCustomer.getGroupCustomerName());
			dto.setIntValue(AppUtil.getGroupCustomerValue(groupCustomer, AppConstants.CUSTOMER_MODEL_CUSTOMER_COUNT).intValue());
			list.add(dto);
		}
		return list;
	}

	private List<CustomerModelParam> createDefault(String systemBookCode, Integer branchNum) {
		List<CustomerModelParam> customerModelParams = new ArrayList<CustomerModelParam>();

		List<GroupCustomerDTO> groupCustomers = CopyUtil.toList(groupCustomerDao.findDefault(systemBookCode, branchNum),GroupCustomerDTO.class);
		CustomerModelParam customerModelParam = new CustomerModelParam();
		customerModelParam.setGroupCustomers(new ArrayList<GroupCustomerDTO>());
		customerModelParam.setModelName("消费频次模型(CFM)");
		customerModelParam.setModelType(AppConstants.CUSTOMER_MODEL_CUSTOMER_COUNT);
		customerModelParam.getGroupCustomers().add(GroupCustomerDTO.get(groupCustomers, systemBookCode + branchNum + GroupCustomer.SUFFIX_CFM_01));
		customerModelParam.getGroupCustomers().add(GroupCustomerDTO.get(groupCustomers, systemBookCode + branchNum + GroupCustomer.SUFFIX_CFM_02));
		customerModelParam.getGroupCustomers().add(GroupCustomerDTO.get(groupCustomers, systemBookCode + branchNum + GroupCustomer.SUFFIX_CFM_03));
		customerModelParam.getGroupCustomers().add(GroupCustomerDTO.get(groupCustomers, systemBookCode + branchNum + GroupCustomer.SUFFIX_CFM_04));
		customerModelParams.add(customerModelParam);

		customerModelParam = new CustomerModelParam();
		customerModelParam.setGroupCustomers(new ArrayList<GroupCustomerDTO>());
		customerModelParam.setModelName("消费能力模型(SPM)");
		customerModelParam.setModelType(AppConstants.CUSTOMER_MODEL_CUSTOMER_COUNT);
		customerModelParam.getGroupCustomers().add(GroupCustomerDTO.get(groupCustomers, systemBookCode + branchNum + GroupCustomer.SUFFIX_SPM_05));
		customerModelParam.getGroupCustomers().add(GroupCustomerDTO.get(groupCustomers, systemBookCode + branchNum + GroupCustomer.SUFFIX_SPM_06));
		customerModelParam.getGroupCustomers().add(GroupCustomerDTO.get(groupCustomers, systemBookCode + branchNum + GroupCustomer.SUFFIX_SPM_07));
		customerModelParam.getGroupCustomers().add(GroupCustomerDTO.get(groupCustomers, systemBookCode + branchNum + GroupCustomer.SUFFIX_SPM_08));
		customerModelParams.add(customerModelParam);

		customerModelParam = new CustomerModelParam();
		customerModelParam.setGroupCustomers(new ArrayList<GroupCustomerDTO>());
		customerModelParam.setModelName("客户流失模型(LOC)");
		customerModelParam.setModelType(AppConstants.CUSTOMER_MODEL_CUSTOMER_COUNT);
		customerModelParam.getGroupCustomers().add(GroupCustomerDTO.get(groupCustomers, systemBookCode + branchNum + GroupCustomer.SUFFIX_LOC_09));
		customerModelParam.getGroupCustomers().add(GroupCustomerDTO.get(groupCustomers, systemBookCode + branchNum + GroupCustomer.SUFFIX_LOC_10));
		customerModelParam.getGroupCustomers().add(GroupCustomerDTO.get(groupCustomers, systemBookCode + branchNum + GroupCustomer.SUFFIX_LOC_11));
		customerModelParam.getGroupCustomers().add(GroupCustomerDTO.get(groupCustomers, systemBookCode + branchNum + GroupCustomer.SUFFIX_LOC_12));
		customerModelParams.add(customerModelParam);
		return customerModelParams;
	}

	@Override
	public List<MobileCustomerModelDTO> findMobileCustomerModelDTOs(String systemBookCode, Integer branchNum) {
		List<CustomerModelParam> customerModelParams = createDefault(systemBookCode, branchNum);
		customerModelParams.addAll(branchResourceService.findCustomerModelParams(systemBookCode, branchNum));

		List<MobileCustomerModelDTO> list = new ArrayList<MobileCustomerModelDTO>();

		List<GroupCustomer> groupCustomers = groupCustomerDao.findBybranch(systemBookCode, branchNum, null);
		for (int i = 0; i < customerModelParams.size(); i++) {
			CustomerModelParam customerModelParam = customerModelParams.get(i);
			String type = customerModelParam.getModelType();
			for (int j = 0; j < customerModelParam.getGroupCustomers().size(); j++) {
				if (customerModelParam.getGroupCustomers().get(j) == null) {
					continue;
				}
				String groupCustomerId = customerModelParam.getGroupCustomers().get(j).getGroupCustomerId();
				GroupCustomer groupCustomer = GroupCustomer.get(groupCustomers, groupCustomerId);
				if (groupCustomer != null) {
					MobileCustomerModelDTO dto = new MobileCustomerModelDTO();
					dto.setModelMemo(groupCustomer.getGroupCustomerMemo());
					dto.setModelType(customerModelParam.getModelName().concat("(按" + type + ")"));
					dto.setModelName(groupCustomer.getGroupCustomerName());
					dto.setModelValueInt(AppUtil.getGroupCustomerValue(groupCustomer,
							AppConstants.CUSTOMER_MODEL_CUSTOMER_COUNT).intValue());
					if (type.equals(AppConstants.CUSTOMER_MODEL_COUPON_CONSUME)
							|| type.equals(AppConstants.CUSTOMER_MODEL_CONSUME_COUNT)
							|| type.equals(AppConstants.CUSTOMER_MODEL_CUSTOMER_COUNT)) {
						dto.setModelValueInt(AppUtil.getGroupCustomerValue(groupCustomer, type).intValue());
					} else {
						dto.setModelValue(AppUtil.getGroupCustomerValue(groupCustomer, type));

					}
					list.add(dto);
				}
			}
		}

		return list;
	}

	@Override
	public List<NameAndValueDTO> findBranchCardConsume(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo) {
		List<Object[]> objects = cardConsumeDao.findBranchSum(systemBookCode, branchNums, dateFrom, dateTo, null);
		List<NameAndValueDTO> list = new ArrayList<NameAndValueDTO>();
		if(objects.size() == 0){
			return list;
		}
		Object[] object = null;
		List<Branch> branchs = branchService.findInCache(systemBookCode);
		for(int i = 0;i < objects.size();i++){
			object = objects.get(i);
			
			NameAndValueDTO dto = new NameAndValueDTO();

			dto.setName(AppUtil.getBranch(branchs, (Integer) object[0]).getBranchName());
			dto.setValue(object[1] == null?BigDecimal.ZERO:(BigDecimal)object[1]);
			list.add(dto);
		}
		
		Collections.sort(list, new Comparator<NameAndValueDTO>() {

			@Override
			public int compare(NameAndValueDTO o1, NameAndValueDTO o2) {
				return -o1.getValue().compareTo(o2.getValue());
			}
		});		
		return list;
	}

	@Override
	public List<NameAndValueDTO> findBranchCardDeposit(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo) {
		List<Object[]> objects = cardDepositDao.findBranchSum(systemBookCode, branchNums, dateFrom, dateTo, null);
		List<NameAndValueDTO> list = new ArrayList<NameAndValueDTO>();
		if(objects.size() == 0){
			return list;
		}
		Object[] object = null;
		List<Branch> branchs = branchService.findInCache(systemBookCode);
		for(int i = 0;i < objects.size();i++){
			object = objects.get(i);
			
			NameAndValueDTO dto = new NameAndValueDTO();

			dto.setName(AppUtil.getBranch(branchs, (Integer) object[0]).getBranchName());
			dto.setValue(object[2] == null?BigDecimal.ZERO:(BigDecimal)object[2]);
			list.add(dto);
		}
		
		Collections.sort(list, new Comparator<NameAndValueDTO>() {

			@Override
			public int compare(NameAndValueDTO o1, NameAndValueDTO o2) {
				return -o1.getValue().compareTo(o2.getValue());
			}
		});		
		return list;
	}

	@Override
	public MobileCardDTO getMobileCardDTOByDate(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo, String dateType) {
		MobileCardDTO mobileCardDTO = new MobileCardDTO();
		mobileCardDTO.setConsumeTotalMoney(BigDecimal.ZERO);
		mobileCardDTO.setDepositTotalMoney(BigDecimal.ZERO);
		
		CardUserQuery cardUserQuery = new CardUserQuery();
		cardUserQuery.setSystemBookCode(systemBookCode);
		cardUserQuery.setBranchNums(branchNums);
		cardUserQuery.setDateFrom(dateFrom);
		cardUserQuery.setDateTo(dateTo);		
		Object[] object = cardUserDao.sumByCardUserQuery(cardUserQuery);		
		mobileCardDTO.setNewCardCount((Integer) object[0]);
		
		List<Object[]> objects = cardConsumeDao.findDateSummary(systemBookCode, branchNums, dateFrom, dateTo, dateType);
		BigDecimal money = null;
		NameAndValueDTO nameAndValueDTO = null;
		for(int i = 0;i < objects.size();i++){
			object = objects.get(i);
			money = object[1] == null?BigDecimal.ZERO:(BigDecimal)object[1];
			
			nameAndValueDTO = new NameAndValueDTO();
			nameAndValueDTO.setName((String) object[0]);
			nameAndValueDTO.setValue(money);
			mobileCardDTO.getConsumeDateMoneyDetails().add(nameAndValueDTO);
			
			mobileCardDTO.setConsumeTotalMoney(mobileCardDTO.getConsumeTotalMoney().add(money));
			
		}
		
		objects = cardDepositDao.findDateSummary(systemBookCode, branchNums, dateFrom, dateTo, dateType);
		for(int i = 0;i < objects.size();i++){
			object = objects.get(i);
			money = object[1] == null?BigDecimal.ZERO:(BigDecimal)object[1];
			
			nameAndValueDTO = new NameAndValueDTO();
			nameAndValueDTO.setName((String) object[0]);
			nameAndValueDTO.setValue(money);
			mobileCardDTO.getDepositDateMoneyDetails().add(nameAndValueDTO);
			
			mobileCardDTO.setDepositTotalMoney(mobileCardDTO.getDepositTotalMoney().add(money));
			
		}		
		return mobileCardDTO;
	}

	@Override
	public void addUserBranchNum(Integer appUserNum, Integer branchNum) {
		RedisUtil.setPut(AppConstants.REDIS_PRE_USER_COLLECT_BRANCH + appUserNum, branchNum);
		
	}

	@Override
	public void deleteUserBranchNum(Integer appUserNum, Integer branchNum) {
		RedisUtil.setRemove(AppConstants.REDIS_PRE_USER_COLLECT_BRANCH + appUserNum, branchNum);
		
	}

	@Override
	public List<Integer> findUserBranchNums(Integer appUserNum) {
		return new ArrayList<Integer>(RedisUtil.setGet(AppConstants.REDIS_PRE_USER_COLLECT_BRANCH + appUserNum));
	}

	@Override
	public List<NameAndTwoValueDTO> findBranchPosSummary(String systemBookCode, List<Integer> branchNums,
			Integer itemNum, Date dateFrom, Date dateTo) {
		List<Integer> itemNums = new ArrayList<Integer>();
		itemNums.add(itemNum);
		List<Object[]> objects = posOrderDao.findBranchItemSummary(systemBookCode, branchNums, dateFrom, dateTo, itemNums, false);
		List<NameAndTwoValueDTO> list = new ArrayList<NameAndTwoValueDTO>();
		if(objects.size() == 0){
			return list;
		}
		Integer branchNum = null;
		Object[] object = null;
		List<Branch> branchs = branchService.findInCache(systemBookCode);
		for(int i = 0;i < objects.size();i++){
			object = objects.get(i);
			
			NameAndTwoValueDTO dto = new NameAndTwoValueDTO();
			branchNum = (Integer) object[0];
			Branch branch = AppUtil.getBranch(branchs, branchNum);
			dto.setName(branch.getBranchCode() + "|" + branch.getBranchName());
			dto.setValue(object[2] == null?BigDecimal.ZERO:(BigDecimal)object[2]);
			dto.setValue2(object[3] == null?BigDecimal.ZERO:(BigDecimal)object[3]);
			list.add(dto);
		}
		return list;
	}

	@Override
	public List<SalesDiscountDTO> findSalesDiscount(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo, Integer rankFrom, Integer rankTo, String sortType) {
		List<SalesDiscount> salesDiscounts = mobileAppDao.findItemDiscount(systemBookCode, branchNums, dateFrom, dateTo, rankFrom, rankTo, sortType);
		List<SalesDiscountDTO> list = new ArrayList<SalesDiscountDTO>();
		for(int i = 0;i < salesDiscounts.size();i++){
			SalesDiscount salesDiscount = salesDiscounts.get(i);
			SalesDiscountDTO dto = new SalesDiscountDTO();
			BeanUtils.copyProperties(salesDiscount, dto);
			list.add(dto);
		}
		return list;
	}

	@Override
	public List<NameAndValueDTO> findBranchCardCountV2(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		List<Branch> branchs = branchService.findInCache(systemBookCode);
		List<Object[]> objects = cardUserService.findCardCount(systemBookCode, branchNums, dateFrom, dateTo, null);
		List<NameAndValueDTO> list = new ArrayList<NameAndValueDTO>();
		
		Object[] object = null;
		Integer num = null;
		Integer count = null;
		Branch branch = null;
	
		for(int i = 0;i < objects.size();i++){
			object = objects.get(i);
			num = (Integer) object[0];
			count = object[1] == null?0:(Integer) object[1] ;
							
			branch = AppUtil.getBranch(branchs, num);
			NameAndValueDTO dto = new NameAndValueDTO();
			dto.setValue(BigDecimal.valueOf(count));//发卡量
			dto.setName(branch.getBranchName());
			list.add(dto);
	
		}
		return list;
	}

	@Override
	@Cacheable(value = "serviceCache")
	public MobileBusinessDTO findMobileBusiness(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo) {
		MobileBusinessDTO mobileBusinessDTO = new MobileBusinessDTO();
		
		MobileBusiness mobileBusiness = mobileAppDao
				.findMobileAppBusiness(systemBookCode, branchNums, dateFrom, dateTo);
		mobileBusinessDTO.setBusinessMoney(mobileBusiness.getBusinessMoney());
		mobileBusinessDTO.setReceiptCount(mobileBusiness.getReceiptCount().intValue());
		mobileBusinessDTO.setDiscountMoney(mobileBusiness.getDiscountMoney());
		mobileBusinessDTO.setReceiptAvgMoney(mobileBusiness.getReceiptAvgMoney());
		
		mobileBusinessDTO.setCashTotal(mobileBusinessDTO.getCashTotal().add(
				posOrderDao.getPosCash(systemBookCode, branchNums, dateFrom, dateTo)));
		mobileBusinessDTO.setCashTotal(mobileBusinessDTO.getCashTotal().add(
				cardDepositDao.getCashMoney(systemBookCode, branchNums, dateFrom, dateTo, AppConstants.PAYMENT_CASH)));
		mobileBusinessDTO.setCashTotal(mobileBusinessDTO.getCashTotal().add(
				replaceCardDao.getCashMoney(systemBookCode, branchNums, dateFrom, dateTo)));
		mobileBusinessDTO.setCashTotal(mobileBusinessDTO.getCashTotal().add(
				relatCardDao.getCashMoney(systemBookCode, branchNums, dateFrom, dateTo)));
		mobileBusinessDTO.setCashTotal(mobileBusinessDTO.getCashTotal().add(
				otherInoutDao.getCashMoney(systemBookCode, branchNums, dateFrom, dateTo)));
		mobileBusinessDTO.setCardDeposit(cardDepositDao.getCashMoney(systemBookCode, branchNums, dateFrom, dateTo, null));
		mobileBusinessDTO.setCardAddedCount(cardUserDao.findTotalCardCount(systemBookCode, branchNums, dateFrom, dateTo, null));
		return mobileBusinessDTO;
	}
	
	@Override
	@Cacheable(value = "serviceCache")
	public List<MobileBusinessDetailDTO> findPaymentSummary(String systemBookCode, List<Integer> branchNums,
                                                            Date dateFrom, Date dateTo){
		List<Object[]> objects = mobileAppDao.findPaymentSummary(systemBookCode, branchNums, dateFrom, dateTo);
		List<MobileBusinessDetailDTO> list = new ArrayList<MobileBusinessDetailDTO>();
		boolean hasCouponType = false;
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			MobileBusinessDetailDTO detail = new MobileBusinessDetailDTO();
			String type = (String) object[0];
			BigDecimal money = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			if (type.equals(AppConstants.PAYMENT_COUPON)) {
				money = money.add(mobileAppDao.getCouponMoney(systemBookCode, branchNums, dateFrom, dateTo));
				hasCouponType = true;
			}
			detail.setDetailValue(money);
			detail.setDetailType(type);
			list.add(detail);
		}
		if (!hasCouponType) {

			BigDecimal couponMoney = mobileAppDao.getCouponMoney(systemBookCode, branchNums, dateFrom, dateTo);
			MobileBusinessDetailDTO detail = new MobileBusinessDetailDTO();
			detail.setDetailValue(couponMoney);
			detail.setDetailType(AppConstants.PAYMENT_COUPON);
			list.add(detail);
		}

		return list;
	}
	
	@Override
	public List<MobileBusinessDetailDTO> findDepositSummary(String systemBookCode, List<Integer> branchNums,
			Date dateFrom, Date dateTo){
		List<MobileBusinessDetailDTO> mobileBusinessDetails = new ArrayList<MobileBusinessDetailDTO>();
		List<Object[]> objects = cardDepositDao.findMoneyByType(systemBookCode, branchNums, dateFrom, dateTo);
		MobileBusinessDetailDTO sum = new MobileBusinessDetailDTO();
		sum.setDetailType("卡存款累计总额");
		sum.setDetailValue(BigDecimal.ZERO);
		
		mobileBusinessDetails.add(sum);
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			MobileBusinessDetailDTO mobileBusinessDetail = new MobileBusinessDetailDTO();
			mobileBusinessDetail.setDetailType((String) object[0]);
			mobileBusinessDetail.setDetailValue((BigDecimal) object[1]);
			mobileBusinessDetails.add(mobileBusinessDetail);

			sum.setDetailValue(sum.getDetailValue().add(mobileBusinessDetail.getDetailValue()));
		}
		return mobileBusinessDetails;
	}

	@Override
	public List<NameAndValueDTO> findDiscountSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo) {
		List<BranchDTO> branchs = CopyUtil.toList(branchService.findInCache(systemBookCode),BranchDTO.class);
		List<NameAndValueDTO> list = new ArrayList<NameAndValueDTO>();

		NameAndValueDTO nameAndValueDTO = null;
		nameAndValueDTO = new NameAndValueDTO();
		nameAndValueDTO.setValue(BigDecimal.ZERO);
		nameAndValueDTO.setName("客户折扣");
		nameAndValueDTO.createDetails(branchNums, branchs);
		list.add(nameAndValueDTO);
		
		nameAndValueDTO = new NameAndValueDTO();
		nameAndValueDTO.setValue(BigDecimal.ZERO);
		nameAndValueDTO.setName("会员折扣");
		nameAndValueDTO.createDetails(branchNums, branchs);
		list.add(nameAndValueDTO);
		
		nameAndValueDTO = new NameAndValueDTO();
		nameAndValueDTO.setValue(BigDecimal.ZERO);
		nameAndValueDTO.setName("其他折扣");
		nameAndValueDTO.createDetails(branchNums, branchs);
		list.add(nameAndValueDTO);
		
		nameAndValueDTO = new NameAndValueDTO();
		nameAndValueDTO.setValue(BigDecimal.ZERO);
		nameAndValueDTO.setName("经理折扣");
		nameAndValueDTO.createDetails(branchNums, branchs);
		list.add(nameAndValueDTO);
		
		nameAndValueDTO = new NameAndValueDTO();
		nameAndValueDTO.setValue(BigDecimal.ZERO);
		nameAndValueDTO.setName("四舍五入");
		nameAndValueDTO.createDetails(branchNums, branchs);
		list.add(nameAndValueDTO);
		
		nameAndValueDTO = new NameAndValueDTO();
		nameAndValueDTO.setValue(BigDecimal.ZERO);
		nameAndValueDTO.setName("促销折扣");
		nameAndValueDTO.createDetails(branchNums, branchs);
		list.add(nameAndValueDTO);
		
		List<Object[]> objects = posOrderDao.findBusiDiscountAnalysisBranchs(systemBookCode, dateFrom, dateTo, branchNums);
		for(int i = 0;i < objects.size();i++){
			Object[] object = objects.get(i);
			
			Integer branchNum = (Integer)object[0];
						
			nameAndValueDTO = NameAndValueDTO.get(list, "客户折扣");
			NameAndValueDTO.NameAndValueDetailDTO nameAndValueDetailDTO = nameAndValueDTO.getDetail(branchNum);
			nameAndValueDetailDTO.setDetailValue((BigDecimal) object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1]);
			nameAndValueDTO.setValue(nameAndValueDTO.getValue().add(nameAndValueDetailDTO.getDetailValue()));
			
			nameAndValueDTO = NameAndValueDTO.get(list, "会员折扣");
			nameAndValueDetailDTO = nameAndValueDTO.getDetail(branchNum);
			nameAndValueDetailDTO.setDetailValue((BigDecimal) object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2]);
			nameAndValueDTO.setValue(nameAndValueDTO.getValue().add(nameAndValueDetailDTO.getDetailValue()));
						
			nameAndValueDTO = NameAndValueDTO.get(list, "经理折扣");
			nameAndValueDetailDTO = nameAndValueDTO.getDetail(branchNum);
			nameAndValueDetailDTO.setDetailValue((BigDecimal) object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5]);
			nameAndValueDTO.setValue(nameAndValueDTO.getValue().add(nameAndValueDetailDTO.getDetailValue()));
			
			nameAndValueDTO = NameAndValueDTO.get(list, "四舍五入");
			nameAndValueDetailDTO = nameAndValueDTO.getDetail(branchNum);
			nameAndValueDetailDTO.setDetailValue((BigDecimal) object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6]);
			nameAndValueDTO.setValue(nameAndValueDTO.getValue().add(nameAndValueDetailDTO.getDetailValue()));
			
			nameAndValueDTO = NameAndValueDTO.get(list, "促销折扣");
			nameAndValueDetailDTO = nameAndValueDTO.getDetail(branchNum);
			BigDecimal policyMoney = (BigDecimal) object[7] == null ? BigDecimal.ZERO : (BigDecimal) object[7];
			nameAndValueDetailDTO.setDetailValue(policyMoney);
			nameAndValueDTO.setValue(nameAndValueDTO.getValue().add(nameAndValueDetailDTO.getDetailValue()));
			
			nameAndValueDTO = NameAndValueDTO.get(list, "其他折扣");
			nameAndValueDetailDTO = nameAndValueDTO.getDetail(branchNum);
			nameAndValueDetailDTO.setDetailValue((BigDecimal) object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3]);
			nameAndValueDetailDTO.setDetailValue(nameAndValueDetailDTO.getDetailValue().subtract(policyMoney));
			nameAndValueDTO.setValue(nameAndValueDTO.getValue().add(nameAndValueDetailDTO.getDetailValue()));
			
		}
		return list;
	}

	@Override
	@Cacheable(value = "serviceCache")
	public List<MobileBusinessDTO> findBusinessMoneyGroupByShop(String systemBookCode, List<Integer> branchNums,
			Date dateFrom, Date dateTo) {
		List<MobileBusinessDTO> list = new ArrayList<MobileBusinessDTO>();
		if(branchNums == null || branchNums.size() == 0){
			return list;
		}
		List<Branch> branchs = branchService.findInCache(systemBookCode);
		for (int i = 0; i < branchNums.size(); i++) {
			Integer branchNum = branchNums.get(i);
			MobileBusinessDTO mobileAppShopBusiness = new MobileBusinessDTO();
			mobileAppShopBusiness.setBranchNum(branchNum);
			Branch branch = AppUtil.getBranch(branchs, branchNum);
			if (branch != null) {
				mobileAppShopBusiness.setBranchName(branch.getBranchName());
			}
			list.add(mobileAppShopBusiness);
		}
		List<Object[]> objects = mobileAppDao.findShopPaymentMoney(systemBookCode, branchNums, dateFrom, dateTo);
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			BigDecimal money = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			for (int j = 0; j < list.size(); j++) {
				MobileBusinessDTO mobileBusiness = list.get(j);
				if (mobileBusiness.getBranchNum().equals(branchNum)) {
					mobileBusiness.setBusinessMoney(money);
					mobileBusiness.setBusinessCardMoney(object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2]);
					break;
				}
			}
		}
		return list;
	}
	
	@Override
	public List<MobileBusinessDTO> findDiscountGroupByShop(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo) {
		List<MobileBusinessDTO> list = new ArrayList<MobileBusinessDTO>();
		List<Branch> branchs = branchService.findInCache(systemBookCode);
		for (int i = 0; i < branchNums.size(); i++) {
			Integer branchNum = branchNums.get(i);
			MobileBusinessDTO mobileAppShopBusiness = new MobileBusinessDTO();
			mobileAppShopBusiness.setBranchNum(branchNum);
			for (int j = 0; j < branchs.size(); j++) {
				Branch branch = branchs.get(j);
				if (branch.getId().getBranchNum().equals(branchNum)) {
					mobileAppShopBusiness.setBranchName(branch.getBranchName());
					break;
				}
			}
			list.add(mobileAppShopBusiness);
		}
		List<Object[]> objects = mobileAppDao.findShopDiscountMoney(systemBookCode, branchNums, dateFrom, dateTo);
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			BigDecimal money = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			for (int j = 0; j < list.size(); j++) {
				MobileBusinessDTO mobileBusiness = list.get(j);
				if (mobileBusiness.getBranchNum().equals(branchNum)) {
					mobileBusiness.setDiscountMoney(money);
					break;
				}
			}
		}
		return list;
	}
	
	@Override
	@Cacheable(value = "serviceCache")
	public List<MobileBusinessDTO> findBusinessReceiptGroupByShop(String systemBookCode, List<Integer> branchNums,
			Date dateFrom, Date dateTo) {
		List<MobileBusinessDTO> list = new ArrayList<MobileBusinessDTO>();
		List<Branch> branchs = branchService.findInCache(systemBookCode);
		for (int i = 0; i < branchNums.size(); i++) {
			Integer branchNum = branchNums.get(i);
			MobileBusinessDTO mobileBusiness = new MobileBusinessDTO();
			mobileBusiness.setBranchNum(branchNum);
			Branch branch = AppUtil.getBranch(branchs, branchNum);
			if (branch != null) {
				mobileBusiness.setBranchName(branch.getBranchName());
			}
			list.add(mobileBusiness);
		}
		List<Object[]> objects = mobileAppDao.findShopReceipt(systemBookCode, branchNums, dateFrom, dateTo);
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			BigDecimal receiptMoney = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			Integer receiptCount = object[2] == null ? 0 : (Integer) object[2];
			for (int j = 0; j < list.size(); j++) {
				MobileBusinessDTO mobileBusiness = list.get(j);
				if (mobileBusiness.getBranchNum().equals(branchNum)) {
					mobileBusiness.setBusinessMoney(receiptMoney);
					mobileBusiness.setReceiptCount(receiptCount);
					if (receiptCount == 0) {
						mobileBusiness.setReceiptAvgMoney(BigDecimal.ZERO);
					} else {
						mobileBusiness.setReceiptAvgMoney(receiptMoney
								.divide(BigDecimal.valueOf(receiptCount), 2, BigDecimal.ROUND_HALF_UP));
					}
					mobileBusiness.setBusinessCardMoney(object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3]);
					mobileBusiness.setReceiptCardCount(object[4] == null ? 0 : (Integer) object[4]);
					mobileBusiness.setReceiptUnCardCount(receiptCount - mobileBusiness.getReceiptCardCount());
					
					if (mobileBusiness.getReceiptCardCount() == 0) {
						mobileBusiness.setReceiptCardAvgMoney(BigDecimal.ZERO);
					} else {
						mobileBusiness.setReceiptCardAvgMoney(mobileBusiness.getBusinessCardMoney()
								.divide(BigDecimal.valueOf(mobileBusiness.getReceiptCardCount()), 2, BigDecimal.ROUND_HALF_UP));
					}
					
					if (mobileBusiness.getReceiptUnCardCount() == 0) {
						mobileBusiness.setReceiptUnCardAvgMoney(BigDecimal.ZERO);
					} else {
						mobileBusiness.setReceiptUnCardAvgMoney((receiptMoney.subtract(mobileBusiness.getBusinessCardMoney()))
								.divide(BigDecimal.valueOf(mobileBusiness.getReceiptUnCardCount()), 2, BigDecimal.ROUND_HALF_UP));
					}
					break;
				}
			}
		}
		return list;
	}
	
	@Override
	public List<MobileBusinessDetailDTO> findCashSummaryGroupByShop(String systemBookCode, List<Integer> branchNums,
			Date dateFrom, Date dateTo, String cashType) {
		List<Branch> branchs = branchService.findInCache(systemBookCode);
		Map<Integer, MobileBusinessDetailDTO> map = new LinkedHashMap<Integer, MobileBusinessDetailDTO>();
		for (int i = 0; i < branchs.size(); i++) {
			Branch branch = branchs.get(i);
			if (!branchNums.contains(branch.getId().getBranchNum())) {
				continue;
			}
			MobileBusinessDetailDTO mobileBusinessDetail = new MobileBusinessDetailDTO();
			mobileBusinessDetail.setBranchName(branch.getBranchName());
			mobileBusinessDetail.setBranchNum(branch.getId().getBranchNum());
			if (cashType.contains(AppConstants.CASH_TYPE_POS)) {
				mobileBusinessDetail.setDetailType(AppConstants.CASH_TYPE_POS);

			} else if (cashType.contains(AppConstants.CASH_TYPE_CARD_DEPOSIT)) {
				mobileBusinessDetail.setDetailType(AppConstants.CASH_TYPE_CARD_DEPOSIT);

			} else if (cashType.contains(AppConstants.CASH_TYPE_REPLACE_CARD)) {
				mobileBusinessDetail.setDetailType(AppConstants.CASH_TYPE_REPLACE_CARD);

			} else if (cashType.contains(AppConstants.CASH_TYPE_RELAT_CARD)) {
				mobileBusinessDetail.setDetailType(AppConstants.CASH_TYPE_RELAT_CARD);

			} else if (cashType.contains(AppConstants.CASH_TYPE_OTHER_INOUT)) {
				mobileBusinessDetail.setDetailType(AppConstants.CASH_TYPE_OTHER_INOUT);

			} else {
				mobileBusinessDetail.setDetailType(AppConstants.CASH_TYPE_TOTAL);

			}
			map.put(mobileBusinessDetail.getBranchNum(), mobileBusinessDetail);
		}
		List<Object[]> objects = new ArrayList<Object[]>();
		if (cashType.contains(AppConstants.CASH_TYPE_POS)) {
			objects = posOrderDao.findPosCashGroupByBranch(systemBookCode, branchNums, dateFrom, dateTo);
		} else if (cashType.contains(AppConstants.CASH_TYPE_CARD_DEPOSIT)) {
			objects = cardDepositDao.findCashGroupByBranch(systemBookCode, branchNums, dateFrom, dateTo, AppConstants.PAYMENT_CASH);
		} else if (cashType.contains(AppConstants.CASH_TYPE_REPLACE_CARD)) {
			objects = replaceCardDao.findCashGroupByBranch(systemBookCode, branchNums, dateFrom, dateTo);
		} else if (cashType.contains(AppConstants.CASH_TYPE_RELAT_CARD)) {
			objects = relatCardDao.findCashGroupByBranch(systemBookCode, branchNums, dateFrom, dateTo);
		} else if (cashType.contains(AppConstants.CASH_TYPE_OTHER_INOUT)) {
			objects = otherInoutDao.findCashGroupByBranch(systemBookCode, branchNums, dateFrom, dateTo);
		} else {
			objects.addAll(posOrderDao.findPosCashGroupByBranch(systemBookCode, branchNums, dateFrom, dateTo));
			objects.addAll(cardDepositDao.findCashGroupByBranch(systemBookCode, branchNums, dateFrom, dateTo, AppConstants.PAYMENT_CASH));
			objects.addAll(replaceCardDao.findCashGroupByBranch(systemBookCode, branchNums, dateFrom, dateTo));
			objects.addAll(relatCardDao.findCashGroupByBranch(systemBookCode, branchNums, dateFrom, dateTo));
			objects.addAll(otherInoutDao.findCashGroupByBranch(systemBookCode, branchNums, dateFrom, dateTo));

		}
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			BigDecimal cashMoney = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			MobileBusinessDetailDTO mobileBusinessDetail = map.get(branchNum);
			if (mobileBusinessDetail != null) {
				mobileBusinessDetail.setDetailValue(mobileBusinessDetail.getDetailValue().add(cashMoney));
			}
		}
		return new ArrayList<MobileBusinessDetailDTO>(map.values());
	}
	
	@Override
	public List<MobileBusinessDetailDTO> findDepositSummaryGroupByShop(String systemBookCode, List<Integer> branchNums,
			Date dateFrom, Date dateTo, String cashType) {
		List<Branch> branchs = branchService.findInCache(systemBookCode);
		List<MobileBusinessDetailDTO> mobileBusinessDetails = new ArrayList<MobileBusinessDetailDTO>();
		List<Object[]> objects = null;
		if (cashType.equals("卡存款累计总额")) {
			objects = cardDepositDao.findCashGroupByBranch(systemBookCode, branchNums, dateFrom, dateTo, null);

		} else {
			objects = cardDepositDao.findCashGroupByBranch(systemBookCode, branchNums, dateFrom, dateTo, cashType);
		}
		for (int i = 0; i < branchNums.size(); i++) {
			Integer branchNum = branchNums.get(i);
			MobileBusinessDetailDTO mobileBusinessDetail = new MobileBusinessDetailDTO();
			mobileBusinessDetail.setBranchNum(branchNum);
			mobileBusinessDetail.setBranchName("");
			mobileBusinessDetail.setDetailType(cashType);
			Branch branch = AppUtil.getBranch(branchs, branchNum);
			if (branch != null) {
				mobileBusinessDetail.setBranchName(branch.getBranchName());
			}
			for (int j = 0; j < objects.size(); j++) {
				Object[] object = objects.get(j);
				if (branchNum.equals((Integer) object[0])) {
					mobileBusinessDetail.setDetailValue((BigDecimal) object[1]);
					break;
				}

			}
			mobileBusinessDetails.add(mobileBusinessDetail);
		}
		return mobileBusinessDetails;
	}
	
	@Override
	public List<MobileBusinessDetailDTO> findPaymentSummaryGroupByShop(String systemBookCode, List<Integer> branchNums,
			Date dateFrom, Date dateTo, String type) {
		List<MobileBusinessDetailDTO> list = new ArrayList<MobileBusinessDetailDTO>();
		List<Branch> branchs = branchService.findInCache(systemBookCode);
		for (int i = 0; i < branchNums.size(); i++) {
			Integer branchNum = branchNums.get(i);
			MobileBusinessDetailDTO detail = new MobileBusinessDetailDTO();
			detail.setDetailType(type);
			detail.setBranchNum(branchNum);
			for (int j = 0; j < branchs.size(); j++) {
				Branch branch = branchs.get(j);
				if (branch.getId().getBranchNum().equals(branchNum)) {
					detail.setBranchName(branch.getBranchName());
					break;
				}
			}
			list.add(detail);
		}
		List<Object[]> objects = mobileAppDao.findShopPayment(systemBookCode, branchNums, dateFrom, dateTo, type);
		if (type.equals(AppConstants.PAYMENT_COUPON)) {
			objects.addAll(mobileAppDao.findShopCouponMoney(systemBookCode, branchNums, dateFrom, dateTo));
		}
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			BigDecimal typeMoney = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			for (int j = 0; j < list.size(); j++) {
				MobileBusinessDetailDTO detail = list.get(j);
				if (detail.getBranchNum().equals(branchNum)) {
					detail.setDetailValue(detail.getDetailValue().add(typeMoney));
					break;
				}
			}
		}
		return list;
	}
	
	@Override
	public List<MobileBusinessDetailDTO> findCashSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo) {
		List<MobileBusinessDetailDTO> list = new ArrayList<MobileBusinessDetailDTO>();

		BigDecimal cashTotal = BigDecimal.ZERO;

		MobileBusinessDetailDTO detail = new MobileBusinessDetailDTO();
		detail.setDetailType(AppConstants.CASH_TYPE_POS);
		detail.setDetailValue(posOrderDao.getPosCash(systemBookCode, branchNums, dateFrom, dateTo));
		cashTotal = cashTotal.add(detail.getDetailValue());
		list.add(detail);

		detail = new MobileBusinessDetailDTO();
		detail.setDetailType(AppConstants.CASH_TYPE_CARD_DEPOSIT);
		detail.setDetailValue(cardDepositDao.getCashMoney(systemBookCode, branchNums, dateFrom, dateTo,
				AppConstants.PAYMENT_CASH));
		cashTotal = cashTotal.add(detail.getDetailValue());
		list.add(detail);

		detail = new MobileBusinessDetailDTO();
		detail.setDetailType(AppConstants.CASH_TYPE_REPLACE_CARD);
		detail.setDetailValue(replaceCardDao.getCashMoney(systemBookCode, branchNums, dateFrom, dateTo));
		cashTotal = cashTotal.add(detail.getDetailValue());
		list.add(detail);

		detail = new MobileBusinessDetailDTO();
		detail.setDetailType(AppConstants.CASH_TYPE_RELAT_CARD);
		detail.setDetailValue(relatCardDao.getCashMoney(systemBookCode, branchNums, dateFrom, dateTo));
		cashTotal = cashTotal.add(detail.getDetailValue());
		list.add(detail);

		detail = new MobileBusinessDetailDTO();
		detail.setDetailType(AppConstants.CASH_TYPE_OTHER_INOUT);
		detail.setDetailValue(otherInoutDao.getCashMoney(systemBookCode, branchNums, dateFrom, dateTo));
		cashTotal = cashTotal.add(detail.getDetailValue());
		list.add(detail);

		detail = new MobileBusinessDetailDTO();
		detail.setDetailType(AppConstants.CASH_TYPE_TOTAL);
		detail.setDetailValue(cashTotal);
		list.add(0, detail);
		return list;
	}

	@Override
	public List<OtherInfoDTO> findOtherInfoDetails(String systemBookCode, Integer branchNum, Date dateFrom,
			Date dateTo, String infoType) {
		List<OtherInfoDTO> list = new ArrayList<OtherInfoDTO>();
		if(infoType.equals("反结账")){
			List<Integer> branchNums = new ArrayList<Integer>();
			branchNums.add(branchNum);
			
			List<Object[]> objects = posOrderDao.findRepayDetail(systemBookCode, branchNums, dateFrom, dateTo);
			Object[] object = null;
			for(int i = 0;i < objects.size();i++){
				object = objects.get(i);
				
				OtherInfoDTO dto = new OtherInfoDTO();
				dto.setName((String) object[0]);
				dto.setMoney(object[1] == null?BigDecimal.ZERO:(BigDecimal)object[1]);
				dto.setOperator((String) object[2]);
				dto.setBizday((String) object[3]);
				dto.setDate((Date) object[4]);
				list.add(dto);
			}
		}
		else if(infoType.equals("客户折扣")) {
			List<Integer> branchNums = new ArrayList<Integer>();
			branchNums.add(branchNum);
			
			List<Object[]> objects = posOrderDao.findClientDiscountAnalysisAmountAndMoney(systemBookCode, dateFrom, dateTo, branchNums);
			Object[] object = null;
			for(int i = 0;i < objects.size();i++){
				object = objects.get(i);
				
				OtherInfoDTO dto = new OtherInfoDTO();
				dto.setName((String) object[0]);
				dto.setPaymentMoney(AppUtil.getValue(object[1], BigDecimal.class));
				dto.setMoney(object[2] == null?BigDecimal.ZERO:(BigDecimal)object[2]);
				dto.setOperator((String) object[3]);
				dto.setBizday((String) object[4]);
				dto.setDate((Date) object[5]);
				list.add(dto);
			}
		}
		else if(infoType.equals("经理折扣")) {
			List<Integer> branchNums = new ArrayList<Integer>();
			branchNums.add(branchNum);
			
			List<Object[]> objects = posOrderDao.findMgrDiscountAnalysisAmountAndMoney(systemBookCode, dateFrom, dateTo, branchNums);
			Object[] object = null;
			for(int i = 0;i < objects.size();i++){
				object = objects.get(i);
				
				OtherInfoDTO dto = new OtherInfoDTO();
				dto.setName((String) object[0]);
				dto.setPaymentMoney(AppUtil.getValue(object[1], BigDecimal.class));
				dto.setMoney(object[2] == null?BigDecimal.ZERO:(BigDecimal)object[2]);
				dto.setOperator((String) object[3]);
				dto.setBizday((String) object[4]);
				dto.setDate((Date) object[5]);
				list.add(dto);
			}
		}
		else {
			LogQuery logQuery = new LogQuery();
			logQuery.setSystemBookCode(systemBookCode);
			logQuery.setDateFrom(dateFrom);
			logQuery.setDateTo(dateTo);
			logQuery.setOperateType(infoType);
			logQuery.setPaging(false);
			logQuery.setSortField("retail_pos_log_time");
			logQuery.setSortType("DESC");
			List<RetailPosLog> retailPosLogs = retailPosLogDao.findByQuery(systemBookCode, branchNum, logQuery, 0, 0);
			for(int i = 0;i < retailPosLogs.size();i++){
				RetailPosLog retailPosLog = retailPosLogs.get(i);
				
				OtherInfoDTO dto = new OtherInfoDTO();
				dto.setName(retailPosLog.getRetailPosLogItemName());
				dto.setBizday(retailPosLog.getRetailPosLogBizday());
				dto.setMoney(retailPosLog.getRetailPosLogMoney());
				if(dto.getMoney() == null){
					dto.setMoney(BigDecimal.ZERO);
				}
				dto.setOperator(retailPosLog.getRetailPosLogOperator());
				dto.setDate(retailPosLog.getRetailPosLogTime());
				dto.setOrderNo(retailPosLog.getRetailPosLogOrderNo() == null?"":retailPosLog.getRetailPosLogOrderNo());
				list.add(dto);
			}
			
			if(infoType.equals(AppConstants.RETAIL_POS_LOG_ALL_CANCLE)){
				Collections.sort(list, new Comparator<OtherInfoDTO>() {

					@Override
					public int compare(OtherInfoDTO o1, OtherInfoDTO o2) {
						if(o1.getBizday().equals(o2.getBizday())){
							return -o1.getMoney().compareTo(o2.getMoney());
							
						} else {
							return o1.getBizday().compareTo(o2.getBizday());
						}
					}
				});
			}
			if(infoType.equals(AppConstants.RETAIL_POS_LOG_DEL)){
				List<OtherInfoDTO> summaryList = new ArrayList<OtherInfoDTO>();
				OtherInfoDTO dto;
				for(int i = 0;i < list.size();i++){
					dto = list.get(i);
					if(StringUtils.isEmpty(dto.getOrderNo())){
						summaryList.add(dto);
						continue;
					}
					OtherInfoDTO summaryDTO = OtherInfoDTO.getByOrderNo(summaryList, dto.getOrderNo());
					if(summaryDTO == null){
						summaryDTO = new OtherInfoDTO();
						summaryDTO.setName(dto.getName());
						summaryDTO.setBizday(dto.getBizday());
						summaryDTO.setOperator(dto.getOperator());
						summaryDTO.setDate(dto.getDate());
						summaryDTO.setOrderNo(dto.getOrderNo());
						summaryDTO.setMoney(BigDecimal.ZERO);
						summaryList.add(summaryDTO);
					}
					OtherInfoDTO.OtherInfoDetailDTO detailDTO = new OtherInfoDTO.OtherInfoDetailDTO();
					BeanUtils.copyProperties(dto, detailDTO);
					summaryDTO.getDetails().add(detailDTO);
					if(summaryDTO.getDetails().size() == 2){
						summaryDTO.setName(summaryDTO.getName().concat("..."));
					}
					summaryDTO.setMoney(summaryDTO.getMoney().add(dto.getMoney()));		
				}
				list = summaryList;
				
			}
		}
		return list;
	}

	@Override
	public List<ShipOrderDTO> findShipOrders(String systemBookCode, Integer centerBranchNum, Integer branchNum,
			Date dateFrom, Date dateTo) {
		List<Integer> branchNums = new ArrayList<Integer>();
		branchNums.add(branchNum);
		if(centerBranchNum == null){
			centerBranchNum = branchGroupService.readTransferBranch(systemBookCode, branchNum).getId().getBranchNum();
		}
		return shipOrderDao.findShipOrderDTOs(systemBookCode, centerBranchNum, branchNums, dateFrom, dateTo);
	}

	@Override
	public List<CardReportDTO> findCardReportByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		List<CardReportDTO> cardReportDTOs = new ArrayList<CardReportDTO>();
		List<Object[]> cardSendObjects = cardUserDao.findCardCount(systemBookCode, branchNums, dateFrom, dateTo,
				null);
		List<Object[]> cardRevokeObjects = cardUserDao.findRevokeCardCount(systemBookCode, branchNums, dateFrom,
				dateTo, null);

		List<Branch> branchs = branchService.findInCache(systemBookCode);
		for (int i = 0; i < cardSendObjects.size(); i++) {
			Object[] sendObject = cardSendObjects.get(i);
			CardReportDTO cardReportDTO = new CardReportDTO();
			cardReportDTO.setBranchNum((Integer) sendObject[0]);
			cardReportDTO.setBranchName(AppUtil.getBranch(branchs, (Integer) sendObject[0]).getBranchName());
			cardReportDTOs.add(cardReportDTO);
			cardReportDTO.setSendCardCount((Integer) sendObject[1]);
		}
		for (int i = 0; i < cardRevokeObjects.size(); i++) {
			Object[] revokeObject = cardRevokeObjects.get(i);
			CardReportDTO cardReportDTO = CardReportDTO.readByBranch((Integer) revokeObject[0], cardReportDTOs);
			if (cardReportDTO == null) {
				cardReportDTO = new CardReportDTO();
				cardReportDTO.setBranchNum((Integer) revokeObject[0]);
				cardReportDTO.setBranchName(AppUtil.getBranch(branchs, (Integer) revokeObject[0]).getBranchName());
				cardReportDTOs.add(cardReportDTO);
			}
			cardReportDTO.setReturnCardCount(((Long) revokeObject[1]).intValue());
			cardReportDTO.setReturnCardMoney(revokeObject[2] == null?BigDecimal.ZERO:(BigDecimal)revokeObject[2]);
		}
		
		List<Object[]> cardDepositObjects = cardDepositDao.findBranchPaymentTypeSum(systemBookCode, branchNums, dateFrom, dateTo,
				null);
		String paymentType = null; 
		for (int i = 0; i < cardDepositObjects.size(); i++) {
			
			Object[] depositObject = cardDepositObjects.get(i);
			paymentType = (String) depositObject[1];
			CardReportDTO cardReportDTO = CardReportDTO.readByBranch((Integer) depositObject[0], cardReportDTOs);
			if (cardReportDTO == null) {    
				cardReportDTO = new CardReportDTO();
				cardReportDTO.setBranchNum((Integer) depositObject[0]);
				cardReportDTO.setBranchName(AppUtil.getBranch(branchs, (Integer) depositObject[0]).getBranchName());
				cardReportDTOs.add(cardReportDTO);
			}
			
			if(paymentType.equals(AppConstants.PAYMENT_ORI)){
				cardReportDTO.setOriDepositMoney(cardReportDTO.getOriDepositMoney().add((BigDecimal) depositObject[3]));
			} else {

				cardReportDTO.setPaymentMoney(cardReportDTO.getPaymentMoney().add((BigDecimal) depositObject[2]));
				cardReportDTO.setDepositMoney(cardReportDTO.getDepositMoney().add((BigDecimal) depositObject[3]));
				cardReportDTO.setSendMoney(cardReportDTO.getDepositMoney().subtract(cardReportDTO.getPaymentMoney()));
			}
		}
		
		List<Object[]> objects = cardUserLogDao.findBranchCount(systemBookCode, branchNums, dateFrom, dateTo, AppConstants.CARD_USER_LOG_TYPE_CHANGE_STORGE,
				null);
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			CardReportDTO cardReportDTO = CardReportDTO.readByBranch((Integer) object[0], cardReportDTOs);
			if (cardReportDTO == null) {
				cardReportDTO = new CardReportDTO();
				cardReportDTO.setBranchNum((Integer) object[0]);
				cardReportDTO.setBranchName(AppUtil.getBranch(branchs, (Integer) object[0]).getBranchName());
				cardReportDTOs.add(cardReportDTO);
			}
			cardReportDTO.setChangeEleCardCount(object[1] == null?0:(Integer)object[1]);
		}
		return cardReportDTOs;
	}

	@Override
	public List<NameAndTwoValueDTO> findDeptRank(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer rankFrom, Integer rankTo, String sortField) {

		List<NameAndTwoValueDTO> dtos = new ArrayList<NameAndTwoValueDTO>();
		List<MobileSalesRank> list = mobileAppDao.findDeptRank(systemBookCode, branchNums, dateFrom, dateTo);
		if(list.size() == 0){
			return dtos;
		}
		List<MobileSalesRank> returnList = new ArrayList<MobileSalesRank>();
		for (int i = list.size() - 1; i >= 0; i--) {
			MobileSalesRank childRank = list.get(i);
			String code = childRank.getSalesCode();
			MobileSalesRank newRank = readFromList(returnList, code);
			if (newRank == null) {
				newRank = new MobileSalesRank();
				BeanUtils.copyProperties(childRank, newRank);
				newRank.setSalesName(childRank.getSalesName());
				newRank.setSalesCode(childRank.getSalesCode());
				newRank.setSalesId(childRank.getSalesId());
				returnList.add(newRank);
			} else {
				newRank.setSalesCount(newRank.getSalesCount().add(childRank.getSalesCount()));
				newRank.setSelesMoney(newRank.getSelesMoney().add(childRank.getSelesMoney()));
				newRank.setSelesUnitCount(newRank.getSelesUnitCount().add(childRank.getSelesUnitCount()));
			}
		}
		if(StringUtils.isEmpty(sortField)){
			sortField = "money";
		}
		Comparator<MobileSalesRank> comparator = null;
		if(sortField.equals("amount")){
			comparator = new Comparator<MobileSalesRank>() {

				@Override
				public int compare(MobileSalesRank o1, MobileSalesRank o2) {

					if (o1.getSalesCount().compareTo(o2.getSalesCount()) > 0) {
						return -1;
					} else if (o1.getSalesCount().compareTo(o2.getSalesCount()) < 0) {
						return 1;
					} else {
						return -o1.getSelesMoney().compareTo(o2.getSelesMoney());
					}
				}

			};
		} else {
			comparator = new Comparator<MobileSalesRank>() {

				@Override
				public int compare(MobileSalesRank o1, MobileSalesRank o2) {

					if (o1.getSelesMoney().compareTo(o2.getSelesMoney()) > 0) {
						return -1;
					} else if (o1.getSelesMoney().compareTo(o2.getSelesMoney()) < 0) {
						return 1;
					} else {
						return -o1.getSalesCount().compareTo(o2.getSalesCount());
					}
				}
			};
		}

		Collections.sort(returnList, comparator);
		MobileSalesRank rank = null;
		if(rankFrom != null && rankTo != null){

			int count = returnList.size();
			for (int i = rankFrom; i < rankTo; i++) {
				if (i >= count) {
					break;
				}
				rank = returnList.get(i);
				NameAndTwoValueDTO dto = new NameAndTwoValueDTO();

				dto.setName(rank.getSalesName());
				dto.setValue(rank.getSalesCount());
				dto.setValue2(rank.getSelesMoney());
				dtos.add(dto);
			}
			NameAndTwoValueDTO otherDto = new NameAndTwoValueDTO();
			otherDto.setName("其他部门");
			otherDto.setValue(BigDecimal.ZERO);
			otherDto.setValue2(BigDecimal.ZERO);
			boolean hasOther = false;
			for (int i = 0;i < returnList.size();i++) {

				if(i < rankFrom || i >= rankTo){
					hasOther = true;
					rank = returnList.get(i);
					otherDto.setValue(otherDto.getValue().add(rank.getSalesCount()));
					otherDto.setValue2(otherDto.getValue2().add(rank.getSelesMoney()));
				}

			}
			if(hasOther){
				dtos.add(otherDto);
				
			}
		} else {
			for (int i = 0;i < returnList.size(); i++) {

				rank = returnList.get(i);

				NameAndTwoValueDTO dto = new NameAndTwoValueDTO();
				dto.setName(rank.getSalesName());
				dto.setValue(rank.getSalesCount());
				dto.setValue2(rank.getSelesMoney());
				dtos.add(dto);
			}

		}
		return dtos;
	}

}
