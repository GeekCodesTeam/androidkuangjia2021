package com.geek.libbase.plugins2;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BasePluginActivity2 extends AppCompatActivity implements IPlugin2 {

    private int mFrom = FROM_INTERNAL;
    private Activity mProxyActivity;

    protected abstract int getLayoutId();

    protected void setup(Bundle savedInstanceState) {

    }

    @Override
    public void attach(Activity proxyActivity) {
        this.mProxyActivity = proxyActivity;
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        if (saveInstanceState != null) {
            mFrom = saveInstanceState.getInt("FROM_ProxyActivity");
        }
        if (mFrom == FROM_INTERNAL) {
            super.onCreate(saveInstanceState);
            mProxyActivity = this;
        }
//        setContentView(getLayoutId());
//        setup(saveInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        if (mFrom == FORM_EXTERNAL) {
            mProxyActivity.setContentView(layoutResID);
        } else {
            super.setContentView(layoutResID);
        }
    }
}
