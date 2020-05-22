package com.iqilu.utils;


import java.io.*;

/**
 * 序列化工具
 *
 * @author zhangyicheng
 * @date 2020/05/22
 */
public class SerializableUtils {

    /**
     * java对象序列化成字节数组
     *
     * @author zhangyicheng
     * @date 2020/05/22
     */
    public static byte[] toBytes(Object object) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(object);
            return baos.toByteArray();
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    /**
     * 字节数组反序列化成java对象
     *
     * @author zhangyicheng
     * @date 2020/05/22
     */
    public static Object toObject(byte[] bytes) {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        try (ObjectInputStream ois = new ObjectInputStream(bais)) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

}
