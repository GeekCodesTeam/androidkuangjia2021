package com.example.bizyewu1.bean;

/**
 * 描述：
 * -
 * 创建人：baoshengxiang
 * 创建时间：2017/8/11
 */
public class SbbdBean {


    private String signVersion;
    private String accessKey;
    private String accessSecret;

    public SbbdBean() {
    }

    public SbbdBean(String signVersion, String accessKey, String accessSecret) {
        this.signVersion = signVersion;
        this.accessKey = accessKey;
        this.accessSecret = accessSecret;
    }

    public String getSignVersion() {
        return signVersion;
    }

    public void setSignVersion(String signVersion) {
        this.signVersion = signVersion;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }
}
