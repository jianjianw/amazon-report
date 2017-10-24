package com.nhsoft.module.report.sharding;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.google.common.collect.Range;
import com.nhsoft.module.report.util.DateUtil;

import java.util.*;

/**
 * Created by yangqin on 2017/10/20.
 */
public class AlipayLogSharding {
	
	public static TableRule createTableRule(DataSourceRule dataSourceRule){
		
		List<String> actualTables = new ArrayList<String>();
		actualTables.add("alipay_log");
		actualTables.add("alipay_log_history");
		
		return TableRule.builder("alipay_log")
				.actualTables(actualTables)
				.dataSourceRule(dataSourceRule)
				.tableShardingStrategy(new TableShardingStrategy("alipay_log_start", new AlipayLogSharding.AlipayLogShardingAlgorithm()))
				.build();
	}
	
	public static class AlipayLogShardingAlgorithm implements SingleKeyTableShardingAlgorithm<Date> {
		
		@Override
		public String doEqualSharding(Collection<String> collection, ShardingValue<Date> shardingValue) {
			
			return "alipay_log";
		}
		
		@Override
		public Collection<String> doInSharding(Collection<String> collection, ShardingValue<Date> shardingValue) {
			Collection<String> result = new LinkedHashSet<String>(1);
			result.add("alipay_log");
			return result;
		}
		
		@Override
		public Collection<String> doBetweenSharding(Collection<String> collection, ShardingValue<Date> shardingValue) {
			Range<Date> range = (Range<Date>) shardingValue.getValueRange();
			
			Date dateFrom = range.lowerEndpoint();
			Date dateTo = range.upperEndpoint();
			Collection<String> result = new LinkedHashSet<String>(0);
			Date now = DateUtil.getMinOfDate(Calendar.getInstance().getTime());
			Date limitDate = DateUtil.addMonth(now, -1);
			if(dateTo.compareTo(limitDate) < 0){
				result.add("alipay_log_history");
			} else if(dateFrom.compareTo(limitDate) >= 0){
				result.add("alipay_log");
			} else {
				result.add("alipay_log_history");
				result.add("alipay_log");
				
			}
			return result;
		}
	}
}
