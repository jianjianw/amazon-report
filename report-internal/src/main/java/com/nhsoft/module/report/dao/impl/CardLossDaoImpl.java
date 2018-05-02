package com.nhsoft.module.report.dao.impl;

import com.nhsoft.module.report.dao.BranchDao;
import com.nhsoft.module.report.dao.CardLossDao;
import com.nhsoft.module.report.model.Branch;
import com.nhsoft.module.report.model.CardLoss;
import com.nhsoft.module.report.model.CardUser;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;
import com.nhsoft.report.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("deprecation")
@Repository
public class CardLossDaoImpl extends DaoImpl implements CardLossDao {

	@Autowired
	private BranchDao branchDao;
	
	@Override
	public List<CardLoss> findByCardUserNum(Integer cardUserNum,
											String operateName) {
		Criteria criteria = currentSession().createCriteria(CardLoss.class, "c")
				.add(Restrictions.eq("c.cardUserNum", cardUserNum));
		if(StringUtils.isNotEmpty(operateName)){
			criteria.add(Restrictions.eq("c.cardLossOperateName", operateName));
		}
		criteria.addOrder(Order.asc("c.cardLossOperateTime"));
		return criteria.list();
	}

	private Criteria createByCardReportQuery(CardReportQuery cardReportQuery){
		Criteria criteria = currentSession().createCriteria(CardLoss.class, "c")
				.add(Restrictions.eq("c.systemBookCode", cardReportQuery.getSystemBookCode()));
		if(cardReportQuery.getOperateBranch() != null){
			Branch.BranchId id = new Branch.BranchId();
			id.setSystemBookCode(cardReportQuery.getSystemBookCode());
			id.setBranchNum(cardReportQuery.getOperateBranch());
			Branch branch = currentSession().get(Branch.class, id);
			criteria.add(Restrictions.eq("c.cardLossBranchName", branch.getBranchName()));
		}
		if(cardReportQuery.getOperateBranchNums() != null && cardReportQuery.getOperateBranchNums().size() > 0) {
			List<String> branchNames = branchDao.findAll(cardReportQuery.getSystemBookCode()).stream().filter(b -> cardReportQuery.getOperateBranchNums().contains(b.getId().getBranchNum())).map(Branch::getBranchName).collect(Collectors.toList());
			criteria.add(Restrictions.in("c.cardLossBranchName", branchNames));
		}
		if(cardReportQuery.getBranchNum() != null 
				|| StringUtils.isNotEmpty(cardReportQuery.getCardPrintNum())
				|| (cardReportQuery.getBranchNums() != null && cardReportQuery.getBranchNums().size() > 0)){
			DetachedCriteria subCriteria = DetachedCriteria.forClass(CardUser.class, "u")
					.add(Restrictions.eqProperty("u.cardUserNum", "c.cardUserNum"))
					.add(Restrictions.eq("u.systemBookCode", cardReportQuery.getSystemBookCode()));
			if(cardReportQuery.getBranchNum() != null){
				subCriteria.add(Restrictions.eq("u.cardUserEnrollShop", cardReportQuery.getBranchNum()));
			}
			if(StringUtils.isNotEmpty(cardReportQuery.getCardPrintNum())){
				subCriteria.add(Restrictions.eq("u.cardUserPrintedNum", cardReportQuery.getCardPrintNum()));
			}
			if(cardReportQuery.getBranchNums() != null && cardReportQuery.getBranchNums().size() > 0){
				subCriteria.add(Restrictions.in("u.cardUserEnrollShop", cardReportQuery.getBranchNums()));
			}
			criteria.add(Subqueries.exists(subCriteria.setProjection(Projections.property("u.cardUserNum"))));
		}
		if(cardReportQuery.getDateFrom() != null){
			criteria.add(Restrictions.ge("c.cardLossOperateTime", DateUtil.getMinOfDate(cardReportQuery.getDateFrom())));
		}
		if(cardReportQuery.getDateTo() != null){
			criteria.add(Restrictions.le("c.cardLossOperateTime", DateUtil.getMaxOfDate(cardReportQuery.getDateTo())));
		}
		if(StringUtils.isNotEmpty(cardReportQuery.getOperator())){
			criteria.add(Restrictions.eq("c.cardLossOperator", cardReportQuery.getOperator()));
		}
		if(cardReportQuery.getPrizeCard() != null){
			if(cardReportQuery.getPrizeCard()){
				criteria.add(Restrictions.eq("c.cardLossOperateName", "挂失"));
			} else {
				criteria.add(Restrictions.eq("c.cardLossOperateName", "解挂"));
			}
		}
		return criteria;
	}
	
	@Override
	public List<CardLoss> findByCardReportQuery(
			CardReportQuery cardReportQuery, int offset, int limit) {
		Criteria criteria = createByCardReportQuery(cardReportQuery);
		if(cardReportQuery.isPaging()){
			criteria.setFirstResult(offset);
			criteria.setMaxResults(limit);
			if(cardReportQuery.getSortField() != null){
				if(cardReportQuery.getSortType().equals("ASC")){
					criteria.addOrder(Order.asc(cardReportQuery.getSortField()));
				} else {
					criteria.addOrder(Order.desc(cardReportQuery.getSortField()));
				}
			} else {
				criteria.addOrder(Order.asc("c.cardLossOperateTime"));
			}
		}	
		return criteria.list();
	}

	@Override
	public int countByCardReportQuery(CardReportQuery cardReportQuery) {
		Criteria criteria = createByCardReportQuery(cardReportQuery);
		criteria.setProjection(Projections.rowCount());
		return ((Long)criteria.uniqueResult()).intValue();
	}

	@Override
	public List<CardLoss> findToLog(Date dateFrom, Date dateTo) {
		SQLQuery query = currentSession().createSQLQuery("select * from card_loss with(nolock) where card_loss_operate_time > '" + DateUtil.getLongDateTimeStr(dateFrom) + "' and card_loss_operate_time <= '" + DateUtil.getLongDateTimeStr(dateTo) + "' order by card_loss_operate_time ");
		query.addEntity(CardLoss.class);
		return query.list();
	}
	
	

}
