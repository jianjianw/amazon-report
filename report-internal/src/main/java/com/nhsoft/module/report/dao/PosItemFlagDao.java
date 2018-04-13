package com.nhsoft.module.report.dao;


import com.nhsoft.module.report.model.ItemFlagDetail;
import com.nhsoft.module.report.model.PosItemFlag;

import java.util.List;

public interface PosItemFlagDao {

    public PosItemFlag read(int itemFlagNum);

    public PosItemFlag readByName(String systemBookCode, String itemFlagName);

    public List<PosItemFlag> find(String systemBookCode, String itemFlagType);

    public List<ItemFlagDetail> findDetails(int itemFlagNum);

    public List<Integer> findItemNums(int itemFlagNum);


}
