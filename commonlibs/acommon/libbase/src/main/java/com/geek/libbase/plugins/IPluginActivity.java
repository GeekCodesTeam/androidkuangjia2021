package com.geek.libbase.plugins;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * FileName：IPluginActivity
 * Create By：liumengqiang
 * Description：插件基类接口
 * TODO 需要定义一份和所有Activity中一样方法，Activity中的有些没有定义出来。
 */
public interface IPluginActivity {
    /**
     * 给插件Activity指定上下文
     *
     * @param activity
     */
    void attach(Activity activity);

    // 以下全都是Activity生命周期函数,
    // 插件Activity本身 在被用作"插件"的时候不具备生命周期，由宿主里面的代理Activity类代为管理
    void onCreate(Bundle saveInstanceState);

    void onStart();

    void onResume();

    void onRestart();

    void onPause();

    void onStop();

    void onDestroy();

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
