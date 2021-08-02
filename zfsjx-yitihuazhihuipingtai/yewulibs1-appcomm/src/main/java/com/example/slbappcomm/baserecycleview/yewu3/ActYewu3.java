package com.example.slbappcomm.baserecycleview.yewu3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.slbappcomm.R;
import com.example.slbappcomm.baserecycleview.yewu3.adapter.ZRecyclerAdapter;
import com.zcolin.gui.zrecyclerview.BaseRecyclerAdapter;
import com.zcolin.gui.zrecyclerview.ZRecyclerView;

import java.util.ArrayList;

public class ActYewu3 extends AppCompatActivity {

    private ZRecyclerView recyclerView;
    private ZRecyclerAdapter recyclerAdapter;
    private View headerView2;
    private int mPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview3_main);

        recyclerView = findViewById(R.id.recycler_view);

        //recyclerView.setGridLayout(true, 2);  //默认为LinearLayoutManager
        recyclerView.setOnPullLoadMoreListener(new PullLoadMoreListener());

        //设置数据为空时的EmptyView，DataObserver是注册在adapter之上的，也就是setAdapter是注册上，notifyDataSetChanged的时候才会生效
        recyclerView.setEmptyView(this, R.layout.recycleview3_view_recycler_empty);

        //设置HeaderView和footerView
        recyclerView.addHeaderView(this, R.layout.recycleview3_view_recyclerheader);
        headerView2 = LayoutInflater.from(this).inflate(R.layout.recycleview3_view_recyclerheader, null);
        ((TextView) headerView2.findViewById(R.id.textView)).setText("HEDER 2");
        recyclerView.addHeaderView(headerView2);
        recyclerView.addFooterView(this, R.layout.recycleview3_view_recyclerfooter);

        //recyclerView.setLoadMoreProgressView(view);
        //recyclerView.setIsShowNoMore(false);      //不显示已加载全部
        // recyclerView.setIsLoadMoreEnabled(false);//到底加载是否可用
        // recyclerView.setIsRefreshEnabled(false);//下拉刷新是否可用
        //recyclerView.setIsProceeConflict(true);   //处理与子控件的冲突，如viewpager
        //recyclerView.setLoadMoreFooter(customview implements ILoadMoreFooter);   //设置自定义的加载Footer
        //recyclerView.setLoadMoreText("正在加载...", "正在加载...", "*****已加载全部*****");//设置加载文字
        //recyclerView.addDefaultItemDecoration();//增加默认分割线
        recyclerView.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<Object>() {
            @Override
            public void onItemClick(View covertView, int position, Object data) {
                Toast.makeText(ActYewu3.this, data + "", Toast.LENGTH_SHORT).show();
                if (position == 0) {
                    Intent intent = new Intent(ActYewu3.this, ScrollViewLayoutActivity.class);
                    startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(ActYewu3.this, EmptyViewLayoutActivity.class);
                    startActivity(intent);
                } else if (position == 2) {
                    Intent intent = new Intent(ActYewu3.this, GridLayoutActivity.class);
                    startActivity(intent);
                } else if (position == 3) {
                    Intent intent = new Intent(ActYewu3.this, StaggeredLayoutActivity.class);
                    startActivity(intent);
                } else if (position == 4) {
                    Intent intent = new Intent(ActYewu3.this, MultiTypeLayoutActivity.class);
                    startActivity(intent);
                } else if (position == 5) {
                    Intent intent = new Intent(ActYewu3.this, SwipeMenuLayoutActivity.class);
                    startActivity(intent);
                } else if (position == 6) {
                    Intent intent = new Intent(ActYewu3.this, DecorationActivity.class);
                    startActivity(intent);
                } else if (position == 7) {
                    Intent intent = new Intent(ActYewu3.this, DesignSupportActivity.class);
                    startActivity(intent);
                } else if (position == 8) {
                    Intent intent = new Intent(ActYewu3.this, FlexBoxLayoutActivity.class);
                    startActivity(intent);
                } else if (position == 9) {
                    recyclerView.removeHeaderView(headerView2);
                }
            }
        });

        recyclerView.setOnItemLongClickListener(new BaseRecyclerAdapter.OnItemLongClickListener<Object>() {
            @Override
            public boolean onItemLongClick(View covertView, int position, Object data) {
                recyclerAdapter.getDatas().remove(position);
                recyclerAdapter.notifyItemRemoved(position);
                recyclerAdapter.notifyItemRangeChanged(position, recyclerAdapter.getDatas().size() - position);
                return true;
            }
        });

        notifyData(new ArrayList<String>(), false);
        recyclerView.refreshWithPull();
        // recyclerView.refresh();//没有下拉刷新效果，直接刷新数据
        // recyclerView.setRefreshing(true);只有下拉刷新效果，不刷新数据
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
        }, 300);
    }

    //制造假数据
    private ArrayList<String> setList(int page) {
        ArrayList<String> dataList = new ArrayList<>();
        int start = 15 * (page - 1);
        for (int i = start; i < 15 * page; i++) {
            if (i == 0) {
                dataList.add("ScrollView");
            } else if (i == 1) {
                dataList.add("EmptyViewLayout");
            } else if (i == 2) {
                dataList.add("GridLayout");
            } else if (i == 3) {
                dataList.add("StaggeredGridLayout");
            } else if (i == 4) {
                dataList.add("MultiTypeLayout");
            } else if (i == 5) {
                dataList.add("SwipeMenuLayout");
            } else if (i == 6) {
                dataList.add("Decoration");
            } else if (i == 7) {
                dataList.add("DesignSupportActivity");
            } else if (i == 8) {
                dataList.add("FlexBoxLayoutActivity");
            } else if (i == 9) {
                dataList.add("移除Header2");
            } else {
                dataList.add(String.format("第%d条数据", i));
            }
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
