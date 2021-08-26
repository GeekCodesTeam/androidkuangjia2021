package com.geek.appplugin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.geek.libbase.plugin.PluginActivity;

import org.jetbrains.annotations.Nullable;


public final class Main1Activity extends PluginActivity {
    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        Log.d("Main1Activity orgin", "onCreate11111");
        Log.d("Main1Activity orgin", "packageName:" + this.getPackageName());
        Log.d("Main1Activity orgin", "baseContext" + this.getBaseContext());
        Log.d("Main1Activity orgin", "baseContext" + this.getTheme());
        setContentView(R.layout.activity_main1);
        Log.d("Main1Activity orgin", "baseContext" + this.getBaseContext());
        Log.d("Main1Activity orgin", "baseContext" + this.getTheme());
        this.findViewById(R.id.button).setOnClickListener((OnClickListener) (new OnClickListener() {
            @Override
            public final void onClick(View it) {
                startActivity(new Intent(Main1Activity.this, Main2Activity.class));
            }
        }));
    }

    @Override
    public void onDestroy() {
        Log.d("Main1Activity orgin", "onDestroy11111111");
        super.onDestroy();
    }
}
