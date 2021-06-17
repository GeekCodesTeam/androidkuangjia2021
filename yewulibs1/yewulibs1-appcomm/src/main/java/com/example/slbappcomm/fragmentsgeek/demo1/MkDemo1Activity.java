package com.example.slbappcomm.fragmentsgeek.demo1;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.annotation.Nullable;
import androidx.collection.SparseArrayCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.libbase.base.SlbBaseActivity;
import com.example.slbappcomm.R;
import com.example.libbase.base.SlbBaseFragment;
import com.example.libbase.base.SlbBaseLazyFragmentNew;
import com.example.slbappcomm.fragmentsgeek.demo1.factorys.MkDemo1FragmentFactory;
import com.geek.libutils.app.FragmentHelper;


public class MkDemo1Activity extends SlbBaseActivity implements OnClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mk_demo1;
    }

    @Override
    protected void setup(@Nullable Bundle savedInstanceState) {
        super.setup(savedInstanceState);
        findview();
        onclickListener();
        doNetWork();
    }

    private void doNetWork() {

    }

    private void onclickListener() {

    }

    private void findview() {
        setupFragments();
    }


    /**
     * 初始化首页fragments
     */
    private void setupFragments() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        SparseArrayCompat<Class<? extends SlbBaseFragment>> array = MkDemo1FragmentFactory.get();//一个版本模式bufen
        int size = array.size();
        SlbBaseFragment item;
        for (int i = 0; i < size; i++) {
            item = FragmentHelper.newFragment(array.valueAt(i), null);
            ft.replace(array.keyAt(i), item, item.getClass().getName());
        }
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onClick(View v) {

    }

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
