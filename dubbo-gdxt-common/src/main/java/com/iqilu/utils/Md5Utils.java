package com.iqilu.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密算法
 *
 * @author zhangyicheng
 * @date 2020/05/20
 */
public class Md5Utils {

    /**
     * 对字符串md5加密(小写+字母)
     *
     * @param str 传入要加密的字符串
     * @return 返回固定长度字符串, MD5加密后的字符串
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static String getMd5(String str) {

        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] b = md.digest();
            int i;
            StringBuilder buf = new StringBuilder("");
            for (byte value : b) {
                i = value;
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 测试
     *
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static void main(String[] args) {
        String res = getMd5("54321");
        System.out.println(res);

    }

}
