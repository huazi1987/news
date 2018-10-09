package com.news.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Vector;

public final class DateTool {
    public static String FORMAT_MS = "yyyy-MM-dd HH:mm:ss.S";
    public static final String FORMAT_ONE = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_TWO = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_THREE = "yyyyMMdd-HHmmss";
    public static final String FORMAT_FOUR = "yyyyMMddHHmmss";
    public static final String FORMAT_FIVE = "MM-dd HH:mm";
    public static final String FORMAT_SIX = "yyyy/MM/dd HH:mm";
    public static final String LONG_DATE_FORMAT = "yyyy-MM-dd";
    public static final String EIGHT_STYLE_DATE_FORMAT = "yyyyMMdd";
    public static final String SHORT_DATE_FORMAT = "MM-dd";
    public static final String LONG_TIME_FORMAT = "HH:mm:ss";
    public static final String MONTG_DATE_FORMAT = "yyyy-MM";
    public static final String MONTH_DATE_FORMAT_2 = "yyyy/MM";
    public static final String YEAR_MONTH_DAY = "yyyyMMdd";

    public DateTool() {
    }

    public static Date calculateByDate(Date d, int amount) {
        return calculate(d, 5, amount);
    }

    public static Date calculateByMinute(Date d, int amount) {
        return calculate(d, 12, amount);
    }

    public static Date calculateByYear(Date d, int amount) {
        return calculate(d, 1, amount);
    }

    public static Date calculate(Date d, int field, int amount) {
        if (d == null) {
            return null;
        } else {
            GregorianCalendar g = new GregorianCalendar();
            g.setGregorianChange(d);
            g.add(field, amount);
            return g.getTime();
        }
    }

    public static String date2String(String formater, Date aDate) {
        if (formater != null && !"".equals(formater)) {
            return aDate == null ? null : (new SimpleDateFormat(formater)).format(aDate);
        } else {
            return null;
        }
    }

    public static String date2String(String formater) {
        return date2String(formater, new Date());
    }

    public static int dayOfWeek() {
        GregorianCalendar g = new GregorianCalendar();
        int ret = g.get(7);
        g = null;
        return ret;
    }

    public static String[] fecthAllTimeZoneIds() {
        Vector v = new Vector();
        String[] ids = TimeZone.getAvailableIDs();

        for(int i = 0; i < ids.length; ++i) {
            v.add(ids[i]);
        }

        Collections.sort(v, String.CASE_INSENSITIVE_ORDER);
        v.copyInto(ids);
        v = null;
        return ids;
    }

    public static String string2Timezone(String srcFormater, String srcDateTime, String dstFormater, String dstTimeZoneId) {
        if (srcFormater != null && !"".equals(srcFormater)) {
            if (srcDateTime != null && !"".equals(srcDateTime)) {
                if (dstFormater != null && !"".equals(dstFormater)) {
                    if (dstTimeZoneId != null && !"".equals(dstTimeZoneId)) {
                        SimpleDateFormat sdf = new SimpleDateFormat(srcFormater);

                        Date d;
                        try {
                            int diffTime = getDiffTimeZoneRawOffset(dstTimeZoneId);
                            d = sdf.parse(srcDateTime);
                            long nowTime = d.getTime();
                            long newNowTime = nowTime - (long)diffTime;
                            d = new Date(newNowTime);
                            String var11 = date2String(dstFormater, d);
                            return var11;
                        } catch (ParseException var15) {
                            d = null;
                        } finally {
                            sdf = null;
                        }

                        return null;
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static int getDefaultTimeZoneRawOffset() {
        return TimeZone.getDefault().getRawOffset();
    }

    public static int getTimeZoneRawOffset(String timeZoneId) {
        return TimeZone.getTimeZone(timeZoneId).getRawOffset();
    }

    private static int getDiffTimeZoneRawOffset(String timeZoneId) {
        return TimeZone.getDefault().getRawOffset() - TimeZone.getTimeZone(timeZoneId).getRawOffset();
    }

    public static String string2TimezoneDefault(String srcDateTime, String dstTimeZoneId) {
        return string2Timezone("yyyy-MM-dd HH:mm:ss", srcDateTime, "yyyy-MM-dd HH:mm:ss", dstTimeZoneId);
    }

    public static long date2Ms(Date date, String format) {
        if (date == null) {
            date = new Date(0L);
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String date_str = sdf.format(date);

        try {
            long millionSeconds = sdf.parse(date_str).getTime();
            return millionSeconds;
        } catch (ParseException var6) {
            var6.printStackTrace();
            return 0L;
        }
    }

    public static Date getChinaDate() {
        return Calendar.getInstance(new Locale(Locale.CHINESE.toString(), Locale.CHINA.toString())).getTime();
    }
}
