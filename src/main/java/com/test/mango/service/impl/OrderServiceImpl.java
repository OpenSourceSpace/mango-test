package com.test.mango.service.impl;

import com.test.mango.dao.PayOrderDao;
import com.test.mango.pojo.PayOrder;
import com.test.mango.service.OrderService;
import com.test.mango.util.MakeNumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by hubian on 23/12/2017.
 */
@Service
public class OrderServiceImpl implements OrderService{
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private PayOrderDao dao;
    @Override
    public String makeOrder(String merchant, String merchantOrderNo, BigDecimal amount) {
        PayOrder order=new PayOrder();
        order.setAmount(amount);
        order.setMerchant(merchant);
        order.setMerchantOrderNo(merchantOrderNo);
        order.setOrderNo(MakeNumberUtil.makeOrderNumber(merchant));
        logger.info("try to save:{}",order.toString());
        dao.addPayOrder(MakeNumberUtil.getShardByOrderNumber(order.getOrderNo()),order);
        return order.getOrderNo();
    }

    @Override
    public PayOrder findOrder(String orderNo) {
       return dao.getPayOrder(MakeNumberUtil.getShardByOrderNumber(orderNo),orderNo);
    }
}
