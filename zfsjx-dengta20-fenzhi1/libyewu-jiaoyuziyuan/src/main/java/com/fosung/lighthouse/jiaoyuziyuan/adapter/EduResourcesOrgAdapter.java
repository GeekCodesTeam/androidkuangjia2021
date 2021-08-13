/*
 * *********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     17-9-12 下午3:25
 * ********************************************************
 */

package com.fosung.lighthouse.jiaoyuziyuan.adapter;

import android.widget.TextView;

import com.fosung.eduapi.bean.EduResExecutorReplyBean;
import com.fosung.lighthouse.jiaoyuziyuan.R;
import com.zcolin.gui.zrecyclerview.BaseRecyclerAdapter;


/**
 * 党员教育的网上班级的通知公告列表RecyclerView适配器
 */
public class EduResourcesOrgAdapter extends BaseRecyclerAdapter<EduResExecutorReplyBean.DataBean> {

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_edu_res_org;
    }

    @Override
    public void setUpData(final CommonHolder holder, int position, int viewType,
                          EduResExecutorReplyBean.DataBean data) {

        TextView tv_title = getView(holder,R.id.tv_name);
        tv_title.setText(data.name);

    }

}