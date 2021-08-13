package com.fosung.lighthouse.ezhibu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.geek.libbase.base.SlbBaseActivity;
import com.geek.libbase.plugins.PluginBaseActivity;
import com.geek.libbase.plugins.PluginConst;

public class EzhibuAct2 extends SlbBaseActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_ezhibu2;
    }

    @Override
    protected void setup(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.setup(savedInstanceState);
        // 方法1接收
        if (savedInstanceState != null) {
            String aaaa = savedInstanceState.getString(PluginConst.query1);
            ToastUtils.showLong("方法1接收:" + "aaa->" + aaaa );
        }
        // TODO test
        findViewById(R.id.tv1).setOnClickListener(v -> {
            // 方法1
            Intent it = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.EzhibuAct3");
            it.putExtra("query1", "pc");
            it.putExtra("query2", "45464");
            it.putExtra("query2", "aaaa");
            startActivity(it);
        });
        findViewById(R.id.tv2).setOnClickListener(v -> {
            // 方法2
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("dataability://cs.znclass.com/" +
                            "com.fosung.lighthouse.ezhibu.hs.act.slbapp.EzhibuAct3?query3=aaaa&query2=45464&query1=pc"));
            startActivity(intent);
        });
//        DeviceUtil.getUUID();
    }
}