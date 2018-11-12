package com.yui.tool.imgremove.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具
 *
 * @author XuZhuohao
 * @date 2018-10-31 22:00
 */
public class DateUtil {

    public static Long getMMdd(Date date){
        SimpleDateFormat sf = new SimpleDateFormat("MMdd");
        return Long.valueOf(sf.format(date));
    }

    /**
     * 获取当前天，1-365（366）
     * @param date 传入时间
     * @return d
     */
    public static int getD(Date date){
        SimpleDateFormat sf = new SimpleDateFormat("D");
        return Integer.valueOf(sf.format(date));
    }
    /**
     * 获取当前天，1-365（366）
     * @param date 传入时间
     * @return YYYYMM
     */
    public static int getYYYYMM(Date date){
        SimpleDateFormat sf = new SimpleDateFormat("YYYYMM");
        return Integer.valueOf(sf.format(date));
    }

}
