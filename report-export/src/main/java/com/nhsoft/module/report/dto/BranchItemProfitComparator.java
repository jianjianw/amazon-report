package com.nhsoft.module.report.dto;

import java.util.Comparator;

public class BranchItemProfitComparator implements Comparator<TransferProfitByPosItemDTO> {


    private String sortField;
    private String sortType;

    public BranchItemProfitComparator(String sortField, String sortType) {
        this.sortField = sortField;
        this.sortType = sortType;
    }

    @Override
    public int compare(TransferProfitByPosItemDTO data01, TransferProfitByPosItemDTO data02) {
        int value01 = 0;
        int value02 = 0;
        if("asc".equals(sortType) || "ASC".equals(sortType)){
            value01 = 1;
            value02 = -1;
        }
        if("desc".equals(sortType) || "DESC".equals(sortType)){
            value01 = -1;
            value02 = 1;
        }
        //默认按tranferBrnachNum升序排序
        int compare = data01.getTranferBranchNum().compareTo(data02.getTranferBranchNum());

        switch(sortField){
            case "" :
                if(compare == 0){

                }
                break;
        }

        return compare;
    }



}
