package com.iqilu.utils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 公共工具类
 *
 * @author zhangyicheng
 * @version 1.1
 * @date 2020/05/20
 */
public class CommonUtils {

    /* ---------------线程处理工具 ------------------ */

    /**
     * 按秒休眠.
     *
     * @param seconds 秒数
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static void sleepSec(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 按毫秒数休眠.
     *
     * @param ms 毫秒数
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static void sleepMs(int ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /* --------------日期时间处理工具----------------- */

    /**
     * 获取系统当前时间
     *
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static String getDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    /**
     * 获取系统当前时间
     *
     * @param dateType 日期格式
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static String getDateTime(String dateType) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateType);
        return sdf.format(new Date());
    }

    /**
     * 获取今天日期及所在一周的周一和周日日期.
     *
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static Map<String, String> getWeekDate() {
        Map<String, String> dateMap = new HashMap<String, String>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");

        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayWeek == 1) {
            dayWeek = 8;
        }
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek);
        Date mondayDate = cal.getTime();
        String weekBegin = sdf.format(mondayDate);

        cal.add(Calendar.DATE, 4 + cal.getFirstDayOfWeek());
        Date sundayDate = cal.getTime();
        String weekEnd = sdf.format(sundayDate);

        dateMap.put("todayDate", sdf.format(cal.getTime()));
        dateMap.put("mondayDate", weekBegin);
        dateMap.put("sundayDate", weekEnd);

        return dateMap;
    }

    /**
     * 获取计算日期的前后具体天数日期
     *
     * @param date     计算的日期 格式: yyyy-MM-dd
     * @param quantity 计算日期的前后天数
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static String lastOrLaterDate(String date, int quantity) {

        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        String nextDay = "";
        try {
            Date temp = dft.parse(date);
            Calendar cld = Calendar.getInstance();
            cld.setTime(temp);
            cld.add(Calendar.DATE, quantity);
            temp = cld.getTime();
            nextDay = dft.format(temp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return nextDay;
    }

    /**
     * 获取过去或未来第几天的日期
     *
     * @param past         第几天日期
     * @param date         日期
     * @param frontAndBack 过去/未来 true/false
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static String getPastOrFutureDate(int past, Date date, boolean frontAndBack) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (frontAndBack) {
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - past);
        } else {
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + past);
        }
        Date today = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(today);
    }

    /**
     * 获取某之前/后 N天的日期
     *
     * @param date         日期 yyyy-MM-dd
     * @param quantity     前天数
     * @param frontAndBack 前/后 true/false
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static ArrayList<String> pastOrFutureDay(String date, int quantity, boolean frontAndBack) {
        ArrayList<String> pastDaysList = new ArrayList<>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = sdf.parse(date);
            for (int i = quantity - 1; i >= 0; i--) {
                pastDaysList.add(getPastOrFutureDate(i, d, frontAndBack));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!frontAndBack) {
            Collections.reverse(pastDaysList);
            return pastDaysList;
        }
        return pastDaysList;
    }

    /* ----------数据类型处理工具---------- */

    /**
     * 获取UUID
     *
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static String getUuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 设置Float保留的位数
     *
     * @param number 输入的Float值
     * @param digit  格式化成Float的格式
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static float floatDigit(float number, String digit) {
        // 设置保留位数
        DecimalFormat df = new DecimalFormat(digit);
        return number;
    }

    /**
     * 设置double保留的位数
     *
     * @param number 输入的double值
     * @param digit  格式化成double的格式
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static double doubleDigit(double number, String digit) {
        // 设置保留位数
        DecimalFormat df = new DecimalFormat(digit);
        return number;
    }

    /* --------------文件、流等处理工具----------------- */

    /**
     * 创建文件
     *
     * @param filePath 文件路径 + 文件名称
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static boolean createFile(String filePath) {
        File f = new File(filePath);
        try {
            return f.createNewFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * 文件/目录重命名
     *
     * @param oldPath 老名称
     * @param newPath 新名称
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static boolean renameFileOrDir(String oldPath, String newPath) {
        File oldFile = new File(oldPath);
        return oldFile.renameTo(new File(newPath));
    }

    /**
     * 单个文件的删除
     *
     * @param filePath 文件路径
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static boolean deleteFile(String filePath) {
        File f = new File(filePath);
        if (f.isFile() && f.exists()) {
            return f.delete();
        }
        return false;
    }

    /**
     * 创建一级目录
     *
     * @param dirName 目录名
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static boolean createDir(String dirName) {
        File dir = new File(dirName);
        if (!dir.exists()) {
            return dir.mkdir();
        }
        return false;
    }

    /**
     * 创建多级目录
     *
     * @param dirName 目录名
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static boolean createDirs(String dirName) {
        File dir = new File(dirName);
        if (!dir.exists()) {
            return dir.mkdirs();
        }
        return false;
    }

    /**
     * 删除目录
     *
     * @param dirName 目录名
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static boolean deleteEmptyDir(String dirName) {
        File dir = new File(dirName);
        if (dir.isDirectory()) {
            return dir.delete();
        }
        return false;
    }

    /**
     * 递归删除目录
     *
     * @param dirName 目录名
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static void deleteDirs(String dirName) {
        File dir = new File(dirName);
        File[] dirs = dir.listFiles();
        for (int i = 0; dirs != null && i < dirs.length; i++) {
            File f = dirs[i];
            // 如果是文件直接删除
            if (f.isFile()) {
                f.delete();
            }
            // 如果是目录继续遍历删除
            if (f.isDirectory()) {
                deleteDirs(f.getAbsolutePath());
            }
        }
        // 删除本身
        dir.delete();
    }

    /**
     * 获取项目绝对路径路径
     *
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public String projectPath() {
        File f = new File(this.getClass().getResource("/").getPath());
        return String.valueOf(f);
    }

    /**
     * 获取当前类的所在工程路径
     *
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public String allPath() {
        File f2 = new File(this.getClass().getResource("").getPath());
        return String.valueOf(f2);
    }


    /**
     * 测试
     *
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static void main(String[] args) {

    }

}
