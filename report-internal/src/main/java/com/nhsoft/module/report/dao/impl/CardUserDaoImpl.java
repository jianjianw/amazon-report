package com.nhsoft.module.report.dao.impl;


import com.nhsoft.module.report.dao.CardUserDao;
import com.nhsoft.module.report.model.CardUser;
import com.nhsoft.module.report.query.CardUserQuery;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.module.report.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/9/19.
 */
@Repository
public class CardUserDaoImpl extends  DaoImpl implements CardUserDao {
	
	private String createByCardUserQuery(CardUserQuery cardUserQuery) {
		StringBuffer sb = new StringBuffer();
		sb.append("from card_user as c with(nolock) left join card_balance as b with(nolock) on c.card_user_num = b.card_user_num ");
		sb.append("where c.system_book_code = '" + cardUserQuery.getSystemBookCode() + "' ");
		if (cardUserQuery.getBranchNums() != null && cardUserQuery.getBranchNums().size() > 0) {
			sb.append("and c.card_user_enroll_shop in " + AppUtil.getIntegerParmeList(cardUserQuery.getBranchNums()));
		}
		if (StringUtils.isNotEmpty(cardUserQuery.getCardUserName())) {
			sb.append("and c.card_user_cust_name like '%" + cardUserQuery.getCardUserName() + "%' ");
		}
		if (StringUtils.isNotEmpty(cardUserQuery.getCardPhone())) {
			sb.append("and c.card_user_phone like '%" + cardUserQuery.getCardPhone() + "%' ");
		}
		if (StringUtils.isNotEmpty(cardUserQuery.getCardIdNum())) {
			sb.append("and c.card_user_id_card_num like '%" + cardUserQuery.getCardIdNum() + "%' ");
		}
		if (StringUtils.isNotEmpty(cardUserQuery.getCardUserType())) {
			sb.append("and c.card_user_card_type = '" + Integer.parseInt(cardUserQuery.getCardUserType()) + "' ");
		}
		if (StringUtils.isNotEmpty(cardUserQuery.getStateName())) {
			sb.append("and c.card_user_state_name like '%" + cardUserQuery.getStateName() + "' ");
		}
		if (StringUtils.isNotEmpty(cardUserQuery.getCardPrintNum())) {
			sb.append("and c.card_user_printed_num like '%" + cardUserQuery.getCardPrintNum() + "%' ");
		}
		if (StringUtils.isNotEmpty(cardUserQuery.getCardUserException())) {
			sb.append("and c.card_user_exception_memo like '%" + cardUserQuery.getCardUserException() + "%' ");
		}
		if (StringUtils.isNotEmpty(cardUserQuery.getCardUserStorage())) {
			sb.append("and c.card_user_storage = '" + cardUserQuery.getCardUserStorage() + "' ");
		}
		if (cardUserQuery.getIslock() != null) {
			if (cardUserQuery.getIslock()) {
				sb.append("and c.card_user_locked = 1 ");
			} else {
				sb.append("and c.card_user_locked = 0 ");
				
			}
		}
		if (cardUserQuery.getDateFrom() != null) {
			sb.append("and c.card_user_date >= '"
					+ DateUtil.getLongDateTimeStr(DateUtil.getMinOfDate(cardUserQuery.getDateFrom())) + "' ");
		}
		if (cardUserQuery.getDateTo() != null) {
			sb.append("and c.card_user_date <= '"
					+ DateUtil.getLongDateTimeStr(DateUtil.getMaxOfDate(cardUserQuery.getDateTo())) + "' ");
		}
		if (cardUserQuery.getBalanceForm() != null) {
			sb.append("and b.card_balance_money >= " + cardUserQuery.getBalanceForm() + " ");
		}
		if (cardUserQuery.getBalanceTo() != null) {
			sb.append("and b.card_balance_money <= " + cardUserQuery.getBalanceTo() + " ");
		}
		if (cardUserQuery.getConsumeForm() != null) {
			sb.append("and b.card_balance_total_consume >= " + cardUserQuery.getConsumeForm() + " ");
		}
		if (cardUserQuery.getConsumeTo() != null) {
			sb.append("and b.card_balance_total_consume <= " + cardUserQuery.getConsumeTo() + " ");
		}
		if (cardUserQuery.getDepositForm() != null) {
			sb.append("and b.card_balance_total_deposit >= " + cardUserQuery.getDepositForm() + " ");
		}
		if (cardUserQuery.getDepositTo() != null) {
			sb.append("and b.card_balance_total_deposit <= " + cardUserQuery.getDepositTo() + " ");
		}
		if (cardUserQuery.getPointForm() != null) {
			sb.append("and c.card_user_total_cash >= " + cardUserQuery.getPointForm() + " ");
		}
		if (cardUserQuery.getPointTo() != null) {
			sb.append("and c.card_user_total_cash <= " + cardUserQuery.getPointTo() + " ");
		}
		boolean filterInitTime = false;
		if (cardUserQuery.getBirthDay() != null) {
			filterInitTime = true;
			if (cardUserQuery.getMonth()) {
				sb.append("and month(c.card_user_birth) = " + DateUtil.getMonth(cardUserQuery.getBirthDay()));
			}
			if (cardUserQuery.getDay()) {
				sb.append("and day(c.card_user_birth) = " + DateUtil.getDayOfMonth(cardUserQuery.getBirthDay()));
			}
		}
		if (cardUserQuery.getBirthFrom() != null) {
			filterInitTime = true;
			sb.append("and SUBSTRING(convert(varchar(8), c.card_user_birth, 112), 5, 4) >= '"
					+ DateUtil.getMonthDayStr(cardUserQuery.getBirthFrom()) + "' ");
		}
		if (cardUserQuery.getBirthTo() != null) {
			filterInitTime = true;
			sb.append("and SUBSTRING(convert(varchar(8), c.card_user_birth, 112), 5, 4) <= '"
					+ DateUtil.getMonthDayStr(cardUserQuery.getBirthTo()) + "' ");
			
		}
		if (filterInitTime) {
			sb.append("and c.card_user_birth != '" + AppConstants.INIT_TIME + "' ");
		}
		if (cardUserQuery.getCardUserNums() != null && cardUserQuery.getCardUserNums().size() > 0) {
			sb.append("and c.card_user_num in " + AppUtil.getIntegerParmeList(cardUserQuery.getCardUserNums()));
		}
		if (cardUserQuery.getValid() != null && cardUserQuery.getValid()) {
			sb.append("and c.card_user_state_code in (3, 7) ");
		}
		
		if (cardUserQuery.getPointBalanceFrom() != null) {
			sb.append("and c.card_user_point >= " + cardUserQuery.getPointBalanceFrom() + " ");
		}
		if (cardUserQuery.getPointBalanceTo() != null) {
			sb.append("and c.card_user_point <= " + cardUserQuery.getPointBalanceTo() + " ");
		}
		
		if (cardUserQuery.getDeadlineFrom() != null) {
			sb.append("and c.card_user_deadline >= '"
					+ DateUtil.getLongDateTimeStr(DateUtil.getMinOfDate(cardUserQuery.getDeadlineFrom())) + "' ");
		}
		if (cardUserQuery.getDeadlineTo() != null) {
			sb.append("and c.card_user_deadline <= '"
					+ DateUtil.getLongDateTimeStr(DateUtil.getMaxOfDate(cardUserQuery.getDeadlineTo())) + "' ");
		}
		if(cardUserQuery.getCardUserTypes() != null && cardUserQuery.getCardUserTypes().size() > 0){
			sb.append("and c.card_user_card_type in " + AppUtil.getIntegerParmeList(cardUserQuery.getCardUserTypes()));
			
		}
		return sb.toString();
	}
	
	@Override
	public List<CardUser> findByCardUserQuery(CardUserQuery cardUserQuery, int offset, int limit) {
		String sql = "select c.* " + createByCardUserQuery(cardUserQuery);
		if (cardUserQuery.getSortField() != null) {
			if(cardUserQuery.getSortField().equals("pointValue")){
				StringBuffer sb = new StringBuffer();
				sb.append("select card.*, isNull(point.pointBalance, 0) as pointValue from (" + sql + ") as card left join ");
				sb.append("(select card_user_num, sum(client_point_balance) as pointBalance from client_point with(nolock) where system_book_code = '" + cardUserQuery.getSystemBookCode() + "' ");
				sb.append("and client_point_del_flag = 0 group by card_user_num) as point ");
				sb.append("on card.card_user_num = point.card_user_num ");
				
				sql = sb.toString();
			} else {
				cardUserQuery.setSortField(AppUtil.getDBColumnName(cardUserQuery.getSortField()));
				
			}
			
			if (cardUserQuery.getSortField().equals("state")) {
				cardUserQuery.setSortField("card_user_state_code");
			}
			if (cardUserQuery.getSortField().equals("card_balance_total_deposit")
					|| cardUserQuery.getSortField().equals("card_balance_total_consume")
					|| cardUserQuery.getSortField().equals("card_balance_money")
					|| cardUserQuery.getSortField().equals("card_change_money")
					) {
				cardUserQuery.setSortField("b." + cardUserQuery.getSortField());
				
			} else if (cardUserQuery.getSortField().equals("pointValue")){
				//DO NOTHING
			} else {
				cardUserQuery.setSortField("c." + cardUserQuery.getSortField());
				
			}
			if (cardUserQuery.getSortType().equals("ASC")) {
				sql = sql + "order by " + cardUserQuery.getSortField() + " asc ";
			} else {
				sql = sql + "order by " + cardUserQuery.getSortField() + " desc ";
			}
		}
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.addEntity(CardUser.class);
		if (cardUserQuery.isPaging()) {
			query.setFirstResult(offset);
			query.setMaxResults(limit);
		}
		List<CardUser> cardUsers = query.list();
		return cardUsers;
	}
	
	@Override
	public Object[] sumByCardUserQuery(CardUserQuery cardUserQuery) {
		String sql = "select count(c.card_user_num) as amount, sum(c.card_user_total_cash) as cash, sum(b.card_balance_total_deposit) as deposit, sum(b.card_balance_total_consume) as consume,"
				+ "sum(b.card_balance_money) as balance, sum(c.card_user_total_invoice) as invoice, sum(b.card_change_money) as changeMoney "
				+ createByCardUserQuery(cardUserQuery);
		Query query = currentSession().createSQLQuery(sql);
		return (Object[]) query.uniqueResult();
	}
	
	@Override
	public List<Object[]> findCardCount(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType) {
		dateFrom = DateUtil.getMinOfDate(dateFrom);
		dateTo = DateUtil.getMaxOfDate(dateTo);
		
		StringBuffer sb = new StringBuffer();
		sb.append("select card_user_enroll_shop, count(card_user_num) as num ");
		sb.append("from card_user with(nolock) where system_book_code = '" + systemBookCode + "' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and card_user_enroll_shop in " + AppUtil.getIntegerParmeList(branchNums));
		}
		
		sb.append("and card_user_date between '" + DateUtil.getDateTimeString(dateFrom) + "' and '" + DateUtil.getDateTimeString(dateTo) + "' ");
		if (cardUserCardType != null) {
			sb.append("and card_user_card_type = " + cardUserCardType + " ");
		}
		sb.append("group by card_user_enroll_shop order by count(card_user_num) desc ");
		//排序不能动
		Query query = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = query.list();
		return objects;
	}
	
	@Override
	public Integer count(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, Boolean valid) {
		Criteria criteria = currentSession().createCriteria(CardUser.class, "c").add(
				Restrictions.eq("c.systemBookCode", systemBookCode));
		if (branchNum != null && !branchNum.equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)) {
			criteria.add(Restrictions.eq("c.cardUserEnrollShop", branchNum));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("c.cardUserDate", DateUtil.getMinOfDate(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("c.cardUserDate", DateUtil.getMaxOfDate(dateTo)));
		}
		if (valid != null) {
			criteria.add(Restrictions.eq("c.state.stateCode", 3));
			criteria.add(Restrictions
					.disjunction()
					.add(Restrictions.isNull("c.cardUserDeadline"))
					.add(Restrictions.ge("c.cardUserDeadline", DateUtil.getMinOfDate(Calendar.getInstance().getTime())))
					.add(Restrictions.eq("c.cardUserDeadline", DateUtil.getDateTimeHMS(AppConstants.INIT_TIME))));
		}
		criteria.setProjection(Projections.rowCount());
		return ((Long) criteria.uniqueResult()).intValue();
	}
	
	@Override
	public List<Object[]> findRevokeCardCount(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType) {
		Criteria criteria = currentSession().createCriteria(CardUser.class, "c")
				.createAlias("c.cardBalance", "balance", JoinType.LEFT_OUTER_JOIN)
				.add(Restrictions.eq("c.systemBookCode", systemBookCode))
				.add(Restrictions.gt("c.cardUserRevokeShop", 0));
		if (branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("c.cardUserRevokeShop", branchNums));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("c.cardUserRevokeTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("c.cardUserRevokeTime", DateUtil.getMaxOfDate(dateTo)));
		}
		if (cardUserCardType != null) {
			criteria.add(Restrictions.eq("c.cardUserCardType", cardUserCardType));
		}
		criteria.add(Restrictions.ne("c.state.stateCode", AppConstants.CARD_INIT_REVOKE_CODE));
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("c.cardUserRevokeShop"))
				.add(Projections.count("c.cardUserNum"))
				.add(Projections.sum("balance.cardBalanceMoney"))
		);
		return criteria.list();
	}
	
	@Override
	public List<Object[]> findCardCountByBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType) {
		Criteria criteria = currentSession().createCriteria(CardUser.class, "c").add(
				Restrictions.eq("c.systemBookCode", systemBookCode));
		if (branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("c.cardUserEnrollShop", branchNums));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("c.cardUserDate", DateUtil.getMinOfDate(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("c.cardUserDate", DateUtil.getMaxOfDate(dateTo)));
		}
		if (cardUserCardType != null) {
			criteria.add(Restrictions.eq("c.cardUserCardType", cardUserCardType));
		}
		criteria.setProjection(Projections
				.projectionList()
				.add(Projections.sqlGroupProjection("CONVERT(varchar(12) , card_user_date, 112 ) as biz",
						"CONVERT(varchar(12) , card_user_date, 112 )", new String[] { "biz" },
						new Type[] { StandardBasicTypes.STRING })).add(Projections.count("c.cardUserNum")));
		return criteria.list();
	}
	
	@Override
	public List<Object[]> findRevokeCardCountByBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType) {
		Criteria criteria = currentSession().createCriteria(CardUser.class, "c")
				.createAlias("c.cardBalance", "balance", JoinType.LEFT_OUTER_JOIN)
				.add(Restrictions.eq("c.systemBookCode", systemBookCode))
				.add(Restrictions.gt("c.cardUserRevokeShop", 0));
		if (branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("c.cardUserRevokeShop", branchNums));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("c.cardUserRevokeTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("c.cardUserRevokeTime", DateUtil.getMaxOfDate(dateTo)));
		}
		if (cardUserCardType != null) {
			criteria.add(Restrictions.eq("c.cardUserCardType", cardUserCardType));
		}
		criteria.add(Restrictions.ne("c.state.stateCode", AppConstants.CARD_INIT_REVOKE_CODE));
		criteria.setProjection(Projections
				.projectionList()
				.add(Projections.sqlGroupProjection("CONVERT(varchar(12) , card_user_revoke_time, 112 ) as biz",
						"CONVERT(varchar(12) , card_user_revoke_time, 112 )", new String[] { "biz" },
						new Type[] { StandardBasicTypes.STRING }))
				.add(Projections.count("c.cardUserNum"))
				.add(Projections.sum("balance.cardBalanceMoney"))
		
		);
		return criteria.list();
	}
	
	@Override
	public List<Object[]> findRevokeCardCountByBranchCardType(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType) {
		Criteria criteria = currentSession().createCriteria(CardUser.class, "c")
				.createAlias("c.cardBalance", "balance")
				.add(Restrictions.eq("c.systemBookCode", systemBookCode))
				.add(Restrictions.gt("c.cardUserRevokeShop", 0));
		if (branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("c.cardUserRevokeShop", branchNums));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("c.cardUserRevokeTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("c.cardUserRevokeTime", DateUtil.getMaxOfDate(dateTo)));
		}
		if (cardUserCardType != null) {
			criteria.add(Restrictions.eq("c.cardUserCardType", cardUserCardType));
		}
		criteria.add(Restrictions.ne("c.state.stateCode", AppConstants.CARD_INIT_REVOKE_CODE));
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("c.cardUserRevokeShop"))
				.add(Projections.groupProperty("c.cardUserCardType"))
				.add(Projections.count("c.cardUserNum"))
				.add(Projections.sum("balance.cardBalanceMoney"))
		);
		return criteria.list();
	}
	
	@Override
	public List<Object[]> findCardCountByBranchCardType(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType) {
		Criteria criteria = currentSession().createCriteria(CardUser.class, "c").add(
				Restrictions.eq("c.systemBookCode", systemBookCode));
		if (branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("c.cardUserEnrollShop", branchNums));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("c.cardUserDate", DateUtil.getMinOfDate(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("c.cardUserDate", DateUtil.getMaxOfDate(dateTo)));
		}
		if (cardUserCardType != null) {
			criteria.add(Restrictions.eq("c.cardUserCardType", cardUserCardType));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("c.cardUserEnrollShop"))
				.add(Projections.groupProperty("c.cardUserCardType"))
				.add(Projections.count("c.cardUserNum")));
		return criteria.list();
	}
	
	@Override
	public List<Object[]> findCardCountByBranchBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType) {
		Criteria criteria = currentSession().createCriteria(CardUser.class, "c").add(
				Restrictions.eq("c.systemBookCode", systemBookCode));
		if (branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("c.cardUserEnrollShop", branchNums));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("c.cardUserDate", DateUtil.getMinOfDate(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("c.cardUserDate", DateUtil.getMaxOfDate(dateTo)));
		}
		if (cardUserCardType != null) {
			criteria.add(Restrictions.eq("c.cardUserCardType", cardUserCardType));
		}
		criteria.setProjection(Projections
				.projectionList()
				.add(Projections.groupProperty("c.cardUserEnrollShop"))
				.add(Projections.sqlGroupProjection("CONVERT(varchar(12) , card_user_date, 112 ) as biz",
						"CONVERT(varchar(12) , card_user_date, 112 )", new String[] { "biz" },
						new Type[] { StandardBasicTypes.STRING })).add(Projections.count("c.cardUserNum")));
		return criteria.list();
	}
	
	@Override
	public List<Object[]> findRevokeCardCountByBranchBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType) {
		Criteria criteria = currentSession().createCriteria(CardUser.class, "c")
				.createAlias("c.cardBalance", "balance")
				.add(Restrictions.eq("c.systemBookCode", systemBookCode))
				.add(Restrictions.gt("c.cardUserRevokeShop", 0));
		if (branchNums != null && branchNums.size() > 0) {
			criteria.add(Restrictions.in("c.cardUserRevokeShop", branchNums));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("c.cardUserRevokeTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("c.cardUserRevokeTime", DateUtil.getMaxOfDate(dateTo)));
		}
		if (cardUserCardType != null) {
			criteria.add(Restrictions.eq("c.cardUserCardType", cardUserCardType));
		}
		criteria.setProjection(Projections
				.projectionList()
				.add(Projections.groupProperty("c.cardUserRevokeShop"))
				.add(Projections.sqlGroupProjection("CONVERT(varchar(12) , card_user_revoke_time, 112 ) as biz",
						"CONVERT(varchar(12) , card_user_revoke_time, 112 )", new String[] { "biz" },
						new Type[] { StandardBasicTypes.STRING }))
				.add(Projections.count("c.cardUserNum"))
				.add(Projections.sum("balance.cardBalanceMoney"))
		
		);
		return criteria.list();
	}
	
	@Override
	public BigDecimal getRevokeMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(b.card_balance_money) ");
		sb.append("from card_balance as b with(nolock) inner join card_user as c with(nolock) on c.card_user_num = b.card_user_num ");
		sb.append("where c.system_book_code = '" + systemBookCode + "' and c.card_user_revoke_shop > 0 ");
		if(branchNums != null && branchNums.size() > 0){
			sb.append("and c.card_user_revoke_shop in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if(dateFrom != null){
			sb.append("and c.card_user_revoke_time >= '" + DateUtil.getLongDateTimeStr(DateUtil.getMinOfDate(dateFrom)) + "' ");
		}
		if(dateTo != null){
			sb.append("and c.card_user_revoke_time <= '" + DateUtil.getLongDateTimeStr(DateUtil.getMaxOfDate(dateTo)) + "' ");
		}
		
		Query query = currentSession().createSQLQuery(sb.toString());
		Object object = query.uniqueResult();
		return object == null?BigDecimal.ZERO:(BigDecimal)object;
	}

	@Override
	public int findTotalCardCount(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
								  Integer cardUserCardType) {
		dateFrom = DateUtil.getMinOfDate(dateFrom);
		dateTo = DateUtil.getMaxOfDate(dateTo);

		StringBuffer sb = new StringBuffer();
		sb.append("select count(card_user_num) as num ");
		sb.append("from card_user with(nolock) where system_book_code = '" + systemBookCode + "' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and card_user_enroll_shop in " + AppUtil.getIntegerParmeList(branchNums));
		}

		sb.append("and card_user_date between '" + DateUtil.getDateTimeString(dateFrom) + "' and '" + DateUtil.getDateTimeString(dateTo) + "' ");
		if (cardUserCardType != null) {
			sb.append("and card_user_card_type = " + cardUserCardType + " ");
		}
		Query query = currentSession().createSQLQuery(sb.toString());
		Object object = query.uniqueResult();
		if(object != null){
			return (Integer)object;
		}
		return 0;
	}

	@Override
	public List<Object[]> findCardUserCountByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select card_user_enroll_shop,count(card_user_num) ");
		sb.append("from card_user ");
		sb.append("where system_book_code = :systemBookCode ");
		if(branchNums != null && branchNums.size()>0){
			sb.append("and card_user_enroll_shop in " + AppUtil.getIntegerParmeList(branchNums));
		}
		if (dateFrom != null) {
			sb.append("and card_user_date >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if (dateTo != null) {
			sb.append("and card_user_date <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		sb.append("group by card_user_enroll_shop order by card_user_enroll_shop asc");
		SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
		sqlQuery.setString("systemBookCode", systemBookCode);
		List list = sqlQuery.list();
		return list;

	}
}
