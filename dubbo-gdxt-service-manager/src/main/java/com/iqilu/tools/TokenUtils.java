package com.iqilu.tools;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.iqilu.bean.ddo.UserDO;

import java.util.Date;

/**
 * 获取Token
 *
 * @author zhangyicheng
 * @date 2020/05/21
 */
public class TokenUtils {

    /**
     * Token过期时间
     */
    private static final int EXPIRE_TIME = 7200000;

    public static String getToken(UserDO user) {
        Date expiresDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        String token = "";
        // 将用户id保存到token里面 | 签名时间 | 过期时间 | 以密码作为token的密钥
        token = JWT.create().withAudience(user.getId())
                .withIssuedAt(new Date())
                .withExpiresAt(expiresDate)
                .sign(Algorithm.HMAC256(user.getPassWord()));
        return token;
    }

}
