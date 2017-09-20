package com.nhsoft.report.dao.impl;


import com.nhsoft.report.dao.PolicyPromotionQuantityDao;
import com.nhsoft.report.dto.PolicyPosItem;
import com.nhsoft.report.model.PolicyPromotionQuantity;
import com.nhsoft.report.shared.queryBuilder.PolicyPosItemQuery;
import com.nhsoft.report.util.AppConstants;
import com.nhsoft.report.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Repository
public class PolicyPromotionQuantityDaoImpl extends DaoImpl implements PolicyPromotionQuantityDao {
	@Override
	public int count(String systemBookCode, List<Integer> branchNums, Date dateFrom,
					 Date dateTo, String dateType, String promotionQuantityCategory) {
		Criteria criteria = createCriteria(systemBookCode, branchNums, dateFrom, dateTo, dateType, promotionQuantityCategory);
		criteria.setProjection(Projections.rowCount());
		return ((Long)criteria.uniqueResult()).intValue();
	}

	private Criteria createCriteria(String systemBookCode, List<Integer> branchNums,
									Date dateFrom, Date dateTo, String dateType, String promotionQuantityCategory) {
		dateFrom = DateUtil.getMinOfDate(dateFrom);
		dateTo = DateUtil.getMaxOfDate(dateTo);
		Criteria criteria = currentSession().createCriteria(PolicyPromotionQuantity.class, "p")
				.add(Restrictions.eq("p.systemBookCode", systemBookCode));
		if(branchNums != null && branchNums.size() > 0){
			criteria.add(Restrictions.in("p.branchNum", branchNums));

		}
		if(dateType.equals(AppConstants.STATE_INIT_TIME)){
			criteria.add(Restrictions.between("p.promotionQuantityCreateTime", dateFrom, dateTo));
		}else if(dateType.equals(AppConstants.STATE_AUDIT_TIME)){
			criteria.add(Restrictions.between("p.promotionQuantityAuditTime", dateFrom, dateTo));
		}else if(dateType.equals(AppConstants.POLICY_ORDER_TIME)){

			criteria.add(Restrictions.le("p.promotionQuantityDateFrom", dateTo))
					.add(Restrictions.ge("p.promotionQuantityDateTo", dateFrom));
		}
		if(StringUtils.isNotEmpty(promotionQuantityCategory)){
			criteria.add(Restrictions.eq("p.promotionQuantityCategory", promotionQuantityCategory));
		} else {
			criteria.add(Restrictions.isNull("p.promotionQuantityCategory"));
		}
		return criteria;
	}

	@Override
	public List<PolicyPosItem> findPolicyPosItems(PolicyPosItemQuery posItemQuery) {
		Date dateFrom = DateUtil.getMinOfDate(posItemQuery.getDtFrom());
		Date dateTo = DateUtil.getMaxOfDate(posItemQuery.getDtTo());
		Criteria criteria = currentSession().createCriteria(PolicyPromotionQuantity.class, "p")
				.createAlias("p.policyPromotionQuantityDetails", "detail")
				.add(Restrictions.eq("p.systemBookCode", posItemQuery.getSystemBookCode()))
				.add(Restrictions.eq("p.state.stateCode", AppConstants.STATE_INIT_AUDIT_CODE))
				.add(Restrictions.le("p.promotionQuantityDateFrom", dateTo))
				.add(Restrictions.ge("p.promotionQuantityDateTo", dateFrom));

		if(posItemQuery.getItemNums() != null && posItemQuery.getItemNums().size() > 0){
			criteria.add(Restrictions.in("detail.itemNum", posItemQuery.getItemNums()));
		}
		if(StringUtils.isNotEmpty(posItemQuery.getPolicyCategory())){

			criteria.add(Restrictions.eq("p.promotionQuantityCategory", posItemQuery.getPolicyCategory()));


		} else 	{
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.eq("p.promotionQuantityCategory", ""))
					.add(Restrictions.isNull("p.promotionQuantityCategory")));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("detail.itemNum"))
				.add(Projections.property("p.promotionQuantityAppliedBranch"))
				.add(Projections.property("p.promotionQuantityDateFrom"))
				.add(Projections.property("p.promotionQuantityDateTo"))
				.add(Projections.property("p.promotionQuantityTimeFrom"))
				.add(Projections.property("p.promotionQuantityTimeTo"))
				.add(Projections.property("p.promotionQuantityNo"))
				.add(Projections.property("p.promotionQuantityCreator"))
				.add(Projections.property("p.promotionQuantityAuditor"))
				.add(Projections.property("detail.promotionQuantityDetailMinAmount"))
				.add(Projections.property("detail.promotionQuantityDetailSpecialPrice"))
				.add(Projections.property("detail.promotionQuantityDetailStdPrice"))
				.add(Projections.property("p.promotionQuantityMonActived"))
				.add(Projections.property("p.promotionQuantityTuesActived"))
				.add(Projections.property("p.promotionQuantityWedActived"))
				.add(Projections.property("p.promotionQuantityThursActived"))
				.add(Projections.property("p.promotionQuantityFridayActived"))
				.add(Projections.property("p.promotionQuantitySatActived"))
				.add(Projections.property("p.promotionQuantitySunActived"))
				.add(Projections.property("p.promotionQuantityCategory"))
				.add(Projections.property("p.promotionQuantityCardOnly"))


		);
		List<PolicyPosItem> list = new ArrayList<PolicyPosItem>();
		List<Object[]> objects = criteria.list();
		String promotionQuantityCategory = null;
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

			policyPosItem.setBillLimmit((BigDecimal)object[9]);
			policyPosItem.setPolicyPrice((BigDecimal)object[10]);
			policyPosItem.setPolicyStdPrice((BigDecimal)object[11]);
			String value = "";
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
			if((Boolean)object[17]){
				value = value+"*";
			}else{
				value = value+" ";
			}
			if((Boolean)object[18]){
				value = value+"*";
			}else{
				value = value+" ";
			}
			policyPosItem.setEffectiveDate(value);

			if(StringUtils.isNotEmpty(posItemQuery.getPolicyCategory())){
				policyPosItem.setPolicyType(posItemQuery.getPolicyCategory());

			} else {

				promotionQuantityCategory = (String)object[19];
				if(StringUtils.isNotEmpty(promotionQuantityCategory)){
					policyPosItem.setPolicyType(promotionQuantityCategory);

				} else {
					policyPosItem.setPolicyType(AppConstants.POLICY_PROMOTION_QUANTITY);

				}

			}
			policyPosItem.setCardOnly(object[20] == null?false:(Boolean)object[20]);
			list.add(policyPosItem);
		}
		return list;
	}
}
