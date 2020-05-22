package com.iqilu.config;

import com.iqilu.dao.esdao.EsPostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 在Bean完成注入后执行的初始化操作..
 *
 * @author zhangyicheng
 * @date 2020/05/22
 */
@Component
@Slf4j
public class Initialize {

    @Autowired
    EsPostRepository esPostRepository;

    @PostConstruct
    public void init() {

        log.info("在Bean完成注入后执行的初始化操作..");

    }

}
