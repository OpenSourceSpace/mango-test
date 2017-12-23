package com.test.mango.pojo;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * Created by hubian on 23/12/2017.
 */
public class BasePojo implements Serializable {
    @Override
    public String toString(){
        return JSONObject.toJSONString(this);
    }
}
