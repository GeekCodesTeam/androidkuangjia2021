package com.fosung.xuanchuanlan.xuanchuanlan.main.http.entity;

import com.fosung.xuanchuanlan.mian.http.AppsBaseReplyBean;

import java.util.List;

public class XCLMainNoticeListReplayBean extends AppsBaseReplyBean {

    public List<XCLMainNoticeListReplayBean.XCLNoticeData> datalist;

    public class XCLNoticeData{

//             "id": "3366018343663314331",
//             "createUserId": "6365702234688638976",
//             "createDatetime": "2019-09-02 19:40:35",
//             "lastUpdateUserId": "6365702234688638976",
//             "lastUpdateDatetime": "2019-10-21 13:54:17",
//             "title": "召开“解放思想大讨论”专题民主生活会的通知",
//             "type": "3366016836129790996",
//             "content": "根据镇党委和区教育局党工委文件精神，经校党支部研究决定：6月30日下午3:30，在党支部活动室召开“解放思想大讨论”专题组织生活会，现将有关事项通知如下：\n\n1、学习贯彻中央\n\n2、缴纳党费，尽量微信转账。
//             "status": "VALID",
//             "recomStatus": "RECOM",
//             "pubDatetime": "2019-09-02 19:49:01"

        public String id;
        public String createUserId;
        public String createDatetime;
        public String lastUpdateUserId;
        public String lastUpdateDatetime;
        public String title;
        public String type;
        public String content;
        public String status;
        public String recomStatus;
        public String pubDatetime;

    }

}
