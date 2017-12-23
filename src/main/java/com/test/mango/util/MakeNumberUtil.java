package com.test.mango.util;

import com.test.mango.contants.MangoContant;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hubian on 23/12/2017.
 */

public class MakeNumberUtil {
    //sdf不是线程安全的
    private static SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
    public static String makeOrderNumber(String merchantNo) {
        String orderNo=
                "P" //系统号
                +"1"//版本号
                +ShardUtil.getShardKeyByID(merchantNo,MangoContant.ORDER_SPLIT_ORDER_COUNT,MangoContant.ORDER_SPLIT_TABLE_COUNT,MangoContant.DB_SHARD_LENGTH,MangoContant.TABLE_SAHRD_LENGTH)//分库分表Shard值
                +sdf.format(new Date())//时间戳
                + RandomCodeUtil.getRandomNum(MangoContant.ORDER_NO_RANDOWM_LENGTH) ;//随机数，可以用数据库或者其他方式生成
        return orderNo;
    }

    public static String getShardByOrderNumber(String orderNo) {
        return orderNo.substring(2,2+MangoContant.DB_SHARD_LENGTH+MangoContant.TABLE_SAHRD_LENGTH);
    }

    public static void main(String[]args){
        for (int i=10000;i<20000;i++){
            String orderNo=makeOrderNumber(String.valueOf(i));
            System.out.println(orderNo);
            System.out.println(getShardByOrderNumber(orderNo));
        }

    }


}

