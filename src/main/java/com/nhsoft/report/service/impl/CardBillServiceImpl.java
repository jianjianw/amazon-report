package com.nhsoft.report.service.impl;

import com.nhsoft.report.dao.CardBillDao;
import com.nhsoft.report.dao.CardUserLogDao;
import com.nhsoft.report.dao.CardUserRegisterDao;
import com.nhsoft.report.dao.ReplaceCardDao;
import com.nhsoft.report.service.CardBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/9/19.
 */
@Service
public class CardBillServiceImpl implements CardBillService{
	
	@Autowired
	private CardBillDao cardBillDao;
	
	@Autowired
	private CardUserRegisterDao cardUserRegisterDao;
	@Autowired
	private CardUserLogDao cardUserLogDao;
	@Autowired
	private ReplaceCardDao replaceCardDao;
	
	
	@Override
	public List<Object[]> findBranchBalance(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		List<Object[]> objects = cardBillDao.findBranchBalance(systemBookCode, branchNums, dateFrom, dateTo);
		List<Object[]> deliverObjects = cardUserRegisterDao.findBranchDeliverCount(systemBookCode, branchNums, dateFrom, dateTo);
		
		boolean find = false;
		for(int i = 0;i < deliverObjects.size();i++){
			Object[] localObject = deliverObjects.get(i);
			localObject[1] = -(Integer)localObject[1];
			find = false;
			for(int j = 0;j < objects.size();j++){
				Object[] object = objects.get(j);
				if(object[0].equals(localObject[0])){
					object[1] = ((Integer)object[1]) + ((Integer)localObject[1]);
					find = true;
					break;
				}
			}
			if(!find){
				objects.add(localObject);
			}
		}
		List<Object[]> revokeObjects = cardUserLogDao.findBranchRevokeCount(systemBookCode, branchNums, dateFrom, dateTo);
		
		for(int i = 0;i < revokeObjects.size();i++){
			Object[] localObject = revokeObjects.get(i);
			
			find = false;
			for(int j = 0;j < objects.size();j++){
				Object[] object = objects.get(j);
				if(object[0].equals(localObject[0])){
					object[1] = ((Integer)object[1]) + ((Integer)localObject[1]);
					find = true;
					break;
				}
			}
			if(!find){
				objects.add(localObject);
			}
		}
		List<Object[]> replaceObjects = replaceCardDao.findBranchCount(systemBookCode, branchNums, dateFrom, dateTo);
		
		for(int i = 0;i < replaceObjects.size();i++){
			Object[] localObject = replaceObjects.get(i);
			localObject[1] = -(Integer)localObject[1];
			
			find = false;
			for(int j = 0;j < objects.size();j++){
				Object[] object = objects.get(j);
				if(object[0].equals(localObject[0])){
					object[1] = ((Integer)object[1]) + ((Integer)localObject[1]);
					find = true;
					break;
				}
			}
			if(!find){
				objects.add(localObject);
			}
		}
		return objects;
	}
}
