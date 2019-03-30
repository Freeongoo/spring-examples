package hello.util;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Useful for convert Date to Local and vice versa
 */
public class DateUtils {

    public static final String ONLY_DATE_FORMAT_ISO = "yyyy-MM-dd";

    public static final String DATE_FORMAT_ISO = "yyyy-MM-dd'T'HH:mm:ss"; // ISO 8601

    public static final Long UNKNOWN_FINISH_DATE = 3539948789000L; // 5 March 2082 г., 15:06:29

    public static final long DAY_IN_MSEC = 24 * 60 * 60 * 1000L;

    public static final int MSEC_IN_ONE_MIN = 60 * 1000;

    /**
     * With included boundary dates
     *
     * @param date date
     * @param dateFrom dateFrom
     * @param dateTo dateTo
     * @return true or false
     */
    public static boolean isBetween(Date date, Date dateFrom, Date dateTo) {
        return (date.after(dateFrom) && date.before(dateTo)) || date.equals(dateFrom) || date.equals(dateTo);
    }

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDateFromUTC(Date date) {
        if (date == null) return null;
        return asDateFromUTC(asLocalDateTime(date));
    }

    public static Date asDateFromUTC(LocalDate localDate) {
        if (localDate == null) return null;
        return Date.from(localDate.atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDateFromUTC(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        return Date.from(localDateTime.atZone(ZoneOffset.UTC).toInstant());
    }

    public static LocalDate asLocalDate(Date date) {
        if (date == null) return null;
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        if (date == null) return null;
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDateTime getBeginCurrDay(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        return localDateTime.withHour(0).withMinute(0).withSecond(0);
    }

    public static LocalDateTime getBeginCurrDay(Date date) {
        if (date == null) return null;
        return getBeginCurrDay(asLocalDateTime(date));
    }

    public static Date getBeginCurrDayAsDate(Date date) {
        if (date == null) return null;
        return asDate(getBeginCurrDay(date));
    }

    public static LocalDateTime getEndCurrDay(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        return localDateTime.withHour(23).withMinute(59).withSecond(59);
    }

    public static LocalDateTime getEndCurrDay(Date date) {
        if (date == null) return null;
        return getEndCurrDay(asLocalDateTime(date));
    }

    public static Date getEndCurrDayAsDate(Date date) {
        if (date == null) return null;
        return asDate(getEndCurrDay(date));
    }

    public static LocalDateTime getBeginNextDay(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        return localDateTime.withHour(0).withMinute(0).withSecond(0).plusDays(1);
    }

    public static LocalDateTime getBeginNextDay(Date date) {
        if (date == null) return null;
        return getBeginNextDay(asLocalDateTime(date));
    }

    public static LocalDateTime getBeginPreviousDay(Date date) {
        if (date == null) return null;
        return asLocalDateTime(date).withHour(0).withMinute(0).withSecond(0).minusDays(1);
    }

    public static Date getBeginPreviousDayAsDate(Date date) {
        if (date == null) return null;
        return asDate(getBeginPreviousDay(date));
    }

    public static Date getBeginNextDayAsDate(Date date) {
        if (date == null) return null;
        return asDate(getBeginNextDay(date));
    }

    public static LocalDateTime getBeginCurrMonth(Date date) {
        if (date == null) return null;
        return asLocalDateTime(date).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
    }

    public static Date getBeginCurrMonthAsDate(Date date) {
        if (date == null) return null;
        return asDate(getBeginCurrMonth(date));
    }

    public static LocalDateTime getBeginCurrYear(Date date) {
        if (date == null) return null;
        return asLocalDateTime(date).withDayOfYear(1).withMonth(1).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
    }

    public static Date getBeginCurrYearAsDate(Date date) {
        if (date == null) return null;
        return asDate(getBeginCurrYear(date));
    }

    public static Date parse(String dateStr, String format) {
        if (StringUtils.isEmpty(dateStr)) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(String.format("Cannot parse string: '%s' to date", dateStr), e);
        }
    }

    /**
     * Parse format ISO with/without time
     *
     * @param dateStr dateStr
     * @return Date
     */
    public static Date parseISO(String dateStr) {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }

        if (dateStr.contains("T"))
            return parse(dateStr, DATE_FORMAT_ISO);

        return parse(dateStr, ONLY_DATE_FORMAT_ISO);
    }

    /**
     * If dateFrom > dateTo return negative count of days
     * If dateFrom < dateTo return positive count of days
     * else == zero
     *
     * @param dateFrom dateFrom
     * @param dateTo dateTo
     * @return Long
     */
    public static Long getCountDaysBetween(Date dateFrom, Date dateTo) {
        if (dateFrom == null || dateTo == null) return 0L;

        LocalDate localFrom = DateUtils.asLocalDate(dateFrom);
        LocalDate localTo = DateUtils.asLocalDate(dateTo);

        if (localFrom.isAfter(localTo)) return 0L;

        return ChronoUnit.DAYS.between(localFrom, localTo);
    }

    /**
     * @param date date
     * @param countDays countDays may be positive or negative
     * @return Date
     */
    public static Date calcIncOrDecDays(Date date, long countDays) {
        if (date == null) return null;
        LocalDateTime localDateTime = asLocalDateTime(date);
        if (countDays < 0)
            return asDate(localDateTime.minusDays(Math.abs(countDays)));
        if (countDays > 0)
            return asDate(localDateTime.plusDays(Math.abs(countDays)));

        return date;
    }
}
