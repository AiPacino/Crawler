package com.mobin.common;

import org.jsoup.select.Evaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Mobin on 2017/9/11.
 */
public class CrawlerOptions {
    private static final Logger log = LoggerFactory.getLogger(CrawlerOptions.class);
    public String type = null;
    public int threadNum = 1;

    public CrawlerOptions(String[] args){
        for (int i = 0; i < args.length; i ++) {
            String a = args[i];
            switch (a) {
                case "-type":
                    type = args[++ i];
                    break;
                case "-threadNum":
                    threadNum = Integer.valueOf(args[++ i]);
                    break;
                default:
                    log.error("无效的参数" + a);
                    System.exit(-1);
            }
        }
    }

    @Override
    public String toString() {
        return "CrawlerOptions：[type=" +type+",threadNum="+threadNum+"]";
    }
}
