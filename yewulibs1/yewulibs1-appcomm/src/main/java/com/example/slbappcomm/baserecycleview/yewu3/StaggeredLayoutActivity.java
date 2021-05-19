package com.example.slbappcomm.baserecycleview.yewu3;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.slbappcomm.R;
import com.example.slbappcomm.baserecycleview.yewu3.adapter.ZRecyclerAdapter;
import com.zcolin.gui.zrecyclerview.BaseRecyclerAdapter;
import com.zcolin.gui.zrecyclerview.ZRecyclerView;

import java.util.ArrayList;

public class StaggeredLayoutActivity extends AppCompatActivity {

    private ZRecyclerView    recyclerView;
    private ZRecyclerAdapter recyclerAdapter;
    private int              mPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview3_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setStaggeredGridLayout(false, 2);//默认已设置LinearLayoutManager
        recyclerView.setOnPullLoadMoreListener(new PullLoadMoreListener());
        recyclerView.setEmptyView(this, R.layout.recycleview3_view_recycler_empty);
        recyclerView.addHeaderView(this, R.layout.recycleview3_view_recyclerheader);
        recyclerView.addFooterView(this, R.layout.recycleview3_view_recyclerfooter);
        recyclerView.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<Object>() {
            @Override
            public void onItemClick(View covertView, int position, Object data) {
                Toast.makeText(StaggeredLayoutActivity.this, data+"", Toast.LENGTH_SHORT).show();
            }
        });

        notifyData(new ArrayList<String>(), false);
        recyclerView.refreshWithPull();
    }

    /**
     * 设置数据Adapter
     */
    public void notifyData(ArrayList<String> list, boolean isClear) {
        if (recyclerAdapter == null) {
            recyclerAdapter = new ZRecyclerAdapter();
            recyclerAdapter.addDatas(list);
            recyclerView.setAdapter(recyclerAdapter);
        } else {
            if (isClear) {
                recyclerAdapter.setDatas(list);
            } else {
                recyclerAdapter.addDatas(list);
            }
            recyclerAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 模仿从网络请求数据
     */
    public void requestData(final int page) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyData(setList(page), page == 1);
                recyclerView.setPullLoadMoreCompleted();
                if (page == 2) {
                    recyclerView.setNoMore(true);
                }
            }
        }, 1000);
    }

    //制造假数据
    private ArrayList<String> setList(int page) {
        ArrayList<String> dataList = new ArrayList<>();
        int start = 15 * (page - 1);
        for (int i = start; i < 15 * page; i++) {
            dataList.add(String.format("第%d条数据", i));
        }
        return dataList;
    }

    class PullLoadMoreListener implements ZRecyclerView.PullLoadMoreListener {
        @Override
        public void onRefresh() {
            mPage = 1;
            requestData(mPage);
            recyclerView.setNoMore(false);
        }

        @Override
        public void onLoadMore() {
            mPage = mPage + 1;
            requestData(mPage);
        }
    }
}
