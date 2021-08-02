package com.haier.cellarette.baselibrary.btnonclick;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.haier.cellarette.baselibrary.R;
import com.haier.cellarette.baselibrary.btnonclick.view.BounceView;

/**
 * https://github.com/hariprasanths/Bounceview-Android
 */
public class BtnActivity extends AppCompatActivity {

    private Button tv1;
    private Button tv2;
    private TextView tv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btnonclick);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executeAnimation1(tv1);

            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executeAnimation2(tv2);

            }
        });
        BounceView.addAnimTo(tv3);
        //Bounce animation
//        BounceView.addAnimTo(tv3)
//                .setScaleForPopOutAnim(1.1f, 1.1f);
        //Horizontal flip animation
//        BounceView.addAnimTo(tv3)
//                .setScaleForPopOutAnim(1f, 0f);
//        //Vertical flip animation
//        BounceView.addAnimTo(tv3)
//                .setScaleForPopOutAnim(0f, 1f);
//        //Flicker animation
//        BounceView.addAnimTo(tv3)
//                .setScaleForPopOutAnim(0f, 0f);
//        BounceView.addAnimTo(tv3)
//                //Default push in scalex: 0.9f , scaley: 0.9f
//                .setScaleForPushInAnim(BounceView.PUSH_IN_SCALE_X, BounceView.PUSH_IN_SCALE_Y)
//                //Default pop out scalex: 1.1f, scaley: 1.1f
//                .setScaleForPopOutAnim(BounceView.POP_OUT_SCALE_X, BounceView.POP_OUT_SCALE_Y)
//                //Default push in anim duration: 100 (in milliseconds)
//                .setPushInAnimDuration(BounceView.PUSH_IN_ANIM_DURATION)
//                //Default pop out anim duration: 100 (in milliseconds)
//                .setPopOutAnimDuration(BounceView.POP_OUT_ANIM_DURATION)
//                //Default interpolator: AccelerateDecelerateInterpolator()
//                .setInterpolatorPushIn(BounceView.DEFAULT_INTERPOLATOR)
//                .setInterpolatorPopOut(BounceView.DEFAULT_INTERPOLATOR);
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(BtnActivity.this, "点击了", Toast.LENGTH_LONG).show();
            }
        });
    }

    // 方法一
    private void executeAnimation1(View view) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.95f, 1.0f, 0.95f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(100);
        scaleAnimation.setRepeatCount(1);
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        view.startAnimation(scaleAnimation);
    }

    // 方法二
    private void executeAnimation2(View view) {
        Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.btn_onclickxg);
        view.startAnimation(scaleAnimation);
    }
}
