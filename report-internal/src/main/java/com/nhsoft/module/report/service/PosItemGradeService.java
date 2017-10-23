package com.nhsoft.module.report.service;



import com.nhsoft.module.report.model.PosItemGrade;

import java.util.List;

/**
 * Created by yangqin on 2017/9/20.
 */
public interface PosItemGradeService {
	
	public List<PosItemGrade> findByIds(List<Integer> itemGradeNums);
	
}
