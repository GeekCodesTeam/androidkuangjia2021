package com.example.gsyvideoplayer;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gsyvideoplayer.exosource.GSYExoHttpDataSourceFactory;
import com.example.gsyvideoplayer.simple.SimpleActivity;
import com.example.gsyvideoplayer.utils.JumpUtils;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.cache.ProxyCacheManager;
import com.shuyu.gsyvideoplayer.player.IjkPlayerManager;
import com.shuyu.gsyvideoplayer.player.PlayerFactory;
import com.shuyu.gsyvideoplayer.player.SystemPlayerManager;
import com.shuyu.gsyvideoplayer.utils.Debuger;

import java.io.File;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import permissions.dispatcher.PermissionUtils;
import tv.danmaku.ijk.media.exo2.Exo2PlayerManager;
import tv.danmaku.ijk.media.exo2.ExoMediaSourceInterceptListener;
import tv.danmaku.ijk.media.exo2.ExoSourceManager;

public class GSYMainActivity extends AppCompatActivity implements View.OnClickListener {

    Button openBtn;
    Button openBtn2;
    Button changeCore;
    Button list_btn;
    Button list_btn_2;
    Button list_detail;
    Button clear_cache;
    Button recycler;
    Button recycler_2;
    Button list_detail_list;
    Button web_detail;
    Button danmaku_video;
    Button fragment_video;
    Button more_type;
    Button input_type;
    Button open_control;
    Button open_filter;
    Button open_btn_pick;
    Button open_btn_auto;
    Button open_scroll;
    Button open_window;
    Button open_btn_ad;
    Button open_btn_multi;
    Button open_btn_ad2;
    Button open_list_ad;
    Button open_custom_exo;
    Button open_simple;
    Button open_switch;
    Button media_codec;
    Button detail_normal_activity;
    Button detail_download_activity;
    Button detail_audio_activity;
    Button detail_subtitle_activity;
    Button view_pager2_activity;

    final String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gsymain);
        openBtn = findViewById(R.id.open_btn1);
        openBtn2 = findViewById(R.id.open_btn_empty);
        changeCore = findViewById(R.id.change_core);
        list_btn = findViewById(R.id.list_btn);
        list_btn_2 = findViewById(R.id.list_btn_2);
        list_detail = findViewById(R.id.list_detail);
        clear_cache = findViewById(R.id.clear_cache);
        recycler = findViewById(R.id.recycler);
        recycler_2 = findViewById(R.id.recycler_2);
        list_detail_list = findViewById(R.id.list_detail_list);
        web_detail = findViewById(R.id.web_detail);
        danmaku_video = findViewById(R.id.danmaku_video);
        fragment_video = findViewById(R.id.fragment_video);
        more_type = findViewById(R.id.more_type);
        input_type = findViewById(R.id.input_type);
        open_control = findViewById(R.id.open_control);
        open_filter = findViewById(R.id.open_filter);
        open_btn_pick = findViewById(R.id.open_btn_pick);
        open_btn_auto = findViewById(R.id.open_btn_auto);
        open_scroll = findViewById(R.id.open_scroll);
        open_window = findViewById(R.id.open_window);
        open_btn_ad = findViewById(R.id.open_btn_ad);
        open_btn_multi = findViewById(R.id.open_btn_multi);
        open_btn_ad2 = findViewById(R.id.open_btn_ad2);
        open_list_ad = findViewById(R.id.open_list_ad);
        open_custom_exo = findViewById(R.id.open_custom_exo);
        open_simple = findViewById(R.id.open_simple);
        open_switch = findViewById(R.id.open_switch);
        media_codec = findViewById(R.id.media_codec);
        detail_normal_activity = findViewById(R.id.detail_normal_activity);
        detail_download_activity = findViewById(R.id.detail_download_activity);
        detail_audio_activity = findViewById(R.id.detail_audio_activity);
        detail_subtitle_activity = findViewById(R.id.detail_subtitle_activity);
        view_pager2_activity = findViewById(R.id.view_pager2_activity);
        openBtn.setOnClickListener(this);
        list_btn.setOnClickListener(this);
        list_btn_2.setOnClickListener(this);
        list_detail.setOnClickListener(this);
        clear_cache.setOnClickListener(this);
        recycler.setOnClickListener(this);
        recycler_2.setOnClickListener(this);
        list_detail_list.setOnClickListener(this);
        web_detail.setOnClickListener(this);
        danmaku_video.setOnClickListener(this);
        fragment_video.setOnClickListener(this);
        more_type.setOnClickListener(this);
        input_type.setOnClickListener(this);
        openBtn2.setOnClickListener(this);
        open_control.setOnClickListener(this);
        open_filter.setOnClickListener(this);
        open_btn_pick.setOnClickListener(this);
        open_btn_auto.setOnClickListener(this);
        open_scroll.setOnClickListener(this);
        open_window.setOnClickListener(this);
        open_btn_ad.setOnClickListener(this);
        open_btn_multi.setOnClickListener(this);
        open_btn_ad2.setOnClickListener(this);
        open_list_ad.setOnClickListener(this);
        open_custom_exo.setOnClickListener(this);
        open_simple.setOnClickListener(this);
        open_switch.setOnClickListener(this);
        media_codec.setOnClickListener(this);
        detail_normal_activity.setOnClickListener(this);
        detail_download_activity.setOnClickListener(this);
        detail_audio_activity.setOnClickListener(this);
        detail_subtitle_activity.setOnClickListener(this);
        changeCore.setOnClickListener(this);
        view_pager2_activity.setOnClickListener(this);
        Debuger.enable();

        boolean hadPermission = PermissionUtils.hasSelfPermissions(this, permissions);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !hadPermission) {
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            requestPermissions(permissions, 1110);
        }
        // GSY applicationbufen
//        ExoSourceManager.setExoMediaSourceInterceptListener(new ExoMediaSourceInterceptListener() {
//            @Override
//            public MediaSource getMediaSource(String dataSource, boolean preview, boolean cacheEnable, boolean isLooping, File cacheDir) {
//                //如果返回 null，就使用默认的
//                return null;
//            }
//
//            /**
//             * 通过自定义的 HttpDataSource ，可以设置自签证书或者忽略证书
//             * demo 里的 GSYExoHttpDataSourceFactory 使用的是忽略证书
//             * */
//            @Override
//            public HttpDataSource.BaseFactory getHttpDataSourceFactory(String userAgent, @Nullable TransferListener listener, int connectTimeoutMillis, int readTimeoutMillis, boolean allowCrossProtocolRedirects) {
//                //如果返回 null，就使用默认的
//                return new GSYExoHttpDataSourceFactory(userAgent, listener,
//                        connectTimeoutMillis,
//                        readTimeoutMillis, allowCrossProtocolRedirects);
//            }
//        });
        // 代理缓存bufen
//        ProxyCacheManager.instance().setHostnameVerifier(new HostnameVerifier() {
//            @Override
//            public boolean verify(String hostname, SSLSession session) {
//                return true;
//            }
//        });
//        final TrustManager[] trustAllCerts = new TrustManager[]{
//                new X509TrustManager() {
//                    @Override
//                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
//                    }
//
//                    @Override
//                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
//                    }
//
//                    @Override
//                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                        return null;
//                    }
//                }
//        };
//        ProxyCacheManager.instance().setTrustAllCerts(trustAllCerts);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean sdPermissionResult = PermissionUtils.verifyPermissions(grantResults);
        if (!sdPermissionResult) {
            Toast.makeText(this, "没获取到sd卡权限，无法播放本地视频哦", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.open_simple) {//简单的播放
            startActivity(new Intent(this, SimpleActivity.class));
        } else if (id == R.id.open_btn1) {//直接一个页面播放的
            JumpUtils.goToVideoPlayer(this, openBtn);
        } else if (id == R.id.list_btn) {//普通列表播放，只支持全屏，但是不支持屏幕重力旋转，滑动后不持有
            JumpUtils.goToVideoPlayer(this);
        } else if (id == R.id.list_btn_2) {//支持全屏重力旋转的列表播放，滑动后不会被销毁
            JumpUtils.goToVideoPlayer2(this);
        } else if (id == R.id.recycler) {//recycler的demo
            JumpUtils.goToVideoRecyclerPlayer(this);
        } else if (id == R.id.recycler_2) {//recycler的demo
            JumpUtils.goToVideoRecyclerPlayer2(this);
        } else if (id == R.id.list_detail) {//支持旋转全屏的详情模式
            JumpUtils.goToDetailPlayer(this);
        } else if (id == R.id.list_detail_list) {//播放一个连续列表
            JumpUtils.goToDetailListPlayer(this);
        } else if (id == R.id.web_detail) {//正常播放，带preview
            JumpUtils.gotoWebDetail(this);
        } else if (id == R.id.danmaku_video) {//播放一个弹幕视频
            JumpUtils.gotoDanmaku(this);
        } else if (id == R.id.fragment_video) {//播放一个弹幕视频
            JumpUtils.gotoFragment(this);
        } else if (id == R.id.more_type) {//跳到多类型详情播放器，比如切换分辨率，旋转等
            JumpUtils.gotoMoreType(this);
        } else if (id == R.id.input_type) {
            JumpUtils.gotoInput(this);
        } else if (id == R.id.open_btn_empty) {
            JumpUtils.goToPlayEmptyControlActivity(this, openBtn2);
        } else if (id == R.id.open_control) {
            JumpUtils.gotoControl(this);
        } else if (id == R.id.open_filter) {
            JumpUtils.gotoFilter(this);
        } else if (id == R.id.open_btn_pick) {//无缝切换
            JumpUtils.goToVideoPickPlayer(this, openBtn);
        } else if (id == R.id.open_btn_auto) {//列表自动播放
            JumpUtils.goToAutoVideoPlayer(this);
        } else if (id == R.id.open_scroll) {//列表自动播放
            JumpUtils.goToScrollDetailPlayer(this);
        } else if (id == R.id.open_window) {//多窗体下的悬浮窗
            JumpUtils.goToScrollWindow(this);
        } else if (id == R.id.open_btn_ad) {//广告
            JumpUtils.goToVideoADPlayer(this);
        } else if (id == R.id.open_btn_multi) {//多个同时播放
            JumpUtils.goToMultiVideoPlayer(this);
        } else if (id == R.id.open_btn_ad2) {//多个同时播放
            JumpUtils.goToVideoADPlayer2(this);
        } else if (id == R.id.open_list_ad) {//多个同时播放
            JumpUtils.goToADListVideoPlayer(this);
        } else if (id == R.id.open_custom_exo) {//多个同时播放
            JumpUtils.goToDetailExoListPlayer(this);
        } else if (id == R.id.open_switch) {
            JumpUtils.goToSwitch(this);
        } else if (id == R.id.media_codec) {
            JumpUtils.goMediaCodec(this);
        } else if (id == R.id.detail_normal_activity) {
            JumpUtils.goToDetailNormalActivity(this);
        } else if (id == R.id.detail_download_activity) {
            JumpUtils.goToDetailDownloadActivity(this);
        } else if (id == R.id.detail_subtitle_activity) {
            JumpUtils.goToGSYExoSubTitleDetailPlayer(this);
        } else if (id == R.id.detail_audio_activity) {
            JumpUtils.goToDetailAudioActivity(this);
        } else if (id == R.id.view_pager2_activity) {
            JumpUtils.goToViewPager2Activity(this);
        } else if (id == R.id.change_core) {
            i += 1;
            if (i % 3 == 0) {
                PlayerFactory.setPlayManager(IjkPlayerManager.class);
                changeCore.setText("IJK 内核");
            } else if (i % 3 == 1) {
                PlayerFactory.setPlayManager(Exo2PlayerManager.class);
                changeCore.setText("EXO 内核");
            } else if (i % 3 == 2) {
                PlayerFactory.setPlayManager(SystemPlayerManager.class);
                changeCore.setText("系统 内核");
            }
        } else if (id == R.id.clear_cache) {//清理缓存
            GSYVideoManager.instance().clearAllDefaultCache(GSYMainActivity.this);
            //String url = "https://res.exexm.com/cw_145225549855002";
            //GSYVideoManager.clearDefaultCache(MainActivity.this, url);
        }
    }
}
