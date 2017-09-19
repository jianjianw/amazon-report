package com.nhsoft.report.service;


import com.nhsoft.report.model.PosClient;
import com.nhsoft.report.param.PosClientParam;
import com.nhsoft.report.shared.queryBuilder.ClientQuery;

import java.util.Date;
import java.util.List;

/**
 * 批发客户
 * @author nhsoft
 *
 */
public interface PosClientService {

	/**
	 * 查询
	 * @param systemBookCode
	 * @param clientFids 主键列表
	 * @return
	 */
	public List<PosClient> find(String systemBookCode, List<String> clientFids);

	/**
	 * 
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @param clientType 客户类型
	 * @param clientQuery 查询条件对象
	 * @param offset 查询起始位
	 * @param limit 查询数量
	 * @param sortField 排序字段
	 * @param sortType ASC or DESC
	 * @return
	 */
	public List<PosClient> findByType(String systemBookCode, Integer branchNum, String clientType, ClientQuery clientQuery,
									  int offset, int limit, String sortField, String sortType);

	/**
	 * 查询记录数量
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @param clientType 客户类型
	 * @param clientQuery 查询条件对象
	 * @return
	 */
	public int countByType(String systemBookCode, Integer branchNum, String clientType, ClientQuery clientQuery);

	/**
	 * 保存或更新
	 * @param posClient
	 * @return
	 */
	public PosClient saveOrUpdate(PosClient posClient);

	/**
	 * 删除
	 * @param posClient
	 */
	public void delete(PosClient posClient);

	/**
	 * 检查客户代码是否存在
	 * @param systemBookCode
	 * @param clientCode 客户代码
	 * @return
	 */
	public boolean checkCode(String systemBookCode, String clientCode);

	/**
	 * 检查客户类型是否存在
	 * @param systemBookCode
	 * @param clientType  客户类型
	 * @return
	 */
	public boolean checkTypeName(String systemBookCode, String clientType);

	/**
	 * 读取
	 * @param clientFid 主键
	 * @return
	 */
	public PosClient read(String clientFid);

	/**
	 * 批量保存
	 * @param posClients
	 * @return
	 */
	public List<PosClient> batchSave(List<PosClient> posClients);

	/**
	 * 根据规则生成代码
	 * @param systemBookCode
	 * @param param 规则参数
	 * @param prixCode 前缀
	 * @return
	 */
	public String autoGenerateCode(String systemBookCode, PosClientParam param, String prixCode);

	/**
	 * 查询所有
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @return
	 */
	public List<PosClient> findAll(String systemBookCode, Integer branchNum);

	/**
	 * 查询
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @param regionNums 区域主键列表
	 * @return
	 */
	public List<PosClient> findByRegion(String systemBookCode, Integer branchNum, List<Integer> regionNums);

	/**
	 * 从缓存中查询
	 * @param systemBookCode
	 * @return
	 */
	public List<PosClient> findInCache(String systemBookCode);

	/**
	 * 查询所有未删除的
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @return
	 */
	public List<PosClient> find(String systemBookCode, Integer branchNum);

	/**
	 * 按类型查询重复的客户
	 * @param systemBookCode
	 * @param repeatType = 0 代码重复客户  = 1名称重复客户 2 手机号码模糊匹配客户
	 * @return
	 */
	public List<PosClient> findRepeatClient(String systemBookCode, int repeatType);

	/**
	 * 客户合并
	 * @param remainPosClient 保留客户
	 * @param deletePosClients 需合并客户
	 */
	public void mergeClients(PosClient remainPosClient, List<PosClient> deletePosClients);

	/**
	 *  按类型查询重复的客户
	 * @param systemBookCode
	 * @param checkName 验证名称重复
	 * @param checkCode 验证代码重复
	 * @param checkPhone 验证手机号码重复
	 * @return
	 */
	public List<PosClient> findRepeatClient(String systemBookCode, boolean checkName, boolean checkCode, boolean checkPhone);

	/**
	 * 查询客户生日数
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	public Integer countByBirthDay(String systemBookCode, Integer branchNum);

	/**
	 * 从缓存中读取
	 * @param systemBookCode
	 * @param clientFid
	 * @return
	 */
	public PosClient readInCache(String systemBookCode, String clientFid);

	/**
	 * 根据最后修改时间查询
	 * @param systemBookCode
	 * @param branchNum
	 * @param lastEditTime
	 * @return
	 */
	public List<PosClient> findByLastEditTime(String systemBookCode, Integer branchNum, Date lastEditTime);

	/**
	 * 根据批发客户群组查询数量
	 * @param systemBookCode
	 * @param branchNums
	 * @param param
	 * @return
	 */
	public int countByPosClientCustomGroup(String systemBookCode, List<Integer> branchNums,
                                           PosClientCustomGroupParam param);

	/**
	 * 根据批发客户群组查询
	 * @param systemBookCode
	 * @param branchNums
	 * @param param
	 * @return
	 */
	public List<PosClient> findByPosClientCustomGroup(String systemBookCode, List<Integer> branchNums,
                                                      PosClientCustomGroupParam param);
	
	/**
	 * 按主键查询
	 * @param clientFids
	 * @return
	 */
	public List<PosClient> findByFids(List<String> clientFids);
	
	/**
	 * 查询没有客户欠款记录的客户
	 * @param systemBookCode
	 * @return
	 */
	public List<Object[]> findNoBalanceClients(String systemBookCode);
	
	public void updateClientShared(List<String> clientFids, boolean clientShared);
}
