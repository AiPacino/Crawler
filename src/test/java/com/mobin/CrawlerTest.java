package com.mobin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mobin.common.DatabaseConnection;
import com.mobin.config.Config;
import com.mobin.crawler.Capital;
import com.mobin.crawler.Pollutant;
import com.mobin.domain.CapitalData;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

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
        DatabaseConnection databaseConnection = new DatabaseConnection();
        final  Connection con = databaseConnection.getConnection();
        Spider.create(new PageProcessor() {
            private Site site = Site.me().setRetryTimes(3).setTimeOut(1000);
            @Override
            public void process(Page page) {
                Html html = page.getHtml();
                String city = html.xpath("//div[@class='citySelect webox']/allText()").toString();
                String str = html.getDocument().getElementsByTag("script").get(4).data().split("=")[1];
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

                }
                String sql = "INSERT INTO capital(city,time,weather,centigrade,winddirection,windrate) VALUES(?,?,?,?,?,?)";
                try {
                    PreparedStatement statement = con.prepareStatement(sql);
                    for (CapitalData data: list){
                        statement.setString(1,data.getCity());
                        statement.setString(2,data.getTime());
                        statement.setString(3,data.getWeather());
                        statement.setString(4,data.getCentigrade());
                        statement.setString(5,data.getWindDirection());
                        statement.setString(6,data.getWindRate());
                        statement.addBatch();
                    }
                    statement.executeBatch();
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        con.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public Site getSite() {
                return site;
            }
        }).addUrl("http://www.weather.com.cn/weathern/102010100.shtml").run();
    }

    @Test
    public void rex(){
        String s = "[a,c,b]";
        System.out.println(s.split("(\\[|\\])")[1]);

    }

}
