package com.example.gsydemo.updateview;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.gsydemo.bt.Member;
import com.example.gsyvideoplayer.R;

import java.text.DecimalFormat;


/**
 * Created by mqh on 2018/7/28.
 */

public class HrCardView extends FrameLayout {
    private Context mContext;
    private LinearLayout ll_wrap;
    private ImageView headIv;
    private TextView nameTv;
    private TextView calTipTv;
    private ImageView calIv;
    private TextView calTv;
    private TextView heartTipTv;
    private ImageView hrIv;
    private TextView hrTv;
    private ImageView percentIv;
    private TextView percentTv;
    private TextView percentUnitTv;
    private TextView tv_bmi;
    private TextView tv_tizhi;
    private Member mMember;
    private int mWidth;
    private int mHeight;
    private float ratio;// E537C3D7
//    private int[] bgColors = {Color.parseColor("#80000000"), Color.parseColor("#E559C97F"), Color.parseColor("#E5FB9831"), Color.parseColor("#E5F16B76"), Color.parseColor("#E5F5584A")};
    private int[] bgColors = {Color.parseColor("#80000000"), Color.parseColor("#80000000"), Color.parseColor("#80000000"), Color.parseColor("#80000000"), Color.parseColor("#80000000")};

    public HrCardView(Context context) {
        this(context, null);
    }

    public HrCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HrCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.item_hr_card, this);
        ll_wrap = findViewById(R.id.ll_wrap);
        headIv = findViewById(R.id.headIv);
        nameTv = findViewById(R.id.nameTv);
        calTipTv = findViewById(R.id.calTipTv);
        calIv = findViewById(R.id.calIv);
        calTv = findViewById(R.id.calTv);
        headIv = findViewById(R.id.headIv);
        heartTipTv = findViewById(R.id.heartTipTv);
        hrTv = findViewById(R.id.hrTv);
        hrIv = findViewById(R.id.hrIv);
        percentIv = findViewById(R.id.percentIv);
        percentTv = findViewById(R.id.percentTv);
        percentUnitTv = findViewById(R.id.percentUnitTv);
        tv_bmi = findViewById(R.id.tv_bmi);
        tv_tizhi = findViewById(R.id.tv_tizhi);
    }

    public synchronized void setMember(Member member) {
        this.mMember = member;
        if (mMember == null) {
            reset();
        } else {
            if (!TextUtils.isEmpty(mMember.getHeadImgUrl()))
                Glide.with(mContext)
                        .load(mMember.getHeadImgUrl()).placeholder(R.drawable.lololo)
                        .transform(new GlideCircleTransform(mContext)).dontAnimate()
                        .into(headIv);
            nameTv.setText(mMember.getNickname());
            if (member.getCal() > 0) {
                calTv.setText(((int) member.getCal()) + "");
            }
        }
    }

    public Member getMember() {
        return mMember;
    }

    private int oldIndex;

    public void setHr(Member member) {
        if (member == null || member.getHr() == 0)
            return;
        if (hrIv != null) {
            hrTv.setText(member.getHr() + "");
        }
        if (calTv != null) {
            int cal = ((int) member.getCal());
            DecimalFormat df = new DecimalFormat("######0.00");
            calTv.setText(String .format("%.2f",member.getCal()));
        }
        int percent = member.getHr() * 100 / member.getMaxhr();
        if (percentTv != null)
            percentTv.setText(percent + "");
        if (tv_bmi != null)
            tv_bmi.setText("BMI：" + member.getBmi());
        if (tv_tizhi != null)
            tv_tizhi.setText("体脂：" + member.getTizhi());
//        MemberBeanSqlite.changeData2(mContext, member);
        //背景颜色渐变
        if (ll_wrap != null && member != null && member.getMaxhr() != 0) {
            int newIndex = getColorLevelIndex(percent);
            if (newIndex != oldIndex) {
                startBgChangeAnimation(oldIndex, newIndex);
            }
            oldIndex = newIndex;
        }
    }

    private int getColorLevelIndex(int percent) {
        int ret;
        if (percent < 60) {
            ret = 0;
        } else if (percent < 70) {
            ret = 1;
        } else if (percent < 80) {
            ret = 2;
        } else if (percent < 90) {
            ret = 3;
        } else {
            ret = 4;
        }
        return ret;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0 && (mHeight != h || mWidth != w)) {
            mWidth = w;
            mHeight = h;
//            resetLayout();
        }
    }

    private void setBgGd(int color) {
        GradientDrawable gd = (GradientDrawable) ll_wrap.getBackground();
        if (gd == null) {
            int padding = (int) (2 * ratio);
            gd = new GradientDrawable();
            gd.setCornerRadius(ratio * 8);
            ll_wrap.setPadding(padding, 0, padding, 0);
        }
        gd.setColor(color);
//        ll_wrap.setBackgroundDrawable(gd);
        ll_wrap.setBackground(gd);
    }

    private synchronized void startBgChangeAnimation(int oldIndex, int newIndex) {
        ValueAnimator colorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), bgColors[oldIndex], bgColors[newIndex]);
        colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int color = (int) animation.getAnimatedValue();
                setBgGd(color);
            }
        });
        colorAnimator.setDuration(300);
        colorAnimator.start();
    }

    //根据自身大小，重新设置内容空间的大小
    private void resetLayout() {
//        this.removeView(rootView);
//        this.addView(rootView);
        ratio = mHeight / 208f;
        setBgGd(bgColors[0]);
        //头像
        LinearLayout.LayoutParams headIvLp = (LinearLayout.LayoutParams) headIv.getLayoutParams();
        headIvLp.width = headIvLp.height = (int) (120 * ratio);
        headIvLp.bottomMargin = (int) (17 * ratio);
        headIv.setLayoutParams(headIvLp);
        //名字
        adjustTextSize(nameTv, 18);
        //CALORIE文字
        adjustTextSize(calTipTv, 14.4f);
        LinearLayout.LayoutParams calTipLp = (LinearLayout.LayoutParams) calTipTv.getLayoutParams();
        calTipLp.leftMargin = (int) (10 * ratio);
        calTipLp.topMargin = (int) (14 * ratio);
        calTipTv.setLayoutParams(calTipLp);
//        //卡路里图片
//        LinearLayout.LayoutParams calIvLp = (LinearLayout.LayoutParams) calIv.getLayoutParams();
//        calIvLp.height = (int) (26 * ratio);
//        calIvLp.width = (int) (20 * ratio);
//        calIvLp.rightMargin = calIvLp.leftMargin = (int) (10 * ratio);
//        calIv.setLayoutParams(calIvLp);
//        //卡路里数字
//        adjustTextSize(calTv, 64);

        //心率比值图片
        LinearLayout.LayoutParams percentIvLp = (LinearLayout.LayoutParams) percentIv.getLayoutParams();
        percentIvLp.height = (int) (26 * ratio);
        percentIvLp.width = (int) (20 * ratio);
        percentIvLp.leftMargin = (int) (20 * ratio);
        percentIv.setLayoutParams(percentIvLp);
        //心率比值文字
        adjustTextSize(percentTv, 64);
        adjustTextSize(percentUnitTv, 30);
        LinearLayout.LayoutParams unitTvLp = (LinearLayout.LayoutParams) percentUnitTv.getLayoutParams();
        unitTvLp.rightMargin = (int) (10 * ratio);
        percentUnitTv.setLayoutParams(unitTvLp);

        //HEART RATE文字
        adjustTextSize(heartTipTv, 14.4f);
        LinearLayout.LayoutParams heartTipLp = (LinearLayout.LayoutParams) heartTipTv.getLayoutParams();
        heartTipLp.leftMargin = (int) (10 * ratio);
        heartTipLp.topMargin = (int) (14 * ratio);
        heartTipTv.setLayoutParams(heartTipLp);
        //心率文字
        adjustTextSize(hrTv, 29);
        //心率图片
        LinearLayout.LayoutParams hrIvLp = (LinearLayout.LayoutParams) hrIv.getLayoutParams();
        hrIvLp.width = (int) (16 * ratio);
        hrIvLp.height = (int) (14 * ratio);
        hrIvLp.rightMargin = (int) (4 * ratio);
        hrIvLp.leftMargin = (int) (10 * ratio);
        hrIv.setLayoutParams(hrIvLp);


//        //心率比值图片
//        LinearLayout.LayoutParams percentIvLp = (LinearLayout.LayoutParams) percentIv.getLayoutParams();
//        percentIvLp.height = (int) (17 * ratio);
//        percentIvLp.width = (int) (14 * ratio);
//        percentIvLp.leftMargin = (int) (8 * ratio);
//        percentIvLp.rightMargin = (int) (4 * ratio);
//        percentIv.setLayoutParams(percentIvLp);
//        //心率和心率比值文字
//        adjustTextSize(hrTv, 29);
//        adjustTextSize(percentTv, 29);
//        adjustTextSize(percentUnitTv, 12.24f);
//        LinearLayout.LayoutParams unitTvLp = (LinearLayout.LayoutParams) percentUnitTv.getLayoutParams();
//        unitTvLp.rightMargin = (int) (8 * ratio);
//        percentUnitTv.setLayoutParams(unitTvLp);

        //卡路里图片
        LinearLayout.LayoutParams calIvLp = (LinearLayout.LayoutParams) calIv.getLayoutParams();
        calIvLp.height = (int) (17 * ratio);
        calIvLp.width = (int) (14 * ratio);
        calIvLp.leftMargin = (int) (8 * ratio);
        calIvLp.rightMargin = (int) (4 * ratio);
        calIv.setLayoutParams(calIvLp);
        //卡路里数字
        adjustTextSize(calTv, 29);
        LinearLayout.LayoutParams calTvLp = (LinearLayout.LayoutParams) calTv.getLayoutParams();
        calTvLp.rightMargin = (int) (10 * ratio);
        calTv.setLayoutParams(calTvLp);
    }

    private void reset() {
        Glide.with(mContext)
                .load(R.drawable.lololo)
                .transform(new GlideCircleTransform(mContext))
                .into(headIv);
        nameTv.setText("- -");
        calTv.setText("- -");
        percentTv.setText("- -");
        hrTv.setText("- -");
    }

    private void adjustTextSize(TextView textView, float oPx) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, oPx * ratio);
    }
}
