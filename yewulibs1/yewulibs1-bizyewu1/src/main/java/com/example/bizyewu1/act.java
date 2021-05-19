package com.example.bizyewu1;

import android.text.TextUtils;

import com.fosung.lighthouse.test.BuildConfigApp;


public class act {

    public void test(){
        //版本判断
        if (TextUtils.equals(BuildConfigApp.versionNameConfig, "测试")) {

        } else if (TextUtils.equals(BuildConfigApp.versionNameConfig, "预生产")) {

        } else if (TextUtils.equals(BuildConfigApp.versionNameConfig, "线上")) {

        }
    }
}
