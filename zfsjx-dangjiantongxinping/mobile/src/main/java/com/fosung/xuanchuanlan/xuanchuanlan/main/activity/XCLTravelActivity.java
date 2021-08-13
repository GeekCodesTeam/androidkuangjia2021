package com.fosung.xuanchuanlan.xuanchuanlan.main.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.fosung.frameutils.http.response.ZResponse;
import com.fosung.xuanchuanlan.R;
import com.fosung.xuanchuanlan.common.base.ActivityParam;
import com.fosung.xuanchuanlan.common.base.BaseActivity;
import com.fosung.xuanchuanlan.mian.activity.JavaScriptInterface;
import com.fosung.xuanchuanlan.xuanchuanlan.main.http.XCLHttp;
import com.fosung.xuanchuanlan.xuanchuanlan.main.http.XCLHttpUrlMaster;
import com.fosung.xuanchuanlan.xuanchuanlan.main.http.entity.FeatureTravel;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import okhttp3.Response;

import static com.fosung.xuanchuanlan.mian.activity.WebActivity.MyChromeClient.FILECHOOSER_RESULTCODE;

@ActivityParam(isShowToolBar = false)
public class XCLTravelActivity extends BaseActivity {

    private com.tencent.smtt.sdk.WebView web;
    private ProgressBar progressBar;
    public ValueCallback<Uri> UploadMsg;
    public ValueCallback<Uri[]> UploadMsg2;
    private String url = "https://www.sdta.cn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        try {
            if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 11) {
                getWindow()
                        .setFlags(
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
        }

        if (savedInstanceState != null) {
            // 从已保存状态恢复成员的值
            url = savedInstanceState.getString("url");

        }
        setContentView(R.layout.activity_xcl_travel);

        web = (com.tencent.smtt.sdk.WebView) findViewById(R.id.id_travel_web);
        progressBar = (ProgressBar) findViewById(R.id.id_travel_progressBar);

        setWebView();
        initData();
//        web.loadUrl(url);

    }

    private void initData () {
        Map<String, String> map = new HashMap<String, String>();

        XCLHttp.post(XCLHttpUrlMaster.TRAVEL, map, new ZResponse<FeatureTravel>(FeatureTravel.class) {
            @Override
            public void onSuccess(Response response, FeatureTravel resObj) {
                if (resObj.datalist != null && resObj.datalist.size() > 0) {
                    url = resObj.datalist.get(0).addr;
                    web.loadUrl(url);
                }else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(XCLTravelActivity.this,"暂无数据",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(int code, String error) {
                super.onError(code, error);
            }
        });
    }

    private void setWebView() {
        //web.clearCache(true);
        com.tencent.smtt.sdk.WebSettings webSettings = web.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
        webSettings.setAppCacheEnabled(true);//是否使用缓存
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webSettings.setMediaPlaybackRequiresUserGesture(false);
        }
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(false); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(false); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
//        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        web.addJavascriptInterface(new JavaScriptInterface(this), "android");
        web.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView webView, String s) {
                return false;
            }
        });
        web.setWebChromeClient(new com.tencent.smtt.sdk.WebChromeClient() {
            @Override
            public void onProgressChanged(com.tencent.smtt.sdk.WebView webView, int i) {
                if (i < 100) {
                    Log.d("keshihua", "开始加载");
                } else {
                    Log.d("keshihua", "加载结束");
                }
            }
        });
        web.setBackgroundColor(ContextCompat.getColor(this, R.color.webred_bg)); // 设置背景色
//        web.getBackground().setAlpha(0);
//        web.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                Log.d("keshihua", "开始加载");
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                Log.d("keshihua", "加载结束");
//            }
//
//            // 链接跳转都会走这个方法
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
////                if (url != null) return false;
////                try {
////                    if (url.startsWith("http:") || url.startsWith("https:")) {
////                        view.loadUrl(url);
////                        return true;
////                    } else {
////                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
////                        startActivity(intent);
////                        return true;
////                    }
////                } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
////                    return false;
////                }
//
//            }
//        });

        web.setWebChromeClient(new MyChromeClient(this));
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setPluginState(WebSettings.PluginState.ON);
//        webSettings.setUseWideViewPort(true); // 关键点
//        webSettings.setAllowFileAccess(true); // 允许访问文件
//        webSettings.setSupportZoom(true); // 支持缩放
//        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH); // 提高渲染的优先级
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
//        web.getSettings().setUseWideViewPort(true);
//        web.addJavascriptInterface(new MyJavaScript(), "JsUtils");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == UploadMsg && null == UploadMsg2) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (UploadMsg2 != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (UploadMsg != null) {
                UploadMsg.onReceiveValue(result);
                UploadMsg = null;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent) {
        if (requestCode != FILECHOOSER_RESULTCODE || UploadMsg2 == null)
            return;
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (intent != null) {
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        UploadMsg2.onReceiveValue(results);
        UploadMsg2 = null;
    }

    class MyChromeClient extends com.tencent.smtt.sdk.WebChromeClient {

        private Activity activity;

        public static final int FILECHOOSER_RESULTCODE = 5173;

        public String mCameraFilePath = "";

        @SuppressWarnings("deprecation")
        public MyChromeClient(Activity cordova) {
            this.activity = cordova;
        }

        @Override
        public void onProgressChanged(com.tencent.smtt.sdk.WebView webView, int i) {
            super.onProgressChanged(webView, i);
            if (i == 100)
                progressBar.setVisibility(View.GONE);
        }


        // <input type="file" name="fileField" id="fileField" />
        // Android > 4.1.1


        @Override
        public boolean onShowFileChooser(com.tencent.smtt.sdk.WebView webView, com.tencent.smtt.sdk.ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
            // TODO 自动生成的方法存根
            UploadMsg2 = valueCallback;
            this.activity.startActivityForResult(createDefaultOpenableIntent(),

                    this.FILECHOOSER_RESULTCODE);
            return false;
        }

        //        @Override
//        public boolean onShowFileChooser(WebView webView,
//                                         ValueCallback<Uri[]> filePathCallback,
//                                         FileChooserParams fileChooserParams) {
//            // TODO 自动生成的方法存根
//            UploadMsg2 = filePathCallback;
//            this.activity.startActivityForResult(createDefaultOpenableIntent(),
//
//                    this.FILECHOOSER_RESULTCODE);
//            return false;
//        }
        @SuppressWarnings("static-access")
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            UploadMsg = uploadMsg;
            this.activity.startActivityForResult(createDefaultOpenableIntent(), this.FILECHOOSER_RESULTCODE);
        }

        // 3.0 +
        @SuppressWarnings("static-access")
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            UploadMsg = uploadMsg;
            this.activity.startActivityForResult(createDefaultOpenableIntent(), this.FILECHOOSER_RESULTCODE);
        }

        // Android < 3.0
        @SuppressWarnings("static-access")
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            UploadMsg = uploadMsg;
            this.activity.startActivityForResult(createDefaultOpenableIntent(),
                    this.FILECHOOSER_RESULTCODE);
        }

        private Intent createDefaultOpenableIntent() {
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            /**
             * createCamcorderIntent(),
             * createSoundRecorderIntent()
             */
            Intent chooser = createChooserIntent(createCameraIntent());
            chooser.putExtra(Intent.EXTRA_INTENT, i);
            return chooser;
        }

        private Intent createChooserIntent(Intent... intents) {
            Intent chooser = new Intent(Intent.ACTION_CHOOSER);
            chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents);
            chooser.putExtra(Intent.EXTRA_TITLE, "选择图片");
            return chooser;
        }

        @SuppressWarnings("static-access")
        private Intent createCameraIntent() {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File externalDataDir = Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            File cameraDataDir = new File(externalDataDir.getAbsolutePath()
                    + File.separator + "keshihua");
            cameraDataDir.mkdirs();
            String mCameraFilePath = cameraDataDir.getAbsolutePath() + File.separator
                    + System.currentTimeMillis() + ".jpg";
            this.mCameraFilePath = mCameraFilePath;
            cameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mCameraFilePath)));
            return cameraIntent;
        }

        /*
         * private Intent createCamcorderIntent() { return new
         * Intent(MediaStore.ACTION_VIDEO_CAPTURE); }
         * private Intent createSoundRecorderIntent() { return new
         * Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION); }
         */
        public Uri getImageContentUri(Context context, File imageFile) {
            String filePath = imageFile.getAbsolutePath();
            Cursor cursor = context.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Images.Media._ID},
                    MediaStore.Images.Media.DATA + "=? ",
                    new String[]{filePath}, null);
            if (cursor != null && cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
                Uri baseUri = Uri.parse("content://media/external/images/media");
                return Uri.withAppendedPath(baseUri, "" + id);
            } else {
                if (imageFile.exists()) {
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.DATA, filePath);
                    return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                } else {
                    return null;
                }
            }
        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    private void goBack() {
        if (web.canGoBack()) {
            web.goBack();
        } else {
            finish();
        }
    }


    @Override
    public void onBackPressed() {
        goBack();
    }

    @Override
    protected void onDestroy() {
        if (this.web != null) {
            web.destroy();
        }
        super.onDestroy();
    }
}
