package com.nhsoft.report.dao.impl;

import com.nhsoft.report.dao.OnlineOrderDao;
import com.nhsoft.module.report.dto.OnlineOrderSaleAnalysisDTO;
import com.nhsoft.module.report.query.State;
import com.nhsoft.module.report.query.OnlineOrderQuery;
import com.nhsoft.report.util.AppConstants;
import com.nhsoft.report.util.AppUtil;
import com.nhsoft.report.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/9/19.
 */
@Repository
public class OnlineOrderDaoImpl extends  DaoImpl implements OnlineOrderDao{
	@Override
	public Integer count(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(distinct o.onlineOrderFid) ");
		sb.append("from OnlineOrder as o inner join o.onlineOrderFlows as f ");
		sb.append("where o.systemBookCode = :systemBookCode ");
		if(branchNum != null){
			sb.append("and o.branchNum = :branchNum ");
		}
		sb.append("and (case when f.onlineOrderFlowItem = :item then f.onlineOrderFlowTime end) is not null ");
		sb.append("and (case when f.onlineOrderFlowItem = :item then f.onlineOrderFlowTime end) between :dateFrom and :dateTo ");
		Query query = currentSession().createQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setString("item", AppConstants.BANNER_DATE_TYPE_PAY);
		if(branchNum != null){
			query.setInteger("branchNum", branchNum);
		}
		query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		return ((Long)query.uniqueResult()).intValue();
	}
	
	private String createByOnlineOrderQuerySQL(OnlineOrderQuery onlineOrderQuery){
		StringBuffer sb = new StringBuffer();
		sb.append("from online_order_flow as flow with(nolock) inner join online_order as o with(nolock) on o.online_order_fid = flow.online_order_fid ");
		if(onlineOrderQuery.isQueryDetails()){
			sb.append("inner join online_order_detail as detail with(nolock) on detail.online_order_fid = o.online_order_fid ");
			
		}
		sb.append("where o.system_book_code = '" + onlineOrderQuery.getSystemBookCode() + "' ");
		if(onlineOrderQuery.getBranchNums() != null && onlineOrderQuery.getBranchNums().size() > 0){
			sb.append("and o.branch_num in " + AppUtil.getIntegerParmeList(onlineOrderQuery.getBranchNums()));
		}
		if(StringUtils.isNotEmpty(onlineOrderQuery.getEqOnlineOrderNo())){
			
			sb.append("and o.online_order_no = '" + onlineOrderQuery.getEqOnlineOrderNo() + "' ");
			return sb.toString();
			
		}
		if(StringUtils.isNotEmpty(onlineOrderQuery.getOnlineOrderFid())){
			
			sb.append("and o.online_order_fid = '" + onlineOrderQuery.getOnlineOrderFid() + "' ");
			return sb.toString();
			
		}
		if(StringUtils.isNotEmpty(onlineOrderQuery.getOnlineOrderNo())){
			sb.append("and o.online_order_no like '%" + onlineOrderQuery.getOnlineOrderNo() + "%' ");
			if(onlineOrderQuery.getOnlineOrderNo().length() >= 5){
				return sb.toString();
				
			}
			
		}
		if(StringUtils.isNotEmpty(onlineOrderQuery.getOnlineOrderClientNick())){
			sb.append("and o.online_order_client_nick like '% " + onlineOrderQuery.getOnlineOrderClientNick() + "%' ");
		}
		if(StringUtils.isNotEmpty(onlineOrderQuery.getReceiverName())){
			sb.append("and o.online_order_client_name = '" + onlineOrderQuery.getReceiverName() + "' ");
		}
		if(StringUtils.isNotEmpty(onlineOrderQuery.getReceiverPhone())){
			sb.append("and o.online_order_phone = '" + onlineOrderQuery.getReceiverPhone() + "' ");
		}
		if(StringUtils.isNotEmpty(onlineOrderQuery.getProvince())){
			sb.append("and o.online_order_state = '" + onlineOrderQuery.getProvince() + "' ");
		}
		if(StringUtils.isNotEmpty(onlineOrderQuery.getCity())){
			sb.append("and o.online_order_city = '" + onlineOrderQuery.getCity() + "' ");
		}
		if(StringUtils.isNotEmpty(onlineOrderQuery.getArea())){
			sb.append("and o.online_order_district = '" + onlineOrderQuery.getArea() + "' ");
		}
		if(onlineOrderQuery.getPaymentMoney() != null){
			if(StringUtils.isNotEmpty(onlineOrderQuery.getMathFalg())){
				if(onlineOrderQuery.getMathFalg().equals(">")){
					sb.append("and o.online_order_payment_money > " + onlineOrderQuery.getPaymentMoney() + " ");
					
				} else if(onlineOrderQuery.getMathFalg().equals(">=")){
					sb.append("and o.online_order_payment_money >= " + onlineOrderQuery.getPaymentMoney() + " ");
				} else if(onlineOrderQuery.getMathFalg().equals("=")){
					sb.append("and o.online_order_payment_money = " + onlineOrderQuery.getPaymentMoney() + " ");
				} else if(onlineOrderQuery.getMathFalg().equals("<=")){
					sb.append("and o.online_order_payment_money <= " + onlineOrderQuery.getPaymentMoney() + " ");
					
				} else if(onlineOrderQuery.getMathFalg().equals("<")){
					sb.append("and o.online_order_payment_money < " + onlineOrderQuery.getPaymentMoney() + " ");
					
				}
			} else {
				sb.append("and o.online_order_payment_money >= " + onlineOrderQuery.getPaymentMoney() + " ");
				
			}
		}
		if(onlineOrderQuery.getStates() != null && onlineOrderQuery.getStates().size() > 0){
			if(onlineOrderQuery.getStates().contains(AppConstants.ONLINE_ORDER_SETTLEMENT)){
				onlineOrderQuery.getStates().add(AppConstants.ONLINE_ORDER_CODPAY);
			}
			StringBuffer orBuffer = new StringBuffer();
			for(int i = 0;i < onlineOrderQuery.getStates().size();i++){
				State state = onlineOrderQuery.getStates().get(i);
				if(i != 0){
					orBuffer.append(" or ");
				}
				orBuffer.append("o.online_order_state_name like '%" + state.getStateName() + "' ");
			}
			sb.append("and (").append(orBuffer.toString()).append(") ");
			
		}
		if(onlineOrderQuery.getHandled() != null) {
			if(onlineOrderQuery.getHandled()) {
				sb.append("and o.online_order_state_code >= 7 ");
			} else {
				sb.append("and o.online_order_state_code = 3 ");
			}
		}
		if(StringUtils.isNotEmpty(onlineOrderQuery.getOpenId())){
			sb.append("and o.online_order_open_id = '" + onlineOrderQuery.getOpenId() + "' ");
		}
		if(onlineOrderQuery.getTeamOrder() != null){
			if(onlineOrderQuery.getTeamOrder()){
				sb.append("and o.online_order_type = " + AppConstants.ONLINE_ORDER_TYPE_TEAM + " ");
				
				
			} else {
				sb.append("and o.online_order_type = " + AppConstants.ONLINE_ORDER_TYPE_NORMAL + " ");
			}
		}
		if(StringUtils.isNotEmpty(onlineOrderQuery.getPayType())){
			if(onlineOrderQuery.getPayType().equals(AppConstants.PAYMENT_GIFTCARD)){
				sb.append("and o.online_order_payment_type = '"+AppConstants.PAYMENT_GIFTCARD+"' ");
			}
			else if(onlineOrderQuery.getPayType().equals(AppConstants.PAY_TYPE_WEIXINPAY)){
				sb.append("and o.online_order_payment_type = '"+AppConstants.PAY_TYPE_WEIXINPAY+"' ");
			}
		}
		if(StringUtils.isNotEmpty(onlineOrderQuery.getDeliveryType())){
			if(onlineOrderQuery.getDeliveryType().equals(AppConstants.KDT_SHIPPINT_TYPE_FETCH)){
				sb.append("and o.online_order_deliver = '"+AppConstants.KDT_SHIPPINT_TYPE_FETCH+"' ");
			}
			else if(onlineOrderQuery.getDeliveryType().equals(AppConstants.DELIVER_TYPE_TRANSFER)){
				sb.append("and (o.online_order_deliver is null or o.online_order_deliver != '"+AppConstants.KDT_SHIPPINT_TYPE_FETCH+"') ");
			}
		}
		if(onlineOrderQuery.getItemNums() != null && onlineOrderQuery.getItemNums().size() > 0){
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(onlineOrderQuery.getItemNums()));
		}
		if(onlineOrderQuery.getWeixinItemNums() != null && onlineOrderQuery.getWeixinItemNums().size() > 0){
			sb.append("and detail.weixin_item_num in " + AppUtil.getIntegerParmeList(onlineOrderQuery.getWeixinItemNums()));
		}
		if(StringUtils.isNotEmpty(onlineOrderQuery.getOnlineOrderSource())){
			sb.append("and o.online_order_source = '" + onlineOrderQuery.getOnlineOrderSource() + "' ");
		}
		if(StringUtils.isEmpty(onlineOrderQuery.getOpenId())){
			if(StringUtils.isNotEmpty(onlineOrderQuery.getDateType())) {
				sb.append("and flow.online_order_flow_item = '" + onlineOrderQuery.getDateType() + "' ");
				sb.append("and flow.online_order_flow_time between '" + DateUtil.getLongDateTimeStr(DateUtil.getMinOfDate(onlineOrderQuery.getDateFrom())) + "' ");
				sb.append("and '" + DateUtil.getLongDateTimeStr(DateUtil.getMaxOfDate(onlineOrderQuery.getDateTo())) + "' ");
			} else {
				sb.append("and o.online_order_last_edit_time between '" + DateUtil.getLongDateTimeStr(DateUtil.getMinOfDate(onlineOrderQuery.getDateFrom())) + "' ");
				sb.append("and '" + DateUtil.getLongDateTimeStr(DateUtil.getMaxOfDate(onlineOrderQuery.getDateTo())) + "' ");
			}
			
		}
		
		return sb.toString();
	}
	
	
	@Override
	public List<OnlineOrderSaleAnalysisDTO> findOnlineOrderSaleAnalysisByItem(OnlineOrderQuery onlineOrderQuery) {
		onlineOrderQuery.setQueryDetails(true);
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.item_num, sum(detail.online_order_detail_qty) as amount,");
		sb.append("sum(detail.online_order_detail_total_money - detail.online_order_detail_discount_money) as money ");
		sb.append(createByOnlineOrderQuerySQL(onlineOrderQuery));
		sb.append("and detail.item_num is not null and detail.online_order_detail_state_code = 1 ");
		sb.append("group by detail.item_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		List<OnlineOrderSaleAnalysisDTO> list = new ArrayList<OnlineOrderSaleAnalysisDTO>();
		List<Object[]> objects = query.list();
		Object[] object = null;
		for(int i = 0;i < objects.size();i++){
			object = objects.get(i);
			
			OnlineOrderSaleAnalysisDTO dto = new OnlineOrderSaleAnalysisDTO();
			dto.setItemNum((Integer) object[0]);
			dto.setSaleQty((BigDecimal) object[1]);
			dto.setSaleMoney((BigDecimal) object[2]);
			list.add(dto);
		}
		return list;
	}

	@Override
	public List<OnlineOrderSaleAnalysisDTO> findOnlineOrderSaleAnalysisByBranchItem(OnlineOrderQuery onlineOrderQuery) {
		onlineOrderQuery.setQueryDetails(true);
		StringBuffer sb = new StringBuffer();
		sb.append("select o.branch_num, detail.item_num, sum(detail.online_order_detail_qty) as amount,");
		sb.append("sum(detail.online_order_detail_total_money - detail.online_order_detail_discount_money) as money ");
		sb.append(createByOnlineOrderQuerySQL(onlineOrderQuery));
		sb.append("and detail.item_num is not null and detail.online_order_detail_state_code = 1 ");
		sb.append("group by o.branch_num, detail.item_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		List<OnlineOrderSaleAnalysisDTO> list = new ArrayList<OnlineOrderSaleAnalysisDTO>();
		List<Object[]> objects = query.list();
		Object[] object = null;
		for(int i = 0;i < objects.size();i++){
			object = objects.get(i);

			OnlineOrderSaleAnalysisDTO dto = new OnlineOrderSaleAnalysisDTO();
			dto.setBranchNum((Integer) object[0]);
			dto.setItemNum((Integer) object[1]);
			dto.setSaleQty((BigDecimal) object[2]);
			dto.setSaleMoney((BigDecimal) object[3]);
			list.add(dto);
		}
		return list;
	}
}
