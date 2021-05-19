package com.haier.cellarette.baselibrary.recycleviewmultitype.activitys;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.GridLayoutManager;
//import androidx.appcompat.widget.LinearLayoutManager;
//import androidx.appcompat.widget.LinearSnapHelper;
//import androidx.appcompat.widget.OrientationHelper;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.haier.cellarette.baselibrary.R;
import com.haier.cellarette.baselibrary.recycleviewmultitype.models.demo1.ItemDemo1;
import com.haier.cellarette.baselibrary.recycleviewmultitype.viewholders.demo1.ItemDemo1Viewholder;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import me.drakeet.multitype.MultiTypeAsserts;


public class RecDemo1Activity extends AppCompatActivity {

    private MultiTypeAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview_demo1);
        mRecyclerView = findViewById(R.id.list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
//        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,1, RecyclerView.VERTICAL,false));
        new LinearSnapHelper().attachToRecyclerView(mRecyclerView);

        Items items = new Items();//Items 等同于 ArrayList<Object>
        mAdapter = new MultiTypeAdapter();
        mAdapter.register(ItemDemo1.class,new ItemDemo1Viewholder("我是从Activity传进去的值"));
        mRecyclerView.setAdapter(mAdapter);
        MultiTypeAsserts.assertHasTheSameAdapter(mRecyclerView,mAdapter);

        for (int i = 0; i < 20; i++) {
            items.add(new ItemDemo1(i+"","url:"+i));
        }
        mAdapter.setItems(items);
        mAdapter.notifyDataSetChanged();
        MultiTypeAsserts.assertAllRegistered(mAdapter,items);
    }
}
