package com.haier.cellarette.baselibrary.recycleviewmultitype.activitys;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.DividerItemDecoration;
//import androidx.appcompat.widget.LinearLayoutManager;
//import androidx.appcompat.widget.OrientationHelper;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;


import com.haier.cellarette.baselibrary.R;
import com.haier.cellarette.baselibrary.recycleviewmultitype.models.demo3.ItemDemo3;
import com.haier.cellarette.baselibrary.recycleviewmultitype.viewholders.demo3.ItemDemo3Binder;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import me.drakeet.multitype.MultiTypeAsserts;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;

//import static androidx.appcompat.widget.DividerItemDecoration.VERTICAL;

public class RecDemo3Activity extends AppCompatActivity {

    private MultiTypeAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview_demo3);
        mRecyclerView = findViewById(R.id.list);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, VERTICAL));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
//        new LinearSnapHelper().attachToRecyclerView(mRecyclerView);

        mAdapter = new MultiTypeAdapter();
        mAdapter.register(ItemDemo3.class,new ItemDemo3Binder());
        mRecyclerView.setAdapter(mAdapter);
        MultiTypeAsserts.assertHasTheSameAdapter(mRecyclerView,mAdapter);

        Items items = new Items();//Items 等同于 ArrayList<Object>
        for (int i = 0; i < 30; i++) {
            items.add(new ItemDemo3("1000" + i,i+""));
        }
        mAdapter.setItems(items);
        mAdapter.notifyDataSetChanged();
        MultiTypeAsserts.assertAllRegistered(mAdapter,items);
    }
}
