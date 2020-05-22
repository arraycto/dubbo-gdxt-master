package com.iqilu.bean.ddo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 验证登录系统身份使用
 *
 * @author zhangyicheng
 * @date 2020/05/21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDO {
    String id;
    String userName;
    String passWord;
}