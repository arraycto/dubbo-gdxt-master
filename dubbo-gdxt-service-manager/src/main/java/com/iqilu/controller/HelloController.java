package com.iqilu.controller;


import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.iqilu.bean.ddo.EsManagerDO;
import com.iqilu.dao.esdao.EsManagerRepository;
import com.iqilu.service.HelloService;
import com.iqilu.tools.RedisUtils;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 连接测试专用
 * 说明: 目录以Hello开头的均为功能连接测试专用, 业务测试,性能测试写入test目录下.
 *
 * @author zhangyicheng
 * @date 2020/05/20
 */
@RestController
@RequestMapping(value = "test")
@Slf4j
public class HelloController {

    @Autowired
    HelloService helloService;

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    EsManagerRepository esManagerRepository;

    /**
     * Nacos动态配置
     */
    @NacosValue(value = "${useLocalCache:false}", autoRefreshed = true)
    private boolean useLocalCache;

    /**
     * 测试MySQL的连接
     *
     * @return 返回mysql连接成功信息
     */
    @RequestMapping(value = "mysql", method = RequestMethod.GET)
    public String helloMysql() {
        return helloService.helloMysql();
    }

    /**
     * 测试redis的连接
     *
     * @author zhangyicheng
     * @date 2020/05/21
     */
    @RequestMapping(value = "redis", method = RequestMethod.GET)
    public String helloRedis() {
        redisUtils.set("redisKey", "Hello Redis");
        return "";
    }

    /**
     * 测试ElasticSearch的连接
     *
     * @author zhangyicheng
     * @date 2020/05/21
     */
    @RequestMapping(value = "es", method = RequestMethod.GET)
    public Map<String, Object> helloElasticSearch(String title) {

        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder.should(QueryBuilders.matchPhraseQuery("title", title));
        Page<EsManagerDO> search = (Page<EsManagerDO>) esManagerRepository.search(builder);
        List<EsManagerDO> content = search.getContent();
        Map<String, Object> map = new HashMap<>();
        map.put("list", content);

        return map;
    }

    /**
     * RabbitMQ生产测试接口
     *
     * @author zhangyicheng
     * @date 2020/05/21
     */
    @RequestMapping(value = "rabbitSend", method = RequestMethod.GET)
    public void helloRabbitProduction() throws Exception {
        Map<String, Object> properties = new HashMap<>(100);
        properties.put("number", "12345");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        properties.put("send_time", simpleDateFormat.format(new Date()));
        helloService.send("Hello RabbitMQ For Spring Boot!", properties);
    }

    /**
     * RabbitMQ消费队列的数据
     *
     * @author zhangyicheng
     * @date 2020/05/21
     */
    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(
            // 指定队列名称, 是否持久化
            value = @Queue(value = "queue-1", durable = "true"),
            // 指定路由名称, 是否持久化, 类型, 是否过滤, 指定路由key
            exchange = @Exchange(value = "exchange-1", durable = "true",
                    type = "topic", ignoreDeclarationExceptions = "true"), key = "springboot.*")
    )
    public void onMessage(Message message, Channel channel) throws Exception {
        // 消费端收到消费体内容
        System.out.println("消费端接收: " + message.getPayload());
        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        if (deliveryTag != null) {
            // 手工签收ACK
            channel.basicAck(deliveryTag, false);
        }
    }

    /**
     * Nacos动态配置
     *
     * @author zhangyicheng
     * @date 2020/05/21
     */
    @RequestMapping(value = "/nacosDynamic", method = RequestMethod.GET)
    @ResponseBody
    public boolean get() {
        return useLocalCache;
    }


}
