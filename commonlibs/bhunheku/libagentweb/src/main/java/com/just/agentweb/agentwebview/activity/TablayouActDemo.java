package com.just.agentweb.agentwebview.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.just.agentweb.R;
import com.just.agentweb.agentwebview.adapter.TabFragmentPagerAdapter;
import com.just.agentweb.agentwebview.adapter.Tablayoutdapter;
import com.just.agentweb.agentwebview.bean.HCategoryBean;
import com.just.agentweb.agentwebview.bean.HCategoryBean1;
import com.just.agentweb.agentwebview.bean.OneBean1;
import com.just.agentweb.agentwebview.fragment.F2NewItem1;
import com.just.agentweb.agentwebview.fragment.F2NewItem2;
import com.just.agentweb.agentwebview.widgets.ViewPagerSlide;
import com.just.agentweb.agentwebview.widgets.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TablayouActDemo extends BaseActWebActivity {
    private ViewPagerSlide viewpagerBaseactTablayout;
    private XRecyclerView recyclerViewTitle;

    private String current_id;
    private Tablayoutdapter mAdapter11;
    private List<OneBean1> mDataTablayout;
    private TabFragmentPagerAdapter tabFragmentPagerAdapter;

    private MessageReceiverIndex mMessageReceiver;

    public class MessageReceiverIndex extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if ("TablayouActDemo".equals(intent.getAction())) {
                }
            } catch (Exception e) {
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview_tablayout;
    }

    @Override
    protected void setup(@Nullable Bundle savedInstanceState) {
        super.setup(savedInstanceState);
        viewpagerBaseactTablayout = findViewById(R.id.viewpager_baseact_tablayout);
        recyclerViewTitle = findViewById(R.id.recycler_view_title);
        mMessageReceiver = new MessageReceiverIndex();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction("TablayouActDemo");
        LocalBroadcastManagers.getInstance(this).registerReceiver(mMessageReceiver, filter);
        tvTitleName.setText("Tab测试");
        onclick();
        donetworkdemo();
    }


    private void donetworkdemo() {
        HCategoryBean hCategoryBean = new HCategoryBean();
        List<HCategoryBean1> mDataTablayout1 = new ArrayList<>();
        mDataTablayout1.add(new HCategoryBean1("1", "测试11111"));
        mDataTablayout1.add(new HCategoryBean1("2", "测试22222"));
        hCategoryBean.setList(mDataTablayout1);
        mDataTablayout = new ArrayList<>();
        for (int i = 0; i < hCategoryBean.getList().size(); i++) {
            if (i == 0) {
                mDataTablayout.add(new OneBean1(hCategoryBean.getList().get(i).getCode(), hCategoryBean.getList().get(i).getName(), true));
            } else {
                mDataTablayout.add(new OneBean1(hCategoryBean.getList().get(i).getCode(), hCategoryBean.getList().get(i).getName(), false));
            }
        }
        current_id = mDataTablayout.get(0).getTab_id();
        mAdapter11.setNewData(mDataTablayout);
        init_viewp(mDataTablayout);
        Log.e("---geekyun----", current_id);
    }


    private void onclick() {
        //
        recyclerViewTitle.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
//        JackSnapHelper mLinearSnapHelper = new JackSnapHelper(JackSnapHelper.TYPE_SNAP_START);
//        mLinearSnapHelper.attachToRecyclerView(recyclerView1Order11);
        mDataTablayout = new ArrayList<>();
        mAdapter11 = new Tablayoutdapter();
        recyclerViewTitle.setAdapter(mAdapter11);
        mAdapter11.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                OneBean1 bean1 = (OneBean1) adapter.getData().get(position);
//                set_footer_change(bean1);
                //
                current_id = bean1.getTab_id();
                // 这段代码如果单独用tablayout的时候 就需要 因为tablayout会自动执行
//                if (!once) {
//                    once = true;
//                    return;
//                }
                if (null == current_id) {
                    return;
                }
                if (bean1.isEnable()) {
                    // 不切换当前的item点击 刷新当前页面
                    return;
                }
                Log.e("---geekyun----", current_id);
                Log.e("---geekyun-position---", position + "");
                viewpagerBaseactTablayout.setCurrentItem(position, true);
            }
        });
    }

    public static final String URL_KEY = "url_key";

    private void init_viewp(List<OneBean1> mlist) {
        if (mlist == null || mlist.size() == 0) {
            return;
        }
        List<Fragment> mFragmentList = new ArrayList<>();
        for (int i = 0; i < mlist.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putString("id", mlist.get(i).getTab_id());
            if (i == 0) {
                bundle.putString(URL_KEY, "file:///android_asset/html/hello.html");
                F2NewItem1 fragment1 = FragmentHelper.newFragment(F2NewItem1.class, bundle);
                mFragmentList.add(fragment1);
            } else {
                bundle.putString(URL_KEY, "https://www.baidu.com/");
                F2NewItem2 fragment2 = FragmentHelper.newFragment(F2NewItem2.class, bundle);
                mFragmentList.add(fragment2);
            }
        }
        tabFragmentPagerAdapter = new TabFragmentPagerAdapter(this.getSupportFragmentManager(), this, mFragmentList);
        viewpagerBaseactTablayout.setAdapter(tabFragmentPagerAdapter);
        viewpagerBaseactTablayout.setOffscreenPageLimit(4);
        viewpagerBaseactTablayout.setScroll(true);
        viewpagerBaseactTablayout.setCurrentItem(0, true);
        viewpagerBaseactTablayout.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
//                curPosition = position;
                OneBean1 bean1 = mAdapter11.getData().get(position);
                set_footer_change(bean1);
            }
        });
    }

    /**
     * 业务逻辑部分
     */

    private void initList() {
        for (int i = 0; i < mAdapter11.getData().size(); i++) {
            OneBean1 item = mAdapter11.getData().get(i);
            if (item.isEnable()) {
                item.setEnable(false);
            }
        }
    }


    private void set_footer_change(OneBean1 model) {
        if (model.isEnable()) {
            // 不切换当前的item点击 刷新当前页面
            return;
        } else {
            // 切换到另一个item
            //设置为选中
            initList();
            model.setEnable(true);
            mAdapter11.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManagers.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    @NonNull
    @Override
    protected ViewGroup getAgentWebParent() {
        return null;
    }
}
