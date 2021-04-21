package com.zust.zxp.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String formatDate(Date date, String patten){

        return new SimpleDateFormat(patten).format(date);
    }
}
