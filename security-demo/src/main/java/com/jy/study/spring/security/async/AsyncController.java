package com.jy.study.spring.security.async;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class AsyncController {

    private static final Logger logger = LoggerFactory.getLogger(AsyncController.class);

    @Autowired
    private MockQueue mockQueue;
    @Autowired
    private DeferredResultHolder deferredResultHolder;

    //1. runnable异步处理Rest服务
//    @RequestMapping("/order")
//    public Callable<String> order() throws InterruptedException {
//        logger.info("主线程开始");
//        Callable<String> result = () -> {
//            logger.info("子线程开始");
//            Thread.sleep(2000);
//            logger.info("子线程结束");
//            return "place order success";
//        };
//        logger.info("主线程返回");
//        return result;
//    }

    //2. deferred result 异步处理 Rest 服务
    @RequestMapping("/order")
    public DeferredResult<String> order() throws InterruptedException {
        logger.info("主线程开始");
        String orderNum = RandomStringUtils.randomNumeric(8);
        mockQueue.setPlaceOrder(orderNum);
        DeferredResult<String> result = new DeferredResult<>();
        deferredResultHolder.getMap().put(orderNum, result);
        logger.info("主线程返回");
        return result;
    }

}
