package com.example.slbappcomm.fragmentsgeek.demo1.factorys;

import androidx.collection.SparseArrayCompat;

import com.example.libbase.base.SlbBaseFragment;
import com.example.slbappcomm.R;
import com.example.slbappcomm.fragmentsgeek.demo1.fragments.MkDemo1Fragment1;
import com.example.slbappcomm.fragmentsgeek.demo1.fragments.MkDemo1Fragment2;

public class MkDemo1FragmentFactory {
    private static SparseArrayCompat<Class<? extends SlbBaseFragment>> sIndexFragments = new SparseArrayCompat<>();

    static {

        sIndexFragments.put(R.id.demo1_page_0_item_0, MkDemo1Fragment1.class);//模块1
        sIndexFragments.put(R.id.demo1_page_0_item_1, MkDemo1Fragment2.class);//模块2

    }

    public static Class<? extends SlbBaseFragment> get(int id) {
        if (sIndexFragments.indexOfKey(id) < 0) {
            throw new UnsupportedOperationException("cannot find fragment by " + id);
        }
        return sIndexFragments.get(id);
    }

    public static SparseArrayCompat<Class<? extends SlbBaseFragment>> get() {
        return sIndexFragments;
    }
}
