package com.nhsoft.module.azure.dao.impl;

import com.nhsoft.module.azure.dao.AzureDao;
import com.nhsoft.module.azure.model.*;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
public class AzureDaoImpl extends DaoImpl implements AzureDao {

    public void insertItemDaily(String systemBookCode, List<ItemDaily> itemDailys) {

        for (int i = 0; i <itemDailys.size() ; i++) {
            ItemDaily itemDaily = itemDailys.get(i);
            currentSession().saveOrUpdate(itemDaily);
            if(i % 30 == 0){
                currentSession().flush();
                currentSession().clear();
            }
        }
    }

    public void insertItemDailyDetail(String systemBookCode, List<ItemDailyDetail> itemDailyDetails) {

        for (int i = 0; i <itemDailyDetails.size() ; i++) {
            ItemDailyDetail itemDailyDetail = itemDailyDetails.get(i);
            currentSession().saveOrUpdate(itemDailyDetail);
            if(i % 30 == 0){
                currentSession().flush();
                currentSession().clear();
            }
        }
        System.out.println("插入商品日时段销售汇总");

    }

    public void insertPosItemLat(String systemBookCode, List<PosItemLat> posItemLats) {
        for (int i = 0; i <posItemLats.size() ; i++) {
            PosItemLat posItemLat = posItemLats.get(i);
            currentSession().saveOrUpdate(posItemLat);
            if(i % 30 == 0){
                currentSession().flush();
                currentSession().clear();
            }
        }
    }

    public void insertBranchLat(String systemBookCode, List<BranchLat> branchLats) {

    }

    public void insertBranch(String systemBookCode, List<Branch> branches) {
        for (int i = 0; i <branches.size() ; i++) {
            Branch branch = branches.get(i);
            currentSession().saveOrUpdate(branch);
            if(i % 30 == 0){
                currentSession().flush();
                currentSession().clear();
            }
        }
        System.out.println("插入分店");
    }


    public void insertBranchDaily(List<BranchDaily> branchDailys) {

        for (int i = 0; i <branchDailys.size() ; i++) {
            BranchDaily branchDaily = branchDailys.get(i);
            currentSession().saveOrUpdate(branchDaily);
            if(i % 30 == 0){
                currentSession().flush();
                currentSession().clear();
            }
        }
        System.out.println("插入分店日销售汇总表");

    }

    public void deleteBranchDaily(String systemBookCode, Date dateFrom, Date dateTo) {
        String date = formatDate(dateFrom);
        String sql = "delete from branch_daily where shift_table_bizday < '" + date + "' ";
        SQLQuery sqlQuery = currentSession().createSQLQuery(sql);
        sqlQuery.executeUpdate();
    }

    public void deleteItemDetailDaily(String systemBookCode, Date dateFrom, Date dateTo) {
        String date = formatDate(dateFrom);
        String sql = "delete from item_daily_detail where shift_table_bizday < '" + date + "' ";
        SQLQuery sqlQuery = currentSession().createSQLQuery(sql);
        sqlQuery.executeUpdate();
    }

    public String formatDate(Date date){
        if(date != null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String format = sdf.format(date);
            return format;
        }else{
            return null;
        }

    }
}
