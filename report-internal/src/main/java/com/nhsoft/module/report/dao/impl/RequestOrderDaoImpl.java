package com.nhsoft.module.report.dao.impl;


import com.nhsoft.module.report.dao.RequestOrderDao;
import com.nhsoft.module.report.dto.RequestOrderDTO;
import com.nhsoft.module.report.model.RequestOrder;
import com.nhsoft.module.report.model.RequestOrderDetail;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.report.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Repository
public class RequestOrderDaoImpl extends DaoImpl implements RequestOrderDao {
	

	@Override
	public int countByBranch(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(RequestOrder.class, "r")
				.add(Restrictions.eq("r.outSystemBookCode", systemBookCode));
		if (branchNum != null && !branchNum.equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)) {
			criteria.add(Restrictions.eq("r.branchNum", branchNum));
		}
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("r.requestOrderCreateTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("r.requestOrderCreateTime", DateUtil.getMaxOfDate(dateTo)));
		}
		criteria.setProjection(Projections.rowCount());
		return ((Long) criteria.uniqueResult()).intValue();
	}


	@Override
	public List<Object[]> findBranchNewItemSummary(String systemBookCode, Integer branchNum, List<Integer> branchNums, List<Integer> newItemNums, List<String> categoryCodes, Date dateFrom,
												   Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select r.branch_num, count(distinct d.item_num) as newItemCount ");
		sb.append("from request_order_detail as d with(nolock) inner join request_order as r with(nolock) on r.request_order_fid = d.request_order_fid ");
		sb.append("where r.out_system_book_code = :systemBookCode and r.out_branch_num = :branchNum ");
		if (branchNums != null && branchNums.size() > 0) {
			sb.append("and r.branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		}
		sb.append("and r.request_order_audit_time >= :dateFrom and r.request_order_audit_time <= :dateTo and r.request_order_state_code = 3 ");
		if (newItemNums != null && newItemNums.size() > 0) {
			sb.append("and d.item_num in " + AppUtil.getIntegerParmeList(newItemNums));
		}
		if (categoryCodes != null && categoryCodes.size() > 0) {
			sb.append("and exists (select 1 from pos_item as item where item.system_book_code = :systemBookCode and item.item_category_code in " + AppUtil.getStringParmeList(categoryCodes)
					+ " and item.item_num = d.item_num) ");
		}
		sb.append("group by r.branch_num");
		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setInteger("branchNum", branchNum);
		query.setParameter("dateFrom", DateUtil.getMinOfDate(dateFrom));
		query.setParameter("dateTo", DateUtil.getMaxOfDate(dateTo));
		List<Object[]> objects = query.list();
		return objects;
	}



	@Override
	public List<RequestOrderDetail> findDetails(String requestOrderFid) {
		Criteria criteria = currentSession().createCriteria(RequestOrderDetail.class, "d").add(Restrictions.eq("d.id.requestOrderFid", requestOrderFid));
		List<RequestOrderDetail> requestOrderDetails = criteria.list();
		return requestOrderDetails;
	}



	@Override
	public List<Object[]> findItemSummary(String systemBookCode, Integer centerBranchNum, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> itemNums) {
		StringBuffer sb = new StringBuffer();
		sb.append("select detail.item_num, sum(detail.request_order_detail_qty) as amount,  sum(detail.request_order_detail_use_qty) as useAmount, ");
		sb.append("sum(detail.request_order_detail_out_qty) as outAmount, ");
		sb.append(
				"sum(case when request_order_detail_out_use_qty is not null then request_order_detail_out_use_qty when request_order_detail_out_qty is null then 0.00 else (detail.request_order_detail_out_qty/detail.request_order_detail_use_rate) end) as outUseAmount ");
		sb.append("from request_order_detail as detail with(nolock) inner join request_order as r with(nolock) on r.request_order_fid = detail.request_order_fid ");
		sb.append("where r.out_system_book_code = '" + systemBookCode + "' and r.out_branch_num = " + centerBranchNum + " ");
		sb.append("and r.branch_num = " + branchNum);
		sb.append("and r.request_order_audit_time between '" + DateUtil.getLongDateTimeStr(DateUtil.getMinOfDate(dateFrom)) + "' ");
		sb.append("and '" + DateUtil.getLongDateTimeStr(DateUtil.getMaxOfDate(dateTo)) + "' ");
		sb.append("and r.request_order_state_code = " + AppConstants.STATE_INIT_AUDIT_CODE + " ");
		if (itemNums != null && itemNums.size() > 0) {
			sb.append("and detail.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		sb.append("group by detail.item_num");
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}
	
	@Override
	public BigDecimal readBranchUnOutMoney(String systemBookCode, Integer branchNum, Integer outBranchNum) {
		if(branchNum.equals(outBranchNum)){
			return BigDecimal.ZERO;
		}
		Criteria criteria = currentSession().createCriteria(RequestOrder.class, "r").add(Restrictions.eq("r.outSystemBookCode", systemBookCode)).add(Restrictions.eq("r.outBranchNum", outBranchNum))
				.add(Restrictions.eq("r.branchNum", branchNum)).add(Restrictions.ge("r.requestOrderDeadline", DateUtil.getMinOfDate(Calendar.getInstance().getTime())))
				.add(Restrictions.eq("r.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.sqlRestriction(" request_order_fid not in (select request_order_fid from request_order_transfer_out_order where request_order_fid is not null)"));
		
		criteria.setProjection(Projections.projectionList()
				.add(Projections.sum("r.requestOrderTotalMoney"))
				.add(Projections.sum("r.requestOrderPreMoney"))
		);
		Object[] objects = (Object[]) criteria.uniqueResult();
		if (objects != null) {
			return objects[0] == null? BigDecimal.ZERO:(BigDecimal)objects[0];
		}
		return BigDecimal.ZERO;
	}
	
	@Override
	public List<RequestOrderDTO> findDTOs(String systemBookCode, Integer centerBranchNum, Integer branchNum, Date dateFrom, Date dateTo) {
		Criteria criteria = currentSession().createCriteria(RequestOrder.class, "r")
				.add(Restrictions.eq("r.outSystemBookCode", systemBookCode));
		if (centerBranchNum != null) {
			criteria.add(Restrictions.eq("r.outBranchNum", centerBranchNum));
		}
		if (branchNum != null) {
			criteria.add(Restrictions.eq("r.branchNum", branchNum));
		}
		
		criteria.add(Restrictions.eq("r.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE));
		
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("r.requestOrderAuditTime", DateUtil.getMinOfDate(dateFrom)));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("r.requestOrderAuditTime", DateUtil.getMaxOfDate(dateTo)));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("r.requestOrderFid"))
				.add(Projections.property("r.requestOrderTotalMoney"))
				.add(Projections.property("r.requestOrderAuditTime"))
				.add(Projections.property("r.requestOrderOutDate"))
				.add(Projections.property("r.requestOrderPickDate"))
				.add(Projections.property("r.requestOrderSendDate"))
				.add(Projections.property("r.requestOrderInDate"))
				.add(Projections.property("r.requestOrderTransferState"))
				.add(Projections.property("r.requestOrderDeadline"))
		
		);
		List<Object[]> objects = criteria.list();
		List<RequestOrderDTO> list = new ArrayList<RequestOrderDTO>();
		
		String requestOrderTransferState = null;
		Date now = DateUtil.getMinOfDate(Calendar.getInstance().getTime());
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			
			requestOrderTransferState = (String) object[7];
			
			RequestOrderDTO dto = new RequestOrderDTO();
			dto.setRequestOrderFid((String) object[0]);
			dto.setRequestOrderTotalMoney((BigDecimal) object[1]);
			dto.setRequestOrderAuditTime((Date) object[2]);
			dto.setRequestOrderOutDate((Date) object[3]);
			dto.setRequestOrderPickDate((Date) object[4]);
			dto.setRequestOrderSendDate((Date) object[5]);
			dto.setRequestOrderInDate((Date) object[6]);
			
			if (StringUtils.isEmpty(requestOrderTransferState)) {
				if (object[6] != null) {
					dto.setRequestOrderState("已收货");
				} else if (object[5] != null) {
					dto.setRequestOrderState("已发车");
				} else if (object[4] != null) {
					dto.setRequestOrderState("已配货");
				} else if (object[3] != null) {
					dto.setRequestOrderState("已审核");
				} else {
					dto.setRequestOrderState("待审核");
				}
				
			} else {
				dto.setRequestOrderState(requestOrderTransferState);
				if(dto.getRequestOrderState().equals("待处理")){
					Date deadLine = (Date) object[8];
					if(deadLine.before(now)){
						dto.setRequestOrderState("已过期");
						
					}
				}
			}
			list.add(dto);
			
		}
		return list;
	}
	
	@Override
	public List<Object[]> findFidShipOrderDeliver(List<String> requestOrderFids) {
		StringBuffer sb = new StringBuffer();
		sb.append("select rt.request_order_fid, s.ship_order_deliver ");
		sb.append("from request_order_transfer_out_order as rt with(nolock) inner join ship_transfer_out as st with(nolock) on rt.out_order_fid = st.out_order_fid ");
		sb.append("inner join ship_order as s with(nolock) on s.ship_order_fid = st.ship_order_fid ");
		sb.append("where rt.request_order_fid in " + AppUtil.getStringParmeList(requestOrderFids));
		sb.append("order by rt.request_order_fid, s.ship_order_deliver ");
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}
}