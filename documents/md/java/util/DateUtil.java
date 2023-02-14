package com.zyc.zdh.util;

import org.apache.commons.lang3.time.FastDateFormat;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {


    public static final FastDateFormat df
            = FastDateFormat.getInstance("yyyy-MM-dd");
    public static final FastDateFormat df_nodash
            = FastDateFormat.getInstance("yyyyMMdd");
    public static final FastDateFormat df_time
            = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");


    /** *
     *
     * @example add("20180101",90),add("20180101",-90)
     * @param start  起始日期
     * @param dayNum 天数
     * @return
     */
    public static Date add(Date start,int dayNum){
        Calendar beginDate = Calendar.getInstance();
        beginDate.setTime(start);
        beginDate.add(Calendar.DAY_OF_MONTH, dayNum);
        return beginDate.getTime();
    }

    public static Timestamp add(Timestamp start, int dateType,int num){
        Calendar beginDate = Calendar.getInstance();
        beginDate.setTimeInMillis(start.getTime());
        beginDate.add(dateType, num);
        return new Timestamp(beginDate.getTimeInMillis());
    }


    public static Date pase(String date) throws Exception{
        return df.parse(date);
    }


    public static String  format(Date date){
       return df.format(date);
    }

    public static String  format(Timestamp date){
        return df.format(date);
    }

    public static String  formatNodash(Date date){
        return df_nodash.format(date);
    }

    public static String  formatNodash(Timestamp date){
        return df_nodash.format(date);
    }

    public static String  formatTime(Timestamp dt){
        return df_time.format(dt);
    }

    public static String getCurrentTime(){
        return df_time.format(new Timestamp(new Date().getTime()));
    }

    public static Date pase(String date,String format){
        try {
            return FastDateFormat.getInstance(format).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String year(Date date) {
        return format(date).substring(0, 4);
    }

    public static int month(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int day(Timestamp date){
        Calendar beginDate = Calendar.getInstance();
        beginDate.setTimeInMillis(date.getTime());
        return beginDate.get(Calendar.DAY_OF_MONTH);
    }

    public static int hour(Timestamp date){
        Calendar beginDate = Calendar.getInstance();
        beginDate.setTimeInMillis(date.getTime());
        return beginDate.get(Calendar.HOUR_OF_DAY);
    }

    public static int minute(Timestamp date){
        Calendar beginDate = Calendar.getInstance();
        beginDate.setTimeInMillis(date.getTime());
        return beginDate.get(Calendar.MINUTE);
    }

    public static int second(Timestamp date){
        Calendar beginDate = Calendar.getInstance();
        beginDate.setTimeInMillis(date.getTime());
        return beginDate.get(Calendar.SECOND);
    }


}
