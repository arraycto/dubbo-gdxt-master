package com.iqilu;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动
 *
 * @author zhangyicheng
 * @date 2020/05/20
 */
@MapperScan({"com.iqilu.dao.mysqldao"})
@Slf4j
@EnableDubbo
@SpringBootApplication
@NacosPropertySource(dataId = "manager", autoRefreshed = true)
public class ManagerApplication {
    public static void main(String[] args) {
        // 功能: 解决netty冲突.
        System.setProperty("es.set.netty.runtime.available.processors","false");

        SpringApplication.run(ManagerApplication.class, args);
        log.info("project: manager is running !!!!!!");
    }
}
