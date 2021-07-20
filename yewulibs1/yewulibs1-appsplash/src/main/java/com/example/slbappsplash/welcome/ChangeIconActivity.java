package com.example.slbappsplash.welcome;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.libbase.base.SlbBaseActivity;
import com.example.slbappsplash.R;

public class ChangeIconActivity extends SlbBaseActivity {

    private String[] strings = new String[]{
            "com.example.slbappsplash.DefaultAlias",
            "com.example.slbappsplash.NewActivity1",
            "com.example.slbappsplash.NewActivity2",
            "com.example.slbappsplash.NewActivity3",
            "com.example.slbappsplash.NewActivity4",
            "com.example.slbappsplash.NewActivity5",
            "com.example.slbappsplash.NewActivity6",
            "com.example.slbappsplash.NewActivity7"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_changeactivity;
    }

    @Override
    protected void setup(@Nullable Bundle savedInstanceState) {
        super.setup(savedInstanceState);

    }

    /**
     * 设置Activity为启动入口
     *
     * @param view
     */
    public void setActivity(View view) {
        Toast.makeText(this, "直接设置Activity是会有很多问题的，不要这么做哦！", Toast.LENGTH_SHORT).show();

        // 这种方式不要用。
//        PackageManager packageManager = getPackageManager();
//        packageManager.setComponentEnabledSetting(new ComponentName(this, getPackageName() +
//                ".NewActivity1"), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager
//                .DONT_KILL_APP);
//        packageManager.setComponentEnabledSetting(new ComponentName(this, getPackageName() +
//                ".NewActivity2"), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager
//                .DONT_KILL_APP);
//        packageManager.setComponentEnabledSetting(new ComponentName(this, getPackageName() +
//                ".MainActivity"), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager
//                .DONT_KILL_APP);
    }


    /**
     * 设置默认的别名为启动入口
     *
     * @param view
     */
    public void setDefaultAlias(View view) {
        Toast.makeText(this, "已切换，生效中", Toast.LENGTH_SHORT).show();
        set_alias(strings, strings[0]);
    }

    /**
     * 设置别名1为启动入口
     *
     * @param view
     */
    public void setAlias1(View view) {
        Toast.makeText(this, "已切换，生效中", Toast.LENGTH_SHORT).show();
        set_alias(strings, strings[1]);
    }

    /**
     * 设置别名2为启动入口
     *
     * @param view
     */
    public void setAlias2(View view) {
        Toast.makeText(this, "已切换，生效中", Toast.LENGTH_SHORT).show();
        set_alias(strings, strings[2]);
    }

    /**
     * 设置别名3为启动入口
     *
     * @param view
     */
    public void setAlias3(View view) {
        Toast.makeText(this, "已切换，生效中", Toast.LENGTH_SHORT).show();
        set_alias(strings, strings[3]);
    }

    /**
     * 设置别名4为启动入口
     *
     * @param view
     */
    public void setAlias4(View view) {
        Toast.makeText(this, "已切换，生效中", Toast.LENGTH_SHORT).show();
        set_alias(strings, strings[4]);
    }

    /**
     * 设置别名5为启动入口
     *
     * @param view
     */
    public void setAlias5(View view) {
        Toast.makeText(this, "已切换，生效中", Toast.LENGTH_SHORT).show();
        set_alias(strings, strings[5]);
    }

    /**
     * 设置别名6为启动入口
     *
     * @param view
     */
    public void setAlias6(View view) {
        Toast.makeText(this, "已切换，生效中", Toast.LENGTH_SHORT).show();
        set_alias(strings, strings[6]);
    }

    /**
     * 设置别名7为启动入口
     *
     * @param view
     */
    public void setAlias7(View view) {
        Toast.makeText(this, "已切换，生效中", Toast.LENGTH_SHORT).show();
        set_alias(strings, strings[7]);
    }

    private void set_alias(String[] strings, String isyou) {
        PackageManager packageManager = getPackageManager();
        for (int i = 0; i < strings.length; i++) {
            packageManager.setComponentEnabledSetting(new ComponentName(this, /*getPackageName() +*/
                            strings[i]), PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP);
        }
        packageManager.setComponentEnabledSetting(new ComponentName(this, /*getPackageName() +*/
                        isyou), PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

}
