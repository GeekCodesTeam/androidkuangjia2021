package com.fosung.xuanchuanlan.xuanchuanlan.daketang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fosung.frameutils.http.response.ZResponse;
import com.fosung.frameutils.imageloader.ImageLoaderUtils;
import com.fosung.frameutils.util.DisplayUtil;
import com.fosung.frameutils.util.ScreenUtil;
import com.fosung.xuanchuanlan.R;
import com.fosung.xuanchuanlan.common.base.ActivityParam;
import com.fosung.xuanchuanlan.common.base.BaseVideoPlayActivity;
import com.fosung.xuanchuanlan.xuanchuanlan.daketang.http.entity.DKTCourseListReply;
import com.fosung.xuanchuanlan.xuanchuanlan.main.http.XCLHttp;
import com.fosung.xuanchuanlan.xuanchuanlan.main.http.XCLHttpUrlMaster;
import com.zcolin.gui.zrecyclerview.BaseRecyclerAdapter;
import com.zcolin.gui.zrecyclerview.ZRecyclerView;
import com.zplayer.library.ZPlayer;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

@ActivityParam(isShowToolBar = false)
public class DKTTopicDetailActivity extends BaseVideoPlayActivity implements View.OnClickListener {

    private ZRecyclerView zRecyclerView;
    private DKTTopicDetailCourseAdapter adapter;
    private TextView pubTimeTextView;
    private TextView topicTextView;
    private TextView descTextView;
    private ImageButton seprateBtn;

    private ImageView ivVideoCover;
    private ImageView ivPlay;
    private RelativeLayout rlPlayControl;
    private Integer currentIndex = 0;
    private String topicId;

    private final float topicImageScale = 16.0f/10.0f;
    private final float courseImageScale = 670.0f/400.0f;//省灯塔教育资源库图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
        isPlayed = false;
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



    private void requestForCourseList(){


        Map<String, String> map = new HashMap<String, String>();
        map.put("id",topicId);//3370426038818449131
        XCLHttp.postJson(XCLHttpUrlMaster.DKTCourseList, map, new ZResponse<DKTCourseListReply>(DKTCourseListReply.class) {
            @Override
            public void onSuccess(Response response, DKTCourseListReply resObj) {
                if (resObj != null && resObj.datalist!= null && !resObj.datalist.isEmpty()){
                    adapter.setDatas(resObj.datalist);
//                    setVideoCoverImageForCurrentIndex();
                }

                zRecyclerView.setPullLoadMoreCompleted();
            }

            @Override
            public void onError(int code, String error) {
                super.onError(code, error);
//                adapter.setDatas(null);
                zRecyclerView.setPullLoadMoreCompleted();
            }
            @Override
            public void onFinished() {
                super.onFinished();
                zRecyclerView.setPullLoadMoreCompleted();
            }
        });
    }
//    private void setVideoCoverImageForCurrentIndex(){
//        if (adapter.getDatas() != null && !adapter.getDatas().isEmpty()) {
//            DKTCourseListReply.DKTCourseData data = adapter.getDatas().get(currentIndex);
//            data.picAddr = data.picAddr == null ? null : data.picAddr.replace(" ", "%20");
//            String url = XCLHttpUrlMaster.getDYJYFullImageURL(data.picAddr);
//            ImageLoaderUtils.displayImage(this, url, ivVideoCover,R.drawable.course_placeholder);
//        }
//    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_dkttopic_detail;
    }

    @Override
    protected ZPlayer initPlayer() {
        LinearLayout headerView = (LinearLayout) LayoutInflater.from(mActivity)
                .inflate(R.layout.activity_dkt_topic_detail_list_head_view, null);

        Intent intent = getIntent();
        String pubTime = intent.getStringExtra("pubTime");
        String topicName = intent.getStringExtra("name");
        String desc = intent.getStringExtra("desc");
        topicId = intent.getStringExtra("topicId");
        String coverImg = intent.getStringExtra("coverImg");


        pubTimeTextView = (TextView)headerView.findViewById(R.id.dkt_topic_pub_time);
        pubTimeTextView.setText(pubTime);
        topicTextView = (TextView)headerView.findViewById(R.id.dkt_topic_name);
        topicTextView.setText(topicName);
        descTextView = (TextView)headerView.findViewById(R.id.dkt_topic_desc);
        descTextView.setText("课程简介："+ (TextUtils.isEmpty(desc)?"暂无":desc));
        seprateBtn = (ImageButton)headerView.findViewById(R.id.dkt_separate_btn);

        descTextView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                descTextView.getViewTreeObserver().removeOnPreDrawListener(this);
                int lineCount = descTextView.getLineCount();
                seprateBtn.setVisibility(lineCount <= 2 ? View.GONE : View.VISIBLE);
                descTextView.setMaxLines(2);
                return false;
            }
        });


        zRecyclerView = getView(R.id.recyclerview_course);
        zRecyclerView.setGridLayout(true,2);

        zRecyclerView.addHeaderView(headerView);

        adapter = new DKTTopicDetailCourseAdapter();
        zRecyclerView.setAdapter(adapter);


        zRecyclerView.setIsLoadMoreEnabled(false);
        zRecyclerView.setOnPullLoadMoreListener(new ZRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                requestForCourseList();
            }

            @Override
            public void onLoadMore() {
//                zRecyclerView.setNoMore(false);
            }
        });

        zRecyclerView.refreshWithPull();



        zRecyclerView.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<DKTCourseListReply.DKTCourseData>() {
            @Override
            public void onItemClick(View covertView, int position, DKTCourseListReply.DKTCourseData data) {

//                player.stop();
//                player.release();
                currentIndex = position;
//                setVideoCoverImageForCurrentIndex();
                playWithAddr(data.videoAddr);
            }
        });

        ivPlay = getView(R.id.iv_play);
        ivPlay.setOnClickListener(this);
        ivVideoCover = getView(R.id.iv_video_cover);
        ImageLoaderUtils.displayImage(this, coverImg, ivVideoCover,R.drawable.course_placeholder);

        rlPlayControl = getView(R.id.rl_player_control);
        final ZPlayer player = getView(R.id.view_player);

        player.getLayoutParams().height = (int)(ScreenUtil.getScreenWidth(mActivity) / topicImageScale);//动态调整高度为670*400
        player.setNetChangeListener(false)//设置监听手机网络的变化,这个参数是内部是否处理网络监听，和setOnNetChangeListener没有关系
                .setShowTopControl(false)
                .setShowCenterControl(true)
                .setSupportGesture(false)
                .setOnFullScreenListener(new ZPlayer.OnFullScreenListener() {
                    @Override
                    public void onFullScreen(boolean b) {
                        //全屏的时候使用了attrs.flags |= Window.FEATURE_NO_TITLE;此Flag会导致锁屏问题，解决此问题需要取消设置此标志位。
                        getWindow().clearFlags(Window.FEATURE_NO_TITLE);
                        if (b) {
//                            rlBottom.setVisibility(View.GONE);
                            player.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                        } else {
                            player.getLayoutParams().height = (int)(ScreenUtil.getScreenWidth(mActivity) / topicImageScale);
//                            rlBottom.setVisibility(View.VISIBLE);
                        }

//                        player.setFullScreen(b);
//                        player.updateFullScreenButton();
                    }
                })

                .onComplete(new Runnable() {
                    @Override
                    public void run() {
                        currentIndex++;
                        if (currentIndex >= adapter.getDatas().size()){
                            currentIndex = currentIndex % adapter.getDatas().size();
                        }
                        DKTCourseListReply.DKTCourseData data = adapter.getDatas().get(currentIndex);
                        playWithAddr(data.videoAddr);
                    }
                })
                .setScaleType(ZPlayer.SCALETYPE_FITPARENT);

        return player;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.dkt_separate_btn:{
                ImageButton imageButton = (ImageButton) v;
                boolean selected = imageButton.isSelected();
                if (selected){
                    descTextView.setMaxLines(2);
                }else{
                    descTextView.setMaxLines(Integer.MAX_VALUE);
                }
                imageButton.setSelected(!selected);
            }
            break;
            case R.id.iv_play:
                if (player != null && adapter.getDatas() != null && !adapter.getDatas().isEmpty()) {
                    DKTCourseListReply.DKTCourseData data = adapter.getDatas().get(currentIndex);
                    playWithAddr(data.videoAddr);

                }
                break;
        }

    }

    private void playWithAddr(String addr){
        addr = (addr == null ? null : addr.replace(" ", "%20"));
        String url = addr;
        if (url != null){
            player.play(url);
        }else{
            Toast.makeText(this, "此课件暂无视频地址", Toast.LENGTH_SHORT).show();
        }
        rlPlayControl.setVisibility(View.GONE);
    }
    

    private class DKTTopicDetailCourseAdapter extends BaseRecyclerAdapter<DKTCourseListReply.DKTCourseData> {


        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.item_dkt_course_recyclerview;
        }

        @Override
        public void setUpData(CommonHolder holder, int position, int viewType, DKTCourseListReply.DKTCourseData data) {
            final ImageView imageView = getView(holder,R.id.dkt_course_icon);
            imageView.getLayoutParams().height = (int) ((ScreenUtil.getScreenWidth(DKTTopicDetailActivity.this) - DisplayUtil.dip2px(DKTTopicDetailActivity.this,60)) /2 /courseImageScale);
            TextView textView = getView(holder,R.id.dkt_course_name);
            textView.setText(data.courseName);
            String imageUrl = data.picAddr;
            ImageLoaderUtils.displayImage(holder.itemView.getContext(),imageUrl,imageView,R.drawable.course_placeholder);

        }
    }
}
