package com.geek.appplugin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.geek.libbase.plugin.PluginActivity;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;

import org.jetbrains.annotations.Nullable;

public final class Main2Activity extends PluginActivity {
    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        Log.d("Main2Activity orgin", "onCreate2222222");
        setContentView(R.layout.activity_main2);

        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new XPopup.Builder(getBaseContext())
//                        .maxWidth(600)
                        .maxHeight(800)
                        .isDarkTheme(true)
                        .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                        .asCenterList("请选择一项", new String[]{"条目1", "条目2", "条目3", "条目4", "条目1", "条目2", "条目3", "条目4",
                                        "条目1", "条目2", "条目3", "条目4", "条目1", "条目2", "条目3", "条目4",
                                        "条目1", "条目2", "条目3", "条目4", "条目1", "条目2", "条目3", "条目4",
                                        "条目1", "条目2", "条目3", "条目4", "条目1", "条目2", "条目3", "条目4",
                                        "条目1", "条目2", "条目3", "条目4", "条目1", "条目2", "条目3", "条目4",},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
//                                        toast("click " + text);
                                    }
                                })
//                        .bindLayout(R.layout.my_custom_attach_popup) //自定义布局
                        .show();
//                ToastUtils.showLong("sssssssssssssssssss");
            }
        });
    }

    @Override
    public void onDestroy() {
        Log.d("Main2Activity orgin", "onDestroy222222");
        super.onDestroy();
    }

}
