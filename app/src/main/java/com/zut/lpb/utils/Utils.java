package com.zut.lpb.utils;

import java.util.Calendar;

public class Utils {
    /**
     * 获取系统时间 年月日
     */
    public static String getSystemTime(){
        Calendar calendar = Calendar.getInstance();//取得当前时间的年月日 时分秒
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return ""+year+'-'+month+'-'+day;
    }
}
