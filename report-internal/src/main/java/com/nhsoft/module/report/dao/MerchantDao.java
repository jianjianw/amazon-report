package com.nhsoft.module.report.dao;

import com.nhsoft.module.report.model.Merchant;

import java.util.List;

public interface MerchantDao {

    public List<Merchant> find(String systemBookCode, Integer branchNum);


}
