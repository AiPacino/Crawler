package com.mobin.crawler.Map;

import com.mobin.config.Config;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

import static com.mobin.config.Config.*;

/**
 * Created by Mobin on 2017/9/8.
 */
public class BaiduMap implements PageProcessor{
    private static final String BAIDU_AK = Config.BAIDU_AK;
    private static final String urlStr = "";

    public void process(Page page) {
        int dataSize = new JsonPathSelector("$.results[*]")
                .selectList(page.getRawText()).size();
    }

    public Site getSite() {
        return null;
    }

//    static class URLProperties{
//        private static final String URLStr = "http://api.map.baidu.com/place/v2/search?q=";
//        public String q;
//        public String tag;
//
//        @Override
//        public String toString() {
//            return "URLProperties{" +
//                    "q=" + q +
//                    ", tag=" + tag +
//                    '}';
//        }
//    }


}
