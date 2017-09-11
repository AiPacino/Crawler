package com.mobin.crawler;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

/**
 * Created by Mobin on 2017/9/11.
 */
public class Capital implements PageProcessor{
    private Site site = Site.me().setCycleRetryTimes(3).setTimeOut(1000);
    @Override
    public void process(Page page) {
        Html html = page.getHtml();
//        System.out.println(html.xpath("//ul[@class='t clearfix']/li[@class='on']/div[@class='d1']/allText()"));
//        System.out.println(html.xpath("//ul[@class='t clearfix']/li[@class='on']/div[@class='d2']/allText()"));
        System.out.println(html.xpath("//div[@class='citySelect webox']/allText()").toString() + html.xpath("//div[@class='w_jingdian']/dl[3]/allText()").toString());
    }

    @Override
    public Site getSite() {
        return site;
    }
}
