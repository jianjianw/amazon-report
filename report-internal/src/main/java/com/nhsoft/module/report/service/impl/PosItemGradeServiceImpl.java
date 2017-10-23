package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.PosItemGradeDao;
import com.nhsoft.module.report.model.PosItemGrade;
import com.nhsoft.module.report.service.PosItemGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yangqin on 2017/9/20.
 */
@Service
public class PosItemGradeServiceImpl implements PosItemGradeService {
	
	@Autowired
	private PosItemGradeDao posItemGradeDao;
	
	
	@Override
	public List<PosItemGrade> findByIds(List<Integer> itemGradeNums) {
		return posItemGradeDao.findByIds(itemGradeNums);
	}
}
