package com.mobin.crawler;

import com.mobin.common.DatabaseConnection;
import com.mobin.common.InsertToPg;
import com.mobin.config.Config;
import com.mobin.domain.CapitalData;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Locale.US;

/**
 * Created by Mobin on 2017/9/8.
 * 过去24小时污染物浓度
 */
public class Pollutant implements PageProcessor, InsertToPg {
    Logger log = LoggerFactory.getLogger(Pollutant.class);
    private Site site = Site.me().setCycleRetryTimes(3).setTimeOut(1000);
    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private final ArrayList<PollutantData> datas = new ArrayList<>();
    Connection conn = new DatabaseConnection().getConnection();
    PreparedStatement ps = null;

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        PollutantData data = new PollutantData();
        String[] t = html.xpath("//table[@class='tblNormal']/tbody/tr[2]/td[1]/allText()").toString().split(" ");
        data.setStation(html.xpath("//div[@id='dd_stnh24_stationName']/text()").toString());
        try {
            data.setCollected_at(new Timestamp(df.parse(t[0] + " " + t[1]).getTime()));
            data.setNo2(parseDouble(html.xpath("//table[@class='tblNormal']/tbody/tr[2]/td[2]/allText()").toString()));
            data.setO3(parseDouble(html.xpath("//table[@class='tblNormal']/tbody/tr[2]/td[3]/allText()").toString()));
            data.setSo2(parseDouble(html.xpath("//table[@class='tblNormal']/tbody/tr[2]/td[4]/allText()").toString()));
            data.setCo(parseDouble(html.xpath("//table[@class='tblNormal']/tbody/tr[2]/td[5]/allText()").toString()));
            data.setPm10(parseDouble(html.xpath("//table[@class='tblNormal']/tbody/tr[2]/td[6]/allText()").toString()));
            data.setPm25(parseDouble(html.xpath("//table[@class='tblNormal']/tbody/tr[2]/td[7]/allText()").toString()));
            data.setCreated_at(new Timestamp(Calendar.getInstance().getTime().getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        synchronized (PollutantData.class) {
            count.addAndGet(1);
            datas.add(data);
        }
    }


    @Override
    public Site getSite() {
        return site;
    }

    public double parseDouble(String source) {
        return "-".equals(source) ? 0.0 : Double.parseDouble(source);
    }

    @Override
    public void insertTopostgres() {
        String sql = "INSERT INTO pollutants(station,collected_at,no2,o3,so2,co,pm10,pm25,created_at) VALUES(?,?,?,?,?,?,?,?,?)";
        try {
            ps = conn.prepareStatement(sql);
            for (PollutantData data : datas) {
                ps.setString(1, data.getStation());
                ps.setTimestamp(2, data.getCollected_at());
                ps.setDouble(3, data.getNo2());
                ps.setDouble(4, data.getO3());
                ps.setDouble(5, data.getSo2());
                ps.setDouble(6, data.getCo());
                ps.setDouble(7, data.getPm10());
                ps.setDouble(8, data.getPm25());
                ps.setTimestamp(9, data.getCreated_at());
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
}
