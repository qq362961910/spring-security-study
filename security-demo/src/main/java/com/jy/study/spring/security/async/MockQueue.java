package com.jy.study.spring.security.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MockQueue {

    private static final Logger logger = LoggerFactory.getLogger(MockQueue.class);

    private String placeOrder;

    private String completeOrder;

    public String getPlaceOrder() {
        return placeOrder;
    }

    public void setPlaceOrder(String placeOrder) {
        new Thread(() -> {
            logger.info("接到下单请求: " + placeOrder);
            try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
            this.completeOrder = placeOrder;
            logger.info("下单处理请求完毕: " + placeOrder);
        }).start();
    }

    public String getCompleteOrder() {
        return completeOrder;
    }

    public void setCompleteOrder(String completeOrder) {
        this.completeOrder = completeOrder;
    }
}
