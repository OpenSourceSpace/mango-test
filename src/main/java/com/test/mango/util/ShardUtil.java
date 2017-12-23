package com.test.mango.util;


import com.test.mango.contants.MangoContant;

public class ShardUtil {

    public static String getShardKeyByID(String originID,int dbCount,int tableCount,int dbShardLength,int tableShardLength) {
        long hash = HashUtil.fnv1_31(originID);
        long a=hash%tableCount;
        long b=hash%dbCount;
        String shardTable="000"+a;
        String shardDB="000"+b;
        return shardDB.substring(shardDB.length()- dbShardLength)+
                shardTable.substring(shardTable.length()- tableShardLength);
    }

    public static String getShardKeyById(String id) {
        return id.substring(1, 3);
    }

    public static void main(String[] args) {

        for (int i=10000;i<20000;i++){
            System.out.println(i+ ":"+getShardKeyByID(String.valueOf(i),8,10,2,2));
        }
        for (int i=10000;i<20000;i++){
            System.out.println(i+ ":"+getShardKeyByID(String.valueOf(i),8,100,2,2));
        }
        for (int i=0;i<10;i++){
            System.out.println(i+ ":"+getShardKeyByID("1122313",8,100,2,2));
        }

    }
}
