/*
 * *********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     17-9-12 下午3:25
 * ********************************************************
 */

package com.fosung.lighthouse.jiaoyuziyuan.adapter;

import com.fosung.lighthouse.jiaoyuziyuan.R;
import com.zcolin.gui.zrecyclerview.BaseRecyclerAdapter;


/**
 * 党员教育的网上班级的通知公告列表RecyclerView适配器
 */
public class EduResourcesListAdapter extends BaseRecyclerAdapter<String> {

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_edu_resources_list;
    }

    @Override
    public void setUpData(final CommonHolder holder, int position, int viewType,
                          String data) {
    }

}