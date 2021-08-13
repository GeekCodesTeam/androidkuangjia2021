package com.haier.cellarette.baselibrary.recycleviewalluses.demo6baseadpterhelp;

import android.view.View;

import com.blankj.utilcode.util.ToastUtils;

public class BaseRecActDemo6BeanMsgPresenter {

    private static volatile BaseRecActDemo6BeanMsgPresenter instance;

    public static BaseRecActDemo6BeanMsgPresenter getInstance() {
        if (instance == null) {
            synchronized (BaseRecActDemo6BeanMsgPresenter.class) {
                if (instance == null) {
                    instance = new BaseRecActDemo6BeanMsgPresenter();
                }
            }
        }
        return instance;
    }

    public BaseRecActDemo6BeanMsgPresenter() {

    }

    public void onClickCar(View view, BaseRecActDemo6BeanMsg movie) {
        ToastUtils.showLong(movie.content);
    }

    public void onClickName(View view, BaseRecActDemo6BeanMsg movie) {
        ToastUtils.showLong(movie.name);
    }
}
