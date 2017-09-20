package com.nhsoft.report.dao;


import com.nhsoft.report.model.PosItemGrade;

import java.util.List;

public interface PosItemGradeDao {
	
	
	public List<PosItemGrade> find(String systemBookCode, Integer itemNum);
	
	public List<PosItemGrade> find(String systemBookCode, List<Integer> itemNums);
	
	public List<PosItemGrade> find(String systemBookCode);

	public List<PosItemGrade> findByIds(List<Integer> itemGradeNums);

	public List<PosItemGrade> findByItemNums(List<Integer> itemNums);
	
}
