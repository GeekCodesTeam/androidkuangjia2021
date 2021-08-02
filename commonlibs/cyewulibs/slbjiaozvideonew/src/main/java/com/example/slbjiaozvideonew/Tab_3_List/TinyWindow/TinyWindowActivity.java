package com.example.slbjiaozvideonew.Tab_3_List.TinyWindow;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

import com.example.slbjiaozvideonew.CustomJzvd.JzvdStdTinyWindow;
import com.example.slbjiaozvideonew.R;
import com.example.slbjiaozvideonew.UrlsKt;

/**
 * Created by Nathen on 2017/10/31.
 */

public class TinyWindowActivity extends AppCompatActivity {

    JzvdStdTinyWindow jzvdStdTinyWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);
//        getSupportActionBar().setDisplayUseLogoEnabled(false);
//        getSupportActionBar().setTitle(getString(R.string.tiny_window));
        setContentView(R.layout.activity_tiny_window);

        jzvdStdTinyWindow = findViewById(R.id.jz_video);
        jzvdStdTinyWindow.setUp(UrlsKt.getVideos()[9],
                UrlsKt.getTitles()[9]
                , JzvdStd.SCREEN_NORMAL);
        Glide.with(this)
                .load(UrlsKt.getThumbnails()[9])
                .into(jzvdStdTinyWindow.posterImageView);

    }


    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void clickTinyWindow(View view) {
        jzvdStdTinyWindow.gotoTinyScreen();
    }

    public void clickAutoTinyListViewRecyclerView(View view) {
        Toast.makeText(this, "comming soon", Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(this, ActivityTinyWindowRecycleView.class));
    }

    public void clickAutoTinyListViewRecyclerViewMultiHolder(View view) {
        Toast.makeText(this, "comming soon", Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(this, ActivityTinyWindowRecycleViewMultiHolder.class));
    }
}
