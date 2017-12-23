package com.test.mango.contants;

/**
 * Created by hubian on 23/12/2017.
 */
public class MangoContant {
    //订单分库分表最大容量，一旦确定，不能修改
    public static final int ORDER_SPLIT_TABLE_COUNT=10;
    public static final int ORDER_SPLIT_ORDER_COUNT=8;
    public static final int ORDER_NO_RANDOWM_LENGTH=4;

    //db和表占用长度。2字段即最多99库，99表。足够用了
    public static final int DB_SHARD_LENGTH=2;
    public static final int TABLE_SAHRD_LENGTH=2;
}
