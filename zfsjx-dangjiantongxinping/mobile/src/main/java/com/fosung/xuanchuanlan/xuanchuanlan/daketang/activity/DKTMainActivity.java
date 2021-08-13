package com.fosung.xuanchuanlan.xuanchuanlan.daketang.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.fosung.frameutils.http.response.ZResponse;
import com.fosung.xuanchuanlan.R;
import com.fosung.xuanchuanlan.common.base.BaseActivity;
import com.fosung.xuanchuanlan.xuanchuanlan.daketang.http.entity.DKTListApply;
import com.fosung.xuanchuanlan.xuanchuanlan.main.http.XCLHttp;
import com.fosung.xuanchuanlan.xuanchuanlan.main.http.XCLHttpUrlMaster;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class DKTMainActivity extends BaseActivity {

    private TabLayout mTabTl;
    private ViewPager mContentVp;
    private List<Fragment> tabFragments; // 存放Fragment的容器
    private List<DKTListApply.DatalistBean> tabIndicators; // 存放Fragment的容器
    private ContentPagerAdapter contentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dktmain);
        setToolbarTitle("大课堂");
        requestTypeData();
        initRes();
    }

    private void requestTypeData() {

        Map<String, String> map = new HashMap<String, String>();

        XCLHttp.post(XCLHttpUrlMaster.DKTMAINTYPE, map, new ZResponse<DKTListApply>(DKTListApply.class) {

            @Override
            public void onSuccess(Response response, DKTListApply resObj) {
                if (resObj.datalist != null && resObj.datalist.size() > 0) {
                    initContent(resObj.datalist); // 初始化 ViewPager
                } else {
                    Toast.makeText(DKTMainActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(int code, String error) {
                super.onError(code, error);
            }
        });

    }

    private void initRes() {
        mTabTl = (TabLayout) findViewById(R.id.dkt_tablayout);
        mContentVp = (ViewPager) findViewById(R.id.dkt_viewpager);

        initTab(); // 初始化 TabLayout

    }

    private void initTab() {
        // 下面的两行代码是设置 TabLayout 的标签滚动方式 代码改动运行一下就知道了
        mTabTl.setTabMode(TabLayout.MODE_SCROLLABLE); // 设置 TabLayout 模式

        mTabTl.setTabTextColors(ContextCompat.getColor(this, R.color.black), ContextCompat.getColor(this, R.color.red)); // 设置 TabLayout 的 Title 的 Text 颜色
        mTabTl.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.red)); // 设置选中后的 Text 颜色
        ViewCompat.setElevation(mTabTl, 10); // 设置 阴影

        // 下面这句代码很关键!!! 是这句代码起到了 使 TabLayout 与 ViewPager 相关联的作用
        // 下面这句代码有个小 BUG 要注意! setupWithViewPager会清空标签的Title
        mTabTl.setupWithViewPager(mContentVp); // 设置 TabLayout 的 ViewPager(相关联)
    }

    private void initContent(List<DKTListApply.DatalistBean> typeList) {
        tabIndicators = typeList;
        tabFragments = new ArrayList<>(); // 创建盛放 Fragment 的容器 (List)
        for (int i = 0; i < typeList.size(); i++) {

            DKTListApply.DatalistBean model = typeList.get(i);

            DKTMainFragment fragment = new DKTMainFragment();
            Bundle bundle = new Bundle();
            bundle.putString("id", model.id);
            fragment.setArguments(bundle);

            tabFragments.add(fragment);
        }

        contentAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        mContentVp.setAdapter(contentAdapter);
    }


    class ContentPagerAdapter extends FragmentPagerAdapter // ViewPager 的适配器 这里用的匿名类 FragmentStatePagerAdapter
    {
        ContentPagerAdapter(FragmentManager fm) // 构造器
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) // 按照 position 从 List 中依次取出 PagerFragment
        {
            return tabFragments.get(position);
        }

        @Override
        public int getCount() // 返回 ViewPager 中 页面的总数
        {
            return tabIndicators.size();
        }

        @Override
        public CharSequence getPageTitle(int position) // TabLayout 的 Title 就是从这个函数取到的
        {
            return tabIndicators.get(position).typename;
        }
    }

}
