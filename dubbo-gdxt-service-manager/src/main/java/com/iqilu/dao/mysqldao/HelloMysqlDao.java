package com.iqilu.dao.mysqldao;

import org.springframework.stereotype.Repository;

/**
 * 连接测试专用
 *
 * @author zhangyicheng
 * @date 2020/05/20
 */
@Repository
public interface HelloMysqlDao {

    /**
     * mysql连接测试
     *
     * @return 字符串数字
     * @date 2020/05/20
     */
    String helloMysql();
}
