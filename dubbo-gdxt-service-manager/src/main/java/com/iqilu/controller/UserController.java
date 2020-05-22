package com.iqilu.controller;

import com.alibaba.fastjson.JSONObject;
import com.iqilu.bean.ddo.UserDO;
import com.iqilu.config.annotation.UserLoginToken;
import com.iqilu.service.UserService;
import com.iqilu.tools.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户相关操作
 *
 * @author zhangyicheng
 * @date 2020/05/21
 */
@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 登录获取Token
     * @author zhangyicheng
     * @date 2020/05/12
     */
    @PostMapping("/login")
    public Object login(UserDO user) {
        JSONObject jsonObject = new JSONObject();
        UserDO userForBase = userService.findByUserName(user);
        if (userForBase == null) {
            jsonObject.put("message", "登录失败,用户不存在");
            return jsonObject;
        } else {
            if (!userForBase.getPassWord().equals(user.getPassWord())) {
                jsonObject.put("message", "登录失败,密码错误");
                return jsonObject;
            } else {
                String token = TokenUtils.getToken(userForBase);
                jsonObject.put("token", token);
                jsonObject.put("user", userForBase);
                return jsonObject;
            }
        }
    }

    /**
     * 不加@UserLoginToken不验证Token
     *
     * @author zhangyicheng
     * @date 2020/05/21
     */
    @UserLoginToken
    @GetMapping("/getMessage")
    public String getMessage() {
        return "你已通过验证 !";
    }

}
