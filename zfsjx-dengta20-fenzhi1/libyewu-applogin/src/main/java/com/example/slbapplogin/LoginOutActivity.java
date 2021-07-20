package com.example.slbapplogin;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SPUtils;
import com.example.libbase.base.SlbBaseActivity;
import com.geek.libutils.SlbLoginUtil;

public class LoginOutActivity extends SlbBaseActivity {

    private LinearLayout btn_cancel;
    private LinearLayout btn_ok;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_slbloginout;
    }

    @Override
    protected void setup(@Nullable Bundle savedInstanceState) {
        super.setup(savedInstanceState);
        findview();
        onclick();
        donetwork();
    }


    private void donetwork() {

    }

    private void onclick() {
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLoginCanceled();
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                donetloginout();
            }

        });
    }

    private void onLoginSuccess() {
        setResult(SlbLoginUtil.LOGINOUT_RESULT_OK);
        finish();
    }

    private void onLoginCanceled() {
        setResult(SlbLoginUtil.LOGINOUT_RESULT_CANCELED);
        finish();
    }

    /**
     * 登出操作
     */
    private void donetloginout() {
        //step 请求服务器成功后清除sp中的数据
        SPUtils.getInstance().getString("", "");
        onLoginSuccess();
    }

    private void findview() {
        btn_ok = findViewById(R.id.ll_ok);
        btn_cancel = findViewById(R.id.ll_cancel);
    }

    @Override
    public void finish() {
        super.finish();
//        overridePendingTransition(R.anim.push_bottom_in, R.anim.push_bottom_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideSoftKeyboard();
    }

    /**
     * 隐藏软键盘
     */
    protected void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
