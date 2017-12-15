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
        //这个现在没有用
        for (int i = 0; i <itemDailys.size() ; i++) {
            ItemDaily itemDaily = itemDailys.get(i);
            currentSession().save(itemDaily);
            if(i % 30 == 0){
                currentSession().flush();
            }
        }
    }

    public void batchSaveItemDailyDetails(String systemBookCode, List<ItemDailyDetail> itemDailyDetails,Date dateFrom,Date dateTo) {

       /* //先删除
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        String date = formatDate(time);
        String sql = "delete from item_daily_detail where shift_table_bizday = '" + date + "' ";
        SQLQuery sqlQuery = currentSession().createSQLQuery(sql);
        sqlQuery.executeUpdate();*/


        StringBuilder sb  =  new StringBuilder();
        sb.append("delete from item_daily_detail ");
        sb.append("where shift_table_bizday >= '" + formatDate(dateFrom) + "' ");
        sb.append("and shift_table_bizday <= '" + formatDate(dateTo) + "' ");
        SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
        sqlQuery.executeUpdate();

        //再插入
        for (int i = 0; i <itemDailyDetails.size() ; i++) {
            ItemDailyDetail itemDailyDetail = itemDailyDetails.get(i);
            currentSession().save(itemDailyDetail);
            if(i % 30 == 0){
                currentSession().flush();
                currentSession().clear();
            }
        }

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
    }


    public void batchSaveBranchDailies(String systemBookCode,List<BranchDaily> branchDailys,Date dateFrom,Date dateTo) {

      /*  //先删除
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        String date = formatDate(time);
        String sql = "delete from branch_daily where shift_table_bizday = '" + date + "' ";//branch_daily
        SQLQuery sqlQuery = currentSession().createSQLQuery(sql);
        sqlQuery.executeUpdate();*/

        StringBuilder sb  =  new StringBuilder();
        sb.append("delete from branch_daily ");
        sb.append("where shift_table_bizday >= '" + formatDate(dateFrom) + "' ");
        sb.append("and shift_table_bizday <= '" + formatDate(dateTo) + "' ");
        SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
        sqlQuery.executeUpdate();

        for (int i = 0; i <branchDailys.size() ; i++) {
            BranchDaily branchDaily = branchDailys.get(i);
            currentSession().save(branchDaily);
            if(i % 30 == 0){
                currentSession().flush();
                currentSession().clear();
            }
        }

    }

    public void batchDeleteBranchDailies(String systemBookCode, Date dateFrom, Date dateTo) {
       /* String date = formatDate(dateFrom);
        String sql = "delete from branch_daily where shift_table_bizday < '" + date + "' ";
        SQLQuery sqlQuery = currentSession().createSQLQuery(sql);
        sqlQuery.executeUpdate();*/

        StringBuilder sb  =  new StringBuilder();
        sb.append("delete from branch_daily ");
        sb.append("where shift_table_bizday < '" + formatDate(dateFrom) + "' ");
        SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
        sqlQuery.executeUpdate();

    }

    public void batchDeleteItemDetailDailies(String systemBookCode, Date dateFrom, Date dateTo) {
       /* String date = formatDate(dateFrom);
        String sql = "delete from item_daily_detail where shift_table_bizday < '" + date + "' ";
        SQLQuery sqlQuery = currentSession().createSQLQuery(sql);
        sqlQuery.executeUpdate();*/

        StringBuilder sb  =  new StringBuilder();
        sb.append("delete from item_daily_detail ");
        sb.append("where shift_table_bizday < '" + formatDate(dateFrom) + "' ");
        SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
        sqlQuery.executeUpdate();

    }

    public List<Object> findPosItemNums(String systemBookCode) {

        StringBuilder sb = new StringBuilder();
        sb.append("select item_num from pos_item_lat ");
        sb.append("where system_book_code = '"+ systemBookCode + "' ");
        SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
        return sqlQuery.list();

       /* String sql = "select item_num from pos_item_lat where system_book_code = '"+systemBookCode+"' ";
        SQLQuery sqlQuery = currentSession().createSQLQuery(sql);
        return sqlQuery.list();*/
    }

    public void batchSaveBranchDailyDirects(String systemBookCode, List<BranchDailyDirect> branchDailyDirects,Date dateFrom,Date dateTo) {

       /* //先删除
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        String date = formatDate(time);
        String sql = "delete from branch_daily_direct where shift_table_bizday = '" + date + "' ";
        SQLQuery sqlQuery = currentSession().createSQLQuery(sql);
        sqlQuery.executeUpdate();*/

        StringBuilder sb  =  new StringBuilder();
        sb.append("delete from branch_daily_direct ");
        sb.append("where shift_table_bizday >= '" + formatDate(dateFrom) + "' ");
        sb.append("and shift_table_bizday <= '" + formatDate(dateTo) + "' ");
        SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
        sqlQuery.executeUpdate();

        for (int i = 0; i <branchDailyDirects.size() ; i++) {
            BranchDailyDirect branchDailyDirect = branchDailyDirects.get(i);
            currentSession().save(branchDailyDirect);
            if(i % 30 == 0){
                currentSession().flush();
                currentSession().clear();
            }
        }

    }

    public void batchDeleteBranchDailyDirects(String systemBookCode, Date dateFrom, Date dateTo) {

       /* String date = formatDate(dateFrom);
        String sql = "delete from branch_daily_direct where shift_table_bizday < '" + date + "' ";
        SQLQuery sqlQuery = currentSession().createSQLQuery(sql);
        sqlQuery.executeUpdate();*/

        StringBuilder sb  =  new StringBuilder();
        sb.append("delete from branch_daily_direct ");
        sb.append("where shift_table_bizday < '" + formatDate(dateFrom) + "' ");
        SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
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
