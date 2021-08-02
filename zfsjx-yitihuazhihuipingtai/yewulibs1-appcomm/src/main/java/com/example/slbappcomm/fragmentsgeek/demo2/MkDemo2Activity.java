package com.example.slbappcomm.fragmentsgeek.demo2;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.annotation.Nullable;
import androidx.collection.SparseArrayCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.slbappcomm.R;
import com.example.libbase.base.SlbBaseActivity;
import com.example.libbase.base.SlbBaseFragment;
import com.example.libbase.base.SlbBaseLazyFragmentNew;
import com.example.slbappcomm.fragmentsgeek.demo2.configs.MkDemo2Config;
import com.geek.libutils.app.FragmentHelper;
import com.geek.libutils.app.MyLogUtil;

public class MkDemo2Activity extends SlbBaseActivity implements OnClickListener {

    public final String ST = "STATISTICS";

    public void config() {
//        Context ctx = getApplicationContext();
//        ApplicationInfo info = null;
//        try {
//            info = ctx.getPackageManager().getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        if (info == null) {
//            throw new UnsupportedOperationException();
//        }
//        String klassName = info.metaData.getString(ST);
//        MyLogUtil.d("ST_DATA_klassName", klassName);
        try {
            ApplicationInfo appInfo = getApplicationContext().getPackageManager()
                    .getApplicationInfo(getApplicationContext().getPackageName(),
                            PackageManager.GET_META_DATA);
            String klassName = appInfo.metaData.getString(ST);
            MyLogUtil.d("ST_DATA_activity", "ST=" + klassName);
            if (klassName.equals(".ceshi")) {
//                ServiceAddr.setIsDebugServer(true);
            } else if (klassName.equals(".yushengchan")) {
//                ServiceAddr.setIsDebugServer(true);
            } else if (klassName.equals(".xianshang")) {
//                ServiceAddr.setIsDebugServer(false);
            }
            MyLogUtil.d("ST_DATA_activity", "ST_DATA=" /*+ ServiceAddr.isIsDebugServer()*/);
//            String aaa = "JCT_310_1157_0000";
//            MyLogUtil.d("ST_DATA", aaa.split("_")[0]);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//
//        setupFragments();
//        findview();
//        onclickListener();
//        doNetWork();
//    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mk_demo2;
    }

    @Override
    protected void setup(@Nullable Bundle savedInstanceState) {
        super.setup(savedInstanceState);
        MkDemo2Config.config();
        config();
        setupFragments();
        findview();
        onclickListener();
        doNetWork();

    }

    /**
     * 初始化首页fragments
     */
    private void setupFragments() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //TODO 多版本模式bufen
        SparseArrayCompat<Class<? extends SlbBaseFragment>> array = MkDemo2Config.getFragments();//
        int size = array.size();
        SlbBaseFragment item;
        for (int i = 0; i < size; i++) {
            item = FragmentHelper.newFragment(array.valueAt(i), null);
            ft.replace(array.keyAt(i), item, item.getClass().getName());
        }
        ft.commitAllowingStateLoss();
    }

//    private SparseArrayCompat<Class<? extends BaseFragment>> which_version_fragment_config() {
//        if (ConstantNetUtil.VERSION_APK == NetConfig.version_name1) {
//            return Demo2Config1.getFragments();
//        } else if (ConstantNetUtil.VERSION_APK == NetConfig.version_name2) {
//            return Demo2Config2.getFragments();
//        }
//        return Demo2Config1.getFragments();
//    }

    private void doNetWork() {

    }

    private void onclickListener() {

    }

    private void findview() {

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
