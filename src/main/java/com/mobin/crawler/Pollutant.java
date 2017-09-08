package com.mobin.crawler;

import com.mobin.config.Config;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by Mobin on 2017/9/8.
 */
public class Pollutant implements PageProcessor{
    private Site site = Site.me().setRetryTimes(3).setTimeOut(1000);
    @Override
    public void process(Page page) {
        System.out.println(page.getHtml().xpath("//table[@class='tblNormal']//td"));
       // System.out.println(page.getHtml().css("table.tblNormal").$("th").xpath(""));
    }

    @Override
    public Site getSite() {
        return site;
    }
}
