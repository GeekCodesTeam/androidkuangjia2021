package com.example.slbappjpushshare.fenxiang;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import cn.jiguang.share.android.api.PlatActionListener;
import cn.jiguang.share.android.api.Platform;

public class JPushShareUtils {

    private static final String TAG = JPushShareUtils.class.getName();
    public static String share_url = "http://blog.51cto.com/liangxiao";
    public static String share_text = "一个行走的geek，一个行走的CD";
    public static String share_title = " 梁肖技术中心-51CTO博客";
    public static String share_image_assets_url = "file:///android_asset/" + "img/geek_icon.png";
    public static String share_imageurl = "https://s2.51cto.com//wyfs02/M01/89/BA/wKioL1ga-u7QnnVnAAAfrCiGnBQ946_middle.jpg";
    public static String share_imageurl_1 = "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1308/02/c0/24056523_1375430477597.jpg";
    public static String share_videourl = "http://v.youku.com/v_show/id_XOTQwMDE1ODAw.html?from=s1.8-1-1.2&spm=a2h0k.8191407.0.0";
    public static String share_musicurl = "http://music.huoxing.com/upload/20130330/1364651263157_1085.mp3";
    public static String music_shareUrl = "https://y.qq.com/n/yqq/song/109325260_num.html?ADTAG=h5_playsong&no_redirect=1";
    public static String APP_ID = "wxa3fa50c49fcd271c";// wxfd6dfaf7196fc320
    public static String APP_KEY = "e8a93eed2ccd89b047f29d0aa62e1c95";// 0431ee301b83b6c00794493c448d980c   dabe083e20ccacfed39e62df72c58f41

    private OnShareResultInfoLitener onShareResultInfoLitener;

    public JPushShareUtils(OnShareResultInfoLitener onShareResultInfoLitener) {
        this.onShareResultInfoLitener = onShareResultInfoLitener;

    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
//            String toastMsg = (String) msg.obj;
            Bundle b = msg.getData();
            String platform = b.getString("platform");
            String toastMsg = b.getString("toastMsg");
            String data = b.getString("data");
            if (onShareResultInfoLitener != null) {
                onShareResultInfoLitener.onResults(platform, toastMsg, data);
            }

            return false;
        }
    });

    private void she_handler_msg(int what, String platform, String toastMsg, String datas) {
        if (mHandler == null) {
            return;
        }
        Message msg = mHandler.obtainMessage(what);
//                msg.obj = data.toString();
        Bundle b = new Bundle();
        b.putString("platform", platform);
        b.putString("toastMsg", toastMsg);
        b.putString("data", datas);
        msg.setData(b);
        msg.sendToTarget();
    }

    /**
     * 下载图片
     *
     * @param url
     * @return
     * @throws Exception
     */
    public Bitmap downloadUrlToBitmap(String url) throws Exception {
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
        BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
        Bitmap bitmap = BitmapFactory.decodeStream(in);
        urlConnection.disconnect();
        in.close();
        return bitmap;
    }


    /**
     * 从资源中获取的Drawable --> Bitmap
     *
     * @param context
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Context context, Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;
        Bitmap bm = bd.getBitmap();
        return bm;
    }

    public PlatActionListener mShareListener1 = new PlatActionListener() {
        @Override
        public void onComplete(Platform platform, int action, HashMap<String, Object> data) {
            String toastMsg = "分享成功";
            she_handler_msg(1, platform.getName(), toastMsg, data.toString());

        }

        @Override
        public void onError(Platform platform, int action, final int errorCode, final Throwable error) {
            Log.e("LoginActivity", "error:" + errorCode + ",msg:" + error);
            String toastMsg = "分享失败";
//            she_handler_msg(1, platform.getName(),toastMsg + (error != null ? error.getMessage() : "") + "---" + errorCode, "");
            she_handler_msg(1, platform.getName(), toastMsg, "");

        }

        @Override
        public void onCancel(Platform platform, int action) {
            String toastMsg = "分享取消";
            she_handler_msg(1, platform.getName(), toastMsg, "");

        }
    };

    /**
     * 销毁bufen
     */
    public void ondes() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

//    /**
//     * 跳转到微信
//     */
//    private void getWechatApi(Context context) {
//        try {
////            Intent intent = new Intent(Intent.ACTION_MAIN);
////            ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
////            intent.addCategory(Intent.CATEGORY_LAUNCHER);
////            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////            intent.setComponent(cmp);
////            context.startActivity(intent);
//            String appId = "wxd930ea5d5a258f4f"; // 填应用AppId
//            IWXAPI api = WXAPIFactory.createWXAPI(context, appId);
//
//            WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
//            req.userName = "gh_d43f693ca31f"; // 填小程序原始id
////            req.path = path;                  //拉起小程序页面的可带参路径，不填默认拉起小程序首页
//            req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;// 可选打开 开发版，体验版和正式版
//            api.sendReq(req);
//        } catch (ActivityNotFoundException e) {
//            // TODO: handle exception
////           showToastLong("检查到您手机没有安装微信，请安装后使用该功能");
//        }
//    }

}
