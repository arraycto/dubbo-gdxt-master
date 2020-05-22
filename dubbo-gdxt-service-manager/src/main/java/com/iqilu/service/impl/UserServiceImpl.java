package com.iqilu.service.impl;

import com.iqilu.bean.ddo.UserDO;
import com.iqilu.dao.mysqldao.UserDao;
import com.iqilu.service.UserService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户相关操作
 *
 * @author zhangyicheng
 * @date 2020/05/21
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    /**
     * Token登记
     *
     * @author zhangyicheng
     * @date 2020/05/21
     * @param user 用户名
     * @return 用户信息
     */
    @Override
    public UserDO findByUserName(UserDO user) {
        return userDao.findByUserName(user.getUserName());
    }


    /**
     * Token认证数据库
     *
     * @author zhangyicheng
     * @date 2020/05/21
     * @param userId 用户编号
     * @return 用户信息
     */
    @Override
    public UserDO findUserById(String userId) {
        return userDao.findUserById(userId);
    }
}
