package com.test.mango.dao;

import com.test.mango.pojo.PayOrder;
import com.test.mango.shard.MangoTestShardingStrategy;
import org.jfaster.mango.annotation.*;

@DB(table = "MANGO_ORDER")
@Sharding(shardingStrategy = MangoTestShardingStrategy.class)
public interface PayOrderDao {
    String ALL_COLUMNS = "ORDER_NO, MERCHANT, MERCHANT_ORDER_NO, AMOUNT,CREATE_DATE, UPDATE_DATE";
    String INSERT_COLUMNS = "ORDER_NO, MERCHANT, MERCHANT_ORDER_NO,AMOUNT";
    @SQL("insert into #table(" + INSERT_COLUMNS + ") values(:orderNo, :merchant, :merchantOrderNo,:amount)")
    int addPayOrder(@ShardingBy String shardKey, PayOrder payOrder);

    @SQL("select " + ALL_COLUMNS + " from #table where ORDER_NO = :2")
    PayOrder getPayOrder(@ShardingBy String shardKey, String lePayOrderNo);
}
