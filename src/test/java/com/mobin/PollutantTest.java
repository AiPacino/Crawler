package com.mobin;

import com.mobin.config.Config;
import com.mobin.crawler.Pollutant;
import org.junit.Test;
import us.codecraft.webmagic.Spider;

/**
 * Created by Mobin on 2017/9/8.
 */
public class PollutantTest {
    @Test
    public void pollutant(){
        Spider.create(new Pollutant()).addUrl(Config.getStringProperties("pollutantURL")).run();
    }
}
