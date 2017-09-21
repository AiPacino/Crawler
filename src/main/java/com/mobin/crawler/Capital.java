package com.mobin.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.annotations.VisibleForTesting;
import com.mobin.common.DatabaseConnection;
import com.mobin.common.InsertTopg;
import com.mobin.config.Config;
import com.mobin.domain.CapitalData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Mobin on 2017/9/11.
 */
public class Capital implements PageProcessor, InsertTopg{
    Logger log = LoggerFactory.getLogger(Capital.class);
    private Site site = Site.me().setCycleRetryTimes(3).setTimeOut(1000);
    private final ArrayList<CapitalData> datas = new ArrayList<>();
    Connection conn = new DatabaseConnection().getConnection();
    PreparedStatement ps = null;
    public static final AtomicInteger count = new AtomicInteger();
    private static boolean isBeijiang = true;
    private String city = null;
    private String weatherInfo_all = null;
    private int index = 4;


    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        if (isBeijiang && page.getUrl().toString().endsWith("101010100.shtml")) {
            city = html.xpath("//div[@class='crumbs fl']/a/allText()").toString();
            index = 2;
            isBeijiang = false;
        }else {
            city = html.xpath("//div[@class='citySelect webox']/allText()").toString();
            index = 4;
        }
        weatherInfo_all = weatherInfoAll(html,index);
        JSONObject json = JSON.parseObject(weatherInfo_all);
        String weatherInfo_7d = json.getJSONArray("7d").get(1).toString().split("(\\[|\\])")[1];
        String[] str1 = weatherInfo_7d.split(",\"");
        add(city, str1);
    }

    public String weatherInfoAll(Html html,int index){
        return html.getDocument().getElementsByTag("script").get(index).data().split("=")[1];
    }

    @Override
    public Site getSite() {
        return site;
    }

    public synchronized void add(String city,String[] infos){
        for (int i = 0; i < infos.length; i ++) {
            count.addAndGet(1);
            CapitalData data = new CapitalData();
            String[] info = infos[i].replaceAll("\"","").split(",");
            data.setCity(city);
            data.setTime(info[0]);
            data.setWeather(info[2]);
            data.setCentigrade(info[3]);
            data.setWindDirection(info[4]);
            data.setWindRate(info[5]);
            datas.add(data);
        }
    }

    @Override
    public void insertTopostgres(){
        String sql = "INSERT INTO capitals(city,time,weather,centigrade,winddirection,windrate,created_at, updated_at) VALUES(?,?,?,?,?,?,?,?)";
        try {
            ps = conn.prepareStatement(sql);
            for (CapitalData data: datas) {
                ps.setString(1,data.getCity());
                ps.setString(2, Config.YEAR + "年" + Config.MONTH + "月" + data.getTime());
                ps.setString(3,data.getWeather());
                ps.setString(4,data.getCentigrade());
                ps.setString(5,data.getWindDirection());
                ps.setString(6,data.getWindRate());
                ps.setTimestamp(7,Config.TIMESTAM);
                ps.setTimestamp(8,Config.TIMESTAM);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (Exception e) {
            throw  new RuntimeException(e);
        }finally {
            try {
                ps.close();
                conn.close();
            } catch (SQLException e) {
            }

        }
    }
}
