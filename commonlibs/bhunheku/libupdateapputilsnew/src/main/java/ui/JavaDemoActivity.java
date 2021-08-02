package ui;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.commonlibs.libupdateapputilsnew.R;

import constant.DownLoadBy;
import constant.UiType;
import listener.Md5CheckResultListener;
import listener.OnBtnClickListener;
import listener.OnInitUiListener;
import listener.UpdateDownloadListener;
import model.UiConfig;
import model.UpdateConfig;
import update.UpdateAppUtils;

/**
 * desc: java使用实例
 * time: 2019/6/27
 *
 * @author yk
 */
public class JavaDemoActivity extends AppCompatActivity implements View.OnClickListener {

    private String apkUrl = "http://118.24.148.250:8080/yk/update_signed.apk";
    private String updateTitle = "发现新版本V2.0.0";
    private String updateContent = "1、Kotlin重构版\n2、支持自定义UI\n3、增加md5校验\n4、更多功能等你探索";
    private Button btnJava;//java更新
    private Button btnBasicUpdate;//基础使用
    private Button btnBrowserUpdate;//浏览器更新
    private Button btnForceUpdate;//强制更新
    private Button btnCustomuiUpdate;//自定义Ui

    private UiConfig uiConfig;//基础UI配置
    private UpdateConfig updateConfig;//基础使用
    private UiConfig uiConfigbrowser;//浏览器UI配置
    private UpdateConfig updateConfigbrowser;//浏览器使用

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateutilsnew_java_demo);
        btnJava = findViewById(R.id.btn_java);
        btnBasicUpdate = findViewById(R.id.btn_basic_update);
        btnBrowserUpdate = findViewById(R.id.btn_browser_update);
        btnForceUpdate = findViewById(R.id.btn_force_update);
        btnCustomuiUpdate = findViewById(R.id.btn_customui_update);

        btnJava.setOnClickListener(this);
        btnBasicUpdate.setOnClickListener(this);
        btnBrowserUpdate.setOnClickListener(this);
        btnForceUpdate.setOnClickListener(this);
        btnCustomuiUpdate.setOnClickListener(this);

        UpdateAppUtils.init(this);

        /*基础Config配置权限使用*/
        updateConfig = new UpdateConfig();
        updateConfig.setCheckWifi(true);
        updateConfig.setNeedCheckMd5(false);
        updateConfig.setNotifyImgRes(R.drawable.ic_update_logo);
        updateConfig.setApkSaveName("时代智囊");

        /*基础UI配置*/
        uiConfig = new UiConfig();
        uiConfig.setUiType(UiType.PLENTIFUL);



        /*浏览器Config配置权限使用*/
        updateConfigbrowser = new UpdateConfig();
        updateConfigbrowser.setCheckWifi(true);
        updateConfigbrowser.setDownloadBy(DownLoadBy.BROWSER);
        updateConfigbrowser.setServerVersionName("2.0.0");

        /*浏览器UI配置*/
        uiConfigbrowser = new UiConfig();
        uiConfigbrowser.setUiType(UiType.PLENTIFUL);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_java) {//java基础代码常规更新
            showjava();
        } else if (id == R.id.btn_basic_update) {//基础更新
            showBasicUpdate();
        } else if (id == R.id.btn_browser_update) {//浏览器更新
            showBrowserUpdate();
        } else if (id == R.id.btn_force_update) {//强制更新
            showForceUpdate();
        } else if (id == R.id.btn_customui_update) {//自定义Ui更新
            showCustomuiUpdate();
        }
    }

    /**
     * 自定义Ui更新
     */
    private void showCustomuiUpdate() {
        /*属性配置*/
        UpdateConfig customconfig = new UpdateConfig();
        customconfig.setAlwaysShowDownLoadDialog(true);

        /*UI配置*/
        UiConfig customUI = new UiConfig();
        customUI.setUiType(UiType.CUSTOM);
        customUI.setCustomLayoutId(R.layout.view_update_dialog_custom);

        UpdateAppUtils
                .getInstance()
                .apkUrl(apkUrl)
                .updateTitle(updateTitle)
                .updateContent(updateContent)
                .updateConfig(customconfig)
                .uiConfig(customUI)
                .setOnInitUiListener(new OnInitUiListener() {
                    @Override
                    public void onInitUpdateUi(View view, UpdateConfig updateConfig, UiConfig uiConfig) {
                        TextView tvTitle = view.findViewById(R.id.tv_update_title);
                        TextView tvName = view.findViewById(R.id.tv_version_name);
                        if (tvName != null && tvName != null) {
                            tvTitle.setText("版本更新了");
                            tvName.setText("v2.0.0");
                        }
                    }
                })
                .update();


    }

    /**
     * 强制更新
     */
    private void showForceUpdate() {
        // ui配置
        UiConfig uiConfigforce = new UiConfig();
        uiConfigforce.setUiType(UiType.PLENTIFUL);
        uiConfigforce.setCancelBtnText("下次再说");
        uiConfigforce.setUpdateLogoImgRes(R.drawable.ic_update);
        uiConfigforce.setUpdateBtnBgRes(R.drawable.bg_btn);
        uiConfigforce.setTitleTextColor(Color.BLACK);
        uiConfigforce.setTitleTextSize(18f);
        uiConfigforce.setContentTextColor(Color.parseColor("#88e16531"));

        // 更新配置
        UpdateConfig forceconfig = new UpdateConfig();
        forceconfig.setForce(true);
        forceconfig.setDebug(true);
        forceconfig.setCheckWifi(true);
        forceconfig.setShowNotification(true);
        forceconfig.setNotifyImgRes(R.drawable.ic_update_logo);
        forceconfig.setApkSavePath(getExternalFilesDir(null).getAbsolutePath() + "/teprinciple");
        forceconfig.setApkSaveName("teprinciple");

        UpdateAppUtils
                .getInstance()
                .apkUrl(apkUrl)
                .updateTitle(updateTitle)
                .updateContent(updateContent)
                .updateConfig(forceconfig)
                .uiConfig(uiConfigforce)
                .setUpdateDownloadListener(new UpdateDownloadListener() {
                    @Override
                    public void onStart() {
                        Log.e("testaaa", "onStart");
                    }

                    @Override
                    public void onDownload(int progress) {
                        Log.e("testaaa", "onDownload" + progress);
                    }

                    @Override
                    public void onFinish() {
                        Log.e("testaaa", "onFinish");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("testaaa", "onError" + e.getMessage());
                    }
                }).update();

    }

    /**
     * 浏览器更新
     */
    private void showBrowserUpdate() {
        // 使用SpannableString
        SpanUtils spanUtils = new SpanUtils(this);
        SpannableStringBuilder span = spanUtils
                .appendLine("1、Kotlin重构版")
                .appendLine("2、支持自定义UI").setForegroundColor(Color.RED)
                .appendLine("3、增加md5校验").setForegroundColor(Color.parseColor("#88e16531")).setFontSize(20, true)
                .appendLine("4、更多功能等你探索").setBoldItalic()
                .appendLine().appendImage(R.drawable.ic_update_logo).setBoldItalic()
                .create();

        UpdateAppUtils
                .getInstance()
                .apkUrl(apkUrl)
                .updateTitle(updateTitle)
                .updateContent(span)
                .updateConfig(updateConfigbrowser)
                .uiConfig(uiConfigbrowser)
                // 设置 取消 按钮点击事件
                .setCancelBtnClickListener(new OnBtnClickListener() {
                    @Override
                    public boolean onClick() {
                        Toast.makeText(JavaDemoActivity.this, "取消", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                // 设置 立即更新 按钮点击事件
                .setUpdateBtnClickListener(new OnBtnClickListener() {
                    @Override
                    public boolean onClick() {
                        Toast.makeText(JavaDemoActivity.this, "立即更新", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }).update();
    }

    /***
     * 基础更新
     * */
    private void showBasicUpdate() {
        UpdateAppUtils
                .getInstance()
                .apkUrl(apkUrl)
                .updateTitle(updateTitle)
                .updateConfig(updateConfig)
                .uiConfig(uiConfig)
                .updateContent(updateContent)
                .update();
    }

    /**
     * java 代码使用
     */
    private void showjava() {


        UpdateAppUtils
                .getInstance()
                .apkUrl(apkUrl)
                .updateTitle(updateTitle)
                .updateContent(updateContent)
                .uiConfig(uiConfig)
                .updateConfig(updateConfig)
                .setMd5CheckResultListener(new Md5CheckResultListener() {
                    @Override
                    public void onResult(boolean result) {

                    }
                })
                .setUpdateDownloadListener(new UpdateDownloadListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onDownload(int progress) {

                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                })
                .update();
    }
}
