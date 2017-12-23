
package com.mango.test;

import com.test.mango.pojo.PayOrder;
import com.test.mango.service.OrderService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;

public class PayOrderTest extends AnnotationContextBaseTest {
    private static final Logger logger = LoggerFactory.getLogger(PayOrderTest.class);

    @Autowired
    private OrderService orderService;

    @Test
    public void testInsert(){
        String merchant1="1122313";
        String merchantOrderNo1=String.valueOf(System.currentTimeMillis());
        String orderNo1=orderService.makeOrder(merchant1,merchantOrderNo1,new BigDecimal(100));
        PayOrder order1=orderService.findOrder(orderNo1);
        System.out.println(orderNo1+":"+order1);

        String merchant2="2323332";
        String merchantOrderNo2=String.valueOf(System.currentTimeMillis());
        String orderNo2=orderService.makeOrder(merchant2,merchantOrderNo2,new BigDecimal(100));
        PayOrder order2=orderService.findOrder(orderNo2);
        System.out.println(orderNo2+":"+order2);


    }

}