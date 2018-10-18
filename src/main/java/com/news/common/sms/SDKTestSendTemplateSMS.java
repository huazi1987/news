package com.news.common.sms;

import java.util.HashMap;

import com.cloopen.rest.sdk.CCPRestSmsSDK;

public class SDKTestSendTemplateSMS {

    private CCPRestSmsSDK restAPI;

    private static final String SERVER_IP="app.cloopen.com";

    private static final String SERVER_PORT="8883";

    private static final String ACCOUNT="8aaf07086537124901665ca1c557056b";

    private static final String ACCOUNT_TOKEN="7944e658512140ea824e82a74445c377";

    private static final String APP_ID="8aaf07086537124901665ca1c577056c";

    private static final String TEMPLATE_ID = "355837";

    public SDKTestSendTemplateSMS(){
        restAPI = new CCPRestSmsSDK();
        restAPI.init(SERVER_IP, SERVER_PORT);
        restAPI.setAccount(ACCOUNT, ACCOUNT_TOKEN);
        restAPI.setAppId(APP_ID);
    }

    public boolean sendSMS(String phoneNum, int num){
        HashMap<String, Object> result = restAPI.sendTemplateSMS(phoneNum,TEMPLATE_ID ,new String[]{String.valueOf(num),"5"});

        System.out.println("SDKTestGetSubAccounts result=" + result);
        if("000000".equals(result.get("statusCode"))){
            return true;
        }else{
            System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
            return false;
        }
    }

    public static void main(String[] args){
        SDKTestSendTemplateSMS sdkTestSendTemplateSMS = new SDKTestSendTemplateSMS();
        sdkTestSendTemplateSMS.sendSMS("13264586161", 612345);
    }

}
