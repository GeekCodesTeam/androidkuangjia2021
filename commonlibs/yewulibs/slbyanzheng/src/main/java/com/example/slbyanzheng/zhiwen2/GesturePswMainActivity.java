package com.example.slbyanzheng.zhiwen2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.slbyanzheng.R;
import com.lib.lock.gesture.content.SPManager;


/**
 * 作者：xin on 2018/7/9 0009 15:03
 * <p>
 * 邮箱：ittfxin@126.com
 * <p>
 * https://github.com/wzx54321/XinFrameworkLib
 */

public class GesturePswMainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnPswSettings;
    private Button mBtnVerifyPsw;


    public static void Launcher(Context context) {

        Intent intent = new Intent(context, GesturePswMainActivity.class);

        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gesture_psw_main);

        mBtnPswSettings = findViewById(R.id.btn_settings_gesture_password);
        mBtnVerifyPsw = findViewById(R.id.btn_verify_gesture_password);

        mBtnPswSettings.setOnClickListener(this);
        mBtnVerifyPsw.setOnClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (hasPsw()) {
            mBtnVerifyPsw.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_settings_gesture_password) {
            if (hasPsw()) {
                GestureVerifyActivity.launch(this, true);
            } else {
                GestureSettingsActivity.launch(this);
            }
        } else if (id == R.id.btn_verify_gesture_password) {
            GestureVerifyActivity.launch(this, false);
        }
    }


    private boolean hasPsw() {
        return !TextUtils.isEmpty(SPManager.getInstance().getPatternPSW());
    }
}
