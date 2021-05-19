package com.example.slbjiaozvideonew.CustomJzvd;

import android.content.Context;
import android.util.AttributeSet;

import cn.jzvd.JzvdStd;
import com.example.slbjiaozvideonew.R;

public class MyJzvdStdNoTitleNoClarity extends JzvdStd {

    public MyJzvdStdNoTitleNoClarity(Context context) {
        super(context);
    }

    public MyJzvdStdNoTitleNoClarity(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_jzstd_notitle;
    }

}
