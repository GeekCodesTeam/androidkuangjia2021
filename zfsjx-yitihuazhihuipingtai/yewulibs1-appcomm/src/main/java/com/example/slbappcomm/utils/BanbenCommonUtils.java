package com.example.slbappcomm.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.SPUtils;
import com.fosung.lighthouse.test.BuildConfigApp;
import com.fosung.lighthouse.test.UrlManager;
import com.haier.cellarette.baselibrary.zothers.StartHiddenManager;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;

public class BanbenCommonUtils {
    //    public static String banben_comm = VersionUtils.banben;
//    public static String dizhi1_comm = VersionUtils.dizhi1;
//    public static String dizhi2_comm = VersionUtils.dizhi2;
    public static String banben_comm = BuildConfigApp.versionNameConfig;
    public static String dizhi1_comm = BuildConfigApp.SERVER_ISERVICE_NEW1;
    public static String dizhi2_comm = BuildConfigApp.SERVER_ISERVICE_NEW2;

    public static StartHiddenManager startHiddenManager;

    //
    public static void changeUrl(final Activity activity, View left, View right, String intent) {
        String aaa = "https";
        if (TextUtils.equals(BanbenCommonUtils.banben_comm, "测试")) {
            aaa = "https://test1";
        } else if (TextUtils.equals(BanbenCommonUtils.banben_comm, "预生产")) {
            aaa = "https://yushengchan1";
        } else if (TextUtils.equals(BanbenCommonUtils.banben_comm, "线上")) {
            aaa = "https://xianshang1";
        }
        final String finalAaa = aaa;
        startHiddenManager = new StartHiddenManager(left, right, intent, new StartHiddenManager.OnClickFinish() {
            @Override
            public void onFinish() {
                new XPopup.Builder(activity)
                        //.dismissOnBackPressed(false)
                        .dismissOnTouchOutside(true) //对于只使用一次的弹窗，推荐设置这个
                        .autoOpenSoftInput(true)
//                        .autoFocusEditText(false) //是否让弹窗内的EditText自动获取焦点，默认是true
                        .isRequestFocus(false)
                        //.moveUpToKeyboard(false)   //是否移动到软键盘上面，默认为true
                        .asInputConfirm("修改地址", SPUtils.getInstance().getString(UrlManager.default_url1key, finalAaa), null, "",
                                new OnInputConfirmListener() {
                                    @Override
                                    public void onConfirm(String text) {
//                                        toast("input text: " + text);
                                        if (text.contains("http")) {
                                            String[] content = text.split(",");
                                            SPUtils.getInstance().put(UrlManager.default_url1key, content[0]);
                                            SPUtils.getInstance().put(UrlManager.default_url2key, content[1]);
                                            BuildConfigApp.SERVER_ISERVICE_NEW1 = content[0];
                                            BuildConfigApp.SERVER_ISERVICE_NEW2 = content[1];
                                            BanbenCommonUtils.dizhi1_comm = content[0];
                                            BanbenCommonUtils.dizhi2_comm = content[1];
                                        }
//                                new XPopup.Builder(getContext()).asLoading().show();
                                    }
                                })
                        .show();
            }
        });
    }

    public static void changeUrl_ondes() {
        startHiddenManager.onDestory();
    }


    public static void changeUrl2(Activity activity) {
        new XPopup.Builder(activity)
                //.dismissOnBackPressed(false)
                .dismissOnTouchOutside(true) //对于只使用一次的弹窗，推荐设置这个
                .autoOpenSoftInput(true)
//                        .autoFocusEditText(false) //是否让弹窗内的EditText自动获取焦点，默认是true
                .isRequestFocus(false)
                //.moveUpToKeyboard(false)   //是否移动到软键盘上面，默认为true
                .asInputConfirm("修改地址", "地址格式为：" + dizhi1_comm, null, "",
                        new OnInputConfirmListener() {
                            @Override
                            public void onConfirm(String text) {
//                                        toast("input text: " + text);
                                if (text.contains("http")) {
//                                            String[] content = text.split(",");
                                    SPUtils.getInstance().put("版本地址1", text);
                                    BuildConfigApp.SERVER_ISERVICE_NEW1 = text;
                                    BanbenCommonUtils.dizhi1_comm = text;

                                }
//                                new XPopup.Builder(getContext()).asLoading().show();
                            }
                        })
                .show();
    }

}