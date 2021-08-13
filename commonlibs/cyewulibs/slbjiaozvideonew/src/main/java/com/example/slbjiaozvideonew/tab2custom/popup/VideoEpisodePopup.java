package com.example.slbjiaozvideonew.tab2custom.popup;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.slbjiaozvideonew.R;
import com.example.slbjiaozvideonew.tab2custom.AGEpsodeEntity;
import com.example.slbjiaozvideonew.tab2custom.VideoEpisodeAdapter;
import com.example.slbjiaozvideonew.utils.DipAndPx;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class VideoEpisodePopup extends PopupWindow {
    private static final int COMPLETED = 0;
    //    protected DismissTimerTask mDismissTimerTask;
    private Context mC;
    private LinearLayout main;
    private LayoutInflater inflater;
    private View contentView;
    private RecyclerView episodeRecycler;
    private VideoEpisodeAdapter episodeAdapter;
    private List<AGEpsodeEntity> episodeList;
    private EpisodeClickListener episondeClickListener;
    /**
     * 当前正在播放的集数
     */
    private int playNum = 0;
    //    private Timer mDismissTimer;
    private ScheduledExecutorService mExecutorService;
    private Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == COMPLETED) {
                dismiss();

            }
        }
    };

    public VideoEpisodePopup(Context context, List<AGEpsodeEntity> entities) {
        super(context);
        this.mC = context;
        mC = context;
        this.episodeList = entities;
        inflater = (LayoutInflater) mC.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.popup_video_episode, null);
        setContentView(contentView);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        setWidth(DipAndPx.dip2px(context, 320));
        setOutsideTouchable(true);
        //不设置该属性，弹窗于屏幕边框会有缝隙并且背景不是半透明
        setBackgroundDrawable(new BitmapDrawable());
        main = contentView.findViewById(R.id.video_main);
        episodeRecycler = contentView.findViewById(R.id.video_episode);
        episodeRecycler.setLayoutManager(new GridLayoutManager(context, 5));
        episodeAdapter = new VideoEpisodeAdapter(mC, episodeList);
        episodeRecycler.setAdapter(episodeAdapter);

        episodeAdapter.setmOnItemClickListener(new VideoEpisodeAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                if (episondeClickListener != null) {
                    episondeClickListener.onEpisodeClickListener(episodeList.get(position), position);
                }
                //更换当前正在播放的集数
                if (playNum < 1) {
                    playNum = 1;
                }
                episodeList.get(playNum - 1).setPlay(false);
                playNum = position + 1;
                episodeList.get(playNum - 1).setPlay(true);
                episodeAdapter.notifyDataSetChanged();
            }
        });

        main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startDismissTimer();
                return false;
            }
        });
        episodeRecycler.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startDismissTimer();
                return false;
            }
        });
    }

    public EpisodeClickListener getEpisondeClickListener() {
        return episondeClickListener;
    }

    public void setEpisondeClickListener(EpisodeClickListener episondeClickListener) {
        this.episondeClickListener = episondeClickListener;
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        startDismissTimer();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        cancelDismissTimer();
    }

    public void setPlayNum(int playNum) {
        if (this.playNum != 0) {
            episodeList.get(this.playNum - 1).setPlay(false);
            this.playNum = playNum;
            episodeList.get(playNum - 1).setPlay(true);
        } else {
            this.playNum = playNum;
            episodeList.get(this.playNum - 1).setPlay(true);
        }

        episodeRecycler.getAdapter().notifyDataSetChanged();
    }

    public void startDismissTimer() {
        cancelDismissTimer();
//        mDismissTimer = new Timer();
//        mDismissTimerTask = new DismissTimerTask();
//        mDismissTimer.schedule(mDismissTimerTask, 2500);
        //
        mExecutorService = Executors.newScheduledThreadPool(1);
        mExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = COMPLETED;
                handler.sendMessage(message);
            }
        }, 0, 2500, TimeUnit.MILLISECONDS);
    }

    public void cancelDismissTimer() {
//        if (mDismissTimer != null) {
//            mDismissTimer.cancel();
//        }
//        if (mDismissTimerTask != null) {
//            mDismissTimerTask.cancel();
//        }
        //
        if (mExecutorService != null) {
            mExecutorService.shutdown();
        }

    }

    public interface EpisodeClickListener {
        /**
         * 选集发生变化
         *
         * @param entity
         * @param position
         */
        void onEpisodeClickListener(AGEpsodeEntity entity, int position);
    }

//    public class DismissTimerTask extends TimerTask {
//
//        @Override
//        public void run() {
//            Message message = new Message();
//            message.what = COMPLETED;
//            handler.sendMessage(message);
//        }
//    }
}
