package com.iqilu.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

/**
 * 定时任务
 *
 * @author zhangyicheng
 * @date 2020/05/20
 */
@RestController
@Slf4j
public class ScheduledUtils {


    @Scheduled(cron = "00 43 22 13 * ?")
    public void scheduledTest() {
        log.info("任务执行(测试)...");
    }

}
