package com.news.common.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.Credentials;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.ProfileConfigFile;


public class AliYunUtil {

    private static ProfileConfigFile profileConfigFile = new ProfileConfigFile("/usr/local/credentials");

    public static OSSClient getOSSClient(String endpoint){
        final CredentialsProvider credentialsProvider = new CredentialsProvider() {
            @Override
            public void setCredentials(Credentials credentials) {
            }

            @Override
            public Credentials getCredentials() {
                return profileConfigFile.getCredentials();
            }
        };
        return new OSSClient(endpoint, credentialsProvider, null);
    }


}
