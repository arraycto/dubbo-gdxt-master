package com.iqilu.service;

import java.util.Map;

/**
 * 连接测试专用
 *
 * @author zhangyicheng
 * @date 2020/05/20
 */
public interface HelloService {

    /**
     * Mysql连接测试
     *
     * @author zhangyicheng
     * @date 2020/05/20
     * @return 返回测试ID信息
     */
    String helloMysql();

    /**
     * Rabbit发送消息
     *
     * @author zhangyicheng
     * @date 2020/05/21
     * @param message 发送的消息
     * @param properties 发送的键值对内容
     */
    void send(Object message, Map<String, Object> properties);

}
