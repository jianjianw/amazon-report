package com.nhsoft.report.service;



import com.nhsoft.report.model.SystemBook;

import java.util.List;

/**
 * 帐套
 * @author nhsoft
 *
 */
public interface SystemBookService {

	/**
	 * 读取
	 * @param systemBookCode
	 * @return
	 */
    public SystemBook
	read(String systemBookCode);
    
    /**
     * 查询所有
     * @return
     */
    public List<SystemBook> findAll();
    
	 /**
     * 查询所有启用记录
     * @return
     */
    public List<SystemBook> findAllActiveBooks();
    
    /**
     * 更新帐套
     * @param systemBook
     */
    public void update(SystemBook systemBook);

    /**
     * 保存
     * @param systemBook
     */
	public void save(SystemBook systemBook);
	
	/**
	 * 从缓存中读单个
	 * @param systemBookCode
	 * @return
	 */
	public SystemBook readInCache(String systemBookCode);
	
	/**
	 * 从缓存中读所有
	 * @return
	 */
	public List<SystemBook> findAllInCache();
	
	/**
	 * 微商城续费
	 * @param systemBookCode
	 */
	public void addWShopExpiration(String systemBookCode, int year);
	
	/**
	 * 修改乐盟帐套名称
	 * @param systemBookCode
	 * @param systemBookName
	 */
	public void updateLementBookName(String systemBookCode, String systemBookName);
	
	/**
	 * 根据上级帐套号查询
	 * @param systemBookCode
	 * @return
	 */
	public List<SystemBook> findByParent(String systemBookCode);
	
	/**
	 * 更新连锁标记
	 * @param systemBookCode
	 * @param checkCode
	 */
	public void updateChainActived(String systemBookCode, String checkCode);
}
