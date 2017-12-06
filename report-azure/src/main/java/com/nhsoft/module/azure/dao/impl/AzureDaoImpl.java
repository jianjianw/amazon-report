package com.nhsoft.module.azure.dao.impl;

import com.nhsoft.module.azure.dao.AzureDao;
import com.nhsoft.module.azure.model.*;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AzureDaoImpl extends DaoImpl implements AzureDao {



    /**
     *  system_book_code     dm_code              not null,
     branch_num           dm_num               not null,
     shift_table_bizday   dm_bizday            not null,
     item_num             dm_num               not null,
     shift_table_date     dm_date              null,
     item_money           dm_money             null,
     item_amount          dm_amount            null,
     *
     *
     * */

    public void insertItemDaily(String systemBookCode, List<ItemDaily> itemDailys) {
        for (int i = 0; i <itemDailys.size() ; i++) {
            ItemDaily itemDaily = itemDailys.get(i);
            String sql = "insert into item_daily values (?,?,?,?,?,?,?)";
            SQLQuery sqlQuery = currentSession().createSQLQuery(sql);
            sqlQuery.setString(0,itemDaily.getSystemBookCode());
            sqlQuery.setInteger(1, itemDaily.getBranchNum());
            sqlQuery.setString(2,itemDaily.getShiftTableBizday());
            sqlQuery.setInteger(3,itemDaily.getItemNum());
            sqlQuery.setDate(4,itemDaily.getShiftTableDate());
            sqlQuery.setBigDecimal(5,itemDaily.getItemMoney());
            sqlQuery.setBigDecimal(6,itemDaily.getItemAmount());
            sqlQuery.executeUpdate();
        }
        System.out.println("插入商品日销售汇总");

    }

    public void insertItemDailyDetail(String systemBookCode, List<ItemDailyDetail> itemDailyDetails) {

        for (int i = 0; i <itemDailyDetails.size() ; i++) {
            ItemDailyDetail itemDailyDetail = itemDailyDetails.get(i);
            String sql = "insert into item_daily_detail values (?,?,?,?,?,?,?,?,?)";
            SQLQuery sqlQuery = currentSession().createSQLQuery(sql);
            sqlQuery.setInteger(0,itemDailyDetail.getBranchNum());
            sqlQuery.setString(1,itemDailyDetail.getShiftTableBizday());
            sqlQuery.setInteger(2,itemDailyDetail.getItemNum());
            sqlQuery.setString(3,itemDailyDetail.getSystemBookCode());
            sqlQuery.setString(4,itemDailyDetail.getItemPeriod());
            sqlQuery.setDate(5,itemDailyDetail.getShiftTableDate());
            sqlQuery.setInteger(6,itemDailyDetail.getItemAmout());
            sqlQuery.setBigDecimal(7,itemDailyDetail.getItemMoney());
            sqlQuery.setString(8,itemDailyDetail.getItemSource());
            sqlQuery.executeUpdate();
        }

        System.out.println("插入商品日时段销售汇总");

    }

    public void insertPosItemLat(String systemBookCode, List<PosItemLat> posItemLats) {

    }

    public void insertBranchLat(String systemBookCode, List<BranchLat> branchLats) {

    }

    public void insertBranch(String systemBookCode, List<Branch> branches) {

    }


    public void insertBranchDaily(List<BranchDaily> branchDailys) {

        for (int i = 0; i <branchDailys.size() ; i++) {
            BranchDaily branchDaily = branchDailys.get(i);
            String sql = "insert into branch_daily values (?,?,?,?,?,?,?,?)";
            SQLQuery sqlQuery = currentSession().createSQLQuery(sql);
            sqlQuery.setString(0,branchDaily.getSystemBookCode());
            sqlQuery.setInteger(1, branchDaily.getBranchNum());
            sqlQuery.setString(2,branchDaily.getShiftTableBizday());
            sqlQuery.setDate(3,branchDaily.getShiftTableDate());
            sqlQuery.setBigDecimal(4,branchDaily.getDailyMoney());
            sqlQuery.setInteger(5,branchDaily.getDailyQty());
            sqlQuery.setBigDecimal(6,branchDaily.getDailyPrice());
            sqlQuery.setBigDecimal(7,branchDaily.getTargetMoney());
            sqlQuery.executeUpdate();

        }
        System.out.println("插入分店日销售汇总表");

    }
}
