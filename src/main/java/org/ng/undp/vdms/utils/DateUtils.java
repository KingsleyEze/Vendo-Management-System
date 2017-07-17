package org.ng.undp.vdms.utils;

import org.apache.tomcat.jni.Local;
import org.ng.undp.vdms.constants.StudyLabConstants;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by emmanuel on 12/16/16.
 */
public class DateUtils {
    private static Logger logger = LoggerFactory.getLogger(Utility.class);
    public static DateTimeFormatter df = DateTimeFormat.forPattern("d/M/yyyy");

    public static Date getDateFromString(String date) {
        if (StringUtils.isEmpty(date))
            return null;

        return DateTime.parse(date, df).toDate();
    }

    public static String getDateAsString(Date date) {
        if (null == date)
            return null;


        String s = df.print(new DateTime(date));
        return s;
    }

    public static Date getWeekStartDate() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone(StudyLabConstants.CURRENT_TIME_ZONE));
        System.out.print("Current time " + c.getTime());
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        System.out.print("Clear time " + c.getTime());


        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date start = c.getTime();
        System.out.println("Week Start Date =" + start);
        return start;
    }

    public static Date getPreviousWeekStartDate() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone(StudyLabConstants.CURRENT_TIME_ZONE));
        System.out.println("Current time " + c.getTime());
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        System.out.println("Clear time " + c.getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        System.out.println("Day of week time " + c.getTime());
        c.add(Calendar.WEEK_OF_YEAR, -1);
        Date start = c.getTime();
        System.out.println("Week Start Date =" + start);
        return start;
    }

    public static Map<String, Date> getWeekInReviewDates() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone(StudyLabConstants.CURRENT_TIME_ZONE));
        System.out.println("Current time " + c.getTime());
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.add(Calendar.WEEK_OF_YEAR, -1);

        Map<String, Date> map = new HashMap<String, Date>();
        map.put("start", c.getTime());
        c.add(Calendar.WEEK_OF_YEAR, 1);
        // As start date was monday 12:00am, adding 1 week would still get it to next monday 12:00 am.
        // To ensure that Sunday's date is sent as end date we are subtracting 1 second
        c.add(Calendar.SECOND, -1);
        map.put("end", c.getTime());
        return map;
    }

    public static Date getYearStartDate(Date date) throws Exception {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }

        DateTime dt = new DateTime(date);
        int year = dt.getYear();
        String s = "01/01/" + year;
        return DateTime.parse(s, df).toDate();

    }

    public static Date getYearEndDate(Date date) throws Exception {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }

        DateTime dt = new DateTime(date);
        int year = dt.getYear();
        String s = "31/12/" + year;
        return DateTime.parse(s, df).toDate();
    }


    public static boolean isPastDateFromToday(Date date1) {
          // if date1 is in future of today
        //24/july/2016 is past  of 23/june/2017

        Locale locale = Locale.ENGLISH;
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Africa/Lagos"),locale);

        logger.info(c.getTime().toString());
        logger.info(date1.toString());
        System.out.println(c.after(date1));
        return (c.after(date1));




    }

    public static boolean isFirstDateBeforeSecondDate(Date date1, Date date2) {

        logger.info("First date " + date1.toString());
        logger.info("Second date " + date2.toString());
        System.out.println(date1.before(date2));
        return (date1.before(date2));




    }


    public static boolean isPastDate(Date date1, Date date2) {
        //If date 1 is greater than 0 means date 1 is in future of date 2

        if (date1.compareTo(date2) > 0) {
            return true;
        }

        return false;
    }

    public static boolean isFutureDate(Date date1, Date date2) {
        return false;
    }


    public static Date getMonthStartDate(Date date) throws Exception {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }

        DateTime dt = new DateTime(date);
        int year = dt.getMonthOfYear();
        String s = "01/" + year + "/" + dt.getYear();

        return DateTime.parse(s, df).toDate();

    }

    public static Date getMonthEndDate(Date date) throws Exception {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        String s = "";
        DateTime dt = new DateTime(date);
        int year = dt.getMonthOfYear();

        if (year == 2 && dt.getYear() % 4 == 0) {
            s = "29/" + year + "/" + dt.getYear();
            System.out.println(s);
        }
        if (year == 2 && dt.getYear() % 4 == 1) {
            s = "28/" + year + "/" + dt.getYear();
            System.out.println(s);
        }
        if (year != 2) {
            s = "30/" + year + "/" + dt.getYear();
            System.out.println(s);
        }

        return DateTime.parse(s, df).toDate();

    }


}
