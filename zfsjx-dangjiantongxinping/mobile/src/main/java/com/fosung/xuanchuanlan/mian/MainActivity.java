package com.fosung.xuanchuanlan.mian;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.blankj.utilcode.util.AppUtils;
import com.bumptech.glide.Glide;
import com.fosung.frameutils.http.ZHttp;
import com.fosung.frameutils.http.response.ZResponse;
import com.fosung.frameutils.http.response.ZStringResponse;
import com.fosung.frameutils.permission.PermissionHelper;
import com.fosung.frameutils.permission.PermissionsResultAction;
import com.fosung.frameutils.util.AppUtil;
import com.fosung.frameutils.util.DisplayUtil;
import com.fosung.frameutils.util.FileUtil;
import com.fosung.frameutils.util.GsonUtil;
import com.fosung.frameutils.util.ToastUtil;
import com.fosung.xuanchuanlan.R;
import com.fosung.xuanchuanlan.common.app.ConfApplication;
import com.fosung.xuanchuanlan.common.base.ActivityParam;
import com.fosung.xuanchuanlan.common.base.BaseVideoPlayActivity;
import com.fosung.xuanchuanlan.common.consts.PathConst;
import com.fosung.xuanchuanlan.common.util.UpdateMgr;
import com.fosung.xuanchuanlan.mian.activity.VideoActivity;
import com.fosung.xuanchuanlan.mian.activity.VideoConferenceActivity;
import com.fosung.xuanchuanlan.mian.activity.WebActivity;
import com.fosung.xuanchuanlan.mian.adapter.AppMoreRecyclerViewAdapter;
import com.fosung.xuanchuanlan.mian.adapter.AppRecyclerViewAdapter;
import com.fosung.xuanchuanlan.mian.adapter.TypeRecyclerViewAdapter;
import com.fosung.xuanchuanlan.mian.http.HttpUrlMaster;
import com.fosung.xuanchuanlan.mian.http.entity.AppsListReply;
import com.fosung.xuanchuanlan.mian.http.entity.LogoBean;
import com.fosung.xuanchuanlan.mian.http.entity.UpdateBean;
import com.fosung.xuanchuanlan.mian.http.entity.UpdateReple;
import com.fosung.xuanchuanlan.mian.http.entity.WeatherInfo;
import com.fosung.xuanchuanlan.setting.activity.LogoActivity;
import com.fosung.xuanchuanlan.setting.activity.UserCenterActivity;
import com.fosung.xuanchuanlan.setting.activity.WifiListActivity;
import com.tencent.smtt.sdk.QbSdk;
import com.zcolin.gui.ZConfirm;
import com.zcolin.gui.ZDialog;
import com.zcolin.libamaplocation.LocationUtil;
import com.zplayer.library.ZPlayer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

@ActivityParam(isShowToolBar = false)
public class MainActivity extends BaseVideoPlayActivity implements ZPlayer.OnNetChangeListener {
    private AppRecyclerViewAdapter appAdapter;
    private AppMoreRecyclerViewAdapter moreAdapter;
    private TypeRecyclerViewAdapter typeAdapter;
    private LinearLayoutManager mLayoutManager;
    private LinearLayoutManager typeLayoutManager;
    private RecyclerView mRecyclerView;
    private RecyclerView moreAppRecyclerView;
    private RecyclerView typeRecyclerView;
    private ZPlayer player;
    private TextView weather, date;
    private ImageView logo;
    private String city;
    List<AppsListReply.DataBean.MenuListBean.AppListBean> appList;
    List<AppsListReply.DataBean.MenuListBean.AppListBean> moreappList;
    List<AppsListReply.DataBean.MenuListBean> list;
    Drawable horizontalDivider;
    Drawable verticalDivider;
    // 客户端版本号，品牌，型号，客户端唯一标识、操作系统名称。版本
//    private String serial_no = "";
    private String user_id = "";
    private String machineId = "";
    private String voidurl = "";
    private String machineMac = "";
    private String orgCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected ZPlayer initPlayer() {
        player = (ZPlayer) findViewById(R.id.recommend);
        logo = (ImageView) findViewById(R.id.logo);
        weather = (TextView) findViewById(R.id.weather);
        date = (TextView) findViewById(R.id.date);
        mRecyclerView = (RecyclerView) findViewById(R.id.appRecyclerView);
        appList = new ArrayList<>();
        appAdapter = new AppRecyclerViewAdapter(appList, MainActivity.this);
        GridLayoutManager gridLayoutManager
                = new GridLayoutManager(this, 2);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(DisplayUtil.dip2px(this, 20)));
        horizontalDivider = ContextCompat.getDrawable(MainActivity.this, R.drawable.line_divider);
        verticalDivider = ContextCompat.getDrawable(MainActivity.this, R.drawable.line_divider);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(appAdapter);

        //更多app列表
        moreAppRecyclerView = (RecyclerView) findViewById(R.id.moreAppRecyclerView);
        moreappList = new ArrayList<>();
        moreAdapter = new AppMoreRecyclerViewAdapter(moreappList, MainActivity.this);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        moreAppRecyclerView.addItemDecoration(new HorItemDecoration(DisplayUtil.dip2px(this, 20)));
        moreAppRecyclerView.setLayoutManager(mLayoutManager);
        moreAppRecyclerView.setAdapter(moreAdapter);
        //更多type列表
        typeRecyclerView = (RecyclerView) findViewById(R.id.typeRecyclerView);
        list = new ArrayList<>();
        typeAdapter = new TypeRecyclerViewAdapter(list);
        typeLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        typeRecyclerView.setLayoutManager(typeLayoutManager);
        typeRecyclerView.setAdapter(typeAdapter);

        ImageView userCenter = (ImageView) findViewById(R.id.personal);
        userCenter.setOnClickListener(onclick);

        ImageView wifi = (ImageView) findViewById(R.id.wifi);
        wifi.setOnClickListener(onclick);
//        if(TextUtils.isEmpty(serial_no)){
//            requestReadPhoneState();
//        }
        SharedPreferences sp = ConfApplication.APP_CONTEXT.getSharedPreferences("SystemInfo", Context.MODE_PRIVATE);
        //  serial_no=sp.getString("serial_no", "");//客户端唯一标识
        machineMac = sp.getString("machineMac", "");//yong户唯一标识
        machineId = sp.getString("machineId", "");//盒子ID
        initData();
        getlogo();
        typeAdapter.setOnClicklister(new TypeRecyclerViewAdapter.OnClicklister() {
            @Override
            public void onClick(int position) {
                if (list.get(position).appList != null && list.get(position).appList.size() > 0) {
                    if (list.get(position).appList.size() > 4) {
                        appList = list.get(position).appList.subList(0, 4);
                        moreappList = list.get(position).appList.subList(4, list.get(position).appList.size());
                        appAdapter.setdata(appList);
                        appAdapter.notifyDataSetChanged();
                        moreAdapter.setdata(moreappList);
                        moreAdapter.notifyDataSetChanged();
                    } else {
                        appList = list.get(position).appList;
                        appAdapter.setdata(appList);
                        appAdapter.notifyDataSetChanged();
                        moreappList = new ArrayList<>();
                        moreAdapter.setdata(moreappList);
                        moreAdapter.notifyDataSetChanged();
                    }

                }
            }
        });
        player.setNetChangeListener(true)//设置监听手机网络的变化,这个参数是内部是否处理网络监听，和setOnNetChangeListener没有关系
                .setShowTopControl(false)
                .setShowCenterControl(true)
                .setSupportGesture(false)
                .setOnFullScreenListener(new ZPlayer.OnFullScreenListener() {
                    @Override
                    public void onFullScreen(boolean b) {
                        //全屏的时候使用了attrs.flags |= Window.FEATURE_NO_TITLE;此Flag会导致锁屏问题，解决此问题需要取消设置此标志位。
//                        getWindow().clearFlags(Window.FEATURE_NO_TITLE);
                        Intent intent = new Intent(MainActivity.this, VideoActivity.class);
                        startActivity(intent);
                    }
                })
                .onComplete(new Runnable() {
                    @Override
                    public void run() {

                    }
                })
                .setScaleType(ZPlayer.SCALETYPE_FITPARENT)
                .setOnNetChangeListener(this);

        appAdapter.setOncklick(new AppRecyclerViewAdapter.OnclickListener() {
            @Override
            public void Oncklick(int postion) {
                if (!TextUtils.isEmpty(user_id)) {
                    if (appList != null) {
                        Intent intent = new Intent();
                        if ("VIDEO_MEETING_BOX".equals(appList.get(postion).code)) {
                            intent.setClass(MainActivity.this, VideoConferenceActivity.class);
                            startActivity(intent);
                        } else if ("face_box".equals(appList.get(postion).code)) {
//                            intent.setClass(MainActivity.this, FaceActivity.class);
//                            startActivity(intent);
                        } else if ("org_life".equals(appList.get(postion).code)) {
                            intent.setClass(MainActivity.this, WebActivity.class);
                            checkX5(intent);
                        } else if ("BOX_BMFWZN".equals(appList.get(postion).code)) {
                            intent.setClass(MainActivity.this, WebActivity.class);
                            //    intent.putExtra("Url", appList.get(postion).url);
                            intent.putExtra("Url", "serve/index");
                            intent.putExtra("title", appList.get(postion).name);
                            checkX5(intent);
                        } else if ("BOX_DSBK".equals(appList.get(postion).code)) {
                            intent.setClass(MainActivity.this, WebActivity.class);
//                            intent.putExtra("Url", appList.get(postion).url);
                            intent.putExtra("Url", "encyclopedia/index");
                            intent.putExtra("title", appList.get(postion).name);
                            checkX5(intent);
                        } else if ("BOX_FWCK".equals(appList.get(postion).code)) {
                            intent.setClass(MainActivity.this, WebActivity.class);
                            //  intent.putExtra("Url", appList.get(postion).url);
                            intent.putExtra("Url", "fwck/index");
                            intent.putExtra("title", appList.get(postion).name);
                            checkX5(intent);
                        } else if ("BOX_HSGJX".equals(appList.get(postion).code)) {
                            intent.setClass(MainActivity.this, WebActivity.class);
                            // intent.putExtra("Url", appList.get(postion).url);
                            intent.putExtra("Url", "cabinet/index");
                            intent.putExtra("title", appList.get(postion).name);
                            checkX5(intent);
                        } else if ("BOX_NOTICE".equals(appList.get(postion).code)) {
                            intent.setClass(MainActivity.this, WebActivity.class);
                            intent.putExtra("Url", "inform/index");
                            //  intent.putExtra("Url", appList.get(postion).url);
                            intent.putExtra("title", appList.get(postion).name);
                            checkX5(intent);
                        } else if ("BOX_NEWS_DYNAMIC".equals(appList.get(postion).code)) {
                            intent.setClass(MainActivity.this, WebActivity.class);
                            intent.putExtra("Url", "news/index");
                            //  intent.putExtra("Url", appList.get(postion).url);
                            intent.putExtra("title", appList.get(postion).name);
                            checkX5(intent);
                        } else if ("BOX_ORGANIZATION".equals(appList.get(postion).code)) {
                            intent.setClass(MainActivity.this, WebActivity.class);
                            intent.putExtra("Url", "organization/index");
                            // intent.putExtra("Url", appList.get(postion).url);
                            intent.putExtra("title", appList.get(postion).name);
                            checkX5(intent);
                        } else {
                            ToastUtil.toastLong("正在开发中，敬请期待！");
                        }

                    }
                } else {
                    Intent intent = new Intent(MainActivity.this, LogoActivity.class);
                    startActivity(intent);
                }
            }
        });
        moreAdapter.setLinstener(new AppMoreRecyclerViewAdapter.MorOnclickLinstener() {
            @Override
            public void Onclick(int postion) {

                if (!TextUtils.isEmpty(user_id)) {
                    if (moreappList != null) {
                        Intent intent = new Intent();
                        if ("VIDEO_MEETING_BOX".equals(moreappList.get(postion).code)) {
                            intent.setClass(MainActivity.this, VideoConferenceActivity.class);
                            startActivity(intent);
                        } else if ("face_box".equals(moreappList.get(postion).code)) {
//                            intent.setClass(MainActivity.this, FaceActivity.class);
//                            startActivity(intent);
                        } else if ("org_life".equals(moreappList.get(postion).code)) {
                            intent.setClass(MainActivity.this, WebActivity.class);
                            checkX5(intent);
                        } else if ("BOX_BMFWZN".equals(moreappList.get(postion).code)) {
                            intent.setClass(MainActivity.this, WebActivity.class);
                            // intent.putExtra("Url", moreappList.get(postion).url);
                            intent.putExtra("Url", "serve/index");
                            intent.putExtra("title", moreappList.get(postion).name);
                            checkX5(intent);
                        } else if ("BOX_DSBK".equals(moreappList.get(postion).code)) {
                            intent.setClass(MainActivity.this, WebActivity.class);
                            intent.putExtra("Url", "encyclopedia/index");
                            // intent.putExtra("Url", moreappList.get(postion).url);
                            intent.putExtra("title", moreappList.get(postion).name);
                            checkX5(intent);
                        } else if ("BOX_FWCK".equals(moreappList.get(postion).code)) {
                            intent.setClass(MainActivity.this, WebActivity.class);
                            intent.putExtra("Url", "fwck/index");
                            //  intent.putExtra("Url", moreappList.get(postion).url);
                            intent.putExtra("title", moreappList.get(postion).name);
                            checkX5(intent);
                        } else if ("BOX_HSGJX".equals(moreappList.get(postion).code)) {
                            intent.setClass(MainActivity.this, WebActivity.class);
                            intent.putExtra("Url", "cabinet/index");
                            //  intent.putExtra("Url", moreappList.get(postion).url);
                            intent.putExtra("title", moreappList.get(postion).name);
                            checkX5(intent);
                        } else if ("BOX_NOTICE".equals(moreappList.get(postion).code)) {
                            intent.setClass(MainActivity.this, WebActivity.class);
                            intent.putExtra("Url", "inform/index");
                            // intent.putExtra("Url", moreappList.get(postion).url);
                            intent.putExtra("title", moreappList.get(postion).name);
                            startActivity(intent);
                        } else if ("BOX_NEWS_DYNAMIC".equals(moreappList.get(postion).code)) {
                            intent.setClass(MainActivity.this, WebActivity.class);
                            intent.putExtra("Url", "news/index");
                            //  intent.putExtra("Url", moreappList.get(postion).url);
                            intent.putExtra("title", moreappList.get(postion).name);
                            checkX5(intent);
                        } else if ("BOX_ORGANIZATION".equals(moreappList.get(postion).code)) {
                            intent.setClass(MainActivity.this, WebActivity.class);
                            intent.putExtra("Url", "organization/index");
                            //   intent.putExtra("Url", moreappList.get(postion).url);
                            intent.putExtra("title", moreappList.get(postion).name);
                            checkX5(intent);
                        } else {
                            ToastUtil.toastLong("正在开发中，敬请期待！");
                        }
                    }
                } else {
                    Intent intent = new Intent(MainActivity.this, LogoActivity.class);
                    startActivity(intent);
                }
            }
        });
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dates = sDateFormat.format(new Date());
        date.setText(dates);
        return player;
    }

    private View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.personal) {
                Intent intent = new Intent(MainActivity.this, UserCenterActivity.class);
                startActivity(intent);
            } else if (v.getId() == R.id.wifi) {
                Intent intent = new Intent(MainActivity.this, WifiListActivity.class);
                startActivity(intent);
            }


        }
    };

    private void initData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("machineId", machineId);
        map.put("machineCode", "12345678");
        map.put("machineMac", machineMac);
        map.put("orgCode", orgCode);
        ZHttp.post(HttpUrlMaster.GRANTINFO, map, new ZResponse<AppsListReply>(AppsListReply.class) {
            @Override
            public void onSuccess(Response response, AppsListReply resObj) {
                if (resObj != null && resObj.data != null && resObj.data.menuList != null && resObj.data.menuList.size() > 0) {
                    list = resObj.data.menuList;
                    typeAdapter.setdata(list);
                    typeAdapter.notifyDataSetChanged();
                    if (resObj.data.menuList.get(0).appList != null && resObj.data.menuList.get(0).appList.size() > 0) {
                        if (resObj.data.menuList.get(0).appList.size() > 4) {
                            appList = resObj.data.menuList.get(0).appList.subList(0, 4);
                            moreappList = resObj.data.menuList.get(0).appList.subList(4, resObj.data.menuList.get(0).appList.size());
                            appAdapter.setdata(appList);
                            appAdapter.notifyDataSetChanged();
                            moreAdapter.setdata(moreappList);
                            moreAdapter.notifyDataSetChanged();
                        } else {
                            appList = resObj.data.menuList.get(0).appList;
                            appAdapter.setdata(appList);
                            appAdapter.notifyDataSetChanged();
                        }
                    }
                }
                if (resObj != null && resObj.data != null && resObj.data.videoList != null && resObj.data.videoList.size() > 0) {
                    voidurl = resObj.data.videoList.get(0).playUrl;
                    player.play(voidurl);
                }

                mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        appAdapter.reloveLayout(mRecyclerView.getMeasuredHeight(), mRecyclerView.getMeasuredWidth());

                    }
                });
//                AppsListReply.DataBean.MenuListBean.AppListBean bean = appList.get(0);
//                appList.clear();
//                appList.add(bean);

//                mRecyclerView.addItemDecoration(new GridDividerItemDecoration(horizontalDivider, verticalDivider, 2));

            }


            @Override
            public void onError(int code, String error) {
                super.onError(code, error);
            }
        });

    }

    private void getlogo() {
        // 加载logo
        SharedPreferences usersp = ConfApplication.APP_CONTEXT.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        user_id = usersp.getString("user_id", "");//yong户唯一标识
        orgCode = usersp.getString("user_orgCode", "");//yong户唯一标识
        Map<String, String> logomap = new HashMap<String, String>();
        logomap.put("machineId", machineId);
        logomap.put("machineCode", "12345678");
        logomap.put("machineMac", machineMac);
        logomap.put("orgCode", orgCode);
        ZHttp.post(HttpUrlMaster.GRANTLOGO, logomap, new ZResponse<LogoBean>(LogoBean.class) {
            @Override
            public void onSuccess(Response response, LogoBean resObj) {
                if (resObj != null && resObj.data != null) {
                    if (!TextUtils.isEmpty(resObj.data.get(0).imagUrl)) {
                        Glide.with(MainActivity.this)
                                .load(resObj.data.get(0).imagUrl)
                                .placeholder(R.drawable.logo) //设置资源加载过程中的占位符
                                .error(R.drawable.logo)
                                .into(logo);
                    }
                }
            }

            @Override
            public void onError(int code, String error) {
                super.onError(code, error);
                ToastUtil.toastLong("失败" + error);
            }
        });
    }

    @Override
    public void onWifi() {

    }

    @Override
    public void onMobile() {

    }

    @Override
    public void onDisConnect() {

    }

    @Override
    public void onNoAvailable() {

    }

    //RecyclerView横向间隔
    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            //不是第一个的格子都设一个左边和底部的间距
//            outRect.left = space / 2;
//            outRect.right = space / 2;
//            if (parent.getChildLayoutPosition(view) < 2) {
//                outRect.bottom = space;
//            }
//            //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
////            if (parent.getChildLayoutPosition(view) % 2 == 0) {
////                outRect.left = 0;
////            }
//            if (parent.getChildLayoutPosition(view) % 2 == 0) {
//
//                outRect.set(space, 0, space, 0);
//
//            } else {
//
//                outRect.set(0, 0, space, 0);
//            }

            outRect.top = space / 2;
            outRect.bottom = space / 2;

            if (parent.getChildAdapterPosition(view) == 0 || parent.getChildAdapterPosition(view) == 1) {
                outRect.top = 0;
            }

//            if(parent.getChildCount()%2==0){
//                if(parent.getChildAdapterPosition(view) == parent.getChildCount()-1 ||parent.getChildAdapterPosition(view) == parent.getChildCount()-2){
//                    outRect.bottom
//                }
//            }

            if (parent.getChildLayoutPosition(view) % 2 == 0) {
                outRect.left = 0;
                outRect.right = space / 2;

            } else {
                outRect.left = space / 2;
                outRect.right = 0;


            }
//            outRect.left = space / 2;
//            outRect.right = space / 2;
//            if (parent.getChildLayoutPosition(view) < 2) {
//                outRect.bottom = space;
//            }

        }

    }


    //RecyclerView横向间隔
    public class HorItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public HorItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            //不是第一个的格子都设一个左边和底部的间距
            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.right = space + DisplayUtil.dip2px(MainActivity.this, 20);
            } else if (parent.getChildLayoutPosition(view) == parent.getChildCount()) {
                outRect.right = 0;
            } else {
                outRect.right = space;
            }
            //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
//            if (parent.getChildLayoutPosition(view) % 2 == 0) {
//                outRect.left = 0;
//            }
        }

    }


    //获取
//    private void requestReadPhoneState() {
//
//        PermissionHelper.requestReadPhoneStatePermission(this, new PermissionsResultAction() {
//            @Override
//            public void onGranted() {
//                TelephonyManager mTm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
//                @SuppressLint("MissingPermission") String serial_no = mTm.getDeviceId();//唯一标识 imei
//                SharedPreferences sp = ConfApplication.APP_CONTEXT.getSharedPreferences("SystemInfo", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sp.edit();
//                editor.putString("serial_no", serial_no);
//                editor.commit();
//            //    ToastUtil.toastLong("serial_no"+serial_no);
//            }
//
//            @Override
//            public void onDenied(String permission) {
//                ToastUtil.toastShort("请授予本程序此权限");
//            }
//        });
//    }
    @Override
    public void onResume() {
        super.onResume();
//        player.play(voidurl, player.getonReCurrentPosition());
        player.play(voidurl, player.getCurrentPosition());
        SharedPreferences usersp = ConfApplication.APP_CONTEXT.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        user_id = usersp.getString("user_id", "");//yong户唯一标识
        orgCode = usersp.getString("user_orgCode", "");//yong户唯一标识
    }


    @Override
    protected void onDestroy() {
        Log.e("zhouqi", "onPause");
//        if (player != null) {
//            player.onDestroy();
//        }
        super.onDestroy();
    }

    private void requestLocationInfo() {

        PermissionHelper.requestLocationPermission(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
                final LocationUtil location = new LocationUtil(mActivity);
                location.startLocation(new LocationUtil.OnGetLocation() {
                    @Override
                    public void getLocation(AMapLocation location) {
                        if (location != null && location.getCity() != null) {
                            requestWeatherInfo(location.getCity());
                            city = location.getCity();
                        }
                    }

                    @Override
                    public void locationFail() {
                        ToastUtil.toastLong("locationFail");
                    }
                });
            }

            @Override
            public void onDenied(String permission) {
                ToastUtil.toastShort("请授予本程序[定位]权限");
            }
        });
    }

    private void requestWeatherInfo(final String cityName) {
        String url = "http://restapi.amap.com/v3/weather/weatherInfo?key=ac472e992680b96b54a41edcfbd9325b&extensions=all&city=" + cityName;
        ZHttp.get(url, new ZStringResponse() {
            @Override
            public void onError(int code, Call call, Exception e) {
            }

            @Override
            public void onSuccess(Response response, String resObj) {
                try {
                    WeatherInfo info = GsonUtil.stringToBean(resObj, WeatherInfo.class);
                    if (info != null && "10000".equals(info.infocode) && info.forecasts != null && info.forecasts.size() > 0) {
                        if (cityName.equals(info.forecasts.get(0).city)) {
                            weather.setText(info.forecasts.get(0).casts.get(0).dayweather + info.forecasts.get(0).casts.get(0).daytemp);
                        }
                    }
                } catch (Exception e) {

                }
            }
        });

    }

    private void checkX5(Intent intent) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            if (!QbSdk.isTbsCoreInited()) {
                ToastUtil.toastLong("内核未初始化完毕，请稍后再试");
            } else {
                if (!QbSdk.canLoadX5(this)) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setTitle("内核提示");
                    dialog.setPositiveButton("重启", new AlertDialog.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            Process.killProcess(Process.myPid());
                        }
                    });
                    dialog.setNegativeButton("稍后重启", null);
                    dialog.setMessage("内核初始化失败,请重启应用重新初始化.");
                    dialog.create().show();
                } else {
                    startActivity(intent);
                }
            }
        }

        startActivity(intent);
    }

    private void update() {
        // 更新
        UpdateReple bean = new UpdateReple();
        bean.updateCode = "DTWS_00";
        bean.channelCode = "Android";
        bean.versionState = "lts";
        bean.currentVersion = AppUtils.getAppVersionName();
//        bean.expression=user_name;
        String Jsonbean = GsonUtil.beanToString(bean);
        //  ToastUtil.toastLong(""+Jsonbean);
        ZHttp.postJson(HttpUrlMaster.UPDATEINFO, Jsonbean, new ZResponse<UpdateBean>(UpdateBean.class) {
            @Override
            public void onSuccess(Response response, final UpdateBean resObj) {
                if (resObj != null && resObj.data != null) {
                    if (resObj.data.update) {
                        if ("1".equals(resObj.data.updateMsg.updateStrategy)) {
                            new ZConfirm(mActivity).setTitle("版本更新  " + resObj.data.updateMsg.versionInfo + "版")
                                    .setMessage("必须完成本次更新才能继续使用本系统\n\n" + resObj.data.updateMsg.updateRemark)
                                    .setCancelBtnText("退出系统")
                                    .setOKBtnText("立即升级")
                                    .setIsCancelAble(false)
                                    .addSubmitListener(new ZDialog.ZDialogSubmitInterface() {
                                        @Override
                                        public boolean submit() {
                                            File file = new File(PathConst.CACHE + "xuanchuanlan.apk");
                                            FileUtil.delete(file);
                                            UpdateMgr.downLoadApp(mActivity, resObj.data.updateMsg.url);
                                            return true;
                                        }
                                    })
                                    .addCancelListener(new ZDialog.ZDialogCancelInterface() {
                                        @Override
                                        public boolean cancel() {
                                            AppUtil.quitSystem();
                                            return true;
                                        }
                                    })
                                    .show();
                        } else {
                            new ZConfirm(mActivity).setTitle("版本更新  " + resObj.data.updateMsg.versionInfo + "版")
                                    .setMessage(resObj.data.updateMsg.updateRemark)
                                    .setCancelBtnText("暂不升级")
                                    .setOKBtnText("立即升级")
                                    .addSubmitListener(new ZDialog.ZDialogSubmitInterface() {
                                        @Override
                                        public boolean submit() {
                                            File file = new File(PathConst.CACHE + "xuanchuanlan.apk");
                                            FileUtil.delete(file);
                                            UpdateMgr.downLoadApp(mActivity, resObj.data.updateMsg.url);
                                            return true;
                                        }
                                    })
                                    .show();
                        }
                    }
                }
            }

            @Override
            public void onError(int code, String error) {
                super.onError(code, error);

            }
        });
    }

}
