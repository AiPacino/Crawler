package com.mobin;

import org.junit.Test;

/**
 * Created by Mobin on 2017/9/25.
 */
public class WeatherEnumTest {
    @Test
    public void enumTest(){
        String e = WeatherEnum.get("1");
        System.out.println(e);
    }

}
