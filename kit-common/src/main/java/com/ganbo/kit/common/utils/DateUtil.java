package com.ganbo.kit.common.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期转换工具类
 *
 * @author brucegan
 * @date 2019-07-31 18:54
 */
public class DateUtil {
    public static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat yyyy_MM_ddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
    public static final SimpleDateFormat yyyy_MM_dd_HHmmssSSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    public static final SimpleDateFormat HHmmss = new SimpleDateFormat("HH:mm:ss");
    public static final Object lock = new Object();


    public static String formateDateyyyyMMdd(Date date) {
        synchronized (lock) {
            return yyyyMMdd.format(date);
        }
    }

    public static String formateDateyyyy_MM_ddHHmmss(Date date) {
        synchronized (lock) {
            return yyyy_MM_ddHHmmss.format(date);
        }
    }

    public static String formateDateyyyy_MM_dd(Date date) {
        synchronized (lock) {
            return yyyy_MM_dd.format(date);
        }
    }

    public static String formateDateyyyyMMddHHmmss(Date date) {
        synchronized (lock) {
            return yyyyMMddHHmmss.format(date);
        }
    }

    public static String formateDateyyyyMMddHHmmssSSS(Date date) {
        synchronized (lock) {
            return yyyy_MM_dd_HHmmssSSS.format(date);
        }
    }

    /**
     * @param dateStr 时间串
     * @return 转化结果，无法转化则new date
     */
    public static Date parseDate(String dateStr) {
        synchronized (lock) {
            try {
                return yyyy_MM_ddHHmmss.parse(dateStr);
            } catch (Exception e2) {
                try {
                    return yyyy_MM_dd.parse(dateStr);
                } catch (Exception e3) {
                    try {
                        return yyyyMMdd.parse(dateStr);
                    } catch (Exception e4) {
                        return new Date();
                    }
                }
            }
        }
    }

    /**
     * 正对有毫秒的时间字符串进行解析
     *
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static Date parseDateyyyy_MM_dd_HHmmssSSS(String dateStr) throws ParseException {
        synchronized (lock) {
            return yyyy_MM_dd_HHmmssSSS.parse(dateStr);
        }
    }

    public static Date parseHHmmss(String dateStr) throws ParseException {
        synchronized (lock) {
            return HHmmss.parse(dateStr);
        }
    }

    /**
     * 获取下一天时间串
     *
     * @param dateStr
     * @return 下一天的时间串
     */
    public static String getNextDayString(String dateStr) {
        Date date;
        synchronized (lock) {
            try {
                date = yyyy_MM_ddHHmmss.parse(dateStr);
                date.setTime(date.getTime() + 24 * 60 * 60 * 1000);
                return formateDateyyyy_MM_ddHHmmss(date);
            } catch (Exception e) {
                try {
                    date = yyyy_MM_dd.parse(dateStr);
                    date.setTime(date.getTime() + 24 * 60 * 60 * 1000);
                    return formateDateyyyy_MM_dd(date);
                } catch (Exception e2) {
                    try {
                        date = yyyyMMdd.parse(dateStr);
                        date.setTime(date.getTime() + 24 * 60 * 60 * 1000);
                        return formateDateyyyyMMdd(date);
                    } catch (Exception e3) {
                        return "";
                    }
                }
            }
        }
    }


    /**
     * 获取随机日期
     *
     * @param beginDate 起始日期，格式为：yyyyMMdd
     * @param endDate   结束日期，格式为：yyyyMMdd
     * @return
     */

    public static Date randomDate(String beginDate, String endDate) {
        try {
            Date start = parseDate(beginDate);
            Date end = parseDate(endDate);
            if (start.getTime() >= end.getTime()) {
                return null;
            }
            long date = random(start.getTime(), end.getTime());

            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static long random(long begin, long end) {
        long rtn = begin + (long) (Math.random() * (end - begin));
        // 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值
        if (rtn == begin || rtn == end) {
            return random(begin, end);
        }
        return rtn;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间 串
     * @param bdate  较大的时间 串
     * @return 相差天数
     */
    public static int daysBetween(String smdate, String bdate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parseDate(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(parseDate(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * @param mss 要转换的毫秒数
     * @return 该毫秒数转换为 * 分 * 秒 * 毫秒 后的格式
     */
    public static String formatDuring(long mss) {
        long minutes = (mss) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        long ms = (mss % (1000));
        if (minutes != 0) {
            return minutes + "分钟" + (seconds != 0 ? seconds + "秒" + (ms != 0 ? ms + "毫秒" : "") : "");
        }
        if (seconds != 0) {
            return seconds + "秒" + (ms != 0 ? ms + "毫秒" : "");
        }
        return ms + "毫秒";
    }

    public static String getYear() {
        Calendar date = Calendar.getInstance();
        return String.valueOf(date.get(Calendar.YEAR));
    }

    public static Date beginOfToday() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static Date beginOfWeek() {
        Calendar c = Calendar.getInstance();
        Date date = new Date();
        date.setTime(date.getTime() - 3600000 * 24);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static Date beginOfMonth() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    /**
     * 获取指定LocalDateTime的星期
     *
     * @param localDateTime
     * @return
     */
    public static Integer getWeek(LocalDateTime localDateTime) {
        return localDateTime.get(WeekFields.of(DayOfWeek.of(1), 1).dayOfWeek());
    }

    /**
     * 根据时间撮和时区获取年
     *
     * @param timeStamp
     * @param timezone
     * @return
     */
    public static String getYear(long timeStamp, String timezone) {
        Date date = new Date(timeStamp);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy");
        sf.setTimeZone(TimeZone.getTimeZone(timezone));
        return sf.format(date);
    }

    /**
     * 时间撮 +/- 年
     *
     * @param timestamp 要计算的时间撮
     * @param years     加减的年,例：加一年 1，减一年 -1
     * @return
     */
    public static long plusYears(long timestamp, long years, String timeZone) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of(timeZone));
        return localDateTime.plusYears(years).atZone(ZoneId.of(timeZone)).toInstant().toEpochMilli();
    }

    /**
     * 时间撮 +/- 天
     *
     * @param timestamp 要计算的时间撮
     * @param day       加减的天,例：加一天 1，减一天 -1
     * @return
     */
    public static long plusDays(long timestamp, long day) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.plusDays(day).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }


    /**
     * 获取某天的第一秒对应的时间:当天的00:00.000
     *
     * @param date
     * @return
     */
    public static Date dayStart(Date date) {
        if (date == null) {
            return null;
        }
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return Date.from(localDateTime
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0)
                .atZone(zoneId)
                .toInstant());
    }

    /**
     * 获取某天的最后一秒对应的时间:当天的23:59.000
     *
     * @param day
     * @return
     */
    public static Date dayEnd(Date day) {
        if (day == null) {
            return null;
        }
        long theDay = dayStart(day).getTime();
        final long ONE_DAY_MILLIS = 1 * 24 * 60 * 60 * 1000;

        return new Date(theDay + ONE_DAY_MILLIS - 1);
    }

    /**
     * 从当前时间增加秒 seconds 得到另外一个时间
     *
     * @param day
     * @param seconds 可以为负数
     * @return
     */
    public static Date offsetSeconds(Date day, int seconds) {
        long nowMillis = day.getTime();
        final long OFFSET_MILLIS = seconds * 1000L;

        return new Date(nowMillis + OFFSET_MILLIS);
    }

    /**
     * 将Date 转换 LocalDate对象
     *
     * @param date
     * @return
     */
    public static LocalDate getDateToLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDate();
    }

    /**
     * 将LocalDate  转换 Date对象
     *
     * @param localDate
     * @return
     */
    public static Date getLocalDateToDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * 对日期进行天数 加减操作
     *
     * @param date
     * @return
     */
    public static Date getDateToDayOffset(Date date, int day) {
        if (date == null) {
            return null;
        }
        LocalDate localDate = DateUtil.getDateToLocalDate(date);
        localDate = localDate.plusDays(day);
        return DateUtil.getLocalDateToDate(localDate);
    }

    /**
     * 获取指定日期所在月份的开始日期
     *
     * @param str
     * @return
     */
    public static Date getMonthStartDate(String str) {
        Calendar c = Calendar.getInstance();
        c.setTime(parseDate(str));
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);  //设置为1号,当前日期既为本月第一天
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    /**
     * 获取指定日期所在月份的结束日期
     *
     * @param str
     * @return
     */
    public static Date getMonthEndDate(String str) {
        Calendar c = Calendar.getInstance();
        c.setTime(parseDate(str));
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }

}
