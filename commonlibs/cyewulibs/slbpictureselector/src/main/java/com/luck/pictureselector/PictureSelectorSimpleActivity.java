package com.luck.pictureselector;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.camera2.Camera2Config;
import androidx.camera.core.CameraXConfig;
import com.luck.picture.lib.app.IApp;
import com.luck.picture.lib.app.PictureAppMaster;
import com.luck.picture.lib.engine.PictureSelectorEngine;

public class PictureSelectorSimpleActivity extends AppCompatActivity implements View.OnClickListener, IApp, CameraXConfig.Provider {
    private Button btn_activity, btn_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otherpictureselector);
        PictureAppMaster.getInstance().setApp(this);
        btn_activity = findViewById(R.id.btn_activity);
        btn_fragment = findViewById(R.id.btn_fragment);
        btn_activity.setOnClickListener(this);
        btn_fragment.setOnClickListener(this);
    }

    @Override
    public Context getAppContext() {
        return PictureSelectorApp.get();
    }

    @Override
    public PictureSelectorEngine getPictureSelectorEngine() {
        return new PictureSelectorEngineImp();
    }

    @NonNull
    @Override
    public CameraXConfig getCameraXConfig() {
        return Camera2Config.defaultConfig();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_activity) {
            startActivity(new Intent(PictureSelectorSimpleActivity.this, PictureSelectorAct.class));
        } else if (id == R.id.btn_fragment) {
            startActivity(new Intent(PictureSelectorSimpleActivity.this, PhotoFragmentActivity.class));
        }
    }
}
