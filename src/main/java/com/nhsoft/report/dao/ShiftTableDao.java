package com.nhsoft.report.dao;


import com.nhsoft.report.model.ShiftTable;

import java.util.Date;
import java.util.List;

public interface ShiftTableDao {

	public List<ShiftTable> find(String systemBookCode, Integer branchNum, Date dtFrom, Date dtTo);
}
