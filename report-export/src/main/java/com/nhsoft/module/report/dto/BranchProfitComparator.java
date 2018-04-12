package com.nhsoft.module.report.dto;

import java.util.Comparator;

public class BranchProfitComparator implements Comparator<BranchProfitDataDTO> {


    private String sortField;
    private String sortType;

    public BranchProfitComparator() {
    }

    public BranchProfitComparator(String sortField, String sortType) {
        this.sortField = sortField;
        this.sortType = sortType;

    }

    @Override
    public int compare(BranchProfitDataDTO data01, BranchProfitDataDTO data02) {
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
        //默认按门店升序排序
        int i = data01.getBranchNum().compareTo(data02.getBranchNum());

        switch (sortField){
            case "costMoney" :
                if(i == 0){
                    if(data01.getCostMoney().compareTo(data02.getCostMoney()) > 0){
                        return value01;
                    }else if(data01.getCostMoney().compareTo(data02.getCostMoney()) < 0){
                        return value02;
                    }else{
                        return 0;
                    }
                }
                break;
            case "posItemCode" :
                if(i == 0){
                    if(data01.getPosItemCode().compareTo(data02.getPosItemCode()) > 0){
                        return value01;
                    }else if(data01.getPosItemCode().compareTo(data02.getPosItemCode()) < 0){
                        return value02;
                    }else{
                        return 0;
                    }
                }
                break;
            case "posOrderMoney":
                if(i == 0){
                    if(data01.getPosOrderMoney().compareTo(data02.getPosOrderMoney()) > 0){
                        return value01;
                    }else if(data01.getPosOrderMoney().compareTo(data02.getPosOrderMoney()) < 0){
                        return value02;
                    }else{
                        return 0;
                    }
                }
                break;
            case "startInventoryMoney" :
                if(i == 0){
                    if(data01.getStartInventoryMoney().compareTo(data02.getStartInventoryMoney()) > 0){
                        return value01;
                    }else if(data01.getStartInventoryMoney().compareTo(data02.getStartInventoryMoney()) < 0){
                        return value02;
                    }else{
                        return 0;
                    }
                }
                break;
            case "endInventoryMoney" :
                if(i == 0){
                    if(data01.getEndInventoryMoney().compareTo(data02.getEndInventoryMoney()) > 0){
                        return value01;
                    }else if(data01.getEndInventoryMoney().compareTo(data02.getEndInventoryMoney()) < 0){
                        return value02;
                    }else{
                        return 0;
                    }
                }
                break;
            case "startSaleMoney" :
                if(i == 0){
                    if(data01.getStartSaleMoney().compareTo(data02.getStartSaleMoney()) > 0){
                        return value01;
                    }else if(data01.getStartSaleMoney().compareTo(data02.getStartSaleMoney()) < 0){
                        return value02;
                    }else{
                        return 0;
                    }
                }
                break;
            case "endSaleMoney" :
                if(i == 0){
                    if(data01.getEndSaleMoney().compareTo(data02.getEndSaleMoney()) > 0){
                        return value01;
                    }else if(data01.getEndSaleMoney().compareTo(data02.getEndSaleMoney()) < 0){
                        return value02;
                    }else{
                        return 0;
                    }
                }
                break;
        }

        return i;
    }
}
