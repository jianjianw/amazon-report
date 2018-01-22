package com.nhsoft.module.report.dao.impl;


import com.nhsoft.module.report.dao.AlipayLogDao;
import com.nhsoft.module.report.dto.AlipayDetailDTO;
import com.nhsoft.module.report.model.AlipayLog;
import com.nhsoft.module.report.query.LogQuery;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.report.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class AlipayLogDaoImpl extends ShardingDaoImpl implements AlipayLogDao {
	
	
	private Criteria createByLogQuery(String systemBookCode, Integer branchNum, LogQuery logQuery){
		Criteria criteria = currentSession().createCriteria(AlipayLog.class, "a")
				.add(Restrictions.eq("a.systemBookCode", systemBookCode));
		if(StringUtils.isNotEmpty(logQuery.getLogItem())){
			criteria.add(Restrictions.like("a.alipayLogOrderNo", logQuery.getLogItem(), MatchMode.ANYWHERE));
			return criteria;
		}
		if(branchNum != null){
			criteria.add(Restrictions.eq("a.branchNum", branchNum));
		}
		if(StringUtils.isNotEmpty(logQuery.getOperator())){
			criteria.add(Restrictions.eq("a.alipayLogOperator", logQuery.getOperator()));
		}
		if(StringUtils.isNotEmpty(logQuery.getOperateType())){
			criteria.add(Restrictions.eq("a.alipayLogType", logQuery.getOperateType()));
		}
		criteria.add(Restrictions.between("a.alipayLogStart", DateUtil.getMinOfDate(logQuery.getDateFrom()), DateUtil.getMaxOfDate(logQuery.getDateTo())));
		criteria.setLockMode(LockMode.NONE);
		return criteria;
	}
	
	@Override
	public int countByLogQuery(String systemBookCode, Integer branchNum, LogQuery logQuery) {
		Criteria criteria = createByLogQuery(systemBookCode, branchNum, logQuery);
		criteria.setProjection(Projections.rowCount());
		return ((Long)criteria.uniqueResult()).intValue();
	}
	
	@Override
	public List<AlipayLog> findByLogQuery(String systemBookCode, Integer branchNum, LogQuery logQuery, int offset,
	                                      int limit) {
		Criteria criteria = createByLogQuery(systemBookCode, branchNum, logQuery);
		if(logQuery.isPaging()){
			criteria.setFirstResult(offset);
			criteria.setMaxResults(limit);
		}
		if(logQuery.getSortField() != null){
			if(logQuery.getSortType().equals("ASC")){
				criteria.addOrder(Order.asc(logQuery.getSortField()));
			} else {
				criteria.addOrder(Order.desc(logQuery.getSortField()));
			}
		} else {
			criteria.addOrder(Order.asc("a.alipayLogStart"));
		}
		return criteria.list();
	}
	
	@Override
	public List<Object[]> findBranchSummaryPayFail(String systemBookCode, List<Integer> branchNums, Date dateFrom,
													Date dateTo, boolean isDeposit, String alipayLogTypes){
		Criteria criteria = currentSession().createCriteria(AlipayLog.class, "a")
				.add(Restrictions.eq("a.systemBookCode", systemBookCode))
				.add(Restrictions.eq("a.alipayLogTradeState", false));
		if(branchNums != null && branchNums.size() > 0){
			
			criteria.add(Restrictions.in("a.branchNum", branchNums));
		}
		
		criteria.add(Restrictions.between("a.alipayLogStart", DateUtil.getMinOfDate(dateFrom), DateUtil.getMaxOfDate(dateTo)));
		
		if(isDeposit){
			criteria.add(Restrictions.like("a.alipayLogOrderNo", "DEP", MatchMode.START));
		} else {
			//criteria.add(Restrictions.not(Restrictions.like("a.alipayLogOrderNo", "DEP", MatchMode.START)));  sharding jdbc  不支持这种not like
			criteria.add(Restrictions.sqlRestriction("substring(alipay_log_order_no, 0, 4) != 'DEP'"));
		}
		if(StringUtils.isNotEmpty(alipayLogTypes)){
			criteria.add(Restrictions.in("a.alipayLogType", alipayLogTypes.split(",")));
			
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("a.branchNum"))
				.add(Projections.sum("a.alipayLogMoney"))
				.add(Projections.count("a.alipayLogId"))
		);
		return criteria.list();
	}

	@Override
	public List<Object[]> findBranchSummaryRePay(String systemBookCode, List<Integer> branchNums, Date dateFrom,
												  Date dateTo, String alipayLogTypes) {
		
		Criteria criteria = currentSession().createCriteria(AlipayLog.class, "a")
				.add(Restrictions.eq("a.systemBookCode", systemBookCode));
				
		if(branchNums != null && branchNums.size() > 0){
			
			criteria.add(Restrictions.in("a.branchNum", branchNums));
		}
		criteria.add(Restrictions.between("a.alipayLogStart", DateUtil.getMinOfDate(dateFrom), DateUtil.getMaxOfDate(dateTo)));
		
		if(StringUtils.isNotEmpty(alipayLogTypes)){
			criteria.add(Restrictions.in("a.alipayLogType", alipayLogTypes.split(",")));
			
		}
		criteria.add(Restrictions.isNotNull("a.alipayLogBuyerId"));
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("a.branchNum"))
				.add(Projections.sum("a.alipayLogMoney"))
				.add(Projections.count("a.alipayLogId"))
		);
		return criteria.list();
	}

	@Override
	public List<AlipayDetailDTO> findAlipayDetailDTOs(String systemBookCode, List<Integer> branchNums, Date dateFrom,
													   Date dateTo, String orderNoPre, String alipayLogTypes){
		Criteria criteria = currentSession().createCriteria(AlipayLog.class, "a")
				.add(Restrictions.eq("a.systemBookCode", systemBookCode));
				
		
		if (branchNums != null && branchNums.size() > 0) {
			
			criteria.add(Restrictions.in("a.branchNum", branchNums));
		}
		if(StringUtils.isNotEmpty(orderNoPre)){
			
			if(orderNoPre.equals("member")){
				
				//微会员存款时关联单据号为空
				criteria.add(Restrictions.eq("a.alipayLogOrderNo", ""));
				
			} else {
				criteria.add(Restrictions.like("a.alipayLogOrderNo", orderNoPre, MatchMode.START));
				
			}
		
		}
		criteria.add(Restrictions.between("a.alipayLogStart", DateUtil.getMinOfDate(dateFrom), DateUtil.getMaxOfDate(dateTo)));
		criteria.add(Restrictions.eq("a.alipayLogTradeState", true))
				.add(Restrictions.eq("a.alipayLogTradeValid", true))
				.add(Restrictions.in("a.alipayLogType", alipayLogTypes.split(",")));
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("a.alipayLogTradeNo"))
				.add(Projections.property("a.alipayLogOrderNo"))
				.add(Projections.property("a.branchNum"))
				.add(Projections.property("a.alipayLogStart"))
				.add(Projections.property("a.alipayLogBuyerId"))
				.add(Projections.property("a.alipayLogMoney"))
				.add(Projections.property("a.alipayLogReceiptMoney"))
				.add(Projections.property("a.alipayLogBuyerMoney"))
		);
		criteria.setLockMode(LockMode.NONE);
		List<Object[]> objects = criteria.list();
		int size = objects.size();
		List<AlipayDetailDTO> list = new ArrayList<AlipayDetailDTO>(size);
		BigDecimal buyerMoney;
		for (int i = 0; i < size; i++) {
			Object[] object = objects.get(i);
			
			AlipayDetailDTO alipayDetailDTO = new AlipayDetailDTO();
			alipayDetailDTO.setOrderFid((String) object[0]);
			alipayDetailDTO.setOrderNo((String) object[1]);
			alipayDetailDTO.setBranchNum((Integer) object[2]);
			alipayDetailDTO.setOrderTime((Date) object[3]);
			alipayDetailDTO.setCustomerId((String) object[4]);
			alipayDetailDTO.setConsumeSuccessMoney(object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5]);
			alipayDetailDTO.setConsumeSuccessActualMoney(object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6]);
			
			//微会员存款没有OrderNo
			if(alipayDetailDTO.getOrderNo().startsWith("DEP")){
				
				alipayDetailDTO.setType("POS存款");
				
			} else if(StringUtils.isEmpty(alipayDetailDTO.getOrderNo())){
				
				alipayDetailDTO.setType("微会员存款");
				
			} else if(alipayDetailDTO.getOrderNo().startsWith(AppConstants.S_Prefix_WD)) {
				alipayDetailDTO.setType("微店消费");
				
			} else {
				alipayDetailDTO.setType("POS消费");
				
			}
			buyerMoney = object[7] == null ? BigDecimal.ZERO : (BigDecimal) object[7];
			alipayDetailDTO.setAlipayDiscountMoney(alipayDetailDTO.getConsumeSuccessActualMoney().subtract(buyerMoney));
			alipayDetailDTO.setBranchDiscountMoney(alipayDetailDTO.getConsumeSuccessMoney().subtract(alipayDetailDTO.getConsumeSuccessActualMoney()));
			list.add(alipayDetailDTO);
		}
		return list;
	}

	@Override
	public List<AlipayDetailDTO> findCancelAlipayDetailDTOs(String systemBookCode, List<Integer> branchNums,
															 Date dateFrom, Date dateTo, String orderNoPre, String alipayLogTypes){
		Criteria criteria = currentSession().createCriteria(AlipayLog.class, "a")
				.add(Restrictions.eq("a.systemBookCode", systemBookCode));
				
		
		if (branchNums != null && branchNums.size() > 0) {
			
			criteria.add(Restrictions.in("a.branchNum", branchNums));
		}
		criteria.add(Restrictions.between("a.alipayLogStart", DateUtil.getMinOfDate(dateFrom), DateUtil.getMaxOfDate(dateTo)));
		criteria.add(Restrictions.eq("a.alipayLogTradeState", true))
				.add(Restrictions.eq("a.alipayLogTradeValid", false))
				.add(Restrictions.in("a.alipayLogType", alipayLogTypes.split(",")));
		if(StringUtils.isNotEmpty(orderNoPre)){
			
			if(orderNoPre.equals("POS")){
				criteria.add(Restrictions.not(Restrictions.like("a.alipayLogOrderNo", "DEP", MatchMode.START)));
				criteria.add(Restrictions.not(Restrictions.like("a.alipayLogOrderNo", AppConstants.S_Prefix_WD, MatchMode.START)));
			} else {
				
					
				if(orderNoPre.equals("member")){
					
					//微会员存款时关联单据号为空
					criteria.add(Restrictions.eq("a.alipayLogOrderNo", ""));
					
				} else {
					criteria.add(Restrictions.like("a.alipayLogOrderNo", orderNoPre, MatchMode.START));
					
				}
					
				
			}
			
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("a.alipayLogTradeNo"))
				.add(Projections.property("a.alipayLogOrderNo"))
				.add(Projections.property("a.branchNum"))
				.add(Projections.property("a.alipayLogStart"))
				.add(Projections.property("a.alipayLogBuyerId"))
				.add(Projections.property("a.alipayLogMoney"))
				.add(Projections.property("a.alipayLogReceiptMoney"))
				.add(Projections.property("a.alipayLogBuyerMoney"))
		);
		criteria.setLockMode(LockMode.NONE);
		List<Object[]> objects = criteria.list();
		int size = objects.size();
		List<AlipayDetailDTO> list = new ArrayList<AlipayDetailDTO>(size);
		BigDecimal buyerMoney = null;
		for (int i = 0; i < size; i++) {
			Object[] object = objects.get(i);
			
			AlipayDetailDTO alipayDetailDTO = new AlipayDetailDTO();
			alipayDetailDTO.setOrderFid((String) object[0]);
			alipayDetailDTO.setOrderNo((String) object[1]);
			alipayDetailDTO.setBranchNum((Integer) object[2]);
			alipayDetailDTO.setOrderTime((Date) object[3]);
			alipayDetailDTO.setCustomerId((String) object[4]);
			alipayDetailDTO.setConsumeSuccessMoney(object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5]);
			alipayDetailDTO.setConsumeSuccessActualMoney(object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6]);
			
			//微会员存款没有orderNo
			if(alipayDetailDTO.getOrderNo().startsWith("DEP") || StringUtils.isEmpty(alipayDetailDTO.getOrderNo())){
				
				alipayDetailDTO.setType("卡存款");
				
			} else if(alipayDetailDTO.getOrderNo().startsWith(AppConstants.S_Prefix_WD)) {
				alipayDetailDTO.setType("微店消费");
				
			} else {
				alipayDetailDTO.setType("POS消费");
				
			}
			buyerMoney = object[7] == null ? BigDecimal.ZERO : (BigDecimal) object[7];
			alipayDetailDTO.setAlipayDiscountMoney(alipayDetailDTO.getConsumeSuccessActualMoney().subtract(buyerMoney));
			alipayDetailDTO.setBranchDiscountMoney(alipayDetailDTO.getConsumeSuccessMoney().subtract(alipayDetailDTO.getConsumeSuccessActualMoney()));
			alipayDetailDTO.setValid(false);
			list.add(alipayDetailDTO);
		}
		return list;
	}

	@Override
	public List<Object[]> findDepositSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String alipayLogTypes){
		Criteria criteria = currentSession().createCriteria(AlipayLog.class, "a")
				.add(Restrictions.eq("a.systemBookCode", systemBookCode));
				
		
		if (branchNums != null && branchNums.size() > 0) {
			
			criteria.add(Restrictions.in("a.branchNum", branchNums));
		}
		criteria.add(Restrictions.between("a.alipayLogStart", DateUtil.getMinOfDate(dateFrom), DateUtil.getMaxOfDate(dateTo)));
		criteria.add(Restrictions.eq("a.alipayLogTradeState", true))
				.add(Restrictions.eq("a.alipayLogTradeValid", true))
				.add(Restrictions.in("a.alipayLogType", alipayLogTypes.split(",")));
		criteria.add(Restrictions.like("a.alipayLogOrderNo", "DEP", MatchMode.START));
		
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("a.branchNum"))
				.add(Projections.sum("a.alipayLogMoney"))
				.add(Projections.count("a.alipayLogId"))
		);
		criteria.setLockMode(LockMode.NONE);
		List<Object[]> objects = criteria.list();
		
		
		criteria = currentSession().createCriteria(AlipayLog.class, "a")
				.add(Restrictions.eq("a.systemBookCode", systemBookCode));
				
		
		if (branchNums != null && branchNums.size() > 0) {
			
			criteria.add(Restrictions.in("a.branchNum", branchNums));
		}
		criteria.add(Restrictions.between("a.alipayLogStart", DateUtil.getMinOfDate(dateFrom), DateUtil.getMaxOfDate(dateTo)));
		criteria.add(Restrictions.eq("a.alipayLogTradeState", true))
				.add(Restrictions.eq("a.alipayLogTradeValid", true))
				.add(Restrictions.in("a.alipayLogType", alipayLogTypes.split(",")));
		criteria.add(Restrictions.eq("a.alipayLogOrderNo", ""));
		
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("a.branchNum"))
				.add(Projections.sum("a.alipayLogMoney"))
				.add(Projections.count("a.alipayLogId"))
		);
		criteria.setLockMode(LockMode.NONE);
		objects.addAll(criteria.list());
		return objects;
	}

	@Override
	public List<AlipayLog> test(String systemBookCode,LogQuery logQuery,Date dateFrom,Date dateTo) {

			Criteria criteria = currentSession().createCriteria(AlipayLog.class, "a")
					.add(Restrictions.eq("a.systemBookCode", systemBookCode));
			if(StringUtils.isNotEmpty(logQuery.getLogItem())){
				criteria.add(Restrictions.like("a.alipayLogOrderNo", logQuery.getLogItem(), MatchMode.ANYWHERE));
				return criteria.list();
			}
			if(StringUtils.isNotEmpty(logQuery.getOperator())){
				criteria.add(Restrictions.eq("a.alipayLogOperator", logQuery.getOperator()));
			}
			if(StringUtils.isNotEmpty(logQuery.getOperateType())){
				criteria.add(Restrictions.eq("a.alipayLogType", logQuery.getOperateType()));
			}
			criteria.add(Restrictions.between("a.alipayLogStart", DateUtil.getMinOfDate(logQuery.getDateFrom()), DateUtil.getMaxOfDate(logQuery.getDateTo())));
			criteria.setLockMode(LockMode.NONE);
			return criteria.list();

	}

}
