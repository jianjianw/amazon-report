package com.nhsoft.report.dao.impl;



import com.nhsoft.report.dao.PolicyPresentDao;
import com.nhsoft.report.dto.PolicyPosItem;
import com.nhsoft.report.model.PolicyPresent;
import com.nhsoft.report.shared.queryBuilder.PolicyPosItemQuery;
import com.nhsoft.report.util.AppConstants;
import com.nhsoft.report.util.DateUtil;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Repository
public class PolicyPresentDaoImpl extends DaoImpl implements PolicyPresentDao {
	@Override
	public int count(String systemBookCode, List<Integer> branchNums, Date dateFrom,
					 Date dateTo, String dateType) {
		Criteria criteria = createCriteria(systemBookCode, branchNums, dateFrom, dateTo, dateType);
		criteria.setProjection(Projections.rowCount());
		return ((Long)criteria.uniqueResult()).intValue();
	}

	private Criteria createCriteria(String systemBookCode, List<Integer> branchNums,
									Date dateFrom, Date dateTo, String dateType) {
		dateFrom = DateUtil.getMinOfDate(dateFrom);
		dateTo = DateUtil.getMaxOfDate(dateTo);
		Criteria criteria = currentSession().createCriteria(PolicyPresent.class, "p")
				.add(Restrictions.eq("p.systemBookCode", systemBookCode));
		if(branchNums != null && branchNums.size() > 0){
			criteria.add(Restrictions.in("p.branchNum", branchNums));

		}
		if(dateType.equals(AppConstants.STATE_INIT_TIME)){
			criteria.add(Restrictions.between("p.policyPresentCreateTime", dateFrom, dateTo));
		}else if(dateType.equals(AppConstants.STATE_AUDIT_TIME)){
			criteria.add(Restrictions.between("p.policyPresentAuditTime", dateFrom, dateTo));
		}else if(dateType.equals(AppConstants.POLICY_ORDER_TIME)){

			dateFrom = DateUtil.getMinOfDate(dateFrom);
			dateTo = DateUtil.getMaxOfDate(dateTo);
			criteria.add(Restrictions.le("p.policyPresentDateFrom", dateTo))
					.add(Restrictions.ge("p.policyPresentDateTo", dateFrom));
		}
		return criteria;
	}

	@Override
	public List<PolicyPosItem> findPolicyPosItems(PolicyPosItemQuery posItemQuery) {
		Date dateFrom = DateUtil.getMinOfDate(posItemQuery.getDtFrom());
		Date dateTo = DateUtil.getMaxOfDate(posItemQuery.getDtTo());
		Criteria criteria = currentSession().createCriteria(PolicyPresent.class, "p")
				.add(Restrictions.eq("p.systemBookCode", posItemQuery.getSystemBookCode()))
				.add(Restrictions.eq("p.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.le("p.policyPresentDateFrom", dateTo))
				.add(Restrictions.ge("p.policyPresentDateTo", dateFrom));

		if(posItemQuery.getItemNums() != null && posItemQuery.getItemNums().size() > 0){
			criteria.add(Restrictions.in("p.itemNum", posItemQuery.getItemNums()));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("p.itemNum"))
				.add(Projections.property("p.policyPresentAppliedBranch"))
				.add(Projections.property("p.policyPresentDateFrom"))
				.add(Projections.property("p.policyPresentDateTo"))
				.add(Projections.property("p.policyPresentTimeFrom"))
				.add(Projections.property("p.policyPresentTimeTo"))
				.add(Projections.property("p.policyPresentNo"))
				.add(Projections.property("p.policyPresentCreator"))
				.add(Projections.property("p.policyPresentAuditor"))
				.add(Projections.property("p.policyPresentSaleAmount"))
				.add(Projections.property("p.policyPresentMonActived"))
				.add(Projections.property("p.policyPresentTuesActived"))
				.add(Projections.property("p.policyPresentWedActived"))
				.add(Projections.property("p.policyPresentThursActived"))
				.add(Projections.property("p.policyPresentFridayActived"))
				.add(Projections.property("p.policyPresentSatActived"))
				.add(Projections.property("p.policyPresentSunActived"))
				.add(Projections.property("p.policyPresentCardOnly"))
		);
		List<PolicyPosItem> list = new ArrayList<PolicyPosItem>();
		List<Object[]> objects = criteria.list();
		for(int i = 0;i < objects.size();i++){
			PolicyPosItem policyPosItem = new PolicyPosItem();
			Object[] object = objects.get(i);
			policyPosItem.setItemNum((Integer)object[0]);
			policyPosItem.setPolicyAppliedBranch((String)object[1]);
			policyPosItem.setDateFrom((Date)object[2]);
			policyPosItem.setDateTo((Date)object[3]);
			policyPosItem.setTimeFrom((Date)object[4]);
			policyPosItem.setTimeTo((Date)object[5]);
			policyPosItem.setPolicyNo((String)object[6]);
			policyPosItem.setPolicyCreator((String)object[7]);
			policyPosItem.setPolicyAuditor((String)object[8]);
			policyPosItem.setPolicyType(AppConstants.POLICY_PRENSENT);
			policyPosItem.setBillLimmit((BigDecimal)object[9]);
			String value = "";
			if((Boolean)object[10]){
				value = value+"*";
			}else{
				value = value+" ";
			}
			if((Boolean)object[11]){
				value = value+"*";
			}else{
				value = value+" ";
			}
			if((Boolean)object[12]){
				value = value+"*";
			}else{
				value = value+" ";
			}
			if((Boolean)object[13]){
				value = value+"*";
			}else{
				value = value+" ";
			}
			if((Boolean)object[14]){
				value = value+"*";
			}else{
				value = value+" ";
			}
			if((Boolean)object[15]){
				value = value+"*";
			}else{
				value = value+" ";
			}
			if((Boolean)object[16]){
				value = value+"*";
			}else{
				value = value+" ";
			}
			policyPosItem.setEffectiveDate(value);
			policyPosItem.setCardOnly(object[17] == null?false:(Boolean)object[17]);
			list.add(policyPosItem);
		}
		return list;
	}
}
