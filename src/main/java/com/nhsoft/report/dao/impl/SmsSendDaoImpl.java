package com.nhsoft.report.dao.impl;


import com.nhsoft.report.dao.SmsSendDao;
import com.nhsoft.report.model.SmsSend;
import com.nhsoft.report.util.AppConstants;
import com.nhsoft.report.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;


@Repository
public class SmsSendDaoImpl extends DaoImpl implements SmsSendDao {
	@Override
	public int count(String systemBookCode, List<Integer> branchNums,
					 Date dateFrom, Date dateTo, String status, String phone, String delivery) {
		Criteria criteria = createCriteria(systemBookCode, branchNums, dateFrom, dateTo, status, phone, delivery);
		criteria.setProjection(Projections.rowCount());
		Object object = criteria.uniqueResult();
		return ((Long)object).intValue();
	}

	private Criteria createCriteria(String systemBookCode, List<Integer> branchNums,
									Date dateFrom, Date dateTo, String status, String phone, String delivery){
		Criteria criteria = currentSession().createCriteria(SmsSend.class, "s")
				.add(Restrictions.eq("s.systemBookCode", systemBookCode))
				.add(Restrictions.between("s.smsSendOperateTime", DateUtil.getMinOfDate(dateFrom), DateUtil.getMaxOfDate(dateTo)));

		if(branchNums != null && branchNums.size() > 0){
			criteria.add(Restrictions.in("s.branchNum", branchNums));
		}
		if (StringUtils.isNotEmpty(status)){
			if(status.equals(AppConstants.SMS_SEND_SUCCESS)){
				criteria.add(Restrictions.eq("s.smsSendState", 2));
			}else if(status.equals(AppConstants.SMS_SEND_FAIL)){
				criteria.add(Restrictions.eq("s.smsSendState", 3));
			}else if(status.equals(AppConstants.SMS_SEND_NULL)){
				criteria.add(Restrictions.eq("s.smsSendState", 1));
			}
		}
		if(StringUtils.isNotEmpty(phone)){
			criteria.add(Restrictions.like("s.smsSendNumber", phone, MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(delivery)){
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.like("s.smsSendDeliveryStatusCode", delivery, MatchMode.ANYWHERE))
					.add(Restrictions.like("s.smsSendDeliveryStatus", delivery, MatchMode.ANYWHERE)));
		}
		return criteria;
	}
}
