package com.nhsoft.report.provider.sharding;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.google.common.collect.Range;
import com.nhsoft.report.utils.DynamicDataSourceContextHolder;
import com.nhsoft.report.utils.DateUtil;

import java.util.*;

/**
 * Created by yangqin on 2017/11/2.
 */
public class PosItemLogSharding {
	
	public static TableRule createTableRule(DataSourceRule dataSourceRule){
		
		List<String> actualTables = new ArrayList<String>();
		actualTables.add("pos_item_log");
		actualTables.add("pos_item_log_2012");
		actualTables.add("pos_item_log_2013");
		actualTables.add("pos_item_log_2014");
		actualTables.add("pos_item_log_2015");
		actualTables.add("pos_item_log_2016");
		
		return TableRule.builder("pos_item_log")
				.actualTables(actualTables)
				.dataSourceRule(dataSourceRule)
				.tableShardingStrategy(new TableShardingStrategy("pos_item_log_date_index", new PosItemLogSharding.PosItemLogShardingTableAlgorithm()))
				.databaseShardingStrategy(new DatabaseShardingStrategy("system_book_code", new PosItemLogSharding.PosItemLogShardingDataBaseAlgorithm()))
				.build();
	}

public static class PosItemLogShardingDataBaseAlgorithm implements SingleKeyDatabaseShardingAlgorithm<String> {
	
	@Override
	public String doEqualSharding(Collection<String> collection, ShardingValue<String> shardingValue) {
		return DynamicDataSourceContextHolder.getDataSourceType();
	}
	
	@Override
	public Collection<String> doInSharding(Collection<String> collection, ShardingValue<String> shardingValue) {
		Collection<String> result = new LinkedHashSet<String>(1);
		result.add(DynamicDataSourceContextHolder.getDataSourceType());
		return result;
	}
	
	@Override
	public Collection<String> doBetweenSharding(Collection<String> collection, ShardingValue<String> shardingValue) {
		Collection<String> result = new LinkedHashSet<String>(1);
		result.add(DynamicDataSourceContextHolder.getDataSourceType());
		return result;
	}
}


public static class PosItemLogShardingTableAlgorithm implements SingleKeyTableShardingAlgorithm<String> {
	
	@Override
	public String doEqualSharding(Collection<String> collection, ShardingValue<String> shardingValue) {
		
		String value = shardingValue.getValue();
		value = value.substring(0,4);
		if(value.compareTo(DateUtil.getCurrentYear() + "") >= 0) {
			return "pos_item_log";
		}
		for (String each : collection) {
			
			if (each.endsWith(value)) {
				return each;
			}
		}
		return "pos_item_log";
	}
	
	@Override
	public Collection<String> doInSharding(Collection<String> collection, ShardingValue<String> shardingValue) {
		Collection<String> result = new LinkedHashSet<String>(1);
		result.add("pos_item_log");
		return result;
	}
	
	@Override
	public Collection<String> doBetweenSharding(Collection<String> tableNames, ShardingValue<String> shardingValue) {
		Range<String> range = (Range<String>) shardingValue.getValueRange();
		
		Collection<String> result = new LinkedHashSet<String>(tableNames.size());
		int begin = Integer.parseInt(range.lowerEndpoint().substring(0,4));
		int end = Integer.parseInt(range.upperEndpoint().substring(0,4));
		
		Range<Integer> newRange;
		if(begin == end){
			newRange = Range.singleton(begin);
			
		} else {
			newRange = Range.open(begin, end);
		}

		for (Integer i = newRange.lowerEndpoint(); i <= newRange.upperEndpoint(); i++) {
			if(i >= 2017) {
				result.add("pos_item_log");
			} else {
				result.add("pos_item_log_" + i);
			}
		}

		return result;
	}
}
}
