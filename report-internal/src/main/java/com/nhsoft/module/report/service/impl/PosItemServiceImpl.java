package com.nhsoft.module.report.service.impl;


import com.nhsoft.module.report.dao.PosItemDao;
import com.nhsoft.module.report.dao.StoreMatrixDao;
import com.nhsoft.module.report.model.ItemBar;
import com.nhsoft.module.report.model.ItemMatrix;
import com.nhsoft.module.report.model.PosItem;
import com.nhsoft.module.report.model.PosItemKit;
import com.nhsoft.module.report.service.PosItemService;
import com.nhsoft.report.utils.MemCacheUtil;
import com.nhsoft.report.utils.ServiceBizException;
import com.nhsoft.module.report.queryBuilder.PosItemQuery;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.report.utils.BaseManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
@Service
@SuppressWarnings("unchecked")
public class PosItemServiceImpl extends BaseManager implements PosItemService {

	private static final Logger logger = LoggerFactory.getLogger(PosItemServiceImpl.class);
	@Autowired
	private PosItemDao posItemDao;
	@Autowired
	private StoreMatrixDao storeMatrixDao;
	private ConcurrentMap<String, String> concurrentMap = new ConcurrentHashMap<String, String>();
	private ConcurrentMap<String, Date> cacheDateMap = new ConcurrentHashMap<String, Date>();


	@Override
	public List<PosItem> findAll(String systemBookCode) {
		return posItemDao.findAll(systemBookCode);
	}

	@Override
	public List<PosItemKit> findPosItemKits(Integer itemNum) {
		List<PosItemKit> posItemKits = posItemDao.findPosItemKits(itemNum);
		for (int i = 0; i < posItemKits.size(); i++) {
			PosItemKit posItemKit = posItemKits.get(i);

			PosItem posItem = posItemKit.getPosItem();
			if (posItem != null) {
				posItem.setItemBars(posItemDao.findItemBars(posItem.getItemNum()));

				if (posItem.getItemType() == AppConstants.C_ITEM_TYPE_MATRIX) {
					posItem.setItemMatrixs(posItemDao.findItemMatrixs(posItem.getItemNum()));

				} else {
					posItem.setItemMatrixs(new ArrayList<ItemMatrix>());
				}
			}
		}
		return posItemKits;
	}

	public List<PosItemKit> findPosItemKits(String systemBookCode) {
		List<PosItemKit> posItemKits = posItemDao.findPosItemKits(systemBookCode);
		int size = posItemKits.size();
		if (size > 0) {
			List<Integer> itemNums = new ArrayList<Integer>(size);
			PosItem posItem;
			for (int i = 0; i < size; i++) {
				posItem = posItemKits.get(i).getPosItem();
				if (posItem == null) {
					continue;
				}
				if (!itemNums.contains(posItem.getItemNum())) {
					itemNums.add(posItem.getItemNum());
				}
				posItem.setPosItemKits(new ArrayList<PosItemKit>(0));
			}
			if (itemNums.size() > 0) {
				List<ItemBar> itemBars = posItemDao.findItemBars(itemNums);
				List<ItemMatrix> itemMatrixs = posItemDao.findItemMatrixs(itemNums);
				for (int i = 0; i < size; i++) {
					posItem = posItemKits.get(i).getPosItem();
					if (posItem == null) {
						continue;
					}
					posItem.setItemBars(ItemBar.find(itemBars, posItem.getItemNum()));
					if (posItem.getItemType() == AppConstants.C_ITEM_TYPE_MATRIX) {
						posItem.setItemMatrixs(ItemMatrix.find(itemMatrixs, posItem.getItemNum()));

					} else {
						posItem.setItemMatrixs(new ArrayList<ItemMatrix>(0));
					}
				}
			}

		}
		return posItemKits;
	}

	@Override
	public List<Integer> findItemNumsByPosItemQuery(PosItemQuery posItemQuery, int offset, int limit) {
		return posItemDao.findItemNumsByPosItemQuery(posItemQuery, offset, limit);
	}

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

	private List<PosItem> findAllWithDetails(String systemBookCode) {
		List<PosItem> posItems = posItemDao.findAll(systemBookCode);
		findItemDetails(posItems);
		return posItems;
	}

	/**
	 * 组装itemBars和itemMatrixs
	 *
	 * @param posItems
	 */
	@Override
	public void findItemDetails(List<PosItem> posItems) {
		if (posItems.isEmpty()) {
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
	public List<PosItem> findByPosItemQuery(PosItemQuery posItemQuery, String sortField, String sortName, int first,
											int count) {
		List<PosItem> posItems = posItemDao.findByPosItemQuery(posItemQuery, sortField, sortName, first, count);
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


}
