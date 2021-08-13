package com.example.slbappcomm.playermusic.floatbutton.floatbutton1;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.example.slbappcomm.R;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

public class MyButton extends AbastractDragFloatActionButton {
    private Context context;

    public MyButton(Context context) {
        super(context);
        this.context = context;
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.custom_button;//拿到你自己定义的悬浮布局
    }

    @Override
    public void renderView(View view) {
        //初始化那些布局
        ImageView iv1 = view.findViewById(R.id.iv1);
        iv1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("hs.act.slbapp.ListenMusicActivity"));
            }
        });
    }
}
