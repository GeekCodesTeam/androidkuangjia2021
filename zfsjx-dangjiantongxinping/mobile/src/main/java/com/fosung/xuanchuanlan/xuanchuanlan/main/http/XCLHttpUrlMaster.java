/*
 * *********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     17-10-9 下午12:07
 * ********************************************************
 */

package com.fosung.xuanchuanlan.xuanchuanlan.main.http;


/**
 * HttpUrl地址常量类
 * <p/>
 */
public class XCLHttpUrlMaster {


    public static final String BASEURL_DEBUG = "http://10.1.1.19:6300/api";
    public static final String BASEURL_RELEASE = "http://119.188.115.251:6300/api";

//    public static final String BASEURL = BuildConfig.IS_RELEASE_URL ? BASEURL_RELEASE : BASEURL_RELEASE;//ip地址
    public static final String BASEURL = BASEURL_RELEASE;//ip地址
//    public static final String BASEURL = "http://192.168.104.165:8081/api";//ip地址

    public static final String CUSTOMERSERVICE = BASEURL + "/fssocietycustomer/queryall";//客户服务
    public static final String TRAVEL = BASEURL + "/fssocietytourism/queryall";//特色旅游

//    public static final String DTDJ_SWGK = "https://swgk.dtdjzx.gov.cn/sanwu_phone/mobile.jsp";//三务公开页面地址

    public static final String DKTMAINTYPE = BASEURL + "/fssocietyhistorytype/queryall";//大课堂首页分类

    public static final String DKTCourseList = BASEURL + "/fssocietycourse/selectBySpecialId";//大课堂根据专题id（3370426038818449131）获取课程
    public static final String DKTMAINTLIST = BASEURL + "/fssocietycourseware/selectByTypr";//大课堂分类查专题

    public static final String NOTICETYPE = BASEURL + "/fssocietyannountypetype/queryall";//通知公告首页分类
    public static final String NOTICELIST = BASEURL + "/fssocietyannouncement/selectByType";//通知公告首页列表
    public static final String DYNAMICLIST = BASEURL + "/fssocietylcoalnews/queryall";//通知公告首页列表
    public static final String REDTYPE = BASEURL + "/fssocietyboxtype/queryAllBillboard/billboard";//红色背板分类
    public static final String REDLIST = BASEURL + "/fssocietybillboard/selectByType";//红色背板列表
    public static final String CENTERINFO = BASEURL + "/fssocietycenteroverview/queryall";//中心简介
    public static final String ORGSTRUCTURE = BASEURL + "/fssocietyorganization/queryall";//组织架构
    public static final String SERVICETEAM = BASEURL + "/fssocietyserviceteam/queryall";//组织架构

    public static final String XCLMainNoitceList = BASEURL + "/fssocietyannouncement/selectByRecom";//宣传栏轮播通知接口


}
