package com.nhsoft.report.service.impl;


import com.nhsoft.report.dao.PosItemDao;
import com.nhsoft.report.dao.PosItemGradeDao;
import com.nhsoft.report.dao.StoreMatrixDao;
import com.nhsoft.report.model.*;
import com.nhsoft.report.service.PosItemService;
import com.nhsoft.report.shared.ServiceBizException;
import com.nhsoft.report.shared.queryBuilder.PosItemQuery;
import com.nhsoft.report.util.AppConstants;
import com.nhsoft.report.util.AppUtil;
import com.nhsoft.report.util.BaseManager;
import com.nhsoft.report.util.MemCacheUtil;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@SuppressWarnings("unchecked")
public class PosItemServiceImpl extends BaseManager implements PosItemService {

	private static final Logger logger = LoggerFactory.getLogger(PosItemServiceImpl.class);
	private PosItemDao posItemDao;
	private StoreMatrixDao storeMatrixDao;
	private PosItemGradeDao posItemGradeDao;
	private ConcurrentMap<String, String> concurrentMap = new ConcurrentHashMap<String, String>();
	private ConcurrentMap<String, Date> cacheDateMap = new ConcurrentHashMap<String, Date>();




	@Override
	public List<Integer> findItemNums(String systemBookCode, String categoryCodes) {
		return posItemDao.findItemNums(systemBookCode, categoryCodes);
	}


	@Override
	public List<PosItem> findShortItems(String systemBookCode) {

		Date updateDate = cacheDateMap.get(systemBookCode);
		if(updateDate == null){
			updateDate = Calendar.getInstance().getTime();
			cacheDateMap.put(systemBookCode, updateDate);
		}

		String memberCacheKey = AppConstants.MEMCACHED_PRE_POSITEM_UPDATE_TIME + systemBookCode;
		Date memberCacheDate = (Date) MemCacheUtil.get(memberCacheKey);
		if(memberCacheDate != null
				&& memberCacheDate.compareTo(updateDate) != 0){
			removeElementFromCache(AppConstants.CACHE_NAME_POS_ITEM + systemBookCode);

		}

		String cacheKey = AppConstants.CACHE_NAME_POS_ITEM + systemBookCode;
		Element element = getElementFromCache(cacheKey);
		try {
			if (element == null) {

				String localSystemBookCode = concurrentMap.get(cacheKey);
				if (localSystemBookCode == null) {
					localSystemBookCode = new String(systemBookCode);

					String oldLocalSystemBookCode = concurrentMap.putIfAbsent(cacheKey, localSystemBookCode);
					if (oldLocalSystemBookCode != null) {
						localSystemBookCode = oldLocalSystemBookCode;
					}
				}
				synchronized (localSystemBookCode) {
					element = getElementFromCache(cacheKey);
					if (element != null) {
						return (List<PosItem>) element.getObjectValue();
					}
					long begin = System.currentTimeMillis();
					List<PosItem> posItems = findAllWithKits(systemBookCode);
					logger.debug(String.format(
							"帐套[%s]商品缓存过期，从数据库读取记录[%d]条, 耗时 %s 秒",
							systemBookCode,
							posItems.size(),
							BigDecimal.valueOf((System.currentTimeMillis() - begin)).divide(BigDecimal.valueOf(1000),
									2, BigDecimal.ROUND_HALF_UP)));
					element = new Element(cacheKey, posItems);
					element.setTimeToIdle(AppConstants.CACHE_LIVE_SECOND);
					putElementToCache(element);

					if(memberCacheDate != null){
						cacheDateMap.put(systemBookCode, memberCacheDate);
					}

				}
			}
			List<PosItem> posItems = (List<PosItem>) element.getObjectValue();
			return posItems;
		} catch (Exception e) {
			throw new ServiceBizException(e.getMessage(), e);
		}
	}

	private List<PosItem> findAllWithKits(String systemBookCode) {
		List<PosItem> posItems = posItemDao.findAll(systemBookCode);
		List<PosItemKit> posItemKits = findPosItemKits(systemBookCode);
		for (int i = 0; i < posItems.size(); i++) {
			PosItem posItem = posItems.get(i);
			posItem.setPosItemKits(PosItemKit.find(posItemKits, posItem.getItemNum()));

		}
		findItemDetails(posItems);
		return posItems;
	}

	@Override
	public List<PosItemKit> findPosItemKits(String systemBookCode) {
		List<PosItemKit> posItemKits = posItemDao.findPosItemKits(systemBookCode);
		if (posItemKits.size() > 0) {
			List<Integer> itemNums = new ArrayList<Integer>();
			PosItem posItem;
			for (int i = 0; i < posItemKits.size(); i++) {
				posItem = posItemKits.get(i).getPosItem();
				if (posItem == null) {
					continue;
				}
				if (!itemNums.contains(posItem.getItemNum())) {
					itemNums.add(posItem.getItemNum());
				}
				posItem.setPosItemKits(new ArrayList<PosItemKit>());
			}
			if (itemNums.size() > 0) {
				List<ItemBar> itemBars = posItemDao.findItemBars(itemNums);
				List<ItemMatrix> itemMatrixs = posItemDao.findItemMatrixs(itemNums);
				for (int i = 0; i < posItemKits.size(); i++) {
					posItem = posItemKits.get(i).getPosItem();
					if (posItem == null) {
						continue;
					}
					posItem.setItemBars(ItemBar.find(itemBars, posItem.getItemNum()));
					if (posItem.getItemType() == AppConstants.C_ITEM_TYPE_MATRIX) {
						posItem.setItemMatrixs(ItemMatrix.find(itemMatrixs, posItem.getItemNum()));

					} else {
						posItem.setItemMatrixs(new ArrayList<ItemMatrix>());
					}
				}
			}

		}
		return posItemKits;
	}


	/**
	 * 组装itemBars和itemMatrixs
	 *
	 * @param posItems
	 */
	@Override
	public void findItemDetails(List<PosItem> posItems) {
		if (posItems.size() == 0) {
			return;
		}
		List<ItemBar> itemBars;
		List<ItemMatrix> itemMatrixs;
		PosItem posItem;
		if (posItems.size() > 50) {
			String systemBookCode = posItems.get(0).getSystemBookCode();
			itemBars = posItemDao.findItemBars(systemBookCode);
			itemMatrixs = posItemDao.findItemMatrixs(systemBookCode);

		} else {
			List<Integer> itemNums = new ArrayList<Integer>();
			for (int i = 0; i < posItems.size(); i++) {
				posItem = posItems.get(i);
				itemNums.add(posItem.getItemNum());
			}
			itemBars = posItemDao.findItemBars(itemNums);
			itemMatrixs = posItemDao.findItemMatrixs(itemNums);
		}
		for (int i = 0; i < posItems.size(); i++) {
			posItem = posItems.get(i);
			posItem.setItemBars(ItemBar.find(itemBars, posItem.getItemNum()));
			if (posItem.getItemType() == AppConstants.C_ITEM_TYPE_MATRIX) {
				posItem.setItemMatrixs(ItemMatrix.find(itemMatrixs, posItem.getItemNum()));

			} else {
				posItem.setItemMatrixs(new ArrayList<ItemMatrix>());
			}

		}
	}

	@Override
	public List<PosItem> findByItemNumsWithoutDetails(List<Integer> itemNums) {

		return posItemDao.findByItemNums(itemNums);
	}

	@Override
	public List<PosItem> find(String systemBookCode) {
		List<PosItem> posItems = posItemDao.find(systemBookCode);
		findItemDetails(posItems);
		return posItems;
	}

	@Override
	public List<PosItem> find(String systemBookCode, List<String> categoryCodes, List<Integer> itemNums, String itemMethod) {
		return posItemDao.find(systemBookCode, categoryCodes, itemNums, itemMethod);
	}

	@Override
	public List<Object[]> findBySuppliers(List<Integer> supplierNums, List<Integer> itemNums,
										  List<String> categoryCodes, Integer branchNum, String systemBookCode) {
		return posItemDao.findBySuppliers(supplierNums, itemNums, categoryCodes, branchNum, systemBookCode);
	}

	@Override
	public List<Integer> findUnSaleItemNums(String systemBookCode, Integer branchNum) {
		String key = systemBookCode + "|" + branchNum;
		String cacheKey = AppConstants.CACHE_SALE_CEASE_ITEM + key;
		Element element = getElementFromCache(cacheKey);
		if (element == null) {

			String localKey = concurrentMap.get(cacheKey);
			if (localKey == null) {
				localKey = new String(key);

				String oldLocalKey = concurrentMap.putIfAbsent(cacheKey, localKey);
				if (oldLocalKey != null) {
					localKey = oldLocalKey;
				}
			}
			synchronized (localKey) {
				element = getElementFromCache(cacheKey);
				if (element != null) {
					return (List<Integer>) element.getObjectValue();
				}

				List<Integer> unSaleItemNums = posItemDao.findUnSaleItemNums(systemBookCode);
				List<Object[]> objects = storeMatrixDao.findItemSaleCeaseFlag(systemBookCode, branchNum);
				for (int i = objects.size() - 1; i >= 0; i--) {
					Object[] object = objects.get(i);
					Integer itemNum = (Integer) object[0];
					boolean flag = object[1] == null ? false : (Boolean) object[1];
					if (flag) {
						if (!unSaleItemNums.contains(itemNum)) {
							unSaleItemNums.add(itemNum);
						}
					} else {
						if (unSaleItemNums.contains(itemNum)) {
							unSaleItemNums.remove(itemNum);
						}
					}
				}
				logger.info(String.format("帐套[%s]分店[%d]停售商品缓存过期，从数据库读取记录[%d]条", systemBookCode, branchNum,
						unSaleItemNums.size()));
				element = new Element(cacheKey, unSaleItemNums);
				element.setTimeToLive(600);
				putElementToCache(element);
			}

		}
		return (List<Integer>) element.getObjectValue();
	}

	@Override
	public List<PosItem> findByItemNums(List<Integer> itemNums) {
		if (itemNums == null || itemNums.size() == 0) {
			return new ArrayList<PosItem>();
		}

		List<PosItem> posItems = posItemDao.findByItemNums(itemNums);
		findItemDetails(posItems);
		return posItems;
	}

	@Override
	public List<PosItemKit> findPosItemKitsWithDetails(List<Integer> itemNums) {
		List<PosItemKit> posItemKits = posItemDao.findPosItemKitsWithPosItem(itemNums);
		if (posItemKits.size() > 0) {
			itemNums.clear();
			PosItem posItem;
			for (int i = 0; i < posItemKits.size(); i++) {
				posItem = posItemKits.get(i).getPosItem();
				if (posItem == null) {
					continue;
				}
				if (!itemNums.contains(posItem.getItemNum())) {
					itemNums.add(posItem.getItemNum());
				}
				posItem.setPosItemKits(new ArrayList<PosItemKit>());
			}
			if (itemNums.size() > 0) {
				List<ItemBar> itemBars = posItemDao.findItemBars(itemNums);
				List<ItemMatrix> itemMatrixs = posItemDao.findItemMatrixs(itemNums);
				for (int i = 0; i < posItemKits.size(); i++) {
					posItem = posItemKits.get(i).getPosItem();
					if (posItem == null) {
						continue;
					}
					posItem.setItemBars(ItemBar.find(itemBars, posItem.getItemNum()));
					if (posItem.getItemType() == AppConstants.C_ITEM_TYPE_MATRIX) {
						posItem.setItemMatrixs(ItemMatrix.find(itemMatrixs, posItem.getItemNum()));

					} else {
						posItem.setItemMatrixs(new ArrayList<ItemMatrix>());
					}
				}
			}
		}
		return posItemKits;
	}

	@Override
	public List<PosItem> findByPosItemQuery(PosItemQuery posItemQuery, String sortField, String sortName, int first,
											int count) {
		List<PosItem> posItems = posItemDao.findByPosItemQuery(posItemQuery, sortField, sortName, first, count);
		if (posItemQuery.getIsFindItemGrade() != null && posItemQuery.getIsFindItemGrade()) {
			List<Integer> itemNums = new ArrayList<Integer>();
			for (int i = 0; i < posItems.size(); i++) {
				PosItem posItem = posItems.get(i);
				posItem.setPosItemGrades(new ArrayList<PosItemGrade>());

				if (posItem.getItemType() == AppConstants.C_ITEM_TYPE_GRADE) {

					itemNums.add(posItem.getItemNum());
				}
			}
			if (itemNums.size() > 0) {
				List<PosItemGrade> posItemGrades = posItemGradeDao.findByItemNums(itemNums);
				for (int i = 0; i < posItems.size(); i++) {
					PosItem posItem = posItems.get(i);
					if (posItem.getItemType() == AppConstants.C_ITEM_TYPE_GRADE) {
						posItem.setPosItemGrades(PosItemGrade.find(posItemGrades, posItem.getItemNum()));

					}
				}
			}
		}
		if(posItemQuery.getItemType() != null){
			if(posItemQuery.getItemType() == AppConstants.C_ITEM_TYPE_GRADE){
				List<PosItemGrade> posItemGrades = new ArrayList<PosItemGrade>();
				if(posItemQuery.isPaging()){
					List<Integer> itemNums = new ArrayList<Integer>();
					for (int i = 0; i < posItems.size(); i++) {

						itemNums.add(posItems.get(i).getItemNum());

					}
					if(itemNums.size() > 0){
						posItemGrades = posItemGradeDao.findByItemNums(itemNums);
					}

				} else {
					posItemGrades = posItemGradeDao.find(posItemQuery.getSystemBookCode());
				}

				for (int i = 0; i < posItems.size(); i++) {
					PosItem posItem = posItems.get(i);
					posItem.setPosItemGrades(PosItemGrade.find(posItemGrades, posItem.getItemNum()));
					posItem.setPosItemKits(new ArrayList<PosItemKit>());

				}

			} else if(posItemQuery.getItemType() == AppConstants.C_ITEM_TYPE_KIT){
				List<PosItemKit> posItemKits = new ArrayList<PosItemKit>();
				if(posItemQuery.isPaging()){
					List<Integer> itemNums = new ArrayList<Integer>();
					for (int i = 0; i < posItems.size(); i++) {

						itemNums.add(posItems.get(i).getItemNum());

					}
					if(itemNums.size() > 0){
						posItemKits = posItemDao.findPosItemKits(itemNums);
					}

				} else {
					posItemKits = findPosItemKits(posItemQuery.getSystemBookCode());
				}

				for (int i = 0; i < posItems.size(); i++) {
					PosItem posItem = posItems.get(i);
					posItem.setPosItemKits(PosItemKit.find(posItemKits, posItem.getItemNum()));
					posItem.setPosItemGrades(new ArrayList<PosItemGrade>());
				}
			}
		}
		if(posItemQuery.getQueryKit() != null && posItemQuery.getQueryKit()){
			List<PosItemKit> posItemKits = new ArrayList<PosItemKit>();
			if(posItemQuery.isPaging()){
				List<Integer> itemNums = new ArrayList<Integer>();
				for (int i = 0; i < posItems.size(); i++) {

					itemNums.add(posItems.get(i).getItemNum());

				}
				if(itemNums.size() > 0){
					posItemKits = posItemDao.findPosItemKits(itemNums);
				}

			} else {
				posItemKits = posItemDao.findPosItemKits(posItemQuery.getSystemBookCode());
			}

			for (int i = 0; i < posItems.size(); i++) {
				PosItem posItem = posItems.get(i);
				posItem.setPosItemKits(PosItemKit.find(posItemKits, posItem.getItemNum()));
				posItem.setPosItemGrades(new ArrayList<PosItemGrade>());
			}
		}
		if(posItemQuery.getQueryProperties() == null || posItemQuery.getQueryProperties().isEmpty()){
			findItemDetails(posItems);

		}
		return posItems;
	}

	@Override
	public List<PosItem> findByItemNumsInCache(String systemBookCode, List<Integer> itemNums) {
		if(itemNums == null || itemNums.size() == 0){
			return new ArrayList<PosItem>();
		}
		List<PosItem> posItems = new ArrayList<PosItem>();
		String cacheKey = AppConstants.CACHE_NAME_POS_ITEM + systemBookCode;
		Element element = getElementFromCache(cacheKey);
		if(element != null){
			List<PosItem> cachePosItems = (List<PosItem>) element.getObjectValue();
			Integer itemNum = null;
			List<Integer> notExistsItemNums = new ArrayList<Integer>();
			for(int i = itemNums.size() - 1;i >= 0;i--){
				itemNum = itemNums.get(i);
				PosItem posItem = AppUtil.getPosItem(itemNum, cachePosItems);
				if(posItem == null){
					notExistsItemNums.add(itemNum);
					continue;
				}
				posItems.add(posItem);

			}
			//缓存中没找到的 再去数据库中查
			if(notExistsItemNums.size() > 0){
				posItems.addAll(findByItemNums(notExistsItemNums));
			}

		} else {
			posItems.addAll(findByItemNums(itemNums));
		}
		return posItems;
	}

	@Override
	public PosItem readWithoutDetails(Integer itemNum) {
		PosItem posItem = posItemDao.read(itemNum);
		if (posItem != null) {
			posItem.setItemBars(posItemDao.findItemBars(itemNum));
			if (posItem.getItemType() == AppConstants.C_ITEM_TYPE_MATRIX) {
				posItem.setItemMatrixs(posItemDao.findItemMatrixs(posItem.getItemNum()));

			} else {
				posItem.setItemMatrixs(new ArrayList<ItemMatrix>());
			}
		}
		return posItem;
	}
}
