package com.nhsoft.module.report.service;

import com.nhsoft.module.report.dto.PosItemLogSummaryDTO;

import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/9/20.
 */
public interface PosItemLogService {
	
	/**
	 * 根据分店 商品 进出库方向 汇总 数量 金额 辅助数量
	 * @param systemBookCode
	 * @param branchNums 进出分店列表
	 * @param dateFrom 进出时间起
	 * @param dateTo 进出时间止
	 * @param summaries 进出类型 有多个用逗号隔开
	 * @param itemNums 商品主键列表
	 * @param storehouseNum  仓库号
	 * @return
	 */
	public List<Object[]> findBranchItemFlagSummary(String systemBookCode,
	                                                List<Integer> branchNums, Date dateFrom, Date dateTo, String summaries, List<Integer> itemNums, Integer storehouseNum);
	
	/**
	 * 根据分店 营业日 商品 多特性编码 进出库类型汇总 数量 金额 辅助数量
	 * @param systemBookCode
	 * @param branchNums 进出分店列表
	 * @param dateFrom 进出时间起
	 * @param dateTo 进出时间止
	 * @param summaries 进出类型  有多个用逗号隔开
	 * @param itemNums 商品主键列表
	 * @param storehouseNum 仓库号
	 * @param memos 调整类型名称列表
	 * @return
	 */
	public List<Object[]> findSumByBranchDateItemFlag(String systemBookCode,
	                                                  List<Integer> branchNums, Date dateFrom, Date dateTo, String summaries, List<Integer> itemNums,
	                                                  Integer storehouseNum, List<String> memos);
	
	/**
	 * 根据商品 多特性编码 进出库类型汇总 数量 金额 辅助数量
	 * @param systemBookCode
	 * @param branchNums 进出分店列表
	 * @param dateFrom 进出时间起
	 * @param dateTo 进出时间止
	 * @param summaries 进出类型  有多个用逗号隔开
	 * @param itemNums 商品主键列表
	 * @param storehouseNum 仓库号
	 * @param memos 调整类型名称列表
	 * @return
	 */
	public List<Object[]> findSumByItemFlag(String systemBookCode,
	                                        List<Integer> branchNums, Date dateFrom, Date dateTo, String summaries, List<Integer> itemNums,
	                                        Integer storehouseNum, List<String> memos);

}
