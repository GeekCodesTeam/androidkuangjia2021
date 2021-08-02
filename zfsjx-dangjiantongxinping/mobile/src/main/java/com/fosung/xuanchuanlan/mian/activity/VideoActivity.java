package com.fosung.xuanchuanlan.mian.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fosung.frameutils.http.response.ZResponse;
import com.fosung.frameutils.util.DisplayUtil;
import com.fosung.xuanchuanlan.R;
import com.fosung.xuanchuanlan.common.app.ConfApplication;
import com.fosung.xuanchuanlan.common.base.ActivityParam;
import com.fosung.xuanchuanlan.common.base.BaseVideoPlayActivity;
import com.fosung.xuanchuanlan.mian.adapter.VideoPriRecyclerViewAdapter;
import com.fosung.xuanchuanlan.mian.adapter.VideoPubRecyclerViewAdapter;
import com.fosung.xuanchuanlan.mian.http.HttpUrlMaster;
import com.fosung.xuanchuanlan.mian.http.entity.VideoDetailBean;
import com.fosung.xuanchuanlan.mian.http.entity.VideoPriBean;
import com.fosung.xuanchuanlan.mian.http.entity.VideoSelectEvent;
import com.fosung.xuanchuanlan.xuanchuanlan.main.http.XCLHttp;
import com.zplayer.library.ZPlayer;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

@ActivityParam(isShowToolBar = false)
public class VideoActivity extends BaseVideoPlayActivity implements ZPlayer.OnNetChangeListener {

    public static final String isNews = "isNews";
    public static final String isLibrary = "isLibrary";

    private ZPlayer player;
    private RecyclerView recyclerView;
    private String machineId = "";
    private String machineMac = "";
    private LinearLayoutManager typeLayoutManager;
    VideoPubRecyclerViewAdapter videoAdapter;
    VideoPriRecyclerViewAdapter videopriAdapter;
    List<VideoDetailBean.DataBean.PUBLICBean> publist;
    List<VideoDetailBean.DataBean.PRIVATEBean> prilist;
    RadioButton news, dinfindnews;
    private LinearLayout mLlParent;
    private RelativeLayout mRlTitle, mRlLeft;
    private TextView mTvNote;
//    private ImageView mIvRadio;

    private int mPaddingAll, mPaddingBottom;
    private boolean videoNew, videoEdit;
    private Integer selectedIndex = 0;
    private List selectedList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_video;
    }

    @Override
    protected ZPlayer initPlayer() {
        isPlayed = false;
        mPaddingAll = DisplayUtil.dip2px(this, 30);
        mPaddingBottom = DisplayUtil.dip2px(this, 30);
        mLlParent = (LinearLayout) findViewById(R.id.ll_parent);
        mRlTitle = (RelativeLayout) findViewById(R.id.rl_title);
        mRlLeft = (RelativeLayout) findViewById(R.id.rl_left);
        mTvNote = (TextView) findViewById(R.id.tv_note);
//        mIvRadio = (ImageView) findViewById(R.id.iv_radio);

        player = (ZPlayer) findViewById(R.id.recommend);
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        news = (RadioButton) findViewById(R.id.news);
        dinfindnews = (RadioButton) findViewById(R.id.dinfindnews);
        typeLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//      Drawable horizontalDivider = ContextCompat.getDrawable(this, R.drawable.vido_divider);
//      recyclerView.addItemDecoration(new com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration(horizontalDivider));
        recyclerView.setLayoutManager(typeLayoutManager);
        publist = new ArrayList<>();
        prilist = new ArrayList<>();
        videoAdapter = new VideoPubRecyclerViewAdapter(publist);
        videopriAdapter = new VideoPriRecyclerViewAdapter(prilist);
        recyclerView.setAdapter(videoAdapter);
//        recyclerView.setAdapter(videopriAdapter);
        SharedPreferences sp = ConfApplication.APP_CONTEXT.getSharedPreferences("SystemInfo", Context.MODE_PRIVATE);
        machineMac = sp.getString("machineMac", "");//yong户唯一标识
        machineId = sp.getString("machineId", "");//盒子ID
        player.setNetChangeListener(false)//设置监听手机网络的变化,这个参数是内部是否处理网络监听，和setOnNetChangeListener没有关系
                .setShowTopControl(false)
                .setShowCenterControl(true)
                .setShowNavIcon(false)
                .setSupportGesture(false)
//                .setBackListener(new ZPlayer.OnBackListener() {
//                    @Override
//                    public void onBackClick() {
//                        if (player.isPriScreen())
//                            player.setIsPriSceen(false);
//                    }
//                })
                .setOnFullScreenListener(new ZPlayer.OnFullScreenListener() {
                    @Override
                    public void onFullScreen(boolean b) {
                        //全屏的时候使用了attrs.flags |= Window.FEATURE_NO_TITLE;此Flag会导致锁屏问题，解决此问题需要取消设置此标志位。
                        //getWindow().clearFlags(Window.FEATURE_NO_TITLE);
                        updateFull(b);
//                        player.setFullScreen(b);
//                        player.updateFullScreenButton();
                    }
                })
                .onComplete(new Runnable() {
                    @Override
                    public void run() {

                    }
                })
                .setScaleType(ZPlayer.SCALETYPE_FITPARENT)
                .setOnNetChangeListener(this);
        findViewById(R.id.detail_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setAdapter(videoAdapter);
                videoAdapter.setdata(publist);
                videoAdapter.notifyDataSetChanged();
            }
        });
        dinfindnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setAdapter(videopriAdapter);
                videopriAdapter.setdata(prilist);
                videopriAdapter.notifyDataSetChanged();
            }
        });
        videoAdapter.setOnClicklister(new VideoPubRecyclerViewAdapter.OnClicklister() {
            @Override
            public void onClick(int position) {
                player.play(publist.get(position).storePath);
                videoAdapter.resetSel(position);
                videopriAdapter.resetSel(-1);
                mTvNote.setText(publist.get(position).name);
                selectedIndex = position;
                selectedList = publist;
            }
        });
        videopriAdapter.setOnClicklister(new VideoPriRecyclerViewAdapter.OnClicklister() {
            @Override
            public void onClick(int position) {
                player.play(prilist.get(position).storePath);
                videoAdapter.resetSel(-1);
                videopriAdapter.resetSel(position);
                mTvNote.setText(prilist.get(position).name);
                selectedIndex = position;
                selectedList = prilist;
            }
        });

        videoNew = getIntent().getBooleanExtra(isNews, false);
        videoEdit = getIntent().getBooleanExtra(isLibrary, false);

        if (videoNew && !videoEdit) {
            news.setBackgroundResource(R.drawable.tab_video);
            dinfindnews.setVisibility(View.GONE);
            initData();
        } else if (!videoNew && videoEdit) {
            dinfindnews.setBackgroundResource(R.drawable.tab_video);
            news.setVisibility(View.GONE);
            initPriDate();
        } else {
            initData();
            initPriDate();
        }
        return player;
    }

    private void updateFull(boolean b) {
        if (b) {
            mLlParent.setPadding(0, 0, 0, 0);
            mRlTitle.setVisibility(View.GONE);
            mRlLeft.setVisibility(View.GONE);
            mTvNote.setVisibility(View.GONE);
            player.setShowNavIcon(true);
//            mIvRadio.setVisibility(View.GONE);
        } else {
            mLlParent.setPadding(mPaddingAll, mPaddingAll, mPaddingAll, mPaddingBottom);
            mRlTitle.setVisibility(View.GONE);
            mRlLeft.setVisibility(View.VISIBLE);
            mTvNote.setVisibility(View.VISIBLE);
            player.setShowNavIcon(false);
//            mIvRadio.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        if (player.isFullScreen()) {
            player.setFullScreenOnly(false);
        } else {
            int position = player.getCurrentPosition();//播放进度
            EventBus.getDefault().post(new VideoSelectEvent(selectedList, selectedIndex, position));
            Log.i("EventBus POST", "video position: " + position);
            super.onBackPressed();
        }
    }

    private void initData() {

        Map<String, String> map = new HashMap<String, String>();
        map.put("machineId", machineId);
        map.put("machineCode", "12345678");
        map.put("machineMac", machineMac);
        XCLHttp.post(HttpUrlMaster.FETVIDEO, map, new ZResponse<VideoDetailBean>(VideoDetailBean.class) {
            @Override
            public void onSuccess(Response response, VideoDetailBean resObj) {
                if (resObj.data != null && resObj.data.PUBLIC != null) {
                    publist = resObj.data.PUBLIC;
                    videoAdapter.setdata(publist);
                    videoAdapter.notifyDataSetChanged();
                    if (publist.size() > 0 && videoNew) {
                        selectedList = publist;
                        recyclerView.setAdapter(videoAdapter);
                        videoAdapter.setdata(publist);
                        player.play(publist.get(0).storePath);
                        videoAdapter.resetSel(0);
                        videopriAdapter.resetSel(-1);
                        mTvNote.setText(publist.get(0).name);
                        videoAdapter.notifyDataSetChanged();
                    }
                }
            }


            @Override
            public void onError(int code, String error) {
                super.onError(code, error);
            }
        });

    }

    private void initPriDate() {
        SharedPreferences sp = ConfApplication.APP_CONTEXT.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String orgCode = sp.getString("user_orgCode", "");
        Map<String, String> map = new HashMap<String, String>();
        map.put("machineMac", machineMac);
        map.put("orgCode", orgCode);
        map.put("maType", "billboard");
        map.put("maId", machineId);
        XCLHttp.post(HttpUrlMaster.FETVIDEO_PRI, map, new ZResponse<VideoPriBean>(VideoPriBean.class) {
            @Override
            public void onSuccess(Response response, VideoPriBean resObj) {
                if (resObj != null && resObj.datalist != null) {
                    prilist = resObj.datalist;
                    videopriAdapter.setdata(prilist);
                    videopriAdapter.notifyDataSetChanged();
                }

                if (prilist.size() > 0 && !videoNew) {
                    selectedList = prilist;
                    recyclerView.setAdapter(videopriAdapter);
                    videopriAdapter.setdata(prilist);
                    player.play(prilist.get(0).storePath);
                    videoAdapter.resetSel(-1);
                    videopriAdapter.resetSel(0);
                    mTvNote.setText(prilist.get(0).name);
                    videopriAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(int code, String error) {
                super.onError(code, error);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        if (player != null) {
            player.onDestroy();
        }
        super.onDestroy();
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
}
