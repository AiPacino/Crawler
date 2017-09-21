package com.mobin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mobin.common.DatabaseConnection;
import com.mobin.config.Config;
import com.mobin.crawler.Capital;
import com.mobin.crawler.Pollutant;
import com.mobin.domain.CapitalData;
import org.junit.Test;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Mobin on 2017/9/8.
 */
public class CrawlerTest {
    @Test
    public void pollutant(){
        Spider.create(new Pollutant()).addUrl(Config.getStringProperties("pollutantURL")).run();
    }

    @Test
    public void capital() throws SQLException {
        Long startTime = System.currentTimeMillis();
        Capital capital = new Capital();
        Spider.create(capital).
                addUrl(Config.getStringProperties("capitalURL").split(",")).
                thread(5).run();
        capital.insertTopostgres();
        Long endTime = System.currentTimeMillis();
        System.out.println(capital.count);
        System.out.println("爬虫总消耗时间：" + (endTime - startTime)+ "ms");
    }

    @Test
    public void capital1(){
        final ArrayList<CapitalData> list = new ArrayList();
//        DatabaseConnection databaseConnection = new DatabaseConnection();
//        final  Connection con = databaseConnection.getConnection();
        Spider.create(new PageProcessor() {
            private Site site = Site.me().setRetryTimes(3).setTimeOut(1000);
            @Override
            public void process(Page page) {
                Html html = page.getHtml();
                String city = html.xpath("//div[@class='crumbs fl']/a/allText()").toString();
                String str = html.getDocument().getElementsByTag("script").get(2).data().split("=")[1];
                JSONObject json = JSON.parseObject(str);
                String s = json.getJSONArray("7d").get(1).toString().split("(\\[|\\])")[1];
                String[] str1 = s.split(",\"");
                for (int i = 0; i < str1.length; i ++) {
                    CapitalData data = new CapitalData();
                    String[] info = str1[i].replaceAll("\"","").split(",");
                    data.setCity(city);
                    data.setTime(info[0]);
                    data.setWeather(info[2]);
                    data.setCentigrade(info[3]);
                    data.setWindDirection(info[4]);
                    data.setWindRate(info[5]);
                    System.out.println(data);
                    synchronized (list){
                        list.add(data);
                    }
                    System.out.println(data);
                }

//                String sql = "INSERT INTO capitals(city,time,weather,centigrade,winddirection,windrate,created_at,updated_at) VALUES(?,?,?,?,?,?,?,?)";
//                try {
//                    PreparedStatement statement = con.prepareStatement(sql);
//                    for (CapitalData data: list){
//                        statement.setString(1,data.getCity());
//                        statement.setString(2,data.getTime());
//                        statement.setString(3,data.getWeather());
//                        statement.setString(4,data.getCentigrade());
//                        statement.setString(5,data.getWindDirection());
//                        statement.setString(6,data.getWindRate());
//                        statement.setTimestamp(7,new Timestamp(new Date().getTime()));
//                        statement.setTimestamp(8, new Timestamp(new Date().getTime()));
//                        statement.addBatch();
//                    }
//                    statement.executeBatch();
//                    statement.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }finally {
//                    try {
//                        con.close();
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                }
            }

            @Override
            public Site getSite() {
                return site;
            }
        }).addUrl("http://www.weather.com.cn/weather/101010100.shtml").run();
    }

    @Test
    public void rex(){
        String s = "[a,c,b]";
        System.out.println(s.split("(\\[|\\])")[1]);
        Calendar calendar= Calendar.getInstance();
        System.out.println(calendar.get(Calendar.MONTH)+ 1);
        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.getTime().getTime());
        System.out.println(calendar.getTimeInMillis());
    }

}
