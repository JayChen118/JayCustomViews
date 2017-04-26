package cn.jaychen.jaycustomviews.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd", Locale.SIMPLIFIED_CHINESE);

    private static final DateFormat HOUR_MINUTE_FORMAT = new SimpleDateFormat("HH:mm", Locale.SIMPLIFIED_CHINESE);

    private static final DateFormat HOUR_MINUTE_SECOND_FORMAT = new SimpleDateFormat("HH:mm:ss", Locale.SIMPLIFIED_CHINESE);

    private static final DateFormat MONTH_DATE_HOUR_MINUTE_SECOND_FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);

    private static final DateFormat NORMAL_FORMAT_1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);

    private static final DateFormat DATE_HOUR_MINUTE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.SIMPLIFIED_CHINESE);

    private static final DateFormat NORMAL_FORMAT_2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);


    /**
     * 一天的毫秒数
     */
    public static final long MILLISECONDS_OF_DAY = 86400000;

    /**
     * 一小时的毫秒数
     */
    public static final long MILLISECONDS_OF_HOUR = 3600000;

    public static String timeFormat(long timeMillis, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
        return format.format(new Date(timeMillis));
    }

    public static String getHourMinute(long time){
        return HOUR_MINUTE_FORMAT.format(new Date(time));
    }

    public static String getHourMinuteSecond(long time) {
        return HOUR_MINUTE_SECOND_FORMAT.format(new Date(time));
    }

    public static String getMonthDateHourMinuteSecond(long time) {
        return MONTH_DATE_HOUR_MINUTE_SECOND_FORMAT.format(new Date(time));
    }

    public long dateStrToLong(String dateStr) {
        if (dateStr == null || "".equals(dateStr.trim())) {
            return 0;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return format.parse(dateStr).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Date parse(String time) {
        try {
            return NORMAL_FORMAT_1.parse(time);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static String getDateHourMinuteString(long time) {
        return DATE_HOUR_MINUTE_FORMAT.format(new Date(time));
    }

    public static String getFullDateTimeString(long time) {
        return NORMAL_FORMAT_2.format(new Date(time));
    }

    public static String getDateDisplayString(long date) {
        return getDateDisplayString(new Date((date)));
    }

    public static String getDateDisplayString(String date) {
        return getDateDisplayString(parse(date));
    }

    public static String getDateDisplayString(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        Calendar todayCalendar = Calendar.getInstance();

        if (isSameDay(calendar, todayCalendar)) {
            // 同一年同一日
            return "今天";
        }

        calendar.add(Calendar.DAY_OF_YEAR, 1);

        if (isSameDay(calendar, todayCalendar)) {
            //加了一天后，是同一年同一日
            return "昨天";
        }

        return DATE_FORMAT.format(date);
    }


    public static String getTimeDisplayString(long time) {
        return getTimeDisplayString(new Date(time));
    }

    public static String getTimeDisplayString(Date time) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);

        Calendar todayCalendar = Calendar.getInstance();

        if (isSameDay(calendar, todayCalendar)) {
            // 同一年同一日
            return HOUR_MINUTE_FORMAT.format(time);
        }

        calendar.add(Calendar.DAY_OF_YEAR, 1);

        if (isSameDay(calendar, todayCalendar)) {
            //加了一天后，是同一年同一日
            return "昨天";
        }

        return DATE_FORMAT.format(time);
    }

    private static boolean isSameDay(Calendar calendar1, Calendar calendar2) {
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 计算两个时间的天数差距
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long diffDay(Date date1, Date date2) {
        long days = (date1.getTime() - date2.getTime()) / MILLISECONDS_OF_DAY;
        if (days < 0) {
            return -days;
        } else {
            return days;
        }
    }

    /**
     * 计算两个时间的小时差距（不算天数）
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long diffHour(Date date1, Date date2) {
        long hours = ((date1.getTime() - date2.getTime()) % MILLISECONDS_OF_DAY) / MILLISECONDS_OF_HOUR;
        if (hours < 0) {
            return -hours;
        } else {
            return hours;
        }
    }

    /**
     * 把日期“2011-11-11”转为“2011年11月11日”
     *
     * @param dateString 日期“2011-11-11”
     * @return “2011年11月11日”
     */
    public static String translateDateFormat(String dateString) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat secondFormat = new SimpleDateFormat("yyyy年MM月dd日");
        try {
            Date date = format.parse(dateString);
            return secondFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateString;
        }
    }

    public static String getDateString() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date());
    }

}