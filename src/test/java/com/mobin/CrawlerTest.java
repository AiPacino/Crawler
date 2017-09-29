package com.mobin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mobin.common.DatabaseConnection;
import com.mobin.config.Config;
import com.mobin.crawler.Capital;
import com.mobin.crawler.Pollutant;
import com.mobin.domain.CapitalData;
import com.mobin.domain.ChinaCityData;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import javax.sound.midi.Soundbank;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by Mobin on 2017/9/8.
 */
public class CrawlerTest {
    @Test
    public void pollutant() {
        Spider.create(new Pollutant()).addUrl(Config.getStringProperties("pollutantURL").split(",")).run();
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
        System.out.println("爬虫总消耗时间：" + (endTime - startTime) + "ms");
    }

    @Test
    public void capital1() {
        final ArrayList<CapitalData> list = new ArrayList();
//        DatabaseConnection databaseConnection = new DatabaseConnection();
//        final  Connection con = databaseConnection.getConnection();
        Spider.create(new PageProcessor() {
            private Site site = Site.me().setRetryTimes(3).setTimeOut(1000);

            @Override
            public void process(Page page) {
                Html html = page.getHtml();
                String city = html.xpath("//div[@class='crumbs fl']/a/allText()").toString();
                //  System.out.println(html.getDocument().getElementsByTag("script").get(4).data().split("=")[1]);
                String weatherInfo_all = html.getDocument().getElementsByTag("script").get(4).data().split("=")[1];
                JSONObject json = JSON.parseObject(weatherInfo_all);
                System.out.println(json.get("7d"));
//                String weatherInfo_7d = json.getJSONArray("7d").get(1).toString().split("(\\[|\\])")[1];
//                String[] str1 = weatherInfo_7d.split(",\"");
//                for (int i = 0; i < str1.length; i ++) {
//                    CapitalData data = new CapitalData();
//                    String[] info = str1[i].replaceAll("\"","").split(",");
//                    data.setCity(city);
//                    data.setTime(info[0]);
//                    data.setWeather(info[2]);
//                    data.setCentigrade(info[3]);
//                    data.setWindDirection(info[4]);
//                    data.setWindRate(info[5]);
//                    System.out.println(data);
//                    synchronized (list){
//                        list.add(data);
//                    }
//                    System.out.println(data);
//                }

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
        }).addUrl("http://www.weather.com.cn/weathern/224010100.shtml").run();
    }

    @Test
    public void chinaCity() {
        final ArrayList<CapitalData> list = new ArrayList();
//        DatabaseConnection databaseConnection = new DatabaseConnection();
//        final  Connection con = databaseConnection.getConnection();
        Spider.create(new PageProcessor() {
            private Site site = Site.me().setRetryTimes(3).setTimeOut(1000);

            @Override
            public synchronized void process(Page page) {
                ChinaCityData data = new ChinaCityData();
                Html html = page.getHtml();
                String script = html.getDocument().getElementsByTag("script").get(3).data();
                String sunups = script.split(";")[7].split("=")[1];
                String sunsets = script.split(";")[8].split("=")[1];
                JSONArray sunupsJson = JSON.parseArray(sunups);
                JSONArray sunsetsJson = JSON.parseArray(sunsets);
                String str = script.split("(=\\[)|(,\\[)")[1];
                JSONArray json = JSON.parseArray(str);
                Config.initWindRateMap();
                Config.initWeatherMap();
                Config.initWindDirectionMap();
                for (Iterator iterator = json.iterator(); iterator.hasNext(); ) {
                    JSONObject ob = (JSONObject) iterator.next();
                    data.setSunsup(sunupsJson.get(0).toString());   //日出
                    data.setSunset(sunsetsJson.get(0).toString());   //日落
                    data.setTemperature(ob.get("jb").toString());  //温度
                    data.setWeather(Config.weatherMap.get(Integer.parseInt(ob.get("ja").toString())));  //天气现象
                    data.setWindDirection(Config.windDirectionMap.get(Integer.parseInt(ob.get("jd").toString())));  //风向
                    data.setWindRate(Config.windRateMap.get(Integer.parseInt(ob.get("jc").toString())));  //风速
                    data.setTime(ob.get("jf").toString());  //时间
                    System.out.println(data);
                }
            }

            @Override
            public Site getSite() {
                return site;
            }
        }).addUrl("http://www.weather.com.cn/weather1dn/101010100.shtml").run();
    }


    @Test
    public void rex() {
        String s = "[a,c,b]";
        System.out.println(s.split("(\\[|\\])")[1]);
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.MONTH) + 1);
        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.getTime().getTime());
        System.out.println(calendar.getTimeInMillis());
    }

    @Test
    public void weather() {
        Spider.create(new PageProcessor() {
            private Site site = Site.me().setRetryTimes(3).setTimeOut(1000);

            @Override
            public void process(Page page) {
                Html html = page.getHtml();
                String time = html.xpath("//div[@id='day0']/div[1]/div[2]/allText()").toString();
                /**
                 * 1 ：晴
                 * 2：阴
                 * 3：阵雨
                 * 4：雷阵雨
                 * */
                for (int i = 2; i <= 9; i++) {
                    ChinaCityData data = new ChinaCityData();
                 /*   data.setTp(html.xpath("//div[@id='day0']/div[1]/div["+i+"]/allText()").toString());
                    data.setWeather( WeatherEnum.get(html.xpath("//div[@id='day0']/div[2]/div["+i+"]/img/@src").toString().split("\\/day\\/|\\.png")[1]));
                    data.setTemperature( html.xpath("//div[@id='day0']/div[3]/div["+i+"]/allText()").toString());
                    data.setRainfall( html.xpath("//div[@id='day0']/div[4]/div["+i+"]/allText()").toString());
                    data.setWindRate( html.xpath("//div[@id='day0']/div[5]/div["+i+"]/allText()").toString());
                    data.setWindDirection( html.xpath("//div[@id='day0']/div[5]/div["+i+"]/allText()").toString());
                    data.setAirPressure( html.xpath("//div[@id='day0']/div[7]/div["+i+"]/allText()").toString());
                    data.setHumidity( html.xpath("//div[@id='day0']/div[8]/div["+i+"]/allText()").toString());
                    data.setCludeage( html.xpath("//div[@id='day0']/div[9]/div["+i+"]/allText()").toString());
                    data.setVisibility( html.xpath("//div[@id='day0']/div[10]/div["+i+"]/allText()").toString());
                    System.out.println(data);*/
                }
//                System.out.println(time);
//                System.out.println(weather);
//                System.out.println(temp);
            }

            @Override
            public Site getSite() {
                return site;
            }
        }).addUrl("http://www.nmc.cn/publish/forecast/AGD/shen-zhen.html").run();
    }

    @Test
    public void weather1() {
        Spider.create(new PageProcessor() {
            private Site site = Site.me().setRetryTimes(3).setTimeOut(1000);

            @Override
            public void process(Page page) {
                String s = page.getHtml().xpath("//*[@id='drawTF']/svg/text[1]/tspan").toString();
                System.out.println(s);
            }

            @Override
            public Site getSite() {
                return site;
            }
        }).addUrl("http://www.weather.com.cn/weathern/101280101.shtml").run();
    }
}
