package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {
    /**
     * 获取两个日期之间的日期（不包含开始和结束时间）
     *
     * @param start 开始日期
     * @param end   结束日期
     * @return 日期集合
     */
    public static List<Date> getBetweenDates(Date start, Date end) {
        List<Date> result = new ArrayList<>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        tempStart.add(Calendar.DAY_OF_YEAR, 1);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        while (tempStart.before(tempEnd)) {
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }

    /**
     * 字符转日期
     *
     * @param strDate 字符日期
     * @param format  格式
     * @return 日期
     * @throws ParseException 转换异常
     */
    public static Date strToDate(String strDate, String format) throws ParseException {

        if (null != strDate && null != format && !"".equals(strDate) && !"".equals(format)) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(strDate);
        }
        return null;
    }

    /**
     * 日期转字符
     *
     * @param date   日期
     * @param format 格式
     * @return 字符日期
     */
    public static String dateToStr(Date date, String format) {
        if (null != date && null != format && !"".equals(format)) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        }
        return null;
    }

    /**
     * 获取随机日期
     *
     * @param beginDate 起始日期，格式为：yyyy-MM-dd
     * @param endDate   结束日期，格式为：yyyy-MM-dd
     * @return 随机时间
     */

    private static Date randomDate(String beginDate, String endDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date start = format.parse(beginDate);// 构造开始日期
            Date end = format.parse(endDate);// 构造结束日期
            // getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
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
}
