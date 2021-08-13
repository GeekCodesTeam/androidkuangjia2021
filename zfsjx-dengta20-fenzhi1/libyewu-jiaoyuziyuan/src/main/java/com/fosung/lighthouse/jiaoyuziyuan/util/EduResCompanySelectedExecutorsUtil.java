package com.fosung.lighthouse.jiaoyuziyuan.util;

import android.content.Context;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.fosung.eduapi.bean.EduResCompanyExecutorReplyBean;

import java.util.ArrayList;
import java.util.List;

public class EduResCompanySelectedExecutorsUtil {

    private static EduResCompanySelectedExecutorsUtil mInstance;
    private static final Object mLock = new Object();
    private Context context;
    public static final String ACTION_SELECTED_EXECUTOR_CHANGED = "ACTION_SELECTED_EXECUTOR_CHANGED";
    private List<EduResCompanyExecutorReplyBean.DataBean> dataList = new ArrayList<>();


    public static EduResCompanySelectedExecutorsUtil getInstance(Context context) {
        synchronized (mLock) {
            if (mInstance == null) {
                mInstance = new EduResCompanySelectedExecutorsUtil(context.getApplicationContext());
            }
            return mInstance;
        }
    }
    private EduResCompanySelectedExecutorsUtil(Context context){
        this.context = context;
    }

    public List<EduResCompanyExecutorReplyBean.DataBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<EduResCompanyExecutorReplyBean.DataBean> dataList) {
        this.dataList = dataList;
    }

    public void add(EduResCompanyExecutorReplyBean.DataBean bean){
        dataList.add(bean);
        Intent intent = new Intent(ACTION_SELECTED_EXECUTOR_CHANGED);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public void remove(EduResCompanyExecutorReplyBean.DataBean bean){
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
