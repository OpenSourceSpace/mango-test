package com.test.mango.shard;

import com.test.mango.service.impl.OrderServiceImpl;
import org.jfaster.mango.sharding.ShardingStrategy;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

/**
 * Created by hubian on 23/12/2017.
 */
public class MangoTestShardingStrategy implements ShardingStrategy<String, String> {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MangoTestShardingStrategy.class);

    @Override
    public String getDataSourceFactoryName(String shardID) {

        //String dbname="dsf0";//返回固定库
        String dbname="dsf"+String.valueOf(Integer.valueOf(shardID.substring(0,2))%2);//2个库
        logger.info("-----------db:"+dbname);
        return dbname;
    }

    @Override
    public String getTargetTable(String table, String shardID) {
        //String tablename=table+"_0";//放到一个表里
        String tablename=table+"_"+String.valueOf(Integer.valueOf(shardID.substring(2,4))%10);//10个表
        logger.info("-----------table:"+tablename);
        return tablename;
    }

}