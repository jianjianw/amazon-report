package com.nhsoft.module.azure.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 商品日时段销售汇总
 * */
@Entity
public class ItemDailyDetail implements Serializable {
    public ItemDailyDetail() {
        this.branchNum = 0;
        this.shiftTableBizday = "";
        this.itemNum = 0;
        this.systemBookCode = "";
        this.itemPeriod = "";
        this.itemAmout = BigDecimal.ZERO;
        this.itemMoney = BigDecimal.ZERO;
        this.itemSource = "";

        this.amount = new BigDecimal[48];
        this.money = new BigDecimal[48];
        this.period = new String[48];
        for (int i = 0; i < 48 ; i++) {
            amount[i] = BigDecimal.ZERO;
            money[i] = BigDecimal.ZERO;
        }

        period[0] = "0030" ;
        period[1] = "0100" ;
        period[2] = "0130" ;
        period[3] = "0200" ;
        period[4] = "0230" ;
        period[5] = "0300" ;
        period[6] = "0330" ;
        period[7] = "0400" ;
        period[8] = "0430" ;
        period[9] = "0500" ;
        period[10] = "0530" ;
        period[11] = "0600" ;
        period[12] = "0630" ;
        period[13] = "0700" ;
        period[14] = "0730" ;
        period[15] = "0800" ;
        period[16] = "0830" ;
        period[17] = "0900" ;
        period[18] = "0930" ;
        period[19] = "1000" ;
        period[20] = "1030" ;
        period[21] = "1100" ;
        period[22] = "1130" ;
        period[23] = "1200" ;
        period[24] = "1230" ;
        period[25] = "1300" ;
        period[26] = "1330" ;
        period[27] = "1400" ;
        period[28] = "1430" ;
        period[29] = "1500" ;
        period[30] = "1530" ;
        period[31] = "1600" ;
        period[32] = "1630" ;
        period[33] = "1700" ;
        period[34] = "1730" ;
        period[35] = "1800" ;
        period[36] = "1830" ;
        period[37] = "1900" ;
        period[38] = "1930" ;
        period[39] = "2000" ;
        period[40] = "2030" ;
        period[41] = "2100" ;
        period[42] = "2130" ;
        period[43] = "2200" ;
        period[44] = "2230" ;
        period[45] = "2300" ;
        period[46] = "2330" ;
        period[47] = "2400" ;


    }



    /*public ItemDailyDetail() {
        this.itemAmout = BigDecimal.ZERO;
        this.itemMoney = BigDecimal.ZERO;
    }*/

    @Id
    private Integer branchNum;
    @Id
    private String shiftTableBizday;      //营业日期
    @Id
    private Integer itemNum;
    @Id
    private String systemBookCode;
    @Id
    private String itemPeriod;      //时段
    private Date shiftTableDate;          //营业日期
    private BigDecimal itemAmout;   //销售数量
    private BigDecimal itemMoney;   //销售金额
    private String itemSource;      //销售来源

    //为了封装数据

    @Transient
    private BigDecimal[] money ;
    @Transient
    private BigDecimal[] amount ;
    @Transient
    private String[] period ;


    public Integer getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(Integer branchNum) {
        this.branchNum = branchNum;
    }

    public String getShiftTableBizday() {
        return shiftTableBizday;
    }

    public void setShiftTableBizday(String shiftTableBizday) {
        this.shiftTableBizday = shiftTableBizday;
    }

    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }

    public String getSystemBookCode() {
        return systemBookCode;
    }

    public void setSystemBookCode(String systemBookCode) {
        this.systemBookCode = systemBookCode;
    }

    public String getItemPeriod() {
        return itemPeriod;
    }

    public void setItemPeriod(String itemPeriod) {
        this.itemPeriod = itemPeriod;
    }

    public Date getShiftTableDate() {
        return shiftTableDate;
    }

    public void setShiftTableDate(Date shiftTableDate) {
        this.shiftTableDate = shiftTableDate;
    }

    public BigDecimal getItemAmout() {
        return itemAmout;
    }

    public void setItemAmout(BigDecimal itemAmout) {
        this.itemAmout = itemAmout;
    }

    public BigDecimal getItemMoney() {
        return itemMoney;
    }

    public void setItemMoney(BigDecimal itemMoney) {
        this.itemMoney = itemMoney;
    }

    public String getItemSource() {
        return itemSource;
    }

    public void setItemSource(String itemSource) {
        this.itemSource = itemSource;
    }

    public BigDecimal[] getMoney() {
        return money;
    }

    public void setMoney(BigDecimal[] money) {
        this.money = money;
    }

    public BigDecimal[] getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal[] amount) {
        this.amount = amount;
    }

    public void append(BigDecimal itemMoney, BigDecimal itemAmount,String periodStr){

        Integer index;
        Integer hour = Integer.valueOf(periodStr.substring(0, 2));
        Integer min = Integer.valueOf(periodStr.substring(2, 4));
        if(hour == 0){
            index = 0;
        }else if(min == 0){
            index = hour * 2 -1;
        }else{
            index = hour * 2;
        }


        money[index] = money[index].add(itemMoney);
        amount[index] = amount[index].add(itemAmount);
    }

    public List<ItemDailyDetail> toArray(){
        List<ItemDailyDetail> itemDailyDetails = new ArrayList<ItemDailyDetail>();

        SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
        String format = sdf.format(Calendar.getInstance().getTime());

        SimpleDateFormat dayadf = new SimpleDateFormat("yyyyMMdd");
        String bizday = dayadf.format(Calendar.getInstance().getTime());

        for (int i = 0; i <period.length ; i++) {
            if ((period[i].compareTo(format) > 0 ) && (shiftTableBizday.equals(bizday))) {
                continue;
            }
            ItemDailyDetail itemDailyDetail  = new ItemDailyDetail();
            itemDailyDetail.setSystemBookCode(systemBookCode);
            itemDailyDetail.setBranchNum(branchNum);
            itemDailyDetail.setShiftTableBizday(shiftTableBizday);
            itemDailyDetail.setItemNum(itemNum);
            itemDailyDetail.setItemSource(itemSource);
            itemDailyDetail.setShiftTableDate(shiftTableDate);
            itemDailyDetail.setItemPeriod(period[i]);
            for(int j = 0; j < (i + 1) ; j++){
                itemDailyDetail.setItemAmout(itemDailyDetail.getItemAmout().add(amount[j]));
                itemDailyDetail.setItemMoney(itemDailyDetail.getItemMoney().add(money[j]));
            }
            itemDailyDetails.add(itemDailyDetail);
        }
        return itemDailyDetails;
    }
}
