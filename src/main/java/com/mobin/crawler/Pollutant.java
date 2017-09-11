package com.mobin.crawler;

import com.mobin.config.Config;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

/**
 * Created by Mobin on 2017/9/8.
 * 过去24小时污染物浓度
 */
public class Pollutant implements PageProcessor{
    private Site site = Site.me().setRetryTimes(3).setTimeOut(1000);
    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        for (int i = 1; i<= 14; i ++) {
            System.out.println(html.xpath("//table[@class='tblNormal']/tbody/tr["+i+"]/allText()"));
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
