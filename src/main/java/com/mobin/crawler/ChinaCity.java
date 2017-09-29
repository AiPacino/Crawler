package com.mobin.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mobin.common.DatabaseConnection;
import com.mobin.common.InsertToPg;
import com.mobin.config.Config;
import com.mobin.domain.CapitalData;
import com.mobin.domain.ChinaCityData;
import com.mobin.domain.PollutantData;
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
import java.util.Date;
import java.util.Iterator;

/**
 * Created by Mobin on 2017/9/29.
 */
public class ChinaCity implements PageProcessor, InsertToPg {
    Logger log = LoggerFactory.getLogger(ChinaCity.class);
    private Site site = Site.me().setCycleRetryTimes(3).setTimeOut(1000);
    private final ArrayList<ChinaCityData> datas = new ArrayList<>();
    Connection conn = new DatabaseConnection().getConnection();
    PreparedStatement ps = null;
    @Override
    public void insertTopostgres() {
        String sql = "INSERT INTO chinacitys(timed,weather,temperature,windRate,windDirection,sunsup,sunset,created_at) VALUES(?,?,?,?,?,?,?,?)";
        try {
            ps = conn.prepareStatement(sql);
            for (ChinaCityData data : datas) {
                ps.setString(1, data.getTime());
                ps.setString(2, data.getWeather());
                ps.setString(3, data.getTemperature());
                ps.setString(4, data.getWindRate());
                ps.setString(5, data.getWindDirection());
                ps.setString(6, data.getSunsup());
                ps.setString(7, data.getSunset());
                ps.setTimestamp(8, data.getCreated_at());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                ps.close();
                conn.close();
            } catch (SQLException e) {
            }

        }
    }

    @Override
    public void process(Page page) {
        ChinaCityData data = new ChinaCityData();
        Html html = page.getHtml();
        String script = html.getDocument().getElementsByTag("script").get(3).data();
        String sunups = script.split(";")[7].split("=")[1];
        String sunsets = script.split(";")[8].split("=")[1];
        JSONArray sunupsJson = JSON.parseArray(sunups);
        JSONArray sunsetsJson = JSON.parseArray(sunsets);
        String str = script.split("(=\\[)|(,\\[)")[1];
        JSONArray json = JSON.parseArray(str);
        for (Iterator iterator = json.iterator(); iterator.hasNext(); ) {
            JSONObject ob = (JSONObject) iterator.next();
            data.setSunsup(sunupsJson.get(0).toString());   //日出
            data.setSunset(sunsetsJson.get(0).toString());   //日落
            data.setTemperature(ob.get("jb").toString());  //温度
            data.setWeather(Config.weatherMap.get(Integer.parseInt(ob.get("ja").toString())));  //天气现象
            data.setWindDirection(Config.windDirectionMap.get(Integer.parseInt(ob.get("jd").toString())));  //风向
            data.setWindRate(Config.windRateMap.get(Integer.parseInt(ob.get("jc").toString())));  //风速
            data.setCreated_at(new Timestamp(new Date().getTime()));
            data.setTime(ob.get("jf").toString());  //时间
        }
        synchronized (ChinaCity.class){
            count.addAndGet(1);
            datas.add(data);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
