package com.haier.cellarette.baselibrary.liandong.demo2;


import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.haier.cellarette.baselibrary.R;
import com.haier.cellarette.baselibrary.liandong.demo2.adapter.Liandong2CategoryAdapter;
import com.haier.cellarette.baselibrary.liandong.demo2.adapter.Liandong2MainAdapter;
import com.haier.cellarette.baselibrary.liandong.demo2.bean.Liandong2CategoryBean;
import com.haier.cellarette.baselibrary.liandong.demo2.bean.Liandong2FoodBean;

import java.util.ArrayList;
import java.util.List;

public class Liandong2Activity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    RecyclerView rcvCategory;
    RecyclerView rcvMain;

    Liandong2CategoryAdapter mLiandong2CategoryAdapter;
    Liandong2MainAdapter mLiandong2MainAdapter;
    List<Liandong2CategoryBean> mCategoryList = new ArrayList<>();
    List<Liandong2FoodBean> mFoodList = new ArrayList<>();

    String[] mCategoryArr = {"经典小食", "主食汉堡", "汉堡套餐", "中式简餐", "招牌炸鸡", "畅爽饮品", "热饮", "奶茶类", "咖啡类", "特色盖浇饭", "单人套餐", "海鲜"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liandong2);
        rcvCategory = findViewById(R.id.rcv_left);
        rcvMain = findViewById(R.id.rcv_main);

        rcvCategory.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mLiandong2CategoryAdapter = new Liandong2CategoryAdapter(this, rcvCategory, mCategoryList);
        rcvCategory.setAdapter(mLiandong2CategoryAdapter);

        rcvMain.setLayoutManager(new LinearLayoutManager(this));
        mLiandong2MainAdapter = new Liandong2MainAdapter(this, rcvMain, mFoodList);
        rcvMain.setAdapter(mLiandong2MainAdapter);

        initEvent();
        initData();
    }

    private int firstVisibleItemPosition = -1;
    private int lastVisibleItemPosition = -1;

    private void initEvent() {
        rcvMain.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
//                    Log.e("onScrollStateChanged", "scroll_state_dragging");
//                } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    Log.e("onScrollStateChanged", "scroll_state_idle");
//
//                } else if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
//                    Log.e("onScrollStateChanged", "scroll_state_settling");
//                }

//                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
//                    firstVisibleItemPosition = ((LinearLayoutManager) rcvMain.getLayoutManager()).findFirstVisibleItemPosition();
//                    Log.e("onScrollStateChanged", "onScrollStateChanged: " + firstVisibleItemPosition);
//                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.e(TAG, "onScrolled: " + dx + " -- " + dy);
                firstVisibleItemPosition = ((LinearLayoutManager) rcvMain.getLayoutManager()).findFirstVisibleItemPosition();
                lastVisibleItemPosition = ((LinearLayoutManager) rcvMain.getLayoutManager()).findLastVisibleItemPosition();

                if (mLiandong2MainAdapter.isMove()) {//第二次滚动
                    //在这里进行第二次滚动

                    //获取要置顶的项在当前屏幕的位置，mIndex是记录的要置顶项在RecyclerView中的位置
                    int n = mLiandong2MainAdapter.getIndex() - firstVisibleItemPosition;
                    if (0 <= n && n < rcvMain.getChildCount()) {
                        //获取要置顶的项顶部离RecyclerView顶部的距离
                        int top = rcvMain.getChildAt(n).getTop();
                        //最后的移动
                        rcvMain.scrollBy(0, top);
                    }
                    mLiandong2MainAdapter.setMove(false);
                } else {         //手动滚动
                    if (dy > 0) {//上滑
                        if (mLiandong2MainAdapter.getData().get(firstVisibleItemPosition).getGroup() !=
                                mLiandong2CategoryAdapter.getData().get(mLiandong2CategoryAdapter.getCheckedItem()).getGroupId()) {
                            mLiandong2CategoryAdapter.updateCheck(mLiandong2MainAdapter.getData().get(firstVisibleItemPosition).getGroup());
                        }
                    } else if (dy < 0) {//下滑
                        if (mLiandong2MainAdapter.getData().get(firstVisibleItemPosition).getGroup() !=
                                mLiandong2CategoryAdapter.getData().get(mLiandong2CategoryAdapter.getCheckedItem()).getGroupId()) {
                            mLiandong2CategoryAdapter.updateCheck(mLiandong2MainAdapter.getData().get(firstVisibleItemPosition).getGroup());
                        }
                    }
                }
            }
        });

        mLiandong2CategoryAdapter.setOnItemClickListener(new Liandong2CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mLiandong2MainAdapter.updatePosition(mLiandong2CategoryAdapter.getData().get(position).getGroupId());
            }
        });
    }

    private void initData() {
        mLiandong2CategoryAdapter.notifyDataSetChanged();
        mLiandong2MainAdapter.notifyDataSetChanged();
        mLiandong2CategoryAdapter.updateCheck(0);
    }

    {

        for (int i = 0; i < mCategoryArr.length; i++) {
            Liandong2CategoryBean bean = new Liandong2CategoryBean();
            bean.setTitle(mCategoryArr[i]);
            bean.setGroupId(i);
            mCategoryList.add(bean);
        }

        for (int i = 0; i < mCategoryArr.length; i++) {
            Liandong2FoodBean groupBean = new Liandong2FoodBean();
            groupBean.setGroupId(true);
            groupBean.setGroup(i);
            groupBean.setGroupTitle(mCategoryArr[i]);
            mFoodList.add(groupBean);
            for (int j = 0; j < 5; j++) {
                Liandong2FoodBean bean = new Liandong2FoodBean();
                bean.setGroup(i);
                bean.setImageId(R.drawable.img00);
                bean.setTitle(mCategoryArr[i] + j);
                mFoodList.add(bean);
            }
        }
    }
}
