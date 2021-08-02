package com.haier.cellarette.baselibrary.musicutils;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.haier.cellarette.baselibrary.R;
import com.haier.cellarette.baselibrary.musicutils.musicplayer.DemoMusicActivity;

public class MusicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicact);
        findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MusicUtil.getInstance(MusicActivity.this).playMusic(R.raw.ring1);
            }
        });
        findViewById(R.id.tv2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicUtil.getInstance(MusicActivity.this).playMusic(R.raw.ring2);
            }
        });
        findViewById(R.id.tv3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicUtil.getInstance(MusicActivity.this).playMusic(R.raw.ring3);
            }
        });
        findViewById(R.id.tv4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MusicActivity.this, DemoMusicActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MusicUtil.getInstance(MusicActivity.this).MusicDestory();
    }
}
