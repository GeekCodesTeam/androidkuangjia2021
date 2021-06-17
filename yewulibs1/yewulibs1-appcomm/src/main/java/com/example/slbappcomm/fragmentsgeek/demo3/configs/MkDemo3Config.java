package com.example.slbappcomm.fragmentsgeek.demo3.configs;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import androidx.collection.SparseArrayCompat;
import com.example.libbase.base.SlbBaseFragment;
import com.geek.libutils.app.BaseApp;
import com.geek.libutils.app.MyLogUtil;

public class MkDemo3Config {

    private static final String INDEX_META_DATA = "DEMO3_CONFIG";

    /** viewpager页大小*/
    public static int PAGE_COUNT;
    /** viewpager每页的itemview id*/
    public static String PAGE_LAYOUT_ID;

    /** 默认显示第几页*/
    public static int DEFAULT_PAGE_INDEX;

    /**
     * fragment配置
     */
    private static SparseArrayCompat<Class<? extends SlbBaseFragment>> sIndexFragments = new SparseArrayCompat<>();

    public static void config() {
        Context ctx = BaseApp.get();
        ApplicationInfo info = null;

        try {
            info = ctx.getPackageManager().getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (info == null) {
            throw new UnsupportedOperationException();
        }

        String klassName = info.metaData.getString(INDEX_META_DATA);
        if (TextUtils.isEmpty(klassName)) {
            throw new UnsupportedOperationException("please config " + INDEX_META_DATA + " value");
        }

//        if (klassName.startsWith(".")) {
//            klassName = App.get().getPackageName() + klassName;
//        }

        MyLogUtil.d("geek", klassName);

        try {
            Class<?> klass = Class.forName(klassName);
            klass.getDeclaredMethod("setup").invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Class<? extends SlbBaseFragment> getFragment(int id) {
        if (sIndexFragments.indexOfKey(id) < 0) {
            throw new UnsupportedOperationException("cannot find fragment by " + id);
        }
        return sIndexFragments.get(id);
    }

    public static SparseArrayCompat<Class<? extends SlbBaseFragment>> getFragments() {
        return sIndexFragments;
    }
}
