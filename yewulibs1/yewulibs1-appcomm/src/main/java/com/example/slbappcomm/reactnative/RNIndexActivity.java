//package com.example.slbappcomm.reactnative;
//
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.KeyEvent;
//
//import com.facebook.react.BuildConfig;
//import com.facebook.react.ReactInstanceManager;
//import com.facebook.react.ReactRootView;
//import com.facebook.react.common.LifecycleState;
//import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
//import com.facebook.react.shell.MainReactPackage;
//
//public class RNIndexActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler {
//    private ReactRootView mReactRootView;
//    private ReactInstanceManager mReactInstanceManager;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mReactRootView = new ReactRootView(this);
//        mReactInstanceManager = ReactInstanceManager.builder()
//                .setApplication(getApplication())
//                .setBundleAssetName("index.android.bundle")
//                .setJSMainModulePath("index.android")
//                .addPackage(new MainReactPackage())
//                .setUseDeveloperSupport(BuildConfig.DEBUG)
//                .setInitialLifecycleState(LifecycleState.RESUMED)
//                .build();
//
//        // 注意这里的HelloWorld必须对应“index.android.js”中的
//        // “AppRegistry.registerComponent()”的第一个参数
//        mReactRootView.startReactApplication(mReactInstanceManager, "SLBAPP", null);
//
//        setContentView(mReactRootView);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        if (mReactInstanceManager != null) {
//            mReactInstanceManager.onHostPause(this);
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        if (mReactInstanceManager != null) {
//            mReactInstanceManager.onHostResume(this, this);
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//        if (mReactInstanceManager != null) {
//            mReactInstanceManager.destroy();
//        }
//    }
//
//    @Override
//    public void invokeDefaultOnBackPressed() {
//        super.onBackPressed();
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (mReactInstanceManager != null) {
//            mReactInstanceManager.onBackPressed();
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_MENU && mReactInstanceManager != null) {
//            mReactInstanceManager.showDevOptionsDialog();
//            return true;
//        }
//        return super.onKeyUp(keyCode, event);
//    }
//}