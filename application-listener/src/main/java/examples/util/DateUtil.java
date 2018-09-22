package examples.util;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static final String format = "yyyy-MM-dd HH:mm:ss";

    public static String convertTime(long time){
        return convertTime(time, DateUtil.format);
    }

    public static String convertTime(long time, String format){
        Date date = new Date(time);
        Format sformat = new SimpleDateFormat(format);
        return sformat.format(date);
    }
}
