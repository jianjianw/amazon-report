package com.nhsoft.module.report.dao.impl;



import com.nhsoft.module.report.dao.MobileAppDao;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.phone.server.model.*;
import com.nhsoft.module.report.dto.MobileBusinessDTO;
import com.nhsoft.module.report.dto.MobileCardDTO;
import com.nhsoft.report.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

@Repository
public class MobileAppDaoImpl extends DaoImpl implements MobileAppDao {

	@Override
	public MobileBusiness findMobileAppBusiness(String systemBookCode, List<Integer> branchNums, Date dateFrom,
	                                            Date dateTo) {
		MobileBusiness mobileAppBusiness = new MobileBusiness();

		String hql = " select sum(order_payment_money + order_coupon_total_money - order_mgr_discount_money) as paymentMoney,"
				+ " count(order_no) as receiptCount, "
				+ " sum(order_discount_money) as discount, sum(order_mgr_discount_money) as mgrDiscount, sum(order_round) as orderRound "
				+ " from pos_order with(nolock) "
				+ " where system_book_code = '"
				+ systemBookCode
				+ "' and branch_num in "
				+ AppUtil.getIntegerParmeList(branchNums)
				+ " "
				+ " and shift_table_bizday between '"
				+ DateUtil.getDateShortStr(dateFrom)
				+ "' and '"
				+ DateUtil.getDateShortStr(dateTo) + "' " + " and order_state_code in (5, 7)";
		Query query = currentSession().createSQLQuery(hql);
		List<Object[]> objects = query.list();
		if (objects.size() > 0) {
			Object[] obj = objects.get(0);
			mobileAppBusiness.setBusinessMoney(obj[0] == null ? BigDecimal.ZERO : (BigDecimal) obj[0]);
			mobileAppBusiness.setReceiptCount(obj[1] == null ? BigDecimal.ZERO : BigDecimal.valueOf((Integer) obj[1]));
			mobileAppBusiness.setReceiptMoeny(mobileAppBusiness.getBusinessMoney());
			mobileAppBusiness.setDiscountMoney(obj[2] == null ? BigDecimal.ZERO : (BigDecimal) obj[2]);
			mobileAppBusiness.setDiscountMoney(mobileAppBusiness.getDiscountMoney().add(
					obj[3] == null ? BigDecimal.ZERO : (BigDecimal) obj[3]));
			mobileAppBusiness.setDiscountMoney(mobileAppBusiness.getDiscountMoney().add(
					obj[4] == null ? BigDecimal.ZERO : (BigDecimal) obj[4]));

			if (mobileAppBusiness.getReceiptCount().compareTo(BigDecimal.ZERO) != 0) {
				mobileAppBusiness.setReceiptAvgMoney(mobileAppBusiness.getBusinessMoney().divide(
						mobileAppBusiness.getReceiptCount(), 2, BigDecimal.ROUND_HALF_UP));

			} else {
				mobileAppBusiness.setReceiptAvgMoney(BigDecimal.ZERO);
			}
		}
		return mobileAppBusiness;
	}

	@Override
	public List<Object[]> findShopPaymentMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num, sum(order_payment_money + order_coupon_total_money - order_mgr_discount_money) as paymentMoney, ");
		sb.append("sum(case when order_card_user_num > 0 then (order_payment_money - order_mgr_discount_money + order_coupon_total_money) end) as cardMoney ");
		sb.append("from pos_order with(nolock) ");
		sb.append("where system_book_code = '" + systemBookCode + "' and branch_num in "
				+ AppUtil.getIntegerParmeList(branchNums) + " ");
		sb.append("and shift_table_bizday between '" + DateUtil.getDateShortStr(dateFrom) + "' and '"
				+ DateUtil.getDateShortStr(dateTo) + "' ");
		sb.append("and order_state_code in (5, 7) ");
		sb.append("group by branch_num order by branch_num");

		Query query = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = query.list();
		return objects;
	}

	@Override
	public List<Object[]> findShopReceipt(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num, sum(order_payment_money + order_coupon_total_money - order_mgr_discount_money) as paymentMoney, count(order_no) as orderNums, ");
		sb.append("sum(case when order_card_user_num > 0 then (order_payment_money - order_mgr_discount_money + order_coupon_total_money) end) as cardMoney, ");
		sb.append("count(case when order_card_user_num > 0 then order_no end) as cardAmount ");
		sb.append("from pos_order with(nolock) ");
		sb.append("where system_book_code = '" + systemBookCode + "' and branch_num in "
				+ AppUtil.getIntegerParmeList(branchNums) + " ");
		sb.append("and shift_table_bizday between '" + DateUtil.getDateShortStr(dateFrom) + "' and '"
				+ DateUtil.getDateShortStr(dateTo) + "' ");
		sb.append("and order_state_code in (5, 7) ");
		sb.append("group by branch_num order by branch_num");

		Query query = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = query.list();
		return objects;
	}

	@Override
	public List<Object[]> findShopDiscountMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo) {
		String hql = " select branch_num, sum(order_discount_money + order_mgr_discount_money + order_round) as discount"
				+ " from pos_order with(nolock) "
				+ " where system_book_code = '"
				+ systemBookCode
				+ "' and branch_num in "
				+ AppUtil.getIntegerParmeList(branchNums)
				+ " "
				+ " and shift_table_bizday between '"
				+ DateUtil.getDateShortStr(dateFrom)
				+ "' and '"
				+ DateUtil.getDateShortStr(dateTo)
				+ "' "
				+ " and order_state_code in (5, 7)"
				+ " group by branch_num order by branch_num ";
		Query query = currentSession().createSQLQuery(hql);
		List<Object[]> objects = query.list();
		return objects;
	}

	@Override
	public List<Object[]> findPaymentSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select payment_pay_by, sum(payment_money) ");
		sb.append("from payment with(nolock) where system_book_code = '" + systemBookCode + "' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums) + " ");
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");

		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		sb.append("group by payment_pay_by order by payment_pay_by ");
		Query query = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = query.list();
		return objects;
	}

	@Override
	public List<Object[]> findShopPayment(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
			String paymentType) {
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num, sum(payment_money) ");
		sb.append("from payment with(nolock) where system_book_code = '" + systemBookCode + "' ");
		sb.append("and payment_pay_by = '" + paymentType + "' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums) + " ");
		}
		if (dateFrom != null) {
			sb.append("and shift_table_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");

		}
		if (dateTo != null) {
			sb.append("and shift_table_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		sb.append("group by branch_num order by branch_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		List<Object[]> objects = query.list();
		return objects;
	}

	@Override
	public List<MobileSalesRank> findProductRank(String systemBookCode, List<Integer> branchNums, Date dateFrom,
	                                             Date dateTo, String categoryCode, String var, Integer rankFrom, Integer rankTo, String sortField) {
		StringBuffer queryStr = new StringBuffer();
		queryStr.append(" select sum(case when t.order_detail_state_code = 4 then -t.order_detail_amount else order_detail_amount end) as amount,");
		queryStr.append(" sum(case when t.order_detail_state_code = 1 then t.order_detail_payment_money when t.order_detail_state_code = 4 then -t.order_detail_payment_money end) as money,");
		queryStr.append(" p.item_name, p.item_code as code, p.item_num from pos_order_detail as t with(nolock), pos_item as p with(nolock) ");
		queryStr.append(" where t.order_detail_book_code = '" + systemBookCode + "' ");
		queryStr.append(" and t.order_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		queryStr.append(" and t.order_detail_bizday between '" + DateUtil.getDateShortStr(dateFrom) + "' and '"
				+ DateUtil.getDateShortStr(dateTo) + "'");
		queryStr.append(" and t.order_detail_order_state in (5, 7)");
		queryStr.append(" and p.item_num = t.item_num");
		queryStr.append(" and t.order_detail_state_code != 8 ");
		if (StringUtils.isNotEmpty(categoryCode)) {
			queryStr.append(" and p.item_category_code = '" + categoryCode + "' ");
		}
		if (StringUtils.isNotEmpty(var)) {
			queryStr.append(" and (p.item_name like '%" + var + "%' or p.item_code like '%" + var
					+ "%' or p.store_item_pinyin like '%" + var.toUpperCase() + "%') ");
		}
		queryStr.append(" group by p.item_name, p.item_code, p.item_num");
		if (StringUtils.isEmpty(sortField)) {
			queryStr.append(" order by amount desc, money desc");

		} else if (sortField.equals("money")) {
			queryStr.append(" order by money desc, amount desc");
		} else if (sortField.equals("amount")) {
			queryStr.append(" order by amount desc, money desc");
		}

		Query query = currentSession().createSQLQuery(queryStr.toString());
		if(rankFrom != null && rankTo != null){
			
			query.setFirstResult(rankFrom);
			query.setMaxResults(rankTo - rankFrom);
		}
		List<Object[]> objects = query.list();
		List<MobileSalesRank> list = new ArrayList<MobileSalesRank>();
		String itemName;
		String itemCode;
		Integer itemNum;
		BigDecimal amount;
		BigDecimal money;
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			amount = object[0] == null ? BigDecimal.ZERO : (BigDecimal) object[0];
			money = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			itemName = (String) object[2];
			itemCode = (String) object[3];
			itemNum = (Integer)object[4];
			MobileSalesRank rank = new MobileSalesRank();
			rank.setSalesCode(itemCode);
			if(rankFrom != null){
				rank.setSalesRank(i + 1 + rankFrom);
				
			}
			rank.setSalesCount(amount);
			rank.setSelesMoney(money);
			rank.setSalesName(itemName);
			rank.setSelesUnitCount(BigDecimal.ZERO);
			rank.setIsProduct(true);
			rank.setSalesId(itemNum.toString());
			list.add(rank);
		}
		return list;
	}

	@Override
	public List<MobileSalesRank> findCategoryRank(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo, List<String> categoryCodes) {
		StringBuffer queryStr = new StringBuffer();
		queryStr.append(" select sum(case when t.order_detail_state_code = 4 then -t.order_detail_amount else order_detail_amount end) as amount,");
		queryStr.append(" sum(case when t.order_detail_state_code = 1 then t.order_detail_payment_money when t.order_detail_state_code = 4 then -t.order_detail_payment_money end) as money,");
		queryStr.append(" p.item_category, p.item_category_code from pos_order_detail as t with(nolock), pos_item as p with(nolock) ");
		queryStr.append(" where t.order_detail_book_code = '" + systemBookCode + "'");
		queryStr.append(" and t.order_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		queryStr.append(" and t.order_detail_bizday between '" + DateUtil.getDateShortStr(dateFrom) + "' and '"
				+ DateUtil.getDateShortStr(dateTo) + "'");
		queryStr.append(" and t.order_detail_order_state in (5, 7)");
		queryStr.append(" and p.item_num = t.item_num");
		queryStr.append(" and t.order_detail_state_code != 8 ");

		if (categoryCodes != null && categoryCodes.size() > 0) {
			queryStr.append(" and p.item_category_code in " + AppUtil.getStringParmeList(categoryCodes) + " ");
		}
		queryStr.append(" group by p.item_category, p.item_category_code");
		Query query = currentSession().createSQLQuery(queryStr.toString());
		List<Object[]> objects = query.list();
		List<MobileSalesRank> list = new ArrayList<MobileSalesRank>();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			BigDecimal amount = object[0] == null ? BigDecimal.ZERO : (BigDecimal) object[0];
			BigDecimal money = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			String categoryName = (String) object[2];
			String categoryCode = (String) object[3];
			MobileSalesRank rank = new MobileSalesRank();
			rank.setSalesCount(amount);
			rank.setSelesMoney(money);
			rank.setSalesName(categoryName);
			rank.setSalesCode(categoryCode);
			rank.setSalesId(categoryCode);
			rank.setSelesUnitCount(BigDecimal.ZERO);
			list.add(rank);
		}
		return list;
	}

	@Override
	public MobileCardSum readCardSum(String systemBookCode, List<Integer> branchNums) {
		String hql = "select sum(d.deposit_money) as money, sum(d.deposit_cash) as cash "
				+ " from card_deposit as d with(nolock) where  d.system_book_code = '" + systemBookCode + "' "
				+ " and d.branch_num in " + AppUtil.getIntegerParmeList(branchNums);
		Query query = currentSession().createSQLQuery(hql);
		List<Object[]> list = query.list();
		MobileCardSum data = new MobileCardSum();
		if (list.size() > 0) {
			Object[] object = list.get(0);
			BigDecimal totalDeposit = object[0] == null ? BigDecimal.ZERO : (BigDecimal) object[0];
			BigDecimal totalCash = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			data.setTotalDepositMoney(totalDeposit);
			data.setTotalDepositCash(totalCash);
		}
		hql = "select sum(c.consume_money) as consumeTotal  from Card_consume as c with(nolock) where "
				+ " c.system_book_code = '" + systemBookCode + "' and c.branch_num in "
				+ AppUtil.getIntegerParmeList(branchNums);
		query = currentSession().createSQLQuery(hql);
		list = query.list();
		if (list.size() > 0) {
			Object object = list.get(0);
			BigDecimal totalConsume = object == null ? BigDecimal.ZERO : (BigDecimal) object;
			data.setTotalConsume(totalConsume);
		}
		hql = "select count(c.card_user_num) as amount,sum(b.card_balance_money) as money "
				+ " from card_user as c with(nolock) left join card_balance as b with(nolock) on b.card_user_num = c.card_user_num "
				+ " where  c.system_book_code = '" + systemBookCode + "'  and c.card_user_enroll_shop in "
				+ AppUtil.getIntegerParmeList(branchNums) + " and c.card_user_state_code in (3, 7) "
				+ " and (c.card_user_deadline >= :now or c.card_user_deadline is null or c.card_user_deadline = :initDate) ";
		
		String now = DateUtil.getDateTimeString(DateUtil.getMinOfDate(Calendar.getInstance().getTime()));
		query = currentSession().createSQLQuery(hql);
		query.setString("initDate", AppConstants.INIT_TIME);
		query.setString("now", now);
		list = query.list();
		if (list.size() > 0) {
			Object[] object = list.get(0);
			Integer totalValueCard = object[0] == null ? 0 : (Integer) object[0];
			data.setCardTotal(totalValueCard);

			BigDecimal balance = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			data.setBalanceMoney(balance);
		}
		hql = " select sum(c.client_point_balance) as point"
				+ " from card_user as u with(nolock), client_point as c with(nolock) where u.card_user_num = c.card_user_num "
				+ " and c.system_book_code = '" + systemBookCode + "'  and c.branch_num in "
				+ AppUtil.getIntegerParmeList(branchNums) + " and u.card_user_state_code in (3, 7) "
				+ " and c.client_point_del_flag = 0 "
				+ " and (u.card_user_deadline >= :now or u.card_user_deadline is null or u.card_user_deadline = :initDate) ";
		query = currentSession().createSQLQuery(hql);
		query.setString("initDate", AppConstants.INIT_TIME);
		query.setString("now", now);
		List<Object> listCount = query.list();
		if ((listCount.size() > 0) && (listCount.get(0) != null)) {
			BigDecimal totalPoint = (BigDecimal) listCount.get(0);
			data.setBalancePoint(totalPoint);
		}
		return data;
	}

	@Override
	public List<MobileCardSum> findCardSums(String systemBookCode, List<Integer> branchNums) {
		Map<Integer, MobileCardSum> map = new HashMap<Integer, MobileCardSum>();
		String hql = "select branch_num, sum(deposit_money) as money, sum(deposit_cash) as cash "
				+ " from card_deposit with(nolock) where  system_book_code = '" + systemBookCode + "' "
				+ " and branch_num in " + AppUtil.getIntegerParmeList(branchNums) + "  group by branch_num";
		Query query = currentSession().createSQLQuery(hql);
		List<Object[]> list = query.list();
		MobileCardSum data = null;
		for (int i = 0; i < list.size(); i++) {
			Object[] object = list.get(i);
			Integer branchNum = (Integer) object[0];
			BigDecimal totalDeposit = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			BigDecimal totalCash = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			data = new MobileCardSum();
			data.setTotalDepositMoney(totalDeposit);
			data.setTotalDepositCash(totalCash);
			data.setShopNum(branchNum);
			map.put(branchNum, data);
		}
		String sql = "select branch_num, sum(consume_money) as consumeTotal "
				+ " from card_consume with(nolock) where  system_book_code = '" + systemBookCode + "' "
				+ " and branch_num in " + AppUtil.getIntegerParmeList(branchNums) + "  group by branch_num";
		query = currentSession().createSQLQuery(sql);
		list = query.list();
		for (int i = 0; i < list.size(); i++) {
			Object[] object = list.get(i);
			Integer branchNum = (Integer) object[0];
			BigDecimal totalConsume = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			data = map.get(branchNum);
			if (data == null) {
				data = new MobileCardSum();
				data.setShopNum(branchNum);
				map.put(branchNum, data);
			}
			data.setTotalConsume(totalConsume);
		}
		String now = DateUtil.getDateTimeString(DateUtil.getMinOfDate(Calendar.getInstance().getTime()));

		hql = "select card_user_enroll_shop, count(card_user_num)  from card_user with(nolock) where "
				+ " system_book_code = '" + systemBookCode + "'  and card_user_enroll_shop in "
				+ AppUtil.getIntegerParmeList(branchNums) + " and card_user_state_code in (3, 7) "
				+ " and (card_user_deadline >= :now or card_user_deadline is null or card_user_deadline = :initDate) "
				+ " group by card_user_enroll_shop ";
		query = currentSession().createSQLQuery(hql);
		query.setString("initDate", AppConstants.INIT_TIME);
		query.setString("now", now);
		list = query.list();
		for (int i = 0; i < list.size(); i++) {
			Object[] object = list.get(i);
			Integer branchNum = (Integer) object[0];
			Integer cardTotal = object[1] == null ? 0 : (Integer) object[1];
			data = map.get(branchNum);
			if (data == null) {
				data = new MobileCardSum();
				data.setShopNum(branchNum);
				map.put(branchNum, data);
			}
			data.setCardTotal(cardTotal);
		}
		return new ArrayList<MobileCardSum>(map.values());
	}

	@Override
	public MobileCardManage readCardManager(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		dateFrom = DateUtil.getMinOfDate(dateFrom);
		dateTo = DateUtil.getMaxOfDate(dateTo);

		
		String dateTimeFrom =  DateUtil.getDateTimeString(dateFrom);
		String dateTimeTo =  DateUtil.getDateTimeString(dateTo);

		MobileCardManage cardManager = new MobileCardManage();
		String hql = "select count(card_user_num) as num  from card_user with(nolock) where system_book_code = '"
				+ systemBookCode + "'  and card_user_enroll_shop in " + AppUtil.getIntegerParmeList(branchNums)
				+ " and card_user_date between '" + dateTimeFrom + "' and '"
				+ dateTimeTo + "' ";
		Query query = currentSession().createSQLQuery(hql);
		Object object = (Object) query.uniqueResult();
		cardManager.setCardAddedCount((Integer) object);

		hql = "select count(deposit_fid) as num , sum(deposit_cash) as cash, sum(deposit_money) as money "
				+ " from card_deposit with(nolock) where system_book_code = '" + systemBookCode + "'  "
				+ " and branch_num in " + AppUtil.getIntegerParmeList(branchNums) + " and shift_table_bizday between '"
				+ DateUtil.getDateShortStr(dateFrom) + "' and '" + DateUtil.getDateShortStr(dateTo) + "' ";
		query = currentSession().createSQLQuery(hql);
		Object[] objects = (Object[]) query.uniqueResult();
		Integer count = 0;
		BigDecimal cash = objects[1] == null ? BigDecimal.ZERO : (BigDecimal) objects[1];
		BigDecimal money = objects[2] == null ? BigDecimal.ZERO : (BigDecimal) objects[2];
		cardManager.setCardDepositCount((Integer) objects[0]);
		cardManager.setCardDepositCash(cash);
		cardManager.setCardDepositMoney(money.subtract(cash));

		hql = "select sum(case when consume_type = '消费' and consume_money > 0 then 1 when  consume_type = '反消费' then -1 else 0 end ) as num, sum(consume_money) as money "
				+ " from card_consume with(nolock) where system_book_code = '"
				+ systemBookCode
				+ "' "
				+ " and branch_num in "
				+ AppUtil.getIntegerParmeList(branchNums)
				+ " and shift_table_bizday between '"
				+ DateUtil.getDateShortStr(dateFrom) + "' and '" + DateUtil.getDateShortStr(dateTo) + "' ";
		query = currentSession().createSQLQuery(hql);
		objects = (Object[]) query.uniqueResult();
		money = objects[1] == null ? BigDecimal.ZERO : (BigDecimal) objects[1];
		cardManager.setCardConsumeCount((Integer) objects[0]);
		cardManager.setCardConsumeMoney(money);

		hql = "select count(detail.ticketSendDetailPrintNum) as num, sum(detail.ticketSendDetailValue) as money "
				+ " from TicketSendDetail as detail where detail.systemBookCode = :systemBookCode "
				+ " and detail.ticketSendDetailSendBranch in (:branchNums)  and detail.ticketSendDetailSendTime between :dateFrom and :dateTo";
		query = currentSession().createQuery(hql);
		query.setString("systemBookCode", systemBookCode);
		query.setParameterList("branchNums", branchNums);
		query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		query.setLockOptions(LockOptions.READ);
		objects = (Object[]) query.uniqueResult();
		count = objects[0] == null ? 0 : ((Long) objects[0]).intValue();
		money = objects[1] == null ? BigDecimal.ZERO : (BigDecimal) objects[1];
		cardManager.setCardTicketCount(count);
		cardManager.setCardTicketMoney(money);

		hql = "select count(detail.ticketSendDetailPrintNum) as num, sum(detail.ticketSendDetailValue) as money "
				+ " from TicketSendDetail as detail "
				+ " where detail.systemBookCode = :systemBookCode"
				+ " and detail.ticketSendDetailBranchNum in (:branchNums) "
				+ " and detail.ticketSendDetailTime between :dateFrom and :dateTo";
		query = currentSession().createQuery(hql);
		query.setString("systemBookCode", systemBookCode);
		query.setParameterList("branchNums", branchNums);
		query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		query.setLockOptions(LockOptions.READ);
		objects = (Object[]) query.uniqueResult();
		count = objects[0] == null ? 0 : ((Long) objects[0]).intValue();
		money = objects[1] == null ? BigDecimal.ZERO : (BigDecimal) objects[1];
		cardManager.setCardTicketUseCount(count);
		cardManager.setCardTicketUseMoney(money);

		hql = "select client_point_operate_type, sum(client_point_balance) "
				+ " from client_point with(nolock) where branch_num in " + AppUtil.getIntegerParmeList(branchNums)
				+ " and system_book_code = '" + systemBookCode + "' " + " and client_point_date between '"
				+ dateTimeFrom + "' and '" + dateTimeTo + "' "
				+ " group by client_point_operate_type ";
		query = currentSession().createSQLQuery(hql);
		List<Object[]> objs = query.list();
		for (int i = 0; i < objs.size(); i++) {
			objects = objs.get(i);
			String operateType = (String) objects[0];
			BigDecimal point = objects[1] == null ? BigDecimal.ZERO : (BigDecimal) objects[1];
			if (operateType == null) {
				continue;
			}
			if (operateType.equals(AppConstants.C_CLIENT_POINT_CHANGE)
					|| operateType.equals(AppConstants.C_CLIENT_POINT_TO_DEPOSIT)) {
				cardManager.setCardPointConsume(cardManager.getCardPointConsume().add(point.negate()));
			} else {
				cardManager.setCardPointAdded(cardManager.getCardPointAdded().add(point));
			}

		}
		
		if(AppUtil.getBQBookCodes().contains(systemBookCode)){
			StringBuffer sb = new StringBuffer();
			sb.append("select count(card_user_log_fid) from card_user_log with(nolock) ");
			sb.append("where system_book_code = '" + systemBookCode + "' ");
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
			sb.append("and card_user_log_time between '" + dateTimeFrom + "' and '" + dateTimeTo + "' ");
			sb.append("and card_user_log_type = '" + AppConstants.CARD_USER_LOG_TYPE_CHANGE_STORGE + "' ");
			sb.append("and card_user_log_memo = '修改卡介质为[电子卡]'");
			query = currentSession().createSQLQuery(sb.toString());
			object = (Object) query.uniqueResult();
			cardManager.setCardChangeCount(object == null?0:(Integer) object);
			
		}
		return cardManager;
	}

	@Override
	public List<MobileCardManage> findShopCardManagers(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo) {

		dateFrom = DateUtil.getMinOfDate(dateFrom);
		dateTo = DateUtil.getMaxOfDate(dateTo);
		
		String dateTimeFrom =  DateUtil.getDateTimeString(dateFrom);
		String dateTimeTo =  DateUtil.getDateTimeString(dateTo);
		
		Map<Integer, MobileCardManage> map = new LinkedHashMap<Integer, MobileCardManage>();
		String hql = "select card_user_enroll_shop, count(card_user_num) as num  "
				+ " from card_user with(nolock) where card_user_enroll_shop in "
				+ AppUtil.getIntegerParmeList(branchNums) + " and system_book_code = '" + systemBookCode + "' "
				+ " and card_user_date between '" + dateTimeFrom + "' and '"
				+ dateTimeTo + "' " + " group by card_user_enroll_shop";
		Query query = currentSession().createSQLQuery(hql);
		List<Object[]> objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			MobileCardManage cardManager = new MobileCardManage();
			Integer branchNum = (Integer) object[0];
			Integer count = (Integer) object[1];
			cardManager.setShopNum(branchNum);
			cardManager.setCardAddedCount(count);
			map.put(branchNum, cardManager);
		}

		hql = "select branch_num, count(deposit_fid) as num , sum(deposit_cash) as cash, sum(deposit_money) as money "
				+ " from card_deposit with(nolock) where system_book_code = '" + systemBookCode + "'  "
				+ " and branch_num in " + AppUtil.getIntegerParmeList(branchNums) + " and shift_table_bizday between '"
				+ DateUtil.getDateShortStr(dateFrom) + "' and '" + DateUtil.getDateShortStr(dateTo) + "' "
				+ " group by branch_num ";
		query = currentSession().createSQLQuery(hql);
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			int count = object[1] == null ? 0 : (Integer) object[1];
			BigDecimal cash = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BigDecimal money = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			MobileCardManage cardManager = map.get(branchNum);
			if (cardManager == null) {
				cardManager = new MobileCardManage();
				cardManager.setShopNum(branchNum);
				map.put(branchNum, cardManager);
			}
			cardManager.setCardDepositCount(count);
			cardManager.setCardDepositCash(cash);
			cardManager.setCardDepositMoney(money.subtract(cash));
		}

		hql = "select branch_num, sum(case when consume_type = '消费' and consume_money > 0 then 1 when  consume_type = '反消费' then -1 else 0 end ) as num, sum(consume_money) as money "
				+ " from card_consume with(nolock) where system_book_code = '"
				+ systemBookCode
				+ "'  "
				+ " and branch_num in "
				+ AppUtil.getIntegerParmeList(branchNums)
				+ " and shift_table_bizday between '"
				+ DateUtil.getDateShortStr(dateFrom)
				+ "' and '"
				+ DateUtil.getDateShortStr(dateTo)
				+ "' "
				+ " group by branch_num ";
		query = currentSession().createSQLQuery(hql);
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			int count = object[1] == null ? 0 : (Integer) object[1];
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			MobileCardManage cardManager = map.get(branchNum);
			if (cardManager == null) {
				cardManager = new MobileCardManage();
				cardManager.setShopNum(branchNum);
				map.put(branchNum, cardManager);
			}
			cardManager.setCardConsumeCount(count);
			cardManager.setCardConsumeMoney(money);
		}

		hql = "select detail.ticketSendDetailSendBranch, count(detail.ticketSendDetailPrintNum) as num, sum(detail.ticketSendDetailValue) as money "
				+ " from TicketSendDetail as detail where detail.systemBookCode = :systemBookCode "
				+ " and detail.ticketSendDetailSendBranch in (:branchNums) "
				+ " and detail.ticketSendDetailSendTime between :dateFrom and :dateTo "
				+ " group by detail.ticketSendDetailSendBranch ";
		query = currentSession().createQuery(hql);
		query.setString("systemBookCode", systemBookCode);
		query.setParameterList("branchNums", branchNums, StandardBasicTypes.INTEGER);
		query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		query.setLockOptions(LockOptions.READ);
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			int count = object[1] == null ? 0 : ((Long) object[1]).intValue();
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			MobileCardManage cardManager = map.get(branchNum);
			if (cardManager == null) {
				cardManager = new MobileCardManage();
				cardManager.setShopNum(branchNum);
				map.put(branchNum, cardManager);
			}
			cardManager.setCardTicketCount(count);
			cardManager.setCardTicketMoney(money);
		}

		hql = "select detail.ticketSendDetailBranchNum, count(detail.ticketSendDetailPrintNum) as num, sum(detail.ticketSendDetailValue) as money "
				+ " from TicketSendDetail as detail where detail.systemBookCode = :systemBookCode "
				+ " and detail.ticketSendDetailBranchNum in (:branchNums) "
				+ " and detail.ticketSendDetailTime between :dateFrom and :dateTo"
				+ " group by detail.ticketSendDetailBranchNum";
		query = currentSession().createQuery(hql);
		query.setString("systemBookCode", systemBookCode);
		query.setParameterList("branchNums", branchNums, StandardBasicTypes.INTEGER);
		query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		query.setLockOptions(LockOptions.READ);
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			int count = object[1] == null ? 0 : ((Long) object[1]).intValue();
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			MobileCardManage cardManager = map.get(branchNum);
			if (cardManager == null) {
				cardManager = new MobileCardManage();
				cardManager.setShopNum(branchNum);
				map.put(branchNum, cardManager);
			}
			cardManager.setCardTicketUseCount(count);
			cardManager.setCardTicketUseMoney(money);
		}

		hql = "select branch_num, client_point_operate_type, sum(client_point_balance) "
				+ " from client_point with(nolock) where branch_num in " + AppUtil.getIntegerParmeList(branchNums)
				+ " and system_book_code = '" + systemBookCode + "' " + " and client_point_date between '"
				+ dateTimeFrom + "' and '" + dateTimeTo + "' "
				+ " group by branch_num, client_point_operate_type ";
		query = currentSession().createSQLQuery(hql);
		objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String operateType = (String) object[1];
			BigDecimal point = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			if (operateType == null) {
				continue;
			}
			MobileCardManage cardManager = map.get(branchNum);
			if (cardManager == null) {
				cardManager = new MobileCardManage();
				cardManager.setShopNum(branchNum);
				map.put(branchNum, cardManager);
			}
			if (operateType.equals(AppConstants.C_CLIENT_POINT_CHANGE)
					|| operateType.equals(AppConstants.C_CLIENT_POINT_TO_DEPOSIT)) {
				cardManager.setCardPointConsume(cardManager.getCardPointConsume().add(point.negate()));
			} else {
				cardManager.setCardPointAdded(cardManager.getCardPointAdded().add(point));
			}

		}
		
		
		if(AppUtil.getBQBookCodes().contains(systemBookCode)){
			StringBuffer sb = new StringBuffer();
			sb.append("select branch_num, count(card_user_log_fid) from card_user_log with(nolock) ");
			sb.append("where system_book_code = '" + systemBookCode + "' ");
			sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
			sb.append("and card_user_log_time between '" + dateTimeFrom + "' and '" + dateTimeTo + "' ");
			sb.append("and card_user_log_type = '" + AppConstants.CARD_USER_LOG_TYPE_CHANGE_STORGE + "' ");
			sb.append("and card_user_log_memo = '修改卡介质为[电子卡]' ");
			sb.append("group by branch_num ");
			query = currentSession().createSQLQuery(sb.toString());
			objects = query.list();
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer branchNum = (Integer) object[0];
				int count = object[1] == null ? 0 : ((Integer) object[1]);
				MobileCardManage cardManager = map.get(branchNum);
				if (cardManager == null) {
					cardManager = new MobileCardManage();
					cardManager.setShopNum(branchNum);
					map.put(branchNum, cardManager);
				}
				cardManager.setCardChangeCount(count);
			}
			
		}		
		return new ArrayList<MobileCardManage>(map.values());
	}

	@Override
	public List<Object[]> findShopTimeAnalysis(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select datepart(hh,order_time), sum(order_payment_money + order_coupon_total_money - order_mgr_discount_money) as money, count(order_no) as amount ");
		sb.append("from pos_order with(nolock) where system_book_code = '" + systemBookCode + "' ");
		sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums) + " ");
		sb.append("and shift_table_bizday between '" + DateUtil.getDateShortStr(dateFrom) + "' and '"
				+ DateUtil.getDateShortStr(dateTo) + "' and order_state_code in (5, 7) ");
		sb.append("group by datepart(hh,order_time) order by datepart(hh,order_time)");
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}

	@Override
	public List<Object[]> findShopGroupReportsByTime(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo, String timeType) {
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num, sum(order_payment_money + order_coupon_total_money - order_mgr_discount_money) as money, count(order_no) as amount ");
		sb.append("from pos_order with(nolock) where system_book_code = '" + systemBookCode + "' ");
		sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums) + " ");
		sb.append("and shift_table_bizday between '" + DateUtil.getDateShortStr(dateFrom) + "' and '"
				+ DateUtil.getDateShortStr(dateTo) + "' and order_state_code in (5, 7) and datepart(hh,order_time) = "
				+ timeType + " ");
		sb.append("group by branch_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}

	@Override
	public List<Object[]> findShopGroupReportsByTerminal(String systemBookCode, List<Integer> branchNums,
			Date dateFrom, Date dateTo, String timeType) {
		StringBuffer sb = new StringBuffer();
		sb.append("select order_machine, sum(order_payment_money + order_coupon_total_money - order_mgr_discount_money) as money, count(order_no) as amount ");
		sb.append("from pos_order with(nolock) where system_book_code = '" + systemBookCode + "' ");
		sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums) + " ");
		sb.append("and shift_table_bizday between '" + DateUtil.getDateShortStr(dateFrom) + "' and '"
				+ DateUtil.getDateShortStr(dateTo) + "' and order_state_code in (5, 7) and datepart(hh,order_time) = "
				+ timeType + " ");
		sb.append("group by order_machine ");
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}

	@Override
	public List<Object[]> findTerminalTimeAnalysis(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select order_machine, datepart(hh,order_time), sum(order_payment_money + order_coupon_total_money - order_mgr_discount_money) as money, count(order_no) as amount ");
		sb.append("from pos_order with(nolock) where system_book_code = '" + systemBookCode + "' ");
		sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums) + " ");
		sb.append("and shift_table_bizday between '" + DateUtil.getDateShortStr(dateFrom) + "' and '"
				+ DateUtil.getDateShortStr(dateTo) + "' and order_state_code in (5, 7) ");
		sb.append("group by order_machine, datepart(hh,order_time) ");
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}

	@Override
	public List<SalesDiscount> findItemDiscount(String systemBookCode, List<Integer> branchNums, Date dateFrom,
	                                            Date dateTo, Integer rankFrom, Integer rankTo, String sortType) {
		StringBuffer queryStr = new StringBuffer();
		queryStr.append(" select p.item_name, p.item_code, ");
		queryStr.append(" sum(case when t.order_detail_state_code = 2 then order_detail_amount end) as presentAmount, ");
		queryStr.append(" sum(case when t.order_detail_state_code = 2 then order_detail_payment_money end) as presentMoney, ");
		queryStr.append(" sum(case when t.order_detail_state_code = 4 then order_detail_amount end) as returnAmount, ");
		queryStr.append(" sum(case when t.order_detail_state_code = 4 then order_detail_payment_money end) as returnMoney, ");
		queryStr.append(" sum(case when (t.order_detail_state_code = 1 and t.order_detail_discount != 0) then order_detail_amount when (t.order_detail_state_code = 4 and t.order_detail_discount != 0) then -order_detail_amount end) as discountAmount, ");
		queryStr.append(" sum(case when t.order_detail_state_code = 1 then t.order_detail_discount when t.order_detail_state_code = 4 then -order_detail_discount end) as discountMoney ");
		queryStr.append(" from pos_order_detail as t with(nolock), pos_item as p with(nolock) ");
		queryStr.append(" where t.order_detail_book_code = '" + systemBookCode + "' ");
		if (branchNums != null && branchNums.size() > 0) {
			queryStr.append(" and t.order_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));

		}
		queryStr.append(" and t.order_detail_bizday between '" + DateUtil.getDateShortStr(dateFrom) + "' and '"
				+ DateUtil.getDateShortStr(dateTo) + "'");
		queryStr.append(" and t.order_detail_order_state in (5, 7)");
		queryStr.append(" and p.item_num = t.item_num");
		queryStr.append(" and t.order_detail_state_code != 8 ");

		queryStr.append(" group by p.item_name, p.item_code ");
		queryStr.append(" having sum(case when t.order_detail_state_code = 2 then order_detail_amount end) != 0 ");
		queryStr.append(" or sum(case when t.order_detail_state_code = 4 then order_detail_amount end) != 0 ");
		queryStr.append(" or sum(case when t.order_detail_discount != 0 then order_detail_amount end) != 0 ");
		if (sortType == null) {
			sortType = "赠";
		}
		if (sortType.equals("赠")) {
			queryStr.append(" order by presentMoney desc, returnMoney desc, discountMoney desc");
		} else if (sortType.equals("退")) {
			queryStr.append(" order by returnMoney desc, presentMoney desc, discountMoney desc");
		} else {
			queryStr.append(" order by discountMoney desc, presentMoney desc, returnMoney desc ");
		}
		Query query = currentSession().createSQLQuery(queryStr.toString());
		if(rankFrom != null && rankTo != null){
			query.setFirstResult(rankFrom);
			query.setMaxResults(rankTo - rankFrom);
			
		}

		List<SalesDiscount> list = new ArrayList<SalesDiscount>();
		List<Object[]> objects = query.list();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			SalesDiscount salesDiscount = new SalesDiscount();
			salesDiscount.setItemName((String) object[0]);
			salesDiscount.setItemCode((String) object[1]);
			salesDiscount.setItemPresented(object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2]);
			salesDiscount.setItemPresentedMoney(object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3]);
			salesDiscount.setItemRemoved(object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4]);
			salesDiscount.setItemRemovedMoney(object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5]);
			salesDiscount.setItemDiscount(object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6]);
			salesDiscount.setItemDiscountMoney(object[7] == null ? BigDecimal.ZERO : (BigDecimal) object[7]);
			list.add(salesDiscount);
		}
		return list;
	}

	@Override
	public List<Object[]> findCouponTypeSummaryByDate(String systemBookCode, Integer branchNum, Date dateFrom,
			Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.order_detail_item, sum(detail.order_detail_amount) as amount, sum(detail.order_detail_payment_money) as money ");
		sb.append("from pos_order_detail as detail with(nolock) ");
		sb.append("where detail.order_detail_book_code = '" + systemBookCode + "' ");
		sb.append("and detail.order_detail_branch_num = " + branchNum + " ");

		if (dateFrom != null) {
			sb.append("and detail.order_detail_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if (dateTo != null) {
			sb.append("and detail.order_detail_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		sb.append("and detail.order_detail_order_state in (5, 7) and detail.item_num is null ");
		sb.append("and detail.order_detail_state_code = 1 ");
		sb.append("group by detail.order_detail_item order by detail.order_detail_item asc ");
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}

	@Override
	public BigDecimal getCouponMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(detail.order_detail_payment_money) as money ");
		sb.append("from pos_order_detail as detail with(nolock) ");
		sb.append("where detail.order_detail_book_code = '" + systemBookCode + "' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and detail.order_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}

		if (dateFrom != null) {
			sb.append("and detail.order_detail_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if (dateTo != null) {
			sb.append("and detail.order_detail_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		sb.append("and detail.order_detail_order_state in (5, 7) and detail.item_num is null ");
		sb.append("and detail.order_detail_state_code = 1 ");
		Query query = currentSession().createSQLQuery(sb.toString());
		Object object = query.uniqueResult();
		return object == null ? BigDecimal.ZERO : (BigDecimal) object;
	}

	@Override
	public List<Object[]> findShopCouponMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.order_detail_branch_num, sum(detail.order_detail_payment_money) as money ");
		sb.append("from pos_order_detail as detail with(nolock) ");
		sb.append("where detail.order_detail_book_code = '" + systemBookCode + "' ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and detail.order_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}

		if (dateFrom != null) {
			sb.append("and detail.order_detail_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
		}
		if (dateTo != null) {
			sb.append("and detail.order_detail_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
		}
		sb.append("and detail.order_detail_order_state in (5, 7) and detail.item_num is null ");
		sb.append("and detail.order_detail_state_code = 1 ");
		sb.append("group by detail.order_detail_branch_num");
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}

	@Override
	public MobileBusinessDTO findMobileAppBusinessDTO(String systemBookCode, List<Integer> branchNums, Date dateFrom,
	                                                  Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(order_payment_money + order_coupon_total_money - order_mgr_discount_money) as paymentMoney, count(order_no) as orderNums, ");
		sb.append("sum(case when order_card_user_num > 0 then (order_payment_money - order_mgr_discount_money + order_coupon_total_money) end) as cardMoney, ");
		sb.append("count(case when order_card_user_num > 0 then order_no end) as cardAmount, ");
		sb.append("sum(order_discount_money + order_round + order_mgr_discount_money) as discount, ");
		sb.append("sum(order_gross_profit) as profit,");
		sb.append("count(distinct branch_num) as branchCount ");
		sb.append("from pos_order with(nolock) ");
		sb.append("where system_book_code = '" + systemBookCode + "' and branch_num in "
				+ AppUtil.getIntegerParmeList(branchNums) + " ");
		sb.append("and shift_table_bizday between '" + DateUtil.getDateShortStr(dateFrom) + "' and '"
				+ DateUtil.getDateShortStr(dateTo) + "' ");
		sb.append("and order_state_code in (5, 7) ");

		MobileBusinessDTO dto = new MobileBusinessDTO();
		Query query = currentSession().createSQLQuery(sb.toString());
		Object[] objects = (Object[]) query.uniqueResult();
		dto.setBusinessMoney(objects[0] == null ? BigDecimal.ZERO : (BigDecimal) objects[0]);
		dto.setReceiptCount(objects[1] == null ? 0 : (Integer) objects[1]);
		dto.setBusinessCardMoney(objects[2] == null ? BigDecimal.ZERO : (BigDecimal) objects[2]);
		dto.setReceiptCardCount(objects[3] == null ? 0 : (Integer) objects[3]);
		dto.setDiscountMoney(objects[4] == null ? BigDecimal.ZERO : (BigDecimal) objects[4]);
		dto.setProfitMoney(objects[5] == null ? BigDecimal.ZERO : (BigDecimal) objects[5]);
		dto.setBranchCount(objects[6] == null ? 0: (Integer) objects[6]);
		return dto;
	}

	@Override
	public MobileCardDTO getMobileCardDTO(String systemBookCode, List<Integer> branchNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(c.card_user_num) as amount,sum(b.card_balance_money) as money ");
		sb.append("from card_user as c with(nolock) left join card_balance as b with(nolock) on b.card_user_num = c.card_user_num ");
		sb.append("where c.system_book_code = '" + systemBookCode + "' ");
		sb.append("and c.card_user_enroll_shop in " + AppUtil.getIntegerParmeList(branchNums));
		sb.append("and c.card_user_state_code in (3,7) ");
		sb.append("and (c.card_user_deadline >= :now or c.card_user_deadline is null or c.card_user_deadline = :initDate) ");

		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("initDate", AppConstants.INIT_TIME);
		query.setString("now", DateUtil.getDateTimeString(DateUtil.getMinOfDate(Calendar.getInstance().getTime())));
		Object[] object = (Object[]) query.uniqueResult();
		
		MobileCardDTO mobileCardDTO = new MobileCardDTO();
		mobileCardDTO.setValidCardCount(object[0] == null?0:(Integer)object[0]);
		mobileCardDTO.setCardBalanceMoney(object[1] == null?BigDecimal.ZERO:(BigDecimal)object[1]);
		return mobileCardDTO;
	}

	@Override
	public List<MobileSalesRank> findDeptRank(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer queryStr = new StringBuffer();

		queryStr.append(" select sum(case when t.order_detail_state_code = 4 then -t.order_detail_amount else order_detail_amount end) as amount,");
		queryStr.append(" sum(case when t.order_detail_state_code = 1 then t.order_detail_payment_money when t.order_detail_state_code = 4 then -t.order_detail_payment_money end) as money,");
		queryStr.append(" p.item_department from pos_order_detail as t with(nolock) inner join pos_item as p with(nolock) on p.item_num = t.item_num ");
		queryStr.append(" where t.order_detail_book_code = '" + systemBookCode + "'");
		queryStr.append(" and t.order_detail_branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		queryStr.append(" and t.order_detail_bizday between '" + DateUtil.getDateShortStr(dateFrom) + "' and '"
				+ DateUtil.getDateShortStr(dateTo) + "'");
		queryStr.append(" and t.order_detail_order_state in (5, 7)");
		queryStr.append(" and t.order_detail_state_code != 8 ");
		queryStr.append(" group by p.item_department");

		Query query = currentSession().createSQLQuery(queryStr.toString());
		List<Object[]> objects = query.list();
		List<MobileSalesRank> list = new ArrayList<MobileSalesRank>();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			BigDecimal amount = object[0] == null ? BigDecimal.ZERO : (BigDecimal) object[0];
			BigDecimal money = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			String deptName = (String) object[2];
			MobileSalesRank rank = new MobileSalesRank();
			rank.setSalesCount(amount);
			rank.setSelesMoney(money);
			rank.setSalesName(deptName);
			rank.setSalesCode(deptName);
			rank.setSalesId(deptName);
			rank.setSelesUnitCount(BigDecimal.ZERO);
			list.add(rank);
		}
		return list;

	}

	@Override
	public List<Object[]> findUnMemberCountByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select branch_num as branchNum, ");
		sb.append("count(order_no) as orderNo ");
		sb.append("from pos_order with(nolock) ");
		sb.append("where system_book_code = '" + systemBookCode + "' ");
		sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		sb.append("and shift_table_bizday between '" + DateUtil.getDateShortStr(dateFrom) + "' and '" + DateUtil.getDateShortStr(dateTo) + "' ");
		sb.append("and order_state_code in " + AppUtil.getIntegerParmeList(AppUtil.getNormalPosOrderState()));
		sb.append("and ORDER_CARD_USER_NUM = 0 ");
		sb.append("group by branch_num ");
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}


}
