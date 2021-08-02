package com.example.slbappcomm.baserecycleview.yewu3;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.slbappcomm.R;
import com.example.slbappcomm.baserecycleview.yewu3.adapter.ZRecyclerAdapter;
import com.zcolin.gui.zrecyclerview.BaseRecyclerAdapter;
import com.zcolin.gui.zrecyclerview.ZRecyclerView;

import java.util.ArrayList;

public class EmptyViewLayoutActivity extends AppCompatActivity {

    private ZRecyclerView recyclerView;
    private ZRecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview3_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setEmptyView(this, R.layout.recycleview3_view_recycler_empty);

        /**
         * 初始话的时候需要设置Adapter，此时会注册emptyView监听者，当请求完数据后需要设置数据，此时如果数据为空会显示EmptyView
         */
        notifyData(new ArrayList<String>(), true);//设置Adapter
        notifyData(new ArrayList<String>(), true);//设置数据
    }

    /**
     * 设置数据Adapter
     */
    public void notifyData(ArrayList<String> list, boolean isClear) {
        if (recyclerAdapter == null) {
            recyclerAdapter = new ZRecyclerAdapter();
            recyclerAdapter.addDatas(list);
            recyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<String>() {
                @Override
                public void onItemClick(View covertView, int position, String data) {
                    Toast.makeText(
                            EmptyViewLayoutActivity.this,
                            position + ":" + data,
                            Toast.LENGTH_SHORT).show();
                }
            });
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
}
