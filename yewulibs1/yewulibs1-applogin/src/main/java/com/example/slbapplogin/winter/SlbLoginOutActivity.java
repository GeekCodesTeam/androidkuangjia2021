package com.example.slbapplogin.winter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.blankj.utilcode.util.ToastUtils;
import com.example.bizyewu2.presenter.HTuichudengluPresenter;
import com.example.bizyewu2.view.HTuichudengluView;
import com.example.libbase.base.SlbBaseActivity;
import com.example.slbappcomm.utils.CommonUtils;
import com.example.slbapplogin.R;
import com.geek.libutils.SlbLoginUtil;
import com.geek.libutils.data.MmkvUtils;

public class SlbLoginOutActivity extends SlbBaseActivity implements HTuichudengluView {

    private LinearLayout ll_cancel;
    private LinearLayout ll_ok;
    private HTuichudengluPresenter hTuichudengluPresenter;

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
        hTuichudengluPresenter = new HTuichudengluPresenter();
        hTuichudengluPresenter.onCreate(this);

    }

    private void onclick() {
        ll_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLoginCanceled();
            }
        });
        ll_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hTuichudengluPresenter.get_tuichudenglu();

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
        MmkvUtils.getInstance().remove_common(CommonUtils.MMKV_SEX);
        MmkvUtils.getInstance().remove_common(CommonUtils.MMKV_IMG);
        MmkvUtils.getInstance().remove_common(CommonUtils.MMKV_TEL);
        MmkvUtils.getInstance().remove_common(CommonUtils.MMKV_NAME);
        MmkvUtils.getInstance().remove_common(CommonUtils.MMKV_forceLogin);
        MmkvUtils.getInstance().remove_common(CommonUtils.MMKV_TOKEN);
        onLoginSuccess();
    }

    private void findview() {
        ll_ok = findViewById(R.id.ll_ok);
        ll_cancel = findViewById(R.id.ll_cancel);
    }

    @Override
    public void finish() {
        super.finish();
//        overridePendingTransition(R.anim.push_bottom_in, R.anim.push_bottom_out);
    }

    @Override
    protected void onDestroy() {
        hideSoftKeyboard();
        hTuichudengluPresenter.onDestory();
        super.onDestroy();
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

    // 退出登录bufen
    @Override
    public void OnTuichudengluSuccess(String s) {
        ToastUtils.showLong(s);
        donetloginout();

    }

    @Override
    public void OnTuichudengluNodata(String s) {
        ToastUtils.showLong(s);
        donetloginout();

    }

    @Override
    public void OnTuichudengluFail(String s) {
        ToastUtils.showLong(s);
        donetloginout();
    }
}
