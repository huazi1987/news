package com.news.common.sms;

import com.alibaba.fastjson.JSONObject;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.news.common.util.EncryptUtil;
import com.news.common.util.HttpClientUtil;
import com.news.common.util.StringUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SDKIM {

    private CCPRestSmsSDK restAPI;

    private static final String SERVER_IP="app.cloopen.com";

    private static final String SERVER_PORT="8883";

    private static final String ACCOUNT="8aaf07086537124901665ca1c557056b";

    private static final String ACCOUNT_TOKEN="17c2c07f45a89072724b2ee9afe1f1f5";

    private static final String APP_ID="8aaf07086537124901665ca1c577056c";

    private static SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMddHHmmss");

    private static final String DOMAIN = "https://imapp.yuntongxun.com:8883/";

    private static final String VERSION = "2013-12-26";

    private static final String REDISTER_URL = DOMAIN+VERSION+"/Application/"+APP_ID+"/IM/Register?sig=";

    public static void register(String loginName, String nickname, String sex, String birth){
        JSONObject data = new JSONObject();
        data.put("userName", loginName);
        if (!StringUtil.isEmpty(nickname)){
            data.put("nickname", nickname);
        }else {
            data.put("nickname", loginName);
        }
        if (!StringUtil.isEmpty(sex)){
            data.put("sex", sex);
        }
        if (!StringUtil.isEmpty(nickname)){
            data.put("birth", birth);
        }
        Date nowDate = new Date();

        Map<String, String> header = new HashMap<>();
        header.put("Authorization", authorization(nowDate));
        String result  = HttpClientUtil.doPostJson(REDISTER_URL+sign(nowDate), data.toJSONString(), header);
        System.out.println(result);
    }

    private static String sign(Date nowDate){
        return EncryptUtil.encodeMD5(APP_ID+ACCOUNT_TOKEN+SDF.format(nowDate));
    }


    private static String authorization(Date nowDate){
        try {
            return EncryptUtil.encodeBASE64(APP_ID+":"+SDF.format(nowDate));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args){
        SDKIM.register("13264586161","huazai",null, null);
    }

}
