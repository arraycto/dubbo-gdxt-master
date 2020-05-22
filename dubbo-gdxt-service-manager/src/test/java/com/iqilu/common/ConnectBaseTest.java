package com.iqilu.common;

import com.iqilu.BaseTest;
import org.junit.Test;

/**
 * 连接测试
 *
 * @author zhangyicheng
 * @date 2020/05/22
 */
public class ConnectBaseTest extends BaseTest {

    /**
     * mysql连接测试
     *
     * @author zhangyicheng
     * @date 2020/05/22
     */
    @Test
    public void testCase() {
        String str = helloService.helloMysql();
        assert str.equals("1");
    }

    // TODO: 编写相关连接测试方法..

}
