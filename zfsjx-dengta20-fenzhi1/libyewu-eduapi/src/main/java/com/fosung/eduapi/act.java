package com.example.bizyewu2;

import android.text.TextUtils;

import com.fosung.lighthouse.app.BuildConfigyewu;


public class act {

    public void test(){
        //版本判断
        if (TextUtils.equals(BuildConfigyewu.versionNameConfig, "测试")) {

        } else if (TextUtils.equals(BuildConfigyewu.versionNameConfig, "预生产")) {

        } else if (TextUtils.equals(BuildConfigyewu.versionNameConfig, "线上")) {

        }
    }
}
