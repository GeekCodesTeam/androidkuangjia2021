package com.geek.libbase.baserecycleview;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.geek.libbase.R;
import com.geek.libbase.base.SlbBaseActivity;
import com.geek.libbase.emptyview.EmptyViewNewNew;
import com.geek.libbase.widgets.TabSelectAdapter;
import com.geek.libbase.widgets.TabUtils;
import com.geek.libbase.widgets.ViewPagerSlide;
import com.geek.libutils.app.MyLogUtil;
import com.google.android.material.tabs.TabLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class SlbBaseActivityViewPage extends SlbBaseActivity {

    protected ImageView ivBack;
    protected TextView tvCenterContent;
    protected TextView tv_right;
    protected EmptyViewNewNew emptyview;
    protected LinearLayout ll_refresh1;
    protected TabLayout tablayoutMy;
    protected ViewPagerSlide viewpager_my1_order;
    protected SlbBaseFragmentViewPagerAdapter orderFragmentPagerAdapter;
    protected String current_id;
    protected boolean enscrolly;

    public boolean isEnscrolly() {
        return enscrolly;
    }

    public void setEnscrolly(boolean enscrolly) {
        this.enscrolly = enscrolly;
    }

    @Override
    protected void setup(@Nullable Bundle savedInstanceState) {
        super.setup(savedInstanceState);
        findview();
        onclick();
        emptyview.loading();
        donetwork();
    }

    private void donetwork() {
        donetworkAdd();
        retryData();
    }

    private void retryData() {
        retryDataAdd();
    }

    protected void init_tablayout(final List<SlbBaseActivityViewPageTabBean1> mlist, List<Fragment> mFragmentList) {
        //
        if (mlist == null || mlist.size() == 0) {
            return;
        }
        List<String> titlesString = new ArrayList<>();
        for (SlbBaseActivityViewPageTabBean1 bean1 : mlist) {
            titlesString.add(bean1.getTab_name());
        }
        current_id = mlist.get(0).getTab_id();
        orderFragmentPagerAdapter = new SlbBaseFragmentViewPagerAdapter(getSupportFragmentManager(), SlbBaseActivityViewPage.this, titlesString, mFragmentList, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewpager_my1_order.setAdapter(orderFragmentPagerAdapter);
        viewpager_my1_order.setOffscreenPageLimit(mFragmentList.size());
        viewpager_my1_order.setScroll(true);
        tablayoutMy.setupWithViewPager(viewpager_my1_order);
//        tablayoutMy.removeAllTabs();
//        for (OneBean1 item : mlist) {
//            tablayoutMy.addTab(tablayoutMy.newTab()
//                    .setTag(item.getTab_id()).setText(item.getTab_name()));
//        }
        tablayoutMy.clearAnimation();
        tablayoutMy.post(new Runnable() {
            @Override
            public void run() {
                TextView textView = new TextView(SlbBaseActivityViewPage.this);
                float selectedSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 16, getResources().getDisplayMetrics());
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, selectedSize);
                textView.setTextColor(getResources().getColor(R.color.color_333333));// color_999999
                textView.setText(Objects.requireNonNull(tablayoutMy.getTabAt(0)).getText());
                textView.setTypeface(Typeface.DEFAULT_BOLD);
                Objects.requireNonNull(tablayoutMy.getTabAt(0)).setCustomView(textView);
            }
        });
        tablayoutMy.addOnTabSelectedListener(new TabSelectAdapter() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                TabUtils.tabSelect(tablayoutMy, tab);
                TextView textView = new TextView(SlbBaseActivityViewPage.this);
                float selectedSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 16, getResources().getDisplayMetrics());
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, selectedSize);
                textView.setTextColor(getResources().getColor(R.color.color_333333));// color_999999
                textView.setText(tab.getText());
                textView.setTypeface(Typeface.DEFAULT_BOLD);
                tab.setCustomView(textView);
                //
                String tag = mlist.get(tab.getPosition()).getTab_id();
                current_id = tag;
                // 这段代码如果单独用tablayout的时候 就需要 因为tablayout会自动执行
//                if (!once) {
//                    once = true;
//                    return;
//                }
                if (null == tag) {
                    return;
                }
                MyLogUtil.e("---geekyun----", current_id);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setCustomView(null);
            }
        });
        TabUtils.setIndicatorWidth(tablayoutMy, 40);
        //
        viewpager_my1_order.setCurrentItem(0, false);
    }

    private void onclick() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        emptyview.bind(ll_refresh1).setRetryListener(new EmptyViewNewNew.RetryListener() {
            @Override
            public void retry() {
                // 主布局
                emptyview.loading();
                retryData();
            }
        });
        onclickAdd();
    }

    private void findview() {
        ivBack = findViewById(R.id.baserecycleview_iv_back1);
        tvCenterContent = findViewById(R.id.baserecycleview_tv_center_content1);
        tv_right = findViewById(R.id.baserecycleview_tv_right1);
        emptyview = findViewById(R.id.baserecycleview_emptyview1);
        ll_refresh1 = findViewById(R.id.baserecycleview_ll_refresh1);
        tablayoutMy = findViewById(R.id.baserecycleview_tab1);
        viewpager_my1_order = findViewById(R.id.baserecycleview_viewpager1);
        findviewAdd();
    }

    protected abstract void findviewAdd();// 初始化

    protected abstract void onclickAdd();// 事件

    protected abstract void donetworkAdd();// 请求接口bufen

    protected abstract void retryDataAdd();// 刷新

    protected void OnOrderSuccess(final List<SlbBaseActivityViewPageTabBean1> mlist, List<Fragment> mFragmentList) {
        emptyview.success();
        init_tablayout(mlist, mFragmentList);
    }

    protected void OnOrderNodata() {
        emptyview.nodata();
    }

    protected void OnOrderFail() {
        emptyview.errorNet();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        super.onResume();
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void updateUI(final Boolean scrolly) {
        setEnscrolly(scrolly);
    }

}
