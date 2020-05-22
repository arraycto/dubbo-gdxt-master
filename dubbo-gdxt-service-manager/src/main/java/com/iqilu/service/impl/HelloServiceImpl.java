package com.iqilu.service.impl;

import com.iqilu.dao.mysqldao.HelloMysqlDao;
import com.iqilu.service.HelloService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Map;

/**
 * 连接测试专用
 *
 * @author zhangyicheng
 * @date 2020/05/21
 */
@Service
public class HelloServiceImpl implements HelloService {

    @Autowired
    HelloMysqlDao helloMysqlDao;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * MySQL连接测试
     *
     * @return 返回mysql连接成功信息
     * @date 2020/05/21
     */
    @Override
    public String helloMysql() {
        return helloMysqlDao.helloMysql();
    }

    /**
     * 发送消息
     *
     * @date 2020/05/21
     */
    @Override
    public void send(Object message, Map<String, Object> properties) {

        MessageHeaders mhs = new MessageHeaders(properties);
        Message msg = MessageBuilder.createMessage(message, mhs);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        // 此处是伪代码, 正式时使用id + 时间戳,全局唯一
        CorrelationData cd = new CorrelationData("1234567890");
        rabbitTemplate.convertAndSend("exchange-1", "springboot.hello", msg);
    }

    /**
     * 实现一个监听器用户监听Broker端给我们返回的确认请求 - 1
     * 注意一点, 在发消息的时候对template进行配置mandatory=true保证监听有效
     * 回调函数: confirm确认
     *
     * @date 2020/05/21
     */
    private final RabbitTemplate.ConfirmCallback confirmCallback = (correlationData, ack, cause) -> {
        /// System.out.println("correlationData: " + correlationData);
        System.out.println("ACK: " + ack);
        // 如果ack为false则进行补偿措施
        if (!ack) {
            System.out.println("ACK返回的为False, 异常处理...");
        }
        // TODO: 添加处理可靠性投递逻辑
    };

    /**
     * 保证消息对Broker端是可达的, 如果出现路由键不可达的情况,
     * 则使用监听器对不可达的消息进行后续的处理, 保证消息的路由成功; 回调函数: return返回 如果路由不存在调用
     *
     * @date 2020/05/21
     */
    private final RabbitTemplate.ReturnCallback returnCallback = (
            message, replyCode, replyText, exchange, routingKey) ->
            System.out.println("return exchange: " + exchange + ", routingKey: "
            + routingKey + ", replyCode: " + replyCode + ", replyText: " + replyText);

}
