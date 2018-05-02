package com.nhsoft.module.report.dao.impl;

import com.nhsoft.module.report.dao.ConsumePointDao;
import com.nhsoft.module.report.model.CardUser;
import com.nhsoft.module.report.model.ConsumePoint;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;
import com.nhsoft.report.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.*;
import org.hibernate.sql.JoinType;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class ConsumePointDaoImpl extends DaoImpl implements ConsumePointDao {



	@Override
	public List<ConsumePoint> findByCardReportQuery(CardReportQuery cardReportQuery, int offset, int limit) {
		Criteria criteria = createByCardReportQuery(cardReportQuery);
		if (cardReportQuery.isPaging()) {
			criteria.setFirstResult(offset);
			criteria.setMaxResults(limit);
			if (cardReportQuery.getSortField() != null) {
				if (cardReportQuery.getSortType().equals("ASC")) {
					criteria.addOrder(Order.asc(cardReportQuery.getSortField()));
				} else {
					criteria.addOrder(Order.desc(cardReportQuery.getSortField()));
				}
			} else {
				criteria.addOrder(Order.asc("c.consumePointDate"));
			}
		} else {
			criteria.addOrder(Order.asc("c.consumePointDate"));
		}
		return criteria.list();
	}


	@Override
	public Object[] sumByCardReportQuery(CardReportQuery cardReportQuery) {
		Criteria criteria = createByCardReportQuery(cardReportQuery);
		criteria.createAlias("c.posItem", "p", JoinType.LEFT_OUTER_JOIN);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.count("c.consumePointFid"))
				.add(Projections.sum("c.consumePointPoint"))
				.add(Projections.sum("c.consumePointAmount"))
				.add(Projections.sum("c.consumePointCost"))
				.add(Projections.sqlProjection("sum(consume_point_amount * item_cost_price) as cost, sum(consume_point_amount * item_transfer_price) as transfer", new String[] { "cost", "transfer" },
						new Type[] { StandardBasicTypes.BIG_DECIMAL, StandardBasicTypes.BIG_DECIMAL })));
		return (Object[]) criteria.uniqueResult();
	}


	private Criteria createByCardReportQuery(CardReportQuery cardReportQuery) {
		Criteria criteria = currentSession().createCriteria(ConsumePoint.class, "c").add(
				Restrictions.eq("c.systemBookCode", cardReportQuery.getSystemBookCode()));
		if (cardReportQuery.getOperateBranch() != null) {
			criteria.add(Restrictions.eq("c.branchNum", cardReportQuery.getOperateBranch()));
		}
		if(cardReportQuery.getOperateBranchNums() != null && cardReportQuery.getOperateBranchNums().size() > 0) {
			criteria.add(Restrictions.in("c.branchNum", cardReportQuery.getOperateBranchNums()));
		}
		if (cardReportQuery.getCardUserNum() != null) {
			criteria.add(Restrictions.eq("c.consumePointCustNum", cardReportQuery.getCardUserNum()));
		}
		if (cardReportQuery.getBranchNum() != null || StringUtils.isNotEmpty(cardReportQuery.getCardPrintNum())
				|| (cardReportQuery.getBranchNums() != null && cardReportQuery.getBranchNums().size() > 0)) {
			DetachedCriteria subCriteria = DetachedCriteria.forClass(CardUser.class, "u")
					.add(Restrictions.eqProperty("u.cardUserNum", "c.consumePointCustNum"))
					.add(Restrictions.eq("u.systemBookCode", cardReportQuery.getSystemBookCode()));
			if (cardReportQuery.getBranchNum() != null) {
				subCriteria.add(Restrictions.eq("u.cardUserEnrollShop", cardReportQuery.getBranchNum()));
			}
			if (StringUtils.isNotEmpty(cardReportQuery.getCardPrintNum())) {
				subCriteria.add(Restrictions.eq("u.cardUserPrintedNum", cardReportQuery.getCardPrintNum()));
			}
			if (cardReportQuery.getBranchNums() != null && cardReportQuery.getBranchNums().size() > 0) {
				subCriteria.add(Restrictions.in("u.cardUserEnrollShop", cardReportQuery.getBranchNums()));
			}
			criteria.add(Subqueries.exists(subCriteria.setProjection(Projections.property("u.cardUserNum"))));
		}
		if (cardReportQuery.getDateFrom() != null) {
			criteria.add(Restrictions.ge("c.consumePointDate", DateUtil.getMinOfDate(cardReportQuery.getDateFrom())));
		}
		if (cardReportQuery.getDateTo() != null) {
			criteria.add(Restrictions.le("c.consumePointDate", DateUtil.getMaxOfDate(cardReportQuery.getDateTo())));
		}
		if (StringUtils.isNotEmpty(cardReportQuery.getOperator())) {
			criteria.add(Restrictions.eq("c.consumePointOperator", cardReportQuery.getOperator()));
		}
		if(cardReportQuery.getItemNums() != null && cardReportQuery.getItemNums().size() > 0) {
			criteria.add(Restrictions.in("c.consumePointItemNum", cardReportQuery.getItemNums()));
		}
		return criteria;
	}

}
