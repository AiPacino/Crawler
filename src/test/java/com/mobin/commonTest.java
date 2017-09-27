package com.mobin;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Mobin on 2017/9/27.
 */
public class commonTest {
    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Test
    public void stringToDate() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(df.parse("2017-09-27 14:00:00"));
        calendar.roll(Calendar.HOUR_OF_DAY, -1 );
        System.out.println(calendar.getTime());
    }
}
