package com.iqilu.dao.mysqldao;

import com.iqilu.bean.ddo.UserDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 用户相关操作
 *
 * @author zhangyicheng
 * @date 2020/05/21
 */
@Repository
public interface UserDao {

    /**
     * Token登记
     *
     * @author zhangyicheng
     * @date 2020/05/21
     * @param username 用户名
     * @return 用户信息
     */
    UserDO findByUserName(@Param("username") String username);

    /**
     * Token认证数据库
     *
     * @author zhangyicheng
     * @date 2020/05/21
     * @param id 用户编号
     * @return 用户信息
     */
    UserDO findUserById(@Param("id") String id);
}
