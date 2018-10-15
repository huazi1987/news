package com.news.common.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.Credentials;
import com.aliyun.oss.common.auth.CredentialsProvider;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;


public class AliYunUtil {

    private final static String accessKeyId = "LTAIZq8U8NAXgyho";
    private final static String accessKeySecret = "VUzE3Hg3Ukz08UALNe7af0FJYsDfgc";


    public static OSSClient getOSSClient(String endpoint){
        CredentialsProvider credentialsProvider = new CredentialsProvider() {
            @Override
            public void setCredentials(Credentials credentials) {
            }

            @Override
            public Credentials getCredentials() {

                return new PrivateCredential();
            }
        };
        return new OSSClient(endpoint, credentialsProvider, null);
    }

    static class PrivateCredential implements Credentials{

        @Override
        public String getAccessKeyId() {
            return accessKeyId;
        }

        @Override
        public String getSecretAccessKey() {
            return accessKeySecret;
        }

        @Override
        public String getSecurityToken() {
            return null;
        }

        @Override
        public boolean useSecurityToken() {
            return false;
        }
    }

//
//    public static void main(String[] args){
//        OSSClient ossClient= AliYunUtil.getOSSClient("http://oss-cn-beijing.aliyuncs.com");
//        File file = new File("/Users/zhanghua/Desktop/Untitled-3.html");
//        ossClient.putObject("communication-emperor","image/1111.html", file);
//    }

}
