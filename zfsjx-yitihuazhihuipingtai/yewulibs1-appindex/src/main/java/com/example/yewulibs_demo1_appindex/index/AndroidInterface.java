package com.example.yewulibs_demo1_appindex.index;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.geek.libutils.app.LocalBroadcastManagers;
import com.geek.libutils.app.MyLogUtil;
import com.geek.libutils.data.MmkvUtils;
import com.just.agentweb.AgentWeb;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;

public class AndroidInterface {
    private AgentWeb agent;
    private Context context;

    public AndroidInterface(AgentWeb agent, Context context) {
        this.agent = agent;
        this.context = context;//
    }

    @JavascriptInterface
    public String get_token() {
        return MmkvUtils.getInstance().get_common("token");

    }

    @JavascriptInterface
    public String get_loc() {
//        ToastUtils.showLong(MmkvUtils.getInstance().get_common("经纬度"));
        String bean = MmkvUtils.getInstance().get_common("经纬度");
        if (!TextUtils.isEmpty(bean) && bean.length() > 10) {
            MyLogUtil.e("LocUtil2", "拿到咯，那我就不管啦");
            MmkvUtils.getInstance().set_common("H5是否取得经纬度", "true");
        } else {
            MyLogUtil.e("LocUtil2", "没拿到，好吧，我记住了，等拿到了告诉你");
            MmkvUtils.getInstance().set_common("H5是否取得经纬度", "false");
        }
        return bean;
    }

    @JavascriptInterface
    public void set_shouye1_id1(String id) {
        Intent msgIntent = new Intent();
        msgIntent.putExtra("id1", id);
        msgIntent.setAction("ShouyeActivity");
        LocalBroadcastManagers.getInstance(context).sendBroadcast(msgIntent);
//        MmkvUtils.getInstance().set_common("tojs_id1", id);
        //
//        SendToFragment(id);
    }

    private void SendToFragment(String id1) {
        //举例
//        IndexFoodFragmentUpdateIds iff = new IndexFoodFragmentUpdateIds();
//        iff.setFood_definition_id(id1);
//        iff.setFood_name(id2);
        if (ActivityUtils.getTopActivity() != null && ActivityUtils.getTopActivity() instanceof ShouyeActivity) {
            ((ShouyeActivity) ActivityUtils.getTopActivity()).callFragment(id1, WebViewItem1.class.getName());
        }
    }

    @JavascriptInterface
    public String get_shouye1_id1() {
        return MmkvUtils.getInstance().get_common("tojs_id1");
    }

    @JavascriptInterface
    public void BackToAndroid1(final String str) {
        MyLogUtil.e("ssssssssssssssss", str);//pad
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showLong("调用了android方法,获取到js的值为" + str);
                new XPopup.Builder(context).asConfirm("我是标题", "我是内容",
                        new OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                ToastUtils.showLong("click confirm");
                            }
                        })
                        .show();
            }
        });
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                ToastUtils.showLong("调用了android方法,获取到js的值为" + str);
//            }
//        });
    }

    @JavascriptInterface
    public void BackToAndroid2(final String str) {
        MyLogUtil.e("ssssssssssssssss", str);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showLong("调用了android方法,获取到js的值为" + str);
            }
        });
    }

    @JavascriptInterface
    public void BackToAndroid3(final String str) {
        MyLogUtil.e("ssssssssssssssss", str);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showLong("调用了android方法,获取到js的值为" + str);
            }
        });
    }

    @JavascriptInterface
    public void BackToAndroid4(final String str) {
        MyLogUtil.e("ssssssssssssssss", str);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showLong("调用了android方法,获取到js的值为" + str);
            }
        });
    }

    @JavascriptInterface
    public void BackToAndroid5(final String str) {
        MyLogUtil.e("ssssssssssssssss", str);
        Intent msgIntent = new Intent();
        msgIntent.putExtra("h5", str);
        msgIntent.setAction("ShouyeActivity");
        LocalBroadcastManagers.getInstance(context).sendBroadcast(msgIntent);
//        new Handler(Looper.getMainLooper()).post(new Runnable() {
//            @Override
//            public void run() {
//                ToastUtils.showLong("调用了android方法,获取到js的值为" + str);
//            }
//        });
    }

    @JavascriptInterface
    public void BackToAndroid5(final String str, final String color) {
        MyLogUtil.e("ssssssssssssssss", str);
        Intent msgIntent = new Intent();
        msgIntent.putExtra("h5", str);
        msgIntent.putExtra("color", color);
        msgIntent.setAction("ShouyeActivity");
        LocalBroadcastManagers.getInstance(context).sendBroadcast(msgIntent);
//        new Handler(Looper.getMainLooper()).post(new Runnable() {
//            @Override
//            public void run() {
//                ToastUtils.showLong("调用了android方法,获取到js的值为" + str);
//            }
//        });
    }
}