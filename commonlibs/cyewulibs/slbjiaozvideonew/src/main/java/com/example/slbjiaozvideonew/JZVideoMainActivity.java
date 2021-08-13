package com.example.slbjiaozvideonew;

import static cn.jzvd.Jzvd.backPress;

import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;

public class JZVideoMainActivity extends AppCompatActivity {

    private VpAdapter adapter;
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationViewEx;
    private MenuItem menuItem;
    private SparseIntArray items;// used for change ViewPager selected item
    private List<Fragment> fragments;// used for ViewPager adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_jzvideo);

        initView();
        initData();
        initEvent();
    }

    private void initView() {
        viewPager = findViewById(R.id.viewpager);
        bottomNavigationViewEx = findViewById(R.id.bottom_navigation);
//        bottomNavigationViewEx.enableAnimation(false);
//        bottomNavigationViewEx.enableShiftingMode(false);
//        bottomNavigationViewEx.enableItemShiftingMode(true);
    }

    private void initData() {
        fragments = new ArrayList<>(4);
        items = new SparseIntArray(4);

        Fragment1Base basicsFragment = new Fragment1Base();
        Fragment2Custom customFragment = new Fragment2Custom();
        Fragment3List complexFragment = new Fragment3List();
        Fragment4More otherFragment = new Fragment4More();

        fragments.add(basicsFragment);
        fragments.add(customFragment);
        fragments.add(complexFragment);
        fragments.add(otherFragment);

        items.put(R.id.i_base, 0);
        items.put(R.id.i_custom, 1);
        items.put(R.id.i_complex, 2);
        items.put(R.id.i_other, 3);

        adapter = new VpAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(items.size());
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (menuItem != null) {
                menuItem.setChecked(false);
            } else {
                bottomNavigationViewEx.getMenu().getItem(0).setChecked(false);
            }
            menuItem = bottomNavigationViewEx.getMenu().getItem(position);
            menuItem.setChecked(true);

            Jzvd.releaseAllVideos();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        private int previousPosition = -1;

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int position = items.get(item.getItemId());
            if (previousPosition != position) {
                previousPosition = position;
                viewPager.setCurrentItem(position);
            }
            return true;
        }
    };

    private void initEvent() {
        bottomNavigationViewEx.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        viewPager.addOnPageChangeListener(pageChangeListener);
    }

    private static class VpAdapter extends FragmentPagerAdapter {
        private List<Fragment> data;

        public VpAdapter(FragmentManager fm, List<Fragment> data) {
            super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Fragment getItem(int position) {
            return data.get(position);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        Jzvd.goOnPlayOnResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.goOnPlayOnPause();
    }

    @Override
    public void onBackPressed() {
        if (backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Jzvd.releaseAllVideos();
        navigationItemSelectedListener = null;
        bottomNavigationViewEx.setOnNavigationItemSelectedListener(null);
        viewPager.removeOnPageChangeListener(pageChangeListener);
    }
}
