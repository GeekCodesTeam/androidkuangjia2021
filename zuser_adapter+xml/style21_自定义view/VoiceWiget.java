package com.hs.zhidaokeyboard.voiceutil.widgets;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hs.zhidaokeyboard.R;
import com.hs.zhidaokeyboard.quanxian.PermissionsChecker;
import com.hs.zhidaokeyboard.voiceutil.voicemanage.MediaManager;

import static com.hs.zhidaokeyboard.quanxian.PermissionsChecker.PERMISSIONS_STORAGE;
import static com.hs.zhidaokeyboard.quanxian.PermissionsChecker.REQUEST_EXTERNAL_STORAGE;

/**
 * Created by shining on 2017/11/16.
 */

public class VoiceWiget extends FrameLayout {

    private TextView tv_play;
    private String filePaths;

    public VoiceWiget(Context context) {
        super(context);
        init(context);
    }

    public VoiceWiget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VoiceWiget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public VoiceWiget(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(final Context context) {
        View.inflate(context, R.layout.activity_demo_voice_item, this);
        tv_play = (TextView) findViewById(R.id.tv_play);
        tv_play.setBackgroundResource(R.drawable.fm_play_new);
        tv_play.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                PermissionsChecker mPermissionsChecker = new PermissionsChecker(context);
                // 缺少权限时, 进入权限配置页面
                if (mPermissionsChecker.lacksPermissions(PERMISSIONS_STORAGE)) {
                    // We don't have permission so prompt the user
                    ActivityCompat.requestPermissions((Activity) context, PERMISSIONS_STORAGE,
                            REQUEST_EXTERNAL_STORAGE);
                } else {
                    if (MediaManager.mMediaPlayer != null && MediaManager.mMediaPlayer.isPlaying()) {
                        tv_play.setBackgroundResource(R.drawable.fm_play_new);
                        MediaManager.mMediaPlayer.reset();
                        return;
                    }
                    // 播放动画
                    tv_play.setBackgroundResource(R.drawable.fm_main_pause);
                    // 播放音频
                    MediaManager.playSound(filePaths,
                            new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    //finish
                                    tv_play.setBackgroundResource(R.drawable.fm_play_new);
                                }
                            });

                }
            }
        });
    }

    /**
     * 播放amr地址
     * @param filePaths
     */
    public void setup(String filePaths) {
        this.filePaths = filePaths;
    }

    /**
     * 录音完成后的回调
     *
     * @author zhy
     */
    public interface VoiceWigetListener {
        void onPlay(String filePath);
    }

    private VoiceWigetListener mListener;

    public void setVoiceWigetListener(VoiceWigetListener listener) {
        mListener = listener;
    }


    public void onVoicePause() {
        MediaManager.pause();
    }

    public void onVoiceResume() {
        MediaManager.resume();
    }

    public void onVoiceDestroy() {
        MediaManager.release();
    }

}
