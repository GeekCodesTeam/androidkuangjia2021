package com.fosung.xuanchuanlan.mian.activity;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fosung.frameutils.http.ZHttp;
import com.fosung.frameutils.http.response.ZResponse;
import com.fosung.frameutils.util.GsonUtil;
import com.fosung.xuanchuanlan.R;
import com.fosung.xuanchuanlan.common.app.ConfApplication;
import com.fosung.xuanchuanlan.mian.http.AppsBaseReplyBean;
import com.fosung.xuanchuanlan.mian.http.HttpUrlMaster;
import com.fosung.xuanchuanlan.mian.http.entity.CreateMeetingApply;
import com.fosung.xuanchuanlan.mian.http.entity.CreateMeetingReply;
import com.fosung.xuanchuanlan.mian.http.entity.VoidSynchronizeBean;
//import com.yuntongxun.plugin.fullconf.manager.inter.OnConfActionListener;
//import com.yuntongxun.plugin.fullconf.wrap.ConfPanel;
//import com.yuntongxun.plugin.fullconf.wrap.Configuration;
//import com.yuntongxun.plugin.fullconf.wrap.WrapManager;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

public class VoidActivityDetail extends AppCompatActivity {
    private Button createMeting;
    private boolean islogon=false;
    private EditText number;
    private CheckBox ckalloworg,ckallowmeeting,ckcamera,ckvoice,issing;
    private ImageView detail_back;
    TextView meeting;
    String meetingId;
    String meetingname;
    String user_id,user_orgCode,user_orgName,user_orgId,user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_void_detail);
        createMeting = (Button) findViewById(R.id.createMeeting);
        detail_back = (ImageView) findViewById(R.id.detail_back);
        ckalloworg = (CheckBox) findViewById(R.id.ckalloworg);
        ckallowmeeting = (CheckBox) findViewById(R.id.ckallowmeeting);
        ckcamera = (CheckBox) findViewById(R.id.ckcamera);
        ckvoice = (CheckBox) findViewById(R.id.ckvoice);
        issing = (CheckBox) findViewById(R.id.issing);
        meeting = (TextView) findViewById(R.id.meeting);
        number = (EditText) findViewById(R.id.number);
        SharedPreferences usersp = ConfApplication.APP_CONTEXT.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        user_id=usersp.getString("user_id", "");//yong户唯一标识
        user_orgCode=usersp.getString("user_orgCode", "");//yong户唯一标识
        user_orgName=usersp.getString("user_orgName", "");//yong户唯一标识
        user_orgId=usersp.getString("user_orgId", "");//yong户唯一标识
        user_name=usersp.getString("user_name", "");//yong户唯一标识
        createMeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meetingname=number.getText().toString();
                CreateMeetingApply apply = new CreateMeetingApply();
                apply.userId=user_id;
                apply.orgCode=user_orgCode;
                apply.orgId=user_orgId;
                apply.orgName=user_orgName;
                apply.videoSub=""+meetingname;
                apply.subOrgCode=user_orgCode;
                apply.subOrgId=user_orgId;
                apply.clientType="BOX";
                if(ckalloworg.isChecked()){
                    apply.ifSubOrg="AGREE";
                }else {
                    apply.ifSubOrg="NOT";
                }
                if(ckallowmeeting.isChecked()){
                    apply.ifNo="AGREE";
                }else {
                    apply.ifNo="NOT";
                }
                if(ckcamera.isChecked()){
                    apply.ifOis="AGREE";
                }else {
                    apply.ifOis="NOT";
                }
                if(ckvoice.isChecked()){
                    apply.ifMute="AGREE";
                }else {
                    apply.ifMute="NOT";
                }
                if(issing.isChecked()){
                    apply.ifMute="AGREE";
                }else {
                    apply.ifMute="NOT";
                }
                String json = GsonUtil.beanToString(apply);
                        ZHttp.postJson(HttpUrlMaster.VOID_CREATE, json, new ZResponse<CreateMeetingReply>(CreateMeetingReply.class) {
                            @Override
                            public void onSuccess(Response response, CreateMeetingReply resObj) {
                                if (resObj != null&&resObj.data!=null) {
                                    meetingId=resObj.data.videoNo;
                                    startPhoneMeeting(resObj,resObj.data.videoNo,user_name);                                }
                            }

                            @Override
                            public void onError(int code, String error) {
                                super.onError(code, error);
                            }
                        });
            }
        });
        detail_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VoidActivityDetail.this.finish();
            }
        });
    }


    private void startPhoneMeeting(CreateMeetingReply resObj,String meetingno,String name) {
//        ArrayList<Configuration> lists = new ArrayList<>();
//        ifVideo	是	string	是否录像 AGREE 是 NOT 否 默认否
//        ifScreenshot	是	string	是否截图 AGREE 是 NOT 否 默认否
        Boolean isopenRecord=false;
        Boolean isFaceCount=false;
        Boolean isopenFaceCpture=false;
        int time=resObj.data.screenshotInterval*1000*60;
        if("AGREE".equals(resObj.data.ifVideo)){
            isopenRecord=true;
        }else if("NOT".equals(resObj.data.ifVideo)){
            isopenRecord=false;
        }
        if("AGREE".equals(resObj.data.ifScreenshot)){
            isopenFaceCpture=true;
        }else if("NOT".equals(resObj.data.ifScreenshot)){
            isopenFaceCpture=false;
        }
        if("AGREE".equals(resObj.data.ifPeopleNum)){
            isFaceCount=true;
        }else if("NOT".equals(resObj.data.ifPeopleNum)){
            isFaceCount=false;
        }
        if("AGREE".equals(resObj.data.ifSign)){
            /** 签到 */
//            lists.add(new Configuration(Configuration.PANENAME.CONF_SGIN));
        }

//        /** 静音 */
//        lists.add(new Configuration(Configuration.PANENAME.CONF_MUTE));
//        /** 摄像头 */
//        lists.add(new Configuration(Configuration.PANENAME.CONF_VIDEO));
//        /** 扬声器 */
//        lists.add(new Configuration(Configuration.PANENAME.CONF_LOUDSPEAK));
//        /** 参会人 */
////        lists.add(new Configuration(Configuration.PANENAME.CONF_MEMBER));
////        /** 会议室锁定 */
////        lists.add(new Configuration(Configuration.PANENAME.CONF_LOCK));
////        /** 会场控制 */
//        lists.add(new Configuration(Configuration.PANENAME.CONF_CONTROL));
//        /** 结束 */
//        lists.add(new Configuration(Configuration.PANENAME.CONF_END));
//        /** 主讲 */
//        //   lists.add(new Configuration(Configuration.PANENAME.CONF_HOST));
//               /** 布局 */
////        lists.add(new Configuration(Configuration.PANENAME.CONF_LAYOUT));
//
//
//        ConfPanel confPanel = new ConfPanel.ConfPanelBuilder(this)
//                /**打开会议录制 只有主持人生效↓ */
//                .openRecord(isopenRecord)
//                /**打开 AI识别会议人数 未设置时间 默认30秒认别一次↓ */
//                .openFaceCount(isFaceCount)
//                /**打开 会议截图 未设置时间 默认30秒截图一张↓ */
//                .openFaceCpture(isopenFaceCpture)
//                /**设置 会议截图时间间隔 单位毫秒↓ */
//                .setCptureDelayTime(time)
//                /**AI识别会议人数时间间隔  单位毫秒↓ */
//                .setFaceCountDelayTime(10000)
//                /**会议功能配置 */
//                .setConfiguration(lists)
//
//          .setConfActionListener(new OnConfActionListener() {
//            @Override
//            public void onHnaldeConfAction(String action, String conferenceId, String data) {
//                //0是解散，1是退出，2点击开始签到会进行人脸识别，  3人脸识别的数量 4是入会成功
//            Log.e("dongdata",""+data);
//                if("0".equals(action)){
//                    outmeeting("2",conferenceId);
//                }else if ("1".equals(action)){
//                    outmeeting("1",conferenceId);
//                }else if ("2".equals(action)){
//                    SingBean bean= new SingBean();
//                    bean.clientType="AFR";
//                    bean.clientUserId=user_id;
//                    bean.meetingNo=conferenceId;
//                    bean.rtnInfo=data;
//                    String jsong=GsonUtil.beanToString(bean);
//                    signInfo(GsonUtil.beanToString(bean));
//                }else if ("3".equals(action)){
//                    attendance(data);
//                }else if ("4".equals(action)){
//                    MethodInfo();
//                }
//            }
//        }).build();
//
//        WrapManager.getInstance().joinPhoneMeeting(confPanel,meetingno, name);

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
