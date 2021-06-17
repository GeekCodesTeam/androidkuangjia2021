package com.fosung.xuanchuanlan.mian.http.entity;

import com.fosung.xuanchuanlan.mian.http.AppsBaseReplyBean;

public class UpdateBean extends AppsBaseReplyBean {

    /**
     * data : {"updateMsg":{"id":"3367803531250630731","createUserId":null,"createDatetime":"2019-09-12 10:35:27","lastUpdateUserId":null,"lastUpdateDatetime":"2019-09-12 10:35:27","appName":"dsf","appUpdateCode":"DTWS_00","channelCode":"Android","versionState":"lts","rangeStrategy":"^((13[0-9])|(14[579])|(15([0-3,5-9]))|(16[6])|(17[0135678])|(18[0-9]|19[89]))\\d{8}$","versionInfo":"2.12.35","updateStrategy":"1","updateContent":"1212","url":"http://wwww.baidu.com","updateRemark":"dsds"},"update":true}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * updateMsg : {"id":"3367803531250630731","createUserId":null,"createDatetime":"2019-09-12 10:35:27","lastUpdateUserId":null,"lastUpdateDatetime":"2019-09-12 10:35:27","appName":"dsf","appUpdateCode":"DTWS_00","channelCode":"Android","versionState":"lts","rangeStrategy":"^((13[0-9])|(14[579])|(15([0-3,5-9]))|(16[6])|(17[0135678])|(18[0-9]|19[89]))\\d{8}$","versionInfo":"2.12.35","updateStrategy":"1","updateContent":"1212","url":"http://wwww.baidu.com","updateRemark":"dsds"}
         * update : true
         */

        public UpdateMsgBean updateMsg;
        public boolean update;

        public static class UpdateMsgBean {
            /**
             * id : 3367803531250630731
             * createUserId : null
             * createDatetime : 2019-09-12 10:35:27
             * lastUpdateUserId : null
             * lastUpdateDatetime : 2019-09-12 10:35:27
             * appName : dsf
             * appUpdateCode : DTWS_00
             * channelCode : Android
             * versionState : lts
             * rangeStrategy : ^((13[0-9])|(14[579])|(15([0-3,5-9]))|(16[6])|(17[0135678])|(18[0-9]|19[89]))\d{8}$
             * versionInfo : 2.12.35
             * updateStrategy : 1
             * updateContent : 1212
             * url : http://wwww.baidu.com
             * updateRemark : dsds
             */

            public String id;
            public String appName;
            public String appUpdateCode;
            public String channelCode;
            public String versionState;
            public String rangeStrategy;
            public String versionInfo;
            public String updateStrategy;
            public String updateContent;
            public String url;
            public String updateRemark;
        }
    }
}
