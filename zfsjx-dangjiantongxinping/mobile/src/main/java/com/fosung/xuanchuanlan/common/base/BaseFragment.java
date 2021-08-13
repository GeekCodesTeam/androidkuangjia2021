/*
 * *********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     17-5-25 下午1:38
 * ********************************************************
 */

package com.fosung.xuanchuanlan.common.base;


import android.os.Bundle;
import android.view.View;


import androidx.annotation.Nullable;

import com.fosung.frameutils.app.BaseFrameFrag;

import java.lang.reflect.Method;

public abstract class BaseFragment extends BaseFrameFrag {
    @Override
    protected void createView(@Nullable Bundle savedInstanceState) {
        super.createView(savedInstanceState);



        injectZClick();
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
}
