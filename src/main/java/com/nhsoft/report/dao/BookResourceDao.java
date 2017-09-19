package com.nhsoft.report.dao;



import com.nhsoft.report.model.BookResource;

import java.util.List;

public interface BookResourceDao {

	public BookResource read(String systemBookCode, String bookResourceName);
}