package com.fosung.lighthouse.jiaoyuziyuan.util;

import android.content.Context;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.fosung.eduapi.bean.EduResExecutorReplyBean;

import java.util.ArrayList;
import java.util.List;

public class EduResSelectedExecutorsUtil {

    private static EduResSelectedExecutorsUtil mInstance;
    private static final Object mLock = new Object();
    private Context context;
    public static final String ACTION_SELECTED_EXECUTOR_CHANGED = "ACTION_SELECTED_EXECUTOR_CHANGED";
    private List<EduResExecutorReplyBean.DataBean> dataList = new ArrayList<>();


    public static EduResSelectedExecutorsUtil getInstance(Context context) {
        synchronized (mLock) {
            if (mInstance == null) {
                mInstance = new EduResSelectedExecutorsUtil(context.getApplicationContext());
            }
            return mInstance;
        }
    }
    private EduResSelectedExecutorsUtil(Context context){
        this.context = context;
    }

    public List<EduResExecutorReplyBean.DataBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<EduResExecutorReplyBean.DataBean> dataList) {
        this.dataList = dataList;
    }

    public void add(EduResExecutorReplyBean.DataBean bean){
        dataList.add(bean);
        Intent intent = new Intent(ACTION_SELECTED_EXECUTOR_CHANGED);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public void remove(EduResExecutorReplyBean.DataBean bean){
        dataList.remove(bean);
        Intent intent = new Intent(ACTION_SELECTED_EXECUTOR_CHANGED);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public void remove(int index){
        dataList.remove(index);
        Intent intent = new Intent(ACTION_SELECTED_EXECUTOR_CHANGED);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public void clear(){
        dataList.clear();
        Intent intent = new Intent(ACTION_SELECTED_EXECUTOR_CHANGED);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
