package com.nhsoft.module.azure.dao.impl;

import com.nhsoft.module.azure.dao.AzureDao;
import com.nhsoft.module.azure.model.*;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
public class AzureDaoImpl extends DaoImpl implements AzureDao {

    public void batchSaveItemDailies(String systemBookCode, List<ItemDaily> itemDailys) {
        //先删除
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        String date = formatDate(time);
        String sql = "delete from item_daily where shift_table_bizday = '" + date + "' ";
        SQLQuery sqlQuery = currentSession().createSQLQuery(sql);
        sqlQuery.executeUpdate();

        for (int i = 0; i <itemDailys.size() ; i++) {
            ItemDaily itemDaily = itemDailys.get(i);
            currentSession().save(itemDaily);
            if(i % 30 == 0){
                currentSession().flush();
            }
        }
    }

    public void batchSaveItemDailyDetails(String systemBookCode, List<ItemDailyDetail> itemDailyDetails) {
        //先删除
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        String date = formatDate(time);
        String sql = "delete from item_daily_detail where shift_table_bizday = '" + date + "' ";
        SQLQuery sqlQuery = currentSession().createSQLQuery(sql);
        sqlQuery.executeUpdate();
        currentSession().flush();
        //再插入
        for (int i = 0; i <itemDailyDetails.size() ; i++) {
            ItemDailyDetail itemDailyDetail = itemDailyDetails.get(i);
            currentSession().save(itemDailyDetail);
            if(i % 30 == 0){
                currentSession().flush();
            }
        }
        System.out.println("插入商品日时段销售汇总");

    }

    public void batchSavePosItemLats(String systemBookCode, List<PosItemLat> posItemLats) {
        for (int i = 0; i <posItemLats.size() ; i++) {
            PosItemLat posItemLat = posItemLats.get(i);
            currentSession().saveOrUpdate(posItemLat);
            if(i % 30 == 0){
                currentSession().flush();
                currentSession().clear();
            }
        }
    }

    public void batchSaveBranchLats(String systemBookCode, List<BranchLat> branchLats) {

    }

    public void batchSaveBranchs(String systemBookCode, List<Branch> branches) {
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


    public void batchSaveBranchDailies(List<BranchDaily> branchDailys) {

        //先删除
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        String date = formatDate(time);
        String sql = "delete from branch_daily where shift_table_bizday = '" + date + "' ";//branch_daily
        SQLQuery sqlQuery = currentSession().createSQLQuery(sql);
        sqlQuery.executeUpdate();


        for (int i = 0; i <branchDailys.size() ; i++) {
            BranchDaily branchDaily = branchDailys.get(i);
            currentSession().save(branchDaily);
            if(i % 30 == 0){
                currentSession().flush();
                currentSession().clear();
            }
        }
        System.out.println("插入分店日销售汇总表");

    }

    public void batchDeleteBranchDailies(String systemBookCode, Date dateFrom, Date dateTo) {
        String date = formatDate(dateFrom);
        String sql = "delete from branch_daily where shift_table_bizday < '" + date + "' ";
        SQLQuery sqlQuery = currentSession().createSQLQuery(sql);
        sqlQuery.executeUpdate();
    }

    public void batchDeleteItemDetailDailies(String systemBookCode, Date dateFrom, Date dateTo) {
        String date = formatDate(dateFrom);
        String sql = "delete from item_daily_detail where shift_table_bizday < '" + date + "' ";
        SQLQuery sqlQuery = currentSession().createSQLQuery(sql);
        sqlQuery.executeUpdate();
    }

    public List<Object[]> findPosItemNums(String systemBookCode) {
        String sql = "select item_num from pos_item_lat where system_book_code = '"+systemBookCode+"'";
        SQLQuery sqlQuery = currentSession().createSQLQuery(sql);
        return sqlQuery.list();
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
