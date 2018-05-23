package com.nhsoft.module.report.dao.impl;

import com.nhsoft.module.report.dao.MerchantContractDao;
import com.nhsoft.module.report.model.MerchantContract;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.report.utils.DateUtil;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
public class MerchantContractDaoImpl extends DaoImpl implements MerchantContractDao {


    @Override
    public List<MerchantContract> findByMerchantNum(String systemBookCode, Integer branchNum, List<Integer> merchantNums) {
        Date now = Calendar.getInstance().getTime();
        StringBuilder sb = new StringBuilder();
        sb.append("select * from merchant_contract as m with(nolock) where system_book_code = '" + systemBookCode + "' and branch_num = " + branchNum + " ");
        sb.append("and merchant_contract_state_code = 3 and merchant_contract_start <= '" + DateUtil.getLongDateTimeStr(DateUtil.getMaxOfDate(now)) + "' and merchant_contract_end >= '" + DateUtil.getLongDateTimeStr(DateUtil.getMinOfDate(now)) + "' ");
        if (merchantNums != null && merchantNums.size() > 0) {
            sb.append("and merchant_num in " + AppUtil.getIntegerParmeList(merchantNums));
        }
        SQLQuery query = currentSession().createSQLQuery(sb.toString());
        query.addEntity(MerchantContract.class);
        return query.list();
    }
}
