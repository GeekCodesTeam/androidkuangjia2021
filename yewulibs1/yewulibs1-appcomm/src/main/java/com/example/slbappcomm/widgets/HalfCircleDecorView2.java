package com.example.slbappcomm.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.geek.libglide47.base.util.DisplayUtil;
import com.geek.libutils.app.MyLogUtil;

public class HalfCircleDecorView2 extends RelativeLayout {
    private Paint mPaint;
    private int radius = 8;
    private int num = 8;
    private int w;
    private int h;
    private int tbGap;

    public HalfCircleDecorView2(@NonNull Context context) {
        this(context, null);
    }

    public HalfCircleDecorView2(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HalfCircleDecorView2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        tbGap = DisplayUtil.dip2px(getContext(), 8);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
        MyLogUtil.e("ssssssssssss-----h---", h + "");
        num = h / 32;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        MyLogUtil.e("ssssssssssss---num--", num + "");
        for (int i = 0; i < num; i++) {
            canvas.drawCircle(w * 10 / 38, tbGap + (h - 2 * tbGap) * (i * 2 + 1) / (num * 2), radius, mPaint);
        }
    }
}
