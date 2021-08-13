/*
 * *********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     17-5-25 下午1:38
 * ********************************************************
 */

package com.zcolin.frame.app;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;

import java.lang.reflect.Method;

public abstract class BaseFragment extends BaseFrameFrag {

    private LoadingPopupView loadingPopup = null;

    private long mCurrentMs = System.currentTimeMillis();

    @Override
    protected void createView(@Nullable Bundle savedInstanceState) {
        super.createView(savedInstanceState);

        injectZClick();
    }

    protected void showProgressDialog(String msg) {
        if (loadingPopup == null) {
            loadingPopup = new XPopup.Builder(getContext())
                    .dismissOnBackPressed(false).asLoading();
        }
        loadingPopup.setTitle(msg);
        if (!loadingPopup.isShow()) {
            loadingPopup.show();
        }

    }

    protected void hideProgressDialog() {
        if (loadingPopup != null) {
            loadingPopup.dismiss();
        }
    }

    private void injectZClick() {
        Method[] methods = getClass().getDeclaredMethods();
        for (final Method method : methods) {
            method.setAccessible(true);
            ZClick clickArray = method.getAnnotation(ZClick.class);//通过反射api获取方法上面的注解
            if (clickArray != null && clickArray.value().length > 0) {
                final boolean isHasParam = method.getParameterTypes() != null && method.getParameterTypes().length > 0;
                for (int click : clickArray.value()) {
                    final View view = getView(click);//通过注解的值获取View控件
                    if (view != null) {
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    if (isHasParam) {
                                        method.invoke(BaseFragment.this, view);
                                    } else {
                                        method.invoke(BaseFragment.this);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            }
        }
    }

    public String getIdentifier() {
        return getClass().getName() + mCurrentMs;
    }

}
