package com.test.mango.util;

import java.util.Random;

/**
 * Created by hubian on 22/12/2017.
 */
public class RandomCodeUtil {
    private static final String[] RANDOM_NUM={"0","1","2","3","4","5","6","7","8","9"};
    public static String getRandomNum(int length){
        StringBuffer buffer=new StringBuffer();
        Random r = new Random();
        for (int i=0;i<length;i++){
            int random=r.nextInt(RANDOM_NUM.length);
            buffer.append(RANDOM_NUM[random]);
        }
        return buffer.toString();
    }
}
