/*
 * *********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     17-10-9 下午12:07
 * ********************************************************
 */

package com.fosung.xuanchuanlan.mian.http;


/**
 * HttpUrl地址常量类
 * <p/>
 */
public class HttpUrlMaster {


    private static final String BASE_URL_DEBUG = "http://10.1.1.19:6200";
    private static final String BASE_URL_RELEASE = "http://119.188.115.251:6200";//release地址

    private static final String BASE_URL_VOID = "http://27.221.110.100:9200";//gov release地址

    private static final String BASE_URL_RONGLIAN = "http://62.234.47.181:8882";//gov release地址

//    public static final String BASE_URL = BuildConfig.IS_RELEASE_URL? BASE_URL_RELEASE: BASE_URL_RELEASE;
    public static final String BASE_URL = BASE_URL_RELEASE;


    public static final String GRANTINFO = BASE_URL + "/api/boxadmin/fsboxmachine/queryMachineInfo";
    public static final String GRANTLOGO = BASE_URL + "/api/boxadmin/fsboxmachine/queryMachineLogo";
    public static final String CHECKBOX = BASE_URL + "/api/boxadmin/fsboxmachine/checkBox";
    public static final String REGISTERCODE = BASE_URL + "/api/boxadmin/fsboxmachine/activateBox";
    public static final String FETVIDEO = BASE_URL + "/api/boxadmin/fsboxmachine/getVideo";
    public static final String FETVIDEO_PRI = BASE_URL + "/api/boxadmin/fsboxmachine/queryall";
    public static final String  MachineAdvertisement= BASE_URL + "/api/boxadmin/fsboxmachine/queryMachineAdvertisement";
    public static final String VOID_LIST = BASE_URL_VOID + "/api/meeting/out/list";
    public static final String VOID_CREATE = BASE_URL_VOID + "/api/meeting/out/create";
    public static final String VOID_MEETING_CHECK = BASE_URL_VOID + "/api/meeting/out/joinCheck";
    public static final String VOID_MEETING_INFOSYNC = BASE_URL_VOID + "/api/meeting/out/infoSync";
    public static final String CHECH_ID = BASE_URL_VOID + "/api/meeting/out/identityVerify";//身份证号
    public static final String UPLOAD_USER_PIC = BASE_URL_VOID + "/api/meeting/out/faceUpload";//上传图片
    public static final String SIGNINFO= BASE_URL_VOID + "/api/meeting/out/signInfo";//上传图片
    public static final String ATTENDCE= BASE_URL_VOID + "/api/meeting/out/attendance";//参会人数同步,数人头
    public static final String MethodInfo= BASE_URL_VOID + "/api/meeting/out/meetingMethodInfo";//入会方式信息同步
    public static final String FACESEARCH= BASE_URL_RONGLIAN + "/cv/v1/faceSearch";//人脸检测

    public static final String USER_ROOT = "http://103.239.153.238:82";//OAUTH 跟地址
    public static final String USER_LOGIN = USER_ROOT + "/oauth/token";//登录接口
    public static final String USER_INFO = USER_ROOT + "/oauth/userinfo";//用户信息接口
    public static final String UPDATE = "http://s.dtdjzx.gov.cn/cloud";//更新接口
    public static final String UPDATEINFO= UPDATE + "/update/check";//更新接口信息

    //首页布局接口
    private static final String BASE_MDP = "http://10.1.1.19:9200"; //正式映射124.133.15.90:6024 马德鹏
    public static final String FirstPage_Version = BASE_URL + "/api/fsboxgrant/getVersion/";//检查首页布局版本
    public static final String FirstPage = BASE_URL + "/api/fsboxgrant/boxviewgrant/";//首页
}
