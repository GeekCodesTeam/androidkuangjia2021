package com.example.slbappcomm;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.SPUtils;
import com.example.slbappcomm.utils.BanbenCommonUtils;
import com.fosung.lighthouse.test.BuildConfigApp;
import com.geek.libutils.app.MyLogUtil;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ActDemo extends AppCompatActivity /*implements CheckverionView*/ {

    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;  //定时器任务
    private Handler mHandler;
    private HandlerThread mHandlerThread;
//    private CheckverionPresenter checkverionPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_main);
        findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                startActivity(new Intent("hs.act.slbapp.VideoPlayActivity"));
            }
        });
//        checkverionPresenter = new CheckverionPresenter();
//        checkverionPresenter.onCreate(this);
//        checkverionPresenter.checkVerion("1");
        //
        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
        scheduledThreadPoolExecutor.scheduleAtFixedRate(timerRunnable, 0, 2000, TimeUnit.SECONDS);
        mHandlerThread = new HandlerThread("myHandlerThread");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //这个方法是运行在 handler-thread 线程中的 ，可以执行耗时操作 更新UI
                Log.d("handler ", "消息： " + msg.what + "  线程： " + Thread.currentThread().getName());

            }
        };
        //
        MyLogUtil.e("版本--old---", BanbenCommonUtils.dizhi1_comm);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
//                ToastUtils.showLong(",地址2：" + BanbenCommonUtils.dizhi1_comm + ",地址2：" + BanbenCommonUtils.dizhi2_comm);
                test();
//                checkverionPresenter.checkVerion("1");

            }
        }, 5000);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void test() {
        //
//        StartHiddenManager startHiddenManager = new StartHiddenManager(null, null, null, new StartHiddenManager.OnClickFinish() {
//            @Override
//            public void onFinish() {
//                new XPopup.Builder(ActDemo.this)
//                        //.dismissOnBackPressed(false)
//                        .dismissOnTouchOutside(true) //对于只使用一次的弹窗，推荐设置这个
//                        .autoOpenSoftInput(true)
////                        .autoFocusEditText(false) //是否让弹窗内的EditText自动获取焦点，默认是true
//                        .isRequestFocus(false)
//                        //.moveUpToKeyboard(false)   //是否移动到软键盘上面，默认为true
//                        .asInputConfirm("修改地址", SPUtils.getInstance().getString("版本地址2", "https://www.baidu.com"), null, "",
//                                new OnInputConfirmListener() {
//                                    @Override
//                                    public void onConfirm(String text) {
////                                        toast("input text: " + text);
//                                        if (text.contains("http")) {
//                                            String[] content = text.split(",");
//                                            SPUtils.getInstance().put("版本地址1", content[0]);
//                                            SPUtils.getInstance().put("版本地址2", content[1]);
//                                            BuildConfigApp.SERVER_ISERVICE_NEW1 = content[0];
//                                            BuildConfigApp.SERVER_ISERVICE_NEW2 = content[1];
//                                        }
////                                new XPopup.Builder(getContext()).asLoading().show();
//                                    }
//                                })
//                        .show();
//            }
//        });
        //版本判断
        if (TextUtils.equals(BanbenCommonUtils.banben_comm, "测试")) {
            SPUtils.getInstance().put("版本地址1", "https://testnew");
            BuildConfigApp.SERVER_ISERVICE_NEW1 = "https://testnew";
            BanbenCommonUtils.dizhi1_comm = "https://testnew";
        } else if (TextUtils.equals(BanbenCommonUtils.banben_comm, "预生产")) {
            SPUtils.getInstance().put("版本地址1", "https://yushengchannew");
            BuildConfigApp.SERVER_ISERVICE_NEW1 = "https://yushengchannew";
            BanbenCommonUtils.dizhi1_comm = "https://yushengchannew";
        } else if (TextUtils.equals(BanbenCommonUtils.banben_comm, "线上")) {
            SPUtils.getInstance().put("版本地址1", "https://xianshangnew");
            BuildConfigApp.SERVER_ISERVICE_NEW1 = "https://xianshangnew";
            BanbenCommonUtils.dizhi1_comm = "https://xianshangnew";
        }
        MyLogUtil.e("版本--new---", BuildConfigApp.SERVER_ISERVICE_NEW1);
        MyLogUtil.e("版本--new---", BanbenCommonUtils.dizhi1_comm);
    }

    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            // 业务逻辑 拿到结果发送消息队列
            mHandler.sendEmptyMessage(111);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (scheduledThreadPoolExecutor != null) {
            scheduledThreadPoolExecutor.shutdown();
        }
        mHandlerThread.quit();
    }

//    @Override
//    public void OnUpdateVersionSuccess(VersionInfoBean versionInfoBean) {
//        ToastUtils.showLong("网络请求进来了");
//    }
//
//    @Override
//    public void OnUpdateVersionNodata(String bean) {
//        ToastUtils.showLong("网络请求进来了");
//    }
//
//    @Override
//    public void OnUpdateVersionFail(String msg) {
//        ToastUtils.showLong(msg);
//        MyLogUtil.e("版本ssss", BuildConfigApp.SERVER_ISERVICE_NEW1);
//    }
//
//    @Override
//    public String getIdentifier() {
//        return null;
//    }
}
