package com.test.mango.service;

import com.test.mango.pojo.PayOrder;

import java.math.BigDecimal;

/**
 * Created by hubian on 23/12/2017.
 */
public interface OrderService {
    public String makeOrder(String merchant, String merchantOrderNo, BigDecimal amount);
    public PayOrder findOrder(String orderNo);
}
