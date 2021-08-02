package com.fosung.lighthouse.jiaoyuziyuan.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.fosung.eduapi.bean.EduResourceDetailBean;
import com.fosung.eduapi.presenter.EduResDetailPresenter;
import com.fosung.eduapi.view.EduResDetailView;
import com.fosung.lighthouse.jiaoyuziyuan.R;
import com.fosung.lighthouse.jiaoyuziyuan.adapter.EduResourceDetailPagerAdapter;
import com.fosung.lighthouse.jiaoyuziyuan.fragment.EduDetailInfoFragment;
import com.fosung.lighthouse.jiaoyuziyuan.fragment.EduDetailRecordFragment;
import com.fosung.lighthouse.jiaoyuziyuan.fragment.EduDetailReviewFragment;
import com.fosung.lighthouse.jiaoyuziyuan.widgets.AudioControlView;
import com.fosung.lighthouse.jiaoyuziyuan.widgets.scrolllayout.ScrollLayout;
import com.google.android.material.tabs.TabLayout;
import com.haier.cellarette.baselibrary.toasts3.utils.MSizeUtils;
import com.zcolin.frame.app.ActivityParam;
import com.zcolin.frame.app.BaseActivity;
import com.zcolin.frame.app.BaseFrameFrag;
import com.zcolin.frame.util.ToastUtil;
import com.zcolin.gui.webview.ZWebView;

import java.util.ArrayList;

import xyz.doikki.videocontroller.StandardVideoController;
import xyz.doikki.videocontroller.component.CompleteView;
import xyz.doikki.videocontroller.component.ErrorView;
import xyz.doikki.videocontroller.component.GestureView;
import xyz.doikki.videocontroller.component.PrepareView;
import xyz.doikki.videocontroller.component.VodControlView;
import xyz.doikki.videoplayer.player.VideoView;

@ActivityParam(isImmerse = false)
public class EduResourceDetailAct extends BaseActivity implements View.OnClickListener, EduResDetailView {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<String> listChannel = new ArrayList<>();
    private BaseFrameFrag[] arrTabFrag;

    private ScrollLayout mScrollLayout;
    private VideoView mVideoView;
    private VideoView audioPlayer;
    private ZWebView webView;
    private TextView tv_sh_look;

    private int HeaderHeight;
    private String id;
    private String auditStatus;

    private EduResDetailPresenter eduResDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edu_resources_detail);
        setToolbarTitle("审核详情");
        id = getIntent().getStringExtra("id");
        auditStatus = getIntent().getStringExtra("auditStatus");

        initView();

        eduResDetailPresenter = new EduResDetailPresenter();
        eduResDetailPresenter.onCreate(this);
        eduResDetailPresenter.getEduResDetailRequest(id,"030b9e46-b8ea-47ec-9feb-fb8c3eead801");
    }

    private void initView() {

        mScrollLayout = findViewById(R.id.scroll_down_layout);
        HeaderHeight = mScrollLayout.getScreenWidth() * 400 / 670;

        viewPager = getView(R.id.viewpager);
        tabLayout = getView(R.id.tabs);

        listChannel.add("基本信息");
        listChannel.add("拓展信息");
        listChannel.add("AI审核");
        listChannel.add("报审记录");
        listChannel.add("审核意见");

        arrTabFrag = new BaseFrameFrag[listChannel.size()];

        EduResourceDetailPagerAdapter mainChildPagerAdapter = new EduResourceDetailPagerAdapter(this, listChannel,
                getSupportFragmentManager());
        viewPager.setAdapter(mainChildPagerAdapter);

        setUpTab();

        final ViewTreeObserver vto = getToolBar().getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                getToolBar().getViewTreeObserver().removeOnPreDrawListener(this);
                int scrollHeight = getToolBar().getMeasuredHeight();
//                scrollHeight  = scrollHeight < 263 ? 0 : scrollHeight;

                mScrollLayout.setMinOffset(0); 
                mScrollLayout.setMaxOffset((mScrollLayout.getScreenHeight() - HeaderHeight));
                mScrollLayout.setExitOffset(MSizeUtils.dp2px(EduResourceDetailAct.this, 100));
                mScrollLayout.setIsSupportExit(true);
                mScrollLayout.setAllowHorizontalScroll(true);
                mScrollLayout.setToOpen();
                Log.d("scrollHeight ", "" + scrollHeight);


                initWebView();

                mScrollLayout.setOnScrollChangedListener(new ScrollLayout.OnScrollChangedListener() {
                    @Override
                    public void onScrollProgressChanged(float currentProgress) {
                        tv_sh_look.setY(currentProgress - MSizeUtils.dp2px(EduResourceDetailAct.this, 40));
                    }

                    @Override
                    public void onScrollFinished(ScrollLayout.Status currentStatus) {
                    }

                    @Override
                    public void onChildScroll(int top) {

                    }
                });


                return true;
            }
        });


    }

    private void setUpTab() {

        for (int i = 0; i < listChannel.size(); i++) {
            tabLayout.addTab(tabLayout.newTab());
        }

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initPlayerView(EduResourceDetailBean bean) {
        mVideoView = findViewById(R.id.player);
        mVideoView.setVisibility(View.VISIBLE);

        mVideoView.getLayoutParams().height = HeaderHeight;//动态调整高度为670*400

        StandardVideoController controller = new StandardVideoController(this);
        //根据屏幕方向自动进入/退出全屏
        controller.setEnableOrientation(true);

        PrepareView prepareView = new PrepareView(this);//准备播放界面
        ImageView thumb = prepareView.findViewById(xyz.doikki.dkplayer.R.id.thumb);//封面图
        Glide.with(this).load(bean.getData().getCoverUrl()).into(thumb);
        controller.addControlComponent(prepareView);
        controller.addControlComponent(new CompleteView(this));//自动完成播放界面
        controller.addControlComponent(new ErrorView(this));//错误界面

        VodControlView vodControlView = new VodControlView(this);//点播控制条
        controller.addControlComponent(vodControlView);

        GestureView gestureControlView = new GestureView(this);//滑动控制视图
        controller.addControlComponent(gestureControlView);
        //根据是否为直播决定是否需要滑动调节进度
        controller.setCanChangePosition(true);
        mVideoView.setVideoController(controller);
        mVideoView.setUrl(bean.getData().getUrl());
        //保存播放进度
//            mVideoView.setProgressManager(new ProgressManagerImpl());
        //播放状态监听
        mVideoView.addOnStateChangeListener(mOnStateChangeListener);

//        mVideoView.start();
    }

    private void initAudioPlayerView(EduResourceDetailBean bean) {
        audioPlayer = findViewById(R.id.audioPlayer);
        audioPlayer.setVisibility(View.VISIBLE);
        audioPlayer.setEnableAudioFocus(false);
//        audioPlayer.getLayoutParams().height = HeaderHeight;//动态调整高度为670*400

        StandardVideoController controller = new StandardVideoController(this);
        //根据屏幕方向自动进入/退出全屏
        controller.setEnableOrientation(false);
        controller.setGestureEnabled(false);

        AudioControlView vodControlView = new AudioControlView(this);//点播控制条
        vodControlView.onLockStateChanged(true);
        controller.addControlComponent(vodControlView);

        audioPlayer.setVideoController(controller);

        audioPlayer.setUrl(bean.getData().getUrl());

        //播放状态监听
        audioPlayer.addOnStateChangeListener(mOnStateChangeListener);


//        audioPlayer.start();
    }

    private VideoView.OnStateChangeListener mOnStateChangeListener = new VideoView.SimpleOnStateChangeListener() {
        @Override
        public void onPlayerStateChanged(int playerState) {
            switch (playerState) {
                case VideoView.PLAYER_NORMAL://小屏
                    break;
                case VideoView.PLAYER_FULL_SCREEN://全屏
                    break;
            }
        }

        @Override
        public void onPlayStateChanged(int playState) {
            switch (playState) {
                case VideoView.STATE_IDLE:
                    break;
                case VideoView.STATE_PREPARING:
                    break;
                case VideoView.STATE_PREPARED:
                    break;
                case VideoView.STATE_PLAYING:
                    break;
                case VideoView.STATE_PAUSED:
                    break;
                case VideoView.STATE_BUFFERING:
                    break;
                case VideoView.STATE_BUFFERED:
                    break;
                case VideoView.STATE_PLAYBACK_COMPLETED:
                    break;
                case VideoView.STATE_ERROR:
                    break;
            }
        }
    };

    public void initWebView() {
        webView = getView(R.id.webView);
        webView.setVisibility(View.VISIBLE);
        tv_sh_look = getView(R.id.tv_sh_look);
        tv_sh_look.setVisibility(View.VISIBLE);
        tv_sh_look.setOnClickListener(this);
        tv_sh_look.setY(HeaderHeight - MSizeUtils.dp2px(EduResourceDetailAct.this, 40));

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.setWebViewClient(new ArticleWebViewClient());

    }

    private class ArticleWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //重置webview中img标签的图片大小
            imgReset();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    /**
     * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
     **/
    private void imgReset() {
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++) " +
                "{"
                + "var img = objs[i]; " +
                " img.style.maxWidth = '100%'; img.style.height = 'auto'; " +
                "}" +
                "})()");
    }

    @Override
    public void OnEduResDetailSuccess(EduResourceDetailBean bean) {
        if ("VIDEO".equals(bean.getData().getType())) {
            initPlayerView(bean);
        } else if ("AUDIO".equals(bean.getData().getType())) {
            initAudioPlayerView(bean);
        } else {
            initWebView();
            webView.loadDataWithBaseURL(null, bean.getData().getContent(), "text/html; charset=UTF-8", null, null);
        }
    }

    @Override
    public void OnEduResDetailFail(String msg) {
        ToastUtil.toastShort(msg);
    }

    /**
     * 根据位置获取Frag
     *
     * @param pos frag在viewpager中的位置
     */
    public BaseFrameFrag getFragByPosition(int pos) {
        if (arrTabFrag[pos] == null) {
            arrTabFrag[pos] = getNewFragByPos(pos);
        }
        return arrTabFrag[pos];
    }

    /*
     * 根据传入的位置创建新的Frag
     */
    private BaseFrameFrag getNewFragByPos(int i) {
        BaseFrameFrag frag;
        if (i < 3) {
            frag = EduDetailInfoFragment.newInstance(i);
        } else if (i == 4){
            frag = EduDetailReviewFragment.newInstance(i);
        } else {
            frag = EduDetailRecordFragment.newInstance(i);
        }
        return frag;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mVideoView != null) {
            mVideoView.resume();
        }

        if (audioPlayer != null) {
            audioPlayer.resume();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mVideoView != null) {
            mVideoView.pause();
        }
        if (audioPlayer != null) {
            audioPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {
            mVideoView.release();
        }

        if (audioPlayer != null) {
            audioPlayer.release();
        }
        if (webView != null) {
            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(webView);
            }
            webView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearHistory();
            webView.clearView();
            webView.removeAllViews();
            webView.destroy();
        }

    }

    @Override
    public void onBackPressed() {
        if (mVideoView == null || !mVideoView.onBackPressed()) {
            super.onBackPressed();
        }
        if (audioPlayer == null || !audioPlayer.onBackPressed()) {
            super.onBackPressed();
        }

    }

}