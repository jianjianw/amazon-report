package com.nhsoft.report.dao;


import com.nhsoft.report.model.PosItemGrade;

import java.util.List;

public interface PosItemGradeDao {
	
	public void save(PosItemGrade posItemGrade);
	
	public void update(PosItemGrade posItemGrade);
	
	public List<PosItemGrade> find(String systemBookCode, Integer itemNum);

	public String getMaxCode(String systemBookCode, String codePrix, Integer codeLengh, boolean isBarCode);

	public List<PosItemGrade> find(String systemBookCode, List<Integer> itemNums);

	public PosItemGrade read(Integer itemGradeNum);

	public List<PosItemGrade> find(String systemBookCode);

	public List<PosItemGrade> findByIds(List<Integer> itemGradeNums);

	public List<PosItemGrade> findByItemNums(List<Integer> itemNums);

	public void delete(Integer itemGradeNum);

}
