package com.nhsoft.module.report.dao.impl;


import com.nhsoft.module.report.dao.BranchItemRecoredDao;
import com.nhsoft.module.report.util.AppUtil;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
public class BranchItemRecoredDaoImpl extends DaoImpl implements BranchItemRecoredDao {
	@Override
	public List<Object[]> findItemAuditDate(String systemBookCode, Integer branchNum, Integer storehouseNum,
											List<Integer> itemNums, List<String> branchItemRecoredTypes) {
		StringBuffer sb = new StringBuffer();
		sb.append("select item_num, max(branch_item_recored_in_date) from branch_item_recored with(nolock) ");
		sb.append("where system_book_code = '" + systemBookCode + "' and branch_num = " + branchNum + " ");
		if(storehouseNum != null){
			sb.append("and storehouse_num = " + storehouseNum + " ");

		}
		if(itemNums != null && itemNums.size() > 0){
			sb.append("and item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		if(branchItemRecoredTypes != null && branchItemRecoredTypes.size() > 0){
			sb.append("and branch_item_recored_type in " + AppUtil.getStringParmeList(branchItemRecoredTypes));
		}
		sb.append("group by item_num ");
		Query query = currentSession().createSQLQuery(sb.toString());
		return query.list();
	}
}
