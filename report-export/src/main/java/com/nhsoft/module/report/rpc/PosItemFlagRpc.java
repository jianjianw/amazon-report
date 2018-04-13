package com.nhsoft.module.report.rpc;


import com.nhsoft.module.report.dto.ItemFlagDetailDTO;
import com.nhsoft.module.report.dto.PosItemFlagDTO;

import java.util.List;

public interface PosItemFlagRpc {


	public List<PosItemFlagDTO> find(String systemBookCode, String itemFlagType);

	public List<ItemFlagDetailDTO> findDetails(String systemBookCode, int itemFlagNum);

	public PosItemFlagDTO readWithoutDetails(String systemBookCode, Integer itemFlagNum);

	public List<Integer> findItemNums(String systemBookCode, int itemFlagNum);

}
