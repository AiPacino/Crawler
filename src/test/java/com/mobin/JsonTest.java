package com.mobin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

/**
 * Created by Mobin on 2017/9/12.
 */
public class JsonTest {
    @Test
    public void strToJson(){
        String str = "{\"1d\":[\"12日09时,d01,多云,22℃,西风,微风\"]}";
        JSONObject json = JSON.parseObject(str);
        System.out.println(json.get("1d"));
    }
}
