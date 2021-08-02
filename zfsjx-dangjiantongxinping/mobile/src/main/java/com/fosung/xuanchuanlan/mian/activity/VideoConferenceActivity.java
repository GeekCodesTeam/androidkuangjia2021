package com.fosung.xuanchuanlan.mian.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fosung.frameutils.http.ZHttp;
import com.fosung.frameutils.http.response.ZResponse;
import com.fosung.frameutils.util.GsonUtil;
import com.fosung.frameutils.util.ToastUtil;
import com.fosung.xuanchuanlan.R;
import com.fosung.xuanchuanlan.common.app.ConfApplication;
import com.fosung.xuanchuanlan.common.base.ActivityParam;
import com.fosung.xuanchuanlan.common.base.BaseActivity;
import com.fosung.xuanchuanlan.mian.adapter.VoidRecyclerViewAdapter;
import com.fosung.xuanchuanlan.mian.http.AppsBaseReplyBean;
import com.fosung.xuanchuanlan.mian.http.HttpUrlMaster;
import com.fosung.xuanchuanlan.mian.http.entity.CheckMeetingReply;
import com.fosung.xuanchuanlan.mian.http.entity.VoidListReply;
import com.fosung.xuanchuanlan.mian.http.entity.VoidSynchronizeBean;
//import com.yuntongxun.plugin.fullconf.manager.inter.OnConfActionListener;
//import com.yuntongxun.plugin.fullconf.wrap.ConfPanel;
//import com.yuntongxun.plugin.fullconf.wrap.Configuration;
//import com.yuntongxun.plugin.fullconf.wrap.WrapManager;
//import com.yuntongxun.plugin.login.presenter.LoginPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

@ActivityParam(isShowToolBar = false)
public class VideoConferenceActivity extends BaseActivity {

    private SwipeRefreshLayout mRefresh;
    private RecyclerView voidrecyclerview;
    private VoidRecyclerViewAdapter adapter;
    private List<VoidListReply.DatalistBean> mList;
    private LinearLayoutManager voidLayoutManager;
    private TextView createMeeting;
    private TextView join;
//    private LoginPresenter mPresenter;
    private EditText meetingnumber;
    private boolean islogon=false;
    private ImageView back;
    private LinearLayout create_layout;
    private String user_id;
    private String user_orgCode;
    private String user_orgName,user_name,user_orgId;
    //private InternalReceiver internalReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_conference);
        mList = new ArrayList<>();
        mRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        voidrecyclerview = (RecyclerView) findViewById(R.id.voidrecyclerview);
        createMeeting = (TextView) findViewById(R.id.createMeeting);
        meetingnumber = (EditText) findViewById(R.id.meetingnumber);
        create_layout = (LinearLayout) findViewById(R.id.create_layout);
        back = (ImageView) findViewById(R.id.back);
        join = (TextView) findViewById(R.id.join);
        adapter = new VoidRecyclerViewAdapter(mList, VideoConferenceActivity.this);
        voidLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        voidrecyclerview.setLayoutManager(voidLayoutManager);
        voidrecyclerview.setAdapter(adapter);
        create_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoConferenceActivity.this, VoidActivityDetail.class);
                startActivity(intent);
            }
        });
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String  viodnumber= meetingnumber.getText().toString();
               if(!TextUtils.isEmpty(viodnumber)){
                   checkMeeting(viodnumber);
               }else {
                   ToastUtil.toastLong("会议号不能为空");
               }
            }
        });
        mRefresh.setColorSchemeResources(R.color.red_bg);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoConferenceActivity.this.finish();
            }
        });
        adapter.setOnclick(new VoidRecyclerViewAdapter.OnClickListener() {
            @Override
            public void onclick(int postion) {
                if(mList!=null){
                    String viodnumber= mList.get(postion).videoNo;
                    if(!TextUtils.isEmpty(viodnumber)){
                        checkMeeting(viodnumber);
                    }else {
                        ToastUtil.toastLong("会议号为空");
                    }
                }
            }
        });
        voidrecyclerview.addItemDecoration(new SpacesItemDecoration(15));
        SharedPreferences usersp = ConfApplication.APP_CONTEXT.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        user_id=usersp.getString("user_id", "");//yong户唯一标识
        user_name=usersp.getString("user_name", "");//yong户唯一标识
        user_orgCode=usersp.getString("user_orgCode", "");//yong户唯一标识
        user_orgName=usersp.getString("user_orgName", "");//yong户唯一标识
        user_orgId=usersp.getString("user_orgId", "");//yong户唯一标识
        if(!TextUtils.isEmpty(user_id)){
//            WrapManager.getInstance().app_AutoLogin();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId",user_id);
        map.put("orgCode", user_orgCode);
        ZHttp.post(HttpUrlMaster.VOID_LIST, map, new ZResponse<VoidListReply>(VoidListReply.class) {
            @Override
            public void onSuccess(Response response, VoidListReply resObj) {
                mRefresh.setRefreshing(false);
                if (resObj != null && resObj.datalist != null && resObj.datalist.size() > 0) {
                    mList = resObj.datalist;
                    adapter.setdata(mList);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(int code, String error) {
                super.onError(code, error);
                mRefresh.setRefreshing(false);
            }
        });

    }
    private void checkMeeting(final String  viodnumber) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", user_id);
        map.put("videoNo", viodnumber);
        map.put("clientType", "BOX");
        ZHttp.post(HttpUrlMaster.VOID_MEETING_CHECK, map, new ZResponse<CheckMeetingReply>(CheckMeetingReply.class) {
            @Override
            public void onSuccess(Response response, CheckMeetingReply resObj) {
     //           ToastUtil.toastLong(resObj.getErrorMessage()+"");
                if (resObj != null&&resObj.data!=null&&"true".equals(resObj.data.ifJoin)) {
                    startPhoneMeeting(resObj,viodnumber,user_orgName);
                }
            }

            @Override
            public void onError(int code, String error) {
                super.onError(code, error);
                ToastUtil.toastLong(error+"");
            }
        });
    }

    private void startPhoneMeeting(CheckMeetingReply resObj,String meetingno, String name) {
//        ArrayList<Configuration> lists = new ArrayList<>();
//        ifVideo	是	string	是否录像 AGREE 是 NOT 否 默认否
//        ifScreenshot	是	string	是否截图 AGREE 是 NOT 否 默认否
        Boolean isopenRecord=false;
        Boolean isFaceCount=false;
        Boolean isopenFaceCpture=false;
        if("AGREE".equals(resObj.data.meetingInfo.ifVideo)){
            isopenRecord=true;
        }else if("NOT".equals(resObj.data.meetingInfo.ifVideo)){
            isopenRecord=false;
        }
        if("AGREE".equals(resObj.data.meetingInfo.ifScreenshot)){
            isopenFaceCpture=true;
        }else if("NOT".equals(resObj.data.meetingInfo.ifScreenshot)){
            isopenFaceCpture=false;
        }
        if("AGREE".equals(resObj.data.meetingInfo.ifPeopleNum)){
            isFaceCount=true;
        }else if("NOT".equals(resObj.data.meetingInfo.ifPeopleNum)){
            isFaceCount=false;
        }
        if("AGREE".equals(resObj.data.meetingInfo.ifSign)){
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
//        lists.add(new Configuration(Configuration.PANENAME.CONF_MEMBER));
////        /** 会议室锁定 */
////        lists.add(new Configuration(Configuration.PANENAME.CONF_LOCK));
////        /** 会场控制 */
////        lists.add(new Configuration(Configuration.PANENAME.CONF_CONTROL));
//        /** 结束 */
//        lists.add(new Configuration(Configuration.PANENAME.CONF_END));
//        /** 主讲 */
//        //   lists.add(new Configuration(Configuration.PANENAME.CONF_HOST));
//        /** 布局 */
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
//                // .setCptureDelayTime(10000)
//                /**AI识别会议人数时间间隔  单位毫秒↓ */
//                .setFaceCountDelayTime(10000)
//                /**会议功能配置 */
//                .setConfiguration(lists)
//                .setConfActionListener(new OnConfActionListener() {
//                    @Override
//                    public void onHnaldeConfAction(String action, String conferenceId, String data) {
//                        if("0".equals(action)){
//                            outmeeting("2",conferenceId);
//                        }
//                        if ("1".equals(action)){
//                            outmeeting("1",conferenceId);
//                        }
//                        if ("2".equals(action)){
//                            SingBean bean= new SingBean();
//                            bean.clientType="AFR";
//                            bean.clientUserId=user_id;
//                            bean.meetingNo=conferenceId;
//                            bean.rtnInfo=data;
//                            String jsong= GsonUtil.beanToString(bean);
//                            signInfo(GsonUtil.beanToString(bean));
//                        }
//                        if ("4".equals(action)){
//                            MethodInfo(conferenceId);
//                        }
//                        if ("3".equals(action)){
//                            attendance(data,conferenceId);
//                        }
//                    }
//
//                })
//                .build();
//
//       WrapManager.getInstance().joinPhoneMeeting(confPanel,meetingno, name);

    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildPosition(view) == 0)
                outRect.top = space;
        }
    }

    //入会方式信息同步
    private void MethodInfo(String meetingId) {
        VoidSynchronizeBean bean= new VoidSynchronizeBean();
        bean.clientType="BOX";
        bean.clientUserId=user_id;
        bean.clientUserName=user_name;
        bean.meetingNo=meetingId;
        bean.orgId=user_orgId;
        bean.orgName=user_orgName;
        bean.orgCode=user_orgCode;
        String json= GsonUtil.beanToString(bean);
        ZHttp.postJson(HttpUrlMaster.MethodInfo, json, new ZResponse<AppsBaseReplyBean>(AppsBaseReplyBean.class) {
            @Override
            public void onSuccess(Response response, AppsBaseReplyBean resObj) {
               // ToastUtil.toastLong("成功MethodInfo");
            }

            @Override
            public void onError(int code, String error) {
                super.onError(code, error);
            //    ToastUtil.toastLong(error+"");
            }
        });
    }
    //参会人数同步,数人头
    private void attendance(String num,String meetingId) {
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
             //   ToastUtil.toastLong("成功attendance");
            }

            @Override
            public void onError(int code, String error) {
                super.onError(code, error);
            //    ToastUtil.toastLong(error+"");
            }
        });
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
              //     ToastUtil.toastLong(error+"");
            }
        });
    }
}
