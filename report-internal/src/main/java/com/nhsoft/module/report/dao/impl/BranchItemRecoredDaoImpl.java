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

	@Override
	public List<Object[]> findItemMinAuditDate(String systemBookCode, Integer branchNum, Integer storehouseNum, List<Integer> itemNums, List<String> branchItemRecoredTypes) {
		StringBuffer sb = new StringBuffer();
		sb.append("select item_num, min(branch_item_recored_in_date) from branch_item_recored with(nolock) ");
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

	@Override
	public List<Object[]> findItemReceiveDate(String systemBookCode, List<Integer> branchNums, Integer storehouseNum, List<Integer> itemNums, List<String> branchItemRecoredTypes) {
		StringBuffer sb = new StringBuffer();

		sb.append("select item_num, MAX (CONVERT(datetime, branch_item_recored_in_date,101)) from branch_item_recored with(nolock) ");
		sb.append("where system_book_code = '" + systemBookCode + "' ");

		if(branchNums != null && branchNums.size() > 0){
			sb.append("and branch_num in " +  AppUtil.getIntegerParmeList(branchNums));
		}
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

	@Override
	public List<Object[]> findItemPrice(String systemBookCode, Integer branchNum, List<Integer> itemNums, List<String> branchItemRecoredTypes) {
		StringBuilder sb = new StringBuilder();
		sb.append("select  a.item_num, (a.branch_item_recored_use_price/a.branch_item_recored_use_rate) from ");
		sb.append("(select *, row_number() over (partition by item_num order by branch_item_recored_in_date desc ) rn  ");
		sb.append("from branch_item_recored with(nolock) where ");
		sb.append("system_book_code = :systemBookCode and branch_num = :branchNum ");
		if(itemNums != null){
			sb.append("and item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		sb.append("and branch_item_recored_type in (:branchItemRecoredTypes) ");
		sb.append("and branch_item_recored_use_price is not null and branch_item_recored_use_rate > 0 ) as a ");
		sb.append("where rn = 1 ");

		Query query = currentSession().createSQLQuery(sb.toString());
		query.setString("systemBookCode", systemBookCode);
		query.setInteger("branchNum", branchNum);
		query.setParameterList("branchItemRecoredTypes", branchItemRecoredTypes);
		return query.list();
	}
}
