package com.example.gsyvideoplayer.simple;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gsyvideoplayer.R;

import permissions.dispatcher.PermissionUtils;

public class SimpleActivity extends AppCompatActivity implements View.OnClickListener {

    final String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        findViewById(R.id.simple_list_1).setOnClickListener(this);
        findViewById(R.id.simple_list_2).setOnClickListener(this);
        findViewById(R.id.simple_detail_1).setOnClickListener(this);
        findViewById(R.id.simple_detail_2).setOnClickListener(this);
        findViewById(R.id.simple_player).setOnClickListener(this);
        boolean hadPermission = PermissionUtils.hasSelfPermissions(this, permissions);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !hadPermission) {
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            requestPermissions(permissions, 1110);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean sdPermissionResult = PermissionUtils.verifyPermissions(grantResults);
        if (!sdPermissionResult) {
            Toast.makeText(this, "没获取到sd卡权限，无法播放本地视频哦", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.simple_player) {
            startActivity(new Intent(this, SimplePlayer.class));
        } else if (id == R.id.simple_list_1) {
            startActivity(new Intent(this, SimpleListVideoActivityMode1.class));
        } else if (id == R.id.simple_list_2) {
            startActivity(new Intent(this, SimpleListVideoActivityMode2.class));
        } else if (id == R.id.simple_detail_1) {
            startActivity(new Intent(this, SimpleDetailActivityMode1.class));
        } else if (id == R.id.simple_detail_2) {
            startActivity(new Intent(this, SimpleDetailActivityMode2.class));
        }
    }
}
