package com.example.libbase.widgets;

import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.example.libbase.R;

public class SmartBarInject {

    protected SmartBar mSmartBar;

    public static SmartBarInject inject(IBaseAction action) {
        return new SmartBarInject(action);
    }

    public SmartBarInject(final IBaseAction action) {
        FrameLayout decor = (FrameLayout) action.who().getWindow().getDecorView();
        mSmartBar = (SmartBar) LayoutInflater.from(action.who()).inflate(R.layout.smart_bar, decor, false);
        decor.addView(mSmartBar);
        mSmartBar.setOnKeyListener(new SmartBar.OnKeyListener() {
            @Override
            public void onBackPressed() {
                action.onBackPressed();
            }

            @Override
            public void onHomePressed() {
                action.onHomePressed();
            }
        });
    }

    public SmartBar show(int flag) {
        mSmartBar.show(flag);
        return mSmartBar;
    }

    public SmartBar getSmartBar() {
        return mSmartBar;
    }
}
