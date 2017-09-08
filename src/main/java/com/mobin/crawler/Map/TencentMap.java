package com.mobin.crawler.Map;

import com.mobin.config.Config;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by Mobin on 2017/9/8.
 */
public class TencentMap implements PageProcessor{
    private static final String TENCENT_AK = Config.TENCENT_AK;

    @Override
    public void process(Page page) {

    }

    @Override
    public Site getSite() {
        return null;
    }
}
