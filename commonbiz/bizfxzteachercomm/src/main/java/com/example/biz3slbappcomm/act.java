package com.example.biz3slbappcomm;

import android.text.TextUtils;

import com.sdzn.fzx.teacher.BuildConfig3;

public class act {

    public void test(){
        //版本判断
        if (TextUtils.equals(BuildConfig3.versionNameConfig, "测试")) {

        } else if (TextUtils.equals(BuildConfig3.versionNameConfig, "预生产")) {

        } else if (TextUtils.equals(BuildConfig3.versionNameConfig, "线上")) {

        }
    }
}
