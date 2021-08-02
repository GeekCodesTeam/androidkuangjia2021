package com.example.slbappcomm.fragmentsgeek.demo5;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.slbappcomm.R;
import com.example.slbappcomm.fragmentsgeek.demo5.domain.MkShopCategoryItem;
import com.example.slbappcomm.fragmentsgeek.demo5.fragment.MkFragmentContent;
import com.example.slbappcomm.fragmentsgeek.demo5.fragment.MkFragmentIndex;
import com.google.android.material.tabs.TabLayout;
import com.haier.cellarette.baselibrary.tablayout.TabSelectAdapter;
import com.haier.cellarette.baselibrary.tablayout.UnAnimTabLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MkDemo5Activity extends AppCompatActivity {

    private static final String INDEX_TAG = "index";
    private static final String LIST_TAG = "list";
    public UnAnimTabLayout mCateTabLayout;
    private List<MkShopCategoryItem> mDataTablayout;

    private MkFragmentIndex mShopIndexFragment; // 商城首页
    private MkFragmentContent mShopGoodsListFragment; // 商城商品列表

    private FragmentManager mFragmentManager;
    private boolean once;

    /**
     * 当系统内存不足，Fragment 的宿主 Activity 回收的时候，Fragment 的实例并没有随之被回收。
     * Activity 被系统回收时，会主动调用 onSaveInstance() 方法来保存视图层（View Hierarchy），
     * 所以当 Activity 通过导航再次被重建时，之前被实例化过的 Fragment 依然会出现在 Activity 中，
     * 此时的 FragmentTransaction 中的相当于又再次 add 了 fragment 进去的，hide()和show()方法对之前保存的fragment已经失效了，所以就出现了重叠。
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mk_demo5);
        findview();
        onclick();

        mFragmentManager = getSupportFragmentManager();
        if (savedInstanceState!=null){
            mShopIndexFragment = (MkFragmentIndex) mFragmentManager.findFragmentByTag(INDEX_TAG);
            mShopGoodsListFragment = (MkFragmentContent) mFragmentManager.findFragmentByTag(LIST_TAG);
        }
        donetwork();
    }

    private void donetwork() {
        data_tablayout();

        showFragment(MkShopCategoryItem.DEF_TAG_ID);
    }

    private void onclick() {
        mCateTabLayout.addOnTabSelectedListener(new TabSelectAdapter() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String tag = (String) tab.getTag();
                if (!once) {
                    once = true;
                    return;
                }
                if (TextUtils.isEmpty(tag)) {
                    return;
                }
                showFragment(tag);
//                hookForNoAnimator();
            }
        });

    }

    private void findview() {
        mCateTabLayout = findViewById(R.id.tab);

    }

    private void showFragment(String tag) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideFragments(transaction);

        if (tag.equalsIgnoreCase(MkShopCategoryItem.DEF_TAG_ID)) { // 推荐fragment
            if (mShopIndexFragment == null) {
                mShopIndexFragment = new MkFragmentIndex();
                transaction.add(R.id.container, mShopIndexFragment, INDEX_TAG);
            } else {
                transaction.show(mShopIndexFragment);
                mShopIndexFragment.initData();
            }
        } else {
            if (mShopGoodsListFragment == null) {
                mShopGoodsListFragment = new MkFragmentContent();
                Bundle args = new Bundle();
                args.putString("tablayoutId", tag);
                mShopGoodsListFragment.setArguments(args);
                transaction.add(R.id.container, mShopGoodsListFragment, LIST_TAG);
            } else {
                transaction.show(mShopGoodsListFragment);
                mShopGoodsListFragment.getCate(tag);
            }
        }

        transaction.commitAllowingStateLoss();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mShopIndexFragment != null) {
            transaction.hide(mShopIndexFragment);
        }
        if (mShopGoodsListFragment != null) {
            transaction.hide(mShopGoodsListFragment);
        }
    }

    /**
     * hook掉当前选中tab， 从而达到禁用滑动动画效果， 解决动画卡断问题
     */
    private void hookForNoAnimator() {
        try {
            Field f = mCateTabLayout.getClass().getDeclaredField("mScrollAnimator");
            f.setAccessible(true);
            f.set(mCateTabLayout, null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void data_tablayout() {
        mDataTablayout = new ArrayList<>();
        mDataTablayout.add(new MkShopCategoryItem("1", "shining01"));
        mDataTablayout.add(new MkShopCategoryItem("2", "shining02"));
        mDataTablayout.add(new MkShopCategoryItem("3", "shining03"));
        mDataTablayout.add(new MkShopCategoryItem("4", "shining04"));
        mDataTablayout.add(new MkShopCategoryItem("5", "shining05"));
        mDataTablayout.add(new MkShopCategoryItem("6", "shining06"));

        mDataTablayout.add(0, buildFixedCate());

        for (MkShopCategoryItem item : mDataTablayout) {
            mCateTabLayout.addTab(mCateTabLayout.newTab()
                    .setTag(item.getCategory2_id()).setText(item.getCategory2_name()));
        }
    }

    /**
     * 创建固定的类目（本地创建“推荐”）
     *
     * @return
     */
    private MkShopCategoryItem buildFixedCate() {
        MkShopCategoryItem item = new MkShopCategoryItem();
        item.setCategory2_name(MkShopCategoryItem.DEF_TAG_NAME);
        item.setCategory2_id(MkShopCategoryItem.DEF_TAG_ID);
        return item;
    }

    @Override
    protected void onDestroy() {
//        mPresenter.onDestory();
        super.onDestroy();
    }
}