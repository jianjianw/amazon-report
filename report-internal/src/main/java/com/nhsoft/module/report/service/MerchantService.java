package com.nhsoft.module.report.service;

import com.nhsoft.module.report.model.Merchant;

import java.util.List;

public interface MerchantService {

    public List<Merchant> find(String systemBookCode, Integer branchNum);

}
