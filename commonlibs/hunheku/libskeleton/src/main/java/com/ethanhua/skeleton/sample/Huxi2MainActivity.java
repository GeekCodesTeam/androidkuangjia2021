package com.ethanhua.skeleton.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.libskeleton.R;

public class Huxi2MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainhuxi2);
        findViewById(R.id.btn_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Huxi2RecyclerViewActivity.start(Huxi2MainActivity.this, Huxi2RecyclerViewActivity.TYPE_LINEAR);
            }
        });
        findViewById(R.id.btn_grid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Huxi2RecyclerViewActivity.start(Huxi2MainActivity.this, Huxi2RecyclerViewActivity.TYPE_GRID);
            }
        });
        findViewById(R.id.btn_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Huxi2ViewActivity.start(Huxi2MainActivity.this, Huxi2ViewActivity.TYPE_VIEW);
            }
        });
        findViewById(R.id.btn_Imgloading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Huxi2ViewActivity.start(Huxi2MainActivity.this, Huxi2ViewActivity.TYPE_IMG_LOADING);
            }
        });

        findViewById(R.id.btn_status).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Huxi2MainActivity.this, Huxi2StatusViewActivity.class));
            }
        });
    }
}
