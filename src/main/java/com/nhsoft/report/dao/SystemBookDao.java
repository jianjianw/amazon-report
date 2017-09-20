package com.nhsoft.report.dao;


import com.nhsoft.report.model.SystemBook;

import java.util.List;

public interface SystemBookDao {

	public SystemBook read(String systemBookCode);
	
	public List<SystemBook> findAll();
	
	 /**
     * 查询所有激活帐套
     * @return
     */
    public List<SystemBook> findAllActiveBooks();
    
    /**
     * 更新帐套
     * @param systemBook
     */
    public void update(SystemBook systemBook);

	public void save(SystemBook systemBook);
	
	/**
	 * 根据上级帐套号查询
	 * @param systemBookCode
	 * @return
	 */
	public List<SystemBook> findByParent(String systemBookCode);
}