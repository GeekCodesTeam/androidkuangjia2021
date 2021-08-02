package net.ossrs.yasea.activity;

import android.content.Intent;
import android.media.projection.MediaProjection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import net.ossrs.yasea.R;
import net.ossrs.yasea.SimplePublisher;
import net.ossrs.yasea.SimpleRtmpListener;
import net.ossrs.yasea.utils.ScreenApp;
import net.ossrs.yasea.utils.ServiceUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ScreenConfigActivity extends AppCompatActivity {

    private TextView tv;
    private String myUrl = "";
    private String rtmpUrl = "";
    public static final int REQUEST_MEDIA_PROJECTION_SCREEN = 1002;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_screen);
        tv = findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SimplePublisher.getInstance().isRecording()) {
                    SimplePublisher.getInstance().stop();
                    ServiceUtil.getInstance().mMediaProjection = null;
                    stopService(new Intent(ScreenConfigActivity.this, FadeService.class));
                    // 这里可以用通知来做业务
                    ToastUtils.showLong("结束投屏");
                }
                finish();
            }
        });
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        if (appLinkIntent != null) {
            String appLinkAction = appLinkIntent.getAction();
            if (appLinkAction != null) {
                Uri appLinkData = appLinkIntent.getData();
                if (appLinkData != null) {
                    ToastUtils.showLong("进入ScreenConfigActivity成功");
                }
            }
        }
        vivView();

    }

    private void vivView() {
        if (SimplePublisher.getInstance().isRecording()) {
            SimplePublisher.getInstance().stop();
            ServiceUtil.getInstance().mMediaProjection = null;
            stopService(new Intent(ScreenConfigActivity.this, FadeService.class));
            // 这里可以用通知来做业务
            ToastUtils.showLong("结束投屏");
//            OkHttpUtils.post().url(myUrl + "/RtmpStop?")
//                    .build()
//                    .execute(new StringCallback() {
//                        @Override
//                        public void onError(Call call, Exception e, int id) {
//                            EventBus.getDefault().post(Event.EVENT_OPEN_SCREEN);
//                            Toast.makeText(App2.get(), "结束投屏", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onResponse(String response, int id) {
//                            if (response != null) {//地址
//                                EventBus.getDefault().post(Event.EVENT_OPEN_SCREEN);
//                                Toast.makeText(App2.get(), "结束投屏", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
            finish();
        } else {
            ServiceUtil.getInstance().set_start_screen(ScreenConfigActivity.this);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (intentResult != null) {
                if (intentResult.getContents() == null) {
                    Toast.makeText(ScreenApp.get(), "内容为空", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ScreenApp.get(), "扫描成功", Toast.LENGTH_LONG).show();
                    Log.e("eeeee--url", "--" + intentResult.getContents());
                    // ScanResult 为 获取到的字符串
                    String url = intentResult.getContents();
//                        Gson gson = new Gson();
//                        Test test = gson.fromJson(url, Test.class);
//                        Log.e("eeeee--url", "--" + url);
                    // 如果需要根据电脑分辨率变更投屏大小，可以把下面两行代码放开
//                        Config.currHeight = Integer.parseInt(test.getWeight());
//                        Config.currWidth = Integer.parseInt(test.getHeight());
                    myUrl = url;
                    rtmpUrl = myUrl + "/RtmpAddr?";
//                    http://o-pull.xczx-jn.com/live/test2/playlist.m3u8 观看录屏地址
                    rtmpUrl = "rtmp://o-push.xczx-jn.com/live/test2";
                    //
                    if (ServiceUtil.getInstance().mMediaProjection == null) {
                        ScreenConfigActivity.this.startActivityForResult(
                                ServiceUtil.getInstance().mMediaProjectionManager.createScreenCaptureIntent(),
                                REQUEST_MEDIA_PROJECTION_SCREEN);
                        Log.e("eeeee--", "投屏--mMediaProjection == null");
                    } else {
//                            EventBus.getDefault().post(Event.EVENT_CLOSE_SCREEN);
                        SimplePublisher.getInstance().start(ScreenConfigActivity.this, rtmpUrl, false, listener, ServiceUtil.getInstance().mMediaProjection);
                        Log.e("eeeee--", "投屏--Event.EVENT_CLOSE_SCREEN");
                    }
                }
            }
        } else if (requestCode == REQUEST_MEDIA_PROJECTION_SCREEN) {
            Log.e("eeeeerequestCode--", "REQUEST_MEDIA_PROJECTION_SCREEN");
            // 必须在services中运行api 29
            Intent service = new Intent(this, FadeService.class);
            service.putExtra("code", resultCode);
            service.putExtra("data", data);
            startForegroundService(service);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void updateUI(MediaProjection mMediaProjection) {
        if (mMediaProjection != null) {
//                EventBus.getDefault().post(Event.EVENT_CLOSE_SCREEN);
            Log.e("eeeeerequestCode--", "进入投屏lib");
            SimplePublisher.getInstance().start(ScreenConfigActivity.this, rtmpUrl, false, listener, mMediaProjection);
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        super.onResume();
    }

    SimpleRtmpListener listener = new SimpleRtmpListener() {

        @Override
        public void onRtmpConnecting(String msg) {
//            mButton.setText("重连中");
            Log.e("eeeeelistener--", "重连中Connecting" + msg);
        }

        @Override
        public void onRtmpConnected(String msg) {
            Log.e("eeeeelistener--", "录制中RtmpConnected" + msg);
//            mButton.setText("录制中");
            //        OkHttpUtils.post().url(myUrl + "/RtmpPlay?")
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        ToastUtil.showShortlToast("投屏链接失败");
//                        com.sdzn.fzx.student.libutils.util.Log.e("eeeee_T_RtmpPlay", "---投屏链接失败");
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        com.sdzn.fzx.student.libutils.util.Log.e("eeeee_T_RtmpPlay", "---");
//                        if (response != null) {//地址
////                            ToastUtil.showShortlToast("RtmpPlay---");
//
//                        }
//                    }
//                });
            finish();
        }

        @Override
        public void onRtmpStopped() {
            Log.e("eeeee_TAG_RTMP", "onRtmpStopped");
        }

        @Override
        public void onRtmpDisconnected() {
            Log.e("eeeee_TAG_RTMP", "链接onRtmpDisconnected");
        }

        @Override
        public void onRtmpException(Exception e) {
            Log.e("eeeee_TAG_RTMP", "链接断开");
        }
    };

}
