package com.example.libbase.plugins;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * FileName：ActivityStackManager
 * Create By：liumengqiang
 * Description：创建Activity栈；主要作用是将打开的Activity记录下来，用于支持插件的LaunchModel
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class ActivityStackManager {
    /**
     * String : Activity路径名
     * PluginActivity
     */
    private List<PluginActivityItem> activityList = new ArrayList<>();

    private ActivityStackManager() {

    }

    private static final class CreateActivityStack {
        public static ActivityStackManager instance = new ActivityStackManager();
    }

    public static ActivityStackManager getInstance() {
        return CreateActivityStack.instance;
    }

    /**
     * 是否能够跳转到新的Activity
     *
     * @return
     */
    public boolean checkCanStartNewActivity(Bundle bundle) {
        String reallyActivityName = bundle.getString(PluginConst.REALLY_ACTIVITY_NAME);
        if (StringUtils.stringIsEmpty(reallyActivityName)) {
            throw new IllegalArgumentException("ActivityStackManager-参数异常：reallyActivityName is null");
        }
        int launchModel = bundle.getInt(PluginConst.LAUNCH_MODEL);
        if (launchModel < 0) {
            throw new IllegalArgumentException("ActivityStackManager-参数异常：launchModel is null");
        }
        int index = getActivityInListOfIndex(reallyActivityName);
        if (index == -1) { //不包含，可直接加入
            return true;
        } else { //包含该Activity，此时需要判断启动模式
            return checkLaunchModel(index, launchModel);
        }
    }

    /**
     * 获取要跳转的Activity在集合中的索引值
     *
     * @return
     */
    private int getActivityInListOfIndex(String reallyActivityName) {
        //先遍历一遍数组，获取index的值
        int index = -1;
        for (int i = 0; i < activityList.size(); i++) {
            if (reallyActivityName.equals(activityList.get(i).getActivityReallyName())) {
                index = i;
            }
        }
        return index;
    }

    private boolean checkLaunchModel(int index, int launchModel) {
        switch (launchModel) {
            case PluginConst.LaunchModel.STANDARD: { //标准模式
                return true;
            }
            case PluginConst.LaunchModel.SINGLE_TOP: { //SingleTop模式
                return checkSingleTop(index);
            }
            case PluginConst.LaunchModel.SINGLE_TASK: { //SingleTask模式
                return checkSingleTask(index);
            }
            case PluginConst.LaunchModel.SINGLE_INSTANCE: { //SingInstance模式
                /**
                 * TODO 此处没有做处理
                 */
                return true;
            }
        }
        return true;
    }

    /**
     * 检查是否是SingleTop模式
     *
     * @param index
     * @return
     */
    private boolean checkSingleTop(int index) {
        if (index == activityList.size() - 1) { //是在顶层  此时直接复用此Activity，不用跳转到新的Activity
            return false;
        } else {
            return true;
        }
    }

    /**
     * 检查是否是SingleTask模式
     *
     * @param index
     * @return
     */
    private boolean checkSingleTask(int index) {
        //移除删除index之后的所有元素，并且元素内的Activity执行finish方法
        Iterator<PluginActivityItem> iterator = activityList.iterator();
        int flagIndex = 0;
        while (iterator.hasNext()) {
            PluginActivityItem item = iterator.next();
            if (flagIndex > index) {
                item.getActivity().finish();
                iterator.remove();
            }
            flagIndex += 1;
        }
        return false;
    }

    /**
     * 向List中添加数据
     *
     * @param activity 要入栈的Activity
     * @Bundle
     */
    public void addActivity(Activity activity, Bundle bundle) {
        String reallyActivityName = bundle.getString(PluginConst.REALLY_ACTIVITY_NAME);
        int launchModel = bundle.getInt(PluginConst.LAUNCH_MODEL);
        activityList.add(new PluginActivityItem(activity, launchModel, reallyActivityName)); //直接启动
    }

    /**
     * 删除最后一个元素
     */
    public void removeLastActivity() {
        activityList.remove(activityList.size() - 1);
    }

    /**
     * 清空插件的Activity
     */
    public void clearActivityList() {
        activityList.clear();
    }
}
