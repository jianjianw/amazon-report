package com.nhsoft.report.rpc;

import com.nhsoft.report.dto.BranchArea;
import com.nhsoft.report.model.Branch;
import com.nhsoft.report.model.BranchRegion;

import java.util.List;

public interface BranchRpc {



    /**
     * 查询分店
     * @param systemBookCode 帐套号
     * @return
     */
    public List<Branch> findAll(String systemBookCode);

    /*
     *	查询分店区域
     * @param systemBookCode 帐套号
     * @return
     * */
    public List<BranchRegion> findBranchRegion(String systemBookCode);

    /**
     * 按照区域查询分店
     * @param systemBookCode 帐套号
     * @param  branchRegionNum   分店区域号
     * @return
     * */
    public List<Branch> findBranchByBranchRegionNum(String systemBookCode, Integer branchRegionNum);


    /**
     * 查询分店面积
     * @param systemBookCode
     * @param branchNums 分店号
     */
    public List<BranchArea> findBranchArea(String systemBookCode, List<Integer> branchNums);
}
