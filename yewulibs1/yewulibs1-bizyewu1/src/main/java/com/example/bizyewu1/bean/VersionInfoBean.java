package com.example.bizyewu1.bean;

/**
 * 描述：
 * -
 * 创建人：baoshengxiang
 * 创建时间：2017/8/11
 */
public class VersionInfoBean {
    /**
     * id : 1
     * versionNumber : 60
     * versionInfo : 第一版
     * type : 0
     * targetUrl : http://file.znclass.com/znxt-2.1.0-release60-2019.12.24-apk.apk
     * isOpen : 0
     */

    private int id;
    private int versionNumber;
    private String versionInfo;
    private int type;
    private String targetUrl;
    private int isOpen;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(String versionInfo) {
        this.versionInfo = versionInfo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }

  /*  private InfoBean versionInfo;

    public InfoBean getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(InfoBean versionInfo) {
        this.versionInfo = versionInfo;
    }

    public static class InfoBean {
        private String versionInfo;
        private int versionNumber;
        private String targetUrl;

        public String getVersionInfo() {
            return versionInfo;
        }

        public void setVersionInfo(String versionInfo) {
            this.versionInfo = versionInfo;
        }

        public int getVersionNumber() {
            return versionNumber;
        }

        public void setVersionNumber(int versionNumber) {
            this.versionNumber = versionNumber;
        }

        public String getTargetUrl() {
            return targetUrl;
        }

        public void setTargetUrl(String targetUrl) {
            this.targetUrl = targetUrl;
        }
    }
*/

}
