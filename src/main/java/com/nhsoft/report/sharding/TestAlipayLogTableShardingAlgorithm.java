package com.nhsoft.report.sharding;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.google.common.collect.Range;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedHashSet;

public class TestAlipayLogTableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<String> {


    public String doEqualSharding(Collection<String> collection, ShardingValue<String> shardingValue) {

        String value = shardingValue.getValue();
        String month = value.substring(5, 7);
        int intMonth = Integer.parseInt(month);
        Calendar calendar = Calendar.getInstance();
        int crrentMonth = calendar.get(Calendar.MONTH) + 1;
        if (intMonth < crrentMonth && crrentMonth - intMonth > 3) {
            return "alipay_log_history";
        } else {
            return "alipay_log";
        }
    }

    public Collection<String> doInSharding(Collection<String> collection, ShardingValue<String> shardingValue) {
        return null;
    }

    public Collection<String> doBetweenSharding(Collection<String> collection, ShardingValue<String> shardingValue) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        int crrentMonth = calendar.get(Calendar.MONTH)+1;
        Collection<String> result = new LinkedHashSet<String>(collection.size());
        Range<String> range = shardingValue.getValueRange();
        int begin = Integer.parseInt(range.lowerEndpoint().substring(5,7));
        int end = Integer.parseInt(range.upperEndpoint().substring(5,7));
        Range<Integer> newRange;
        if(begin == end){
            newRange = Range.singleton(begin);
        } else {
            newRange = Range.open(begin, end);
        }
        for (Integer i = newRange.lowerEndpoint(); i <= newRange.upperEndpoint(); i++) {
            if(i<crrentMonth && crrentMonth-i>3){
                result.add("alipay_log_history");
            }else{
                result.add("alipay_log");
            }
        }
        return result;
    }
}
