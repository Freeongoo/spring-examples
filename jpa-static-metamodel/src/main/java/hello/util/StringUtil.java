package hello.util;

public final class StringUtil {

    public static boolean convertToBoolean(String str) {
        String trim = str.trim();
        return !trim.equals("") && !trim.equals("0") && !trim.toLowerCase().equals("false");
    }
}
