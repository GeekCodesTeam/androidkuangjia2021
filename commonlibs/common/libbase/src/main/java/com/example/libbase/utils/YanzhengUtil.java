package com.example.libbase.utils;

import android.app.Activity;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.libbase.R;
import com.geek.libutils.app.BaseApp;
import com.google.android.material.textfield.TextInputLayout;

public class YanzhengUtil {


    public static boolean isPhone(String phone, TextView view) {
        if (TextUtils.isEmpty(phone)) {
            view.setText("手机号不能为空");
            return false;
        }
        if (!TextUtils.isDigitsOnly(phone)) {
            view.setText("手机号格式错误，仅支持纯数字");
            ToastUtils.showLong("手机号格式错误，仅支持纯数字");

            return false;
        }
        if (phone.length() != 11) {
            view.setText("手机号格式错误，应为11位纯数字");
            return false;
        }
        return true;
    }

    /**
     * 设置眼睛显隐bufen
     *
     * @param edt
     * @param ivEyes
     */
    public static void set_mima_vis(EditText edt, ImageView ivEyes) {
        TransformationMethod type = edt.getTransformationMethod();
        if (PasswordTransformationMethod.getInstance().equals(type)) {
            edt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            edt.setSelection(edt.getText().toString().trim().length());
            ivEyes.setImageResource(R.drawable.eyes_icon_open);
        } else {
            edt.setTransformationMethod(PasswordTransformationMethod.getInstance());
            edt.setSelection(edt.getText().toString().trim().length());
//                   edPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            ivEyes.setImageResource(R.drawable.eyes_icon_close);
        }
    }


    /**
     * 倒计时控件
     */
    private static CountDownTimer timer;

    /**
     * 从x开始倒计时
     *
     * @param x
     */
    public static void startTime(long x, final Button btnHqyzm) {
        if (timer != null) {
            timer.cancel();
        }
        timer = new CountDownTimer(x, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int remainTime = (int) (millisUntilFinished / 1000L);
                btnHqyzm.setEnabled(false);
                btnHqyzm.setBackgroundResource(R.drawable.common_btn_bg2);
                btnHqyzm.setText(BaseApp.get().getResources().getString(R.string.yhzc_tip2, remainTime));
                btnHqyzm.setTextColor(ContextCompat.getColor(BaseApp.get(), R.color.color999999));
            }

            @Override
            public void onFinish() {
                btnHqyzm.setEnabled(true);
                btnHqyzm.setBackgroundResource(R.drawable.common_btn_bg1);
                btnHqyzm.setText(BaseApp.get().getResources().getString(R.string.yhzc_tip1));
                btnHqyzm.setTextColor(ContextCompat.getColor(BaseApp.get(), R.color.color4DA3FE));
            }
        };
        timer.start();
    }

    public static void timer_des() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    /**
     * 显示错误提示，并获取焦点
     *
     * @param textInputLayout
     * @param error
     */
    public static void showError(TextInputLayout textInputLayout, String error) {
        textInputLayout.setError(error);
        textInputLayout.getEditText().setFocusable(true);
        textInputLayout.getEditText().setFocusableInTouchMode(true);
        textInputLayout.getEditText().requestFocus();
    }

    /**
     * 验证用户名
     *
     * @param account
     * @return
     */
    public static boolean validateAccount(TextInputLayout til_account, String account, String content) {
        if (StringUtils.isEmpty(account)) {
            showError(til_account, content);// "用户名不能为空"
            return false;
        }
        return true;
    }

    /**
     * 验证密码
     *
     * @param password
     * @return
     */
    public static boolean validatePassword(TextInputLayout til_password, String password, String content) {
        if (StringUtils.isEmpty(password)) {
            showError(til_password, content);// "密码不能为空"
            return false;
        }

//        if (password.length() < 6 || password.length() > 18) {
//            showError(til_password, "密码长度为6-18位");
//            return false;
//        }

        return true;
    }

}
