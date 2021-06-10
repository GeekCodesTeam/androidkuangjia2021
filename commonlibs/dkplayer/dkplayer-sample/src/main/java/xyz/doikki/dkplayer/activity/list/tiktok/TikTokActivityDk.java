package xyz.doikki.dkplayer.activity.list.tiktok;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import xyz.doikki.dkplayer.R;
import xyz.doikki.dkplayer.activity.BaseActivityDk;
import xyz.doikki.dkplayer.adapter.TikTokAdapterDk;
import xyz.doikki.dkplayer.bean.TiktokBeanDk;
import xyz.doikki.dkplayer.util.DataUtilDk;
import xyz.doikki.dkplayer.util.UtilsDk;
import xyz.doikki.dkplayer.util.cache.PreloadManagerDk;
import xyz.doikki.dkplayer.widget.controller.TikTokControllerDk;
import xyz.doikki.dkplayer.widget.render.TikTokRenderViewFactoryDk;
import xyz.doikki.videoplayer.player.VideoView;
import xyz.doikki.videoplayer.util.L;

import java.util.ArrayList;
import java.util.List;

/**
 * 模仿抖音短视频, 使用RecyclerView实现
 * Created by Doikki on 2018/1/6.
 * @deprecated 推荐 {@link TikTok2ActivityDk}
 */
@Deprecated
public class TikTokActivityDk extends BaseActivityDk<VideoView> {

    private TikTokControllerDk mController;
    private int mCurPos;
    private RecyclerView mRecyclerView;
    private List<TiktokBeanDk> mVideoList = new ArrayList<>();
    private TikTokAdapterDk mTikTokAdapter;

    private static final String KEY_INDEX = "index";
    private int mIndex;

    public static void start(Context context, int index) {
        Intent i = new Intent(context, TikTokActivityDk.class);
        i.putExtra(KEY_INDEX, index);
        context.startActivity(i);
    }

    @Override
    protected int getTitleResId() {
        return R.string.str_tiktok_1;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_tiktokdk;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusBarTransparent();
        mVideoView = new VideoView(this);
        //以下只能二选一，看你的需求
        mVideoView.setRenderViewFactory(TikTokRenderViewFactoryDk.create());
//        mVideoView.setScreenScaleType(VideoView.SCREEN_SCALE_CENTER_CROP);
        mVideoView.setLooping(true);
        mController = new TikTokControllerDk(this);
        mVideoView.setVideoController(mController);

        initRecyclerView();

        addData(null);

        Intent extras = getIntent();
        mIndex = extras.getIntExtra(KEY_INDEX, 0);
        mRecyclerView.scrollToPosition(mIndex);
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.rv);

        mTikTokAdapter = new TikTokAdapterDk(mVideoList);
        ViewPagerLayoutManagerDk layoutManager = new ViewPagerLayoutManagerDk(this, OrientationHelper.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mTikTokAdapter);
        layoutManager.setOnViewPagerListener(new OnViewPagerListenerDk() {
            @Override
            public void onInitComplete() {
                //自动播放第index条
                startPlay(mIndex);
            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                if (mCurPos == position) {
                    mVideoView.release();
                }
            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
                if (mCurPos == position) return;
                startPlay(position);
            }
        });
    }

    private void startPlay(int position) {
        View itemView = mRecyclerView.getChildAt(0);
        TikTokAdapterDk.VideoHolder viewHolder = (TikTokAdapterDk.VideoHolder) itemView.getTag();
        mVideoView.release();
        UtilsDk.removeViewFormParent(mVideoView);
        TiktokBeanDk item = mVideoList.get(position);
        String playUrl = PreloadManagerDk.getInstance(this).getPlayUrl(item.videoDownloadUrl);
        L.i("startPlay: " + "position: " + position + "  url: " + playUrl);
        mVideoView.setUrl(playUrl);
        mController.addControlComponent(viewHolder.mTikTokView, true);
        viewHolder.mPlayerContainer.addView(mVideoView, 0);
        mVideoView.start();
        mCurPos = position;
    }

    public void addData(View view) {
        mVideoList.addAll(DataUtilDk.getTiktokDataFromAssets(this));
        mTikTokAdapter.notifyDataSetChanged();
    }
}
