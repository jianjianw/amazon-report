package com.nhsoft.report.provider.sharding;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.nhsoft.module.report.util.DynamicDataSourceContextHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by yangqin on 2017/11/4.
 */
@Configuration
public class PosOrderSharding {
	
	public static String systemBookCodes;
	
	@Value("${sharding.pos_order.book_codes}")
	public void setSystemBookCodes(String systemBookCodes) {
		PosOrderSharding.systemBookCodes = systemBookCodes;
	}

	public static TableRule createTableRule(DataSourceRule dataSourceRule, String bookCodes){
		PosOrderSharding.systemBookCodes = bookCodes;
		List<String> actualTables = new ArrayList<String>();
		actualTables.add("pos_order");
		String[] bookArray = systemBookCodes.split(",");
		for(int i = 0;i < bookArray.length;i++){
			actualTables.add("pos_order_" + bookArray[i]);
		}
		PosOrderSharding.PosOrderAlgorithm keyGeneratorAlgorithm = new PosOrderSharding.PosOrderAlgorithm("pos_order");
		
		return TableRule.builder("pos_order")
				.actualTables(actualTables)
				.dataSourceRule(dataSourceRule)
				.tableShardingStrategy(new TableShardingStrategy("system_book_code", keyGeneratorAlgorithm))
				.databaseShardingStrategy(new DatabaseShardingStrategy("system_book_code", new PosOrderSharding.PosOrderShardingDataBaseAlgorithm()))
				
				.build();
	}
	
	public static TableRule createPaymentTableRule(DataSourceRule dataSourceRule, String bookCodes){
		PosOrderSharding.systemBookCodes = bookCodes;
		List<String> actualTables = new ArrayList<String>();
		actualTables.add("payment");
		String[] bookArray = systemBookCodes.split(",");
		for(int i = 0;i < bookArray.length;i++){
			actualTables.add("payment_" + bookArray[i]);
		}
		PosOrderSharding.PosOrderAlgorithm keyGeneratorAlgorithm = new PosOrderSharding.PosOrderAlgorithm("payment");
		
		return TableRule.builder("payment")
				.actualTables(actualTables)
				.dataSourceRule(dataSourceRule)
				.tableShardingStrategy(new TableShardingStrategy("system_book_code", keyGeneratorAlgorithm))
				.databaseShardingStrategy(new DatabaseShardingStrategy("system_book_code", new PosOrderSharding.PosOrderShardingDataBaseAlgorithm()))
				
				.build();
	}
	
	public static TableRule createPosOrderDetailTableRule(DataSourceRule dataSourceRule, String bookCodes){
		PosOrderSharding.systemBookCodes = bookCodes;
		List<String> actualTables = new ArrayList<String>();
		actualTables.add("pos_order_detail");
		String[] bookArray = systemBookCodes.split(",");
		for(int i = 0;i < bookArray.length;i++){
			actualTables.add("pos_order_detail_" + bookArray[i]);
		}
		PosOrderSharding.PosOrderAlgorithm keyGeneratorAlgorithm = new PosOrderSharding.PosOrderAlgorithm("pos_order_detail");
		
		return TableRule.builder("pos_order_detail")
				.actualTables(actualTables)
				.dataSourceRule(dataSourceRule)
				.tableShardingStrategy(new TableShardingStrategy("ORDER_DETAIL_BOOK_CODE", keyGeneratorAlgorithm))
				.databaseShardingStrategy(new DatabaseShardingStrategy("ORDER_DETAIL_BOOK_CODE", new PosOrderSharding.PosOrderShardingDataBaseAlgorithm()))
				
				.build();
	}
	
	public static TableRule createPosOrderKitDetailTableRule(DataSourceRule dataSourceRule, String bookCodes){
		PosOrderSharding.systemBookCodes = bookCodes;
		List<String> actualTables = new ArrayList<String>();
		actualTables.add("pos_order_kit_detail");
		String[] bookArray = systemBookCodes.split(",");
		for(int i = 0;i < bookArray.length;i++){
			actualTables.add("pos_order_kit_detail_" + bookArray[i]);
		}
		PosOrderSharding.PosOrderAlgorithm keyGeneratorAlgorithm = new PosOrderSharding.PosOrderAlgorithm("pos_order_kit_detail");
		
		return TableRule.builder("pos_order_kit_detail")
				.actualTables(actualTables)
				.dataSourceRule(dataSourceRule)
				.tableShardingStrategy(new TableShardingStrategy("order_kit_detail_book_code", keyGeneratorAlgorithm))
				.databaseShardingStrategy(new DatabaseShardingStrategy("order_kit_detail_book_code", new PosOrderSharding.PosOrderShardingDataBaseAlgorithm()))
				
				.build();
	}
	
	public static class PosOrderShardingDataBaseAlgorithm implements SingleKeyDatabaseShardingAlgorithm<String> {
		
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
	
	
	public static class PosOrderAlgorithm implements SingleKeyTableShardingAlgorithm<String> {
		
		public PosOrderAlgorithm(){
		
		}
		
		public PosOrderAlgorithm(String tableName){
			this.tableName = tableName;
		}
		
		private String tableName;
		
		@Override
		public String doEqualSharding(Collection<String> collection, ShardingValue<String> shardingValue) {
			if(systemBookCodes.contains(shardingValue.getValue())){
				return tableName + "_" + shardingValue.getValue();
			}
			return tableName;
		}
		
		@Override
		public Collection<String> doInSharding(Collection<String> collection, ShardingValue<String> shardingValue) {
			
			return null;
		}
		
		@Override
		public Collection<String> doBetweenSharding(Collection<String> collection, ShardingValue<String> shardingValue) {
			return null;
		}
	}
}
