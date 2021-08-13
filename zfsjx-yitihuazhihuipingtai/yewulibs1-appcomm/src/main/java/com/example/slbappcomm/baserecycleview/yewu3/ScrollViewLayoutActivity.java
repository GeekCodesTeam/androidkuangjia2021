package com.example.slbappcomm.baserecycleview.yewu3;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.slbappcomm.R;
import com.zcolin.gui.zrecyclerview.swiperefreshlayout.SwipeRefreshLayout;
import com.zcolin.gui.zrecyclerview.swiperefreshlayout.ZSwipeRefreshLayout;

public class ScrollViewLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview3_scrollview);


        final ZSwipeRefreshLayout refreshLayout = findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }
}
