package com.iqilu;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * 测试类
 *
 * @author zhangyicheng
 * @date 2020/05/20
 */
@RunWith(SpringRunner.class)
//由于是Web项目，Junit需要模拟ServletContext
@WebAppConfiguration
@SpringBootTest(classes = ManagerApplication.class)
public class ManagerApplicationTest {

	@Test
	public void contextLoads() {
	}

	@Before
	public void init() {
		System.out.println("--- 开始测试 ---");
	}

	@After
	public void after() {
		System.out.println("--- 测试结束 ---");
	}

}
