package top.irises.pbox3.utils;

import android.view.View;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    private static SimpleDateFormat formater;
    private static SimpleDateFormat formater2;

    private static long unixYear = 31622400l;
    private static String pattern = "yyyy-MM-dd HH:mm:ss";
    private static String pattern2 = "yyyy年M月dd日";
    private static String pattern3 = "yyyy年M月dd日,HH点mm分";
    private static String pattern4 = "yyyy-MM-dd HH:mm";
    static {
        formater = new SimpleDateFormat(pattern2);
        formater2 = new SimpleDateFormat(pattern);
    }
    /**
     * 使用当前时间,并获取unix时间戳
     * @return
     */
    public synchronized static long getTimestamp(){
        return new Date().getTime();
    }

    /**
     * 给定具体日期,转换为unix时间戳
     * @param date
     * @return
     */
    public synchronized static long convertToTimestamp(Date date) throws ParseException {
        return date.getTime();
    }

    /**
     * 给定时间戳,返回当前日期字符串
     * @param timeStamp
     * @return
     */
    public synchronized static String getDateFromTimestamp(long timeStamp){
        return formater.format(new Date(timeStamp));
    }

    public synchronized static String getSimpleDate(long timeStamp){
        return formater.format(timeStamp);
    }

    /**
     * 给定时间字符串,转换为long timestamp
     */
    public synchronized static long getTimestamp(int year,int month,int day,int hour,int minute) throws ParseException {
        Date date = new Date();
        String datetime = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day)+" "+String.valueOf(hour)+":"+String.valueOf(minute)+":0";
        date = formater2.parse(datetime);
        return date.getTime();
    }
}
