package xyz.doikki.dkplayer.activity.list.tiktok;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import xyz.doikki.dkplayer.R;
import xyz.doikki.dkplayer.activity.BaseActivityDk;
import xyz.doikki.dkplayer.adapter.Tiktok2AdapterDk;
import xyz.doikki.dkplayer.bean.TiktokBeanDk;
import xyz.doikki.dkplayer.util.DataUtilDk;
import xyz.doikki.dkplayer.util.UtilsDk;
import xyz.doikki.dkplayer.util.cache.PreloadManagerDk;
import xyz.doikki.dkplayer.util.cache.ProxyVideoCacheManagerDk;
import xyz.doikki.dkplayer.widget.VerticalViewPagerDk;
import xyz.doikki.dkplayer.widget.controller.TikTokControllerDk;
import xyz.doikki.dkplayer.widget.render.TikTokRenderViewFactoryDk;
import xyz.doikki.videoplayer.player.VideoView;
import xyz.doikki.videoplayer.util.L;

import java.util.ArrayList;
import java.util.List;


/**
 * 模仿抖音短视频，使用VerticalViewPager实现，实现了预加载功能
 * Created by Doikki on 2019/10/13.
 */

public class TikTok2ActivityDk extends BaseActivityDk<VideoView> {

    /**
     * 当前播放位置
     */
    private int mCurPos;
    private List<TiktokBeanDk> mVideoList = new ArrayList<>();
    private Tiktok2AdapterDk mTiktok2Adapter;
    private VerticalViewPagerDk mViewPager;

    private PreloadManagerDk mPreloadManager;

    private TikTokControllerDk mController;

    private static final String KEY_INDEX = "index";

    public static void start(Context context, int index) {
        Intent i = new Intent(context, TikTok2ActivityDk.class);
        i.putExtra(KEY_INDEX, index);
        context.startActivity(i);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_tiktok2dk;
    }

    @Override
    protected int getTitleResId() {
        return R.string.str_tiktok_2;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusBarTransparent();
        initViewPager();
        initVideoView();
        mPreloadManager = PreloadManagerDk.getInstance(this);

        addData(null);
        Intent extras = getIntent();
        int index = extras.getIntExtra(KEY_INDEX, 0);
        mViewPager.setCurrentItem(index);

        mViewPager.post(new Runnable() {
            @Override
            public void run() {
                startPlay(index);
            }
        });
    }

    private void initVideoView() {
        mVideoView = new VideoView(this);
        mVideoView.setLooping(true);

        //以下只能二选一，看你的需求
        mVideoView.setRenderViewFactory(TikTokRenderViewFactoryDk.create());
//        mVideoView.setScreenScaleType(VideoView.SCREEN_SCALE_CENTER_CROP);

        mController = new TikTokControllerDk(this);
        mVideoView.setVideoController(mController);
    }

    private void initViewPager() {
        mViewPager = findViewById(R.id.vvp);
        mViewPager.setOffscreenPageLimit(4);
        mTiktok2Adapter = new Tiktok2AdapterDk(mVideoList);
        mViewPager.setAdapter(mTiktok2Adapter);
        mViewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            private int mCurItem;

            /**
             * VerticalViewPager是否反向滑动
             */
            private boolean mIsReverseScroll;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (position == mCurItem) {
                    return;
                }
                mIsReverseScroll = position < mCurItem;
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == mCurPos) {
                    return;
                }
                startPlay(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (state == VerticalViewPagerDk.SCROLL_STATE_DRAGGING) {
                    mCurItem = mViewPager.getCurrentItem();
                }

                if (state == VerticalViewPagerDk.SCROLL_STATE_IDLE) {
                    mPreloadManager.resumePreload(mCurPos, mIsReverseScroll);
                } else {
                    mPreloadManager.pausePreload(mCurPos, mIsReverseScroll);
                }
            }
        });
    }

    private void startPlay(int position) {
        int count = mViewPager.getChildCount();
        for (int i = 0; i < count; i ++) {
            View itemView = mViewPager.getChildAt(i);
            Tiktok2AdapterDk.ViewHolder viewHolder = (Tiktok2AdapterDk.ViewHolder) itemView.getTag();
            if (viewHolder.mPosition == position) {
                mVideoView.release();
                UtilsDk.removeViewFormParent(mVideoView);

                TiktokBeanDk tiktokBean = mVideoList.get(position);
                String playUrl = mPreloadManager.getPlayUrl(tiktokBean.videoDownloadUrl);
                L.i("startPlay: " + "position: " + position + "  url: " + playUrl);
                mVideoView.setUrl(playUrl);
                mController.addControlComponent(viewHolder.mTikTokView, true);
                viewHolder.mPlayerContainer.addView(mVideoView, 0);
                mVideoView.start();
                mCurPos = position;
                break;
            }
        }
    }

    public void addData(View view) {
        mVideoList.addAll(DataUtilDk.getTiktokDataFromAssets(this));
        mTiktok2Adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPreloadManager.removeAllPreloadTask();
        //清除缓存，实际使用可以不需要清除，这里为了方便测试
        ProxyVideoCacheManagerDk.clearAllCache(this);
    }
}
