package com.jgg.games.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.jgg.games.utils.constant.TimeConstant;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/2
 *     desc  : 时间相关工具类
 * </pre>
 */
public class TimeUtils {

    private TimeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public final static String PATTERN_YEAR_MONTH_DAY = "yyyy-MM-dd";
    public final static String PATTERN_MMDDHHMM = "MM月dd日 HH:mm";

    /**
     * 将时间戳转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param millis 毫秒时间戳
     * @return 时间字符串
     */
    public static String millis2String(long millis) {
        return new SimpleDateFormat(DEFAULT_PATTERN, Locale.getDefault()).format(new Date(millis));
    }

    /**
     * 将时间戳转为时间字符串
     * <p>格式为pattern</p>
     *
     * @param millis  毫秒时间戳
     * @param pattern 时间格式
     * @return 时间字符串
     */
    public static String millis2String(long millis, String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(new Date(millis));
    }

    /**
     *  987134 转日期
     * @param millis
     * @param pattern
     * @return
     */
    public static String millis2String(String millis, String pattern) {
        if (StringUtils.isEmpty(millis)){
            return "";
        }
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(new Date(Long.parseLong(millis)));
    }


    /**
     * 将时间字符串转为时间戳
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 毫秒时间戳
     */
    public static long string2Millis(String time) {
        return string2Millis(time, DEFAULT_PATTERN);
    }

    /**
     * 将时间字符串转为时间戳
     * <p>time格式为pattern</p>
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return 毫秒时间戳
     */
    public static long string2Millis(String time, String pattern) {
        try {
            return new SimpleDateFormat(pattern, Locale.getDefault()).parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 将时间字符串转为Date类型
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return Date类型
     */
    public static Date string2Date(String time) {
        return string2Date(time, DEFAULT_PATTERN);
    }

    /**
     * 将时间字符串转为Date类型
     * <p>time格式为pattern</p>
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return Date类型
     */
    public static Date string2Date(String time, String pattern) {
        return new Date(string2Millis(time, pattern));
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param date Date类型时间
     * @return 时间字符串
     */
    public static String date2String(Date date) {
        return date2String(date, DEFAULT_PATTERN);
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为pattern</p>
     *
     * @param date    Date类型时间
     * @param pattern 时间格式
     * @return 时间字符串
     */
    public static String date2String(Date date, String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(date);
    }

    /**
     * 将Date类型转为时间戳
     *
     * @param date Date类型时间
     * @return 毫秒时间戳
     */
    public static long date2Millis(Date date) {
        return date.getTime();
    }

    /**
     * 将时间戳转为Date类型
     *
     * @param millis 毫秒时间戳
     * @return Date类型时间
     */
    public static Date millis2Date(long millis) {
        return new Date(millis);
    }

    /**
     * 获取两个时间差（单位：unit）
     * <p>time0和time1格式都为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time0 时间字符串0
     * @param time1 时间字符串1
     * @param unit  单位类型
     *              </ul>
     * @return unit时间戳
     */
    public static long getTimeSpan(String time0, String time1, @TimeConstant.Unit int unit) {
        return getTimeSpan(time0, time1, unit, DEFAULT_PATTERN);
    }

    /**
     * 获取两个时间差（单位：unit）
     * <p>time0和time1格式都为format</p>
     *
     * @param time0   时间字符串0
     * @param time1   时间字符串1
     * @param unit    单位类型
     *                <ul>
     *                <li>{@link TimeConstant#MSEC}: 毫秒</li>
     *                <li>{@link TimeConstant#SEC }: 秒</li>
     *                <li>{@link TimeConstant#MIN }: 分</li>
     *                <li>{@link TimeConstant#HOUR}: 小时</li>
     *                <li>{@link TimeConstant#DAY }: 天</li>
     *                </ul>
     * @param pattern 时间格式
     * @return unit时间戳
     */
    public static long getTimeSpan(String time0, String time1, @TimeConstant.Unit int unit, String pattern) {
        return ConvertUtils.millis2TimeSpan(Math.abs(string2Millis(time0, pattern) - string2Millis(time1, pattern)), unit);
    }

    /**
     * 获取两个时间差（单位：unit）
     *
     * @param date0 Date类型时间0
     * @param date1 Date类型时间1
     * @param unit  单位类型
     *              <ul>
     *              <li>{@link TimeConstant#MSEC}: 毫秒</li>
     *              <li>{@link TimeConstant#SEC }: 秒</li>
     *              <li>{@link TimeConstant#MIN }: 分</li>
     *              <li>{@link TimeConstant#HOUR}: 小时</li>
     *              <li>{@link TimeConstant#DAY }: 天</li>
     *              </ul>
     * @return unit时间戳
     */
    public static long getTimeSpan(Date date0, Date date1, @TimeConstant.Unit int unit) {
        return ConvertUtils.millis2TimeSpan(Math.abs(date2Millis(date0) - date2Millis(date1)), unit);
    }

    /**
     * 获取两个时间差（单位：unit）
     *
     * @param millis0 毫秒时间戳0
     * @param millis1 毫秒时间戳1
     * @param unit    单位类型
     *                <ul>
     *                <li>{@link TimeConstant#MSEC}: 毫秒</li>
     *                <li>{@link TimeConstant#SEC }: 秒</li>
     *                <li>{@link TimeConstant#MIN }: 分</li>
     *                <li>{@link TimeConstant#HOUR}: 小时</li>
     *                <li>{@link TimeConstant#DAY }: 天</li>
     *                </ul>
     * @return unit时间戳
     */
    public static long getTimeSpan(long millis0, long millis1, @TimeConstant.Unit int unit) {
        return ConvertUtils.millis2TimeSpan(Math.abs(millis0 - millis1), unit);
    }

    /**
     * 获取合适型两个时间差
     * <p>time0和time1格式都为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time0     时间字符串0
     * @param time1     时间字符串1
     * @param precision 精度
     *                  <p>precision = 0，返回null</p>
     *                  <p>precision = 1，返回天</p>
     *                  <p>precision = 2，返回天和小时</p>
     *                  <p>precision = 3，返回天、小时和分钟</p>
     *                  <p>precision = 4，返回天、小时、分钟和秒</p>
     *                  <p>precision &gt;= 5，返回天、小时、分钟、秒和毫秒</p>
     * @return 合适型两个时间差
     */
    public static String getFitTimeSpan(String time0, String time1, int precision) {
        return ConvertUtils.millis2FitTimeSpan(Math.abs(string2Millis(time0, DEFAULT_PATTERN) - string2Millis(time1, DEFAULT_PATTERN)), precision);
    }

    /**
     * 获取合适型两个时间差
     * <p>time0和time1格式都为pattern</p>
     *
     * @param time0     时间字符串0
     * @param time1     时间字符串1
     * @param precision 精度
     *                  <p>precision = 0，返回null</p>
     *                  <p>precision = 1，返回天</p>
     *                  <p>precision = 2，返回天和小时</p>
     *                  <p>precision = 3，返回天、小时和分钟</p>
     *                  <p>precision = 4，返回天、小时、分钟和秒</p>
     *                  <p>precision &gt;= 5，返回天、小时、分钟、秒和毫秒</p>
     * @param pattern   时间格式
     * @return 合适型两个时间差
     */
    public static String getFitTimeSpan(String time0, String time1, int precision, String pattern) {
        return ConvertUtils.millis2FitTimeSpan(Math.abs(string2Millis(time0, pattern) - string2Millis(time1, pattern)), precision);
    }

    /**
     * 获取合适型两个时间差
     *
     * @param date0     Date类型时间0
     * @param date1     Date类型时间1
     * @param precision 精度
     *                  <p>precision = 0，返回null</p>
     *                  <p>precision = 1，返回天</p>
     *                  <p>precision = 2，返回天和小时</p>
     *                  <p>precision = 3，返回天、小时和分钟</p>
     *                  <p>precision = 4，返回天、小时、分钟和秒</p>
     *                  <p>precision &gt;= 5，返回天、小时、分钟、秒和毫秒</p>
     * @return 合适型两个时间差
     */
    public static String getFitTimeSpan(Date date0, Date date1, int precision) {
        return ConvertUtils.millis2FitTimeSpan(Math.abs(date2Millis(date0) - date2Millis(date1)), precision);
    }

    /**
     * 获取合适型两个时间差
     *
     * @param millis0   毫秒时间戳1
     * @param millis1   毫秒时间戳2
     * @param precision 精度
     *                  <p>precision = 0，返回null</p>
     *                  <p>precision = 1，返回天</p>
     *                  <p>precision = 2，返回天和小时</p>
     *                  <p>precision = 3，返回天、小时和分钟</p>
     *                  <p>precision = 4，返回天、小时、分钟和秒</p>
     *                  <p>precision &gt;= 5，返回天、小时、分钟、秒和毫秒</p>
     * @return 合适型两个时间差
     */
    public static String getFitTimeSpan(long millis0, long millis1, int precision) {
        return ConvertUtils.millis2FitTimeSpan(Math.abs(millis0 - millis1), precision);
    }

    /**
     * 获取当前毫秒时间戳
     *
     * @return 毫秒时间戳
     */
    public static long getNowTimeMills() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @return 时间字符串
     */
    public static String getNowTimeString() {
        return millis2String(System.currentTimeMillis(), DEFAULT_PATTERN);
    }

    /**
     * 获取当前时间字符串
     * <p>格式为pattern</p>
     *
     * @param pattern 时间格式
     * @return 时间字符串
     */
    public static String getNowTimeString(String pattern) {
        return millis2String(System.currentTimeMillis(), pattern);
    }

    /**
     * 获取当前Date
     *
     * @return Date类型时间
     */
    public static Date getNowTimeDate() {
        return new Date();
    }

    /**
     * 获取与当前时间的差（单位：unit）
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @param unit 单位类型
     *             <ul>
     *             <li>{@link TimeConstant#MSEC}: 毫秒</li>
     *             <li>{@link TimeConstant#SEC }: 秒</li>
     *             <li>{@link TimeConstant#MIN }: 分</li>
     *             <li>{@link TimeConstant#HOUR}: 小时</li>
     *             <li>{@link TimeConstant#DAY }: 天</li>
     *             </ul>
     * @return unit时间戳
     */
    public static long getTimeSpanByNow(String time, @TimeConstant.Unit int unit) {
        return getTimeSpan(getNowTimeString(), time, unit, DEFAULT_PATTERN);
    }

    /**
     * 获取与当前时间的差（单位：unit）
     * <p>time格式为pattern</p>
     *
     * @param time    时间字符串
     * @param unit    单位类型
     *                <ul>
     *                <li>{@link TimeConstant#MSEC}: 毫秒</li>
     *                <li>{@link TimeConstant#SEC }: 秒</li>
     *                <li>{@link TimeConstant#MIN }: 分</li>
     *                <li>{@link TimeConstant#HOUR}: 小时</li>
     *                <li>{@link TimeConstant#DAY }: 天</li>
     *                </ul>
     * @param pattern 时间格式
     * @return unit时间戳
     */
    public static long getTimeSpanByNow(String time, @TimeConstant.Unit int unit, String pattern) {
        return getTimeSpan(getNowTimeString(), time, unit, pattern);
    }

    /**
     * 获取与当前时间的差（单位：unit）
     *
     * @param date Date类型时间
     * @param unit 单位类型
     *             <ul>
     *             <li>{@link TimeConstant#MSEC}: 毫秒</li>
     *             <li>{@link TimeConstant#SEC }: 秒</li>
     *             <li>{@link TimeConstant#MIN }: 分</li>
     *             <li>{@link TimeConstant#HOUR}: 小时</li>
     *             <li>{@link TimeConstant#DAY }: 天</li>
     *             </ul>
     * @return unit时间戳
     */
    public static long getTimeSpanByNow(Date date, @TimeConstant.Unit int unit) {
        return getTimeSpan(new Date(), date, unit);
    }

    /**
     * 获取与当前时间的差（单位：unit）
     *
     * @param millis 毫秒时间戳
     * @param unit   单位类型
     *               <ul>
     *               <li>{@link TimeConstant#MSEC}: 毫秒</li>
     *               <li>{@link TimeConstant#SEC }: 秒</li>
     *               <li>{@link TimeConstant#MIN }: 分</li>
     *               <li>{@link TimeConstant#HOUR}: 小时</li>
     *               <li>{@link TimeConstant#DAY }: 天</li>
     *               </ul>
     * @return unit时间戳
     */
    public static long getTimeSpanByNow(long millis, @TimeConstant.Unit int unit) {
        return getTimeSpan(System.currentTimeMillis(), millis, unit);
    }

    /**
     * 获取合适型与当前时间的差
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time      时间字符串
     * @param precision 精度
     *                  <ul>
     *                  <li>precision = 0，返回null</li>
     *                  <li>precision = 1，返回天</li>
     *                  <li>precision = 2，返回天和小时</li>
     *                  <li>precision = 3，返回天、小时和分钟</li>
     *                  <li>precision = 4，返回天、小时、分钟和秒</li>
     *                  <li>precision &gt;= 5，返回天、小时、分钟、秒和毫秒</li>
     *                  </ul>
     * @return 合适型与当前时间的差
     */
    public static String getFitTimeSpanByNow(String time, int precision) {
        return getFitTimeSpan(getNowTimeString(), time, precision, DEFAULT_PATTERN);
    }

    /**
     * 获取合适型与当前时间的差
     * <p>time格式为pattern</p>
     *
     * @param time      时间字符串
     * @param precision 精度
     * @param pattern   时间格式
     *                  <ul>
     *                  <li>precision = 0，返回null</li>
     *                  <li>precision = 1，返回天</li>
     *                  <li>precision = 2，返回天和小时</li>
     *                  <li>precision = 3，返回天、小时和分钟</li>
     *                  <li>precision = 4，返回天、小时、分钟和秒</li>
     *                  <li>precision &gt;= 5，返回天、小时、分钟、秒和毫秒</li>
     *                  </ul>
     * @return 合适型与当前时间的差
     */
    public static String getFitTimeSpanByNow(String time, int precision, String pattern) {
        return getFitTimeSpan(getNowTimeString(), time, precision, pattern);
    }

    /**
     * 获取合适型与当前时间的差
     *
     * @param date      Date类型时间
     * @param precision 精度
     *                  <ul>
     *                  <li>precision = 0，返回null</li>
     *                  <li>precision = 1，返回天</li>
     *                  <li>precision = 2，返回天和小时</li>
     *                  <li>precision = 3，返回天、小时和分钟</li>
     *                  <li>precision = 4，返回天、小时、分钟和秒</li>
     *                  <li>precision &gt;= 5，返回天、小时、分钟、秒和毫秒</li>
     *                  </ul>
     * @return 合适型与当前时间的差
     */
    public static String getFitTimeSpanByNow(Date date, int precision) {
        return getFitTimeSpan(getNowTimeDate(), date, precision);
    }

    /**
     * 获取合适型与当前时间的差
     *
     * @param millis    毫秒时间戳
     * @param precision 精度
     *                  <ul>
     *                  <li>precision = 0，返回null</li>
     *                  <li>precision = 1，返回天</li>
     *                  <li>precision = 2，返回天和小时</li>
     *                  <li>precision = 3，返回天、小时和分钟</li>
     *                  <li>precision = 4，返回天、小时、分钟和秒</li>
     *                  <li>precision &gt;= 5，返回天、小时、分钟、秒和毫秒</li>
     *                  </ul>
     * @return 合适型与当前时间的差
     */
    public static String getFitTimeSpanByNow(long millis, int precision) {
        return getFitTimeSpan(System.currentTimeMillis(), millis, precision);
    }

    /**
     * 获取友好型与当前时间的差
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 友好型与当前时间的差
     * <ul>
     * <li>如果小于1秒钟内，显示刚刚</li>
     * <li>如果在1分钟内，显示XXX秒前</li>
     * <li>如果在1小时内，显示XXX分钟前</li>
     * <li>如果在1小时外的今天内，显示今天15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(String time) {
        return getFriendlyTimeSpanByNow(time, DEFAULT_PATTERN);
    }

    /**
     * 获取友好型与当前时间的差
     * <p>time格式为pattern</p>
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return 友好型与当前时间的差
     * <ul>
     * <li>如果小于1秒钟内，显示刚刚</li>
     * <li>如果在1分钟内，显示XXX秒前</li>
     * <li>如果在1小时内，显示XXX分钟前</li>
     * <li>如果在1小时外的今天内，显示今天15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(String time, String pattern) {
        return getFriendlyTimeSpanByNow(string2Millis(time, pattern));
    }

    /**
     * 获取友好型与当前时间的差
     *
     * @param date Date类型时间
     * @return 友好型与当前时间的差
     * <ul>
     * <li>如果小于1秒钟内，显示刚刚</li>
     * <li>如果在1分钟内，显示XXX秒前</li>
     * <li>如果在1小时内，显示XXX分钟前</li>
     * <li>如果在1小时外的今天内，显示今天15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(Date date) {
        return getFriendlyTimeSpanByNow(date.getTime());
    }

    /**
     * 获取友好型与当前时间的差
     *
     * @param millis 毫秒时间戳
     * @return 友好型与当前时间的差
     * <ul>
     * <li>如果小于1秒钟内，显示刚刚</li>
     * <li>如果在1分钟内，显示XXX秒前</li>
     * <li>如果在1小时内，显示XXX分钟前</li>
     * <li>如果在1小时外的今天内，显示今天15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    @SuppressLint("DefaultLocale")
    public static String getFriendlyTimeSpanByNow(long millis) {
        long now = System.currentTimeMillis();
        long span = now - millis;
        if (span < 0)
            return String.format("%tc", millis);// U can read http://www.apihome.cn/api/java/Formatter.html to understand it.
        if (span < 1000) {
            return "刚刚";
        } else if (span < TimeConstant.MIN) {
            return String.format("%d秒前", span / TimeConstant.SEC);
        } else if (span < TimeConstant.HOUR) {
            return String.format("%d分钟前", span / TimeConstant.MIN);
        }
        // 获取当天00:00
        long wee = (now / TimeConstant.DAY) * TimeConstant.DAY - 8 * TimeConstant.HOUR;
        if (millis >= wee) {
            return String.format("今天%tR", millis);
        } else if (millis >= wee - TimeConstant.DAY) {
            return String.format("昨天%tR", millis);
        } else {
            return String.format("%tF", millis);
        }
    }

    /**
     * 判断是否同一天
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isSameDay(String time) {
        return isSameDay(string2Millis(time, DEFAULT_PATTERN));
    }

    /**
     * 判断是否同一天
     * <p>time格式为pattern</p>
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isSameDay(String time, String pattern) {
        return isSameDay(string2Millis(time, pattern));
    }

    /**
     * 判断是否同一天
     *
     * @param date Date类型时间
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isSameDay(Date date) {
        return isSameDay(date.getTime());
    }

    /**
     * 判断是否同一天
     *
     * @param millis 毫秒时间戳
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isSameDay(long millis) {
        long wee = (System.currentTimeMillis() / TimeConstant.DAY) * TimeConstant.DAY - 8 * TimeConstant.HOUR;
        return millis >= wee && millis < wee + TimeConstant.DAY;
    }

    /**
     * 判断是否闰年
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return {@code true}: 闰年<br>{@code false}: 平年
     */
    public static boolean isLeapYear(String time) {
        return isLeapYear(string2Date(time, DEFAULT_PATTERN));
    }

    /**
     * 判断是否闰年
     * <p>time格式为pattern</p>
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return {@code true}: 闰年<br>{@code false}: 平年
     */
    public static boolean isLeapYear(String time, String pattern) {
        return isLeapYear(string2Date(time, pattern));
    }

    /**
     * 判断是否闰年
     *
     * @param date Date类型时间
     * @return {@code true}: 闰年<br>{@code false}: 平年
     */
    public static boolean isLeapYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return isLeapYear(year);
    }

    /**
     * 判断是否闰年
     *
     * @param millis 毫秒时间戳
     * @return {@code true}: 闰年<br>{@code false}: 平年
     */
    public static boolean isLeapYear(long millis) {
        return isLeapYear(millis2Date(millis));
    }

    /**
     * 判断是否闰年
     *
     * @param year 年份
     * @return {@code true}: 闰年<br>{@code false}: 平年
     */
    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    /**
     * 获取星期
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 星期
     */
    public static String getWeek(String time) {
        return getWeek(string2Date(time, DEFAULT_PATTERN));
    }

    /**
     * 获取星期
     * <p>time格式为pattern</p>
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return 星期
     */
    public static String getWeek(String time, String pattern) {
        return getWeek(string2Date(time, pattern));
    }

    /**
     * 获取星期
     *
     * @param date Date类型时间
     * @return 星期
     */
    public static String getWeek(Date date) {
        return new SimpleDateFormat("EEEE", Locale.getDefault()).format(date);
    }

    /**
     * 获取星期
     *
     * @param millis 毫秒时间戳
     * @return 星期
     */
    public static String getWeek(long millis) {
        return getWeek(new Date(millis));
    }

    /**
     * 获取星期
     * <p>注意：周日的Index才是1，周六为7</p>
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 1...5
     */
    public static int getWeekIndex(String time) {
        return getWeekIndex(string2Date(time, DEFAULT_PATTERN));
    }

    /**
     * 获取星期
     * <p>注意：周日的Index才是1，周六为7</p>
     * <p>time格式为pattern</p>
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return 1...7
     */
    public static int getWeekIndex(String time, String pattern) {
        return getWeekIndex(string2Date(time, pattern));
    }

    /**
     * 获取星期
     * <p>注意：周日的Index才是1，周六为7</p>
     *
     * @param date Date类型时间
     * @return 1...7
     */
    public static int getWeekIndex(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取星期
     * <p>注意：周日的Index才是1，周六为7</p>
     *
     * @param millis 毫秒时间戳
     * @return 1...7
     */
    public static int getWeekIndex(long millis) {
        return getWeekIndex(millis2Date(millis));
    }

    /**
     * 获取月份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 1...5
     */
    public static int getWeekOfMonth(String time) {
        return getWeekOfMonth(string2Date(time, DEFAULT_PATTERN));
    }

    /**
     * 获取月份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     * <p>time格式为pattern</p>
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return 1...5
     */
    public static int getWeekOfMonth(String time, String pattern) {
        return getWeekOfMonth(string2Date(time, pattern));
    }

    /**
     * 获取月份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     *
     * @param date Date类型时间
     * @return 1...5
     */
    public static int getWeekOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 获取月份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     *
     * @param millis 毫秒时间戳
     * @return 1...5
     */
    public static int getWeekOfMonth(long millis) {
        return getWeekOfMonth(millis2Date(millis));
    }

    /**
     * 获取年份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 1...54
     */
    public static int getWeekOfYear(String time) {
        return getWeekOfYear(string2Date(time, DEFAULT_PATTERN));
    }

    /**
     * 获取年份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     * <p>time格式为pattern</p>
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return 1...54
     */
    public static int getWeekOfYear(String time, String pattern) {
        return getWeekOfYear(string2Date(time, pattern));
    }

    /**
     * 获取年份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     *
     * @param date Date类型时间
     * @return 1...54
     */
    public static int getWeekOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取年份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     *
     * @param millis 毫秒时间戳
     * @return 1...54
     */
    public static int getWeekOfYear(long millis) {
        return getWeekOfYear(millis2Date(millis));
    }

}