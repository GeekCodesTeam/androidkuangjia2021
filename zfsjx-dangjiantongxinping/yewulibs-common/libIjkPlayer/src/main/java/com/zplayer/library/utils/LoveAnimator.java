package com.zplayer.library.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zplayer.library.R;

import java.util.Random;


public class LoveAnimator extends RelativeLayout {
    private Context mContext;
    final float[] num = {-30, -20, 0, 20, 30};

    public LoveAnimator(Context context) {
        super(context);
        initView(context);
    }

    public LoveAnimator(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LoveAnimator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @SuppressLint("NewApi")
    public LoveAnimator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //触摸监听
        final ImageView imageView = new ImageView(this.mContext);
        LayoutParams params = new LayoutParams(300, 300);
        params.leftMargin = (int) (event.getX() - 150);
        params.topMargin = (int) (event.getY() - 300);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.details_icon_like_pressed));
        imageView.setLayoutParams(params);
        addView(imageView);
        //设置imageView动画
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scale(imageView, "scaleX", 2f, 0.9f, 100, 0))
                .with(scale(imageView, "scaleY", 2f, 0.9f, 100, 0))
                .with(rotation(imageView, 0, 0, num[new Random().nextInt(4)]))
                .with(alpha(imageView,0,1,100,0))
                .with(scale(imageView,"scaleX",0.9f,1,50,150))
                .with(scale(imageView,"scaleY",0.9f,1,50,150))
                .with(translationY(imageView,0,-600,800,400))
                .with(alpha(imageView,1,0,300,400))
                .with(scale(imageView,"scaleX",1,3f,700,400))
                .with(scale(imageView,"scaleY",1,3f,700,400));
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                removeViewInLayout(imageView);
            }
        });
        return super.onTouchEvent(event);
    }

    private Animator translationY(View view,float from,float to,long time,long delayTime) {
        ObjectAnimator translationY = ObjectAnimator.ofFloat(view,"translationY",from,to);
        translationY.setInterpolator(new LinearInterpolator());
        translationY.setStartDelay(delayTime);
        translationY.setDuration(time);
        return translationY;
    }

    private Animator alpha(View view, float from, float to, long time, long delayTime) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", from, to);
        alpha.setInterpolator(new LinearInterpolator());
        alpha.setDuration(time);
        alpha.setStartDelay(delayTime);
        return alpha;
    }

    private Animator rotation(View view, long time, long delayTime, float... values) {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(view, "rotation", values);
        rotation.setDuration(time);
        rotation.setStartDelay(delayTime);
        rotation.setInterpolator(new TimeInterpolator() {
            @Override
            public float getInterpolation(float input) {
                return input;
            }
        });
        return rotation;
    }

    public ObjectAnimator scale(View view, String propertyName,
                                float from, float to,
                                long time, long delayTime) {
        ObjectAnimator translation = ObjectAnimator.ofFloat(view, propertyName, from, to);
        translation.setInterpolator(new LinearInterpolator());
        translation.setStartDelay(delayTime);
        translation.setDuration(time);
        return translation;
    }

}
