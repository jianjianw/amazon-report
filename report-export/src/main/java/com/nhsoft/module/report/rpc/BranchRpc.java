package com.nhsoft.module.report.rpc;

import com.nhsoft.module.azure.model.BranchLat;
import com.nhsoft.module.report.dto.BranchArea;
import com.nhsoft.module.report.dto.BranchDTO;
import com.nhsoft.module.report.dto.BranchRegionDTO;

import java.util.Date;
import java.util.List;

public interface BranchRpc {



    /**
     * 查询分店
     * @param systemBookCode 帐套号
     * @return
     */
    public List<BranchDTO> findInCache(String systemBookCode);

    /**
     * 根据账套号查询区域
     * @param systemBookCode 帐套号
     * @return
     * */
    public List<BranchRegionDTO> findBranchRegion(String systemBookCode);

    /**
     * 按照区域查询分店
     * @param systemBookCode 帐套号
     * @param  branchRegionNum   分店区域号
     * @return
     * */
    public List<BranchDTO> findBranchByBranchRegionNum(String systemBookCode, Integer branchRegionNum);


    /**
     * 查询分店面积
     * @param systemBookCode
     * @param branchNums 分店号
     */
    public List<BranchArea> findBranchArea(String systemBookCode, List<Integer> branchNums);

    /**
     * 根据分店号查询分店
     * @param systemBookCode
     * @param branchNum 分店号
     * */
    public BranchDTO readWithNolock(String systemBookCode, Integer branchNum);

    /**
     * bi 查询分店
     *  @param systemBookCode
     * */
    public List<com.nhsoft.module.azure.model.Branch> findBranch(String systemBookCode);

}
