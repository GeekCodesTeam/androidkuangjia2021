package com.fosung.lighthouse.jiaoyuziyuan.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fosung.lighthouse.jiaoyuziyuan.R;

import org.xmlpull.v1.XmlPullParser;

public class SegmentView extends LinearLayout {
    private TextView textViewLeft;
    private TextView textViewRight;
    private onSegmentViewClickListener listener;
    public SegmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SegmentView(Context context) {
        super(context);
        init();
    }

    public SegmentView(Context context, AttributeSet attrs, int defStyle) {
        super(context,attrs,defStyle);
        init();
    }

    private void init() {
//		this.setLayoutParams(new LinearLayout.LayoutParams(dp2Px(getContext(), 60), LinearLayout.LayoutParams.WRAP_CONTENT));
        textViewLeft = new TextView(getContext());
        textViewRight = new TextView(getContext());
        textViewLeft.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        textViewRight.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        textViewLeft.setText("手机");
        textViewRight.setText("电脑");
        @SuppressLint("ResourceType") XmlPullParser xrp = getResources().getXml(R.drawable.seg_text_color_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);
            textViewLeft.setTextColor(csl);
            textViewRight.setTextColor(csl);
        } catch (Exception e) {
        }
        textViewLeft.setGravity(Gravity.CENTER);
        textViewRight.setGravity(Gravity.CENTER);
        textViewLeft.setPadding(3, 6, 3, 6);
        textViewRight.setPadding(3, 6, 3, 6);
        setSegmentTextSize(16);
        textViewLeft.setBackgroundResource(R.drawable.seg_left);
        textViewRight.setBackgroundResource(R.drawable.seg_right);
        textViewLeft.setSelected(true);
        this.removeAllViews();
        this.addView(textViewLeft);
        this.addView(textViewRight);
        this.invalidate();

        textViewLeft.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (textViewLeft.isSelected()) {
                    return;
                }
                textViewLeft.setSelected(true);
                textViewRight.setSelected(false);
                if (listener != null) {
                    listener.onSegmentViewClick(textViewLeft, 0);
                }
            }
        });
        textViewRight.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (textViewRight.isSelected()) {
                    return;
                }
                textViewRight.setSelected(true);
                textViewLeft.setSelected(false);
                if (listener != null) {
                    listener.onSegmentViewClick(textViewRight, 1);
                }
            }
        });
    }
    /**
     * 设置字体大小 单位dip
     * @param dp
     */
    public void setSegmentTextSize(int dp) {
        textViewLeft.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
        textViewRight.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
    }

    private static int dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public void setOnSegmentViewClickListener(onSegmentViewClickListener listener) {
        this.listener = listener;
    }


    /**
     * 设置文字
     * @param text
     * @param position
     */
    public void setSegmentText(CharSequence text,int position) {
        if (position == 0) {
            textViewLeft.setText(text);
        }
        if (position == 1) {
            textViewRight.setText(text);
        }
    }

    public static interface onSegmentViewClickListener{
        /**
         *
         * @param v
         * @param position 0-左边 1-右边
         */
        public void onSegmentViewClick(View v,int position);
    }
}
