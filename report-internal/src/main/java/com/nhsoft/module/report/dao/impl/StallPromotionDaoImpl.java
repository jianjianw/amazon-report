package com.nhsoft.module.report.dao.impl;

import com.nhsoft.module.report.dao.StallPromotionDao;
import com.nhsoft.module.report.dto.StallPromotionCondition;
import com.nhsoft.module.report.model.StallPromotion;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.report.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StallPromotionDaoImpl extends DaoImpl implements StallPromotionDao {

    @Override
    public List<StallPromotion> find(StallPromotionCondition stallPromotionCondition) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from stall_promotion where system_book_code = :systemBookCode and branch_num = :branchNum ");
        if (StringUtils.isNotEmpty(stallPromotionCondition.getDateType())) {
            if (stallPromotionCondition.getDateType().equals(AppConstants.STATE_INIT_TIME)) {
                if(stallPromotionCondition.getDateStart() != null) {
                    sb.append("and policy_promotion_create_time >= :dateStart ");
                }
                if(stallPromotionCondition.getDateEnd() != null) {
                    sb.append("and policy_promotion_create_time <= :dateEnd ");
                }
            } else if (stallPromotionCondition.getDateType().equals(AppConstants.STATE_AUDIT_TIME)) {
                if(stallPromotionCondition.getDateStart() != null) {
                    sb.append("and policy_promotion_audit_time >= :dateStart ");
                }
                if(stallPromotionCondition.getDateEnd() != null) {
                    sb.append("and policy_promotion_audit_time <= :dateEnd ");
                }
            } else if (stallPromotionCondition.getDateType().equals(AppConstants.POLICY_ORDER_TIME)) {
                if(stallPromotionCondition.getDateEnd() != null) {
                    sb.append("and policy_promotion_date_from <= :dateEnd ");
                }
                if(stallPromotionCondition.getDateStart() != null) {
                    sb.append("and policy_promotion_date_to >= :dateStart ");
                }
            }
        }
        if (StringUtils.isNotEmpty(stallPromotionCondition.getStallPromotionNo())) {
            sb.append("and policy_promotion_no = :stallPromotionNo ");
        }
        if (stallPromotionCondition.getMerchantNum() != null) {
            sb.append("and merchant_num = :merchantNum ");
        }
        if (stallPromotionCondition.getStallNum() != null) {
            sb.append("and stall_num = :stallNum ");
        }
        if (stallPromotionCondition.getStates() != null && !stallPromotionCondition.getStates().isEmpty()) {
            sb.append("and policy_promotion_state_code in " + AppUtil.getIntegerParmeList(stallPromotionCondition.getStates()) + " ");
        }
        if (StringUtils.isNotEmpty(stallPromotionCondition.getSortField())) {
            sb.append("order by " + AppUtil.getDBColumnName(stallPromotionCondition.getSortField()) + " ");
            if (stallPromotionCondition.getSortType() != null && "DESC".equals(stallPromotionCondition.getSortType())) {
                sb.append("desc ");
            }
        }
        String sql = sb.toString();
        SQLQuery query = currentSession().createSQLQuery(sql);
        query.setString("systemBookCode", stallPromotionCondition.getSystemBookCode());
        query.setInteger("branchNum", stallPromotionCondition.getBranchNum());
        if (StringUtils.isNotEmpty(stallPromotionCondition.getDateType())) {
            if (stallPromotionCondition.getDateType().equals(AppConstants.STATE_INIT_TIME)) {
                if(stallPromotionCondition.getDateStart() != null) {
                    query.setParameter("dateStart", DateUtil.getMinOfDate(stallPromotionCondition.getDateStart()));
                }
                if(stallPromotionCondition.getDateEnd() != null) {
                    query.setParameter("dateEnd", DateUtil.getMaxOfDate(stallPromotionCondition.getDateEnd()));
                }
            } else if (stallPromotionCondition.getDateType().equals(AppConstants.STATE_AUDIT_TIME)) {
                if(stallPromotionCondition.getDateStart() != null) {
                    query.setParameter("dateStart", DateUtil.getMinOfDate(stallPromotionCondition.getDateStart()));
                }
                if(stallPromotionCondition.getDateEnd() != null) {
                    query.setParameter("dateEnd", DateUtil.getMaxOfDate(stallPromotionCondition.getDateEnd()));
                }
            } else if (stallPromotionCondition.getDateType().equals(AppConstants.POLICY_ORDER_TIME)) {
                if(stallPromotionCondition.getDateStart() != null) {
                    query.setParameter("dateStart", DateUtil.getMinOfDate(stallPromotionCondition.getDateStart()));
                }
                if(stallPromotionCondition.getDateEnd() != null) {
                    query.setParameter("dateEnd", DateUtil.getMaxOfDate(stallPromotionCondition.getDateEnd()));
                }
            }
        }
        if (StringUtils.isNotEmpty(stallPromotionCondition.getStallPromotionNo())) {
            query.setString("stallPromotionNo", stallPromotionCondition.getStallPromotionNo());
        }
        if (stallPromotionCondition.getMerchantNum() != null) {
            query.setInteger("merchantNum", stallPromotionCondition.getMerchantNum());
        }
        if (stallPromotionCondition.getStallNum() != null) {
            query.setInteger("stallNum", stallPromotionCondition.getStallNum());
        }
        if (stallPromotionCondition.isPage()) {
            if (stallPromotionCondition.getLimit() != null && stallPromotionCondition.getOffset() != null) {
                query.setFirstResult(stallPromotionCondition.getLimit());
                query.setMaxResults(stallPromotionCondition.getOffset());
            }
        }
        query.addEntity(StallPromotion.class);
        return query.list();
    }
}
