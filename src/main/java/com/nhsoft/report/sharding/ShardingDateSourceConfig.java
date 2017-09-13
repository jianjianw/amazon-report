package com.nhsoft.report.sharding;

import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Map;

public class ShardingDateSourceConfig {
    public static DataSource getDateSource(Map customDataSources){
        DataSourceRule dataSourceRule = new DataSourceRule(customDataSources, "ama");
        TableRule orderTableRule = TableRule.builder("alipay_log")
                .actualTables(Arrays.asList("alipay_log", "alipay_log_history"))
                .dataSourceRule(dataSourceRule)
                .build();
        ShardingRule shardingRule = ShardingRule.builder()
                .dataSourceRule(dataSourceRule)
                .tableRules(Arrays.asList(orderTableRule))
                .tableShardingStrategy(new TableShardingStrategy("alipay_log_start", new AlipayLogTableShardingAlgorithm())).build();
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(shardingRule);
        return dataSource;
    }
}
