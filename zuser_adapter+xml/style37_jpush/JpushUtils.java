package com.haier.cellarette.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.haier.cellarette.service.StatusService;
import com.haier.wifi.util.WifiUtil;
import com.haier.wine_commen.base.ExampleUtil;
import com.haier.wine_commen.util.SP;

import java.lang.ref.WeakReference;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class JpushUtils {
    private final int MSG_SET_ALIAS = 1001;
    public static String jpushTag = "jpushTag";

    private MyHandler mHandler;
    private static JpushUtils jpushUtils;
    private Context mc;

    private JpushUtils(Context context) {
        mc = context;
        mHandler = new MyHandler(context);
    }

    public synchronized static JpushUtils getJpush(Context context) {
        if (jpushUtils == null) {
            jpushUtils = new JpushUtils(context);
        }
        return jpushUtils;
    }


    // 这是来自 JPush Example 的设置别名的 Activity 里的代码。一般 App 的设置的调用入口，在任何方便的地方调用都可以。
    public void setAlias(String alias) {
        if (TextUtils.isEmpty(alias)) {
            return;
        }
        if (!ExampleUtil.isValidTagAndAlias(alias)) {
            return;
        }
        if (SP.get(mc, jpushTag, false)) {
            return;
        }
        if (!WifiUtil.isNetworkConnected(mc)) {
            return;
        }
        // 调用 Handler 来异步设置别名
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
    }

    private class MyHandler extends Handler {
        private WeakReference<Context> reference;

        public MyHandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            StatusService activity = (StatusService) reference.get();
            if (activity != null) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case MSG_SET_ALIAS:
//                        Logger.d(activity.TAG, "Set alias in handler.");
                        // 调用 JPush 接口来设置别名。
                        JPushInterface.setAliasAndTags(activity.getApplicationContext(), (String) msg.obj, null, mAliasCallback);
                        break;
                    default:
//                        Logger.i(activity.TAG, "Unhandled msg - " + msg.what);
                }
            }
        }
    }


    /**
     * /**
     * TagAliasCallback类是JPush开发包jar中的类，用于
     * 设置别名和标签的回调接口，成功与否都会回调该方法
     * 同时给定回调的代码。如果code=0,说明别名设置成功。
     * /**
     * 6001   无效的设置，tag/alias 不应参数都为 null
     * 6002   设置超时    建议重试
     * 6003   alias 字符串不合法    有效的别名、标签组成：字母（区分大小写）、数字、下划线、汉字。
     * 6004   alias超长。最多 40个字节    中文 UTF-8 是 3 个字节
     * 6005   某一个 tag 字符串不合法  有效的别名、标签组成：字母（区分大小写）、数字、下划线、汉字。
     * 6006   某一个 tag 超长。一个 tag 最多 40个字节  中文 UTF-8 是 3 个字节
     * 6007   tags 数量超出限制。最多 100个 这是一台设备的限制。一个应用全局的标签数量无限制。
     * 6008   tag/alias 超出总长度限制。总长度最多 1K 字节
     * 6011   10s内设置tag或alias大于3次 短时间内操作过于频繁
     **/
    private TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    Log.e("setJpush;", 0 + "");
                    logs = "Set tag and alias success";
//                    Logger.i("alias-->", logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    SP.put(mc, jpushTag, true);
                    mHandler.removeCallbacksAndMessages(null);
                    break;
                case 6002:
                    Log.e("setJpush;", 6002 + "");
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
//                    Logger.i("alias-->", logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 0);
                    break;
                case 6014:
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 0);
                    break;
                default:
//                    logs = "Failed with errorCode = " + code;
//                    Logger.e(TAG, logs);
            }
        }
    };

    public void onDestory() {
        mHandler.removeCallbacksAndMessages(null);
    }
}
