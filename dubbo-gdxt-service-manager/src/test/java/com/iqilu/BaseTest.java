package com.iqilu;

import com.iqilu.service.HelloService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 测试基类
 *
 * @author zhangyicheng
 * @date 2020/05/21
 */
public class BaseTest extends ManagerApplicationTest {

    @Autowired
    protected HelloService helloService;

    @Test
    public void testAll() {}

}
