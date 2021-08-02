package com.example.slbappcomm.playermusic.floatbutton.floatbutton3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.example.slbappcomm.R;

import java.util.ArrayList;

public class DragFloatActionInject {

    public static final String LISTENBOOK_TAG1 = "后台播放";//
    public static final String HUIBEN_IDS_ZONG = "bookId";
    public static final String HUIBEN_IDS = "id";
    public static final String HUIBEN_TITLES = "name";
    public static final String HUIBEN_XMLY = "喜马拉雅听书数据";
    private View view;
    private DragFloatActionButton dragFloatActionButton;
    private String float_action;
    private String float_id_zong;
    private String float_id;
    private String float_name;
    private ArrayList<String> float_xmly;

    public String getFloat_action() {
        return float_action;
    }

    public void setFloat_action(String float_action) {
        this.float_action = float_action;
    }

    public String getFloat_id_zong() {
        return float_id_zong;
    }

    public void setFloat_id_zong(String float_id_zong) {
        this.float_id_zong = float_id_zong;
    }

    public String getFloat_id() {
        return float_id;
    }

    public void setFloat_id(String float_id) {
        this.float_id = float_id;
    }

    public String getFloat_name() {
        return float_name;
    }

    public void setFloat_name(String float_name) {
        this.float_name = float_name;
    }

    public ArrayList<String> getFloat_xmly() {
        return float_xmly;
    }

    public void setFloat_xmly(ArrayList<String> float_xmly) {
        this.float_xmly = float_xmly;
    }

    public static DragFloatActionInject inject(Activity context) {
        return new DragFloatActionInject(context);
    }

    public DragFloatActionInject(final Activity context) {
        FrameLayout decor = (FrameLayout) context.getWindow().getDecorView();
        view = LayoutInflater.from(context).inflate(R.layout.activity_dragfloataction_relativelayout, decor, false);
        dragFloatActionButton = view.findViewById(com.example.slbappcomm.R.id.mybtn2);
        Glide.with(context).load(R.drawable.slb_playgif2).into(dragFloatActionButton);
        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (SPUtils.getInstance().getBoolean(LISTENBOOK_TAG1, false) && audio.isMusicActive()) {
            dragFloatActionButton.setVisibility(View.VISIBLE);
        } else {
            dragFloatActionButton.setVisibility(View.GONE);
        }
        dragFloatActionButton.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                // 通过广播传回来听书页面的值点击进入听书当前item页面
                if (TextUtils.isEmpty(getFloat_id())) {
                    return;
                }
//                Intent intent = new Intent("hs.act.slbapp.ListenMusicActivity");
                Intent intent = new Intent(getFloat_action());
                intent.putExtra(HUIBEN_IDS_ZONG, getFloat_id_zong());
                intent.putExtra(HUIBEN_IDS, getFloat_id());
                intent.putExtra(HUIBEN_TITLES, getFloat_name());
                intent.putStringArrayListExtra(HUIBEN_XMLY, getFloat_xmly());
                context.startActivity(intent);
            }
        });

        decor.addView(view);

    }

    public abstract class OnMultiClickListener implements View.OnClickListener {
        // 两次点击按钮之间的点击间隔不能少于1000毫秒
        private final int MIN_CLICK_DELAY_TIME = 1000;
        private long lastClickTime;

        public abstract void onMultiClick(View v);

        @Override
        public void onClick(View v) {
            long curClickTime = System.currentTimeMillis();
            if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
                // 超过点击间隔后再将lastClickTime重置为当前点击时间
                lastClickTime = curClickTime;
                onMultiClick(v);
            }
        }
    }

}
