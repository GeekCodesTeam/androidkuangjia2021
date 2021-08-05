package com.geek.libbase.viewpager2.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.geek.libbase.R;
import com.geek.libbase.viewpager2.adapter.MyRecyclerViewAdapter;


public class RecyclerViewBannerActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunbo_recyclerview_banner);
        recyclerView = findViewById(R.id.net_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyRecyclerViewAdapter(this));
    }

}
