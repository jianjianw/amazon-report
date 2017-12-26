package com.nhsoft.module.azure.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

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
        this.itemPeriodAmout = BigDecimal.ZERO;
        this.itemPeriodMoney = BigDecimal.ZERO;


        this.amount = new BigDecimal[48];
        this.money = new BigDecimal[48];
        this.period = new String[48];
        //初始化数组
        for (int i = 0; i < 48 ; i++) {
            amount[i] = BigDecimal.ZERO;
            money[i] = BigDecimal.ZERO;
            if(i == 0){
                period[i] = "00:30";
            }else if(i % 2 == 0){
                if(i<19){
                    period[i] = "0"+ (i/2) +":30";  //偶数
                }else{
                    period[i] = (i/2) +":30";
                }
            }else if(i % 2 != 0){      //基数
                if(i<19){
                    period[i] = "0" + ((i+1)/2) + ":00";
                }else{
                    period[i] = ((i+1)/2) + ":00";
                }
            }

        }
    }


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
    @Id
    private String itemSource;      //销售来源
    private BigDecimal itemPeriodAmout;    //当前时段销售数量
    private BigDecimal itemPeriodMoney;    //当前时段销售金额

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

    public String[] getPeriod() {
        return period;
    }

    public void setPeriod(String[] period) {
        this.period = period;
    }

    public BigDecimal getItemPeriodAmout() {
        return itemPeriodAmout;
    }

    public void setItemPeriodAmout(BigDecimal itemPeriodAmout) {
        this.itemPeriodAmout = itemPeriodAmout;
    }

    public BigDecimal getItemPeriodMoney() {
        return itemPeriodMoney;
    }

    public void setItemPeriodMoney(BigDecimal itemPeriodMoney) {
        this.itemPeriodMoney = itemPeriodMoney;
    }

    public void append(BigDecimal itemMoney, BigDecimal itemAmount, String periodStr){

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

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
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
            itemDailyDetail.setItemPeriodAmout(amount[i]);
            itemDailyDetail.setItemPeriodMoney(money[i]);
            itemDailyDetails.add(itemDailyDetail);
        }
        return itemDailyDetails;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemDailyDetail that = (ItemDailyDetail) o;

        if (branchNum != null ? !branchNum.equals(that.branchNum) : that.branchNum != null) return false;
        if (shiftTableBizday != null ? !shiftTableBizday.equals(that.shiftTableBizday) : that.shiftTableBizday != null)
            return false;
        if (itemNum != null ? !itemNum.equals(that.itemNum) : that.itemNum != null) return false;
        if (systemBookCode != null ? !systemBookCode.equals(that.systemBookCode) : that.systemBookCode != null)
            return false;
        if (itemPeriod != null ? !itemPeriod.equals(that.itemPeriod) : that.itemPeriod != null) return false;
        if (shiftTableDate != null ? !shiftTableDate.equals(that.shiftTableDate) : that.shiftTableDate != null)
            return false;
        if (itemAmout != null ? !itemAmout.equals(that.itemAmout) : that.itemAmout != null) return false;
        if (itemMoney != null ? !itemMoney.equals(that.itemMoney) : that.itemMoney != null) return false;
        if (itemSource != null ? !itemSource.equals(that.itemSource) : that.itemSource != null) return false;
        if (itemPeriodAmout != null ? !itemPeriodAmout.equals(that.itemPeriodAmout) : that.itemPeriodAmout != null)
            return false;
        if (itemPeriodMoney != null ? !itemPeriodMoney.equals(that.itemPeriodMoney) : that.itemPeriodMoney != null)
            return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(money, that.money)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(amount, that.amount)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(period, that.period);
    }

    @Override
    public int hashCode() {
        int result = branchNum != null ? branchNum.hashCode() : 0;
        result = 31 * result + (shiftTableBizday != null ? shiftTableBizday.hashCode() : 0);
        result = 31 * result + (itemNum != null ? itemNum.hashCode() : 0);
        result = 31 * result + (systemBookCode != null ? systemBookCode.hashCode() : 0);
        result = 31 * result + (itemPeriod != null ? itemPeriod.hashCode() : 0);
        result = 31 * result + (shiftTableDate != null ? shiftTableDate.hashCode() : 0);
        result = 31 * result + (itemAmout != null ? itemAmout.hashCode() : 0);
        result = 31 * result + (itemMoney != null ? itemMoney.hashCode() : 0);
        result = 31 * result + (itemSource != null ? itemSource.hashCode() : 0);
        result = 31 * result + (itemPeriodAmout != null ? itemPeriodAmout.hashCode() : 0);
        result = 31 * result + (itemPeriodMoney != null ? itemPeriodMoney.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(money);
        result = 31 * result + Arrays.hashCode(amount);
        result = 31 * result + Arrays.hashCode(period);
        return result;
    }
}
