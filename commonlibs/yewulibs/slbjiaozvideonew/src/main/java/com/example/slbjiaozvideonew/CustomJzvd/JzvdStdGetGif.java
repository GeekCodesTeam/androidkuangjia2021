package com.example.slbjiaozvideonew.CustomJzvd;

import android.content.Context;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.slbjiaozvideonew.R;
import com.example.slbjiaozvideonew.Tab_3_List.GifCreateHelper;

import java.io.File;

import cn.jzvd.JzvdStd;

/**
 * Created by dl on 2020/4/6.
 */
public class JzvdStdGetGif extends JzvdStd implements GifCreateHelper.JzGifListener {

    GifCreateHelper mGifCreateHelper;

    TextView tv_hint;
    FrameLayout fl_hint_region;
    ImageView convert_to_gif;

    String saveGifPath;
    long current;

    public JzvdStdGetGif(Context context) {
        super(context);
    }

    public JzvdStdGetGif(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        super.init(context);

        tv_hint = findViewById(R.id.tv_hint);
        fl_hint_region = findViewById(R.id.fl_hint_region);
        convert_to_gif = findViewById(R.id.convert_to_gif);


        convert_to_gif.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_hint.setText("正在创建Gif...");
                fl_hint_region.setVisibility(View.VISIBLE);

                saveGifPath = Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/jiaozi-" + System.currentTimeMillis() + ".gif";
                mGifCreateHelper = new GifCreateHelper(JzvdStdGetGif.this, JzvdStdGetGif.this, 200, 1, 300, 200, 5000, saveGifPath);
                current = System.currentTimeMillis();
                mGifCreateHelper.startGif();//这个函数里用了jzvd的两个参数。
                try {
                    mediaInterface.pause();
                    onStatePause();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        topContainer.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        fl_hint_region.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }


    @Override
    public void process(final int curPosition, final int total, final String status) {
        Log.e("Jzvd-gif", status + "  " + curPosition + "/" + total + "  time: " + (System.currentTimeMillis() - current));
        post(new Runnable() {
            @Override
            public void run() {
                tv_hint.setText(curPosition + "/" + total + " " + status);
            }
        });
    }

    @Override
    public void result(boolean success, File file) {
        fl_hint_region.setVisibility(View.GONE);
        Toast.makeText(getContext(), "创建成功:" + saveGifPath, Toast.LENGTH_LONG).show();
    }

    @Override
    public int getLayoutId() {
        return R.layout.jz_layout_gif;
    }

    @Override
    public void onClickUiToggle() {
        super.onClickUiToggle();
        if (screen == SCREEN_FULLSCREEN) {
            if (bottomContainer.getVisibility() == View.VISIBLE) {
                convert_to_gif.setVisibility(View.VISIBLE);
            } else {
                convert_to_gif.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void setScreenFullscreen() {
        super.setScreenFullscreen();
        convert_to_gif.setVisibility(View.VISIBLE);
    }

    @Override
    public void dissmissControlView() {
        super.dissmissControlView();
        post(new Runnable() {
            @Override
            public void run() {
                if (screen == SCREEN_FULLSCREEN) {
                    convert_to_gif.setVisibility(View.GONE);
                    bottomProgressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void changeUiToPlayingClear() {
        super.changeUiToPlayingClear();
        if (screen == SCREEN_FULLSCREEN) {
            bottomProgressBar.setVisibility(GONE);
            convert_to_gif.setVisibility(View.GONE);
        }
    }

    @Override
    public void setScreenNormal() {
        super.setScreenNormal();
        convert_to_gif.setVisibility(View.GONE);
    }

    @Override
    public void reset() {
        posterImageView.setImageBitmap(textureView.getBitmap());
        super.reset();
        cancelDismissControlViewTimer();
        unregisterWifiListener(getApplicationContext());
    }
}
