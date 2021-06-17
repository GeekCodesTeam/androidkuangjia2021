package com.fosung.xuanchuanlan.mian.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.webkit.JavascriptInterface;

import com.fosung.frameutils.http.ZHttp;
import com.fosung.frameutils.http.response.ZResponse;
import com.fosung.frameutils.util.GsonUtil;
import com.fosung.xuanchuanlan.common.app.ConfApplication;
import com.fosung.xuanchuanlan.mian.http.AppsBaseReplyBean;
import com.fosung.xuanchuanlan.mian.http.HttpUrlMaster;
import com.fosung.xuanchuanlan.mian.http.entity.VoidSynchronizeBean;
//import com.yuntongxun.ecsdk.core.EventBus;
//import com.yuntongxun.plugin.fullconf.manager.inter.OnConfActionListener;
//import com.yuntongxun.plugin.fullconf.wrap.ConfPanel;
//import com.yuntongxun.plugin.fullconf.wrap.Configuration;
//import com.yuntongxun.plugin.fullconf.wrap.WrapManager;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;


/**
 * Company：fosung
 * Description：js原生交互
 */

public class JavaScriptInterface {
    private Context context;
    private Handler handler = new Handler();
    public static final String ACTION_THIRD_LOGIN = "ACTION_THIRD_LOGIN";
    public static final String THIRD_LOGIN_USERNAME = "THIRD_LOGIN_USERNAME";
    public static final String THIRD_LOGIN_PASSWD = "THIRD_LOGIN_PASSWD";
    public static final String THIRD_LOGIN_ROOMID = "THIRD_LOGIN_ROOMID";
    public static final String THIRD_LOGIN_TYPE = "THIRD_LOGIN_TYPE";
    public static final String THIRD_LOGIN_ISSHOWFACERECT = "THIRD_LOGIN_ISSHOWFACERECT";
    public static final String THIRD_LOGIN_LAYOUTMODE = "THIRD_LOGIN_LAYOUTMODE";
    public static final String THIRD_LOGIN_ISORG = "THIRD_LOGIN_ISORG";
    public static final int LOGIN_TYPE_HST = 2; //好视通会议账号
    public static final int LOGIN_TYPE_DT = 1; //灯塔党员账号



    public static final String THIRD_LOGIN_PATROL_MODE = "THIRD_LOGIN_PATROL_MODE"; //true for PATROL
    String user_id,user_orgCode,user_orgName,user_orgId,user_name;
    String meetingId;
    public JavaScriptInterface(Context c) {
        this.context = c;
    }

    @JavascriptInterface
    public void goBack() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Activity activity = (Activity) JavaScriptInterface.this.context;
                activity.onBackPressed();
            }
        });

    }

    @JavascriptInterface
    public void close() {
        Activity activity = (Activity) JavaScriptInterface.this.context;
        activity.finish();
    }

    @JavascriptInterface
    public void showtitle(int tag) {
                EventBus.getDefault().post(new WebActivity.TitleState(tag));
    }
    @JavascriptInterface
    public void tuneUpHaoShiTong() {
        try {
            Intent intent = new Intent(ACTION_THIRD_LOGIN);
            intent.putExtra(THIRD_LOGIN_USERNAME, "fosung0000");
            intent.putExtra(THIRD_LOGIN_PASSWD, "fs123456");
            intent.putExtra(THIRD_LOGIN_ROOMID, "10210");
            intent.putExtra(THIRD_LOGIN_PATROL_MODE, false);
            intent.putExtra(THIRD_LOGIN_TYPE, 1);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @JavascriptInterface
    public void tuneUpHaoShiTong(String name, String pwd, String roomId) {
        tuneUpHaoShiTong(name, pwd, roomId, false, LOGIN_TYPE_HST);
    }

    /**
     * @param name       用户名
     * @param pwd        密码
     * @param roomId     房间id号
     * @param login_type 1: 灯塔党员账号,2:可视化管理账号
     */
    @JavascriptInterface
    public void NewtuneUpHaoShiTong(String name, String pwd, String roomId, boolean mode, int login_type, boolean face, boolean isorg,int layout) {
        try {
            Intent intent = new Intent(ACTION_THIRD_LOGIN);
            intent.putExtra(THIRD_LOGIN_USERNAME, name);
            intent.putExtra(THIRD_LOGIN_PASSWD, pwd);
            intent.putExtra(THIRD_LOGIN_PATROL_MODE, mode);
            intent.putExtra(THIRD_LOGIN_ROOMID, roomId);
            intent.putExtra(THIRD_LOGIN_TYPE, login_type);
            intent.putExtra(THIRD_LOGIN_ISSHOWFACERECT, face);
            intent.putExtra(THIRD_LOGIN_LAYOUTMODE, layout);
            intent.putExtra(THIRD_LOGIN_ISORG, isorg);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * @param name       用户名
     * @param pwd        密码
     * @param roomId     房间id号
     * @param mode       true:巡查模式，false：非巡查模式
     * @param login_type 1: 灯塔党员账号,2:可视化管理账号
     */
    @JavascriptInterface
    public void tuneUpHaoShiTong(String name, String pwd, String roomId, boolean mode, int login_type) {
        try {
            Intent intent = new Intent(ACTION_THIRD_LOGIN);
            intent.putExtra(THIRD_LOGIN_USERNAME, name);
            intent.putExtra(THIRD_LOGIN_PASSWD, pwd);
            intent.putExtra(THIRD_LOGIN_ROOMID, roomId);
            intent.putExtra(THIRD_LOGIN_PATROL_MODE, mode);
            intent.putExtra(THIRD_LOGIN_TYPE, login_type);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public void tuneUpHaoShiTongSetting(String meetingno) {
        SharedPreferences usersp = ConfApplication.APP_CONTEXT.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        user_id=usersp.getString("user_id", "");//yong户唯一标识
        user_orgCode=usersp.getString("user_orgCode", "");//yong户唯一标识
        user_orgName=usersp.getString("user_orgName", "");//yong户唯一标识
        user_orgId=usersp.getString("user_orgId", "");//yong户唯一标识
        user_name=usersp.getString("user_name", "");//yong户唯一标识
//        if(!TextUtils.isEmpty(user_id)){
//            WrapManager.getInstance().app_AutoLogin();
//        }
//        ArrayList<Configuration> lists = new ArrayList<>();
//        ifVideo	是	string	是否录像 AGREE 是 NOT 否 默认否
//        ifScreenshot	是	string	是否截图 AGREE 是 NOT 否 默认否
        Boolean isopenRecord=false;
        Boolean isFaceCount=false;
        Boolean isopenFaceCpture=false;

//        /** 静音 */
//        lists.add(new Configuration(Configuration.PANENAME.CONF_MUTE));
//        /** 摄像头 */
//        lists.add(new Configuration(Configuration.PANENAME.CONF_VIDEO));
//        /** 扬声器 */
//        lists.add(new Configuration(Configuration.PANENAME.CONF_LOUDSPEAK));
//        /** 参会人 */
//        lists.add(new Configuration(Configuration.PANENAME.CONF_MEMBER));
////        /** 会议室锁定 */
////        lists.add(new Configuration(Configuration.PANENAME.CONF_LOCK));
////        /** 会场控制 */
////        lists.add(new Configuration(Configuration.PANENAME.CONF_CONTROL));
//        /** 结束 */
//        lists.add(new Configuration(Configuration.PANENAME.CONF_END));
//        lists.add(new Configuration(Configuration.PANENAME.CONF_SGIN));
//        /** 主讲 */
//        //   lists.add(new Configuration(Configuration.PANENAME.CONF_HOST));
//        /** 布局 */
////        lists.add(new Configuration(Configuration.PANENAME.CONF_LAYOUT));
//
//
//        ConfPanel confPanel = new ConfPanel.ConfPanelBuilder(context)
//                /**打开会议录制 只有主持人生效↓ */
//                .openRecord(isopenRecord)
//                /**打开 AI识别会议人数 未设置时间 默认30秒认别一次↓ */
//                .openFaceCount(isFaceCount)
//                /**打开 会议截图 未设置时间 默认30秒截图一张↓ */
//                .openFaceCpture(isopenFaceCpture)
//                /**设置 会议截图时间间隔 单位毫秒↓ */
//                // .setCptureDelayTime(10000)
//                /**AI识别会议人数时间间隔  单位毫秒↓ */
//                .setFaceCountDelayTime(10000)
//                /**会议功能配置 */
//                .setConfiguration(lists)
//                .setConfActionListener(new OnConfActionListener() {
//                    @Override
//                    public void onHnaldeConfAction(String action, String conferenceId, String data) {
//                        //0是解散，1是退出，2点击开始签到会进行人脸识别，  3人脸识别的数量 4是入会成功
//                        Log.e("dongdata",""+data);
//                        meetingId=conferenceId;
//                        if("0".equals(action)){
//                            outmeeting("2",conferenceId);
//                        }else if ("1".equals(action)){
//                            outmeeting("1",conferenceId);
//                        }else if ("2".equals(action)){
//                            SingBean bean= new SingBean();
//                            bean.clientType="AFR";
//                            bean.clientUserId=user_id;
//                            bean.meetingNo=conferenceId;
//                            bean.rtnInfo=data;
//                            String jsong= GsonUtil.beanToString(bean);
//                            signInfo(GsonUtil.beanToString(bean));
//                        }else if ("3".equals(action)){
//                            attendance(data);
//                        }else if ("4".equals(action)){
//                            MethodInfo();
//                        }
//                    }
//                }).build();
//
//        WrapManager.getInstance().joinPhoneMeeting(confPanel,meetingno, "");
    }

    private void outmeeting(String action,String meetingId) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("meetingNo", meetingId);
        map.put("eventType", action);
        ZHttp.post(HttpUrlMaster.VOID_MEETING_INFOSYNC, map, new ZResponse<AppsBaseReplyBean>(AppsBaseReplyBean.class) {
            @Override
            public void onSuccess(Response response, AppsBaseReplyBean resObj) {
//                ToastUtil.toastLong("成功");
            }

            @Override
            public void onError(int code, String error) {
                super.onError(code, error);
                //       ToastUtil.toastLong(error+"");
            }
        });
    }
    private void signInfo(String jsong) {
        ZHttp.postJson(HttpUrlMaster.SIGNINFO, jsong, new ZResponse<AppsBaseReplyBean>(AppsBaseReplyBean.class) {
            @Override
            public void onSuccess(Response response, AppsBaseReplyBean resObj) {
                //      ToastUtil.toastLong("成功");
            }

            @Override
            public void onError(int code, String error) {
                super.onError(code, error);
                //   ToastUtil.toastLong(error+"");
            }
        });
    }
    //入会方式信息同步
    private void MethodInfo() {
        VoidSynchronizeBean bean= new VoidSynchronizeBean();
        bean.clientType="BOX";
        bean.clientUserId=user_id;
        bean.clientUserName=user_name;
        bean.meetingNo=meetingId;
        bean.orgId=user_orgId;
        bean.orgName=user_orgName;
        bean.orgCode=user_orgCode;
        String json=GsonUtil.beanToString(bean);
        ZHttp.postJson(HttpUrlMaster.MethodInfo, json, new ZResponse<AppsBaseReplyBean>(AppsBaseReplyBean.class) {
            @Override
            public void onSuccess(Response response, AppsBaseReplyBean resObj) {
                //      ToastUtil.toastLong("成功MethodInfo");
            }

            @Override
            public void onError(int code, String error) {
                super.onError(code, error);
                //             ToastUtil.toastLong(error+"");
            }
        });
    }
    //参会人数同步,数人头
    private void attendance(String num) {
        VoidSynchronizeBean bean= new VoidSynchronizeBean();
        bean.clientType="BOX";
        bean.clientUserId=user_id;
        bean.clientUserName=user_name;
        bean.meetingNo=meetingId;
        bean.num=num;
        String json=GsonUtil.beanToString(bean);
        ZHttp.postJson(HttpUrlMaster.ATTENDCE, json, new ZResponse<AppsBaseReplyBean>(AppsBaseReplyBean.class) {
            @Override
            public void onSuccess(Response response, AppsBaseReplyBean resObj) {
                //    ToastUtil.toastLong("成功attendance");
            }

            @Override
            public void onError(int code, String error) {
                super.onError(code, error);
                //      ToastUtil.toastLong(error+"");
            }
        });
    }

}
