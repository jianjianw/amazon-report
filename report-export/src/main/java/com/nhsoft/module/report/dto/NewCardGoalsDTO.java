package com.nhsoft.module.report.dto;

import java.io.Serializable;

public class NewCardGoalsDTO implements Serializable {

    private Integer branchNum;
    private String bizday;
    private Integer newCard; //发卡目标


    public Integer getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(Integer branchNum) {
        this.branchNum = branchNum;
    }

    public String getBizday() {
        return bizday;
    }

    public void setBizday(String bizday) {
        this.bizday = bizday;
    }

    public Integer getNewCard() {
        return newCard;
    }

    public void setNewCard(Integer newCard) {
        this.newCard = newCard;
    }
}
