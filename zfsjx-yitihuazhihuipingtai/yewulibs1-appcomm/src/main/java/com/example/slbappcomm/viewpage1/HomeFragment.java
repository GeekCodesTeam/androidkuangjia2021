package com.example.slbappcomm.viewpage1;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.slbappcomm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 首页
 * @author: zhukai
 * @date: 2019/3/31 20:37
 */
public class HomeFragment extends NewLazyFragment {

    private RecyclerView mRecyclerView;
    private ListAdapter mAdapter;
    private List<String> mData;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("TAG", "HomeFragment onAttach()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("TAG", "HomeFragment onCreate()");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("TAG", "HomeFragment onCreateView()");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_viewpage1_home;
    }

    /**
     * 初始化视图
     *
     * @param view
     */
    @Override
    protected void initView(View view) {
        super.initView(view);
        mRecyclerView = view.findViewById(R.id.rv_home);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        super.initData();
        mData = new ArrayList<>();
        showProgressDialog("请稍后");
        // 模拟数据的延迟加载
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    mData.add("首页文章" + (i + 1));
                }
                mAdapter = new ListAdapter(getActivity(), mData);
                mRecyclerView.setAdapter(mAdapter);
                hideProgressDialog();
            }
        }, 2000);
//        Observable.timer(2, TimeUnit.SECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        for (int i = 0; i < 20; i++) {
//                            mData.add("首页文章" + (i + 1));
//                        }
//                        mAdapter = new ListAdapter(getActivity(), mData);
//                        mRecyclerView.setAdapter(mAdapter);
//                        hideProgressDialog();
//                    }
//                });
    }

    /**
     * 初始化事件
     */
    @Override
    protected void initEvent() {
        super.initEvent();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("TAG", "HomeFragment onActivityCreated()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("TAG", "HomeFragment onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("TAG", "HomeFragment onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("TAG", "HomeFragment onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("TAG", "HomeFragment onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("TAG", "HomeFragment onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "HomeFragment onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("TAG", "HomeFragment onDetach()");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("TAG", "HomeFragment isVisibleToUser = " + isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e("TAG", "HomeFragment onHiddenChanged = " + hidden);
    }
}
