package com.msb.hjycommunity.log;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LogDemo {
    Logger logger = LoggerFactory.getLogger(LogDemo.class);

    @Test
    public void test1() {
        logger.info("Hello LogBack!");
    }

    @Test
    public void test2() {
        logger.trace("这个级别很少有，主要是用来追踪！！");
        logger.debug("经常写BUG的程序员测试的时候，多打印");
        logger.info("系统日志");
        logger.warn("这个错误很少见,不影响程序继续运行,酌情处理");
        logger.error("发生了严重错误,程序阻断了,需要立即处理,发送警报");
    }

    /**
     * Slf4j注解方式实现日志打印
     */
    @Test
    public void test3() {
        log.trace("这个级别很少有，主要是用来追踪！！");
        log.debug("经常写BUG的程序员测试的时候，多打印");
        log.info("系统日志");
        log.warn("这个错误很少见,不影响程序继续运行,酌情处理");
        log.error("发生了严重错误,程序阻断了,需要立即处理,发送警报");
    }

    /**
     * 日志打印的规范
     * 1. 使用log输出Exception的全部信息
     * 2. 增加对低级别日志的判断
     * 3. 尽量使用占位符，而不是拼接方式
     */
    @Test
    public void test4() {
        String name = "我是大佬";
        log.info("hello " + name);
        log.debug("hello {}", name);    // 推荐使用

        String userId = "100010";
        String orderId = "3242343253253535";
        log.debug("记录当前订单的 userId: [{}] 和 orderId: [{}]", userId, orderId);

        // 使用log输出Exception的全部信息
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            // e.printStackTrace();
            log.error("/ by zero", e);
        }

        // 增加对低级别日志的判断
        // 先拼接字符串“hello”和“name”。然后执行debug方法,判断日志级别
        log.debug("hello " + name);
        // 执行顺序：不执行，先判断日志级别，可以避免无用的字符串操作，提高性能s
        if (log.isDebugEnabled()) {
            log.debug("hello " + name);
        }

        /**
         * 日志打印的注意事项
         * 尽量不要在日志中调用方法获取值，否则会因为日志而报出空指针异常。(日志不能打断业务逻辑)
         * 上线后除了进行常规的测试外，应可以通过对日志进行观察来判断新功能是否工作正常。
         * 对不同类型的日志进行分类输出，比如ERROR日志单独输出，防止日志数量过大时不利于分析错误信息。
         * 日志文件一般保留周期为15天，防止以周为单位的错误出现，应设置定时任务删除过期日志（如15天前）
         */
    }
}
