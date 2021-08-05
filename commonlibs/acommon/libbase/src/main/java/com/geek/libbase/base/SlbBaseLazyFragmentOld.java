package com.geek.libbase.base;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;

public abstract class SlbBaseLazyFragmentOld extends SlbBaseFragment {

    /**
     * 获取MainActivity的ViewPager
     * @return
     */
//    public IndexViewPager findIndexViewPager() {
//        if (BaseAppManager.getInstance().top() instanceof RecDemo3Activity){
//
//        }
//        if (getActivity() == null || !(getActivity() instanceof RecDemo3Activity)) { return null;}
//        return null;
////        return ((RecDemo3Activity) getActivity()).getViewPager();
//    }

    /**
     * 给Viewpager设置事件分发对象
     *
     * @params view
     */
//    public void setPagerTouchBindView(View view) {
//        IndexViewPager pager = findIndexViewPager();
//        if (pager != null) { pager.bind(view);}
//    }
//
//    public void removePagerTouchBindView(View view) {
//        IndexViewPager pager = findIndexViewPager();
//        if (pager != null) { pager.remove(view);}
//    }
    public void call(Object value) {

    }


    boolean mIsPrepare = false;        //视图还没准备好
    boolean mIsVisible = false;        //不可见
    boolean mIsFirstLoad = true;    //第一次加载

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIsPrepare = true;
        lazyLoad();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            mIsVisible = true;
            lazyLoad();
        } else {
            mIsVisible = false;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            mIsVisible = true;
            lazyLoad();
        } else {
            mIsVisible = false;
        }
    }

    private void lazyLoad() {
        //这里进行三个条件的判断，如果有一个不满足，都将不进行加载
        if (!mIsPrepare || !mIsVisible || !mIsFirstLoad) {
            return;
        }
        loadData();
        //数据加载完毕,恢复标记,防止重复加载
        mIsFirstLoad = false;
    }

    public void loadData() {
        //这里进行网络请求和数据装载

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsFirstLoad = true;
        mIsPrepare = false;
        mIsVisible = false;
    }

    public void hideInput() {
        // 先隐藏键盘
        if (getActivity().getCurrentFocus() != null) {
            ((InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
