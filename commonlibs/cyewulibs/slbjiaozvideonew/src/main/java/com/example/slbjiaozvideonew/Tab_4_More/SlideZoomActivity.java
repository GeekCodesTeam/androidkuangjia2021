package com.example.slbjiaozvideonew.Tab_4_More;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import com.example.slbjiaozvideonew.R;
import com.example.slbjiaozvideonew.Tab_3_List.ListView.adapter.CommentAdapter;
import com.example.slbjiaozvideonew.UrlsKt;

public class SlideZoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);
//        getSupportActionBar().setDisplayUseLogoEnabled(false);
//        getSupportActionBar().setTitle(getString(R.string.slide_zoom));
        CommentAdapter commentAdapter = new CommentAdapter();
        setContentView(R.layout.activity_slide_zoom);
        JzvdStd mJzvdStd = findViewById(R.id.surface_container);
        mJzvdStd.setUp(UrlsKt.getVideos()[14], UrlsKt.getTitles()[14], JzvdStd.SCREEN_NORMAL);
        Glide.with(this).load(UrlsKt.getThumbnails()[14]).into(mJzvdStd.posterImageView);
        RecyclerView recyclerView = findViewById(R.id.rv_comment);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(commentAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }
    @Override
    protected void onResume() {
        super.onResume();
        Jzvd.goOnPlayOnResume();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Jzvd.releaseAllVideos();
    }
}