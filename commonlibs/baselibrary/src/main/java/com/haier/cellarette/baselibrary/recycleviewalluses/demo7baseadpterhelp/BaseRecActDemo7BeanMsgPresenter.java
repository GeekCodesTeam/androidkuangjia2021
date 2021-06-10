package com.haier.cellarette.baselibrary.recycleviewalluses.demo7baseadpterhelp;

import android.view.View;

import com.blankj.utilcode.util.ToastUtils;

public class BaseRecActDemo7BeanMsgPresenter {

    private static volatile BaseRecActDemo7BeanMsgPresenter instance;

    public static BaseRecActDemo7BeanMsgPresenter getInstance() {
        if (instance == null) {
            synchronized (BaseRecActDemo7BeanMsgPresenter.class) {
                if (instance == null) {
                    instance = new BaseRecActDemo7BeanMsgPresenter();
                }
            }
        }
        return instance;
    }

    public BaseRecActDemo7BeanMsgPresenter() {

    }

    public void onClickCar(View view, BaseRecActDemo7BeanMsg movie) {
        ToastUtils.showLong(movie.content);
    }

    public void onClickName(View view, BaseRecActDemo7BeanMsg movie) {
        ToastUtils.showLong(movie.name);
    }
}
