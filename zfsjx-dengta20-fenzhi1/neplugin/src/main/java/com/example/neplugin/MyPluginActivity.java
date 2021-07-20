package com.example.neplugin;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.libbase.plugins.PluginBaseActivity;


public class MyPluginActivity extends PluginBaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ne_plugin);
    }


}
