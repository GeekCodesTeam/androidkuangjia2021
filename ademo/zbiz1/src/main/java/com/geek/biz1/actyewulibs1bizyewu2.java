package com.geek.biz1;

import android.text.TextUtils;

import com.github.geekcodesteam.app.BuildConfigyewu;

public class actyewulibs1bizyewu2 {

    public void test() {
        //版本判断
        if (TextUtils.equals(BuildConfigyewu.versionNameConfig, "测试")) {

        } else if (TextUtils.equals(BuildConfigyewu.versionNameConfig, "预生产")) {

        } else if (TextUtils.equals(BuildConfigyewu.versionNameConfig, "线上")) {

        }
    }
}
