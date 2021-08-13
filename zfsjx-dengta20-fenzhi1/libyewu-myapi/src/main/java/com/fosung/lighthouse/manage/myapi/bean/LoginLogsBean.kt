package com.fosung.lighthouse.manage.myapi.bean

import com.haier.cellarette.libretrofit.common.ResponseSlbBean1
import java.io.Serializable

/**
 *@author: lhw
 *@date: 2021/8/12
 *@desc
 */
class LoginLogsBean : ResponseSlbBean1<Any>(), Serializable {

    //{
    //    "data": [
    //        {
    //            "logId": "4354790844059648",
    //            "clientName": "手机端登录",
    //            "loginDate": 1627549081431,
    //            "dateDetail": "5分钟前"
    //        },
    //        {
    //            "logId": "4354705114304512",
    //            "clientName": "党组织和单位信息管理系统",
    //            "loginDate": 1627543848902,
    //            "dateDetail": "1小时前"
    //        }
    //    ],
    //    "success": true,
    //    "message": ""
    //}

    var data: ArrayList<LoginLogBean>? = null

    class LoginLogBean {
        var logId: String? = null
        var clientName: String? = null
        var loginDate: String? = null
        var dateDetail: String? = null
    }


}