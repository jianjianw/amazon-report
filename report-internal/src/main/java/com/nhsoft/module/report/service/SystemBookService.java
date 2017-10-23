package com.nhsoft.module.report.service;


import com.nhsoft.module.report.model.SystemBook;

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
    public SystemBook read(String systemBookCode);
    
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
	 * 根据上级帐套号查询
	 * @param systemBookCode
	 * @return
	 */
	public List<SystemBook> findByParent(String systemBookCode);
	

}
