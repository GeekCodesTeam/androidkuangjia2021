package com.example.slbappcomm.fragmentsgeek.demo4;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import androidx.collection.SparseArrayCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.slbappcomm.R;
import com.example.libbase.base.SlbBaseFragment;
import com.example.libbase.base.SlbBaseLazyFragmentNew;
import com.example.slbappcomm.fragmentsgeek.demo4.configs.IWithViewPager;
import com.example.slbappcomm.fragmentsgeek.demo4.configs.MkDemo4Config;
import com.example.slbappcomm.fragmentsgeek.demo4.fragments.MkDemo4Fragment10;
import com.example.slbappcomm.fragmentsgeek.demo4.fragments.MkDemo4Fragment11;
import com.example.slbappcomm.fragmentsgeek.demo4.fragments.MkDemo4Fragment20;
import com.example.slbappcomm.fragmentsgeek.demo4.fragments.MkDemo4Fragment21;
import com.geek.libutils.app.FragmentHelper;
import com.geek.libutils.app.MyLogUtil;
import com.haier.cellarette.baselibrary.bannerview.banner.DotIndicatorView;
import com.haier.cellarette.baselibrary.tablayout.ViewPagerChangeAdapter;
import com.haier.cellarette.baselibrary.tablayout.ViewPagerSlide;

import java.util.List;

public class MkDemo4Activity extends FragmentActivity implements OnClickListener, IWithViewPager {

    private ViewPagerSlide mViewPager;
    private DotIndicatorView mIndicator;

    //当前选中的项
    private int currenttab = -1;

    /**
     * 页面集合
     */
    List<Fragment> fragmentList;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MkDemo4Config.config();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mk_demo4);
        mContext = MkDemo4Activity.this;
        findview();
        addListener();
        doNetWork();
    }

    private void doNetWork() {
        setupViewPager();
        setupFragments();

        //
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mViewPager.setScroll(true);
                showIndicator(true);
            }
        }, 10000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SendToFragment("Demo4Activity");
            }
        }, 20000);
    }


    /**
     * 页面传值操作部分
     *
     * @param id1
     */
    private void SendToFragment(String id1) {
        //举例
//        IndexFoodFragmentUpdateIds iff = new IndexFoodFragmentUpdateIds();
//        iff.setFood_definition_id(id1);
//        iff.setFood_name(id2);
        callFragment("Demo4Activity1", MkDemo4Fragment10.class.getName());
        callFragment("Demo4Activity2", MkDemo4Fragment11.class.getName());
        callFragment("Demo4Activity3", MkDemo4Fragment20.class.getName());
        callFragment("Demo4Activity4", MkDemo4Fragment21.class.getName());
    }

    private void setupViewPager() {
        mViewPager.setScroll(false);
        mViewPager.setOffscreenPageLimit(MkDemo4Config.PAGE_COUNT);
        mViewPager.setAdapter(new IndexPagerAdapter());

        mIndicator.create(MkDemo4Config.PAGE_COUNT);
        mIndicator.setupWithViewPager(mViewPager);
        showIndicator(false);
        withViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPagerChangeAdapter() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
//                    MobEventHelper.onEvent(Demo4Activity.this, "UI2_index_personal_center");//统计
                }
            }
        });

        mViewPager.setCurrentItem(MkDemo4Config.DEFAULT_PAGE_INDEX);//设置当前显示标签页为第一页
    }

    /**
     * 初始化首页fragments
     */
    private void setupFragments() {
        // 使用HierarchyChangeListener的目的是防止在viewpager的itemview还没有准备好就去inflateFragment
        // 带来的问题
        mViewPager.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
            @Override
            public void onChildViewAdded(View parent, View child) {
                if (((ViewGroup) parent).getChildCount() < MkDemo4Config.PAGE_COUNT) {
                    return;
                }
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SparseArrayCompat<Class<? extends SlbBaseFragment>> array = MkDemo4Config.getFragments();
                int size = array.size();
                SlbBaseFragment item;
                for (int i = 0; i < size; i++) {
                    item = FragmentHelper.newFragment(array.valueAt(i), null);
                    ft.replace(array.keyAt(i), item, item.getClass().getName());
                }
                ft.commitAllowingStateLoss();
            }

            @Override
            public void onChildViewRemoved(View parent, View child) {

            }
        });


    }


    private void addListener() {

    }

    private void findview() {
        mViewPager =  findViewById(R.id.viewpager_my);
        mIndicator =  findViewById(R.id.indicator);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showIndicator(boolean show) {
        mIndicator.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void withViewPager(ViewPager viewPager) {
        mIndicator.setupWithViewPager(viewPager);
    }

    /**
     * 首页viewpager adapter
     */
    public class IndexPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            MyLogUtil.d(MkDemo4Config.PAGE_LAYOUT_ID + position);
            int layoutId = getResources().getIdentifier(MkDemo4Config.PAGE_LAYOUT_ID + position, "layout", getPackageName());
            if (layoutId == 0) {
                throw new UnsupportedOperationException("layout not found!");
            }
            View itemLayout = LayoutInflater.from(MkDemo4Activity.this).inflate(layoutId, container, false);
            container.addView(itemLayout);
            return itemLayout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    //手动设置ViewPager要显示的视图
    public void changeView(int desTab) {
        mViewPager.setCurrentItem(desTab, true);
    }

//    public IndexViewPager getViewPager() {
//        return mViewPager;
//    }

    /**
     * fragment间通讯bufen
     *
     * @param value 要传递的值
     * @param tag   要通知的fragment的tag
     */
    public void callFragment(Object value, String... tag) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment;
        for (String item : tag) {
            if (TextUtils.isEmpty(item)) {
                continue;
            }

            fragment = fm.findFragmentByTag(item);
            if (fragment != null && fragment instanceof SlbBaseLazyFragmentNew) {
                ((SlbBaseLazyFragmentNew) fragment).call(value);
            }
        }
    }
}
