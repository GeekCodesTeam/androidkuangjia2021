package com.example.slbapplogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.example.libbase.base.SlbBaseActivity;
import com.geek.libutils.SlbLoginUtil;


public class LoginMainActivity extends SlbBaseActivity {

    private TextView tv1;


    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void setup(@Nullable Bundle savedInstanceState) {
        super.setup(savedInstanceState);
        tv1 = findViewById(R.id.tv1);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SlbLoginUtil.get().loginTowhere(LoginMainActivity.this,  new Runnable() {
                    @Override
                    public void run() {
                        //登录to
//                        ToastUtil.showToastCenter("可以跳转了~");
                        tv1.setText("更新UI~");
//                        startActivity(new Intent(LoginMainActivity.this, MainActivity2.class));
                    }
                });
            }
        });
        findViewById(R.id.tv2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SlbLoginUtil.get().loginOutTowhere(LoginMainActivity.this,  new Runnable() {
                    @Override
                    public void run() {
                        //退出登录to
                        //登录to
//                        ToastUtil.showToastCenter("可以跳转了~");
                        tv1.setText("login");
//                        startActivity(new Intent(LoginMainActivity.this, MainActivity3.class));
                    }
                });
            }
        });
    }

    @Override
    protected void onActResult(int requestCode, int resultCode, Intent data) {
        super.onActResult(requestCode, resultCode, data);
        if (SlbLoginUtil.get().login_activity_result(requestCode, resultCode, data)) {
////            if (LoginUtil.get().isUserLogin()) {
////                onUserLogined(LoginUtil.get().userId());
////            } else {
////                onUserLoginCanceled();
////            }
//            return;
        }
    }
}
