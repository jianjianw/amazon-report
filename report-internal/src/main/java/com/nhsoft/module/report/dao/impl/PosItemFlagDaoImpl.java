package com.nhsoft.module.report.dao.impl;

import com.nhsoft.module.report.dao.PosItemFlagDao;
import com.nhsoft.module.report.model.ItemFlagDetail;
import com.nhsoft.module.report.model.PosItemFlag;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PosItemFlagDaoImpl extends DaoImpl implements PosItemFlagDao {


    @Override
    public PosItemFlag read(int itemFlagNum) {
        return currentSession().get(PosItemFlag.class, itemFlagNum);
    }


    @Override
    public PosItemFlag readByName(String systemBookCode, String itemFlagName) {
        Criteria criteria = currentSession().createCriteria(PosItemFlag.class, "f")
                .add(Restrictions.eq("f.systemBookCode", systemBookCode))
                .add(Restrictions.eq("f.itemFlagName", itemFlagName));
        criteria.setMaxResults(1);
        return (PosItemFlag) criteria.uniqueResult();
    }


    @Override
    public List<PosItemFlag> find(String systemBookCode, String itemFlagType) {
        String sql = "select * from pos_item_flag with(nolock) where system_book_code = '" + systemBookCode + "' ";
        if (StringUtils.isNotEmpty(itemFlagType)) {
            sql = sql + "and item_flag_type = '" + itemFlagType + "' ";
        }
        SQLQuery query = currentSession().createSQLQuery(sql);
        query.addEntity(PosItemFlag.class);
        return query.list();
    }

    private void deleteDetails(int itemFlagNum) {

        String sql = "delete from item_flag_detail where item_flag_num = " + itemFlagNum;
        Query query = currentSession().createSQLQuery(sql);
        query.executeUpdate();
    }


    @Override
    public List<ItemFlagDetail> findDetails(int itemFlagNum) {
        Criteria criteria = currentSession().createCriteria(ItemFlagDetail.class, "detail")
                .add(Restrictions.eq("detail.id.itemFlagNum", itemFlagNum));
        return criteria.list();
    }

    @Override
    public List<Integer> findItemNums(int itemFlagNum) {
        String sql = "select item_num from item_flag_detail where item_flag_num = " + itemFlagNum;
        Query query = currentSession().createSQLQuery(sql);
        return query.list();
    }


}
