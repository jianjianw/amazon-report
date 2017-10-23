package com.nhsoft.module.report.dao;


import com.nhsoft.module.report.model.BookResource;

public interface BookResourceDao {

	public BookResource read(String systemBookCode, String bookResourceName);
}