package com.haier.cellarette.baselibrary.liandong.demo1;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.haier.cellarette.baselibrary.R;
import com.haier.cellarette.baselibrary.liandong.demo1.adapter.Liandong1LeftAdapterLiandong1;
import com.haier.cellarette.baselibrary.liandong.demo1.adapter.Liandong1RightAdapterLiandong1;
import com.haier.cellarette.baselibrary.liandong.demo1.base.Liandong1SimpleRecyclerAdapter;
import com.haier.cellarette.baselibrary.liandong.demo1.bean.Liandong1SortBean;
import com.haier.cellarette.baselibrary.liandong.demo1.bean.Liandong1SortItem;
import com.haier.cellarette.baselibrary.liandong.demo1.contants.Liandong1ItemType;
import com.haier.cellarette.baselibrary.liandong.demo1.utils.Liandong1MyUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Liandong1Activity extends AppCompatActivity {

    private RecyclerView leftRecyclerView;
    private RecyclerView rightRecyclerView;

    private Liandong1LeftAdapterLiandong1 liandong1LeftAdapter;
    private Liandong1RightAdapterLiandong1 liandong1RightAdapter;

    private final List<Liandong1SortBean> mLeftList = new ArrayList<>();

    private final List<Liandong1SortItem> mRightList = new ArrayList<>();

    private final Map<Integer, Integer> indexMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liandong1);
        initView();
    }

    private void initView() {
        leftRecyclerView = (RecyclerView) findViewById(R.id.rv_sort_left) ;
        rightRecyclerView = (RecyclerView) findViewById(R.id.rv_sort_right);
        // 左列表
        leftRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ((SimpleItemAnimator) leftRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        liandong1LeftAdapter = new Liandong1LeftAdapterLiandong1();
        liandong1LeftAdapter.setListData(mLeftList);
        leftRecyclerView.setAdapter(liandong1LeftAdapter);
        // 左侧列表的点击事件
        liandong1LeftAdapter.setOnItemClickListener(new Liandong1SimpleRecyclerAdapter.OnItemClickListener<Liandong1SortBean>() {
            @Override
            public void onItemClick(Liandong1SortBean item, int index) {
                // 左侧选中并滑到中间位置
                liandong1LeftAdapter.setSelectedPosition(index);
                Liandong1MyUtils.moveToMiddle(leftRecyclerView, index);
                // 右侧滑到对应位置
                ((GridLayoutManager)rightRecyclerView.getLayoutManager())
                        .scrollToPositionWithOffset(indexMap.get(index),0);
            }
        });
        // 右列表
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){
            @Override
            public int getSpanSize(int position) {
                if (mRightList.get(position).viewType == Liandong1ItemType.BIG_SORT) {
                    return 3;
                } else {
                    return 1;
                }
            }
        });
        rightRecyclerView.setLayoutManager(gridLayoutManager);
        liandong1RightAdapter = new Liandong1RightAdapterLiandong1();
        liandong1RightAdapter.setListData(mRightList);
        rightRecyclerView.setAdapter(liandong1RightAdapter);
        liandong1RightAdapter.setOnItemClickListener(new Liandong1SimpleRecyclerAdapter.OnItemClickListener<Liandong1SortItem>() {
            @Override
            public void onItemClick(Liandong1SortItem item, int index) {
                Toast.makeText(Liandong1Activity.this, item.name, Toast.LENGTH_SHORT).show();
            }
        });
        //右侧列表的滚动事件
        rightRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //获取右侧列表的第一个可见Item的position
                int topPosition = ((GridLayoutManager) rightRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                // 如果此项对应的是左边的大类的index
                if (mRightList.get(topPosition).position != -1) {
                    Liandong1MyUtils.moveToMiddle(leftRecyclerView, mRightList.get(topPosition).position);
                    liandong1LeftAdapter.setSelectedPosition(mRightList.get(topPosition).position);
                }

            }
        });
    }

    {

        // 构造点数据，比如整个数据刚刚好就是从json转过来的，一个Bean里面有一个大类，有若干个小类
        // 左侧的adapter就直接用这个构造好的list
        for (int i = 0; i < 30; i++) {
            Liandong1SortBean bean = new Liandong1SortBean();
            bean.bigSortId = i;
            bean.bigSortName = "大分类" + i;
            List<Liandong1SortBean.ListBean> list = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                Liandong1SortBean.ListBean listBean = new Liandong1SortBean.ListBean();
                listBean.smallSortId = j;
                listBean.smallSortName = "小标签" + j;
                list.add(listBean);
            }
            bean.list = list;
            mLeftList.add(bean);
        }
        // 右侧的list是将每一个大类和小类按次序添加，并且标记大类的位置
        for (int i = 0; i < mLeftList.size(); i++) {
            Liandong1SortItem bigItem = new Liandong1SortItem();
            bigItem.viewType = Liandong1ItemType.BIG_SORT;
            bigItem.id = mLeftList.get(i).bigSortId;
            bigItem.name = mLeftList.get(i).bigSortName;
            // 标记大类的位置，所有项的position默认是-1，如果是大类就添加position，让他和左侧位置对应
            bigItem.position = i;
            mRightList.add(bigItem);
            for (int j = 0; j < mLeftList.get(i).list.size(); j++) {
                Liandong1SortItem smallItem = new Liandong1SortItem();
                smallItem.viewType = Liandong1ItemType.SMALL_SORT;
                smallItem.id = mLeftList.get(i).list.get(j).smallSortId;
                smallItem.name = mLeftList.get(i).list.get(j).smallSortName;
                mRightList.add(smallItem);
            }
        }
        // 点击左侧需要知道对应右侧的位置，用map先保存起来
        for (int i = 0; i < mRightList.size(); i++) {
            if (mRightList.get(i).position != -1) {
                indexMap.put(mRightList.get(i).position, i);
            }
        }

    }


}
