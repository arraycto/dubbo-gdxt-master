package com.iqilu.service;

import com.iqilu.bean.ddo.UserDO;

/**
 * 用户相关操作
 *
 * @author zhangyicheng
 * @date 2020/05/21
 */

public interface UserService {

    /**
     * Token登记
     *
     * @param user 用户信息
     * @return 用户信息
     * @author zhangyicheng
     * @date 2020/05/21
     */
    UserDO findByUserName(UserDO user);

    /**
     * Token认证数据库
     *
     * @param userId 用户编号
     * @return 用户信息
     * @author zhangyicheng
     * @date 2020/05/21
     */
    UserDO findUserById(String userId);
}
